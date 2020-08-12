/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.sentralindahperdana.View.Dialog;

import com.excellentsystem.sentralindahperdana.DAO.BarangDAO;
import com.excellentsystem.sentralindahperdana.DAO.ReturPembelianDetailDAO;
import com.excellentsystem.sentralindahperdana.DAO.ReturPembelianHeadDAO;
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
import com.excellentsystem.sentralindahperdana.Model.ReturPembelianDetail;
import com.excellentsystem.sentralindahperdana.Model.ReturPembelianHead;
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
import javafx.scene.control.ComboBox;
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
public class NewReturPembelianController {

    @FXML private TableView<ReturPembelianDetail> returPembelianDetailTable;
    @FXML private TableColumn<ReturPembelianDetail, String> kodeBarangColumn;
    @FXML private TableColumn<ReturPembelianDetail, String> namaBarangColumn;
    @FXML private TableColumn<ReturPembelianDetail, String> satuanColumn;
    @FXML private TableColumn<ReturPembelianDetail, Number> qtyColumn;
    @FXML private TableColumn<ReturPembelianDetail, Number> hargaColumn;
    @FXML private TableColumn<ReturPembelianDetail, Number> totalColumn;
    @FXML private TableColumn removeColumn = new TableColumn<>("");
    
    @FXML private TextField namaField;
    @FXML private TextArea alamatField;
    
    @FXML private TextArea catatanField;
    
    @FXML private Label noReturPembelianField;
    @FXML private Label tglReturField;
    
    @FXML private TextField totalReturPembelianField;
    
    @FXML private Label tipeKeuanganLabel;
    @FXML private ComboBox<String> tipeKeuanganCombo;
    
    @FXML private Button addSupplierButton;
    @FXML private Button addItemButton;
    @FXML private Button saveButton;
    
