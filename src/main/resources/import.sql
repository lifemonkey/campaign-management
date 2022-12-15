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
INSERT INTO roles (id,name,description,can_approve_reward,can_reject_reward,campaign_management,rule_management,prize_management) VALUES (1,'ADMIN','admin',1,1,1,1,1);
INSERT INTO roles (id,name,description,can_approve_reward,can_reject_reward,campaign_management,rule_management,prize_management) VALUES (2,'BO_STAFF','bo staff',0,0,1,1,1);
INSERT INTO roles (id,name,description,can_approve_reward,can_reject_reward,campaign_management,rule_management,prize_management) VALUES (3,'FIN_STAFF','fin staff',1,1,0,0,0);
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;

--
-- Dumping data for table `users`
-- tainguyen91@gmail.com/admin123
-- tainguyen91_bo@gmail.com/bo123456
-- tainguyen91_fin@gmail.com/fin12345
-- nguyen.to@gmail.com/nt123456
-- hung.doan@gmail.com/hd123456
--
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO users (id,username,password,first_name,last_name,expired_date,role_id,image_url,status,created_by,created_date,last_modified_by,last_modified_date) VALUES (1,'tainguyen91@gmail.com','$2a$12$WlGaaoA5QrZgLmVrozSccedDn/WUDOVIeAMEUr.wd.fVboP6MWjIC','Nguyen','Huu Tai',NULL,1,NULL,'ACTIVE','system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'),'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO users (id,username,password,first_name,last_name,expired_date,role_id,image_url,status,created_by,created_date,last_modified_by,last_modified_date) VALUES (2,'tainguyen91_bo@gmail.com','$2a$12$5o5Sqn1Fa.t/ZLkF3eMo7edutHXIoDI5FOfObYujqIxz5rTxGMDBG','Nguyen','Huu_Tai_Bo',NULL,2,NULL,'ACTIVE','system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'),'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO users (id,username,password,first_name,last_name,expired_date,role_id,image_url,status,created_by,created_date,last_modified_by,last_modified_date) VALUES (3,'tainguyen91_fin@gmail.com','$2a$12$2ftvBCDZE80uqt4vM.wsMO1j.ogZuIAwSGTIEETlN58wtOvqQdJoi','Nguyen','Huu_Tai_Fin',NULL,3,NULL,'ACTIVE','system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'),'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO users (id,username,password,first_name,last_name,expired_date,role_id,image_url,status,created_by,created_date,last_modified_by,last_modified_date) VALUES (4,'nguyen.to@gmail.com','$2a$12$dXGJmoUpZNkloYADWBMUEOViipoiSporNOvbPaaK3ZYpf7ZeMw9VW','Nguyen','To',NULL,3,NULL,'INACTIVE','system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'),'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO users (id,username,password,first_name,last_name,expired_date,role_id,image_url,status,created_by,created_date,last_modified_by,last_modified_date) VALUES (5,'hung.doan@gmail.com','$2a$12$/vJ84HiGgykDtT91GZ5ym.NCY76dtrPDjdgM2HPzThrm4ULaK4pZi','Hung','Doan',NULL,3,NULL,'INACTIVE','system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'),'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'));
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

--
-- Dumping data for table `target_list`
--
/*!40000 ALTER TABLE `target_list` DISABLE KEYS */;
INSERT INTO target_list (id,name,description,target_type,created_by,created_date,last_modified_by,last_modified_date) VALUES (1,'List 1','List 1 description',1,'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'),'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO target_list (id,name,description,target_type,created_by,created_date,last_modified_by,last_modified_date) VALUES (2,'List 2','List 2 description',1,'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'),'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO target_list (id,name,description,target_type,created_by,created_date,last_modified_by,last_modified_date) VALUES (3,'List 3','List 3 description',2,'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'),'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO target_list (id,name,description,target_type,created_by,created_date,last_modified_by,last_modified_date) VALUES (4,'List 4','List 4 description',2,'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'),'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO target_list (id,name,description,target_type,created_by,created_date,last_modified_by,last_modified_date) VALUES (5,'List 5','List 5 description',3,'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'),'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'));
/*!40000 ALTER TABLE `target_list` ENABLE KEYS */;

--
-- Dumping data for table `accounts`
--
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO accounts (id,external_id,first_name,last_name,created_by,created_date,last_modified_by,last_modified_date) VALUES (1,'ext_id_1','Hung','Doan','system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'),'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO accounts (id,external_id,first_name,last_name,created_by,created_date,last_modified_by,last_modified_date) VALUES (2,'ext_id_2','Nguyen','To','system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'),'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO accounts (id,external_id,first_name,last_name,created_by,created_date,last_modified_by,last_modified_date) VALUES (3,'ext_id_3','Tai','Nguyen','system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'),'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO accounts (id,external_id,first_name,last_name,created_by,created_date,last_modified_by,last_modified_date) VALUES (4,'ext_id_4','Thinh','Phan','system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'),'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO accounts (id,external_id,first_name,last_name,created_by,created_date,last_modified_by,last_modified_date) VALUES (5,'ext_id_5','Khai','Kieu','system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'),'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'));
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;

