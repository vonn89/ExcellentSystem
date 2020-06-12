/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.Services;

import com.excellentsystem.AuriSteel.DAO.AsetTetapDAO;
import com.excellentsystem.AuriSteel.DAO.BahanDAO;
import com.excellentsystem.AuriSteel.DAO.BarangDAO;
import com.excellentsystem.AuriSteel.DAO.BebanPembelianDAO;
import com.excellentsystem.AuriSteel.DAO.BebanPenjualanDetailDAO;
import com.excellentsystem.AuriSteel.DAO.BebanPenjualanHeadDAO;
import com.excellentsystem.AuriSteel.DAO.BebanProduksiDetailDAO;
import com.excellentsystem.AuriSteel.DAO.BebanProduksiHeadDAO;
import com.excellentsystem.AuriSteel.DAO.CustomerDAO;
import com.excellentsystem.AuriSteel.DAO.GudangDAO;
import com.excellentsystem.AuriSteel.DAO.HutangDAO;
import com.excellentsystem.AuriSteel.DAO.KategoriBahanDAO;
import com.excellentsystem.AuriSteel.DAO.KategoriHutangDAO;
import com.excellentsystem.AuriSteel.DAO.KategoriKeuanganDAO;
import com.excellentsystem.AuriSteel.DAO.KategoriPiutangDAO;
import com.excellentsystem.AuriSteel.DAO.KategoriTransaksiDAO;
import com.excellentsystem.AuriSteel.DAO.KeuanganDAO;
import com.excellentsystem.AuriSteel.DAO.LogBahanDAO;
import com.excellentsystem.AuriSteel.DAO.LogBarangDAO;
import com.excellentsystem.AuriSteel.DAO.OtoritasDAO;
import com.excellentsystem.AuriSteel.DAO.PegawaiDAO;
import com.excellentsystem.AuriSteel.DAO.PembayaranDAO;
import com.excellentsystem.AuriSteel.DAO.PembelianBarangDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PembelianBarangHeadDAO;
import com.excellentsystem.AuriSteel.DAO.PembelianDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PembelianHeadDAO;
import com.excellentsystem.AuriSteel.DAO.PemesananCoilDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PemesananCoilHeadDAO;
import com.excellentsystem.AuriSteel.DAO.PemesananDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PemesananHeadDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanCoilDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanCoilHeadDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanHeadDAO;
import com.excellentsystem.AuriSteel.DAO.PenyesuaianStokBahanDAO;
import com.excellentsystem.AuriSteel.DAO.PenyesuaianStokBarangDAO;
import com.excellentsystem.AuriSteel.DAO.PindahBahanDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PindahBahanHeadDAO;
import com.excellentsystem.AuriSteel.DAO.PindahBarangDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PindahBarangHeadDAO;
import com.excellentsystem.AuriSteel.DAO.PiutangDAO;
import com.excellentsystem.AuriSteel.DAO.ProduksiDetailBahanDAO;
import com.excellentsystem.AuriSteel.DAO.ProduksiDetailBarangDAO;
import com.excellentsystem.AuriSteel.DAO.ProduksiHeadDAO;
import com.excellentsystem.AuriSteel.DAO.ProduksiOperatorDAO;
import com.excellentsystem.AuriSteel.DAO.StokBahanDAO;
import com.excellentsystem.AuriSteel.DAO.StokBarangDAO;
import com.excellentsystem.AuriSteel.DAO.SupplierDAO;
import com.excellentsystem.AuriSteel.DAO.TerimaPembayaranDAO;
import com.excellentsystem.AuriSteel.DAO.UserDAO;
import com.excellentsystem.AuriSteel.Function;
import static com.excellentsystem.AuriSteel.Main.sistem;
import static com.excellentsystem.AuriSteel.Main.tglBarang;
import static com.excellentsystem.AuriSteel.Main.tglSql;
import com.excellentsystem.AuriSteel.Model.AsetTetap;
import com.excellentsystem.AuriSteel.Model.Bahan;
import com.excellentsystem.AuriSteel.Model.Barang;
import com.excellentsystem.AuriSteel.Model.BebanPembelian;
import com.excellentsystem.AuriSteel.Model.BebanPenjualanDetail;
import com.excellentsystem.AuriSteel.Model.BebanPenjualanHead;
import com.excellentsystem.AuriSteel.Model.BebanProduksiDetail;
import com.excellentsystem.AuriSteel.Model.BebanProduksiHead;
import com.excellentsystem.AuriSteel.Model.Customer;
import com.excellentsystem.AuriSteel.Model.Gudang;
import com.excellentsystem.AuriSteel.Model.Hutang;
import com.excellentsystem.AuriSteel.Model.KategoriBahan;
import com.excellentsystem.AuriSteel.Model.KategoriHutang;
import com.excellentsystem.AuriSteel.Model.KategoriKeuangan;
import com.excellentsystem.AuriSteel.Model.KategoriPiutang;
import com.excellentsystem.AuriSteel.Model.KategoriTransaksi;
import com.excellentsystem.AuriSteel.Model.Keuangan;
import com.excellentsystem.AuriSteel.Model.LogBahan;
import com.excellentsystem.AuriSteel.Model.LogBarang;
import com.excellentsystem.AuriSteel.Model.Otoritas;
import com.excellentsystem.AuriSteel.Model.Pegawai;
import com.excellentsystem.AuriSteel.Model.Pembayaran;
import com.excellentsystem.AuriSteel.Model.PembelianBarangDetail;
import com.excellentsystem.AuriSteel.Model.PembelianBarangHead;
import com.excellentsystem.AuriSteel.Model.PembelianDetail;
import com.excellentsystem.AuriSteel.Model.PembelianHead;
import com.excellentsystem.AuriSteel.Model.PemesananCoilDetail;
import com.excellentsystem.AuriSteel.Model.PemesananCoilHead;
import com.excellentsystem.AuriSteel.Model.PemesananDetail;
import com.excellentsystem.AuriSteel.Model.PemesananHead;
import com.excellentsystem.AuriSteel.Model.PenjualanCoilDetail;
import com.excellentsystem.AuriSteel.Model.PenjualanCoilHead;
import com.excellentsystem.AuriSteel.Model.PenjualanDetail;
import com.excellentsystem.AuriSteel.Model.PenjualanHead;
import com.excellentsystem.AuriSteel.Model.PenyesuaianStokBahan;
import com.excellentsystem.AuriSteel.Model.PenyesuaianStokBarang;
import com.excellentsystem.AuriSteel.Model.PindahBahanDetail;
import com.excellentsystem.AuriSteel.Model.PindahBahanHead;
import com.excellentsystem.AuriSteel.Model.PindahBarangDetail;
import com.excellentsystem.AuriSteel.Model.PindahBarangHead;
import com.excellentsystem.AuriSteel.Model.Piutang;
import com.excellentsystem.AuriSteel.Model.ProduksiDetailBahan;
import com.excellentsystem.AuriSteel.Model.ProduksiDetailBarang;
import com.excellentsystem.AuriSteel.Model.ProduksiHead;
import com.excellentsystem.AuriSteel.Model.ProduksiOperator;
import com.excellentsystem.AuriSteel.Model.StokBahan;
import com.excellentsystem.AuriSteel.Model.StokBarang;
import com.excellentsystem.AuriSteel.Model.Supplier;
import com.excellentsystem.AuriSteel.Model.TerimaPembayaran;
import com.excellentsystem.AuriSteel.Model.User;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class Service {
    private static void insertKeuangan(Connection con, String noKeuangan, String tanggal, String tipeKeuangan, 
            String kategori, String deskripsi, double jumlahRp, String kodeUser)throws Exception{
        Keuangan k = new Keuangan();
        k.setNoKeuangan(noKeuangan);
        k.setTglKeuangan(tanggal);
        k.setTipeKeuangan(tipeKeuangan);
        k.setKategori(kategori);
        k.setDeskripsi(deskripsi);
        k.setJumlahRp(jumlahRp);
        k.setKodeUser(kodeUser);
        KeuanganDAO.insert(con, k);
    }
    private static void insertStokAndLogBahan(Connection con, String kodeBahan, String kodeGudang, String kategori, String keterangan, 
            double qtyIn, double nilaiIn, double qtyOut, double nilaiOut)throws Exception{
        StokBahan stokBahan = StokBahanDAO.get(con, tglBarang.format(Function.getServerDate(con)), kodeBahan, kodeGudang);
        if(stokBahan.getTanggal().equals(tglBarang.format(Function.getServerDate(con)))){
            stokBahan.setStokMasuk(stokBahan.getStokMasuk()+qtyIn);
            stokBahan.setStokKeluar(stokBahan.getStokKeluar()+qtyOut);
            stokBahan.setStokAkhir(stokBahan.getStokAkhir()+qtyIn-qtyOut);
            StokBahanDAO.update(con, stokBahan);
        }else{
            StokBahan stok = new StokBahan();
            stok.setTanggal(tglBarang.format(Function.getServerDate(con)));
            stok.setKodeBahan(stokBahan.getKodeBahan());
            stok.setKodeGudang(stokBahan.getKodeGudang());
            stok.setStokAwal(stokBahan.getStokAkhir());
            stok.setStokMasuk(qtyIn);
            stok.setStokKeluar(qtyOut);
            stok.setStokAkhir(stokBahan.getStokAkhir()+qtyIn-qtyOut);
            StokBahanDAO.insert(con, stok);
        }
        LogBahan logUmum = LogBahanDAO.getLastByBahanAndGudang(con, kodeBahan, kodeGudang);
        LogBahan logBahan = new LogBahan();
        logBahan.setTanggal(tglSql.format(Function.getServerDate(con)));
        logBahan.setKodeBahan(logUmum.getKodeBahan());
        logBahan.setKodeGudang(logUmum.getKodeGudang());
        logBahan.setKategori(kategori);
        logBahan.setKeterangan(keterangan);
        logBahan.setStokAwal(logUmum.getStokAkhir());
        logBahan.setNilaiAwal(logUmum.getNilaiAkhir());
        logBahan.setStokMasuk(qtyIn);
        logBahan.setNilaiMasuk(nilaiIn);
        logBahan.setStokKeluar(qtyOut);
        logBahan.setNilaiKeluar(nilaiOut);
        logBahan.setStokAkhir(logUmum.getStokAkhir()+qtyIn-qtyOut);
        logBahan.setNilaiAkhir(logUmum.getNilaiAkhir()+nilaiIn-nilaiOut);
        LogBahanDAO.insert(con, logBahan);
    }
    private static void insertStokAndLogBarang(Connection con, String kodeBarang, String kodeGudang, String kategori, String keterangan, 
            double qtyIn, double nilaiIn, double qtyOut, double nilaiOut)throws Exception{
        if(qtyIn!=0 || qtyOut!=0){
            StokBarang stokBarang = StokBarangDAO.get(con, tglBarang.format(Function.getServerDate(con)), kodeBarang, kodeGudang);
            if(stokBarang.getTanggal().equals(tglBarang.format(Function.getServerDate(con)))){
                stokBarang.setStokMasuk(stokBarang.getStokMasuk()+qtyIn);
                stokBarang.setStokKeluar(stokBarang.getStokKeluar()+qtyOut);
                stokBarang.setStokAkhir(stokBarang.getStokAkhir()+qtyIn-qtyOut);
                StokBarangDAO.update(con, stokBarang);
            }else{
                StokBarang stok = new StokBarang();
                stok.setTanggal(tglBarang.format(Function.getServerDate(con)));
                stok.setKodeBarang(stokBarang.getKodeBarang());
                stok.setKodeGudang(stokBarang.getKodeGudang());
                stok.setStokAwal(stokBarang.getStokAkhir());
                stok.setStokMasuk(qtyIn);
                stok.setStokKeluar(qtyOut);
                stok.setStokAkhir(stokBarang.getStokAkhir()+qtyIn-qtyOut);
                StokBarangDAO.insert(con, stok);
            }
            LogBarang logUmum = LogBarangDAO.getLastByBarangAndGudang(con, kodeBarang, kodeGudang);
            LogBarang logBarang = new LogBarang();
            logBarang.setTanggal(tglSql.format(Function.getServerDate(con)));
            logBarang.setKodeBarang(logUmum.getKodeBarang());
            logBarang.setKodeGudang(logUmum.getKodeGudang());
            logBarang.setKategori(kategori);
            logBarang.setKeterangan(keterangan);
            logBarang.setStokAwal(logUmum.getStokAkhir());
            logBarang.setNilaiAwal(logUmum.getNilaiAkhir());
            logBarang.setStokMasuk(qtyIn);
            logBarang.setNilaiMasuk(nilaiIn);
            logBarang.setStokKeluar(qtyOut);
            logBarang.setNilaiKeluar(nilaiOut);
            logBarang.setStokAkhir(logUmum.getStokAkhir()+qtyIn-qtyOut);
            logBarang.setNilaiAkhir(logUmum.getNilaiAkhir()+nilaiIn-nilaiOut);
            LogBarangDAO.insert(con, logBarang);
        }
    }
    
    public static String setPenyusutanAset(Connection con){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            LocalDate now = LocalDate.parse(tglBarang.format(Function.getServerDate(con)), DateTimeFormatter.ISO_DATE);
            List<Keuangan> listKeuanganAsetTetap = KeuanganDAO.getAllByTipeKeuangan(con, "Aset Tetap");
            for(AsetTetap aset : AsetTetapDAO.getAllByStatus(con, "open")){
                if(aset.getMasaPakai()!=0){
                    LocalDate tglBeli = LocalDate.parse(aset.getTglBeli(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    int selisih = ((now.getYear()-tglBeli.getYear())*12) + (now.getMonthValue()-tglBeli.getMonthValue());
                    if(selisih <= aset.getMasaPakai()){
                        double totalPenyusutan = 0;
                        double penyusutanPerbulan = aset.getNilaiAwal()/aset.getMasaPakai();
                        for(int i=1 ; i<=selisih ; i++){
                            LocalDate tglSusut = tglBeli.plusMonths(i);
                            if(tglSusut.isBefore(now)||tglSusut.isEqual(now)){
                                boolean statusInput = true;
                                for(Keuangan k : listKeuanganAsetTetap){
                                    if(k.getDeskripsi().equals("Penyusutan Aset Tetap Ke-"+i+" ("+aset.getNoAset()+")"))
                                        statusInput = false;
                                }
                                if(statusInput){
                                    String noKeuangan = KeuanganDAO.getId(con);
                                    insertKeuangan(con, noKeuangan, tglSusut.toString()+" 00:00:00", "Aset Tetap", aset.getKategori(), 
                                            "Penyusutan Aset Tetap Ke-"+i+" ("+aset.getNoAset()+")", -penyusutanPerbulan, "System");
                                    insertKeuangan(con, noKeuangan, tglSusut.toString()+" 00:00:00", "Pendapatan/Beban", "Beban Penyusutan Aset Tetap", 
                                            "Penyusutan Aset Tetap Ke-"+i+" ("+aset.getNoAset()+")", -penyusutanPerbulan, "System");
                                }
                                totalPenyusutan = totalPenyusutan + penyusutanPerbulan;
                            }
                        }
                        aset.setPenyusutan(totalPenyusutan);
                        aset.setNilaiAkhir(aset.getNilaiAwal()-totalPenyusutan);
                        AsetTetapDAO.update(con, aset);
                    }
                }
            }
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }
    
    public static String updateBahan(Connection con, Bahan b){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            BahanDAO.update(con, b);
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }
    
    public static String newBarang(Connection con, Barang barang){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            if(BarangDAO.get(con, barang.getKodeBarang())!=null){
                status="Kode barang sudah pernah terdaftar";
            }else{
                BarangDAO.insert(con,barang);
                
                for(Gudang g : sistem.getListGudang()){
                    LogBarang log = new LogBarang();
                    log.setTanggal(tglSql.format(Function.getServerDate(con)));
                    log.setKodeBarang(barang.getKodeBarang());
                    log.setKodeGudang(g.getKodeGudang());
                    log.setKategori("New Barang");
                    log.setKeterangan("");
                    log.setStokAwal(0);
                    log.setNilaiAwal(0);
                    log.setStokMasuk(0);
                    log.setNilaiMasuk(0);
                    log.setStokKeluar(0);
                    log.setNilaiKeluar(0);
                    log.setStokAkhir(0);
                    log.setNilaiAkhir(0);
                    LogBarangDAO.insert(con, log);

                    StokBarang stok = new StokBarang();
                    stok.setTanggal(tglBarang.format(Function.getServerDate(con)));
                    stok.setKodeBarang(barang.getKodeBarang());
                    stok.setKodeGudang(g.getKodeGudang());
                    stok.setStokAwal(0);
                    stok.setStokMasuk(0);
                    stok.setStokKeluar(0);
                    stok.setStokAkhir(0);
                    StokBarangDAO.insert(con, stok);
                }
            }
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }
    public static String updateBarang(Connection con, Barang barang){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            BarangDAO.update(con,barang);
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }
    public static String deleteBarang(Connection con, Barang barang){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            double stokAkhir = 0;
            for(Gudang g : sistem.getListGudang()){
                LogBarang log = LogBarangDAO.getLastByBarangAndGudang(con, barang.getKodeBarang(), g.getKodeGudang());
                stokAkhir = stokAkhir + log.getStokAkhir();
            }
            if(stokAkhir>0){
                status = "Barang tidak dapat dihapus, karena stok barang masih ada";
            }else{
                barang.setStatus("false");
                BarangDAO.update(con, barang);
            }
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }
    
    public static String newCustomer(Connection con, Customer customer){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            CustomerDAO.insert(con, customer);
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }
    public static String updateCustomer(Connection con, Customer customer){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            CustomerDAO.update(con, customer);
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }
    public static String deleteCustomer(Connection con, Customer customer){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            if(customer.getDeposit()!=0||customer.getHutang()!=0)
                status = "Customer tidak dapat dihapus, karena masih memiliki hutang/deposit";
            else{
                customer.setStatus("false");
                CustomerDAO.update(con, customer);
            }
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }
    
    public static String newPegawai(Connection con, Pegawai pegawai){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            PegawaiDAO.insert(con, pegawai);
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }
    public static String updatePegawai(Connection con, Pegawai pegawai){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            PegawaiDAO.update(con, pegawai);
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }
    public static String deletePegawai(Connection con, Pegawai pegawai){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            List<Customer> listCustomer = CustomerDAO.getAllByStatus(con, "true");
            for(Customer c : listCustomer){
                if(c.getKodeSales().equals(pegawai.getKodePegawai()))
                    status = "tidak dapat dihapus, karena pegawai masih terdaftar sebagai sales pada salah satu customer";
            }
            if(status.equals("true")){
                pegawai.setStatus("false");
                PegawaiDAO.update(con, pegawai);
            }
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }
    
    public static String newSupplier(Connection con, Supplier supplier){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            SupplierDAO.insert(con, supplier);
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }
    public static String updateSupplier(Connection con, Supplier supplier){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            SupplierDAO.update(con, supplier);
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }
    public static String deleteSupplier(Connection con, Supplier supplier){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            if(supplier.getDeposit()!=0||supplier.getHutang()!=0)
                status = "tidak dapat dihapus, karena masih memiliki hutang/deposit";
            
            if(status.equals("true")){
                supplier.setStatus("false");
                SupplierDAO.update(con, supplier);
            }
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }
    
    public static String newUser(Connection con, User user){
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            for(User u : UserDAO.getAll(con)){
                if(user.getKodeUser().equals(u.getKodeUser()))
                    status = "Username sudah pernah terdaftar";
            }
            UserDAO.insert(con, user);
            for(Otoritas o : user.getOtoritas()){
                OtoritasDAO.insert(con, o);
            }
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return "true";
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String updateUser(Connection con, User user){
        try{
            con.setAutoCommit(false);
            
            UserDAO.update(con, user);
            OtoritasDAO.delete(con, user);
            for(Otoritas o : user.getOtoritas()){
                OtoritasDAO.insert(con, o);
            }
            
            con.commit();
            con.setAutoCommit(true);
            return "true";
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String deleteUser(Connection con, User user){
        try{
            con.setAutoCommit(false);
            
            user.setStatus("false");
            UserDAO.update(con, user);
            OtoritasDAO.delete(con, user);
            
            con.commit();
            con.setAutoCommit(true);
            return "true";
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
//    public static String saveAbsensiManual(Connection con ,Absensi a){
//        try{
//            con.setAutoCommit(false);
//            String status = "true";
//            
//            Absensi alama = AbsensiDAO.get(con, a.getTanggal(), a.getKodePegawai());
//            if(alama==null){
//                AbsensiDAO.insert(con, a);
//            }else{
//                AbsensiDAO.update(con, a);
//            }
//            
//            if(status.equals("true"))
//                con.commit();
//            else
//                con.rollback();
//            con.setAutoCommit(true);
//            return status;
//        }catch(Exception e){
//            try{
//                con.rollback();
//                con.setAutoCommit(true);
//                return e.toString();
//            }catch(SQLException ex){
//                return ex.toString();
//            }
//        }
//    }
    
    public static String newKategoriBahan(Connection con, KategoriBahan k)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            KategoriBahanDAO.insert(con, k);

            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String deleteKategoriBahan(Connection con, KategoriBahan k)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            k.setStatus("false");
            KategoriBahanDAO.update(con, k);

            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String newGudang(Connection con, Gudang k)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            GudangDAO.insert(con, k);

            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String deleteGudang(Connection con, Gudang k)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            GudangDAO.delete(con, k);

            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String newKategoriKeuangan(Connection con, KategoriKeuangan t)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            KategoriKeuanganDAO.insert(con, t);

            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String deleteKategoriKeuangan(Connection con, KategoriKeuangan t)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            double saldo = KeuanganDAO.getSaldoAkhir(con, tglBarang.format(Function.getServerDate(con)), t.getKodeKeuangan());
            if(saldo!=0)
                status = "Tidak dapat dihapus,karena saldo "+t.getKodeKeuangan()+" masih ada";
            else{
                KategoriKeuanganDAO.delete(con, t);
            }
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String newKategoriTransaksi(Connection con, KategoriTransaksi k)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            KategoriTransaksiDAO.insert(con, k);

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
    public static String updateKategoriTransaksi(Connection con, KategoriTransaksi k)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            KategoriTransaksiDAO.update(con, k);

            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String deleteKategoriTransaksi(Connection con, KategoriTransaksi k)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            k.setStatus("false");
            KategoriTransaksiDAO.update(con, k);

            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String newKategoriHutang(Connection con, KategoriHutang k)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            KategoriHutangDAO.insert(con, k);

            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String deleteKategoriHutang(Connection con, KategoriHutang k)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            KategoriHutangDAO.delete(con, k);

            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String newKategoriPiutang(Connection con, KategoriPiutang k)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            KategoriPiutangDAO.insert(con, k);

            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String deleteKategoriPiutang(Connection con, KategoriPiutang k)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            KategoriPiutangDAO.delete(con, k);

            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String newPemesanan(Connection con, PemesananHead pemesanan){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            PemesananHeadDAO.insert(con, pemesanan);
            for(PemesananDetail detail : pemesanan.getListPemesananDetail()){
                PemesananDetailDAO.insert(con, detail);
            }
            
            if(status.equals("true"))
                con.commit();
            else 
                con.rollback();
            con.setAutoCommit(true);
            
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String editPemesanan(Connection con, PemesananHead p){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            PemesananHeadDAO.update(con, p);
            PemesananDetailDAO.delete(con, p.getNoPemesanan());
            for(PemesananDetail detail : p.getListPemesananDetail()){
                PemesananDetailDAO.insert(con, detail);
            }
            
            if(status.equals("true"))
                con.commit();
            else 
                con.rollback();
            con.setAutoCommit(true);
            
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String batalPemesanan(Connection con, PemesananHead pemesanan){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            PemesananHeadDAO.update(con, pemesanan);
            
            if(status.equals("true"))
                con.commit();
            else 
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String selesaiPemesanan(Connection con, PemesananHead pemesanan){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            PemesananHeadDAO.update(con, pemesanan);
            
            if(status.equals("true"))
                con.commit();
            else 
                con.rollback();
            con.setAutoCommit(true);
            
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String newPemesananCoil(Connection con, PemesananCoilHead pemesanan){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            PemesananCoilHeadDAO.insert(con, pemesanan);
            for(PemesananCoilDetail detail : pemesanan.getListPemesananCoilDetail()){
                PemesananCoilDetailDAO.insert(con, detail);
            }
            
            if(status.equals("true"))
                con.commit();
            else 
                con.rollback();
            con.setAutoCommit(true);
            
            return status;
        }catch(Exception e){
            try{
                e.printStackTrace();
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String batalPemesananCoil(Connection con, PemesananCoilHead pemesanan){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            List<PenjualanCoilHead> listPenjualan = PenjualanCoilHeadDAO.getAllByNoPemesananAndStatus(
                    con, pemesanan.getNoPemesanan(), "true");
            if(listPenjualan.isEmpty())
                PemesananCoilHeadDAO.update(con, pemesanan);
            else
                status = "Pemesanan tidak dapat dibatalkan, karena sudah ada penjualan";
            
            if(status.equals("true"))
                con.commit();
            else 
                con.rollback();
            con.setAutoCommit(true);
            
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String selesaiPemesananCoil(Connection con, PemesananCoilHead pemesanan){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            PemesananCoilHeadDAO.update(con, pemesanan);
            
            if(status.equals("true"))
                con.commit();
            else 
                con.rollback();
            con.setAutoCommit(true);
            
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String newTerimaDownPayment(Connection con, PemesananHead pemesanan, double jumlahBayar, String tipeKeuangan){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            PemesananHeadDAO.update(con, pemesanan);
            
            Customer customer = pemesanan.getCustomer();
            customer.setDeposit(customer.getDeposit()+jumlahBayar);
            CustomerDAO.update(con, customer);

            Hutang h = new Hutang();
            h.setNoHutang(HutangDAO.getId(con));
            h.setTglHutang(tglSql.format(Function.getServerDate(con)));
            h.setKategori("Terima Pembayaran Down Payment");
            h.setKeterangan(pemesanan.getNoPemesanan());
            h.setTipeKeuangan(tipeKeuangan);
            h.setJumlahHutang(jumlahBayar);
            h.setPembayaran(0);
            h.setSisaHutang(jumlahBayar);
            h.setJatuhTempo("2000-01-01");
            h.setKodeUser(sistem.getUser().getKodeUser());
            h.setStatus("open");
            HutangDAO.insert(con, h);

            String noKeuangan = KeuanganDAO.getId(con);
            insertKeuangan(con, noKeuangan, tglSql.format(Function.getServerDate(con)), tipeKeuangan, "Terima Pembayaran Down Payment", 
                    "Terima Pembayaran Down Payment - "+pemesanan.getNoPemesanan()+" ("+h.getNoHutang()+")", jumlahBayar, sistem.getUser().getKodeUser());
            insertKeuangan(con, noKeuangan, tglSql.format(Function.getServerDate(con)), "Hutang", "Terima Pembayaran Down Payment", 
                    "Terima Pembayaran Down Payment - "+pemesanan.getNoPemesanan()+" ("+h.getNoHutang()+")", jumlahBayar, sistem.getUser().getKodeUser());
            
            if(status.equals("true"))
                con.commit();
            else 
                con.rollback();
            con.setAutoCommit(true);
            
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String batalTerimaDownPayment(Connection con, Hutang h){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            HutangDAO.delete(con, h);
            
            PemesananHead pemesanan = h.getPemesananHead();
            pemesanan.setDownPayment(pemesanan.getDownPayment()-h.getJumlahHutang());
            pemesanan.setSisaDownPayment(pemesanan.getSisaDownPayment()-h.getJumlahHutang());
            PemesananHeadDAO.update(con, pemesanan);
            
            Customer customer = pemesanan.getCustomer();
            customer.setDeposit(customer.getDeposit()-h.getJumlahHutang());
            CustomerDAO.update(con, customer);
            
            KeuanganDAO.delete(con, h.getTipeKeuangan(), "Terima Pembayaran Down Payment", 
                    "Terima Pembayaran Down Payment - "+pemesanan.getNoPemesanan()+" ("+h.getNoHutang()+")");
            KeuanganDAO.delete(con, "Hutang", "Terima Pembayaran Down Payment", 
                    "Terima Pembayaran Down Payment - "+pemesanan.getNoPemesanan()+" ("+h.getNoHutang()+")");
            
            if(status.equals("true"))
                con.commit();
            else 
                con.rollback();
            con.setAutoCommit(true);
            
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String newTerimaDownPaymentCoil(Connection con, PemesananCoilHead pemesanan, double jumlahBayar, String tipeKeuangan){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            PemesananCoilHeadDAO.update(con, pemesanan);
            
            Customer customer = pemesanan.getCustomer();
            customer.setDeposit(customer.getDeposit()+jumlahBayar);
            CustomerDAO.update(con, customer);

            Hutang h = new Hutang();
            h.setNoHutang(HutangDAO.getId(con));
            h.setTglHutang(tglSql.format(Function.getServerDate(con)));
            h.setKategori("Terima Pembayaran Down Payment");
            h.setKeterangan(pemesanan.getNoPemesanan());
            h.setTipeKeuangan(tipeKeuangan);
            h.setJumlahHutang(jumlahBayar);
            h.setPembayaran(0);
            h.setSisaHutang(jumlahBayar);
            h.setJatuhTempo("2000-01-01");
            h.setKodeUser(sistem.getUser().getKodeUser());
            h.setStatus("open");
            HutangDAO.insert(con, h);

            String noKeuangan = KeuanganDAO.getId(con);
            
            insertKeuangan(con, noKeuangan, tglSql.format(Function.getServerDate(con)), tipeKeuangan, "Terima Pembayaran Down Payment", 
                    "Terima Pembayaran Down Payment - "+pemesanan.getNoPemesanan()+" ("+h.getNoHutang()+")", jumlahBayar, sistem.getUser().getKodeUser());
            insertKeuangan(con, noKeuangan, tglSql.format(Function.getServerDate(con)), "Hutang", "Terima Pembayaran Down Payment", 
                    "Terima Pembayaran Down Payment - "+pemesanan.getNoPemesanan()+" ("+h.getNoHutang()+")", jumlahBayar, sistem.getUser().getKodeUser());
            
            if(status.equals("true"))
                con.commit();
            else 
                con.rollback();
            con.setAutoCommit(true);
            
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String batalTerimaDownPaymentCoil(Connection con, Hutang h){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            HutangDAO.delete(con, h);
            
            PemesananCoilHead pemesanan = h.getPemesananCoilHead();
            pemesanan.setDownPayment(pemesanan.getDownPayment()-h.getJumlahHutang());
            pemesanan.setSisaDownPayment(pemesanan.getSisaDownPayment()-h.getJumlahHutang());
            PemesananCoilHeadDAO.update(con, pemesanan);
            
            Customer customer = pemesanan.getCustomer();
            customer.setDeposit(customer.getDeposit()-h.getJumlahHutang());
            CustomerDAO.update(con, customer);
            
            KeuanganDAO.delete(con, h.getTipeKeuangan(), "Terima Pembayaran Down Payment", 
                    "Terima Pembayaran Down Payment - "+pemesanan.getNoPemesanan()+" ("+h.getNoHutang()+")");
            KeuanganDAO.delete(con, "Hutang", "Terima Pembayaran Down Payment", 
                    "Terima Pembayaran Down Payment - "+pemesanan.getNoPemesanan()+" ("+h.getNoHutang()+")");
            
            if(status.equals("true"))
                con.commit();
            else 
                con.rollback();
            con.setAutoCommit(true);
            
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String newPenjualan(Connection con, PenjualanHead penjualan){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            PenjualanHeadDAO.insert(con, penjualan);
            
            String noKeuangan = KeuanganDAO.getId(con);
            insertKeuangan(con, noKeuangan, tglSql.format(Function.getServerDate(con)), "Penjualan", "Penjualan", 
                    "Penjualan - "+penjualan.getNoPenjualan(), penjualan.getTotalPenjualan(), sistem.getUser().getKodeUser());
            insertKeuangan(con, noKeuangan, tglSql.format(Function.getServerDate(con)), "Piutang", "Piutang Penjualan", 
                    "Penjualan - "+penjualan.getNoPenjualan(), penjualan.getSisaPembayaran(), sistem.getUser().getKodeUser());
            
            Piutang piutang = new Piutang();
            piutang.setNoPiutang(PiutangDAO.getId(con));
            piutang.setTglPiutang(tglSql.format(Function.getServerDate(con)));
            piutang.setKategori("Piutang Penjualan");
            piutang.setKeterangan(penjualan.getNoPenjualan());
            piutang.setTipeKeuangan("Penjualan");
            piutang.setJumlahPiutang(penjualan.getTotalPenjualan());
            piutang.setPembayaran(penjualan.getPembayaran());
            piutang.setSisaPiutang(penjualan.getSisaPembayaran());
            piutang.setJatuhTempo("2000-01-01");
            piutang.setKodeUser(sistem.getUser().getKodeUser());
            if(piutang.getSisaPiutang()>0)
                piutang.setStatus("open");
            else
                piutang.setStatus("close");
            PiutangDAO.insert(con, piutang);

            Customer customer = CustomerDAO.get(con, penjualan.getKodeCustomer());
            if(penjualan.getSisaPembayaran()>0)
                customer.setHutang(customer.getHutang()+penjualan.getSisaPembayaran());
            
            PemesananHead pemesanan = penjualan.getPemesananHead();
            if(penjualan.getPembayaran()>0){
                customer.setDeposit(customer.getDeposit()-penjualan.getPembayaran());
                
                pemesanan.setSisaDownPayment(pemesanan.getSisaDownPayment()-penjualan.getPembayaran());

                TerimaPembayaran tp = new TerimaPembayaran();
                tp.setNoTerimaPembayaran(TerimaPembayaranDAO.getId(con));
                tp.setTglTerima(tglSql.format(Function.getServerDate(con)));
                tp.setNoPiutang(piutang.getNoPiutang());
                tp.setJumlahPembayaran(penjualan.getPembayaran());
                tp.setTipeKeuangan("Down Payment");
                tp.setCatatan(penjualan.getNoPemesanan());
                tp.setKodeUser(sistem.getUser().getKodeUser());
                tp.setTglBatal("2000-01-01 00:00:00");
                tp.setUserBatal("");
                tp.setStatus("true");
                TerimaPembayaranDAO.insert(con, tp);

                double bayar = penjualan.getPembayaran();
                List<Hutang> listHutang = HutangDAO.getAllByKategoriAndKeteranganAndStatus(
                        con, "Terima Pembayaran Down Payment",pemesanan.getNoPemesanan(),"%");
                for(Hutang h : listHutang){
                    if(h.getSisaHutang()>bayar){
                        Pembayaran pembayaran = new Pembayaran();
                        pembayaran.setNoPembayaran(PembayaranDAO.getId(con));
                        pembayaran.setTglPembayaran(tglSql.format(Function.getServerDate(con)));
                        pembayaran.setNoHutang(h.getNoHutang());
                        pembayaran.setJumlahPembayaran(bayar);
                        pembayaran.setTipeKeuangan("Penjualan");
                        pembayaran.setCatatan(penjualan.getNoPenjualan());
                        pembayaran.setKodeUser(sistem.getUser().getKodeUser());
                        pembayaran.setTglBatal("2000-01-01 00:00:00");
                        pembayaran.setUserBatal("");
                        pembayaran.setStatus("true");
                        PembayaranDAO.insert(con, pembayaran);
                        
                        h.setPembayaran(h.getPembayaran()+bayar);
                        h.setSisaHutang(h.getSisaHutang()-bayar);
                        HutangDAO.update(con, h);
                        
                        bayar = 0;
                    }else{
                        Pembayaran pembayaran = new Pembayaran();
                        pembayaran.setNoPembayaran(PembayaranDAO.getId(con));
                        pembayaran.setTglPembayaran(tglSql.format(Function.getServerDate(con)));
                        pembayaran.setNoHutang(h.getNoHutang());
                        pembayaran.setJumlahPembayaran(h.getSisaHutang());
                        pembayaran.setTipeKeuangan("Penjualan");
                        pembayaran.setCatatan(penjualan.getNoPenjualan());
                        pembayaran.setKodeUser(sistem.getUser().getKodeUser());
                        pembayaran.setTglBatal("2000-01-01 00:00:00");
                        pembayaran.setUserBatal("");
                        pembayaran.setStatus("true");
                        PembayaranDAO.insert(con, pembayaran);

                        h.setPembayaran(h.getPembayaran()+h.getSisaHutang());
                        h.setSisaHutang(h.getSisaHutang()-h.getSisaHutang());
                        h.setStatus("close");
                        HutangDAO.update(con, h);
                        
                        bayar = bayar - h.getSisaHutang();
                    }
                }

                insertKeuangan(con, noKeuangan, tglSql.format(Function.getServerDate(con)), "Hutang", "Terima Pembayaran Down Payment", 
                        "Penjualan - "+penjualan.getNoPenjualan(), -penjualan.getPembayaran(), sistem.getUser().getKodeUser());
            }
            CustomerDAO.update(con, customer);
            
            for(PenjualanDetail detail : penjualan.getListPenjualanDetail()){
                PemesananDetail d = PemesananDetailDAO.get(con, detail.getNoPemesanan(), detail.getNoUrut());
                d.setQtyTerkirim(d.getQtyTerkirim()+detail.getQty());
                PemesananDetailDAO.update(con, d);
            }
            double qtyBelumDikirim = 0;
            List<PemesananDetail> listPemesanan = PemesananDetailDAO.getAllByNoPemesanan(con, pemesanan.getNoPemesanan());
            for(PemesananDetail d : listPemesanan){
                qtyBelumDikirim = qtyBelumDikirim + d.getQty()-d.getQtyTerkirim();
            }
            if(qtyBelumDikirim==0)
                pemesanan.setStatus("close");
            PemesananHeadDAO.update(con, pemesanan);
            
            List<PenjualanDetail> stokBarang = new ArrayList<>();
            double hpp=0;
            for(PenjualanDetail d : penjualan.getListPenjualanDetail()){
                LogBarang log = LogBarangDAO.getLastByBarangAndGudang(con, d.getKodeBarang(), penjualan.getKodeGudang());
                if(log.getStokAkhir()!=0)
                    d.setNilai(log.getNilaiAkhir()/log.getStokAkhir());
                PenjualanDetailDAO.insert(con, d);
                
                hpp = hpp + d.getNilai()*d.getQty();
                
                Boolean inStok = false;
                for(PenjualanDetail detail : stokBarang){
                    if(d.getKodeBarang().equals(detail.getKodeBarang())){
                        detail.setNilai((detail.getNilai()*detail.getQty()+d.getNilai()*d.getQty())/
                                (detail.getQty()+d.getQty()));
                        detail.setQty(detail.getQty()+d.getQty());
                        inStok = true;
                    }
                }
                if(!inStok){
                    PenjualanDetail temp = new PenjualanDetail();
                    temp.setNoPenjualan(d.getNoPenjualan());
                    temp.setNoPemesanan(d.getNoPemesanan());
                    temp.setNoUrut(d.getNoUrut());
                    temp.setKodeBarang(d.getKodeBarang());
                    temp.setNamaBarang(d.getNamaBarang());
                    temp.setKeterangan(d.getKeterangan());
                    temp.setSatuan(d.getSatuan());
                    temp.setQty(d.getQty());
                    temp.setNilai(d.getNilai());
                    temp.setHargaJual(d.getHargaJual());
                    temp.setTotal(d.getTotal());
                    stokBarang.add(temp);
                }
            }
            insertKeuangan(con, noKeuangan, tglSql.format(Function.getServerDate(con)), "HPP", "Penjualan", 
                    "Penjualan - "+penjualan.getNoPenjualan(), hpp, sistem.getUser().getKodeUser());
            insertKeuangan(con, noKeuangan, tglSql.format(Function.getServerDate(con)), "Stok Barang", penjualan.getKodeGudang(), 
                    "Penjualan - "+penjualan.getNoPenjualan(), -hpp, sistem.getUser().getKodeUser());
            
            for(PenjualanDetail d : stokBarang){
                StokBarang stok = StokBarangDAO.get(con, tglBarang.format(Function.getServerDate(con)), d.getKodeBarang(), penjualan.getKodeGudang());
                if(stok==null)
                    status = "Stok barang "+d.getNamaBarang()+" tidak ditemukan";
                else{
                    if(stok.getStokAkhir()<d.getQty()){
                        status = "Stok barang "+d.getNamaBarang()+" tidak mencukupi";
                    }else{
                        if(stok.getTanggal().equals(tglBarang.format(Function.getServerDate(con)))){
                            stok.setStokKeluar(stok.getStokKeluar()+d.getQty());
                            stok.setStokAkhir(stok.getStokAkhir()-d.getQty());
                            StokBarangDAO.update(con, stok);
                        }else{
                            StokBarang newStok = new StokBarang();
                            newStok.setTanggal(tglBarang.format(Function.getServerDate(con)));
                            newStok.setKodeBarang(d.getKodeBarang());
                            newStok.setKodeGudang(penjualan.getKodeGudang());
                            newStok.setStokAwal(stok.getStokAkhir());
                            newStok.setStokMasuk(0);
                            newStok.setStokKeluar(d.getQty());
                            newStok.setStokAkhir(stok.getStokAkhir()-d.getQty());
                            StokBarangDAO.insert(con, newStok);
                        }
                    }
                }
                LogBarang lb = LogBarangDAO.getLastByBarangAndGudang(con, d.getKodeBarang(), penjualan.getKodeGudang());
                LogBarang log = new LogBarang();
                log.setTanggal(tglSql.format(Function.getServerDate(con)));
                log.setKodeBarang(d.getKodeBarang());
                log.setKodeGudang(penjualan.getKodeGudang());
                log.setKategori("Penjualan");
                log.setKeterangan(penjualan.getNoPenjualan());
                log.setStokAwal(lb.getStokAkhir());
                log.setNilaiAwal(lb.getNilaiAkhir());
                log.setStokMasuk(0);
                log.setNilaiMasuk(0);
                log.setStokKeluar(d.getQty());
                log.setNilaiKeluar(d.getNilai()*d.getQty());
                log.setStokAkhir(lb.getStokAkhir()-d.getQty());
                log.setNilaiAkhir(lb.getNilaiAkhir()-(d.getNilai()*d.getQty()));
                LogBarangDAO.insert(con, log);
            }
            
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
    public static String batalPenjualan(Connection con, PenjualanHead penjualan){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            PenjualanHeadDAO.update(con, penjualan);
            
            KeuanganDAO.delete(con, "Penjualan", "Penjualan", "Penjualan - "+penjualan.getNoPenjualan());
            
            Piutang piutang = PiutangDAO.getByKategoriAndKeteranganAndStatus(
                    con, "Piutang Penjualan",penjualan.getNoPenjualan(),"%");
            PiutangDAO.delete(con, piutang);
            
            KeuanganDAO.delete(con, "Piutang", "Piutang Penjualan", "Penjualan - "+penjualan.getNoPenjualan());
            
            Customer customer = CustomerDAO.get(con, penjualan.getKodeCustomer());
            if(penjualan.getSisaPembayaran()>0)
                customer.setHutang(customer.getHutang()-penjualan.getSisaPembayaran());
            
            PemesananHead pemesanan = PemesananHeadDAO.get(con, penjualan.getNoPemesanan());
            TerimaPembayaran dp = null;
            List<TerimaPembayaran> terimaPembayaran = TerimaPembayaranDAO.getAllByNoPiutangAndStatus(
                    con, piutang.getNoPiutang(), "true");
            for(TerimaPembayaran tp : terimaPembayaran){
                if(tp.getTipeKeuangan().equals("Down Payment"))
                    dp = tp;
                else
                    status = "Tidak dapat dibatal,karena sudah ada pembayaran";
            }
            if(dp!=null){
                customer.setDeposit(customer.getDeposit()+dp.getJumlahPembayaran());
                
                pemesanan.setSisaDownPayment(pemesanan.getSisaDownPayment()+dp.getJumlahPembayaran());

                dp.setTglBatal(tglSql.format(Function.getServerDate(con)));
                dp.setUserBatal(sistem.getUser().getKodeUser());
                dp.setStatus("false");
                TerimaPembayaranDAO.update(con, dp);

                List<Hutang> listHutang = HutangDAO.getAllByKategoriAndKeteranganAndStatus(
                        con, "Terima Pembayaran Down Payment",pemesanan.getNoPemesanan(),"%");
                for(Hutang h : listHutang){
                    List<Pembayaran> pembayaran = PembayaranDAO.getAllByNoHutang(con, h.getNoHutang(), "true");
                    for(Pembayaran p : pembayaran){
                        if(p.getTipeKeuangan().equals("Penjualan")&&p.getCatatan().equals(penjualan.getNoPenjualan())){
                            p.setTglBatal(tglSql.format(Function.getServerDate(con)));
                            p.setUserBatal(sistem.getUser().getKodeUser());
                            p.setStatus("false");
                            PembayaranDAO.update(con, p);
                            
                            h.setPembayaran(h.getPembayaran()-p.getJumlahPembayaran());
                            h.setSisaHutang(h.getSisaHutang()+p.getJumlahPembayaran());
                            h.setStatus("open");
                            HutangDAO.update(con, h);
                        }
                    }
                }
                KeuanganDAO.delete(con, "Hutang", "Terima Pembayaran Down Payment", "Penjualan - "+penjualan.getNoPenjualan());
            }
            CustomerDAO.update(con, customer);
            
            penjualan.setListPenjualanDetail(PenjualanDetailDAO.getAllPenjualanDetail(con, penjualan.getNoPenjualan()));
            for(PenjualanDetail detail : penjualan.getListPenjualanDetail()){
                PemesananDetail d = PemesananDetailDAO.get(con, detail.getNoPemesanan(), detail.getNoUrut());
                d.setQtyTerkirim(d.getQtyTerkirim()-detail.getQty());
                PemesananDetailDAO.update(con, d);
            }
            pemesanan.setStatus("open");
            PemesananHeadDAO.update(con, pemesanan);
            
            List<PenjualanDetail> stokBarang = new ArrayList<>();
            double hpp=0;
            for(PenjualanDetail d : penjualan.getListPenjualanDetail()){
                hpp = hpp + d.getNilai()*d.getQty();
                
                Boolean inStok = false;
                for(PenjualanDetail detail : stokBarang){
                    if(d.getKodeBarang().equals(detail.getKodeBarang())){
                        detail.setNilai((detail.getNilai()*detail.getQty()+d.getNilai()*d.getQty())/
                                (detail.getQty()+d.getQty()));
                        detail.setQty(detail.getQty()+d.getQty());
                        inStok = true;
                    }
                }
                if(!inStok){
                    PenjualanDetail temp = new PenjualanDetail();
                    temp.setNoPenjualan(d.getNoPenjualan());
                    temp.setNoPemesanan(d.getNoPemesanan());
                    temp.setNoUrut(d.getNoUrut());
                    temp.setKodeBarang(d.getKodeBarang());
                    temp.setNamaBarang(d.getNamaBarang());
                    temp.setKeterangan(d.getKeterangan());
                    temp.setSatuan(d.getSatuan());
                    temp.setQty(d.getQty());
                    temp.setNilai(d.getNilai());
                    temp.setHargaJual(d.getHargaJual());
                    temp.setTotal(d.getTotal());
                    stokBarang.add(temp);
                }
            }
            KeuanganDAO.delete(con, "HPP", "Penjualan", "Penjualan - "+penjualan.getNoPenjualan());
                
            KeuanganDAO.delete(con, "Stok Barang", penjualan.getKodeGudang(), "Penjualan - "+penjualan.getNoPenjualan());
            
            for(PenjualanDetail d : stokBarang){
                StokBarang stok = StokBarangDAO.get(con, tglBarang.format(tglSql.parse(penjualan.getTglPenjualan())), d.getKodeBarang(), penjualan.getKodeGudang());
                stok.setStokKeluar(stok.getStokKeluar()-d.getQty());
                stok.setStokAkhir(stok.getStokAkhir()+d.getQty());
                StokBarangDAO.update(con, stok);
                resetStokBarang(con, d.getKodeBarang(), penjualan.getKodeGudang(), tglBarang.format(tglSql.parse(penjualan.getTglPenjualan())));

                LogBarangDAO.delete(con, d.getKodeBarang(), penjualan.getKodeGudang(), "Penjualan", penjualan.getNoPenjualan());
                resetLogBarang(con, penjualan.getTglPenjualan(), d.getKodeBarang(), penjualan.getKodeGudang());
            }
            
            if(status.equals("true"))
                con.commit();
            else 
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String newPenjualanCoil(Connection con, PenjualanCoilHead penjualan, boolean selesai){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            PenjualanCoilHeadDAO.insert(con, penjualan);
            
            String noKeuangan = KeuanganDAO.getId(con);
            insertKeuangan(con, noKeuangan, tglSql.format(Function.getServerDate(con)), "Penjualan", "Penjualan Coil", 
                    "Penjualan Coil - "+penjualan.getNoPenjualan(), penjualan.getTotalPenjualan(), sistem.getUser().getKodeUser());
            insertKeuangan(con, noKeuangan, tglSql.format(Function.getServerDate(con)), "Piutang", "Piutang Penjualan", 
                    "Penjualan Coil - "+penjualan.getNoPenjualan(), penjualan.getSisaPembayaran(), sistem.getUser().getKodeUser());
            
            Piutang piutang = new Piutang();
            piutang.setNoPiutang(PiutangDAO.getId(con));
            piutang.setTglPiutang(tglSql.format(Function.getServerDate(con)));
            piutang.setKategori("Piutang Penjualan");
            piutang.setKeterangan(penjualan.getNoPenjualan());
            piutang.setTipeKeuangan("Penjualan Coil");
            piutang.setJumlahPiutang(penjualan.getTotalPenjualan());
            piutang.setPembayaran(penjualan.getPembayaran());
            piutang.setSisaPiutang(penjualan.getSisaPembayaran());
            piutang.setJatuhTempo("2000-01-01");
            piutang.setKodeUser(sistem.getUser().getKodeUser());
            if(piutang.getSisaPiutang()>0)
                piutang.setStatus("open");
            else
                piutang.setStatus("close");
            PiutangDAO.insert(con, piutang);

            Customer customer = CustomerDAO.get(con, penjualan.getKodeCustomer());
            if(penjualan.getSisaPembayaran()>0)
                customer.setHutang(customer.getHutang()+penjualan.getSisaPembayaran());
            
            PemesananCoilHead pemesanan = penjualan.getPemesananCoilHead();
            if(penjualan.getPembayaran()>0){
                customer.setDeposit(customer.getDeposit()-penjualan.getPembayaran());
                
                pemesanan.setSisaDownPayment(pemesanan.getSisaDownPayment()-penjualan.getPembayaran());

                TerimaPembayaran tp = new TerimaPembayaran();
                tp.setNoTerimaPembayaran(TerimaPembayaranDAO.getId(con));
                tp.setTglTerima(tglSql.format(Function.getServerDate(con)));
                tp.setNoPiutang(piutang.getNoPiutang());
                tp.setJumlahPembayaran(penjualan.getPembayaran());
                tp.setTipeKeuangan("Down Payment");
                tp.setCatatan(penjualan.getNoPemesanan());
                tp.setKodeUser(sistem.getUser().getKodeUser());
                tp.setTglBatal("2000-01-01 00:00:00");
                tp.setUserBatal("");
                tp.setStatus("true");
                TerimaPembayaranDAO.insert(con, tp);

                double bayar = penjualan.getPembayaran();
                List<Hutang> listHutang = HutangDAO.getAllByKategoriAndKeteranganAndStatus(
                        con, "Terima Pembayaran Down Payment",pemesanan.getNoPemesanan(),"%");
                for(Hutang h : listHutang){
                    if(h.getSisaHutang()>bayar){
                        Pembayaran pembayaran = new Pembayaran();
                        pembayaran.setNoPembayaran(PembayaranDAO.getId(con));
                        pembayaran.setTglPembayaran(tglSql.format(Function.getServerDate(con)));
                        pembayaran.setNoHutang(h.getNoHutang());
                        pembayaran.setJumlahPembayaran(bayar);
                        pembayaran.setTipeKeuangan("Penjualan");
                        pembayaran.setCatatan(penjualan.getNoPenjualan());
                        pembayaran.setKodeUser(sistem.getUser().getKodeUser());
                        pembayaran.setTglBatal("2000-01-01 00:00:00");
                        pembayaran.setUserBatal("");
                        pembayaran.setStatus("true");
                        PembayaranDAO.insert(con, pembayaran);
                        
                        h.setPembayaran(h.getPembayaran()+bayar);
                        h.setSisaHutang(h.getSisaHutang()-bayar);
                        HutangDAO.update(con, h);
                        
                        bayar = 0;
                    }else{
                        Pembayaran pembayaran = new Pembayaran();
                        pembayaran.setNoPembayaran(PembayaranDAO.getId(con));
                        pembayaran.setTglPembayaran(tglSql.format(Function.getServerDate(con)));
                        pembayaran.setNoHutang(h.getNoHutang());
                        pembayaran.setJumlahPembayaran(h.getSisaHutang());
                        pembayaran.setTipeKeuangan("Penjualan");
                        pembayaran.setCatatan(penjualan.getNoPenjualan());
                        pembayaran.setKodeUser(sistem.getUser().getKodeUser());
                        pembayaran.setTglBatal("2000-01-01 00:00:00");
                        pembayaran.setUserBatal("");
                        pembayaran.setStatus("true");
                        PembayaranDAO.insert(con, pembayaran);

                        h.setPembayaran(h.getPembayaran()+h.getSisaHutang());
                        h.setSisaHutang(h.getSisaHutang()-h.getSisaHutang());
                        h.setStatus("close");
                        HutangDAO.update(con, h);
                        
                        bayar = bayar - h.getSisaHutang();
                    }
                }

                insertKeuangan(con, noKeuangan, tglSql.format(Function.getServerDate(con)), "Hutang", "Terima Pembayaran Down Payment", 
                        "Penjualan Coil - "+penjualan.getNoPenjualan(), -penjualan.getPembayaran(), sistem.getUser().getKodeUser());
            }
            CustomerDAO.update(con, customer);
            
            if(selesai)
                pemesanan.setStatus("close");
            PemesananCoilHeadDAO.update(con, pemesanan);
            
            double hpp=0;
            for(PenjualanCoilDetail d : penjualan.getListPenjualanDetail()){
                LogBahan log = LogBahanDAO.getLastByBahanAndGudang(con, d.getKodeBahan(), penjualan.getKodeGudang());
                if(log.getStokAkhir()!=0)
                    d.setNilai(log.getNilaiAkhir()/log.getStokAkhir());
                PenjualanCoilDetailDAO.insert(con, d);
                
                hpp = hpp + log.getNilaiAkhir();
            }
            insertKeuangan(con, noKeuangan, tglSql.format(Function.getServerDate(con)), "HPP", "Penjualan Coil", 
                    "Penjualan Coil - "+penjualan.getNoPenjualan(), hpp, sistem.getUser().getKodeUser());
            insertKeuangan(con, noKeuangan, tglSql.format(Function.getServerDate(con)), "Stok Bahan", penjualan.getKodeGudang(), 
                    "Penjualan Coil - "+penjualan.getNoPenjualan(), -hpp, sistem.getUser().getKodeUser());
            
            for(PenjualanCoilDetail d : penjualan.getListPenjualanDetail()){
                double qty = d.getBeratBersih();
                StokBahan stok = StokBahanDAO.getStokAkhir(con, d.getKodeBahan(), penjualan.getKodeGudang());
                if(stok==null)
                    status = "Stok bahan "+d.getKodeBahan()+" tidak ditemukan";
                else{
                    if(stok.getStokAkhir()<qty){
                        status = "Stok barang "+d.getKodeBahan()+" tidak mencukupi";
                    }else{
                        if(stok.getTanggal().equals(tglBarang.format(Function.getServerDate(con)))){
                            stok.setStokKeluar(stok.getStokKeluar()+qty);
                            stok.setStokAkhir(stok.getStokAkhir()-qty);
                            StokBahanDAO.update(con, stok);
                        }else{
                            StokBahan newStok = new StokBahan();
                            newStok.setTanggal(tglBarang.format(Function.getServerDate(con)));
                            newStok.setKodeBahan(d.getKodeBahan());
                            newStok.setKodeGudang(penjualan.getKodeGudang());
                            newStok.setStokAwal(stok.getStokAkhir());
                            newStok.setStokMasuk(0);
                            newStok.setStokKeluar(qty);
                            newStok.setStokAkhir(stok.getStokAkhir()-qty);
                            StokBahanDAO.insert(con, newStok);
                        }
                    }
                }
                LogBahan lb = LogBahanDAO.getLastByBahanAndGudang(con, d.getKodeBahan(), penjualan.getKodeGudang());

                LogBahan log = new LogBahan();
                log.setTanggal(tglSql.format(Function.getServerDate(con)));
                log.setKodeBahan(d.getKodeBahan());
                log.setKodeGudang(penjualan.getKodeGudang());
                log.setKategori("Penjualan");
                log.setKeterangan(penjualan.getNoPenjualan());
                log.setStokAwal(lb.getStokAkhir());
                log.setNilaiAwal(lb.getNilaiAkhir());
                log.setStokMasuk(0);
                log.setNilaiMasuk(0);
                log.setStokKeluar(qty);
                log.setNilaiKeluar(d.getNilai()*qty);
                log.setStokAkhir(lb.getStokAkhir()-qty);
                log.setNilaiAkhir(lb.getNilaiAkhir()-(d.getNilai()*qty));
                LogBahanDAO.insert(con, log);

                Bahan bahan = BahanDAO.get(con, d.getKodeBahan());
                bahan.setStatus("false");
                BahanDAO.update(con, bahan);
            }
            
            if(status.equals("true"))
                con.commit();
            else 
                con.rollback();
            con.setAutoCommit(true);
            
            return status;
        }catch(Exception e){
            try{
                e.printStackTrace();
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String batalPenjualanCoil(Connection con, PenjualanCoilHead penjualan){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            Date tanggal = Function.getServerDate(con);
            PenjualanCoilHeadDAO.update(con, penjualan);
            
            KeuanganDAO.delete(con, "Penjualan", "Penjualan Coil", "Penjualan Coil - "+penjualan.getNoPenjualan());
            
            Piutang piutang = PiutangDAO.getByKategoriAndKeteranganAndStatus(
                    con, "Piutang Penjualan",penjualan.getNoPenjualan(),"%");
            PiutangDAO.delete(con, piutang);
            
            KeuanganDAO.delete(con, "Piutang", "Piutang Penjualan", "Penjualan Coil - "+penjualan.getNoPenjualan());
            
            Customer customer = CustomerDAO.get(con, penjualan.getKodeCustomer());
            if(penjualan.getSisaPembayaran()>0)
                customer.setHutang(customer.getHutang()-penjualan.getSisaPembayaran());
            
            PemesananCoilHead pemesanan = PemesananCoilHeadDAO.get(con, penjualan.getNoPemesanan());
            TerimaPembayaran dp = null;
            List<TerimaPembayaran> terimaPembayaran = TerimaPembayaranDAO.getAllByNoPiutangAndStatus(
                    con, piutang.getNoPiutang(),"true");
            for(TerimaPembayaran tp : terimaPembayaran){
                if(tp.getTipeKeuangan().equals("Down Payment"))
                    dp = tp;
                else
                    status = "Tidak dapat dibatal,karena sudah ada pembayaran";
            }
            if(dp!=null){
                customer.setDeposit(customer.getDeposit()+dp.getJumlahPembayaran());
                
                pemesanan.setSisaDownPayment(pemesanan.getSisaDownPayment()+dp.getJumlahPembayaran());

                dp.setTglBatal(tglSql.format(tanggal));
                dp.setUserBatal(sistem.getUser().getKodeUser());
                dp.setStatus("false");
                TerimaPembayaranDAO.update(con, dp);

                List<Hutang> listHutang = HutangDAO.getAllByKategoriAndKeteranganAndStatus(
                        con, "Terima Pembayaran Down Payment",pemesanan.getNoPemesanan(),"%");
                for(Hutang h : listHutang){
                    List<Pembayaran> pembayaran = PembayaranDAO.getAllByNoHutang(con, h.getNoHutang(),"true");
                    for(Pembayaran p : pembayaran){
                        if(p.getTipeKeuangan().equals("Penjualan")&&p.getCatatan().equals(penjualan.getNoPenjualan())){
                            p.setTglBatal(tglSql.format(tanggal));
                            p.setUserBatal(sistem.getUser().getKodeUser());
                            p.setStatus("false");
                            PembayaranDAO.update(con, p);
                            
                            h.setPembayaran(h.getPembayaran()-p.getJumlahPembayaran());
                            h.setSisaHutang(h.getSisaHutang()+p.getJumlahPembayaran());
                            h.setStatus("close");
                            HutangDAO.update(con, h);
                        }
                    }
                }
                KeuanganDAO.delete(con, "Hutang", "Terima Pembayaran Down Payment", "Penjualan Coil - "+penjualan.getNoPenjualan());
            }
            CustomerDAO.update(con, customer);
            
            pemesanan.setStatus("open");
            PemesananCoilHeadDAO.update(con, pemesanan);
            
            penjualan.setListPenjualanDetail(PenjualanCoilDetailDAO.getAllPenjualanCoilDetail(con, penjualan.getNoPenjualan()));
            List<PenjualanCoilDetail> stokBahan = new ArrayList<>();
            double hpp=0;
            for(PenjualanCoilDetail d : penjualan.getListPenjualanDetail()){
                hpp = hpp + d.getNilai()*d.getBeratBersih();
                
                Boolean inStok = false;
                for(PenjualanCoilDetail detail : stokBahan){
                    if(d.getKodeBahan().equals(detail.getKodeBahan())){
                        detail.setNilai((detail.getNilai()*detail.getBeratBersih()+d.getNilai()*d.getBeratBersih())/
                                (detail.getBeratBersih()+d.getBeratBersih()));
                        detail.setBeratBersih(detail.getBeratBersih()+d.getBeratBersih());
                        inStok = true;
                    }
                }
                if(!inStok)
                    stokBahan.add(d);
            }
            
            KeuanganDAO.delete(con, "HPP", "Penjualan Coil", "Penjualan Coil - "+penjualan.getNoPenjualan());
            KeuanganDAO.delete(con, "Stok Bahan", penjualan.getKodeGudang(), "Penjualan Coil - "+penjualan.getNoPenjualan());
            
            for(PenjualanCoilDetail d : stokBahan){
                StokBahan stok = StokBahanDAO.get(con, 
                        tglBarang.format(tglSql.parse(penjualan.getTglPenjualan())), d.getKodeBahan(), penjualan.getKodeGudang());
                stok.setStokKeluar(stok.getStokKeluar()-d.getBeratBersih());
                stok.setStokAkhir(stok.getStokAkhir()+d.getBeratBersih());
                StokBahanDAO.update(con, stok);
                resetStokBahan(con, d.getKodeBahan(), penjualan.getKodeGudang(), tglBarang.format(tglSql.parse(penjualan.getTglPenjualan())), tanggal);

                LogBahanDAO.delete(con, d.getKodeBahan(), penjualan.getKodeGudang(), "Penjualan", penjualan.getNoPenjualan());
                resetLogBahan(con, penjualan.getTglPenjualan(), d.getKodeBahan(), penjualan.getKodeGudang(), tanggal);
                
                Bahan bahan = BahanDAO.get(con, d.getKodeBahan());
                bahan.setStatus("true");
                BahanDAO.update(con, bahan);
            }
            
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
    
    public static String newPembelian(Connection con, PembelianHead p){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            String tanggal = tglSql.format(Function.getServerDate(con));
            String noKeuangan = KeuanganDAO.getId(con);
            
            PembelianHeadDAO.insert(con, p);
            for(BebanPembelian beban : p.getListBebanPembelianCoil()){
                BebanPembelianDAO.insert(con, beban);
            }
            Hutang hutang = new Hutang();
            hutang.setNoHutang(HutangDAO.getId(con));
            hutang.setTglHutang(tanggal);
            hutang.setKategori("Hutang Pembelian");
            hutang.setKeterangan(p.getNoPembelian());
            hutang.setTipeKeuangan("Pembelian");
            hutang.setJumlahHutang(p.getGrandtotal());
            hutang.setPembayaran(p.getPembayaran());
            hutang.setSisaHutang(p.getSisaPembayaran());
            hutang.setJatuhTempo("2000-01-01");
            hutang.setKodeUser(sistem.getUser().getKodeUser());
            hutang.setStatus("open");
            HutangDAO.insert(con, hutang);
            
            insertKeuangan(con, noKeuangan, tanggal, "Hutang", "Hutang Pembelian", 
                    "Pembelian - "+p.getNoPembelian(), p.getSisaPembayaran(), sistem.getUser().getKodeUser());
            
            double bebanPerItem = p.getTotalBebanPembelian()/p.getListPembelianCoilDetail().size();
            for(PembelianDetail detail : p.getListPembelianCoilDetail()){
                PembelianDetailDAO.insert(con, detail);
                Bahan bahan = BahanDAO.get(con, detail.getKodeBahan());
                if(bahan==null){
                    double stokBeli = detail.getBeratBersih();
                    
                    bahan = new Bahan();
                    bahan.setKodeBahan(detail.getKodeBahan());
                    bahan.setKodeKategori(detail.getKodeKategori());
                    bahan.setNoKontrak(detail.getNoKontrak());
                    bahan.setNamaBahan(detail.getNamaBahan());
                    bahan.setSpesifikasi(detail.getSpesifikasi());
                    bahan.setKeterangan("");
                    bahan.setBeratKotor(detail.getBeratKotor());
                    bahan.setBeratBersih(detail.getBeratBersih());
                    bahan.setPanjang(detail.getPanjang());
                    bahan.setHargaBeli(detail.getTotal()+bebanPerItem);
                    bahan.setStatus("true");
                    BahanDAO.insert(con, bahan);
                    
                    StokBahan stokBahan = new StokBahan();
                    stokBahan.setTanggal(tanggal);
                    stokBahan.setKodeBahan(detail.getKodeBahan());
                    stokBahan.setKodeGudang(p.getKodeGudang());
                    stokBahan.setStokAwal(0);
                    stokBahan.setStokMasuk(stokBeli);
                    stokBahan.setStokKeluar(0);
                    stokBahan.setStokAkhir(stokBeli);
                    StokBahanDAO.insert(con, stokBahan);
                    
                    LogBahan log = new LogBahan();
                    log.setTanggal(tanggal);
                    log.setKodeBahan(detail.getKodeBahan());
                    log.setKodeGudang(p.getKodeGudang());
                    log.setKategori("Pembelian");
                    log.setKeterangan(p.getNoPembelian());
                    log.setStokAwal(0);
                    log.setNilaiAwal(0);
                    log.setStokMasuk(stokBeli);
                    log.setNilaiMasuk(detail.getTotal()+bebanPerItem);
                    log.setStokKeluar(0);
                    log.setNilaiKeluar(0);
                    log.setStokAkhir(stokBeli);
                    log.setNilaiAkhir(detail.getTotal()+bebanPerItem);
                    LogBahanDAO.insert(con, log);
                }else
                    status = "Kode bahan sudah pernah terdaftar";
            }
            
            insertKeuangan(con, noKeuangan, tanggal, "Stok Bahan", p.getKodeGudang(), 
                    "Pembelian - "+p.getNoPembelian(), p.getGrandtotal(), sistem.getUser().getKodeUser());
            
            if(status.equals("true"))
                con.commit();
            else 
                con.rollback();
            con.setAutoCommit(true);
            
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                e.printStackTrace();
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String batalPembelian(Connection con, PembelianHead pembelian){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            Date tanggal = Function.getServerDate(con);
            if(pembelian.getPembayaran()>0){
                status = "Tidak dapat dibatalkan, karena sudah ada pembayaran";
            }else{
                PembelianHeadDAO.update(con, pembelian);

                Hutang hutang = HutangDAO.getByKategoriAndKeteranganAndStatus(
                        con, "Hutang Pembelian", pembelian.getNoPembelian(), "%");
                HutangDAO.delete(con, hutang);

                KeuanganDAO.delete(con, "Hutang", "Hutang Pembelian", "Pembelian - "+pembelian.getNoPembelian());
                
                pembelian.setListPembelianCoilDetail(PembelianDetailDAO.getAllByNoPembelian(con, pembelian.getNoPembelian()));
                for(PembelianDetail d : pembelian.getListPembelianCoilDetail()){
                    Bahan b = BahanDAO.get(con, d.getKodeBahan());
                    double stokBeli = b.getBeratBersih();
                    
                    StokBahan stokAkhir = StokBahanDAO.getStokAkhir(con, d.getKodeBahan(), pembelian.getKodeGudang());
                    if(stokAkhir.getStokAkhir()<stokBeli)
                        status = "Stok bahan "+stokAkhir.getKodeBahan()+" kurang dari jumlah yang dibeli";
                    else{
                        StokBahanDAO.delete(con, d.getKodeBahan(), pembelian.getKodeGudang());
                        BahanDAO.delete(con, d.getKodeBahan());
                        
                        LogBahanDAO.delete(con, d.getKodeBahan(), pembelian.getKodeGudang(), "Pembelian", pembelian.getNoPembelian());
                        resetLogBahan(con, pembelian.getTglPembelian(), d.getKodeBahan(), pembelian.getKodeGudang(), tanggal);
                    }
                }
                KeuanganDAO.delete(con, "Stok Bahan", pembelian.getKodeGudang(), "Pembelian - "+pembelian.getNoPembelian());

            }
            if(status.equals("true"))
                con.commit();
            else 
                con.rollback();
            con.setAutoCommit(true);
            
            return status;
        }catch(Exception e){
            try{
                e.printStackTrace();
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String newPembelianBarang(Connection con, PembelianBarangHead p){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            Date tanggal = Function.getServerDate(con);
            String noKeuangan = KeuanganDAO.getId(con);
            
            PembelianBarangHeadDAO.insert(con, p);
            for(BebanPembelian beban : p.getListBebanPembelian()){
                BebanPembelianDAO.insert(con, beban);
            }
            Hutang hutang = new Hutang();
            hutang.setNoHutang(HutangDAO.getId(con));
            hutang.setTglHutang(tglSql.format(tanggal));
            hutang.setKategori("Hutang Pembelian");
            hutang.setKeterangan(p.getNoPembelian());
            hutang.setTipeKeuangan("Pembelian");
            hutang.setJumlahHutang(p.getGrandtotal());
            hutang.setPembayaran(p.getPembayaran());
            hutang.setSisaHutang(p.getSisaPembayaran());
            hutang.setJatuhTempo("2000-01-01");
            hutang.setKodeUser(sistem.getUser().getKodeUser());
            hutang.setStatus("open");
            HutangDAO.insert(con, hutang);
            
            insertKeuangan(con, noKeuangan, tglSql.format(tanggal), "Hutang", "Hutang Pembelian", 
                    "Pembelian Barang - "+p.getNoPembelian(), p.getSisaPembayaran(), sistem.getUser().getKodeUser());
            
            double totalQty = 0;
            for(PembelianBarangDetail detail : p.getListPembelianBarangDetail()){
                totalQty = totalQty + detail.getQty();
            }
            double bebanPerItem = p.getTotalBebanPembelian()/totalQty;
            
            double totalNilai = 0;
            List<PembelianBarangDetail> groupByBarang = new ArrayList<>();
            for(PembelianBarangDetail detail : p.getListPembelianBarangDetail()){
                detail.setNilai(detail.getHargaBeli()+bebanPerItem);
                PembelianBarangDetailDAO.insert(con, detail);
                
                boolean x = true;
                for(PembelianBarangDetail d : groupByBarang){
                    if(detail.getKodeBarang().equals(d.getKodeBarang())){
                        d.setQty(d.getQty()+detail.getQty());
                        d.setNilai(((d.getNilai()*d.getQty())+(detail.getNilai()*detail.getQty()))/
                                (d.getQty()+detail.getQty()));
                        x = false;
                    }
                }
                if(x)
                    groupByBarang.add(detail);
                
                totalNilai = totalNilai + (detail.getNilai()*detail.getQty());
            }
                
            insertKeuangan(con, noKeuangan, tglSql.format(tanggal), "Stok Barang", p.getKodeGudang(), 
                    "Pembelian Barang - "+p.getNoPembelian(), totalNilai, sistem.getUser().getKodeUser());
            
            for(PembelianBarangDetail d : groupByBarang){    
                StokBarang stokAkhir = StokBarangDAO.get(con, tglBarang.format(tanggal), d.getKodeBarang(), p.getKodeGudang());
                if(stokAkhir.getTanggal().equals(tglBarang.format(tanggal))){
                    stokAkhir.setStokMasuk(stokAkhir.getStokMasuk()+d.getQty());
                    stokAkhir.setStokAkhir(stokAkhir.getStokAkhir()+d.getQty());
                    StokBarangDAO.update(con, stokAkhir);
                }else{
                    StokBarang stokBarang = new StokBarang();
                    stokBarang.setTanggal(tglBarang.format(tanggal));
                    stokBarang.setKodeBarang(d.getKodeBarang());
                    stokBarang.setKodeGudang(p.getKodeGudang());
                    stokBarang.setStokAwal(stokAkhir.getStokAkhir());
                    stokBarang.setStokMasuk(d.getQty());
                    stokBarang.setStokKeluar(0);
                    stokBarang.setStokAkhir(stokAkhir.getStokAkhir()+d.getQty());
                    StokBarangDAO.insert(con, stokBarang);
                }
                
                double nilai = d.getNilai()*d.getQty();
                LogBarang logBarang = LogBarangDAO.getLastByBarangAndGudang(con, d.getKodeBarang(), p.getKodeGudang());
                LogBarang newLogBarang = new LogBarang();
                newLogBarang.setTanggal(tglSql.format(tanggal));
                newLogBarang.setKodeBarang(d.getKodeBarang());
                newLogBarang.setKodeGudang(p.getKodeGudang());
                newLogBarang.setKategori("Pembelian Barang");
                newLogBarang.setKeterangan(p.getNoPembelian());
                newLogBarang.setStokAwal(logBarang.getStokAkhir());
                newLogBarang.setNilaiAwal(logBarang.getNilaiAkhir());
                newLogBarang.setStokMasuk(d.getQty());
                newLogBarang.setNilaiMasuk(nilai);
                newLogBarang.setStokKeluar(0);
                newLogBarang.setNilaiKeluar(0);
                newLogBarang.setStokAkhir(logBarang.getStokAkhir()+d.getQty());
                newLogBarang.setNilaiAkhir(logBarang.getNilaiAkhir()+nilai);
                LogBarangDAO.insert(con, newLogBarang);
            }
            
            if(status.equals("true"))
                con.commit();
            else 
                con.rollback();
            con.setAutoCommit(true);
            
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                e.printStackTrace();
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String batalPembelianBarang(Connection con, PembelianBarangHead pembelian){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            if(pembelian.getPembayaran()>0){
                status = "Tidak dapat dibatalkan, karena sudah ada pembayaran";
            }else{
                PembelianBarangHeadDAO.update(con, pembelian);

                Hutang hutang = HutangDAO.getByKategoriAndKeteranganAndStatus(
                        con, "Hutang Pembelian", pembelian.getNoPembelian(), "%");
                HutangDAO.delete(con, hutang);

                KeuanganDAO.delete(con, "Hutang", "Hutang Pembelian", "Pembelian Barang - "+pembelian.getNoPembelian());
                
                pembelian.setListPembelianBarangDetail(PembelianBarangDetailDAO.getAllByNoPembelian(con, pembelian.getNoPembelian()));
                List<PembelianBarangDetail> stokBarang = new ArrayList<>();
                for(PembelianBarangDetail d : pembelian.getListPembelianBarangDetail()){
                    Boolean inStok = false;
                    for(PembelianBarangDetail detail : stokBarang){
                        if(d.getKodeBarang().equals(detail.getKodeBarang())){
                            detail.setQty(detail.getQty()+d.getQty());
                            inStok = true;
                        }
                    }
                    if(!inStok)
                        stokBarang.add(d);
                }

                for(PembelianBarangDetail d : stokBarang){
                    StokBarang stokAkhir = StokBarangDAO.get(con, tglBarang.format(Function.getServerDate(con)), d.getKodeBarang(), pembelian.getKodeGudang());
                    if(stokAkhir==null)
                        status = "Stok barang "+d.getNamaBarang()+" tidak ditemukan";
                    else{
                        if(stokAkhir.getStokAkhir()<d.getQty()){
                            status = "Stok barang "+d.getNamaBarang()+" tidak mencukupi";
                        }else{
                            StokBarang stok = StokBarangDAO.get(con, tglBarang.format(tglSql.parse(pembelian.getTglPembelian())), d.getKodeBarang(), pembelian.getKodeGudang());
                            stok.setStokMasuk(stok.getStokMasuk()-d.getQty());
                            stok.setStokAkhir(stok.getStokAkhir()-d.getQty());
                            StokBarangDAO.update(con, stok);
                            resetStokBarang(con, d.getKodeBarang(), pembelian.getKodeGudang(), tglBarang.format(tglSql.parse(pembelian.getTglPembelian())));

                            LogBarangDAO.delete(con, d.getKodeBarang(), pembelian.getKodeGudang(), "Pembelian Barang", pembelian.getNoPembelian());
                            resetLogBarang(con, pembelian.getTglPembelian(), d.getKodeBarang(), pembelian.getKodeGudang());

                        }
                    }
                }
                KeuanganDAO.delete(con, "Stok Barang", pembelian.getKodeGudang(), "Pembelian Barang - "+pembelian.getNoPembelian());
            }
            if(status.equals("true"))
                con.commit();
            else 
                con.rollback();
            con.setAutoCommit(true);
            
            return status;
        }catch(Exception e){
            try{
                e.printStackTrace();
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String newPindahBahan(Connection con, PindahBahanHead p){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            PindahBahanHeadDAO.insert(con, p);
            
            String noKeuangan = KeuanganDAO.getId(con);
            
            double totalNilai = 0;
            int i = 1;
            for(PindahBahanDetail d : p.getListPindahBahanDetail()){
                d.setNoPindah(p.getNoPindah());
                d.setNoUrut(i);
                
                LogBahan log = LogBahanDAO.getLastByBahanAndGudang(con, d.getKodeBahan(), p.getGudangAsal());
                if(log.getStokAkhir()!=0)
                    d.setNilai(log.getNilaiAkhir());
                PindahBahanDetailDAO.insert(con, d);
                
                i++;
                StokBahan stokKeluar = StokBahanDAO.getStokAkhir(con, d.getKodeBahan(), p.getGudangAsal());
                if(stokKeluar==null)
                    status = "Stok bahan "+d.getKodeBahan()+" tidak ditemukan";
                else{
                    if(stokKeluar.getStokAkhir()<d.getBeratBersih()){
                        status = "Stok bahan "+d.getKodeBahan()+" tidak mencukupi";
                    }else{
                        if(stokKeluar.getTanggal().equals(tglBarang.format(Function.getServerDate(con)))){
                            stokKeluar.setStokKeluar(stokKeluar.getStokKeluar()+d.getBeratBersih());
                            stokKeluar.setStokAkhir(stokKeluar.getStokAkhir()-d.getBeratBersih());
                            StokBahanDAO.update(con, stokKeluar);
                        }else{
                            StokBahan newStok = new StokBahan();
                            newStok.setTanggal(tglBarang.format(Function.getServerDate(con)));
                            newStok.setKodeBahan(d.getKodeBahan());
                            newStok.setKodeGudang(p.getGudangAsal());
                            newStok.setStokAwal(stokKeluar.getStokAkhir());
                            newStok.setStokMasuk(0);
                            newStok.setStokKeluar(d.getBeratBersih());
                            newStok.setStokAkhir(stokKeluar.getStokAkhir()-d.getBeratBersih());
                            StokBahanDAO.insert(con, newStok);
                        }
                        StokBahan stokMasuk = StokBahanDAO.getStokAkhir(con, d.getKodeBahan(), p.getGudangTujuan());
                        if(stokMasuk==null){
                            StokBahan newStok = new StokBahan();
                            newStok.setTanggal(tglBarang.format(Function.getServerDate(con)));
                            newStok.setKodeBahan(d.getKodeBahan());
                            newStok.setKodeGudang(p.getGudangTujuan());
                            newStok.setStokAwal(0);
                            newStok.setStokMasuk(d.getBeratBersih());
                            newStok.setStokKeluar(0);
                            newStok.setStokAkhir(d.getBeratBersih());
                            StokBahanDAO.insert(con, newStok);
                        }else{
                            if(stokMasuk.getTanggal().equals(tglBarang.format(Function.getServerDate(con)))){
                                stokMasuk.setStokMasuk(stokMasuk.getStokMasuk()+d.getBeratBersih());
                                stokMasuk.setStokAkhir(stokMasuk.getStokAkhir()+d.getBeratBersih());
                                StokBahanDAO.update(con, stokMasuk);
                            }else{
                                StokBahan newStok = new StokBahan();
                                newStok.setTanggal(tglBarang.format(Function.getServerDate(con)));
                                newStok.setKodeBahan(d.getKodeBahan());
                                newStok.setKodeGudang(p.getGudangTujuan());
                                newStok.setStokAwal(stokMasuk.getStokAkhir());
                                newStok.setStokMasuk(d.getBeratBersih());
                                newStok.setStokKeluar(0);
                                newStok.setStokAkhir(stokMasuk.getStokAkhir()+d.getBeratBersih());
                                StokBahanDAO.insert(con, newStok);
                            }
                        }
                    }
                }
                LogBahan lbKeluar = LogBahanDAO.getLastByBahanAndGudang(con, d.getKodeBahan(), p.getGudangAsal());
                LogBahan logKeluar = new LogBahan();
                logKeluar.setTanggal(tglSql.format(Function.getServerDate(con)));
                logKeluar.setKodeBahan(d.getKodeBahan());
                logKeluar.setKodeGudang(p.getGudangAsal());
                logKeluar.setKategori("Pindah Bahan");
                logKeluar.setKeterangan(p.getNoPindah());
                logKeluar.setStokAwal(lbKeluar.getStokAkhir());
                logKeluar.setNilaiAwal(lbKeluar.getNilaiAkhir());
                logKeluar.setStokMasuk(0);
                logKeluar.setNilaiMasuk(0);
                logKeluar.setStokKeluar(lbKeluar.getStokAkhir());
                logKeluar.setNilaiKeluar(lbKeluar.getNilaiAkhir());
                logKeluar.setStokAkhir(0);
                logKeluar.setNilaiAkhir(0);
                LogBahanDAO.insert(con, logKeluar);

                LogBahan logMasuk = new LogBahan();
                logMasuk.setTanggal(tglSql.format(Function.getServerDate(con)));
                logMasuk.setKodeBahan(d.getKodeBahan());
                logMasuk.setKodeGudang(p.getGudangTujuan());
                logMasuk.setKategori("Pindah Bahan");
                logMasuk.setKeterangan(p.getNoPindah());
                logMasuk.setStokAwal(0);
                logMasuk.setNilaiAwal(0);
                logMasuk.setStokMasuk(d.getBeratBersih());
                logMasuk.setNilaiMasuk(d.getNilai());
                logMasuk.setStokKeluar(0);
                logMasuk.setNilaiKeluar(0);
                logMasuk.setStokAkhir(d.getBeratBersih());
                logMasuk.setNilaiAkhir(d.getNilai());
                LogBahanDAO.insert(con, logMasuk);
                
                totalNilai = totalNilai + d.getNilai();
            }
            insertKeuangan(con, noKeuangan, tglSql.format(Function.getServerDate(con)), "Stok Bahan", p.getGudangAsal(), 
                    "Pindah Bahan - "+p.getNoPindah(), -totalNilai, sistem.getUser().getKodeUser());
            insertKeuangan(con, noKeuangan, tglSql.format(Function.getServerDate(con)), "Stok Bahan", p.getGudangTujuan(), 
                    "Pindah Bahan - "+p.getNoPindah(), totalNilai, sistem.getUser().getKodeUser());
            
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
    public static String batalPindahBahan(Connection con, PindahBahanHead p){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            PindahBahanHeadDAO.update(con, p);
            
            for(PindahBahanDetail d : p.getListPindahBahanDetail()){
                StokBahan stokBatalKeluar = StokBahanDAO.get(con, tglBarang.format(tglSql.parse(p.getTglPindah())), 
                        d.getKodeBahan(), p.getGudangAsal());
                stokBatalKeluar.setStokKeluar(stokBatalKeluar.getStokKeluar()-d.getBeratBersih());
                stokBatalKeluar.setStokAkhir(stokBatalKeluar.getStokAkhir()+d.getBeratBersih());
                StokBahanDAO.update(con, stokBatalKeluar);
                resetStokBahan(con, d.getKodeBahan(), p.getGudangAsal(), tglBarang.format(tglSql.parse(p.getTglPindah())), Function.getServerDate(con));
                
                StokBahan stokBatalMasuk = StokBahanDAO.get(con, tglBarang.format(tglSql.parse(p.getTglPindah())), 
                        d.getKodeBahan(), p.getGudangTujuan());
                stokBatalMasuk.setStokMasuk(stokBatalMasuk.getStokMasuk()-d.getBeratBersih());
                stokBatalMasuk.setStokAkhir(stokBatalMasuk.getStokAkhir()-d.getBeratBersih());
                StokBahanDAO.update(con, stokBatalMasuk);
                resetStokBahan(con, d.getKodeBahan(), p.getGudangTujuan(), tglBarang.format(tglSql.parse(p.getTglPindah())), Function.getServerDate(con));

                LogBahanDAO.delete(con, d.getKodeBahan(), p.getGudangAsal(), "Pindah Bahan", p.getNoPindah());
                resetLogBahan(con, p.getTglPindah(), d.getKodeBahan(), p.getGudangAsal(), Function.getServerDate(con));
                
                LogBahanDAO.delete(con, d.getKodeBahan(), p.getGudangTujuan(), "Pindah Bahan", p.getNoPindah());
                resetLogBahan(con, p.getTglPindah(), d.getKodeBahan(), p.getGudangTujuan(), Function.getServerDate(con));
            
            }
            KeuanganDAO.delete(con, "Stok Bahan", p.getGudangAsal(), "Pindah Bahan - "+p.getNoPindah());
            KeuanganDAO.delete(con, "Stok Bahan", p.getGudangTujuan(), "Pindah Bahan - "+p.getNoPindah());
            
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
    
    public static String newPindahBarang(Connection con, PindahBarangHead p){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            PindahBarangHeadDAO.insert(con, p);
            
            String noKeuangan = KeuanganDAO.getId(con);
            
            int i = 1;
            double totalNilai = 0;
            List<PindahBarangDetail> stokBarang = new ArrayList<>();
            for(PindahBarangDetail d : p.getListPindahBarangDetail()){
                d.setNoPindah(p.getNoPindah());
                d.setNoUrut(i);
                
                LogBarang log = LogBarangDAO.getLastByBarangAndGudang(con, d.getKodeBarang(), p.getGudangAsal());
                if(log.getStokAkhir()!=0)
                    d.setNilai(log.getNilaiAkhir()/log.getStokAkhir());
                PindahBarangDetailDAO.insert(con, d);
                
                Boolean inStok = false;
                for(PindahBarangDetail detail : stokBarang){
                    if(d.getKodeBarang().equals(detail.getKodeBarang())){
                        detail.setNilai((detail.getNilai()*detail.getQty()+d.getNilai()*d.getQty())/
                                (detail.getQty()+d.getQty()));
                        detail.setQty(detail.getQty()+d.getQty());
                        inStok = true;
                    }
                }
                if(!inStok){
                    PindahBarangDetail temp = new PindahBarangDetail();
                    temp.setNoPindah(d.getNoPindah());
                    temp.setNoUrut(d.getNoUrut());
                    temp.setKodeBarang(d.getKodeBarang());
                    temp.setNamaBarang(d.getNamaBarang());
                    temp.setKeterangan(d.getKeterangan());
                    temp.setSatuan(d.getSatuan());
                    temp.setQty(d.getQty());
                    temp.setNilai(d.getNilai());
                    stokBarang.add(temp);
                }
                i++;
                
                totalNilai = totalNilai + (d.getNilai()*d.getQty());
            }
            insertKeuangan(con, noKeuangan, tglSql.format(Function.getServerDate(con)), "Stok Barang", p.getGudangAsal(), 
                    "Pindah Barang - "+p.getNoPindah(), -totalNilai, sistem.getUser().getKodeUser());
            insertKeuangan(con, noKeuangan, tglSql.format(Function.getServerDate(con)), "Stok Barang", p.getGudangTujuan(), 
                    "Pindah Barang - "+p.getNoPindah(), totalNilai, sistem.getUser().getKodeUser());
            
            for(PindahBarangDetail d : stokBarang){
                StokBarang stokKeluar = StokBarangDAO.get(con, tglBarang.format(Function.getServerDate(con)), 
                        d.getKodeBarang(), p.getGudangAsal());
                if(stokKeluar==null)
                    status = "Stok barang "+d.getNamaBarang()+" tidak ditemukan";
                else{
                    if(stokKeluar.getStokAkhir()<d.getQty()){
                        status = "Stok barang "+d.getNamaBarang()+" tidak mencukupi";
                    }else{
                        if(stokKeluar.getTanggal().equals(tglBarang.format(Function.getServerDate(con)))){
                            stokKeluar.setStokKeluar(stokKeluar.getStokKeluar()+d.getQty());
                            stokKeluar.setStokAkhir(stokKeluar.getStokAkhir()-d.getQty());
                            StokBarangDAO.update(con, stokKeluar);
                        }else{
                            StokBarang newStok = new StokBarang();
                            newStok.setTanggal(tglBarang.format(Function.getServerDate(con)));
                            newStok.setKodeBarang(d.getKodeBarang());
                            newStok.setKodeGudang(p.getGudangAsal());
                            newStok.setStokAwal(stokKeluar.getStokAkhir());
                            newStok.setStokMasuk(0);
                            newStok.setStokKeluar(d.getQty());
                            newStok.setStokAkhir(stokKeluar.getStokAkhir()-d.getQty());
                            StokBarangDAO.insert(con, newStok);
                        }
                        StokBarang stokMasuk = StokBarangDAO.get(con, tglBarang.format(Function.getServerDate(con)), 
                                d.getKodeBarang(), p.getGudangTujuan());
                        if(stokMasuk==null)
                            status = "Stok barang "+d.getNamaBarang()+" tidak ditemukan";
                        else{
                            if(stokMasuk.getTanggal().equals(tglBarang.format(Function.getServerDate(con)))){
                                stokMasuk.setStokMasuk(stokMasuk.getStokMasuk()+d.getQty());
                                stokMasuk.setStokAkhir(stokMasuk.getStokAkhir()+d.getQty());
                                StokBarangDAO.update(con, stokMasuk);
                            }else{
                                StokBarang newStok = new StokBarang();
                                newStok.setTanggal(tglBarang.format(Function.getServerDate(con)));
                                newStok.setKodeBarang(d.getKodeBarang());
                                newStok.setKodeGudang(p.getGudangTujuan());
                                newStok.setStokAwal(stokMasuk.getStokAkhir());
                                newStok.setStokMasuk(d.getQty());
                                newStok.setStokKeluar(0);
                                newStok.setStokAkhir(stokMasuk.getStokAkhir()+d.getQty());
                                StokBarangDAO.insert(con, newStok);
                            }
                        }
                    }
                }
                LogBarang lbKeluar = LogBarangDAO.getLastByBarangAndGudang(con, d.getKodeBarang(), p.getGudangAsal());
                LogBarang logKeluar = new LogBarang();
                logKeluar.setTanggal(tglSql.format(Function.getServerDate(con)));
                logKeluar.setKodeBarang(d.getKodeBarang());
                logKeluar.setKodeGudang(p.getGudangAsal());
                logKeluar.setKategori("Pindah Barang");
                logKeluar.setKeterangan(p.getNoPindah());
                logKeluar.setStokAwal(lbKeluar.getStokAkhir());
                logKeluar.setNilaiAwal(lbKeluar.getNilaiAkhir());
                logKeluar.setStokMasuk(0);
                logKeluar.setNilaiMasuk(0);
                logKeluar.setStokKeluar(d.getQty());
                logKeluar.setNilaiKeluar(d.getNilai()*d.getQty());
                logKeluar.setStokAkhir(lbKeluar.getStokAkhir()-d.getQty());
                logKeluar.setNilaiAkhir(lbKeluar.getNilaiAkhir()-(d.getNilai()*d.getQty()));
                LogBarangDAO.insert(con, logKeluar);

                LogBarang lbMasuk = LogBarangDAO.getLastByBarangAndGudang(con, d.getKodeBarang(), p.getGudangTujuan());
                LogBarang logMasuk = new LogBarang();
                logMasuk.setTanggal(tglSql.format(Function.getServerDate(con)));
                logMasuk.setKodeBarang(d.getKodeBarang());
                logMasuk.setKodeGudang(p.getGudangTujuan());
                logMasuk.setKategori("Pindah Barang");
                logMasuk.setKeterangan(p.getNoPindah());
                logMasuk.setStokAwal(lbMasuk.getStokAkhir());
                logMasuk.setNilaiAwal(lbMasuk.getNilaiAkhir());
                logMasuk.setStokMasuk(d.getQty());
                logMasuk.setNilaiMasuk(d.getNilai()*d.getQty());
                logMasuk.setStokKeluar(0);
                logMasuk.setNilaiKeluar(0);
                logMasuk.setStokAkhir(lbMasuk.getStokAkhir()+d.getQty());
                logMasuk.setNilaiAkhir(lbMasuk.getNilaiAkhir()+(d.getNilai()*d.getQty()));
                LogBarangDAO.insert(con, logMasuk);

            }
            
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
    public static String batalPindahBarang(Connection con, PindahBarangHead p){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            PindahBarangHeadDAO.update(con, p);
            
            List<PindahBarangDetail> stokBarang = new ArrayList<>();
            for(PindahBarangDetail d : p.getListPindahBarangDetail()){
                Boolean inStok = false;
                for(PindahBarangDetail detail : stokBarang){
                    if(d.getKodeBarang().equals(detail.getKodeBarang())){
                        detail.setNilai((detail.getNilai()*detail.getQty()+d.getNilai()*d.getQty())/
                                (detail.getQty()+d.getQty()));
                        detail.setQty(detail.getQty()+d.getQty());
                        inStok = true;
                    }
                }
                if(!inStok){
                    PindahBarangDetail temp = new PindahBarangDetail();
                    temp.setNoPindah(d.getNoPindah());
                    temp.setNoUrut(d.getNoUrut());
                    temp.setKodeBarang(d.getKodeBarang());
                    temp.setNamaBarang(d.getNamaBarang());
                    temp.setKeterangan(d.getKeterangan());
                    temp.setSatuan(d.getSatuan());
                    temp.setQty(d.getQty());
                    temp.setNilai(d.getNilai());
                    stokBarang.add(temp);
                }
            }
            KeuanganDAO.delete(con, "Stok Barang", p.getGudangAsal(), "Pindah Barang - "+p.getNoPindah());
            KeuanganDAO.delete(con, "Stok Barang", p.getGudangTujuan(), "Pindah Barang - "+p.getNoPindah());
            
            for(PindahBarangDetail d : stokBarang){
                StokBarang stokBatalKeluar = StokBarangDAO.get(con, tglBarang.format(tglSql.parse(p.getTglPindah())), 
                        d.getKodeBarang(), p.getGudangAsal());
                stokBatalKeluar.setStokKeluar(stokBatalKeluar.getStokKeluar()-d.getQty());
                stokBatalKeluar.setStokAkhir(stokBatalKeluar.getStokAkhir()+d.getQty());
                StokBarangDAO.update(con, stokBatalKeluar);
                resetStokBarang(con, d.getKodeBarang(), p.getGudangAsal(), tglBarang.format(tglSql.parse(p.getTglPindah())));
                
                StokBarang stokBatalMasuk = StokBarangDAO.get(con, tglBarang.format(tglSql.parse(p.getTglPindah())), 
                        d.getKodeBarang(), p.getGudangTujuan());
                stokBatalMasuk.setStokMasuk(stokBatalMasuk.getStokMasuk()-d.getQty());
                stokBatalMasuk.setStokAkhir(stokBatalMasuk.getStokAkhir()-d.getQty());
                StokBarangDAO.update(con, stokBatalMasuk);
                resetStokBarang(con, d.getKodeBarang(), p.getGudangTujuan(), tglBarang.format(tglSql.parse(p.getTglPindah())));

                LogBarangDAO.delete(con, d.getKodeBarang(), p.getGudangAsal(), "Pindah Barang", p.getNoPindah());
                resetLogBarang(con, p.getTglPindah(), d.getKodeBarang(), p.getGudangAsal());
                
                LogBarangDAO.delete(con, d.getKodeBarang(), p.getGudangTujuan(), "Pindah Barang", p.getNoPindah());
                resetLogBarang(con, p.getTglPindah(), d.getKodeBarang(), p.getGudangTujuan());
            }
            
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
//    public static String newReturPenjualan(Connection con, ReturPenjualanHead retur){
//        try{
//            con.setAutoCommit(false);
//            String status = "true";
//            
//            ReturPenjualanHeadDAO.insert(con, retur);
//            
//            String noKeuangan = KeuanganDAO.getId(con);
//            insertKeuangan(con, noKeuangan, tglSql.format(Function.getServerDate(con)), retur.getTipeKeuangan(), "Retur Penjualan", 
//                    "Retur Penjualan - "+retur.getNoReturPenjualan(), -retur.getTotalRetur(), sistem.getUser().getKodeUser());
//            insertKeuangan(con, noKeuangan, tglSql.format(Function.getServerDate(con)), "Retur Penjualan", "Retur Penjualan", 
//                    "Retur Penjualan - "+retur.getNoReturPenjualan(), retur.getTotalRetur(), sistem.getUser().getKodeUser());
//            
//            double hpp = 0;
//            List<ReturPenjualanDetail> stokBarang = new ArrayList<>();
//            for(ReturPenjualanDetail d : retur.getListReturPenjualanDetail()){
//                ReturPenjualanDetailDAO.insert(con, d);
//                
//                hpp = hpp + d.getNilai()*d.getQty();
//                
//                Boolean inStok = false;
//                for(ReturPenjualanDetail detail : stokBarang){
//                    if(d.getKodeBarang().equals(detail.getKodeBarang())){
//                        detail.setNilai((detail.getNilai()*detail.getQty()+d.getNilai()*d.getQty())/
//                                (detail.getQty()+d.getQty()));
//                        detail.setQty(detail.getQty()+d.getQty());
//                        inStok = true;
//                    }
//                }
//                if(!inStok)
//                    stokBarang.add(d);
//            }
//            insertKeuangan(con, noKeuangan, tglSql.format(Function.getServerDate(con)), "HPP", "Retur Penjualan", 
//                    "Retur Penjualan - "+retur.getNoReturPenjualan(), -hpp, sistem.getUser().getKodeUser());
//            
//            for(ReturPenjualanDetail d : stokBarang){
//                StokBarang stok = StokBarangDAO.get(con, tglBarang.format(Function.getServerDate(con)), d.getKodeBarang());
//                if(stok==null){
//                    StokBarang newStok = new StokBarang();
//                    newStok.setTanggal(tglBarang.format(Function.getServerDate(con)));
//                    newStok.setKodeBarang(d.getKodeBarang());
//                    newStok.setStokAwal(0);
//                    newStok.setStokMasuk(d.getQty());
//                    newStok.setStokKeluar(0);
//                    newStok.setStokAkhir(d.getQty());
//                    StokBarangDAO.insert(con, newStok);
//                }else{
//                    if(stok.getTanggal().equals(tglBarang.format(Function.getServerDate(con)))){
//                        stok.setStokMasuk(stok.getStokMasuk()+d.getQty());
//                        stok.setStokAkhir(stok.getStokAkhir()+d.getQty());
//                        StokBarangDAO.update(con, stok);
//                    }else{
//                        StokBarang newStok = new StokBarang();
//                        newStok.setTanggal(tglBarang.format(Function.getServerDate(con)));
//                        newStok.setKodeBarang(d.getKodeBarang());
//                        newStok.setStokAwal(stok.getStokAkhir());
//                        newStok.setStokMasuk(d.getQty());
//                        newStok.setStokKeluar(0);
//                        newStok.setStokAkhir(stok.getStokAkhir()+d.getQty());
//                        StokBarangDAO.insert(con, newStok);
//                    }
//                }
//                LogBarang lb = LogBarangDAO.getLastByBarang(con, d.getKodeBarang());
//                double nilai = d.getNilai()* d.getQty();
//
//                LogBarang log = new LogBarang();
//                log.setTanggal(tglSql.format(Function.getServerDate(con)));
//                log.setKodeBarang(d.getKodeBarang());
//                log.setKategori("Retur Penjualan");
//                log.setKeterangan(retur.getNoReturPenjualan());
//                log.setStokAwal(lb.getStokAkhir());
//                log.setNilaiAwal(lb.getNilaiAkhir());
//                log.setStokMasuk(d.getQty());
//                log.setNilaiMasuk(nilai);
//                log.setStokKeluar(0);
//                log.setNilaiKeluar(0);
//                log.setStokAkhir(lb.getStokAkhir()+d.getQty());
//                log.setNilaiAkhir(lb.getNilaiAkhir()+nilai);
//                LogBarangDAO.insert(con, log);
//
//                insertKeuangan(con, noKeuangan, tglSql.format(Function.getServerDate(con)), "Stok Barang", d.getKodeBarang(), 
//                        "Retur Penjualan - "+retur.getNoReturPenjualan(), nilai, sistem.getUser().getKodeUser());
//            }
//            if(status.equals("true"))
//                con.commit();
//            else 
//                con.rollback();
//            con.setAutoCommit(true);
//            
//            return status;
//        }catch(Exception e){
//            try{
//                con.rollback();
//            con.setAutoCommit(true);
//                return e.toString();
//            }catch(SQLException ex){
//                return ex.toString();
//            }
//        }
//    }
//    public static String batalReturPenjualan(Connection con, ReturPenjualanHead retur){
//        try{
//            con.setAutoCommit(false);
//            String status = "true";
//            
//            ReturPenjualanHeadDAO.update(con, retur);
//            
//            KeuanganDAO.delete(con, retur.getTipeKeuangan(), "Retur Penjualan", "Retur Penjualan - "+retur.getNoReturPenjualan());
//            KeuanganDAO.delete(con, "Retur Penjualan", "Retur Penjualan", "Retur Penjualan - "+retur.getNoReturPenjualan());
//            
//            retur.setListReturPenjualanDetail(ReturPenjualanDetailDAO.getAllByNoRetur(con, retur.getNoReturPenjualan()));
//            double hpp = 0;
//            List<ReturPenjualanDetail> stokBarang = new ArrayList<>();
//            for(ReturPenjualanDetail d : retur.getListReturPenjualanDetail()){
//                hpp = hpp + d.getNilai()*d.getQty();
//                
//                Boolean inStok = false;
//                for(ReturPenjualanDetail detail : stokBarang){
//                    if(d.getKodeBarang().equals(detail.getKodeBarang())){
//                        detail.setNilai((detail.getNilai()*detail.getQty()+d.getNilai()*d.getQty())/
//                                (detail.getQty()+d.getQty()));
//                        detail.setQty(detail.getQty()+d.getQty());
//                        inStok = true;
//                    }
//                }
//                if(!inStok)
//                    stokBarang.add(d);
//            }
//            KeuanganDAO.delete(con, "HPP", "HPP", "Retur Penjualan - "+retur.getNoReturPenjualan());
//            
//            for(ReturPenjualanDetail d : stokBarang){
//                StokBarang stok = StokBarangDAO.get(con, tglBarang.format(tglSql.parse(retur.getTglReturPenjualan())), d.getKodeBarang());
//                if(stok==null)
//                    status = "Stok barang "+d.getKodeBarang()+" tidak ditemukan";
//                else if(stok.getStokAkhir()<d.getQty())
//                    status = "Stok barang "+d.getKodeBarang()+" tidak mencukupi";
//                else{    
//                    stok.setStokMasuk(stok.getStokMasuk()-d.getQty());
//                    stok.setStokAkhir(stok.getStokAkhir()-d.getQty());
//                    StokBarangDAO.update(con, stok);
//                    resetStokBarang(con, d.getKodeBarang(), tglBarang.format(tglSql.parse(retur.getTglReturPenjualan())));
//
//                    LogBarangDAO.delete(con, d.getKodeBarang(), "Retur Penjualan", retur.getNoReturPenjualan());
//                    resetLogBarang(con, retur.getTglReturPenjualan(), d.getKodeBarang());
//
//                    KeuanganDAO.delete(con, "Stok Barang", d.getKodeBarang(), "Retur Penjualan - "+retur.getNoReturPenjualan());
//                }
//            }
//
//            if(status.equals("true"))
//                con.commit();
//            else 
//                con.rollback();
//            con.setAutoCommit(true);
//            
//            return status;
//        }catch(Exception e){
//            try{
//                con.rollback();
//                con.setAutoCommit(true);
//                return e.toString();
//            }catch(SQLException ex){
//                return ex.toString();
//            }
//        }
//    }
    
    public static String newProduksiBarang(Connection con, ProduksiHead produksi){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            String noKeuangan = KeuanganDAO.getId(con);
            Date tanggal = Function.getServerDate(con);
            
            if(produksi.getJenisProduksi().equals("Bahan - Barang")){
                for(ProduksiDetailBahan d : produksi.getListProduksiDetailBahan()){
                    Bahan b = BahanDAO.get(con, d.getKodeBarang());
                    d.setBahan(b);
                    d.setNilai(d.getQty()*b.getHargaBeli()/b.getBeratBersih());

                    if(d.isBahanHabis()){
                        b.setStatus("false");
                        BahanDAO.update(con, b);
                    }
                    StokBahan stokAkhirBahan = StokBahanDAO.getStokAkhir(con, d.getKodeBarang(), produksi.getKodeGudang());
                    if(stokAkhirBahan.getTanggal().equals(tglBarang.format(tanggal))){
                        stokAkhirBahan.setStokKeluar(stokAkhirBahan.getStokKeluar()+d.getQty());
                        stokAkhirBahan.setStokAkhir(stokAkhirBahan.getStokAkhir()-d.getQty());
                        StokBahanDAO.update(con, stokAkhirBahan);
                    }else{
                        StokBahan stokBahan = new StokBahan();
                        stokBahan.setTanggal(tglBarang.format(tanggal));
                        stokBahan.setKodeBahan(d.getKodeBarang());
                        stokBahan.setKodeGudang(produksi.getKodeGudang());
                        stokBahan.setStokAwal(stokAkhirBahan.getStokAkhir());
                        stokBahan.setStokMasuk(0);
                        stokBahan.setStokKeluar(d.getQty());
                        stokBahan.setStokAkhir(stokAkhirBahan.getStokAkhir()-d.getQty());
                        StokBahanDAO.insert(con, stokBahan);
                    }
                    LogBahan lb = LogBahanDAO.getLastByBahanAndGudang(con, d.getKodeBarang(), produksi.getKodeGudang());
                    LogBahan log = new LogBahan();
                    log.setTanggal(tglSql.format(tanggal));
                    log.setKodeBahan(d.getKodeBarang());
                    log.setKodeGudang(produksi.getKodeGudang());
                    log.setKategori("Produksi");
                    log.setKeterangan(produksi.getKodeProduksi());
                    log.setStokAwal(lb.getStokAkhir());
                    log.setNilaiAwal(lb.getNilaiAkhir());
                    log.setStokMasuk(0);
                    log.setNilaiMasuk(0);
                    log.setStokKeluar(d.getQty());
                    log.setNilaiKeluar(d.getNilai());
                    log.setStokAkhir(lb.getStokAkhir()-d.getQty());
                    log.setNilaiAkhir(lb.getNilaiAkhir()-d.getNilai());
                    LogBahanDAO.insert(con, log);

                    produksi.setMaterialCost(produksi.getMaterialCost() + d.getNilai());
                    ProduksiDetailBahanDAO.insert(con, d);
                }
                insertKeuangan(con, noKeuangan, tglSql.format(tanggal), "Stok Bahan", produksi.getKodeGudang(),
                        "Produksi Barang - "+produksi.getKodeProduksi(), -produksi.getMaterialCost(), sistem.getUser().getKodeUser());
            }else if(produksi.getJenisProduksi().equals("Barang - Barang")){
                List<ProduksiDetailBahan> groupByBarang = new ArrayList<>();
                for(ProduksiDetailBahan d : produksi.getListProduksiDetailBahan()){
                    LogBarang log = LogBarangDAO.getLastByBarangAndGudang(con, d.getKodeBarang(), produksi.getKodeGudang());
                    d.setNilai(log.getNilaiAkhir()/log.getStokAkhir()*d.getQty());
                    ProduksiDetailBahanDAO.insert(con, d);
                    produksi.setMaterialCost(produksi.getMaterialCost() + d.getNilai());
                    
                    boolean x = true;
                    for(ProduksiDetailBahan p : groupByBarang){
                        if(d.getKodeBarang().equals(p.getKodeBarang())){
                            p.setQty(p.getQty()+d.getQty());
                            p.setNilai(p.getNilai()+d.getNilai());
                            x = false;
                        }
                    }
                    if(x)
                        groupByBarang.add(d);
                }
                for(ProduksiDetailBahan d : groupByBarang){    
                    StokBarang stokAkhir = StokBarangDAO.get(con, tglBarang.format(tanggal), d.getKodeBarang(), produksi.getKodeGudang());
                    if(stokAkhir.getTanggal().equals(tglBarang.format(tanggal))){
                        stokAkhir.setStokKeluar(stokAkhir.getStokKeluar()+d.getQty());
                        stokAkhir.setStokAkhir(stokAkhir.getStokAkhir()-d.getQty());
                        StokBarangDAO.update(con, stokAkhir);
                    }else{
                        StokBarang stokBarang = new StokBarang();
                        stokBarang.setTanggal(tglBarang.format(tanggal));
                        stokBarang.setKodeBarang(d.getKodeBarang());
                        stokBarang.setKodeGudang(produksi.getKodeGudang());
                        stokBarang.setStokAwal(stokAkhir.getStokAkhir());
                        stokBarang.setStokMasuk(0);
                        stokBarang.setStokKeluar(d.getQty());
                        stokBarang.setStokAkhir(stokAkhir.getStokAkhir()-d.getQty());
                        StokBarangDAO.insert(con, stokBarang);
                    }

                    LogBarang logBarang = LogBarangDAO.getLastByBarangAndGudang(con, d.getKodeBarang(), produksi.getKodeGudang());
                    LogBarang newLogBarang = new LogBarang();
                    newLogBarang.setTanggal(tglSql.format(tanggal));
                    newLogBarang.setKodeBarang(d.getKodeBarang());
                    newLogBarang.setKodeGudang(produksi.getKodeGudang());
                    newLogBarang.setKategori("Produksi");
                    newLogBarang.setKeterangan(produksi.getKodeProduksi());
                    newLogBarang.setStokAwal(logBarang.getStokAkhir());
                    newLogBarang.setNilaiAwal(logBarang.getNilaiAkhir());
                    newLogBarang.setStokMasuk(0);
                    newLogBarang.setNilaiMasuk(0);
                    newLogBarang.setStokKeluar(d.getQty());
                    newLogBarang.setNilaiKeluar(d.getNilai());
                    newLogBarang.setStokAkhir(logBarang.getStokAkhir()-d.getQty());
                    newLogBarang.setNilaiAkhir(logBarang.getNilaiAkhir()-d.getNilai());
                    LogBarangDAO.insert(con, newLogBarang);

                }
                insertKeuangan(con, noKeuangan, tglSql.format(tanggal), "Stok Barang", produksi.getKodeGudang(),
                        "Produksi Barang - "+produksi.getKodeProduksi(), -produksi.getMaterialCost(), sistem.getUser().getKodeUser());
            }
            ProduksiHeadDAO.insert(con, produksi);
            
            insertKeuangan(con, noKeuangan, tglSql.format(tanggal), "Stok Barang", produksi.getKodeGudang(), 
                    "Produksi Barang - "+produksi.getKodeProduksi(), produksi.getMaterialCost(), sistem.getUser().getKodeUser());
            
            double totalProduksi = 0;
            for(ProduksiDetailBarang d : produksi.getListProduksiDetailBarang()){
                totalProduksi = totalProduksi + (d.getQty()*d.getBarang().getBerat());
            }
            
            List<ProduksiDetailBarang> groupByBarang = new ArrayList<>();
            for(ProduksiDetailBarang d : produksi.getListProduksiDetailBarang()){
                d.setNilai(produksi.getMaterialCost()/totalProduksi*(d.getQty()*d.getBarang().getBerat()));
                ProduksiDetailBarangDAO.insert(con, d);
                boolean x = true;
                for(ProduksiDetailBarang p : groupByBarang){
                    if(d.getKodeBarang().equals(p.getKodeBarang())){
                        p.setQty(p.getQty()+d.getQty());
                        p.setNilai(p.getNilai()+d.getNilai());
                        x = false;
                    }
                }
                if(x)
                    groupByBarang.add(d);
            }
            for(ProduksiDetailBarang d : groupByBarang){    
                StokBarang stokAkhir = StokBarangDAO.get(con, tglBarang.format(tanggal), d.getKodeBarang(), produksi.getKodeGudang());
                if(stokAkhir.getTanggal().equals(tglBarang.format(tanggal))){
                    stokAkhir.setStokMasuk(stokAkhir.getStokMasuk()+d.getQty());
                    stokAkhir.setStokAkhir(stokAkhir.getStokAkhir()+d.getQty());
                    StokBarangDAO.update(con, stokAkhir);
                }else{
                    StokBarang stokBarang = new StokBarang();
                    stokBarang.setTanggal(tglBarang.format(tanggal));
                    stokBarang.setKodeBarang(d.getKodeBarang());
                    stokBarang.setKodeGudang(produksi.getKodeGudang());
                    stokBarang.setStokAwal(stokAkhir.getStokAkhir());
                    stokBarang.setStokMasuk(d.getQty());
                    stokBarang.setStokKeluar(0);
                    stokBarang.setStokAkhir(stokAkhir.getStokAkhir()+d.getQty());
                    StokBarangDAO.insert(con, stokBarang);
                }
                
                LogBarang logBarang = LogBarangDAO.getLastByBarangAndGudang(con, d.getKodeBarang(), produksi.getKodeGudang());
                LogBarang newLogBarang = new LogBarang();
                newLogBarang.setTanggal(tglSql.format(tanggal));
                newLogBarang.setKodeBarang(d.getKodeBarang());
                newLogBarang.setKodeGudang(produksi.getKodeGudang());
                newLogBarang.setKategori("Produksi");
                newLogBarang.setKeterangan(produksi.getKodeProduksi());
                newLogBarang.setStokAwal(logBarang.getStokAkhir());
                newLogBarang.setNilaiAwal(logBarang.getNilaiAkhir());
                newLogBarang.setStokMasuk(d.getQty());
                newLogBarang.setNilaiMasuk(d.getNilai());
                newLogBarang.setStokKeluar(0);
                newLogBarang.setNilaiKeluar(0);
                newLogBarang.setStokAkhir(logBarang.getStokAkhir()+d.getQty());
                newLogBarang.setNilaiAkhir(logBarang.getNilaiAkhir()+d.getNilai());
                LogBarangDAO.insert(con, newLogBarang);
                
            }
            for(ProduksiOperator op : produksi.getListProduksiOperator()){
                ProduksiOperatorDAO.insert(con, op);
            }
            
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
    public static String batalProduksiBarang(Connection con, ProduksiHead produksi){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            Date tanggal = Function.getServerDate(con);
            for(ProduksiDetailBahan d : produksi.getListProduksiDetailBahan()){
                Bahan b = BahanDAO.get(con, d.getKodeBarang());
                b.setStatus("true");
                BahanDAO.update(con, b);
                
                d.setNilai(d.getQty()*b.getHargaBeli()/b.getBeratBersih());

                StokBahan stokAkhirBahan = StokBahanDAO.getStokAkhir(con, d.getKodeBarang(), produksi.getKodeGudang());
                if(stokAkhirBahan.getTanggal().equals(tglBarang.format(tanggal))){
                    stokAkhirBahan.setStokMasuk(stokAkhirBahan.getStokMasuk()+d.getQty());
                    stokAkhirBahan.setStokAkhir(stokAkhirBahan.getStokAkhir()+d.getQty());
                    StokBahanDAO.update(con, stokAkhirBahan);
                }else{
                    StokBahan stokBahan = new StokBahan();
                    stokBahan.setTanggal(tglBarang.format(tanggal));
                    stokBahan.setKodeBahan(d.getKodeBarang());
                    stokBahan.setKodeGudang(produksi.getKodeGudang());
                    stokBahan.setStokAwal(stokAkhirBahan.getStokAkhir());
                    stokBahan.setStokMasuk(d.getQty());
                    stokBahan.setStokKeluar(0);
                    stokBahan.setStokAkhir(stokAkhirBahan.getStokAkhir()+d.getQty());
                    StokBahanDAO.insert(con, stokBahan);
                }
                LogBahan lb = LogBahanDAO.getLastByBahanAndGudang(con, d.getKodeBarang(), produksi.getKodeGudang());
                LogBahan log = new LogBahan();
                log.setTanggal(tglSql.format(tanggal));
                log.setKodeBahan(d.getKodeBarang());
                log.setKodeGudang(produksi.getKodeGudang());
                log.setKategori("Batal Produksi");
                log.setKeterangan(produksi.getKodeProduksi());
                log.setStokAwal(lb.getStokAkhir());
                log.setNilaiAwal(lb.getNilaiAkhir());
                log.setStokMasuk(d.getQty());
                log.setNilaiMasuk(d.getNilai());
                log.setStokKeluar(0);
                log.setNilaiKeluar(0);
                log.setStokAkhir(lb.getStokAkhir()+d.getQty());
                log.setNilaiAkhir(lb.getNilaiAkhir()+d.getNilai());
                LogBahanDAO.insert(con, log);
            }
            ProduksiHeadDAO.update(con, produksi);
            
            String noKeuangan = KeuanganDAO.getId(con);
            
            insertKeuangan(con, noKeuangan, tglSql.format(tanggal), "Stok Bahan", produksi.getKodeGudang(),
                    "Batal Produksi Barang - "+produksi.getKodeProduksi(), produksi.getMaterialCost(), sistem.getUser().getKodeUser());
            insertKeuangan(con, noKeuangan, tglSql.format(tanggal), "Stok Barang", produksi.getKodeGudang(), 
                    "Batal Produksi Barang - "+produksi.getKodeProduksi(), -produksi.getMaterialCost(), sistem.getUser().getKodeUser());
            
            produksi.setListProduksiDetailBarang(ProduksiDetailBarangDAO.get(con, produksi.getKodeProduksi()));
            List<ProduksiDetailBarang> groupByBarang = new ArrayList<>();
            for(ProduksiDetailBarang d : produksi.getListProduksiDetailBarang()){
                boolean x = true;
                for(ProduksiDetailBarang p : groupByBarang){
                    if(d.getKodeBarang().equals(p.getKodeBarang())){
                        p.setQty(p.getQty()+d.getQty());
                        p.setNilai(p.getNilai()+d.getNilai());
                        x = false;
                    }
                }
                if(x)
                    groupByBarang.add(d);
            }
            for(ProduksiDetailBarang d : groupByBarang){    
                StokBarang stokAkhir = StokBarangDAO.get(con, tglBarang.format(tanggal), d.getKodeBarang(), produksi.getKodeGudang());
                if(stokAkhir==null)
                    status = "Stok barang "+d.getKodeBarang()+" tidak ditemukan";
                else if(stokAkhir.getStokAkhir()<d.getQty())
                    status = "Stok barang "+d.getKodeBarang()+" tidak mencukupi";
                else{    
                    if(stokAkhir.getTanggal().equals(tglBarang.format(tanggal))){
                        stokAkhir.setStokKeluar(stokAkhir.getStokKeluar()+d.getQty());
                        stokAkhir.setStokAkhir(stokAkhir.getStokAkhir()-d.getQty());
                        StokBarangDAO.update(con, stokAkhir);
                    }else{
                        StokBarang stokBarang = new StokBarang();
                        stokBarang.setTanggal(tglBarang.format(tanggal));
                        stokBarang.setKodeBarang(d.getKodeBarang());
                        stokBarang.setKodeGudang(produksi.getKodeGudang());
                        stokBarang.setStokAwal(stokAkhir.getStokAkhir());
                        stokBarang.setStokMasuk(0);
                        stokBarang.setStokKeluar(d.getQty());
                        stokBarang.setStokAkhir(stokAkhir.getStokAkhir()-d.getQty());
                        StokBarangDAO.insert(con, stokBarang);
                    }

                    LogBarang logBarang = LogBarangDAO.getLastByBarangAndGudang(con, d.getKodeBarang(), produksi.getKodeGudang());
                    LogBarang newLogBarang = new LogBarang();
                    newLogBarang.setTanggal(tglSql.format(tanggal));
                    newLogBarang.setKodeBarang(d.getKodeBarang());
                    newLogBarang.setKodeGudang(produksi.getKodeGudang());
                    newLogBarang.setKategori("Batal Produksi");
                    newLogBarang.setKeterangan(produksi.getKodeProduksi());
                    newLogBarang.setStokAwal(logBarang.getStokAkhir());
                    newLogBarang.setNilaiAwal(logBarang.getNilaiAkhir());
                    newLogBarang.setStokMasuk(0);
                    newLogBarang.setNilaiMasuk(0);
                    newLogBarang.setStokKeluar(d.getQty());
                    newLogBarang.setNilaiKeluar(d.getNilai());
                    newLogBarang.setStokAkhir(logBarang.getStokAkhir()-d.getQty());
                    newLogBarang.setNilaiAkhir(logBarang.getNilaiAkhir()-d.getNilai());
                    LogBarangDAO.insert(con, newLogBarang);

                }
            }
            if(status.equals("true"))
                con.commit();
            else 
                con.rollback();
            con.setAutoCommit(true);
            
            return status;
        }catch(Exception e){
            try{
                con.rollback();
            con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String newBebanPenjualan(Connection con, BebanPenjualanHead b){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            String noKeuangan = KeuanganDAO.getId(con);
            
            BebanPenjualanHeadDAO.insert(con, b);
            double totalPenjualan = 0;
            for(BebanPenjualanDetail d : b.getListBebanPenjualanDetail()){
                totalPenjualan = totalPenjualan + d.getPenjualanHead().getTotalPenjualan();
            }
            for(BebanPenjualanDetail d : b.getListBebanPenjualanDetail()){
                double beban = d.getPenjualanHead().getTotalPenjualan()/totalPenjualan*b.getTotalBebanPenjualan();
                d.getPenjualanHead().setTotalBebanPenjualan(d.getPenjualanHead().getTotalBebanPenjualan()+beban);
                PenjualanHeadDAO.update(con, d.getPenjualanHead());
                
                d.setNoBebanPenjualan(b.getNoBebanPenjualan());
                d.setJumlahRp(beban);
                d.setStatus("true");
                BebanPenjualanDetailDAO.insert(con, d);
            }
            
            insertKeuangan(con, noKeuangan, tglSql.format(Function.getServerDate(con)), "Pendapatan/Beban", "Beban Penjualan Langsung", 
                    b.getNoBebanPenjualan(), -b.getTotalBebanPenjualan(), sistem.getUser().getKodeUser());
            insertKeuangan(con, noKeuangan, tglSql.format(Function.getServerDate(con)), b.getTipeKeuangan(), "Beban Penjualan Langsung", 
                    b.getNoBebanPenjualan(), -b.getTotalBebanPenjualan(), sistem.getUser().getKodeUser());
            
            if(status.equals("true"))
                con.commit();
            else 
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
            con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String batalBebanPenjualan(Connection con, BebanPenjualanHead b){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            BebanPenjualanHeadDAO.update(con, b);
            for(BebanPenjualanDetail d : b.getListBebanPenjualanDetail()){
                PenjualanHead p = PenjualanHeadDAO.get(con, d.getNoPenjualan());
                p.setTotalBebanPenjualan(p.getTotalBebanPenjualan()-d.getJumlahRp());
                PenjualanHeadDAO.update(con, p);
                
                d.setStatus("false");
                BebanPenjualanDetailDAO.update(con, d);
            }
            
            KeuanganDAO.delete(con, "Pendapatan/Beban", "Beban Penjualan Langsung", b.getNoBebanPenjualan());
            KeuanganDAO.delete(con, b.getTipeKeuangan(), "Beban Penjualan Langsung", b.getNoBebanPenjualan());
            
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
    
    public static String newBebanProduksi(Connection con, BebanProduksiHead b){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            String noKeuangan = KeuanganDAO.getId(con);
            
            BebanProduksiHeadDAO.insert(con, b);
            double totalProduksi = 0;
            for(BebanProduksiDetail d : b.getListBebanProduksiDetail()){
                totalProduksi = totalProduksi + d.getProduksiHead().getMaterialCost();
            }
            for(BebanProduksiDetail d : b.getListBebanProduksiDetail()){
                double beban = d.getProduksiHead().getMaterialCost()/totalProduksi*b.getTotalBebanProduksi();
                d.getProduksiHead().setBiayaProduksi(d.getProduksiHead().getBiayaProduksi()+beban);
                ProduksiHeadDAO.update(con, d.getProduksiHead());
                
                d.setNoBebanProduksi(b.getNoBebanProduksi());
                d.setJumlahRp(beban);
                d.setStatus("true");
                BebanProduksiDetailDAO.insert(con, d);
            }
            
            insertKeuangan(con, noKeuangan, tglSql.format(Function.getServerDate(con)), "Pendapatan/Beban", "Beban Produksi Langsung", 
                    b.getNoBebanProduksi(), -b.getTotalBebanProduksi(), sistem.getUser().getKodeUser());
            insertKeuangan(con, noKeuangan, tglSql.format(Function.getServerDate(con)), b.getTipeKeuangan(), "Beban Produksi Langsung", 
                    b.getNoBebanProduksi(), -b.getTotalBebanProduksi(), sistem.getUser().getKodeUser());
            
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
    public static String batalBebanProduksi(Connection con, BebanProduksiHead b){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            BebanProduksiHeadDAO.update(con, b);
            for(BebanProduksiDetail d : b.getListBebanProduksiDetail()){
                ProduksiHead p = ProduksiHeadDAO.get(con, d.getNoProduksi());
                p.setBiayaProduksi(p.getBiayaProduksi()-d.getJumlahRp());
                ProduksiHeadDAO.update(con, p);
                
                d.setStatus("false");
                BebanProduksiDetailDAO.update(con, d);
            }
            
            KeuanganDAO.delete(con, "Pendapatan/Beban", "Beban Produksi Langsung", b.getNoBebanProduksi());
            KeuanganDAO.delete(con, b.getTipeKeuangan(), "Beban Produksi Langsung", b.getNoBebanProduksi());
            
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
    
    public static String newKeuangan(Connection con, Keuangan keu){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            String noKeuangan = KeuanganDAO.getId(con);
            keu.setNoKeuangan(noKeuangan);
            KeuanganDAO.insert(con, keu);
            
            insertKeuangan(con, noKeuangan, tglSql.format(Function.getServerDate(con)), "Pendapatan/Beban", keu.getKategori(), 
                    keu.getDeskripsi(), keu.getJumlahRp(), sistem.getUser().getKodeUser());
            
            if(status.equals("true"))
                con.commit();
            else 
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
            con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String batalTransaksi(Connection con, Keuangan k)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            String noKeuangan = KeuanganDAO.getId(con);
            insertKeuangan(con, noKeuangan, tglSql.format(Function.getServerDate(con)), k.getTipeKeuangan(), k.getKategori(), 
                    "Batal Transaksi - "+k.getDeskripsi(), k.getJumlahRp()*-1, sistem.getUser().getKodeUser());
            insertKeuangan(con, noKeuangan, tglSql.format(Function.getServerDate(con)), "Pendapatan/Beban", k.getKategori(), 
                    "Batal Transaksi - "+k.getDeskripsi(), k.getJumlahRp()*-1, sistem.getUser().getKodeUser());

            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String transferKeuangan(Connection con, String dari, String ke, String keterangan, double jumlahRp)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            String noKeuangan = KeuanganDAO.getId(con);
            insertKeuangan(con, noKeuangan, tglSql.format(Function.getServerDate(con)), dari, "Transfer Keuangan", 
                    keterangan, jumlahRp*-1, sistem.getUser().getKodeUser());
            insertKeuangan(con, noKeuangan, tglSql.format(Function.getServerDate(con)), ke, "Transfer Keuangan", 
                    keterangan, jumlahRp, sistem.getUser().getKodeUser());

            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String newHutang(Connection con, Hutang hutang){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            String noKeuangan = KeuanganDAO.getId(con);
            HutangDAO.insert(con, hutang);
            
            Keuangan k = new Keuangan();
            k.setNoKeuangan(noKeuangan);
            k.setTglKeuangan(tglSql.format(Function.getServerDate(con)));
            k.setTipeKeuangan(hutang.getTipeKeuangan());
            k.setKategori(hutang.getKategori());
            k.setDeskripsi(hutang.getNoHutang()+" - "+hutang.getKeterangan());
            k.setJumlahRp(hutang.getJumlahHutang());
            k.setKodeUser(sistem.getUser().getKodeUser());
            KeuanganDAO.insert(con, k);
            
            Keuangan h = new Keuangan();
            h.setNoKeuangan(noKeuangan);
            h.setTglKeuangan(tglSql.format(Function.getServerDate(con)));
            h.setTipeKeuangan("Hutang");
            h.setKategori(hutang.getKategori());
            h.setDeskripsi(hutang.getNoHutang()+" - "+hutang.getKeterangan());
            h.setJumlahRp(hutang.getJumlahHutang());
            h.setKodeUser(sistem.getUser().getKodeUser());
            KeuanganDAO.insert(con, h);
            
            if(status.equals("true"))
                con.commit();
            else 
                con.rollback();
            con.setAutoCommit(true);
            
            return status;
        }catch(Exception e){
            try{
                con.rollback();
            con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String newPembayaranHutang(Connection con, Pembayaran pembayaran){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            String noKeuangan = KeuanganDAO.getId(con);
            PembayaranDAO.insert(con, pembayaran);
            
            Hutang hutang = pembayaran.getHutang();
            hutang.setPembayaran(hutang.getPembayaran()+pembayaran.getJumlahPembayaran());
            hutang.setSisaHutang(hutang.getSisaHutang()-pembayaran.getJumlahPembayaran());
            if(hutang.getSisaHutang()==0)
                hutang.setStatus("close");
            HutangDAO.update(con, hutang);
            
            if(pembayaran.getHutang().getKategori().equals("Hutang Pembelian")){
                if(hutang.getKeterangan().startsWith("PO")){
                    PembelianHead pembelian = PembelianHeadDAO.get(con, hutang.getKeterangan());
                    pembelian.setPembayaran(pembelian.getPembayaran()+pembayaran.getJumlahPembayaran());
                    pembelian.setSisaPembayaran(pembelian.getSisaPembayaran()-pembayaran.getJumlahPembayaran());
                    PembelianHeadDAO.update(con, pembelian);
                }else if(hutang.getKeterangan().startsWith("PB")){
                    PembelianBarangHead pembelian = PembelianBarangHeadDAO.get(con, hutang.getKeterangan());
                    pembelian.setPembayaran(pembelian.getPembayaran()+pembayaran.getJumlahPembayaran());
                    pembelian.setSisaPembayaran(pembelian.getSisaPembayaran()-pembayaran.getJumlahPembayaran());
                    PembelianBarangHeadDAO.update(con, pembelian);
                }
            }
            
            Keuangan keuPembayaran = new Keuangan();
            keuPembayaran.setNoKeuangan(noKeuangan);
            keuPembayaran.setTglKeuangan(tglSql.format(Function.getServerDate(con)));
            keuPembayaran.setTipeKeuangan(pembayaran.getTipeKeuangan());
            keuPembayaran.setKategori(hutang.getKategori());
            keuPembayaran.setDeskripsi("Pembayaran - "+hutang.getKeterangan()+" ("+pembayaran.getNoPembayaran()+")");
            keuPembayaran.setJumlahRp(-pembayaran.getJumlahPembayaran());
            keuPembayaran.setKodeUser(sistem.getUser().getKodeUser());
            KeuanganDAO.insert(con, keuPembayaran);
            
            Keuangan pb = new Keuangan();
            pb.setNoKeuangan(noKeuangan);
            pb.setTglKeuangan(tglSql.format(Function.getServerDate(con)));
            pb.setTipeKeuangan("Hutang");
            pb.setKategori(hutang.getKategori());
            pb.setDeskripsi("Pembayaran - "+hutang.getKeterangan()+" ("+pembayaran.getNoPembayaran()+")");
            pb.setJumlahRp(-pembayaran.getJumlahPembayaran());
            pb.setKodeUser(sistem.getUser().getKodeUser());
            KeuanganDAO.insert(con, pb);
            
            if(status.equals("true"))
                con.commit();
            else 
                con.rollback();
            con.setAutoCommit(true);
            
            return status;
        }catch(Exception e){
            try{
                con.rollback();
            con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    } 
    public static String batalPembayaranHutang(Connection con, Pembayaran pembayaran){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            PembayaranDAO.update(con, pembayaran);
            
            Hutang hutang = pembayaran.getHutang();
            hutang.setPembayaran(hutang.getPembayaran()-pembayaran.getJumlahPembayaran());
            hutang.setSisaHutang(hutang.getSisaHutang()+pembayaran.getJumlahPembayaran());
            if(hutang.getSisaHutang()!=0)
                hutang.setStatus("open");
            HutangDAO.update(con, hutang);
            
            if(hutang.getKategori().equals("Hutang Pembelian")){
                if(hutang.getKeterangan().startsWith("PO")){
                    PembelianHead pembelian = PembelianHeadDAO.get(con, hutang.getKeterangan());
                    pembelian.setPembayaran(pembelian.getPembayaran()-pembayaran.getJumlahPembayaran());
                    pembelian.setSisaPembayaran(pembelian.getSisaPembayaran()+pembayaran.getJumlahPembayaran());
                    PembelianHeadDAO.update(con, pembelian);
                }else if(hutang.getKeterangan().startsWith("PB")){
                    PembelianBarangHead pembelian = PembelianBarangHeadDAO.get(con, hutang.getKeterangan());
                    pembelian.setPembayaran(pembelian.getPembayaran()-pembayaran.getJumlahPembayaran());
                    pembelian.setSisaPembayaran(pembelian.getSisaPembayaran()+pembayaran.getJumlahPembayaran());
                    PembelianBarangHeadDAO.update(con, pembelian);
                }
            }
            
            KeuanganDAO.delete(con, pembayaran.getTipeKeuangan(), hutang.getKategori(), 
                    "Pembayaran - "+hutang.getKeterangan()+" ("+pembayaran.getNoPembayaran()+")");
            
            KeuanganDAO.delete(con, "Hutang", hutang.getKategori(), 
                    "Pembayaran - "+hutang.getKeterangan()+" ("+pembayaran.getNoPembayaran()+")");
            
            
            if(status.equals("true"))
                con.commit();
            else 
                con.rollback();
            con.setAutoCommit(true);
            
            return status;
        }catch(Exception e){
            try{
                con.rollback();
            con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    } 
    public static String setJatuhTempoHutang(Connection con, Hutang hutang){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            HutangDAO.update(con, hutang);
            
            if(status.equals("true"))
                con.commit();
            else 
                con.rollback();
            con.setAutoCommit(true);
            
            return status;
        }catch(Exception e){
            try{
                con.rollback();
            con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    } 
    
    public static String newPiutang(Connection con, Piutang piutang){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            String noKeuangan = KeuanganDAO.getId(con);
            Keuangan keuangan = new Keuangan();
            keuangan.setNoKeuangan(noKeuangan);
            keuangan.setTglKeuangan(tglSql.format(Function.getServerDate(con)));
            keuangan.setTipeKeuangan(piutang.getTipeKeuangan());
            keuangan.setKategori(piutang.getKategori());
            keuangan.setDeskripsi(piutang.getNoPiutang()+" - "+piutang.getKeterangan());
            keuangan.setJumlahRp(piutang.getJumlahPiutang()*-1);
            keuangan.setKodeUser(sistem.getUser().getKodeUser());
            KeuanganDAO.insert(con, keuangan);
            
            Keuangan p = new Keuangan();
            p.setNoKeuangan(noKeuangan);
            p.setTglKeuangan(tglSql.format(Function.getServerDate(con)));
            p.setTipeKeuangan("Piutang");
            p.setKategori(piutang.getKategori());
            p.setDeskripsi(piutang.getNoPiutang()+" - "+piutang.getKeterangan());
            p.setJumlahRp(piutang.getJumlahPiutang());
            p.setKodeUser(sistem.getUser().getKodeUser());
            KeuanganDAO.insert(con, p);
            
            PiutangDAO.insert(con, piutang);
            
            if(status.equals("true"))
                con.commit();
            else 
                con.rollback();
            con.setAutoCommit(true);
            
            return status;
        }catch(Exception e){
            try{
                con.rollback();
            con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String newTerimaPembayaranPiutang(Connection con, TerimaPembayaran terimaPembayaran){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            String noKeuangan = KeuanganDAO.getId(con);
            TerimaPembayaranDAO.insert(con, terimaPembayaran);
            
            Piutang piutang = terimaPembayaran.getPiutang();
            piutang.setPembayaran(piutang.getPembayaran()+terimaPembayaran.getJumlahPembayaran());
            piutang.setSisaPiutang(piutang.getSisaPiutang()-terimaPembayaran.getJumlahPembayaran());
            if(piutang.getSisaPiutang()==0)
                piutang.setStatus("close");
            PiutangDAO.update(con, piutang);
            
            if(piutang.getKategori().equals("Piutang Penjualan")){
                String kodeCustomer = "";
                if(piutang.getKeterangan().startsWith("PJ")){
                    PenjualanHead penjualan = PenjualanHeadDAO.get(con, piutang.getKeterangan());
                    penjualan.setPembayaran(penjualan.getPembayaran()+terimaPembayaran.getJumlahPembayaran());
                    penjualan.setSisaPembayaran(penjualan.getSisaPembayaran()-terimaPembayaran.getJumlahPembayaran());
                    PenjualanHeadDAO.update(con, penjualan);
                    kodeCustomer = penjualan.getKodeCustomer();
                }else if(piutang.getKeterangan().startsWith("PE")){
                    PenjualanCoilHead penjualan = PenjualanCoilHeadDAO.get(con, piutang.getKeterangan());
                    penjualan.setPembayaran(penjualan.getPembayaran()+terimaPembayaran.getJumlahPembayaran());
                    penjualan.setSisaPembayaran(penjualan.getSisaPembayaran()-terimaPembayaran.getJumlahPembayaran());
                    PenjualanCoilHeadDAO.update(con, penjualan);
                    kodeCustomer = penjualan.getKodeCustomer();
                }
                Customer customer = CustomerDAO.get(con, kodeCustomer);
                customer.setHutang(customer.getHutang()-terimaPembayaran.getJumlahPembayaran());
                CustomerDAO.update(con, customer);
            }
            
            Keuangan keuTerimaPembayaran = new Keuangan();
            keuTerimaPembayaran.setNoKeuangan(noKeuangan);
            keuTerimaPembayaran.setTglKeuangan(tglSql.format(Function.getServerDate(con)));
            keuTerimaPembayaran.setTipeKeuangan(terimaPembayaran.getTipeKeuangan());
            keuTerimaPembayaran.setKategori(piutang.getKategori());
            keuTerimaPembayaran.setDeskripsi("Terima Pembayaran - "+piutang.getKeterangan()+" ("+terimaPembayaran.getNoTerimaPembayaran()+")");
            keuTerimaPembayaran.setJumlahRp(terimaPembayaran.getJumlahPembayaran());
            keuTerimaPembayaran.setKodeUser(sistem.getUser().getKodeUser());
            KeuanganDAO.insert(con, keuTerimaPembayaran);
            
            Keuangan keuPiutang = new Keuangan();
            keuPiutang.setNoKeuangan(noKeuangan);
            keuPiutang.setTglKeuangan(tglSql.format(Function.getServerDate(con)));
            keuPiutang.setTipeKeuangan("Piutang");
            keuPiutang.setKategori(piutang.getKategori());
            keuPiutang.setDeskripsi("Terima Pembayaran - "+piutang.getKeterangan()+" ("+terimaPembayaran.getNoTerimaPembayaran()+")");
            keuPiutang.setJumlahRp(-terimaPembayaran.getJumlahPembayaran());
            keuPiutang.setKodeUser(sistem.getUser().getKodeUser());
            KeuanganDAO.insert(con, keuPiutang);
            
            if(status.equals("true"))
                con.commit();
            else 
                con.rollback();
            con.setAutoCommit(true);
            
            return status;
        }catch(Exception e){
            try{
                con.rollback();
            con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }  
    public static String batalTerimaPembayaranPiutang(Connection con, TerimaPembayaran terimaPembayaran){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            TerimaPembayaranDAO.update(con, terimaPembayaran);
            
            Piutang piutang = terimaPembayaran.getPiutang();
            piutang.setPembayaran(piutang.getPembayaran()-terimaPembayaran.getJumlahPembayaran());
            piutang.setSisaPiutang(piutang.getSisaPiutang()+terimaPembayaran.getJumlahPembayaran());
            if(piutang.getSisaPiutang()!=0)
                piutang.setStatus("open");
            PiutangDAO.update(con, piutang);
            
            if(piutang.getKategori().equals("Piutang Penjualan")){
                String kodeCustomer = "";
                if(piutang.getKeterangan().startsWith("PJ")){
                    PenjualanHead penjualan = PenjualanHeadDAO.get(con, piutang.getKeterangan());
                    penjualan.setPembayaran(penjualan.getPembayaran()-terimaPembayaran.getJumlahPembayaran());
                    penjualan.setSisaPembayaran(penjualan.getSisaPembayaran()+terimaPembayaran.getJumlahPembayaran());
                    PenjualanHeadDAO.update(con, penjualan);
                    kodeCustomer = penjualan.getKodeCustomer();
                }else if(piutang.getKeterangan().startsWith("PE")){
                    PenjualanCoilHead penjualan = PenjualanCoilHeadDAO.get(con, piutang.getKeterangan());
                    penjualan.setPembayaran(penjualan.getPembayaran()-terimaPembayaran.getJumlahPembayaran());
                    penjualan.setSisaPembayaran(penjualan.getSisaPembayaran()+terimaPembayaran.getJumlahPembayaran());
                    PenjualanCoilHeadDAO.update(con, penjualan);
                    kodeCustomer = penjualan.getKodeCustomer();
                }
                
                Customer customer = CustomerDAO.get(con, kodeCustomer);
                customer.setHutang(customer.getHutang()+terimaPembayaran.getJumlahPembayaran());
                CustomerDAO.update(con, customer);
            }
            KeuanganDAO.delete(con, terimaPembayaran.getTipeKeuangan(), piutang.getKategori(), 
                    "Terima Pembayaran - "+piutang.getKeterangan()+" ("+terimaPembayaran.getNoTerimaPembayaran()+")");
            
            KeuanganDAO.delete(con, "Piutang", piutang.getKategori(), 
                    "Terima Pembayaran - "+piutang.getKeterangan()+" ("+terimaPembayaran.getNoTerimaPembayaran()+")");
            
            if(status.equals("true"))
                con.commit();
            else 
                con.rollback();
            con.setAutoCommit(true);
            
            return status;
        }catch(Exception e){
            try{
                con.rollback();
            con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String setJatuhTempoPiutang(Connection con, Piutang piutang){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            PiutangDAO.update(con, piutang);
            
            if(status.equals("true"))
                con.commit();
            else 
                con.rollback();
            con.setAutoCommit(true);
            
            return status;
        }catch(Exception e){
            try{
                con.rollback();
            con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    } 
    
    public static String newModal(Connection con, Keuangan k){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            String noKeuangan = KeuanganDAO.getId(con);
            k.setNoKeuangan(noKeuangan);
            KeuanganDAO.insert(con, k);
            
            Keuangan modal = new Keuangan();
            modal.setNoKeuangan(noKeuangan);
            modal.setTglKeuangan(tglSql.format(Function.getServerDate(con)));
            modal.setTipeKeuangan("Modal");
            modal.setKategori(k.getKategori());
            modal.setDeskripsi(k.getDeskripsi());
            modal.setJumlahRp(k.getJumlahRp());
            modal.setKodeUser(sistem.getUser().getKodeUser());
            KeuanganDAO.insert(con, modal);
            
            if(status.equals("true"))
                con.commit();
            else 
                con.rollback();
            con.setAutoCommit(true);
            
            return status;
        }catch(Exception e){
            try{
                con.rollback();
            con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String pembelianAsetTetap(Connection con, AsetTetap aset,String tipeKeuangan){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            String noKeuangan = KeuanganDAO.getId(con);
            AsetTetapDAO.insert(con, aset);
            
            Keuangan k = new Keuangan();
            k.setNoKeuangan(noKeuangan);
            k.setTglKeuangan(tglSql.format(Function.getServerDate(con)));
            k.setTipeKeuangan(tipeKeuangan);
            k.setKategori("Pembelian Aset Tetap");
            k.setDeskripsi(aset.getNoAset());
            k.setJumlahRp(-aset.getNilaiAwal());
            k.setKodeUser(sistem.getUser().getKodeUser());
            KeuanganDAO.insert(con, k);
            
            Keuangan keuangan = new Keuangan();
            keuangan.setNoKeuangan(noKeuangan);
            keuangan.setTglKeuangan(tglSql.format(Function.getServerDate(con)));
            keuangan.setTipeKeuangan("Aset Tetap");
            keuangan.setKategori(aset.getKategori());
            keuangan.setDeskripsi("Pembelian Aset Tetap - "+aset.getNoAset());
            keuangan.setJumlahRp(aset.getNilaiAwal());
            keuangan.setKodeUser(sistem.getUser().getKodeUser());
            KeuanganDAO.insert(con, keuangan);
            
            if(status.equals("true"))
                con.commit();
            else 
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String penjualanAsetTetap(Connection con, AsetTetap aset,String tipeKeuangan){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            String noKeuangan = KeuanganDAO.getId(con);
            AsetTetapDAO.update(con, aset);

            Keuangan k = new Keuangan();
            k.setNoKeuangan(noKeuangan);
            k.setTglKeuangan(tglSql.format(Function.getServerDate(con)));
            k.setTipeKeuangan(tipeKeuangan);
            k.setKategori("Penjualan Aset Tetap");
            k.setDeskripsi(aset.getNoAset());
            k.setJumlahRp(aset.getHargaJual());
            k.setKodeUser(sistem.getUser().getKodeUser());
            KeuanganDAO.insert(con, k);

            Keuangan asetTetap = new Keuangan();
            asetTetap.setNoKeuangan(noKeuangan);
            asetTetap.setTglKeuangan(tglSql.format(Function.getServerDate(con)));
            asetTetap.setTipeKeuangan("Aset Tetap");
            asetTetap.setKategori(aset.getKategori());
            asetTetap.setDeskripsi("Penjualan Aset Tetap - "+aset.getNoAset());
            asetTetap.setJumlahRp(-aset.getNilaiAkhir());
            asetTetap.setKodeUser(sistem.getUser().getKodeUser());
            KeuanganDAO.insert(con, asetTetap);

            if(aset.getHargaJual() > aset.getNilaiAkhir()){
                Keuangan pendapatan = new Keuangan();
                pendapatan.setNoKeuangan(noKeuangan);
                pendapatan.setTglKeuangan(tglSql.format(Function.getServerDate(con)));
                pendapatan.setTipeKeuangan("Pendapatan");
                pendapatan.setKategori("Pendapatan Penjualan Aset Tetap");
                pendapatan.setDeskripsi("Penjualan Aset Tetap - "+aset.getNoAset());
                pendapatan.setJumlahRp(aset.getHargaJual()-aset.getNilaiAkhir());
                pendapatan.setKodeUser(sistem.getUser().getKodeUser());
                KeuanganDAO.insert(con, pendapatan);
            }else if(aset.getHargaJual() < aset.getNilaiAkhir()){
                Keuangan beban = new Keuangan();
                beban.setNoKeuangan(noKeuangan);
                beban.setTglKeuangan(tglSql.format(Function.getServerDate(con)));
                beban.setTipeKeuangan("Beban");
                beban.setKategori("Beban Penjualan Aset Tetap");
                beban.setDeskripsi("Penjualan Aset Tetap - "+aset.getNoAset());
                beban.setJumlahRp(aset.getNilaiAkhir()-aset.getHargaJual());
                beban.setKodeUser(sistem.getUser().getKodeUser());
                KeuanganDAO.insert(con, beban);
            }
            
            if(status.equals("true"))
                con.commit();
            else 
                con.rollback();
            con.setAutoCommit(true);
            
            return status;
        }catch(Exception e){
            try{
                con.rollback();
            con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String savePenyesuaianStokBahan(Connection con ,PenyesuaianStokBahan p){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            LogBahan logBahan = LogBahanDAO.getLastByBahanAndGudang(con, p.getKodeBahan(), p.getKodeGudang());
            logBahan.setStokAkhir(Math.round(logBahan.getStokAkhir()*100)/100);
            if(logBahan.getStokAkhir()+p.getQty()<0){
                status = "Stok bahan "+p.getKodeBahan()+" tidak mencukupi";
            }else{
                double qty = p.getQty();
                double nilai = 0;
                if(logBahan.getStokAkhir()!=0)
                    nilai = logBahan.getNilaiAkhir()/logBahan.getStokAkhir()*p.getQty();
                
                if(logBahan.getStokAkhir()+p.getQty()<=0){
                    Bahan bahan = BahanDAO.get(con, logBahan.getKodeBahan());
                    bahan.setStatus("false");
                    BahanDAO.update(con, bahan);
                }
                
                if(qty<0)
                    insertStokAndLogBahan(con, p.getKodeBahan(), p.getKodeGudang(),
                            "Penyesuaian Stok Bahan", p.getNoPenyesuaian(), 0, 0, qty*-1, nilai*-1);
                else
                    insertStokAndLogBahan(con, p.getKodeBahan(), p.getKodeGudang(),
                            "Penyesuaian Stok Bahan", p.getNoPenyesuaian(), qty, nilai, 0, 0);

                String noKeuangan = KeuanganDAO.getId(con);
                Keuangan k = new Keuangan();
                k.setNoKeuangan(noKeuangan);
                k.setTglKeuangan(tglSql.format(Function.getServerDate(con)));
                k.setTipeKeuangan("Stok Bahan");
                k.setKategori(p.getKodeGudang());
                k.setDeskripsi("Penyesuaian Stok Bahan - "+p.getNoPenyesuaian());
                k.setJumlahRp(nilai);
                k.setKodeUser(sistem.getUser().getKodeUser());
                KeuanganDAO.insert(con, k);
                
                Keuangan k2 = new Keuangan();
                k2.setNoKeuangan(noKeuangan);
                k2.setTglKeuangan(tglSql.format(Function.getServerDate(con)));
                k2.setTipeKeuangan("Pendapatan/Beban");
                k2.setKategori("Penyesuaian Stok Bahan");
                k2.setDeskripsi("Penyesuaian Stok Bahan - "+p.getNoPenyesuaian());
                k2.setJumlahRp(nilai);
                k2.setKodeUser(sistem.getUser().getKodeUser());
                KeuanganDAO.insert(con, k2);
                
                p.setNilai(nilai);
                PenyesuaianStokBahanDAO.insert(con, p);
            }
            
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
    public static String savePenyesuaianStokBarang(Connection con ,PenyesuaianStokBarang p){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            LogBarang logBarang = LogBarangDAO.getLastByBarangAndGudang(con, p.getKodeBarang(), p.getKodeGudang());
            logBarang.setStokAkhir(Math.round(logBarang.getStokAkhir()*100)/100);
            if(logBarang.getStokAkhir()+p.getQty()<0){
                status = "Stok barang "+p.getKodeBarang()+" tidak mencukupi";
            }else{
                double qty = p.getQty();
                double nilai = 0;
                if(logBarang.getStokAkhir()!=0)
                    nilai = logBarang.getNilaiAkhir()/logBarang.getStokAkhir()*p.getQty();
                
                if(qty<0)
                    insertStokAndLogBarang(con, p.getKodeBarang(), p.getKodeGudang(),
                            "Penyesuaian Stok Barang", p.getNoPenyesuaian(), 0, 0, qty*-1, nilai*-1);
                else
                    insertStokAndLogBarang(con, p.getKodeBarang(), p.getKodeGudang(), 
                            "Penyesuaian Stok Barang", p.getNoPenyesuaian(), qty, nilai, 0, 0);

                String noKeuangan = KeuanganDAO.getId(con);
                Keuangan k = new Keuangan();
                k.setNoKeuangan(noKeuangan);
                k.setTglKeuangan(tglSql.format(Function.getServerDate(con)));
                k.setTipeKeuangan("Stok Barang");
                k.setKategori(p.getKodeGudang());
                k.setDeskripsi("Penyesuaian Stok Barang - "+p.getNoPenyesuaian());
                k.setJumlahRp(nilai);
                k.setKodeUser(sistem.getUser().getKodeUser());
                KeuanganDAO.insert(con, k);
                
                Keuangan k2 = new Keuangan();
                k2.setNoKeuangan(noKeuangan);
                k2.setTglKeuangan(tglSql.format(Function.getServerDate(con)));
                k2.setTipeKeuangan("Pendapatan/Beban");
                k2.setKategori("Penyesuaian Stok Barang");
                k2.setDeskripsi("Penyesuaian Stok Barang - "+p.getNoPenyesuaian());
                k2.setJumlahRp(nilai);
                k2.setKodeUser(sistem.getUser().getKodeUser());
                KeuanganDAO.insert(con, k2);
                
                p.setNilai(nilai);
                PenyesuaianStokBarangDAO.insert(con, p);
            }
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static void resetLogBarang(Connection con, String tglPenjualan, String kodeBarang, String kodeGudang)throws Exception{
        List<LogBarang> listBarang = LogBarangDAO.getAllByTanggalAndBarangAndGudang(
                con, tglBarang.format(tglSql.parse(tglPenjualan)), tglBarang.format(Function.getServerDate(con)), kodeBarang, kodeGudang);
        LogBarang logBarang = LogBarangDAO.getLastBeforeDateAndBarangAndGudang(
                con, tglPenjualan, kodeBarang, kodeGudang);
        listBarang.sort(Comparator.comparing(LogBarang::getTanggal));
        double stok = logBarang.getStokAkhir();
        double nilai = logBarang.getNilaiAkhir();
        for(LogBarang log : listBarang){
            log.setStokAwal(stok);
            log.setNilaiAwal(nilai);
            
            stok = stok + log.getStokMasuk() - log.getStokKeluar();
            nilai = nilai + log.getNilaiMasuk() - log.getNilaiKeluar();

            log.setStokAkhir(stok);
            log.setNilaiAkhir(nilai);
            
            LogBarangDAO.update(con, log);
        }
    }
    public static void resetStokBarang(Connection con, String kodeBarang, String kodeGudang, String tanggal)throws Exception{
        List<StokBarang> listStokBarang = StokBarangDAO.getAllByTanggalAndBarangAndGudang(
                con, tanggal, tglBarang.format(Function.getServerDate(con)), 
                kodeBarang, kodeGudang);
        listStokBarang.sort(Comparator.comparing(StokBarang::getTanggal));
        double stok = listStokBarang.get(0).getStokAwal();
        for(StokBarang s : listStokBarang){
            s.setStokAwal(stok);
            stok = stok + s.getStokMasuk() - s.getStokKeluar();
            s.setStokAkhir(stok);
            StokBarangDAO.update(con, s);
        }
    }
    
    public static void resetLogBahan(Connection con, String tglPenjualan, String kodeBahan, String kodeGudang, Date date)throws Exception{
        List<LogBahan> listBarang = LogBahanDAO.getAllByTanggalAndBahanAndGudang(
                con, tglBarang.format(tglSql.parse(tglPenjualan)), tglBarang.format(date), kodeBahan, kodeGudang);
        LogBahan logBahan = LogBahanDAO.getLastBeforeDateAndBahanAndGudang(
                con, tglBarang.format(tglSql.parse(tglPenjualan)), kodeBahan, kodeGudang);
        listBarang.sort(Comparator.comparing(LogBahan::getTanggal));
        double stok = 0;
        double nilai = 0;
        if(logBahan!=null){
            stok = logBahan.getStokAkhir();
            nilai = logBahan.getNilaiAkhir();
        }
        for(LogBahan log : listBarang){
            log.setStokAwal(stok);
            log.setNilaiAwal(nilai);
            
            stok = stok + log.getStokMasuk() - log.getStokKeluar();
            nilai = nilai + log.getNilaiMasuk() - log.getNilaiKeluar();

            log.setStokAkhir(stok);
            log.setNilaiAkhir(nilai);
            
            LogBahanDAO.update(con, log);
        }
    }
    public static void resetStokBahan(Connection con, String kodeBahan, String kodeGudang, String tanggal, Date date)throws Exception{
        List<StokBahan> listStokBahan = StokBahanDAO.getAllByTanggalAndBahanAndGudang(con, tanggal, tglBarang.format(date), kodeBahan, kodeGudang);
        listStokBahan.sort(Comparator.comparing(StokBahan::getTanggal));
        double stok = listStokBahan.get(0).getStokAwal();
        for(StokBahan s : listStokBahan){
            s.setStokAwal(stok);
            stok = stok + s.getStokMasuk() - s.getStokKeluar();
            s.setStokAkhir(stok);
            StokBahanDAO.update(con, s);
        }
    }
    
    
}
