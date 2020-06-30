/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.Service;

import com.excellentsystem.jagobangunpersadafx.DAO.AsetTetapDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.CustomerDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.HutangDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.KPRDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.KaryawanDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.KategoriHutangDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.KategoriPiutangDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.KategoriPropertyDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.KategoriTransaksiDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.KeuanganDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.OtoritasDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.OtoritasKeuanganDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PembangunanDetailDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PembangunanHeadDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PembayaranDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PembelianTanahDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PemecahanPropertyDetailDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PemecahanPropertyHeadDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PenggabunganPropertyDetailDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PenggabunganPropertyHeadDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PiutangDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PropertyDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.RAPDetailDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.RAPDetailPropertyDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.RAPHeadDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.RAPRealisasiDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.SAPDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.SDPDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.SKLDetailDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.SKLHeadDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.SKLManualDetailDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.SKLManualHeadDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.SPPDetailDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.SPPHeadDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.STJDetailDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.STJHeadDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.TerimaPembayaranDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.TipeKeuanganDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.TukangDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.UserDAO;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.sistem;
import static com.excellentsystem.jagobangunpersadafx.Main.tglBarang;
import static com.excellentsystem.jagobangunpersadafx.Main.tglSql;
import com.excellentsystem.jagobangunpersadafx.Model.AsetTetap;
import com.excellentsystem.jagobangunpersadafx.Model.Customer;
import com.excellentsystem.jagobangunpersadafx.Model.Hutang;
import com.excellentsystem.jagobangunpersadafx.Model.KPR;
import com.excellentsystem.jagobangunpersadafx.Model.Karyawan;
import com.excellentsystem.jagobangunpersadafx.Model.KategoriHutang;
import com.excellentsystem.jagobangunpersadafx.Model.KategoriPiutang;
import com.excellentsystem.jagobangunpersadafx.Model.KategoriProperty;
import com.excellentsystem.jagobangunpersadafx.Model.KategoriTransaksi;
import com.excellentsystem.jagobangunpersadafx.Model.Keuangan;
import com.excellentsystem.jagobangunpersadafx.Model.Otoritas;
import com.excellentsystem.jagobangunpersadafx.Model.OtoritasKeuangan;
import com.excellentsystem.jagobangunpersadafx.Model.PembangunanDetail;
import com.excellentsystem.jagobangunpersadafx.Model.PembangunanHead;
import com.excellentsystem.jagobangunpersadafx.Model.Pembayaran;
import com.excellentsystem.jagobangunpersadafx.Model.PembelianTanah;
import com.excellentsystem.jagobangunpersadafx.Model.PemecahanPropertyDetail;
import com.excellentsystem.jagobangunpersadafx.Model.PemecahanPropertyHead;
import com.excellentsystem.jagobangunpersadafx.Model.PenggabunganPropertyDetail;
import com.excellentsystem.jagobangunpersadafx.Model.PenggabunganPropertyHead;
import com.excellentsystem.jagobangunpersadafx.Model.Piutang;
import com.excellentsystem.jagobangunpersadafx.Model.Property;
import com.excellentsystem.jagobangunpersadafx.Model.RAPDetail;
import com.excellentsystem.jagobangunpersadafx.Model.RAPDetailProperty;
import com.excellentsystem.jagobangunpersadafx.Model.RAPHead;
import com.excellentsystem.jagobangunpersadafx.Model.RAPRealisasi;
import com.excellentsystem.jagobangunpersadafx.Model.SAP;
import com.excellentsystem.jagobangunpersadafx.Model.SDP;
import com.excellentsystem.jagobangunpersadafx.Model.SKLDetail;
import com.excellentsystem.jagobangunpersadafx.Model.SKLHead;
import com.excellentsystem.jagobangunpersadafx.Model.SKLManualDetail;
import com.excellentsystem.jagobangunpersadafx.Model.SKLManualHead;
import com.excellentsystem.jagobangunpersadafx.Model.SPPDetail;
import com.excellentsystem.jagobangunpersadafx.Model.SPPHead;
import com.excellentsystem.jagobangunpersadafx.Model.STJDetail;
import com.excellentsystem.jagobangunpersadafx.Model.STJHead;
import com.excellentsystem.jagobangunpersadafx.Model.TerimaPembayaran;
import com.excellentsystem.jagobangunpersadafx.Model.TipeKeuangan;
import com.excellentsystem.jagobangunpersadafx.Model.Tukang;
import com.excellentsystem.jagobangunpersadafx.Model.User;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;

/**
 *
 * @author Xtreme
 */
public class Service {
    
