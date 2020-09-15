/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.Service;

import com.excellentsystem.TokoEmasJagoPusat.DAO.AmbilBarangDetailDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.AmbilBarangHeadDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.BarangCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.BatalBarcodeDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.HancurDetailDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.HancurHeadDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.HutangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.JenisDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.KategoriDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.KeuanganCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.KeuanganPusatDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.LeburRosokCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.LogHargaEmasDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.OtoritasDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.PegawaiDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.PembayaranHutangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.PembayaranPiutangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.PembelianDetailDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.PembelianHeadDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.PenjualanCabangDetailDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.PenjualanCabangHeadDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.PenjualanCiokCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.PenyesuaianStokBarangPusatDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.PindahDetailDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.PindahHeadDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.PiutangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.ReturPembelianDetailDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.ReturPembelianHeadDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.RevisiBarangCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.RosokCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.SPDetailDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.SPHeadDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.SetoranCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.SistemDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.StokBarangCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.StokBarangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.StokRosokCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.SubKategoriDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.SupplierDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.TambahUangCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.TokoCabang.BarangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.TokoCabang.KeuanganDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.TokoCabang.SistemCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.TokoCabang.StokBarangDiCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.TpBarangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.UserDAO;
import com.excellentsystem.TokoEmasJagoPusat.Function;
import static com.excellentsystem.TokoEmasJagoPusat.Function.pembulatan;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.user;
import com.excellentsystem.TokoEmasJagoPusat.Model.AmbilBarangDetail;
import com.excellentsystem.TokoEmasJagoPusat.Model.AmbilBarangHead;
import com.excellentsystem.TokoEmasJagoPusat.Model.BarangCabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.BatalBarcode;
import com.excellentsystem.TokoEmasJagoPusat.Model.Cabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.HancurDetail;
import com.excellentsystem.TokoEmasJagoPusat.Model.HancurHead;
import com.excellentsystem.TokoEmasJagoPusat.Model.Hutang;
import com.excellentsystem.TokoEmasJagoPusat.Model.Jenis;
import com.excellentsystem.TokoEmasJagoPusat.Model.Kategori;
import com.excellentsystem.TokoEmasJagoPusat.Model.KeuanganCabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.KeuanganPusat;
import com.excellentsystem.TokoEmasJagoPusat.Model.LeburRosokCabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.LogHargaEmas;
import com.excellentsystem.TokoEmasJagoPusat.Model.Otoritas;
import com.excellentsystem.TokoEmasJagoPusat.Model.Pegawai;
import com.excellentsystem.TokoEmasJagoPusat.Model.PembayaranHutang;
import com.excellentsystem.TokoEmasJagoPusat.Model.PembayaranPiutang;
import com.excellentsystem.TokoEmasJagoPusat.Model.PembelianDetail;
import com.excellentsystem.TokoEmasJagoPusat.Model.PembelianHead;
import com.excellentsystem.TokoEmasJagoPusat.Model.PenjualanCabangDetail;
import com.excellentsystem.TokoEmasJagoPusat.Model.PenjualanCabangHead;
import com.excellentsystem.TokoEmasJagoPusat.Model.PenjualanCiokCabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.PenyesuaianStokBarangPusat;
import com.excellentsystem.TokoEmasJagoPusat.Model.PindahDetail;
import com.excellentsystem.TokoEmasJagoPusat.Model.PindahHead;
import com.excellentsystem.TokoEmasJagoPusat.Model.Piutang;
import com.excellentsystem.TokoEmasJagoPusat.Model.ReturPembelianDetail;
import com.excellentsystem.TokoEmasJagoPusat.Model.ReturPembelianHead;
import com.excellentsystem.TokoEmasJagoPusat.Model.RevisiBarangCabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.RosokCabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.SPDetail;
import com.excellentsystem.TokoEmasJagoPusat.Model.SPHead;
import com.excellentsystem.TokoEmasJagoPusat.Model.SetoranCabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.Sistem;
import com.excellentsystem.TokoEmasJagoPusat.Model.StokBarang;
import com.excellentsystem.TokoEmasJagoPusat.Model.StokBarangCabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.StokRosokCabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.SubKategori;
import com.excellentsystem.TokoEmasJagoPusat.Model.Supplier;
import com.excellentsystem.TokoEmasJagoPusat.Model.TambahUangCabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.TokoCabang.Barang;
import com.excellentsystem.TokoEmasJagoPusat.Model.TokoCabang.Keuangan;
import com.excellentsystem.TokoEmasJagoPusat.Model.TokoCabang.SistemCabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.TokoCabang.StokBarangDiCabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.TpBarang;
import com.excellentsystem.TokoEmasJagoPusat.Model.User;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yunaz
 */
public class Service {
    public static String updateStokBarangBarcodeMasuk(Connection con, String kodeBarang, String kodeBarcode, 
            String kodeCabang, String kodeGudang, String kodeKategori, String kodeJenis,
            int qty, double berat, double beratPersen, double nilai, String status)throws Exception{
        StokBarangCabang s = StokBarangCabangDAO.getBarcode(con, sistem.getTglSystem(), kodeGudang, kodeBarcode);
        if(s==null){
            StokBarangCabang sb = new StokBarangCabang();
            sb.setTanggal(sistem.getTglSystem());
            sb.setKodeBarang(kodeBarang);
            sb.setKodeBarcode(kodeBarcode);
            sb.setKodeCabang(kodeCabang);
            sb.setKodeGudang(kodeGudang);
            sb.setKodeKategori(kodeKategori);
            sb.setKodeJenis(kodeJenis);

            sb.setStokAwal(0);
            sb.setBeratAwal(0);
            sb.setBeratPersenAwal(0);
            sb.setNilaiAwal(0);

            sb.setStokMasuk(qty);
            sb.setBeratMasuk(pembulatan(berat));
            sb.setBeratPersenMasuk(pembulatan(beratPersen));
            sb.setNilaiMasuk(pembulatan(nilai));

            sb.setStokKeluar(0);
            sb.setBeratKeluar(0);
            sb.setBeratPersenKeluar(0);
            sb.setNilaiKeluar(0);

            sb.setStokAkhir(qty);
            sb.setBeratAkhir(pembulatan(berat));
            sb.setBeratPersenAkhir(pembulatan(beratPersen));
            sb.setNilaiAkhir(pembulatan(nilai));
            StokBarangCabangDAO.insert(con, sb);
        }else{
            s.setStokMasuk(s.getStokMasuk()+qty);
            s.setBeratMasuk(pembulatan(s.getBeratMasuk()+berat));
            s.setBeratPersenMasuk(pembulatan(s.getBeratPersenMasuk()+beratPersen));
            s.setNilaiMasuk(pembulatan(s.getNilaiMasuk()+nilai));

            s.setStokAkhir(s.getStokAkhir()+qty);
            s.setBeratAkhir(pembulatan(s.getBeratAkhir()+berat));
            s.setBeratPersenAkhir(pembulatan(s.getBeratPersenAkhir()+beratPersen));
            s.setNilaiAkhir(pembulatan(s.getNilaiAkhir()+nilai));
            StokBarangCabangDAO.update(con, s);
        }
        return status;
    }
    public static String updateStokBarangBarcodeBatalMasuk(Connection con, String kodeBarcode, String kodeGudang, 
            int qty, double berat, double beratPersen, double nilai, String status)throws Exception{
        StokBarangCabang s = StokBarangCabangDAO.getBarcode(con, sistem.getTglSystem(), kodeGudang, kodeBarcode);
        if(s==null)
            status = "Stok barang dengan kode barcode "+kodeBarcode+" di gudang "+kodeGudang+" tidak ditemukan";
        else if(s.getStokAkhir()<qty)
            status = "Stok barang dengan kode barcode "+kodeBarcode+" di gudang "+kodeGudang+" tidak mencukupi";
        else if(s.getBeratAkhir()<berat)
            status = "Berat barang dengan kode barcode "+kodeBarcode+" di gudang "+kodeGudang+" tidak mencukupi";
//        else if(s.getNilaiAkhir()<nilai)
//            status = "Nilai barang dengan kode barcode "+kodeBarcode+" di gudang "+kodeGudang+" tidak mencukupi";
        else{
            s.setStokMasuk(s.getStokMasuk()-qty);
            s.setBeratMasuk(pembulatan(s.getBeratMasuk()-berat));
            s.setBeratPersenMasuk(pembulatan(s.getBeratPersenMasuk()-beratPersen));
            s.setNilaiMasuk(pembulatan(s.getNilaiMasuk()-nilai));

            s.setStokAkhir(s.getStokAkhir()-qty);
            s.setBeratAkhir(pembulatan(s.getBeratAkhir()-berat));
            s.setBeratPersenAkhir(pembulatan(s.getBeratPersenAkhir()-beratPersen));
            s.setNilaiAkhir(pembulatan(s.getNilaiAkhir()-nilai));
            StokBarangCabangDAO.update(con, s);
        }
        return status;
    }
    public static String updateStokBarangBarcodeKeluar(Connection con, String kodeBarcode, 
            String kodeGudang, int qty, double berat, double beratPersen, double nilai, String status)throws Exception{
        StokBarangCabang s = StokBarangCabangDAO.getBarcode(con, sistem.getTglSystem(), kodeGudang, kodeBarcode);
        if(s==null)
            status = "Stok barang dengan kode barcode "+kodeBarcode+" di gudang "+kodeGudang+" tidak ditemukan";
        else if(s.getStokAkhir()<qty)
            status = "Stok barang dengan kode barcode "+kodeBarcode+" di gudang "+kodeGudang+" tidak mencukupi";
        else if(s.getBeratAkhir()<berat)
            status = "Berat barang dengan kode barcode "+kodeBarcode+" di gudang "+kodeGudang+" tidak mencukupi";
//        else if(s.getNilaiAkhir()<nilai)
//            status = "Nilai barang dengan kode barcode "+kodeBarcode+" di gudang "+kodeGudang+" tidak mencukupi";
        else{
            s.setStokKeluar(s.getStokKeluar()+qty);
            s.setBeratKeluar(pembulatan(s.getBeratKeluar()+berat));
            s.setBeratPersenKeluar(pembulatan(s.getBeratPersenKeluar()+beratPersen));
            s.setNilaiKeluar(pembulatan(s.getNilaiKeluar()+nilai));

            s.setStokAkhir(s.getStokAkhir()-qty);
            s.setBeratAkhir(pembulatan(s.getBeratAkhir()-berat));
            s.setBeratPersenAkhir(pembulatan(s.getBeratPersenAkhir()-beratPersen));
            s.setNilaiAkhir(pembulatan(s.getNilaiAkhir()-nilai));
            StokBarangCabangDAO.update(con, s);
        }
        return status;
    }
    public static String updateStokBarangBarcodeBatalKeluar(Connection con, String kodeBarcode, String kodeGudang, 
            int qty, double berat, double beratPersen, double nilai, String status)throws Exception{
        StokBarangCabang s = StokBarangCabangDAO.getBarcode(con, sistem.getTglSystem(), kodeGudang, kodeBarcode);
        if(s==null)
            status = "Stok barang dengan kode barcode "+kodeBarcode+" di gudang "+kodeGudang+" tidak ditemukan";
        else{
            s.setStokKeluar(s.getStokKeluar()-qty);
            s.setBeratKeluar(pembulatan(s.getBeratKeluar()-berat));
            s.setBeratPersenKeluar(pembulatan(s.getBeratPersenKeluar()-beratPersen));
            s.setNilaiKeluar(pembulatan(s.getNilaiKeluar()-nilai));

            s.setStokAkhir(s.getStokAkhir()+qty);
            s.setBeratAkhir(pembulatan(s.getBeratAkhir()+berat));
            s.setBeratPersenAkhir(pembulatan(s.getBeratPersenAkhir()+beratPersen));
            s.setNilaiAkhir(pembulatan(s.getNilaiAkhir()+nilai));
            StokBarangCabangDAO.update(con, s);
        }
        return status;
    }
    
    public static String updateStokBarangNonBarcodeMasuk(Connection con, String kodeCabang, String kodeGudang,  
            String kodeKategori, String kodeJenis, int qty, double berat, double beratPersen, double nilai, String status)throws Exception{
        StokBarangCabang s = StokBarangCabangDAO.getNonBarcode(con, sistem.getTglSystem(), kodeGudang, kodeJenis);
        if(s==null){
            StokBarangCabang sb = new StokBarangCabang();
            sb.setTanggal(sistem.getTglSystem());
            sb.setKodeBarang("");
            sb.setKodeBarcode("");
            sb.setKodeCabang(kodeCabang);
            sb.setKodeGudang(kodeGudang);
            sb.setKodeKategori(kodeKategori);
            sb.setKodeJenis(kodeJenis);

            sb.setStokAwal(0);
            sb.setBeratAwal(0);
            sb.setBeratPersenAwal(0);
            sb.setNilaiAwal(0);

            sb.setStokMasuk(qty);
            sb.setBeratMasuk(pembulatan(berat));
            sb.setBeratPersenMasuk(pembulatan(beratPersen));
            sb.setNilaiMasuk(pembulatan(nilai));

            sb.setStokKeluar(0);
            sb.setBeratKeluar(0);
            sb.setBeratPersenKeluar(0);
            sb.setNilaiKeluar(0);

            sb.setStokAkhir(qty);
            sb.setBeratAkhir(pembulatan(berat));
            sb.setBeratPersenAkhir(pembulatan(beratPersen));
            sb.setNilaiAkhir(pembulatan(nilai));
            StokBarangCabangDAO.insert(con, sb);
        }else{
            s.setStokMasuk(s.getStokMasuk()+qty);
            s.setBeratMasuk(pembulatan(s.getBeratMasuk()+berat));
            s.setBeratPersenMasuk(pembulatan(s.getBeratPersenMasuk()+beratPersen));
            s.setNilaiMasuk(pembulatan(s.getNilaiMasuk()+nilai));

            s.setStokAkhir(s.getStokAkhir()+qty);
            s.setBeratAkhir(pembulatan(s.getBeratAkhir()+berat));
            s.setBeratPersenAkhir(pembulatan(s.getBeratPersenAkhir()+beratPersen));
            s.setNilaiAkhir(pembulatan(s.getNilaiAkhir()+nilai));
            StokBarangCabangDAO.update(con, s);
        }
        return status;
    }
    public static String updateStokBarangNonBarcodeBatalMasuk(Connection con, String kodeGudang, 
            String kodeJenis, int qty, double berat, double beratPersen, double nilai, String status)throws Exception{
        StokBarangCabang s = StokBarangCabangDAO.getNonBarcode(con, sistem.getTglSystem(), kodeGudang, kodeJenis);
        if(s==null)
            status = "Stok barang "+kodeJenis+" di gudang "+kodeGudang+" tidak ditemukan";
        else if(s.getStokAkhir()<qty)
            status = "Stok barang "+kodeJenis+" di gudang "+kodeGudang+" tidak mencukupi";
        else if(s.getBeratAkhir()<berat)
            status = "Berat barang "+kodeJenis+" di gudang "+kodeGudang+" tidak mencukupi";
//        else if(s.getNilaiAkhir()<nilai)
//            status = "Nilai barang "+kodeJenis+" di gudang "+kodeGudang+" tidak mencukupi";
        else{
            s.setStokMasuk(s.getStokMasuk()-qty);
            s.setBeratMasuk(pembulatan(s.getBeratMasuk()-berat));
            s.setBeratPersenMasuk(pembulatan(s.getBeratPersenMasuk()-beratPersen));
            s.setNilaiMasuk(pembulatan(s.getNilaiMasuk()-nilai));

            s.setStokAkhir(s.getStokAkhir()-qty);
            s.setBeratAkhir(pembulatan(s.getBeratAkhir()-berat));
            s.setBeratPersenAkhir(pembulatan(s.getBeratPersenAkhir()-beratPersen));
            s.setNilaiAkhir(pembulatan(s.getNilaiAkhir()-nilai));
            StokBarangCabangDAO.update(con, s);
        }
        return status;
    }
    public static String updateStokBarangNonBarcodeKeluar(Connection con, String kodeGudang, String kodeJenis,
            int qty, double berat, double beratPersen, double nilai, String status)throws Exception{
        StokBarangCabang s = StokBarangCabangDAO.getNonBarcode(con, sistem.getTglSystem(), kodeGudang, kodeJenis);
        if(s==null)
            status = "Stok barang "+kodeJenis+" di gudang "+kodeGudang+" tidak ditemukan";
        else if(s.getStokAkhir()<qty)
            status = "Stok barang "+kodeJenis+" di gudang "+kodeGudang+" tidak mencukupi";
        else if(s.getBeratAkhir()<berat)
            status = "Berat barang "+kodeJenis+" di gudang "+kodeGudang+" tidak mencukupi";
//        else if(s.getNilaiAkhir()<nilai)
//            status = "Nilai barang "+kodeJenis+" di gudang "+kodeGudang+" tidak mencukupi";
        else{
            s.setStokKeluar(s.getStokKeluar()+qty);
            s.setBeratKeluar(pembulatan(s.getBeratKeluar()+berat));
            s.setBeratPersenKeluar(pembulatan(s.getBeratPersenKeluar()+beratPersen));
            s.setNilaiKeluar(pembulatan(s.getNilaiKeluar()+nilai));

            s.setStokAkhir(s.getStokAkhir()-qty);
            s.setBeratAkhir(pembulatan(s.getBeratAkhir()-berat));
            s.setBeratPersenAkhir(pembulatan(s.getBeratPersenAkhir()-beratPersen));
            s.setNilaiAkhir(pembulatan(s.getNilaiAkhir()-nilai));
            StokBarangCabangDAO.update(con, s);
        }
        return status;
    }
    public static String updateStokBarangNonBarcodeBatalKeluar(Connection con, String kodeGudang, String kodeJenis,
            int qty, double berat, double beratPersen, double nilai, String status)throws Exception{
        StokBarangCabang s = StokBarangCabangDAO.getNonBarcode(con, sistem.getTglSystem(), kodeGudang, kodeJenis);
        if(s==null)
            status = "Stok barang "+kodeJenis+" di gudang "+kodeGudang+" tidak ditemukan";
        else{
            s.setStokKeluar(s.getStokKeluar()-qty);
            s.setBeratKeluar(pembulatan(s.getBeratKeluar()-berat));
            s.setBeratPersenKeluar(pembulatan(s.getBeratPersenKeluar()-beratPersen));
            s.setNilaiKeluar(pembulatan(s.getNilaiKeluar()-nilai));

            s.setStokAkhir(s.getStokAkhir()+qty);
            s.setBeratAkhir(pembulatan(s.getBeratAkhir()+berat));
            s.setBeratPersenAkhir(pembulatan(s.getBeratPersenAkhir()+beratPersen));
            s.setNilaiAkhir(pembulatan(s.getNilaiAkhir()+nilai));
            StokBarangCabangDAO.update(con, s);
        }
        return status;
    }
    
