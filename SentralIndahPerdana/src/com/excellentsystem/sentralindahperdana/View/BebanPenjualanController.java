/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View;

import com.excellentsystem.sentralindahperdana.DAO.BebanPenjualanDAO;
import com.excellentsystem.sentralindahperdana.DAO.CustomerDAO;
import com.excellentsystem.sentralindahperdana.DAO.PenjualanDetailDAO;
import com.excellentsystem.sentralindahperdana.DAO.PenjualanHeadDAO;
import com.excellentsystem.sentralindahperdana.DAO.RencanaAnggaranBebanPenjualanDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import static com.excellentsystem.sentralindahperdana.Main.tglLengkap;
import static com.excellentsystem.sentralindahperdana.Main.tglSql;
import com.excellentsystem.sentralindahperdana.Model.BebanPenjualan;
import com.excellentsystem.sentralindahperdana.Model.Customer;
import com.excellentsystem.sentralindahperdana.Model.Otoritas;
import com.excellentsystem.sentralindahperdana.Model.PenjualanDetail;
import com.excellentsystem.sentralindahperdana.Model.PenjualanHead;
import com.excellentsystem.sentralindahperdana.Model.RencanaAnggaranBebanPenjualan;
import com.excellentsystem.sentralindahperdana.Service.Service;
import com.excellentsystem.sentralindahperdana.View.Dialog.MessageController;
import com.excellentsystem.sentralindahperdana.View.Dialog.NewBebanPenjualanController;
import java.sql.Connection;
import java.text.ParseException;
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
 * @author excellent
 */
public class BebanPenjualanController  {

