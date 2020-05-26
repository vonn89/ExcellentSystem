/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View.Dialog;

import com.excellentsystem.TokoEmasJagoPusat.DAO.PenjualanCabangDetailDAO;
import static com.excellentsystem.TokoEmasJagoPusat.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.gr;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSql;
import com.excellentsystem.TokoEmasJagoPusat.Model.PenjualanCabangDetail;
import com.excellentsystem.TokoEmasJagoPusat.Model.PenjualanCabangHead;
import java.sql.Connection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Excellent
 */
public class DetailPenjualanCabangController {

    @FXML private TableView<PenjualanCabangDetail> penjualanDetailTable;
    @FXML private TableColumn<PenjualanCabangDetail, String> kodeKategoriColumn;
    @FXML private TableColumn<PenjualanCabangDetail, String> kodeJenisColumn;
    @FXML private TableColumn<PenjualanCabangDetail, Number> qtyColumn;
    @FXML private TableColumn<PenjualanCabangDetail, Number> beratColumn;
    @FXML private TableColumn<PenjualanCabangDetail, Number> hargaPersenColumn;
    @FXML private TableColumn<PenjualanCabangDetail, Number> totalColumn;
    
    @FXML private TextField noPenjualanField;
    @FXML private TextField tglPenjualanField;
    @FXML private TextField kodeCabangField;
    @FXML private TextField kodeUserField;
    @FXML private TextField hargaEmasField;
    
    @FXML private Label totalQtyLabel;
    @FXML private Label totalBeratLabel;
    @FXML private Label totalHargaPersenLabel;
    @FXML private Label totalPenjualanLabel;
    
    private ObservableList<PenjualanCabangDetail> listPenjualanDetail = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        kodeJenisColumn.setCellValueFactory(cellData -> cellData.getValue().kodeJenisProperty());
        qtyColumn.setCellValueFactory(cellData -> cellData.getValue().qtyProperty());
        qtyColumn.setCellFactory(col -> getTableCell(rp));
        beratColumn.setCellValueFactory(cellData -> cellData.getValue().beratProperty());
        beratColumn.setCellFactory(col -> getTableCell(gr));
        hargaPersenColumn.setCellValueFactory(cellData -> cellData.getValue().hargaPersenProperty());
        hargaPersenColumn.setCellFactory(col -> getTableCell(gr));
        totalColumn.setCellValueFactory(cellData -> cellData.getValue().totalHargaPersenProperty());
        totalColumn.setCellFactory(col -> getTableCell(gr));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            penjualanDetailTable.refresh();
        });
        rowMenu.getItems().addAll(refresh);
        penjualanDetailTable.setContextMenu(rowMenu);
        penjualanDetailTable.setRowFactory(table -> {
            TableRow<PenjualanCabangDetail> row = new TableRow<PenjualanCabangDetail>(){
                @Override
                public void updateItem(PenjualanCabangDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(null);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            penjualanDetailTable.refresh();
                        });
                        rowMenu.getItems().addAll(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
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
        penjualanDetailTable.setItems(listPenjualanDetail);
    }
    public void setPenjualanCabang(PenjualanCabangHead p){
        Task<List<PenjualanCabangDetail>> task = new Task<List<PenjualanCabangDetail>>(){
            @Override 
            public List<PenjualanCabangDetail> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    return PenjualanCabangDetailDAO.getAllByNoPenjualan(conPusat, p.getNoPenjualanCabang());
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
            try{
                mainApp.closeLoading();
                List<PenjualanCabangDetail> listDetail = task.getValue();
                listPenjualanDetail.clear();
                listPenjualanDetail.addAll(listDetail);
                noPenjualanField.setText(p.getNoPenjualanCabang());
                tglPenjualanField.setText(tglLengkap.format(tglSql.parse(p.getTglPenjualan())));
                kodeCabangField.setText(p.getKodeCabang());
                kodeUserField.setText(p.getKodeUser());
                hargaEmasField.setText(rp.format(p.getHargaEmas()));
                hitungTotal();
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
    private void hitungTotal(){
        double totalQty = 0;
        double totalBerat = 0;
        double totalHargaPersen = 0;
        double totalPenjualan = 0;
        for(PenjualanCabangDetail d : listPenjualanDetail){
            totalQty = totalQty + d.getQty();
            totalBerat = totalBerat + d.getBerat();
            totalHargaPersen = totalHargaPersen + d.getTotalHargaPersen();
            totalPenjualan = totalPenjualan + d.getTotalHargaRp();
        }
        totalQtyLabel.setText(gr.format(totalQty));
        totalBeratLabel.setText(gr.format(totalBerat));
        totalHargaPersenLabel.setText(gr.format(totalHargaPersen));
        totalPenjualanLabel.setText(rp.format(totalPenjualan));
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
