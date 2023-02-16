CREATE DATABASE  IF NOT EXISTS `podsistem3` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `podsistem3`;
-- MySQL dump 10.13  Distrib 8.0.32, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: podsistem3
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Adresa`
--

DROP TABLE IF EXISTS `Adresa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Adresa` (
  `idAdresa` int unsigned NOT NULL AUTO_INCREMENT,
  `Ulica` varchar(45) NOT NULL,
  `Broj` int unsigned NOT NULL,
  PRIMARY KEY (`idAdresa`),
  UNIQUE KEY `idAdresa_UNIQUE` (`idAdresa`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Adresa`
--

LOCK TABLES `Adresa` WRITE;
/*!40000 ALTER TABLE `Adresa` DISABLE KEYS */;
INSERT INTO `Adresa` VALUES (1,'Humska',1),(2,'Praska',34),(3,'Sutjeska',2),(4,'Dusanov grad bb',38400),(5,'Generala Milojka Lesjanina',14);
/*!40000 ALTER TABLE `Adresa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Artikal`
--

DROP TABLE IF EXISTS `Artikal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Artikal` (
  `idArtikal` int unsigned NOT NULL AUTO_INCREMENT,
  `Naziv` varchar(45) NOT NULL,
  `Opis` varchar(100) DEFAULT NULL,
  `Cena` float unsigned NOT NULL,
  `Popust` int unsigned NOT NULL,
  PRIMARY KEY (`idArtikal`),
  UNIQUE KEY `idArtikal_UNIQUE` (`idArtikal`),
  UNIQUE KEY `Naziv_UNIQUE` (`Naziv`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Artikal`
--

LOCK TABLES `Artikal` WRITE;
/*!40000 ALTER TABLE `Artikal` DISABLE KEYS */;
INSERT INTO `Artikal` VALUES (1,'Kosarkaska lopta','outdoor',1500,5),(2,'Bianconeri duks',NULL,3000,10);
/*!40000 ALTER TABLE `Artikal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Grad`
--

DROP TABLE IF EXISTS `Grad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Grad` (
  `idGrad` int unsigned NOT NULL AUTO_INCREMENT,
  `Naziv` varchar(45) NOT NULL,
  `Drzava` varchar(45) NOT NULL,
  PRIMARY KEY (`idGrad`),
  UNIQUE KEY `idGrad_UNIQUE` (`idGrad`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Grad`
--

LOCK TABLES `Grad` WRITE;
/*!40000 ALTER TABLE `Grad` DISABLE KEYS */;
INSERT INTO `Grad` VALUES (1,'Beograd','Srbija'),(2,'Novi Sad','Srbija'),(3,'Nis','Srbija'),(4,'Prizren','Srbija'),(5,'Cacak','Srbija');
/*!40000 ALTER TABLE `Grad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Korisnik`
--

DROP TABLE IF EXISTS `Korisnik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Korisnik` (
  `KorisnickoIme` varchar(45) NOT NULL,
  `idGrad` int unsigned NOT NULL,
  `idAdresa` int unsigned NOT NULL,
  `Novac` float unsigned NOT NULL,
  PRIMARY KEY (`KorisnickoIme`),
  UNIQUE KEY `KorisnickoIme_UNIQUE` (`KorisnickoIme`),
  KEY `fk_Adresa_idx` (`idAdresa`),
  KEY `fk_Adresa_s3_idx` (`idAdresa`),
  KEY `fk_Grad_s3_idx` (`idGrad`),
  CONSTRAINT `fk_Adresa_s3` FOREIGN KEY (`idAdresa`) REFERENCES `Adresa` (`idAdresa`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_Grad_s3` FOREIGN KEY (`idGrad`) REFERENCES `Grad` (`idGrad`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Korisnik`
--

LOCK TABLES `Korisnik` WRITE;
/*!40000 ALTER TABLE `Korisnik` DISABLE KEYS */;
INSERT INTO `Korisnik` VALUES ('jokan',1,1,50000),('remax',1,2,20000),('zoc',1,1,0);
/*!40000 ALTER TABLE `Korisnik` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Korpa`
--

DROP TABLE IF EXISTS `Korpa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Korpa` (
  `idKorpa` int unsigned NOT NULL AUTO_INCREMENT,
  `UkupnaCena` float unsigned NOT NULL,
  `Korisnik` varchar(45) NOT NULL,
  PRIMARY KEY (`idKorpa`),
  UNIQUE KEY `idKorpa_UNIQUE` (`idKorpa`),
  UNIQUE KEY `Korisnik_UNIQUE` (`Korisnik`),
  KEY `fk_Korpa_s3_1_idx` (`Korisnik`),
  CONSTRAINT `fk_Korpa_s3_1` FOREIGN KEY (`Korisnik`) REFERENCES `Korisnik` (`KorisnickoIme`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Korpa`
--

LOCK TABLES `Korpa` WRITE;
/*!40000 ALTER TABLE `Korpa` DISABLE KEYS */;
INSERT INTO `Korpa` VALUES (2,0,'zoc');
/*!40000 ALTER TABLE `Korpa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Narudzbina`
--

DROP TABLE IF EXISTS `Narudzbina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Narudzbina` (
  `idNarudzbina` int unsigned NOT NULL AUTO_INCREMENT,
  `UkupnaCena` float unsigned NOT NULL,
  `VremeKreiranja` datetime NOT NULL,
  `Korisnik` varchar(45) NOT NULL,
  PRIMARY KEY (`idNarudzbina`),
  UNIQUE KEY `idNarudzbina_UNIQUE` (`idNarudzbina`),
  KEY `fk_Narudzbina_1_idx` (`Korisnik`),
  CONSTRAINT `fk_Narudzbina_1` FOREIGN KEY (`Korisnik`) REFERENCES `Korisnik` (`KorisnickoIme`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Narudzbina`
--

LOCK TABLES `Narudzbina` WRITE;
/*!40000 ALTER TABLE `Narudzbina` DISABLE KEYS */;
/*!40000 ALTER TABLE `Narudzbina` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Sadrzi`
--

DROP TABLE IF EXISTS `Sadrzi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Sadrzi` (
  `idKorpa` int unsigned NOT NULL,
  `idArtikal` int unsigned NOT NULL,
  `KolicinaArtikla` int unsigned NOT NULL,
  PRIMARY KEY (`idKorpa`,`idArtikal`),
  KEY `fk_Sadrzi_s3_2_idx` (`idArtikal`),
  CONSTRAINT `fk_Sadrzi_s3_1` FOREIGN KEY (`idKorpa`) REFERENCES `Korpa` (`idKorpa`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_Sadrzi_s3_2` FOREIGN KEY (`idArtikal`) REFERENCES `Artikal` (`idArtikal`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Sadrzi`
--

LOCK TABLES `Sadrzi` WRITE;
/*!40000 ALTER TABLE `Sadrzi` DISABLE KEYS */;
/*!40000 ALTER TABLE `Sadrzi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Stavka`
--

DROP TABLE IF EXISTS `Stavka`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Stavka` (
  `idStavka` int unsigned NOT NULL AUTO_INCREMENT,
  `idNarudzbina` int unsigned NOT NULL,
  `idArtikal` int unsigned NOT NULL,
  `Kolicina` int unsigned NOT NULL,
  `JedinicnaCena` float unsigned NOT NULL,
  PRIMARY KEY (`idStavka`),
  UNIQUE KEY `idStavka_UNIQUE` (`idStavka`),
  UNIQUE KEY `idNarudzbina_UNIQUE` (`idNarudzbina`),
  UNIQUE KEY `idArtikal_UNIQUE` (`idArtikal`),
  CONSTRAINT `fk_Artikal_s3` FOREIGN KEY (`idArtikal`) REFERENCES `Artikal` (`idArtikal`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_Narudzbina` FOREIGN KEY (`idNarudzbina`) REFERENCES `Narudzbina` (`idNarudzbina`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Stavka`
--

LOCK TABLES `Stavka` WRITE;
/*!40000 ALTER TABLE `Stavka` DISABLE KEYS */;
/*!40000 ALTER TABLE `Stavka` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Transakcija`
--

DROP TABLE IF EXISTS `Transakcija`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Transakcija` (
  `idTransakcija` int unsigned NOT NULL AUTO_INCREMENT,
  `SumaNovca` float unsigned NOT NULL,
  `VremePlacanja` datetime NOT NULL,
  `idNarudzbina` int unsigned NOT NULL,
  PRIMARY KEY (`idTransakcija`),
  KEY `fk_Transakcija_1_idx` (`idNarudzbina`),
  CONSTRAINT `fk_Transakcija_1` FOREIGN KEY (`idNarudzbina`) REFERENCES `Narudzbina` (`idNarudzbina`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Transakcija`
--

LOCK TABLES `Transakcija` WRITE;
/*!40000 ALTER TABLE `Transakcija` DISABLE KEYS */;
/*!40000 ALTER TABLE `Transakcija` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-16 12:25:17
