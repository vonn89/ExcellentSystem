/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.Service;

import com.excellentsystem.sentralindahperdana.DAO.AsetTetapDAO;
import com.excellentsystem.sentralindahperdana.DAO.BarangDAO;
import com.excellentsystem.sentralindahperdana.DAO.BebanPenjualanDAO;
import com.excellentsystem.sentralindahperdana.DAO.CustomerDAO;
import com.excellentsystem.sentralindahperdana.DAO.HutangDAO;
import com.excellentsystem.sentralindahperdana.DAO.KategoriBarangDAO;
import com.excellentsystem.sentralindahperdana.DAO.KategoriHutangDAO;
import com.excellentsystem.sentralindahperdana.DAO.KategoriKeuanganDAO;
import com.excellentsystem.sentralindahperdana.DAO.KategoriPekerjaanDAO;
import com.excellentsystem.sentralindahperdana.DAO.KategoriPiutangDAO;
import com.excellentsystem.sentralindahperdana.DAO.KategoriTransaksiDAO;
import com.excellentsystem.sentralindahperdana.DAO.KeuanganDAO;
import com.excellentsystem.sentralindahperdana.DAO.LogBarangDAO;
import com.excellentsystem.sentralindahperdana.DAO.LogUserDAO;
import com.excellentsystem.sentralindahperdana.DAO.OtoritasDAO;
import com.excellentsystem.sentralindahperdana.DAO.OtoritasKeuanganDAO;
import com.excellentsystem.sentralindahperdana.DAO.PegawaiDAO;
import com.excellentsystem.sentralindahperdana.DAO.PekerjaanDAO;
import com.excellentsystem.sentralindahperdana.DAO.PembayaranHutangDAO;
import com.excellentsystem.sentralindahperdana.DAO.PembayaranPiutangDAO;
import com.excellentsystem.sentralindahperdana.DAO.PembelianDetailDAO;
import com.excellentsystem.sentralindahperdana.DAO.PembelianHeadDAO;
import com.excellentsystem.sentralindahperdana.DAO.PengirimanDetailDAO;
import com.excellentsystem.sentralindahperdana.DAO.PengirimanHeadDAO;
import com.excellentsystem.sentralindahperdana.DAO.PenjualanDetailDAO;
import com.excellentsystem.sentralindahperdana.DAO.PenjualanHeadDAO;
import com.excellentsystem.sentralindahperdana.DAO.PiutangDAO;
import com.excellentsystem.sentralindahperdana.DAO.RencanaAnggaranBebanMaterialDAO;
import com.excellentsystem.sentralindahperdana.DAO.RencanaAnggaranBebanPenjualanDAO;
import com.excellentsystem.sentralindahperdana.DAO.ReturDetailDAO;
import com.excellentsystem.sentralindahperdana.DAO.ReturHeadDAO;
import com.excellentsystem.sentralindahperdana.DAO.ReturPembelianDetailDAO;
import com.excellentsystem.sentralindahperdana.DAO.ReturPembelianHeadDAO;
import com.excellentsystem.sentralindahperdana.DAO.SatuanDAO;
import com.excellentsystem.sentralindahperdana.DAO.SistemDAO;
import com.excellentsystem.sentralindahperdana.DAO.StokBarangDAO;
import com.excellentsystem.sentralindahperdana.DAO.SupplierDAO;
import com.excellentsystem.sentralindahperdana.DAO.UserDAO;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import static com.excellentsystem.sentralindahperdana.Main.tglBarang;
import static com.excellentsystem.sentralindahperdana.Main.tglSql;
import com.excellentsystem.sentralindahperdana.Model.AsetTetap;
import com.excellentsystem.sentralindahperdana.Model.Barang;
import com.excellentsystem.sentralindahperdana.Model.BebanPenjualan;
import com.excellentsystem.sentralindahperdana.Model.Customer;
import com.excellentsystem.sentralindahperdana.Model.Hutang;
import com.excellentsystem.sentralindahperdana.Model.KategoriBarang;
import com.excellentsystem.sentralindahperdana.Model.KategoriHutang;
import com.excellentsystem.sentralindahperdana.Model.KategoriKeuangan;
import com.excellentsystem.sentralindahperdana.Model.KategoriPekerjaan;
import com.excellentsystem.sentralindahperdana.Model.KategoriPiutang;
import com.excellentsystem.sentralindahperdana.Model.KategoriTransaksi;
import com.excellentsystem.sentralindahperdana.Model.Keuangan;
import com.excellentsystem.sentralindahperdana.Model.LogBarang;
import com.excellentsystem.sentralindahperdana.Model.LogUser;
import com.excellentsystem.sentralindahperdana.Model.Otoritas;
import com.excellentsystem.sentralindahperdana.Model.OtoritasKeuangan;
import com.excellentsystem.sentralindahperdana.Model.Pegawai;
import com.excellentsystem.sentralindahperdana.Model.Pekerjaan;
import com.excellentsystem.sentralindahperdana.Model.PembayaranHutang;
import com.excellentsystem.sentralindahperdana.Model.PembayaranPiutang;
import com.excellentsystem.sentralindahperdana.Model.PembelianDetail;
import com.excellentsystem.sentralindahperdana.Model.PembelianHead;
import com.excellentsystem.sentralindahperdana.Model.PengirimanDetail;
import com.excellentsystem.sentralindahperdana.Model.PengirimanHead;
import com.excellentsystem.sentralindahperdana.Model.PenjualanDetail;
import com.excellentsystem.sentralindahperdana.Model.PenjualanHead;
import com.excellentsystem.sentralindahperdana.Model.Piutang;
import com.excellentsystem.sentralindahperdana.Model.RencanaAnggaranBebanMaterial;
import com.excellentsystem.sentralindahperdana.Model.RencanaAnggaranBebanPenjualan;
import com.excellentsystem.sentralindahperdana.Model.ReturDetail;
import com.excellentsystem.sentralindahperdana.Model.ReturHead;
import com.excellentsystem.sentralindahperdana.Model.ReturPembelianDetail;
import com.excellentsystem.sentralindahperdana.Model.ReturPembelianHead;
import com.excellentsystem.sentralindahperdana.Model.Satuan;
import com.excellentsystem.sentralindahperdana.Model.Sistem;
import com.excellentsystem.sentralindahperdana.Model.StokBarang;
import com.excellentsystem.sentralindahperdana.Model.Supplier;
import com.excellentsystem.sentralindahperdana.Model.User;
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
 * @author yunaz
 */
public class Service {
    
