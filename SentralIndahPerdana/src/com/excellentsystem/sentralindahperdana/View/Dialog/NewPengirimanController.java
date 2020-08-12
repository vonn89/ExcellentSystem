/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View.Dialog;

import com.excellentsystem.sentralindahperdana.DAO.BarangDAO;
import com.excellentsystem.sentralindahperdana.DAO.CustomerDAO;
import com.excellentsystem.sentralindahperdana.DAO.PekerjaanDAO;
import com.excellentsystem.sentralindahperdana.DAO.PengirimanDetailDAO;
import com.excellentsystem.sentralindahperdana.DAO.PengirimanHeadDAO;
import com.excellentsystem.sentralindahperdana.DAO.PenjualanDetailDAO;
import com.excellentsystem.sentralindahperdana.DAO.PenjualanHeadDAO;
import com.excellentsystem.sentralindahperdana.DAO.RencanaAnggaranBebanMaterialDAO;
import com.excellentsystem.sentralindahperdana.DAO.ReturDetailDAO;
import com.excellentsystem.sentralindahperdana.DAO.SatuanDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import static com.excellentsystem.sentralindahperdana.Main.tglLengkap;
import static com.excellentsystem.sentralindahperdana.Main.tglSql;
import com.excellentsystem.sentralindahperdana.Model.Barang;
import com.excellentsystem.sentralindahperdana.Model.Pekerjaan;
import com.excellentsystem.sentralindahperdana.Model.PengirimanDetail;
import com.excellentsystem.sentralindahperdana.Model.PengirimanHead;
import com.excellentsystem.sentralindahperdana.Model.PenjualanDetail;
import com.excellentsystem.sentralindahperdana.Model.PenjualanHead;
import com.excellentsystem.sentralindahperdana.Model.RencanaAnggaranBebanMaterial;
import com.excellentsystem.sentralindahperdana.Model.ReturDetail;
import com.excellentsystem.sentralindahperdana.Model.Satuan;
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
public class NewPengirimanController  {

    
    @FXML private TableView<PengirimanDetail> pengirimanDetailTable;
    @FXML private TableColumn<PengirimanDetail, String> kodePekerjaanColumn;
    @FXML private TableColumn<PengirimanDetail, String> namaPekerjaanColumn;
    @FXML private TableColumn<PengirimanDetail, String> keteranganColumn;
    @FXML private TableColumn<PengirimanDetail, String> kodeBarangColumn;
    @FXML private TableColumn<PengirimanDetail, String> namaBarangColumn;
    @FXML private TableColumn<PengirimanDetail, String> satuanColumn;
    @FXML private TableColumn<PengirimanDetail, Number> qtyColumn;
    @FXML private TableColumn removeColumn = new TableColumn<>("");
    
    @FXML private Label noPengirimanField;
    @FXML private Label tglPengirimanField;
    
    @FXML private TextField noPenjualanField;
    @FXML private TextField namaField;
    @FXML private TextField namaProyekField;
    @FXML private TextArea lokasiPengerjaanField;
    
    @FXML private TextField jenisKendaraanField;
    @FXML private TextField noPolisiField;
    @FXML private TextField supirField;
    
    @FXML private TextArea catatanField;
    
    @FXML private Button addPenjualanButton;
    @FXML private Button addItemButton;
    @FXML public Button saveButton;
    
