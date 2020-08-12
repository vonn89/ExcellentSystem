/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View.Dialog;

import com.excellentsystem.sentralindahperdana.DAO.KategoriTransaksiDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import static com.excellentsystem.sentralindahperdana.Main.timeout;
import com.excellentsystem.sentralindahperdana.Model.BebanPenjualan;
import com.excellentsystem.sentralindahperdana.Model.KategoriTransaksi;
import com.excellentsystem.sentralindahperdana.Model.PenjualanDetail;
import com.excellentsystem.sentralindahperdana.Model.PenjualanHead;
import java.sql.Connection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class NewBebanPenjualanController  {
    @FXML private TextField noPenjualanField;
    @FXML private TextField namaProyekField;
    @FXML private TextField pekerjaanField;
    @FXML public ComboBox<String> kategoriCombo;
    @FXML public TextField keteranganField;
    @FXML public TextField jumlahRpField;
    @FXML public ComboBox<String> tipeKeuanganCombo;
    @FXML public Button saveButton;
    @FXML private Button addPenjualanButton;
    @FXML private Button addPenjualanDetailButton;
    public PenjualanHead penjualanHead;
    public PenjualanDetail penjualanDetail;
    private Main mainApp;   
    private Stage stage;
    private Stage owner;
    public void initialize(){
        Function.setNumberField(jumlahRpField);
    }
    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        ObservableList<String> allKategori = FXCollections.observableArrayList();
        Task<Void> task = new Task<Void>() {
            @Override 
            public Void call() throws Exception{
                Thread.sleep(timeout);
                try(Connection con = Koneksi.getConnection()){
                    List<KategoriTransaksi> listKategoriTransaksi = KategoriTransaksiDAO.getAll(con);
                    for(KategoriTransaksi k : listKategoriTransaksi){
                        if(k.getJenisTransaksi().equals("K"))
                            allKategori.add(k.getKodeKategori());
                    }
                    return null;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
            try{
                mainApp.closeLoading();
                kategoriCombo.setItems(allKategori);
                tipeKeuanganCombo.setItems(Function.getTipeKeuanganByUser());
            }catch(Exception e){
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }   
    @FXML
    private void addPenjualan(){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddPemesanan.fxml");
        AddPemesananController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.penjualanHeadTable.setRowFactory(table -> {
            TableRow<PenjualanHead> row = new TableRow<PenjualanHead>() {};
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&
                        mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null){
                        try{
                            mainApp.closeDialog(stage, child);
                            penjualanHead = row.getItem();
                            noPenjualanField.setText(penjualanHead.getNoPenjualan());
                            namaProyekField.setText(penjualanHead.getNamaProyek());
                        }catch(Exception e){
                            mainApp.showMessage(Modality.NONE, "Error", e.toString());
                        }
                    }
                }
            });
            return row;
        });
    }
    @FXML
    private void addPenjualanDetail(){
        if(penjualanHead==null)
            mainApp.showMessage(Modality.NONE,"Warning", "Penjualan belum dipilih");
        else{
            Stage child = new Stage();
            FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddDetailPenjualan.fxml");
            AddDetailPenjualanController controller = loader.getController();
            controller.setMainApp(mainApp, stage, child);
            controller.searchPenjualanDetail(penjualanHead.getNoPenjualan());
            controller.penjualanDetailTable.setRowFactory(table -> {
                TableRow<PenjualanDetail> row = new TableRow<PenjualanDetail>() {};
                row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&
                            mouseEvent.getClickCount() == 2){
                        if(row.getItem()!=null){
                            mainApp.closeDialog(stage, child);
                            penjualanDetail = row.getItem();
                            pekerjaanField.setText(penjualanDetail.getNamaPekerjaan());
                        }
                    }
                });
                return row;
            });
        }
    }
    public void setBebanPenjualan(BebanPenjualan b){
        addPenjualanButton.setVisible(false);
        addPenjualanDetailButton.setVisible(false);
        kategoriCombo.setDisable(true);
        keteranganField.setDisable(true);
        jumlahRpField.setDisable(true);
        tipeKeuanganCombo.setDisable(true);
        saveButton.setVisible(false);
        
        penjualanHead = b.getPenjualanDetail().getPenjualanHead();
        penjualanDetail = b.getPenjualanDetail();
        noPenjualanField.setText(b.getNoPenjualan());
        namaProyekField.setText(penjualanHead.getNamaProyek());
        pekerjaanField.setText(penjualanDetail.getNamaPekerjaan());
        kategoriCombo.getSelectionModel().select(b.getKategori());
        keteranganField.setText(b.getKeterangan());
        jumlahRpField.setText(df.format(b.getJumlahRp()));
        tipeKeuanganCombo.getSelectionModel().select(b.getTipeKeuangan());
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }   
    
}
