/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View.Dialog;

import com.excellentsystem.TokoEmasJagoPusat.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.Function;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.gr;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import com.excellentsystem.TokoEmasJagoPusat.Model.Cabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.StokBarangCabang;
import java.sql.Connection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Excellent
 */
public class PindahRosokCabangController  {

    @FXML public TextField keteranganField;
    @FXML public ComboBox<String> kodeCabangCombo;
    @FXML public ComboBox<String> kodeGudangCombo;
    @FXML public TextField kodeJenisField;
    @FXML public TextField qtyField;
    @FXML public TextField beratField;
    @FXML public TextField persentaseEmasField;
    @FXML public TextField beratPersenField;
    
    @FXML public Button saveButton;
    
    public StokBarangCabang stokBarangCabang;
    private ObservableList<String> listGudang = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        Function.setNumberField(qtyField, rp);
        Function.setNumberField(beratField, gr);
        persentaseEmasField.setOnKeyReleased((event) -> {
            try{
                String string = persentaseEmasField.getText();
                if(string.contains("-"))
                    persentaseEmasField.undo();
                else{
                    if(string.indexOf(".")>0){
                        String string2 = string.substring(string.indexOf(".")+1, string.length());
                        if(string2.contains("."))
                            persentaseEmasField.undo();
                        else if(!string2.equals("") && Double.parseDouble(string2)!=0)
                            persentaseEmasField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                    }else
                        persentaseEmasField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                }
                persentaseEmasField.end();
            }catch(Exception e){
                persentaseEmasField.undo();
            }
            beratPersenField.setText(gr.format(Double.parseDouble(beratField.getText().replaceAll(",", "")) * 
                    Double.parseDouble(persentaseEmasField.getText().replaceAll(",", "")) /100));
        });
        keteranganField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) 
                kodeCabangCombo.requestFocus();
        });
        kodeCabangCombo.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) 
                kodeGudangCombo.requestFocus();
        });
        qtyField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                beratField.requestFocus();
                beratField.selectAll();
            }
        });
        beratField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                persentaseEmasField.requestFocus();
                persentaseEmasField.selectAll();
            }
        });
        persentaseEmasField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                saveButton.requestFocus();
            }
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
        setData();
    }
    private void setData(){
        Task<List<Cabang>> task = new Task<List<Cabang>>() {
            @Override 
            public List<Cabang> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    return CabangDAO.getAll(conPusat);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ec) -> {
            mainApp.closeLoading();
            List<Cabang> listCabang = task.getValue();
            ObservableList<String> allCabang = FXCollections.observableArrayList();
            for(Cabang c : listCabang){
                allCabang.add(c.getKodeCabang());
            }
            kodeCabangCombo.setItems(allCabang);
            setGudang();
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
        stokBarangCabang = s;
        kodeCabangCombo.getSelectionModel().select(s.getKodeCabang());
        kodeGudangCombo.getSelectionModel().select(s.getKodeGudang());
        kodeJenisField.setText(s.getKodeJenis());
        qtyField.setText(rp.format(s.getStokAkhir()));
        beratField.setText(gr.format(s.getBeratAkhir()));
        if(s.getBeratAkhir()!=0)
            persentaseEmasField.setText(gr.format(s.getBeratPersenAkhir()/s.getBeratAkhir()*100));
        else
            persentaseEmasField.setText(gr.format(0));
        beratPersenField.setText(gr.format(s.getBeratPersenAkhir()));
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
        persentaseEmasField.setText(gr.format(0));
        beratPersenField.setText(gr.format(0));
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
