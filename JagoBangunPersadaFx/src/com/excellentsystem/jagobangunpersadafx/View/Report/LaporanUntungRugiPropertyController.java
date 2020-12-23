/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Report;

import com.excellentsystem.jagobangunpersadafx.DAO.KategoriTransaksiDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.KeuanganDAO;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.qty;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import com.excellentsystem.jagobangunpersadafx.Model.KategoriTransaksi;
import com.excellentsystem.jagobangunpersadafx.Model.Keuangan;
import com.excellentsystem.jagobangunpersadafx.Model.Property;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.AddPropertyController;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author yunaz
 */
public class LaporanUntungRugiPropertyController {

    @FXML
    private Label namaPropertyLabel;
    @FXML
    private Label luasTanahLabel;
    @FXML
    private Label luasBangunanLabel;
    @FXML
    private Label hargaPropertyLabel;
    @FXML
    private Label diskonLabel;
    @FXML
    private Label nilaiPropertyLabel;
    @FXML
    private Label nilaiPropertyPerMeterLabel;

    @FXML
    private Label terimaTandaJadiLabel;
    @FXML
    private Label terimaDownPaymentLabel;
    @FXML
    private Label terimaPencairanKPRLabel;
    @FXML
    private Label terimaAngsuranLabel;
    @FXML
    private Label pendapatanLabel;

    @FXML
    private Label totalPemasukanLabel;

    private List<Keuangan> terimaTandaJadiList = new ArrayList<>();
    private List<Keuangan> terimaDownPaymentList = new ArrayList<>();
    private List<Keuangan> terimaPencairanKPRList = new ArrayList<>();
    private List<Keuangan> terimaAngsuranList = new ArrayList<>();
    private List<Keuangan> pendapatanList = new ArrayList<>();

    @FXML
    private Label pembelianTanahLabel;
    @FXML
    private Label pembangunanLabel;
    @FXML
    private Label realisasiProyekLabel;
    @FXML
    private Label bebanLabel;

    private List<Keuangan> pembelianTanahList = new ArrayList<>();
    private List<Keuangan> realisasiProyekList = new ArrayList<>();
    private List<Keuangan> pembangunanList = new ArrayList<>();
    private List<Keuangan> bebanList = new ArrayList<>();

    @FXML
    private Label totalPengeluaranLabel;

    private Property property = null;
    private ObservableList<Keuangan> allKeuangan = FXCollections.observableArrayList();
    private ObservableList<KategoriTransaksi> allKategori = FXCollections.observableArrayList();
    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    private void reset() {
        namaPropertyLabel.setText("0");
        luasTanahLabel.setText("0");
        luasBangunanLabel.setText("0");
        hargaPropertyLabel.setText("0");
        diskonLabel.setText("0");
        nilaiPropertyLabel.setText("0");
        nilaiPropertyPerMeterLabel.setText("0");

        terimaTandaJadiLabel.setText("0");
        terimaDownPaymentLabel.setText("0");
        terimaPencairanKPRLabel.setText("0");
        terimaAngsuranLabel.setText("0");
        pendapatanLabel.setText("0");

        totalPemasukanLabel.setText("0");

        terimaTandaJadiList = new ArrayList<>();
        terimaDownPaymentList = new ArrayList<>();
        terimaPencairanKPRList = new ArrayList<>();
        terimaAngsuranList = new ArrayList<>();
        pendapatanList = new ArrayList<>();

        pembelianTanahLabel.setText("0");
        realisasiProyekLabel.setText("0");
        pembangunanLabel.setText("0");
        bebanLabel.setText("0");

        pembelianTanahList = new ArrayList<>();
        realisasiProyekList = new ArrayList<>();
        pembangunanList = new ArrayList<>();
        bebanList = new ArrayList<>();

        totalPengeluaranLabel.setText("0");

        property = null;
        allKeuangan.clear();
    }

