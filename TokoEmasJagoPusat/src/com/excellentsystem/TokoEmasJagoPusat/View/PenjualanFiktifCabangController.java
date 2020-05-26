/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View;

import com.excellentsystem.TokoEmasJagoPusat.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.PenjualanFiktifDetailDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.PenjualanFiktifHeadDAO;
import com.excellentsystem.TokoEmasJagoPusat.Function;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSql;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSystem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.timeout;
import com.excellentsystem.TokoEmasJagoPusat.Model.BarangFiktif;
import com.excellentsystem.TokoEmasJagoPusat.Model.Cabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.Helper.Day;
import com.excellentsystem.TokoEmasJagoPusat.Model.KategoriFiktif;
import com.excellentsystem.TokoEmasJagoPusat.Model.PelangganFiktif;
import com.excellentsystem.TokoEmasJagoPusat.Model.PenjualanFiktifDetail;
import com.excellentsystem.TokoEmasJagoPusat.Model.PenjualanFiktifHead;
import com.excellentsystem.TokoEmasJagoPusat.PrintOut.PrintOut;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.MessageController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.PenjualanFiktifCabang.AddNewPenjualanFiktifCabangController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.PenjualanFiktifCabang.DetailPenjualanFiktifCabangController;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class PenjualanFiktifCabangController  {

    @FXML private TableView<PenjualanFiktifHead> penjualanTable;
    @FXML private TableColumn<PenjualanFiktifHead, Boolean> statusColumn;
    @FXML private TableColumn<PenjualanFiktifHead, String> kodeCabangColumn;
    @FXML private TableColumn<PenjualanFiktifHead, String> noPenjualanColumn;
    @FXML private TableColumn<PenjualanFiktifHead, String> tglPenjualanColumn;
    @FXML private TableColumn<PenjualanFiktifHead, String> namaPelangganColumn;
    @FXML private TableColumn<PenjualanFiktifHead, String> alamatPelangganColumn;
    @FXML private TableColumn<PenjualanFiktifHead, String> noTelpPelangganColumn;
    @FXML private TableColumn<PenjualanFiktifHead, Number> grandtotalColumn;
    
    @FXML private TextField searchField;
    @FXML private Label totalPenjualanField;
    @FXML private Label totalNotaField;
    @FXML private DatePicker tglMulaiPicker;
    @FXML private DatePicker tglAkhirPicker;
    @FXML private ComboBox<String> cabangCombo;
    @FXML private CheckBox checkAll;
    
    private ObservableList<String> allCabang = FXCollections.observableArrayList();
    private ObservableList<PenjualanFiktifHead> allPenjualan = FXCollections.observableArrayList();
    private ObservableList<PenjualanFiktifHead> filterData = FXCollections.observableArrayList();
    private Main mainApp;   
    public void initialize() {
        kodeCabangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeCabangProperty());
        noPenjualanColumn.setCellValueFactory(cellData -> cellData.getValue().noPenjualanProperty());
        tglPenjualanColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPenjualan())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglPenjualanColumn.setComparator(Function.sortDate(tglLengkap));
        namaPelangganColumn.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        alamatPelangganColumn.setCellValueFactory(cellData -> cellData.getValue().alamatProperty());
        noTelpPelangganColumn.setCellValueFactory(cellData -> cellData.getValue().noTelpProperty());
        grandtotalColumn.setCellValueFactory(cellData -> cellData.getValue().grandtotalProperty());
        grandtotalColumn.setCellFactory(col -> Function.getTableCell(rp));
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        statusColumn.setCellFactory(CheckBoxTableCell.forTableColumn((Integer v) -> {
            hitungTotal();
            return penjualanTable.getItems().get(v).statusProperty();
        }));
        tglMulaiPicker.setConverter(Function.getTglConverter());
        tglMulaiPicker.setValue(LocalDate.parse(sistem.getTglSystem()).minusMonths(1));
        tglMulaiPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglAkhirPicker, LocalDate.parse(sistem.getTglSystem())));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglMulaiPicker, LocalDate.parse(sistem.getTglSystem())));
        
        final ContextMenu rm = new ContextMenu();
        MenuItem addNew = new MenuItem("Tambah Penjualan Fiktif Cabang");
        addNew.setOnAction((ActionEvent e)->{
            addNewPenjualan();
        });
        MenuItem hapus = new MenuItem("Hapus Penjualan Fiktif Cabang");
        hapus.setOnAction((ActionEvent e)->{
            hapusPenjualan();
        });
        MenuItem nota = new MenuItem("Print Struk Penjualan Fiktif Cabang");
        nota.setOnAction((ActionEvent e)->{
            print();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            getPenjualan();
        });
        rm.getItems().addAll(addNew, hapus, nota, refresh);
        penjualanTable.setContextMenu(rm);
        penjualanTable.setRowFactory((TableView<PenjualanFiktifHead> tableView) -> {
            final TableRow<PenjualanFiktifHead> row = new TableRow<PenjualanFiktifHead>(){
                @Override
                public void updateItem(PenjualanFiktifHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else{
                        MenuItem addNew = new MenuItem("Tambah Penjualan Fiktif Cabang");
                        addNew.setOnAction((ActionEvent e)->{
                            addNewPenjualan();
                        });
                        MenuItem nota = new MenuItem("Print Struk Penjualan Fiktif Cabang");
                        nota.setOnAction((ActionEvent e)->{
                            print();
                        });
                        MenuItem lihat = new MenuItem("Ubah Penjualan Fiktif Cabang");
                        lihat.setOnAction((ActionEvent e)->{
                            ubahPenjualan(item);
                        });
                        MenuItem hapus = new MenuItem("Hapus Penjualan Fiktif Cabang");
                        hapus.setOnAction((ActionEvent e)->{
                            hapusPenjualan();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            getPenjualan();
                        });
                        ContextMenu rowMenu = new ContextMenu();
                        rowMenu.getItems().addAll(addNew, lihat, hapus, nota,  refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&
                        mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null){
                        if(row.getItem().isStatus())
                            row.getItem().setStatus(false);
                        else
                            row.getItem().setStatus(true);
                    }
                }
            });
            return row;
        });
        allPenjualan.addListener((ListChangeListener.Change<? extends PenjualanFiktifHead> change) -> {
            searchPenjualan();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchPenjualan();
        });
        filterData.addAll(allPenjualan);
        penjualanTable.setItems(filterData);
        cabangCombo.setItems(allCabang);
    }     
    public void setMainApp(Main mainApp) {
        try(Connection conPusat = KoneksiPusat.getConnection()){
            this.mainApp = mainApp;
            allCabang.add("Semua");
            List<Cabang> listCabang = CabangDAO.getAll(conPusat);
            for(Cabang c : listCabang){
                allCabang.add(c.getKodeCabang());
            }
            cabangCombo.getSelectionModel().select("Semua");
            getPenjualan();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    @FXML
    private void getPenjualan(){
        Task<List<PenjualanFiktifHead>> task = new Task<List<PenjualanFiktifHead>>() {
            @Override 
            public List<PenjualanFiktifHead> call() throws Exception {
                try (Connection con = KoneksiPusat.getConnection()) {
                    String cabang = cabangCombo.getSelectionModel().getSelectedItem();
                    if(cabangCombo.getSelectionModel().getSelectedItem().equals("Semua"))
                        cabang = "%";
                    return PenjualanFiktifHeadDAO.getAllByDateAndCabang(con, 
                            tglMulaiPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), cabang);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            mainApp.closeLoading();
            allPenjualan.clear();
            allPenjualan.addAll(task.getValue());
            hitungTotal();
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }
    @FXML
    private void checkAllHandle(){
        for(PenjualanFiktifHead d: filterData){
            d.setStatus(checkAll.isSelected());
        }
        penjualanTable.refresh();
        hitungTotal();
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
            for (PenjualanFiktifHead temp : allPenjualan) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(temp);
                else{
                    if(checkColumn(temp.getNoPenjualan())||
                        checkColumn(tglLengkap.format(tglSql.parse(temp.getTglPenjualan())))||
                        checkColumn(temp.getKodeCabang())||
                        checkColumn(temp.getNama())||
                        checkColumn(temp.getAlamat())||
                        checkColumn(temp.getNoTelp())||
                        checkColumn(rp.format(temp.getGrandtotal())))
                        filterData.add(temp);
                }
            }
            checkAllHandle();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
            e.printStackTrace();
        }
    }
    private void hitungTotal(){
        double qty = 0;
        double harga = 0;
        for(PenjualanFiktifHead temp : filterData){
            if(temp.isStatus()){
                qty = qty + 1;
                harga = harga + temp.getGrandtotal();
            }
        }
        totalNotaField.setText(rp.format(qty));
        totalPenjualanField.setText(rp.format(harga));
    }
    private void addNewPenjualan(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/PenjualanFiktifCabang/AddNewPenjualanFiktifCabang.fxml");
        AddNewPenjualanFiktifCabangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.saveButton.setOnAction((event) -> {
            if(controller.kodeCabangCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Cabang belum dipilih");
            else if(controller.bulanCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Periode bulan belum dipilih");
            else if(controller.tahunCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Periode tahun belum dipilih");
            else if(Double.parseDouble(controller.totalPenjualanField.getText().replaceAll(",", ""))==0)
                mainApp.showMessage(Modality.NONE, "Warning", "Total penjualan belum diisi");
            else if(Double.parseDouble(controller.minPenjualanField.getText().replaceAll(",", ""))==0)
                mainApp.showMessage(Modality.NONE, "Warning", "Minimal penjualan/nota belum diisi");
            else if(Double.parseDouble(controller.maksPenjualanField.getText().replaceAll(",", ""))==0)
                mainApp.showMessage(Modality.NONE, "Warning", "Maksimal penjualan/nota belum diisi");
            else if(controller.listDay.isEmpty())
                mainApp.showMessage(Modality.NONE, "Warning", "Hari kerja masih kosong");
            else if(controller.listPelangganFiktif.isEmpty())
                mainApp.showMessage(Modality.NONE, "Warning", "Data pelanggan masih kosong");
            else if(controller.listBarangFiktif.isEmpty())
                mainApp.showMessage(Modality.NONE, "Warning", "Data barang masih kosong");
            else{
                Task<Void> task = new Task<Void>() {
                    @Override 
                    public Void call() throws Exception{
                        Thread.sleep(timeout);
                        try(Connection con  = KoneksiPusat.getConnection()){
                            int minPenjualan = Integer.parseInt(controller.minPenjualanField.getText().replaceAll(",", ""));
                            int maksPenjualan = Integer.parseInt(controller.maksPenjualanField.getText().replaceAll(",", ""));
                            int totalPenjualan = Integer.parseInt(controller.totalPenjualanField.getText().replaceAll(",", ""));
                            Random r = new Random();
                            List<PenjualanFiktifHead> listPenjualan = new ArrayList<>();
                            double total = 0;
                            while(total<totalPenjualan){
                                PelangganFiktif pelanggan = controller.listPelangganFiktif.get(r.nextInt(controller.listPelangganFiktif.size()));
                                List<Day> allDay = new ArrayList<>();
                                for(Day d : controller.listDay){
                                    if(d.isStatus())
                                        allDay.add(d);
                                }
                                Day day = allDay.get(r.nextInt(allDay.size()));
                                int jam = r.nextInt(7)+8;
                                int menit = r.nextInt(60);
                                LocalDate date = LocalDate.parse(day.getTanggal(), DateTimeFormatter.ofPattern("dd MMMM yyyy"));
                                PenjualanFiktifHead p = new PenjualanFiktifHead();
                                p.setNoPenjualan(PenjualanFiktifHeadDAO.getId(con, 
                                        controller.kodeCabangCombo.getSelectionModel().getSelectedItem(), 
                                        date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
                                p.setTglPenjualan(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+" "+jam+":"+menit+":00");
                                p.setKodeCabang(controller.kodeCabangCombo.getSelectionModel().getSelectedItem());
                                p.setNama(pelanggan.getNama());
                                p.setAlamat(pelanggan.getAlamat());
                                p.setNoTelp(pelanggan.getNoTelp());
                                p.setGrandtotal(Double.parseDouble(String.valueOf(
                                    minPenjualan + (r.nextInt((maksPenjualan-minPenjualan)/1000) * 1000) + (r.nextInt(2) * 500)
                                )));

                                total = total + p.getGrandtotal();

                                if(total>totalPenjualan)
                                    p.setGrandtotal(p.getGrandtotal() + (totalPenjualan - total));

                                PenjualanFiktifHeadDAO.insert(con, p);
                                listPenjualan.add(p);
                            }
                            for(PenjualanFiktifHead p : listPenjualan){
                                double t = 0;
                                while(t<p.getGrandtotal()){
                                    KategoriFiktif k = controller.listKategoriFiktif.get(r.nextInt(controller.listKategoriFiktif.size()));
                                    int hargaMax = (int) k.getHargaJualMax();
                                    int hargaMin = (int) k.getHargaJualMin();
                                    int harga = hargaMin + (r.nextInt((hargaMax-hargaMin)/1000) * 1000);

                                    List<BarangFiktif> tempBarang = new ArrayList<>();
                                    for(BarangFiktif x : controller.listBarangFiktif){
                                        if(x.getKodeKategori().equals(k.getKodeKategori()))
                                            tempBarang.add(x);
                                    }
                                    if(!tempBarang.isEmpty()){
                                        BarangFiktif b = tempBarang.get(r.nextInt(tempBarang.size()));

                                        PenjualanFiktifDetail d = new PenjualanFiktifDetail();
                                        d.setNoPenjualan(p.getNoPenjualan());
                                        d.setKodeKategori(k.getKodeKategori());
                                        d.setNamaBarang(b.getNamaBarang());
                                        d.setKadar(b.getKadar());
                                        d.setKodeIntern(b.getKodeIntern());
                                        d.setBerat(Double.parseDouble(String.valueOf(
                                            5 + r.nextInt(95) / 10
                                        )));
                                        d.setHargaJual(d.getBerat()*harga);

                                        t = t + d.getHargaJual();

                                        if(t>p.getGrandtotal()){
                                            d.setHargaJual(d.getHargaJual()+ (p.getGrandtotal() - t));
                                            double berat = d.getHargaJual()/harga;
                                            d.setBerat(Math.ceil(berat*100)/100);
                                        }
                                        PenjualanFiktifDetailDAO.insert(con, d);
                                    }
                                }
                            }
                            return null;
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getPenjualan();
                    mainApp.showMessage(Modality.NONE, "Success", "Penjualan fiktif cabang berhasil disimpan");
                    mainApp.closeDialog(mainApp.MainStage, stage);
                });
                task.setOnFailed((e) -> {
                    mainApp.closeLoading();
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                });
                new Thread(task).start();

            }
        });
    }
    private void ubahPenjualan(PenjualanFiktifHead p){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/PenjualanFiktifCabang/DetailPenjualanFiktifCabang.fxml");
        DetailPenjualanFiktifCabangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setDetailPenjualan(p);
        controller.saveButton.setOnAction((event) -> {
            if(controller.allPenjualanDetail.isEmpty())
                mainApp.showMessage(Modality.NONE, "Warning", "Barang penjualan masih kosong");
            else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception {
                        try (Connection con = KoneksiPusat.getConnection()) {
                            p.setNama(controller.namaField.getText());
                            p.setAlamat(controller.alamatField.getText());
                            p.setNoTelp(controller.noTelpField.getText());
                            p.setGrandtotal(Double.parseDouble(controller.totalPenjualanField.getText().replaceAll(",", "")));
                            p.setListPenjualanDetail(controller.allPenjualanDetail);
                            PenjualanFiktifHeadDAO.update(con, p);

                            PenjualanFiktifDetailDAO.delete(con, p.getNoPenjualan());
                            for(PenjualanFiktifDetail d : p.getListPenjualanDetail()){
                                PenjualanFiktifDetailDAO.insert(con, d);
                            }
                            return "true";
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent e) -> {
                    mainApp.closeLoading();
                    getPenjualan();
                    mainApp.showMessage(Modality.NONE, "Success", "Penjualan fiktif cabang berhasil disimpan");
                    mainApp.closeDialog(mainApp.MainStage, stage);
                });
                task.setOnFailed((e) -> {
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                    mainApp.closeLoading();
                });
                new Thread(task).start();
            }
        });
    }
    private void hapusPenjualan(){
        int qty = 0;
        for(PenjualanFiktifHead p : filterData){
            if(p.isStatus())
                qty = qty +1;
        }
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Hapus "+qty+" penjualan ?");
        controller.OK.setOnAction((ActionEvent ex) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try(Connection con = KoneksiPusat.getConnection()){
                        for(PenjualanFiktifHead p : filterData){
                            if(p.isStatus()){
                                PenjualanFiktifHeadDAO.delete(con, p.getNoPenjualan());
                                PenjualanFiktifDetailDAO.delete(con, p.getNoPenjualan());
                            }
                        }
                        return "true";
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getPenjualan();
                String status = task.getValue();
                if(status.equals("true")){
                    mainApp.showMessage(Modality.NONE, "Success", "Penjualan fiktif cabang berhasil dihapus");
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
    private void print(){
        List<PenjualanFiktifHead> listPenjualan = new ArrayList<>();
        for(PenjualanFiktifHead p : filterData){
            if(p.isStatus())
                listPenjualan.add(p);
        }
        if(listPenjualan.isEmpty()){
            mainApp.showMessage(Modality.NONE, "Warning", "Data penjualan belum di pilih");
        }else{
            MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                    "Print "+listPenjualan.size()+" penjualan ?");
            controller.OK.setOnAction((ActionEvent ex) -> {
                try(Connection con = KoneksiPusat.getConnection()){
                    String cabang = cabangCombo.getSelectionModel().getSelectedItem();
                    if(cabangCombo.getSelectionModel().getSelectedItem().equals("Semua"))
                        cabang = "%";
                    String tglMulai = tglSystem.format(tglBarang.parse(tglMulaiPicker.getValue().toString()));
                    String tglAkhir = tglSystem.format(tglBarang.parse(tglAkhirPicker.getValue().toString()));
                    List<PenjualanFiktifDetail> listDetail = PenjualanFiktifDetailDAO.getAllByDateAndCabang(
                            con, tglMulai, tglAkhir, cabang);
                    for(PenjualanFiktifHead p : listPenjualan){
                        List<PenjualanFiktifDetail> detail = new ArrayList<>();
                        for(PenjualanFiktifDetail d : listDetail){
                            if(p.getNoPenjualan().equals(d.getNoPenjualan()))
                                detail.add(d);
                        }
                        p.setListPenjualanDetail(detail);
                    }
                    PrintOut report = new PrintOut();
                    report.printStrukPenjualanDirect(listPenjualan);
                    mainApp.closeMessage();
                }catch(Exception e){
                    mainApp.showMessage(Modality.NONE, "Error", e.toString());
                }
            });
        }
    }
    
}