    private PengirimanHead pengiriman;
    private Stage stage;
    private Stage owner;
    private ObservableList<PengirimanDetail> allPengirimanDetail = FXCollections.observableArrayList();
    private Main mainApp;   
    private Callback<TableColumn, TableCell> getButtonCell(){
        return (final TableColumn p) -> new TableCell<PengirimanDetail, Boolean>(){
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
                        PengirimanDetail p = (PengirimanDetail)getTableView().getItems().get( getIndex() );
                        allPengirimanDetail.remove(p);
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
        kodePekerjaanColumn.setCellValueFactory(cellData -> cellData.getValue().getPenjualanDetail().kodePekerjaanProperty());
        namaPekerjaanColumn.setCellValueFactory(cellData -> cellData.getValue().getPenjualanDetail().namaPekerjaanProperty());
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().getPenjualanDetail().keteranganProperty());
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
        
        removeColumn.setCellValueFactory( new PropertyValueFactory<>(""));
        removeColumn.setCellFactory(getButtonCell());
        pengirimanDetailTable.setItems(allPengirimanDetail);
    }
    public void setMainApp(Main mainApp,Stage owner, Stage stage) {
        try{
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
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    public void setNewPengiriman(){
        Task<String> task = new Task<String>() {
            @Override 
            public String call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    return PengirimanHeadDAO.getId(con);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
            try{
                mainApp.closeLoading();
                pengiriman = new PengirimanHead();
                pengiriman.setNoPengiriman(task.getValue());
                pengiriman.setTglPengiriman(tglSql.format(new Date()));
                noPengirimanField.setText(pengiriman.getNoPengiriman());
                tglPengirimanField.setText(tglLengkap.format(tglSql.parse(pengiriman.getTglPengiriman())));
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
    public void setDetailPengiriman(String noPengiriman){
        Task<PengirimanHead> task = new Task<PengirimanHead>() {
            @Override 
            public PengirimanHead call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    pengiriman = PengirimanHeadDAO.get(con, noPengiriman);
                    pengiriman.setPenjualanHead(PenjualanHeadDAO.get(con, pengiriman.getNoPenjualan()));
                    pengiriman.getPenjualanHead().setCustomer(CustomerDAO.get(con, pengiriman.getPenjualanHead().getKodeCustomer()));
                    pengiriman.setAllDetail(PengirimanDetailDAO.getAllByNoPengiriman(con, noPengiriman));
                    List<Pekerjaan> listPekerjaan = PekerjaanDAO.getAllByStatus(con, "%");
                    List<Barang> listBarang = BarangDAO.getAllByStatus(con, "%");
                    List<Satuan> listSatuan = SatuanDAO.getAll(con);
                    List<PenjualanDetail> listPenjualanDetail = PenjualanDetailDAO.getAllByNoPenjualan(con, pengiriman.getNoPenjualan());
                    for(PengirimanDetail d : pengiriman.getAllDetail()){
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
                        for(PenjualanDetail penjualanDetail : listPenjualanDetail){
                            if(d.getNoUrutPenjualan().equals(penjualanDetail.getNoUrut()))
                                d.setPenjualanDetail(penjualanDetail);
                        }
                        for(Pekerjaan b : listPekerjaan){
                            if(d.getPenjualanDetail().getKodePekerjaan().equals(b.getKodePekerjaan()))
                                d.getPenjualanDetail().setPekerjaan(b);
                        }
                    }
                    return pengiriman;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
            try{
                mainApp.closeLoading();
                addPenjualanButton.setVisible(false);
                addItemButton.setVisible(false);
                catatanField.setDisable(true);
                namaProyekField.setDisable(true);
                lokasiPengerjaanField.setDisable(true);
                jenisKendaraanField.setDisable(true);
                noPolisiField.setDisable(true);
                supirField.setDisable(true);
                
                saveButton.setVisible(false);
                noPengirimanField.setText(pengiriman.getNoPengiriman());
                tglPengirimanField.setText(tglLengkap.format(tglSql.parse(pengiriman.getTglPengiriman())));
                noPenjualanField.setText(pengiriman.getNoPenjualan());
                namaField.setText(pengiriman.getPenjualanHead().getCustomer().getNama());
                namaProyekField.setText(pengiriman.getPenjualanHead().getNamaProyek());
                lokasiPengerjaanField.setText(pengiriman.getPenjualanHead().getLokasiPengerjaan());
                jenisKendaraanField.setText(pengiriman.getJenisKendaraan());
                noPolisiField.setText(pengiriman.getNoPolisi());
                supirField.setText(pengiriman.getSupir());
                catatanField.setText(pengiriman.getCatatan());
                allPengirimanDetail.addAll(pengiriman.getAllDetail());
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
    private void addPenjualanDetail(){
        if(pengiriman.getPenjualanHead()==null)
            mainApp.showMessage(Modality.NONE,"Warning", "Penjualan belum dipilih");
        else{
            Stage child = new Stage();
            FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddDetailPenjualan.fxml");
            AddDetailPenjualanController controller = loader.getController();
            controller.setMainApp(mainApp, stage, child);
            controller.searchPenjualanDetail(pengiriman.getPenjualanHead().getNoPenjualan());
            controller.penjualanDetailTable.setRowFactory(table -> {
                TableRow<PenjualanDetail> row = new TableRow<PenjualanDetail>() {};
                row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&
                            mouseEvent.getClickCount() == 2){
                        if(row.getItem()!=null){
                            mainApp.closeDialog(stage, child);
                            addBarang(row.getItem());
                        }
                    }
                });
                return row;
            });
        }
    }
    private void addBarang(PenjualanDetail penjualanDetail){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddBarangPengiriman.fxml");
        AddBarangPengirimanController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.addButton.setOnAction((event) -> {
            if(controller.barang==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Barang belum dipilih");
            else if(controller.qtyField.getText().equals("0")||controller.qtyField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Qty tidak boleh kosong");
            else if(controller.satuanCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Satuan belum dipilih");
            else{
                try(Connection con = Koneksi.getConnection()){
                    List<PengirimanDetail> listPengiriman = PengirimanDetailDAO.getAllByNoPenjualanAndNoUrutAndStatus(
                            con, penjualanDetail.getNoPenjualan(), penjualanDetail.getNoUrut(), "true");
                    List<ReturDetail> listRetur = ReturDetailDAO.getAllByNoPenjualanAndNoUrutAndStatus(
                            con, penjualanDetail.getNoPenjualan(), penjualanDetail.getNoUrut(), "true");
                    double qtyTerkirim = 0;
                    for(PengirimanDetail d : listPengiriman){
                        if(d.getKodeBarang().equals(controller.barang.getKodeBarang()))
                            qtyTerkirim = qtyTerkirim + d.getQty();
                    }
                    for(ReturDetail d : listRetur){
                        if(d.getKodeBarang().equals(controller.barang.getKodeBarang()))
                            qtyTerkirim = qtyTerkirim - d.getQty();
                    }
                    for(PengirimanDetail d : allPengirimanDetail){
                        if(d.getNoUrutPenjualan().equals(penjualanDetail.getNoUrut())&&
                                d.getKodeBarang().equals(controller.barang.getKodeBarang()))
                            qtyTerkirim = qtyTerkirim + d.getQty();
                    }
                    double qtyInput = Double.parseDouble(controller.qtyField.getText().replaceAll(",", ""));
                    for(Satuan s : controller.barang.getAllSatuan()){
                        if(s.getKodeSatuan().equals(controller.satuanCombo.getSelectionModel().getSelectedItem()))
                            qtyInput = qtyInput * s.getQty();
                    }
                    qtyTerkirim = qtyTerkirim + qtyInput;
                    double qtyAnggaran = 0;
                    List<RencanaAnggaranBebanMaterial> rab = RencanaAnggaranBebanMaterialDAO.getAllByNoPenjualanAndNoUrut(
                            con, penjualanDetail.getNoPenjualan(), penjualanDetail.getNoUrut());
                    for(RencanaAnggaranBebanMaterial r : rab){
                        if(r.getKodeBarang().equals(controller.barang.getKodeBarang()))
                            qtyAnggaran = qtyAnggaran + r.getQty();
                    }
                    if(qtyTerkirim>qtyAnggaran){
                        MessageController msg = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                            "Qty yang dikirim melebihi rencana anggaran belanja, anda yakin ?");
                        msg.OK.setOnAction((ActionEvent e) -> {
                            mainApp.closeMessage();
                            mainApp.closeDialog(stage,child);
                            
                            double x = Double.parseDouble(controller.qtyField.getText().replaceAll(",", ""));
                            for(Satuan s : controller.barang.getAllSatuan()){
                                if(s.getKodeSatuan().equals(controller.satuanCombo.getSelectionModel().getSelectedItem()))
                                    x = x * s.getQty();
                            }
                            PengirimanDetail detail = new PengirimanDetail();
                            detail.setNoUrutPenjualan(penjualanDetail.getNoUrut());
                            detail.setKodeBarang(controller.barang.getKodeBarang());
                            detail.setNamaBarang(controller.barang.getNamaBarang());
                            detail.setQty(x);
                            detail.setSatuan(controller.satuanCombo.getSelectionModel().getSelectedItem());
                            detail.setNilai(0);
                            detail.setBarang(controller.barang);
                            detail.setPenjualanDetail(penjualanDetail);
                            allPengirimanDetail.add(detail);
                        });
                    }else{
                        mainApp.closeDialog(stage,child);
                        PengirimanDetail detail = new PengirimanDetail();
                        detail.setNoUrutPenjualan(penjualanDetail.getNoUrut());
                        detail.setKodeBarang(controller.barang.getKodeBarang());
                        detail.setNamaBarang(controller.barang.getNamaBarang());
                        detail.setQty(qtyInput);
                        detail.setSatuan(controller.satuanCombo.getSelectionModel().getSelectedItem());
                        detail.setNilai(0);
                        detail.setBarang(controller.barang);
                        detail.setPenjualanDetail(penjualanDetail);
                        allPengirimanDetail.add(detail);
                    }
                }catch(Exception e){
                    mainApp.showMessage(Modality.NONE, "Error", e.toString());
                }
            }
        });
    }
    @FXML
    private void addPenjualan(){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddPemesanan.fxml");
        AddPemesananController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.penjualanHeadTable.setRowFactory(table -> {
            TableRow<PenjualanHead> row = new TableRow<PenjualanHead>() {};
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&
                        mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null){
                        try{
                            mainApp.closeDialog(stage, child);
                            pengiriman.setPenjualanHead(row.getItem());
                            noPenjualanField.setText(pengiriman.getPenjualanHead().getNoPenjualan());
                            namaField.setText(pengiriman.getPenjualanHead().getCustomer().getNama());
                            namaProyekField.setText(pengiriman.getPenjualanHead().getNamaProyek());
                            lokasiPengerjaanField.setText(pengiriman.getPenjualanHead().getLokasiPengerjaan());
                            allPengirimanDetail.clear();
                        }catch(Exception e){
                            mainApp.showMessage(Modality.NONE, "Error", e.toString());
                        }
                    }
                }
            });
            return row;
        });
    }
    @FXML
    private void save(){
        if(pengiriman.getPenjualanHead()==null)
            mainApp.showMessage(Modality.NONE, "Warning", "Penjualan belum dipilih");
        else if(allPengirimanDetail.isEmpty())
            mainApp.showMessage(Modality.NONE, "Warning", "Barang yang akan dikirim masih kosong");
        else{
            MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Save pengiriman "+pengiriman.getNoPengiriman()+" ?");
            controller.OK.setOnAction((ActionEvent e) -> {
                mainApp.closeMessage();
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try (Connection con = Koneksi.getConnection()) {
                            String noPengiriman = PengirimanHeadDAO.getId(con);
                            pengiriman.setNoPengiriman(noPengiriman);
                            pengiriman.setTglPengiriman(tglSql.format(new Date()));
                            pengiriman.setNoPenjualan(pengiriman.getPenjualanHead().getNoPenjualan());
                            pengiriman.setJenisKendaraan(jenisKendaraanField.getText());
                            pengiriman.setNoPolisi(noPolisiField.getText());
                            pengiriman.setSupir(supirField.getText());
                            pengiriman.setCatatan(catatanField.getText());
                            pengiriman.setKodeUser(sistem.getUser().getUsername());
                            pengiriman.setTglBatal("2000-01-01 00:00:00");
                            pengiriman.setUserBatal("");
                            pengiriman.setStatus("true");
                            for(PengirimanDetail temp : allPengirimanDetail){
                                temp.setNoPengiriman(noPengiriman);
                            }
                            pengiriman.setAllDetail(allPengirimanDetail);
                            return Service.saveNewPengiriman(con, pengiriman);
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
                            mainApp.closeDialog(owner, stage);
                            mainApp.showMessage(Modality.NONE, "Success", "Data pengiriman barang berhasil disimpan");
                            mainApp.showPengirimanBarang();
                        }else{
                            mainApp.showMessage(Modality.NONE, "Error", task.get());
                        }
                    }catch(Exception exc){
                        mainApp.showMessage(Modality.NONE, "Error", exc.toString());
                    }
                });
                task.setOnFailed((ex) -> {
                    task.getException().printStackTrace();
                    mainApp.closeLoading();
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                });
                new Thread(task).start();
            });
        }
    }
}
