/*
SQLyog 企业版 - MySQL GUI v7.14 
MySQL - 5.5.19 : Database - cloudcommunication
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`cloudcommunication` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `cloudcommunication`;

/*Table structure for table `calling` */

DROP TABLE IF EXISTS `calling`;

CREATE TABLE `calling` (
  `id` varchar(100) NOT NULL COMMENT '呼叫id',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '数据更新时间',
  `update_check` int(5) DEFAULT '0' COMMENT '更新检查',
  `from_calling` varchar(50) DEFAULT NULL COMMENT '主叫电话号码',
  `to_calling` varchar(50) DEFAULT NULL COMMENT '被叫电话号码',
  `direction` tinyint(1) DEFAULT NULL COMMENT '呼叫方向，取值有0（呼入），1（呼出）',
  `appid` varchar(50) DEFAULT NULL COMMENT '应用id',
  `verification_code` varchar(50) DEFAULT NULL COMMENT '验证码',
  `projectno` varchar(50) DEFAULT NULL COMMENT '项目编号',
  `is_success` tinyint(1) DEFAULT '0' COMMENT '投票是否成功',
  `verification_try` int(5) DEFAULT '0' COMMENT '尝试验证码次数',
  `projectno_try` int(5) DEFAULT '0' COMMENT '项目编号尝试次数',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `sms` */

DROP TABLE IF EXISTS `sms`;

CREATE TABLE `sms` (
  `id` varchar(50) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '数据更新时间',
  `update_check` int(5) DEFAULT '0',
  `cell_number` varchar(50) DEFAULT NULL COMMENT '接收验证码手机号码',
  `verify_code` varchar(50) DEFAULT NULL COMMENT '验证码',
  `time_limit` int(5) DEFAULT NULL COMMENT '有效分钟数',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
