/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View.Dialog;

import com.excellentsystem.sentralindahperdana.DAO.KategoriBarangDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import static com.excellentsystem.sentralindahperdana.Main.timeout;
import com.excellentsystem.sentralindahperdana.Model.Barang;
import com.excellentsystem.sentralindahperdana.Model.KategoriBarang;
import com.excellentsystem.sentralindahperdana.Model.Satuan;
import java.sql.Connection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class DetailBarangController {

    @FXML public TextField kodeBarangField;
    @FXML public ComboBox<String> kodeKategoriCombo;
    @FXML public TextField namaBarangField;
    @FXML public TextField keteranganField;
    @FXML public TextField satuanDasarField;
    @FXML public TextField labaPersenField;

    @FXML public TextField kodeSatuanField;
    @FXML public TextField qtyField;
    @FXML public Button saveButton;
    
    @FXML private TableView<Satuan> satuanTable;
    @FXML private TableColumn<Satuan, String> kodeSatuanColumn;
    @FXML private TableColumn<Satuan, Number> qtyColumn;
    @FXML private TableColumn removeColumn = new TableColumn<>("");
    
    @FXML private VBox vbox;
    private Main mainApp;
    private Stage owner;
    private Stage stage;
    public ObservableList<Satuan> allSatuan = FXCollections.observableArrayList();
    private ObservableList<String> allKategori = FXCollections.observableArrayList();
    private Callback<TableColumn, TableCell> getButtonCell(){
        return (final TableColumn p) -> new TableCell<Satuan, Boolean>(){
            final Button btn = new Button("");
            @Override
            public void updateItem( Boolean item, boolean empty ){
                super.updateItem( item, empty );
                if (empty){
                    setGraphic(null);
                }else{
                    Image imageDecline = new Image(Main.class.getResourceAsStream("Resource/delete.png"),20,20,false,true);
                    btn.setGraphic(new ImageView(imageDecline));
                    btn.setPrefSize(30, 30);
                    btn.setOnAction((ActionEvent e) ->{
                        Satuan s = (Satuan)getTableView().getItems().get( getIndex() );
                        allSatuan.remove(s);
                    });
                    if(saveButton.isVisible())
                        setGraphic( btn );
                    else
                        setGraphic(null);
                }
            }
        };
    }
    public void initialize() {
        kodeSatuanColumn.setCellValueFactory(cellData -> cellData.getValue().kodeSatuanProperty());
        qtyColumn.setCellValueFactory(cellData -> cellData.getValue().qtyProperty());
        qtyColumn.setCellFactory(col -> Function.getTableCell());
        removeColumn.setCellValueFactory( new PropertyValueFactory<>(""));
        removeColumn.setCellFactory(getButtonCell());
        
        labaPersenField.setOnKeyReleased((event) -> {
            try{
                String string = labaPersenField.getText();
                if(string.indexOf(".")>0){
                    String string2 = string.substring(string.indexOf(".")+1, string.length());
                    if(string2.contains("."))
                        labaPersenField.undo();
                    else if(!string2.equals("") && Double.parseDouble(string2)!=0)
                        labaPersenField.setText(Main.df.format(Double.parseDouble(string.replaceAll(",", ""))));
                }else
                    labaPersenField.setText(Main.df.format(Double.parseDouble(string.replaceAll(",", ""))));
                labaPersenField.end();
            }catch(Exception e){
                labaPersenField.undo();
            }
        });
        qtyField.setOnKeyReleased((event) -> {
            try{
                String string = qtyField.getText();
                if(string.indexOf(".")>0){
                    String string2 = string.substring(string.indexOf(".")+1, string.length());
                    if(string2.contains("."))
                        qtyField.undo();
                    else if(!string2.equals("") && Double.parseDouble(string2)!=0)
                        qtyField.setText(Main.df.format(Double.parseDouble(string.replaceAll(",", ""))));
                }else
                    qtyField.setText(Main.df.format(Double.parseDouble(string.replaceAll(",", ""))));
                qtyField.end();
            }catch(Exception e){
                qtyField.undo();
            }
        });
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Satuan");
        addNew.setOnAction((ActionEvent e)->{
            kodeSatuanField.setText("");
            qtyField.setText("0");
            kodeSatuanField.setDisable(false);
            kodeSatuanField.requestFocus();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            satuanTable.refresh();
        });
        rowMenu.getItems().addAll(addNew, refresh);
        satuanTable.setContextMenu(rowMenu);
        satuanTable.setRowFactory(t -> {
            TableRow<Satuan> row = new TableRow<Satuan>() {
                @Override
                public void updateItem(Satuan item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(null);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Satuan");
                        addNew.setOnAction((ActionEvent e)->{
                            kodeSatuanField.setText("");
                            qtyField.setText("0");
                            kodeSatuanField.setDisable(false);
                            kodeSatuanField.requestFocus();
                        });
                        MenuItem edit = new MenuItem("Edit Satuan");
                        edit.setOnAction((ActionEvent e) -> {
                            selectSatuan(item);
                        });
                        MenuItem delete = new MenuItem("Delete Satuan");
                        delete.setOnAction((ActionEvent e) -> {
                            allSatuan.remove(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            satuanTable.refresh();
                        });
                        if(saveButton.isVisible())
                            rowMenu.getItems().addAll(addNew,edit,delete,refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
    }
    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        for(Node n : vbox.getChildren()){
            n.managedProperty().bind(n.visibleProperty());
        }
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        Task<Void> task = new Task<Void>() {
            @Override 
            public Void call() throws Exception{
                Thread.sleep(timeout);
                try(Connection con = Koneksi.getConnection()){
                    List<KategoriBarang> kategori = KategoriBarangDAO.getAll(con);
                    allKategori.clear();
                    for (KategoriBarang temp : kategori) {
                        allKategori.add(temp.getKodeKategori());
                    }
                    return null;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            mainApp.closeLoading();
            kodeKategoriCombo.setItems(allKategori);
            satuanTable.setItems(allSatuan);
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    public void setBarangDetail(Barang b) {
        kodeBarangField.setText("");
        kodeKategoriCombo.getSelectionModel().clearSelection();
        namaBarangField.setText("");
        keteranganField.setText("");
        satuanDasarField.setText("");
        labaPersenField.setText("");
        kodeSatuanField.setText("");
        qtyField.setText("1");
        if (b != null) {
            allSatuan.addAll(b.getAllSatuan());
            kodeBarangField.setText(b.getKodeBarang());
            kodeKategoriCombo.getSelectionModel().select(b.getKategoriBarang());
            namaBarangField.setText(b.getNamaBarang());
            keteranganField.setText(b.getKeterangan());
            satuanDasarField.setText(b.getSatuanDasar());
            labaPersenField.setText(df.format(b.getLaba()));
        }
    }
    private void selectSatuan(Satuan s){
        if(s!=null){
            kodeSatuanField.setText(s.getKodeSatuan());
            qtyField.setText(df.format(s.getQty()));
            kodeSatuanField.setDisable(true);
        }
    }
    @FXML
    private void saveSatuan() {
        try {
            if("".equals(kodeSatuanField.getText())) 
                mainApp.showMessage(Modality.NONE, "Warning", "Kode satuan masih kosong");
            else if(satuanDasarField.getText().equals(kodeSatuanField.getText())) 
                mainApp.showMessage(Modality.NONE, "Warning", "Kode satuan tidak boleh sama dengan satuan dasar");
            else if("".equals(qtyField.getText()) || Double.parseDouble(qtyField.getText().replaceAll(",", "")) <= 0) 
                mainApp.showMessage(Modality.NONE, "Warning", "Qty masih kosong");
            else{
                Satuan satuan = null;
                for(Satuan temp : allSatuan){
                    if(temp.getKodeSatuan().equals(kodeSatuanField.getText()))
                        satuan = temp;
                }
                if(satuan==null){
                    satuan = new Satuan();
                    satuan.setKodeBarang(kodeBarangField.getText());
                    satuan.setKodeSatuan(kodeSatuanField.getText());
                    satuan.setQty(Double.parseDouble(qtyField.getText().replaceAll(",", "")));
                    allSatuan.add(satuan);
                }else{
                    Satuan newSatuan = new Satuan();
                    newSatuan.setKodeBarang(kodeBarangField.getText());
                    newSatuan.setKodeSatuan(kodeSatuanField.getText());
                    newSatuan.setQty(Double.parseDouble(qtyField.getText().replaceAll(",", "")));
                    allSatuan.remove(satuan);
                    allSatuan.add(newSatuan);
                }
                satuanTable.refresh();
                kodeSatuanField.setText("");
                qtyField.setText("1");
                kodeSatuanField.setDisable(false);
            }
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    @FXML
    private void close() {
        mainApp.closeDialog(owner, stage);
    }

}