    @FXML
    private void addProperty() {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/AddProperty.fxml");
        AddPropertyController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        List<String> status = new ArrayList<>();
        status.add("Available");
        status.add("Reserved");
        status.add("Sold");
        x.getProperty(status);
        x.propertyTable.setRowFactory((TableView<Property> tableView) -> {
            final TableRow<Property> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                    reset();
                    property = row.getItem();
                    namaPropertyLabel.setText(property.getNamaProperty());
                    luasTanahLabel.setText(qty.format(property.getLuasTanah()));
                    luasBangunanLabel.setText(qty.format(property.getLuasBangunan()));
                    hargaPropertyLabel.setText(rp.format(property.getHargaJual()));
                    diskonLabel.setText(rp.format(property.getDiskon()));
                    nilaiPropertyLabel.setText(rp.format(property.getNilaiProperty()));
                    nilaiPropertyPerMeterLabel.setText(rp.format(property.getNilaiProperty() / property.getLuasTanah()));
                    getKeuangan();
                    mainApp.closeDialog(mainApp.MainStage, stage);
                }
            });
            return row;
        });
    }

    private void getKeuangan() {
        Task<List<Keuangan>> task = new Task<List<Keuangan>>() {
            @Override
            public List<Keuangan> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    allKategori.clear();
                    allKategori.addAll(KategoriTransaksiDAO.getAll(con));
                    if (property != null) {
                        return KeuanganDAO.getAllByKodeProperty(con, property.getKodeProperty());
                    } else {
                        return null;
                    }
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try {
                mainApp.closeLoading();
                allKeuangan.clear();
                allKeuangan.addAll(task.getValue());
                setData();
            } catch (Exception ex) {
                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
            }
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }

    private void setData() {
        try {
            for (Keuangan k : allKeuangan) {
                if (k.getKategori().equals("Terima Tanda Jadi") && !k.getTipeKeuangan().equals("HUTANG")) {
                    terimaTandaJadiLabel.setText(rp.format(
                            Double.parseDouble(terimaTandaJadiLabel.getText().replaceAll(",", "")) + k.getJumlahRp()
                    ));
                    terimaTandaJadiList.add(k);
                }
                if (k.getKategori().equals("Terima Down Payment") && !k.getTipeKeuangan().equals("HUTANG")) {
                    terimaDownPaymentLabel.setText(rp.format(
                            Double.parseDouble(terimaDownPaymentLabel.getText().replaceAll(",", "")) + k.getJumlahRp()
                    ));
                    terimaDownPaymentList.add(k);
                }
                if (k.getKategori().equals("Pencairan KPR") && !k.getTipeKeuangan().equals("PIUTANG")) {
                    terimaPencairanKPRLabel.setText(rp.format(
                            Double.parseDouble(terimaPencairanKPRLabel.getText().replaceAll(",", "")) + k.getJumlahRp()
                    ));
                    terimaPencairanKPRList.add(k);
                }
                if (k.getKategori().equals("Terima Pembayaran Angsuran") && !k.getTipeKeuangan().equals("PIUTANG")) {
                    terimaAngsuranLabel.setText(rp.format(
                            Double.parseDouble(terimaAngsuranLabel.getText().replaceAll(",", "")) + k.getJumlahRp()
                    ));
                    terimaAngsuranList.add(k);
                }
                for (KategoriTransaksi kt : allKategori) {
                    if (kt.getJenisTransaksi().equals("D")) {
                        if (k.getKategori().equals(kt.getKodeKategori()) && !k.getTipeKeuangan().equals("PENDAPATAN")) {
                            pendapatanLabel.setText(rp.format(
                                    Double.parseDouble(pendapatanLabel.getText().replaceAll(",", "")) + k.getJumlahRp()
                            ));
                            pendapatanList.add(k);
                        }
                    }
                }

                if (k.getKategori().equals("Pembelian Tanah")) {
                    pembelianTanahLabel.setText(rp.format(
                            Double.parseDouble(pembelianTanahLabel.getText().replaceAll(",", "")) + k.getJumlahRp() * -1
                    ));
                    pembelianTanahList.add(k);
                }
                if (k.getDeskripsi().startsWith("Pemecahan Property")) {
                    pembelianTanahLabel.setText(rp.format(
                            Double.parseDouble(pembelianTanahLabel.getText().replaceAll(",", "")) + k.getJumlahRp()
                    ));
                    pembelianTanahList.add(k);
                }
                if (k.getDeskripsi().startsWith("Penggabungan Property")) {
                    pembelianTanahLabel.setText(rp.format(
                            Double.parseDouble(pembelianTanahLabel.getText().replaceAll(",", "")) + k.getJumlahRp()
                    ));
                    pembelianTanahList.add(k);
                }
                if (k.getKategori().equals("Realisasi Proyek")) {
                    realisasiProyekLabel.setText(rp.format(
                            Double.parseDouble(realisasiProyekLabel.getText().replaceAll(",", "")) + k.getJumlahRp() * -1
                    ));
                    realisasiProyekList.add(k);
                }
                if (k.getKategori().equals("Pembangunan")) {
                    pembangunanLabel.setText(rp.format(
                            Double.parseDouble(pembangunanLabel.getText().replaceAll(",", "")) + k.getJumlahRp() * -1
                    ));
                    pembangunanList.add(k);
                }
                for (KategoriTransaksi kt : allKategori) {
                    if (kt.getJenisTransaksi().equals("K")) {
                        if (k.getKategori().equals(kt.getKodeKategori()) && !k.getTipeKeuangan().equals("BEBAN")) {
                            bebanLabel.setText(rp.format(
                                    Double.parseDouble(bebanLabel.getText().replaceAll(",", "")) + k.getJumlahRp() * -1
                            ));
                            bebanList.add(k);
                        }
                    }
                }
            }
            totalPemasukanLabel.setText(rp.format(
                    Double.parseDouble(terimaTandaJadiLabel.getText().replaceAll(",", ""))
                    + Double.parseDouble(terimaDownPaymentLabel.getText().replaceAll(",", ""))
                    + Double.parseDouble(terimaPencairanKPRLabel.getText().replaceAll(",", ""))
                    + Double.parseDouble(terimaAngsuranLabel.getText().replaceAll(",", ""))
                    + Double.parseDouble(pendapatanLabel.getText().replaceAll(",", ""))
            ));
            totalPengeluaranLabel.setText(rp.format(
                    Double.parseDouble(pembelianTanahLabel.getText().replaceAll(",", ""))
                    + Double.parseDouble(realisasiProyekLabel.getText().replaceAll(",", ""))
                    + Double.parseDouble(pembangunanLabel.getText().replaceAll(",", ""))
                    + Double.parseDouble(bebanLabel.getText().replaceAll(",", ""))
            ));
        } catch (Exception ex) {
            mainApp.showMessage(Modality.NONE, "Error", ex.toString());
        }
    }

    private void viewDetail(List<Keuangan> keuangan) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Report/DetailKeuangan.fxml");
        DetailKeuanganController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setKeuangan(keuangan);
    }

    @FXML
    private void viewDetailTerimaTandaJadi() {
        viewDetail(terimaTandaJadiList);
    }

    @FXML
    private void viewDetailTerimaDownPayment() {
        viewDetail(terimaDownPaymentList);
    }

    @FXML
    private void viewDetailTerimaPencairanKPR() {
        viewDetail(terimaPencairanKPRList);
    }

    @FXML
    private void viewDetailTerimaAngsuran() {
        viewDetail(terimaAngsuranList);
    }

    @FXML
    private void viewDetailPendapatan() {
        viewDetail(pendapatanList);
    }

    @FXML
    private void viewDetailPembelianTanah() {
        viewDetail(pembelianTanahList);
    }

    @FXML
    private void viewDetailRealisasi() {
        viewDetail(realisasiProyekList);
    }

    @FXML
    private void viewDetailPembangunan() {
        viewDetail(pembangunanList);
    }

    @FXML
    private void viewDetailBeban() {
        viewDetail(bebanList);
    }
}