--
-- Dumping data for table `token_black_list`
--
/*!40000 ALTER TABLE `token_black_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `token_black_list` ENABLE KEYS */;

--
-- Dumping data for table `refresh_token`
--
/*!40000 ALTER TABLE `refresh_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `refresh_token` ENABLE KEYS */;

--
-- Dumping data for table `status`
--
/*!40000 ALTER TABLE `status` DISABLE KEYS */;
INSERT INTO status (id,name) VALUES (1,'Running');
INSERT INTO status (id,name) VALUES (2,'Pending Approve');
INSERT INTO status (id,name) VALUES (3,'Initialization');
INSERT INTO status (id,name) VALUES (4,'Paused');
INSERT INTO status (id,name) VALUES (5,'Cancelled');
INSERT INTO status (id,name) VALUES (6,'Completed');
INSERT INTO status (id,name) VALUES (7,'Rejected');
/*!40000 ALTER TABLE `status` ENABLE KEYS */;

--
-- Dumping data for table `campaign`
--
/*!40000 ALTER TABLE `campaign` DISABLE KEYS */;
INSERT INTO campaign (id,name,description,from_date,end_date,campaign_type,notes,status_id,approved_rejected_by,created_by,created_date,last_modified_by,last_modified_date) VALUES (1,'Campaign 1','Campaign 1 description',to_date('2022-10-22 11:54:50','YYYY-MM-DD HH24:MI:SS'),to_date('2022-11-22 11:54:50','YYYY-MM-DD HH24:MI:SS'),1,'Note for campaign 1',1,1,'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'),'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO campaign (id,name,description,from_date,end_date,campaign_type,notes,status_id,approved_rejected_by,created_by,created_date,last_modified_by,last_modified_date) VALUES (2,'Campaign 2','Campaign 2 description',to_date('2022-11-19 11:54:50','YYYY-MM-DD HH24:MI:SS'),to_date('2022-11-29 11:54:50','YYYY-MM-DD HH24:MI:SS'),1,'Note for campaign 2',1,2,'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'),'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO campaign (id,name,description,from_date,end_date,campaign_type,notes,status_id,approved_rejected_by,created_by,created_date,last_modified_by,last_modified_date) VALUES (3,'Campaign 3','Campaign 3 description',to_date('2022-11-12 11:54:50','YYYY-MM-DD HH24:MI:SS'),to_date('2022-12-22 11:54:50','YYYY-MM-DD HH24:MI:SS'),1,'Note for campaign 3',2,2,'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'),'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO campaign (id,name,description,from_date,end_date,campaign_type,notes,status_id,approved_rejected_by,created_by,created_date,last_modified_by,last_modified_date) VALUES (4,'Campaign 4','Campaign 4 description',to_date('2022-09-12 11:54:50','YYYY-MM-DD HH24:MI:SS'),to_date('2022-12-02 11:54:50','YYYY-MM-DD HH24:MI:SS'),2,'Note for campaign 4',2,2,'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'),'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO campaign (id,name,description,from_date,end_date,campaign_type,notes,status_id,approved_rejected_by,created_by,created_date,last_modified_by,last_modified_date) VALUES (5,'Campaign 5','Campaign 5 description',to_date('2022-12-10 11:54:50','YYYY-MM-DD HH24:MI:SS'),to_date('2023-01-11 11:54:50','YYYY-MM-DD HH24:MI:SS'),2,'Note for campaign 5',3,3,'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'),'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'));
/*!40000 ALTER TABLE `campaign` ENABLE KEYS */;

--
-- Dumping data for table `files`
--
/*!40000 ALTER TABLE `files` DISABLE KEYS */;
INSERT INTO files (id,name,description,file_type,image_url,campaign_id,created_by,created_date,last_modified_by,last_modified_date) VALUES (1,'File 1','File 1 description',1,'https://google.com/image_1',1,'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'),'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO files (id,name,description,file_type,image_url,campaign_id,created_by,created_date,last_modified_by,last_modified_date) VALUES (2,'File 2','File 2 description',1,'https://google.com/image_2',2,'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'),'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO files (id,name,description,file_type,image_url,campaign_id,created_by,created_date,last_modified_by,last_modified_date) VALUES (3,'File 3','File 3 description',1,'https://google.com/image_3',3,'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'),'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO files (id,name,description,file_type,image_url,campaign_id,created_by,created_date,last_modified_by,last_modified_date) VALUES (4,'File 4','File 4 description',3,'https://google.com/image_4',4,'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'),'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO files (id,name,description,file_type,image_url,campaign_id,created_by,created_date,last_modified_by,last_modified_date) VALUES (5,'File 5','File 5 description',2,'https://google.com/image_5',5,'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'),'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'));
/*!40000 ALTER TABLE `files` ENABLE KEYS */;

