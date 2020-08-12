/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View;

import com.excellentsystem.sentralindahperdana.DAO.CustomerDAO;
import com.excellentsystem.sentralindahperdana.DAO.PekerjaanDAO;
import com.excellentsystem.sentralindahperdana.DAO.PembayaranPiutangDAO;
import com.excellentsystem.sentralindahperdana.DAO.PenjualanDetailDAO;
import com.excellentsystem.sentralindahperdana.DAO.PenjualanHeadDAO;
import com.excellentsystem.sentralindahperdana.DAO.PiutangDAO;
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
import com.excellentsystem.sentralindahperdana.Model.PembayaranPiutang;
import com.excellentsystem.sentralindahperdana.Model.PenjualanDetail;
import com.excellentsystem.sentralindahperdana.Model.PenjualanHead;
import com.excellentsystem.sentralindahperdana.Model.Piutang;
import com.excellentsystem.sentralindahperdana.PrintOut.Report;
import com.excellentsystem.sentralindahperdana.Service.Service;
import com.excellentsystem.sentralindahperdana.View.Dialog.DetailPiutangController;
import com.excellentsystem.sentralindahperdana.View.Dialog.JatuhTempoController;
import com.excellentsystem.sentralindahperdana.View.Dialog.MessageController;
import com.excellentsystem.sentralindahperdana.View.Dialog.NewPembayaranController;
import com.excellentsystem.sentralindahperdana.View.Dialog.NewPenjualanController;
import java.sql.Connection;
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
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
public class PenjualanController {
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
    @FXML private DatePicker tglMulaiPicker;
    @FXML private DatePicker tglAkhirPicker;
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
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPenjualan())));
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
        
        tglMulaiPicker.setConverter(Function.getTglConverter());
        tglMulaiPicker.setValue(LocalDate.now().minusMonths(1));
        tglMulaiPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglAkhirPicker));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglMulaiPicker));
        
        final ContextMenu rm = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            getPenjualan();
        });
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
                        MenuItem detail = new MenuItem("Lihat Detail Penjualan");
                        detail.setOnAction((ActionEvent e)->{
                            lihatDetailPenjualan(item);
                        });
                        MenuItem bayar = new MenuItem("Terima Pembayaran Penjualan");
                        bayar.setOnAction((ActionEvent e)->{
                            showPembayaran(item);
                        });
                        MenuItem detailBayar = new MenuItem("Lihat Detail Terima Pembayaran Penjualan");
                        detailBayar.setOnAction((ActionEvent e)->{
                            showDetailPiutang(item);
                        });
                        MenuItem batalSelesai = new MenuItem("Batal Proyek Selesai");
                        batalSelesai.setOnAction((ActionEvent e)->{
                            batalProyekSelesai(item);
                        });
                        MenuItem invoice = new MenuItem("Print Invoice");
                        invoice.setOnAction((ActionEvent e)->{
                            printInvoice(item);
                        });
                        MenuItem tempo = new MenuItem("Set Jatuh Tempo Penjualan");
                        tempo.setOnAction((ActionEvent e)->{
                            setJatuhTempo(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            getPenjualan();
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Lihat Detail Penjualan")&&o.isStatus())
                                rm.getItems().add(detail);
                            if(o.getJenis().equals("Terima Pembayaran Penjualan")&&o.isStatus()&&item.getSisaPembayaran()>0)
                                rm.getItems().add(bayar);
                            if(o.getJenis().equals("Lihat Detail Terima Pembayaran Penjualan")&&o.isStatus()
                                    &&item.getPembayaran()>0)
                                rm.getItems().add(detailBayar);
                            if(o.getJenis().equals("Batal Proyek Selesai")&&o.isStatus())
                                rm.getItems().add(batalSelesai);
                            if(o.getJenis().equals("Set Jatuh Tempo Penjualan")&&o.isStatus()&&item.getSisaPembayaran()>0)
                                rm.getItems().add(tempo);
                            if(o.getJenis().equals("Print Invoice")&&o.isStatus())
                                rm.getItems().add(invoice);
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
                            if(o.getJenis().equals("Lihat Detail Penjualan")&&o.isStatus())
                                lihatDetailPenjualan(row.getItem());
                        }
                    }
                }
            });
            return row;
        });
        allPenjualan.addListener((ListChangeListener.Change<? extends PenjualanHead> change) -> {
            searchPenjualan();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchPenjualan();
        });
        filterData.addAll(allPenjualan);
        penjualanTable.setItems(filterData);
    }
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getPenjualan();
    }
    @FXML
    private void getPenjualan(){
        Task<List<PenjualanHead>> task = new Task<List<PenjualanHead>>() {
            @Override 
            public List<PenjualanHead> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    List<Customer> allCustomer = CustomerDAO.getAllByStatus(con, "%");
                    List<PenjualanHead> allPenjualan = PenjualanHeadDAO.getAllByTglPenjualan(con, 
                            tglMulaiPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
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
    private void searchPenjualan() {
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
    private void newPenjualan(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPenjualan.fxml");
        NewPenjualanController controller = loader.getController();
        controller.setMainApp(mainApp,mainApp.MainStage, stage);
        controller.setNewPenjualan();
    }
    private void lihatDetailPenjualan(PenjualanHead p){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPenjualan.fxml");
        NewPenjualanController controller = loader.getController();
        controller.setMainApp(mainApp,mainApp.MainStage, stage);
        controller.setDetailPenjualan(p.getNoPenjualan());
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
                            Piutang pt = PiutangDAO.getByKategoriAndKeterangan(con, "Piutang Penjualan",p.getNoPenjualan());
                            PembayaranPiutang pb = new PembayaranPiutang();
                            pb.setNoPembayaran(PembayaranPiutangDAO.getId(con));
                            pb.setTglPembayaran(tglSql.format(new Date()));
                            pb.setNoPiutang(pt.getNoPiutang());
                            pb.setJumlahPembayaran(jumlahBayar);
                            pb.setTipeKeuangan(controller.tipeKeuanganCombo.getSelectionModel().getSelectedItem());
                            pb.setCatatan("");
                            pb.setKodeUser(sistem.getUser().getUsername());
                            pb.setTglBatal("2000-01-01 00:00:00");
                            pb.setUserBatal("");
                            pb.setStatus("true");
                            pb.setPiutang(pt);
                            return Service.savePembayaranPiutang(con, pb);
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
                        mainApp.showMessage(Modality.NONE, "Success", "Pembayaran penjualan berhasil disimpan");
                        getPenjualan();
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
    private void setJatuhTempo(PenjualanHead p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/JatuhTempo.fxml");
        JatuhTempoController controller = loader.getController();
        controller.setMainApp(mainApp,mainApp.MainStage, stage);
        controller.saveButton.setOnAction((event) -> {
            String jatuhTempo = controller.jatuhTempoPicker.getValue().toString();
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try (Connection con = Koneksi.getConnection()) {
                        Piutang piutang = PiutangDAO.getByKategoriAndKeterangan(con, "Piutang Penjualan", p.getNoPenjualan());
                        piutang.setJatuhTempo(jatuhTempo);
                        return Service.saveJatuhTempoPiutang(con, piutang);
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
                    mainApp.showMessage(Modality.NONE, "Success", "Jatuh tempo penjualan berhasil disimpan");
                    getPenjualan();
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
    private void showDetailPiutang(PenjualanHead p){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailPiutang.fxml");
        DetailPiutangController x = loader.getController();
        x.setMainApp(mainApp,mainApp.MainStage, stage);
        x.setDetailPenjualan(p, true);
    }
    private void batalProyekSelesai(PenjualanHead p){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Batal Proyek Selesai "+p.getNoPenjualan()+", anda yakin ?");
        controller.OK.setOnAction((ActionEvent e) -> {
            mainApp.closeMessage();
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try (Connection con = Koneksi.getConnection()) {
                        return Service.batalProyekSelesai(con, p);
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
                        mainApp.showMessage(Modality.NONE, "Success", "Proyek selesai berhasil dibatal");
                        mainApp.showPenjualan();
                    }else{
                        mainApp.showMessage(Modality.NONE, "Warning", task.get());
                    }
                }catch(Exception exx){
                    mainApp.showMessage(Modality.NONE, "Error", exx.toString());
                }
            });
            task.setOnFailed((ex) -> {
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                mainApp.closeLoading();
            });
            new Thread(task).start();
        });
    }
    private void printInvoice(PenjualanHead p){
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
            report.printInvoice(listPenjualan, p.getTotalPenjualan());
        }catch (Exception e){
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
