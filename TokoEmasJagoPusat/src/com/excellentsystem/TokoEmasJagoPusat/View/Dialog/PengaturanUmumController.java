/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View.Dialog;

import com.excellentsystem.TokoEmasJagoPusat.DAO.SistemDAO;
import com.excellentsystem.TokoEmasJagoPusat.Function;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.timeout;
import com.excellentsystem.TokoEmasJagoPusat.Model.Sistem;
import com.excellentsystem.TokoEmasJagoPusat.Service.Service;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class PengaturanUmumController  {
    
    @FXML private TextField hargaEmasField;
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        Function.setNumberField(hargaEmasField, rp);
        hargaEmasField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                save();
        });
        hargaEmasField.requestFocus();
    }    
    public void setMainApp(Main main,Stage owner, Stage stage){
        this.mainApp = main;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        getSistem();
    }
    private void getSistem(){
        Task<Void> task = new Task<Void>() {
            @Override 
            public Void call() throws Exception{
                Thread.sleep(timeout);
                try(Connection con = KoneksiPusat.getConnection()){
                    Sistem temp = SistemDAO.get(con);
                    sistem.setVersion(temp.getVersion());
                    sistem.setTglSystem(temp.getTglSystem());
                    sistem.setHargaEmas(temp.getHargaEmas());
                    return null;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            hargaEmasField.setText(rp.format(sistem.getHargaEmas()));
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    @FXML
    private void save(){
        Task<String> task = new Task<String>() {
            @Override 
            public String call() throws Exception{
                Thread.sleep(timeout);
                try(Connection con = KoneksiPusat.getConnection()){
                    sistem.setHargaEmas(Double.parseDouble(hargaEmasField.getText().replaceAll(",", "")));
                    return Service.savePengaturanUmum(con, sistem);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            getSistem();
            String status = task.getValue();
            if(status.equals("true")){
                mainApp.showMessage(Modality.NONE, "Success", "Pengaturan umum berhasil disimpan");
                close();
                mainApp.mainController.checkTglSystem();
            }else
                mainApp.showMessage(Modality.NONE, "Failed", status);
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    @FXML
    public void backup() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select location to backup");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("zip file", "*.zip"));
            File backupfile = fileChooser.showSaveDialog(stage);
            if(backupfile!=null){
                File file = new File("temp.sql");
                String executeCmd = "mysqldump --host "+Main.ipServer+" -P 3306 "
                        + "-u root -pjagopusat tokoemasjagopusat -r \"" + file.getPath()+"\"";
                Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
                int processComplete = runtimeProcess.waitFor();
                if (processComplete == 0) {
                    FileOutputStream fos = new FileOutputStream(backupfile.getPath());
                    ZipOutputStream zipOut = new ZipOutputStream(fos);
                    FileInputStream fis = new FileInputStream(file);
                    ZipEntry zipEntry = new ZipEntry(file.getName());
                    zipOut.putNextEntry(zipEntry);
                    final byte[] bytes = new byte[1024];
                    int length;
                    while((length = fis.read(bytes)) >= 0) {
                        zipOut.write(bytes, 0, length);
                    }
                    zipOut.close();
                    fis.close();
                    fos.close();
                    file.delete();
                    mainApp.showMessage(Modality.NONE,"Success","Backup Complete");
                } else {
                    mainApp.showMessage(Modality.NONE,"Failed","Backup Failure");
                }
            }
        } catch (IOException | InterruptedException ex) {
            mainApp.showMessage(Modality.NONE, "Error", ex.toString());
        }
    }
    @FXML
    public void restore() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select file backup");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("sql file", "*.sql"));
            File backupfile = fileChooser.showOpenDialog(stage);
            if(backupfile!=null){
                String folderPath = backupfile.getPath();
                String executeCmd = "mysql --host "+Main.ipServer+" -P 3306  -u root -pjagopusat -Dtokoemasjagopusat -e \"source " + folderPath+"\"";
                System.out.println(executeCmd);
                Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
                int processComplete = runtimeProcess.waitFor();
                if (processComplete == 0) {
                    mainApp.showMessage(Modality.NONE,"Success","Restore Complete");
                } else {
                    mainApp.showMessage(Modality.NONE,"Failed","Restore Failure");
                }
            }
        } catch (IOException | InterruptedException ex) {
            mainApp.showMessage(Modality.NONE, "Error", ex.toString());
        }
    }
}
