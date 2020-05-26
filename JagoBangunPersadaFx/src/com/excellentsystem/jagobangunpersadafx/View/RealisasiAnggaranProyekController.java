/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View;

import com.excellentsystem.jagobangunpersadafx.DAO.PropertyDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.RAPDetailPropertyDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.RAPHeadDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.RAPRealisasiDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import static com.excellentsystem.jagobangunpersadafx.Function.getDateCellAkhir;
import static com.excellentsystem.jagobangunpersadafx.Function.getDateCellMulai;
import static com.excellentsystem.jagobangunpersadafx.Function.getTableCell;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.qty;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import static com.excellentsystem.jagobangunpersadafx.Main.sistem;
import static com.excellentsystem.jagobangunpersadafx.Main.tgl;
import static com.excellentsystem.jagobangunpersadafx.Main.tglBarang;
import static com.excellentsystem.jagobangunpersadafx.Main.tglLengkap;
import static com.excellentsystem.jagobangunpersadafx.Main.tglSql;
import com.excellentsystem.jagobangunpersadafx.Model.Otoritas;
import com.excellentsystem.jagobangunpersadafx.Model.Property;
import com.excellentsystem.jagobangunpersadafx.Model.RAPDetailProperty;
import com.excellentsystem.jagobangunpersadafx.Model.RAPHead;
import com.excellentsystem.jagobangunpersadafx.Model.RAPRealisasi;
import com.excellentsystem.jagobangunpersadafx.Printout.PrintOut;
import com.excellentsystem.jagobangunpersadafx.Service.Service;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailRealisasiProyekController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailRencanaAnggaranProyekController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.MessageController;
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
public class RealisasiAnggaranProyekController  {
    @FXML private TableView<RAPHead> proyekTable;
    @FXML private TableColumn<RAPHead, String> noRAPColumn;
    @FXML private TableColumn<RAPHead, String> tglRAPColumn;
    @FXML private TableColumn<RAPHead, String> kategoriColumn;
    @FXML private TableColumn<RAPHead, String> keteranganColumn;
    @FXML private TableColumn<RAPHead, String> metodeColumn;
    @FXML private TableColumn<RAPHead, String> perkiraanWaktuColumn;
    @FXML private TableColumn<RAPHead, Number> totalPropertyColumn;
    @FXML private TableColumn<RAPHead, Number> totalAnggaranColumn;
    @FXML private TableColumn<RAPHead, String> kodeUserColumn;
    @FXML private TableColumn<RAPHead, String> statusApprovalColumn;
    
    @FXML private TableView<RAPRealisasi> realisasiTable;
    @FXML private TableColumn<RAPRealisasi, String> tglRealisasiColumn;
    @FXML private TableColumn<RAPRealisasi, String> keteranganRealisasiColumn;
    @FXML private TableColumn<RAPRealisasi, String> satuanColumn;
    @FXML private TableColumn<RAPRealisasi, Number> qtyColumn;
    @FXML private TableColumn<RAPRealisasi, Number> jumlahRpColumn;
    @FXML private TableColumn<RAPRealisasi, String> tipeKeuanganColumn;
    @FXML private TableColumn<RAPRealisasi, String> kodeUserRealisasiColumn;
    
    @FXML private TextField searchField;
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    @FXML private Label totalRealisasiLabel;
    private ObservableList<RAPHead> allRAP = FXCollections.observableArrayList();
    private ObservableList<RAPHead> filterData = FXCollections.observableArrayList();
    private ObservableList<RAPRealisasi> allDetailRealisasi = FXCollections.observableArrayList();
    private Main mainApp;   
    public void initialize() {
        noRAPColumn.setCellValueFactory(cellData -> cellData.getValue().noRapProperty());
        noRAPColumn.setCellFactory(col -> Function.getWrapTableCell(noRAPColumn));
        
        tglRAPColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglRap())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglRAPColumn.setCellFactory(col -> Function.getWrapTableCell(tglRAPColumn));
        tglRAPColumn.setComparator(Function.sortDate(tglLengkap));
        
