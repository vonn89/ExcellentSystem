/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.sentralindahperdana.View.Dialog;

import com.excellentsystem.sentralindahperdana.DAO.BarangDAO;
import com.excellentsystem.sentralindahperdana.DAO.PembelianDetailDAO;
import com.excellentsystem.sentralindahperdana.DAO.PembelianHeadDAO;
import com.excellentsystem.sentralindahperdana.DAO.SatuanDAO;
import com.excellentsystem.sentralindahperdana.DAO.SupplierDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import static com.excellentsystem.sentralindahperdana.Main.tglLengkap;
import static com.excellentsystem.sentralindahperdana.Main.tglSql;
import com.excellentsystem.sentralindahperdana.Model.Barang;
import com.excellentsystem.sentralindahperdana.Model.PembelianDetail;
import com.excellentsystem.sentralindahperdana.Model.PembelianHead;
import com.excellentsystem.sentralindahperdana.Model.Satuan;
import com.excellentsystem.sentralindahperdana.Model.Supplier;
import com.excellentsystem.sentralindahperdana.Service.Service;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class NewPembelianController  {
    @FXML private TableView<PembelianDetail> pembelianDetailTable;
    @FXML private TableColumn<PembelianDetail, String> kodeBarangColumn;
    @FXML private TableColumn<PembelianDetail, String> namaBarangColumn;
    @FXML private TableColumn<PembelianDetail, String> satuanColumn;
    @FXML private TableColumn<PembelianDetail, Number> qtyColumn;
    @FXML private TableColumn<PembelianDetail, Number> hargaBeliColumn;
    @FXML private TableColumn<PembelianDetail, Number> totalColumn;
    @FXML private TableColumn removeColumn = new TableColumn<>("");
    
    @FXML private Label noPembelianField;
    @FXML private Label tglPembelianField;
    
    @FXML private TextField namaField;
    @FXML private TextArea alamatField;
    
    @FXML private TextArea catatanField;
    
    @FXML private TextField totalPembelianField;
    @FXML private TextField pembayaranField;
    @FXML private TextField sisaPembayaranField;
    
    @FXML private Button addSupplierButton;
    @FXML private Button addItemButton;
    @FXML public Button saveButton;
    
    private PembelianHead pembelian;
    private ObservableList<PembelianDetail> allPembelianDetail = FXCollections.observableArrayList();
    private Main mainApp;   
    private Stage stage;
    private Stage owner;
    private Callback<TableColumn, TableCell> getButtonCell(){
        return (final TableColumn p) -> new TableCell<PembelianDetail, Boolean>(){
            final Button btn = new Button("");
            @Override
            public void updateItem( Boolean item, boolean empty ){
                super.updateItem( item, empty );
                if ( empty ){
                    setGraphic( null );
                }else{
                    btn.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("Resource/delete.png"),20,20,false,true)));
                    btn.setPrefSize(30, 30);
                    btn.setOnAction((ActionEvent e) ->{
                        PembelianDetail d = (PembelianDetail)getTableView().getItems().get( getIndex() );
                        allPembelianDetail.remove(d);
                        hitungTotal();
                    });
                    if(saveButton.isVisible())
                        setGraphic(btn);
                    else
                        setGraphic(null);
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
        qtyColumn.setCellValueFactory(cellData -> {
            double q = cellData.getValue().getQty();
            for (Satuan temp : cellData.getValue().getBarang().getAllSatuan()) {
                if (temp.getKodeSatuan().equals(cellData.getValue().getSatuan())) 
                    q = q / temp.getQty();
            }
            return new SimpleDoubleProperty(q);
        });
        qtyColumn.setCellFactory((c) -> Function.getTableCell());
        hargaBeliColumn.setCellValueFactory(cellData -> {
            double harga = cellData.getValue().getHarga();
            for (Satuan temp : cellData.getValue().getBarang().getAllSatuan()) {
                if (temp.getKodeSatuan().equals(cellData.getValue().getSatuan())) 
                    harga = harga * temp.getQty();
            }
            return new SimpleDoubleProperty(harga);
        });
        hargaBeliColumn.setCellFactory((c) -> Function.getTableCell());
        totalColumn.setCellValueFactory(cellData -> 
            new SimpleDoubleProperty(cellData.getValue().getHarga()*cellData.getValue().getQty()));
        totalColumn.setCellFactory(col -> Function.getTableCell());
        
        removeColumn.setCellValueFactory( new PropertyValueFactory<>(""));
        removeColumn.setCellFactory(getButtonCell());
        pembelianDetailTable.setItems(allPembelianDetail);
    }
    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.screenSize.getHeight()-80);
        stage.setWidth(mainApp.screenSize.getWidth()-80);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
    }
    @FXML
    private void hitungTotal(){
        double totalPembelian = 0;
        for(PembelianDetail temp : allPembelianDetail){
            totalPembelian = totalPembelian + temp.getQty()*temp.getHarga();
        }
        totalPembelianField.setText(df.format(totalPembelian));
        sisaPembayaranField.setText(df.format(
            Double.parseDouble(totalPembelianField.getText().replaceAll(",", ""))-
            Double.parseDouble(pembayaranField.getText().replaceAll(",", ""))));
    }
    public void setNewPembelian(){
        Task<String> task = new Task<String>() {
            @Override 
            public String call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    return PembelianHeadDAO.getId(con);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
            try{
                mainApp.closeLoading();
                pembelian = new PembelianHead();
                pembelian.setNoPembelian(task.getValue());
                pembelian.setTglPembelian(tglSql.format(new Date()));
                pembelian.setPembayaran(0);
                noPembelianField.setText(pembelian.getNoPembelian());
                tglPembelianField.setText(tglLengkap.format(tglSql.parse(pembelian.getTglPembelian())));
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
    public void setDetailPembelian(String noPembelian){
        Task<PembelianHead> task = new Task<PembelianHead>() {
            @Override 
            public PembelianHead call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    pembelian = PembelianHeadDAO.get(con, noPembelian);
                    pembelian.setSupplier(SupplierDAO.get(con, pembelian.getKodeSupplier()));
                    pembelian.setAllDetail(PembelianDetailDAO.getAllByNoPembelian(con, noPembelian));
                    List<Barang> listBarang = BarangDAO.getAllByStatus(con, "%");
                    List<Satuan> listSatuan = SatuanDAO.getAll(con);
                    for(PembelianDetail d : pembelian.getAllDetail()){
                        for(Barang b : listBarang){
                            if(d.getKodeBarang().equals(b.getKodeBarang()))
                                d.setBarang(b);
                        }
                        List<Satuan> satuan = new ArrayList<>();
                        for(Satuan s : listSatuan){
                            if(d.getKodeBarang().equals(s.getKodeBarang()))
                                satuan.add(s);
                        }
                        d.getBarang().setAllSatuan(satuan);
                    }
                    return pembelian;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
            try{
                mainApp.closeLoading();
                addSupplierButton.setVisible(false);
                addItemButton.setVisible(false);
                catatanField.setDisable(true);
                saveButton.setVisible(false);

                noPembelianField.setText(pembelian.getNoPembelian());
                tglPembelianField.setText(tglLengkap.format(tglSql.parse(pembelian.getTglPembelian())));
                namaField.setText(pembelian.getSupplier().getNama());
                alamatField.setText(pembelian.getSupplier().getAlamat());
                catatanField.setText(pembelian.getCatatan());

                allPembelianDetail.addAll(pembelian.getAllDetail());
                totalPembelianField.setText(df.format(pembelian.getTotalPembelian()));
                pembayaranField.setText(df.format(pembelian.getPembayaran()));
                sisaPembayaranField.setText(df.format(pembelian.getSisaPembayaran()));
                
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
    private void backToDataPembelian(){
        mainApp.closeDialog(owner, stage);
    }
    @FXML
    private void addBarang(){
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
                mainApp.closeDialog(stage,child);
                PembelianDetail d = null;
                for(PembelianDetail temp : allPembelianDetail){
                    if(temp.getKodeBarang().equals(controller.barang.getKodeBarang())&&
                        temp.getSatuan().equals(controller.satuanCombo.getSelectionModel().getSelectedItem())&&
                        temp.getHarga()==harga)
                            d = temp;
                }
                if(d==null){
                    PembelianDetail detail = new PembelianDetail();
                    detail.setKodeBarang(controller.barang.getKodeBarang());
                    detail.setNamaBarang(controller.barang.getNamaBarang());
                    detail.setSatuan(controller.satuanCombo.getSelectionModel().getSelectedItem());
                    detail.setQty(qty);
                    detail.setHarga(harga);
                    detail.setBarang(controller.barang);
                    allPembelianDetail.add(detail);
                    hitungTotal();
                }else{
                    d.setQty(d.getQty()+qty);
                    pembelianDetailTable.refresh();
                    hitungTotal();
                }
            }
        });
    }
    @FXML
    private void addSupplier(){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddSupplier.fxml");
        AddSupplierController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.supplierTable.setRowFactory(table -> {
            TableRow<Supplier> row = new TableRow<Supplier>() {};
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null){
                        mainApp.closeDialog(stage, child);
                        pembelian.setSupplier(row.getItem());
                        namaField.setText(pembelian.getSupplier().getNama());
                        alamatField.setText(pembelian.getSupplier().getAlamat());
                    }
                }
            });
            return row;
        });
    }
    @FXML
    private void save(){
        if(pembelian.getSupplier()==null){
            mainApp.showMessage(Modality.NONE, "Warning", "Supplier belum dipilih");
        }else if(allPembelianDetail.isEmpty()){
            mainApp.showMessage(Modality.NONE, "Warning", "Bahan baku belum diinput");
        }else{
            MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Save pembelian "+pembelian.getNoPembelian()+" ?");
            controller.OK.setOnAction((ActionEvent e) -> {
                mainApp.closeMessage();
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try (Connection con = Koneksi.getConnection()) {
                            String noPembelian = PembelianHeadDAO.getId(con);
                            pembelian.setNoPembelian(noPembelian);
                            pembelian.setTglPembelian(tglSql.format(new Date()));
                            pembelian.setKodeSupplier(pembelian.getSupplier().getKodeSupplier());
                            pembelian.setTotalPembelian(Double.parseDouble(totalPembelianField.getText().replaceAll(",", "")));
                            pembelian.setPembayaran(Double.parseDouble(pembayaranField.getText().replaceAll(",", "")));
                            pembelian.setSisaPembayaran(Double.parseDouble(sisaPembayaranField.getText().replaceAll(",", "")));
                            pembelian.setCatatan(catatanField.getText());
                            pembelian.setKodeUser(sistem.getUser().getUsername());
                            pembelian.setTglBatal("2000-01-01 00:00:00");
                            pembelian.setUserBatal("");
                            pembelian.setStatus("true");
                            for(PembelianDetail temp : allPembelianDetail){
                                temp.setNoPembelian(noPembelian);
                            }
                            pembelian.setAllDetail(allPembelianDetail);
                            return Service.saveNewPembelian(con, pembelian);
                        }
                    }
                };
                task.setOnRunning((ex) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent ex) -> {
                    try{
                        mainApp.closeLoading();    
                        if(task.get().equals("true")){
                            mainApp.showMessage(Modality.NONE, "Success", "Data pembelian berhasil disimpan");
                            mainApp.showPembelian();
                            mainApp.closeDialog(owner, stage);
                        }else{
                            mainApp.showMessage(Modality.NONE, "Error", task.get());
                        }
                    }catch(Exception exc){
                        mainApp.showMessage(Modality.NONE, "Error", exc.toString());
                    }
                });
                task.setOnFailed((ex) -> {
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                    mainApp.closeLoading();
                });
                new Thread(task).start();
            });
        }
    }
}