    public static void setPenyusutanAset(Connection con)throws Exception{
        LocalDate now = LocalDate.now();
        List<Keuangan> allAset = KeuanganDAO.getAllByTipeKeuanganAndTanggal(con, "Aset Tetap", "", now.toString());
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
                            boolean status = true;
                            for(Keuangan k : allAset){
                                if(k.getKeterangan().equals("Penyusutan Aset Tetap Ke-"+i+" ("+aset.getNoAset()+")"))
                                    status = false;
                            }
                            if(status){
                                Keuangan k = new Keuangan();
                                k.setNoKeuangan(KeuanganDAO.getId(con));
                                k.setTglKeuangan(tglSusut.toString()+" 00:00:00");
                                k.setTipeKeuangan("Aset Tetap");
                                k.setKategori(aset.getKategori());
                                k.setKeterangan("Penyusutan Aset Tetap Ke-"+i+" ("+aset.getNoAset()+")");
                                k.setJumlahRp(-penyusutanPerbulan);
                                k.setKodeUser("System");
                                KeuanganDAO.insert(con, k);

                                Keuangan k2 = new Keuangan();
                                k2.setNoKeuangan(k.getNoKeuangan());
                                k2.setTglKeuangan(tglSusut.toString()+" 00:00:00");
                                k2.setTipeKeuangan("Untung/Rugi");
                                k2.setKategori("Beban Penyusutan Aset Tetap");
                                k2.setKeterangan("Penyusutan Aset Tetap Ke-"+i+" ("+aset.getNoAset()+")");
                                k2.setJumlahRp(-penyusutanPerbulan);
                                k2.setKodeUser("System");
                                KeuanganDAO.insert(con, k2);
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
    }
    public static String savePengaturanUmum(Connection con, Sistem s)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            SistemDAO.update(con, s);
            
            LogUser l = new LogUser();
            l.setTanggal(tglSql.format(new Date()));
            l.setKodeUser(sistem.getUser().getUsername());
            l.setKategori("Save Pengaturan Umum");
            l.setKeterangan(s.toString());
            LogUserDAO.insert(con, l);
            
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
    
    public static String saveNewUser(Connection con, User u)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            UserDAO.insert(con, u);
            for(Otoritas o : u.getOtoritas()){
                OtoritasDAO.insert(con, o);
            }
            for(OtoritasKeuangan o : u.getOtoritasKeuangan()){
                OtoritasKeuanganDAO.insert(con, o);
            }
            LogUser l = new LogUser();
            l.setTanggal(tglSql.format(new Date()));
            l.setKodeUser(sistem.getUser().getUsername());
            l.setKategori("Save New User");
            l.setKeterangan(u.toString());
            LogUserDAO.insert(con, l);
            
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
    public static String saveUpdateUser(Connection con, User u)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            UserDAO.update(con, u);
            OtoritasDAO.deleteAllByKodeUser(con, u.getUsername());
            for(Otoritas o : u.getOtoritas()){
                OtoritasDAO.insert(con, o);
            }
            OtoritasKeuanganDAO.deleteByKodeUser(con, u.getUsername());
            for(OtoritasKeuangan o : u.getOtoritasKeuangan()){
                OtoritasKeuanganDAO.insert(con, o);
            }
            LogUser l = new LogUser();
            l.setTanggal(tglSql.format(new Date()));
            l.setKodeUser(sistem.getUser().getUsername());
            l.setKategori("Save Update User");
            l.setKeterangan(u.toString());
            LogUserDAO.insert(con, l);

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
    public static String saveUpdatePassword(Connection con, User u)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            UserDAO.update(con, u);
            LogUser l = new LogUser();
            l.setTanggal(tglSql.format(new Date()));
            l.setKodeUser(sistem.getUser().getUsername());
            l.setKategori("Save Ubah Password");
            l.setKeterangan(u.toString());
            LogUserDAO.insert(con, l);

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
    public static String deleteUser(Connection con, User u)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            UserDAO.delete(con, u);
            OtoritasDAO.deleteAllByKodeUser(con, u.getUsername());
            OtoritasKeuanganDAO.deleteByKodeUser(con, u.getUsername());
            LogUser l = new LogUser();
            l.setTanggal(tglSql.format(new Date()));
            l.setKodeUser(sistem.getUser().getUsername());
            l.setKategori("Delete User");
            l.setKeterangan(u.toString());
            LogUserDAO.insert(con, l);

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
    
    public static String saveNewCustomer(Connection con, Customer c)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            c.setKodeCustomer(CustomerDAO.getId(con));
            CustomerDAO.insert(con, c);
            
            LogUser l = new LogUser();
            l.setTanggal(tglSql.format(new Date()));
            l.setKodeUser(sistem.getUser().getUsername());
            l.setKategori("Save New Customer");
            l.setKeterangan(c.toString());
            LogUserDAO.insert(con, l);

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
    public static String saveUpdateCustomer(Connection con, Customer c)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            CustomerDAO.update(con, c);
            
            LogUser l = new LogUser();
            l.setTanggal(tglSql.format(new Date()));
            l.setKodeUser(sistem.getUser().getUsername());
            l.setKategori("Save Update Customer");
            l.setKeterangan(c.toString());
            LogUserDAO.insert(con, l);

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
    public static String deleteCustomer(Connection con, Customer c)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            if(c.getDeposit()!=0||c.getHutang()!=0){
                status = "Tidak dapat dihapus, karena customer masih memiliki deposit/hutang";
            }else{
                c.setStatus("false");
                CustomerDAO.update(con, c);
                
                LogUser l = new LogUser();
                l.setTanggal(tglSql.format(new Date()));
                l.setKodeUser(sistem.getUser().getUsername());
                l.setKategori("Delete Customer");
                l.setKeterangan(c.toString());
                LogUserDAO.insert(con, l);
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
    
    public static String saveNewPegawai(Connection con, Pegawai p)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            p.setKodePegawai(PegawaiDAO.getId(con));
            PegawaiDAO.insert(con, p);
            
            LogUser l = new LogUser();
            l.setTanggal(tglSql.format(new Date()));
            l.setKodeUser(sistem.getUser().getUsername());
            l.setKategori("Save New Pegawai");
            l.setKeterangan(p.toString());
            LogUserDAO.insert(con, l);

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
    public static String saveUpdatePegawai(Connection con, Pegawai p)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            PegawaiDAO.update(con, p);
            
            LogUser l = new LogUser();
            l.setTanggal(tglSql.format(new Date()));
            l.setKodeUser(sistem.getUser().getUsername());
            l.setKategori("Save Update Pegawai");
            l.setKeterangan(p.toString());
            LogUserDAO.insert(con, l);

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
    public static String deletePegawai(Connection con, Pegawai p)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            p.setStatus("false");
            PegawaiDAO.update(con, p);

            LogUser l = new LogUser();
            l.setTanggal(tglSql.format(new Date()));
            l.setKodeUser(sistem.getUser().getUsername());
            l.setKategori("Delete Pegawai");
            l.setKeterangan(p.toString());
            LogUserDAO.insert(con, l);
            
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
    
    public static String saveNewSupplier(Connection con, Supplier s)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            s.setKodeSupplier(SupplierDAO.getId(con));
            SupplierDAO.insert(con, s);
            
            LogUser l = new LogUser();
            l.setTanggal(tglSql.format(new Date()));
            l.setKodeUser(sistem.getUser().getUsername());
            l.setKategori("Save New Supplier");
            l.setKeterangan(s.toString());
            LogUserDAO.insert(con, l);

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
    public static String saveUpdateSupplier(Connection con, Supplier s)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            SupplierDAO.update(con, s);
            LogUser l = new LogUser();
            l.setTanggal(tglSql.format(new Date()));
            l.setKodeUser(sistem.getUser().getUsername());
            l.setKategori("Save Update Supplier");
            l.setKeterangan(s.toString());
            LogUserDAO.insert(con, l);

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
    public static String deleteSupplier(Connection con, Supplier s)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            if(s.getDeposit()!=0||s.getHutang()!=0)
                status = "Tidak dapat dihapus, karena supplier masih ada deposit/hutang";
            else{
                s.setStatus("false");
                SupplierDAO.update(con, s);
                LogUser l = new LogUser();
                l.setTanggal(tglSql.format(new Date()));
                l.setKodeUser(sistem.getUser().getUsername());
                l.setKategori("Delete Supplier");
                l.setKeterangan(s.toString());
                LogUserDAO.insert(con, l);
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
    
    public static String saveNewKategoriKeuangan(Connection con, KategoriKeuangan t)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            KategoriKeuanganDAO.insert(con, t);
            for(User u : UserDAO.getAllByStatus(con, "true")){
                OtoritasKeuangan o = new OtoritasKeuangan();
                o.setKodeUser(u.getUsername());
                o.setKodeKeuangan(t.getKodeKeuangan());
                o.setStatus(false);
                OtoritasKeuanganDAO.insert(con, o);
            }
            LogUser l = new LogUser();
            l.setTanggal(tglSql.format(new Date()));
            l.setKodeUser(sistem.getUser().getUsername());
            l.setKategori("Save New Tipe Keuangan");
            l.setKeterangan(t.toString());
            LogUserDAO.insert(con, l);

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
            
            double saldo = KeuanganDAO.getSaldoAkhir(con, tglBarang.format(new Date()), t.getKodeKeuangan());
            if(saldo!=0)
                status = "Tidak dapat dihapus,karena saldo "+t.getKodeKeuangan()+" masih ada";
            else{
                KategoriKeuanganDAO.delete(con, t);
                OtoritasKeuanganDAO.deleteByKodeKeuangan(con, t.getKodeKeuangan());
                LogUser l = new LogUser();
                l.setTanggal(tglSql.format(new Date()));
                l.setKodeUser(sistem.getUser().getUsername());
                l.setKategori("Delete Tipe Keuangan");
                l.setKeterangan(t.toString());
                LogUserDAO.insert(con, l);
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
    
    public static String saveNewKategoriTransaksi(Connection con, KategoriTransaksi k)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            KategoriTransaksiDAO.insert(con, k);
            LogUser l = new LogUser();
            l.setTanggal(tglSql.format(new Date()));
            l.setKodeUser(sistem.getUser().getUsername());
            l.setKategori("Save New Kategori Transaksi");
            l.setKeterangan(k.toString());
            LogUserDAO.insert(con, l);

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

            KategoriTransaksiDAO.delete(con, k);
            LogUser l = new LogUser();
            l.setTanggal(tglSql.format(new Date()));
            l.setKodeUser(sistem.getUser().getUsername());
            l.setKategori("Delete Kategori Transaksi");
            l.setKeterangan(k.toString());
            LogUserDAO.insert(con, l);

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
    
    public static String saveNewKategoriHutang(Connection con, KategoriHutang k)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            KategoriHutangDAO.insert(con, k);
            LogUser l = new LogUser();
            l.setTanggal(tglSql.format(new Date()));
            l.setKodeUser(sistem.getUser().getUsername());
            l.setKategori("Save New Kategori Hutang");
            l.setKeterangan(k.toString());
            LogUserDAO.insert(con, l);

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
            LogUser l = new LogUser();
            l.setTanggal(tglSql.format(new Date()));
            l.setKodeUser(sistem.getUser().getUsername());
            l.setKategori("Delete Kategori Hutang");
            l.setKeterangan(k.toString());
            LogUserDAO.insert(con, l);

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
    
    public static String saveNewKategoriPiutang(Connection con, KategoriPiutang k)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            KategoriPiutangDAO.insert(con, k);
            LogUser l = new LogUser();
            l.setTanggal(tglSql.format(new Date()));
            l.setKodeUser(sistem.getUser().getUsername());
            l.setKategori("Save New Kategori Piutang");
            l.setKeterangan(k.toString());
            LogUserDAO.insert(con, l);

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
            LogUser l = new LogUser();
            l.setTanggal(tglSql.format(new Date()));
            l.setKodeUser(sistem.getUser().getUsername());
            l.setKategori("Delete Kategori Piutang");
            l.setKeterangan(k.toString());
            LogUserDAO.insert(con, l);

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
    
    public static String saveNewKategoriBarang(Connection con, KategoriBarang k)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            KategoriBarangDAO.insert(con, k);
            LogUser l = new LogUser();
            l.setTanggal(tglSql.format(new Date()));
            l.setKodeUser(sistem.getUser().getUsername());
            l.setKategori("Save New Kategori Barang");
            l.setKeterangan(k.toString());
            LogUserDAO.insert(con, l);

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
    public static String deleteKategoriBarang(Connection con, KategoriBarang k)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            List<Barang> listBarang = BarangDAO.getAllByStatus(con,"true");
            for(Barang b : listBarang){
                if(b.getKategoriBarang().equals(k.getKodeKategori()))
                    status = "Tidak dapat hapus, karena kategori barang masih digunakan";
            }
            if(status.equals("true")){
                KategoriBarangDAO.delete(con, k);
                LogUser l = new LogUser();
                l.setTanggal(tglSql.format(new Date()));
                l.setKodeUser(sistem.getUser().getUsername());
                l.setKategori("Delete Kategori Barang");
                l.setKeterangan(k.toString());
                LogUserDAO.insert(con, l);
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
    
    public static String saveNewKategoriPekerjaan(Connection con, KategoriPekerjaan k)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            KategoriPekerjaanDAO.insert(con, k);
            LogUser l = new LogUser();
            l.setTanggal(tglSql.format(new Date()));
            l.setKodeUser(sistem.getUser().getUsername());
            l.setKategori("Save New Kategori Pekerjaan");
            l.setKeterangan(k.toString());
            LogUserDAO.insert(con, l);

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
    public static String deleteKategoriPekerjaan(Connection con, KategoriPekerjaan k)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            List<Pekerjaan> listPekerjaan = PekerjaanDAO.getAllByStatus(con,"true");
            for(Pekerjaan b : listPekerjaan){
                if(b.getKategoriPekerjaan().equals(k.getKodeKategori()))
                    status = "Tidak dapat hapus, karena kategori pekerjaan masih digunakan";
            }
            if(status.equals("true")){
                KategoriPekerjaanDAO.delete(con, k);
                LogUser l = new LogUser();
                l.setTanggal(tglSql.format(new Date()));
                l.setKodeUser(sistem.getUser().getUsername());
                l.setKategori("Delete Kategori Pekerjaan");
                l.setKeterangan(k.toString());
                LogUserDAO.insert(con, l);
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
    
    public static String saveNewBarang(Connection con, Barang b)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            b.setKodeBarang(BarangDAO.getId(con));
            BarangDAO.insert(con, b);
            for(Satuan s: b.getAllSatuan()){
                s.setKodeBarang(b.getKodeBarang());
                SatuanDAO.insert(con, s);
            }
            LogBarang log = new LogBarang();
            log.setTanggal(tglSql.format(new Date()));
            log.setKodeBarang(b.getKodeBarang());
            log.setKategori("NEW BARANG");
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
            stok.setTanggal(tglBarang.format(new Date()));
            stok.setKodeBarang(b.getKodeBarang());
            stok.setStokAwal(0);
            stok.setStokMasuk(0);
            stok.setStokKeluar(0);
            stok.setStokAkhir(0);
            StokBarangDAO.insert(con, stok);
                
            LogUser l = new LogUser();
            l.setTanggal(tglSql.format(new Date()));
            l.setKodeUser(sistem.getUser().getUsername());
            l.setKategori("Save New Barang");
            l.setKeterangan(b.getKodeBarang());
            LogUserDAO.insert(con, l);

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
    public static String saveUpdateBarang(Connection con, Barang b)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            BarangDAO.update(con, b);
            SatuanDAO.deleteAllByKodeBarang(con, b.getKodeBarang());
            for(Satuan s: b.getAllSatuan()){
                s.setKodeBarang(b.getKodeBarang());
                SatuanDAO.insert(con, s);
            }
            LogUser l = new LogUser();
            l.setTanggal(tglSql.format(new Date()));
            l.setKodeUser(sistem.getUser().getUsername());
            l.setKategori("Save Update Barang");
            l.setKeterangan(b.getKodeBarang());
            LogUserDAO.insert(con, l);

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
    public static String deleteBarang(Connection con, Barang b)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            double stok = 0;
            List<StokBarang> listStokBarang = StokBarangDAO.getAllByTanggal(con, tglBarang.format(new Date()));
            for(StokBarang s : listStokBarang){
                if(s.getKodeBarang().equals(b.getKodeBarang()))
                    stok = stok + s.getStokAkhir();
            }
            if(stok!=0){
                status = "Tidak dapat dihapus, karena stok barang masih ada";
            }else{
                b.setStatus("false");
                BarangDAO.update(con, b);

                LogUser l = new LogUser();
                l.setTanggal(tglSql.format(new Date()));
                l.setKodeUser(sistem.getUser().getUsername());
                l.setKategori("Delete Barang");
                l.setKeterangan(b.toString());
                LogUserDAO.insert(con, l);
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
    
    public static String saveNewPekerjaan(Connection con, Pekerjaan p)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            p.setKodePekerjaan(PekerjaanDAO.getId(con));
            PekerjaanDAO.insert(con, p);
            
            LogUser l = new LogUser();
            l.setTanggal(tglSql.format(new Date()));
            l.setKodeUser(sistem.getUser().getUsername());
            l.setKategori("Save New Pekerjaan");
            l.setKeterangan(p.getKodePekerjaan());
            LogUserDAO.insert(con, l);

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
    public static String saveUpdatePekerjaan(Connection con, Pekerjaan p)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            PekerjaanDAO.update(con, p);
            
            LogUser l = new LogUser();
            l.setTanggal(tglSql.format(new Date()));
            l.setKodeUser(sistem.getUser().getUsername());
            l.setKategori("Save Update Pekerjaan");
            l.setKeterangan(p.getKodePekerjaan());
            LogUserDAO.insert(con, l);

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
    public static String deletePekerjaan(Connection con, Pekerjaan p)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            p.setStatus("false");
            PekerjaanDAO.update(con, p);

            LogUser l = new LogUser();
            l.setTanggal(tglSql.format(new Date()));
            l.setKodeUser(sistem.getUser().getUsername());
            l.setKategori("Delete Pekerjaan");
            l.setKeterangan(p.toString());
            LogUserDAO.insert(con, l);

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
    
    public static String saveTransferKeuangan(Connection con, String dari, String ke, String keterangan, double jumlahRp)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            Keuangan d = new Keuangan();
            d.setNoKeuangan(KeuanganDAO.getId(con));
            d.setTglKeuangan(tglSql.format(new Date()));
            d.setTipeKeuangan(dari);
            d.setKategori("Transfer Keuangan");
            d.setKeterangan(keterangan);
            d.setJumlahRp(jumlahRp*-1);
            d.setKodeUser(sistem.getUser().getUsername());
            KeuanganDAO.insert(con, d);
            
            Keuangan t = new Keuangan();
            t.setNoKeuangan(d.getNoKeuangan());
            t.setTglKeuangan(tglSql.format(new Date()));
            t.setTipeKeuangan(ke);
            t.setKategori("Transfer Keuangan");
            t.setKeterangan(keterangan);
            t.setJumlahRp(jumlahRp);
            t.setKodeUser(sistem.getUser().getUsername());
            KeuanganDAO.insert(con, t);
            
            LogUser l = new LogUser();
            l.setTanggal(tglSql.format(new Date()));
            l.setKodeUser(sistem.getUser().getUsername());
            l.setKategori("Save Transfer Keuangan");
            l.setKeterangan(d.toString()+"\n"+t.toString());
            LogUserDAO.insert(con, l);

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
    public static String saveTransaksiKeuangan(Connection con, Keuangan k)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            KeuanganDAO.insert(con, k);

            Keuangan k2 = new Keuangan();
            k2.setNoKeuangan(k.getNoKeuangan());
            k2.setTglKeuangan(k.getTglKeuangan());
            k2.setTipeKeuangan("Untung/Rugi");
            k2.setKategori(k.getKategori());
            k2.setKeterangan(k.getKeterangan());
            k2.setJumlahRp(k.getJumlahRp());
            k2.setKodeUser(k.getKodeUser());
            KeuanganDAO.insert(con, k2);

            LogUser l = new LogUser();
            l.setTanggal(tglSql.format(new Date()));
            l.setKodeUser(sistem.getUser().getUsername());
            l.setKategori("Save Transaksi Keuangan");
            l.setKeterangan(k.toString());
            LogUserDAO.insert(con, l);

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
    public static String batalTransaksiKeuangan(Connection con, Keuangan k)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            KeuanganDAO.delete(con, k.getNoKeuangan());
            
            LogUser l = new LogUser();
            l.setTanggal(tglSql.format(new Date()));
            l.setKodeUser(sistem.getUser().getUsername());
            l.setKategori("Batal Transaksi Keuangan");
            l.setKeterangan(k.toString());
            LogUserDAO.insert(con, l);

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
    
    public static String saveNewHutang(Connection con, Hutang hutang)throws Exception{
        try{
            con.setAutoCommit(false);
            String status = "true";

            HutangDAO.insert(con, hutang);

            Keuangan k = new Keuangan();
            k.setNoKeuangan(KeuanganDAO.getId(con));
            k.setTglKeuangan(tglSql.format(new Date()));
            k.setTipeKeuangan(hutang.getKategoriKeuangan());
            k.setKategori(hutang.getKategori());
            k.setKeterangan(hutang.getNoHutang()+" - "+hutang.getKeterangan());
            k.setJumlahRp(hutang.getJumlahHutang());
            k.setKodeUser(sistem.getUser().getUsername());
            KeuanganDAO.insert(con, k);

            Keuangan k2 = new Keuangan();
            k2.setNoKeuangan(k.getNoKeuangan());
            k2.setTglKeuangan(tglSql.format(new Date()));
            k2.setTipeKeuangan("Hutang");
            k2.setKategori(hutang.getKategori());
            k2.setKeterangan(hutang.getNoHutang()+" - "+hutang.getKeterangan());
            k2.setJumlahRp(hutang.getJumlahHutang());
            k2.setKodeUser(sistem.getUser().getUsername());
            KeuanganDAO.insert(con, k2);

            LogUser l = new LogUser();
            l.setTanggal(tglSql.format(new Date()));
            l.setKodeUser(sistem.getUser().getUsername());
            l.setKategori("Save New Hutang");
            l.setKeterangan(hutang.toString());
            LogUserDAO.insert(con, l);

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
    public static String savePembayaranHutang(Connection con, PembayaranHutang pembayaran)throws Exception{
        try{
            con.setAutoCommit(false);
            String status = "true";

            PembayaranHutangDAO.insert(con, pembayaran);
            
            Hutang h = pembayaran.getHutang();
            h.setPembayaran(h.getPembayaran()+pembayaran.getJumlahPembayaran());
            h.setSisaHutang(h.getSisaHutang()-pembayaran.getJumlahPembayaran());
            if(h.getSisaHutang()==0)
                h.setStatus("close");
            HutangDAO.update(con, h);
            
            if(h.getKategori().equals("Hutang Pembelian")){
                PembelianHead p = PembelianHeadDAO.get(con, h.getKeterangan());
                p.setPembayaran(p.getPembayaran()+pembayaran.getJumlahPembayaran());
                p.setSisaPembayaran(p.getSisaPembayaran()-pembayaran.getJumlahPembayaran());
                PembelianHeadDAO.update(con, p);
                
                Supplier s = SupplierDAO.get(con, p.getKodeSupplier());
                s.setHutang(s.getHutang()-pembayaran.getJumlahPembayaran());
                SupplierDAO.update(con, s);
            }
            Keuangan keuPembayaran = new Keuangan();
            keuPembayaran.setNoKeuangan(KeuanganDAO.getId(con));
            keuPembayaran.setTglKeuangan(tglSql.format(new Date()));
            keuPembayaran.setTipeKeuangan(pembayaran.getTipeKeuangan());
            keuPembayaran.setKategori(h.getKategori());
            keuPembayaran.setKeterangan("Pembayaran - "+h.getKeterangan()+" ("+pembayaran.getNoPembayaran()+")");
            keuPembayaran.setJumlahRp(-pembayaran.getJumlahPembayaran());
            keuPembayaran.setKodeUser(sistem.getUser().getUsername());
            KeuanganDAO.insert(con, keuPembayaran);

            Keuangan keu = new Keuangan();
            keu.setNoKeuangan(keuPembayaran.getNoKeuangan());
            keu.setTglKeuangan(tglSql.format(new Date()));
            keu.setTipeKeuangan("Hutang");
            keu.setKategori(h.getKategori());
            keu.setKeterangan("Pembayaran - "+h.getKeterangan()+" ("+pembayaran.getNoPembayaran()+")");
            keu.setJumlahRp(-pembayaran.getJumlahPembayaran());
            keu.setKodeUser(sistem.getUser().getUsername());
            KeuanganDAO.insert(con, keu);

            LogUser l = new LogUser();
            l.setTanggal(tglSql.format(new Date()));
            l.setKodeUser(sistem.getUser().getUsername());
            l.setKategori("Save Pembayaran Hutang");
            l.setKeterangan(h.getNoHutang());
            LogUserDAO.insert(con, l);

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
    public static String saveBatalPembayaranHutang(Connection con, PembayaranHutang pembayaran){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            PembayaranHutangDAO.update(con, pembayaran);
            
            Hutang hutang = pembayaran.getHutang();
            hutang.setPembayaran(hutang.getPembayaran()-pembayaran.getJumlahPembayaran());
            hutang.setSisaHutang(hutang.getSisaHutang()+pembayaran.getJumlahPembayaran());
            if(hutang.getSisaHutang()!=0)
                hutang.setStatus("open");
            HutangDAO.update(con, hutang);
            
            if(hutang.getKategori().equals("Hutang Pembelian")){
                PembelianHead p = PembelianHeadDAO.get(con, hutang.getKeterangan());
                p.setPembayaran(p.getPembayaran()-pembayaran.getJumlahPembayaran());
                p.setSisaPembayaran(p.getSisaPembayaran()+pembayaran.getJumlahPembayaran());
                PembelianHeadDAO.update(con, p);
                
                Supplier s = SupplierDAO.get(con, p.getKodeSupplier());
                s.setHutang(s.getHutang()+pembayaran.getJumlahPembayaran());
                SupplierDAO.update(con, s);
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
    public static String saveJatuhTempoHutang(Connection con, Hutang h)throws Exception{
        try{
            con.setAutoCommit(false);
            String status = "true";

            HutangDAO.update(con, h);

            LogUser l = new LogUser();
            l.setTanggal(tglSql.format(new Date()));
            l.setKodeUser(sistem.getUser().getUsername());
            l.setKategori("Save Jatuh Tempo Hutang");
            l.setKeterangan(h.getNoHutang());
            LogUserDAO.insert(con, l);

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
     
    public static String saveNewPiutang(Connection con, Piutang piutang)throws Exception{
        try{
            con.setAutoCommit(false);
            String status = "true";

            PiutangDAO.insert(con, piutang);

            Keuangan k = new Keuangan();
            k.setNoKeuangan(KeuanganDAO.getId(con));
            k.setTglKeuangan(tglSql.format(new Date()));
            k.setTipeKeuangan(piutang.getKategoriKeuangan());
            k.setKategori(piutang.getKategori());
            k.setKeterangan(piutang.getNoPiutang()+" - "+piutang.getKeterangan());
            k.setJumlahRp(-piutang.getJumlahPiutang());
            k.setKodeUser(sistem.getUser().getUsername());
            KeuanganDAO.insert(con, k);

            Keuangan k2 = new Keuangan();
            k2.setNoKeuangan(k.getNoKeuangan());
            k2.setTglKeuangan(tglSql.format(new Date()));
            k2.setTipeKeuangan("Piutang");
            k2.setKategori(piutang.getKategori());
            k2.setKeterangan(piutang.getNoPiutang()+" - "+piutang.getKeterangan());
            k2.setJumlahRp(piutang.getJumlahPiutang());
            k2.setKodeUser(sistem.getUser().getUsername());
            KeuanganDAO.insert(con, k2);

            LogUser l = new LogUser();
            l.setTanggal(tglSql.format(new Date()));
            l.setKodeUser(sistem.getUser().getUsername());
            l.setKategori("Save New Piutang");
            l.setKeterangan(piutang.toString());
            LogUserDAO.insert(con, l);

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
    public static String savePembayaranPiutang(Connection con, PembayaranPiutang pembayaran)throws Exception{
        try{
            con.setAutoCommit(false);
            String status = "true";

            PembayaranPiutangDAO.insert(con, pembayaran);
            
            Piutang p = pembayaran.getPiutang();
            p.setPembayaran(p.getPembayaran()+pembayaran.getJumlahPembayaran());
            p.setSisaPiutang(p.getSisaPiutang()-pembayaran.getJumlahPembayaran());
            if(p.getSisaPiutang()==0)
                p.setStatus("close");
            PiutangDAO.update(con, p);
            
            if(p.getKategori().equals("Piutang Penjualan")){
                PenjualanHead penjualan = PenjualanHeadDAO.get(con, p.getKeterangan());
                penjualan.setPembayaran(penjualan.getPembayaran()+pembayaran.getJumlahPembayaran());
                penjualan.setSisaPembayaran(penjualan.getSisaPembayaran()-pembayaran.getJumlahPembayaran());
                PenjualanHeadDAO.update(con, penjualan);

                Customer customer = CustomerDAO.get(con, penjualan.getKodeCustomer());
                customer.setHutang(customer.getHutang()-pembayaran.getJumlahPembayaran());
                CustomerDAO.update(con, customer);
            }
            
            Keuangan keuPembayaran = new Keuangan();
            keuPembayaran.setNoKeuangan(KeuanganDAO.getId(con));
            keuPembayaran.setTglKeuangan(tglSql.format(new Date()));
            keuPembayaran.setTipeKeuangan(pembayaran.getTipeKeuangan());
            keuPembayaran.setKategori(p.getKategori());
            keuPembayaran.setKeterangan("Terima Pembayaran - "+p.getKeterangan()+" ("+pembayaran.getNoPembayaran()+")");
            keuPembayaran.setJumlahRp(pembayaran.getJumlahPembayaran());
            keuPembayaran.setKodeUser(sistem.getUser().getUsername());
            KeuanganDAO.insert(con, keuPembayaran);

            Keuangan keu = new Keuangan();
            keu.setNoKeuangan(keuPembayaran.getNoKeuangan());
            keu.setTglKeuangan(tglSql.format(new Date()));
            keu.setTipeKeuangan("Piutang");
            keu.setKategori(p.getKategori());
            keu.setKeterangan("Terima Pembayaran - "+p.getKeterangan()+" ("+pembayaran.getNoPembayaran()+")");
            keu.setJumlahRp(-pembayaran.getJumlahPembayaran());
            keu.setKodeUser(sistem.getUser().getUsername());
            KeuanganDAO.insert(con, keu);

            LogUser l = new LogUser();
            l.setTanggal(tglSql.format(new Date()));
            l.setKodeUser(sistem.getUser().getUsername());
            l.setKategori("Save Pembayaran Piutang");
            l.setKeterangan(p.getNoPiutang());
            LogUserDAO.insert(con, l);

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
    public static String saveBatalPembayaranPiutang(Connection con, PembayaranPiutang p){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            PembayaranPiutangDAO.update(con, p);
            
            Piutang piutang = p.getPiutang();
            piutang.setPembayaran(piutang.getPembayaran()-p.getJumlahPembayaran());
            piutang.setSisaPiutang(piutang.getSisaPiutang()+p.getJumlahPembayaran());
            if(piutang.getSisaPiutang()==0)
                piutang.setStatus("close");
            PiutangDAO.update(con, piutang);
            
            if(piutang.getKategori().equals("Piutang Penjualan")){
                PenjualanHead penjualan = PenjualanHeadDAO.get(con, piutang.getKeterangan());
                penjualan.setPembayaran(penjualan.getPembayaran()-p.getJumlahPembayaran());
                penjualan.setSisaPembayaran(penjualan.getSisaPembayaran()+p.getJumlahPembayaran());
                PenjualanHeadDAO.update(con, penjualan);

                Customer customer = CustomerDAO.get(con, penjualan.getKodeCustomer());
                customer.setHutang(customer.getHutang()+p.getJumlahPembayaran());
                CustomerDAO.update(con, customer);
            }
            
            KeuanganDAO.delete(con, p.getTipeKeuangan(), piutang.getKategori(), 
                    "Terima Pembayaran - "+piutang.getKeterangan()+" ("+p.getNoPembayaran()+")");
            
            KeuanganDAO.delete(con, "Piutang", piutang.getKategori(), 
                    "Terima Pembayaran - "+piutang.getKeterangan()+" ("+p.getNoPembayaran()+")");
            
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
    public static String saveJatuhTempoPiutang(Connection con, Piutang p)throws Exception{
        try{
            con.setAutoCommit(false);
            String status = "true";

            PiutangDAO.update(con, p);

            LogUser l = new LogUser();
            l.setTanggal(tglSql.format(new Date()));
            l.setKodeUser(sistem.getUser().getUsername());
            l.setKategori("Save Jatuh Tempo Piutang");
            l.setKeterangan(p.getNoPiutang());
            LogUserDAO.insert(con, l);

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
    
    public static String saveNewModal(Connection con, Keuangan k){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            KeuanganDAO.insert(con, k);
            
            Keuangan modal = new Keuangan();
            modal.setNoKeuangan(k.getNoKeuangan());
            modal.setTglKeuangan(tglSql.format(new Date()));
            modal.setTipeKeuangan("Modal");
            modal.setKategori(k.getKategori());
            modal.setKeterangan(k.getKeterangan());
            modal.setJumlahRp(k.getJumlahRp());
            modal.setKodeUser(sistem.getUser().getUsername());
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
    
    public static String savePembelianAsetTetap(Connection con, AsetTetap aset,String tipeKeuangan)throws Exception{
        try{
            con.setAutoCommit(false);
            String status = "true";

            AsetTetapDAO.insert(con, aset);

            Keuangan k = new Keuangan();
            k.setNoKeuangan(KeuanganDAO.getId(con));
            k.setTglKeuangan(tglSql.format(new Date()));
            k.setTipeKeuangan(tipeKeuangan);
            k.setKategori("Pembelian Aset Tetap");
            k.setKeterangan(aset.getNoAset());
            k.setJumlahRp(-aset.getNilaiAwal());
            k.setKodeUser(sistem.getUser().getUsername());
            KeuanganDAO.insert(con, k);

            Keuangan k2 = new Keuangan();
            k2.setNoKeuangan(k.getNoKeuangan());
            k2.setTglKeuangan(tglSql.format(new Date()));
            k2.setTipeKeuangan("Aset Tetap");
            k2.setKategori(aset.getKategori());
            k2.setKeterangan("Pembelian Aset Tetap - "+aset.getNoAset());
            k2.setJumlahRp(aset.getNilaiAwal());
            k2.setKodeUser(sistem.getUser().getUsername());
            KeuanganDAO.insert(con, k2);

            LogUser l = new LogUser();
            l.setTanggal(tglSql.format(new Date()));
            l.setKodeUser(sistem.getUser().getUsername());
            l.setKategori("Save Pembelian Aset Tetap");
            l.setKeterangan(aset.toString());
            LogUserDAO.insert(con, l);

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
    public static String savePenjualanAsetTetap(Connection con, AsetTetap aset, String tipeKeuangan)throws Exception{
        try{
            con.setAutoCommit(false);
            String status = "true";

            AsetTetapDAO.update(con, aset);
            
            Keuangan k = new Keuangan();
            k.setNoKeuangan(KeuanganDAO.getId(con));
            k.setTglKeuangan(tglSql.format(new Date()));
            k.setTipeKeuangan(tipeKeuangan);
            k.setKategori("Penjualan Aset Tetap");
            k.setKeterangan(aset.getNoAset());
            k.setJumlahRp(aset.getHargaJual());
            k.setKodeUser(sistem.getUser().getUsername());
            KeuanganDAO.insert(con, k);

            Keuangan k2 = new Keuangan();
            k2.setNoKeuangan(k.getNoKeuangan());
            k2.setTglKeuangan(tglSql.format(new Date()));
            k2.setTipeKeuangan("Aset Tetap");
            k2.setKategori(aset.getKategori());
            k2.setKeterangan("Penjualan Aset Tetap - "+aset.getNoAset());
            k2.setJumlahRp(-aset.getNilaiAkhir());
            k2.setKodeUser(sistem.getUser().getUsername());
            KeuanganDAO.insert(con, k2);

            Keuangan k3 = new Keuangan();
            k3.setNoKeuangan(k.getNoKeuangan());
            k3.setTglKeuangan(tglSql.format(new Date()));
            k3.setTipeKeuangan("Untung/Rugi");
            k3.setKategori("Pendapatan/Beban Penjualan Aset Tetap");
            k3.setKeterangan("Penjualan Aset Tetap - "+aset.getNoAset());
            k3.setJumlahRp(aset.getHargaJual()-aset.getNilaiAkhir());
            k3.setKodeUser(sistem.getUser().getUsername());
            KeuanganDAO.insert(con, k3);

            LogUser l = new LogUser();
            l.setTanggal(tglSql.format(new Date()));
            l.setKodeUser(sistem.getUser().getUsername());
            l.setKategori("Save Penjualan Aset Tetap");
            l.setKeterangan(aset.toString());
            LogUserDAO.insert(con, l);

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
    
    public static String saveNewPemesanan(Connection con, PenjualanHead pemesanan){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            PenjualanHeadDAO.insert(con, pemesanan);
            for(PenjualanDetail detail : pemesanan.getAllDetail()){
                PenjualanDetailDAO.insert(con, detail);
                for(RencanaAnggaranBebanPenjualan b : detail.getRencanaAnggaranBebanPenjualan()){
                    RencanaAnggaranBebanPenjualanDAO.insert(con, b);
                }
                for(RencanaAnggaranBebanMaterial b : detail.getRencanaAnggaranBelanja()){
                    RencanaAnggaranBebanMaterialDAO.insert(con, b);
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
    public static String saveBatalPemesanan(Connection con, PenjualanHead penjualan){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            double nilai = 0;
            List<PengirimanDetail> listKirim = PengirimanDetailDAO.getAllByNoPenjualanAndNoUrutAndStatus(
                con, penjualan.getNoPenjualan(), "%", "true");
            for(PengirimanDetail d : listKirim){
                nilai = nilai + d.getNilai()*d.getQty();
            }
            List<ReturDetail> listRetur = ReturDetailDAO.getAllByNoPenjualanAndNoUrutAndStatus(
                con, penjualan.getNoPenjualan(), "%", "true");
            for(ReturDetail d : listRetur){
                nilai = nilai - d.getNilai()*d.getQty();
            }
            List<BebanPenjualan> listBebanPenjualan = BebanPenjualanDAO.getAllByNoPenjualanAndNoUrut(
                con, penjualan.getNoPenjualan(), "%", "true");
            for(BebanPenjualan b : listBebanPenjualan){
                nilai = nilai + b.getJumlahRp();
            }
            if(nilai!=0)
                status = "Tidak dapat dibatalkan, karena sudah ada pengiriman barang atau beban penjualan yang terbayar";
            else if(penjualan.getPembayaran()!=0)
                status = "Tidak dapat dibatalkan, karena sudah ada pembayaran downpayment";
            else
                PenjualanHeadDAO.update(con, penjualan);
            
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
    
    public static String saveNewTerimaDownPayment(Connection con, PenjualanHead pemesanan, double jumlahBayar, String tipeKeuangan){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            pemesanan.setPembayaran(pemesanan.getPembayaran()+jumlahBayar);
            pemesanan.setSisaPembayaran(pemesanan.getSisaPembayaran()-jumlahBayar);
            PenjualanHeadDAO.update(con, pemesanan);
            
            Customer customer = pemesanan.getCustomer();
            customer.setDeposit(customer.getDeposit()+jumlahBayar);
            CustomerDAO.update(con, customer);

            Hutang h = new Hutang();
            h.setNoHutang(HutangDAO.getId(con));
            h.setTglHutang(tglSql.format(new Date()));
            h.setKategori("Terima Pembayaran Down Payment");
            h.setKeterangan(pemesanan.getNoPenjualan());
            h.setKategoriKeuangan(tipeKeuangan);
            h.setJumlahHutang(jumlahBayar);
            h.setPembayaran(0);
            h.setSisaHutang(jumlahBayar);
            h.setJatuhTempo("2000-01-01");
            h.setKodeUser(sistem.getUser().getUsername());
            h.setStatus("open");
            HutangDAO.insert(con, h);

            Keuangan k = new Keuangan();
            k.setNoKeuangan(KeuanganDAO.getId(con));
            k.setTglKeuangan(tglSql.format(new Date()));
            k.setTipeKeuangan(tipeKeuangan);
            k.setKategori("Terima Pembayaran Down Payment");
            k.setKeterangan("Terima Pembayaran Down Payment - "+pemesanan.getNoPenjualan()+" ("+h.getNoHutang()+")");
            k.setJumlahRp(jumlahBayar);
            k.setKodeUser(sistem.getUser().getUsername());
            KeuanganDAO.insert(con, k);

            Keuangan kh = new Keuangan();
            kh.setNoKeuangan(k.getNoKeuangan());
            kh.setTglKeuangan(tglSql.format(new Date()));
            kh.setTipeKeuangan("Hutang");
            kh.setKategori("Terima Pembayaran Down Payment");
            kh.setKeterangan("Terima Pembayaran Down Payment - "+pemesanan.getNoPenjualan()+" ("+h.getNoHutang()+")");
            kh.setJumlahRp(jumlahBayar);
            kh.setKodeUser(sistem.getUser().getUsername());
            KeuanganDAO.insert(con, kh);
            
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
    public static String saveBatalTerimaDownPayment(Connection con, Hutang h){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            h.setStatus("false");
            HutangDAO.update(con, h);
            
            PenjualanHead pemesanan = h.getPenjualan();
            pemesanan.setPembayaran(pemesanan.getPembayaran()-h.getJumlahHutang());
            pemesanan.setSisaPembayaran(pemesanan.getSisaPembayaran()+h.getJumlahHutang());
            PenjualanHeadDAO.update(con, pemesanan);
            
            Customer customer = pemesanan.getCustomer();
            customer.setDeposit(customer.getDeposit()-h.getJumlahHutang());
            CustomerDAO.update(con, customer);
            
            KeuanganDAO.delete(con, h.getKategoriKeuangan(), "Terima Pembayaran Down Payment", 
                    "Terima Pembayaran Down Payment - "+pemesanan.getNoPenjualan()+" ("+h.getNoHutang()+")");
            
            KeuanganDAO.delete(con, "Hutang", "Terima Pembayaran Down Payment", 
                    "Terima Pembayaran Down Payment - "+pemesanan.getNoPenjualan()+" ("+h.getNoHutang()+")");
            
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
    
    public static String saveNewPengiriman(Connection con, PengirimanHead pengiriman){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            PengirimanHeadDAO.insert(con, pengiriman);
            
            List<PenjualanDetail> listPenjualanDetail = PenjualanDetailDAO.getAllByNoPenjualan(con, pengiriman.getNoPenjualan());
            List<PengirimanDetail> stokBarang = new ArrayList<>();
            double nilai=0;
            for(PengirimanDetail d : pengiriman.getAllDetail()){
                LogBarang log = LogBarangDAO.getLastByBarang(con, d.getKodeBarang());
                if(log.getStokAkhir()!=0)
                    d.setNilai(log.getNilaiAkhir()/log.getStokAkhir());
                PengirimanDetailDAO.insert(con, d);
                
                nilai = nilai + d.getNilai()*d.getQty();
                
                Boolean inStok = false;
                for(PengirimanDetail detail : stokBarang){
                    if(d.getKodeBarang().equals(detail.getKodeBarang())){
                        detail.setNilai((detail.getNilai()*detail.getQty()+d.getNilai()*d.getQty())/
                                (detail.getQty()+d.getQty()));
                        detail.setQty(detail.getQty()+d.getQty());
                        inStok = true;
                    }
                }
                if(!inStok)
                    stokBarang.add(d);
                
                for(PenjualanDetail pj : listPenjualanDetail){
                    if(d.getNoUrutPenjualan().equals(pj.getNoUrut()))
                        pj.setTotalBebanMaterial(pj.getTotalBebanMaterial()+(d.getNilai()*d.getQty()));
                }
            }
            for(PenjualanDetail pj : listPenjualanDetail){
                PenjualanDetailDAO.update(con, pj);
            }
            Keuangan keuhpp = new Keuangan();
            keuhpp.setNoKeuangan(KeuanganDAO.getId(con));
            keuhpp.setTglKeuangan(tglSql.format(new Date()));
            keuhpp.setTipeKeuangan("Pekerjaan Proyek/Barang Jadi");
            keuhpp.setKategori(pengiriman.getPenjualanHead().getNamaProyek());
            keuhpp.setKeterangan("Pengiriman Barang - "+pengiriman.getNoPengiriman());
            keuhpp.setJumlahRp(nilai);
            keuhpp.setKodeUser(sistem.getUser().getUsername());
            KeuanganDAO.insert(con, keuhpp);
            
            for(PengirimanDetail d : stokBarang){
                StokBarang stok = StokBarangDAO.get(con, tglBarang.format(new Date()), d.getKodeBarang());
                if(stok.getStokAkhir()<d.getQty()){
                    status = "Stok barang "+d.getNamaBarang()+" tidak mencukupi";
                }else{
                    if(stok.getTanggal().equals(tglBarang.format(new Date()))){
                        stok.setStokKeluar(stok.getStokKeluar()+d.getQty());
                        stok.setStokAkhir(stok.getStokAkhir()-d.getQty());
                        StokBarangDAO.update(con, stok);
                    }else{
                        StokBarang newStok = new StokBarang();
                        newStok.setTanggal(tglBarang.format(new Date()));
                        newStok.setKodeBarang(d.getKodeBarang());
                        newStok.setStokAwal(stok.getStokAkhir());
                        newStok.setStokMasuk(0);
                        newStok.setStokKeluar(d.getQty());
                        newStok.setStokAkhir(stok.getStokAkhir()-d.getQty());
                        StokBarangDAO.insert(con, newStok);
                    }
                }
                LogBarang lb = LogBarangDAO.getLastByBarang(con, d.getKodeBarang());

                LogBarang log = new LogBarang();
                log.setTanggal(tglSql.format(new Date()));
                log.setKodeBarang(d.getKodeBarang());
                log.setKategori("Pengiriman Barang");
                log.setKeterangan(pengiriman.getNoPengiriman());
                log.setStokAwal(lb.getStokAkhir());
                log.setNilaiAwal(lb.getNilaiAkhir());
                log.setStokMasuk(0);
                log.setNilaiMasuk(0);
                log.setStokKeluar(d.getQty());
                log.setNilaiKeluar(d.getNilai()*d.getQty());
                log.setStokAkhir(lb.getStokAkhir()-d.getQty());
                log.setNilaiAkhir(lb.getNilaiAkhir()-(d.getNilai()*d.getQty()));
                LogBarangDAO.insert(con, log);

                Keuangan keuStok = new Keuangan();
                keuStok.setNoKeuangan(keuhpp.getNoKeuangan());
                keuStok.setTglKeuangan(tglSql.format(new Date()));
                keuStok.setTipeKeuangan("Stok Barang");
                keuStok.setKategori(d.getKodeBarang());
                keuStok.setKeterangan("Pengiriman Barang - "+pengiriman.getNoPengiriman());
                keuStok.setJumlahRp(-d.getNilai()*d.getQty());
                keuStok.setKodeUser(sistem.getUser().getUsername());
                KeuanganDAO.insert(con, keuStok);
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
    public static String saveBatalPengiriman(Connection con, PengirimanHead pengiriman){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            PengirimanHeadDAO.update(con, pengiriman);
            
            List<PengirimanDetail> stokBarang = new ArrayList<>();
            List<PenjualanDetail> listPenjualanDetail = PenjualanDetailDAO.getAllByNoPenjualan(con, pengiriman.getNoPenjualan());
            double nilai=0;
            for(PengirimanDetail d : pengiriman.getAllDetail()){
                nilai = nilai + d.getNilai()*d.getQty();
                Boolean inStok = false;
                for(PengirimanDetail detail : stokBarang){
                    if(d.getKodeBarang().equals(detail.getKodeBarang())){
                        detail.setNilai((detail.getNilai()*detail.getQty()+d.getNilai()*d.getQty())/
                                (detail.getQty()+d.getQty()));
                        detail.setQty(detail.getQty()+d.getQty());
                        inStok = true;
                    }
                }
                if(!inStok)
                    stokBarang.add(d);
                
                for(PenjualanDetail pj : listPenjualanDetail){
                    if(d.getNoUrutPenjualan().equals(pj.getNoUrut()))
                        pj.setTotalBebanMaterial(pj.getTotalBebanMaterial()-(d.getNilai()*d.getQty()));
                }
            }
            for(PenjualanDetail pj : listPenjualanDetail){
                PenjualanDetailDAO.update(con, pj);
            }
            KeuanganDAO.delete(con, "Pekerjaan Proyek/Barang Jadi", pengiriman.getPenjualanHead().getNamaProyek(),
                    "Pengiriman Barang - "+pengiriman.getNoPengiriman());
            
            for(PengirimanDetail d : stokBarang){
                StokBarang stok = StokBarangDAO.get(con, tglBarang.format(tglSql.parse(pengiriman.getTglPengiriman())), d.getKodeBarang());
                stok.setStokKeluar(stok.getStokKeluar()-d.getQty());
                stok.setStokAkhir(stok.getStokAkhir()+d.getQty());
                StokBarangDAO.update(con, stok);
                resetStokBarang(con, d.getKodeBarang(), tglBarang.format(tglSql.parse(pengiriman.getTglPengiriman())));

                LogBarangDAO.delete(con, d.getKodeBarang(), "Pengiriman Barang", pengiriman.getNoPengiriman());
                resetLogBarang(con, pengiriman.getTglPengiriman(), d.getKodeBarang());
                
                KeuanganDAO.delete(con, "Stok Barang", d.getKodeBarang(), "Pengiriman Barang - "+pengiriman.getNoPengiriman());
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
    
    public static String saveNewRetur(Connection con, ReturHead retur){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            ReturHeadDAO.insert(con, retur);
            
            List<ReturDetail> stokBarang = new ArrayList<>();
            List<PenjualanDetail> listPenjualanDetail = PenjualanDetailDAO.getAllByNoPenjualan(con, retur.getNoPenjualan());
            double nilai=0;
            for(ReturDetail d : retur.getAllDetail()){
                ReturDetailDAO.insert(con, d);
                
                nilai = nilai + d.getNilai()*d.getQty();
                
                Boolean inStok = false;
                for(ReturDetail detail : stokBarang){
                    if(d.getKodeBarang().equals(detail.getKodeBarang())){
                        detail.setNilai((detail.getNilai()*detail.getQty()+d.getNilai()*d.getQty())/
                                (detail.getQty()+d.getQty()));
                        detail.setQty(detail.getQty()+d.getQty());
                        inStok = true;
                    }
                }
                if(!inStok)
                    stokBarang.add(d);
                
                for(PenjualanDetail pj : listPenjualanDetail){
                    if(d.getNoUrutPenjualan().equals(pj.getNoUrut()))
                        pj.setTotalBebanMaterial(pj.getTotalBebanMaterial()-(d.getNilai()*d.getQty()));
                }
            }
            for(PenjualanDetail pj : listPenjualanDetail){
                PenjualanDetailDAO.update(con, pj);
            }
            Keuangan keuhpp = new Keuangan();
            keuhpp.setNoKeuangan(KeuanganDAO.getId(con));
            keuhpp.setTglKeuangan(tglSql.format(new Date()));
            keuhpp.setTipeKeuangan("Pekerjaan Proyek/Barang Jadi");
            keuhpp.setKategori(retur.getPenjualanHead().getNamaProyek());
            keuhpp.setKeterangan("Retur Barang - "+retur.getNoRetur());
            keuhpp.setJumlahRp(-nilai);
            keuhpp.setKodeUser(sistem.getUser().getUsername());
            KeuanganDAO.insert(con, keuhpp);
            
            for(ReturDetail d : stokBarang){
                StokBarang stok = StokBarangDAO.get(con, tglBarang.format(new Date()), d.getKodeBarang());
                if(stok.getTanggal().equals(tglBarang.format(new Date()))){
                    stok.setStokMasuk(stok.getStokMasuk()+d.getQty());
                    stok.setStokAkhir(stok.getStokAkhir()+d.getQty());
                    StokBarangDAO.update(con, stok);
                }else{
                    StokBarang newStok = new StokBarang();
                    newStok.setTanggal(tglBarang.format(new Date()));
                    newStok.setKodeBarang(d.getKodeBarang());
                    newStok.setStokAwal(stok.getStokAkhir());
                    newStok.setStokMasuk(d.getQty());
                    newStok.setStokKeluar(0);
                    newStok.setStokAkhir(stok.getStokAkhir()+d.getQty());
                    StokBarangDAO.insert(con, newStok);
                }
                LogBarang lb = LogBarangDAO.getLastByBarang(con, d.getKodeBarang());

                LogBarang log = new LogBarang();
                log.setTanggal(tglSql.format(new Date()));
                log.setKodeBarang(d.getKodeBarang());
                log.setKategori("Retur Barang");
                log.setKeterangan(retur.getNoRetur());
                log.setStokAwal(lb.getStokAkhir());
                log.setNilaiAwal(lb.getNilaiAkhir());
                log.setStokMasuk(d.getQty());
                log.setNilaiMasuk(d.getNilai()*d.getQty());
                log.setStokKeluar(0);
                log.setNilaiKeluar(0);
                log.setStokAkhir(lb.getStokAkhir()+d.getQty());
                log.setNilaiAkhir(lb.getNilaiAkhir()+(d.getNilai()*d.getQty()));
                LogBarangDAO.insert(con, log);


                Keuangan keuStok = new Keuangan();
                keuStok.setNoKeuangan(keuhpp.getNoKeuangan());
                keuStok.setTglKeuangan(tglSql.format(new Date()));
                keuStok.setTipeKeuangan("Stok Barang");
                keuStok.setKategori(d.getKodeBarang());
                keuStok.setKeterangan("Retur Barang - "+retur.getNoRetur());
                keuStok.setJumlahRp(d.getNilai()*d.getQty());
                keuStok.setKodeUser(sistem.getUser().getUsername());
                KeuanganDAO.insert(con, keuStok);
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
    public static String saveBatalRetur(Connection con, ReturHead retur){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            ReturHeadDAO.update(con, retur);
            retur.setAllDetail(ReturDetailDAO.getAllByNoRetur(con, retur.getNoRetur()));
            
            List<PenjualanDetail> listPenjualanDetail = PenjualanDetailDAO.getAllByNoPenjualan(con, retur.getNoPenjualan());
            List<ReturDetail> stokBarang = new ArrayList<>();
            double nilai=0;
            for(ReturDetail d : retur.getAllDetail()){
                nilai = nilai + d.getNilai()*d.getQty();
                Boolean inStok = false;
                for(ReturDetail detail : stokBarang){
                    if(d.getKodeBarang().equals(detail.getKodeBarang())){
                        detail.setNilai((detail.getNilai()*detail.getQty()+d.getNilai()*d.getQty())/
                                (detail.getQty()+d.getQty()));
                        detail.setQty(detail.getQty()+d.getQty());
                        inStok = true;
                    }
                }
                if(!inStok)
                    stokBarang.add(d);
                
                for(PenjualanDetail pj : listPenjualanDetail){
                    if(d.getNoUrutPenjualan().equals(pj.getNoUrut()))
                        pj.setTotalBebanMaterial(pj.getTotalBebanMaterial()-(d.getNilai()*d.getQty()));
                }
            }
            for(PenjualanDetail pj : listPenjualanDetail){
                PenjualanDetailDAO.update(con, pj);
            }
            KeuanganDAO.delete(con, "Pekerjaan Proyek/Barang Jadi", retur.getPenjualanHead().getNamaProyek(),
                    "Retur Barang - "+retur.getNoRetur());
            
            for(ReturDetail d : stokBarang){
                StokBarang stokAkhir = StokBarangDAO.get(con, tglBarang.format(new Date()), d.getKodeBarang());
                if(stokAkhir.getStokAkhir()<d.getQty()){
                    status = "Stok barang "+d.getNamaBarang()+" tidak mencukupi";
                }else{
                    StokBarang stok = StokBarangDAO.get(con, tglBarang.format(tglSql.parse(retur.getTglRetur())), d.getKodeBarang());
                    stok.setStokMasuk(stok.getStokMasuk()-d.getQty());
                    stok.setStokAkhir(stok.getStokAkhir()-d.getQty());
                    StokBarangDAO.update(con, stok);
                    resetStokBarang(con, d.getKodeBarang(), tglBarang.format(tglSql.parse(retur.getTglRetur())));

                    LogBarangDAO.delete(con, d.getKodeBarang(), "Retur Barang", retur.getNoRetur());
                    resetLogBarang(con, retur.getTglRetur(), d.getKodeBarang());

                    KeuanganDAO.delete(con, "Stok Barang", d.getKodeBarang(),
                            "Retur Barang - "+retur.getNoRetur());
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
    
    public static String saveBebanPenjualan(Connection con, BebanPenjualan b)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            BebanPenjualanDAO.insert(con, b);
            
            b.getPenjualanDetail().setTotalBebanPenjualan(
                b.getPenjualanDetail().getTotalBebanPenjualan()+b.getJumlahRp());
            PenjualanDetailDAO.update(con, b.getPenjualanDetail());
            
            Keuangan d = new Keuangan();
            d.setNoKeuangan(KeuanganDAO.getId(con));
            d.setTglKeuangan(tglSql.format(new Date()));
            d.setTipeKeuangan(b.getTipeKeuangan());
            d.setKategori(b.getPenjualanDetail().getPenjualanHead().getNamaProyek());
            d.setKeterangan(b.getKategori()+" - "+b.getKeterangan()+" ("+b.getNoBebanPenjualan()+")");
            d.setJumlahRp(-b.getJumlahRp());
            d.setKodeUser(sistem.getUser().getUsername());
            KeuanganDAO.insert(con, d);
            
            Keuangan t = new Keuangan();
            t.setNoKeuangan(d.getNoKeuangan());
            t.setTglKeuangan(tglSql.format(new Date()));
            t.setTipeKeuangan("Pekerjaan Proyek/Barang Jadi");
            t.setKategori(b.getPenjualanDetail().getPenjualanHead().getNamaProyek());
            t.setKeterangan(b.getKategori()+" - "+b.getKeterangan()+" ("+b.getNoBebanPenjualan()+")");
            t.setJumlahRp(b.getJumlahRp());
            t.setKodeUser(sistem.getUser().getUsername());
            KeuanganDAO.insert(con, t);

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
    public static String batalBebanPenjualan(Connection con, BebanPenjualan b)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            BebanPenjualanDAO.update(con, b);
            
            b.getPenjualanDetail().setTotalBebanPenjualan(
                b.getPenjualanDetail().getTotalBebanPenjualan()-b.getJumlahRp());
            PenjualanDetailDAO.update(con, b.getPenjualanDetail());
            
            KeuanganDAO.delete(con, b.getTipeKeuangan(), b.getPenjualanDetail().getPenjualanHead().getNamaProyek(), 
                    b.getKategori()+" - "+b.getKeterangan()+" ("+b.getNoBebanPenjualan()+")");
            
            KeuanganDAO.delete(con, "Pekerjaan Proyek/Barang Jadi", b.getPenjualanDetail().getPenjualanHead().getNamaProyek(), 
                    b.getKategori()+" - "+b.getKeterangan()+" ("+b.getNoBebanPenjualan()+")");
            
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
    
    public static String saveProyekSelesai(Connection con, PenjualanHead penjualan,String tglMulai,String tglSelesai){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            penjualan.setTglMulai(tglMulai);
            penjualan.setTglSelesai(tglSelesai);
            penjualan.setTglPenjualan(tglSql.format(new Date()));
            penjualan.setStatus("close");
            PenjualanHeadDAO.update(con, penjualan);
            
            Keuangan keuPenjualan = new Keuangan();
            keuPenjualan.setNoKeuangan(KeuanganDAO.getId(con));
            keuPenjualan.setTglKeuangan(tglSql.format(new Date()));
            keuPenjualan.setTipeKeuangan("Untung/Rugi");
            keuPenjualan.setKategori("Penjualan");
            keuPenjualan.setKeterangan("Penjualan - "+penjualan.getNoPenjualan());
            keuPenjualan.setJumlahRp(penjualan.getTotalPenjualan());
            keuPenjualan.setKodeUser(sistem.getUser().getUsername());
            KeuanganDAO.insert(con, keuPenjualan);
            
            Piutang piutang = new Piutang();
            piutang.setNoPiutang(PiutangDAO.getId(con));
            piutang.setTglPiutang(tglSql.format(new Date()));
            piutang.setKategori("Piutang Penjualan");
            piutang.setKeterangan(penjualan.getNoPenjualan());
            piutang.setKategoriKeuangan("Penjualan");
            piutang.setJumlahPiutang(penjualan.getTotalPenjualan());
            piutang.setPembayaran(penjualan.getPembayaran());
            piutang.setSisaPiutang(penjualan.getSisaPembayaran());
            piutang.setJatuhTempo("2000-01-01");
            piutang.setKodeUser(sistem.getUser().getUsername());
            if(piutang.getSisaPiutang()>0)
                piutang.setStatus("open");
            else
                piutang.setStatus("close");
            PiutangDAO.insert(con, piutang);

            Keuangan keuPiutang = new Keuangan();
            keuPiutang.setNoKeuangan(keuPenjualan.getNoKeuangan());
            keuPiutang.setTglKeuangan(tglSql.format(new Date()));
            keuPiutang.setTipeKeuangan("Piutang");
            keuPiutang.setKategori("Piutang Penjualan");
            keuPiutang.setKeterangan("Penjualan - "+penjualan.getNoPenjualan());
            keuPiutang.setJumlahRp(penjualan.getSisaPembayaran());
            keuPiutang.setKodeUser(sistem.getUser().getUsername());
            KeuanganDAO.insert(con, keuPiutang);

            Customer customer = CustomerDAO.get(con, penjualan.getKodeCustomer());
            if(penjualan.getSisaPembayaran()>0)
                customer.setHutang(customer.getHutang()+penjualan.getSisaPembayaran());
            
            if(penjualan.getPembayaran()>0){
                customer.setDeposit(customer.getDeposit()-penjualan.getPembayaran());
                
                PembayaranPiutang tp = new PembayaranPiutang();
                tp.setNoPembayaran(PembayaranPiutangDAO.getId(con));
                tp.setTglPembayaran(tglSql.format(new Date()));
                tp.setNoPiutang(piutang.getNoPiutang());
                tp.setJumlahPembayaran(penjualan.getPembayaran());
                tp.setTipeKeuangan("Down Payment");
                tp.setCatatan(penjualan.getNoPenjualan());
                tp.setKodeUser(sistem.getUser().getUsername());
                tp.setTglBatal("2000-01-01 00:00:00");
                tp.setUserBatal("");
                tp.setStatus("true");
                PembayaranPiutangDAO.insert(con, tp);

                double bayar = penjualan.getPembayaran();
                List<Hutang> listHutang = HutangDAO.getAllByKategoriAndKeterangan(con, "Terima Pembayaran Down Payment",penjualan.getNoPenjualan());
                for(Hutang h : listHutang){
                    if(h.getSisaHutang()>bayar){
                        PembayaranHutang pembayaran = new PembayaranHutang();
                        pembayaran.setNoPembayaran(PembayaranHutangDAO.getId(con));
                        pembayaran.setTglPembayaran(tglSql.format(new Date()));
                        pembayaran.setNoHutang(h.getNoHutang());
                        pembayaran.setJumlahPembayaran(bayar);
                        pembayaran.setTipeKeuangan("Penjualan");
                        pembayaran.setCatatan(penjualan.getNoPenjualan());
                        pembayaran.setKodeUser(sistem.getUser().getUsername());
                        pembayaran.setTglBatal("2000-01-01 00:00:00");
                        pembayaran.setUserBatal("");
                        pembayaran.setStatus("true");
                        PembayaranHutangDAO.insert(con, pembayaran);
                        
                        h.setPembayaran(h.getPembayaran()+bayar);
                        h.setSisaHutang(h.getSisaHutang()-bayar);
                        HutangDAO.update(con, h);
                        
                        bayar = 0;
                    }else{
                        PembayaranHutang pembayaran = new PembayaranHutang();
                        pembayaran.setNoPembayaran(PembayaranHutangDAO.getId(con));
                        pembayaran.setTglPembayaran(tglSql.format(new Date()));
                        pembayaran.setNoHutang(h.getNoHutang());
                        pembayaran.setJumlahPembayaran(h.getSisaHutang());
                        pembayaran.setTipeKeuangan("Penjualan");
                        pembayaran.setCatatan(penjualan.getNoPenjualan());
                        pembayaran.setKodeUser(sistem.getUser().getUsername());
                        pembayaran.setTglBatal("2000-01-01 00:00:00");
                        pembayaran.setUserBatal("");
                        pembayaran.setStatus("true");
                        PembayaranHutangDAO.insert(con, pembayaran);
                        
                        h.setPembayaran(h.getPembayaran()+h.getSisaHutang());
                        h.setSisaHutang(h.getSisaHutang()-h.getSisaHutang());
                        h.setStatus("close");
                        HutangDAO.update(con, h);
                        
                        bayar = bayar - h.getSisaHutang();
                    }
                }
                Keuangan kh = new Keuangan();
                kh.setNoKeuangan(keuPenjualan.getNoKeuangan());
                kh.setTglKeuangan(tglSql.format(new Date()));
                kh.setTipeKeuangan("Hutang");
                kh.setKategori("Terima Pembayaran Down Payment");
                kh.setKeterangan("Penjualan - "+penjualan.getNoPenjualan());
                kh.setJumlahRp(-penjualan.getPembayaran());
                kh.setKodeUser(sistem.getUser().getUsername());
                KeuanganDAO.insert(con, kh);
            }
            CustomerDAO.update(con, customer);
            
            double nilai = 0;
            List<PengirimanDetail> listKirim = PengirimanDetailDAO.getAllByNoPenjualanAndNoUrutAndStatus(
                con, penjualan.getNoPenjualan(), "%", "true");
            for(PengirimanDetail d : listKirim){
                nilai = nilai + d.getNilai()*d.getQty();
            }
            List<ReturDetail> listRetur = ReturDetailDAO.getAllByNoPenjualanAndNoUrutAndStatus(
                con, penjualan.getNoPenjualan(), "%", "true");
            for(ReturDetail d : listRetur){
                nilai = nilai - d.getNilai()*d.getQty();
            }
            List<BebanPenjualan> listBebanPenjualan = BebanPenjualanDAO.getAllByNoPenjualanAndNoUrut(
                con, penjualan.getNoPenjualan(), "%", "true");
            for(BebanPenjualan b : listBebanPenjualan){
                nilai = nilai + b.getJumlahRp();
            }
            
            Keuangan keuhpp = new Keuangan();
            keuhpp.setNoKeuangan(keuPenjualan.getNoKeuangan());
            keuhpp.setTglKeuangan(tglSql.format(new Date()));
            keuhpp.setTipeKeuangan("Pekerjaan Proyek/Barang Jadi");
            keuhpp.setKategori(penjualan.getNamaProyek());
            keuhpp.setKeterangan("Penjualan - "+penjualan.getNoPenjualan());
            keuhpp.setJumlahRp(-nilai);
            keuhpp.setKodeUser(sistem.getUser().getUsername());
            KeuanganDAO.insert(con, keuhpp);
            
            Keuangan keuUR = new Keuangan();
            keuUR.setNoKeuangan(keuPenjualan.getNoKeuangan());
            keuUR.setTglKeuangan(tglSql.format(new Date()));
            keuUR.setTipeKeuangan("Untung/Rugi");
            keuUR.setKategori("HPP");
            keuUR.setKeterangan("Penjualan - "+penjualan.getNoPenjualan());
            keuUR.setJumlahRp(-nilai);
            keuUR.setKodeUser(sistem.getUser().getUsername());
            KeuanganDAO.insert(con, keuUR);
            
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
    public static String batalProyekSelesai(Connection con, PenjualanHead penjualan){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            penjualan.setTglMulai("2000-01-01");
            penjualan.setTglSelesai("2000-01-01");
            penjualan.setTglPenjualan("2000-01-01 00:00:00");
            penjualan.setStatus("open");
            PenjualanHeadDAO.update(con, penjualan);
            
            KeuanganDAO.delete(con, "Untung/Rugi", "Penjualan", "Penjualan - "+penjualan.getNoPenjualan());
            
            Piutang piutang = PiutangDAO.getByKategoriAndKeterangan(con, "Piutang Penjualan", penjualan.getNoPenjualan());
            piutang.setStatus("false");
            PiutangDAO.update(con, piutang);

            KeuanganDAO.delete(con, "Piutang", "Piutang Penjualan", "Penjualan - "+penjualan.getNoPenjualan());

            Customer customer = CustomerDAO.get(con, penjualan.getKodeCustomer());
            if(penjualan.getSisaPembayaran()>0)
                customer.setHutang(customer.getHutang()-penjualan.getSisaPembayaran());
            
            PembayaranPiutang dp = null;
            List<PembayaranPiutang> listPembayaranPiutang = PembayaranPiutangDAO.getAllByNoPiutangAndStatus(
                    con, piutang.getNoPiutang(), "true");
            for(PembayaranPiutang tp : listPembayaranPiutang){
                if(tp.getTipeKeuangan().equals("Down Payment"))
                    dp = tp;
                else
                    status = "Tidak dapat dibatal,karena sudah ada pembayaran";
            }
            if(dp!=null){
                customer.setDeposit(customer.getDeposit()+dp.getJumlahPembayaran());
                
                dp.setTglBatal(tglSql.format(new Date()));
                dp.setUserBatal(sistem.getUser().getUsername());
                dp.setStatus("false");
                PembayaranPiutangDAO.update(con, dp);

                List<Hutang> listHutang = HutangDAO.getAllByKategoriAndKeterangan(
                        con, "Terima Pembayaran Down Payment", penjualan.getNoPenjualan());
                for(Hutang h : listHutang){
                    List<PembayaranHutang> pembayaran = PembayaranHutangDAO.getAllByNoHutangAndStatus(con, h.getNoHutang(), "true");
                    for(PembayaranHutang p : pembayaran){
                        if(p.getTipeKeuangan().equals("Penjualan")&&p.getCatatan().equals(penjualan.getNoPenjualan())){
                            p.setTglBatal(tglSql.format(new Date()));
                            p.setUserBatal(sistem.getUser().getUsername());
                            p.setStatus("false");
                            PembayaranHutangDAO.update(con, p);
                            
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
            
            double nilai = 0;
            List<PengirimanDetail> listKirim = PengirimanDetailDAO.getAllByNoPenjualanAndNoUrutAndStatus(
                con, penjualan.getNoPenjualan(), "%", "true");
            for(PengirimanDetail d : listKirim){
                nilai = nilai + d.getNilai()*d.getQty();
            }
            List<ReturDetail> listRetur = ReturDetailDAO.getAllByNoPenjualanAndNoUrutAndStatus(
                con, penjualan.getNoPenjualan(), "%", "true");
            for(ReturDetail d : listRetur){
                nilai = nilai - d.getNilai()*d.getQty();
            }
            List<BebanPenjualan> listBebanPenjualan = BebanPenjualanDAO.getAllByNoPenjualanAndNoUrut(
                con, penjualan.getNoPenjualan(), "%", "true");
            for(BebanPenjualan b : listBebanPenjualan){
                nilai = nilai + b.getJumlahRp();
            }
            KeuanganDAO.delete(con, "Pekerjaan Proyek/Barang Jadi", penjualan.getNamaProyek(), "Penjualan - "+penjualan.getNoPenjualan());
            KeuanganDAO.delete(con, "Untung/Rugi", "HPP", "Penjualan - "+penjualan.getNoPenjualan());
            
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
    
    public static String saveNewPembelian(Connection con, PembelianHead p){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            PembelianHeadDAO.insert(con, p);
            Supplier s = SupplierDAO.get(con, p.getKodeSupplier());
            s.setHutang(s.getHutang()+p.getSisaPembayaran());
            SupplierDAO.update(con, s);

            Hutang hutang = new Hutang();
            hutang.setNoHutang(HutangDAO.getId(con));
            hutang.setTglHutang(tglSql.format(new Date()));
            hutang.setKategori("Hutang Pembelian");
            hutang.setKeterangan(p.getNoPembelian());
            hutang.setKategoriKeuangan("Pembelian");
            hutang.setJumlahHutang(p.getTotalPembelian());
            hutang.setPembayaran(p.getPembayaran());
            hutang.setSisaHutang(p.getSisaPembayaran());
            hutang.setJatuhTempo("2000-01-01");
            hutang.setKodeUser(sistem.getUser().getUsername());
            hutang.setStatus("open");
            HutangDAO.insert(con, hutang);
            
            Keuangan h = new Keuangan();
            h.setNoKeuangan(KeuanganDAO.getId(con));
            h.setTglKeuangan(tglSql.format(new Date()));
            h.setTipeKeuangan("Hutang");
            h.setKategori("Hutang Pembelian");
            h.setKeterangan("Pembelian - "+p.getNoPembelian());
            h.setJumlahRp(p.getSisaPembayaran());
            h.setKodeUser(sistem.getUser().getUsername());
            KeuanganDAO.insert(con, h);
            
            List<PembelianDetail> stokBarang = new ArrayList<>();
            for(PembelianDetail d : p.getAllDetail()){
                PembelianDetailDAO.insert(con, d);
                
                Boolean inStok = false;
                for(PembelianDetail detail : stokBarang){
                    if(d.getKodeBarang().equals(detail.getKodeBarang())){
                        detail.setQty(detail.getQty()+d.getQty());
                        detail.setHarga((detail.getHarga()*detail.getQty()+d.getHarga()*d.getQty())/
                                (detail.getQty()+d.getQty()));
                        inStok = true;
                    }
                }
                if(!inStok)
                    stokBarang.add(d);
            }
            for(PembelianDetail d : stokBarang){
                StokBarang stok = StokBarangDAO.get(con, tglBarang.format(new Date()), d.getKodeBarang());
                if(stok==null){
                    StokBarang newStok = new StokBarang();
                    newStok.setTanggal(tglBarang.format(new Date()));
                    newStok.setKodeBarang(d.getKodeBarang());
                    newStok.setStokAwal(0);
                    newStok.setStokMasuk(d.getQty());
                    newStok.setStokKeluar(0);
                    newStok.setStokAkhir(d.getQty());
                    StokBarangDAO.insert(con, newStok);
                }else{
                    if(stok.getTanggal().equals(tglBarang.format(new Date()))){
                        stok.setStokMasuk(stok.getStokMasuk()+d.getQty());
                        stok.setStokAkhir(stok.getStokAkhir()+d.getQty());
                        StokBarangDAO.update(con, stok);
                    }else{
                        StokBarang newStok = new StokBarang();
                        newStok.setTanggal(tglBarang.format(new Date()));
                        newStok.setKodeBarang(d.getKodeBarang());
                        newStok.setStokAwal(stok.getStokAkhir());
                        newStok.setStokMasuk(d.getQty());
                        newStok.setStokKeluar(0);
                        newStok.setStokAkhir(stok.getStokAkhir()+d.getQty());
                        StokBarangDAO.insert(con, newStok);
                    }
                }
                LogBarang lb = LogBarangDAO.getLastByBarang(con, d.getKodeBarang());
                double nilai = d.getHarga() * d.getQty();

                LogBarang log = new LogBarang();
                log.setTanggal(tglSql.format(new Date()));
                log.setKodeBarang(d.getKodeBarang());
                log.setKategori("Pembelian");
                log.setKeterangan(p.getNoPembelian());
                log.setStokAwal(lb.getStokAkhir());
                log.setNilaiAwal(lb.getNilaiAkhir());
                log.setStokMasuk(d.getQty());
                log.setNilaiMasuk(nilai);
                log.setStokKeluar(0);
                log.setNilaiKeluar(0);
                log.setStokAkhir(lb.getStokAkhir()+d.getQty());
                log.setNilaiAkhir(lb.getNilaiAkhir()+nilai);
                LogBarangDAO.insert(con, log);

                Keuangan keuStok = new Keuangan();
                keuStok.setNoKeuangan(h.getNoKeuangan());
                keuStok.setTglKeuangan(tglSql.format(new Date()));
                keuStok.setTipeKeuangan("Stok Barang");
                keuStok.setKategori(d.getKodeBarang());
                keuStok.setKeterangan("Pembelian - "+p.getNoPembelian());
                keuStok.setJumlahRp(nilai);
                keuStok.setKodeUser(sistem.getUser().getUsername());
                KeuanganDAO.insert(con, keuStok);
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
    public static String saveBatalPembelian(Connection con, PembelianHead pembelian){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            PembelianHeadDAO.update(con, pembelian);
            
            Supplier s = SupplierDAO.get(con, pembelian.getKodeSupplier());
            s.setHutang(s.getHutang()-pembelian.getSisaPembayaran());
            SupplierDAO.update(con, s);
            
            Hutang hutang = HutangDAO.getByKategoriAndKeterangan(con, "Hutang Pembelian",pembelian.getNoPembelian());
            hutang.setStatus("false");
            HutangDAO.update(con, hutang);
                        
            if(pembelian.getPembayaran()>0){
                status = "Tidak dapat dibatalkan, karena sudah ada pembayaran";
            }
            KeuanganDAO.delete(con, "Hutang", "Hutang Pembelian", "Pembelian - "+pembelian.getNoPembelian());
            
            pembelian.setAllDetail(PembelianDetailDAO.getAllByNoPembelian(con, pembelian.getNoPembelian()));
            List<PembelianDetail> stokBarang = new ArrayList<>();
            for(PembelianDetail d : pembelian.getAllDetail()){
                Boolean inStok = false;
                for(PembelianDetail detail : stokBarang){
                    if(d.getKodeBarang().equals(detail.getKodeBarang())){
                        detail.setQty(detail.getQty()+d.getQty());
                        detail.setHarga((detail.getHarga()*detail.getQty()+d.getHarga()*d.getQty())/
                                        (detail.getQty()+d.getQty()));
                        inStok = true;
                    }
                }
                if(!inStok)
                    stokBarang.add(d);
            }
            for(PembelianDetail d : stokBarang){
                StokBarang stokAkhir = StokBarangDAO.get(con, tglBarang.format(new Date()), d.getKodeBarang());
                if(stokAkhir==null)
                    status = "Stok barang "+d.getNamaBarang()+" tidak ditemukan";
                else{
                    if(stokAkhir.getStokAkhir()<d.getQty()){
                        status = "Stok barang "+d.getNamaBarang()+" tidak mencukupi";
                    }else{
                        StokBarang stok = StokBarangDAO.get(con, tglBarang.format(tglSql.parse(pembelian.getTglPembelian())), d.getKodeBarang());
                        stok.setStokMasuk(stok.getStokMasuk()-d.getQty());
                        stok.setStokAkhir(stok.getStokAkhir()-d.getQty());
                        StokBarangDAO.update(con, stok);
                        resetStokBarang(con, d.getKodeBarang(), tglBarang.format(tglSql.parse(pembelian.getTglPembelian())));
                    
                        LogBarangDAO.delete(con, d.getKodeBarang(), "Pembelian", pembelian.getNoPembelian());
                        resetLogBarang(con, pembelian.getTglPembelian(), d.getKodeBarang());

                        KeuanganDAO.delete(con, "Stok Barang", d.getKodeBarang(), "Pembelian - "+pembelian.getNoPembelian());
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
    
    public static String saveNewReturPembelian(Connection con, ReturPembelianHead retur ){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            ReturPembelianHeadDAO.insert(con, retur);
            
            Keuangan keu = new Keuangan();
            keu.setNoKeuangan(KeuanganDAO.getId(con));
            keu.setTglKeuangan(tglSql.format(new Date()));
            keu.setTipeKeuangan(retur.getTipeKeuangan());
            keu.setKategori("Retur Pembelian");
            keu.setKeterangan("Retur Pembelian - "+retur.getNoRetur());
            keu.setJumlahRp(retur.getTotalRetur());
            keu.setKodeUser(sistem.getUser().getUsername());
            KeuanganDAO.insert(con, keu);
            
            List<ReturPembelianDetail> stokBarang = new ArrayList<>();
            for(ReturPembelianDetail d : retur.getAllDetail()){
                ReturPembelianDetailDAO.insert(con, d);
                Boolean inStok = false;
                for(ReturPembelianDetail detail : stokBarang){
                    if(d.getKodeBarang().equals(detail.getKodeBarang())){
                        detail.setQty(detail.getQty()+d.getQty());
                        detail.setHarga((detail.getHarga()*detail.getQty()+d.getHarga()*d.getQty())/
                                (detail.getQty()+d.getQty()));
                        inStok = true;
                    }
                }
                if(!inStok)
                    stokBarang.add(d);
            }
            for(ReturPembelianDetail d : stokBarang){
                StokBarang stok = StokBarangDAO.get(con, tglBarang.format(new Date()), d.getKodeBarang());
                if(stok==null)
                    status = "Stok barang "+d.getNamaBarang()+" tidak ditemukan";
                else{
                    if(stok.getStokAkhir()<d.getQty()){
                        status = "Stok barang "+d.getNamaBarang()+" tidak mencukupi";
                    }else{
                        if(stok.getTanggal().equals(tglBarang.format(new Date()))){
                            stok.setStokKeluar(stok.getStokKeluar()+d.getQty());
                            stok.setStokAkhir(stok.getStokAkhir()-d.getQty());
                            StokBarangDAO.update(con, stok);
                        }else{
                            StokBarang newStok = new StokBarang();
                            newStok.setTanggal(tglBarang.format(new Date()));
                            newStok.setKodeBarang(d.getKodeBarang());
                            newStok.setStokAwal(stok.getStokAkhir());
                            newStok.setStokMasuk(0);
                            newStok.setStokKeluar(d.getQty());
                            newStok.setStokAkhir(stok.getStokAkhir()-d.getQty());
                            StokBarangDAO.insert(con, newStok);
                        }
                    }
                }
                
                LogBarang lb = LogBarangDAO.getLastByBarang(con, d.getKodeBarang());
                double nilai = d.getHarga() * d.getQty();

                LogBarang log = new LogBarang();
                log.setTanggal(tglSql.format(new Date()));
                log.setKodeBarang(d.getKodeBarang());
                log.setKategori("Retur Pembelian");
                log.setKeterangan(retur.getNoRetur());
                log.setStokAwal(lb.getStokAkhir());
                log.setNilaiAwal(lb.getNilaiAkhir());
                log.setStokMasuk(0);
                log.setNilaiMasuk(0);
                log.setStokKeluar(d.getQty());
                log.setNilaiKeluar(nilai);
                log.setStokAkhir(lb.getStokAkhir()-d.getQty());
                log.setNilaiAkhir(lb.getNilaiAkhir()-nilai);
                LogBarangDAO.insert(con, log);


                Keuangan keuStok = new Keuangan();
                keuStok.setNoKeuangan(keu.getNoKeuangan());
                keuStok.setTglKeuangan(tglSql.format(new Date()));
                keuStok.setTipeKeuangan("Stok Barang");
                keuStok.setKategori(d.getKodeBarang());
                keuStok.setKeterangan("Retur Pembelian - "+retur.getNoRetur());
                keuStok.setJumlahRp(-nilai);
                keuStok.setKodeUser(sistem.getUser().getUsername());
                KeuanganDAO.insert(con, keuStok);
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
    public static String saveBatalReturPembelian(Connection con , ReturPembelianHead retur ){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            ReturPembelianHeadDAO.update(con, retur);
            
            KeuanganDAO.delete(con, retur.getTipeKeuangan(), "Retur Pembelian", "Retur Pembelian - "+retur.getNoRetur());
            
            retur.setAllDetail(ReturPembelianDetailDAO.getAllByNoRetur(con, retur.getNoRetur()));
            List<ReturPembelianDetail> stokBarang = new ArrayList<>();
            for(ReturPembelianDetail d : retur.getAllDetail()){
                Boolean inStok = false;
                for(ReturPembelianDetail detail : stokBarang){
                    if(d.getKodeBarang().equals(detail.getKodeBarang())){
                        detail.setQty(detail.getQty()+d.getQty());
                        detail.setHarga((detail.getHarga()*detail.getQty()+d.getHarga()*d.getQty())/
                                (detail.getQty()+d.getQty()));
                        inStok = true;
                    }
                }
                if(!inStok)
                    stokBarang.add(d);
            }
            for(ReturPembelianDetail d : stokBarang){
                StokBarang stokAkhir = StokBarangDAO.get(con, tglBarang.format(new Date()), d.getKodeBarang());
                if(stokAkhir==null)
                    status = "Stok barang "+d.getNamaBarang()+" tidak ditemukan";
                else{
                    if(stokAkhir.getStokAkhir()<d.getQty()){
                        status = "Stok barang "+d.getNamaBarang()+" tidak mencukupi";
                    }else{
                        StokBarang stok = StokBarangDAO.get(con, tglBarang.format(tglSql.parse(retur.getTglRetur())), d.getKodeBarang());
                        stok.setStokKeluar(stok.getStokKeluar()-d.getQty());
                        stok.setStokAkhir(stok.getStokAkhir()+d.getQty());
                        StokBarangDAO.update(con, stok);
                        resetStokBarang(con, d.getKodeBarang(), tglBarang.format(tglSql.parse(retur.getTglRetur())));

                        LogBarangDAO.delete(con, d.getKodeBarang(), "Retur Pembelian", retur.getNoRetur());
                        resetLogBarang(con, retur.getTglRetur(), d.getKodeBarang());

                        KeuanganDAO.delete(con, "Stok Barang", d.getKodeBarang(), "Retur Pembelian - "+retur.getNoRetur());
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
            try{
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static void resetLogBarang(Connection con, String tglPenjualan, String kodeBarang)throws Exception{
        List<LogBarang> listBarang = LogBarangDAO.getAllAfterDateAndBarang(
                con, tglPenjualan, kodeBarang);
        LogBarang logBarang = LogBarangDAO.getLastBeforeDateAndBarang(
                con, tglPenjualan, kodeBarang);
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
    public static void resetStokBarang(Connection con, String kodeBarang, String tanggal)throws Exception{
        List<StokBarang> listStokBarang = StokBarangDAO.getAllByTanggalAndBarang(
                con, tanggal, tglBarang.format(new Date()), kodeBarang);
        listStokBarang.sort(Comparator.comparing(StokBarang::getTanggal));
        double stok = listStokBarang.get(0).getStokAwal();
        for(StokBarang s : listStokBarang){
            s.setStokAwal(stok);
            stok = stok + s.getStokMasuk() - s.getStokKeluar();
            s.setStokAkhir(stok);
            StokBarangDAO.update(con, s);
        }
    }
    
}
