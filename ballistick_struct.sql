-- MySQL dump 10.13  Distrib 5.1.41, for Win32 (ia32)
--
-- Host: localhost    Database: stickemu
-- ------------------------------------------------------
-- Server version	5.1.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inventory` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userid` int(10) unsigned NOT NULL,
  `itemid` int(10) unsigned NOT NULL,
  `itemtype` int(10) unsigned NOT NULL,
  `red1` int(10) unsigned NOT NULL,
  `green1` int(10) unsigned NOT NULL,
  `blue1` int(10) unsigned NOT NULL,
  `red2` int(10) unsigned NOT NULL,
  `blue2` int(10) unsigned NOT NULL,
  `green2` int(10) unsigned NOT NULL,
  `selected` tinyint(1) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory`
--

LOCK TABLES `inventory` WRITE;
/*!40000 ALTER TABLE `inventory` DISABLE KEYS */;
/*!40000 ALTER TABLE `inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ipbans`
--

DROP TABLE IF EXISTS `ipbans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ipbans` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` text NOT NULL,
  `playername` text NOT NULL,
  `mod_responsible` text NOT NULL,
  `date_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ipbans`
--

LOCK TABLES `ipbans` WRITE;
/*!40000 ALTER TABLE `ipbans` DISABLE KEYS */;
/*!40000 ALTER TABLE `ipbans` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quickplay_names`
--

DROP TABLE IF EXISTS `quickplay_names`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quickplay_names` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `type` int(10) unsigned NOT NULL,
  `value` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quickplay_names`
--

LOCK TABLES `quickplay_names` WRITE;
/*!40000 ALTER TABLE `quickplay_names` DISABLE KEYS */;
INSERT INTO `quickplay_names` VALUES (1,0,'Prof.'),(2,0,'Dr.'),(3,0,'Little'),(4,0,'Big'),(5,0,'Stanky'),(6,0,'Papa'),(7,0,'Brother'),(8,0,'Saint'),(9,0,'Mr.'),(10,0,'Sir'),(11,0,'Vladimir'),(12,0,'Randy'),(13,0,'Master'),(14,0,'Greasy'),(15,0,'Jeeves'),(16,0,'Ugly'),(17,0,'Spooky'),(18,0,'Donald'),(19,0,'Uncle'),(20,0,'President'),(21,0,'General'),(22,0,'Ensign'),(23,0,'Admiral'),(24,0,'Perfect'),(25,0,'The Only'),(26,0,'Bad'),(27,0,'Good'),(28,0,'Baron'),(29,0,'Fishy'),(30,1,' Two Toes'),(31,1,' Glock'),(32,1,' Sleuth'),(33,1,' Capone'),(34,1,' Slasher'),(35,1,' McGraw'),(36,1,' McGee'),(37,1,'\'s Wrath'),(38,1,' Joe'),(39,1,'\'s Minion'),(40,1,' Smasher'),(41,1,' Slayer'),(42,1,' Shooter'),(43,1,' Natas');
/*!40000 ALTER TABLE `quickplay_names` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shop`
--

DROP TABLE IF EXISTS `shop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shop` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `itemID` int(10) unsigned NOT NULL,
  `cost` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shop`
--

LOCK TABLES `shop` WRITE;
/*!40000 ALTER TABLE `shop` DISABLE KEYS */;
INSERT INTO `shop` VALUES (1,100,120),(2,101,130),(3,102,370),(4,103,990),(5,104,350),(6,105,420),(7,106,500),(8,107,540),(9,108,660),(10,109,710),(11,110,775),(12,111,850),(13,112,980),(14,113,1500),(15,114,3300),(16,115,4100),(17,116,6500),(18,117,9001),(19,118,2500),(20,119,5200),(21,120,2700),(22,121,1337),(23,122,5350),(24,123,6800),(25,124,1700),(26,125,910),(27,126,2350),(28,127,1259),(29,128,15000),(30,129,1850),(31,130,2925),(32,131,3900),(33,132,625),(34,133,3450),(35,134,4600),(36,135,12000),(37,136,4000),(38,137,2600),(39,138,4800),(40,139,3200),(41,140,6000),(42,141,24000),(43,142,880),(44,143,3400),(45,144,4600),(46,145,5800),(47,201,480),(48,202,650),(49,203,1450),(50,204,2500),(51,205,4500),(52,206,6000);
/*!40000 ALTER TABLE `shop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `UID` int(11) NOT NULL AUTO_INCREMENT,
  `USERname` text NOT NULL,
  `USERpass` text NOT NULL,
  `user_level` tinyint(1) NOT NULL DEFAULT '0',
  `passexpiry` int(11) NOT NULL DEFAULT '99999',
  `cash` int(11) NOT NULL DEFAULT '9999999',
  `labpass`tinyint(1) NOT NULL DEFAULT '1',
  `rounds` int(11) NOT NULL DEFAULT '0',
  `kills` int(11) NOT NULL DEFAULT '0',
  `deaths` int(11) NOT NULL DEFAULT '0',
  `wins` int(11) NOT NULL DEFAULT '0',
  `ticket` int(11) NOT NULL DEFAULT '1',
  `losses` int(11) NOT NULL DEFAULT '0',
  `red` smallint(3) NOT NULL DEFAULT '255',
  `green` smallint(3) NOT NULL DEFAULT '0',
  `blue` smallint(3) NOT NULL DEFAULT '0',
  `ban` tinyint(1) DEFAULT '0',
  UNIQUE KEY `USERname` (`USERname`(20)),
  KEY `UID` (`UID`)
) ENGINE=MyISAM AUTO_INCREMENT=2480 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-08-06 10:24:07
