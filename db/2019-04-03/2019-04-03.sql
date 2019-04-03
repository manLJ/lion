/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3306
 Source Schema         : lion

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 03/04/2019 15:12:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for conf_config
-- ----------------------------
DROP TABLE IF EXISTS `conf_config`;
CREATE TABLE `conf_config`  (
                              `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
                              `name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配置名称',
                              `alias` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配置别名',
                              `description` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配置描述',
                              `status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '状态：1，启用；0，禁用',
                              `creator` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
                              `create_date` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
                              `modifier` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
                              `update_date` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改时间',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'conf_配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for conf_config_item
-- ----------------------------
DROP TABLE IF EXISTS `conf_config_item`;
CREATE TABLE `conf_config_item`  (
                                   `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
                                   `config_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置标识',
                                   `parent_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父类',
                                   `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配置项名称',
                                   `alias` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配置项别名',
                                   `value` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配置项值',
                                   `status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态：1，启用；0，禁用',
                                   `sort` decimal(10, 0) NULL DEFAULT NULL COMMENT '排序号',
                                   `creator` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
                                   `create_date` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
                                   `modifier` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
                                   `update_date` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改时间',
                                   PRIMARY KEY (`id`) USING BTREE,
                                   INDEX `FK_conf_config_item_config_id`(`config_id`) USING BTREE,
                                   CONSTRAINT `FK_conf_config_item_config_id` FOREIGN KEY (`config_id`) REFERENCES `conf_config` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'conf_配置项表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mgnt_slowsql
-- ----------------------------
DROP TABLE IF EXISTS `mgnt_slowsql`;
CREATE TABLE `mgnt_slowsql`  (
                               `ID` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
                               `OPERATETIME` datetime(0) NULL DEFAULT NULL COMMENT '执行时间',
                               `SLOWSQL` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'SQL',
                               `DURATION` decimal(8, 0) NULL DEFAULT NULL COMMENT '单位：毫秒',
                               PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '慢SQL明细' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
                           `ID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
                           `PARENT_ID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父级ID',
                           `CODE` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单编码',
                           `NAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
                           `LEVEL` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '层级',
                           `DELSTATUS` int(11) NULL DEFAULT 1 COMMENT '禁用标识:0,禁用;1,启用',
                           `CREATOR` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人名称',
                           `CREATE_DATE` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建时间',
                           `MODIFIER` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
                           `UPDATE_DATE` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改时间',
                           `SORT_INDEX` int(255) NULL DEFAULT NULL COMMENT '排序号',
                           PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'sys_菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1113286383553851394', NULL, 'systemmgr', '系统管理', NULL, 1, 'admin', '2019-04-03 11:45:32', 'admin', '2019-04-03 11:47:27', 1);
INSERT INTO `sys_menu` VALUES ('1113286455674908673', '1113286383553851394', 'systemmgr.category', '字典管理', NULL, 1, 'admin', '2019-04-03 11:45:49', NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES ('1113286530165747714', '1113286383553851394', 'systemmgr.menu', '导航管理', NULL, 1, 'admin', '2019-04-03 11:46:07', NULL, NULL, 2);
INSERT INTO `sys_menu` VALUES ('1113286621865816065', '1113286383553851394', 'systemmgr.user', '用户管理', NULL, 1, 'admin', '2019-04-03 11:46:29', NULL, NULL, 3);
INSERT INTO `sys_menu` VALUES ('1113286702870409218', '1113286383553851394', 'systemmgr.role', '角色管理', NULL, 1, 'admin', '2019-04-03 11:46:48', NULL, NULL, 4);
INSERT INTO `sys_menu` VALUES ('1113286970429255681', '1113286383553851394', 'systemmgr.changepwd', '修改密码', NULL, 1, 'admin', '2019-04-03 11:47:52', NULL, NULL, 5);
INSERT INTO `sys_menu` VALUES ('1113287037328404481', '1113286383553851394', 'systemmgr.accounterror', '账号解锁', NULL, 1, 'admin', '2019-04-03 11:48:08', NULL, NULL, 6);
INSERT INTO `sys_menu` VALUES ('1113287115619282945', '1113286383553851394', 'systemmgr.slowsql', '慢SQL记录', NULL, 1, 'admin', '2019-04-03 11:48:26', NULL, NULL, 7);

-- ----------------------------
-- Table structure for sys_menu_operation
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_operation`;
CREATE TABLE `sys_menu_operation`  (
                                     `ID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
                                     `CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作编码',
                                     `MENU_ID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单ID',
                                     `NAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作名称',
                                     `DELSTATUS` int(11) NULL DEFAULT 1 COMMENT '禁用标识',
                                     PRIMARY KEY (`ID`) USING BTREE,
                                     INDEX `IM_MENU_OPERATION_MENU_ID`(`MENU_ID`) USING BTREE,
                                     CONSTRAINT `IM_MENU_OPERATION_MENU_ID` FOREIGN KEY (`MENU_ID`) REFERENCES `sys_menu` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'sys_菜单操作管理表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
                           `id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
                           `role_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
                           `type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色类型：企业：ENTERPRISE，运维管理：MANAGEMENT',
                           `delstatus` int(11) NULL DEFAULT 1 COMMENT '禁用标识:0,禁用;1,启用',
                           `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
                           `creator` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人名称',
                           `create_date` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建时间',
                           `modifier` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
                           `update_date` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改时间',
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'sys_角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1113287220736929794', '超级管理员', 'MANAGEMENT', 1, NULL, 'admin', '2019-04-03 11:48:51', NULL, NULL);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
                                `id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
                                `menu_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单ID',
                                `role_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色ID',
                                `delstatus` int(11) NULL DEFAULT 1 COMMENT '禁用标识',
                                PRIMARY KEY (`id`) USING BTREE,
                                INDEX `IX_ROLE_MENU_MENU_ID`(`menu_id`) USING BTREE,
                                INDEX `IX_ROLE_MENU_ROLE_ID`(`role_id`) USING BTREE,
                                CONSTRAINT `IX_ROLE_MENU_MENU_ID` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                CONSTRAINT `IX_ROLE_MENU_ROLE_ID` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'sys_角色菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('1113338280805441537', '1113286383553851394', '1113287220736929794', 1);
INSERT INTO `sys_role_menu` VALUES ('1113338280805441538', '1113286455674908673', '1113287220736929794', 1);
INSERT INTO `sys_role_menu` VALUES ('1113338280813830146', '1113286530165747714', '1113287220736929794', 1);
INSERT INTO `sys_role_menu` VALUES ('1113338280818024450', '1113286621865816065', '1113287220736929794', 1);
INSERT INTO `sys_role_menu` VALUES ('1113338280818024451', '1113286702870409218', '1113287220736929794', 1);
INSERT INTO `sys_role_menu` VALUES ('1113338280818024452', '1113286970429255681', '1113287220736929794', 1);
INSERT INTO `sys_role_menu` VALUES ('1113338280818024453', '1113287037328404481', '1113287220736929794', 1);
INSERT INTO `sys_role_menu` VALUES ('1113338280818024454', '1113287115619282945', '1113287220736929794', 1);

-- ----------------------------
-- Table structure for sys_role_operation
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_operation`;
CREATE TABLE `sys_role_operation`  (
                                     `id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
                                     `menu_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单ID',
                                     `operation_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作ID',
                                     `role_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色ID',
                                     `delstatus` int(11) NULL DEFAULT 1 COMMENT '禁用标识',
                                     PRIMARY KEY (`id`) USING BTREE,
                                     INDEX `IM_ROLE_OPERATION_MENU_ID`(`menu_id`) USING BTREE,
                                     INDEX `IM_ROLE_OPERATION_ROLE_ID`(`role_id`) USING BTREE,
                                     INDEX `IM_ROLE_OPERATION_OPERATION_ID`(`operation_id`) USING BTREE,
                                     CONSTRAINT `IM_ROLE_OPERATION_MENU_ID` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                     CONSTRAINT `IM_ROLE_OPERATION_OPERATION_ID` FOREIGN KEY (`operation_id`) REFERENCES `sys_menu_operation` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                     CONSTRAINT `IM_ROLE_OPERATION_ROLE_ID` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'sys_角色菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
                           `id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
                           `account` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录账号',
                           `china_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中文名称',
                           `head_photo` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像URL',
                           `token` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密钥',
                           `delstatus` int(11) NULL DEFAULT 1 COMMENT '禁用标识:0,禁用;1,启用',
                           `ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后一次登录的IP',
                           `login_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后一次登录的时间',
                           `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
                           `creator` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人名称',
                           `create_date` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建时间',
                           `modifier` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人名称',
                           `update_date` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改时间',
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'sys_用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1234567890', 'admin', '管理员', NULL, 'lpwIktepv6D+mRN/Y2tJ3d8jtlU7MPlMM8JQUM3gw6o=', 1, NULL, NULL, '15951723026', '', '', 'admin', '2019-02-25 16:15:01');

-- ----------------------------
-- Table structure for sys_user_account_error_login
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_account_error_login`;
CREATE TABLE `sys_user_account_error_login`  (
                                               `id` varchar(21) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
                                               `account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账户名',
                                               `ip` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后一次登录的ip',
                                               `error_date` varchar(21) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上次登录错误时间',
                                               `error_times` int(5) NOT NULL COMMENT '错误次数',
                                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_account_lock_record
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_account_lock_record`;
CREATE TABLE `sys_user_account_lock_record`  (
                                               `id` varchar(21) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主鍵',
                                               `acount` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户账号',
                                               `login_ip` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录IP',
                                               `locker` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '锁定人',
                                               `lock_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '锁定时间',
                                               `lock_reason` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '锁定原因',
                                               `un_lock_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '解锁时间',
                                               `un_lock_reason` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '解锁原因',
                                               `un_locker` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '解锁人',
                                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_enterprise_rel
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_enterprise_rel`;
CREATE TABLE `sys_user_enterprise_rel`  (
                                          `id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
                                          `enterprise_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '企业ID',
                                          `user_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户ID',
                                          PRIMARY KEY (`id`) USING BTREE,
                                          INDEX `IM_ENTERPRISE_USER_ENTERPRISEID`(`enterprise_id`) USING BTREE,
                                          INDEX `IM_ENTERPRISE_USER_USERID`(`user_id`) USING BTREE,
                                          CONSTRAINT `IM_ENTERPRISE_USER_ENTERPRISEID` FOREIGN KEY (`enterprise_id`) REFERENCES `b_enterprise` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                          CONSTRAINT `IM_ENTERPRISE_USER_USERID` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'sys_用户企业关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
                                `id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
                                `role_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色ID',
                                `user_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户ID',
                                `delstatus` int(11) NULL DEFAULT 1 COMMENT '禁用标识',
                                PRIMARY KEY (`id`) USING BTREE,
                                INDEX `IX_USER_ROLE_ROLE_ID`(`role_id`) USING BTREE,
                                INDEX `IX_USER_ROLE_USER_ID`(`user_id`) USING BTREE,
                                CONSTRAINT `IX_USER_ROLE_ROLE_ID` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                CONSTRAINT `IX_USER_ROLE_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'sys_用户角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1113315456770416642', '1113287220736929794', '1234567890', 1);

SET FOREIGN_KEY_CHECKS = 1;
