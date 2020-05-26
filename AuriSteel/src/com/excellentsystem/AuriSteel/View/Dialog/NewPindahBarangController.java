/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.DAO.BarangDAO;
import com.excellentsystem.AuriSteel.DAO.PindahBarangDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PindahBarangHeadDAO;
import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.sistem;
import static com.excellentsystem.AuriSteel.Main.tglLengkap;
import static com.excellentsystem.AuriSteel.Main.tglSql;
import com.excellentsystem.AuriSteel.Model.Gudang;
import com.excellentsystem.AuriSteel.Model.PindahBarangDetail;
import com.excellentsystem.AuriSteel.Model.PindahBarangHead;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
public class NewPindahBarangController  {

    @FXML private TableView<PindahBarangDetail> pengirimanDetailTable;
    @FXML private TableColumn<PindahBarangDetail, String> kodeBarangColumn;
    @FXML private TableColumn<PindahBarangDetail, String> namaBarangColumn;
    @FXML private TableColumn<PindahBarangDetail, String> keteranganColumn;
    @FXML private TableColumn<PindahBarangDetail, String> satuanColumn;
    @FXML private TableColumn<PindahBarangDetail, Number> qtyColumn;
    
    @FXML public ComboBox<String> gudangAsalCombo;
    @FXML public ComboBox<String> gudangTujuanCombo;
    @FXML public TextField namaSupirField;
//    @FXML public TextArea catatanField;
    
    @FXML private Label noPengirimanLabel;
    @FXML private Label tglPengirimanLabel;
    @FXML private Label totalQtyField;
    @FXML private Label totalTonaseField;
    
    @FXML private Button cancelButton;
    @FXML public Button saveButton;
    
    public ObservableList<PindahBarangDetail> allPindahBarangDetail = FXCollections.observableArrayList();
    private Main mainApp;   
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeBarangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarangProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        satuanColumn.setCellValueFactory(cellData -> cellData.getValue().satuanProperty());
        qtyColumn.setCellValueFactory(cellData -> cellData.getValue().qtyProperty());
        qtyColumn.setCellFactory(col -> Function.getTableCell());
        pengirimanDetailTable.setItems(allPindahBarangDetail);
        
