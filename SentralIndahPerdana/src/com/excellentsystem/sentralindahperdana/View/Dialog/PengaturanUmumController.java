/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View.Dialog;

import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import static com.excellentsystem.sentralindahperdana.Main.timeout;
import com.excellentsystem.sentralindahperdana.Service.Service;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.sql.Connection;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class PengaturanUmumController {

    @FXML private TextField namaField;
    @FXML private TextField alamatField;
    @FXML private TextField noTelpField;
    @FXML private ImageView image;
    private File file;
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void setMainApp(Main mainApp, Stage owner, Stage stage){
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        namaField.setText(sistem.getNama());
        alamatField.setText(sistem.getAlamat());
        noTelpField.setText(sistem.getNoTelp());
        image.setImage(new Image("file:Image/logo.png",120,120,false,true));
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    public void getImage(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select image file");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("image files", "*.jpg"),new FileChooser.ExtensionFilter("image files", "*.png"));
        file = fileChooser.showOpenDialog(mainApp.MainStage);
        if (file != null) {
            Image img = new Image("file:"+file.getPath(),120,120,false,true);
            image.setImage(img);
        }
    }
    @FXML
    private void saveData(){
        Task<String> task = new Task<String>() {
            @Override 
            public String call() throws Exception{
                Thread.sleep(timeout);
                try(Connection con = Koneksi.getConnection()){
                    sistem.setNama(namaField.getText());
                    sistem.setAlamat(alamatField.getText());
                    sistem.setNoTelp(noTelpField.getText());
                    if(file!=null){
                        Path copyTo = Paths.get("Image/","logo.png");
                        try {
                            Files.copy(file.toPath(), copyTo, REPLACE_EXISTING, COPY_ATTRIBUTES,NOFOLLOW_LINKS);
                        } catch (IOException e) {
                            mainApp.showMessage(Modality.NONE, "Error", "Failed to save image : "+e.toString());
                        }
                    }
                    return Service.savePengaturanUmum(con, sistem);
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
                close();
                MessageController controller = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                        "Setting baru berhasil disimpan,\nto take effect please restart program");
                controller.OK.setOnAction((ActionEvent event) -> {
                    try{
                        mainApp.MainStage.close();
                        mainApp.start(new Stage());
                    }catch(Exception ex){
                        System.exit(0);
                    }
                });
            }else{ 
                mainApp.showMessage(Modality.NONE, "Failed", status);
            }
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
}
