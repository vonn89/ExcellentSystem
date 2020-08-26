/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.Report;

import com.excellentsystem.TokoEmasJagoPusat.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.PenjualanCabangHeadDAO;
import com.excellentsystem.TokoEmasJagoPusat.Function;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglNormal;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoPusat.Model.Cabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.Helper.TotalTransaksiCabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.PenjualanCabangHead;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class LaporanTransaksiCabangController {

    @SuppressWarnings("rawtypes")
    private JasperPrint jasperPrint;
    @FXML
    private ImageView imageView;
    @FXML
    private TextField pageField;
    @FXML
    private Label totalPageLabel;
    @FXML
    private Slider zoomLevel;
    private double zoomFactor;
    private double imageHeight;
    private double imageWidth;

    @FXML
    private DatePicker tglAwalPicker;
    @FXML
    private DatePicker tglAkhirPicker;
    @FXML
    private ComboBox<String> cabangCombo;
    private ObservableList<Cabang> cabang = FXCollections.observableArrayList();

    public void initialize() {
        Function.setNumberField(pageField, rp);
        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker)
                -> Function.getDateCellMulai(tglAkhirPicker, LocalDate.parse(sistem.getTglSystem())));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker)
                -> Function.getDateCellAkhir(tglAwalPicker, LocalDate.parse(sistem.getTglSystem())));

        getCabang();
    }

    private void getCabang() {
        try (Connection conPusat = KoneksiPusat.getConnection()) {
            ObservableList<String> listCabang = FXCollections.observableArrayList();
            cabang.clear();
            cabang.addAll(CabangDAO.getAll(conPusat));
            for (Cabang c : cabang) {
                listCabang.add(c.getKodeCabang());
            }
            cabangCombo.setItems(listCabang);
            cabangCombo.getSelectionModel().selectFirst();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" + e);
            alert.showAndWait();
        }
    }

    @FXML
    private void lihatLaporan() {
        Cabang c = null;
        for (Cabang x : cabang) {
            if (x.getKodeCabang().equals(cabangCombo.getSelectionModel().getSelectedItem())) {
                c = x;
            }
        }
        if (c != null) {
            try (Connection conPusat = KoneksiPusat.getConnection()) {
                try (Connection con = KoneksiCabang.getConnection(c)) {
                    List<TotalTransaksiCabang> listTransaksi = new ArrayList<>();
                    for (LocalDate date = tglAwalPicker.getValue(); date.isBefore(tglAkhirPicker.getValue()); date = date.plusDays(1)) {
                        TotalTransaksiCabang t = new TotalTransaksiCabang();
                        t.setTanggal(date.toString());
                        t.setPenjualan(0);
                        t.setPembelian(0);
                        t.setTerimaHutang(0);
                        t.setHutangLunas(0);
                        t.setHutangBunga(0);
                        t.setSetorPenjualan(0);
                        t.setSetorRR(0);
                        t.setMintaUangBankPenjualan(0);
                        t.setMintaUangBankRR(0);
                        t.setPembelianPusat(0);
                        listTransaksi.add(t);
                    }
                    String sql = "select concat('20',mid(no_keuangan,8,2),'-',mid(no_keuangan,10,2),'-',mid(no_keuangan,12,2)),tipe_kasir,kategori,sum(jumlah_rp) from tt_keuangan "
                            + " where tipe_keuangan = 'Kas' and "
                            + " mid(no_keuangan,8,6) between " + tglSystem.format(tglBarang.parse(tglAwalPicker.getValue().toString())) + " and " + tglSystem.format(tglBarang.parse(tglAkhirPicker.getValue().toString())) + " "
                            + " group by mid(no_keuangan,8,6),tipe_kasir,kategori";
                    PreparedStatement ps = con.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        for (TotalTransaksiCabang t : listTransaksi) {
                            if (t.getTanggal().equals(rs.getString(1))) {
                                if (rs.getString(3).equals("Penjualan Umum")) {
                                    t.setPenjualan(rs.getDouble(4));
                                }
                                if (rs.getString(3).equals("Pembelian Umum")) {
                                    t.setPembelian(rs.getDouble(4) * -1);
                                }
                                if (rs.getString(3).equals("Terima Hutang")) {
                                    t.setTerimaHutang(rs.getDouble(4) * -1);
                                }
                                if (rs.getString(3).equals("Hutang Lunas")) {
                                    t.setHutangLunas(rs.getDouble(4));
                                }
                                if (rs.getString(3).equals("Hutang Bunga")) {
                                    t.setHutangBunga(rs.getDouble(4));
                                }
                                if (rs.getString(3).equals("Setor Uang Kas") && rs.getString(2).equals("Kasir")) {
                                    t.setSetorPenjualan(rs.getDouble(4) * -1);
                                }
                                if (rs.getString(3).equals("Setor Uang Kas") && rs.getString(2).equals("RR")) {
                                    t.setSetorRR(rs.getDouble(4) * -1);
                                }
                                if (rs.getString(3).equals("Terima Uang Bank") && rs.getString(2).equals("Kasir")) {
                                    t.setMintaUangBankPenjualan(rs.getDouble(4));
                                }
                                if (rs.getString(3).equals("Terima Uang Bank") && rs.getString(2).equals("RR")) {
                                    t.setMintaUangBankRR(rs.getDouble(4));
                                }
                            }
                        }
                    }
                    List<PenjualanCabangHead> listPenjualanCabang = PenjualanCabangHeadDAO.getAllByDateAndCabangAndStatus(conPusat,
                            tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), c.getKodeCabang(), "true");
                    for (PenjualanCabangHead p : listPenjualanCabang) {
                        for (TotalTransaksiCabang t : listTransaksi) {
                            if (t.getTanggal().equals(tglBarang.format(tglSystem.parse(p.getNoPenjualanCabang().substring(7, 13))))) {
                                t.setPembelianPusat(t.getPembelianPusat()+ p.getTotalPenjualan());
                            }
                        }
                    }

                    JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanTransaksiCabang.jrxml"));
                    JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listTransaksi);
                    Map hash = new HashMap();
                    hash.put("tanggal", tglNormal.format(tglBarang.parse(tglAwalPicker.getValue().toString())) + " - "
                            + tglNormal.format(tglBarang.parse(tglAkhirPicker.getValue().toString())));
                    hash.put("cabang", cabangCombo.getSelectionModel().getSelectedItem());
                    JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                    jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
                    zoomFactor = 1.5d;
                    zoomLevel.setValue(100d);
                    imageView.setX(0);
                    imageView.setY(0);
                    imageHeight = jasperPrint.getPageHeight();
                    imageWidth = jasperPrint.getPageWidth();
                    zoomLevel.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                        zoomFactor = newValue.doubleValue() * 1.5 / 100;
                        imageView.setFitHeight(imageHeight * zoomFactor);
                        imageView.setFitWidth(imageWidth * zoomFactor);
                    });
                    if (jasperPrint.getPages().size() > 0) {
                        showImage(1);
                        pageField.setText("1");
                        totalPageLabel.setText(String.valueOf(jasperPrint.getPages().size()));
                    } else {
                        imageView.setImage(null);
                        pageField.setText("0");
                        totalPageLabel.setText("0");
                    }
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Application error - \n" + e);
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void changePage() {
        try {
            int pageNumber = Integer.parseInt(pageField.getText().replaceAll(",", ""));
            if (1 <= pageNumber && pageNumber <= jasperPrint.getPages().size()) {
                showImage(pageNumber);
                pageField.setText(rp.format(pageNumber));
            } else {
                pageField.setText("0");
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" + e);
            alert.showAndWait();
        }
    }

    @FXML
    public void prevPage() {
        try {
            if (Integer.parseInt(pageField.getText().replaceAll(",", "")) > 1) {
                int pageNumber = Integer.parseInt(pageField.getText().replaceAll(",", "")) - 1;
                showImage(pageNumber);
                pageField.setText(rp.format(pageNumber));
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" + e);
            alert.showAndWait();
        }
    }

    @FXML
    public void nextPage() {
        try {
            if (Integer.parseInt(pageField.getText().replaceAll(",", "")) < jasperPrint.getPages().size()) {
                int pageNumber = Integer.parseInt(pageField.getText().replaceAll(",", "")) + 1;
                showImage(pageNumber);
                pageField.setText(rp.format(pageNumber));
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" + e);
            alert.showAndWait();
        }
    }

    @FXML
    private void firstPage() {
        try {
            if (jasperPrint.getPages().size() > 0) {
                showImage(1);
                pageField.setText("1");
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" + e);
            alert.showAndWait();
        }
    }

    @FXML
    private void lastPage() {
        try {
            if (jasperPrint.getPages().size() > 0) {
                showImage(jasperPrint.getPages().size());
                pageField.setText(rp.format(jasperPrint.getPages().size()));
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" + e);
            alert.showAndWait();
        }
    }

    private void showImage(int pageNumber) throws Exception {
        imageView.setFitHeight(imageHeight * zoomFactor);
        imageView.setFitWidth(imageWidth * zoomFactor);
        BufferedImage image = (BufferedImage) JasperPrintManager.printPageToImage(jasperPrint, pageNumber - 1, 2);
        WritableImage fxImage = new WritableImage(jasperPrint.getPageWidth(), jasperPrint.getPageHeight());
        imageView.setImage(SwingFXUtils.toFXImage(image, fxImage));
    }

    @FXML
    private void print() {
        try {
            JasperPrintManager.printReport(jasperPrint, true);
        } catch (JRException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" + e);
            alert.showAndWait();
        }
    }

}
