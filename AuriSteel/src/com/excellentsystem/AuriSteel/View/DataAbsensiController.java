/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View;

import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.sistem;
import static com.excellentsystem.AuriSteel.Main.tglBarang;
import static com.excellentsystem.AuriSteel.Main.tglSql;
import com.excellentsystem.AuriSteel.Model.Absensi;
import com.excellentsystem.AuriSteel.Model.Otoritas;
import com.excellentsystem.AuriSteel.Model.Pegawai;
import com.excellentsystem.AuriSteel.View.Dialog.AddAbsensiController;
import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class DataAbsensiController  {

    @FXML private TableView<Pegawai> pegawaiTable;
    @FXML private TableColumn<Pegawai, String> namaColumn;
    
    @FXML private TableView<Absensi> absensiTable;
    @FXML private TableColumn<Absensi, String> hariColumn;
    @FXML private TableColumn<Absensi, String> tanggalColumn;
    @FXML private TableColumn<Absensi, String> jamMasukColumn;
    @FXML private TableColumn<Absensi, String> jamPulangColumn;
    @FXML private TableColumn<Absensi, String> absenMasukColumn;
    @FXML private TableColumn<Absensi, String> absenPulangColumn;
    @FXML private TableColumn<Absensi, String> waktuKerjaColumn;
    @FXML private TableColumn<Absensi, String> terlambatColumn;
    @FXML private TableColumn<Absensi, String> lemburColumn;
    @FXML private TableColumn<Absensi, String> lemburMalamColumn;
    @FXML private TableColumn<Absensi, String> keteranganColumn;
    
    @FXML private Label periodeLabel;
    @FXML private Label totalHariKerjaLabel;
    @FXML private Label totalJamKerjaLabel;
    @FXML private Label totalTerlambatLabel;
    @FXML private Label totalWaktuTerlambatLabel;
    @FXML private Label totalLemburLabel;
    private List<Absensi> listAbsensi;
    private ObservableList<Pegawai> allPegawai = FXCollections.observableArrayList();
    private ObservableList<Absensi> allAbsensi = FXCollections.observableArrayList();
    private Main mainApp;  
    public void initialize() {
        namaColumn.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        namaColumn.setCellFactory(col -> Function.getWrapTableCell(namaColumn));
        
        hariColumn.setCellValueFactory(cellData -> {
            try{
                String hari = new SimpleDateFormat("EEEEEE").format(tglBarang.parse(cellData.getValue().getTanggal()));
                if(hari.equals("Sunday"))
                    hari = "Minggu";
                if(hari.equals("Monday"))
                    hari = "Senin";
                if(hari.equals("Tuesday"))
                    hari = "Selasa";
                if(hari.equals("Wednesday"))
                    hari = "Rabu";
                if(hari.equals("Thursday"))
                    hari = "Kamis";
                if(hari.equals("Friday"))
                    hari = "Jumat";
                if(hari.equals("Saturday"))
                    hari = "Sabtu";
                return new SimpleStringProperty(hari);
            }catch(Exception e){
                return null;
            }
        });
        hariColumn.setCellFactory(col -> Function.getWrapTableCell(hariColumn));
        
        tanggalColumn.setCellValueFactory(cellData -> {
            try{
                return new SimpleStringProperty(new SimpleDateFormat("dd MMMMM yyyy").format(tglBarang.parse(cellData.getValue().getTanggal())));
            }catch(Exception e){
                return null;
            }
        });
        tanggalColumn.setCellFactory(col -> Function.getWrapTableCell(tanggalColumn));
        
        jamMasukColumn.setCellValueFactory(cellData -> {
            try{
                if(cellData.getValue().getJamMasuk()!=0)
                    return new SimpleStringProperty(new SimpleDateFormat("HH:mm").format(new Date(cellData.getValue().getJamMasuk())));
                else 
                    return null;
            }catch(Exception e){
                return null;
            }
        });
        jamMasukColumn.setCellFactory(col -> Function.getWrapTableCell(jamMasukColumn));
        
        jamPulangColumn.setCellValueFactory(cellData -> {
            try{
                if(cellData.getValue().getJamPulang()!=0)
                    return new SimpleStringProperty(new SimpleDateFormat("HH:mm").format(new Date(cellData.getValue().getJamPulang())));
                else 
                    return null;
            }catch(Exception e){
                return null;
            }
        });
        jamPulangColumn.setCellFactory(col -> Function.getWrapTableCell(jamPulangColumn));
        
        absenMasukColumn.setCellValueFactory(cellData -> {
            try{
                if(cellData.getValue().getAbsenMasuk()!=0)
                    return new SimpleStringProperty(new SimpleDateFormat("HH:mm").format(new Date(cellData.getValue().getAbsenMasuk())));
                else 
                    return null;
            }catch(Exception e){
                return null;
            }
        });
        absenMasukColumn.setCellFactory(col -> Function.getWrapTableCell(jamMasukColumn));
        
        absenPulangColumn.setCellValueFactory(cellData -> {
            try{
                if(cellData.getValue().getAbsenPulang()!=0)
                    return new SimpleStringProperty(new SimpleDateFormat("HH:mm").format(new Date(cellData.getValue().getAbsenPulang())));
                else 
                    return null;
            }catch(Exception e){
                return null;
            }
        });
        absenPulangColumn.setCellFactory(col -> Function.getWrapTableCell(jamPulangColumn));
        
        waktuKerjaColumn.setCellValueFactory(cellData -> {
            if(cellData.getValue().getAbsenMasuk()!=0 && cellData.getValue().getAbsenPulang()!=0){
                long absenMasuk = cellData.getValue().getAbsenMasuk();
                long absenPulang = cellData.getValue().getAbsenPulang();
                if(cellData.getValue().getJamMasuk()>=cellData.getValue().getAbsenMasuk())
                    absenMasuk = cellData.getValue().getJamMasuk();
                long lamaKerja = (absenPulang - absenMasuk) /1000/60;
                long jamKerja = lamaKerja / 60;
                long menitKerja = lamaKerja % 60;
                return new SimpleStringProperty(df.format(jamKerja)+" jam "+df.format(menitKerja)+" menit");
            }else
                return null;
        });
        waktuKerjaColumn.setCellFactory(col -> Function.getWrapTableCell(waktuKerjaColumn));
        
        terlambatColumn.setCellValueFactory(cellData -> {
            if(cellData.getValue().getAbsenMasuk()!=0){
                Long terlambat = (cellData.getValue().getAbsenMasuk()-cellData.getValue().getJamMasuk()) /1000/60;
                if(terlambat>0)
                    return new SimpleStringProperty(terlambat+" menit");
                else
                    return null;
            }else
                return null;
        });
        terlambatColumn.setCellFactory(col -> Function.getWrapTableCell(terlambatColumn));
        
        lemburColumn.setCellValueFactory(cellData -> {
            long lembur = (cellData.getValue().getAbsenPulang()-cellData.getValue().getJamPulang()) / 1000/60;
            if(0<lembur && lembur<210)
                return new SimpleStringProperty(lembur+" menit");
            else if(lembur>210)
                return new SimpleStringProperty("210 menit");
            else
                return null;
        });
        lemburColumn.setCellFactory(col -> Function.getWrapTableCell(lemburColumn));
        
        lemburMalamColumn.setCellValueFactory(cellData -> {
            long lembur = (cellData.getValue().getAbsenPulang()-cellData.getValue().getJamPulang()) / 1000/60-210;
            if(lembur>0)
                return new SimpleStringProperty(lembur+" menit");
            else
                return null;
        });
        lemburMalamColumn.setCellFactory(col -> Function.getWrapTableCell(lemburMalamColumn));
        
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        keteranganColumn.setCellFactory(col -> Function.getWrapTableCell(keteranganColumn));
        
        final ContextMenu rm = new ContextMenu();
        MenuItem export = new MenuItem("Export Excel");
        export.setOnAction((ActionEvent e)->{
            exportExcel();
        });
        MenuItem importExcel = new MenuItem("Import Excel");
        importExcel.setOnAction((ActionEvent e)->{
            importExcel();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            getAbsensi();
        });
        rm.getItems().add(importExcel);
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Export Excel")&&o.isStatus())
                rm.getItems().add(export);
        }
        rm.getItems().addAll(refresh);
        absensiTable.setContextMenu(rm);
        absensiTable.setRowFactory((TableView<Absensi> tableView) -> {
            final TableRow<Absensi> row = new TableRow<Absensi>(){
                @Override
                public void updateItem(Absensi item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem editAbsensi = new MenuItem("Edit Absensi");
                        editAbsensi.setOnAction((ActionEvent e)->{
                            editAbsensi(item);
                        });
                        MenuItem importExcel = new MenuItem("Import Excel");
                        importExcel.setOnAction((ActionEvent e)->{
                            importExcel();
                        });
                        MenuItem export = new MenuItem("Export Excel");
                        export.setOnAction((ActionEvent e)->{
                            exportExcel();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            getAbsensi();
                        });
                        rm.getItems().addAll(importExcel);
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Edit Absensi")&&o.isStatus())
                                rm.getItems().add(editAbsensi);
                            if(o.getJenis().equals("Export Excel")&&o.isStatus())
                                rm.getItems().add(export);
                        }
                        rm.getItems().addAll(refresh);
                        setContextMenu(rm);
                    }
                }
            };
            row.itemProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue!=null){
                    Absensi a = newValue;
                    LocalDate date  = LocalDate.parse(a.getTanggal(), DateTimeFormatter.ISO_DATE);
                    if (date.getDayOfWeek().equals(DayOfWeek.SUNDAY))
                        row.setStyle("-fx-background-color: #FFD8D1");//red
                    else
                        row.setStyle("");
                }else
                    row.setStyle("");
            });
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null){
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Edit Absensi")&&o.isStatus())
                                editAbsensi(row.getItem());
                        }
                    }
                }
            });
            return row;
        });
        pegawaiTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> getAbsensi());
    }
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        absensiTable.setItems(allAbsensi);
        pegawaiTable.setItems(allPegawai);
    }
    @FXML
    private void getAbsensi(){
        if(pegawaiTable.getSelectionModel().getSelectedItem()!=null){
            allAbsensi.clear();
            for(Absensi a : listAbsensi){
                if(a.getNama().equals(pegawaiTable.getSelectionModel().getSelectedItem().getNama()))
                    allAbsensi.add(a);
            }
            allAbsensi.sort(Comparator.comparing(Absensi::getTanggal));
            hitungTotal();
        }
    }
    private void hitungTotal(){
        long totalHariKerja = 0;
        long lamaKerja = 0;
        long terlambat = 0;
        long waktuTerlambat = 0;
        long lembur = 0;
        for(Absensi a : allAbsensi){
            long jamMasuk = a.getJamMasuk();
            long jamPulang = a.getJamPulang();
            long absenMasuk = a.getAbsenMasuk();
            long absenPulang = a.getAbsenPulang();
            if(absenMasuk!=0){
                if(absenPulang==0)
                    absenPulang = jamPulang;
                
                totalHariKerja = totalHariKerja + 1;
                
                if(jamMasuk>=absenMasuk)
                    lamaKerja = lamaKerja + ((absenPulang - jamMasuk) /1000/60);
                else
                    lamaKerja = lamaKerja + ((absenPulang - absenMasuk) /1000/60);

                if(((absenMasuk - jamMasuk) /1000/60)>0){
                    terlambat = terlambat + 1;
                    waktuTerlambat = waktuTerlambat + ((absenMasuk - jamMasuk) /1000/60);
                }

                lembur = lembur + ((absenPulang - jamPulang) /1000/60);
            }
        }
        totalHariKerjaLabel.setText(df.format(totalHariKerja)+" hari");
        totalJamKerjaLabel.setText(new DecimalFormat("##").format(lamaKerja / 60)+" jam "+new DecimalFormat("##").format(lamaKerja % 60)+" menit");
        totalTerlambatLabel.setText(df.format(terlambat)+" kali");
        totalWaktuTerlambatLabel.setText("( "+df.format(waktuTerlambat)+" menit )");
        totalLemburLabel.setText(new DecimalFormat("##").format(lembur / 60)+" jam "+new DecimalFormat("##").format(lembur % 60)+" menit");
    }
    private void editAbsensi(Absensi a){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/AddAbsensi.fxml");
        AddAbsensiController controller = loader.getController();
        controller.setMainApp(mainApp,mainApp.MainStage, stage);
        controller.setAbsensi(a);
        controller.saveButton.setOnAction((event) -> {
            try{
                Date absenMasuk = tglSql.parse(a.getTanggal()+" "+controller.jamMasukField.getText()+":00");
                Date absenPulang = tglSql.parse(a.getTanggal()+" "+controller.jamPulangField.getText()+":00");
                a.setAbsenMasuk(absenMasuk.getTime());
                a.setAbsenPulang(absenPulang.getTime());
                a.setKeterangan(controller.keteranganField.getText());
                getAbsensi();
                mainApp.showMessage(Modality.NONE, "Success", "Absensi berhasil disimpan");
                mainApp.closeDialog(mainApp.MainStage, stage);
            }catch(Exception e){
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        });
    }
    @FXML
    private void importExcel(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select .xls or .xlsx files");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel Document 2007", "*.xlsx"),new FileChooser.ExtensionFilter("Excel Document 1997-2007", "*.xls"));
        File selectedFile = fileChooser.showOpenDialog(mainApp.MainStage);
        if (selectedFile != null) {
            Task<List<Absensi>> task = new Task<List<Absensi>>() {
                @Override 
                public List<Absensi> call() throws Exception{
                    List<Absensi> listImport = new ArrayList<>();
                    String excelFilePath = selectedFile.getPath();
                    try (FileInputStream inputStream = new FileInputStream(selectedFile)) {
                        Workbook workbook;
                        if (excelFilePath.endsWith("xlsx")) {
                            workbook = new XSSFWorkbook(inputStream);
                        } else if (excelFilePath.endsWith("xls")) {
                            workbook = new HSSFWorkbook(inputStream);
                        } else {
                            throw new IllegalArgumentException("The specified file is not Excel file");
                        }
                        for(int i = 4 ; i < workbook.getNumberOfSheets() ; i++){
                            if(workbook.getSheetAt(i)==null)
                                break;
                            else{
                                Sheet firstSheet = workbook.getSheetAt(i);
                                Iterator<Row> iterator = firstSheet.iterator();
                                iterator.next();//1
                                Row rowTanggal = iterator.next();//2
                                String tanggal = rowTanggal.getCell(3).getStringCellValue().substring(0,8);
                                
                                Row rowPegawai = iterator.next();//3
                                String namaSatu = rowPegawai.getCell(9).getStringCellValue();
                                String namaDua = rowPegawai.getCell(24).getStringCellValue();
                                String namaTiga = rowPegawai.getCell(39).getStringCellValue();
                                
                                iterator.next();//4
                                iterator.next();//5
                                iterator.next();//6
                                iterator.next();//7
                                iterator.next();//8
                                iterator.next();//9
                                iterator.next();//10
                                iterator.next();//11
                                while (iterator.hasNext()) {
                                    Row row = iterator.next();
                                    if(!namaSatu.equals("")){
                                        Absensi absenSatu = new Absensi();
                                        absenSatu.setTanggal(tanggal+row.getCell(0).getStringCellValue().substring(0, 2));
                                        absenSatu.setNama(namaSatu);
                                        if(!row.getCell(1).getStringCellValue().equals("Absent")){
                                            if(!row.getCell(1).getStringCellValue().equals(""))
                                                absenSatu.setAbsenMasuk(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(absenSatu.getTanggal()+" "+row.getCell(1).getStringCellValue()).getTime());
                                            if(!row.getCell(3).getStringCellValue().equals(""))
                                                absenSatu.setAbsenPulang(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(absenSatu.getTanggal()+" "+row.getCell(3).getStringCellValue()).getTime());
                                        }
                                        listImport.add(absenSatu);
                                    }
                                    
                                    if(!namaDua.equals("")){
                                        Absensi absenDua = new Absensi();
                                        absenDua.setTanggal(tanggal+row.getCell(15).getStringCellValue().substring(0, 2));
                                        absenDua.setNama(namaDua);
                                        if(!row.getCell(16).getStringCellValue().equals("Absent")){
                                            if(!row.getCell(16).getStringCellValue().equals(""))
                                                absenDua.setAbsenMasuk(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(absenDua.getTanggal()+" "+row.getCell(16).getStringCellValue()).getTime());
                                            if(!row.getCell(18).getStringCellValue().equals(""))
                                                absenDua.setAbsenPulang(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(absenDua.getTanggal()+" "+row.getCell(18).getStringCellValue()).getTime());
                                        }
                                        listImport.add(absenDua);
                                    }
                                    
                                    if(!namaTiga.equals("")){
                                        Absensi absenTiga = new Absensi();
                                        absenTiga.setTanggal(tanggal+row.getCell(30).getStringCellValue().substring(0, 2));
                                        absenTiga.setNama(namaTiga);
                                        if(!row.getCell(31).getStringCellValue().equals("Absent")){
                                            if(!row.getCell(31).getStringCellValue().equals(""))
                                                absenTiga.setAbsenMasuk(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(absenTiga.getTanggal()+" "+row.getCell(31).getStringCellValue()).getTime());
                                            if(!row.getCell(33).getStringCellValue().equals(""))
                                                absenTiga.setAbsenPulang(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(absenTiga.getTanggal()+" "+row.getCell(33).getStringCellValue()).getTime());
                                        }
                                        listImport.add(absenTiga);
                                    }
                                    
                                }
                            }
                        }
                        workbook.close();
                    }
                    return listImport;
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent ev) -> {
                try{
                    mainApp.closeLoading();
                    listAbsensi = task.getValue();
                    List<String> listPegawai = new ArrayList<>();
                    for(Absensi a : listAbsensi){
                        if(!listPegawai.contains(a.getNama()))
                            listPegawai.add(a.getNama());
                        a.setJamMasuk(0);
                        a.setJamPulang(0);
                        LocalDate d = LocalDate.parse(a.getTanggal(), DateTimeFormatter.ISO_DATE);
                        if(!d.getDayOfWeek().equals(DayOfWeek.SUNDAY)){
                            Date dateMasuk = tglBarang.parse(d.toString());
                            Date datePulang = tglBarang.parse(d.toString());
                            dateMasuk.setHours(8);
                            dateMasuk.setMinutes(30);
                            datePulang.setHours(16);
                            datePulang.setMinutes(30);
                            a.setJamMasuk(dateMasuk.getTime());
                            a.setJamPulang(datePulang.getTime());
                        }
                    }
                    allPegawai.clear();
                    for(String nama : listPegawai){
                        Pegawai p = new Pegawai();
                        p.setNama(nama);
                        allPegawai.add(p);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    mainApp.showMessage(Modality.NONE, "Error", e.toString());
                }
            });
            task.setOnFailed((e) -> {
                task.getException().printStackTrace();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                mainApp.closeLoading();
            });
            new Thread(task).start();
        }
    }
    private void exportExcel(){}
}