    private ReturPembelianHead returPembelian;
    private ObservableList<ReturPembelianDetail> allReturPembelianDetail = FXCollections.observableArrayList();
    private Main mainApp;  
    private Stage stage;
    private Stage owner; 
    private Callback<TableColumn, TableCell> getButtonCell(){
        return (final TableColumn p) -> new TableCell<ReturPembelianDetail, Boolean>(){
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
                        ReturPembelianDetail beban = (ReturPembelianDetail)getTableView().getItems().get( getIndex() );
                        allReturPembelianDetail.remove(beban);
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
        hargaColumn.setCellValueFactory(cellData -> {
            double harga = cellData.getValue().getHarga();
            for (Satuan temp : cellData.getValue().getBarang().getAllSatuan()) {
                if (temp.getKodeSatuan().equals(cellData.getValue().getSatuan())) 
                    harga = harga * temp.getQty();
            }
            return new SimpleDoubleProperty(harga);
        });
        hargaColumn.setCellFactory((c) -> Function.getTableCell());
        totalColumn.setCellValueFactory(cellData -> {
            return new SimpleDoubleProperty(cellData.getValue().getHarga()*cellData.getValue().getQty());
        });
        totalColumn.setCellFactory(col -> Function.getTableCell());
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
        returPembelianDetailTable.setItems(allReturPembelianDetail);
    }
    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        try{
            this.mainApp = mainApp;
            this.owner = owner;
            this.stage = stage;
            stage.setOnCloseRequest((event) -> {
                mainApp.closeDialog(owner, stage);
            });
            tipeKeuanganCombo.setItems(Function.getTipeKeuanganByUser());
            stage.setHeight(mainApp.screenSize.getHeight()-80);
            stage.setWidth(mainApp.screenSize.getWidth()-80);
            stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
            stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    @FXML
    private void hitungTotal(){
        double totalRetur = 0;
        for(ReturPembelianDetail temp : allReturPembelianDetail){
            totalRetur = totalRetur + temp.getQty()*temp.getHarga();
        }
        totalReturPembelianField.setText(df.format(totalRetur));
    }
    public void setNewReturPembelian(){
        Task<String> task = new Task<String>() {
            @Override 
            public String call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    return ReturPembelianHeadDAO.getId(con);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
            try{
                mainApp.closeLoading();
                returPembelian = new ReturPembelianHead();
                returPembelian.setNoRetur(task.get());
                returPembelian.setTglRetur(tglSql.format(new Date()));
                noReturPembelianField.setText(returPembelian.getNoRetur());
                tglReturField.setText(tglLengkap.format(tglSql.parse(returPembelian.getTglRetur())));
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
    public void setDetailReturPembelian(String noRetur){
        Task<ReturPembelianHead> task = new Task<ReturPembelianHead>() {
            @Override 
            public ReturPembelianHead call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    returPembelian = ReturPembelianHeadDAO.get(con, noRetur);
                    returPembelian.setSupplier(SupplierDAO.get(con, returPembelian.getKodeSupplier()));
                    returPembelian.setAllDetail(ReturPembelianDetailDAO.getAllByNoRetur(con, noRetur));
                    List<Barang> listBarang = BarangDAO.getAllByStatus(con, "%");
                    List<Satuan> listSatuan = SatuanDAO.getAll(con);
                    for(ReturPembelianDetail d : returPembelian.getAllDetail()){
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
                    return returPembelian;
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
                tipeKeuanganCombo.setVisible(false);
                tipeKeuanganLabel.setVisible(false);

                returPembelian = task.get();
                noReturPembelianField.setText(returPembelian.getNoRetur());
                tglReturField.setText(tglLengkap.format(tglSql.parse(returPembelian.getTglRetur())));
                catatanField.setText(returPembelian.getCatatan());
                totalReturPembelianField.setText(df.format(returPembelian.getTotalRetur()));
                tipeKeuanganCombo.getSelectionModel().select(returPembelian.getTipeKeuangan());
                namaField.setText(returPembelian.getSupplier().getNama());
                alamatField.setText(returPembelian.getSupplier().getAlamat());
                allReturPembelianDetail.addAll(returPembelian.getAllDetail());
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
    private void addBarang(){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddBarang.fxml");
        AddBarangController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.addButton.setOnAction((ActionEvent event) -> {
            if(controller.barang==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Barang belum dipilih");
            else if(controller.qtyField.getText().equals("0")||controller.qtyField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Qty masih kosong");
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
                ReturPembelianDetail d = null;
                for(ReturPembelianDetail temp : allReturPembelianDetail){
                    if(temp.getKodeBarang().equals(controller.barang.getKodeBarang())&&
                        temp.getSatuan().equals(controller.satuanCombo.getSelectionModel().getSelectedItem())&&
                        temp.getHarga()==harga)
                            d = temp;
                }
                if(d==null){
                    ReturPembelianDetail detail = new ReturPembelianDetail();
                    detail.setKodeBarang(controller.barang.getKodeBarang());
                    detail.setNamaBarang(controller.barang.getNamaBarang());
                    detail.setSatuan(controller.satuanCombo.getSelectionModel().getSelectedItem());
                    detail.setQty(qty);
                    detail.setHarga(harga);
                    detail.setBarang(controller.barang);
                    allReturPembelianDetail.add(detail);
                    hitungTotal();
                }else{
                    d.setQty(d.getQty()+qty);
                    returPembelianDetailTable.refresh();
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
                        returPembelian.setSupplier(row.getItem());
                        namaField.setText(returPembelian.getSupplier().getNama());
                        alamatField.setText(returPembelian.getSupplier().getAlamat());
                    }
                }
            });
            return row;
        });
    }
    @FXML
    private void backToReturPembelian(){
        mainApp.closeDialog(owner, stage);
    }
    @FXML
    private void save(){
        if(returPembelian.getSupplier()==null){
            mainApp.showMessage(Modality.NONE, "Warning", "Supplier belum dipilih");
        }else if(allReturPembelianDetail.isEmpty()){
            mainApp.showMessage(Modality.NONE, "Warning", "Barang tidak ada");
        }else if(tipeKeuanganCombo.getSelectionModel().getSelectedItem()==null){
            mainApp.showMessage(Modality.NONE, "Warning", "Tipe keuangan belum dipilih");
        }else{
            MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Save retur pembelian "+returPembelian.getNoRetur()+" ?");
            controller.OK.setOnAction((ActionEvent e) -> {
                mainApp.closeMessage();
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try (Connection con = Koneksi.getConnection()) {
                            String noReturPembelian = ReturPembelianHeadDAO.getId(con);
                            returPembelian.setNoRetur(noReturPembelian);
                            returPembelian.setTglRetur(tglSql.format(new Date()));
                            returPembelian.setKodeSupplier(returPembelian.getSupplier().getKodeSupplier());
                            returPembelian.setTotalRetur(Double.parseDouble(totalReturPembelianField.getText().replaceAll(",", "")));
                            returPembelian.setTipeKeuangan(tipeKeuanganCombo.getSelectionModel().getSelectedItem());
                            returPembelian.setCatatan(catatanField.getText());
                            returPembelian.setKodeUser(sistem.getUser().getUsername());
                            returPembelian.setTglBatal("2000-01-01 00:00:00");
                            returPembelian.setUserBatal("");
                            returPembelian.setStatus("true");
                            for(ReturPembelianDetail temp : allReturPembelianDetail){
                                temp.setNoRetur(noReturPembelian);
                            }
                            returPembelian.setAllDetail(allReturPembelianDetail);
                            return Service.saveNewReturPembelian(con, returPembelian);
                        }
                    }
                };
                task.setOnRunning((ec) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent ex) -> {
                    try{
                        mainApp.closeLoading();
                        if(task.get().equals("true")){
                            mainApp.closeDialog(owner, stage);
                            mainApp.showMessage(Modality.NONE, "Success", "Data retur pembelian berhasil disimpan");
                            mainApp.showReturPembelian();
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
