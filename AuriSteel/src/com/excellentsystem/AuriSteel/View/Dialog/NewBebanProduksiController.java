/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.DAO.BebanProduksiDetailDAO;
import com.excellentsystem.AuriSteel.DAO.BebanProduksiHeadDAO;
import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.sistem;
import static com.excellentsystem.AuriSteel.Main.tglLengkap;
import static com.excellentsystem.AuriSteel.Main.tglSql;
import com.excellentsystem.AuriSteel.Model.BebanProduksiDetail;
import com.excellentsystem.AuriSteel.Model.BebanProduksiHead;
import com.excellentsystem.AuriSteel.Model.KategoriKeuangan;
import com.excellentsystem.AuriSteel.Model.ProduksiHead;
import java.sql.Connection;
import java.util.ArrayList;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class NewBebanProduksiController  {

    @FXML private TextField noBebanProduksiField;
    @FXML private TextField tglBebanProduksiField;
    @FXML private TextArea produksiField;
    @FXML public TextField keteranganField;
    @FXML public TextField jumlahRpField;
    @FXML public ComboBox<String> tipeKeuanganCombo;
    
    @FXML private Button addProduksiButton;
    @FXML private Button resetProduksiButton;
    @FXML public Button saveButton;
    @FXML private Button cancelButton;
    @FXML private GridPane gridPane;
    
    public List<BebanProduksiDetail> listDetail = new ArrayList<>();
    private Main mainApp;   
    private Stage stage;
    private Stage owner;
    public void setMainApp(Main mainApp,Stage owner,Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        Function.setNumberField(jumlahRpField);
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }  
    public void setNewBebanProduksi(){
        noBebanProduksiField.setText("");
        tglBebanProduksiField.setText("");
        ObservableList<String> listKeuangan = FXCollections.observableArrayList();
        for(KategoriKeuangan kk : sistem.getListKategoriKeuangan()){
            listKeuangan.add(kk.getKodeKeuangan());
        }
        tipeKeuanganCombo.setItems(listKeuangan);
    }
    public void setDetailBebanProduksi(String noBeban){
        Task<BebanProduksiHead> task = new Task<BebanProduksiHead>() {
            @Override 
            public BebanProduksiHead call()throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    BebanProduksiHead b = BebanProduksiHeadDAO.get(con, noBeban);
                    b.setListBebanProduksiDetail(BebanProduksiDetailDAO.getAllByNoBeban(con, noBeban));
                    return b;
                }
            }
        };
        task.setOnRunning((ex) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
            try {
                mainApp.closeLoading();
                BebanProduksiHead b = task.getValue();
                AnchorPane.setBottomAnchor(gridPane, 0.0);
                addProduksiButton.setVisible(false);
                resetProduksiButton.setVisible(false);
                saveButton.setVisible(false);
                cancelButton.setVisible(false);
                keteranganField.setDisable(true);
                jumlahRpField.setDisable(true);
                tipeKeuanganCombo.setDisable(true);

                noBebanProduksiField.setText(b.getNoBebanProduksi());
                tglBebanProduksiField.setText(tglLengkap.format(tglSql.parse(b.getTglBebanProduksi())));
                String produksi = "";
                for(BebanProduksiDetail d : b.getListBebanProduksiDetail()){
                    produksi = produksi + d.getNoProduksi();
                    if(b.getListBebanProduksiDetail().indexOf(d) < b.getListBebanProduksiDetail().size()-1)
                        produksi = produksi + "\n";
                }
                produksiField.setText(produksi);
                keteranganField.setText(b.getKeterangan());
                jumlahRpField.setText(df.format(b.getTotalBebanProduksi()));
                tipeKeuanganCombo.getSelectionModel().select(b.getTipeKeuangan());
            } catch (Exception ex) {
                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
            }
        });
        task.setOnFailed((ex) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }
    @FXML
    private void addProduksi(){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddProduksi.fxml");
        AddProduksiController x = loader.getController();
        x.setMainApp(mainApp, stage, child);
        x.produksiHeadTable.setRowFactory(table -> {
            final TableRow<ProduksiHead> row = new TableRow<ProduksiHead>(){
                @Override
                public void updateItem(ProduksiHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(null);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem detailProduksi = new MenuItem("Detail Produksi");
                        detailProduksi.setOnAction((ActionEvent e)->{
                            detailProduksi(item, child);
                        });
                        MenuItem detailBebanProduksi = new MenuItem("Detail Beban Produksi");
                        detailBebanProduksi.setOnAction((ActionEvent e)->{
                            detailBebanProduksi(item, child);
                        });
                        rm.getItems().addAll(detailProduksi, detailBebanProduksi);
                        setContextMenu(rm);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&
                        mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null){
                        try{
                            mainApp.closeDialog(stage, child);
                            ProduksiHead p = row.getItem();
                            boolean status = true;
                            for(BebanProduksiDetail d : listDetail){
                                if(d.getNoProduksi().equals(p.getKodeProduksi()))
                                    status = false;
                            }
                            if(status){
                                BebanProduksiDetail d = new BebanProduksiDetail();
                                d.setNoProduksi(p.getKodeProduksi());
                                d.setProduksiHead(p);
                                listDetail.add(d);
                                produksiField.appendText(p.getKodeProduksi()+"\n");
                            }else{
                                mainApp.showMessage(Modality.NONE, "Warning", "Produksi sudah diinput");
                            }
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
    private void resetProduksi(){
        produksiField.setText("");
        listDetail.clear();
    }
    private void detailProduksi(ProduksiHead p, Stage owner){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(owner, child, "View/Dialog/NewProduksiBarang.fxml");
        NewProduksiBarangController controller = loader.getController();
        controller.setMainApp(mainApp, owner, child);
        controller.setDetailProduksi(p.getKodeProduksi());
    }
    private void detailBebanProduksi(ProduksiHead p, Stage owner){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(owner, child, "View/Dialog/DetailBebanProduksi.fxml");
        DetailBebanProduksiController controller = loader.getController();
        controller.setMainApp(mainApp, owner, child);
        controller.setDetailBebanProduksi(p.getKodeProduksi());
    }
    public void close(){
        mainApp.closeDialog(owner, stage);
    }  
}
