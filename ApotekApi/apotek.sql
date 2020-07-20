-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 20, 2020 at 02:23 AM
-- Server version: 10.1.37-MariaDB
-- PHP Version: 7.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `apotek`
--

-- --------------------------------------------------------

--
-- Table structure for table `daftar_obat`
--

CREATE TABLE `daftar_obat` (
  `id` int(11) NOT NULL,
  `nama_obat` varchar(128) NOT NULL,
  `harga` int(64) NOT NULL,
  `jumlah` int(64) NOT NULL,
  `image` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `daftar_obat`
--

INSERT INTO `daftar_obat` (`id`, `nama_obat`, `harga`, `jumlah`, `image`) VALUES
(1, 'Paramex', 2000, 19, 'http://192.168.43.83/Apotek/asset/img/obat/paramex.jpg'),
(2, 'Paracetamol', 2500, 7, 'http://192.168.43.83/Apotek/asset/img/obat/paracetamol.jpg'),
(3, 'Bufacort', 8000, 24, 'http://192.168.43.83/Apotek/asset/img/obat/bufacort.jpg'),
(4, 'Hufagrip', 15000, 17, 'http://192.168.43.83/Apotek/asset/img/obat/hufagrip.jpg'),
(5, 'Termorex', 20000, 5, 'http://192.168.43.83/Apotek/asset/img/obat/termorex.jpg'),
(6, 'Bodrex', 2000, 14, 'http://192.168.43.83/Apotek/asset/img/obat/bodrex.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `pesan`
--

CREATE TABLE `pesan` (
  `id` int(11) NOT NULL,
  `nama_obat` varchar(64) NOT NULL,
  `jumlah` int(128) NOT NULL,
  `harga` int(128) NOT NULL,
  `tanggal` date NOT NULL,
  `checkout` varchar(500) DEFAULT NULL,
  `id_user` int(11) NOT NULL,
  `id_stat` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pesan`
--

INSERT INTO `pesan` (`id`, `nama_obat`, `jumlah`, `harga`, `tanggal`, `checkout`, `id_user`, `id_stat`) VALUES
(6, 'Bodrex', 1, 2000, '2020-07-19', NULL, 2, 1),
(7, 'Bufacort', 1, 8000, '2020-07-20', NULL, 2, 1),
(8, 'Hufagrip', 1, 15000, '2020-07-20', NULL, 2, 1),
(9, 'Bufacort', 2, 16000, '2020-07-20', NULL, 4, 1),
(10, 'Bufacort', 1, 8000, '2020-07-20', NULL, 4, 1),
(11, 'Hufagrip', 10, 150000, '2020-07-20', NULL, 4, 1);

-- --------------------------------------------------------

--
-- Table structure for table `status`
--

CREATE TABLE `status` (
  `id` int(11) NOT NULL,
  `nm_status` varchar(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `status`
--

INSERT INTO `status` (`id`, `nm_status`) VALUES
(1, 'Belum dibayar'),
(2, 'Sedang dikemas'),
(3, 'Sedang dikirim'),
(4, 'Sampai tujuan');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `nama` varchar(64) NOT NULL,
  `alamat` varchar(128) NOT NULL,
  `telp` varchar(12) NOT NULL,
  `username` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `nama`, `alamat`, `telp`, `username`, `password`) VALUES
(1, 'Ajay', 'Indramayu', '081947193502', 'mad14', '$2y$10$fZyG0yvxf7L30z.gAudEAuh7B41WcQvBLYb/QvWsux8YKl2OYZl.q'),
(2, 'Dwi Madya  S', 'Lohbener', '081947193502', 'hylos', '$2y$10$M6sdW7vb3hX5FudBOTyqsehDynAzGxU7XwSlYPackgXeUUy0SHeSO'),
(3, 'caca', 'Lohbener', '089502684312', 'caka', '$2y$10$IeU7QPhmx4maf73dx1zY5.CON7phr/kAgQP9Tq49021ZUGULKS6RG'),
(4, 'Dwi Madya Sidik Fadhil', 'Lohbener', '081947193502', 'mad', '$2y$10$uAoeW/Yju9vp/Nz1KfHwAOXr2G4dRafvuRREt6jHHjdzRyJjauGbK'),
(5, 'Dwi madya', 'Lohbener', '081947193502', 'mad', '$2y$10$VnF0TavXDgYJyyhBjqXh9u6nqetiCH2cQvtv63ITo9UMRA8hvoa3S'),
(6, 'Dwi madya', 'Lohbener', '081947193502', 'mad', '$2y$10$Gyhj9NV1OfPDaS4w3.SAWe2mgKlXkPF8GZi1VWndCf0f/q3lpwCr2');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `daftar_obat`
--
ALTER TABLE `daftar_obat`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pesan`
--
ALTER TABLE `pesan`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `status`
--
ALTER TABLE `status`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `daftar_obat`
--
ALTER TABLE `daftar_obat`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `pesan`
--
ALTER TABLE `pesan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `status`
--
ALTER TABLE `status`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
