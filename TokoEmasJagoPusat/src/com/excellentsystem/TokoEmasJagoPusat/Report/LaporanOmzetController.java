/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.Report;

import com.excellentsystem.TokoEmasJagoPusat.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.TempTransaksiCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.Function;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglNormal;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoPusat.Model.Cabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.Otoritas;
import com.excellentsystem.TokoEmasJagoPusat.Model.TempTransaksiCabang;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class LaporanOmzetController {

    @SuppressWarnings("rawtypes")
    private JasperPrint jasperPrint;
    @FXML private ImageView imageView;
    @FXML private TextField pageField;
    @FXML private Label totalPageLabel;
    @FXML private Slider zoomLevel;
    private double zoomFactor;
    private double imageHeight;
    private double imageWidth;

    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    
    @FXML private TableView<Cabang> cabangTable;
    @FXML private TableColumn<Cabang, String> kodeCabangColumn;
    @FXML private TableColumn<Cabang, Boolean> checkedColumn;
    @FXML private CheckBox checkAll;
    
    private ObservableList<Cabang> cabang = FXCollections.observableArrayList();
    public void initialize(){
        kodeCabangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeCabangProperty());
        checkedColumn.setCellValueFactory(cellData -> cellData.getValue().checkProperty());
        checkedColumn.setCellFactory(CheckBoxTableCell.forTableColumn(
                (Integer param) -> cabangTable.getItems().get(param).checkProperty()));
        
        Function.setNumberField(pageField, rp);
        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellMulai(tglAkhirPicker, LocalDate.parse(sistem.getTglSystem())));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellAkhir(tglAwalPicker, LocalDate.parse(sistem.getTglSystem())));
        
        final ContextMenu menuOtoritas = new ContextMenu();
        MenuItem refreshOtoritas = new MenuItem("Refresh");
        refreshOtoritas.setOnAction((ActionEvent event) -> {
            getCabang();
        });
        menuOtoritas.getItems().addAll(refreshOtoritas);
        cabangTable.setContextMenu(menuOtoritas);
        cabangTable.setRowFactory(table -> {
            TableRow<Cabang> row = new TableRow<Cabang>(){
                @Override
                public void updateItem(Cabang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(null);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            getCabang();
                        });
                        rowMenu.getItems().addAll(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2 && row.getItem()!=null)
                    if(row.getItem().isCheck())
                        row.getItem().setCheck(false);
                    else
                        row.getItem().setCheck(true);
            });
            return row;
        });
        
        getCabang();
        cabangTable.setItems(cabang);
    }
    private void getCabang(){
        try(Connection conPusat = KoneksiPusat.getConnection()){
            cabang.clear();
            cabang.addAll(CabangDAO.getAll(conPusat));
            checkAll();
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
        }
    }  
    @FXML
    private void checkAll(){
        for(Cabang c : cabang){
            c.setCheck(checkAll.isSelected());
        }
        cabangTable.refresh();
    }
    @FXML
    private void importData(){
        String status = "";
        try(Connection conPusat = KoneksiPusat.getConnection()){
            System.out.println("Connect to database pusat");

            for(Cabang c : cabang){
                if(c.isCheck()){
                    System.out.println("Connect to database cabang "+c.getKodeCabang());
                    try(Connection conCabang = KoneksiCabang.getConnection(c)){
                        System.out.println("Getting data from database "+c.getKodeCabang());
                        TempTransaksiCabangDAO.deleteByTanggalAndCabang(conPusat, 
                            tglAwalPicker.getValue().toString(), 
                            tglAkhirPicker.getValue().toString(),
                            c.getKodeCabang());
                        PreparedStatement ps = conCabang.prepareStatement("select concat('20',mid(no_penjualan,8,2),'-',mid(no_penjualan,10,2),'-',mid(no_penjualan,12,2)),sum(grandtotal) "
                                + " from tt_penjualan_head where mid(no_penjualan,8,6) between ? and ? and "
                                + " status='true' group by mid(no_penjualan,8,6)");
                        ps.setString(1, tglSystem.format(tglBarang.parse(tglAwalPicker.getValue().toString())));
                        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhirPicker.getValue().toString())));
                        ResultSet rs = ps.executeQuery();
                        while(rs.next()){
                            TempTransaksiCabang o = TempTransaksiCabangDAO.get(conPusat, rs.getString(1), c.getKodeCabang(), "");
                            if(o!=null){
                                o.setTotalPenjualan(rs.getDouble(2)/1000);
                                TempTransaksiCabangDAO.update(conPusat, o);
                            }else{
                                o = new TempTransaksiCabang();
                                o.setTanggal(rs.getString(1));
                                o.setKodeCabang(c.getKodeCabang());
                                o.setKodeSales("");
                                o.setTotalPenjualan(rs.getDouble(2)/1000);
                                TempTransaksiCabangDAO.insert(conPusat, o);
                            }
                        }
                        System.out.println("Succesfully getting data from database "+c.getKodeCabang());
                        status = status + c.getKodeCabang()+" : Berhasil\n";
                    }catch(Exception e){
                        e.printStackTrace();
                        System.out.println("Cant connect to database "+c.getKodeCabang());
                        status = status + c.getKodeCabang()+" : Gagal\n";
                    }
                }
            }
            System.out.println("Successfully import data");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText("Status Import Data :\n"+status);
            alert.showAndWait();
        }catch(Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +ex);
            alert.showAndWait();
        }
    }
    @FXML
    private void lihatLaporan(){
        try(Connection con = KoneksiPusat.getConnection()){
            List<TempTransaksiCabang> data = new ArrayList<>();
            for(Cabang c : cabang){
                if(c.isCheck()){
                    List<TempTransaksiCabang> listTransaksiCabang = TempTransaksiCabangDAO.getAllByDateAndCabangAndSales(con, 
                            tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), c.getKodeCabang(), "%");
                    for(TempTransaksiCabang t2 : listTransaksiCabang){
                        boolean status = true;
                        for(TempTransaksiCabang t : data){
                            if(t.getTanggal().equals(t2.getTanggal()) && t.getKodeCabang().equals(t2.getKodeCabang())){
                                t.setQtyPenjualan(t.getQtyPenjualan()+t2.getQtyPenjualan());
                                t.setTotalPenjualan(t.getTotalPenjualan()+t2.getTotalPenjualan());
                                t.setQtyPembelian(t.getQtyPembelian()+t2.getQtyPembelian());
                                t.setTotalPembelian(t.getTotalPembelian()+t2.getTotalPembelian());
                                t.setQtyTerimaHutang(t.getQtyTerimaHutang()+t2.getQtyTerimaHutang());
                                t.setTotalTerimaHutang(t.getTotalTerimaHutang()+t2.getTotalTerimaHutang());
                                t.setQtyHutangLunas(t.getQtyHutangLunas()+t2.getQtyHutangLunas());
                                t.setTotalHutangLunas(t.getTotalHutangLunas()+t2.getTotalHutangLunas());
                                t.setTotalHutangBunga(t.getTotalHutangBunga()+t2.getTotalHutangBunga());
                                status = false;
                            }
                        }
                        if(status)
                            data.add(t2);
                    }
                }
            }
            Collections.sort(data, (o1, o2) -> {
                int sComp = ((TempTransaksiCabang) o1).getTanggal().compareTo(((TempTransaksiCabang) o2).getTanggal());
                if (sComp != 0) 
                    return sComp;
                return ((TempTransaksiCabang) o1).getKodeCabang().compareTo(((TempTransaksiCabang) o2).getKodeCabang());
            });
            JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanOmzet.jrxml"));
            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(data);
            Map hash = new HashMap();
            hash.put("tanggal", tglNormal.format(tglBarang.parse(tglAwalPicker.getValue().toString()))+" - "+
                        tglNormal.format(tglBarang.parse(tglAkhirPicker.getValue().toString())));
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
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
            if(jasperPrint.getPages().size() > 0){
                showImage(1);
                pageField.setText("1");
                totalPageLabel.setText(String.valueOf(jasperPrint.getPages().size()));
            }else{
                imageView.setImage(null);
                pageField.setText("0");
                totalPageLabel.setText("0");
            }
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
        }
    }
    @FXML
    private void lihatDiagram(){
        try(Connection con = KoneksiPusat.getConnection()){
            List<TempTransaksiCabang> data = new ArrayList<>();
            for(Cabang c : cabang){
                if(c.isCheck()){
                    List<TempTransaksiCabang> listTransaksiCabang = TempTransaksiCabangDAO.getAllByDateAndCabangAndSales(con, 
                            tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), c.getKodeCabang(), "%");
                    for(TempTransaksiCabang t2 : listTransaksiCabang){
                        boolean status = true;
                        for(TempTransaksiCabang t : data){
                            if(t.getTanggal().equals(t2.getTanggal()) && t.getKodeCabang().equals(t2.getKodeCabang())){
                                t.setQtyPenjualan(t.getQtyPenjualan()+t2.getQtyPenjualan());
                                t.setTotalPenjualan(t.getTotalPenjualan()+t2.getTotalPenjualan());
                                t.setQtyPembelian(t.getQtyPembelian()+t2.getQtyPembelian());
                                t.setTotalPembelian(t.getTotalPembelian()+t2.getTotalPembelian());
                                t.setQtyTerimaHutang(t.getQtyTerimaHutang()+t2.getQtyTerimaHutang());
                                t.setTotalTerimaHutang(t.getTotalTerimaHutang()+t2.getTotalTerimaHutang());
                                t.setQtyHutangLunas(t.getQtyHutangLunas()+t2.getQtyHutangLunas());
                                t.setTotalHutangLunas(t.getTotalHutangLunas()+t2.getTotalHutangLunas());
                                t.setTotalHutangBunga(t.getTotalHutangBunga()+t2.getTotalHutangBunga());
                                status = false;
                            }
                        }
                        if(status)
                            data.add(t2);
                    }
                }
            }
            Collections.sort(data, (o1, o2) -> {
                int sComp = ((TempTransaksiCabang) o1).getTanggal().compareTo(((TempTransaksiCabang) o2).getTanggal());
                if (sComp != 0) 
                    return sComp;
                return ((TempTransaksiCabang) o1).getKodeCabang().compareTo(((TempTransaksiCabang) o2).getKodeCabang());
            });
            JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanOmzetDiagram.jrxml"));
            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(data);
            Map hash = new HashMap();
            hash.put("tanggal", tglNormal.format(tglBarang.parse(tglAwalPicker.getValue().toString()))+" - "+
                        tglNormal.format(tglBarang.parse(tglAkhirPicker.getValue().toString())));
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
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
            if(jasperPrint.getPages().size() > 0){
                showImage(1);
                pageField.setText("1");
                totalPageLabel.setText(String.valueOf(jasperPrint.getPages().size()));
            }else{
                imageView.setImage(null);
                pageField.setText("0");
                totalPageLabel.setText("0");
            }
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
        }
    }
    @FXML
    private void changePage(){
        try {
            int pageNumber = Integer.parseInt(pageField.getText().replaceAll(",", ""));
            if(1<=pageNumber && pageNumber<=jasperPrint.getPages().size()){
                showImage(pageNumber);
                pageField.setText(rp.format(pageNumber));
            }else{
                pageField.setText("0");
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
        }
    }
    @FXML
    public void prevPage(){
        try {
            if(Integer.parseInt(pageField.getText().replaceAll(",", ""))>1){
                int pageNumber = Integer.parseInt(pageField.getText().replaceAll(",", ""))-1;
                showImage(pageNumber);
                pageField.setText(rp.format(pageNumber));
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
        }
    }
    @FXML
    public void nextPage(){
        try {
            if(Integer.parseInt(pageField.getText().replaceAll(",", ""))<jasperPrint.getPages().size()){
                int pageNumber = Integer.parseInt(pageField.getText().replaceAll(",", ""))+1;
                showImage(pageNumber);
                pageField.setText(rp.format(pageNumber));
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
        }
    }
    @FXML
    private void firstPage(){
        try {
            if(jasperPrint.getPages().size() > 0){
                showImage(1);
                pageField.setText("1");
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
        }
    }
    @FXML
    private void lastPage(){
        try {
            if(jasperPrint.getPages().size() > 0){
                showImage(jasperPrint.getPages().size());
                pageField.setText(rp.format(jasperPrint.getPages().size()));
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
        }
    }
    private void showImage(int pageNumber)throws Exception{
        imageView.setFitHeight(imageHeight * zoomFactor);
        imageView.setFitWidth(imageWidth * zoomFactor);
        BufferedImage image = (BufferedImage) JasperPrintManager.printPageToImage(jasperPrint, pageNumber-1, 2);
        WritableImage fxImage = new WritableImage(jasperPrint.getPageWidth(), jasperPrint.getPageHeight());
        imageView.setImage(SwingFXUtils.toFXImage(image, fxImage));
    }
    @FXML
    private void print() {
        try{
            JasperPrintManager.printReport(jasperPrint, true);
        }catch(JRException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
        }
    }
    
    
}
