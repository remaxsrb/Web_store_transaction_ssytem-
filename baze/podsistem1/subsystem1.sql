CREATE DATABASE  IF NOT EXISTS `podsistem1` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `podsistem1`;
-- MySQL dump 10.13  Distrib 8.0.32, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: podsistem1
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
  UNIQUE KEY `iAdresa_UNIQUE` (`idAdresa`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Adresa`
--

LOCK TABLES `Adresa` WRITE;
/*!40000 ALTER TABLE `Adresa` DISABLE KEYS */;
INSERT INTO `Adresa` VALUES (1,'Dusanov grad bb',38400),(2,'Generala Milojka Lesjanina',14),(3,'Humska',1),(4,'Praska',34),(5,'Sutjeska',2);
/*!40000 ALTER TABLE `Adresa` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Grad`
--

LOCK TABLES `Grad` WRITE;
/*!40000 ALTER TABLE `Grad` DISABLE KEYS */;
INSERT INTO `Grad` VALUES (1,'Beograd','Srbija'),(2,'Novi Sad','Srbija'),(3,'Prizren','Srbija'),(4,'Nis','Srbija');
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
  `Sifra` varchar(45) NOT NULL,
  `Ime` varchar(45) NOT NULL,
  `Prezime` varchar(45) NOT NULL,
  `idAdresa` int unsigned NOT NULL,
  `idGrad` int unsigned NOT NULL,
  `Novac` float NOT NULL,
  PRIMARY KEY (`KorisnickoIme`),
  UNIQUE KEY `KorisnickoIme_UNIQUE` (`KorisnickoIme`),
  KEY `fk_Grad_idx` (`idGrad`),
  KEY `fk_Adresa_idx` (`idAdresa`),
  CONSTRAINT `fk_Adresa` FOREIGN KEY (`idAdresa`) REFERENCES `Adresa` (`idAdresa`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_Grad` FOREIGN KEY (`idGrad`) REFERENCES `Grad` (`idGrad`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Korisnik`
--

LOCK TABLES `Korisnik` WRITE;
/*!40000 ALTER TABLE `Korisnik` DISABLE KEYS */;
INSERT INTO `Korisnik` VALUES ('jokan','123','Slavisa','Jokanovic',1,1,50000),('remax','123','Marko','Jovanovic',2,1,20000);
/*!40000 ALTER TABLE `Korisnik` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-06 17:43:46
