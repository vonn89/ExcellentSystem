/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Xtreme
 */
public class Pegawai {
    private final StringProperty kodePegawai = new SimpleStringProperty();
    private final StringProperty nama = new SimpleStringProperty();
    private final StringProperty jabatan = new SimpleStringProperty();
    private final StringProperty alamat = new SimpleStringProperty();
    private final StringProperty kota = new SimpleStringProperty();
    private final StringProperty noTelp = new SimpleStringProperty();
    private final StringProperty noHandphone = new SimpleStringProperty();
    private final StringProperty identitas = new SimpleStringProperty();
    private final StringProperty noIdentitas = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty tglMulai = new SimpleStringProperty();

    public String getTglMulai() {
        return tglMulai.get();
    }

    public void setTglMulai(String value) {
        tglMulai.set(value);
    }

    public StringProperty tglMulaiProperty() {
        return tglMulai;
    }

    public String getKodePegawai() {
        return kodePegawai.get();
    }

    public void setKodePegawai(String value) {
        kodePegawai.set(value);
    }

    public StringProperty kodePegawaiProperty() {
        return kodePegawai;
    }

    public String getIdentitas() {
        return identitas.get();
    }

    public void setIdentitas(String value) {
        identitas.set(value);
    }

    public StringProperty identitasProperty() {
        return identitas;
    }
    

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String value) {
        email.set(value);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getNoIdentitas() {
        return noIdentitas.get();
    }

    public void setNoIdentitas(String value) {
        noIdentitas.set(value);
    }

    public StringProperty noIdentitasProperty() {
        return noIdentitas;
    }

    public String getNoHandphone() {
        return noHandphone.get();
    }

    public void setNoHandphone(String value) {
        noHandphone.set(value);
    }

    public StringProperty noHandphoneProperty() {
        return noHandphone;
    }

    public String getNoTelp() {
        return noTelp.get();
    }

    public void setNoTelp(String value) {
        noTelp.set(value);
    }

    public StringProperty noTelpProperty() {
        return noTelp;
    }
    

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String value) {
        status.set(value);
    }

    public StringProperty statusProperty() {
        return status;
    }
    

    public String getKota() {
        return kota.get();
    }

    public void setKota(String value) {
        kota.set(value);
    }

    public StringProperty kotaProperty() {
        return kota;
    }

    public String getAlamat() {
        return alamat.get();
    }

    public void setAlamat(String value) {
        alamat.set(value);
    }

    public StringProperty alamatProperty() {
        return alamat;
    }

    public String getJabatan() {
        return jabatan.get();
    }

    public void setJabatan(String value) {
        jabatan.set(value);
    }

    public StringProperty jabatanProperty() {
        return jabatan;
    }

    public String getNama() {
        return nama.get();
    }

    public void setNama(String value) {
        nama.set(value);
    }

    public StringProperty namaProperty() {
        return nama;
    }
    
    
}
