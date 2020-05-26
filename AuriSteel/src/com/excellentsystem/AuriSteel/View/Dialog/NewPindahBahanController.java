/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.DAO.PindahBahanDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PindahBahanHeadDAO;
import com.excellentsystem.AuriSteel.DAO.StokBahanDAO;
import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.sistem;
import static com.excellentsystem.AuriSteel.Main.tglLengkap;
import static com.excellentsystem.AuriSteel.Main.tglSql;
import com.excellentsystem.AuriSteel.Model.Bahan;
import com.excellentsystem.AuriSteel.Model.Gudang;
import com.excellentsystem.AuriSteel.Model.PindahBahanDetail;
import com.excellentsystem.AuriSteel.Model.PindahBahanHead;
import com.excellentsystem.AuriSteel.Model.StokBahan;
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
import javafx.scene.control.TreeTableRow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class NewPindahBahanController  {

    @FXML private TableView<PindahBahanDetail> pengirimanDetailTable;
    @FXML private TableColumn<PindahBahanDetail, String> kodeBahanColumn;
    @FXML private TableColumn<PindahBahanDetail, String> namaBahanColumn;
    @FXML private TableColumn<PindahBahanDetail, String> spesifikasiColumn;
    @FXML private TableColumn<PindahBahanDetail, String> keteranganColumn;
    @FXML private TableColumn<PindahBahanDetail, Number> beratKotorColumn;
    @FXML private TableColumn<PindahBahanDetail, Number> beratBersihColumn;
    @FXML private TableColumn<PindahBahanDetail, Number> panjangColumn;
    
    
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
    
    public ObservableList<PindahBahanDetail> allPindahBahanDetail = FXCollections.observableArrayList();
    private Main mainApp;   
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeBahanColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBahanProperty());
        namaBahanColumn.setCellValueFactory(cellData -> cellData.getValue().namaBahanProperty());
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        spesifikasiColumn.setCellValueFactory(cellData -> cellData.getValue().spesifikasiProperty());
        beratKotorColumn.setCellValueFactory(cellData -> cellData.getValue().beratKotorProperty());
        beratKotorColumn.setCellFactory(col -> Function.getTableCell());
        beratBersihColumn.setCellValueFactory(cellData -> cellData.getValue().beratBersihProperty());
        beratBersihColumn.setCellFactory(col -> Function.getTableCell());
        panjangColumn.setCellValueFactory(cellData -> cellData.getValue().panjangProperty());
        panjangColumn.setCellFactory(col -> Function.getTableCell());
        pengirimanDetailTable.setItems(allPindahBahanDetail);
        
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
        pengirimanDetailTable.setRowFactory((TableView<PindahBahanDetail> tv) -> {
            final TableRow<PindahBahanDetail> row = new TableRow<PindahBahanDetail>(){
                @Override
                public void updateItem(PindahBahanDetail item, boolean empty) {
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
                            allPindahBahanDetail.remove(item);
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
        for(PindahBahanDetail d : allPindahBahanDetail){
            total = total + 1;
            totalTonase = totalTonase + d.getBeratKotor();
        }
        totalQtyField.setText(df.format(total));
        totalTonaseField.setText(df.format(totalTonase));
    }
    public void setNewPindah(){
        noPengirimanLabel.setText("");
        tglPengirimanLabel.setText("");
    }
    public void setDetailPindah(String noPindah){
        Task<PindahBahanHead> task = new Task<PindahBahanHead>() {
            @Override 
            public PindahBahanHead call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    PindahBahanHead pindah = PindahBahanHeadDAO.get(con, noPindah);
                    pindah.setListPindahBahanDetail(PindahBahanDetailDAO.getAllPindahBahanDetail(con, noPindah));
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
                
                PindahBahanHead p = task.getValue();
                noPengirimanLabel.setText(p.getNoPindah());
                tglPengirimanLabel.setText(tglLengkap.format(tglSql.parse(p.getTglPindah())));
                namaSupirField.setText(p.getSupir());
//                catatanField.setText(p.getCatatan());
                gudangAsalCombo.getSelectionModel().select(p.getGudangAsal());
                gudangTujuanCombo.getSelectionModel().select(p.getGudangTujuan());
                allPindahBahanDetail.addAll(p.getListPindahBahanDetail());
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
        allPindahBahanDetail.clear();
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
            FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddBahan.fxml");
            AddBahanController controller = loader.getController();
            controller.setMainApp(mainApp, stage, child);
            controller.getBahan(gudangAsalCombo.getSelectionModel().getSelectedItem());
            controller.bahanTable.setRowFactory((param) -> {
                TreeTableRow<Bahan> row = new TreeTableRow<Bahan>(){};
                row.setOnMouseClicked((MouseEvent evt) -> {
                    if(evt.getButton().equals(MouseButton.PRIMARY) && evt.getClickCount()==2){
                        if(row.getItem().getNamaBahan()!=null){
                            try(Connection con = Koneksi.getConnection()){
                                Bahan b = row.getItem();
                                Boolean status = true;
                                for(PindahBahanDetail d : allPindahBahanDetail){
                                    if(d.getKodeBahan().equals(b.getKodeBahan()))
                                        status=false;
                                }
                                if(status==false){
                                    mainApp.showMessage(Modality.NONE, "Warning", "Kode bahan baku sudah diinput");
                                }else{
                                    StokBahan s = StokBahanDAO.getStokAkhir(con, 
                                            b.getKodeBahan(), gudangAsalCombo.getSelectionModel().getSelectedItem());
                                    if(s==null){
                                        mainApp.showMessage(Modality.NONE, "Warning", "Stok bahan tidak ditemukan");
                                    }else{
                                        if(s.getStokAkhir()<=0){
                                            mainApp.showMessage(Modality.NONE, "Warning", "Stok bahan sudah habis");
                                        }else{
                                            mainApp.closeDialog(stage,child);
                                            PindahBahanDetail detail = new PindahBahanDetail();
                                            detail.setKodeBahan(b.getKodeBahan());
                                            detail.setKodeKategori(b.getKodeKategori());
                                            detail.setNamaBahan(b.getNamaBahan());
                                            detail.setNoKontrak(b.getNoKontrak());
                                            detail.setSpesifikasi(b.getSpesifikasi());
                                            detail.setKeterangan(b.getKeterangan());
                                            detail.setBeratKotor(b.getBeratKotor());
                                            detail.setBeratBersih(b.getBeratBersih());
                                            detail.setPanjang(b.getPanjang());
                                            detail.setNilai(b.getHargaBeli());
                                            allPindahBahanDetail.add(detail);
                                            hitungTotal();
                                        }
                                    }
                                } 
                            }catch(Exception e){
                                mainApp.showMessage(Modality.NONE, "Error", e.toString());
                            }
                        }
                    }
                });
                return row; 
            });
        }
    }
}
