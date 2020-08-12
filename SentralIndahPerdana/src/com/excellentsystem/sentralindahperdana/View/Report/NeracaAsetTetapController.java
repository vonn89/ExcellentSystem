/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View.Report;

import com.excellentsystem.sentralindahperdana.DAO.AsetTetapDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import static com.excellentsystem.sentralindahperdana.Main.tglLengkap;
import static com.excellentsystem.sentralindahperdana.Main.tglSql;
import com.excellentsystem.sentralindahperdana.Model.AsetTetap;
import com.excellentsystem.sentralindahperdana.Model.Keuangan;
import java.sql.Connection;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
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
    @FXML private TreeTableColumn<Keuangan, String> tipeKeuanganColumn;
    @FXML private TreeTableColumn<Keuangan, String> kategoriColumn;
    @FXML private TreeTableColumn<Keuangan, String> deskripsiColumn;
    @FXML private TreeTableColumn<Keuangan, Number> jumlahRpColumn;
    @FXML private TreeTableColumn<Keuangan, String> kodeUserColumn;
    @FXML private Label saldoAkhirLabel;
    private ObservableList<Keuangan> allKeuangan = FXCollections.observableArrayList();
    private Main mainApp;   
    private Stage owner;
    private Stage stage;
    final TreeItem<Keuangan> root = new TreeItem<>();
    public void initialize() {
        noKeuanganColumn.setCellValueFactory(param -> param.getValue().getValue().noKeuanganProperty());
        tipeKeuanganColumn.setCellValueFactory(param -> param.getValue().getValue().tipeKeuanganProperty());
        kategoriColumn.setCellValueFactory(param -> param.getValue().getValue().kategoriProperty());
        deskripsiColumn.setCellValueFactory(param -> param.getValue().getValue().keteranganProperty());
        kodeUserColumn.setCellValueFactory(param -> param.getValue().getValue().kodeUserProperty());
        tglKeuanganColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getValue().getTglKeuangan())));
            } catch (Exception ex) {
                return null;
            }
        });
        jumlahRpColumn.setCellValueFactory(param -> param.getValue().getValue().jumlahRpProperty());
        jumlahRpColumn.setCellFactory(col -> Function.getTreeTableCell());
    }    
    public void setMainApp(Main mainApp, Stage owner,Stage stage){
        try{
            this.mainApp = mainApp;
            this.owner = owner;
            this.stage = stage;
            stage.setOnCloseRequest((event) -> {
                mainApp.closeDialog(owner, stage);
            });
            stage.setHeight(mainApp.screenSize.getHeight()-80);
            stage.setWidth(mainApp.screenSize.getWidth()-80);
            stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
            stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    public void setKeuangan(List<Keuangan> temp){
        try(Connection con = Koneksi.getConnection()){
            if(keuanganTable.getRoot()!=null)
                keuanganTable.getRoot().getChildren().clear();
            allKeuangan.clear();
            allKeuangan.addAll(temp);
            List<AsetTetap> listAsetTetap = AsetTetapDAO.getAllByStatus(con, "%");
            double saldoAkhir = 0;
            for(AsetTetap at : listAsetTetap){
                Keuangan k = new Keuangan();
                k.setNoKeuangan(at.getNoAset()+" - "+at.getNama());
                TreeItem<Keuangan> parent = new TreeItem<>(k);
                double total = 0;
                for(Keuangan keu : allKeuangan){
                    if(keu.getKeterangan().matches("(.*)"+at.getNoAset()+"(.*)")){
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
            saldoAkhirLabel.setText(df.format(saldoAkhir));
            keuanganTable.setRoot(root);
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    @FXML 
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    
}
