/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.PasarBaja.View;

import com.excellentsystem.PasarBaja.DAO.CustomerDAO;
import com.excellentsystem.PasarBaja.DAO.PegawaiDAO;
import com.excellentsystem.PasarBaja.Function;
import static com.excellentsystem.PasarBaja.Function.createRow;
import com.excellentsystem.PasarBaja.Koneksi;
import com.excellentsystem.PasarBaja.Main;
import static com.excellentsystem.PasarBaja.Main.df;
import static com.excellentsystem.PasarBaja.Main.sistem;
import com.excellentsystem.PasarBaja.Model.Customer;
import com.excellentsystem.PasarBaja.Model.Otoritas;
import com.excellentsystem.PasarBaja.Model.Pegawai;
import com.excellentsystem.PasarBaja.Services.Service;
import com.excellentsystem.PasarBaja.View.Dialog.DetailCustomerController;
import com.excellentsystem.PasarBaja.View.Dialog.MessageController;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.util.List;
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
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class DataCustomerController  {
    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, String> kodeCustomerColumn;
    @FXML private TableColumn<Customer, String> namaColumn;
    @FXML private TableColumn<Customer, String> alamatColumn;
    @FXML private TableColumn<Customer, String> kotaColumn;
    @FXML private TableColumn<Customer, String> negaraColumn;
    @FXML private TableColumn<Customer, String> kodePosColumn;
    @FXML private TableColumn<Customer, String> emailColumn;
    @FXML private TableColumn<Customer, String> kontakPersonColumn;
    @FXML private TableColumn<Customer, String> noTelpColumn;
    @FXML private TableColumn<Customer, String> noHandphoneColumn;
    @FXML private TableColumn<Customer, String> kodeSalesColumn;
    @FXML private TableColumn<Customer, String> namaSalesColumn;
    @FXML private TableColumn<Customer, String> bankColumn;
    @FXML private TableColumn<Customer, String> atasNamaRekeningColumn;
    @FXML private TableColumn<Customer, String> noRekeningColumn;
    @FXML private TableColumn<Customer, Number> limitHutangColumn;
    @FXML private TableColumn<Customer, Number> hutangColumn;
    @FXML private TableColumn<Customer, Number> depositColumn;
    @FXML private TextField searchField;
    @FXML private Label totalDepositLabel;
    @FXML private Label totalHutangLabel;
    private Main mainApp;  
    private ObservableList<Customer> allCustomer = FXCollections.observableArrayList();
    private ObservableList<Customer> filterData = FXCollections.observableArrayList();
    public void initialize() {
        kodeCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().kodeCustomerProperty());
        kodeCustomerColumn.setCellFactory(col -> Function.getWrapTableCell(kodeCustomerColumn));
        
        namaColumn.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        namaColumn.setCellFactory(col -> Function.getWrapTableCell(namaColumn));
        
        alamatColumn.setCellValueFactory(cellData -> cellData.getValue().alamatProperty());
        alamatColumn.setCellFactory(col -> Function.getWrapTableCell(alamatColumn));
        
        kotaColumn.setCellValueFactory(cellData -> cellData.getValue().kotaProperty());
        kotaColumn.setCellFactory(col -> Function.getWrapTableCell(kotaColumn));
        
        negaraColumn.setCellValueFactory(cellData -> cellData.getValue().negaraProperty());
        negaraColumn.setCellFactory(col -> Function.getWrapTableCell(negaraColumn));
        
        kodePosColumn.setCellValueFactory(cellData -> cellData.getValue().kodePosProperty());
        kodePosColumn.setCellFactory(col -> Function.getWrapTableCell(kodePosColumn));
        
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        emailColumn.setCellFactory(col -> Function.getWrapTableCell(emailColumn));
        
        kontakPersonColumn.setCellValueFactory(cellData ->cellData.getValue().kontakPersonProperty());
        kontakPersonColumn.setCellFactory(col -> Function.getWrapTableCell(kontakPersonColumn));
        
        noTelpColumn.setCellValueFactory(cellData ->cellData.getValue().noTelpProperty());
        noTelpColumn.setCellFactory(col -> Function.getWrapTableCell(noTelpColumn));
        
        noHandphoneColumn.setCellValueFactory(cellData ->cellData.getValue().noHandphoneProperty());
        noHandphoneColumn.setCellFactory(col -> Function.getWrapTableCell(noHandphoneColumn));
        
        kodeSalesColumn.setCellValueFactory(cellData ->cellData.getValue().kodeSalesProperty());
        kodeSalesColumn.setCellFactory(col -> Function.getWrapTableCell(kodeSalesColumn));
        
        namaSalesColumn.setCellValueFactory(cellData ->cellData.getValue().getSales().namaProperty());
        namaSalesColumn.setCellFactory(col -> Function.getWrapTableCell(namaSalesColumn));
        
        bankColumn.setCellValueFactory(cellData -> cellData.getValue().bankProperty());
        bankColumn.setCellFactory(col -> Function.getWrapTableCell(bankColumn));
        
        atasNamaRekeningColumn.setCellValueFactory(cellData -> cellData.getValue().atasNamaRekeningProperty());
        atasNamaRekeningColumn.setCellFactory(col -> Function.getWrapTableCell(atasNamaRekeningColumn));
        
        noRekeningColumn.setCellValueFactory(cellData -> cellData.getValue().noRekeningProperty());
        noRekeningColumn.setCellFactory(col -> Function.getWrapTableCell(noRekeningColumn));
        
        limitHutangColumn.setCellValueFactory(cellData -> cellData.getValue().limitHutangProperty());
        limitHutangColumn.setCellFactory(col -> Function.getTableCell());
        
        hutangColumn.setCellValueFactory(cellData -> cellData.getValue().hutangProperty());
        hutangColumn.setCellFactory(col -> Function.getTableCell());
        
        depositColumn.setCellValueFactory(cellData -> cellData.getValue().depositProperty());
        depositColumn.setCellFactory(col -> Function.getTableCell());
        
        final ContextMenu rm = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Customer");
        addNew.setOnAction((ActionEvent e)->{
            newCustomer();
        });
        MenuItem export = new MenuItem("Export Excel");
        export.setOnAction((ActionEvent e)->{
            exportExcel();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            getCustomer();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Add New Customer")&&o.isStatus())
                rm.getItems().add(addNew);
            if(o.getJenis().equals("Export Excel")&&o.isStatus())
                rm.getItems().add(export);
        }
        rm.getItems().addAll(refresh);
        customerTable.setContextMenu(rm);
        customerTable.setRowFactory((TableView<Customer> tableView) -> {
            final TableRow<Customer> row = new TableRow<Customer>(){
                @Override
                public void updateItem(Customer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Customer");
                        addNew.setOnAction((ActionEvent e)->{
                            newCustomer();
                        });
                        MenuItem edit = new MenuItem("Edit Customer");
                        edit.setOnAction((ActionEvent e)->{
                            editCustomer(item);
                        });
                        MenuItem hapus = new MenuItem("Delete Customer");
                        hapus.setOnAction((ActionEvent e)->{
                            deleteCustomer(item);
                        });
                        MenuItem export = new MenuItem("Export Excel");
                        export.setOnAction((ActionEvent e)->{
                            exportExcel();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            getCustomer();
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Add New Customer")&&o.isStatus())
                                rm.getItems().add(addNew);
                            if(o.getJenis().equals("Edit Customer")&&o.isStatus())
                                rm.getItems().add(edit);
                            if(o.getJenis().equals("Delete Customer")&&o.isStatus())
                                rm.getItems().add(hapus);
                            if(o.getJenis().equals("Export Excel")&&o.isStatus())
                                rm.getItems().add(export);
                        }
                        rm.getItems().addAll(refresh);
                        setContextMenu(rm);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null){    
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Edit Customer")&&o.isStatus())
                                editCustomer(row.getItem());
                        }
                    }
                }
            });
            return row;
        });
        allCustomer.addListener((ListChangeListener.Change<? extends Customer> change) -> {
            searchCustomer();
        });
        searchField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchCustomer();
        });
        filterData.addAll(allCustomer);
    }
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
        getCustomer();
        customerTable.setItems(filterData);
    }
    private void getCustomer(){
        Task<List<Customer>> task = new Task<List<Customer>>() {
            @Override 
            public List<Customer> call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    List<Pegawai> listPegawai = PegawaiDAO.getAllByStatus(con, "%");
                    List<Customer> allCustomer = CustomerDAO.getAllByStatus(con, "true");
                    for(Customer c : allCustomer){
                        for(Pegawai s : listPegawai){
                            if(c.getKodeSales().equals(s.getKodePegawai()))
                                c.setSales(s);
                        }
                    }
                    return allCustomer;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            mainApp.closeLoading();
            allCustomer.clear();
            allCustomer.addAll(task.getValue());
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
    private void searchCustomer() {
        filterData.clear();
        for (Customer temp : allCustomer) {
            if (searchField.getText() == null || searchField.getText().equals(""))
                filterData.add(temp);
            else{
                if(checkColumn(temp.getKodeCustomer())||
                    checkColumn(temp.getNama())||
                    checkColumn(temp.getAlamat())||
                    checkColumn(temp.getKota())||
                    checkColumn(temp.getNegara())||
                    checkColumn(temp.getNegara())||
                    checkColumn(temp.getKodePos())||
                    checkColumn(temp.getEmail())||
                    checkColumn(temp.getKontakPerson())||
                    checkColumn(temp.getNoTelp())||
                    checkColumn(temp.getNoHandphone())||
                    checkColumn(temp.getKodeSales())||
                    checkColumn(temp.getSales().getNama())||
                    checkColumn(temp.getBank())||
                    checkColumn(temp.getAtasNamaRekening())||
                    checkColumn(temp.getNoRekening())||
                    checkColumn(df.format(temp.getLimitHutang()))||
                    checkColumn(df.format(temp.getHutang()))||
                    checkColumn(df.format(temp.getDeposit())))
                    filterData.add(temp);
            }
        }
        hitungTotal();
    }
    private void hitungTotal(){
        double deposit = 0;
        double hutang = 0;
        for(Customer c : filterData){
            deposit = deposit + c.getDeposit();
            hutang = hutang + c.getHutang();
        }
        totalDepositLabel.setText(df.format(deposit));
        totalHutangLabel.setText(df.format(hutang));
    }
    private void newCustomer(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailCustomer.fxml");
        DetailCustomerController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setCustomerDetail(null);
        x.saveButton.setOnAction((ActionEvent ev)->{
            if(x.kodeSalesCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Sales belum dipilih");
            else if(x.limitHutangField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Limit hutang masih kosong");
            else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try (Connection con = Koneksi.getConnection()) {
                            Customer customer = new Customer();
                            customer.setKodeCustomer(CustomerDAO.getId(con));
                            customer.setNama(x.namaField.getText());
                            customer.setAlamat(x.alamatField.getText());
                            customer.setKota(x.kotaField.getText());
                            customer.setNegara(x.negaraField.getText());
                            customer.setKodePos(x.kodePosField.getText());
                            customer.setEmail(x.emailField.getText());
                            customer.setKontakPerson(x.kontakPersonField.getText());
                            customer.setNoTelp(x.noTelpField.getText());
                            customer.setNoHandphone(x.noHandphoneField.getText());
                            customer.setKodeSales(x.kodeSalesCombo.getSelectionModel().getSelectedItem().split(" - ")[0]);
                            customer.setBank(x.bankField.getText());
                            customer.setAtasNamaRekening(x.atasNamaRekeningField.getText());
                            customer.setNoRekening(x.noRekeningField.getText());
                            customer.setLimitHutang(Double.parseDouble(x.limitHutangField.getText().replaceAll(",", "")));
                            customer.setHutang(0);
                            customer.setDeposit(0);
                            customer.setStatus("true");
                            return Service.newCustomer(con, customer);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getCustomer();
                    if(task.getValue().equals("true")){
                        mainApp.showMessage(Modality.NONE, "Success", "Data customer berhasil disimpan");
                        mainApp.closeDialog(mainApp.MainStage, stage);
                    }else{
                        mainApp.showMessage(Modality.NONE, "Error", task.getValue());
                    }
                });
                task.setOnFailed((e) -> {
                    mainApp.closeLoading();
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                });
                new Thread(task).start();
            }
        });
    }
    private void editCustomer(Customer c){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailCustomer.fxml");
        DetailCustomerController x = loader.getController();
        x.setMainApp(mainApp,mainApp.MainStage, stage);
        x.setCustomerDetail(c);
        x.saveButton.setOnAction((ActionEvent ev)->{
            if(x.kodeSalesCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Sales belum dipilih");
            else if(x.limitHutangField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Limit hutang masih kosong");
            else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try (Connection con = Koneksi.getConnection()) {
                            c.setNama(x.namaField.getText());
                            c.setAlamat(x.alamatField.getText());
                            c.setKota(x.kotaField.getText());
                            c.setNegara(x.negaraField.getText());
                            c.setKodePos(x.kodePosField.getText());
                            c.setEmail(x.emailField.getText());
                            c.setKontakPerson(x.kontakPersonField.getText());
                            c.setNoTelp(x.noTelpField.getText());
                            c.setNoHandphone(x.noHandphoneField.getText());
                            c.setKodeSales(x.kodeSalesCombo.getSelectionModel().getSelectedItem().split(" - ")[0]);
                            c.setBank(x.bankField.getText());
                            c.setAtasNamaRekening(x.atasNamaRekeningField.getText());
                            c.setNoRekening(x.noRekeningField.getText());
                            c.setLimitHutang(Double.parseDouble(x.limitHutangField.getText().replaceAll(",", "")));
                            return Service.updateCustomer(con, c);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getCustomer();
                    if(task.getValue().equals("true")){
                        mainApp.showMessage(Modality.NONE, "Success", "Data customer berhasil disimpan");
                        mainApp.closeDialog(mainApp.MainStage, stage);
                    }else{
                        mainApp.showMessage(Modality.NONE, "Error", task.getValue());
                    }
                });
                task.setOnFailed((e) -> {
                    mainApp.closeLoading();
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                });
                new Thread(task).start();
            }
        });
    }
    private void deleteCustomer(Customer c){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Delete customer "+c.getKodeCustomer()+"-"+c.getNama()+" ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try (Connection con = Koneksi.getConnection()) {
                        return Service.deleteCustomer(con, c);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getCustomer();
                if(task.getValue().equals("true")){
                    mainApp.showMessage(Modality.NONE, "Success", "Data customer berhasil dihapus");
                }else{
                    mainApp.showMessage(Modality.NONE, "Error", task.getValue());
                }
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        });
    }
    private void exportExcel() {
        try{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select location to export");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Excel Document 2007", "*.xlsx"),
                    new FileChooser.ExtensionFilter("Excel Document 1997-2007", "*.xls")
            );
            File file = fileChooser.showSaveDialog(mainApp.MainStage);
            if (file != null) {
                Workbook workbook;
                if (file.getPath().endsWith("xlsx")) {
                    workbook = new XSSFWorkbook();
                } else if (file.getPath().endsWith("xls")) {
                    workbook = new HSSFWorkbook();
                } else {
                    throw new IllegalArgumentException("The specified file is not Excel file");
                }
                Font bold = workbook.createFont();
                bold.setBold(true);
                
                CellStyle H1 = workbook.createCellStyle();
                H1.setFont(bold);
                CellStyle H2 = workbook.createCellStyle();
                H2.setFont(bold);
                H2.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                H2.setFillPattern(CellStyle.SOLID_FOREGROUND);
                
                Sheet sheet = workbook.createSheet("Data Customer");
                int rc = 0;
                int c = 17;
                createRow(workbook, sheet, rc, c,"Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Filter : "+searchField.getText());
                rc++;
                createRow(workbook, sheet, rc, c,"Header");
                sheet.getRow(rc).getCell(0).setCellValue("Kode Customer"); 
                sheet.getRow(rc).getCell(1).setCellValue("Nama"); 
                sheet.getRow(rc).getCell(2).setCellValue("Alamat"); 
                sheet.getRow(rc).getCell(3).setCellValue("Kota"); 
                sheet.getRow(rc).getCell(4).setCellValue("Negara"); 
                sheet.getRow(rc).getCell(5).setCellValue("Kode Pos"); 
                sheet.getRow(rc).getCell(6).setCellValue("Email"); 
                sheet.getRow(rc).getCell(7).setCellValue("Kontak Person"); 
                sheet.getRow(rc).getCell(8).setCellValue("No Telp"); 
                sheet.getRow(rc).getCell(9).setCellValue("No Handphone"); 
                sheet.getRow(rc).getCell(10).setCellValue("Sales"); 
                sheet.getRow(rc).getCell(11).setCellValue("Bank"); 
                sheet.getRow(rc).getCell(12).setCellValue("Atas Nama Rekening"); 
                sheet.getRow(rc).getCell(13).setCellValue("No Rekening"); 
                sheet.getRow(rc).getCell(14).setCellValue("Limit Hutang"); 
                sheet.getRow(rc).getCell(15).setCellValue("Hutang"); 
                sheet.getRow(rc).getCell(16).setCellValue("Deposit"); 
                rc++;
                double hutang = 0;
                double deposit = 0;
                for (Customer b : filterData) {
                    createRow(workbook, sheet, rc, c,"Detail");
                    sheet.getRow(rc).getCell(0).setCellValue(b.getKodeCustomer());
                    sheet.getRow(rc).getCell(1).setCellValue(b.getNama());
                    sheet.getRow(rc).getCell(2).setCellValue(b.getAlamat());
                    sheet.getRow(rc).getCell(3).setCellValue(b.getKota());
                    sheet.getRow(rc).getCell(4).setCellValue(b.getNegara());
                    sheet.getRow(rc).getCell(5).setCellValue(b.getKodePos());
                    sheet.getRow(rc).getCell(6).setCellValue(b.getEmail());
                    sheet.getRow(rc).getCell(7).setCellValue(b.getKontakPerson());
                    sheet.getRow(rc).getCell(8).setCellValue(b.getNoTelp());
                    sheet.getRow(rc).getCell(9).setCellValue(b.getNoHandphone());
                    sheet.getRow(rc).getCell(10).setCellValue(b.getSales().getNama());
                    sheet.getRow(rc).getCell(11).setCellValue(b.getBank());
                    sheet.getRow(rc).getCell(12).setCellValue(b.getAtasNamaRekening());
                    sheet.getRow(rc).getCell(13).setCellValue(b.getNoRekening());
                    sheet.getRow(rc).getCell(14).setCellValue(b.getLimitHutang());
                    sheet.getRow(rc).getCell(15).setCellValue(b.getHutang());
                    sheet.getRow(rc).getCell(16).setCellValue(b.getDeposit());
                    rc++;
                    
                    hutang = hutang + b.getHutang();
                    deposit = deposit + b.getDeposit();
                }
                createRow(workbook, sheet, rc, c,"Header");
                sheet.getRow(rc).getCell(0).setCellValue("Total :");
                sheet.getRow(rc).getCell(15).setCellValue(hutang);
                sheet.getRow(rc).getCell(16).setCellValue(deposit);
                for(int i=0 ; i<c ; i++){ sheet.autoSizeColumn(i);}
                try (FileOutputStream outputStream = new FileOutputStream(file)) {
                    workbook.write(outputStream);
                }
            }
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
            e.printStackTrace();
        }
    }
}
