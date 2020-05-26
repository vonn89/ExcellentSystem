/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.DAO.KategoriBahanDAO;
import com.excellentsystem.AuriSteel.Function;
import static com.excellentsystem.AuriSteel.Function.getTableCell;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.sistem;
import com.excellentsystem.AuriSteel.Model.Barang;
import com.excellentsystem.AuriSteel.Model.KategoriBahan;
import java.sql.Connection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author yunaz
 */
public class DetailBarangController  {

    @FXML public TextField kodeBarangField;
    @FXML public TextField namaBarangField;
    @FXML public TextArea spesifikasiField;
    @FXML public TextField beratField;
    @FXML public TextField satuanField;
    @FXML public TextField hargaJualField;
    
    @FXML public Button saveButton;
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        Function.setNumberField(beratField);
        Function.setNumberField(hargaJualField);
        
    }   
    public void setMainApp(Main mainApp, Stage owner, Stage stage){
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((e) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    public void setBarang(Barang b){
        kodeBarangField.setDisable(false);
        hargaJualField.setDisable(false);
        kodeBarangField.setText("");
        namaBarangField.setText("");
        spesifikasiField.setText("");
        beratField.setText("0");
        satuanField.setText("");
        hargaJualField.setText("0");
        if(b!=null){
            kodeBarangField.setDisable(true);
            kodeBarangField.setText(b.getKodeBarang());
            namaBarangField.setText(b.getNamaBarang());
            spesifikasiField.setText(b.getSpesifikasi());
            beratField.setText(df.format(b.getBerat()));
            satuanField.setText(b.getSatuan());
            hargaJualField.setText(df.format(b.getHargaJual()));
            if(!"Manager".equals(sistem.getUser().getLevel())){
                hargaJualField.setDisable(true);
            }
        }
    } 
    public void close(){
        mainApp.closeDialog(owner, stage);
    }
    
}
