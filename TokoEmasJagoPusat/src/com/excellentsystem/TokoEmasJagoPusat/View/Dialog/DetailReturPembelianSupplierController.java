/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View.Dialog;

import com.excellentsystem.TokoEmasJagoPusat.DAO.ReturPembelianDetailDAO;
import static com.excellentsystem.TokoEmasJagoPusat.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.gr;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSql;
import com.excellentsystem.TokoEmasJagoPusat.Model.ReturPembelianDetail;
import com.excellentsystem.TokoEmasJagoPusat.Model.ReturPembelianHead;
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
public class DetailReturPembelianSupplierController {

    
    @FXML private TableView<ReturPembelianDetail> returPembelianDetailTable;
    @FXML private TableColumn<ReturPembelianDetail, String> kodeKategoriColumn;
    @FXML private TableColumn<ReturPembelianDetail, String> kodeJenisColumn;
    @FXML private TableColumn<ReturPembelianDetail, Number> beratColumn;
    @FXML private TableColumn<ReturPembelianDetail, Number> hargaPersenColumn;
    @FXML private TableColumn<ReturPembelianDetail, Number> totalColumn;
    
    @FXML private TextField noReturField;
    @FXML private TextField tglReturField;
    @FXML private TextField supplierField;
    @FXML private TextField kodeUserField;
    @FXML private TextField hargaEmasField;
    
    @FXML private Label totalBeratLabel;
    @FXML private Label totalHargaPersenLabel;
    
    private ObservableList<ReturPembelianDetail> listReturPembelianDetail = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        kodeJenisColumn.setCellValueFactory(cellData -> cellData.getValue().kodeJenisProperty());
        beratColumn.setCellValueFactory(cellData -> cellData.getValue().beratProperty());
        beratColumn.setCellFactory(col -> getTableCell(gr));
        hargaPersenColumn.setCellValueFactory(cellData -> cellData.getValue().hargaPersenProperty());
        hargaPersenColumn.setCellFactory(col -> getTableCell(gr));
        totalColumn.setCellValueFactory(cellData -> cellData.getValue().totalHargaProperty());
        totalColumn.setCellFactory(col -> getTableCell(gr));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            returPembelianDetailTable.refresh();
        });
        rowMenu.getItems().addAll(refresh);
        returPembelianDetailTable.setContextMenu(rowMenu);
        returPembelianDetailTable.setRowFactory(table -> {
            TableRow<ReturPembelianDetail> row = new TableRow<ReturPembelianDetail>(){
                @Override
                public void updateItem(ReturPembelianDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(null);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            returPembelianDetailTable.refresh();
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
        returPembelianDetailTable.setItems(listReturPembelianDetail);
    }
    public void setPembelian(ReturPembelianHead p){
        Task<List<ReturPembelianDetail>> task = new Task<List<ReturPembelianDetail>>(){
            @Override 
            public List<ReturPembelianDetail> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    return ReturPembelianDetailDAO.getAllByNoRetur(conPusat, p.getNoRetur());
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
            try{
                mainApp.closeLoading();
                List<ReturPembelianDetail> listDetail = task.getValue();
                listReturPembelianDetail.clear();
                listReturPembelianDetail.addAll(listDetail);
                noReturField.setText(p.getNoRetur());
                tglReturField.setText(tglLengkap.format(tglSql.parse(p.getTglRetur())));
                supplierField.setText(p.getSupplier());
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
        double totalBerat = 0;
        double totalHargaPersen = 0;
        for(ReturPembelianDetail d : listReturPembelianDetail){
            totalBerat = totalBerat + d.getBerat();
            totalHargaPersen = totalHargaPersen + d.getTotalHarga();
        }
        totalBeratLabel.setText(gr.format(totalBerat));
        totalHargaPersenLabel.setText(gr.format(totalHargaPersen));
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    
    
}
