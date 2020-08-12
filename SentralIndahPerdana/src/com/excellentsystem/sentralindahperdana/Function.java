/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana;

import static com.excellentsystem.sentralindahperdana.Main.df;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import com.excellentsystem.sentralindahperdana.Model.OtoritasKeuangan;
import java.text.Annotation;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableCell;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

/**
 *
 * @author yunaz
 */
public class Function {
    public static String toRGBCode( Color color ){
        return String.format( "#%02X%02X%02X",
            (int)( color.getRed() * 255 ),
            (int)( color.getGreen() * 255 ),
            (int)( color.getBlue() * 255 ) );
    }
    public static TableCell getTableCell(){ 
        TableCell cell = new TableCell<Annotation, Number>(){
            @Override
            public void updateItem(Number value, boolean empty) {
                super.updateItem(value, empty);
                if (empty)
                    setText(null);
                else 
                    setText(df.format(value.doubleValue()));
            }
        };
        return cell;
    }
    public static TreeTableCell getTreeTableCell(){
        return new TreeTableCell<Annotation, Number>() {
            @Override 
            public void updateItem(Number value, boolean empty) {
                super.updateItem(value, empty);
                if (empty||value.doubleValue()==0) 
                    setText(null);
                else 
                    setText(df.format(value.doubleValue()));
            }
        };
    }
    public static DateCell getDateCellDisableBefore(LocalDate date){
        return new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                DayOfWeek day = DayOfWeek.from(item);
                if (day == DayOfWeek.SUNDAY) 
                    this.setStyle("-fx-background-color: derive(RED, 100%);");
                if (item.equals(LocalDate.now())) 
                    this.setStyle("-fx-font-weight: bold;");
                if (item.isBefore(date)) 
                    this.setDisable(true);
            }
        };
    }
    public static DateCell getDateCellDisableAfter(LocalDate date){
        return new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                DayOfWeek day = DayOfWeek.from(item);
                if (day == DayOfWeek.SUNDAY) 
                    this.setStyle("-fx-background-color: derive(RED, 100%);");
                if (item.equals(LocalDate.now())) 
                    this.setStyle("-fx-font-weight: bold;");
                if (item.isAfter(date)) 
                    this.setDisable(true);
            }
        };
    }
    public static DateCell getDateCellMulai(DatePicker tglAkhir){
        return new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                DayOfWeek day = DayOfWeek.from(item);
                if (day == DayOfWeek.SUNDAY) 
                    this.setStyle("-fx-background-color: derive(RED, 100%);");
                if (item.equals(LocalDate.now())) 
                    this.setStyle("-fx-font-weight: bold;");
                if(item.isAfter(LocalDate.now()))
                    this.setDisable(true);
                if(item.isAfter(tglAkhir.getValue()))
                    this.setDisable(true);
            }
        };
    }
    public static DateCell getDateCellAkhir(DatePicker tglMulai){
        return new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                DayOfWeek day = DayOfWeek.from(item);
                if (day == DayOfWeek.SUNDAY) 
                    this.setStyle("-fx-background-color: derive(RED, 100%);");
                if (item.equals(LocalDate.now())) 
                    this.setStyle("-fx-font-weight: bold;");
                if(item.isAfter(LocalDate.now()))
                    this.setDisable(true);
                if(item.isBefore(tglMulai.getValue()))
                    this.setDisable(true);
            }
        };
    }
    public static StringConverter getTglConverter(){
        StringConverter<LocalDate> date = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
            @Override 
            public String toString(LocalDate date) {
                if (date != null) 
                    return dateFormatter.format(date);
                else 
                    return "";
            }
            @Override 
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) 
                    return LocalDate.parse(string, dateFormatter);
                else 
                    return null;
            }
        };
        return date;
    }
    public static void setNumberField(TextField field){
        field.setOnKeyReleased((event) -> {
            try{
                String string = field.getText();
                if(string.indexOf(".")>0){
                    String string2 = string.substring(string.indexOf(".")+1, string.length());
                    if(string2.contains("."))
                        field.undo();
                    else if(!string2.equals("") && Double.parseDouble(string2)!=0)
                        field.setText(df.format(Double.parseDouble(string.replaceAll(",", ""))));
                }else
                    field.setText(df.format(Double.parseDouble(string.replaceAll(",", ""))));
                field.end();
            }catch(Exception e){
                field.undo();
            }
        });
    }
    public static ObservableList<String> getTipeKeuanganByUser() {
        ObservableList<String> tipeKeuangan = FXCollections.observableArrayList();
        for(OtoritasKeuangan ok : sistem.getUser().getOtoritasKeuangan()){
            if(ok.isStatus()) tipeKeuangan.add(ok.getKodeKeuangan());
        }
        return tipeKeuangan;
    }
}
