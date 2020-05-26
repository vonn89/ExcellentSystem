/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View.Dialog;

import com.excellentsystem.TokoEmasJagoPusat.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.Function;
import static com.excellentsystem.TokoEmasJagoPusat.Function.getTreeTableCell;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.gr;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSql;
import com.excellentsystem.TokoEmasJagoPusat.Model.BarangCabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.Cabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.PindahDetail;
import com.excellentsystem.TokoEmasJagoPusat.Model.StokBarangCabang;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Excellent
 */
public class PindahBarangController {
    @FXML private TreeTableView<PindahDetail> pindahDetailTable;
    @FXML private TreeTableColumn<PindahDetail, String> kodeBarcodeColumn;
    @FXML private TreeTableColumn<PindahDetail, String> namaBarangColumn;
    @FXML private TreeTableColumn<PindahDetail, String> kodeKategoriColumn;
    @FXML private TreeTableColumn<PindahDetail, String> kodeInternColumn;
    @FXML private TreeTableColumn<PindahDetail, String> kadarColumn;
    @FXML private TreeTableColumn<PindahDetail, Number> qtyColumn;
    @FXML private TreeTableColumn<PindahDetail, Number> beratColumn;
    @FXML private TreeTableColumn<PindahDetail, Number> beratAsliColumn;
    @FXML private TreeTableColumn<PindahDetail, String> tglBarcodeColumn;
    @FXML private TreeTableColumn<PindahDetail, String> userBarcodeColumn;
    @FXML private TreeTableColumn<PindahDetail, String> asalBarangColumn;
    
    @FXML public ComboBox<String> cabangCombo;
    @FXML public ComboBox<String> gudangCombo;
    @FXML public Label totalQtyLabel;
    @FXML public Label totalBeratLabel;
    @FXML private Label totalBeratAsliLabel;
    @FXML public Button saveButton;
    