        kategoriColumn.setCellValueFactory(cellData ->cellData.getValue().kategoriPembangunanProperty());
        kategoriColumn.setCellFactory(col -> Function.getWrapTableCell(kategoriColumn));
        
        keteranganColumn.setCellValueFactory(cellData ->cellData.getValue().keteranganProperty());
        keteranganColumn.setCellFactory(col -> Function.getWrapTableCell(keteranganColumn));
        
        metodeColumn.setCellValueFactory(cellData ->cellData.getValue().metodePembagianProperty());
        metodeColumn.setCellFactory(col -> Function.getWrapTableCell(metodeColumn));
        
        perkiraanWaktuColumn.setCellValueFactory(cellData ->{
            return new SimpleStringProperty(cellData.getValue().getPerkiraanWaktu()+" hari");
        });
        perkiraanWaktuColumn.setCellFactory(col -> Function.getWrapTableCell(perkiraanWaktuColumn));
        
        totalPropertyColumn.setCellValueFactory(cellData ->cellData.getValue().totalPropertyProperty());
        totalPropertyColumn.setCellFactory(col -> getTableCell(qty));
        
        totalAnggaranColumn.setCellValueFactory(cellData ->cellData.getValue().totalAnggaranProperty());
        totalAnggaranColumn.setCellFactory(col -> getTableCell(rp));
        
        statusApprovalColumn.setCellValueFactory(cellData ->cellData.getValue().statusApprovalProperty());
        statusApprovalColumn.setCellFactory(col -> Function.getWrapTableCell(statusApprovalColumn));
        
        kodeUserColumn.setCellValueFactory(cellData ->cellData.getValue().kodeUserProperty());
        kodeUserColumn.setCellFactory(col -> Function.getWrapTableCell(kodeUserColumn));
        
        tglRealisasiColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglRealisasi())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglRealisasiColumn.setCellFactory(col -> Function.getWrapTableCell(tglRealisasiColumn));
        tglRealisasiColumn.setComparator(Function.sortDate(tglLengkap));
        
        keteranganRealisasiColumn.setCellValueFactory(cellData ->cellData.getValue().keteranganProperty());
        keteranganRealisasiColumn.setCellFactory(col -> Function.getWrapTableCell(keteranganRealisasiColumn));
        
        satuanColumn.setCellValueFactory(cellData ->cellData.getValue().satuanProperty());
        satuanColumn.setCellFactory(col -> Function.getWrapTableCell(satuanColumn));
        
        qtyColumn.setCellValueFactory(cellData ->cellData.getValue().qtyProperty());
        qtyColumn.setCellFactory(col -> getTableCell(qty));
        
        jumlahRpColumn.setCellValueFactory(cellData ->cellData.getValue().jumlahRpProperty());
        jumlahRpColumn.setCellFactory(col -> getTableCell(rp));
        
        tipeKeuanganColumn.setCellValueFactory(cellData ->cellData.getValue().tipeKeuanganProperty());
        tipeKeuanganColumn.setCellFactory(col -> Function.getWrapTableCell(tipeKeuanganColumn));
        
        kodeUserRealisasiColumn.setCellValueFactory(cellData ->cellData.getValue().kodeUserProperty());
        kodeUserRealisasiColumn.setCellFactory(col -> Function.getWrapTableCell(kodeUserRealisasiColumn));
        
        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.now().minusMonths(3));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> getDateCellMulai(tglAkhirPicker));
        
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> getDateCellAkhir(tglAwalPicker));
        
        allRAP.addListener((ListChangeListener.Change<? extends RAPHead> change) -> {
            searchRAP();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchRAP();
        });
        filterData.addAll(allRAP);
        
        ContextMenu rowMenu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            getRAP();
        });
        rowMenu.getItems().addAll(refresh);
        proyekTable.setContextMenu(rowMenu);
        proyekTable.setRowFactory(tv -> {
            TableRow<RAPHead> row = new TableRow<RAPHead>() {
                @Override
                public void updateItem(RAPHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{ 
                        ContextMenu rowMenu = new ContextMenu();
                        MenuItem detail = new MenuItem("Detail Rencana Anggaran Proyek");
                        detail.setOnAction((ActionEvent event) -> {
                            detailRAP(item);
                        });
                        MenuItem print = new MenuItem("Print Realisasi Anggaran Proyek");
                        print.setOnAction((ActionEvent event) -> {
                            printRAP(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            getRAP();
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Detail Rencana Anggaran Proyek")&&o.isStatus())
                                rowMenu.getItems().add(detail);
                            if(o.getJenis().equals("Print Realisasi Anggaran Proyek")&&o.isStatus())
                                rowMenu.getItems().add(print);
                        }
                        rowMenu.getItems().addAll(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2)
                    for(Otoritas o : sistem.getUser().getOtoritas()){
                        if(o.getJenis().equals("Detail Rencana Anggaran Proyek")&&o.isStatus())
                            detailRAP(row.getItem());
                    }
            });
            return row;
        });
        proyekTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> getRealisasi());
        
        ContextMenu realisasiRowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Realisasi");
        addNew.setOnAction((ActionEvent event) -> {
            addNewRealisasi();
        });
        MenuItem refreshRealisasi = new MenuItem("Refresh");
        refreshRealisasi.setOnAction((ActionEvent e)->{
            getRealisasi();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Add New Realisasi")&&o.isStatus())
                realisasiRowMenu.getItems().add(addNew);
        }
        realisasiRowMenu.getItems().addAll(refreshRealisasi);
        realisasiTable.setContextMenu(realisasiRowMenu);
        realisasiTable.setRowFactory(tv -> {
            TableRow<RAPRealisasi> row = new TableRow<RAPRealisasi>() {
                @Override
                public void updateItem(RAPRealisasi item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(realisasiRowMenu);
                    }else{ 
                        ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Realisasi");
                        addNew.setOnAction((ActionEvent event) -> {
                            addNewRealisasi();
                        });
                        MenuItem detail = new MenuItem("Detail Realisasi");
                        detail.setOnAction((ActionEvent event) -> {
                            detailRealisasi(item);
                        });
                        MenuItem hapus = new MenuItem("Batal Realisasi");
                        hapus.setOnAction((ActionEvent event) -> {
                            batalRealisasi(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            getRealisasi();
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Add New Realisasi")&&o.isStatus())
                                rowMenu.getItems().add(addNew);
                            if(o.getJenis().equals("Detail Realisasi")&&o.isStatus())
                                rowMenu.getItems().add(detail);
                            if(o.getJenis().equals("Batal Realisasi")&&o.isStatus())
                                rowMenu.getItems().add(hapus);
                        }
                        rowMenu.getItems().addAll(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2)
                    for(Otoritas o : sistem.getUser().getOtoritas()){
                        if(o.getJenis().equals("Detail Realisasi")&&o.isStatus())
                            detailRealisasi(row.getItem());
                    }
            });
            return row;
        });
    }
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
        getRAP();
        proyekTable.setItems(filterData);
        realisasiTable.setItems(allDetailRealisasi);
    }
    @FXML
    private void getRAP(){
        Task<List<RAPHead>> task = new Task<List<RAPHead>>() {
            @Override 
            public List<RAPHead> call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    List<RAPHead> listRAP = RAPHeadDAO.getAllByDateAndStatusApprovalAndStatusSelesaiAndStatusBatal(con, 
                            tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), 
                            "Approved", "%", "false");
                    return listRAP;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allRAP.clear();
            allRAP.addAll(task.getValue());
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
    private void searchRAP() {
        try{
            filterData.clear();
            for (RAPHead temp : allRAP) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(temp);
                else{
                    if(checkColumn(temp.getNoRap())||
                        checkColumn(temp.getTglRap())||
                        checkColumn(temp.getKategoriPembangunan())||
                        checkColumn(qty.format(temp.getPerkiraanWaktu()))||
                        checkColumn(tgl.format(tglBarang.parse(temp.getTglMulai())))||
                        checkColumn(tglLengkap.format(tglSql.parse(temp.getTglRap())))||
                        checkColumn(tglLengkap.format(tglSql.parse(temp.getTglSelesai())))||
                        checkColumn(temp.getMetodePembagian())||
                        checkColumn(temp.getKeterangan())||
                        checkColumn(qty.format(temp.getTotalProperty()))||
                        checkColumn(rp.format(temp.getTotalAnggaran()))||
                        checkColumn(temp.getKodeUser()))
                            filterData.add(temp);
                }
            }
            hitungTotal();
        }catch(Exception e){
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    @FXML
    private void getRealisasi(){
        allDetailRealisasi.clear();
        if(proyekTable.getSelectionModel().getSelectedItem()!=null){
            Task<List<RAPRealisasi>> task = new Task<List<RAPRealisasi>>() {
                @Override 
                public List<RAPRealisasi> call() throws Exception{
                    try(Connection con = Koneksi.getConnection()){
                        return RAPRealisasiDAO.getAllByNoRapAndStatus(con, proyekTable.getSelectionModel().getSelectedItem().getNoRap(), "true");
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                allDetailRealisasi.addAll(task.getValue());
                hitungTotal();
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                task.getException().printStackTrace();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        }
    }
    private void hitungTotal(){
        double total = 0;
        for(RAPRealisasi r : allDetailRealisasi){
            total = total + r.getJumlahRp();
        }
        totalRealisasiLabel.setText(rp.format(total));
    }
    private void detailRAP(RAPHead r){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailRencanaAnggaranProyek.fxml");
        DetailRencanaAnggaranProyekController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setRAP(r);
    }
    private void addNewRealisasi(){
        if(proyekTable.getSelectionModel().getSelectedItem()==null){
            mainApp.showMessage(Modality.NONE, "Warning", "Rencana anggaran proyek belum dipilih");
        }else{
            Stage stage = new Stage();
            FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailRealisasiProyek.fxml");
            DetailRealisasiProyekController x = loader.getController();
            x.setMainApp(mainApp, mainApp.MainStage, stage);
            x.saveButton.setOnAction((event) -> {
                if("".equals(x.keteranganField.getText())){
                    mainApp.showMessage(Modality.NONE, "Warning", "Keterangan masih kosong");
                }else if("".equals(x.satuanField.getText())){
                    mainApp.showMessage(Modality.NONE, "Warning", "Satuan masih kosong");
                }else if("".equals(x.qtyField.getText())||"0".equals(x.qtyField.getText())){
                    mainApp.showMessage(Modality.NONE, "Warning", "Qty masih kosong");
                }else if("".equals(x.hargaSatuanField.getText())||"0".equals(x.hargaSatuanField.getText())){
                    mainApp.showMessage(Modality.NONE, "Warning", "Harga satuan masih kosong");
                }else if("".equals(x.totalField.getText())||"0".equals(x.totalField.getText())){
                    mainApp.showMessage(Modality.NONE, "Warning", "Total masih kosong");
                }else if(x.tipeKeuanganCombo.getSelectionModel().getSelectedItem()==null||
                        "".equals(x.tipeKeuanganCombo.getSelectionModel().getSelectedItem())){
                    mainApp.showMessage(Modality.NONE, "Warning", "Tipe keuangan belum dipilih");
                }else{
                    if(proyekTable.getSelectionModel().getSelectedItem()!=null){
                        Task<String> task = new Task<String>() {
                            @Override 
                            public String call() throws Exception{
                                try (Connection con = Koneksi.getConnection()) {
                                    RAPRealisasi r = new RAPRealisasi();
                                    r.setNoRap(proyekTable.getSelectionModel().getSelectedItem().getNoRap());
                                    r.setNoUrut(1);
                                    r.setTglRealisasi(tglSql.format(new Date()));
                                    r.setKeterangan(x.keteranganField.getText());
                                    r.setSatuan(x.satuanField.getText());
                                    r.setQty(Integer.parseInt(x.qtyField.getText().replaceAll(",", "")));
                                    r.setJumlahRp(Double.parseDouble(x.totalField.getText().replaceAll(",", "")));
                                    r.setTipeKeuangan(x.tipeKeuanganCombo.getSelectionModel().getSelectedItem());
                                    r.setKodeUser(sistem.getUser().getUsername());
                                    r.setStatus("true");
                                    r.setTglBatal("2000-01-01 00:00:00");
                                    r.setUserBatal("");
                                    return Service.saveRealisasi(con, r);
                                }
                            }
                        };
                        task.setOnRunning((e) -> {
                            mainApp.showLoadingScreen();
                        });
                        task.setOnSucceeded((e) -> {
                            mainApp.closeLoading();
                            getRealisasi();
                            if(task.getValue().equals("true")){
                                mainApp.closeDialog(mainApp.MainStage, stage);
                                mainApp.showMessage(Modality.NONE, "Success","Realisasi proyek berhasil disimpan");
                            }else{
                                mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
                            }
                        });
                        task.setOnFailed((e) -> {
                            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                            mainApp.closeLoading();
                        });
                        new Thread(task).start();
                    }
                }
            });
            x.cancelButton.setOnAction((event) -> {
                mainApp.closeDialog(mainApp.MainStage, stage);
            });
        }
    }
    private void detailRealisasi(RAPRealisasi r){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailRealisasiProyek.fxml");
        DetailRealisasiProyekController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setDetailRealisasi(r);
    }
    private void batalRealisasi(RAPRealisasi r){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Batal realisasi proyek " + r.getNoRap()+ " - "+r.getNoUrut()+", anda yakin ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try (Connection con = Koneksi.getConnection()) {
                        RAPHead rapHead = RAPHeadDAO.get(con, r.getNoRap());
                        rapHead.setListRapPropertyDetail(RAPDetailPropertyDAO.getAllByNoRap(con, r.getNoRap()));
                        Boolean statusTanah = true;
                        for(RAPDetailProperty detail : rapHead.getListRapPropertyDetail()){
                            Property prop = PropertyDAO.get(con, detail.getKodeProperty());
                            if(prop.getStatus().equals("Mapped")||
                                    prop.getStatus().equals("Combined")||
                                    prop.getStatus().equals("Sold - Full Paid")||
                                    prop.getStatus().equals("Sold"))
                                statusTanah = false;
                        }
                        if(statusTanah){
                            r.setTglBatal(tglSql.format(new Date()));
                            r.setUserBatal(sistem.getUser().getUsername());
                            r.setStatus("false");
                            return Service.batalRealisasi(con, r);
                        }else 
                            return "Realisasi proyek tidak dapat dibatalkan, karena status tanah sudah lunas/sudah dipecah/sudah digabung";
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent e) -> {
                mainApp.closeLoading();
                getRealisasi();
                if (task.getValue().equals("true")) 
                    mainApp.showMessage(Modality.NONE, "Success", "Data realisasi proyek berhasil dibatal");
                else 
                    mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
            });
            task.setOnFailed((e) -> {
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                mainApp.closeLoading();
            });
            new Thread(task).start();
        });
    }
    private void printRAP(RAPHead rap){
        try(Connection con = Koneksi.getConnection()){
            rap.setListRapRealisasi(RAPRealisasiDAO.getAllByNoRapAndStatus(con, rap.getNoRap(), "true"));
            PrintOut print = new PrintOut();
            print.printRealisasiAnggaranProyek(rap);
        }catch(Exception e){
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error",e.toString());
        }
    }
}
