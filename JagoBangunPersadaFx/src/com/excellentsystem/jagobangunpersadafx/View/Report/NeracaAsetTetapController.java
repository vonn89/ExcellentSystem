/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Report;

import com.excellentsystem.jagobangunpersadafx.DAO.AsetTetapDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import static com.excellentsystem.jagobangunpersadafx.Main.tglLengkap;
import static com.excellentsystem.jagobangunpersadafx.Main.tglSql;
import com.excellentsystem.jagobangunpersadafx.Model.AsetTetap;
import com.excellentsystem.jagobangunpersadafx.Model.Keuangan;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailAsetTetapController;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author yunaz
 */
public class NeracaAsetTetapController  {
    @FXML private TreeTableView<Keuangan> keuanganTable;
    @FXML private TreeTableColumn<Keuangan, String> noKeuanganColumn;
    @FXML private TreeTableColumn<Keuangan, String> tglKeuanganColumn;
    @FXML private TreeTableColumn<Keuangan, String> deskripsiColumn;
    @FXML private TreeTableColumn<Keuangan, Number> jumlahRpColumn;
    @FXML private Label saldoAkhirLabel;
    private ObservableList<Keuangan> allKeuangan = FXCollections.observableArrayList();
    private List<AsetTetap> listAsetTetap = new ArrayList<>();
    private Main mainApp;   
    private Stage owner;
    private Stage stage;
    final TreeItem<Keuangan> root = new TreeItem<>();
    public void initialize() {
        noKeuanganColumn.setCellValueFactory(param -> param.getValue().getValue().noKeuanganProperty());
        deskripsiColumn.setCellValueFactory(param -> param.getValue().getValue().deskripsiProperty());
        tglKeuanganColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getValue().getTglKeuangan())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglKeuanganColumn.setComparator(Function.sortDate(tglLengkap));
        jumlahRpColumn.setCellValueFactory(param -> param.getValue().getValue().jumlahRpProperty());
        jumlahRpColumn.setCellFactory(col -> Function.getTreeTableCell(rp));
        final ContextMenu rm = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            keuanganTable.refresh();
        });
        rm.getItems().addAll(refresh);
        keuanganTable.setContextMenu(rm);
        keuanganTable.setRowFactory((TreeTableView<Keuangan> tableView) -> {
            final TreeTableRow<Keuangan> row = new TreeTableRow<Keuangan>(){
                @Override
                public void updateItem(Keuangan item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem lihat = new MenuItem("Detail Aset Tetap");
                        lihat.setOnAction((ActionEvent e)->{
                            showDetailAsetTetap(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            keuanganTable.refresh();
                        });
                        rm.getItems().addAll(refresh);
                        setContextMenu(rm);
                    }
                }
            };
            return row;
        });
    }    
    public void setMainApp(Main mainApp, Stage owner,Stage stage){
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.screenSize.getHeight()*0.9);
        stage.setWidth(mainApp.screenSize.getWidth()*0.9);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
    }
    public void setKeuangan(List<Keuangan> temp){
        Task<List<AsetTetap>> task = new Task<List<AsetTetap>>() {
            @Override 
            public List<AsetTetap> call() throws Exception{
                try(Connection con = Koneksi.getConnection()){
                    return AsetTetapDAO.getAllByStatus(con, "%");
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allKeuangan.clear();
            allKeuangan.addAll(temp);
            if(keuanganTable.getRoot()!=null)
                keuanganTable.getRoot().getChildren().clear();
            double saldoAkhir = 0;
            listAsetTetap = task.getValue();
            for(AsetTetap at : listAsetTetap){
                Keuangan k = new Keuangan();
                k.setNoKeuangan(at.getNoAset()+" - "+at.getNama());
                TreeItem<Keuangan> parent = new TreeItem<>(k);
                double total = 0;
                for(Keuangan keu : allKeuangan){
                    if(keu.getDeskripsi().matches("(.*)"+at.getNoAset()+"(.*)")){
                        saldoAkhir = saldoAkhir + keu.getJumlahRp();
                        TreeItem<Keuangan> child = new TreeItem<>(keu);
                        parent.getChildren().addAll(child);
                        total = total + keu.getJumlahRp();
                    }
                }
                k.setJumlahRp(total);
                if(total>1)
                    root.getChildren().add(parent);
            }
            saldoAkhirLabel.setText(rp.format(saldoAkhir));
            keuanganTable.setRoot(root);
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    private void showDetailAsetTetap(Keuangan k) {
        AsetTetap aset = null;
        for(AsetTetap at : listAsetTetap){
            if(k.getDeskripsi()!=null){
                if(k.getDeskripsi().matches("(.*)"+at.getNoAset()+"(.*)"))
                    aset = at;
            }else{
                if(k.getNoKeuangan().matches("(.*)"+at.getNoAset()+"(.*)"))
                    aset = at;
            }
        }
        if(aset!=null){
            Stage child = new Stage();
            FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/Child/DetailAsetTetap.fxml");
            DetailAsetTetapController x = loader.getController();
            x.setMainApp(mainApp, stage, child);
            x.setDetail(aset);
        }
    }
    @FXML 
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