--
-- Dumping data for table `campaign_target_list`
--
/*!40000 ALTER TABLE `campaign_target_list` DISABLE KEYS */;
INSERT INTO campaign_target_list (campaign_id,target_list_id) VALUES (1,1);
INSERT INTO campaign_target_list (campaign_id,target_list_id) VALUES (1,2);
INSERT INTO campaign_target_list (campaign_id,target_list_id) VALUES (1,3);
INSERT INTO campaign_target_list (campaign_id,target_list_id) VALUES (2,1);
INSERT INTO campaign_target_list (campaign_id,target_list_id) VALUES (2,2);
INSERT INTO campaign_target_list (campaign_id,target_list_id) VALUES (3,1);
INSERT INTO campaign_target_list (campaign_id,target_list_id) VALUES (3,2);
INSERT INTO campaign_target_list (campaign_id,target_list_id) VALUES (3,3);
INSERT INTO campaign_target_list (campaign_id,target_list_id) VALUES (3,4);
INSERT INTO campaign_target_list (campaign_id,target_list_id) VALUES (4,1);
INSERT INTO campaign_target_list (campaign_id,target_list_id) VALUES (4,2);
INSERT INTO campaign_target_list (campaign_id,target_list_id) VALUES (5,1);
/*!40000 ALTER TABLE `campaign_target_list` ENABLE KEYS */;

--
-- Dumping data for table `target_list_account`
--
/*!40000 ALTER TABLE `target_list_account` DISABLE KEYS */;
INSERT INTO target_list_account (account_id,target_list_id) VALUES (2,1);
INSERT INTO target_list_account (account_id,target_list_id) VALUES (3,1);
INSERT INTO target_list_account (account_id,target_list_id) VALUES (4,1);
INSERT INTO target_list_account (account_id,target_list_id) VALUES (1,1);
INSERT INTO target_list_account (account_id,target_list_id) VALUES (5,1);
INSERT INTO target_list_account (account_id,target_list_id) VALUES (1,2);
INSERT INTO target_list_account (account_id,target_list_id) VALUES (2,2);
INSERT INTO target_list_account (account_id,target_list_id) VALUES (3,2);
INSERT INTO target_list_account (account_id,target_list_id) VALUES (1,3);
INSERT INTO target_list_account (account_id,target_list_id) VALUES (1,4);
INSERT INTO target_list_account (account_id,target_list_id) VALUES (2,4);
INSERT INTO target_list_account (account_id,target_list_id) VALUES (3,4);
/*!40000 ALTER TABLE `target_list_account` ENABLE KEYS */;

--
-- Dumping data for table `rule_configuration`
--
/*!40000 ALTER TABLE `rule_configuration` DISABLE KEYS */;
INSERT INTO rule_configuration (id,name,description,created_by,created_date,last_modified_by,last_modified_date) VALUES (1,'The first transaction','The first transaction description','system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'),'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO rule_configuration (id,name,description,created_by,created_date,last_modified_by,last_modified_date) VALUES (2,'By numbers of transaction','By numbers of transaction description','system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'),'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO rule_configuration (id,name,description,created_by,created_date,last_modified_by,last_modified_date) VALUES (3,'By value per one transaction','By value per one transaction description','system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'),'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO rule_configuration (id,name,description,created_by,created_date,last_modified_by,last_modified_date) VALUES (4,'Total value of transaction','Total value of transaction description','system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'),'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'));
/*!40000 ALTER TABLE `rule_configuration` ENABLE KEYS */;

--
-- Dumping data for table `transaction_type`
--
/*!40000 ALTER TABLE `transaction_type` DISABLE KEYS */;
INSERT INTO transaction_type (id,name,description,created_by,created_date,last_modified_by,last_modified_date) VALUES (1,'Airtime','Airtime description','system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'),'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO transaction_type (id,name,description,created_by,created_date,last_modified_by,last_modified_date) VALUES (2,'Bundle','Bundle description','system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'),'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO transaction_type (id,name,description,created_by,created_date,last_modified_by,last_modified_date) VALUES (3,'Reward Condition','Reward Condition description','system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'),'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'));
/*!40000 ALTER TABLE `transaction_type` ENABLE KEYS */;

--
-- Dumping data for table `rules`
--
/*!40000 ALTER TABLE `rules` DISABLE KEYS */;
INSERT INTO rules (id,name,description,duration_type,duration_value,reward_condition,campaign_id,rule_configuration_id,transaction_type_id,created_by,created_date,last_modified_by,last_modified_date) VALUES (1,'Rule 1','Rule 1 description','DURATION_INSTANT','',0,1,1,1,'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'),'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO rules (id,name,description,duration_type,duration_value,reward_condition,campaign_id,rule_configuration_id,transaction_type_id,created_by,created_date,last_modified_by,last_modified_date) VALUES (2,'Rule 2','Rule 3 description','DURATION_INSTANT','',0,1,1,1,'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'),'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO rules (id,name,description,duration_type,duration_value,reward_condition,campaign_id,rule_configuration_id,transaction_type_id,created_by,created_date,last_modified_by,last_modified_date) VALUES (3,'Rule 3','Rule 3 description','DURATION_INSTANT','',0,1,1,1,'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'),'system',to_date('2022-11-25 18:07:08','YYYY-MM-DD HH24:MI:SS'));
/*!40000 ALTER TABLE `rules` ENABLE KEYS */;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-26  1:08:54
