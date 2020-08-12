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
import com.excellentsystem.sentralindahperdana.DAO.PenjualanDetailDAO;
import com.excellentsystem.sentralindahperdana.DAO.PenjualanHeadDAO;
import com.excellentsystem.sentralindahperdana.DAO.ReturDetailDAO;
import com.excellentsystem.sentralindahperdana.DAO.ReturHeadDAO;
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
import com.excellentsystem.sentralindahperdana.Model.PenjualanDetail;
import com.excellentsystem.sentralindahperdana.Model.PenjualanHead;
import com.excellentsystem.sentralindahperdana.Model.ReturDetail;
import com.excellentsystem.sentralindahperdana.Model.ReturHead;
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
public class NewReturController  {

    
    @FXML private TableView<ReturDetail> returDetailTable;
    @FXML private TableColumn<ReturDetail, String> kodePekerjaanColumn;
    @FXML private TableColumn<ReturDetail, String> namaPekerjaanColumn;
    @FXML private TableColumn<ReturDetail, String> kodeBarangColumn;
    @FXML private TableColumn<ReturDetail, String> namaBarangColumn;
    @FXML private TableColumn<ReturDetail, String> satuanColumn;
    @FXML private TableColumn<ReturDetail, Number> qtyColumn;
    @FXML private TableColumn removeColumn = new TableColumn<>("");
    
    @FXML private Label noReturField;
    @FXML private Label tglReturField;
    
    @FXML private TextField noPenjualanField;
    @FXML private TextField namaField;
    @FXML private TextField namaProyekField;
    @FXML private TextArea lokasiPengerjaanField;
    
    @FXML private TextField penerimaField;
    @FXML private TextArea catatanField;
    
    @FXML private Button addPenjualanButton;
    @FXML private Button addItemButton;
    @FXML public Button saveButton;
    
