-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.6.25-log - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 kitchen_db 的数据库结构
DROP DATABASE IF EXISTS `kitchen_db`;
CREATE DATABASE IF NOT EXISTS `kitchen_db` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `kitchen_db`;


-- 导出  表 kitchen_db.tb_logs_sys_user_login 结构
DROP TABLE IF EXISTS `tb_logs_sys_user_login`;
CREATE TABLE IF NOT EXISTS `tb_logs_sys_user_login` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '标识',
  `user_id` int(10) unsigned NOT NULL COMMENT '用户标识（tb_sys_user外键）',
  `login_time` timestamp NULL DEFAULT NULL COMMENT '登录时间',
  `login_pass` int(4) DEFAULT NULL COMMENT '是否登录成功 0:false 1:true',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录的创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '用于乐观锁的控制',
  PRIMARY KEY (`id`),
  KEY `FK_LOGINLOG_SYSUSER` (`user_id`),
  CONSTRAINT `FK_LOGINLOG_SYSUSER` FOREIGN KEY (`user_id`) REFERENCES `tb_sys_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户登录日志\r\n可增加字段，记录其它登录信息，例如登录时的ip地址、登录的国家城市等';

-- 正在导出表  kitchen_db.tb_logs_sys_user_login 的数据：~0 rows (大约)
DELETE FROM `tb_logs_sys_user_login`;
/*!40000 ALTER TABLE `tb_logs_sys_user_login` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_logs_sys_user_login` ENABLE KEYS */;


-- 导出  表 kitchen_db.tb_sys_user 结构
DROP TABLE IF EXISTS `tb_sys_user`;
CREATE TABLE IF NOT EXISTS `tb_sys_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户标识',
  `login_name` varchar(100) NOT NULL COMMENT '登录名',
  `login_password` varchar(32) NOT NULL COMMENT '登录密码（MD5加密）',
  `dynamic_salt` varchar(100) DEFAULT NULL COMMENT '动态盐',
  `full_name` varchar(50) DEFAULT NULL COMMENT '用户全名',
  `email` varchar(200) DEFAULT NULL COMMENT '邮箱地址（可作为登录时的凭证）',
  `phone` varchar(50) DEFAULT NULL COMMENT '手机号（可作为登录时的凭证）',
  `register_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户加入平台的时间',
  `state` int(4) NOT NULL DEFAULT '1' COMMENT '用户状态（-1：删除 0：锁定 1：正常）',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录的创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '用于乐观锁的控制',
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_name` (`login_name`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统用户（管理用户、员工）基本信息';

-- 正在导出表  kitchen_db.tb_sys_user 的数据：~1 rows (大约)
DELETE FROM `tb_sys_user`;
/*!40000 ALTER TABLE `tb_sys_user` DISABLE KEYS */;
INSERT INTO `tb_sys_user` (`id`, `login_name`, `login_password`, `dynamic_salt`, `full_name`, `email`, `phone`, `register_time`, `state`, `create_time`, `update_time`) VALUES
	(1, 'admin', '878A71C488CBD278279B66A46DF142EC', 'D86DC0F7B8FEBE7E0B8820344FB9F86B3B4FC24CCF8F1638', '管理员', 'admin@kitchen.com', '18800000000', '2016-09-07 00:16:39', 1, '2016-12-26 05:47:50', '2017-04-06 21:42:48');
/*!40000 ALTER TABLE `tb_sys_user` ENABLE KEYS */;


-- 导出  表 kitchen_db.tb_user 结构
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE IF NOT EXISTS `tb_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '客户标识',
  `login_name` varchar(100) NOT NULL COMMENT '登录名',
  `login_password` varchar(32) NOT NULL COMMENT '登录密码（加密）',
  `dynamic_salt` varchar(100) NOT NULL COMMENT '动态盐',
  `full_name` varchar(50) DEFAULT NULL COMMENT '用户全名',
  `nickname` varchar(50) DEFAULT NULL COMMENT '用户昵称',
  `email` varchar(200) DEFAULT NULL COMMENT '邮箱地址（可作为登录时的凭证）',
  `phone` varchar(50) DEFAULT NULL COMMENT '手机号（可作为登录时的凭证）',
  `head_img_uri` varchar(200) DEFAULT NULL COMMENT '头像URI',
  `register_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '客户注册时间',
  `state` tinyint(4) NOT NULL COMMENT '客户状态',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录的创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '用于乐观锁的控制',
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_name` (`login_name`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='客户（业务用户）基本信息';

-- 正在导出表  kitchen_db.tb_user 的数据：~1 rows (大约)
DELETE FROM `tb_user`;
/*!40000 ALTER TABLE `tb_user` DISABLE KEYS */;
INSERT INTO `tb_user` (`id`, `login_name`, `login_password`, `dynamic_salt`, `full_name`, `nickname`, `email`, `phone`, `head_img_uri`, `register_time`, `state`, `create_time`, `update_time`) VALUES
	(1, 'admin', '41D919A7AD15CDBD0701EF66B64306A7', '1D7E658AF1B6802DBA7A3E427F69393D7A02D0D992591F77', '管理员', '00:01', 'admin@kitchen.com', '18800000000', NULL, '2017-01-03 06:53:21', 1, '2017-01-03 06:53:25', '2017-04-06 21:43:07');
/*!40000 ALTER TABLE `tb_user` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