        ContextMenu cm = new ContextMenu();
        MenuItem addBarang = new MenuItem("Add Barang");
        addBarang.setOnAction((ActionEvent e)->{
            addBarang();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            pengirimanDetailTable.refresh();
        });
        cm.getItems().addAll(addBarang, refresh);
        pengirimanDetailTable.setContextMenu(cm);
        pengirimanDetailTable.setRowFactory((TableView<PindahBarangDetail> tv) -> {
            final TableRow<PindahBarangDetail> row = new TableRow<PindahBarangDetail>(){
                @Override
                public void updateItem(PindahBarangDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(cm);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem addBarang = new MenuItem("Add Barang");
                        addBarang.setOnAction((ActionEvent e)->{
                            addBarang();
                        });
                        MenuItem delete = new MenuItem("Delete Barang");
                        delete.setOnAction((ActionEvent e)->{
                            allPindahBarangDetail.remove(item);
                            hitungTotal();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            pengirimanDetailTable.refresh();
                        });
                        if(saveButton.isVisible())
                            rm.getItems().addAll(addBarang, delete);
                        rm.getItems().addAll(refresh);
                        setContextMenu(rm);
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
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.screenSize.getHeight()*0.9);
        stage.setWidth(mainApp.screenSize.getWidth()*0.9);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
        ObservableList<String> listGudang = FXCollections.observableArrayList();
        for(Gudang g : sistem.getListGudang()){
            listGudang.add(g.getKodeGudang());
        }
        gudangAsalCombo.setItems(listGudang);
        gudangTujuanCombo.setItems(listGudang);
    }
    private void hitungTotal(){
        double total = 0;
        double totalTonase = 0;
        for(PindahBarangDetail d : allPindahBarangDetail){
            total = total + 1;
            totalTonase = totalTonase + (d.getQty()*d.getBarang().getBerat());
        }
        totalQtyField.setText(df.format(total));
        totalTonaseField.setText(df.format(totalTonase));
    }
    public void setNewPindah(){
        noPengirimanLabel.setText("");
        tglPengirimanLabel.setText("");
    }
    public void setDetailPindah(String noPindah){
        Task<PindahBarangHead> task = new Task<PindahBarangHead>() {
            @Override 
            public PindahBarangHead call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    PindahBarangHead pindah = PindahBarangHeadDAO.get(con, noPindah);
                    pindah.setListPindahBarangDetail(PindahBarangDetailDAO.getAllPindahBarangDetail(con, noPindah));
                    for(PindahBarangDetail d : pindah.getListPindahBarangDetail()){
                        d.setBarang(BarangDAO.get(con, d.getKodeBarang()));
                    }
                    return pindah;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
            try{
                mainApp.closeLoading();
                namaSupirField.setDisable(true);
                gudangAsalCombo.setDisable(true);
                gudangTujuanCombo.setDisable(true);
//                catatanField.setDisable(true);
                saveButton.setVisible(false);
                cancelButton.setVisible(false);
                List<MenuItem> removeMenu = new ArrayList<>();
                for(MenuItem m : pengirimanDetailTable.getContextMenu().getItems()){
                    if(m.getText().equals("Add Barang"))
                        removeMenu.add(m);
                }
                pengirimanDetailTable.getContextMenu().getItems().removeAll(removeMenu);
                
                PindahBarangHead p = task.getValue();
                noPengirimanLabel.setText(p.getNoPindah());
                tglPengirimanLabel.setText(tglLengkap.format(tglSql.parse(p.getTglPindah())));
                namaSupirField.setText(p.getSupir());
//                catatanField.setText(p.getCatatan());
                gudangAsalCombo.getSelectionModel().select(p.getGudangAsal());
                gudangTujuanCombo.getSelectionModel().select(p.getGudangTujuan());
                allPindahBarangDetail.addAll(p.getListPindahBarangDetail());
                hitungTotal();
            }catch(Exception e){
                e.printStackTrace();
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        });
        task.setOnFailed((e) -> {
            task.getException().printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }
    @FXML
    private void changeGudang(){
        allPindahBarangDetail.clear();
        hitungTotal();
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    @FXML
    private void addBarang(){
        if(gudangAsalCombo.getSelectionModel().getSelectedItem()==null){
            mainApp.showMessage(Modality.NONE,"Warning", "Gudang asal belum dipilih");
        }else if(gudangTujuanCombo.getSelectionModel().getSelectedItem()==null){
            mainApp.showMessage(Modality.NONE,"Warning", "Gudang tujuan belum dipilih");
        }else if(gudangAsalCombo.getSelectionModel().getSelectedItem().equals(gudangTujuanCombo.getSelectionModel().getSelectedItem())){
            mainApp.showMessage(Modality.NONE, "Warning", "Gudang asal dan tujuan tidak boleh sama");
        }else{
            Stage child = new Stage();
            FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddBarangPindah.fxml");
            AddBarangPindahController controller = loader.getController();
            controller.setMainApp(mainApp, stage, child);
            controller.addButton.setOnAction((ActionEvent event) -> {
                if(controller.barang==null)
                    mainApp.showMessage(Modality.NONE, "Warning", "Barang belum dipilih atau kode barang masih kosong");
                else if(controller.qtyField.getText().equals("0")||controller.qtyField.getText().equals(""))
                    mainApp.showMessage(Modality.NONE, "Warning", "Qty tidak boleh kosong");
                else{
                    mainApp.closeDialog(stage, child);
                    PindahBarangDetail b = null;
                    for(PindahBarangDetail temp : allPindahBarangDetail){
                        if(temp.getKodeBarang().equals(controller.barang.getKodeBarang()))
                            b = temp;
                    }
                    if(b==null){
                        b = new PindahBarangDetail();
                        b.setKodeBarang(controller.barang.getKodeBarang());
                        b.setNamaBarang(controller.barang.getNamaBarang());
                        b.setKeterangan("");
                        b.setSatuan(controller.barang.getSatuan());
                        b.setQty(Double.parseDouble(controller.qtyField.getText().replaceAll(",", "")));
                        b.setNilai(0);
                        b.setBarang(controller.barang);
                        allPindahBarangDetail.add(b);
                    }else{
                        b.setQty(b.getQty()+Double.parseDouble(controller.qtyField.getText().replaceAll(",", "")));
                    }
                    pengirimanDetailTable.refresh();
                    hitungTotal();
                }
            });
        }
    }
}
