/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View.Dialog;

import com.excellentsystem.TokoEmasJagoPusat.DAO.KeuanganCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.LogHargaEmasDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.SistemDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.StokBarangCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.StokBarangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.StokRosokCabangDAO;
import static com.excellentsystem.TokoEmasJagoPusat.Function.shutdown;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglNormal;
import static com.excellentsystem.TokoEmasJagoPusat.Main.timeout;
import com.excellentsystem.TokoEmasJagoPusat.Model.KeuanganCabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.LogHargaEmas;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class TutupTokoController {

    @FXML private Label tglSystemLabel;
    @FXML private CheckBox autoBackupCheckbox;
    @FXML private CheckBox autoShutDownCheckbox;
    @FXML private ProgressBar progressBar;
    @FXML private Button tutupTokoButton;
    @FXML private Button closeButton;
    
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        progressBar.setProgress(0);
    }    
    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        try{
            this.mainApp = mainApp;
            this.stage = stage;
            this.owner = owner;
            tglSystemLabel.setText(tglNormal.format(tglBarang.parse(sistem.getTglSystem())));
            stage.setOnCloseRequest((event) -> {
                mainApp.closeDialog(owner, stage);
            });
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    private File backupfile;
    @FXML
    private void tutupToko(){
        if(Main.sistem.getTglSystem().equals(tglBarang.format(new Date())))
            mainApp.showMessage(Modality.NONE, "Warning", "tanggal sistem sudah sama dengan tanggal komputer");
        
        progressBar.setProgress(0);
        autoBackupCheckbox.setDisable(true);
        autoShutDownCheckbox.setDisable(true);
        tutupTokoButton.setDisable(true);
        closeButton.setDisable(true);
        
        if(autoBackupCheckbox.isSelected()){
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select location to backup");
            fileChooser.setInitialFileName("Backup database pusat - "+sistem.getTglSystem());
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("zip file", "*.zip"));
            backupfile = fileChooser.showSaveDialog(stage);
        }
        Task<String> task = new Task<String>() {
            @Override 
            public String call() throws Exception{
                Thread.sleep(timeout);
                try(Connection con = KoneksiPusat.getConnection()){
                    try{

                        String status = "true";
                        con.setAutoCommit(false);

                        progressBar.setProgress(0.1);
                        LocalDate besok = LocalDate.parse(Main.sistem.getTglSystem(), 
                                DateTimeFormatter.ISO_DATE).plusDays(1);

                        progressBar.setProgress(0.2);
                        StokBarangDAO.insertStokAwal(con, Main.sistem.getTglSystem(), besok.toString());

                        progressBar.setProgress(0.3);
                        StokRosokCabangDAO.insertStokAwal(con, Main.sistem.getTglSystem(), besok.toString());

                        progressBar.setProgress(0.4);
                        StokBarangCabangDAO.insertStokAwal(con, Main.sistem.getTglSystem(), besok.toString() );

                        progressBar.setProgress(0.5);
                        PreparedStatement ps = con.prepareStatement(
                              " select kode_cabang,kode_gudang,sum(nilai_akhir) "
                            + " from tokoemasjagopusat.tt_stok_barang_cabang " 
                            + " where stok_akhir=0 and nilai_akhir!=0 and tanggal=? "
                            + " group by kode_cabang,kode_gudang");
                        ps.setString(1, Main.sistem.getTglSystem());
                        ResultSet rs = ps.executeQuery();
                        while(rs.next()){
                            String cabang = rs.getString(1);
                            String gudang = rs.getString(2);
                            double nilai = rs.getDouble(3);

                            String noKeuangan = KeuanganCabangDAO.getId(con, cabang, besok.toString());
                            KeuanganCabang kc = new KeuanganCabang();
                            kc.setNoKeuangan(noKeuangan);
                            kc.setTglKeuangan(besok.toString()+" 00:00:00");
                            kc.setKodeCabang(cabang);
                            kc.setTipeKasir("Kasir");
                            kc.setTipeKeuangan(gudang);
                            kc.setKategori("Beban Penyusutan Reset Stok Barang");
                            kc.setDeskripsi("Reset Stok Barang");
                            kc.setJumlahRp(-nilai);
                            kc.setKodeUser("System");
                            KeuanganCabangDAO.insert(con, kc);


                            KeuanganCabang kc2 = new KeuanganCabang();
                            kc2.setNoKeuangan(noKeuangan);
                            kc2.setTglKeuangan(besok.toString()+" 00:00:00");
                            kc2.setKodeCabang(cabang);
                            kc2.setTipeKasir("Kasir");
                            kc2.setTipeKeuangan("Beban Penyusutan Reset Stok Barang");
                            kc2.setKategori("Beban Penyusutan Reset Stok Barang");
                            kc2.setDeskripsi("Reset Stok Barang");
                            kc2.setJumlahRp(-nilai);
                            kc2.setKodeUser("System");
                            KeuanganCabangDAO.insert(con, kc2);

                            System.out.println(noKeuangan);
                        }
                        
                        progressBar.setProgress(0.6);
                        LogHargaEmas l = new LogHargaEmas();
                        l.setTanggal(besok.toString());
                        l.setHargaEmas(Main.sistem.getHargaEmas());
                        LogHargaEmasDAO.insert(con, l);

                        progressBar.setProgress(0.7);
                        Main.sistem.setTglSystem(besok.toString());
                        SistemDAO.update(con, Main.sistem);

                        progressBar.setProgress(0.8);

                        if(status.equals("true"))
                            con.commit();
                        else
                            con.rollback();
                        con.setAutoCommit(true);
                        return status;
                    }catch(Exception e){
                        e.printStackTrace();
                        try{
                            con.rollback();
                            con.setAutoCommit(true);
                            return e.toString();
                        }catch(SQLException ex){
                            return ex.toString();
                        }
                    }
                }
            }
        };
        task.setOnSucceeded((e) -> {
            try{
                String status = task.getValue();
                if(status.equals("true")){
                    mainApp.showMessage(Modality.NONE, "Success", "Tutup toko berhasil");
                    progressBar.setProgress(0.9);
                    if(autoBackupCheckbox.isSelected()){
                        File file = new File("temp.sql");
                        String executeCmd = "mysqldump --host "+Main.ipServer+" -P 3306 "
                                + " -u root -pjagopusat tokoemasjagopusat -r \"" + file.getPath()+"\"";
                        System.out.println(executeCmd);
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
                            mainApp.showMessage(Modality.NONE, "Success", "Backup database berhasil");
                        } else {
                            mainApp.showMessage(Modality.NONE, "Success", "Backup database gagal");
                        }
                    }
                    progressBar.setProgress(1);
                    if(autoShutDownCheckbox.isSelected()){
                        shutdown();
                    }
                    mainApp.closeDialog(owner, stage);
                    System.exit(0);
                }else{
                    progressBar.progressProperty().unbind();
                    progressBar.setProgress(0);
                    autoBackupCheckbox.setDisable(false);
                    autoShutDownCheckbox.setDisable(false);
                    tutupTokoButton.setDisable(false);
                    closeButton.setDisable(false);
                    mainApp.showMessage(Modality.NONE, "Failed", status);
                }
            }catch(Exception ex){
                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
            }
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    
}
