/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat;

import com.excellentsystem.TokoEmasJagoPusat.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.HancurDetailDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.KeuanganCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.PenjualanAntarCabangDetailDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.TokoCabang.BarangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.TokoCabang.KeuanganDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.TokoCabang.PenjualanDetailDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.TokoCabang.StokBarangDiCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.Model.Cabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.HancurDetail;
import com.excellentsystem.TokoEmasJagoPusat.Model.KeuanganCabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.PenjualanAntarCabangDetail;
import com.excellentsystem.TokoEmasJagoPusat.Model.TokoCabang.Barang;
import com.excellentsystem.TokoEmasJagoPusat.Model.TokoCabang.Keuangan;
import com.excellentsystem.TokoEmasJagoPusat.Model.TokoCabang.PenjualanDetail;
import com.excellentsystem.TokoEmasJagoPusat.Model.TokoCabang.StokBarangDiCabang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author Excellent
 */
public class Patch {
    public static void patchSelisihPembelianAntarCabang(String kodeCabang){
        try(Connection conPusat = KoneksiPusat.getConnection()){
            Cabang c = CabangDAO.get(conPusat, kodeCabang);
            try(Connection conCabang = KoneksiCabang.getConnection(c)){
                List<PenjualanAntarCabangDetail> listPenjualanAntarCabangDetail = PenjualanAntarCabangDetailDAO.getAllByDateAndCabangAsalAndCabangTujuanAndStatusTerimaAndStatusBatal(
                        conPusat, "2000-01-01", "2100-01-01", "%", kodeCabang, "true", "false");
                for(PenjualanAntarCabangDetail d : listPenjualanAntarCabangDetail){
                    String kodeBarcode = d.getKodeBarcodeBaru();
                    double nilai = d.getHarga();
                    List<StokBarangDiCabang> listStokBarang = StokBarangDiCabangDAO.getAllByDateAndGudangAndJenisAndBarcode(
                            conCabang, "2000-01-01", "2100-01-01", kodeCabang, "%", kodeBarcode);
                    for(StokBarangDiCabang s : listStokBarang){
                        s.setNilaiAwal(s.getStokAwal()*nilai);
                        s.setNilaiMasuk(s.getStokMasuk()*nilai);
                        s.setNilaiKeluar(s.getStokKeluar()*nilai);
                        s.setNilaiAkhir(s.getStokAkhir()*nilai);
                        StokBarangDiCabangDAO.update(conCabang, s);
                    }
                    Barang barang = BarangDAO.get(conCabang, kodeBarcode);
                    barang.setNilaiPokok(nilai);
                    BarangDAO.update(conCabang, barang);
                    if(barang.getStatus().equals("false")){
                        PenjualanDetail penjualanDetail = PenjualanDetailDAO.get(conCabang, kodeBarcode);
                        if(penjualanDetail!=null){
                            penjualanDetail.setHargaBeli(nilai);
                            PenjualanDetailDAO.update(conCabang, penjualanDetail);

                            Keuangan xxx = KeuanganDAO.get(conCabang, "Kasir", kodeCabang, "Penjualan Umum", penjualanDetail.getNoPenjualan());
                            xxx.setJumlahRp(-nilai);
                            KeuanganDAO.update(conCabang, xxx);

                            Keuangan hpp = KeuanganDAO.get(conCabang, "Kasir", "HPP Penjualan Umum", "Penjualan Umum", penjualanDetail.getNoPenjualan());
                            hpp.setJumlahRp(-nilai);
                            KeuanganDAO.update(conCabang, hpp);
                            
                            System.out.println(kodeBarcode+" terjual");
                        }else{
                            PenjualanAntarCabangDetail penjualanAntarCabangDetail = PenjualanAntarCabangDetailDAO.get(
                                conPusat, "2000-01-01", "2100-01-01", kodeBarcode, kodeCabang, "%", "%", "false");
                            if(penjualanAntarCabangDetail!=null){
                                penjualanAntarCabangDetail.setNilaiPokok(nilai);
                                PenjualanAntarCabangDetailDAO.update(conPusat, penjualanAntarCabangDetail);

                                Keuangan xxx = KeuanganDAO.get(conCabang, "Kasir", kodeCabang, "%", penjualanAntarCabangDetail.getNoPenjualan());
                                xxx.setJumlahRp(-nilai);
                                KeuanganDAO.update(conCabang, xxx);

                                Keuangan hpp = KeuanganDAO.get(conCabang, "Kasir", "HPP Penjualan Antar Cabang", "%", penjualanAntarCabangDetail.getNoPenjualan());
                                hpp.setJumlahRp(-nilai);
                                KeuanganDAO.update(conCabang, hpp);

                                System.out.println(kodeBarcode+" terjual cabang");
                            }else{
                                HancurDetail hancurDetail = HancurDetailDAO.get(conPusat, "2000-01-01", "2100-01-01", c.getKodeCabang(), "true", kodeBarcode);
                                if(hancurDetail!=null){
                                    hancurDetail.setNilaiPokok(nilai);
                                    HancurDetailDAO.update(conPusat, hancurDetail);
                                    
                                    Keuangan xxx = KeuanganDAO.get(conCabang, "Kasir", kodeCabang, "Hancur Barang Cabang", hancurDetail.getNoHancur());
                                    xxx.setJumlahRp(-nilai);
                                    KeuanganDAO.update(conCabang, xxx);

                                    KeuanganCabang xxxRosok = KeuanganCabangDAO.get(conPusat, kodeCabang, "Kasir", c.getKodeCabang()+"-Rosok", "Hancur Barang Cabang", hancurDetail.getNoHancur());
                                    xxxRosok.setJumlahRp(nilai);
                                    KeuanganCabangDAO.update(conPusat, xxxRosok);

                                    System.out.println(kodeBarcode+" hancur");
                                }else{
                                    System.out.println(kodeBarcode+" hilang");
                                }
                            }
                        }
                    }else{
                        System.out.println(kodeBarcode+" masih ada");
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void patchHancurBarang(String kodeCabang){
        try(Connection conPusat = KoneksiPusat.getConnection()){
            Cabang c = CabangDAO.get(conPusat, kodeCabang);
            try(Connection conCabang = KoneksiCabang.getConnection(c)){
                List<Keuangan> listKeuangan = KeuanganDAO.getAll(conCabang, "Kasir", kodeCabang, "Hancur Barang Cabang", "%");
                for(Keuangan k : listKeuangan){
                    if(k.getJumlahRp()>0){
                        k.setJumlahRp(k.getJumlahRp()*-1);
                        KeuanganDAO.update(conCabang, k);
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void patchResetStokBarang(){
        try(Connection conPusat = KoneksiPusat.getConnection()){
            PreparedStatement ps = conPusat.prepareStatement(""
                + " select tanggal,kode_cabang,kode_gudang,sum(nilai_akhir) "
                + " from tokoemasjagopusat.tt_stok_barang_cabang " 
                + " where stok_akhir=0 and nilai_akhir!=0 "
                + " group by tanggal,kode_cabang,kode_gudang");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String tanggal = rs.getString(1);
                LocalDate tgl = LocalDate.parse(tanggal, DateTimeFormatter.ISO_DATE);
                String besok = tgl.plusDays(1).toString();
                String cabang = rs.getString(2);
                String gudang = rs.getString(3);
                double nilai = rs.getDouble(4);

                String noKeuangan = KeuanganCabangDAO.getId(conPusat, cabang, besok);
                KeuanganCabang kc = new KeuanganCabang();
                kc.setNoKeuangan(noKeuangan);
                kc.setTglKeuangan(besok+" 00:00:00");
                kc.setKodeCabang(cabang);
                kc.setTipeKasir("Kasir");
                kc.setTipeKeuangan(gudang);
                kc.setKategori("Beban Penyusutan Reset Stok Barang");
                kc.setDeskripsi("Reset Stok Barang");
                kc.setJumlahRp(-nilai);
                kc.setKodeUser("System");
                KeuanganCabangDAO.insert(conPusat, kc);


                KeuanganCabang kc2 = new KeuanganCabang();
                kc2.setNoKeuangan(noKeuangan);
                kc2.setTglKeuangan(besok+" 00:00:00");
                kc2.setKodeCabang(cabang);
                kc2.setTipeKasir("Kasir");
                kc2.setTipeKeuangan("Beban Penyusutan Reset Stok Barang");
                kc2.setKategori("Beban Penyusutan Reset Stok Barang");
                kc2.setDeskripsi("Reset Stok Barang");
                kc2.setJumlahRp(-nilai);
                kc2.setKodeUser("System");
                KeuanganCabangDAO.insert(conPusat, kc2);
                
                System.out.println(noKeuangan);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