    private ReturHead retur;
    private Stage stage;
    private Stage owner;
    private ObservableList<ReturDetail> allReturDetail = FXCollections.observableArrayList();
    private Main mainApp;   
    private Callback<TableColumn, TableCell> getButtonCell(){
        return (final TableColumn p) -> new TableCell<ReturDetail, Boolean>(){
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
                        ReturDetail p = (ReturDetail)getTableView().getItems().get( getIndex() );
                        allReturDetail.remove(p);
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
        returDetailTable.setItems(allReturDetail);
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
    public void setNewRetur(){
        Task<String> task = new Task<String>() {
            @Override 
            public String call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    return ReturHeadDAO.getId(con);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
            try{
                mainApp.closeLoading();
                retur = new ReturHead();
                retur.setNoRetur(task.getValue());
                retur.setTglRetur(tglSql.format(new Date()));
                noReturField.setText(retur.getNoRetur());
                tglReturField.setText(tglLengkap.format(tglSql.parse(retur.getTglRetur())));
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
    public void setDetailRetur(String noRetur){
        Task<ReturHead> task = new Task<ReturHead>() {
            @Override 
            public ReturHead call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    retur = ReturHeadDAO.get(con, noRetur);
                    retur.setPenjualanHead(PenjualanHeadDAO.get(con, retur.getNoPenjualan()));
                    retur.getPenjualanHead().setCustomer(CustomerDAO.get(con, retur.getPenjualanHead().getKodeCustomer()));
                    retur.setAllDetail(ReturDetailDAO.getAllByNoRetur(con, noRetur));
                    List<Pekerjaan> listPekerjaan = PekerjaanDAO.getAllByStatus(con, "%");
                    List<Barang> listBarang = BarangDAO.getAllByStatus(con, "%");
                    List<Satuan> listSatuan = SatuanDAO.getAll(con);
                    List<PenjualanDetail> listPenjualanDetail = PenjualanDetailDAO.getAllByNoPenjualan(con, retur.getNoPenjualan());
                    for(ReturDetail d : retur.getAllDetail()){
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
                    return retur;
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
                penerimaField.setDisable(true);
                
                saveButton.setVisible(false);
                noReturField.setText(retur.getNoRetur());
                tglReturField.setText(tglLengkap.format(tglSql.parse(retur.getTglRetur())));
                noPenjualanField.setText(retur.getNoPenjualan());
                namaField.setText(retur.getPenjualanHead().getCustomer().getNama());
                namaProyekField.setText(retur.getPenjualanHead().getNamaProyek());
                lokasiPengerjaanField.setText(retur.getPenjualanHead().getLokasiPengerjaan());
                penerimaField.setText(retur.getPenerima());
                catatanField.setText(retur.getCatatan());
                allReturDetail.addAll(retur.getAllDetail());
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
        if(retur.getPenjualanHead()==null)
            mainApp.showMessage(Modality.NONE,"Warning", "Penjualan belum dipilih");
        else{
            Stage child = new Stage();
            FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddDetailPenjualan.fxml");
            AddDetailPenjualanController controller = loader.getController();
            controller.setMainApp(mainApp, stage, child);
            controller.searchPenjualanDetail(retur.getPenjualanHead().getNoPenjualan());
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
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddDetailPengiriman.fxml");
        AddDetailPengirimanController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.getBarang(penjualanDetail);
        controller.addButton.setOnAction((event) -> {
            if(controller.pengirimanDetail==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Barang belum dipilih");
            else if(controller.qtyField.getText().equals("0")||controller.qtyField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Qty tidak boleh kosong");
            else{
                try(Connection con = Koneksi.getConnection()){
                    List<PengirimanDetail> listPengiriman = PengirimanDetailDAO.getAllByNoPenjualanAndNoUrutAndStatus(
                            con, penjualanDetail.getNoPenjualan(), penjualanDetail.getNoUrut(), "true");
                    List<ReturDetail> listRetur = ReturDetailDAO.getAllByNoPenjualanAndNoUrutAndStatus(
                            con, penjualanDetail.getNoPenjualan(), penjualanDetail.getNoUrut(), "true");
                    double qtyTerkirim = 0;
                    for(PengirimanDetail d : listPengiriman){
                        if(d.getKodeBarang().equals(controller.pengirimanDetail.getKodeBarang()))
                            qtyTerkirim = qtyTerkirim + d.getQty();
                    }
                    for(ReturDetail d : listRetur){
                        if(d.getKodeBarang().equals(controller.pengirimanDetail.getKodeBarang()))
                            qtyTerkirim = qtyTerkirim - d.getQty();
                    }
                    double qtyInput = Double.parseDouble(controller.qtyField.getText().replaceAll(",", ""));
                    for(Satuan s : controller.pengirimanDetail.getBarang().getAllSatuan()){
                        if(s.getKodeSatuan().equals(controller.satuanField.getText()))
                            qtyInput = qtyInput * s.getQty();
                    }
                    if(qtyTerkirim<qtyInput)
                        mainApp.showMessage(Modality.NONE, "Warning", "Qty yang diretur melebihi qty yang dikirim");
                    else{
                        mainApp.closeDialog(stage,child);
                        ReturDetail detail = new ReturDetail();
                        detail.setNoUrutPenjualan(penjualanDetail.getNoUrut());
                        detail.setKodeBarang(controller.pengirimanDetail.getKodeBarang());
                        detail.setNamaBarang(controller.pengirimanDetail.getNamaBarang());
                        detail.setQty(qtyInput);
                        detail.setSatuan(controller.satuanField.getText());
                        detail.setNilai(controller.pengirimanDetail.getNilai());
                        detail.setBarang(controller.pengirimanDetail.getBarang());
                        detail.setPenjualanDetail(penjualanDetail);
                        allReturDetail.add(detail);
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
                            retur.setPenjualanHead(row.getItem());
                            noPenjualanField.setText(retur.getPenjualanHead().getNoPenjualan());
                            namaField.setText(retur.getPenjualanHead().getCustomer().getNama());
                            namaProyekField.setText(retur.getPenjualanHead().getNamaProyek());
                            lokasiPengerjaanField.setText(retur.getPenjualanHead().getLokasiPengerjaan());
                            allReturDetail.clear();
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
        if(retur.getPenjualanHead()==null)
            mainApp.showMessage(Modality.NONE, "Warning", "Penjualan belum dipilih");
        else if(allReturDetail.isEmpty())
            mainApp.showMessage(Modality.NONE, "Warning", "Barang yang akan diretur masih kosong");
        else{
            MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Save retur "+retur.getNoRetur()+" ?");
            controller.OK.setOnAction((ActionEvent e) -> {
                mainApp.closeMessage();
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try (Connection con = Koneksi.getConnection()) {
                            String noRetur = ReturHeadDAO.getId(con);
                            retur.setNoRetur(noRetur);
                            retur.setTglRetur(tglSql.format(new Date()));
                            retur.setNoPenjualan(retur.getPenjualanHead().getNoPenjualan());
                            retur.setPenerima(penerimaField.getText());
                            retur.setCatatan(catatanField.getText());
                            retur.setKodeUser(sistem.getUser().getUsername());
                            retur.setTglBatal("2000-01-01 00:00:00");
                            retur.setUserBatal("");
                            retur.setStatus("true");
                            for(ReturDetail temp : allReturDetail){
                                temp.setNoRetur(noRetur);
                            }
                            retur.setAllDetail(allReturDetail);
                            return Service.saveNewRetur(con, retur);
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
                            mainApp.showMessage(Modality.NONE, "Success", "Data retur barang berhasil disimpan");
                            mainApp.showRetur();
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
