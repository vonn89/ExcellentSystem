/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View.Dialog;

import com.excellentsystem.TokoEmasJagoPusat.DAO.SPDetailDAO;
import static com.excellentsystem.TokoEmasJagoPusat.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.gr;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSql;
import com.excellentsystem.TokoEmasJagoPusat.Model.SPDetail;
import com.excellentsystem.TokoEmasJagoPusat.Model.SPHead;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Excellent
 */
public class DetailSPBarangCabangController  {

    @FXML private TableView<SPDetail> spDetailTable;
    @FXML private TableColumn<SPDetail, String> kodeJenisColumn;
    @FXML private TableColumn<SPDetail, Number> qtyColumn;
    @FXML private TableColumn<SPDetail, Number> beratColumn;
    @FXML private TableColumn<SPDetail, Number> beratPenyusutanColumn;
    @FXML private TableColumn<SPDetail, Number> beratJadiColumn;
    
    @FXML private VBox vBox;
    @FXML private TextField noSpField;
    @FXML private TextField tglSpField;
    @FXML private TextField kodeCabangField;
    @FXML private TextField jenisSpField;
    @FXML private HBox tglSelesaiHbox;
    @FXML private TextField tglSelesaiField;
    @FXML private HBox userSelesaiHbox;
    @FXML private TextField userSelesaiField;
    
    @FXML private HBox hBox;
    @FXML private Label totalQtyLabel;
    @FXML private Label totalBeratLabel;
    @FXML private Label beratPenyusutanLabel;
    @FXML private Label totalBeratPenyusutanLabel;
    @FXML private Label beratJadiLabel;
    @FXML private Label totalBeratJadiLabel;
    
    public ObservableList<SPDetail> listSPDetail = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeJenisColumn.setCellValueFactory(cellData -> cellData.getValue().kodeJenisProperty());
        qtyColumn.setCellValueFactory(cellData -> cellData.getValue().qtyProperty());
        qtyColumn.setCellFactory(col -> getTableCell(rp));
        beratColumn.setCellValueFactory(cellData -> cellData.getValue().beratProperty());
        beratColumn.setCellFactory(col -> getTableCell(gr));
        beratPenyusutanColumn.setCellValueFactory(cellData -> cellData.getValue().beratPenyusutanProperty());
        beratPenyusutanColumn.setCellFactory(col -> getTableCell(gr));
        beratJadiColumn.setCellValueFactory(cellData -> {
            return new SimpleDoubleProperty(cellData.getValue().getBerat()-cellData.getValue().getBeratPenyusutan());
        });
        beratJadiColumn.setCellFactory(col -> getTableCell(gr));
        
        final ContextMenu menu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            spDetailTable.refresh();
        });
        menu.getItems().addAll(refresh);
        spDetailTable.setContextMenu(menu);
        spDetailTable.setRowFactory(table -> {
            TableRow<SPDetail> row = new TableRow<SPDetail>(){
                @Override
                public void updateItem(SPDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(menu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            spDetailTable.refresh();
                        });
                        rowMenu.getItems().addAll(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        spDetailTable.setItems(listSPDetail);
    }    
    public void setMainApp(Main main, Stage owner, Stage stage){
        this.mainApp = main;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.MainStage.getHeight()*0.9);
        stage.setWidth(mainApp.MainStage.getWidth()*0.9);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
        for(Node n : vBox.getChildren()){
            n.managedProperty().bind(n.visibleProperty());
        }
        for(Node n : hBox.getChildren()){
            n.managedProperty().bind(n.visibleProperty());
        }
    }
    public void setSPBarangCabang(SPHead s){
        Task<List<SPDetail>> task = new Task<List<SPDetail>>() {
            @Override 
            public List<SPDetail> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    List<SPDetail> allSpDetail = SPDetailDAO.getAllByNoSP(conPusat, s.getNoSP());
                    List<String> listJenis = new ArrayList<>();
                    for(SPDetail d : allSpDetail){
                        if(!listJenis.contains(d.getKodeJenis()))
                            listJenis.add(d.getKodeJenis());
                    }
                    List<SPDetail> temp = new ArrayList<>();
                    for(String jenis : listJenis){
                        SPDetail detail = new SPDetail();
                        detail.setKodeJenis(jenis);
                        
                        int totalQty = 0;
                        double totalBerat = 0;
                        double totalBeratPenyusutan = 0;
                        for(SPDetail d : allSpDetail){
                            if(d.getKodeJenis().equals(jenis)){
                                totalQty = totalQty + d.getQty();
                                totalBerat = totalBerat + d.getBerat();
                                totalBeratPenyusutan = totalBeratPenyusutan + d.getBeratPenyusutan();
                            }
                        }
                        detail.setQty(totalQty);
                        detail.setBerat(totalBerat);
                        detail.setBeratPenyusutan(totalBeratPenyusutan);
                        temp.add(detail);
                    }
                    return temp;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
            try{
                mainApp.closeLoading();
                listSPDetail.clear();
                listSPDetail.addAll(task.getValue());
                
                noSpField.setText(s.getNoSP());
                tglSpField.setText(tglLengkap.format(tglSql.parse(s.getTglSP())));
                kodeCabangField.setText(s.getKodeCabang());
                jenisSpField.setText(s.getJenisSP());
                
                if(s.getStatusSelesai().equals("false")){
                    tglSelesaiHbox.setVisible(false);
                    userSelesaiHbox.setVisible(false);
                    beratPenyusutanLabel.setVisible(false);
                    totalBeratPenyusutanLabel.setVisible(false);
                    beratJadiLabel.setVisible(false);
                    totalBeratJadiLabel.setVisible(false);
                    beratPenyusutanColumn.setVisible(false);
                    beratJadiColumn.setVisible(false);
                    
                    tglSelesaiField.setText("");
                    userSelesaiField.setText("");
                }else{
                    tglSelesaiHbox.setVisible(true);
                    userSelesaiHbox.setVisible(true);
                    beratPenyusutanLabel.setVisible(true);
                    totalBeratPenyusutanLabel.setVisible(true);
                    beratJadiLabel.setVisible(true);
                    totalBeratJadiLabel.setVisible(true);
                    beratPenyusutanColumn.setVisible(true);
                    beratJadiColumn.setVisible(true);
                    
                    tglSelesaiField.setText(tglLengkap.format(tglSql.parse(s.getTglSelesai())));
                    userSelesaiField.setText(s.getUserSelesai());
                }
                hitungTotal();
            }catch(Exception e){
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
            
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            task.getException().printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    private void hitungTotal(){
        double qty = 0;
        double berat = 0;
        double beratPenyusutan = 0;
        double beratJadi = 0;
        for(SPDetail d : listSPDetail){
            qty = qty + d.getQty();
            berat = berat + d.getBerat();
            beratPenyusutan = beratPenyusutan + d.getBeratPenyusutan();
            beratJadi = beratJadi + (d.getBerat()-d.getBeratPenyusutan());
        }
        totalQtyLabel.setText(rp.format(qty));
        totalBeratLabel.setText(gr.format(berat));
        totalBeratPenyusutanLabel.setText(gr.format(beratPenyusutan));
        totalBeratJadiLabel.setText(gr.format(beratJadi));
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
