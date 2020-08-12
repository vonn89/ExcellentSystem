/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.sentralindahperdana.View.Dialog;

import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import com.excellentsystem.sentralindahperdana.Model.Customer;
import com.excellentsystem.sentralindahperdana.Model.Otoritas;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    @FXML public TextField bankField;
    @FXML public TextField atasNamaRekeningField;
    @FXML public TextField noRekeningField;
    @FXML public TextField limitHutangField;
    @FXML public TextField hutangField;
    @FXML public TextField depositField;
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
        bankField.setText("");
        atasNamaRekeningField.setText("");
        noRekeningField.setText("");
        limitHutangField.setText("0");
        hutangField.setText("0");
        depositField.setText("0");
        limitHutangField.setDisable(true);
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Set Limit Hutang Customer")&&o.isStatus())
                limitHutangField.setDisable(false);
        }
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
            bankField.setText(customer.getBank());
            atasNamaRekeningField.setText(customer.getAtasNamaRekening());
            noRekeningField.setText(customer.getNoRekening());
            limitHutangField.setText(df.format(customer.getLimitHutang()));
            hutangField.setText(df.format(customer.getHutang()));
            depositField.setText(df.format(customer.getDeposit()));
                
        }
    }
    public void close(){
        mainApp.closeDialog(owner, stage);
    }
        
    
}
