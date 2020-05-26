-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.15-log - MySQL Community Server (GPL)
-- Server OS:                    Win32
-- HeidiSQL Version:             8.3.0.4694
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping database structure for tokoemaspadi
CREATE DATABASE IF NOT EXISTS `tokoemaspadi` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `tokoemaspadi`;


-- Dumping structure for table tokoemaspadi.tm_barang
CREATE TABLE IF NOT EXISTS `tm_barang` (
  `kode_barcode` varchar(50) NOT NULL,
  `nama_barang` varchar(50) NOT NULL,
  `nama_barcode` varchar(50) NOT NULL,
  `kode_kategori` varchar(50) NOT NULL,
  `kode_jenis` varchar(50) NOT NULL,
  `berat` double NOT NULL,
  `berat_pembulatan` double NOT NULL,
  `nilai_pokok` double NOT NULL,
  `harga_jual` double NOT NULL,
  `keterangan` varchar(50) NOT NULL,
  `merk` varchar(50) NOT NULL,
  `barcode_date` datetime NOT NULL,
  `barcode_by` varchar(50) NOT NULL,
  `qty` int(11) NOT NULL,
  PRIMARY KEY (`kode_barcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- Dumping data for table tokoemaspadi.tm_barang: ~4 rows (approximately)
DELETE FROM `tm_barang`;
/*!40000 ALTER TABLE `tm_barang` DISABLE KEYS */;
INSERT INTO `tm_barang` (`kode_barcode`, `nama_barang`, `nama_barcode`, `kode_kategori`, `kode_jenis`, `berat`, `berat_pembulatan`, `nilai_pokok`, `harga_jual`, `keterangan`, `merk`, `barcode_date`, `barcode_by`, `qty`) VALUES
	('00000001', 'tes', 'tes', 'MD', 'CCMD', 1, 1, 190000, 210000, 'Baru', 'UBS', '2019-07-21 15:47:50', 'user', 0),
	('00000002', 'tes', 'tes', 'MT', 'CCMT', 1.11, 1.2, 444000, 499500, 'Baru', 'tes', '2019-07-25 20:59:36', 'user', 0),
	('00000003', 'tes', 'tes', 'MD', 'KLMD', 1.1, 1.2, 209000, 231000, 'Baru', 'ubs', '2019-07-25 21:09:31', 'user', 0),
	('00000004', 'cc mata', 'cc mta', 'MT', 'CCMT', 1.55, 1.6, 0, 697500, 'Baru', 'UBS', '2019-07-30 06:04:42', 'user', 3);
/*!40000 ALTER TABLE `tm_barang` ENABLE KEYS */;


-- Dumping structure for table tokoemaspadi.tm_bunga_gadai
CREATE TABLE IF NOT EXISTS `tm_bunga_gadai` (
  `min` double NOT NULL,
  `max` double NOT NULL,
  `bunga` double DEFAULT NULL,
  PRIMARY KEY (`min`,`max`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table tokoemaspadi.tm_bunga_gadai: ~4 rows (approximately)
DELETE FROM `tm_bunga_gadai`;
/*!40000 ALTER TABLE `tm_bunga_gadai` DISABLE KEYS */;
INSERT INTO `tm_bunga_gadai` (`min`, `max`, `bunga`) VALUES
	(0, 500000, 4),
	(500001, 5000000, 3),
	(5000001, 10000000, 2.5),
	(10000001, 100000000, 2);
/*!40000 ALTER TABLE `tm_bunga_gadai` ENABLE KEYS */;


-- Dumping structure for table tokoemaspadi.tm_gadai_detail
CREATE TABLE IF NOT EXISTS `tm_gadai_detail` (
  `no_gadai` varchar(50) NOT NULL,
  `no_urut` int(11) NOT NULL,
  `kode_kategori` varchar(50) NOT NULL,
  `nama_barang` varchar(50) DEFAULT NULL,
  `berat` double DEFAULT NULL,
  `kondisi` varchar(50) DEFAULT NULL,
  `status_surat` varchar(50) DEFAULT NULL,
  `nilai_jual` double unsigned DEFAULT NULL,
  PRIMARY KEY (`no_gadai`,`no_urut`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table tokoemaspadi.tm_gadai_detail: ~4 rows (approximately)
DELETE FROM `tm_gadai_detail`;
/*!40000 ALTER TABLE `tm_gadai_detail` DISABLE KEYS */;
INSERT INTO `tm_gadai_detail` (`no_gadai`, `no_urut`, `kode_kategori`, `nama_barang`, `berat`, `kondisi`, `status_surat`, `nilai_jual`) VALUES
	('PT-190318-0001', 0, 'MT', 'kl mt', 5, NULL, NULL, 2250000),
	('PT-190721-0001', 1, 'MT', ' tes', 5, NULL, NULL, 2250000),
	('PT-190730-0001', 1, 'MT', 'kl mt', 5, NULL, NULL, 2250000),
	('PT-190730-0002', 1, 'MDS', 'tes', 1, 'Baik', 'Ada', 270000);
/*!40000 ALTER TABLE `tm_gadai_detail` ENABLE KEYS */;


-- Dumping structure for table tokoemaspadi.tm_gadai_head
CREATE TABLE IF NOT EXISTS `tm_gadai_head` (
  `no_gadai` varchar(50) NOT NULL,
  `tgl_gadai` datetime DEFAULT NULL,
  `kode_sales` varchar(50) DEFAULT NULL,
  `nama` varchar(50) DEFAULT NULL,
  `alamat` varchar(50) DEFAULT NULL,
  `keterangan` varchar(500) DEFAULT NULL,
  `total_berat` double DEFAULT NULL,
  `total_pinjaman` double DEFAULT NULL,
  `lama_pinjam` int(11) DEFAULT NULL,
  `bunga_persen` double DEFAULT NULL,
  `bunga_komp` double DEFAULT NULL,
  `bunga_rp` double DEFAULT NULL,
  `jatuh_tempo` date DEFAULT NULL,
  `status_lunas` varchar(50) DEFAULT NULL,
  `tgl_lunas` datetime DEFAULT NULL,
  `sales_lunas` varchar(50) DEFAULT NULL,
  `status_batal` varchar(50) DEFAULT NULL,
  `tgl_batal` datetime DEFAULT NULL,
  `sales_batal` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`no_gadai`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- Dumping data for table tokoemaspadi.tm_gadai_head: ~4 rows (approximately)
DELETE FROM `tm_gadai_head`;
/*!40000 ALTER TABLE `tm_gadai_head` DISABLE KEYS */;
INSERT INTO `tm_gadai_head` (`no_gadai`, `tgl_gadai`, `kode_sales`, `nama`, `alamat`, `keterangan`, `total_berat`, `total_pinjaman`, `lama_pinjam`, `bunga_persen`, `bunga_komp`, `bunga_rp`, `jatuh_tempo`, `status_lunas`, `tgl_lunas`, `sales_lunas`, `status_batal`, `tgl_batal`, `sales_batal`) VALUES
	('PT-190318-0001', '2019-03-18 07:25:37', 'Fera', 'tedjo', '', '', 5, 1575000, 106, 3, 167000, 167000, '2019-06-16', 'true', '2019-07-30 05:38:45', 'SALES1', 'false', '2000-01-01 00:00:00', ''),
	('PT-190721-0001', '2019-07-21 17:38:08', 'SALES1', '', '', '', 5, 1575000, 9, 3, 14500, 14500, '2019-10-19', 'false', '2000-01-01 00:00:00', '', 'false', '2000-01-01 00:00:00', ''),
	('PT-190730-0001', '2019-07-30 05:38:45', 'SALES1', 'tedjo', '', 'No Hutang Lama : PT-190318-0001\nPinjaman : 1,575,000\nCicil Pinjaman : 575,000\nBunga Dibayar : 167,000', 5, 1000000, 0, 3, 0, 0, '2019-10-28', 'false', '2000-01-01 00:00:00', '', 'false', '2000-01-01 00:00:00', ''),
	('PT-190730-0002', '2019-07-30 06:23:47', 'SALES1', 'tes', 'tes', '', 1, 189000, 1, 4, 500, 500, '2019-10-28', 'false', '2000-01-01 00:00:00', '', 'false', '2000-01-01 00:00:00', '');
/*!40000 ALTER TABLE `tm_gadai_head` ENABLE KEYS */;


-- Dumping structure for table tokoemaspadi.tm_jenis
CREATE TABLE IF NOT EXISTS `tm_jenis` (
  `kode_jenis` varchar(50) NOT NULL,
  `nama_jenis` varchar(50) NOT NULL,
  `kode_kategori` varchar(50) NOT NULL,
  PRIMARY KEY (`kode_jenis`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table tokoemaspadi.tm_jenis: ~22 rows (approximately)
DELETE FROM `tm_jenis`;
/*!40000 ALTER TABLE `tm_jenis` DISABLE KEYS */;
INSERT INTO `tm_jenis` (`kode_jenis`, `nama_jenis`, `kode_kategori`) VALUES
	('ATMD', 'anting md', 'MD'),
	('ATMDS', 'anting mds', 'MDS'),
	('ATMT', 'anting mt', 'MT'),
	('CCINTEN', 'cincin inten', 'INTEN'),
	('CCMD', 'cincin md', 'MD'),
	('CCMDS', 'cincin mds', 'MDS'),
	('CCMT', 'cincin mt', 'MT'),
	('GLINTEN', 'gelang inten', 'INTEN'),
	('GLMD', 'gelang md', 'MD'),
	('GLMDS', 'gelang mds', 'MDS'),
	('GLMT', 'gelang mt', 'MT'),
	('KLMD', 'kalung md', 'MD'),
	('KLMDS', 'kalung mds', 'MDS'),
	('KLMT', 'kalung mt', 'MT'),
	('MNINTEN', 'mainan inten', 'INTEN'),
	('MNMD', 'mainan md', 'MD'),
	('MNMDS', 'mainan mds', 'MDS'),
	('MNMT', 'mainan mt', 'MT'),
	('TDINTEN', 'tindik inten', 'INTEN'),
	('TDMD', 'tindik md', 'MD'),
	('TDMDS', 'tindik mds', 'MDS'),
	('TDMT', 'tindik mt', 'MT');
/*!40000 ALTER TABLE `tm_jenis` ENABLE KEYS */;


-- Dumping structure for table tokoemaspadi.tm_kategori
CREATE TABLE IF NOT EXISTS `tm_kategori` (
  `kode_kategori` varchar(50) NOT NULL,
  `nama_kategori` varchar(50) NOT NULL,
  `harga_beli` double NOT NULL,
  `harga_jual` double NOT NULL,
  PRIMARY KEY (`kode_kategori`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- Dumping data for table tokoemaspadi.tm_kategori: ~7 rows (approximately)
DELETE FROM `tm_kategori`;
/*!40000 ALTER TABLE `tm_kategori` DISABLE KEYS */;
INSERT INTO `tm_kategori` (`kode_kategori`, `nama_kategori`, `harga_beli`, `harga_jual`) VALUES
	('INTEN', 'inten md', 250000, 550000),
	('MD', 'emas muda', 200000, 210000),
	('MDS', 'emas tengahan', 260000, 270000),
	('MT', 'emas tua', 400000, 450000),
	('MT87', 'MT87', 450000, 525000),
	('MT916', 'MT916', 550000, 650000),
	('MT99', 'MT99', 575000, 675000);
/*!40000 ALTER TABLE `tm_kategori` ENABLE KEYS */;


-- Dumping structure for table tokoemaspadi.tm_kategori_transaksi
CREATE TABLE IF NOT EXISTS `tm_kategori_transaksi` (
  `kode_kategori` varchar(50) NOT NULL,
  `jenis_transaksi` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`kode_kategori`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table tokoemaspadi.tm_kategori_transaksi: ~5 rows (approximately)
DELETE FROM `tm_kategori_transaksi`;
/*!40000 ALTER TABLE `tm_kategori_transaksi` DISABLE KEYS */;
INSERT INTO `tm_kategori_transaksi` (`kode_kategori`, `jenis_transaksi`) VALUES
	('BEBAN GAJI', 'K'),
	('BEBAN OPERASIONAL', 'K'),
	('PEMBELIAN SUPPLIER', 'K'),
	('PENDAPATAN LAIN-LAIN', 'D'),
	('TAMBAH MODAL', 'D');
/*!40000 ALTER TABLE `tm_kategori_transaksi` ENABLE KEYS */;


-- Dumping structure for table tokoemaspadi.tm_log_harga
CREATE TABLE IF NOT EXISTS `tm_log_harga` (
  `tanggal` datetime NOT NULL,
  `kode_kategori` varchar(50) NOT NULL,
  `harga_beli` double NOT NULL,
  `harga_jual` double NOT NULL,
  PRIMARY KEY (`tanggal`,`kode_kategori`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table tokoemaspadi.tm_log_harga: ~1 rows (approximately)
DELETE FROM `tm_log_harga`;
/*!40000 ALTER TABLE `tm_log_harga` DISABLE KEYS */;
INSERT INTO `tm_log_harga` (`tanggal`, `kode_kategori`, `harga_beli`, `harga_jual`) VALUES
	('2019-07-25 00:00:00', 'MD', 200000, 210000);
/*!40000 ALTER TABLE `tm_log_harga` ENABLE KEYS */;


-- Dumping structure for table tokoemaspadi.tm_otoritas
CREATE TABLE IF NOT EXISTS `tm_otoritas` (
  `username` varchar(50) NOT NULL,
  `jenis` varchar(50) NOT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`username`,`jenis`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table tokoemaspadi.tm_otoritas: ~46 rows (approximately)
DELETE FROM `tm_otoritas`;
/*!40000 ALTER TABLE `tm_otoritas` DISABLE KEYS */;
INSERT INTO `tm_otoritas` (`username`, `jenis`, `status`) VALUES
	('kasir', 'Ambil Barang', 'false'),
	('kasir', 'Barcode Barang', 'false'),
	('kasir', 'Data Pelanggan', 'true'),
	('kasir', 'Data Pelunasan Gadai', 'false'),
	('kasir', 'Data Pembelian', 'true'),
	('kasir', 'Data Penjualan', 'true'),
	('kasir', 'Data Sales', 'false'),
	('kasir', 'Data Terima Gadai', 'true'),
	('kasir', 'Detail Barang Barcode', 'false'),
	('kasir', 'Keuangan', 'false'),
	('kasir', 'Laporan Barang', 'false'),
	('kasir', 'Laporan Gadai', 'false'),
	('kasir', 'Laporan Keuangan', 'false'),
	('kasir', 'Laporan Pembelian', 'false'),
	('kasir', 'Laporan Penjualan', 'false'),
	('kasir', 'Pelunasan Gadai', 'false'),
	('kasir', 'Pembelian Baru', 'true'),
	('kasir', 'Pengaturan Umum', 'false'),
	('kasir', 'Penjualan Baru', 'true'),
	('kasir', 'Stok Barang Barcode', 'false'),
	('kasir', 'Stok Barang Dalam', 'false'),
	('kasir', 'Tambah Barang', 'false'),
	('kasir', 'Terima Gadai', 'true'),
	('user', 'Ambil Barang', 'true'),
	('user', 'Barcode Barang', 'true'),
	('user', 'Data Pelanggan', 'true'),
	('user', 'Data Pelunasan Gadai', 'true'),
	('user', 'Data Pembelian', 'true'),
	('user', 'Data Penjualan', 'true'),
	('user', 'Data Sales', 'true'),
	('user', 'Data Terima Gadai', 'true'),
	('user', 'Detail Barang Barcode', 'true'),
	('user', 'Keuangan', 'true'),
	('user', 'Laporan Barang', 'true'),
	('user', 'Laporan Gadai', 'true'),
	('user', 'Laporan Keuangan', 'true'),
	('user', 'Laporan Pembelian', 'true'),
	('user', 'Laporan Penjualan', 'true'),
	('user', 'Pelunasan Gadai', 'true'),
	('user', 'Pembelian Baru', 'true'),
	('user', 'Pengaturan Umum', 'true'),
	('user', 'Penjualan Baru', 'true'),
	('user', 'Stok Barang Barcode', 'true'),
	('user', 'Stok Barang Dalam', 'true'),
	('user', 'Tambah Barang', 'true'),
	('user', 'Terima Gadai', 'true');
/*!40000 ALTER TABLE `tm_otoritas` ENABLE KEYS */;


-- Dumping structure for table tokoemaspadi.tm_sales
CREATE TABLE IF NOT EXISTS `tm_sales` (
  `nama` varchar(50) NOT NULL,
  PRIMARY KEY (`nama`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table tokoemaspadi.tm_sales: ~1 rows (approximately)
DELETE FROM `tm_sales`;
/*!40000 ALTER TABLE `tm_sales` DISABLE KEYS */;
INSERT INTO `tm_sales` (`nama`) VALUES
	('SALES1');
/*!40000 ALTER TABLE `tm_sales` ENABLE KEYS */;


-- Dumping structure for table tokoemaspadi.tm_system
CREATE TABLE IF NOT EXISTS `tm_system` (
  `nama` varchar(50) DEFAULT NULL,
  `alamat` varchar(50) DEFAULT NULL,
  `kota` varchar(50) DEFAULT NULL,
  `no_telp` varchar(50) DEFAULT NULL,
  `website` varchar(50) DEFAULT NULL,
  `tgl_system` date DEFAULT NULL,
  `persentase_pinjaman` double DEFAULT NULL,
  `jatuh_tempo` int(11) DEFAULT NULL,
  `code` varchar(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- Dumping data for table tokoemaspadi.tm_system: ~1 rows (approximately)
DELETE FROM `tm_system`;
/*!40000 ALTER TABLE `tm_system` DISABLE KEYS */;
INSERT INTO `tm_system` (`nama`, `alamat`, `kota`, `no_telp`, `website`, `tgl_system`, `persentase_pinjaman`, `jatuh_tempo`, `code`) VALUES
	('Toko Emas PADI', 'Pasar Ungaran No. 6', 'Ungaran', '', '', '2019-07-30', 70, 90, '0');
/*!40000 ALTER TABLE `tm_system` ENABLE KEYS */;


-- Dumping structure for table tokoemaspadi.tm_user
CREATE TABLE IF NOT EXISTS `tm_user` (
  `username` varchar(50) NOT NULL,
  `password` varchar(50) DEFAULT NULL,
  `level` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table tokoemaspadi.tm_user: ~2 rows (approximately)
DELETE FROM `tm_user`;
/*!40000 ALTER TABLE `tm_user` DISABLE KEYS */;
INSERT INTO `tm_user` (`username`, `password`, `level`) VALUES
	('kasir', 'kasir', 'Sales'),
	('user', 'user', 'Admin');
/*!40000 ALTER TABLE `tm_user` ENABLE KEYS */;


-- Dumping structure for table tokoemaspadi.tm_verifikasi
CREATE TABLE IF NOT EXISTS `tm_verifikasi` (
  `username` varchar(50) NOT NULL,
  `jenis` varchar(50) NOT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`username`,`jenis`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table tokoemaspadi.tm_verifikasi: ~1 rows (approximately)
DELETE FROM `tm_verifikasi`;
/*!40000 ALTER TABLE `tm_verifikasi` DISABLE KEYS */;
INSERT INTO `tm_verifikasi` (`username`, `jenis`, `status`) VALUES
	('user', 'Hancur Barang', 'true');
/*!40000 ALTER TABLE `tm_verifikasi` ENABLE KEYS */;


-- Dumping structure for table tokoemaspadi.tt_hancur_detail
CREATE TABLE IF NOT EXISTS `tt_hancur_detail` (
  `no_hancur` varchar(50) NOT NULL,
  `no_urut` int(11) NOT NULL,
  `kode_barcode` varchar(50) DEFAULT NULL,
  `qty` int(11) DEFAULT NULL,
  PRIMARY KEY (`no_hancur`,`no_urut`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- Dumping data for table tokoemaspadi.tt_hancur_detail: ~1 rows (approximately)
DELETE FROM `tt_hancur_detail`;
/*!40000 ALTER TABLE `tt_hancur_detail` DISABLE KEYS */;
INSERT INTO `tt_hancur_detail` (`no_hancur`, `no_urut`, `kode_barcode`, `qty`) VALUES
	('HB-190725-0001', 1, '00000002', 1);
/*!40000 ALTER TABLE `tt_hancur_detail` ENABLE KEYS */;


-- Dumping structure for table tokoemaspadi.tt_hancur_head
CREATE TABLE IF NOT EXISTS `tt_hancur_head` (
  `no_hancur` varchar(50) NOT NULL,
  `tgl_hancur` datetime DEFAULT NULL,
  `total_qty` int(11) DEFAULT NULL,
  `total_berat` double DEFAULT NULL,
  `total_berat_pembulatan` double DEFAULT NULL,
  `kode_user` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`no_hancur`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- Dumping data for table tokoemaspadi.tt_hancur_head: ~1 rows (approximately)
DELETE FROM `tt_hancur_head`;
/*!40000 ALTER TABLE `tt_hancur_head` DISABLE KEYS */;
INSERT INTO `tt_hancur_head` (`no_hancur`, `tgl_hancur`, `total_qty`, `total_berat`, `total_berat_pembulatan`, `kode_user`) VALUES
	('HB-190725-0001', '2019-07-25 21:07:13', 1, 1.11, 1.2, 'user');
/*!40000 ALTER TABLE `tt_hancur_head` ENABLE KEYS */;


-- Dumping structure for table tokoemaspadi.tt_keuangan
CREATE TABLE IF NOT EXISTS `tt_keuangan` (
  `no_keuangan` varchar(50) NOT NULL,
  `tgl_keuangan` datetime DEFAULT NULL,
  `kategori` varchar(50) DEFAULT NULL,
  `keterangan` varchar(500) DEFAULT NULL,
  `jumlah_rp` double DEFAULT NULL,
  `kode_user` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table tokoemaspadi.tt_keuangan: ~11 rows (approximately)
DELETE FROM `tt_keuangan`;
/*!40000 ALTER TABLE `tt_keuangan` DISABLE KEYS */;
INSERT INTO `tt_keuangan` (`no_keuangan`, `tgl_keuangan`, `kategori`, `keterangan`, `jumlah_rp`, `kode_user`) VALUES
	('KK-190318-0001', '2019-03-18 07:24:14', 'Penjualan', 'PJ-190318-0001', 210000, 'Dwi'),
	('KK-190318-0002', '2019-03-18 07:24:49', 'Pembelian', 'PB-190318-0001', 2080000, 'Dwi'),
	('KK-190318-0003', '2019-03-18 07:25:37', 'Terima Gadai', 'PT-190318-0001', 1575000, 'Fera'),
	('KK-190617-0001', '2019-06-17 12:17:01', 'Pembelian', 'PB-190617-0001', 400000, 'Nurul'),
	('KK-190721-0001', '2019-07-21 17:38:08', 'Terima Gadai', 'PT-190721-0001', -1575000, 'SALES1'),
	('KK-190721-0002', '2019-07-21 17:40:41', 'Penjualan', 'PJ-190721-0001', 630000, 'SALES1'),
	('KK-190725-0001', '2019-07-25 21:56:59', 'Penjualan', 'PJ-190725-0001', 462000, 'SALES1'),
	('KK-190730-0001', '2019-07-30 05:38:45', 'Pelunasan Gadai', 'PT-190318-0001', 1575000, 'SALES1'),
	('KK-190730-0002', '2019-07-30 05:38:45', 'Bunga Gadai', 'PT-190318-0001', 167000, 'SALES1'),
	('KK-190730-0003', '2019-07-30 05:38:45', 'Terima Gadai', 'PT-190730-0001', -1000000, 'SALES1'),
	('KK-190730-0004', '2019-07-30 06:23:47', 'Terima Gadai', 'PT-190730-0002', -189000, 'SALES1');
/*!40000 ALTER TABLE `tt_keuangan` ENABLE KEYS */;


-- Dumping structure for table tokoemaspadi.tt_pembelian_detail
CREATE TABLE IF NOT EXISTS `tt_pembelian_detail` (
  `no_pembelian` varchar(50) NOT NULL,
  `no_urut` int(11) NOT NULL,
  `kode_kategori` varchar(50) DEFAULT NULL,
  `nama_barang` varchar(50) DEFAULT NULL,
  `berat` double DEFAULT NULL,
  `kondisi` varchar(50) DEFAULT NULL,
  `status_surat` varchar(50) DEFAULT NULL,
  `harga_komp` double DEFAULT NULL,
  `harga_beli` double DEFAULT NULL,
  PRIMARY KEY (`no_pembelian`,`no_urut`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table tokoemaspadi.tt_pembelian_detail: ~2 rows (approximately)
DELETE FROM `tt_pembelian_detail`;
/*!40000 ALTER TABLE `tt_pembelian_detail` DISABLE KEYS */;
INSERT INTO `tt_pembelian_detail` (`no_pembelian`, `no_urut`, `kode_kategori`, `nama_barang`, `berat`, `kondisi`, `status_surat`, `harga_komp`, `harga_beli`) VALUES
	('PB-190318-0001', 0, 'MT', 'mainan mt', 5.5, NULL, NULL, 2080000, 2080000),
	('PB-190617-0001', 0, 'INTEN', 'inten md', 2, 'Baik', NULL, 400000, 400000);
/*!40000 ALTER TABLE `tt_pembelian_detail` ENABLE KEYS */;


-- Dumping structure for table tokoemaspadi.tt_pembelian_head
CREATE TABLE IF NOT EXISTS `tt_pembelian_head` (
  `no_pembelian` varchar(50) NOT NULL,
  `tgl_pembelian` datetime DEFAULT NULL,
  `kode_sales` varchar(50) DEFAULT NULL,
  `nama` varchar(50) DEFAULT NULL,
  `alamat` varchar(50) DEFAULT NULL,
  `total_berat` double DEFAULT NULL,
  `total_pembelian` double DEFAULT NULL,
  `catatan` varchar(500) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `tgl_batal` datetime DEFAULT NULL,
  `user_batal` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`no_pembelian`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- Dumping data for table tokoemaspadi.tt_pembelian_head: ~2 rows (approximately)
DELETE FROM `tt_pembelian_head`;
/*!40000 ALTER TABLE `tt_pembelian_head` DISABLE KEYS */;
INSERT INTO `tt_pembelian_head` (`no_pembelian`, `tgl_pembelian`, `kode_sales`, `nama`, `alamat`, `total_berat`, `total_pembelian`, `catatan`, `status`, `tgl_batal`, `user_batal`) VALUES
	('PB-190318-0001', '2019-03-18 07:24:49', 'Dwi', '', '', 5.5, 2080000, '', 'true', '2000-01-01 00:00:00', ''),
	('PB-190617-0001', '2019-06-17 12:17:01', 'Nurul', 'tes', 'tes', 2, 400000, '', 'true', '2000-01-01 00:00:00', '');
/*!40000 ALTER TABLE `tt_pembelian_head` ENABLE KEYS */;


-- Dumping structure for table tokoemaspadi.tt_penjualan_detail
CREATE TABLE IF NOT EXISTS `tt_penjualan_detail` (
  `no_penjualan` varchar(50) NOT NULL,
  `no_urut` int(11) NOT NULL,
  `kode_barcode` varchar(50) NOT NULL,
  `kode_kategori` varchar(50) NOT NULL,
  `kode_jenis` varchar(50) NOT NULL,
  `nama_barang` varchar(50) DEFAULT NULL,
  `qty` int(11) DEFAULT NULL,
  `berat` double DEFAULT NULL,
  `berat_pembulatan` double DEFAULT NULL,
  `nilai_pokok` double NOT NULL,
  `harga_komp` double NOT NULL,
  `harga_jual` double NOT NULL,
  `total` double DEFAULT NULL,
  `no_pembelian` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`no_penjualan`,`no_urut`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table tokoemaspadi.tt_penjualan_detail: ~3 rows (approximately)
DELETE FROM `tt_penjualan_detail`;
/*!40000 ALTER TABLE `tt_penjualan_detail` DISABLE KEYS */;
INSERT INTO `tt_penjualan_detail` (`no_penjualan`, `no_urut`, `kode_barcode`, `kode_kategori`, `kode_jenis`, `nama_barang`, `qty`, `berat`, `berat_pembulatan`, `nilai_pokok`, `harga_komp`, `harga_jual`, `total`, `no_pembelian`) VALUES
	('PJ-190318-0001', 1, '00000001', 'MD', 'CCMD', 'ant md', 1, 1, 1, 190000, 210000, 210000, 210000, ''),
	('PJ-190721-0001', 1, '00000001', 'MD', 'CCMD', 'tes', 3, 1, 1, 190000, 210000, 210000, 630000, ''),
	('PJ-190725-0001', 1, '00000003', 'MD', 'KLMD', 'tes', 2, 1.1, 1.2, 209000, 231000, 231000, 462000, '');
/*!40000 ALTER TABLE `tt_penjualan_detail` ENABLE KEYS */;


-- Dumping structure for table tokoemaspadi.tt_penjualan_head
CREATE TABLE IF NOT EXISTS `tt_penjualan_head` (
  `no_penjualan` varchar(50) NOT NULL,
  `tgl_penjualan` datetime DEFAULT NULL,
  `kode_sales` varchar(50) DEFAULT NULL,
  `nama` varchar(50) DEFAULT NULL,
  `alamat` varchar(50) DEFAULT NULL,
  `total_qty` double DEFAULT NULL,
  `total_berat` double DEFAULT NULL,
  `total_berat_pembulatan` double DEFAULT NULL,
  `grandtotal` double DEFAULT NULL,
  `catatan` varchar(500) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `tgl_batal` datetime DEFAULT NULL,
  `user_batal` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`no_penjualan`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- Dumping data for table tokoemaspadi.tt_penjualan_head: ~3 rows (approximately)
DELETE FROM `tt_penjualan_head`;
/*!40000 ALTER TABLE `tt_penjualan_head` DISABLE KEYS */;
INSERT INTO `tt_penjualan_head` (`no_penjualan`, `tgl_penjualan`, `kode_sales`, `nama`, `alamat`, `total_qty`, `total_berat`, `total_berat_pembulatan`, `grandtotal`, `catatan`, `status`, `tgl_batal`, `user_batal`) VALUES
	('PJ-190318-0001', '2019-03-18 07:24:14', 'Dwi', '', '', 1, 1, 1, 210000, '', 'true', '2000-01-01 00:00:00', ''),
	('PJ-190721-0001', '2019-07-21 17:40:41', 'SALES1', '', '', 3, 1, 1, 630000, '', 'true', '2000-01-01 00:00:00', ''),
	('PJ-190725-0001', '2019-07-25 21:56:59', 'SALES1', 'te', 'tes', 2, 1.1, 1.2, 462000, '', 'true', '2000-01-01 00:00:00', '');
/*!40000 ALTER TABLE `tt_penjualan_head` ENABLE KEYS */;


-- Dumping structure for table tokoemaspadi.tt_stok_barang
CREATE TABLE IF NOT EXISTS `tt_stok_barang` (
  `tanggal` date NOT NULL,
  `kode_jenis` varchar(50) NOT NULL DEFAULT '',
  `kode_barcode` varchar(50) NOT NULL,
  `stok_awal` int(11) DEFAULT '0',
  `berat_awal` double DEFAULT '0',
  `stok_masuk` int(11) DEFAULT '0',
  `berat_masuk` double DEFAULT '0',
  `stok_keluar` int(11) DEFAULT '0',
  `berat_keluar` double DEFAULT '0',
  `stok_akhir` int(11) DEFAULT '0',
  `berat_akhir` double DEFAULT '0',
  PRIMARY KEY (`tanggal`,`kode_barcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- Dumping data for table tokoemaspadi.tt_stok_barang: ~4 rows (approximately)
DELETE FROM `tt_stok_barang`;
/*!40000 ALTER TABLE `tt_stok_barang` DISABLE KEYS */;
INSERT INTO `tt_stok_barang` (`tanggal`, `kode_jenis`, `kode_barcode`, `stok_awal`, `berat_awal`, `stok_masuk`, `berat_masuk`, `stok_keluar`, `berat_keluar`, `stok_akhir`, `berat_akhir`) VALUES
	('2019-07-21', 'CCMD', '00000001', 0, 0, 3, 3, 3, 3, 0, 0),
	('2019-07-25', 'CCMT', '00000002', 0, 0, 1, 1.11, 1, 1.11, 0, 0),
	('2019-07-25', 'KLMD', '00000003', 0, 0, 2, 2.2, 2, 2.2, 0, 0),
	('2019-07-30', 'CCMT', '00000004', 0, 0, 3, 4.65, 0, 0, 3, 4.65);
/*!40000 ALTER TABLE `tt_stok_barang` ENABLE KEYS */;


-- Dumping structure for table tokoemaspadi.tt_stok_opname_detail
CREATE TABLE IF NOT EXISTS `tt_stok_opname_detail` (
  `no_stok_opname` varchar(50) NOT NULL,
  `no_urut` int(11) NOT NULL,
  `kode_barcode` varchar(50) DEFAULT NULL,
  `qty` int(11) DEFAULT NULL,
  `qty_distok` int(11) DEFAULT NULL,
  PRIMARY KEY (`no_stok_opname`,`no_urut`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table tokoemaspadi.tt_stok_opname_detail: ~2 rows (approximately)
DELETE FROM `tt_stok_opname_detail`;
/*!40000 ALTER TABLE `tt_stok_opname_detail` DISABLE KEYS */;
INSERT INTO `tt_stok_opname_detail` (`no_stok_opname`, `no_urut`, `kode_barcode`, `qty`, `qty_distok`) VALUES
	('SO-190706-0001', 1, '00000007', 2, 2),
	('SO-190721-0001', 1, '00000001', 3, 2);
/*!40000 ALTER TABLE `tt_stok_opname_detail` ENABLE KEYS */;


-- Dumping structure for table tokoemaspadi.tt_stok_opname_head
CREATE TABLE IF NOT EXISTS `tt_stok_opname_head` (
  `no_stok_opname` varchar(50) NOT NULL,
  `tgl_stok_opname` datetime DEFAULT NULL,
  `kode_kategori` varchar(50) DEFAULT NULL,
  `kode_jenis` varchar(50) DEFAULT NULL,
  `total_qty` int(11) DEFAULT NULL,
  `total_berat` double DEFAULT NULL,
  `total_berat_pembulatan` double DEFAULT NULL,
  PRIMARY KEY (`no_stok_opname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table tokoemaspadi.tt_stok_opname_head: ~5 rows (approximately)
DELETE FROM `tt_stok_opname_head`;
/*!40000 ALTER TABLE `tt_stok_opname_head` DISABLE KEYS */;
INSERT INTO `tt_stok_opname_head` (`no_stok_opname`, `tgl_stok_opname`, `kode_kategori`, `kode_jenis`, `total_qty`, `total_berat`, `total_berat_pembulatan`) VALUES
	('SO-190617-0001', '2019-06-17 04:43:39', 'MDS', 'Semua', 0, 0, NULL),
	('SO-190705-0001', '2019-07-05 21:55:21', 'Semua', 'Semua', 0, 0, 0),
	('SO-190706-0001', '2019-07-06 08:06:09', 'Semua', 'Semua', 2, 20.2, 21),
	('SO-190721-0001', '2019-07-21 15:58:58', 'Semua', 'Semua', 3, 3, 3),
	('SO-190725-0001', '2019-07-25 21:08:55', 'Semua', 'Semua', 0, 0, 0);
/*!40000 ALTER TABLE `tt_stok_opname_head` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