    private static void insertKeuangan(Connection con, String noKeuangan, String tanggal, String tipeKeuangan, 
            String kategori, String kodeProperty, String deskripsi, double jumlahRp, String kodeUser, 
            String tglInput, String status, String tglBatal, String userBatal)throws Exception{
        Keuangan k = new Keuangan();
        k.setNoKeuangan(noKeuangan);
        k.setTglKeuangan(tanggal);
        k.setTipeKeuangan(tipeKeuangan);
        k.setKategori(kategori);
        k.setKodeProperty(kodeProperty);
        k.setDeskripsi(deskripsi);
        k.setJumlahRp(jumlahRp);
        k.setKodeUser(kodeUser);
        k.setTglInput(tglInput);
        k.setStatus(status);
        k.setTglBatal(tglBatal);
        k.setUserBatal(userBatal);
        KeuanganDAO.insert(con, k);
    }
    public static void setPenyusutanAset(Connection con)throws Exception{
        LocalDate now = LocalDate.now();
        List<Keuangan> allAset = KeuanganDAO.getAllByTipeKeuanganAndDate(con, "ASET TETAP", "", now.toString());
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
                                if(k.getDeskripsi().equals("Penyusutan Aset Tetap Ke-"+i+" ("+aset.getNoAset()+")"))
                                    status = false;
                            }
                            if(status){
                                String noKeuangan = KeuanganDAO.getIdByDate(con, tglBarang.parse(tglSusut.toString()));
                                insertKeuangan(con, noKeuangan, tglSusut.toString(), "ASET TETAP", aset.getKategori(), "", 
                                        "Penyusutan Aset Tetap Ke-"+i+" ("+aset.getNoAset()+")", -penyusutanPerbulan, "System", 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");
                                insertKeuangan(con, noKeuangan, tglSusut.toString(), "BEBAN", "Beban Penyusutan Aset Tetap", "", 
                                        "Penyusutan Aset Tetap Ke-"+i+" ("+aset.getNoAset()+")", penyusutanPerbulan, "System", 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");
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
    
    public static String newUser(Connection con , User user){
        try{
            con.setAutoCommit(false);
            
            UserDAO.insert(con, user);
            for(Otoritas temp : user.getOtoritas()){
                OtoritasDAO.insert(con, temp);
            }
            for(OtoritasKeuangan temp : user.getOtoritasKeuangan()){
                OtoritasKeuanganDAO.insert(con, temp);
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
    public static String updateUser(Connection con, User user){
        try{
            con.setAutoCommit(false);
            
            UserDAO.update(con, user);
            OtoritasDAO.delete(con, user);
            for(Otoritas o : user.getOtoritas()){
                OtoritasDAO.insert(con, o);
            }
            OtoritasKeuanganDAO.deleteAllByUsername(con, user.getUsername());
            for(OtoritasKeuangan o : user.getOtoritasKeuangan()){
                OtoritasKeuanganDAO.insert(con, o);
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
            
            UserDAO.delete(con, user);
            OtoritasDAO.delete(con, user);
            OtoritasKeuanganDAO.deleteAllByUsername(con, user.getUsername());
            
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
    
    public static String newKategoriKeuangan(Connection con, TipeKeuangan t)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            TipeKeuanganDAO.insert(con, t);
            for(User u : UserDAO.getAll(con)){
                OtoritasKeuangan o = new OtoritasKeuangan();
                o.setUsername(u.getUsername());
                o.setKodeKeuangan(t.getKodeKeuangan());
                o.setStatus(false);
                OtoritasKeuanganDAO.insert(con, o);
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
    public static String deleteKategoriKeuangan(Connection con, TipeKeuangan t)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            double saldo = KeuanganDAO.getSaldoAkhirByDateAndTipeKeuangan(con, tglBarang.format(new Date()), t.getKodeKeuangan());
            if(saldo!=0)
                status = "Tidak dapat dihapus,karena saldo "+t.getKodeKeuangan()+" masih ada";
            else{
                TipeKeuanganDAO.delete(con, t);
                OtoritasKeuanganDAO.deleteAllByKodeKeuangan(con, t.getKodeKeuangan());
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
    
    public static String newKategoriProperty(Connection con, KategoriProperty k)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            KategoriPropertyDAO.insert(con, k);

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
    public static String deleteKategoriProperty(Connection con, KategoriProperty k)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            KategoriPropertyDAO.delete(con, k);

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
    
    public static String newCustomer(Connection con , Customer customer){
        try{
            con.setAutoCommit(false);
            
            CustomerDAO.insert(con, customer);
            
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
    public static String updateCustomer(Connection con, Customer customer){
        try{
            con.setAutoCommit(false);
            
            CustomerDAO.update(con, customer);
            
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
    
    public static String newKaryawan(Connection con , Karyawan karyawan){
        try{
            con.setAutoCommit(false);
            
            KaryawanDAO.insert(con, karyawan);
            
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
    public static String updateKaryawan(Connection con, Karyawan karyawan){
        try{
            con.setAutoCommit(false);
            
            KaryawanDAO.update(con, karyawan);
            
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
    
    public static String newTukang(Connection con , Tukang tukang){
        try{
            con.setAutoCommit(false);
            
            TukangDAO.insert(con, tukang);
            
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
    public static String updateTukang(Connection con, Tukang tukang){
        try{
            con.setAutoCommit(false);
            
            TukangDAO.update(con, tukang);
            
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
    
    public static String updateProperty(Connection con, Property property){
        try{
            con.setAutoCommit(false);
            
            PropertyDAO.update(con, property);
            
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
    
    public static String savePemecahanProperty(Connection con , PemecahanPropertyHead head){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            String noKeuangan = KeuanganDAO.getIdByDate(con, new Date());
            PemecahanPropertyHeadDAO.insert(con, head);
            
            Property p = head.getProperty();
            p.setNamaProperty(head.getNamaProperty());
            p.setNilaiProperty(0);
            p.setStatus("Mapped");
            PropertyDAO.update(con, p);
            
            insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), "ASET LANCAR", "Tanah & Bangunan", p.getKodeProperty(),  
                    "Pemecahan Property - "+head.getNoPemecahan(), -head.getNilaiProperty(), sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");
            
            for(PemecahanPropertyDetail detail : head.getAllDetail()){
                String kodeProperty = PropertyDAO.getId(con);
                detail.setKodeProperty(kodeProperty); 
                PemecahanPropertyDetailDAO.insert(con, detail);
                
                detail.getProperty().setKodeProperty(kodeProperty);
                PropertyDAO.insert(con, detail.getProperty());
                
                insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), "ASET LANCAR", "Tanah & Bangunan", detail.getProperty().getKodeProperty(), 
                        "Pemecahan Property - "+head.getNoPemecahan(), detail.getProperty().getNilaiProperty(), sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");
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
    public static String savePenggabunganProperty(Connection con , PenggabunganPropertyHead head){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            String noKeuangan = KeuanganDAO.getIdByDate(con, new Date());
            String kodeProperty = PropertyDAO.getId(con);
            
            head.setKodeProperty(kodeProperty);
            PenggabunganPropertyHeadDAO.insert(con, head);
            
            head.getProperty().setKodeProperty(kodeProperty);
            PropertyDAO.insert(con, head.getProperty());
            
            insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), "ASET LANCAR", "Tanah & Bangunan",  head.getProperty().getKodeProperty(),
                    "Penggabungan Property - "+head.getNoPenggabungan(), head.getProperty().getNilaiProperty(), sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");
                        
            for(PenggabunganPropertyDetail d : head.getAllDetail()){
                PenggabunganPropertyDetailDAO.insert(con, d);
                
                d.getProperty().setStatus("Combined");
                PropertyDAO.update(con, d.getProperty());
                
                insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), "ASET LANCAR", "Tanah & Bangunan",  d.getProperty().getKodeProperty(),
                        "Penggabungan Property - "+head.getNoPenggabungan(), -d.getProperty().getNilaiProperty(), sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");
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
    
    public static String savePembelianTanah(Connection con, PembelianTanah pembelian, double jumlahBayar){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            String noKeuangan = KeuanganDAO.getIdByDate(con, new Date());
            PembelianTanahDAO.insert(con, pembelian);
            PropertyDAO.insert(con, pembelian.getProperty());
            
            insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), "ASET LANCAR", "Tanah & Bangunan", pembelian.getProperty().getKodeProperty(),
                    "Pembelian Tanah - "+pembelian.getNoPembelian(), pembelian.getHargaBeli(), sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");
            
            if(jumlahBayar!=0){
                insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), pembelian.getTipeKeuangan(), "Pembelian Tanah", pembelian.getProperty().getKodeProperty(),
                        "Pembelian Tanah - "+pembelian.getNoPembelian(), -jumlahBayar, sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");
            }
            
            if(pembelian.getHargaBeli()>jumlahBayar){
                Hutang h = new Hutang();
                h.setNoHutang(HutangDAO.getId(con));
                h.setTglHutang(tglSql.format(new Date()));
                h.setKategori("Hutang Pembelian Tanah");
                h.setKeterangan(pembelian.getNoPembelian());
                h.setJumlahHutang(pembelian.getHargaBeli()-jumlahBayar);
                h.setPembayaran(0);
                h.setSisaHutang(pembelian.getHargaBeli()-jumlahBayar);
                h.setJatuhTempo("2000-01-01");
                h.setStatus("open");
                HutangDAO.insert(con, h);
                
                insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), "HUTANG", "Hutang Pembelian Tanah", pembelian.getKodeProperty(),
                        "Pembelian Tanah - "+pembelian.getNoPembelian(), pembelian.getHargaBeli()-jumlahBayar, sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");
            }
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            e.printStackTrace();
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }
    public static String batalPembelianTanah(Connection con, PembelianTanah pembelian){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            PembelianTanahDAO.update(con, pembelian);
            PropertyDAO.delete(con, pembelian.getKodeProperty());
            
            String noKeuangan = KeuanganDAO.get(con, "ASET LANCAR", "Tanah & Bangunan", pembelian.getProperty().getKodeProperty(), 
                    "Pembelian Tanah - "+pembelian.getNoPembelian()).getNoKeuangan();
            KeuanganDAO.deleteAllByNoKeuangan(con, noKeuangan);
            
            Hutang h = HutangDAO.getByKategoriAndKeteranganAndStatus(
                    con, "Hutang Pembelian Tanah", pembelian.getNoPembelian(), "%");
            if(h!=null){
                h.setStatus("false");
                HutangDAO.update(con, h);

                List<Pembayaran> allPembayaran = PembayaranDAO.getAllByNoHutangAndStatus(con, h.getNoHutang(), "true");
                if(!allPembayaran.isEmpty())
                    status = "Tidak dapat dibatalkan, karena hutang pembelian tanah sudah ada pembayaran";
            }
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            e.printStackTrace();
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }
    
    public static String savePembangunan(Connection con, PembangunanHead p){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            String noKeuangan = KeuanganDAO.getIdByDate(con, new Date());
            PembangunanHeadDAO.insert(con, p);
            
            for(PembangunanDetail d : p.getAllDetail()){
                PembangunanDetailDAO.insert(con, d);
                
                Property prop = PropertyDAO.get(con, d.getKodeProperty());
                prop.setNilaiProperty(prop.getNilaiProperty()+d.getBiaya());
                PropertyDAO.update(con, prop);
                
                insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), "ASET LANCAR", "Tanah & Bangunan", d.getKodeProperty(),
                        "Pembangunan - "+p.getNoPembangunan(), d.getBiaya(), sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");

                insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), p.getTipeKeuangan(), "Pembangunan", d.getKodeProperty(),
                        "Pembangunan - "+p.getNoPembangunan(), -d.getBiaya(), sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");
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
    public static String batalPembangunan(Connection con, PembangunanHead p){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            PembangunanHeadDAO.update(con, p);
            
            for(PembangunanDetail d : p.getAllDetail()){
                Property prop = PropertyDAO.get(con, d.getKodeProperty());
                prop.setNilaiProperty(prop.getNilaiProperty()-d.getBiaya());
                PropertyDAO.update(con, prop);
            }
            String noKeuangan = KeuanganDAO.get(con, "ASET LANCAR", "Tanah & Bangunan", p.getAllDetail().get(0).getKodeProperty(), 
                    "Pembangunan - "+p.getNoPembangunan()).getNoKeuangan();
            KeuanganDAO.deleteAllByNoKeuangan(con, noKeuangan);
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            e.printStackTrace();
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }
    
    public static String saveRap(Connection con, RAPHead p){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            RAPHeadDAO.insert(con, p);
            
            int noUrut=1;
            for(RAPDetail d : p.getListRapDetail()){
                d.setNoRap(p.getNoRap());
                d.setNoUrut(noUrut);
                RAPDetailDAO.insert(con, d);
                noUrut++;
            }
            noUrut=1;
            for(RAPDetailProperty d : p.getListRapPropertyDetail()){
                if(d.isStatus()){
                    d.setNoRap(p.getNoRap());
                    d.setNoUrut(noUrut);
                    RAPDetailPropertyDAO.insert(con, d);
                    noUrut++;
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
    public static String saveEditRap(Connection con, RAPHead p){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            RAPHeadDAO.update(con, p);
            RAPDetailDAO.delete(con, p.getNoRap());
            RAPDetailPropertyDAO.delete(con, p.getNoRap());
            
            int noUrut=1;
            for(RAPDetail d : p.getListRapDetail()){
                d.setNoRap(p.getNoRap());
                d.setNoUrut(noUrut);
                RAPDetailDAO.insert(con, d);
                noUrut++;
            }
            noUrut=1;
            for(RAPDetailProperty d : p.getListRapPropertyDetail()){
                if(d.isStatus()){
                    d.setNoRap(p.getNoRap());
                    d.setNoUrut(noUrut);
                    RAPDetailPropertyDAO.insert(con, d);
                    noUrut++;
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
    public static String saveUpdateRap(Connection con, RAPHead p){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            RAPHeadDAO.update(con, p);
            
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
    
    public static String saveRealisasi(Connection con, RAPRealisasi r, List<ImageView> listImage){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            RAPHead rapHead = RAPHeadDAO.get(con, r.getNoRap());
            rapHead.setListRapRealisasi(RAPRealisasiDAO.getAllByNoRapAndStatus(con, r.getNoRap(), "%"));
            rapHead.setListRapDetail(RAPDetailDAO.getAllByNoRap(con, r.getNoRap()));
            rapHead.setListRapPropertyDetail(RAPDetailPropertyDAO.getAllByNoRap(con, r.getNoRap()));
            
            int noUrut = 1;
            for(RAPRealisasi d : rapHead.getListRapRealisasi()){
                if(noUrut<=d.getNoUrut())
                    noUrut = d.getNoUrut()+1;
            }
            r.setNoUrut(noUrut);
            RAPRealisasiDAO.insert(con, r);
            
            String noKeuangan = KeuanganDAO.getIdByDate(con, new Date());
            
            for(RAPDetailProperty d : rapHead.getListRapPropertyDetail()){
                double nilai = r.getJumlahRp()*d.getPersentase()/100;
                
                Property prop = PropertyDAO.get(con, d.getKodeProperty());
                prop.setNilaiProperty(prop.getNilaiProperty()+nilai);
                PropertyDAO.update(con, prop);
                
                insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), "ASET LANCAR", "Tanah & Bangunan", d.getKodeProperty(),
                        "Realisasi Proyek - "+r.getNoRap()+" - "+r.getNoUrut(), nilai, sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");

                insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), r.getTipeKeuangan(), "Realisasi Proyek", d.getKodeProperty(),
                        "Realisasi Proyek - "+r.getNoRap()+" - "+r.getNoUrut(), -nilai, sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");
            }
            
            int noUrutImage = 1;
            for(ImageView i : listImage){
                File tempFile = new File(r.getNoRap()+"-"+r.getNoUrut()+" - "+noUrutImage+".png");
                BufferedImage bImage = SwingFXUtils.fromFXImage(i.getImage(), null);
                ImageIO.write(bImage, "png", tempFile);
                
                StorageOptions storageOptions = StorageOptions.newBuilder().
                        setProjectId("auristeel-280420").
                        setCredentials(GoogleCredentials.fromStream(Main.class.getResourceAsStream("Resource/credentials.json"))).build();
                Storage storage = storageOptions.getService();
                
                BlobId blobId = BlobId.of("jagobangunpersada", tempFile.getName());
                BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
                storage.create(blobInfo, Files.readAllBytes(Paths.get(tempFile.getPath())));
                
                Files.deleteIfExists(tempFile.toPath()); 
                
                noUrutImage = noUrutImage + 1;
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
    public static String batalRealisasi(Connection con, RAPRealisasi r){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            RAPHead rapHead = RAPHeadDAO.get(con, r.getNoRap());
            rapHead.setListRapPropertyDetail(RAPDetailPropertyDAO.getAllByNoRap(con, r.getNoRap()));
            
            RAPRealisasiDAO.update(con, r);
            
            for(RAPDetailProperty d : rapHead.getListRapPropertyDetail()){
                double nilai = r.getJumlahRp()*d.getPersentase()/100;
                
                Property prop = PropertyDAO.get(con, d.getKodeProperty());
                prop.setNilaiProperty(prop.getNilaiProperty()-nilai);
                PropertyDAO.update(con, prop);
            }
            String noKeuangan = KeuanganDAO.get(con, "ASET LANCAR", "Tanah & Bangunan", rapHead.getListRapPropertyDetail().get(0).getKodeProperty(), 
                    "Realisasi Proyek - "+r.getNoRap()+" - "+r.getNoUrut()).getNoKeuangan();
            KeuanganDAO.deleteAllByNoKeuangan(con, noKeuangan);
            
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
    
    public static String saveTerimaTandaJadi(Connection con, STJHead stj){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            String noKeuangan = KeuanganDAO.getIdByDate(con, new Date());
            String noStj = STJHeadDAO.getId(con, stj.getProperty().getNamaProperty().substring(0, 3).toUpperCase());
            stj.setNoSTJ(noStj);
            stj.setTglSTJ(tglSql.format(new Date()));
            STJHeadDAO.insert(con, stj);
            
            for(STJDetail d : stj.getAllDetail()){
                d.setNoSTJ(noStj);
                STJDetailDAO.insert(con, d);
            }
            
            stj.getProperty().setStatus("Reserved");
            PropertyDAO.update(con, stj.getProperty());
            
            insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), stj.getTipeKeuangan(), "Terima Tanda Jadi", stj.getKodeProperty(),
                    "Terima Tanda Jadi - "+stj.getNoSTJ(), stj.getJumlahRp(), sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");

            insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), "HUTANG", "Terima Tanda Jadi", stj.getKodeProperty(),
                    "Terima Tanda Jadi - "+stj.getNoSTJ(), stj.getJumlahRp(), sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");
            
            Hutang h = new Hutang();
            h.setNoHutang(HutangDAO.getId(con));
            h.setTglHutang(tglSql.format(new Date()));
            h.setKategori("Terima Tanda Jadi");
            h.setKeterangan(stj.getNoSTJ());
            h.setJumlahHutang(stj.getJumlahRp());
            h.setPembayaran(0);
            h.setSisaHutang(stj.getJumlahRp());
            h.setJatuhTempo("2000-01-01");
            h.setStatus("open");
            HutangDAO.insert(con, h);
            
            
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
    public static String batalTerimaTandaJadi(Connection con, STJHead stj){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            STJHeadDAO.update(con, stj);
            
            stj.getProperty().setStatus("Available");
            PropertyDAO.update(con, stj.getProperty());
            
            String noKeuangan = KeuanganDAO.get(con, "HUTANG", "Terima Tanda Jadi", stj.getKodeProperty(), 
                    "Terima Tanda Jadi - "+stj.getNoSTJ()).getNoKeuangan();
            KeuanganDAO.deleteAllByNoKeuangan(con, noKeuangan);
                        
            Hutang h = HutangDAO.getByKategoriAndKeteranganAndStatus(con, "Terima Tanda Jadi", stj.getNoSTJ(),"open");
            h.setStatus("false");
            HutangDAO.update(con, h);
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            e.printStackTrace();
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }
    
    public static String saveTerimaDownPayment(Connection con, SDP sdp){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            String noKeuangan = KeuanganDAO.getIdByDate(con, new Date());
            String noSdp = SDPDAO.getId(con, sdp.getProperty().getNamaProperty().substring(0, 3).toUpperCase());
            sdp.setNoSDP(noSdp);
            sdp.setTglSDP(tglSql.format(new Date()));
            SDPDAO.insert(con, sdp);
            
            insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), sdp.getTipeKeuangan(), "Terima Down Payment", sdp.getKodeProperty(),
                    "Terima Down Payment - "+sdp.getNoSDP(), sdp.getJumlahRp(), sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");

            insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), "HUTANG", "Terima Down Payment", sdp.getKodeProperty(),
                    "Terima Down Payment - "+sdp.getNoSDP(), sdp.getJumlahRp(), sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");
                        
            Hutang h = new Hutang();
            h.setNoHutang(HutangDAO.getId(con));
            h.setTglHutang(tglSql.format(new Date()));
            h.setKategori("Terima Down Payment");
            h.setKeterangan(sdp.getNoSDP());
            h.setJumlahHutang(sdp.getJumlahRp());
            h.setPembayaran(0);
            h.setSisaHutang(sdp.getJumlahRp());
            h.setJatuhTempo("2000-01-01");
            h.setStatus("open");
            HutangDAO.insert(con, h);
            
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
    public static String batalTerimaDownPayment(Connection con, SDP sdp){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            SDPDAO.update(con, sdp);
            
            String noKeuangan = KeuanganDAO.get(con, "HUTANG", "Terima Down Payment", sdp.getKodeProperty(), 
                    "Terima Down Payment - "+sdp.getNoSDP()).getNoKeuangan();
            KeuanganDAO.deleteAllByNoKeuangan(con, noKeuangan);
                        
            Hutang h = HutangDAO.getByKategoriAndKeteranganAndStatus(con, "Terima Down Payment", sdp.getNoSDP(),"open");
            h.setStatus("false");
            HutangDAO.update(con, h);
            
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
    
    public static String savePelunasanDownPayment(Connection con, SKLHead skl){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            String noKeuangan = KeuanganDAO.getIdByDate(con, new Date());
            
            String noSKl = SKLHeadDAO.getId(con, skl.getProperty().getNamaProperty().substring(0, 3).toUpperCase());
            skl.setNoSKL(noSKl);
            skl.setTglSKL(tglSql.format(new Date()));
            SKLHeadDAO.insert(con, skl);
            
            skl.getProperty().setStatus("Sold");
            PropertyDAO.update(con, skl.getProperty());
            for(SKLDetail detail : skl.getAllDetail()){
                detail.setNoSKL(skl.getNoSKL());
                SKLDetailDAO.insert(con, detail);
            }
            Hutang hstj = HutangDAO.getByKategoriAndKeteranganAndStatus(con, "Terima Tanda Jadi", skl.getNoSTJ(),"open");
            hstj.setPembayaran(hstj.getJumlahHutang());
            hstj.setSisaHutang(0);
            hstj.setStatus("close");
            HutangDAO.update(con, hstj);
            
            Pembayaran pstj = new Pembayaran();
            pstj.setNoPembayaran(PembayaranDAO.getId(con));
            pstj.setTglPembayaran(tglSql.format(new Date()));
            pstj.setNoHutang(hstj.getNoHutang());
            pstj.setJumlahPembayaran(hstj.getJumlahHutang());
            pstj.setTipeKeuangan("Penjualan");
            pstj.setCatatan("");
            pstj.setKodeUser(sistem.getUser().getUsername());
            pstj.setTglBatal("2000-01-01 00:00:00");
            pstj.setUserBatal("");
            pstj.setStatus("true");
            PembayaranDAO.insert(con, pstj);
            
            insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), "HUTANG", "Terima Tanda Jadi", skl.getKodeProperty(),
                    "Penjualan - "+skl.getNoSKL(), -hstj.getJumlahHutang(), sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");
            
            List<SDP> allSDP = SDPDAO.getAllByKodeProperty(con, skl.getKodeProperty(), "true");
            double totaldp = 0;
            for(SDP sdp : allSDP){
                Hutang h = HutangDAO.getByKategoriAndKeteranganAndStatus(con, "Terima Down Payment", sdp.getNoSDP(),"open");
                h.setPembayaran(h.getJumlahHutang());
                h.setSisaHutang(0);
                h.setStatus("close");
                HutangDAO.update(con, h);
                
                Pembayaran psdp = new Pembayaran();
                psdp.setNoPembayaran(PembayaranDAO.getId(con));
                psdp.setTglPembayaran(tglSql.format(new Date()));
                psdp.setNoHutang(h.getNoHutang());
                psdp.setJumlahPembayaran(h.getJumlahHutang());
                psdp.setTipeKeuangan("Penjualan");
                psdp.setCatatan("");
                psdp.setKodeUser(sistem.getUser().getUsername());
                psdp.setTglBatal("2000-01-01 00:00:00");
                psdp.setUserBatal("");
                psdp.setStatus("true");
                PembayaranDAO.insert(con, psdp);
                
                totaldp = totaldp + h.getJumlahHutang();
            }
            insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), "HUTANG", "Terima Down Payment", skl.getKodeProperty(),
                    "Penjualan - "+skl.getNoSKL(), -totaldp, sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");
            
            insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), "PENJUALAN", "Penjualan", skl.getKodeProperty(),
                    "Penjualan - "+skl.getNoSKL(), skl.getProperty().getHargaJual()-skl.getProperty().getDiskon(), sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");

            insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), "HPP", "HPP", skl.getKodeProperty(),
                    "Penjualan - "+skl.getNoSKL(), skl.getProperty().getNilaiProperty(), sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");

            insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), "ASET LANCAR", "Tanah & Bangunan", skl.getKodeProperty(),
                    "Penjualan - "+skl.getNoSKL(), -skl.getProperty().getNilaiProperty(), sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");

            insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), "PIUTANG", "Piutang Penjualan", skl.getKodeProperty(),
                    "Penjualan - "+skl.getNoSKL(), skl.getSisaPelunasan(), sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");

            Piutang p = new Piutang();
            p.setNoPiutang(PiutangDAO.getId(con));
            p.setTglPiutang(skl.getTglSKL());
            p.setKategori("Piutang Penjualan");
            p.setKeterangan(skl.getNoSKL());
            p.setJumlahPiutang(skl.getSisaPelunasan());
            p.setPembayaran(0);
            p.setSisaPiutang(skl.getSisaPelunasan());
            p.setJatuhTempo("2000-01-01");
            p.setStatus("open");
            PiutangDAO.insert(con, p);
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            e.printStackTrace();
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }
    public static String batalPelunasanDownPayment(Connection con, SKLHead skl){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            SKLHeadDAO.update(con, skl);
            skl.getProperty().setStatus("Reserved");
            PropertyDAO.update(con, skl.getProperty());
            
            String noKeuangan = KeuanganDAO.get(con, "PENJUALAN", "Penjualan", skl.getKodeProperty(), 
                    "Penjualan - "+skl.getNoSKL()).getNoKeuangan();
            KeuanganDAO.deleteAllByNoKeuangan(con, noKeuangan);
            
            Hutang hstj = HutangDAO.getByKategoriAndKeteranganAndStatus(con, "Terima Tanda Jadi", skl.getNoSTJ(),"close");
            hstj.setPembayaran(0);
            hstj.setSisaHutang(hstj.getJumlahHutang());
            hstj.setStatus("open");
            HutangDAO.update(con, hstj);
            
            List<Pembayaran> listPstj = PembayaranDAO.getAllByNoHutangAndStatus(con, hstj.getNoHutang(), "true");
            for(Pembayaran p : listPstj){
                p.setStatus("false");
                p.setUserBatal(skl.getUserBatal());
                p.setTglBatal(skl.getTglBatal());
                PembayaranDAO.update(con, p);
            }
            
            List<SDP> allSDP = SDPDAO.getAllByKodeProperty(con, skl.getKodeProperty(), "true");
            double totaldp = 0;
            for(SDP sdp : allSDP){
                Hutang h = HutangDAO.getByKategoriAndKeteranganAndStatus(con, "Terima Down Payment", sdp.getNoSDP(),"close");
                h.setPembayaran(0);
                h.setSisaHutang(h.getJumlahHutang());
                h.setStatus("open");
                HutangDAO.update(con, h);
                
                List<Pembayaran> listPsdp = PembayaranDAO.getAllByNoHutangAndStatus(con, h.getNoHutang(), "true");
                for(Pembayaran p : listPsdp){
                    p.setStatus("false");
                    p.setUserBatal(skl.getUserBatal());
                    p.setTglBatal(skl.getTglBatal());
                    PembayaranDAO.update(con, p);
                }
                
                totaldp = totaldp + h.getJumlahHutang();
            }
            Piutang p = PiutangDAO.getByKategoriAndKeteranganAndStatus(con, "Piutang Penjualan", skl.getNoSKL(),"open");
            p.setPembayaran(skl.getSisaPelunasan());
            p.setSisaPiutang(0);
            p.setStatus("false");
            PiutangDAO.update(con, p);
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            e.printStackTrace();
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }
    
    public static String saveSKLManual(Connection con, SKLManualHead skl){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            SKLManualHeadDAO.insertSKL(con, skl);
            for(SKLManualDetail detail : skl.getAllDetail()){
                SKLManualDetailDAO.insertSKLDetail(con, detail);
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
    
    public static String saveTerimaPencairanKPR(Connection con, KPR kpr){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            String noKeuangan = KeuanganDAO.getIdByDate(con, new Date());
            String noKpr = KPRDAO.getId(con);
            kpr.setNoKPR(noKpr);
            kpr.setTglKPR(tglSql.format(new Date()));
            KPRDAO.insert(con, kpr);
            
            kpr.getProperty().setStatus("Sold - Full Paid");
            PropertyDAO.update(con, kpr.getProperty());
            
            insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), kpr.getTipeKeuangan(), "Pencairan KPR", kpr.getKodeProperty(),
                    "Pencairan KPR - "+kpr.getNoKPR(), kpr.getJumlahRp(), sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");
                        
            Piutang p = PiutangDAO.getByKategoriAndKeteranganAndStatus(con,  "Piutang Penjualan", kpr.getNoSKL(),"open");
            p.setPembayaran(p.getPembayaran()+kpr.getJumlahRp());
            p.setSisaPiutang(p.getSisaPiutang()-kpr.getJumlahRp());
            p.setStatus("close");
            PiutangDAO.update(con, p);
            
            TerimaPembayaran tp = new TerimaPembayaran();
            tp.setNoTerimaPembayaran(TerimaPembayaranDAO.getId(con));
            tp.setTglTerima(tglSql.format(new Date()));
            tp.setNoPiutang(p.getNoPiutang());
            tp.setJumlahPembayaran(p.getJumlahPiutang());
            tp.setTipeKeuangan(kpr.getTipeKeuangan());
            tp.setCatatan("");
            tp.setKodeUser(sistem.getUser().getUsername());
            tp.setTglBatal("2000-01-01 00:00:00");
            tp.setUserBatal("");
            tp.setStatus("true");
            TerimaPembayaranDAO.insert(con, tp);
            
            insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), "PIUTANG", "Piutang Penjualan", kpr.getKodeProperty(),
                    "Pencairan KPR - "+kpr.getNoKPR(), -kpr.getJumlahRp(), sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");
                        
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try {
                e.printStackTrace();
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }
    public static String batalTerimaPencairanKPR(Connection con, KPR kpr){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            KPRDAO.update(con, kpr);
            
            kpr.getProperty().setStatus("Sold");
            PropertyDAO.update(con, kpr.getProperty());
            
            String noKeuangan = KeuanganDAO.get(con, "PIUTANG", "Piutang Penjualan", kpr.getKodeProperty(), 
                    "Pencairan KPR - "+kpr.getNoKPR()).getNoKeuangan();
            KeuanganDAO.deleteAllByNoKeuangan(con, noKeuangan);
                        
            Piutang p = PiutangDAO.getByKategoriAndKeteranganAndStatus(con,  "Piutang Penjualan", kpr.getNoSKL(),"close");
            p.setPembayaran(p.getPembayaran()-kpr.getJumlahRp());
            p.setSisaPiutang(p.getSisaPiutang()+kpr.getJumlahRp());
            p.setStatus("open");
            PiutangDAO.update(con, p);
            
            List<TerimaPembayaran> listTerima = TerimaPembayaranDAO.getAllByNoPiutang(con, p.getNoPiutang(), "true");
            for(TerimaPembayaran t : listTerima){
                t.setStatus("false");
                t.setUserBatal(kpr.getUserBatal());
                t.setTglBatal(kpr.getTglBatal());
                TerimaPembayaranDAO.update(con, t);
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
    
    public static String saveTerimaAngsuran(Connection con, SAP sap){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            String noKeuangan = KeuanganDAO.getIdByDate(con, new Date());
            String nosap = SAPDAO.getId(con, sap.getProperty().getNamaProperty().substring(0, 3).toUpperCase());
            sap.setNoSAP(nosap);
            sap.setTglSAP(tglSql.format(new Date()));
            SAPDAO.insert(con, sap);
            
            insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), sap.getTipeKeuangan(), "Terima Pembayaran Angsuran", sap.getKodeProperty(),
                    "Terima Pembayaran Angsuran - "+sap.getNoSAP(), sap.getJumlahRp(), sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");
                        
            Piutang p = PiutangDAO.getByKategoriAndKeteranganAndStatus(con,  "Piutang Penjualan", sap.getNoSKL(),"open");
            p.setPembayaran(p.getPembayaran()+sap.getJumlahRp());
            p.setSisaPiutang(p.getSisaPiutang()-sap.getJumlahRp());
            if(p.getSisaPiutang()==0)
                p.setStatus("close");
            PiutangDAO.update(con, p);
            
            TerimaPembayaran tp = new TerimaPembayaran();
            tp.setNoTerimaPembayaran(TerimaPembayaranDAO.getId(con));
            tp.setTglTerima(tglSql.format(new Date()));
            tp.setNoPiutang(p.getNoPiutang());
            tp.setJumlahPembayaran(p.getJumlahPiutang());
            tp.setTipeKeuangan(sap.getTipeKeuangan());
            tp.setCatatan("");
            tp.setKodeUser(sistem.getUser().getUsername());
            tp.setTglBatal("2000-01-01 00:00:00");
            tp.setUserBatal("");
            tp.setStatus("true");
            TerimaPembayaranDAO.insert(con, tp);
            
            insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), "PIUTANG", "Piutang Penjualan", sap.getKodeProperty(),
                    "Terima Pembayaran Angsuran - "+sap.getNoSAP(), -sap.getJumlahRp(), sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try {
                e.printStackTrace();
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }
    public static String batalTerimaAngsuran(Connection con, SAP sap){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            SAPDAO.update(con, sap);
            
            String noKeuangan = KeuanganDAO.get(con, "PIUTANG", "Piutang Penjualan", sap.getKodeProperty(), 
                    "Terima Pembayaran Angsuran - "+sap.getNoSAP()).getNoKeuangan();
            KeuanganDAO.deleteAllByNoKeuangan(con, noKeuangan);
                        
            Piutang p = PiutangDAO.getByKategoriAndKeteranganAndStatus(con,  "Piutang Penjualan", sap.getNoSKL(),"open");
            if(p==null)
                p = PiutangDAO.getByKategoriAndKeteranganAndStatus(con, "Piutang Penjualan", sap.getNoSKL(), "close");
            p.setPembayaran(p.getPembayaran()-sap.getJumlahRp());
            p.setSisaPiutang(p.getSisaPiutang()+sap.getJumlahRp());
            p.setStatus("open");
            PiutangDAO.update(con, p);
            
            List<TerimaPembayaran> listTerima = TerimaPembayaranDAO.getAllByNoPiutang(con, p.getNoPiutang(), "true");
            for(TerimaPembayaran t : listTerima){
                t.setStatus("false");
                t.setUserBatal(sap.getUserBatal());
                t.setTglBatal(sap.getTglBatal());
                TerimaPembayaranDAO.update(con, t);
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
    
    public static String savePelunasanAngsuran(Connection con, SPPHead spp){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            String nospp = SPPHeadDAO.getId(con, spp.getProperty().getNamaProperty().substring(0, 3).toUpperCase());
            spp.setNoSPP(nospp);
            spp.setTglSPP(tglSql.format(new Date()));
            SPPHeadDAO.insert(con, spp);
            
            spp.getProperty().setStatus("Sold - Full Paid");
            PropertyDAO.update(con, spp.getProperty());
            for(SPPDetail detail : spp.getAllDetail()){
                detail.setNoSPP(nospp);
                SPPDetailDAO.insert(con, detail);
            }
            
            if(status.equals("true"))
                con.commit();
            else
                con.rollback();
            con.setAutoCommit(true);
            return status;
        }catch(Exception e){
            e.printStackTrace();
            try {
                con.rollback();
                con.setAutoCommit(true);
                return e.toString();
            } catch (SQLException ex) {
                return ex.toString();
            }
        }
    }
    public static String batalPelunasanAngsuran(Connection con, SPPHead spp){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            SPPHeadDAO.update(con, spp);
            spp.getProperty().setStatus("Sold");
            PropertyDAO.update(con, spp.getProperty());
            
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
    
    public static String saveAllTransaksiKeuangan(Connection con, List<Keuangan> listKeuangan){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            String noKeuangan = KeuanganDAO.getIdByDate(con, new Date());
            for(Keuangan k : listKeuangan){
                k.setNoKeuangan(noKeuangan);
                KeuanganDAO.insert(con, k);
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
    public static String saveAllTransaksiKeuanganWithImage(Connection con, List<Keuangan> listKeuangan, List<ImageView> listImage){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            String noKeuangan = KeuanganDAO.getIdByDate(con, tglBarang.parse(listKeuangan.get(0).getTglKeuangan()));
            for(Keuangan k : listKeuangan){
                k.setNoKeuangan(noKeuangan);
                KeuanganDAO.insert(con, k);
            }
            int noUrut = 1;
            for(ImageView i : listImage){
                File tempFile = new File(noKeuangan+" - "+noUrut+".png");
                BufferedImage bImage = SwingFXUtils.fromFXImage(i.getImage(), null);
                ImageIO.write(bImage, "png", tempFile);
                
                StorageOptions storageOptions = StorageOptions.newBuilder().
                        setProjectId("auristeel-280420").
                        setCredentials(GoogleCredentials.fromStream(Main.class.getResourceAsStream("Resource/credentials.json"))).build();
                Storage storage = storageOptions.getService();
                
                BlobId blobId = BlobId.of("jagobangunpersada", tempFile.getName());
                BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
                storage.create(blobInfo, Files.readAllBytes(Paths.get(tempFile.getPath())));
                
                Files.deleteIfExists(tempFile.toPath()); 
                
                noUrut = noUrut + 1;
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
    public static String batalTransaksiKeuangan(Connection con, String noKeuangan){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            List<Keuangan> listKeuangan = KeuanganDAO.getAllByNoKeuangan(con, noKeuangan);
            for(Keuangan k : listKeuangan){ 
                k.setStatus("false");
                k.setUserBatal(sistem.getUser().getUsername());
                k.setTglBatal(tglSql.format(new Date()));
                KeuanganDAO.update(con, k);
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
    
    public static String saveNewHutang(Connection con, Hutang hutang,String tipeKeuangan){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            String noKeuangan = KeuanganDAO.getIdByDate(con, new Date());
            HutangDAO.insert(con, hutang);
            
            insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), "HUTANG", hutang.getKategori(), "",
                    hutang.getNoHutang()+" - "+hutang.getKeterangan(), hutang.getJumlahHutang(), sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");
            
            insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), tipeKeuangan, hutang.getKategori(), "",
                    hutang.getNoHutang()+" - "+hutang.getKeterangan(), hutang.getJumlahHutang(), sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");
            
            
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
    public static String saveNewPembayaranHutang(Connection con, Pembayaran pembayaran,String jatuhtempo){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            String noKeuangan = KeuanganDAO.getIdByDate(con, new Date());
            PembayaranDAO.insert(con, pembayaran);
            
            Hutang hutang = pembayaran.getHutang();
            hutang.setPembayaran(hutang.getPembayaran()+pembayaran.getJumlahPembayaran());
            hutang.setSisaHutang(hutang.getSisaHutang()-pembayaran.getJumlahPembayaran());
            hutang.setJatuhTempo(jatuhtempo);
            if(hutang.getSisaHutang()==0)
                hutang.setStatus("close");
            HutangDAO.update(con, hutang);
            
            String kodeProperty = "";
            if(hutang.getKategori().equals("Hutang Pembelian Tanah"))
                kodeProperty = PembelianTanahDAO.get(con, hutang.getKeterangan()).getKodeProperty();
            
            insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), "HUTANG", hutang.getKategori(), kodeProperty,
                    "Pembayaran - "+hutang.getNoHutang()+" - "+hutang.getKeterangan(), -pembayaran.getJumlahPembayaran(), sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");
            
            insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), pembayaran.getTipeKeuangan(), hutang.getKategori(), kodeProperty,
                    "Pembayaran - "+hutang.getNoHutang()+" - "+hutang.getKeterangan(), -pembayaran.getJumlahPembayaran(), sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");
            

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
            
            String noKeuangan = KeuanganDAO.get(con, "HUTANG", hutang.getKategori(), "", 
                    "Pembayaran - "+hutang.getNoHutang()+" - "+hutang.getKeterangan()).getNoKeuangan();
            KeuanganDAO.deleteAllByNoKeuangan(con, noKeuangan);
            
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
    
    public static String saveNewPiutang(Connection con, Piutang piutang,String tipeKeuangan){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            String noKeuangan = KeuanganDAO.getIdByDate(con, new Date());
            
            insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), "PIUTANG", piutang.getKategori(), "",
                    piutang.getNoPiutang()+" - "+piutang.getKeterangan(), piutang.getJumlahPiutang(), sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");
            
            insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), tipeKeuangan, piutang.getKategori(), "",
                    piutang.getNoPiutang()+" - "+piutang.getKeterangan(), -piutang.getJumlahPiutang(), sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");
            
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
    public static String saveNewTerimaPembayaranPiutang(Connection con, TerimaPembayaran terimaPembayaran,String jatuhtempo){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            String noKeuangan = KeuanganDAO.getIdByDate(con, new Date());
            TerimaPembayaranDAO.insert(con, terimaPembayaran);
            
            Piutang piutang = terimaPembayaran.getPiutang();
            piutang.setPembayaran(piutang.getPembayaran()+terimaPembayaran.getJumlahPembayaran());
            piutang.setSisaPiutang(piutang.getSisaPiutang()-terimaPembayaran.getJumlahPembayaran());
            piutang.setJatuhTempo(jatuhtempo);
            if(piutang.getSisaPiutang()==0)
                piutang.setStatus("close");
            PiutangDAO.update(con, piutang);
            
            insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), "PIUTANG", piutang.getKategori(), "",
                    "Terima Pembayaran - "+piutang.getNoPiutang()+" - "+piutang.getKeterangan(), -terimaPembayaran.getJumlahPembayaran(), sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");
            
            insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), terimaPembayaran.getTipeKeuangan(), piutang.getKategori(), "",
                    "Terima Pembayaran - "+piutang.getNoPiutang()+" - "+piutang.getKeterangan(), terimaPembayaran.getJumlahPembayaran(), sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");
            
            
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
            
            String noKeuangan = KeuanganDAO.get(con, "PIUTANG", piutang.getKategori(), "", 
                    "Terima Pembayaran - "+piutang.getNoPiutang()+" - "+piutang.getKeterangan()).getNoKeuangan();
            KeuanganDAO.deleteAllByNoKeuangan(con, noKeuangan);
            
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
    
    public static String savePembelianAsetTetap(Connection con, AsetTetap aset, Double jumlahBayar, String tipeKeuangan){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            String noKeuangan = KeuanganDAO.getIdByDate(con, new Date());
            AsetTetapDAO.insert(con, aset);
            
            insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), tipeKeuangan, "Pembelian Aset Tetap", "",
                    "Pembelian Aset Tetap - "+aset.getNoAset(), -jumlahBayar, sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");
            
            insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), "ASET TETAP", aset.getKategori(), "",
                    "Pembelian Aset Tetap - "+aset.getNoAset(), aset.getNilaiAwal(), sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");
                        
            if(aset.getNilaiAwal()>jumlahBayar){
                Hutang h = new Hutang();
                h.setNoHutang(HutangDAO.getId(con));
                h.setTglHutang(tglSql.format(new Date()));
                h.setKategori("Hutang Pembelian Aset Tetap");
                h.setKeterangan("Pembelian Aset Tetap - "+aset.getNoAset());
                h.setJumlahHutang(aset.getNilaiAwal()-jumlahBayar);
                h.setPembayaran(0);
                h.setSisaHutang(aset.getNilaiAwal()-jumlahBayar);
                h.setJatuhTempo("2000-01-01");
                h.setStatus("open");
                HutangDAO.insert(con, h);

                insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), "HUTANG", "Hutang Pembelian Aset Tetap", "",
                        "Pembelian Aset Tetap - "+aset.getNoAset(), aset.getNilaiAwal()-jumlahBayar, sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");
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
    public static String savePenjualanAsetTetap(Connection con, AsetTetap aset, Double jumlahBayar, String tipeKeuangan){
        try{
            con.setAutoCommit(false);
            String status = "true";
            
            String noKeuangan = KeuanganDAO.getIdByDate(con, new Date());
            AsetTetapDAO.update(con, aset);
                        
            insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), tipeKeuangan, "Penjualan Aset Tetap", "",
                    "Penjualan Aset Tetap - "+aset.getNoAset(), jumlahBayar, sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");
                        
            insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), "ASET TETAP", aset.getKategori(), "",
                    "Penjualan Aset Tetap - "+aset.getNoAset(), -aset.getNilaiAkhir(), sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");
                        
            if(aset.getHargaJual() > aset.getNilaiAkhir()){
                insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), "PENDAPATAN", "Pendapatan Penjualan Aset Tetap", "",
                        "Penjualan Aset Tetap - "+aset.getNoAset(), aset.getHargaJual()-aset.getNilaiAkhir(), sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");
            }else if(aset.getHargaJual() < aset.getNilaiAkhir()){
                insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), "BEBAN", "Beban Penjualan Aset Tetap", "",
                        "Penjualan Aset Tetap - "+aset.getNoAset(), aset.getNilaiAkhir()-aset.getHargaJual(), sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");
            }
            
            if(aset.getHargaJual()>jumlahBayar){
                Piutang p = new Piutang();
                p.setNoPiutang(HutangDAO.getId(con));
                p.setTglPiutang(tglSql.format(new Date()));
                p.setKategori("Piutang Penjualan Aset Tetap");
                p.setKeterangan("Penjualan Aset Tetap - "+aset.getNoAset());
                p.setJumlahPiutang(aset.getHargaJual()-jumlahBayar);
                p.setPembayaran(0);
                p.setSisaPiutang(aset.getHargaJual()-jumlahBayar);
                p.setJatuhTempo("2000-01-01");
                p.setStatus("open");
                PiutangDAO.insert(con, p);

                insertKeuangan(con, noKeuangan, tglBarang.format(new Date()), "PIUTANG", "Piutang Penjualan Aset Tetap", "",
                        "Penjualan Aset Tetap - "+aset.getNoAset(), aset.getHargaJual()-jumlahBayar, sistem.getUser().getUsername(), 
                                        tglSql.format(new Date()), "true", "2000-01-01 00:00:00", "");
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
    
}
