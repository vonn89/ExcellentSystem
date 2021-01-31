/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.DAO.BahanDAO;
import com.excellentsystem.AuriSteel.DAO.BarangDAO;
import com.excellentsystem.AuriSteel.DAO.ProduksiDetailBahanDAO;
import com.excellentsystem.AuriSteel.DAO.ProduksiDetailBarangDAO;
import com.excellentsystem.AuriSteel.DAO.ProduksiHeadDAO;
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
import com.excellentsystem.AuriSteel.Model.Mesin;
import com.excellentsystem.AuriSteel.Model.Pegawai;
import com.excellentsystem.AuriSteel.Model.ProduksiDetailBahan;
import com.excellentsystem.AuriSteel.Model.ProduksiDetailBarang;
import com.excellentsystem.AuriSteel.Model.ProduksiHead;
import com.excellentsystem.AuriSteel.Model.StokBahan;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleDoubleProperty;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class NewProduksiBarangController  {
    
    @FXML private TableView<ProduksiDetailBarang> barangProduksiTable;
    @FXML private TableColumn<ProduksiDetailBarang, String> kodeBarangJadiColumn;
    @FXML private TableColumn<ProduksiDetailBarang, Number> qtyBarangJadiColumn;
    @FXML private TableColumn<ProduksiDetailBarang, Number> beratBarangJadiColumn;
    @FXML public Label totalQtyBarangLabel;
    @FXML public Label totalBeratBarangLabel;
    
    @FXML private TableView<ProduksiDetailBahan> bahanDiproduksiTable;
    @FXML private TableColumn<ProduksiDetailBahan, String> kategoriBahanColumn;
    @FXML private TableColumn<ProduksiDetailBahan, String> kodeBahanColumn;
    @FXML private TableColumn<ProduksiDetailBahan, Number> stokBahanColumn;
    @FXML private TableColumn<ProduksiDetailBahan, Number> beratBahanDiproduksiColumn;
    @FXML private TableColumn<ProduksiDetailBahan, Boolean> bahanHabisColumn;
    @FXML private TableColumn<ProduksiDetailBahan, Number> sisaBeratColumn;
    
    @FXML private TableColumn<ProduksiDetailBahan, String> kodeBarangColumn;
    @FXML private TableColumn<ProduksiDetailBahan, Number> qtyColumn;
    @FXML private TableColumn<ProduksiDetailBahan, Number> beratColumn;
    
    @FXML public Label totalQtyBahanLabel;
    @FXML public Label totalBeratBahanLabel;
    
    @FXML private GridPane gridPane;
    @FXML private Label noProduksiField;
    @FXML private Label tglProduksiField;
    @FXML private Button addOperatorButton;
    @FXML private Button resetOperatorButton;
    @FXML private TextArea operatorField;
    @FXML public ComboBox<String> mesinCombo;
    @FXML public ComboBox<String> gudangCombo;
    @FXML public ComboBox<String> jenisCombo;
    
    @FXML public TextArea catatanField;
    @FXML public Button saveButton;
    @FXML private Button cancelButton;
    
    public ObservableList<ProduksiDetailBahan> listBahanDiproduksi = FXCollections.observableArrayList();
    public ObservableList<ProduksiDetailBarang> listBarangProduksi = FXCollections.observableArrayList();
    public ObservableList<Pegawai> listOperator = FXCollections.observableArrayList();
    private Main mainApp;   
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kategoriBahanColumn.setCellValueFactory(cellData -> {
            String kodeKategori = "";
            if(cellData.getValue().getBahan()!=null){
                kodeKategori = cellData.getValue().getBahan().getKodeKategori();
            }
            return new SimpleStringProperty(kodeKategori);
        });
        kodeBahanColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarangProperty());
        stokBahanColumn.setCellValueFactory(cellData -> {
            if(cellData.getValue().getBahan().getStokBahan()!=null)
                return new SimpleDoubleProperty(cellData.getValue().getBahan().getStokBahan().getStokAkhir());
            else
                return new SimpleDoubleProperty(0);
        });
        stokBahanColumn.setCellFactory(col -> Function.getTableCell());
        beratBahanDiproduksiColumn.setCellValueFactory(cellData -> cellData.getValue().qtyProperty());
        beratBahanDiproduksiColumn.setCellFactory(col -> Function.getTableCell());
        bahanHabisColumn.setCellValueFactory(cellData -> cellData.getValue().bahanHabisProperty());
        bahanHabisColumn.setCellFactory(CheckBoxTableCell.forTableColumn((Integer i) -> bahanDiproduksiTable.getItems().get(i).bahanHabisProperty()));
        
        sisaBeratColumn.setCellValueFactory(cellData -> {
            if(cellData.getValue().getBahan().getStokBahan()!=null)
                return new SimpleDoubleProperty(cellData.getValue().getBahan().getStokBahan().getStokAkhir()-cellData.getValue().getQty());
            else
                return new SimpleDoubleProperty(0);
        });
        sisaBeratColumn.setCellFactory(col -> Function.getTableCell());
        
        kodeBarangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarangProperty());
        qtyColumn.setCellValueFactory(cellData -> cellData.getValue().qtyProperty());
        qtyColumn.setCellFactory(col -> Function.getTableCell());
        beratColumn.setCellValueFactory(cellData -> {
            return new SimpleDoubleProperty(cellData.getValue().getQty()*cellData.getValue().getBarang().getBerat());
        });
        beratColumn.setCellFactory(col -> Function.getTableCell());
        
        
        kodeBarangJadiColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarangProperty());
        qtyBarangJadiColumn.setCellValueFactory(cellData -> cellData.getValue().qtyProperty());
        qtyBarangJadiColumn.setCellFactory(col -> Function.getTableCell());
        beratBarangJadiColumn.setCellValueFactory(cellData -> {
            return new SimpleDoubleProperty(cellData.getValue().getQty()*cellData.getValue().getBarang().getBerat());
        });
        beratBarangJadiColumn.setCellFactory(col -> Function.getTableCell());
        
        barangProduksiTable.setItems(listBarangProduksi);
        bahanDiproduksiTable.setItems(listBahanDiproduksi);
        
        ContextMenu barangContextMenu = new ContextMenu();
        MenuItem addBarang = new MenuItem("Add Barang Produksi");
        addBarang.setOnAction((ActionEvent e)->{
            addBarang();
        });
        MenuItem refreshBarang = new MenuItem("Refresh");
        refreshBarang.setOnAction((ActionEvent e)->{
            barangProduksiTable.refresh();
        });
        barangContextMenu.getItems().addAll(addBarang, refreshBarang);
        barangProduksiTable.setContextMenu(barangContextMenu);
        barangProduksiTable.setRowFactory((TableView<ProduksiDetailBarang> tv) -> {
            final TableRow<ProduksiDetailBarang> row = new TableRow<ProduksiDetailBarang>(){
                @Override
                public void updateItem(ProduksiDetailBarang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(barangContextMenu);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem addBarang = new MenuItem("Add Barang Produksi");
                        addBarang.setOnAction((ActionEvent e)->{
                            addBarang();
                        });
                        MenuItem hapus = new MenuItem("Hapus Barang Produksi");
                        hapus.setOnAction((ActionEvent e)->{
                            removeBarang(item);
                        });
                        MenuItem refreshBarang = new MenuItem("Refresh");
                        refreshBarang.setOnAction((ActionEvent e)->{
                            barangProduksiTable.refresh();
                        });
                        if(saveButton.isVisible())
                            rm.getItems().addAll(addBarang, hapus, refreshBarang);
                        setContextMenu(rm);
                    }
                }
            };
            return row;
        });
        
        ContextMenu bahanDiproduksi = new ContextMenu();
        MenuItem addBahanProduksi = new MenuItem("Add Bahan Produksi");
        addBahanProduksi.setOnAction((ActionEvent e)->{
            addBahan();
        });
        MenuItem refreshBahanDibutuhkan = new MenuItem("Refresh");
        refreshBahanDibutuhkan.setOnAction((ActionEvent e)->{
            bahanDiproduksiTable.refresh();
        });
        bahanDiproduksi.getItems().addAll(addBahanProduksi, refreshBahanDibutuhkan);
        bahanDiproduksiTable.setContextMenu(bahanDiproduksi);
        bahanDiproduksiTable.setRowFactory((TableView<ProduksiDetailBahan> tableView) -> {
            final TableRow<ProduksiDetailBahan> row = new TableRow<ProduksiDetailBahan>(){
                @Override
                public void updateItem(ProduksiDetailBahan item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(bahanDiproduksi);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem addBahan = new MenuItem("Add Bahan Produksi");
                        addBahan.setOnAction((ActionEvent e)->{
                            addBahan();
                        });
                        MenuItem hapus = new MenuItem("Hapus Bahan Produksi");
                        hapus.setOnAction((ActionEvent e)->{
                            removeBahan(item);
                        });
                        MenuItem refreshBahan = new MenuItem("Refresh");
                        refreshBahan.setOnAction((ActionEvent e)->{
                            bahanDiproduksiTable.refresh();
                        });
                        if(saveButton.isVisible())
                            rm.getItems().addAll(addBahan, hapus, refreshBahan);
                        setContextMenu(rm);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null && saveButton.isVisible()){
                        hitungQtyDiproduksi(row.getItem());
                    }
                }
            });
            return row;
        });
    }
    public void setMainApp(Main mainApp,Stage owner, Stage stage) {
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
        gudangCombo.setItems(listGudang);
        ObservableList<String> listMesin = FXCollections.observableArrayList();
        for(Mesin m : sistem.getListMesin()){
            listMesin.add(m.getKodeMesin());
        }
        mesinCombo.setItems(listMesin);
        ObservableList<String> listJenis = FXCollections.observableArrayList();
        listJenis.add("Bahan - Barang");
        listJenis.add("Barang - Barang");
        jenisCombo.setItems(listJenis);
        jenisCombo.getSelectionModel().selectFirst();
    }   
    public void setNewProduksi(){
        noProduksiField.setText("");
        tglProduksiField.setText("");
    }
    @FXML
    private void changeGudang(){
        listBahanDiproduksi.clear();
        listBarangProduksi.clear();
        hitungTotalBahan();
        hitungTotalBarang();
    }
    @FXML
    private void changeJenis(){
        if(jenisCombo.getSelectionModel().getSelectedItem().equals("Bahan - Barang")){
            kategoriBahanColumn.setVisible(true);
            kodeBahanColumn.setVisible(true);
            stokBahanColumn.setVisible(true);
            beratBahanDiproduksiColumn.setVisible(true);
            bahanHabisColumn.setVisible(true);
            sisaBeratColumn.setVisible(true);
            
            kodeBarangColumn.setVisible(false);
            qtyColumn.setVisible(false);
            beratColumn.setVisible(false);
            changeGudang();
        }else if(jenisCombo.getSelectionModel().getSelectedItem().equals("Barang - Barang")){
            kategoriBahanColumn.setVisible(false);
            kodeBahanColumn.setVisible(false);
            stokBahanColumn.setVisible(false);
            beratBahanDiproduksiColumn.setVisible(false);
            bahanHabisColumn.setVisible(false);
            sisaBeratColumn.setVisible(false);
            
            kodeBarangColumn.setVisible(true);
            qtyColumn.setVisible(true);
            beratColumn.setVisible(true);
            changeGudang();
        }else if(jenisCombo.getSelectionModel().getSelectedItem().equals("Bahan - Bahan")){
            
        }
    }
    public void setDetailProduksi(String kodeProduksi){
        Task<ProduksiHead> task = new Task<ProduksiHead>() {
            @Override 
            public ProduksiHead call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    ProduksiHead produksi = ProduksiHeadDAO.get(con, kodeProduksi);
                    produksi.setListProduksiDetailBahan(ProduksiDetailBahanDAO.get(con, kodeProduksi));
                    if(produksi.getJenisProduksi().equals("Bahan - Barang")){
                        for(ProduksiDetailBahan d : produksi.getListProduksiDetailBahan()){
                            d.setBahan(BahanDAO.get(con, d.getKodeBarang()));
                        }
                    }else if(produksi.getJenisProduksi().equals("Barang - Barang")){
                        for(ProduksiDetailBahan d : produksi.getListProduksiDetailBahan()){
                            d.setBarang(BarangDAO.get(con, d.getKodeBarang()));
                        }
                    }
                    produksi.setListProduksiDetailBarang(ProduksiDetailBarangDAO.get(con, kodeProduksi));
                    for(ProduksiDetailBarang d : produksi.getListProduksiDetailBarang()){
                        d.setBarang(BarangDAO.get(con, d.getKodeBarang()));
                    }
                    return produksi;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
            try{
                mainApp.closeLoading();
                ProduksiHead produksi = task.getValue();
                noProduksiField.setText(produksi.getKodeProduksi());
                tglProduksiField.setText(tglLengkap.format(tglSql.parse(produksi.getTglProduksi())));
                gudangCombo.getSelectionModel().select(produksi.getKodeGudang());
                jenisCombo.getSelectionModel().select(produksi.getJenisProduksi());
                mesinCombo.getSelectionModel().select(produksi.getKodeMesin());
                catatanField.setText(produksi.getCatatan());
                changeJenis();
                
                catatanField.setDisable(true);
                saveButton.setVisible(false);
                cancelButton.setVisible(false);
                addOperatorButton.setVisible(false);
                resetOperatorButton.setVisible(false);
                stokBahanColumn.setVisible(false);
                bahanHabisColumn.setVisible(false);
                sisaBeratColumn.setVisible(false);
                gudangCombo.setDisable(true);
                jenisCombo.setDisable(true);
                mesinCombo.setDisable(true);
                List<MenuItem> removeBarangProduksi = new ArrayList<>();
                for(MenuItem m : barangProduksiTable.getContextMenu().getItems()){
                    if(m.getText().equals("Add Barang Produksi"))
                        removeBarangProduksi.add(m);
                }
                barangProduksiTable.getContextMenu().getItems().removeAll(removeBarangProduksi);
                List<MenuItem> removeBahanProduksi = new ArrayList<>();
                for(MenuItem m : bahanDiproduksiTable.getContextMenu().getItems()){
                    if(m.getText().equals("Add Bahan Produksi"))
                        removeBahanProduksi.add(m);
                }
                bahanDiproduksiTable.getContextMenu().getItems().removeAll(removeBahanProduksi);

                listBarangProduksi.clear();
                listBarangProduksi.addAll(produksi.getListProduksiDetailBarang());
                listBahanDiproduksi.clear();
                listBahanDiproduksi.addAll(produksi.getListProduksiDetailBahan());
                AnchorPane.setBottomAnchor(gridPane, 0.0);
                hitungTotalBahan();
                hitungTotalBarang();
            }catch(Exception e){
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }   
    @FXML
    private void addOperator(){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddPegawai.fxml");
        AddPegawaiController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.getPegawai("Operator");
        controller.pegawaiTable.setRowFactory((param) -> {
            TableRow<Pegawai> row = new TableRow<Pegawai>(){};
            row.setOnMouseClicked((MouseEvent evt) -> {
                if(evt.getButton().equals(MouseButton.PRIMARY) && evt.getClickCount()==2){
                    if(row.getItem().getNama()!=null){
                        if(listOperator.contains(row.getItem())){   
                            mainApp.showMessage(Modality.NONE, "Warning", "Operator sudah pernah diinput");
                        }else{
                            mainApp.closeDialog(stage, child);
                            listOperator.add(row.getItem());
                            String operator = "";
                            for(Pegawai p : listOperator){
                                if(listOperator.indexOf(p)==0)
                                    operator = p.getNama();
                                else
                                    operator = operator +"\n"+ p.getNama();
                            }
                            operatorField.setText(operator);
                        }
                    }
                }
            });
            return row; 
        });
    }
    @FXML
    private void resetOperator(){
        listOperator.clear();
        operatorField.setText("");
    }
    private void addBarang(){
        if(gudangCombo.getSelectionModel().getSelectedItem()==null){
            mainApp.showMessage(Modality.NONE, "Warning", "Gudang belum dipilih");
        }else{
            Stage child = new Stage();
            FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddBarangProduksi.fxml");
            AddBarangProduksiController controller = loader.getController();
            controller.setMainApp(mainApp, stage, child);
            controller.addButton.setOnAction((ActionEvent event) -> {
                if(controller.barang==null)
                    mainApp.showMessage(Modality.NONE, "Warning", "Barang belum dipilih atau kode barang masih kosong");
                else if(controller.qtyField.getText().equals("0")||controller.qtyField.getText().equals(""))
                    mainApp.showMessage(Modality.NONE, "Warning", "Qty tidak boleh kosong");
                else{
                    mainApp.closeDialog(stage, child);
                    ProduksiDetailBarang b = null;
                    for(ProduksiDetailBarang temp : listBarangProduksi){
                        if(temp.getKodeBarang().equals(controller.barang.getKodeBarang()))
                            b = temp;
                    }
                    if(b==null){
                        b = new ProduksiDetailBarang();
                        b.setBarang(controller.barang);
                        b.setKodeBarang(controller.barang.getKodeBarang());
                        b.setQty(Double.parseDouble(controller.qtyField.getText().replaceAll(",", "")));
                        listBarangProduksi.add(b);
                    }else{
                        b.setQty(b.getQty()+Double.parseDouble(controller.qtyField.getText().replaceAll(",", "")));
                    }
                    barangProduksiTable.refresh();
                    hitungTotalBarang();
                }
            });
        }
    }
    private void addBahan(){
        if(gudangCombo.getSelectionModel().getSelectedItem()==null){
            mainApp.showMessage(Modality.NONE, "Warning", "Gudang belum dipilih");
        }else{
            if(jenisCombo.getSelectionModel().getSelectedItem().equals("Bahan - Barang")){
                Stage child = new Stage();
                FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddBahan.fxml");
                AddBahanController controller = loader.getController();
                controller.setMainApp(mainApp, stage, child);
                controller.getBahan(gudangCombo.getSelectionModel().getSelectedItem());
                controller.bahanTable.setRowFactory((param) -> {
                    TreeTableRow<Bahan> row = new TreeTableRow<Bahan>(){};
                    row.setOnMouseClicked((MouseEvent evt) -> {
                        if(evt.getButton().equals(MouseButton.PRIMARY) && evt.getClickCount()==2){
                            if(row.getItem().getNamaBahan()!=null){
                                mainApp.closeDialog(stage, child);
                                Bahan bahan = row.getItem();
                                boolean statusInput = false;
                                for(ProduksiDetailBahan temp : listBahanDiproduksi){
                                    if(temp.getKodeBarang().equals(bahan.getKodeBahan()))
                                        statusInput = true;
                                }
                                if(statusInput){
                                    mainApp.showMessage(Modality.NONE, "Warning", "Kode bahan sudah diinput");
                                }else{
                                    try(Connection con = Koneksi.getConnection()){
                                        StokBahan s = StokBahanDAO.getStokAkhir(con, 
                                                bahan.getKodeBahan(), gudangCombo.getSelectionModel().getSelectedItem());
                                        if(s==null){
                                            mainApp.showMessage(Modality.NONE, "Warning", "Stok bahan tidak ditemukan");
                                        }else{
                                            if(s.getStokAkhir()<=0){
                                                mainApp.showMessage(Modality.NONE, "Warning", "Stok bahan sudah habis");
                                            }else{
                                                bahan.setStokBahan(s);

                                                ProduksiDetailBahan b = new ProduksiDetailBahan();
                                                b.setBahan(bahan);
                                                b.setKodeBarang(bahan.getKodeBahan());
                                                b.setQty(0);
                                                b.setBahanHabis(false);
                                                listBahanDiproduksi.add(b);
                                                bahanDiproduksiTable.refresh();
                                                hitungTotalBahan();
                                            }
                                        }
                                    }catch(Exception e){
                                        mainApp.showMessage(Modality.NONE, "Error", e.toString());
                                    }
                                }
                            }
                        }
                    });
                    return row; 
                });
            }if(jenisCombo.getSelectionModel().getSelectedItem().equals("Barang - Barang")){
                Stage child = new Stage();
                FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddBarangProduksi.fxml");
                AddBarangProduksiController controller = loader.getController();
                controller.setMainApp(mainApp, stage, child);
                controller.addButton.setOnAction((ActionEvent event) -> {
                    if(controller.barang==null)
                        mainApp.showMessage(Modality.NONE, "Warning", "Barang belum dipilih atau kode barang masih kosong");
                    else if(controller.qtyField.getText().equals("0")||controller.qtyField.getText().equals(""))
                        mainApp.showMessage(Modality.NONE, "Warning", "Qty tidak boleh kosong");
                    else{
                        mainApp.closeDialog(stage, child);
                        ProduksiDetailBahan b = null;
                        for(ProduksiDetailBahan temp : listBahanDiproduksi){
                            if(temp.getKodeBarang().equals(controller.barang.getKodeBarang()))
                                b = temp;
                        }
                        if(b==null){
                            b = new ProduksiDetailBahan();
                            b.setBarang(controller.barang);
                            b.setKodeBarang(controller.barang.getKodeBarang());
                            b.setQty(Double.parseDouble(controller.qtyField.getText().replaceAll(",", "")));
                            listBahanDiproduksi.add(b);
                        }else{
                            b.setQty(b.getQty()+Double.parseDouble(controller.qtyField.getText().replaceAll(",", "")));
                        }
                        bahanDiproduksiTable.refresh();
                        hitungTotalBahan();
                    }
                });
            }
        }
    }
    private void hitungQtyDiproduksi(ProduksiDetailBahan d){
        double totalBeratBarang = Double.parseDouble(totalBeratBarangLabel.getText().replaceAll(",", ""));
        double totalBeratBahan = Double.parseDouble(totalBeratBahanLabel.getText().replaceAll(",", ""));
        if(d.isBahanHabis()){
            if(totalBeratBahan>totalBeratBarang){
                d.setBahanHabis(false);
                if(d.getQty()>=(totalBeratBahan-totalBeratBarang))
                    d.setQty(d.getQty()-(totalBeratBahan-totalBeratBarang));
                else
                    d.setQty(0);
            }
        }else{
            d.setBahanHabis(true);
            d.setQty(d.getBahan().getStokBahan().getStokAkhir());
        }
        bahanDiproduksiTable.refresh();
        hitungTotalBahan();
    }
    private void hitungTotalBahan(){
        double totalQty = 0;
        double totalBerat = 0;
        for(ProduksiDetailBahan d : listBahanDiproduksi){
            if(jenisCombo.getSelectionModel().getSelectedItem().equals("Bahan - Barang")){
                totalQty = totalQty + 1;
                totalBerat = totalBerat + d.getQty();
            }else if(jenisCombo.getSelectionModel().getSelectedItem().equals("Barang - Barang")){
                totalQty = totalQty + d.getQty();
                totalBerat = totalBerat + (d.getQty()*d.getBarang().getBerat());
            }
        }
        totalQtyBahanLabel.setText(df.format(totalQty));
        totalBeratBahanLabel.setText(df.format(totalBerat));
    }
    private void hitungTotalBarang(){
        double totalQty = 0;
        double totalBerat = 0;
        for(ProduksiDetailBarang d : listBarangProduksi){
            totalQty = totalQty + d.getQty();
            totalBerat = totalBerat + d.getQty()*d.getBarang().getBerat();
        }
        totalQtyBarangLabel.setText(df.format(totalQty));
        totalBeratBarangLabel.setText(df.format(totalBerat));
    }
    private void removeBarang(ProduksiDetailBarang b){
        listBarangProduksi.remove(b);
        hitungTotalBarang();
    }
    private void removeBahan(ProduksiDetailBahan b){
        listBahanDiproduksi.remove(b);
        hitungTotalBahan();
    }
}