    public static String updateStokRosokCiokMasuk(Connection con, String kodeCabang, String kodeGudang,  
            double berat, double beratPersen, double nilai, String status)throws Exception{
        StokRosokCabang s = StokRosokCabangDAO.get(con, sistem.getTglSystem(), kodeCabang, kodeGudang);
        if(s==null){
            StokRosokCabang sb = new StokRosokCabang();
            sb.setTanggal(sistem.getTglSystem());
            sb.setKodeCabang(kodeCabang);
            sb.setKodeGudang(kodeGudang);

            sb.setBeratAwal(0);
            sb.setBeratPersenAwal(0);
            sb.setNilaiAwal(0);

            sb.setBeratMasuk(pembulatan(berat));
            sb.setBeratPersenMasuk(pembulatan(beratPersen));
            sb.setNilaiMasuk(pembulatan(nilai));

            sb.setBeratKeluar(0);
            sb.setBeratPersenKeluar(0);
            sb.setNilaiKeluar(0);

            sb.setBeratAkhir(pembulatan(berat));
            sb.setBeratPersenAkhir(pembulatan(beratPersen));
            sb.setNilaiAkhir(pembulatan(nilai));
            StokRosokCabangDAO.insert(con, sb);
        }else{
            s.setBeratMasuk(pembulatan(s.getBeratMasuk()+berat));
            s.setBeratPersenMasuk(pembulatan(s.getBeratPersenMasuk()+beratPersen));
            s.setNilaiMasuk(pembulatan(s.getNilaiMasuk()+nilai));

            s.setBeratAkhir(pembulatan(s.getBeratAkhir()+berat));
            s.setBeratPersenAkhir(pembulatan(s.getBeratPersenAkhir()+beratPersen));
            s.setNilaiAkhir(pembulatan(s.getNilaiAkhir()+nilai));
            StokRosokCabangDAO.update(con, s);
        }
        return status;
    }
    public static String updateStokRosokCiokBatalMasuk(Connection con, String kodeCabang, String kodeGudang, 
            double berat, double beratPersen, double nilai, String status)throws Exception{
        StokRosokCabang s = StokRosokCabangDAO.get(con, sistem.getTglSystem(), kodeCabang, kodeGudang);
        if(s==null)
            status = "Stok barang di gudang "+kodeGudang+" tidak ditemukan";
        else if(s.getBeratAkhir()<berat)
            status = "Berat barang di gudang "+kodeGudang+" tidak mencukupi";
//        else if(s.getNilaiAkhir()<nilai)
//            status = "Nilai barang di gudang "+kodeGudang+" tidak mencukupi";
        else{
            s.setBeratMasuk(pembulatan(s.getBeratMasuk()-berat));
            s.setBeratPersenMasuk(pembulatan(s.getBeratPersenMasuk()-beratPersen));
            s.setNilaiMasuk(pembulatan(s.getNilaiMasuk()-nilai));

            s.setBeratAkhir(pembulatan(s.getBeratAkhir()-berat));
            s.setBeratPersenAkhir(pembulatan(s.getBeratPersenAkhir()-beratPersen));
            s.setNilaiAkhir(pembulatan(s.getNilaiAkhir()-nilai));
            StokRosokCabangDAO.update(con, s);
        }
        return status;
    }
    public static String updateStokRosokCiokKeluar(Connection con, String kodeCabang, String kodeGudang, 
            double berat, double beratPersen, double nilai, String status)throws Exception{
        StokRosokCabang s = StokRosokCabangDAO.get(con, sistem.getTglSystem(), kodeCabang, kodeGudang);
        if(s==null)
            status = "Stok barang di gudang "+kodeGudang+" tidak ditemukan";
        else if(s.getBeratAkhir()<berat)
            status = "Berat barang di gudang "+kodeGudang+" tidak mencukupi";
//        else if(s.getNilaiAkhir()<nilai)
//            status = "Nilai barang di gudang "+kodeGudang+" tidak mencukupi";
        else{
            s.setBeratKeluar(pembulatan(s.getBeratKeluar()+berat));
            s.setBeratPersenKeluar(pembulatan(s.getBeratPersenKeluar()+beratPersen));
            s.setNilaiKeluar(pembulatan(s.getNilaiKeluar()+nilai));

            s.setBeratAkhir(pembulatan(s.getBeratAkhir()-berat));
            s.setBeratPersenAkhir(pembulatan(s.getBeratPersenAkhir()-beratPersen));
            s.setNilaiAkhir(pembulatan(s.getNilaiAkhir()-nilai));
            StokRosokCabangDAO.update(con, s);
        }
        return status;
    }
    public static String updateStokRosokCiokBatalKeluar(Connection con, String kodeCabang, String kodeGudang, 
            double berat, double beratPersen, double nilai, String status)throws Exception{
        StokRosokCabang s = StokRosokCabangDAO.get(con, sistem.getTglSystem(), kodeCabang, kodeGudang);
        if(s==null)
            status = "Stok barang di gudang "+kodeGudang+" tidak ditemukan";
        else{
            s.setBeratKeluar(pembulatan(s.getBeratKeluar()-berat));
            s.setBeratPersenKeluar(pembulatan(s.getBeratPersenKeluar()-beratPersen));
            s.setNilaiKeluar(pembulatan(s.getNilaiKeluar()-nilai));

            s.setBeratAkhir(pembulatan(s.getBeratAkhir()+berat));
            s.setBeratPersenAkhir(pembulatan(s.getBeratPersenAkhir()+beratPersen));
            s.setNilaiAkhir(pembulatan(s.getNilaiAkhir()+nilai));
            StokRosokCabangDAO.update(con, s);
        }
        return status;
    }
    
    public static String updateStokPusatMasuk(Connection con, String kodeKategori, String kodeJenis, 
            double berat, double beratPersen, double nilai, String status)throws Exception{
        StokBarang s = StokBarangDAO.get(con, sistem.getTglSystem(), kodeJenis);
        if(s==null){
            StokBarang sb = new StokBarang();
            sb.setTanggal(sistem.getTglSystem());
            sb.setKodeKategori(kodeKategori);
            sb.setKodeSubKategori(kodeJenis);

            sb.setBeratAwal(0);
            sb.setBeratPersenAwal(0);
            sb.setNilaiAwal(0);

            sb.setBeratMasuk(pembulatan(berat));
            sb.setBeratPersenMasuk(pembulatan(beratPersen));
            sb.setNilaiMasuk(pembulatan(nilai));

            sb.setBeratKeluar(0);
            sb.setBeratPersenKeluar(0);
            sb.setNilaiKeluar(0);

            sb.setBeratAkhir(pembulatan(berat));
            sb.setBeratPersenAkhir(pembulatan(beratPersen));
            sb.setNilaiAkhir(pembulatan(nilai));
            StokBarangDAO.insert(con, sb);
        }else{
            s.setBeratMasuk(pembulatan(s.getBeratMasuk()+berat));
            s.setBeratPersenMasuk(pembulatan(s.getBeratPersenMasuk()+beratPersen));
            s.setNilaiMasuk(pembulatan(s.getNilaiMasuk()+nilai));

            s.setBeratAkhir(pembulatan(s.getBeratAkhir()+berat));
            s.setBeratPersenAkhir(pembulatan(s.getBeratPersenAkhir()+beratPersen));
            s.setNilaiAkhir(pembulatan(s.getNilaiAkhir()+nilai));
            StokBarangDAO.update(con, s);
        }
        return status;
    }
    public static String updateStokPusatBatalMasuk(Connection con, String kodeJenis, 
            double berat, double beratPersen, double nilai, String status)throws Exception{
        StokBarang s = StokBarangDAO.get(con, sistem.getTglSystem(), kodeJenis);
        if(s==null)
            status = "Stok barang "+kodeJenis+" tidak ditemukan";
        else if(s.getBeratAkhir()<berat)
            status = "Berat barang "+kodeJenis+" tidak mencukupi";
//        else if(s.getNilaiAkhir()<nilai)
//            status = "Nilai barang "+kodeJenis+" tidak mencukupi";
        else{
            s.setBeratMasuk(pembulatan(s.getBeratMasuk()-berat));
            s.setBeratPersenMasuk(pembulatan(s.getBeratPersenMasuk()-beratPersen));
            s.setNilaiMasuk(pembulatan(s.getNilaiMasuk()-nilai));

            s.setBeratAkhir(pembulatan(s.getBeratAkhir()-berat));
            s.setBeratPersenAkhir(pembulatan(s.getBeratPersenAkhir()-beratPersen));
            s.setNilaiAkhir(pembulatan(s.getNilaiAkhir()-nilai));
            StokBarangDAO.update(con, s);
        }
        return status;
    }
    public static String updateStokPusatKeluar(Connection con, String kodeJenis, 
            double berat, double beratPersen, double nilai, String status)throws Exception{
        StokBarang s = StokBarangDAO.get(con, sistem.getTglSystem(), kodeJenis);
        if(s==null)
            status = "Stok barang "+kodeJenis+" tidak ditemukan";
        else if(s.getBeratAkhir()<berat)
            status = "Berat barang "+kodeJenis+" tidak mencukupi";
//        else if(s.getNilaiAkhir()<nilai)
//            status = "Nilai barang "+kodeJenis+" tidak mencukupi";
        else{
            s.setBeratKeluar(pembulatan(s.getBeratKeluar()+berat));
            s.setBeratPersenKeluar(pembulatan(s.getBeratPersenKeluar()+beratPersen));
            s.setNilaiKeluar(pembulatan(s.getNilaiKeluar()+nilai));

            s.setBeratAkhir(pembulatan(s.getBeratAkhir()-berat));
            s.setBeratPersenAkhir(pembulatan(s.getBeratPersenAkhir()-beratPersen));
            s.setNilaiAkhir(pembulatan(s.getNilaiAkhir()-nilai));
            StokBarangDAO.update(con, s);
        }
        return status;
    }
    public static String updateStokPusatBatalKeluar(Connection con, String kodeJenis, 
            double berat, double beratPersen, double nilai, String status)throws Exception{
        StokBarang s = StokBarangDAO.get(con, sistem.getTglSystem(), kodeJenis);
        if(s==null)
            status = "Stok barang "+kodeJenis+" tidak ditemukan";
        else{
            s.setBeratKeluar(pembulatan(s.getBeratKeluar()-berat));
            s.setBeratPersenKeluar(pembulatan(s.getBeratPersenKeluar()-beratPersen));
            s.setNilaiKeluar(pembulatan(s.getNilaiKeluar()-nilai));

            s.setBeratAkhir(pembulatan(s.getBeratAkhir()+berat));
            s.setBeratPersenAkhir(pembulatan(s.getBeratPersenAkhir()+beratPersen));
            s.setNilaiAkhir(pembulatan(s.getNilaiAkhir()+nilai));
            StokBarangDAO.update(con, s);
        }
        return status;
    }
    
    public static void insertKeuanganCabang(Connection conPusat, String noKeuangan, String kodeCabang, String tipeKasir,
            String tipeKeuangan, String kategori, String deskripsi, double jumlahRp, String kodeUser)throws Exception{
        KeuanganCabang k = new KeuanganCabang();
        k.setNoKeuangan(noKeuangan);
        k.setTglKeuangan(Function.getSystemDate());
        k.setKodeCabang(kodeCabang);
        k.setTipeKasir(tipeKasir);
        k.setTipeKeuangan(tipeKeuangan);
        k.setKategori(kategori);
        k.setDeskripsi(deskripsi);
        k.setJumlahRp(jumlahRp);
        k.setKodeUser(kodeUser);
        KeuanganCabangDAO.insert(conPusat, k);
    }
    public static void insertKeuanganPusat(Connection conPusat, String noKeuangan, 
            String tipeKeuangan, String kategori, String deskripsi, double jumlahRp, String kodeUser)throws Exception{
        KeuanganPusat k = new KeuanganPusat();
        k.setNoKeuangan(noKeuangan);
        k.setTglKeuangan(Function.getSystemDate());
        k.setTipeKeuangan(tipeKeuangan);
        k.setKategori(kategori);
        k.setDeskripsi(deskripsi);
        k.setJumlahRp(jumlahRp);
        k.setKodeUser(kodeUser);
        KeuanganPusatDAO.insert(conPusat, k);
    }
    
    public static String savePengaturanUmum(Connection con, Sistem s)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            SistemDAO.update(con, s);
            
            LogHargaEmas lh = new LogHargaEmas();
            lh.setTanggal(s.getTglSystem());
            lh.setHargaEmas(s.getHargaEmas());
            LogHargaEmasDAO.update(con, lh);
            
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

