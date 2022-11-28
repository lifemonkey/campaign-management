-- MySQL dump 10.13  Distrib 8.0.23, for macos10.15 (x86_64)
--
-- Host: localhost    Database: lucky1
-- ------------------------------------------------------
-- Server version	8.0.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `roles`
--
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
CREATE SEQUENCE role_sequence_generator START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
INSERT INTO `roles` (id,name,description,can_approve_reward,can_reject_reward,campaign_management,rule_management,prize_management) VALUES (1,'admin','admin',1,1,1,1,1),(2,'bo_staff','bo staff',0,0,1,1,1),(3,'fin_staff','fin staff',1,1,0,0,0);
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;

--
-- Dumping data for table `users`
--
LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
CREATE SEQUENCE user_sequence_generator START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
INSERT INTO `users` (id,username,password,first_name,last_name,expired_date,role_id,image_url,created_by,last_modified_by) VALUES (1,'tainguyen91@gmail.com','$2a$12$5.LOOqD3L.7prOSJkJ3EGOnysTdIZvPO2vJuxSMPuZEHb2lCQupXK','Nguyen','Huu Tai',NULL,1,NULL,'system','system'),(2,'tainguyen91_bo@gmail.com','$2a$12$9/f4a30MofuDLu9CsHpCl.A0QfKDA7jeLhSWX8G5XPvbDdU97AEJi','Nguyen','Huu_Tai_Bo',NULL,2,NULL,'system','system'),(3,'tainguyen91_fin@gmail.com','$2a$12$j7daLGYUo/yPBf.osFl.NuB21IvmMyL.GkIFbZoH0uj18zr144sUO','Nguyen','Huu_Tai_Fin',NULL,3,NULL,'system','system');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `target_list`
--
LOCK TABLES `target_list` WRITE;
/*!40000 ALTER TABLE `target_list` DISABLE KEYS */;
CREATE SEQUENCE target_list_sequence_generator START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
INSERT INTO `target_list` (id,name,description,target_type,created_by,created_date,last_modified_by,last_modified_date) VALUES (1,'List 1','List 1 description',1,'system','2022-11-25 18:07:08','system','2022-11-25 18:07:08'),(2,'List 2','List 2 description',1,'system','2022-11-25 18:07:08','system','2022-11-25 18:07:08'),(3,'List 3','List 3 description',2,'system','2022-11-25 18:07:08','system','2022-11-25 18:07:08'),(4,'List 4','List 4 description',2,'system','2022-11-25 18:07:08','system','2022-11-25 18:07:08'),(5,'List 5','List 5 description',3,'system','2022-11-25 18:07:08','system','2022-11-25 18:07:08');
/*!40000 ALTER TABLE `target_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `accounts`
--
LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
CREATE SEQUENCE account_sequence_generator START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
INSERT INTO `accounts` (id,external_id,first_name,last_name,created_by,created_date,last_modified_by,last_modified_date) VALUES (1,'ext_id_1','Hung','Doan','system','2022-11-25 18:07:08','system','2022-11-25 18:07:08'),(2,'ext_id_2','Nguyen','To','system','2022-11-25 18:07:08','system','2022-11-25 18:07:08'),(3,'ext_id_3','Tai','Nguyen','system','2022-11-25 18:07:08','system','2022-11-25 18:07:08'),(4,'ext_id_4','Thinh','Phan','system','2022-11-25 18:07:08','system','2022-11-25 18:07:08'),(5,'ext_id_5','Khai','Kieu','system','2022-11-25 18:07:08','system','2022-11-25 18:07:08');
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `token_black_list`
--
LOCK TABLES `token_black_list` WRITE;
/*!40000 ALTER TABLE `token_black_list` DISABLE KEYS */;
CREATE SEQUENCE token_black_list_sequence_generator START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
/*!40000 ALTER TABLE `token_black_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `refresh_token`
--
LOCK TABLES `refresh_token` WRITE;
/*!40000 ALTER TABLE `refresh_token` DISABLE KEYS */;
CREATE SEQUENCE refresh_token_sequence_generator START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
/*!40000 ALTER TABLE `refresh_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `status`
--
LOCK TABLES `status` WRITE;
/*!40000 ALTER TABLE `status` DISABLE KEYS */;
CREATE SEQUENCE status_sequence_generator START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
INSERT INTO `status` (id,name) VALUES (1,'Running'),(2,'Pending Approve'),(3,'Initialization'),(4,'Paused'),(5,'Cancelled'),(6,'Completed'),(7,'Rejected');
/*!40000 ALTER TABLE `status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `files`
--
LOCK TABLES `files` WRITE;
/*!40000 ALTER TABLE `files` DISABLE KEYS */;
CREATE SEQUENCE file_sequence_generator START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
INSERT INTO `files` (id,name,description,file_type,image_url,campaign_id,created_by,created_date,last_modified_by,last_modified_date) VALUES (1,'File 1','File 1 description',1,'https://google.com/image_1',1,'system','2022-11-25 18:07:10','system','2022-11-25 18:07:10'),(2,'File 2','File 2 description',1,'https://google.com/image_2',2,'system','2022-11-25 18:07:10','system','2022-11-25 18:07:10'),(3,'File 3','File 3 description',1,'https://google.com/image_3',3,'system','2022-11-25 18:07:10','system','2022-11-25 18:07:10'),(4,'File 4','File 4 description',3,'https://google.com/image_4',NULL,4,'system','2022-11-25 18:07:10','system','2022-11-25 18:07:10'),(5,'File 5','File 5 description',2,'https://google.com/image_5',NULL,5,'system','2022-11-25 18:07:10','system','2022-11-25 18:07:10');
/*!40000 ALTER TABLE `files` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `campaign`
--
LOCK TABLES `campaign` WRITE;
/*!40000 ALTER TABLE `campaign` DISABLE KEYS */;
CREATE SEQUENCE campaign_sequence_generator START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
INSERT INTO `campaign` (id,name,description,from_date,end_date,campaign_type,notes,status_id,approved_rejected_by,created_by,created_date,last_modified_by,last_modified_date) VALUES (1,'Campaign 1','Campaign 1 description','2022-10-22 11:54:50','2022-11-22 11:54:50',1,'Note for campaign 1',1,1,'system','2022-11-25 18:07:09','system','2022-11-25 18:07:09'),(2,'Campaign 2','Campaign 2 description','2022-11-19 11:54:50','2022-11-29 11:54:50',1,'Note for campaign 2',1,2,'system','2022-11-25 18:07:09','system','2022-11-25 18:07:09'),(3,'Campaign 3','Campaign 3 description','2022-11-12 11:54:50','2022-12-22 11:54:50',1,'Note for campaign 3',2,2,'system','2022-11-25 18:07:09','system','2022-11-25 18:07:09'),(4,'Campaign 4','Campaign 4 description','2022-09-12 11:54:50','2022-12-02 11:54:50',2,'Note for campaign 4',2,2,'system','2022-11-25 18:07:09','system','2022-11-25 18:07:09'),(5,'Campaign 5','Campaign 5 description','2022-12-10 11:54:50','2023-01-11 11:54:50',2,'Note for campaign 5',3,3,'system','2022-11-25 18:07:09','system','2022-11-25 18:07:09');
/*!40000 ALTER TABLE `campaign` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `campaign_target_list`
--
LOCK TABLES `campaign_target_list` WRITE;
/*!40000 ALTER TABLE `campaign_target_list` DISABLE KEYS */;
INSERT INTO `campaign_target_list` (campaign_id,target_list_id) VALUES (1,1),(1,2),(1,3),(2,1),(2,2),(3,1),(3,2),(3,3),(3,4),(4,1),(4,2),(5,1);
/*!40000 ALTER TABLE `campaign_target_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `target_list_account`
--
LOCK TABLES `target_list_account` WRITE;
/*!40000 ALTER TABLE `target_list_account` DISABLE KEYS */;
INSERT INTO `target_list_account` (account_id,target_list_id) VALUES (2,1),(3,1),(4,1),(1,1),(5,1),(1,2),(2,2),(3,2),(1,3),(1,4),(2,4),(3,4);
/*!40000 ALTER TABLE `target_list_account` ENABLE KEYS */;
UNLOCK TABLES;


/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-26  1:08:54
