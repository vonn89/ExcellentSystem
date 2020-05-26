/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View.Dialog;

import static com.excellentsystem.TokoEmasJagoPusat.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.gr;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSql;
import com.excellentsystem.TokoEmasJagoPusat.Model.AmbilBarangDetail;
import java.text.SimpleDateFormat;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
 * @author excellent
 */
public class PembelianController {

    @FXML private TableView<AmbilBarangDetail> pembelianDetailTable;
    @FXML private TableColumn<AmbilBarangDetail, String> kodeJenisColumn;
    @FXML private TableColumn<AmbilBarangDetail, String> namaBarangColumn;
    @FXML private TableColumn<AmbilBarangDetail, Number> qtyColumn;
    @FXML private TableColumn<AmbilBarangDetail, Number> beratKotorColumn;
    @FXML private TableColumn<AmbilBarangDetail, Number> beratBersihColumn;
    @FXML private TableColumn<AmbilBarangDetail, Number> persentaseEmasColumn;
    @FXML private TableColumn<AmbilBarangDetail, Number> beratPersenColumn;
    @FXML private TableColumn<AmbilBarangDetail, Number> hargaColumn;
    
    @FXML private Label noPembelianLabel;
    @FXML private Label tglPembelianLabel;
    @FXML public TextField kodeSalesField;
    
    @FXML public TextField totalPembelianField;
    
    public ObservableList<AmbilBarangDetail> listPembelianDetail = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeJenisColumn.setCellValueFactory(cellData -> cellData.getValue().kodeJenisProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        qtyColumn.setCellValueFactory(cellData -> cellData.getValue().qtyProperty());
        qtyColumn.setCellFactory(col -> getTableCell(gr));
        beratKotorColumn.setCellValueFactory(cellData -> cellData.getValue().beratKotorProperty());
        beratKotorColumn.setCellFactory(col -> getTableCell(gr));
        beratBersihColumn.setCellValueFactory(cellData -> cellData.getValue().beratBersihProperty());
        beratBersihColumn.setCellFactory(col -> getTableCell(gr));
        persentaseEmasColumn.setCellValueFactory(cellData -> cellData.getValue().persentaseEmasProperty());
        persentaseEmasColumn.setCellFactory(col -> getTableCell(gr));
        beratPersenColumn.setCellValueFactory(cellData -> cellData.getValue().beratPersenProperty());
        beratPersenColumn.setCellFactory(col -> getTableCell(gr));
        hargaColumn.setCellValueFactory(cellData -> cellData.getValue().hargaBeliProperty());
        hargaColumn.setCellFactory(col -> getTableCell(rp));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            pembelianDetailTable.refresh();
        });
        rowMenu.getItems().addAll(refresh);
        pembelianDetailTable.setContextMenu(rowMenu);
        pembelianDetailTable.setRowFactory(table -> {
            TableRow<AmbilBarangDetail> row = new TableRow<AmbilBarangDetail>(){
                @Override
                public void updateItem(AmbilBarangDetail item, boolean empty) {
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
    public void setDetailPembelian(List<AmbilBarangDetail> listAmbil){
        try{
            String noPembelian = "";
            String tglPembelian = "";
            String kodeSales = "";
            for(AmbilBarangDetail d : listAmbil){
                noPembelian = d.getNoPembelian();
                tglPembelian = d.getTglPembelian();
                kodeSales = d.getKodeSales();
            }
            noPembelianLabel.setText(noPembelian);
            tglPembelianLabel.setText(new SimpleDateFormat("EEEEEE, dd MMMMM yyyy HH:mm:ss").format(tglSql.parse(tglPembelian)));
            kodeSalesField.setText(kodeSales);
            listPembelianDetail.addAll(listAmbil);
            hitungTotal();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void hitungTotal(){
        double total = 0;
        for(AmbilBarangDetail d : listPembelianDetail){
            total = total + d.getHargaBeli();
        }
        totalPembelianField.setText(rp.format(total));
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