            for(User user : UserDAO.getAll(con)){
                if(user.getKodeUser().equals(u.getKodeUser()))
                    status = "Username sudah pernah terdaftar";
            }
            UserDAO.insert(con, u);
            for(Otoritas o : u.getOtoritas()){
                OtoritasDAO.insert(con, o);
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
    public static String saveUpdateUser(Connection con, User u)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            UserDAO.update(con, u);
            OtoritasDAO.deleteAllByKodeUser(con, u.getKodeUser());
            for(Otoritas o : u.getOtoritas()){
                OtoritasDAO.insert(con, o);
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
    public static String saveUpdatePassword(Connection con, User u)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            UserDAO.update(con, u);
            
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
            OtoritasDAO.deleteAllByKodeUser(con, u.getKodeUser());
            
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
    
    public static String saveNewCabang(Connection con, Cabang c)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            for(Cabang user : CabangDAO.getAll(con)){
                if(user.getKodeCabang().equals(c.getKodeCabang()))
                    status = "Kode cabang sudah pernah terdaftar";
            }
            CabangDAO.insert(con, c);

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
    public static String saveUpdateCabang(Connection con, Cabang u)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            CabangDAO.update(con, u);
            
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
    public static String deleteCabang(Connection con, Cabang c)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            List<Piutang> listPiutang = PiutangDAO.getAllByDateAndCabangAndTipeKasirAndKategoriAndStatus(
                    con, "2000-01-01", sistem.getTglSystem(), c.getKodeCabang(), "%" ,"%", "true");
            for(Piutang p : listPiutang){
                if(p.getSisaPiutang()!=0)
                    status = "Cabang tidak dapat dihapus,\nkarena masih ada hutang yang belum lunas";
            }
            CabangDAO.delete(con, c);
            
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
    
    public static String saveNewKategoriBarang(Connection con, Kategori k)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            for(Kategori x : KategoriDAO.getAll(con)){
                if(x.getKodeKategori().toUpperCase().equals(k.getKodeKategori()))
                    status = "Kode kategori sudah pernah terdaftar";
            }
            KategoriDAO.insert(con, k);

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
    public static String saveUpdateKategoriBarang(Connection con, Kategori k)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            KategoriDAO.update(con, k);

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
    public static String deleteKategoriBarang(Connection con, Kategori k)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            for(Jenis j : JenisDAO.getAll(con)){
                if(j.getKodeKategori().toUpperCase().equals(k.getKodeKategori().toUpperCase()))
                    status = "Kategori barang tidak dapat dihapus,"
                        + "\nkarena masih ada jenis barang dengan kategori "+k.getKodeKategori();
            }
            for(SubKategori x : SubKategoriDAO.getAll(con)){
                if(x.getKodeKategori().toUpperCase().equals(k.getKodeKategori().toUpperCase()))
                    status = "Kategori barang tidak dapat dihapus,"
                        + "\nkarena masih ada subkategori barang dengan kategori "+k.getKodeKategori();
            }
            KategoriDAO.delete(con, k);

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
    
    public static String saveNewSubKategoriBarang(Connection con, SubKategori s)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            for(SubKategori x : SubKategoriDAO.getAll(con)){
                if(x.getKodeSubKategori().toUpperCase().equals(s.getKodeSubKategori()))
                    status = "Kode sub-kategori sudah pernah terdaftar";
            }
            SubKategoriDAO.insert(con, s);

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
    public static String saveUpdateSubKategoriBarang(Connection con, SubKategori s)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            SubKategoriDAO.update(con, s);

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
    public static String deleteSubKategoriBarang(Connection con, SubKategori s)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            for(Jenis j : JenisDAO.getAll(con)){
                if(j.getKodeSubKategori().toUpperCase().equals(s.getKodeSubKategori().toUpperCase()))
                    status = "Sub kategori barang tidak dapat dihapus,"
                        + "\nkarena masih ada jenis barang dengan sub kategori "+s.getKodeSubKategori();
            }
            StokBarang stok =StokBarangDAO.get(con, s.getKodeSubKategori(), Main.sistem.getTglSystem());
            if(stok!=null)
                status = "Sub kategori barang tidak dapat dihapus,"
                        + "\nkarena stok barang di gudang pusat masih ada";
            
            SubKategoriDAO.delete(con, s);

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
    
    public static String saveNewJenisBarang(Connection con, Jenis j)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            for(Jenis x : JenisDAO.getAll(con)){
                if(x.getKodeJenis().toUpperCase().equals(j.getKodeJenis()))
                    status = "Kode jenis sudah pernah terdaftar";
            }
            JenisDAO.insert(con, j);

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
    public static String saveUpdateJenisBarang(Connection con, Jenis j)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            JenisDAO.update(con, j);
                           
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
    public static String deleteJenisBarang(Connection con, Jenis j)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);

            StokBarangCabang stok = StokBarangCabangDAO.getNonBarcode(
                    con, Main.sistem.getTglSystem(), "%", j.getKodeJenis());
            if(stok!=null){
                status = "Jenis barang tidak dapat dihapus,"
                            + "\nkarena masih ada barang dengan jenis barang "+j.getKodeJenis();
            }
            
            JenisDAO.delete(con, j);

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

            PegawaiDAO.insert(con, p);
            
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

            PegawaiDAO.delete(con, p);

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

            for(Supplier x : SupplierDAO.getAll(con)){
                if(x.getKodeSupplier().equals(s.getKodeSupplier()))
                    status = "Supplier sudah pernah terdaftar";
            }
            SupplierDAO.insert(con, s);
            
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

            double totalHutang = 0;
            List<Hutang> listHutang = HutangDAO.getAllByDateAndSupplierAndStatus(con, "2000-01-01", "2100-01-01", s.getKodeSupplier(), "true");
            for(Hutang h : listHutang){
                totalHutang = totalHutang + h.getSisaHutang();
            }
            if(totalHutang!=0)
                status = "Supplier tidak dapat dihapus,"
                        + "\nkarena masih ada hutang yang belum lunas";
            
            SupplierDAO.delete(con, s);
            
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
    
    public static String saveBarcodeBarang(Connection con, BarangCabang b, String kodeCabang, String kodeGudang, String jenisKeluar)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            Kategori k = KategoriDAO.get(con, b.getKodeKategori());
            double persentaseEmas = k.getPersentaseEmas()/100;
            
            b.setKodeBarcode(TpBarangDAO.getKodeBarcode(con));
            b.setKodeBarang(TpBarangDAO.getKodeBarang(con, b.getKodeJenis()));
            b.setBeratPersen(pembulatan(b.getBerat()*persentaseEmas));
            
            double nilaiKeluar = 0;
            StokBarangCabang stokKeluar = StokBarangCabangDAO.getNonBarcode(con, sistem.getTglSystem(), kodeGudang, jenisKeluar);
            if(stokKeluar==null){
                status = "Stok barang "+b.getKodeJenis()+" tidak ditemukan";
            }else{
                if(stokKeluar.getBeratAkhir()<b.getBeratAsli() || stokKeluar.getStokAkhir()<1){
                    status = "Stok "+stokKeluar.getKodeJenis()+" tidak mencukupi";
                }else{
                    nilaiKeluar = stokKeluar.getNilaiAkhir()/stokKeluar.getBeratAkhir()*b.getBeratAsli();
                }
            }
            b.setNilaiPokok(pembulatan(nilaiKeluar));
            BarangCabangDAO.insert(con, b);
            
            status = updateStokBarangNonBarcodeKeluar(con, kodeGudang, jenisKeluar, 1, b.getBeratAsli(), 
                    pembulatan(b.getBeratAsli()*persentaseEmas), pembulatan(nilaiKeluar), status);
            
            TpBarang t = new TpBarang();
            t.setKodeBarang(b.getKodeBarang());
            t.setKodeBarcode(b.getKodeBarcode());
            t.setKodeJenis(b.getKodeJenis());
            TpBarangDAO.update(con, t);
            
            status = updateStokBarangBarcodeMasuk(con, b.getKodeBarang(), b.getKodeBarcode(), kodeCabang, kodeGudang, b.getKodeKategori(), b.getKodeJenis(), 
                    1, b.getBerat(), b.getBeratPersen(), b.getNilaiPokok(), status);
            
            
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
    public static String saveBatalBarcodeBarang(Connection con, BarangCabang b, String kodeCabang, String kodeGudang)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            BatalBarcode bb = new BatalBarcode();
            bb.setKodeBarcode(b.getKodeBarcode());
            bb.setTglBatal(Function.getSystemDate());
            bb.setUserBatal(user.getKodeUser());
            BatalBarcodeDAO.insert(con, bb);
            
            if(b.getInputDate().substring(0,10).equals(sistem.getTglSystem())){
                Kategori k = KategoriDAO.get(con, b.getKodeKategori());
                double persentaseEmas = k.getPersentaseEmas()/100;

                String jenis = "";
                if("New".equals(b.getAsalBarang()))
                    jenis = JenisDAO.get(con,b.getKodeJenis()).getKodeSubKategori();
                else
                    jenis = b.getKodeJenis();
            
                status = updateStokBarangNonBarcodeBatalKeluar(con, kodeGudang, jenis, 1, b.getBeratAsli(), 
                        pembulatan(b.getBeratAsli()*persentaseEmas), b.getNilaiPokok(), status);
                status = updateStokBarangBarcodeBatalMasuk(con, b.getKodeBarcode(), kodeGudang, 1, b.getBerat(), b.getBeratPersen(), b.getNilaiPokok(), status);
            }else{
                Kategori k = KategoriDAO.get(con, b.getKodeKategori());
                double persentaseEmas = k.getPersentaseEmas()/100;

                String jenis = "";
                if("New".equals(b.getAsalBarang()))
                    jenis = JenisDAO.get(con,b.getKodeJenis()).getKodeSubKategori();
                else
                    jenis = b.getKodeJenis();
                
                status = updateStokBarangNonBarcodeMasuk(con, kodeCabang, kodeGudang, b.getKodeKategori(), jenis,  1, b.getBeratAsli(), 
                        pembulatan(b.getBeratAsli()*persentaseEmas), b.getNilaiPokok(), status);
                status = updateStokBarangBarcodeKeluar(con, b.getKodeBarcode(), kodeGudang, 1, b.getBerat(), b.getBeratPersen(), b.getNilaiPokok(), status);
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
    
    public static String savePindahBarang(Connection con, PindahHead p)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            PindahHeadDAO.insert(con, p);
            double nilai = 0;
            for(PindahDetail d : p.getListPindahDetail()){
                d.setNoPindah(p.getNoPindah());
                PindahDetailDAO.insert(con, d);
                
                status = updateStokBarangBarcodeKeluar(con, d.getKodeBarcode(), p.getKodeGudang(), 1, d.getBerat(), d.getBeratPersen(), d.getNilaiPokok(), status);
                status = updateStokBarangBarcodeMasuk(con, d.getKodeBarang(), d.getKodeBarcode(), p.getKodeCabang(), p.getKodeCabang()+"-Pindah", d.getKodeKategori(), d.getKodeJenis(), 
                        1, d.getBerat(), d.getBeratPersen(), d.getNilaiPokok(), status);
                nilai = nilai + d.getNilaiPokok();
            }
            String noKeuangan = KeuanganCabangDAO.getId(con, p.getKodeCabang());
            insertKeuanganCabang(con, noKeuangan, p.getKodeCabang(), "Kasir", p.getKodeCabang()+"-Pindah", "Pindah Barang", p.getNoPindah(), pembulatan(nilai), user.getKodeUser());
            insertKeuanganCabang(con, noKeuangan, p.getKodeCabang(), "Kasir", p.getKodeGudang(), "Pindah Barang", p.getNoPindah(), -pembulatan(nilai), user.getKodeUser());
            
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
    public static String saveBatalPindahBarang(Connection con, PindahHead p)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            PindahHeadDAO.update(con, p);
            p.setListPindahDetail(PindahDetailDAO.getAllByNoPindah(con, p.getNoPindah()));
            if(p.getTglPindah().substring(0,10).equals(sistem.getTglSystem())){
                for(PindahDetail d : p.getListPindahDetail()){
                    status = updateStokBarangBarcodeBatalKeluar(con, d.getKodeBarcode(), p.getKodeGudang(), 1, d.getBerat(), d.getBeratPersen(), d.getNilaiPokok(), status);
                    status = updateStokBarangBarcodeBatalMasuk(con, d.getKodeBarcode(),  p.getKodeCabang()+"-Pindah", 1, d.getBerat(), d.getBeratPersen(), d.getNilaiPokok(), status);
                }
                KeuanganCabang k = KeuanganCabangDAO.get(con, p.getKodeCabang(), "Kasir", p.getKodeCabang()+"-Pindah", "Pindah Barang", p.getNoPindah());
                KeuanganCabangDAO.deleteAll(con, k.getNoKeuangan());
            }else{
                double nilai = 0;
                for(PindahDetail d : p.getListPindahDetail()){
                    status = updateStokBarangBarcodeMasuk(con, d.getKodeBarang(), d.getKodeBarcode(), p.getKodeCabang(), p.getKodeGudang(), d.getKodeKategori(), d.getKodeJenis(), 
                            1, d.getBerat(), d.getBeratPersen(), d.getNilaiPokok(), status);
                    status = updateStokBarangBarcodeKeluar(con, d.getKodeBarcode(),  p.getKodeCabang()+"-Pindah", 1, d.getBerat(), d.getBeratPersen(), d.getNilaiPokok(), status);
                    nilai = nilai + d.getNilaiPokok();
                }
                String noKeuangan = KeuanganCabangDAO.getId(con, p.getKodeCabang());
                insertKeuanganCabang(con, noKeuangan, p.getKodeCabang(), "Kasir", p.getKodeCabang()+"-Pindah", "Batal Pindah Barang", p.getNoPindah(), -pembulatan(nilai), user.getKodeUser());
                insertKeuanganCabang(con, noKeuangan, p.getKodeCabang(), "Kasir", p.getKodeGudang(), "Batal Pindah Barang", p.getNoPindah(), pembulatan(nilai), user.getKodeUser());
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
    
    public static String saveTerimaBalenanCabang(Connection con, AmbilBarangHead a)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            a.setStatusTerima("true");
            a.setTglTerima(Function.getSystemDate());
            a.setUserTerima(user.getKodeUser());
            AmbilBarangHeadDAO.update(con, a);
            
            List<String> listJenis = new ArrayList<>();
            for(AmbilBarangDetail d : a.getDetail()){
                if(!listJenis.contains(d.getKodeJenis()))
                    listJenis.add(d.getKodeJenis());
            }
            double totalAmbil = 0;
            for(String kodeJenis : listJenis){
                String kodeKategori = "";
                int qty = 0;
                double beratKotor = 0;
                double beratPersen = 0;
                double hargaBeli = 0;
                for(AmbilBarangDetail d : a.getDetail()){
                    if(kodeJenis.equals(d.getKodeJenis())){
                        kodeKategori = d.getKodeKategori();
                        qty = qty + d.getQty();
                        beratKotor = beratKotor+ d.getBeratKotor();
                        beratPersen = beratPersen + d.getBeratPersen();
                        hargaBeli = hargaBeli + d.getHargaBeli();
                    }
                }
                status = updateStokBarangNonBarcodeKeluar(con, a.getKodeCabang()+"-Ambil", kodeJenis,  
                        qty, pembulatan(beratKotor), pembulatan(beratPersen), pembulatan(hargaBeli), status);

                status = updateStokBarangNonBarcodeMasuk(con, a.getKodeCabang(), "BLN-"+a.getKodeCabang(), kodeKategori, kodeJenis,  
                        qty, pembulatan(beratKotor), pembulatan(beratPersen), pembulatan(hargaBeli), status);

                totalAmbil = totalAmbil + hargaBeli;
            }
            String noKeuangan = KeuanganCabangDAO.getId(con, a.getKodeCabang());
            insertKeuanganCabang(con, noKeuangan, a.getKodeCabang(), "Kasir", a.getKodeCabang()+"-Ambil", "Terima Balenan Cabang", a.getNoAmbilBarang(), -pembulatan(totalAmbil), user.getKodeUser());
            insertKeuanganCabang(con, noKeuangan, a.getKodeCabang(), "Kasir", "BLN-"+a.getKodeCabang(), "Terima Balenan Cabang", a.getNoAmbilBarang(), pembulatan(totalAmbil), user.getKodeUser());
            
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
    public static String saveBatalTerimaBalenanCabang(Connection con, AmbilBarangHead a)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            AmbilBarangHeadDAO.update(con, a);
            a.setDetail(AmbilBarangDetailDAO.getAllByNoAmbilBarang(con, a.getNoAmbilBarang()));
            if(a.getTglTerima().substring(0,10).equals(sistem.getTglSystem())){
                List<String> listJenis = new ArrayList<>();
                for(AmbilBarangDetail d : a.getDetail()){
                    if(!listJenis.contains(d.getKodeJenis()))
                        listJenis.add(d.getKodeJenis());
                }
                for(String kodeJenis : listJenis){
                    int qty = 0;
                    double beratKotor = 0;
                    double beratPersen = 0;
                    double hargaBeli = 0;
                    for(AmbilBarangDetail d : a.getDetail()){
                        if(kodeJenis.equals(d.getKodeJenis())){
                            qty = qty + d.getQty();
                            beratKotor = beratKotor+ d.getBeratKotor();
                            beratPersen = beratPersen + d.getBeratPersen();
                            hargaBeli = hargaBeli + d.getHargaBeli();
                        }
                    }
                    status = updateStokBarangNonBarcodeBatalKeluar(con, a.getKodeCabang()+"-Ambil",kodeJenis,  
                            qty, pembulatan(beratKotor), pembulatan(beratPersen), pembulatan(hargaBeli), status);
                    status = updateStokBarangNonBarcodeBatalMasuk(con, "BLN-"+a.getKodeCabang(), kodeJenis,  
                            qty, pembulatan(beratKotor), pembulatan(beratPersen), pembulatan(hargaBeli), status);
                }
                KeuanganCabang k = KeuanganCabangDAO.get(con, a.getKodeCabang(), "Kasir", a.getKodeCabang()+"-Ambil", "Terima Balenan Cabang", a.getNoAmbilBarang());
                KeuanganCabangDAO.deleteAll(con, k.getNoKeuangan());
            }else{
                List<String> listJenis = new ArrayList<>();
                for(AmbilBarangDetail d : a.getDetail()){
                    if(!listJenis.contains(d.getKodeJenis()))
                        listJenis.add(d.getKodeJenis());
                }
                double totalAmbil = 0;
                for(String kodeJenis : listJenis){
                    String kodeKategori = "";
                    int qty = 0;
                    double beratKotor = 0;
                    double beratPersen = 0;
                    double hargaBeli = 0;
                    for(AmbilBarangDetail d : a.getDetail()){
                        if(kodeJenis.equals(d.getKodeJenis())){
                            kodeKategori = d.getKodeKategori();
                            qty = qty + d.getQty();
                            beratKotor = beratKotor+ d.getBeratKotor();
                            beratPersen = beratPersen + d.getBeratPersen();
                            hargaBeli = hargaBeli + d.getHargaBeli();
                        }
                    }
                    status = updateStokBarangNonBarcodeMasuk(con, a.getKodeCabang(), a.getKodeCabang()+"-Ambil", kodeKategori, kodeJenis,  
                            qty, pembulatan(beratKotor), pembulatan(beratPersen), pembulatan(hargaBeli), status);
                    status = updateStokBarangNonBarcodeKeluar(con, "BLN-"+a.getKodeCabang(), kodeJenis,  
                            qty, pembulatan(beratKotor), pembulatan(beratPersen), pembulatan(hargaBeli), status);
                    totalAmbil = totalAmbil + hargaBeli;
                }
                String noKeuangan = KeuanganCabangDAO.getId(con, a.getKodeCabang());
                insertKeuanganCabang(con, noKeuangan, a.getKodeCabang(), "Kasir", a.getKodeCabang()+"-Ambil", "Batal Terima Balenan Cabang", a.getNoAmbilBarang(), pembulatan(totalAmbil), user.getKodeUser());
                insertKeuanganCabang(con, noKeuangan, a.getKodeCabang(), "Kasir", "BLN-"+a.getKodeCabang(), "Batal Terima Balenan Cabang", a.getNoAmbilBarang(), -pembulatan(totalAmbil), user.getKodeUser());
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
    
    public static String saveSPBarangCabang(Connection con, SPHead s)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            SPHeadDAO.insert(con, s);
            
            List<String> listJenisKeluar = new ArrayList<>();
            List<String> listJenisMasuk = new ArrayList<>();
            for(SPDetail d : s.getListSPDetail()){
                SPDetailDAO.insert(con, d);
                if(!listJenisKeluar.contains(d.getKodeJenisBalenan()))
                    listJenisKeluar.add(d.getKodeJenisBalenan());
                if(!listJenisMasuk.contains(d.getKodeJenis()))
                    listJenisMasuk.add(d.getKodeJenis());
            }
            double totalNilaiKeluar = 0;
            double totalNilaiMasuk = 0;
            for(String kodeJenis : listJenisKeluar){
                int qty = 0;
                double berat = 0;
                double beratPersen = 0;
                double nilai = 0;
                for(SPDetail d : s.getListSPDetail()){
                    if(kodeJenis.equals(d.getKodeJenisBalenan())){
                        qty = qty + d.getQty();
                        berat = berat + d.getBerat();
                        beratPersen = beratPersen + d.getBeratPersenBalenan();
                        nilai = nilai + d.getNilaiPokok();
                    }
                }
                status = updateStokBarangNonBarcodeKeluar(con, "BLN-"+s.getKodeCabang(), kodeJenis,  qty, pembulatan(berat), pembulatan(beratPersen), pembulatan(nilai), status);

                totalNilaiKeluar = totalNilaiKeluar + nilai;
            }
            for(String kodeJenis : listJenisMasuk){
                String kodeKategori = "";
                int qty = 0;
                double berat = 0;
                double beratPersen = 0;
                double nilai = 0;
                for(SPDetail d : s.getListSPDetail()){
                    if(kodeJenis.equals(d.getKodeJenis())){
                        kodeKategori = d.getKodeKategori();
                        qty = qty + d.getQty();
                        berat = berat + d.getBerat();
                        beratPersen = beratPersen + d.getBeratPersen();
                        nilai = nilai + d.getNilaiPokok();
                    }
                }
                status = updateStokBarangNonBarcodeMasuk(con, s.getKodeCabang(), "SP-"+s.getKodeCabang(), kodeKategori, kodeJenis,  
                        qty, pembulatan(berat), pembulatan(beratPersen), pembulatan(nilai), status);

                totalNilaiMasuk = totalNilaiMasuk + nilai;
            }
            if(pembulatan(totalNilaiKeluar)!=pembulatan(totalNilaiMasuk))
                status = "Nilai keluar tidak sama dengan nilai masuk";
            
            String noKeuangan = KeuanganCabangDAO.getId(con, s.getKodeCabang());
            insertKeuanganCabang(con, noKeuangan, s.getKodeCabang(), "Kasir", "BLN-"+s.getKodeCabang(), "SP Barang Cabang", s.getNoSP(), -pembulatan(totalNilaiKeluar), user.getKodeUser());
            insertKeuanganCabang(con, noKeuangan, s.getKodeCabang(), "Kasir", "SP-"+s.getKodeCabang(), "SP Barang Cabang", s.getNoSP(), pembulatan(totalNilaiMasuk), user.getKodeUser());
            
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
    public static String saveBatalSPBarangCabang(Connection con, SPHead s)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            SPHeadDAO.update(con, s);
            
            s.setListSPDetail(SPDetailDAO.getAllByNoSP(con, s.getNoSP()));
            List<String> listJenisBatalKeluar = new ArrayList<>();
            List<String> listJenisBatalMasuk = new ArrayList<>();
            for(SPDetail d : s.getListSPDetail()){
                if(!listJenisBatalKeluar.contains(d.getKodeJenisBalenan()))
                    listJenisBatalKeluar.add(d.getKodeJenisBalenan());
                if(!listJenisBatalMasuk.contains(d.getKodeJenis()))
                    listJenisBatalMasuk.add(d.getKodeJenis());
            }
            if(s.getTglSP().substring(0,10).equals(sistem.getTglSystem())){
                for(String kodeJenis : listJenisBatalKeluar){
                    int qty = 0;
                    double berat = 0;
                    double beratPersen = 0;
                    double nilai = 0;
                    for(SPDetail d : s.getListSPDetail()){
                        if(kodeJenis.equals(d.getKodeJenisBalenan())){
                            qty = qty + d.getQty();
                            berat = berat + d.getBerat();
                            beratPersen = beratPersen + d.getBeratPersenBalenan();
                            nilai = nilai + d.getNilaiPokok();
                        }
                    }
                    status = updateStokBarangNonBarcodeBatalKeluar(con, "BLN-"+s.getKodeCabang(), kodeJenis,  
                            qty, pembulatan(berat), pembulatan(beratPersen), pembulatan(nilai), status);
                }
                for(String kodeJenis : listJenisBatalMasuk){
                    int qty = 0;
                    double berat = 0;
                    double beratPersen = 0;
                    double nilai = 0;
                    for(SPDetail d : s.getListSPDetail()){
                        if(kodeJenis.equals(d.getKodeJenis())){
                            qty = qty + d.getQty();
                            berat = berat + d.getBerat();
                            beratPersen = beratPersen + d.getBeratPersen();
                            nilai = nilai + d.getNilaiPokok();
                        }
                    }
                    status = updateStokBarangNonBarcodeBatalMasuk(con, "SP-"+s.getKodeCabang(), kodeJenis,  
                            qty, pembulatan(berat), pembulatan(beratPersen), pembulatan(nilai), status);
                }
                
                KeuanganCabang k = KeuanganCabangDAO.get(con, s.getKodeCabang(), "Kasir", "SP-"+s.getKodeCabang(), "SP Barang Cabang", s.getNoSP());
                KeuanganCabangDAO.deleteAll(con, k.getNoKeuangan());
            }else{
                double totalNilaiKeluar = 0;
                double totalNilaiMasuk = 0;
                for(String kodeJenis : listJenisBatalKeluar){
                    String kodeKategori = "";
                    int qty = 0;
                    double berat = 0;
                    double beratPersen = 0;
                    double nilai = 0;
                    for(SPDetail d : s.getListSPDetail()){
                        if(kodeJenis.equals(d.getKodeJenisBalenan())){
                            kodeKategori = d.getKodeKategori();
                            qty = qty + d.getQty();
                            berat = berat + d.getBerat();
                            beratPersen = beratPersen + d.getBeratPersenBalenan();
                            nilai = nilai + d.getNilaiPokok();
                        }
                    }
                    status = updateStokBarangNonBarcodeMasuk(con, s.getKodeCabang(), "BLN-"+s.getKodeCabang(), kodeKategori, kodeJenis,  
                            qty, pembulatan(berat), pembulatan(beratPersen), pembulatan(nilai), status);
                    totalNilaiKeluar = totalNilaiKeluar + nilai;
                }
                for(String kodeJenis : listJenisBatalMasuk){
                    int qty = 0;
                    double berat = 0;
                    double beratPersen = 0;
                    double nilai = 0;
                    for(SPDetail d : s.getListSPDetail()){
                        if(kodeJenis.equals(d.getKodeJenis())){
                            qty = qty + d.getQty();
                            berat = berat + d.getBerat();
                            beratPersen = beratPersen + d.getBeratPersen();
                            nilai = nilai + d.getNilaiPokok();
                        }
                    }
                    status = updateStokBarangNonBarcodeKeluar(con, "SP-"+s.getKodeCabang(), kodeJenis,  
                            qty, pembulatan(berat), pembulatan(beratPersen), pembulatan(nilai), status);
                    totalNilaiMasuk = totalNilaiMasuk + nilai;
                }
                if(pembulatan(totalNilaiKeluar)!=pembulatan(totalNilaiMasuk))
                    status = "Nilai keluar tidak sama dengan nilai masuk";

                String noKeuangan = KeuanganCabangDAO.getId(con, s.getKodeCabang());
                insertKeuanganCabang(con, noKeuangan, s.getKodeCabang(), "Kasir", "BLN-"+s.getKodeCabang(), "Batal SP Barang Cabang", s.getNoSP(), pembulatan(totalNilaiKeluar), user.getKodeUser());
                insertKeuanganCabang(con, noKeuangan, s.getKodeCabang(), "Kasir", "SP-"+s.getKodeCabang(), "Batal SP Barang Cabang", s.getNoSP(), -pembulatan(totalNilaiMasuk), user.getKodeUser());

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
    
    public static String saveSelesaiSPBarangCabang(Connection con, SPHead s)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            double totalBeratPenyusutan = 0;
            double totalNilaiPenyusutan = 0;
            List<SPDetail> listSpDetail = SPDetailDAO.getAllByNoSP(con, s.getNoSP());
            for(SPDetail d : s.getListSPDetail()){
                for(SPDetail detail : listSpDetail){
                    if(d.getKodeJenis().equals(detail.getKodeJenis())){
                        detail.setBeratPenyusutan(pembulatan(detail.getBerat()/d.getBerat()*d.getBeratPenyusutan()));
                        detail.setNilaiPenyusutan(pembulatan(detail.getBerat()/d.getBerat()*d.getNilaiPenyusutan()));
                        SPDetailDAO.update(con, detail);
                    }
                }
                totalBeratPenyusutan = totalBeratPenyusutan + d.getBeratPenyusutan();
                totalNilaiPenyusutan = totalNilaiPenyusutan + d.getNilaiPenyusutan();
            }
            s.setTotalBeratPenyusutan(pembulatan(totalBeratPenyusutan));
            s.setTotalNilaiPenyusutan(pembulatan(totalNilaiPenyusutan));
            SPHeadDAO.update(con, s);
            
            double totalNilai = 0;
            double totalNilaiJadi = 0;
            for(SPDetail d : s.getListSPDetail()){
                double beratJadi = pembulatan(d.getBerat()-d.getBeratPenyusutan());
                double beratPersenJadi = pembulatan(d.getBeratPersen()/d.getBerat()*beratJadi);
                double nilaiJadi = pembulatan(d.getNilaiPokok()-d.getNilaiPenyusutan());
                status = updateStokBarangNonBarcodeKeluar(con, "SP-"+s.getKodeCabang(), d.getKodeJenis(),  
                        d.getQty(), d.getBerat(), d.getBeratPersen(), d.getNilaiPokok(), status);
                status = updateStokBarangNonBarcodeMasuk(con, s.getKodeCabang(), s.getKodeCabang()+"-SP", d.getKodeKategori(), d.getKodeJenis(),  
                        d.getQty(), beratJadi, beratPersenJadi, nilaiJadi, status);
                
                totalNilai = totalNilai + d.getNilaiPokok();
                totalNilaiJadi = totalNilaiJadi + nilaiJadi;
            }
            String noKeuangan = KeuanganCabangDAO.getId(con, s.getKodeCabang());
            insertKeuanganCabang(con, noKeuangan, s.getKodeCabang(), "Kasir", "SP-"+s.getKodeCabang(), "Terima SP Barang Cabang", s.getNoSP(), -pembulatan(totalNilai), user.getKodeUser());
            insertKeuanganCabang(con, noKeuangan, s.getKodeCabang(), "Kasir", s.getKodeCabang()+"-SP", "Terima SP Barang Cabang", s.getNoSP(), pembulatan(totalNilaiJadi), user.getKodeUser());
            insertKeuanganCabang(con, noKeuangan, s.getKodeCabang(), "Kasir", "Beban Penyusutan SP Barang", "Terima SP Barang Cabang", s.getNoSP(), -pembulatan(totalNilai-totalNilaiJadi), user.getKodeUser());
            
            
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
    public static String saveBatalSelesaiSPBarangCabang(Connection con, SPHead s)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            List<SPDetail> listSpDetail = SPDetailDAO.getAllByNoSP(con, s.getNoSP());
            List<String> listJenis = new ArrayList<>();
            for(SPDetail d : listSpDetail){
                if(!listJenis.contains(d.getKodeJenis()))
                    listJenis.add(d.getKodeJenis());
            }
            if(s.getTglSP().substring(0,10).equals(sistem.getTglSystem())){
                for(String kodeJenis : listJenis){
                    int qty = 0;
                    double berat = 0;
                    double beratPersen = 0;
                    double nilai = 0;
                    double beratPenyusutan = 0;
                    double nilaiPenyusutan = 0;
                    for(SPDetail d : listSpDetail){
                        if(kodeJenis.equals(d.getKodeJenis())){
                            qty = qty + d.getQty();
                            berat = berat + d.getBerat();
                            beratPersen = beratPersen + d.getBeratPersen();
                            nilai = nilai + d.getNilaiPokok();
                            beratPenyusutan = beratPenyusutan + d.getBeratPenyusutan();
                            nilaiPenyusutan = nilaiPenyusutan + d.getNilaiPenyusutan();
                        }
                    }
                    double beratJadi = pembulatan(berat-beratPenyusutan);
                    double beratPersenJadi = pembulatan(beratPersen/berat*beratJadi);
                    double nilaiJadi = pembulatan(nilai-nilaiPenyusutan);
                    
                    status = updateStokBarangNonBarcodeBatalKeluar(con, "SP-"+s.getKodeCabang(), kodeJenis,  
                            qty, pembulatan(berat), pembulatan(beratPersen), pembulatan(nilai), status);
                    status = updateStokBarangNonBarcodeBatalMasuk(con, s.getKodeCabang()+"-SP", kodeJenis,  
                            qty, beratJadi, beratPersenJadi, nilaiJadi, status);
                }
                KeuanganCabang k = KeuanganCabangDAO.get(con, s.getKodeCabang(), "Kasir", "SP-"+s.getKodeCabang(), "Terima SP Barang Cabang", s.getNoSP());
                KeuanganCabangDAO.deleteAll(con, k.getNoKeuangan());
            }else{
                double totalNilai = 0;
                double totalNilaiJadi = 0;
                for(String kodeJenis : listJenis){
                    String kodeKategori = "";
                    int qty = 0;
                    double berat = 0;
                    double beratPersen = 0;
                    double nilai = 0;
                    double beratPenyusutan = 0;
                    double nilaiPenyusutan = 0;
                    for(SPDetail d : listSpDetail){
                        if(kodeJenis.equals(d.getKodeJenis())){
                            kodeKategori = d.getKodeKategori();
                            qty = qty + d.getQty();
                            berat = berat + d.getBerat();
                            beratPersen = beratPersen + d.getBeratPersen();
                            nilai = nilai + d.getNilaiPokok();
                            beratPenyusutan = beratPenyusutan + d.getBeratPenyusutan();
                            nilaiPenyusutan = nilaiPenyusutan + d.getNilaiPenyusutan();
                        }
                    }
                    double beratJadi = pembulatan(berat-beratPenyusutan);
                    double beratPersenJadi = pembulatan(beratPersen/berat*beratJadi);
                    double nilaiJadi = pembulatan(nilai-nilaiPenyusutan);
                    
                    status = updateStokBarangNonBarcodeMasuk(con, s.getKodeCabang(), "SP-"+s.getKodeCabang(), kodeKategori, kodeJenis,  
                            qty, pembulatan(berat), pembulatan(beratPersen), pembulatan(nilai), status);
                    status = updateStokBarangNonBarcodeKeluar(con, s.getKodeCabang()+"-SP", kodeJenis,  
                            qty, beratJadi, beratPersenJadi, nilaiJadi, status);
                    
                    totalNilai = totalNilai + nilai;
                    totalNilaiJadi = totalNilaiJadi + nilaiJadi;
                }
                String noKeuangan = KeuanganCabangDAO.getId(con, s.getKodeCabang());
                insertKeuanganCabang(con, noKeuangan, s.getKodeCabang(), "Kasir", "SP-"+s.getKodeCabang(), "Batal Terima SP Barang Cabang", s.getNoSP(), pembulatan(totalNilai), user.getKodeUser());
                insertKeuanganCabang(con, noKeuangan, s.getKodeCabang(), "Kasir", s.getKodeCabang()+"-SP", "Batal Terima SP Barang Cabang", s.getNoSP(), -pembulatan(totalNilaiJadi), user.getKodeUser());
                insertKeuanganCabang(con, noKeuangan, s.getKodeCabang(), "Kasir", "Beban Penyusutan SP Barang", "Batal Terima SP Barang Cabang", s.getNoSP(), pembulatan(totalNilai-totalNilaiJadi), user.getKodeUser());
            }
            
            for(SPDetail d : listSpDetail){
                d.setBeratPenyusutan(0);
                d.setNilaiPenyusutan(0);
                SPDetailDAO.update(con, d);
            }
            s.setTotalBeratPenyusutan(0);
            s.setTotalNilaiPenyusutan(0);
            SPHeadDAO.update(con, s);
            
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
    
    public static String saveRevisiBarangCabang(Connection con, RevisiBarangCabang r)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            if(r.getJenisRevisi().equals("Ubah Jenis")){
                RevisiBarangCabangDAO.insert(con, r);
                status = updateStokBarangNonBarcodeKeluar(con, r.getKodeGudang(), r.getKodeJenis(),  
                        r.getQtyRevisi(), r.getBeratRevisi(), r.getBeratPersen(), r.getNilaiRevisi(), status);
                status = updateStokBarangNonBarcodeMasuk(con, r.getKodeCabang(), r.getKodeGudang(), r.getKodeKategoriRevisi(), r.getKodeJenisRevisi(), 
                        r.getQtyRevisi(), r.getBeratRevisi(), r.getBeratPersenRevisi(), r.getNilaiRevisi(), status);
            }else if(r.getJenisRevisi().equals("Penambahan Stok")){
                RevisiBarangCabangDAO.insert(con, r);
                status = updateStokBarangNonBarcodeMasuk(con, r.getKodeCabang(), r.getKodeGudang(), r.getKodeKategori(), r.getKodeJenis(), r.getQtyRevisi(), 0, 0, 0, status);
            }else if(r.getJenisRevisi().equals("Pengurangan Stok")){
                status = updateStokBarangNonBarcodeKeluar(con, r.getKodeGudang(), r.getKodeJenis(),  
                        r.getQtyRevisi(), 0, 0, 0, status);
                RevisiBarangCabangDAO.insert(con, r);
            }else if(r.getJenisRevisi().equals("Penambahan Berat")){
                status = updateStokBarangNonBarcodeMasuk(con, r.getKodeCabang(), r.getKodeGudang(), r.getKodeKategori(), r.getKodeJenis(), 
                        0, r.getBeratRevisi(), r.getBeratPersenRevisi(), 0, status);
                RevisiBarangCabangDAO.insert(con, r);
            }else if(r.getJenisRevisi().equals("Pengurangan Berat")){
                status = updateStokBarangNonBarcodeKeluar(con, r.getKodeGudang(), r.getKodeJenis(),  0, r.getBeratRevisi(), r.getBeratPersenRevisi(), 0, status);
                RevisiBarangCabangDAO.insert(con, r);
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
    public static String saveBatalRevisiBarangCabang(Connection con, RevisiBarangCabang r)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            RevisiBarangCabangDAO.update(con, r);
            if(r.getTglRevisi().substring(0,10).equals(sistem.getTglSystem())){
                if(r.getJenisRevisi().equals("Ubah Jenis")){
                    status = updateStokBarangNonBarcodeBatalKeluar(con, r.getKodeGudang(), r.getKodeJenis(),  
                            r.getQtyRevisi(), r.getBeratRevisi(), r.getBeratPersen(), r.getNilaiRevisi(), status);
                    status = updateStokBarangNonBarcodeBatalMasuk(con, r.getKodeGudang(), r.getKodeJenisRevisi(), 
                            r.getQtyRevisi(), r.getBeratRevisi(), r.getBeratPersenRevisi(), r.getNilaiRevisi(), status);
                }else if(r.getJenisRevisi().equals("Penambahan Stok")){
                    status = updateStokBarangNonBarcodeBatalMasuk(con, r.getKodeGudang(), r.getKodeJenis(), r.getQtyRevisi(), 0, 0, 0, status);
                }else if(r.getJenisRevisi().equals("Pengurangan Stok")){
                    status = updateStokBarangNonBarcodeBatalKeluar(con, r.getKodeGudang(), r.getKodeJenis(), r.getQtyRevisi(), 0, 0, 0, status);
                }else if(r.getJenisRevisi().equals("Penambahan Berat")){
                    status = updateStokBarangNonBarcodeBatalMasuk(con, r.getKodeGudang(), r.getKodeJenis(), 0, r.getBeratRevisi(), r.getBeratPersenRevisi(), 0, status);
                }else if(r.getJenisRevisi().equals("Pengurangan Berat")){
                    status = updateStokBarangNonBarcodeBatalKeluar(con, r.getKodeGudang(), r.getKodeJenis(),  0, r.getBeratRevisi(), r.getBeratPersenRevisi(), 0, status);
                }
            }else{
                if(r.getJenisRevisi().equals("Ubah Jenis")){
                    status = updateStokBarangNonBarcodeMasuk(con, r.getKodeCabang(), r.getKodeGudang(), r.getKodeKategori(), r.getKodeJenis(),  
                            r.getQtyRevisi(), r.getBeratRevisi(), r.getBeratPersen(), r.getNilaiRevisi(), status);
                    status = updateStokBarangNonBarcodeKeluar(con, r.getKodeGudang(), r.getKodeJenisRevisi(), 
                            r.getQtyRevisi(), r.getBeratRevisi(), r.getBeratPersenRevisi(), r.getNilaiRevisi(), status);
                }else if(r.getJenisRevisi().equals("Penambahan Stok")){
                    status = updateStokBarangNonBarcodeKeluar(con, r.getKodeGudang(), r.getKodeJenis(), r.getQtyRevisi(), 0, 0, 0, status);
                }else if(r.getJenisRevisi().equals("Pengurangan Stok")){
                    status = updateStokBarangNonBarcodeMasuk(con, r.getKodeCabang(), r.getKodeGudang(), r.getKodeKategori(), r.getKodeJenis(), r.getQtyRevisi(), 0, 0, 0, status);
                }else if(r.getJenisRevisi().equals("Penambahan Berat")){
                    status = updateStokBarangNonBarcodeKeluar(con, r.getKodeGudang(), r.getKodeJenis(), 0, r.getBeratRevisi(), r.getBeratPersenRevisi(), 0, status);
                }else if(r.getJenisRevisi().equals("Pengurangan Berat")){
                    status = updateStokBarangNonBarcodeMasuk(con, r.getKodeCabang(), r.getKodeGudang(), r.getKodeKategori(), r.getKodeJenis(),  0, r.getBeratRevisi(), r.getBeratPersenRevisi(), 0, status);
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
    
    public static String saveRosokBarangCabang(Connection con, RosokCabang r)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            RosokCabangDAO.insert(con, r);
            if(r.getKategori().equals("Pindah Rosok Cabang")){
                status = updateStokBarangNonBarcodeKeluar(con, r.getKodeGudang(), r.getKodeJenis(), r.getQty(), r.getBerat(), r.getBeratPersen(), r.getNilaiPokok(), status);
                status = updateStokRosokCiokMasuk(con, r.getKodeCabang(), r.getKodeCabang()+"-Rosok", r.getBerat(), r.getBeratPersenRosok(), r.getNilaiPokok(), status);

                String noKeuangan = KeuanganCabangDAO.getId(con, r.getKodeCabang());
                insertKeuanganCabang(con, noKeuangan, r.getKodeCabang(), "Kasir", r.getKodeGudang(), "Pindah Rosok Cabang", r.getNoRosok(), -r.getNilaiPokok(), user.getKodeUser());
                insertKeuanganCabang(con, noKeuangan, r.getKodeCabang(), "Kasir", r.getKodeCabang()+"-Rosok", "Pindah Rosok Cabang", r.getNoRosok(), r.getNilaiPokok(), user.getKodeUser());
            }else if(r.getKategori().equals("Ambil Rosok Cabang")){
                status = updateStokRosokCiokKeluar(con, r.getKodeCabang(), r.getKodeCabang()+"-Rosok", r.getBerat(), r.getBeratPersenRosok(), r.getNilaiPokok(), status);
                status = updateStokBarangNonBarcodeMasuk(con, r.getKodeCabang(), r.getKodeGudang(), r.getKodeKategori(), r.getKodeJenis(), 
                        r.getQty(), r.getBerat(), r.getBeratPersen(), r.getNilaiPokok(), status);
                
                String noKeuangan = KeuanganCabangDAO.getId(con, r.getKodeCabang());
                insertKeuanganCabang(con, noKeuangan, r.getKodeCabang(), "Kasir", r.getKodeCabang()+"-Rosok", "Ambil Rosok Cabang", r.getNoRosok(), -r.getNilaiPokok(), user.getKodeUser());
                insertKeuanganCabang(con, noKeuangan, r.getKodeCabang(), "Kasir", r.getKodeGudang(), "Ambil Rosok Cabang", r.getNoRosok(), r.getNilaiPokok(), user.getKodeUser());
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
    public static String saveBatalRosokBarangCabang(Connection con, RosokCabang r)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            RosokCabangDAO.update(con, r);
            if(r.getTglRosok().substring(0,10).equals(sistem.getTglSystem())){
                if(r.getKategori().equals("Pindah Rosok Cabang")){
                    status = updateStokBarangNonBarcodeBatalKeluar(con, r.getKodeGudang(), r.getKodeJenis(), 
                            r.getQty(), r.getBerat(), r.getBeratPersen(), r.getNilaiPokok(), status);
                    status = updateStokRosokCiokBatalMasuk(con, r.getKodeCabang(), r.getKodeCabang()+"-Rosok", 
                            r.getBerat(), r.getBeratPersenRosok(), r.getNilaiPokok(), status);

                    KeuanganCabang k = KeuanganCabangDAO.get(con, r.getKodeCabang(), "Kasir", r.getKodeCabang()+"-Rosok", "Pindah Rosok Cabang", r.getNoRosok());
                    KeuanganCabangDAO.deleteAll(con, k.getNoKeuangan());
                }else if(r.getKategori().equals("Ambil Rosok Cabang")){
                    status = updateStokRosokCiokBatalKeluar(con, r.getKodeCabang(), r.getKodeCabang()+"-Rosok", 
                            r.getBerat(), r.getBeratPersenRosok(), r.getNilaiPokok(), status);
                    status = updateStokBarangNonBarcodeBatalMasuk(con, r.getKodeGudang(), r.getKodeJenis(), 
                            r.getQty(), r.getBerat(), r.getBeratPersen(), r.getNilaiPokok(), status);

                    KeuanganCabang k = KeuanganCabangDAO.get(con, r.getKodeCabang(), "Kasir", r.getKodeCabang()+"-Rosok", "Ambil Rosok Cabang", r.getNoRosok());
                    KeuanganCabangDAO.deleteAll(con, k.getNoKeuangan());
                }
            }else{
                if(r.getKategori().equals("Pindah Rosok Cabang")){
                    status = updateStokBarangNonBarcodeMasuk(con, r.getKodeCabang(), r.getKodeGudang(), r.getKodeKategori(), r.getKodeJenis(), 
                            r.getQty(), r.getBerat(), r.getBeratPersen(), r.getNilaiPokok(), status);
                    status = updateStokRosokCiokKeluar(con, r.getKodeCabang(), r.getKodeCabang()+"-Rosok", r.getBerat(), r.getBeratPersenRosok(), r.getNilaiPokok(), status);

                    String noKeuangan = KeuanganCabangDAO.getId(con, r.getKodeCabang());
                    insertKeuanganCabang(con, noKeuangan, r.getKodeCabang(), "Kasir", r.getKodeGudang(), "Batal Pindah Rosok Cabang", r.getNoRosok(), r.getNilaiPokok(), user.getKodeUser());
                    insertKeuanganCabang(con, noKeuangan, r.getKodeCabang(), "Kasir", r.getKodeCabang()+"-Rosok", "Batal Pindah Rosok Cabang", r.getNoRosok(), -r.getNilaiPokok(), user.getKodeUser());
                }else if(r.getKategori().equals("Ambil Rosok Cabang")){
                    status = updateStokRosokCiokMasuk(con, r.getKodeCabang(), r.getKodeCabang()+"-Rosok", r.getBerat(), r.getBeratPersenRosok(), r.getNilaiPokok(), status);
                    status = updateStokBarangNonBarcodeKeluar(con, r.getKodeGudang(), r.getKodeJenis(), 
                            r.getQty(), r.getBerat(), r.getBeratPersen(), r.getNilaiPokok(), status);

                    String noKeuangan = KeuanganCabangDAO.getId(con, r.getKodeCabang());
                    insertKeuanganCabang(con, noKeuangan, r.getKodeCabang(), "Kasir", r.getKodeCabang()+"-Rosok", "Batal Ambil Rosok Cabang", r.getNoRosok(), r.getNilaiPokok(), user.getKodeUser());
                    insertKeuanganCabang(con, noKeuangan, r.getKodeCabang(), "Kasir", r.getKodeGudang(), "Batal Ambil Rosok Cabang", r.getNoRosok(), -r.getNilaiPokok(), user.getKodeUser());
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
    
    public static String saveLeburRosokCabang(Connection con, LeburRosokCabang r)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            LeburRosokCabangDAO.insert(con, r);
            status = updateStokRosokCiokKeluar(con, r.getKodeCabang(), r.getKodeCabang()+"-Rosok", r.getBeratKotor(), r.getBeratPersen(), r.getNilaiPokok(), status);
            status = updateStokRosokCiokMasuk(con, r.getKodeCabang(), r.getKodeCabang()+"-Lebur", r.getBeratKotor(), r.getBeratPersen(), r.getNilaiPokok(), status);

            String noKeuangan = KeuanganCabangDAO.getId(con, r.getKodeCabang());
            insertKeuanganCabang(con, noKeuangan, r.getKodeCabang(), "Kasir", r.getKodeCabang()+"-Rosok", "Lebur Rosok Cabang", r.getNoLebur(), -r.getNilaiPokok(), user.getKodeUser());
            insertKeuanganCabang(con, noKeuangan, r.getKodeCabang(), "Kasir", r.getKodeCabang()+"-Lebur", "Lebur Rosok Cabang", r.getNoLebur(), r.getNilaiPokok(), user.getKodeUser());
            
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
    public static String saveBatalLeburRosokCabang(Connection con, LeburRosokCabang r)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            LeburRosokCabangDAO.update(con, r);
            if(r.getTglLebur().substring(0,10).equals(sistem.getTglSystem())){
                status = updateStokRosokCiokBatalKeluar(con, r.getKodeCabang(), r.getKodeCabang()+"-Rosok", r.getBeratKotor(), r.getBeratPersen(), r.getNilaiPokok(), status);
                status = updateStokRosokCiokBatalMasuk(con, r.getKodeCabang(), r.getKodeCabang()+"-Lebur", r.getBeratKotor(), r.getBeratPersen(), r.getNilaiPokok(), status);

                KeuanganCabang k = KeuanganCabangDAO.get(con, r.getKodeCabang(), "Kasir", r.getKodeCabang()+"-Lebur", "Lebur Rosok Cabang", r.getNoLebur());
                KeuanganCabangDAO.deleteAll(con, k.getNoKeuangan());
            }else{
                status = updateStokRosokCiokMasuk(con, r.getKodeCabang(), r.getKodeCabang()+"-Rosok", r.getBeratKotor(), r.getBeratPersen(), r.getNilaiPokok(), status);
                status = updateStokRosokCiokKeluar(con, r.getKodeCabang(), r.getKodeCabang()+"-Lebur", r.getBeratKotor(), r.getBeratPersen(), r.getNilaiPokok(), status);
                
                String noKeuangan = KeuanganCabangDAO.getId(con, r.getKodeCabang());
                insertKeuanganCabang(con, noKeuangan, r.getKodeCabang(), "Kasir", r.getKodeCabang()+"-Rosok", "Batal Lebur Rosok Cabang", r.getNoLebur(), r.getNilaiPokok(), user.getKodeUser());
                insertKeuanganCabang(con, noKeuangan, r.getKodeCabang(), "Kasir", r.getKodeCabang()+"-Lebur", "Batal Lebur Rosok Cabang", r.getNoLebur(), -r.getNilaiPokok(), user.getKodeUser());
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
    
    public static String saveSelesaiLeburRosokCabang(Connection con, LeburRosokCabang r)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            LeburRosokCabangDAO.update(con, r);
            status = updateStokRosokCiokKeluar(con, r.getKodeCabang(), r.getKodeCabang()+"-Lebur", r.getBeratKotor(), r.getBeratPersen(), r.getNilaiPokok(), status);
            status = updateStokRosokCiokMasuk(con, r.getKodeCabang(), r.getKodeCabang()+"-Ciok", r.getBeratJadi(), r.getBeratJadi(), r.getNilaiPokok(), status);

            String noKeuangan = KeuanganCabangDAO.getId(con, r.getKodeCabang());
            insertKeuanganCabang(con, noKeuangan, r.getKodeCabang(), "Kasir", r.getKodeCabang()+"-Lebur", "Selesai Lebur Rosok Cabang", r.getNoLebur(), -r.getNilaiPokok(), user.getKodeUser());
            insertKeuanganCabang(con, noKeuangan, r.getKodeCabang(), "Kasir", r.getKodeCabang()+"-Ciok", "Selesai Lebur Rosok Cabang", r.getNoLebur(), r.getNilaiPokok(), user.getKodeUser());
            
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
    public static String saveBatalSelesaiLeburRosokCabang(Connection con, LeburRosokCabang r)throws Exception{
        try{
            String status = "true";
            con.setAutoCommit(false);
            
            LeburRosokCabangDAO.update(con, r);
            if(r.getTglLebur().substring(0,10).equals(sistem.getTglSystem())){
                status = updateStokRosokCiokBatalKeluar(con, r.getKodeCabang(), r.getKodeCabang()+"-Lebur", r.getBeratKotor(), r.getBeratPersen(), r.getNilaiPokok(), status);
                status = updateStokRosokCiokBatalMasuk(con, r.getKodeCabang(), r.getKodeCabang()+"-Ciok", r.getBeratJadi(), r.getBeratJadi(), r.getNilaiPokok(), status);

                KeuanganCabang k = KeuanganCabangDAO.get(con, r.getKodeCabang(), "Kasir", r.getKodeCabang()+"-Lebur", "Selesai Lebur Rosok Cabang", r.getNoLebur());
                KeuanganCabangDAO.deleteAll(con, k.getNoKeuangan());
            }else{
                status = updateStokRosokCiokMasuk(con, r.getKodeCabang(), r.getKodeCabang()+"-Lebur", r.getBeratKotor(), r.getBeratPersen(), r.getNilaiPokok(), status);
                status = updateStokRosokCiokKeluar(con, r.getKodeCabang(), r.getKodeCabang()+"-Ciok", r.getBeratJadi(), r.getBeratJadi(), r.getNilaiPokok(), status);
                
                String noKeuangan = KeuanganCabangDAO.getId(con, r.getKodeCabang());
                insertKeuanganCabang(con, noKeuangan, r.getKodeCabang(), "Kasir", r.getKodeCabang()+"-Lebur", "Batal Selesai Lebur Rosok Cabang", r.getNoLebur(), r.getNilaiPokok(), user.getKodeUser());
                insertKeuanganCabang(con, noKeuangan, r.getKodeCabang(), "Kasir", r.getKodeCabang()+"-Ciok", "Batal Selesai Lebur Rosok Cabang", r.getNoLebur(), -r.getNilaiPokok(), user.getKodeUser());
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
    
    public static String savePenjualanCiokCabang(Connection conPusat, PenjualanCiokCabang p, boolean bayarHutang)throws Exception{
        try{
            String status = "true";
            conPusat.setAutoCommit(false);
            
            String noKeuanganPusat = KeuanganPusatDAO.getId(conPusat);
            String noKeuangan = KeuanganCabangDAO.getId(conPusat, p.getKodeCabang());
            
            PenjualanCiokCabangDAO.update(conPusat, p);
            status = updateStokRosokCiokKeluar(conPusat, p.getKodeCabang(), p.getKodeCabang()+"-Ciok", p.getBerat(), p.getBerat(), p.getNilaiPokok(), status);
            
            insertKeuanganCabang(conPusat, noKeuangan, p.getKodeCabang(), "Kasir", "Bank", 
                    "Penjualan Ciok Cabang", p.getNoPenjualan(), p.getTotalPenjualan(), p.getKodeUser());
            insertKeuanganCabang(conPusat, noKeuangan, p.getKodeCabang(), "Kasir", p.getKodeCabang()+"-Ciok", 
                    "Penjualan Ciok Cabang", p.getNoPenjualan(), -p.getNilaiPokok(), p.getKodeUser());
            insertKeuanganCabang(conPusat, noKeuangan, p.getKodeCabang(), "Kasir", "Penjualan Ciok", 
                    "Penjualan Ciok Cabang", p.getNoPenjualan(), p.getTotalPenjualan(), p.getKodeUser());
            insertKeuanganCabang(conPusat, noKeuangan, p.getKodeCabang(), "Kasir", "HPP Penjualan Ciok", 
                    "Penjualan Ciok Cabang", p.getNoPenjualan(), -p.getNilaiPokok(), p.getKodeUser());

            if(bayarHutang){
                double bayarRp = p.getTotalPenjualan();
                List<Piutang> allPiutang = PiutangDAO.getAllByDateAndCabangAndTipeKasirAndKategoriAndStatus(conPusat, 
                        "2000-01-01", sistem.getTglSystem(), p.getKodeCabang(), "Kasir", "%", "true");
                for(Piutang pt : allPiutang){
                    double kursEmas = 1;
                    if(pt.getKategori().equals("Hutang Pembelian"))
                        kursEmas =  sistem.getHargaEmas();
                    
                    double jumlahPiutangSekarang = pt.getSisaPiutang() / pt.getKurs() * kursEmas;
                    bayarRp = bayarRp - jumlahPiutangSekarang;
                    if(bayarRp >= 0){//if setor melebihi piutang = piutang lunas
                        double jumlahBayar = jumlahPiutangSekarang;
                        double jumlahTerbayar = pt.getSisaPiutang();
                        
                        pt.setTerbayar(pt.getTerbayar()+jumlahTerbayar);
                        pt.setSisaPiutang(pt.getSisaPiutang()-jumlahTerbayar);
                        pt.setStatus("false");
                        PiutangDAO.update(conPusat, pt);
                        
                        PembayaranPiutang pb = new PembayaranPiutang();
                        pb.setNoPembayaran(PembayaranPiutangDAO.getId(conPusat));
                        pb.setTglPembayaran(Function.getSystemDate());
                        pb.setNoPiutang(pt.getNoPiutang());
                        pb.setJumlahBayar(jumlahBayar);
                        pb.setKurs(kursEmas);
                        pb.setTerbayar(jumlahTerbayar);
                        pb.setKodeUser(user.getKodeUser());
                        pb.setStatus("true");
                        pb.setTglBatal("2000-01-01 00:00:00");                        
                        pb.setUserBatal("");
                        PembayaranPiutangDAO.insert(conPusat, pb);

                        double selisihKurs = jumlahTerbayar-jumlahBayar;
                        
                        insertKeuanganCabang(conPusat, noKeuangan, p.getKodeCabang(), "Kasir", "Bank" ,
                                "Pembayaran Hutang", pb.getNoPembayaran(), -jumlahBayar, user.getKodeUser());
                        insertKeuanganCabang(conPusat, noKeuangan, p.getKodeCabang(), "Kasir", pt.getKategori() ,
                                "Pembayaran Hutang", pb.getNoPembayaran(), -jumlahTerbayar, user.getKodeUser());
                        insertKeuanganCabang(conPusat, noKeuangan, p.getKodeCabang(), "Kasir", "Pendapatan/Beban Kurs Emas" ,
                                "Pembayaran Hutang", pb.getNoPembayaran(), selisihKurs, user.getKodeUser());
                        
                        String kategoriPusat = "";
                        if(pt.getKategori().equals("Hutang Modal"))
                            kategoriPusat = "Piutang Modal";
                        else if(pt.getKategori().equals("Hutang Pembelian"))
                            kategoriPusat = "Piutang Penjualan";
                        insertKeuanganPusat(conPusat, noKeuanganPusat, "Bank", "Terima Pembayaran "+kategoriPusat+" "+p.getKodeCabang()+"-Kasir", 
                                pb.getNoPembayaran(), jumlahBayar, user.getKodeUser());
                        insertKeuanganPusat(conPusat, noKeuanganPusat, kategoriPusat, "Terima Pembayaran "+kategoriPusat+" "+p.getKodeCabang()+"-Kasir", 
                                pb.getNoPembayaran(), -jumlahTerbayar, user.getKodeUser());
                        insertKeuanganPusat(conPusat, noKeuanganPusat, "Pendapatan/Beban Kurs Emas", "Terima Pembayaran "+kategoriPusat+" "+p.getKodeCabang()+"-Kasir", 
                                pb.getNoPembayaran(), -selisihKurs, user.getKodeUser());
                    }else if(bayarRp < 0){// if setor kurang dari piutang = piutang tidak lunas
                        double jumlahBayar = bayarRp + jumlahPiutangSekarang;
                        double jumlahTerbayar = jumlahBayar / kursEmas * pt.getKurs();
                        
                        pt.setTerbayar(pt.getTerbayar()+jumlahTerbayar);
                        pt.setSisaPiutang(pt.getSisaPiutang()-jumlahTerbayar);
                        PiutangDAO.update(conPusat, pt);

                        PembayaranPiutang pb = new PembayaranPiutang();
                        pb.setNoPembayaran(PembayaranPiutangDAO.getId(conPusat));
                        pb.setTglPembayaran(Function.getSystemDate());
                        pb.setNoPiutang(pt.getNoPiutang());
                        pb.setJumlahBayar(jumlahBayar);
                        pb.setKurs(kursEmas);
                        pb.setTerbayar(jumlahTerbayar);
                        pb.setKodeUser(user.getKodeUser());
                        pb.setStatus("true");
                        pb.setTglBatal("2000-01-01 00:00:00");                        
                        pb.setUserBatal("");
                        PembayaranPiutangDAO.insert(conPusat, pb);
                        
                        double selisihKurs = jumlahTerbayar-jumlahBayar;
                        
                        insertKeuanganCabang(conPusat, noKeuangan, p.getKodeCabang(), "Kasir", "Bank" ,
                                "Pembayaran Hutang", pb.getNoPembayaran(), -jumlahBayar, user.getKodeUser());
                        insertKeuanganCabang(conPusat, noKeuangan, p.getKodeCabang(), "Kasir", pt.getKategori() ,
                                "Pembayaran Hutang", pb.getNoPembayaran(), -jumlahTerbayar, user.getKodeUser());
                        insertKeuanganCabang(conPusat, noKeuangan, p.getKodeCabang(), "Kasir", "Pendapatan/Beban Kurs Emas" ,
                                "Pembayaran Hutang", pb.getNoPembayaran(), selisihKurs, user.getKodeUser());
                        
                        String kategoriPusat = "";
                        if(pt.getKategori().equals("Hutang Modal"))
                            kategoriPusat = "Piutang Modal";
                        else if(pt.getKategori().equals("Hutang Pembelian"))
                            kategoriPusat = "Piutang Penjualan";
                        insertKeuanganPusat(conPusat, noKeuanganPusat, "Bank", "Terima Pembayaran "+kategoriPusat+" "+p.getKodeCabang()+"-Kasir", 
                                pb.getNoPembayaran(), jumlahBayar, user.getKodeUser());
                        insertKeuanganPusat(conPusat, noKeuanganPusat, kategoriPusat, "Terima Pembayaran "+kategoriPusat+" "+p.getKodeCabang()+"-Kasir", 
                                pb.getNoPembayaran(), -jumlahTerbayar, user.getKodeUser());
                        insertKeuanganPusat(conPusat, noKeuanganPusat, "Pendapatan/Beban Kurs Emas", "Terima Pembayaran "+kategoriPusat+" "+p.getKodeCabang()+"-Kasir", 
                                pb.getNoPembayaran(), -selisihKurs, user.getKodeUser());
                        
                        break;
                    }
                }
            }
            
            if(status.equals("true")){
                conPusat.commit();
            }else{
                conPusat.rollback();
            }
            conPusat.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                e.printStackTrace();
                conPusat.rollback();
                conPusat.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String saveHancurBarangCabang(Connection conPusat, Connection conCabang, HancurHead h)throws Exception{
        try{
            String status = "true";
            conPusat.setAutoCommit(false);
            conCabang.setAutoCommit(false);
            
            SistemCabang sistemCabang = SistemCabangDAO.get(conCabang);
            if(!sistemCabang.getTglSystem().equals(sistem.getTglSystem()))
                status = "Tanggal sistem cabang berbeda dengan tanggal sistem pusat";
            else{
                HancurHeadDAO.insert(conPusat, h);
                double totalBerat = 0;
                double totalBeratPersen = 0;
                double totalNilai = 0;
                for(HancurDetail d : h.getListHancurDetail()){
                    Barang b = BarangDAO.get(conCabang, d.getKodeBarcode());
                    if(b!=null){
                        b.setStatus("false");
                        BarangDAO.update(conCabang, b);
                        
                        StokBarangDiCabang s = StokBarangDiCabangDAO.getBarcode(conCabang, b.getKodeBarcode());
                        if(s==null)
                            status = "Stok barang dengan kode barcode "+b.getKodeBarcode()+" tidak ditemukan";
                        else if(s.getStokAkhir()<1)
                            status = "Stok barang dengan kode barcode "+b.getKodeBarcode()+" tidak mencukupi";
                        else if(s.getBeratAkhir()<b.getBerat())
                            status = "Berat barang dengan kode barcode "+b.getKodeBarcode()+" tidak mencukupi";
                        else if(s.getBeratPersenAkhir()<b.getBeratPersen())
                            status = "Berat persen barang dengan kode barcode "+b.getKodeBarcode()+" tidak mencukupi";
                        else if(s.getNilaiAkhir()<b.getNilaiPokok())
                            status = "Nilai barang dengan kode barcode "+b.getKodeBarcode()+" tidak mencukupi";
                        else{
                            s.setStokKeluar(s.getStokKeluar()+1);
                            s.setBeratKeluar(pembulatan(s.getBeratKeluar()+b.getBerat()));
                            s.setBeratPersenKeluar(pembulatan(s.getBeratPersenKeluar()+b.getBeratPersen()));
                            s.setNilaiKeluar(pembulatan(s.getNilaiKeluar()+b.getNilaiPokok()));

                            s.setStokAkhir(s.getStokAkhir()-1);
                            s.setBeratAkhir(pembulatan(s.getBeratAkhir()-b.getBerat()));
                            s.setBeratPersenAkhir(pembulatan(s.getBeratPersenAkhir()-b.getBeratPersen()));
                            s.setNilaiAkhir(pembulatan(s.getNilaiAkhir()-b.getNilaiPokok()));
                            StokBarangDiCabangDAO.update(conCabang, s);
                        }
                        
                        HancurDetailDAO.insert(conPusat, d);
                        
                        totalBerat = totalBerat + b.getBerat();
                        totalBeratPersen = totalBeratPersen + b.getBeratPersen();
                        totalNilai = totalNilai + b.getNilaiPokok();
                    }else{
                        status = "Barang cabang tidak ditemukan";
                    }
                }
                status = updateStokRosokCiokMasuk(conPusat, h.getKodeCabang(), h.getKodeCabang()+"-Rosok", totalBerat, totalBeratPersen, totalNilai, status);
                
                String noKeuangan = KeuanganCabangDAO.getId(conPusat, h.getKodeCabang());
                
                Keuangan k = new Keuangan();
                k.setNoKeuangan(noKeuangan);
                k.setNoUrut(1);
                k.setTglKeuangan(Function.getSystemDate());
                k.setTipeKasir("Kasir");
                k.setTipeKeuangan(h.getKodeCabang());
                k.setKategori("Hancur Barang Cabang");
                k.setKeterangan(h.getNoHancur());
                k.setJumlahRp(-totalNilai);
                k.setKodeSales(user.getKodeUser());
                KeuanganDAO.insert(conCabang, k);
                
                insertKeuanganCabang(conPusat, noKeuangan, h.getKodeCabang(), "Kasir", h.getKodeCabang()+"-Rosok", "Hancur Barang Cabang", 
                        h.getNoHancur(), totalNilai, user.getKodeUser());
                
            }
            
            if(status.equals("true")){
                conPusat.commit();
                conCabang.commit();
            }else{
                conPusat.rollback();
                conCabang.rollback();
            }
            conPusat.setAutoCommit(true);
            conCabang.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                conPusat.rollback();
                conCabang.rollback();
                conPusat.setAutoCommit(true);
                conCabang.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String saveTerimaSetoranCabang(Connection conPusat, SetoranCabang s, boolean bayarHutang)throws Exception{
        try{
            String status = "true";
            conPusat.setAutoCommit(false);
            
            SetoranCabangDAO.update(conPusat, s);
            
            String noKeuanganPusat = KeuanganPusatDAO.getId(conPusat);
            String noKeuangan = KeuanganCabangDAO.getId(conPusat, s.getKodeCabang());
            insertKeuanganCabang(conPusat, noKeuangan, s.getKodeCabang(), s.getTipeKasir(), "Bank", 
                    "Terima Setoran Cabang", s.getNoSetor(), s.getJumlahRp(), s.getKodeUser());
            insertKeuanganCabang(conPusat, noKeuangan, s.getKodeCabang(), s.getTipeKasir(), "Bank-"+s.getKodeCabang(), 
                    "Terima Setoran Cabang", s.getNoSetor(), -s.getJumlahRp(), s.getKodeUser());
            
            if(bayarHutang){
                double bayarRp = s.getJumlahRp();
                List<Piutang> allPiutang = PiutangDAO.getAllByDateAndCabangAndTipeKasirAndKategoriAndStatus(conPusat, 
                        "2000-01-01", sistem.getTglSystem(), s.getKodeCabang(), s.getTipeKasir(), "%","true");
                for(Piutang p : allPiutang){
                    double kursEmas = 1;
                    if(p.getKategori().equals("Hutang Pembelian"))
                        kursEmas =  sistem.getHargaEmas();
                    
                    double jumlahPiutangSekarang = p.getSisaPiutang() / p.getKurs() * kursEmas;
                    bayarRp = bayarRp - jumlahPiutangSekarang;
                    if(bayarRp >= 0){//if setor melebihi piutang = piutang lunas
                        double jumlahBayar = jumlahPiutangSekarang;
                        double jumlahTerbayar = p.getSisaPiutang();
                        
                        p.setTerbayar(p.getTerbayar()+jumlahTerbayar);
                        p.setSisaPiutang(p.getSisaPiutang()-jumlahTerbayar);
                        p.setStatus("false");
                        PiutangDAO.update(conPusat, p);
                        
                        PembayaranPiutang pb = new PembayaranPiutang();
                        pb.setNoPembayaran(PembayaranPiutangDAO.getId(conPusat));
                        pb.setTglPembayaran(Function.getSystemDate());
                        pb.setNoPiutang(p.getNoPiutang());
                        pb.setJumlahBayar(jumlahBayar);
                        pb.setKurs(kursEmas);
                        pb.setTerbayar(jumlahTerbayar);
                        pb.setKodeUser(user.getKodeUser());
                        pb.setStatus("true");
                        pb.setTglBatal("2000-01-01 00:00:00");                        
                        pb.setUserBatal("");
                        PembayaranPiutangDAO.insert(conPusat, pb);

                        double selisihKurs = jumlahTerbayar-jumlahBayar;
                        
                        insertKeuanganCabang(conPusat, noKeuangan, s.getKodeCabang(), s.getTipeKasir(), "Bank" ,
                                "Pembayaran Hutang", pb.getNoPembayaran(), -jumlahBayar, user.getKodeUser());
                        insertKeuanganCabang(conPusat, noKeuangan, s.getKodeCabang(), s.getTipeKasir(), p.getKategori() ,
                                "Pembayaran Hutang", pb.getNoPembayaran(), -jumlahTerbayar, user.getKodeUser());
                        insertKeuanganCabang(conPusat, noKeuangan, s.getKodeCabang(), s.getTipeKasir(), "Pendapatan/Beban Kurs Emas" ,
                                "Pembayaran Hutang", pb.getNoPembayaran(), selisihKurs, user.getKodeUser());
                        
                        String kategoriPusat = "";
                        if(p.getKategori().equals("Hutang Modal"))
                            kategoriPusat = "Piutang Modal";
                        else if(p.getKategori().equals("Hutang Pembelian"))
                            kategoriPusat = "Piutang Penjualan";
                        insertKeuanganPusat(conPusat, noKeuanganPusat, "Bank", "Terima Pembayaran "+kategoriPusat+" "+s.getKodeCabang()+"-"+s.getTipeKasir(), 
                                pb.getNoPembayaran(), jumlahBayar, user.getKodeUser());
                        insertKeuanganPusat(conPusat, noKeuanganPusat, kategoriPusat, "Terima Pembayaran "+kategoriPusat+" "+s.getKodeCabang()+"-"+s.getTipeKasir(), 
                                pb.getNoPembayaran(), -jumlahTerbayar, user.getKodeUser());
                        insertKeuanganPusat(conPusat, noKeuanganPusat, "Pendapatan/Beban Kurs Emas", "Terima Pembayaran "+kategoriPusat+" "+s.getKodeCabang()+"-"+s.getTipeKasir(), 
                                pb.getNoPembayaran(), -selisihKurs, user.getKodeUser());
                    }else if(bayarRp < 0){// if setor kurang dari piutang = piutang tidak lunas
                        double jumlahBayar = bayarRp + jumlahPiutangSekarang;
                        double jumlahTerbayar = jumlahBayar / kursEmas * p.getKurs();
                        
                        p.setTerbayar(p.getTerbayar()+jumlahTerbayar);
                        p.setSisaPiutang(p.getSisaPiutang()-jumlahTerbayar);
                        PiutangDAO.update(conPusat, p);

                        PembayaranPiutang pb = new PembayaranPiutang();
                        pb.setNoPembayaran(PembayaranPiutangDAO.getId(conPusat));
                        pb.setTglPembayaran(Function.getSystemDate());
                        pb.setNoPiutang(p.getNoPiutang());
                        pb.setJumlahBayar(jumlahBayar);
                        pb.setKurs(kursEmas);
                        pb.setTerbayar(jumlahTerbayar);
                        pb.setKodeUser(user.getKodeUser());
                        pb.setStatus("true");
                        pb.setTglBatal("2000-01-01 00:00:00");                        
                        pb.setUserBatal("");
                        PembayaranPiutangDAO.insert(conPusat, pb);
                        
                        double selisihKurs = jumlahTerbayar-jumlahBayar;
                        
                        insertKeuanganCabang(conPusat, noKeuangan, s.getKodeCabang(), s.getTipeKasir(), "Bank" ,
                                "Pembayaran Hutang", pb.getNoPembayaran(), -jumlahBayar, user.getKodeUser());
                        insertKeuanganCabang(conPusat, noKeuangan, s.getKodeCabang(), s.getTipeKasir(), p.getKategori() ,
                                "Pembayaran Hutang", pb.getNoPembayaran(), -jumlahTerbayar, user.getKodeUser());
                        insertKeuanganCabang(conPusat, noKeuangan, s.getKodeCabang(), s.getTipeKasir(), "Pendapatan/Beban Kurs Emas" ,
                                "Pembayaran Hutang", pb.getNoPembayaran(), selisihKurs, user.getKodeUser());
                        
                        String kategoriPusat = "";
                        if(p.getKategori().equals("Hutang Modal"))
                            kategoriPusat = "Piutang Modal";
                        else if(p.getKategori().equals("Hutang Pembelian"))
                            kategoriPusat = "Piutang Penjualan";
                        insertKeuanganPusat(conPusat, noKeuanganPusat, "Bank", "Terima Pembayaran "+kategoriPusat+" "+s.getKodeCabang()+"-"+s.getTipeKasir(), 
                                pb.getNoPembayaran(), jumlahBayar, user.getKodeUser());
                        insertKeuanganPusat(conPusat, noKeuanganPusat, kategoriPusat, "Terima Pembayaran "+kategoriPusat+" "+s.getKodeCabang()+"-"+s.getTipeKasir(), 
                                pb.getNoPembayaran(), -jumlahTerbayar, user.getKodeUser());
                        insertKeuanganPusat(conPusat, noKeuanganPusat, "Pendapatan/Beban Kurs Emas", "Terima Pembayaran "+kategoriPusat+" "+s.getKodeCabang()+"-"+s.getTipeKasir(), 
                                pb.getNoPembayaran(), -selisihKurs, user.getKodeUser());
                        
                        break;
                    }
                }
            }
            
            if(status.equals("true")){
                conPusat.commit();
            }else{
                conPusat.rollback();
            }
            conPusat.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                e.printStackTrace();
                conPusat.rollback();
                conPusat.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String saveTambahUangCabang(Connection conPusat, TambahUangCabang t)throws Exception{
        try{
            String status = "true";
            conPusat.setAutoCommit(false);
            
            TambahUangCabangDAO.insert(conPusat, t);
            
            String noKeuanganPusat = KeuanganPusatDAO.getId(conPusat);
            String noKeuangan = KeuanganCabangDAO.getId(conPusat, t.getKodeCabang());
            double saldoAkhir = KeuanganCabangDAO.getSaldoAfter(conPusat, sistem.getTglSystem(), t.getKodeCabang(), t.getTipeKasir(), "Bank");
            double hutang = pembulatan(t.getJumlahRp()-saldoAkhir);
            if(hutang>0){
                Piutang p = new Piutang();
                p.setNoPiutang(PiutangDAO.getId(conPusat));
                p.setTglPiutang(Function.getSystemDate());
                p.setKodeCabang(t.getKodeCabang());
                p.setTipeKasir(t.getTipeKasir());
                p.setKategori("Hutang Modal");
                p.setNoTransaksi(t.getNoTambahUang());
                p.setKurs(1);
                p.setJumlahPiutang(hutang);
                p.setTerbayar(0);
                p.setSisaPiutang(hutang);
                p.setStatus("true");
                PiutangDAO.insert(conPusat, p);
                
                insertKeuanganCabang(conPusat, noKeuangan, t.getKodeCabang(), t.getTipeKasir(), "Hutang Modal", 
                        "Tambah Uang Cabang", t.getNoTambahUang(), hutang, t.getKodeUser());
                
                insertKeuanganPusat(conPusat, noKeuanganPusat, "Bank", "Piutang Modal "+t.getKodeCabang()+"-"+t.getTipeKasir(), 
                        p.getNoPiutang(), -hutang, user.getKodeUser());
                insertKeuanganPusat(conPusat, noKeuanganPusat, "Piutang Modal", "Piutang Modal "+t.getKodeCabang()+"-"+t.getTipeKasir(), 
                        p.getNoPiutang(), hutang, user.getKodeUser());
                if(hutang<t.getJumlahRp()){
                    insertKeuanganCabang(conPusat, noKeuangan, t.getKodeCabang(), t.getTipeKasir(), "Bank", 
                            "Tambah Uang Cabang", t.getNoTambahUang(), pembulatan(hutang-t.getJumlahRp()), t.getKodeUser());
                }
            }else if(hutang<=0){
                insertKeuanganCabang(conPusat, noKeuangan, t.getKodeCabang(), t.getTipeKasir(), "Bank", 
                        "Tambah Uang Cabang", t.getNoTambahUang(), -t.getJumlahRp(), t.getKodeUser());
            }
            insertKeuanganCabang(conPusat, noKeuangan, t.getKodeCabang(), t.getTipeKasir(), "Bank-"+t.getKodeCabang(), 
                    "Tambah Uang Cabang", t.getNoTambahUang(), t.getJumlahRp(), t.getKodeUser());
            
            if(status.equals("true")){
                conPusat.commit();
            }else{
                conPusat.rollback();
            }
            conPusat.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                conPusat.rollback();
                conPusat.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String saveBatalTambahUangCabang(Connection conPusat, TambahUangCabang t)throws Exception{
        try{
            String status = "true";
            conPusat.setAutoCommit(false);
            
            TambahUangCabangDAO.update(conPusat, t);
            
            KeuanganCabang kc = KeuanganCabangDAO.get(conPusat, t.getKodeCabang(), t.getTipeKasir(), "Bank-"+t.getKodeCabang(), "Tambah Uang Cabang", t.getNoTambahUang());
            KeuanganCabangDAO.deleteAll(conPusat, kc.getNoKeuangan());
            
            Piutang p = PiutangDAO.getByNoTransaksi(conPusat, t.getNoTambahUang());
            if(p!=null){
                if(p.getTerbayar()>1 || p.getStatus().equals("false")){
                    status = "Tidak dapat dibatal, karena hutang modal dari tambah uang cabang sudah ada pembayaran";
                }else{
                    KeuanganPusat kp = KeuanganPusatDAO.get(conPusat, "Bank", "Piutang Modal "+t.getKodeCabang()+"-"+t.getTipeKasir(), p.getNoPiutang());
                    KeuanganPusatDAO.deleteAll(conPusat, kp.getNoKeuangan());

                    PiutangDAO.delete(conPusat, p);
                }
            }else{
                status = "Tidak dapat dibatal, karena hutang modal tidak ditemukan atau sudah dibatal";
            }
            
            if(status.equals("true")){
                conPusat.commit();
            }else{
                conPusat.rollback();
            }
            conPusat.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                conPusat.rollback();
                conPusat.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String savePenjualanCabang(Connection conPusat, PenjualanCabangHead p)throws Exception{
        try{
            String status = "true";
            conPusat.setAutoCommit(false);
            
            PenjualanCabangHeadDAO.insert(conPusat, p);
            List<String> kodeJenis = new ArrayList<>();
            for(PenjualanCabangDetail d : p.getListDetail()){
                StokBarang s = StokBarangDAO.get(conPusat, sistem.getTglSystem(), d.getKodeJenis());
                double nilai = pembulatan(s.getNilaiAkhir()/s.getBeratAkhir()*d.getBerat());
                d.setTotalNilai(nilai);
                PenjualanCabangDetailDAO.insert(conPusat, d);
                
                if(!kodeJenis.contains(d.getKodeJenis()))
                    kodeJenis.add(d.getKodeJenis());
            }
            double totalNilai = 0;
            for(String jenis : kodeJenis){
                int qty = 0;
                double berat = 0;
                double beratPersen = 0;
                double nilai = 0;
                double harga = 0;
                String kategori = "";
                for(PenjualanCabangDetail d : p.getListDetail()){
                    if(d.getKodeJenis().equals(jenis)){
                        kategori = d.getKodeKategori();
                        qty = qty + d.getQty();
                        berat = berat + d.getBerat();
                        beratPersen = beratPersen + d.getBeratPersen();
                        nilai = nilai + d.getTotalNilai();
                        harga = harga + d.getTotalHargaRp();
                    }
                }
                totalNilai = totalNilai + nilai;
                
                status = updateStokPusatKeluar(conPusat, jenis, berat, beratPersen, nilai, status);

                status = updateStokBarangNonBarcodeMasuk(conPusat, p.getKodeCabang(), p.getKodeCabang()+"-New", kategori, jenis, qty, berat, beratPersen, harga, status);
            }
            String noKeuanganPusat = KeuanganPusatDAO.getId(conPusat);
            String noKeuangan = KeuanganCabangDAO.getId(conPusat, p.getKodeCabang());
            double saldoAkhir = KeuanganCabangDAO.getSaldoAfter(conPusat, sistem.getTglSystem(), p.getKodeCabang(), "Kasir", "Bank");
            double hutang = pembulatan(p.getTotalPenjualan() - saldoAkhir);
            if(hutang>0){
                Piutang pt = new Piutang();
                pt.setNoPiutang(PiutangDAO.getId(conPusat));
                pt.setTglPiutang(Function.getSystemDate());
                pt.setKodeCabang(p.getKodeCabang());
                pt.setTipeKasir("Kasir");
                pt.setKategori("Hutang Pembelian");
                pt.setNoTransaksi(p.getNoPenjualanCabang());
                pt.setKurs(p.getHargaEmas());
                pt.setJumlahPiutang(hutang);
                pt.setTerbayar(0);
                pt.setSisaPiutang(hutang);
                pt.setStatus("true");
                PiutangDAO.insert(conPusat, pt);
                
                insertKeuanganCabang(conPusat, noKeuangan, p.getKodeCabang(), "Kasir", "Hutang Pembelian", 
                        "Pembelian Pusat", p.getNoPenjualanCabang(), hutang, user.getKodeUser());
                insertKeuanganPusat(conPusat, noKeuanganPusat, "Piutang Penjualan", "Penjualan "+p.getKodeCabang(), 
                        p.getNoPenjualanCabang(), hutang, user.getKodeUser());
                
                if(hutang<p.getTotalPenjualan()){
                    insertKeuanganCabang(conPusat, noKeuangan, p.getKodeCabang(), "Kasir", "Bank", "Pembelian Pusat", 
                            p.getNoPenjualanCabang(), pembulatan(hutang-p.getTotalPenjualan()), user.getKodeUser());
                    insertKeuanganPusat(conPusat, noKeuanganPusat, "Bank", "Penjualan "+p.getKodeCabang(), 
                            p.getNoPenjualanCabang(), -pembulatan(hutang-p.getTotalPenjualan()), user.getKodeUser());
                }
            }else if(hutang<=0){
                insertKeuanganCabang(conPusat, noKeuangan, p.getKodeCabang(), "Kasir", "Bank", "Pembelian Pusat", 
                        p.getNoPenjualanCabang(), -p.getTotalPenjualan(), user.getKodeUser());
                insertKeuanganPusat(conPusat, noKeuanganPusat, "Bank", "Penjualan "+p.getKodeCabang(), p.getNoPenjualanCabang(), 
                        p.getTotalPenjualan(), user.getKodeUser());
            }
            insertKeuanganPusat(conPusat, noKeuanganPusat, "Penjualan", "Penjualan "+p.getKodeCabang(), p.getNoPenjualanCabang(), p.getTotalPenjualan(), user.getKodeUser());
            insertKeuanganPusat(conPusat, noKeuanganPusat, "Stok Barang", "Penjualan "+p.getKodeCabang(), p.getNoPenjualanCabang(), -totalNilai, user.getKodeUser());
            insertKeuanganPusat(conPusat, noKeuanganPusat, "HPP", "Penjualan "+p.getKodeCabang(), p.getNoPenjualanCabang(), -totalNilai, user.getKodeUser());
            
            insertKeuanganCabang(conPusat, noKeuangan, p.getKodeCabang(), "Kasir", p.getKodeCabang()+"-New", "Pembelian Pusat", 
                    p.getNoPenjualanCabang(), p.getTotalPenjualan(), user.getKodeUser());
            
            
            if(status.equals("true")){
                conPusat.commit();
            }else{
                conPusat.rollback();
            }
            conPusat.setAutoCommit(true);
            return status;
        }catch(Exception e){
            e.printStackTrace();
            try{
                conPusat.rollback();
                conPusat.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String saveBatalPenjualanCabang(Connection conPusat, PenjualanCabangHead p)throws Exception{
        try{
            String status = "true";
            conPusat.setAutoCommit(false);
            
            PenjualanCabangHeadDAO.update(conPusat, p);
            if(p.getTglPenjualan().substring(0,10).equals(sistem.getTglSystem())){
                List<String> kodeJenis = new ArrayList<>();
                p.setListDetail(PenjualanCabangDetailDAO.getAllByNoPenjualan(conPusat, p.getNoPenjualanCabang()));
                for(PenjualanCabangDetail d : p.getListDetail()){
                    if(!kodeJenis.contains(d.getKodeJenis()))
                        kodeJenis.add(d.getKodeJenis());
                }
                for(String jenis : kodeJenis){
                    int qty = 0;
                    double berat = 0;
                    double beratPersen = 0;
                    double nilai = 0;
                    double harga = 0;
                    for(PenjualanCabangDetail d : p.getListDetail()){
                        if(d.getKodeJenis().equals(jenis)){
                            qty = qty + d.getQty();
                            berat = berat + d.getBerat();
                            beratPersen = beratPersen + d.getBeratPersen();
                            nilai = nilai + d.getTotalNilai();
                            harga = harga + d.getTotalHargaRp();
                        }
                    }
                    status = updateStokPusatBatalKeluar(conPusat, jenis, berat, beratPersen, nilai, status);

                    status = updateStokBarangNonBarcodeBatalMasuk(conPusat, p.getKodeCabang()+"-New", jenis, qty, berat, beratPersen, harga, status);
                }
                KeuanganCabang kc = KeuanganCabangDAO.get(conPusat, p.getKodeCabang(), "Kasir", p.getKodeCabang()+"-New", "Pembelian Pusat", p.getNoPenjualanCabang());
                KeuanganCabangDAO.deleteAll(conPusat, kc.getNoKeuangan());

                Piutang pt = PiutangDAO.getByNoTransaksi(conPusat, p.getNoPenjualanCabang());
                if(pt!=null){
                    if(pt.getTerbayar()>1 || pt.getStatus().equals("false")){
                        status = "Tidak dapat dibatal, karena piutang penjualan sudah ada pembayaran";
                    }else{
                        KeuanganPusat kp = KeuanganPusatDAO.get(conPusat, "Penjualan", "Penjualan "+p.getKodeCabang(), p.getNoPenjualanCabang());
                        KeuanganPusatDAO.deleteAll(conPusat, kp.getNoKeuangan());

                        PiutangDAO.delete(conPusat, pt);
                    }
                }else{
                    status = "Tidak dapat dibatal, karena piutang penjualan tidak ditemukan atau sudah dibatal";
                } 
            }else{
                List<String> kodeJenis = new ArrayList<>();
                p.setListDetail(PenjualanCabangDetailDAO.getAllByNoPenjualan(conPusat, p.getNoPenjualanCabang()));
                for(PenjualanCabangDetail d : p.getListDetail()){
                    if(!kodeJenis.contains(d.getKodeJenis()))
                        kodeJenis.add(d.getKodeJenis());
                }
                double totalNilai = 0;
                for(String jenis : kodeJenis){
                    String kategori = "";
                    int qty = 0;
                    double berat = 0;
                    double beratPersen = 0;
                    double nilai = 0;
                    double harga = 0;
                    for(PenjualanCabangDetail d : p.getListDetail()){
                        if(d.getKodeJenis().equals(jenis)){
                            kategori = d.getKodeKategori();
                            qty = qty + d.getQty();
                            berat = berat + d.getBerat();
                            beratPersen = beratPersen + d.getBeratPersen();
                            nilai = nilai + d.getTotalNilai();
                            harga = harga + d.getTotalHargaRp();
                        }
                    }
                    totalNilai = totalNilai + nilai;

                    status = updateStokPusatMasuk(conPusat, kategori, jenis, berat, beratPersen, nilai, status);

                    status = updateStokBarangNonBarcodeKeluar(conPusat, p.getKodeCabang()+"-New", jenis, qty, berat, beratPersen, harga, status);
                }
                
                String noKeuanganPusat = KeuanganPusatDAO.getId(conPusat);
                String noKeuangan = KeuanganCabangDAO.getId(conPusat, p.getKodeCabang());
                Piutang pt = PiutangDAO.getByNoTransaksi(conPusat, p.getNoPenjualanCabang());
                double hutang = 0;
                if(pt!=null){
                    if(pt.getTerbayar()>1 || pt.getStatus().equals("false")){
                        status = "Tidak dapat dibatal, karena piutang penjualan sudah ada pembayaran";
                    }else{
                        hutang = pt.getJumlahPiutang();
                        insertKeuanganCabang(conPusat, noKeuangan, p.getKodeCabang(), "Kasir", "Hutang Pembelian", 
                                "Batal Pembelian Pusat", p.getNoPenjualanCabang(), -hutang, user.getKodeUser());

                        insertKeuanganPusat(conPusat, noKeuanganPusat, "Piutang Penjualan", "Batal Penjualan "+p.getKodeCabang(), 
                                pt.getNoPiutang(), -hutang, user.getKodeUser());

                        if(hutang<p.getTotalPenjualan()){
                            insertKeuanganPusat(conPusat, noKeuanganPusat, "Bank", "Batal Penjualan "+p.getKodeCabang(),  
                                    pt.getNoPiutang(), pembulatan(hutang-p.getTotalPenjualan()), user.getKodeUser());
                            insertKeuanganCabang(conPusat, noKeuangan, p.getKodeCabang(), "Kasir", "Bank", "Batal Pembelian Pusat", 
                                    p.getNoPenjualanCabang(), -pembulatan(hutang-p.getTotalPenjualan()), user.getKodeUser());
                        }
                        PiutangDAO.delete(conPusat, pt);
                    }
                }else{
                    insertKeuanganCabang(conPusat, noKeuangan, p.getKodeCabang(), "Kasir", "Bank", "Batal Pembelian Pusat", 
                            p.getNoPenjualanCabang(), p.getTotalPenjualan(), user.getKodeUser());
                    insertKeuanganPusat(conPusat, noKeuanganPusat, "Bank", "Penjualan "+p.getKodeCabang(), p.getNoPenjualanCabang(), 
                            -p.getTotalPenjualan(), user.getKodeUser());
                } 
                insertKeuanganPusat(conPusat, noKeuanganPusat, "Penjualan", "Batal Penjualan "+p.getKodeCabang(), p.getNoPenjualanCabang(), -p.getTotalPenjualan(), user.getKodeUser());
                insertKeuanganPusat(conPusat, noKeuanganPusat, "Stok Barang", "Batal Penjualan "+p.getKodeCabang(), p.getNoPenjualanCabang(), totalNilai, user.getKodeUser());
                insertKeuanganPusat(conPusat, noKeuanganPusat, "HPP", "Batal Penjualan "+p.getKodeCabang(), p.getNoPenjualanCabang(), totalNilai, user.getKodeUser());

                insertKeuanganCabang(conPusat, noKeuangan, p.getKodeCabang(), "Kasir", p.getKodeCabang()+"-New", "Batal Pembelian Pusat", 
                        p.getNoPenjualanCabang(), -p.getTotalPenjualan(), user.getKodeUser());
            }
            
            if(status.equals("true")){
                conPusat.commit();
            }else{
                conPusat.rollback();
            }
            conPusat.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                conPusat.rollback();
                conPusat.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String savePembelianSupplier(Connection conPusat, PembelianHead p)throws Exception{
        try{
            String status = "true";
            conPusat.setAutoCommit(false);
            
            PembelianHeadDAO.insert(conPusat, p);
            List<String> kodeJenis = new ArrayList<>();
            for(PembelianDetail d : p.getListPembelianDetail()){
                PembelianDetailDAO.insert(conPusat, d);
                
                if(!kodeJenis.contains(d.getKodeJenis()))
                    kodeJenis.add(d.getKodeJenis());
            }
            for(String jenis : kodeJenis){
                double berat = 0;
                double beratPersen = 0;
                double harga = 0;
                String kategori = "";
                for(PembelianDetail d : p.getListPembelianDetail()){
                    if(d.getKodeJenis().equals(jenis)){
                        kategori = d.getKodeKategori();
                        berat = berat + d.getBerat();
                        beratPersen = beratPersen + d.getBeratPersen();
                        harga = harga + d.getHargaPersen()*p.getHargaEmas();
                    }
                }
                
                status = updateStokPusatMasuk(conPusat, kategori, jenis, berat, beratPersen, pembulatan(harga), status);
            }
            String noKeuanganPusat = KeuanganPusatDAO.getId(conPusat);
            Hutang ht = new Hutang();
            ht.setNoHutang(HutangDAO.getId(conPusat));
            ht.setTglHutang(Function.getSystemDate());
            ht.setSupplier(p.getSupplier());
            ht.setNoPembelian(p.getNoPembelian());
            ht.setKurs(p.getHargaEmas());
            ht.setJumlahHutang(p.getTotalPembelian());
            ht.setTerbayar(0);
            ht.setSisaHutang(p.getTotalPembelian());
            ht.setStatus("true");
            HutangDAO.insert(conPusat, ht);

            insertKeuanganPusat(conPusat, noKeuanganPusat, "Hutang Pembelian", 
                    "Pembelian Supplier", p.getNoPembelian(), p.getTotalPembelian(), user.getKodeUser());

            insertKeuanganPusat(conPusat, noKeuanganPusat, "Stok Barang", "Pembelian Supplier", 
                    p.getNoPembelian(), p.getTotalPembelian(), user.getKodeUser());
            
            if(status.equals("true")){
                conPusat.commit();
            }else{
                conPusat.rollback();
            }
            conPusat.setAutoCommit(true);
            return status;
        }catch(Exception e){
            e.printStackTrace();
            try{
                conPusat.rollback();
                conPusat.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String saveBatalPembelianSupplier(Connection conPusat, PembelianHead p)throws Exception{
        try{
            String status = "true";
            conPusat.setAutoCommit(false);
            
            PembelianHeadDAO.update(conPusat, p);
            if(p.getTglPembelian().substring(0,10).equals(sistem.getTglSystem())){
                List<String> kodeJenis = new ArrayList<>();
                p.setListPembelianDetail(PembelianDetailDAO.getAllByNoPembelian(conPusat, p.getNoPembelian()));
                for(PembelianDetail d : p.getListPembelianDetail()){
                    if(!kodeJenis.contains(d.getKodeJenis()))
                        kodeJenis.add(d.getKodeJenis());
                }
                for(String jenis : kodeJenis){
                    double berat = 0;
                    double beratPersen = 0;
                    double harga = 0;
                    for(PembelianDetail d : p.getListPembelianDetail()){
                        if(d.getKodeJenis().equals(jenis)){
                            berat = berat + d.getBerat();
                            beratPersen = beratPersen + d.getBeratPersen();
                            harga = harga + (d.getTotalHarga()*p.getHargaEmas());
                        }
                    }
                    status = updateStokPusatBatalMasuk(conPusat, jenis, berat, beratPersen, pembulatan(harga), status);
                }
                Hutang ht = HutangDAO.getByNoPembelian(conPusat, p.getNoPembelian());
                if(ht!=null){
                    if(ht.getTerbayar()>1 || ht.getStatus().equals("false")){
                        status = "Tidak dapat dibatal, karena hutang pembelian sudah ada pembayaran";
                    }else{
                        KeuanganPusat kp = KeuanganPusatDAO.get(conPusat, "Stok Barang", "Pembelian Supplier", p.getNoPembelian());
                        KeuanganPusatDAO.deleteAll(conPusat, kp.getNoKeuangan());

                        HutangDAO.delete(conPusat, ht);
                    }
                }else{
                    status = "Tidak dapat dibatal, karena hutang pembelian tidak ditemukan atau sudah dibatal";
                } 
            }else{
                List<String> kodeJenis = new ArrayList<>();
                p.setListPembelianDetail(PembelianDetailDAO.getAllByNoPembelian(conPusat, p.getNoPembelian()));
                for(PembelianDetail d : p.getListPembelianDetail()){
                    if(!kodeJenis.contains(d.getKodeJenis()))
                        kodeJenis.add(d.getKodeJenis());
                }
                for(String jenis : kodeJenis){
                    double berat = 0;
                    double beratPersen = 0;
                    double harga = 0;
                    for(PembelianDetail d : p.getListPembelianDetail()){
                        if(d.getKodeJenis().equals(jenis)){
                            berat = berat + d.getBerat();
                            beratPersen = beratPersen + d.getBeratPersen();
                            harga = harga + d.getTotalHarga()*p.getHargaEmas();
                        }
                    }

                    status = updateStokPusatKeluar(conPusat, jenis, berat, beratPersen, pembulatan(harga), status);
                }
                
                String noKeuanganPusat = KeuanganPusatDAO.getId(conPusat);
                Hutang ht = HutangDAO.getByNoPembelian(conPusat, p.getNoPembelian());
                if(ht!=null){
                    if(ht.getTerbayar()>1 || ht.getStatus().equals("false")){
                        status = "Tidak dapat dibatal, karena hutang pembelian sudah ada pembayaran";
                    }else{
                        insertKeuanganPusat(conPusat, noKeuanganPusat, "Hutang Pembelian", 
                                "Batal Pembelian Supplier", p.getNoPembelian(), -ht.getJumlahHutang(), user.getKodeUser());

                        HutangDAO.delete(conPusat, ht);
                    }
                }else{
                    status = "Tidak dapat dibatal, karena hutang pembelian tidak ditemukan atau sudah dibatal";
                } 
                insertKeuanganPusat(conPusat, noKeuanganPusat,  "Stok Barang", "Batal Pembelian Supplier", 
                        p.getNoPembelian(), -p.getTotalPembelian(), user.getKodeUser());
            }
            
            if(status.equals("true")){
                conPusat.commit();
            }else{
                conPusat.rollback();
            }
            conPusat.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                conPusat.rollback();
                conPusat.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String savePembayaranPembelian(Connection conPusat, PembayaranHutang p)throws Exception{
        try{
            String status = "true";
            conPusat.setAutoCommit(false);
            
            PembayaranHutangDAO.insert(conPusat, p);
            
            String noKeuanganPusat = KeuanganPusatDAO.getId(conPusat);
            Hutang ht = HutangDAO.get(conPusat, p.getNoHutang());
            ht.setTerbayar(ht.getTerbayar()+p.getTerbayar());
            ht.setSisaHutang(ht.getSisaHutang()-p.getTerbayar());
            if(ht.getSisaHutang()<=0)
                ht.setStatus("false");
            HutangDAO.update(conPusat, ht);

            insertKeuanganPusat(conPusat, noKeuanganPusat, "Hutang Pembelian", 
                    "Pembayaran Hutang Pembelian", p.getNoPembayaran(), -p.getTerbayar(), user.getKodeUser());

            insertKeuanganPusat(conPusat, noKeuanganPusat, "Bank", 
                    "Pembayaran Hutang Pembelian", p.getNoPembayaran(), -p.getJumlahBayar(), user.getKodeUser());
            
            insertKeuanganPusat(conPusat, noKeuanganPusat, "Pendapatan/Beban Kurs Emas", "Pembayaran Hutang Pembelian", 
                    p.getNoPembayaran(), p.getTerbayar()-p.getJumlahBayar(), user.getKodeUser());
            
            if(status.equals("true")){
                conPusat.commit();
            }else{
                conPusat.rollback();
            }
            conPusat.setAutoCommit(true);
            return status;
        }catch(Exception e){
            e.printStackTrace();
            try{
                conPusat.rollback();
                conPusat.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String saveBatalPembayaranPembelian(Connection conPusat, PembayaranHutang p)throws Exception{
        try{
            String status = "true";
            conPusat.setAutoCommit(false);
            
            PembayaranHutangDAO.update(conPusat, p);
            
            Hutang ht = HutangDAO.get(conPusat, p.getNoHutang());
            ht.setTerbayar(ht.getTerbayar()-p.getTerbayar());
            ht.setSisaHutang(ht.getSisaHutang()+p.getTerbayar());
            ht.setStatus("true");
            HutangDAO.update(conPusat, ht);

            KeuanganPusat kp = KeuanganPusatDAO.get(conPusat, "Hutang Pembelian", "Pembayaran Hutang Pembelian", p.getNoPembayaran());
            KeuanganPusatDAO.deleteAll(conPusat, kp.getNoKeuangan());
            
            if(status.equals("true")){
                conPusat.commit();
            }else{
                conPusat.rollback();
            }
            conPusat.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                conPusat.rollback();
                conPusat.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String saveReturPembelianSupplier(Connection conPusat, ReturPembelianHead p)throws Exception{
        try{
            String status = "true";
            conPusat.setAutoCommit(false);
            
            ReturPembelianHeadDAO.insert(conPusat, p);
            List<String> kodeJenis = new ArrayList<>();
            for(ReturPembelianDetail d : p.getListReturPembelianDetail()){
                ReturPembelianDetailDAO.insert(conPusat, d);
                
                if(!kodeJenis.contains(d.getKodeJenis()))
                    kodeJenis.add(d.getKodeJenis());
            }
            for(String jenis : kodeJenis){
                double berat = 0;
                double beratPersen = 0;
                double harga = 0;
                for(ReturPembelianDetail d : p.getListReturPembelianDetail()){
                    if(d.getKodeJenis().equals(jenis)){
                        berat = berat + d.getBerat();
                        beratPersen = beratPersen + d.getBeratPersen();
                        harga = harga + d.getHargaPersen()*p.getHargaEmas();
                    }
                }
                
                status = updateStokPusatKeluar(conPusat, jenis, berat, beratPersen, pembulatan(harga), status);
            }
            String noKeuanganPusat = KeuanganPusatDAO.getId(conPusat);

            insertKeuanganPusat(conPusat, noKeuanganPusat, "Bank", 
                    "Retur Pembelian Supplier", p.getNoRetur(), p.getTotalRetur(), user.getKodeUser());

            insertKeuanganPusat(conPusat, noKeuanganPusat, "Stok Barang", "Retur Pembelian Supplier", 
                    p.getNoRetur(), -p.getTotalRetur(), user.getKodeUser());
            
            if(status.equals("true")){
                conPusat.commit();
            }else{
                conPusat.rollback();
            }
            conPusat.setAutoCommit(true);
            return status;
        }catch(Exception e){
            e.printStackTrace();
            try{
                conPusat.rollback();
                conPusat.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String saveBatalReturPembelianSupplier(Connection conPusat, ReturPembelianHead p)throws Exception{
        try{
            String status = "true";
            conPusat.setAutoCommit(false);
            
            ReturPembelianHeadDAO.update(conPusat, p);
            if(p.getTglRetur().substring(0,10).equals(sistem.getTglSystem())){
                List<String> kodeJenis = new ArrayList<>();
                p.setListReturPembelianDetail(ReturPembelianDetailDAO.getAllByNoRetur(conPusat, p.getNoRetur()));
                for(ReturPembelianDetail d : p.getListReturPembelianDetail()){
                    if(!kodeJenis.contains(d.getKodeJenis()))
                        kodeJenis.add(d.getKodeJenis());
                }
                for(String jenis : kodeJenis){
                    double berat = 0;
                    double beratPersen = 0;
                    double harga = 0;
                    for(ReturPembelianDetail d : p.getListReturPembelianDetail()){
                        if(d.getKodeJenis().equals(jenis)){
                            berat = berat + d.getBerat();
                            beratPersen = beratPersen + d.getBeratPersen();
                            harga = harga + (d.getTotalHarga()*p.getHargaEmas());
                        }
                    }
                    status = updateStokPusatBatalKeluar(conPusat, jenis, berat, beratPersen, pembulatan(harga), status);
                }
                KeuanganPusat kp = KeuanganPusatDAO.get(conPusat, "Stok Barang", "Retur Pembelian Supplier", p.getNoRetur());
                KeuanganPusatDAO.deleteAll(conPusat, kp.getNoKeuangan());
            }else{
                List<String> kodeJenis = new ArrayList<>();
                p.setListReturPembelianDetail(ReturPembelianDetailDAO.getAllByNoRetur(conPusat, p.getNoRetur()));
                for(ReturPembelianDetail d : p.getListReturPembelianDetail()){
                    if(!kodeJenis.contains(d.getKodeJenis()))
                        kodeJenis.add(d.getKodeJenis());
                }
                for(String jenis : kodeJenis){
                    String kategori = "";
                    double berat = 0;
                    double beratPersen = 0;
                    double harga = 0;
                    for(ReturPembelianDetail d : p.getListReturPembelianDetail()){
                        if(d.getKodeJenis().equals(jenis)){
                            kategori = d.getKodeKategori();
                            berat = berat + d.getBerat();
                            beratPersen = beratPersen + d.getBeratPersen();
                            harga = harga + d.getTotalHarga()*p.getHargaEmas();
                        }
                    }

                    status = updateStokPusatMasuk(conPusat, kategori, jenis, berat, beratPersen, pembulatan(harga), status);
                }
                
                String noKeuanganPusat = KeuanganPusatDAO.getId(conPusat);
                insertKeuanganPusat(conPusat, noKeuanganPusat, "Bank", 
                        "Batal Retur Pembelian Supplier", p.getNoRetur(), -p.getTotalRetur(), user.getKodeUser());
                insertKeuanganPusat(conPusat, noKeuanganPusat,  "Stok Barang", "Batal Retur Pembelian Supplier", 
                        p.getNoRetur(), p.getTotalRetur(), user.getKodeUser());
            }
            
            if(status.equals("true")){
                conPusat.commit();
            }else{
                conPusat.rollback();
            }
            conPusat.setAutoCommit(true);
            return status;
        }catch(Exception e){
            try{
                conPusat.rollback();
                conPusat.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    
    public static String saveTambahBarang(Connection conPusat, String kategori, String jenis, double berat, double beratPersen, double nilai)throws Exception{
        try{
            String status = "true";
            conPusat.setAutoCommit(false);
            
            PenyesuaianStokBarangPusat p = new PenyesuaianStokBarangPusat();
            p.setNoPenyesuaian(PenyesuaianStokBarangPusatDAO.getId(conPusat));
            p.setTglPenyesuaian(Function.getSystemDate());
            p.setKategori(kategori);
            p.setJenis(jenis);
            p.setBerat(berat);
            p.setHargaPersen(beratPersen);
            p.setHargaEmas(sistem.getHargaEmas());
            p.setNilaiPokok(nilai);
            p.setKodeUser(user.getKodeUser());
            p.setStatus("true");
            p.setTglBatal("2000-01-01 00:00:00");
            p.setUserBatal("");
            PenyesuaianStokBarangPusatDAO.insert(conPusat, p);
            
            status = updateStokPusatMasuk(conPusat, kategori, jenis, berat, beratPersen, nilai, status);
            
            String noKeuanganPusat = KeuanganPusatDAO.getId(conPusat);
            insertKeuanganPusat(conPusat, noKeuanganPusat, "Modal", "Penyesuaian Stok Barang", p.getNoPenyesuaian(), nilai, user.getKodeUser());
            insertKeuanganPusat(conPusat, noKeuanganPusat, "Stok Barang", "Penyesuaian Stok Barang", p.getNoPenyesuaian(), nilai, user.getKodeUser());
            
            if(status.equals("true")){
                conPusat.commit();
            }else{
                conPusat.rollback();
            }
            conPusat.setAutoCommit(true);
            return status;
        }catch(Exception e){
            e.printStackTrace();
            try{
                conPusat.rollback();
                conPusat.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
    public static String saveAmbilBarang(Connection conPusat, String kategori, String jenis, double berat, double beratPersen, double nilai)throws Exception{
        try{
            String status = "true";
            conPusat.setAutoCommit(false);
            
            PenyesuaianStokBarangPusat p = new PenyesuaianStokBarangPusat();
            p.setNoPenyesuaian(PenyesuaianStokBarangPusatDAO.getId(conPusat));
            p.setTglPenyesuaian(Function.getSystemDate());
            p.setKategori(kategori);
            p.setJenis(jenis);
            p.setBerat(-berat);
            p.setHargaPersen(-beratPersen);
            p.setHargaEmas(sistem.getHargaEmas());
            p.setNilaiPokok(-nilai);
            p.setKodeUser(user.getKodeUser());
            p.setStatus("true");
            p.setTglBatal("2000-01-01 00:00:00");
            p.setUserBatal("");
            PenyesuaianStokBarangPusatDAO.insert(conPusat, p);
            
            status = updateStokPusatKeluar(conPusat, jenis, berat, beratPersen, nilai, status);
            
            String noKeuanganPusat = KeuanganPusatDAO.getId(conPusat);
            insertKeuanganPusat(conPusat, noKeuanganPusat, "Modal", "Penyesuaian Stok Barang", p.getNoPenyesuaian(), -nilai, user.getKodeUser());
            insertKeuanganPusat(conPusat, noKeuanganPusat, "Stok Barang", "Penyesuaian Stok Barang", p.getNoPenyesuaian(), -nilai, user.getKodeUser());
            
            if(status.equals("true")){
                conPusat.commit();
            }else{
                conPusat.rollback();
            }
            conPusat.setAutoCommit(true);
            return status;
        }catch(Exception e){
            e.printStackTrace();
            try{
                conPusat.rollback();
                conPusat.setAutoCommit(true);
                return e.toString();
            }catch(SQLException ex){
                return ex.toString();
            }
        }
    }
}
