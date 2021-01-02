/*
 Navicat Premium Data Transfer

 Source Server         : yfengleng
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : my_test

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 03/01/2021 00:24:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for administrator
-- ----------------------------
DROP TABLE IF EXISTS `administrator`;
CREATE TABLE `administrator`  (
  `aid` int NOT NULL AUTO_INCREMENT,
  `account` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`aid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of administrator
-- ----------------------------
INSERT INTO `administrator` VALUES (1, 'admin', 'BA3CB603BF780EA2AF7E1B5ADC465F16BE1597307657C92C6C57EAE0');

-- ----------------------------
-- Table structure for consignee
-- ----------------------------
DROP TABLE IF EXISTS `consignee`;
CREATE TABLE `consignee`  (
  `userId` int NOT NULL AUTO_INCREMENT,
  `account` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `type` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `tel` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`userId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of consignee
-- ----------------------------
INSERT INTO `consignee` VALUES (2, 'testConsignee2', '7116131C5A9C303EAF665CFF856F0C255266B236BE1C439E72CD269F', '李木子', 'sample@qq.com', '经销商', 'sampleTel', 'sample');
INSERT INTO `consignee` VALUES (3, 'testConsignee3', '9ED09F47212175476C7E4F01BD38F8A0A4E521F70FCA9F5A0A7F3C9F', '李莉', 'sample1@qq.com', '经销商', 'sampleTel2', 'sample');
INSERT INTO `consignee` VALUES (5, 'test4', '4950CE36690A65AD14144A9D0B7A3B9F2804235911FB316FB05A2754', 'test4', 'yfl888@88.com', '经销商', '1122333', ' ');

-- ----------------------------
-- Table structure for equipment
-- ----------------------------
DROP TABLE IF EXISTS `equipment`;
CREATE TABLE `equipment`  (
  `etId` int NULL DEFAULT NULL,
  `oid` int NULL DEFAULT NULL,
  `fid` int NULL DEFAULT NULL,
  `equipmentId` int NOT NULL AUTO_INCREMENT,
  `equipmentSpecification` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `equipmentStatus` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `rentalStatus` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `equipmentName` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`equipmentId`) USING BTREE,
  INDEX `equipment_type`(`etId`) USING BTREE,
  INDEX `equipment_order_key`(`oid`) USING BTREE,
  INDEX `equipment_factory_key`(`fid`) USING BTREE,
  CONSTRAINT `equipment_equipmentType_key` FOREIGN KEY (`etId`) REFERENCES `equipment_type` (`typeId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `equipment_factory_key` FOREIGN KEY (`fid`) REFERENCES `factory` (`factoryId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `equipment_order_key` FOREIGN KEY (`oid`) REFERENCES `order` (`orderId`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of equipment
-- ----------------------------

-- ----------------------------
-- Table structure for equipment_type
-- ----------------------------
DROP TABLE IF EXISTS `equipment_type`;
CREATE TABLE `equipment_type`  (
  `typeId` int NOT NULL AUTO_INCREMENT,
  `typeName` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`typeId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of equipment_type
-- ----------------------------
INSERT INTO `equipment_type` VALUES (1, '机床');
INSERT INTO `equipment_type` VALUES (2, '流水线车床');
INSERT INTO `equipment_type` VALUES (3, '烤箱');
INSERT INTO `equipment_type` VALUES (4, '可乐魔法器');

-- ----------------------------
-- Table structure for factory
-- ----------------------------
DROP TABLE IF EXISTS `factory`;
CREATE TABLE `factory`  (
  `uid` int NULL DEFAULT NULL,
  `factoryId` int NOT NULL AUTO_INCREMENT,
  `factoryName` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `factoryInfo` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `factoryStatus` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`factoryId`) USING BTREE,
  INDEX `factory_manager_key`(`uid`) USING BTREE,
  CONSTRAINT `factory_manager_key` FOREIGN KEY (`uid`) REFERENCES `manager` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of factory
-- ----------------------------

-- ----------------------------
-- Table structure for manager
-- ----------------------------
DROP TABLE IF EXISTS `manager`;
CREATE TABLE `manager`  (
  `userId` int NOT NULL AUTO_INCREMENT,
  `account` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `type` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `tel` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`userId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of manager
-- ----------------------------
INSERT INTO `manager` VALUES (5, 'test', '96C599F1F4310D62929441218EA129CC40141A1F0938F6E58679D13B', 'test', 'yfl888@88.com', '工厂管理员', '123456789');

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `orderId` int NOT NULL AUTO_INCREMENT,
  `pId` int NOT NULL,
  `productAmount` int NOT NULL,
  `accomplishDeadline` datetime NOT NULL,
  `tenderDeadline` datetime NOT NULL,
  `consigneeId` int NOT NULL,
  `orderStatus` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`orderId`) USING BTREE,
  INDEX `order_product_key`(`pId`) USING BTREE,
  INDEX `consignee_order_key`(`consigneeId`) USING BTREE,
  CONSTRAINT `order_consignee_key` FOREIGN KEY (`consigneeId`) REFERENCES `consignee` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_product_key` FOREIGN KEY (`pId`) REFERENCES `product` (`productId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order
-- ----------------------------

-- ----------------------------
-- Table structure for order_record
-- ----------------------------
DROP TABLE IF EXISTS `order_record`;
CREATE TABLE `order_record`  (
  `oid` int NOT NULL,
  `managerId` int NOT NULL,
  `price` int NOT NULL,
  INDEX `order_record_key`(`oid`) USING BTREE,
  INDEX `manager_record_key`(`managerId`) USING BTREE,
  CONSTRAINT `manager_record_key` FOREIGN KEY (`managerId`) REFERENCES `manager` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_record_key` FOREIGN KEY (`oid`) REFERENCES `order` (`orderId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order_record
-- ----------------------------

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `productId` int NOT NULL AUTO_INCREMENT,
  `ptId` int NULL DEFAULT NULL,
  `productName` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `productInfo` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `productSpecification` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`productId`) USING BTREE,
  INDEX `product_type`(`ptId`) USING BTREE,
  CONSTRAINT `product_productType_key` FOREIGN KEY (`ptId`) REFERENCES `product_type` (`typeId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES (2, 2, '铁剑1', '武器，近战', '100kg');
INSERT INTO `product` VALUES (3, 2, '弩箭', '武器，远程', '100kg');
INSERT INTO `product` VALUES (4, 3, '蛋糕', '食物，生日，食品', '10kg');
INSERT INTO `product` VALUES (5, 4, '可乐', '食物，快乐，饮料，不健康', '5kg');
INSERT INTO `product` VALUES (6, 3, '炸鸡', '食物，快乐，不健康', '5kg');

-- ----------------------------
-- Table structure for product_type
-- ----------------------------
DROP TABLE IF EXISTS `product_type`;
CREATE TABLE `product_type`  (
  `typeId` int NOT NULL AUTO_INCREMENT,
  `typeName` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`typeId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of product_type
-- ----------------------------
INSERT INTO `product_type` VALUES (1, '棉制品');
INSERT INTO `product_type` VALUES (2, '铁制品');
INSERT INTO `product_type` VALUES (3, '食品');
INSERT INTO `product_type` VALUES (4, '饮品');

SET FOREIGN_KEY_CHECKS = 1;
