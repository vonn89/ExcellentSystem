/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View.Dialog;

import com.excellentsystem.TokoEmasJagoPusat.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.JenisDAO;
import com.excellentsystem.TokoEmasJagoPusat.Function;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.gr;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import com.excellentsystem.TokoEmasJagoPusat.Model.Cabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.Jenis;
import com.excellentsystem.TokoEmasJagoPusat.Model.StokBarangCabang;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Excellent
 */
public class RevisiBarangCabangController  {

    @FXML public ComboBox<String> kodeCabangCombo;
    @FXML public ComboBox<String> kodeGudangCombo;
    @FXML public TextField kodeJenisField;
    @FXML public TextField qtyField;
    @FXML public TextField beratField;
    
    @FXML public ComboBox<String> jenisRevisiCombo;
    @FXML public TextField keteranganField;
    @FXML public ComboBox<String> kodeJenisRevisiCombo;
    @FXML public TextField qtyRevisiField;
    @FXML public TextField beratRevisiField;
    
    @FXML private VBox vbox;
    @FXML private HBox kodeJenisHbox;
    @FXML private HBox qtyHbox;
    @FXML private HBox beratHbox;
    
    @FXML public Button saveButton;
    
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public StokBarangCabang stokBarangCabang;
    private List<Cabang> listCabang = new ArrayList<>();
    private ObservableList<String> listKodeJenis = FXCollections.observableArrayList();
    private ObservableList<String> listGudang = FXCollections.observableArrayList();
    public void initialize() {
        Function.setNumberField(qtyRevisiField, rp);
        Function.setNumberField(beratRevisiField, gr);
        kodeJenisRevisiCombo.setItems(listKodeJenis);
        keteranganField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                if(jenisRevisiCombo.getSelectionModel().getSelectedItem().equals("Ubah Jenis")){
                    kodeJenisRevisiCombo.getEditor().requestFocus();
                    kodeJenisRevisiCombo.getEditor().selectAll();
                }else if(jenisRevisiCombo.getSelectionModel().getSelectedItem().equals("Penambahan Stok") ||
                        jenisRevisiCombo.getSelectionModel().getSelectedItem().equals("Pengurangan Stok")){
                    qtyRevisiField.requestFocus();
                    qtyRevisiField.selectAll();
                }else if(jenisRevisiCombo.getSelectionModel().getSelectedItem().equals("Penambahan Berat") ||
                        jenisRevisiCombo.getSelectionModel().getSelectedItem().equals("Pengurangan Berat")){
                    beratRevisiField.requestFocus();
                    beratRevisiField.selectAll();
                }
            }
        });
        kodeJenisRevisiCombo.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) 
                setJenis();
        });
        qtyRevisiField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                if(jenisRevisiCombo.getSelectionModel().getSelectedItem().equals("Ubah Jenis")){
                    beratRevisiField.requestFocus();
                    beratRevisiField.selectAll();
                }else if(jenisRevisiCombo.getSelectionModel().getSelectedItem().equals("Penambahan Stok") ||
                        jenisRevisiCombo.getSelectionModel().getSelectedItem().equals("Pengurangan Stok")){
                    saveButton.requestFocus();
                }
            }
        });
        beratRevisiField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) 
                saveButton.requestFocus();
        });
        kodeGudangCombo.setItems(listGudang);
    }    
    public void setMainApp(Main main, Stage owner, Stage stage){
        this.mainApp = main;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        for(Node n : vbox.getChildren()){
            n.managedProperty().bind(n.visibleProperty());
        }
        setData();
    }
    private void setData(){
        Task<List<Jenis>> task = new Task<List<Jenis>>() {
            @Override 
            public List<Jenis> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    listCabang = CabangDAO.getAll(conPusat);
                    return JenisDAO.getAll(conPusat);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ec) -> {
            mainApp.closeLoading();
            List<Jenis> listJenis = task.getValue();
            for(Jenis j : listJenis){
                listKodeJenis.add(j.getKodeJenis());
            }
            
            ObservableList<String> allCabang = FXCollections.observableArrayList();
            for(Cabang c : listCabang){
                allCabang.add(c.getKodeCabang());
            }
            kodeCabangCombo.setItems(allCabang);
            
            ObservableList<String> allJenis = FXCollections.observableArrayList();
            allJenis.add("Ubah Jenis");
            allJenis.add("Penambahan Stok");
            allJenis.add("Pengurangan Stok");
            allJenis.add("Penambahan Berat");
            allJenis.add("Pengurangan Berat");
            jenisRevisiCombo.setItems(allJenis);
            jenisRevisiCombo.getSelectionModel().selectFirst();
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    @FXML
    private void setGudang(){
        listGudang.clear();
        if(kodeCabangCombo.getSelectionModel().getSelectedItem()!=null){
            listGudang.add(kodeCabangCombo.getSelectionModel().getSelectedItem()+"-SP");
            listGudang.add("BLN-"+kodeCabangCombo.getSelectionModel().getSelectedItem());
        }
        kodeGudangCombo.getSelectionModel().clearSelection();
        reset();
    }
    public void setBarang(StokBarangCabang s){
        kodeCabangCombo.getSelectionModel().select(s.getKodeCabang());
        kodeGudangCombo.getSelectionModel().select(s.getKodeGudang());
        kodeJenisField.setText(s.getKodeJenis());
        qtyField.setText(rp.format(s.getStokAkhir()));
        beratField.setText(gr.format(s.getBeratAkhir()));
        stokBarangCabang = s;
    }
    @FXML
    private void getBarang(){
        if(kodeCabangCombo.getSelectionModel().getSelectedItem()==null)
            mainApp.showMessage(Modality.NONE, "Warning", "Kode cabang belum dipilih");
        else if(kodeGudangCombo.getSelectionModel().getSelectedItem()==null)
            mainApp.showMessage(Modality.NONE, "Warning", "Kode gudang belum dipilih");
        else{
            Stage child = new Stage();
            FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/CekStokBarangCabang.fxml");
            CekStokBarangCabangController controller = loader.getController();
            controller.setMainApp(mainApp, stage, child);
            controller.setData(sistem.getTglSystem(), kodeCabangCombo.getSelectionModel().getSelectedItem(), kodeGudangCombo.getSelectionModel().getSelectedItem());

            final ContextMenu rowMenu = new ContextMenu();
            MenuItem refresh = new MenuItem("Refresh");
            refresh.setOnAction((ActionEvent event) -> {
                getBarang();
            });
            rowMenu.getItems().addAll(refresh);
            controller.stokBarangTable.setContextMenu(rowMenu);
            controller.stokBarangTable.setRowFactory(table -> {
                TableRow<StokBarangCabang> row = new TableRow<StokBarangCabang>() {
                    @Override
                    public void updateItem(StokBarangCabang item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setContextMenu(null);
                        }else{
                            final ContextMenu rowMenu = new ContextMenu();
                            MenuItem pilih = new MenuItem("Pilih Barang");
                            pilih.setOnAction((ActionEvent e) -> {
                                setBarang(item);
                                mainApp.closeDialog(stage, child);
                            });
                            MenuItem refresh = new MenuItem("Refresh");
                            refresh.setOnAction((ActionEvent e) -> {
                                getBarang();
                            });
                            rowMenu.getItems().add(pilih);
                            rowMenu.getItems().add(refresh);
                            setContextMenu(rowMenu);
                        }
                    }
                };
                row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                        if(row.getItem()!=null){
                            setBarang(row.getItem());
                            mainApp.closeDialog(stage, child);
                        }
                    }
                });
                return row;
            });
        }
    }
    @FXML
    public void reset(){
        stokBarangCabang = null;
        kodeJenisField.setText("");
        qtyField.setText(rp.format(0));
        beratField.setText(gr.format(0));
    }
    @FXML
    private void setJenisRevisi(){
        if(jenisRevisiCombo.getSelectionModel().getSelectedItem().equals("Ubah Jenis")){
            kodeJenisHbox.setVisible(true);
            qtyHbox.setVisible(true);
            beratHbox.setVisible(true);
            stage.setHeight(470);
        }else if(jenisRevisiCombo.getSelectionModel().getSelectedItem().equals("Penambahan Stok") ||
                jenisRevisiCombo.getSelectionModel().getSelectedItem().equals("Pengurangan Stok")){
            kodeJenisHbox.setVisible(false);
            qtyHbox.setVisible(true);
            beratHbox.setVisible(false);
            stage.setHeight(405);
        }else if(jenisRevisiCombo.getSelectionModel().getSelectedItem().equals("Penambahan Berat") || 
                jenisRevisiCombo.getSelectionModel().getSelectedItem().equals("Pengurangan Berat")){
            kodeJenisHbox.setVisible(false);
            qtyHbox.setVisible(false);
            beratHbox.setVisible(true);
            stage.setHeight(405);
        }
        keteranganField.requestFocus();
        keteranganField.selectAll();
    }
    @FXML 
    private void setJenis(){
        boolean status = false;
        for(String j : listKodeJenis){
            if(j.equalsIgnoreCase(kodeJenisRevisiCombo.getEditor().getText().toUpperCase())){
                kodeJenisRevisiCombo.getSelectionModel().select(j);
                status = true;
            }
        }
        if(status){
            qtyRevisiField.requestFocus();
            qtyRevisiField.selectAll();
        }else{
            kodeJenisRevisiCombo.getEditor().selectAll();
        }
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
