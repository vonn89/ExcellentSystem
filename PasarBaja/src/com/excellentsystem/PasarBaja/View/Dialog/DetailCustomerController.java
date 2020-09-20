/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.PasarBaja.View.Dialog;

import com.excellentsystem.PasarBaja.DAO.PegawaiDAO;
import com.excellentsystem.PasarBaja.Function;
import com.excellentsystem.PasarBaja.Koneksi;
import com.excellentsystem.PasarBaja.Main;
import static com.excellentsystem.PasarBaja.Main.df;
import static com.excellentsystem.PasarBaja.Main.sistem;
import com.excellentsystem.PasarBaja.Model.Customer;
import com.excellentsystem.PasarBaja.Model.Pegawai;
import java.sql.Connection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class DetailCustomerController  {

    @FXML public TextField kodeCustomerField;
    @FXML public TextField namaField;
    @FXML public TextArea alamatField;
    @FXML public TextField kotaField;
    @FXML public TextField negaraField;
    @FXML public TextField kodePosField;
    @FXML public TextField emailField;
    @FXML public TextField kontakPersonField;
    @FXML public TextField noTelpField;
    @FXML public TextField noHandphoneField;
    @FXML public ComboBox<String> kodeSalesCombo;
    @FXML public TextField bankField;
    @FXML public TextField atasNamaRekeningField;
    @FXML public TextField noRekeningField;
    @FXML public TextField limitHutangField;
    @FXML public Button saveButton;
    private Main mainApp;  
    private Stage stage;
    private Stage owner;
    public void setMainApp(Main mainApp,Stage owner,Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        Function.setNumberField(limitHutangField);
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        getPegawai();
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
        task.setOnSucceeded((WorkerStateEvent e) -> {
            mainApp.closeLoading();
            ObservableList<String> allSales = FXCollections.observableArrayList();
            for(Pegawai temp : task.getValue()){
                allSales.add(temp.getKodePegawai()+" - "+temp.getNama());
            }
            kodeSalesCombo.setItems(allSales);
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }
    public void setCustomerDetail(Customer customer){
        kodeCustomerField.setText("");
        namaField.setText("");
        alamatField.setText("");
        kotaField.setText("");
        negaraField.setText("");
        kodePosField.setText("");
        emailField.setText("");
        kontakPersonField.setText("");
        noTelpField.setText("");
        noHandphoneField.setText("");
        kodeSalesCombo.getSelectionModel().select("");
        bankField.setText("");
        atasNamaRekeningField.setText("");
        noRekeningField.setText("");
        limitHutangField.setText("0");
        if(customer!=null){
            kodeCustomerField.setText(customer.getKodeCustomer());
            namaField.setText(customer.getNama());
            alamatField.setText(customer.getAlamat());
            kotaField.setText(customer.getKota());
            negaraField.setText(customer.getNegara());
            kodePosField.setText(customer.getKodePos());
            emailField.setText(customer.getEmail());
            kontakPersonField.setText(customer.getKontakPerson());
            noTelpField.setText(customer.getNoTelp());
            noHandphoneField.setText(customer.getNoHandphone());
            kodeSalesCombo.getSelectionModel().select(customer.getKodeSales()+" - "+customer.getSales().getNama());
            bankField.setText(customer.getBank());
            atasNamaRekeningField.setText(customer.getAtasNamaRekening());
            noRekeningField.setText(customer.getNoRekening());
            limitHutangField.setText(df.format(customer.getLimitHutang()));
            if(!"Manager".equals(sistem.getUser().getLevel()))
                limitHutangField.setDisable(true);
        }
    }
    public void close(){
        mainApp.closeDialog(owner, stage);
    }
        
    
}
