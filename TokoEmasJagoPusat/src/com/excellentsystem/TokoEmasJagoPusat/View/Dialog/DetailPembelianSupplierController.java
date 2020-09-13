/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View.Dialog;

import com.excellentsystem.TokoEmasJagoPusat.DAO.PembelianDetailDAO;
import static com.excellentsystem.TokoEmasJagoPusat.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.gr;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSql;
import com.excellentsystem.TokoEmasJagoPusat.Model.PembelianDetail;
import com.excellentsystem.TokoEmasJagoPusat.Model.PembelianHead;
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
public class DetailPembelianSupplierController {

    
    @FXML private TableView<PembelianDetail> pembelianDetailTable;
    @FXML private TableColumn<PembelianDetail, String> kodeKategoriColumn;
    @FXML private TableColumn<PembelianDetail, String> kodeJenisColumn;
    @FXML private TableColumn<PembelianDetail, Number> beratColumn;
    @FXML private TableColumn<PembelianDetail, Number> hargaPersenColumn;
    @FXML private TableColumn<PembelianDetail, Number> totalColumn;
    
    @FXML private TextField noPembelianField;
    @FXML private TextField tglPembelianField;
    @FXML private TextField supplierField;
    @FXML private TextField kodeUserField;
    @FXML private TextField hargaEmasField;
    
    @FXML private Label totalBeratLabel;
    @FXML private Label totalHargaPersenLabel;
    
    private ObservableList<PembelianDetail> listPembelianDetail = FXCollections.observableArrayList();
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
            pembelianDetailTable.refresh();
        });
        rowMenu.getItems().addAll(refresh);
        pembelianDetailTable.setContextMenu(rowMenu);
        pembelianDetailTable.setRowFactory(table -> {
            TableRow<PembelianDetail> row = new TableRow<PembelianDetail>(){
                @Override
                public void updateItem(PembelianDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(null);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            pembelianDetailTable.refresh();
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
        pembelianDetailTable.setItems(listPembelianDetail);
    }
    public void setPembelian(PembelianHead p){
        Task<List<PembelianDetail>> task = new Task<List<PembelianDetail>>(){
            @Override 
            public List<PembelianDetail> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    return PembelianDetailDAO.getAllByNoPembelian(conPusat, p.getNoPembelian());
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
            try{
                mainApp.closeLoading();
                List<PembelianDetail> listDetail = task.getValue();
                listPembelianDetail.clear();
                listPembelianDetail.addAll(listDetail);
                noPembelianField.setText(p.getNoPembelian());
                tglPembelianField.setText(tglLengkap.format(tglSql.parse(p.getTglPembelian())));
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
        for(PembelianDetail d : listPembelianDetail){
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
