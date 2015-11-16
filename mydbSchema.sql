-- MySQL dump 10.13  Distrib 5.6.24, for Win64 (x86_64)
--
-- Host: localhost    Database: mydb
-- ------------------------------------------------------
-- Server version	5.6.27-log

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
-- Table structure for table `customerorder`
--

DROP TABLE IF EXISTS `customerorder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customerorder` (
  `customerorder_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_date` varchar(45) DEFAULT NULL,
  `order_status` varchar(45) DEFAULT NULL,
  `employee_id` int(11) NOT NULL,
  PRIMARY KEY (`customerorder_id`),
  KEY `fk_customerorder_employee1_idx` (`employee_id`),
  CONSTRAINT `fk_customerorder_employee1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customerorder`
--

LOCK TABLES `customerorder` WRITE;
/*!40000 ALTER TABLE `customerorder` DISABLE KEYS */;
INSERT INTO `customerorder` VALUES (1,'12-10-2015','Processing',3),(2,'15-10-2015','Processing',2),(3,'07-11-2015','Delivered',1),(4,'25-10-2015','Processing',2),(5,'01-11-2015','Processing',4),(6,'29-10-2015','Processing',3);
/*!40000 ALTER TABLE `customerorder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customerorderline`
--

DROP TABLE IF EXISTS `customerorderline`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customerorderline` (
  `customer_orderline_id` int(11) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `product_id` int(11) NOT NULL,
  `customerorder_id` int(11) NOT NULL,
  KEY `fk_customerorderline_product1_idx` (`product_id`),
  KEY `fk_customerorderline_customerorder1_idx` (`customerorder_id`),
  CONSTRAINT `fk_customerorderline_customerorder1` FOREIGN KEY (`customerorder_id`) REFERENCES `customerorder` (`customerorder_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_customerorderline_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customerorderline`
--

LOCK TABLES `customerorderline` WRITE;
/*!40000 ALTER TABLE `customerorderline` DISABLE KEYS */;
INSERT INTO `customerorderline` VALUES (1,10,2,1),(2,2,1,1),(3,4,3,2),(4,1,5,3),(5,2,1,1),(6,5,4,4),(7,1,5,6),(8,4,1,6),(9,2,2,5);
/*!40000 ALTER TABLE `customerorderline` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee` (
  `employee_id` int(11) NOT NULL AUTO_INCREMENT,
  `employee_name` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(200) NOT NULL,
  PRIMARY KEY (`employee_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'Al Stock','alstock','b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86'),(2,'Ben Back','benback','e019fcd0baab1639bc7c078872d0752adcec990c350a5230441d871ca9e2be867bdbbabbac4f833679bf31f9b95d7d1090e46d38cf43df609a3d90532a90c02b'),(3,'Phil Norris','philnorris','a750504eb2d68869c750d8346d673000534aae133c1be97b12c82f82354e758c8cb917f820bc326d772c5c7d11150c2889f518af0ae2a7392f04a750f15d39b4'),(4,'Ken Tucker','kentucker','1cb74b8a9848a39401b947db2ba173e7db28326c8f0f2006e0ad995fa834a46ad45ee1599ec919488ce00e8e26ca49c3ece4e94e9f6e48acb9701666d987f02c'),(5,'Bill Hardy','billhardy','d44efa425cdb73444b44b8665ca72435e0aea6893847bf480d8f3de717c796aea0c8a36d1f4b9279008d2f9a12a1766b801a6897f52b52e47343a253bbcf761c');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(45) NOT NULL,
  `product_description` varchar(100) NOT NULL,
  `product_price` float NOT NULL,
  `product_porousware` tinyint(1) DEFAULT NULL,
  `product_type` varchar(45) NOT NULL,
  `product_quantity` int(11) DEFAULT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'Blue Gnome','A blue garden gnome',10,1,'Gnome',729),(2,'Bench','A bench perfect for sitting on',100,0,'Garden Accessory',107),(3,'Red Gnome ','A red garden gnome',10,1,'Gnome',175),(4,'Green Gnome','A green garden gnome',10,1,'Gnome',86),(5,'Large Shed','A large shed for storing your tools',10,0,'Garden Accessory',248),(6,'Plant Pot','Perfect for growing plants in',15,1,'Garden Accessory',10);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchaseorder`
--

DROP TABLE IF EXISTS `purchaseorder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchaseorder` (
  `purchase_order_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_date` varchar(100) NOT NULL,
  `order_status` varchar(45) NOT NULL,
  `employee_id` int(11) NOT NULL,
  PRIMARY KEY (`purchase_order_id`),
  KEY `fk_purchaseorder_employee_idx` (`employee_id`),
  CONSTRAINT `fk_purchaseorder_employee` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchaseorder`
--

LOCK TABLES `purchaseorder` WRITE;
/*!40000 ALTER TABLE `purchaseorder` DISABLE KEYS */;
INSERT INTO `purchaseorder` VALUES (1,'2/10/2015','Processing',3),(2,'14/11/2015','Received',4),(3,'10/10/2015','Received',1),(4,'11/11/2015','Processing',5),(5,'12/11/2015','Received',2),(6,'12/11/2015','Order Sent',4),(7,'9/10/2015','Processing',2),(8,'12/11/2015','Out for delivery',1),(9,'12/11/2015','Order Sent',2),(10,'12/11/2015','Out for delivery',5),(11,'12/11/2015','Order Sent',3),(12,'12/11/2015','Order Sent',2),(13,'12/11/2015','Received',5),(14,'12/11/2015','Received',5),(15,'15/11/2015','Received',2);
/*!40000 ALTER TABLE `purchaseorder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchaseorderline`
--

DROP TABLE IF EXISTS `purchaseorderline`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchaseorderline` (
  `purchase_orderline_id` int(11) NOT NULL,
  `quantity` varchar(45) DEFAULT NULL,
  `purchase_order_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  PRIMARY KEY (`purchase_orderline_id`),
  KEY `fk_purchaseorderline_purchaseorder1_idx` (`purchase_order_id`),
  KEY `fk_purchaseorderline_product1_idx` (`product_id`),
  CONSTRAINT `fk_purchaseorderline_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_purchaseorderline_purchaseorder1` FOREIGN KEY (`purchase_order_id`) REFERENCES `purchaseorder` (`purchase_order_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchaseorderline`
--

LOCK TABLES `purchaseorderline` WRITE;
/*!40000 ALTER TABLE `purchaseorderline` DISABLE KEYS */;
INSERT INTO `purchaseorderline` VALUES (1,'3',1,3),(2,'50',3,5),(3,'10',2,1),(4,'25',1,1),(5,'90',1,4),(6,'2',5,2),(7,'14',6,5),(8,'1',8,1),(9,'100',9,5),(10,'25',9,3),(11,'10',9,1),(12,'100',10,2),(13,'100',11,1),(14,'25',11,2),(15,'100',12,3),(16,'5',12,1),(17,'100',13,1),(18,'25',13,3),(19,'200',14,1),(20,'100',15,1);
/*!40000 ALTER TABLE `purchaseorderline` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-11-16 16:19:09
