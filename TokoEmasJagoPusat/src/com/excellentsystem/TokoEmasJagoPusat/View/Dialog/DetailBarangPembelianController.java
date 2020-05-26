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
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglNormal;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoPusat.Model.AmbilBarangDetail;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class DetailBarangPembelianController  {

    @FXML private TableView<AmbilBarangDetail> pembelianDetailTable;
    @FXML private TableColumn<AmbilBarangDetail, String> noPembelianColumn;
    @FXML private TableColumn<AmbilBarangDetail, Number> noUrutColumn;
    @FXML private TableColumn<AmbilBarangDetail, String> kodeSalesColumn;
    @FXML private TableColumn<AmbilBarangDetail, String> kodeKategoriColumn;
    @FXML private TableColumn<AmbilBarangDetail, String> kodeJenisColumn;
    @FXML private TableColumn<AmbilBarangDetail, String> namaBarangColumn;
    @FXML private TableColumn<AmbilBarangDetail, Number> qtyColumn;
    @FXML private TableColumn<AmbilBarangDetail, Number> beratKotorColumn;
    @FXML private TableColumn<AmbilBarangDetail, Number> beratBersihColumn;
    @FXML private TableColumn<AmbilBarangDetail, Number> beratPersenColumn;
    @FXML private TableColumn<AmbilBarangDetail, Number> hargaBeliColumn;
    
    @FXML private Label tanggalLabel;
    @FXML private Label totalQtyLabel;
    @FXML private Label totalBeratKotorLabel;
    @FXML private Label totalBeratBersihLabel;
    @FXML private Label totalBeratPersenLabel;
    @FXML private Label totalPembelianLabel;
    
    private ObservableList<AmbilBarangDetail> listPembelianDetail = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        noPembelianColumn.setCellValueFactory(cellData -> cellData.getValue().noPembelianProperty());
        noUrutColumn.setCellValueFactory(cellData -> cellData.getValue().noUrutProperty());
        kodeSalesColumn.setCellValueFactory(cellData -> cellData.getValue().kodeSalesProperty());
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        kodeJenisColumn.setCellValueFactory(cellData -> cellData.getValue().kodeJenisProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        qtyColumn.setCellValueFactory(cellData -> cellData.getValue().qtyProperty());
        qtyColumn.setCellFactory(col -> getTableCell(rp));
        beratKotorColumn.setCellValueFactory(cellData -> cellData.getValue().beratKotorProperty());
        beratKotorColumn.setCellFactory(col -> getTableCell(gr));
        beratBersihColumn.setCellValueFactory(cellData -> cellData.getValue().beratBersihProperty());
        beratBersihColumn.setCellFactory(col -> getTableCell(gr));
        beratPersenColumn.setCellValueFactory(cellData -> cellData.getValue().beratPersenProperty());
        beratPersenColumn.setCellFactory(col -> getTableCell(gr));
        hargaBeliColumn.setCellValueFactory(cellData -> cellData.getValue().hargaBeliProperty());
        hargaBeliColumn.setCellFactory(col -> getTableCell(rp));
        
        pembelianDetailTable.setItems(listPembelianDetail);
        final ContextMenu menu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            pembelianDetailTable.refresh();
        });
        menu.getItems().addAll(refresh);
        pembelianDetailTable.setContextMenu(menu);
        pembelianDetailTable.setRowFactory(table -> {
            TableRow<AmbilBarangDetail> row = new TableRow<AmbilBarangDetail>(){
                @Override
                public void updateItem(AmbilBarangDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(menu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem detail = new MenuItem("Detail Pembelian");
                        detail.setOnAction((ActionEvent event) -> {
                            detailPembelian(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            pembelianDetailTable.refresh();
                        });
                        rowMenu.getItems().addAll(detail, refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null)
                        detailPembelian(row.getItem());
                }
            });
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
    }
    public void setAmbilBarang(List<AmbilBarangDetail> detailBarang, String tanggal){
        try{
            tanggalLabel.setText(tglNormal.format(tglSystem.parse(tanggal)));
            listPembelianDetail.clear();
            listPembelianDetail.addAll(detailBarang);
            hitungTotal();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void detailPembelian(AmbilBarangDetail p){
        List<AmbilBarangDetail> temp = new ArrayList<>();
        for(AmbilBarangDetail d : listPembelianDetail){
            if(d.getNoPembelian().equals(p.getNoPembelian()))
                temp.add(d);
        }
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/Pembelian.fxml");
        PembelianController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setDetailPembelian(temp);
    }
    private void hitungTotal(){
        double qty = 0;
        double beratKotor = 0;
        double beratBersih = 0;
        double beratPersen = 0;
        double harga = 0;
        for(AmbilBarangDetail d : listPembelianDetail){
            qty = qty + d.getQty();
            beratKotor = beratKotor + d.getBeratKotor();
            beratBersih = beratBersih + d.getBeratBersih();
            beratPersen = beratPersen + d.getBeratPersen();
            harga = harga + d.getHargaBeli();
        }
        totalQtyLabel.setText(rp.format(qty));
        totalBeratKotorLabel.setText(gr.format(beratKotor));
        totalBeratBersihLabel.setText(gr.format(beratBersih));
        totalBeratPersenLabel.setText(gr.format(beratPersen));
        totalPembelianLabel.setText(gr.format(harga));
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