    private final TreeItem<PindahDetail> root = new TreeItem<>();
    private ObservableList<String> allCabang = FXCollections.observableArrayList();
    private ObservableList<String> allGudang = FXCollections.observableArrayList();
    public ObservableList<PindahDetail> listPindahDetail = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeBarcodeColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().kodeBarcodeProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().namaBarangProperty());
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().kodeKategoriProperty());
        kodeInternColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().kodeInternProperty());
        kadarColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().kadarProperty());
        qtyColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().qtyProperty());
        qtyColumn.setCellFactory(col -> getTreeTableCell(rp));
        beratColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().beratProperty());
        beratColumn.setCellFactory(col -> getTreeTableCell(gr));
        beratAsliColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().beratAsliProperty());
        beratAsliColumn.setCellFactory(col -> getTreeTableCell(gr));
        tglBarcodeColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getValue().getInputDate())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglBarcodeColumn.setComparator(Function.sortDate(tglLengkap));
        userBarcodeColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().inputByProperty());
        asalBarangColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().asalBarangProperty());
        
        final ContextMenu menu = new ContextMenu();
        MenuItem addBarang = new MenuItem("Add Barang");
        addBarang.setOnAction((ActionEvent event) -> {
            addBarang();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            setTable();
        });
        menu.getItems().addAll(addBarang, refresh);
        pindahDetailTable.setContextMenu(menu);
        pindahDetailTable.setRowFactory(table -> {
            TreeTableRow<PindahDetail> row = new TreeTableRow<PindahDetail>(){
                @Override
                public void updateItem(PindahDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(menu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem addBarang = new MenuItem("Add Barang");
                        addBarang.setOnAction((ActionEvent event) -> {
                            addBarang();
                        });
                        MenuItem detail = new MenuItem("Detail Barang");
                        detail.setOnAction((ActionEvent event) -> {
                            detailBarang(item);
                        });
                        MenuItem hapus = new MenuItem("Hapus Barang");
                        hapus.setOnAction((ActionEvent event) -> {
                            hapusBarang(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            setTable();
                        });
                        rowMenu.getItems().add(addBarang);
                        if(item.getNamaBarang()!=null){
                            rowMenu.getItems().add(detail);
                            rowMenu.getItems().add(hapus);
                        }
                        rowMenu.getItems().addAll(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&
                        mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null)
                        if(row.getItem().getNamaBarang()!=null)
                            detailBarang(row.getItem());
                    
                }
            });
            return row;
        });
        cabangCombo.setItems(allCabang);
        gudangCombo.setItems(allGudang);
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
        setData();
    }
    private void setData(){
        Task<List<Cabang>> task = new Task<List<Cabang>>() {
            @Override 
            public List<Cabang> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    return CabangDAO.getAll(conPusat);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            List<Cabang> listCabang = task.getValue();
            allCabang.clear();
            for(Cabang c : listCabang){
                allCabang.add(c.getKodeCabang());
            }
            allGudang.clear();
            allGudang.add("New");
            allGudang.add("SP");
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            task.getException().printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    @FXML
    private void reset(){
        listPindahDetail.clear();
        setTable();
    }
    private void setTable(){
        if(pindahDetailTable.getRoot()!=null)
            pindahDetailTable.getRoot().getChildren().clear();
        List<String> jenis = new ArrayList<>();
        for(PindahDetail p: listPindahDetail){
            if(!jenis.contains(p.getKodeJenis().toUpperCase()))
                jenis.add(p.getKodeJenis().toUpperCase());
        }
        for(String s : jenis){
            PindahDetail group = new PindahDetail();
            int qty = 0;
            double berat = 0;
            double beratAsli = 0;
            TreeItem<PindahDetail> parent = new TreeItem<>(group);
            for(PindahDetail p : listPindahDetail){
                if(s.equalsIgnoreCase(p.getKodeJenis())){
                    qty = qty + 1;
                    berat = berat + p.getBerat();
                    beratAsli = beratAsli + p.getBeratAsli();
                    
                    TreeItem<PindahDetail> child = new TreeItem<>(p);
                    parent.getChildren().addAll(child);
                }
            }
            parent.getValue().setKodeBarcode(s);
            parent.getValue().setQty(qty);
            parent.getValue().setBerat(berat);
            parent.getValue().setBeratAsli(beratAsli);
            root.getChildren().add(parent);
        }
        pindahDetailTable.setRoot(root);
        hitungTotal();
    } 
    private void detailBarang(PindahDetail d){
        BarangCabang b = new BarangCabang();
        b.setKodeBarang(d.getKodeBarang());
        b.setKodeBarcode(d.getKodeBarcode());
        b.setNamaBarang(d.getNamaBarang());
        b.setKodeKategori(d.getKodeKategori());
        b.setKodeJenis(d.getKodeJenis());
        b.setKodeIntern(d.getKodeIntern());
        b.setKadar(d.getKadar());
        b.setBerat(d.getBerat());
        b.setBeratAsli(d.getBeratAsli());
        b.setBeratPersen(d.getBeratPersen());
        b.setNilaiPokok(d.getNilaiPokok());
        b.setInputDate(d.getInputDate());
        b.setInputBy(d.getInputBy());
        b.setAsalBarang(d.getAsalBarang());
        b.setStatus(d.getStatus());
        
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/DetailBarang.fxml");
        DetailBarangController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setBarang(b);
    }
    private void addBarang(){
        if(cabangCombo.getSelectionModel().getSelectedItem()==null)
            mainApp.showMessage(Modality.NONE, "Warning", "Kode cabang belum dipilih");
        else if(gudangCombo.getSelectionModel().getSelectedItem()==null)
            mainApp.showMessage(Modality.NONE, "Warning", "Kode gudang belum dipilih");
        else{
            Stage child = new Stage();
            FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/AddBarangPindah.fxml");
            AddBarangPindahController controller = loader.getController();
            controller.setMainApp(mainApp, stage, child);
            controller.getBarang(cabangCombo.getSelectionModel().getSelectedItem(), gudangCombo.getSelectionModel().getSelectedItem(), listPindahDetail);
            controller.addButton.setOnAction((event) -> {
                for(StokBarangCabang s : controller.filterData){
                    if(s.getBarangCabang().isChecked()){
                        PindahDetail d = new PindahDetail();
                        d.setKodeBarang(s.getBarangCabang().getKodeBarang());
                        d.setKodeBarcode(s.getBarangCabang().getKodeBarcode());
                        d.setNamaBarang(s.getBarangCabang().getNamaBarang());
                        d.setKodeKategori(s.getBarangCabang().getKodeKategori());
                        d.setKodeJenis(s.getBarangCabang().getKodeJenis());
                        d.setKodeIntern(s.getBarangCabang().getKodeIntern());
                        d.setKadar(s.getBarangCabang().getKadar());
                        d.setBerat(s.getBarangCabang().getBerat());
                        d.setBeratAsli(s.getBarangCabang().getBeratAsli());
                        d.setBeratPersen(s.getBarangCabang().getBeratPersen());
                        d.setNilaiPokok(s.getBarangCabang().getNilaiPokok());
                        d.setInputDate(s.getBarangCabang().getInputDate());
                        d.setInputBy(s.getBarangCabang().getInputBy());
                        d.setAsalBarang(s.getBarangCabang().getAsalBarang());
                        d.setStatus(s.getBarangCabang().getStatus());
                        listPindahDetail.add(d);
                    }
                }
                setTable();
                mainApp.closeDialog(stage, child);
            });
            
        }
    }
    private void hapusBarang(PindahDetail d){
        listPindahDetail.remove(d);
        setTable();
    }
    private void hitungTotal(){
        double qty = 0;
        double berat = 0;
        double beratAsli = 0;
        for(PindahDetail d : listPindahDetail){
            qty = qty + 1;
            berat = berat + d.getBerat();
            beratAsli = beratAsli + d.getBeratAsli();
        }
        totalQtyLabel.setText(rp.format(qty));
        totalBeratLabel.setText(gr.format(berat));
        totalBeratAsliLabel.setText(gr.format(beratAsli));
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
