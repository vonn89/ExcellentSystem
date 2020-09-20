/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.PasarBaja.View;

import com.excellentsystem.PasarBaja.DAO.PegawaiDAO;
import com.excellentsystem.PasarBaja.Function;
import static com.excellentsystem.PasarBaja.Function.createRow;
import com.excellentsystem.PasarBaja.Koneksi;
import com.excellentsystem.PasarBaja.Main;
import static com.excellentsystem.PasarBaja.Main.sistem;
import com.excellentsystem.PasarBaja.Model.Otoritas;
import com.excellentsystem.PasarBaja.Model.Pegawai;
import com.excellentsystem.PasarBaja.Services.Service;
import com.excellentsystem.PasarBaja.View.Dialog.DetailPegawaiController;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
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
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class DataPegawaiController  {
    @FXML private TableView<Pegawai> pegawaiTable;
    @FXML private TableColumn<Pegawai, String> kodePegawaiColumn;
    @FXML private TableColumn<Pegawai, String> namaColumn;
    @FXML private TableColumn<Pegawai, String> jabatanColumn;
    @FXML private TableColumn<Pegawai, String> alamatColumn;
    @FXML private TableColumn<Pegawai, String> kotaColumn;
    @FXML private TableColumn<Pegawai, String> identitasColumn;
    @FXML private TableColumn<Pegawai, String> noIdentitasColumn;
    @FXML private TableColumn<Pegawai, String> emailColumn;
    @FXML private TableColumn<Pegawai, String> noTelpColumn;
    @FXML private TableColumn<Pegawai, String> noHandphoneColumn;
    @FXML private TextField searchField;
    
    private Main mainApp;      
    private ObservableList<Pegawai> allPegawai = FXCollections.observableArrayList();
    private ObservableList<Pegawai> filterData = FXCollections.observableArrayList();
    public void initialize() {
        kodePegawaiColumn.setCellValueFactory(cellData -> cellData.getValue().kodePegawaiProperty());
        kodePegawaiColumn.setCellFactory(col -> Function.getWrapTableCell(kodePegawaiColumn));
        
        namaColumn.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        namaColumn.setCellFactory(col -> Function.getWrapTableCell(namaColumn));
        
        jabatanColumn.setCellValueFactory(cellData ->cellData.getValue().jabatanProperty());
        jabatanColumn.setCellFactory(col -> Function.getWrapTableCell(jabatanColumn));
        
        alamatColumn.setCellValueFactory(cellData -> cellData.getValue().alamatProperty());
        alamatColumn.setCellFactory(col -> Function.getWrapTableCell(alamatColumn));
        
        kotaColumn.setCellValueFactory(cellData -> cellData.getValue().kotaProperty());
        kotaColumn.setCellFactory(col -> Function.getWrapTableCell(kotaColumn));
        
        identitasColumn.setCellValueFactory(cellData -> cellData.getValue().identitasProperty());
        identitasColumn.setCellFactory(col -> Function.getWrapTableCell(identitasColumn));
        
        noIdentitasColumn.setCellValueFactory(cellData -> cellData.getValue().noIdentitasProperty());
        noIdentitasColumn.setCellFactory(col -> Function.getWrapTableCell(noIdentitasColumn));
        
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        emailColumn.setCellFactory(col -> Function.getWrapTableCell(emailColumn));
        
        noTelpColumn.setCellValueFactory(cellData ->cellData.getValue().noTelpProperty());
        noTelpColumn.setCellFactory(col -> Function.getWrapTableCell(noTelpColumn));
        
        noHandphoneColumn.setCellValueFactory(cellData ->cellData.getValue().noHandphoneProperty());
        noHandphoneColumn.setCellFactory(col -> Function.getWrapTableCell(noHandphoneColumn));
        
        final ContextMenu rm = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Pegawai");
        addNew.setOnAction((ActionEvent e)->{
            newPegawai();
        });
        MenuItem export = new MenuItem("Export Excel");
        export.setOnAction((ActionEvent e)->{
            exportExcel();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            getPegawai();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Add New Pegawai")&&o.isStatus())
                rm.getItems().add(addNew);
            if(o.getJenis().equals("Export Excel")&&o.isStatus())
                rm.getItems().add(export);
        }
        rm.getItems().addAll(refresh);
        pegawaiTable.setContextMenu(rm);
        pegawaiTable.setRowFactory((TableView<Pegawai> tableView) -> {
            final TableRow<Pegawai> row = new TableRow<Pegawai>(){
                @Override
                public void updateItem(Pegawai item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Pegawai");
                        addNew.setOnAction((ActionEvent e)->{
                            newPegawai();
                        });
                        MenuItem edit = new MenuItem("Edit Pegawai");
                        edit.setOnAction((ActionEvent e)->{
                            editPegawai(item);
                        });
                        MenuItem hapus = new MenuItem("Delete Pegawai");
                        hapus.setOnAction((ActionEvent e)->{
                            deletePegawai(item);
                        });
                        MenuItem export = new MenuItem("Export Excel");
                        export.setOnAction((ActionEvent e)->{
                            exportExcel();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            getPegawai();
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Add New Pegawai")&&o.isStatus())
                                rm.getItems().add(addNew);
                            if(o.getJenis().equals("Edit Pegawai")&&o.isStatus())
                                rm.getItems().add(edit);
                            if(o.getJenis().equals("Delete Pegawai")&&o.isStatus())
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
                            if(o.getJenis().equals("Edit Pegawai")&&o.isStatus())
                                editPegawai(row.getItem());
                        }
                    }
                }
            });
            return row;
        });
        allPegawai.addListener((ListChangeListener.Change<? extends Pegawai> change) -> {
            searchPegawai();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchPegawai();
        });
        filterData.addAll(allPegawai);
    }
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getPegawai();
        pegawaiTable.setItems(filterData);
    }
    private void getPegawai(){
        Task<List<Pegawai>> task = new Task<List<Pegawai>>() {
            @Override 
            public List<Pegawai> call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    return PegawaiDAO.getAllByStatus(con, "true");
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allPegawai.clear();
            allPegawai.addAll(task.getValue());
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
    private void searchPegawai() {
        filterData.clear();
        for (Pegawai temp : allPegawai) {
            if (searchField.getText() == null || searchField.getText().equals(""))
                filterData.add(temp);
            else{
                if(checkColumn(temp.getKodePegawai())||
                    checkColumn(temp.getNama())||
                    checkColumn(temp.getJabatan())||
                    checkColumn(temp.getAlamat())||
                    checkColumn(temp.getKota())||
                    checkColumn(temp.getEmail())||
                    checkColumn(temp.getNoTelp())||
                    checkColumn(temp.getNoHandphone())||
                    checkColumn(temp.getIdentitas())||
                    checkColumn(temp.getNoIdentitas()))
                    filterData.add(temp);
            }
        }
    }
    private void newPegawai(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailPegawai.fxml");
        DetailPegawaiController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setPegawaiDetail(null);
        controller.saveButton.setOnAction((ActionEvent ev)->{
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try (Connection con = Koneksi.getConnection()) {
                        Pegawai pegawai = new Pegawai();
                        pegawai.setKodePegawai(PegawaiDAO.getId(con));
                        pegawai.setNama(controller.namaField.getText());
                        pegawai.setJabatan(controller.jabatanCombo.getSelectionModel().getSelectedItem());
                        pegawai.setAlamat(controller.alamatField.getText());
                        pegawai.setKota(controller.kotaField.getText());
                        pegawai.setIdentitas(controller.identitasField.getText());
                        pegawai.setNoIdentitas(controller.noIdentitasField.getText());
                        pegawai.setEmail(controller.emailField.getText());
                        pegawai.setNoTelp(controller.noTelpField.getText());
                        pegawai.setNoHandphone(controller.noHandphoneField.getText());
                        pegawai.setStatus("true");
                        return Service.newPegawai(con, pegawai);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getPegawai();
                if(task.getValue().equals("true")){
                    mainApp.showMessage(Modality.NONE, "Success", "Data pegawai berhasil disimpan");
                    mainApp.closeDialog(mainApp.MainStage, stage);
                }else{
                    mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
                }
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        });
    }
    private void editPegawai(Pegawai pegawai){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailPegawai.fxml");
        DetailPegawaiController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setPegawaiDetail(pegawai);
        controller.saveButton.setOnAction((ActionEvent ev)->{
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try (Connection con = Koneksi.getConnection()) {
                        pegawai.setNama(controller.namaField.getText());
                        pegawai.setJabatan(controller.jabatanCombo.getSelectionModel().getSelectedItem());
                        pegawai.setAlamat(controller.alamatField.getText());
                        pegawai.setKota(controller.kotaField.getText());
                        pegawai.setIdentitas(controller.identitasField.getText());
                        pegawai.setNoIdentitas(controller.noIdentitasField.getText());
                        pegawai.setEmail(controller.emailField.getText());
                        pegawai.setNoTelp(controller.noTelpField.getText());
                        pegawai.setNoHandphone(controller.noHandphoneField.getText());
                        pegawai.setStatus("true");
                        return Service.updatePegawai(con, pegawai);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getPegawai();
                if(task.getValue().equals("true")){
                    mainApp.showMessage(Modality.NONE, "Success", "Data pegawai berhasil disimpan");
                    mainApp.closeDialog(mainApp.MainStage, stage);
                }else{
                    mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
                }
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        });
    }
    private void deletePegawai(Pegawai p){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Delete pegawai "+p.getKodePegawai()+"-"+p.getNama()+" ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            mainApp.closeMessage();
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try (Connection con = Koneksi.getConnection()) {
                        return Service.deletePegawai(con, p);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getPegawai();
                if(task.getValue().equals("true")){
                    mainApp.showMessage(Modality.NONE, "Success", "Data pegawai berhasil dihapus");
                }else{
                    mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
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
                Sheet sheet = workbook.createSheet("Data Pegawai");
                int rc = 0;
                int c = 10;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Filter : "+searchField.getText());
                rc++;
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("Kode Pegawai"); 
                sheet.getRow(rc).getCell(1).setCellValue("Nama"); 
                sheet.getRow(rc).getCell(2).setCellValue("Jabatan"); 
                sheet.getRow(rc).getCell(3).setCellValue("Alamat"); 
                sheet.getRow(rc).getCell(4).setCellValue("Kota"); 
                sheet.getRow(rc).getCell(5).setCellValue("Identitas"); 
                sheet.getRow(rc).getCell(6).setCellValue("No Identitas"); 
                sheet.getRow(rc).getCell(7).setCellValue("Email"); 
                sheet.getRow(rc).getCell(8).setCellValue("No Telp"); 
                sheet.getRow(rc).getCell(9).setCellValue("No Handphone"); 
                rc++;
                for (Pegawai b : filterData) {
                    createRow(workbook, sheet, rc, c, "Detail");
                    sheet.getRow(rc).getCell(0).setCellValue(b.getKodePegawai());
                    sheet.getRow(rc).getCell(1).setCellValue(b.getNama());
                    sheet.getRow(rc).getCell(2).setCellValue(b.getJabatan());
                    sheet.getRow(rc).getCell(3).setCellValue(b.getAlamat());
                    sheet.getRow(rc).getCell(4).setCellValue(b.getKota());
                    sheet.getRow(rc).getCell(5).setCellValue(b.getIdentitas());
                    sheet.getRow(rc).getCell(6).setCellValue(b.getNoIdentitas());
                    sheet.getRow(rc).getCell(7).setCellValue(b.getEmail());
                    sheet.getRow(rc).getCell(8).setCellValue(b.getNoTelp());
                    sheet.getRow(rc).getCell(9).setCellValue(b.getNoHandphone());
                    rc++;
                }
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
