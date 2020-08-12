/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View.Dialog;

import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import com.excellentsystem.sentralindahperdana.Model.RencanaAnggaranBebanMaterial;
import com.excellentsystem.sentralindahperdana.Model.Satuan;
import java.util.List;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class RencanaAnggaranBelanjaController  {

    @FXML private TableView<RencanaAnggaranBebanMaterial> materialTable;
    @FXML private TableColumn<RencanaAnggaranBebanMaterial, String> kodeBarangColumn;
    @FXML private TableColumn<RencanaAnggaranBebanMaterial, String> namaBarangColumn;
    @FXML private TableColumn<RencanaAnggaranBebanMaterial, String> satuanColumn;
    @FXML private TableColumn<RencanaAnggaranBebanMaterial, Number> qtyColumn;
    @FXML private TableColumn<RencanaAnggaranBebanMaterial, Number> estimasiHargaBeliColumn;
    @FXML private TableColumn<RencanaAnggaranBebanMaterial, Number> hargaJualColumn;
    @FXML private TableColumn removeColumn = new TableColumn<>("");
    @FXML private Label totalPembelianLabel;
    @FXML private Label totalPenjualanLabel;
    @FXML public Button closeButton;
    @FXML public Button addItemButton;
    private Main mainApp;  
    private Stage stage;
    private Stage owner;
    public ObservableList<RencanaAnggaranBebanMaterial> allMaterial = FXCollections.observableArrayList();
    private Callback<TableColumn, TableCell> getButtonCell(){
        return (final TableColumn p) -> new TableCell<RencanaAnggaranBebanMaterial, Boolean>(){
            final Button btn = new Button("");
            @Override
            public void updateItem( Boolean item, boolean empty ){
                super.updateItem( item, empty );
                if ( empty ){
                    setGraphic( null );
                }else{
                    Image imageDecline = new Image(Main.class.getResourceAsStream("Resource/delete.png"),20,20,false,true);
                    btn.setGraphic(new ImageView(imageDecline));
                    btn.setPrefSize(30, 30);
                    btn.setOnAction((ActionEvent e) ->{
                        RencanaAnggaranBebanMaterial b = (RencanaAnggaranBebanMaterial)getTableView().getItems().get( getIndex() );
                        deleteMaterial(b);
                    });
                    if(addItemButton.isVisible())
                        setGraphic( btn );
                }
            }
        };
    }
    public void initialize() {
        kodeBarangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarangProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        satuanColumn.setCellValueFactory(cellData -> {
            String satuan = cellData.getValue().getBarang().getSatuanDasar();
            for (Satuan temp : cellData.getValue().getBarang().getAllSatuan()) {
                if (temp.getKodeSatuan().equals(cellData.getValue().getSatuan())) 
                    satuan = cellData.getValue().getSatuan();
            }
            return new SimpleStringProperty(satuan);
        });
        estimasiHargaBeliColumn.setCellValueFactory(cellData -> {
            double harga = cellData.getValue().getHargaBeli();
            for (Satuan temp : cellData.getValue().getBarang().getAllSatuan()) {
                if (temp.getKodeSatuan().equals(cellData.getValue().getSatuan())) 
                    harga = harga * temp.getQty();
            }
            return new SimpleDoubleProperty(harga);
        });
        estimasiHargaBeliColumn.setCellFactory((c) -> Function.getTableCell());
        hargaJualColumn.setCellValueFactory(cellData -> {
            double harga = cellData.getValue().getHargaJual();
            for (Satuan temp : cellData.getValue().getBarang().getAllSatuan()) {
                if (temp.getKodeSatuan().equals(cellData.getValue().getSatuan())) 
                    harga = harga * temp.getQty();
            }
            return new SimpleDoubleProperty(harga);
        });
        hargaJualColumn.setCellFactory((c) -> Function.getTableCell());
        qtyColumn.setCellValueFactory(cellData -> {
            double q = cellData.getValue().getQty();
            for (Satuan temp : cellData.getValue().getBarang().getAllSatuan()) {
                if (temp.getKodeSatuan().equals(cellData.getValue().getSatuan())) 
                    q = q / temp.getQty();
            }
            return new SimpleDoubleProperty(q);
        });
        qtyColumn.setCellFactory((c) -> Function.getTableCell());
        removeColumn.setCellValueFactory( new PropertyValueFactory<>( "" ) );
        removeColumn.setCellFactory(getButtonCell());
        materialTable.setItems(allMaterial);
    }    
    public void setMainApp(Main mainApp,Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((e) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    public void setAnggaranBelanja(List<RencanaAnggaranBebanMaterial> b){
        allMaterial.clear();
        allMaterial.addAll(b);
        hitungTotal();
    }
    @FXML
    private void addMaterial(){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddBarang.fxml");
        AddBarangController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.addButton.setOnAction((ActionEvent event) -> {
            if(controller.barang==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Barang belum dipilih atau kode barang masih kosong");
            else if(controller.qtyField.getText().equals("0")||controller.qtyField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Qty tidak boleh kosong");
            else if(controller.satuanCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Satuan belum dipilih");
            else if(controller.totalField.getText().equals("0")||controller.totalField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Total tidak boleh kosong");
            else{
                double qty = Double.parseDouble(controller.qtyField.getText().replaceAll(",", ""));
                double harga = Double.parseDouble(controller.hargaField.getText().replaceAll(",", ""));
                for(Satuan s : controller.barang.getAllSatuan()){
                    if(s.getKodeSatuan().equals(controller.satuanCombo.getSelectionModel().getSelectedItem())){
                        qty = qty * s.getQty();
                        harga = harga / s.getQty();
                    }
                }
                RencanaAnggaranBebanMaterial d = null;
                for(RencanaAnggaranBebanMaterial temp : allMaterial){
                    if(temp.getKodeBarang().equals(controller.barang.getKodeBarang()))
                        d = temp;
                }
                if(d==null){
                    RencanaAnggaranBebanMaterial detail = new RencanaAnggaranBebanMaterial();
                    detail.setKodeBarang(controller.barang.getKodeBarang());
                    detail.setNamaBarang(controller.barang.getNamaBarang());
                    detail.setSatuan(controller.satuanCombo.getSelectionModel().getSelectedItem());
                    detail.setQty(qty);
                    detail.setHargaBeli(harga);
                    detail.setHargaJual(harga*(100+controller.barang.getLaba())/100);
                    detail.setBarang(controller.barang);
                    allMaterial.add(detail);
                    hitungTotal();
                    mainApp.closeDialog(stage,child);
                }else{
                    mainApp.showMessage(Modality.NONE, "Warning", "Barang sudah pernah diinput");
                }
            }
        });
    }
    private void deleteMaterial(RencanaAnggaranBebanMaterial b){
        allMaterial.remove(b);
        materialTable.refresh();
        hitungTotal();
    }
    private void hitungTotal(){
        double totalPembelian = 0;
        double totalPenjualan = 0;
        for(RencanaAnggaranBebanMaterial d : allMaterial){
            totalPembelian = totalPembelian + d.getHargaBeli()*d.getQty();
            totalPenjualan = totalPenjualan + d.getHargaJual()*d.getQty();
        }
        totalPembelianLabel.setText(df.format(totalPembelian));
        totalPenjualanLabel.setText(df.format(totalPenjualan));
    }
    
}
