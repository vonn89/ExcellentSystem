/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.PasarBaja.Model.Helper;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * @author excellent
 */
public class UntungRugiSummary {

    private final DoubleProperty penjualanBefore = new SimpleDoubleProperty();
    private final DoubleProperty hppBefore = new SimpleDoubleProperty();
    private final DoubleProperty salesExpenseBefore = new SimpleDoubleProperty();
    private final DoubleProperty generalAdministrationExpenseBefore = new SimpleDoubleProperty();
    private final DoubleProperty otherIncomeBefore = new SimpleDoubleProperty();
    private final DoubleProperty otherChargeBefore = new SimpleDoubleProperty();
    private final DoubleProperty incomeTaxBefore = new SimpleDoubleProperty();
    
    private final DoubleProperty hpp = new SimpleDoubleProperty();
    private final DoubleProperty penjualan = new SimpleDoubleProperty();
    private final DoubleProperty salesExpense = new SimpleDoubleProperty();
    private final DoubleProperty generalAdministrationExpense = new SimpleDoubleProperty();
    private final DoubleProperty otherIncome = new SimpleDoubleProperty();
    private final DoubleProperty otherCharge = new SimpleDoubleProperty();
    private final DoubleProperty incomeTax = new SimpleDoubleProperty();

    public double getIncomeTaxBefore() {
        return incomeTaxBefore.get();
    }

    public void setIncomeTaxBefore(double value) {
        incomeTaxBefore.set(value);
    }

    public DoubleProperty incomeTaxBeforeProperty() {
        return incomeTaxBefore;
    }

    public double getIncomeTax() {
        return incomeTax.get();
    }

    public void setIncomeTax(double value) {
        incomeTax.set(value);
    }

    public DoubleProperty incomeTaxProperty() {
        return incomeTax;
    }

    public double getOtherChargeBefore() {
        return otherChargeBefore.get();
    }

    public void setOtherChargeBefore(double value) {
        otherChargeBefore.set(value);
    }

    public DoubleProperty otherChargeBeforeProperty() {
        return otherChargeBefore;
    }

    public double getOtherCharge() {
        return otherCharge.get();
    }

    public void setOtherCharge(double value) {
        otherCharge.set(value);
    }

    public DoubleProperty otherChargeProperty() {
        return otherCharge;
    }

    public double getOtherIncomeBefore() {
        return otherIncomeBefore.get();
    }

    public void setOtherIncomeBefore(double value) {
        otherIncomeBefore.set(value);
    }

    public DoubleProperty otherIncomeBeforeProperty() {
        return otherIncomeBefore;
    }

    public double getOtherIncome() {
        return otherIncome.get();
    }

    public void setOtherIncome(double value) {
        otherIncome.set(value);
    }

    public DoubleProperty otherIncomeProperty() {
        return otherIncome;
    }

    public double getGeneralAdministrationExpense() {
        return generalAdministrationExpense.get();
    }

    public void setGeneralAdministrationExpense(double value) {
        generalAdministrationExpense.set(value);
    }

    public DoubleProperty generalAdministrationExpenseProperty() {
        return generalAdministrationExpense;
    }

    public double getGeneralAdministrationExpenseBefore() {
        return generalAdministrationExpenseBefore.get();
    }

    public void setGeneralAdministrationExpenseBefore(double value) {
        generalAdministrationExpenseBefore.set(value);
    }

    public DoubleProperty generalAdministrationExpenseBeforeProperty() {
        return generalAdministrationExpenseBefore;
    }

    public double getSalesExpense() {
        return salesExpense.get();
    }

    public void setSalesExpense(double value) {
        salesExpense.set(value);
    }

    public DoubleProperty salesExpenseProperty() {
        return salesExpense;
    }

    public double getSalesExpenseBefore() {
        return salesExpenseBefore.get();
    }

    public void setSalesExpenseBefore(double value) {
        salesExpenseBefore.set(value);
    }

    public DoubleProperty salesExpenseBeforeProperty() {
        return salesExpenseBefore;
    }

    public double getPenjualan() {
        return penjualan.get();
    }

    public void setPenjualan(double value) {
        penjualan.set(value);
    }

    public DoubleProperty penjualanProperty() {
        return penjualan;
    }

    public double getHpp() {
        return hpp.get();
    }

    public void setHpp(double value) {
        hpp.set(value);
    }

    public DoubleProperty hppProperty() {
        return hpp;
    }

    public double getPenjualanBefore() {
        return penjualanBefore.get();
    }

    public void setPenjualanBefore(double value) {
        penjualanBefore.set(value);
    }

    public DoubleProperty penjualanBeforeProperty() {
        return penjualanBefore;
    }

    public double getHppBefore() {
        return hppBefore.get();
    }

    public void setHppBefore(double value) {
        hppBefore.set(value);
    }

    public DoubleProperty hppBeforeProperty() {
        return hppBefore;
    }

    
}