    @FXML private TableView<BebanPenjualan> bebanPenjualanTable;
    @FXML private TableColumn<BebanPenjualan, String> noBebanPenjualanColumn;
    @FXML private TableColumn<BebanPenjualan, String> tglBebanPenjualanColumn;
    @FXML private TableColumn<BebanPenjualan, String> noPenjualanColumn;
    @FXML private TableColumn<BebanPenjualan, String> namaCustomerColumn;
    @FXML private TableColumn<BebanPenjualan, String> namaProyekColumn;
    @FXML private TableColumn<BebanPenjualan, String> lokasiPengerjaanColumn;
    @FXML private TableColumn<BebanPenjualan, String> namaPekerjaanColumn;
    @FXML private TableColumn<BebanPenjualan, String> kategoriColumn;
    @FXML private TableColumn<BebanPenjualan, String> keteranganColumn;
    @FXML private TableColumn<BebanPenjualan, Number> jumlahRpColumn;
    @FXML private TableColumn<BebanPenjualan, String> tipeKeuanganColumn;
    @FXML private TableColumn<BebanPenjualan, String> kodeUserColumn;
    @FXML private TextField searchField;
    @FXML private DatePicker tglMulaiPicker;
    @FXML private DatePicker tglAkhirPicker;
    @FXML private Label totalBebanPenjualanField;
    private final ObservableList<BebanPenjualan> allBebanPenjualan = FXCollections.observableArrayList();
    private final ObservableList<BebanPenjualan> filterData = FXCollections.observableArrayList();
    private Main mainApp;   
    public void initialize() {
        noBebanPenjualanColumn.setCellValueFactory(cellData -> cellData.getValue().noBebanPenjualanProperty());
        tglBebanPenjualanColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglBebanPenjualan())));
            } catch (ParseException ex) {
                return null;
            }
        });
        noPenjualanColumn.setCellValueFactory(cellData -> cellData.getValue().noPenjualanProperty());
        namaCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getPenjualanDetail().getPenjualanHead().getCustomer().namaProperty());
        namaProyekColumn.setCellValueFactory(cellData -> cellData.getValue().getPenjualanDetail().getPenjualanHead().namaProyekProperty());
        lokasiPengerjaanColumn.setCellValueFactory(cellData -> cellData.getValue().getPenjualanDetail().getPenjualanHead().lokasiPengerjaanProperty());
        namaPekerjaanColumn.setCellValueFactory(cellData -> cellData.getValue().getPenjualanDetail().namaPekerjaanProperty());
        kategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kategoriProperty());
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        jumlahRpColumn.setCellValueFactory(celldata -> celldata.getValue().jumlahRpProperty());
        jumlahRpColumn.setCellFactory(col -> Function.getTableCell());
        tipeKeuanganColumn.setCellValueFactory(cellData ->cellData.getValue().tipeKeuanganProperty());
        kodeUserColumn.setCellValueFactory(cellData ->cellData.getValue().kodeUserProperty());
        
        tglMulaiPicker.setConverter(Function.getTglConverter());
        tglMulaiPicker.setValue(LocalDate.now().minusMonths(1));
        tglMulaiPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglAkhirPicker));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglMulaiPicker));
        
        final ContextMenu rm = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Beban Penjualan");
        addNew.setOnAction((ActionEvent e)->{
            newBebanPenjualan();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            getBebanPenjualan();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Add New Beban Penjualan")&&o.isStatus())
                rm.getItems().add(addNew);
        }
        rm.getItems().addAll(refresh);
        bebanPenjualanTable.setContextMenu(rm);
        bebanPenjualanTable.setRowFactory((TableView<BebanPenjualan> tableView) -> {
            final TableRow<BebanPenjualan> row = new TableRow<BebanPenjualan>(){
                @Override
                public void updateItem(BebanPenjualan item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Beban Penjualan");
                        addNew.setOnAction((ActionEvent e)->{
                            newBebanPenjualan();
                        });
                        MenuItem detail = new MenuItem("Lihat Detail Beban Penjualan");
                        detail.setOnAction((ActionEvent e)->{
                            lihatDetailBebanPenjualan(item);
                        });
                        MenuItem batal = new MenuItem("Batal Beban Penjualan");
                        batal.setOnAction((ActionEvent e)->{
                            batalBebanPenjualan(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            getBebanPenjualan();
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Add New Beban Penjualan")&&o.isStatus())
                                rm.getItems().add(addNew);
                            if(o.getJenis().equals("Lihat Detail Beban Penjualan")&&o.isStatus())
                                rm.getItems().add(detail);
                            if(o.getJenis().equals("Batal Beban Penjualan")&&o.isStatus())
                                rm.getItems().add(batal);
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
                            if(o.getJenis().equals("Lihat Detail Beban Penjualan")&&o.isStatus())
                                lihatDetailBebanPenjualan(row.getItem());
                        }
                    }
                }
            });
            return row;
        });
        allBebanPenjualan.addListener((ListChangeListener.Change<? extends BebanPenjualan> change) -> {
            searchBebanPenjualan();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchBebanPenjualan();
        });
        filterData.addAll(allBebanPenjualan);
        bebanPenjualanTable.setItems(filterData);
    }    
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getBebanPenjualan();
    }
    @FXML
    private void getBebanPenjualan(){
        Task<List<BebanPenjualan>> task = new Task<List<BebanPenjualan>>() {
            @Override 
            public List<BebanPenjualan> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    List<Customer> allCustomer = CustomerDAO.getAllByStatus(con, "%");
                    List<PenjualanHead> listPenjualan = PenjualanHeadDAO.getAll(con);
                    List<PenjualanDetail> listPenjualanDetail = PenjualanDetailDAO.getAll(con);
                    List<BebanPenjualan> listBebanPenjualan = BebanPenjualanDAO.getAllByDateAndStatus(con,
                        tglMulaiPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "true");
                    for(BebanPenjualan b : listBebanPenjualan){
                        for(PenjualanDetail d : listPenjualanDetail){
                            if(b.getNoPenjualan().equals(d.getNoPenjualan())&&
                                    b.getNoUrut().equals(d.getNoUrut()))
                                b.setPenjualanDetail(d);
                        }
                        for(PenjualanHead p : listPenjualan){
                            if(b.getNoPenjualan().equals(p.getNoPenjualan()))
                                b.getPenjualanDetail().setPenjualanHead(p);
                        }
                        for(Customer c : allCustomer){
                            if(b.getPenjualanDetail().getPenjualanHead().getKodeCustomer().equals(c.getKodeCustomer()))
                                b.getPenjualanDetail().getPenjualanHead().setCustomer(c);
                        }
                    }
                    return listBebanPenjualan;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try{
                mainApp.closeLoading();
                allBebanPenjualan.clear();
                allBebanPenjualan.addAll(task.get());
            }catch(Exception ex){
                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
            }
        });
        task.setOnFailed((e) -> {
            task.getException().printStackTrace();
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
    private void searchBebanPenjualan() {
        try{
            filterData.clear();
            for (BebanPenjualan temp : allBebanPenjualan) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(temp);
                else{
                    if(checkColumn(temp.getNoBebanPenjualan())||
                        checkColumn(tglLengkap.format(tglSql.parse(temp.getTglBebanPenjualan())))||
                        checkColumn(temp.getNoPenjualan())||
                        checkColumn(temp.getPenjualanDetail().getPenjualanHead().getCustomer().getNama())||
                        checkColumn(temp.getPenjualanDetail().getPenjualanHead().getNamaProyek())||
                        checkColumn(temp.getPenjualanDetail().getPenjualanHead().getLokasiPengerjaan())||
                        checkColumn(temp.getPenjualanDetail().getNamaPekerjaan())||
                        checkColumn(temp.getKategori())||
                        checkColumn(temp.getKeterangan())||
                        checkColumn(temp.getKategori())||
                        checkColumn(df.format(temp.getJumlahRp()))||
                        checkColumn(temp.getTipeKeuangan())||
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
        for(BebanPenjualan temp : filterData){
            total = total + temp.getJumlahRp();
        }
        totalBebanPenjualanField.setText(df.format(total));
    }
    private void newBebanPenjualan(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewBebanPenjualan.fxml");
        NewBebanPenjualanController controller = loader.getController();
        controller.setMainApp(mainApp,mainApp.MainStage, stage);
        controller.saveButton.setOnAction((ActionEvent event) -> {
            if(controller.penjualanDetail== null){
                mainApp.showMessage(Modality.NONE, "Warning", "Penjualan belum dipilih");
            }else if(controller.kategoriCombo.getSelectionModel().getSelectedItem()==null){
                mainApp.showMessage(Modality.NONE, "Warning", "Kategori belum dipilih");
            }else if(controller.jumlahRpField.getText()==null||"0".equals(controller.jumlahRpField.getText())){
                mainApp.showMessage(Modality.NONE, "Warning", "Jumlah rp masih kosong");
            }else if(controller.tipeKeuanganCombo.getSelectionModel().getSelectedItem()==null){
                mainApp.showMessage(Modality.NONE, "Warning", "Tipe keuangan belum dipilih");
            }else{
                try(Connection con = Koneksi.getConnection()){
                    double bebanTerbayar = 0;
                    List<BebanPenjualan> listBebanPenjualan = BebanPenjualanDAO.getAllByNoPenjualanAndNoUrut(
                            con, controller.penjualanHead.getNoPenjualan(), controller.penjualanDetail.getNoUrut(), "true");
                    for(BebanPenjualan b : listBebanPenjualan){
                        if(b.getKategori().equals(controller.kategoriCombo.getSelectionModel().getSelectedItem()))
                            bebanTerbayar = bebanTerbayar + b.getJumlahRp();
                    }
                    bebanTerbayar = bebanTerbayar + Double.parseDouble(controller.jumlahRpField.getText().replaceAll(",", ""));

                    double anggaran = 0;
                    List<RencanaAnggaranBebanPenjualan> listRabp = RencanaAnggaranBebanPenjualanDAO.getAllByNoPenjualanAndNoUrut(
                            con, controller.penjualanHead.getNoPenjualan(), controller.penjualanDetail.getNoUrut());
                    for(RencanaAnggaranBebanPenjualan r : listRabp){
                        if(r.getKategori().equals(controller.kategoriCombo.getSelectionModel().getSelectedItem()))
                            anggaran = anggaran + r.getJumlahRp();
                    }
                    if(bebanTerbayar>anggaran){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        MessageController msg = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                            "Qty yang dikirim melebihi rencana anggaran belanja, anda yakin ?");
                        msg.OK.setOnAction((ActionEvent e) -> {
                            try(Connection con1 = Koneksi.getConnection()){
                                mainApp.closeMessage();
                                BebanPenjualan beban = new BebanPenjualan();
                                beban.setNoBebanPenjualan(BebanPenjualanDAO.getId(con1));
                                beban.setTglBebanPenjualan(tglSql.format(new Date()));
                                beban.setNoPenjualan(controller.penjualanHead.getNoPenjualan());
                                beban.setNoUrut(controller.penjualanDetail.getNoUrut());
                                beban.setKategori(controller.kategoriCombo.getSelectionModel().getSelectedItem());
                                beban.setKeterangan(controller.keteranganField.getText());
                                beban.setJumlahRp(Double.parseDouble(
                                        controller.jumlahRpField.getText().replaceAll(",", "")));
                                beban.setTipeKeuangan(controller.tipeKeuanganCombo.getSelectionModel().getSelectedItem());
                                beban.setKodeUser(sistem.getUser().getUsername());
                                beban.setStatus("true");
                                beban.setTglBatal("2000-01-01 00:00:00");
                                beban.setUserBatal("");
                                beban.setPenjualanDetail(controller.penjualanDetail);
                                beban.getPenjualanDetail().setPenjualanHead(controller.penjualanHead);
                                String status = Service.saveBebanPenjualan(con1, beban);
                                if(status.equals("true")){
                                    mainApp.showMessage(Modality.NONE, "Success", "Beban penjualan berhasil disimpan");
                                    getBebanPenjualan();
                                }else
                                    mainApp.showMessage(Modality.NONE, "Failed", status);
                            } catch (Exception ex) {
                                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
                            }
                        });
                    }else{
                        BebanPenjualan beban = new BebanPenjualan();
                        beban.setNoBebanPenjualan(BebanPenjualanDAO.getId(con));
                        beban.setTglBebanPenjualan(tglSql.format(new Date()));
                        beban.setNoPenjualan(controller.penjualanHead.getNoPenjualan());
                        beban.setNoUrut(controller.penjualanDetail.getNoUrut());
                        beban.setKategori(controller.kategoriCombo.getSelectionModel().getSelectedItem());
                        beban.setKeterangan(controller.keteranganField.getText());
                        beban.setJumlahRp(Double.parseDouble(
                                controller.jumlahRpField.getText().replaceAll(",", "")));
                        beban.setTipeKeuangan(controller.tipeKeuanganCombo.getSelectionModel().getSelectedItem());
                        beban.setKodeUser(sistem.getUser().getUsername());
                        beban.setStatus("true");
                        beban.setTglBatal("2000-01-01 00:00:00");
                        beban.setUserBatal("");
                        beban.setPenjualanDetail(controller.penjualanDetail);
                        beban.getPenjualanDetail().setPenjualanHead(controller.penjualanHead);
                        String status = Service.saveBebanPenjualan(con, beban);
                        if(status.equals("true")){
                            mainApp.closeDialog(mainApp.MainStage, stage);
                            mainApp.showMessage(Modality.NONE, "Success", "Beban penjualan berhasil disimpan");
                            getBebanPenjualan();
                        }else
                            mainApp.showMessage(Modality.NONE, "Failed", status);
                    }
                } catch (Exception ex) {
                    mainApp.showMessage(Modality.NONE, "Error", ex.toString());
                }
            }
        });
        
    }
    private void lihatDetailBebanPenjualan(BebanPenjualan b){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewBebanPenjualan.fxml");
        NewBebanPenjualanController x = loader.getController();
        x.setMainApp(mainApp,mainApp.MainStage, stage);
        x.setBebanPenjualan(b);
    }
    private void batalBebanPenjualan(BebanPenjualan b){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Batal beban penjualan "+b.getNoBebanPenjualan()+" ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try (Connection con = Koneksi.getConnection()) {
                        if(b.getPenjualanDetail().getPenjualanHead().getStatus().equals("open")){
                            b.setStatus("false");
                            b.setTglBatal(tglSql.format(new Date()));
                            b.setUserBatal(sistem.getUser().getUsername());
                            return Service.batalBebanPenjualan(con, b);
                        }else
                            return "Proyek sudah selesai, tidak dapat dibatalkan";
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getBebanPenjualan();
                String status = task.getValue();
                if(status.equals("true")){
                    mainApp.showMessage(Modality.NONE, "Success", "Beban penjualan berhasil dibatalkan");
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
}
