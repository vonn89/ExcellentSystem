/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.Main;
import com.excellentsystem.AuriSteel.Model.Customer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class NamaInvoiceController  {

    @FXML private TextField namaField;
    @FXML private TextArea alamatField;
    @FXML private TextField kotaField;
    @FXML private TextField kontakPersonField;
    @FXML private Button addCustomerButton;
    
    private Main mainApp; 
    private Stage stage;
    private Stage owner;
    public Customer customer;
    public void initialize() {
    }    
    public void setMainApp(Main mainApp,Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    public void setCustomer(Customer c){
        customer = c;
        namaField.setText(customer.getNama());
        alamatField.setText(customer.getAlamat());
        kotaField.setText(customer.getKota());
        kontakPersonField.setText(customer.getKontakPerson());
    }
    public void setViewOnly(){
        addCustomerButton.setVisible(false);
    }
    @FXML
    private void addCustomer(){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddCustomer.fxml");
        AddCustomerController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.customerTable.setRowFactory((param) -> {
            TableRow<Customer> row = new TableRow<Customer>(){};
            row.setOnMouseClicked((MouseEvent evt) -> {
                if(evt.getButton().equals(MouseButton.PRIMARY) && evt.getClickCount()==2){
                    mainApp.closeDialog(stage, child);
                    customer = row.getItem();
                    namaField.setText(customer.getNama());
                    alamatField.setText(customer.getAlamat());
                    kotaField.setText(customer.getKota());
                    kontakPersonField.setText(customer.getKontakPerson());
                }
            });
            return row; 
        });
    }
    public void close(){
        mainApp.closeDialog(owner, stage);
    }   
    
}
