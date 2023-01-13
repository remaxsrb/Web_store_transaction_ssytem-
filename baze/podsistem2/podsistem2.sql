-- MySQL dump 10.13  Distrib 8.0.31, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: podsistem2
-- ------------------------------------------------------
-- Server version	8.0.31

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
  `Kategorija` int unsigned NOT NULL,
  PRIMARY KEY (`idArtikal`),
  UNIQUE KEY `idArtikal_UNIQUE` (`idArtikal`),
  UNIQUE KEY `Naziv_UNIQUE` (`Naziv`),
  UNIQUE KEY `Kategorija_UNIQUE` (`Kategorija`),
  CONSTRAINT `fk_Kategorija` FOREIGN KEY (`Kategorija`) REFERENCES `Kategorija` (`idKategorija`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Artikal`
--

LOCK TABLES `Artikal` WRITE;
/*!40000 ALTER TABLE `Artikal` DISABLE KEYS */;
INSERT INTO `Artikal` VALUES (1,'Kosarkaska Lopta','outdoor',1500,5,2),(2,'Bianconeri duks','',3000,10,11);
/*!40000 ALTER TABLE `Artikal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Kategorija`
--

DROP TABLE IF EXISTS `Kategorija`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Kategorija` (
  `idKategorija` int unsigned NOT NULL AUTO_INCREMENT,
  `Naziv` varchar(45) NOT NULL,
  PRIMARY KEY (`idKategorija`),
  UNIQUE KEY `idKategorija_UNIQUE` (`idKategorija`),
  UNIQUE KEY `Naziv_UNIQUE` (`Naziv`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Kategorija`
--

LOCK TABLES `Kategorija` WRITE;
/*!40000 ALTER TABLE `Kategorija` DISABLE KEYS */;
INSERT INTO `Kategorija` VALUES (10,'Duks'),(12,'Duks bez kapuljace'),(11,'Duks sa kapuljacom'),(8,'Epska fantastika'),(7,'Fantstika'),(3,'Fudbalska lopta'),(5,'Knjiga'),(2,'Kosarkaska lopta'),(1,'Lopta'),(9,'Naucna fantastika'),(4,'Odbojkaska lopta'),(6,'Triler');
/*!40000 ALTER TABLE `Kategorija` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Korisnik`
--

DROP TABLE IF EXISTS `Korisnik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Korisnik` (
  `idKorisnik` int unsigned NOT NULL AUTO_INCREMENT,
  `KorisnickoIme` varchar(45) NOT NULL,
  `Novac` float unsigned NOT NULL,
  `Sifra` varchar(45) NOT NULL,
  PRIMARY KEY (`idKorisnik`,`KorisnickoIme`),
  UNIQUE KEY `idKorisnik_UNIQUE` (`idKorisnik`),
  UNIQUE KEY `KorisnickoIme_UNIQUE` (`KorisnickoIme`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Korisnik`
--

LOCK TABLES `Korisnik` WRITE;
/*!40000 ALTER TABLE `Korisnik` DISABLE KEYS */;
INSERT INTO `Korisnik` VALUES (1,'remax',20000,'123'),(2,'jokan',50000,'123');
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
  `idKorisnik` int unsigned NOT NULL,
  PRIMARY KEY (`idKorpa`),
  UNIQUE KEY `idKorpa_UNIQUE` (`idKorpa`),
  UNIQUE KEY `idKorisnik_UNIQUE` (`idKorisnik`),
  CONSTRAINT `fk_Korisnik` FOREIGN KEY (`idKorisnik`) REFERENCES `Korisnik` (`idKorisnik`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Korpa`
--

LOCK TABLES `Korpa` WRITE;
/*!40000 ALTER TABLE `Korpa` DISABLE KEYS */;
/*!40000 ALTER TABLE `Korpa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Potkategorija`
--

DROP TABLE IF EXISTS `Potkategorija`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Potkategorija` (
  `idPotkategorija` int unsigned NOT NULL,
  `idNadKategorija` int unsigned NOT NULL,
  PRIMARY KEY (`idPotkategorija`,`idNadKategorija`),
  KEY `fk_Nadkategorija_idx` (`idNadKategorija`),
  CONSTRAINT `fk_Nadkategorija` FOREIGN KEY (`idNadKategorija`) REFERENCES `Kategorija` (`idKategorija`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_Potkategorija` FOREIGN KEY (`idPotkategorija`) REFERENCES `Kategorija` (`idKategorija`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Potkategorija`
--

LOCK TABLES `Potkategorija` WRITE;
/*!40000 ALTER TABLE `Potkategorija` DISABLE KEYS */;
INSERT INTO `Potkategorija` VALUES (2,1),(3,1),(4,1),(6,5),(7,5),(8,7),(9,7),(11,10),(12,10);
/*!40000 ALTER TABLE `Potkategorija` ENABLE KEYS */;
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
  PRIMARY KEY (`idKorpa`,`idArtikal`),
  KEY `fk_Artikal_idx` (`idArtikal`),
  CONSTRAINT `fk_Artikal` FOREIGN KEY (`idArtikal`) REFERENCES `Artikal` (`idArtikal`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_Korpa` FOREIGN KEY (`idKorpa`) REFERENCES `Korpa` (`idKorpa`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Sadrzi`
--

LOCK TABLES `Sadrzi` WRITE;
/*!40000 ALTER TABLE `Sadrzi` DISABLE KEYS */;
/*!40000 ALTER TABLE `Sadrzi` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-13 11:19:49
