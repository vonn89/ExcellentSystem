/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.Report;

import com.excellentsystem.TokoEmasJagoPusat.DAO.BarangCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.BatalBarcodeDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.StokBarangCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.UserDAO;
import com.excellentsystem.TokoEmasJagoPusat.Function;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import static com.excellentsystem.TokoEmasJagoPusat.Main.gr;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglNormal;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSql;
import static com.excellentsystem.TokoEmasJagoPusat.Main.user;
import com.excellentsystem.TokoEmasJagoPusat.Model.BatalBarcode;
import com.excellentsystem.TokoEmasJagoPusat.Model.Cabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.User;
import java.awt.image.BufferedImage;
import java.sql.Connection;
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

public class LaporanBatalBarcodeController {

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
    @FXML private ComboBox<String> cabangCombo;
    @FXML private ComboBox<String> gudangCombo;
    @FXML private ComboBox<String> userCombo;
    @FXML private TextField searchField;
    private ObservableList<String> cabang = FXCollections.observableArrayList();
    private ObservableList<String> gudang = FXCollections.observableArrayList();
    private ObservableList<String> listUser = FXCollections.observableArrayList();
    public void initialize(){
        Function.setNumberField(pageField, rp);
        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellMulai(tglAkhirPicker, LocalDate.parse(sistem.getTglSystem())));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellAkhir(tglAwalPicker, LocalDate.parse(sistem.getTglSystem())));
        gudangCombo.setItems(gudang);
        gudang.add("Semua");
        gudang.add("New");
        gudang.add("SP");
        gudangCombo.getSelectionModel().selectFirst();
        cabangCombo.setItems(cabang);
        userCombo.setItems(listUser);
        getCabangAndUser();
    }
    private void getCabangAndUser(){
        try(Connection conPusat = KoneksiPusat.getConnection()){
            List<Cabang> listCabang = CabangDAO.getAll(conPusat);
            cabang.add("Semua");
            for(Cabang c: listCabang){
                cabang.add(c.getKodeCabang());
            }
            cabangCombo.getSelectionModel().selectFirst();
            
            List<User> allUser = UserDAO.getAll(conPusat);
            listUser.add("Semua");
            for(User u : allUser){
                listUser.add(u.getKodeUser());
            }
            userCombo.getSelectionModel().select(user.getKodeUser());
            getBarang();
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
        }
    }  
    @FXML
    private void getBarang(){
        if(cabangCombo.getSelectionModel().getSelectedItem()!=null || gudangCombo.getSelectionModel().getSelectedItem()!=null || userCombo.getSelectionModel().getSelectedItem()!=null){
            try(Connection con = KoneksiPusat.getConnection()){
                String user = "%";
                if(userCombo.getSelectionModel().getSelectedItem()!=null && !userCombo.getSelectionModel().getSelectedItem().equals("Semua"))
                    user = userCombo.getSelectionModel().getSelectedItem();
                List<BatalBarcode> listBatal = BatalBarcodeDAO.getAllByDateAndUser(con, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), user);
                List<BatalBarcode> batalBarcode = new ArrayList<>();
                for(BatalBarcode bb : listBatal){
                    bb.setBarangCabang(BarangCabangDAO.get(con, bb.getKodeBarcode()));
                    bb.setStokBarangCabang(StokBarangCabangDAO.getBatalBarcode(con, bb.getBarangCabang().getInputDate().substring(0, 10), bb.getKodeBarcode()));
                    
                    boolean statusCabang = false;
                    if(cabangCombo.getSelectionModel().getSelectedItem().equals("Semua") || 
                            bb.getStokBarangCabang().getKodeCabang().equals(cabangCombo.getSelectionModel().getSelectedItem()))
                        statusCabang = true;
                    
                    boolean statusGudang = false;
                    if(gudangCombo.getSelectionModel().getSelectedItem().equals("Semua") || 
                            bb.getBarangCabang().getAsalBarang().equals(gudangCombo.getSelectionModel().getSelectedItem())){
                        statusGudang = true;
                    }
                    
                    if(statusCabang && statusGudang)
                        batalBarcode.add(bb);
                }
                List<BatalBarcode> filterBarang = filterData(batalBarcode);
                Collections.sort(filterBarang, (o1, o2) -> {
                    int sComp = ((BatalBarcode) o1).getBarangCabang().getKodeKategori().compareTo(((BatalBarcode) o2).getBarangCabang().getKodeKategori());
                    if (sComp != 0) 
                        return sComp;
                    return ((BatalBarcode) o1).getBarangCabang().getKodeJenis().compareTo(((BatalBarcode) o2).getBarangCabang().getKodeJenis());
                });
                JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanBatalBarcode.jrxml"));
                JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(filterBarang);
                Map hash = new HashMap();
                hash.put("cabang", cabangCombo.getSelectionModel().getSelectedItem());
                hash.put("gudang", gudangCombo.getSelectionModel().getSelectedItem());
                hash.put("user", userCombo.getSelectionModel().getSelectedItem());
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
    }
    private Boolean checkColumn(String column){
        if(column!=null){
            if(column.toLowerCase().contains(searchField.getText().toLowerCase()))
                return true;
        }
        return false;
    }
    private List<BatalBarcode> filterData(List<BatalBarcode> listBarang) throws Exception{
        List<BatalBarcode> filterData = new ArrayList<>();
        for (BatalBarcode b : listBarang) {
            if (searchField.getText() == null || searchField.getText().equals(""))
                filterData.add(b);
            else{
                if(checkColumn(b.getBarangCabang().getKodeKategori())||
                    checkColumn(b.getKodeBarcode())||
                    checkColumn(b.getBarangCabang().getKodeBarang())||
                    checkColumn(b.getBarangCabang().getNamaBarang())||
                    checkColumn(b.getBarangCabang().getKodeJenis())||
                    checkColumn(b.getBarangCabang().getKodeIntern())||
                    checkColumn(b.getBarangCabang().getKadar())||
                    checkColumn(b.getBarangCabang().getAsalBarang())||
                    checkColumn(b.getBarangCabang().getInputBy())||
                    checkColumn(tglLengkap.format(tglSql.parse(b.getBarangCabang().getInputDate())))||
                    checkColumn(gr.format(b.getBarangCabang().getBerat()))||
                    checkColumn(gr.format(b.getBarangCabang().getBeratAsli())))
                    filterData.add(b);
            }
        }
        return filterData;
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
