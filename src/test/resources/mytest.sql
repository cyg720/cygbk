/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50705
Source Host           : localhost:3306
Source Database       : boke

Target Server Type    : MYSQL
Target Server Version : 50705
File Encoding         : 65001

Date: 2016-10-30 20:41:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for p_menu
-- ----------------------------
DROP TABLE IF EXISTS `p_menu`;
CREATE TABLE `p_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `code` varchar(100) DEFAULT NULL COMMENT 'code',
  `name` varchar(100) DEFAULT NULL COMMENT '菜单名称',
  `type` int(1) DEFAULT NULL COMMENT '菜单类型（前台后台）',
  `createDate` datetime DEFAULT NULL COMMENT '创建时间',
  `lastUpdataDate` datetime DEFAULT NULL COMMENT '最后一次修改时间',
  `createUserId` int(11) DEFAULT NULL COMMENT '创建人',
  `enabled` int(1) DEFAULT NULL COMMENT '是否启用',
  `parent` int(11) DEFAULT NULL COMMENT '菜单级联Id',
  `url` varchar(225) DEFAULT NULL COMMENT 'url地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_menu
-- ----------------------------
INSERT INTO `p_menu` VALUES ('1', '001', '菜单一', '0', '2016-10-30 20:20:14', '2016-10-30 20:20:16', '1', '1', '0', '/boke');

-- ----------------------------
-- Table structure for p_role
-- ----------------------------
DROP TABLE IF EXISTS `p_role`;
CREATE TABLE `p_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `code` varchar(50) NOT NULL COMMENT 'code',
  `name` varchar(100) NOT NULL COMMENT '角色名称',
  `createDate` datetime NOT NULL COMMENT '创建时间',
  `createUserId` int(11) NOT NULL COMMENT '创建人',
  `lastUpdateDate` datetime DEFAULT NULL COMMENT '最后一次修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_role
-- ----------------------------
INSERT INTO `p_role` VALUES ('1', 'admin', '超级管理员', '2016-10-30 19:41:25', '1', '2016-10-30 19:41:32');

-- ----------------------------
-- Table structure for p_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `p_role_menu`;
CREATE TABLE `p_role_menu` (
  `id` int(11) NOT NULL,
  `roleId` int(11) DEFAULT NULL COMMENT '角色Id',
  `menuId` int(11) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for p_user
-- ----------------------------
DROP TABLE IF EXISTS `p_user`;
CREATE TABLE `p_user` (
  `id` int(11) NOT NULL COMMENT 'id',
  `name` varchar(100) NOT NULL COMMENT '用户名',
  `pwd` varchar(100) NOT NULL COMMENT '密码',
  `address` varchar(100) DEFAULT NULL COMMENT '地址',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话号码',
  `email` varchar(30) DEFAULT NULL COMMENT '邮箱地址',
  `sex` smallint(1) DEFAULT NULL COMMENT '性别',
  `sourceType` smallint(2) DEFAULT NULL COMMENT '用户来源',
  `createDate` datetime DEFAULT NULL COMMENT '创建时间',
  `lastUpdataDate` datetime DEFAULT NULL COMMENT '最后一次修改时间',
  `userRoleId` int(11) DEFAULT NULL COMMENT '用户角色',
  PRIMARY KEY (`id`),
  KEY `userRoleId` (`userRoleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_user
-- ----------------------------
INSERT INTO `p_user` VALUES ('1', 'admin', 'admin', '成都', '18200114540', '786178187@qq.com', '0', '0', '2016-10-30 19:49:30', '2016-10-30 19:49:32', '1');

-- ----------------------------
-- Table structure for p_user_role
-- ----------------------------
DROP TABLE IF EXISTS `p_user_role`;
CREATE TABLE `p_user_role` (
  `userId` int(11) NOT NULL COMMENT '用户Id',
  `roleId` int(11) NOT NULL COMMENT '角色ID',
  KEY `roleId` (`roleId`),
  KEY `userId` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_user_role
-- ----------------------------
INSERT INTO `p_user_role` VALUES ('1', '1');
