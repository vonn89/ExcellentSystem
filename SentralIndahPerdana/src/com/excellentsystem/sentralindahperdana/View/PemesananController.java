/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View;

import com.excellentsystem.sentralindahperdana.DAO.CustomerDAO;
import com.excellentsystem.sentralindahperdana.DAO.PekerjaanDAO;
import com.excellentsystem.sentralindahperdana.DAO.PenjualanDetailDAO;
import com.excellentsystem.sentralindahperdana.DAO.PenjualanHeadDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import static com.excellentsystem.sentralindahperdana.Main.tglLengkap;
import static com.excellentsystem.sentralindahperdana.Main.tglSql;
import com.excellentsystem.sentralindahperdana.Model.Customer;
import com.excellentsystem.sentralindahperdana.Model.Otoritas;
import com.excellentsystem.sentralindahperdana.Model.Pekerjaan;
import com.excellentsystem.sentralindahperdana.Model.PenjualanDetail;
import com.excellentsystem.sentralindahperdana.Model.PenjualanHead;
import com.excellentsystem.sentralindahperdana.PrintOut.Report;
import com.excellentsystem.sentralindahperdana.Service.Service;
import com.excellentsystem.sentralindahperdana.View.Dialog.DetailPembayaranDownPaymentController;
import com.excellentsystem.sentralindahperdana.View.Dialog.MessageController;
import com.excellentsystem.sentralindahperdana.View.Dialog.NewPembayaranController;
import com.excellentsystem.sentralindahperdana.View.Dialog.NewPenjualanController;
import com.excellentsystem.sentralindahperdana.View.Dialog.TglMulaiSelesaiController;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class PemesananController {
    @FXML private TableView<PenjualanHead> penjualanTable;
    @FXML private TableColumn<PenjualanHead, String> noPenjualanColumn;
    @FXML private TableColumn<PenjualanHead, String> tglPenjualanColumn;
    @FXML private TableColumn<PenjualanHead, String> kodeCustomerColumn;
    @FXML private TableColumn<PenjualanHead, String> namaCustomerColumn;
    @FXML private TableColumn<PenjualanHead, String> alamatCustomerColumn;
    @FXML private TableColumn<PenjualanHead, String> namaProyekColumn;
    @FXML private TableColumn<PenjualanHead, String> lokasiPengerjaanColumn;
    @FXML private TableColumn<PenjualanHead, Number> totalPenjualanColumn;
    @FXML private TableColumn<PenjualanHead, Number> pembayaranColumn;
    @FXML private TableColumn<PenjualanHead, Number> sisaPembayaranColumn;
    @FXML private TableColumn<PenjualanHead, String> catatanColumn;
    @FXML private TableColumn<PenjualanHead, String> kodeUserColumn;
    
    @FXML private TextField searchField;
    @FXML private Label totalPenjualanField;
    @FXML private Label pembayaranField;
    @FXML private Label sisaPembayaranField;
    private ObservableList<PenjualanHead> allPenjualan = FXCollections.observableArrayList();
    private ObservableList<PenjualanHead> filterData = FXCollections.observableArrayList();
    private Main mainApp;   
    public void initialize() {
        noPenjualanColumn.setCellValueFactory(cellData -> cellData.getValue().noPenjualanProperty());
        kodeCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().kodeCustomerProperty());
        namaCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getCustomer().namaProperty());
        alamatCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getCustomer().alamatProperty());
        namaProyekColumn.setCellValueFactory(cellData -> cellData.getValue().namaProyekProperty());
        lokasiPengerjaanColumn.setCellValueFactory(cellData -> cellData.getValue().lokasiPengerjaanProperty());
        catatanColumn.setCellValueFactory(cellData -> cellData.getValue().catatanProperty());
        kodeUserColumn.setCellValueFactory(cellData ->cellData.getValue().kodeUserProperty());
        tglPenjualanColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPemesanan())));
            } catch (Exception ex) {
                return null;
            }
        });
        totalPenjualanColumn.setCellValueFactory(celldata -> celldata.getValue().totalPenjualanProperty());
        totalPenjualanColumn.setCellFactory(col -> Function.getTableCell());
        pembayaranColumn.setCellValueFactory(celldata -> celldata.getValue().pembayaranProperty());
        pembayaranColumn.setCellFactory(col -> Function.getTableCell());
        sisaPembayaranColumn.setCellValueFactory(celldata -> celldata.getValue().sisaPembayaranProperty());
        sisaPembayaranColumn.setCellFactory(col -> Function.getTableCell());
        
        final ContextMenu rm = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Pemesanan");
        addNew.setOnAction((ActionEvent e)->{
            newPemesanan();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            getPemesanan();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Add New Pemesanan")&&o.isStatus())
                rm.getItems().add(addNew);
        }
        rm.getItems().addAll(refresh);
        penjualanTable.setContextMenu(rm);
        penjualanTable.setRowFactory((TableView<PenjualanHead> tableView) -> {
            final TableRow<PenjualanHead> row = new TableRow<PenjualanHead>(){
                @Override
                public void updateItem(PenjualanHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Pemesanan");
                        addNew.setOnAction((ActionEvent e)->{
                            newPemesanan();
                        });
                        MenuItem detail = new MenuItem("Lihat Detail Pemesanan");
                        detail.setOnAction((ActionEvent e)->{
                            lihatDetailPemesanan(item);
                        });
                        MenuItem batal = new MenuItem("Batal Pemesanan");
                        batal.setOnAction((ActionEvent e)->{
                            batalPemesanan(item);
                        });
                        MenuItem bayar = new MenuItem("Terima Pembayaran Down Payment");
                        bayar.setOnAction((ActionEvent e)->{
                            showPembayaran(item);
                        });
                        MenuItem detailDp = new MenuItem("Lihat Detail Terima Pembayaran Down Payment");
                        detailDp.setOnAction((ActionEvent e)->{
                            showDetailTerimaPembayaranDownPayment(item);
                        });
                        MenuItem selesai = new MenuItem("Proyek Selesai");
                        selesai.setOnAction((ActionEvent e)->{
                            proyekSelesai(item);
                        });
                        MenuItem print = new MenuItem("Print Order Confirmation");
                        print.setOnAction((ActionEvent e)->{
                            printOrderConfirmation(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            getPemesanan();
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Add New Pemesanan")&&o.isStatus())
                                rm.getItems().add(addNew);
                            if(o.getJenis().equals("Lihat Detail Pemesanan")&&o.isStatus())
                                rm.getItems().add(detail);
                            if(o.getJenis().equals("Batal Pemesanan")&&o.isStatus())
                                rm.getItems().add(batal);
                            if(o.getJenis().equals("Terima Pembayaran Down Payment")&&o.isStatus()&&item.getSisaPembayaran()>0)
                                rm.getItems().add(bayar);
                            if(o.getJenis().equals("Lihat Detail Terima Pembayaran Down Payment")&&o.isStatus()
                                    &&item.getPembayaran()>0)
                                rm.getItems().add(detailDp);
                            if(o.getJenis().equals("Proyek Selesai")&&o.isStatus())
                                rm.getItems().add(selesai);
                            if(o.getJenis().equals("Print Order Confirmation")&&o.isStatus())
                                rm.getItems().add(print);
                        }
                        rm.getItems().add(refresh);
                        setContextMenu(rm);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null){
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Lihat Detail Pemesanan")&&o.isStatus())
                                lihatDetailPemesanan(row.getItem());
                        }
                    }
                }
            });
            return row;
        });
        allPenjualan.addListener((ListChangeListener.Change<? extends PenjualanHead> change) -> {
            searchPemesanan();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchPemesanan();
        });
        filterData.addAll(allPenjualan);
        penjualanTable.setItems(filterData);
    }
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getPemesanan();
    }
    @FXML
    private void getPemesanan(){
        Task<List<PenjualanHead>> task = new Task<List<PenjualanHead>>() {
            @Override 
            public List<PenjualanHead> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    List<Customer> allCustomer = CustomerDAO.getAllByStatus(con, "%");
                    List<PenjualanHead> allPenjualan = PenjualanHeadDAO.getAllByStatus(con,"open");
                    for(PenjualanHead p : allPenjualan){
                        for(Customer c: allCustomer){
                            if(p.getKodeCustomer().equals(c.getKodeCustomer()))
                                p.setCustomer(c);
                        }
                    }
                    return allPenjualan;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try{
                mainApp.closeLoading();
                allPenjualan.clear();
                allPenjualan.addAll(task.get());
            }catch(Exception ex){
                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
            }
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }
    private Boolean checkColumn(String column){
        if(column!=null){
            if(column.toLowerCase().contains(searchField.getText().toLowerCase()))
                return true;
        }
        return false;
    }
    private void searchPemesanan() {
        try{
            filterData.clear();
            for (PenjualanHead temp : allPenjualan) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(temp);
                else{
                    if(checkColumn(temp.getNoPenjualan())||
                        checkColumn(tglLengkap.format(tglSql.parse(temp.getTglPenjualan())))||
                        checkColumn(temp.getKodeCustomer())||
                        checkColumn(temp.getCustomer().getNama())||
                        checkColumn(temp.getCustomer().getAlamat())||
                        checkColumn(temp.getNamaProyek())||
                        checkColumn(temp.getLokasiPengerjaan())||
                        checkColumn(df.format(temp.getTotalPenjualan()))||
                        checkColumn(df.format(temp.getPembayaran()))||
                        checkColumn(df.format(temp.getSisaPembayaran()))||
                        checkColumn(temp.getCatatan())||
                        checkColumn(temp.getStatus())||
                        checkColumn(temp.getKodeUser()))
                        filterData.add(temp);
                }
            }
            hitungTotal();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void hitungTotal(){
        double total = 0;
        double pembayaran = 0;
        double sisaPembayaran = 0;
        for(PenjualanHead temp : filterData){
            total = total + temp.getTotalPenjualan();
            pembayaran = pembayaran + temp.getPembayaran();
            sisaPembayaran = sisaPembayaran + temp.getSisaPembayaran();
        }
        totalPenjualanField.setText(df.format(total));
        pembayaranField.setText(df.format(pembayaran));
        sisaPembayaranField.setText(df.format(sisaPembayaran));
    }
    private void newPemesanan(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPenjualan.fxml");
        NewPenjualanController controller = loader.getController();
        controller.setMainApp(mainApp,mainApp.MainStage, stage);
        controller.setNewPenjualan();
    }
    private void lihatDetailPemesanan(PenjualanHead p){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPenjualan.fxml");
        NewPenjualanController controller = loader.getController();
        controller.setMainApp(mainApp,mainApp.MainStage, stage);
        controller.setDetailPenjualan(p.getNoPenjualan());
    }
    private void batalPemesanan(PenjualanHead p){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Batal Pemesanan "+p.getNoPenjualan()+" ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try (Connection con = Koneksi.getConnection()) {
                        if("close".equals(p.getStatus()))
                            return "Pemesanan tidak dapat dibatalkan, karena pengerjaan proyek sudah selesai";
                        else if("false".equals(p.getStatus()))
                            return "Pemesanan tidak dapat dibatalkan, karena sudah dibatalkan";
                        else{
                            p.setTglBatal(tglSql.format(new Date()));
                            p.setUserBatal(sistem.getUser().getUsername());
                            p.setStatus("false");
                            return Service.saveBatalPemesanan(con, p);
                        }
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                String status = task.getValue();
                if(status.equals("true")){
                    mainApp.showMessage(Modality.NONE, "Success", "Pemesanan berhasil dibatalkan");
                    getPemesanan();
                }else
                    mainApp.showMessage(Modality.NONE, "Failed", status);
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        });
    }
    private void showPembayaran(PenjualanHead p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPembayaran.fxml");
        NewPembayaranController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setPembayaranPenjualan(p.getNoPenjualan());
        controller.saveButton.setOnAction((event) -> {
            double jumlahBayar = Double.parseDouble(controller.jumlahPembayaranField.getText().replaceAll(",", ""));
            if(jumlahBayar>p.getSisaPembayaran())
                mainApp.showMessage(Modality.NONE, "Warning", "Jumlah yang dibayar melebihi dari sisa pembayaran");
            else if(controller.tipeKeuanganCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Tipe keuangan belum dipilih");
            else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try (Connection con = Koneksi.getConnection()) {
                            return Service.saveNewTerimaDownPayment(con, p,  
                                Double.parseDouble(controller.jumlahPembayaranField.getText().replaceAll(",", "")),
                                controller.tipeKeuanganCombo.getSelectionModel().getSelectedItem());
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    if(task.getValue().equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Pembayaran down payment berhasil disimpan");
                        getPemesanan();
                    }else
                        mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
                });
                task.setOnFailed((e) -> {
                    mainApp.closeLoading();
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                });
                new Thread(task).start();
            }
        });
    }
    private void showDetailTerimaPembayaranDownPayment(PenjualanHead p){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailPembayaranDownPayment.fxml");
        DetailPembayaranDownPaymentController x = loader.getController();
        x.setMainApp(mainApp,mainApp.MainStage, stage);
        x.setDetailPenjualan(p);
    }
    private void proyekSelesai(PenjualanHead p){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/TglMulaiSelesai.fxml");
        TglMulaiSelesaiController controller = loader.getController();
        controller.setMainApp(mainApp,mainApp.MainStage, stage);
        controller.saveButton.setOnAction((event) -> {
            String tglMulai = controller.tglMulaiPicker.getValue().toString();
            String tglSelesai = controller.tglSelesaiPicker.getValue().toString();
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try (Connection con = Koneksi.getConnection()) {
                        return Service.saveProyekSelesai(con, p, tglMulai, tglSelesai);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                if(task.getValue().equals("true")){
                    mainApp.closeDialog(mainApp.MainStage, stage);
                    mainApp.showMessage(Modality.NONE, "Success", "Proyek selesai berhasil disimpan");
                    getPemesanan();
                }else
                    mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        });
    }
    private void printOrderConfirmation(PenjualanHead p){
        try(Connection con = Koneksi.getConnection()){
            List<Pekerjaan> listPekerjaan = PekerjaanDAO.getAllByStatus(con, "%");
            List<PenjualanDetail> listPenjualan = PenjualanDetailDAO.getAllByNoPenjualan(con, p.getNoPenjualan());
            for(PenjualanDetail d : listPenjualan){
                d.setPenjualanHead(p);
                for(Pekerjaan pk : listPekerjaan){
                    if(d.getKodePekerjaan().equals(pk.getKodePekerjaan()))
                        d.setPekerjaan(pk);
                }
            }
            Report report = new Report();
            report.printProformaInvoice(listPenjualan, p.getTotalPenjualan());
        }catch (Exception e){
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
