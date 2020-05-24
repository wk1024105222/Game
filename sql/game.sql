/*
 Navicat Premium Data Transfer

 Source Server         : wkai
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : localhost:3306
 Source Schema         : wkai

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 25/05/2020 00:07:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for chat_record
-- ----------------------------
drop table IF EXISTS `chat_record`;
create TABLE `chat_record` (
  `msg_id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '消息ID',
  `from_user_id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '发送方ID',
  `to_user_id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '接收方ID',
  `msg_type` char(1) COLLATE utf8_bin NOT NULL COMMENT '消息类型 T-text I-image',
  `msg_text` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '消息内容',
  `pic_url` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '图片URL',
  `create_time` bigint(20) NOT NULL COMMENT '发送时间',
  `status` char(1) COLLATE utf8_bin NOT NULL COMMENT '是否已读 0-未读 1-已读',
  `order_id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '关联订单号',
  PRIMARY KEY (`msg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='聊天记录表';

-- ----------------------------
-- Table structure for deliver_log
-- ----------------------------
drop table IF EXISTS `deliver_log`;
create TABLE `deliver_log` (
  `msg_id` varchar(32) COLLATE utf8_bin NOT NULL,
  `time` datetime NOT NULL,
  `message` varchar(100) COLLATE utf8_bin NOT NULL,
  `order_id` varchar(32) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`msg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='发货记录表';

-- ----------------------------
-- Table structure for goods_info
-- ----------------------------
drop table IF EXISTS `goods_info`;
create TABLE `goods_info` (
  `goods_id` varchar(32) NOT NULL COMMENT '商品ID',
  `title` varchar(15) NOT NULL COMMENT '标题',
  `game_id` varchar(30) NOT NULL COMMENT '游戏ID',
  `area_id` varchar(30) DEFAULT NULL COMMENT '区服ID',
  `server_id` varchar(30) DEFAULT NULL COMMENT '服务器ID',
  `camp_id` varchar(30) DEFAULT NULL COMMENT '阵营ID',
  `goods_type` varchar(30) NOT NULL COMMENT '商品类型',
  `trace_type` char(1) DEFAULT NULL COMMENT '担保交易（写死）、寄售交易',
  `contain_num` int(11) NOT NULL COMMENT '包含数量',
  `price` decimal(11,2) NOT NULL COMMENT '价格',
  `stock` int(11) NOT NULL COMMENT '库存',
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名',
  `mobile` varchar(20) DEFAULT NULL,
  `tranHour_begin` varchar(6) NOT NULL COMMENT '方便交易开始时间',
  `tranHour_end` varchar(6) NOT NULL COMMENT '方便交易结束时间',
  `expire_days` int(11) NOT NULL COMMENT '有效天数',
  `expire_time` datetime NOT NULL COMMENT '过期时间',
  `recommend_rank` char(1) DEFAULT NULL COMMENT '推荐等级',
  `status` char(1) NOT NULL COMMENT '上架 下架 删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `user_id` varchar(15) NOT NULL,
  PRIMARY KEY (`goods_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品表';

-- ----------------------------
-- Table structure for login_record
-- ----------------------------
drop table IF EXISTS `login_record`;
create TABLE `login_record` (
  `sessionid` varchar(32) NOT NULL COMMENT '会话ID',
  `login_time` datetime NOT NULL COMMENT '登录时间',
  `user_id` varchar(15) NOT NULL COMMENT '用户ID',
  `status` char(1) NOT NULL COMMENT '状态 1登录中 0 已退出 2超时退出',
  `logout_time` datetime DEFAULT NULL COMMENT '登出时间',
  PRIMARY KEY (`sessionid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='登录记录表';

-- ----------------------------
-- Table structure for mobilecheck_record
-- ----------------------------
drop table IF EXISTS `mobilecheck_record`;
create TABLE `mobilecheck_record` (
  `trace_id` varchar(32) NOT NULL COMMENT '请求流水号',
  `mobile` varchar(15) NOT NULL COMMENT '手机号',
  `purpose` char(1) NOT NULL COMMENT '用途',
  `check_code` varchar(10) NOT NULL COMMENT '验证码',
  `result_code` varchar(6) NOT NULL COMMENT '第三方结果码',
  `req_time` datetime NOT NULL COMMENT '请求时间',
  `resp_time` datetime NOT NULL,
  PRIMARY KEY (`trace_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='手机验证码发送记录表';

-- ----------------------------
-- Table structure for order_Record
-- ----------------------------
drop table IF EXISTS `order_Record`;
create TABLE `order_Record` (
  `order_id` varchar(32) NOT NULL COMMENT '订单ID',
  `goods_id` varchar(32) NOT NULL COMMENT '商品ID',
  `role_name` varchar(100) NOT NULL COMMENT '收货人角色名',
  `price` decimal(11,2) NOT NULL COMMENT '商品价格',
  `buy_num` int(11) NOT NULL COMMENT '购买数量',
  `buyer_mobile` varchar(15) NOT NULL COMMENT '手机号',
  `total_amount` decimal(11,2) NOT NULL COMMENT '总金额',
  `bala_amount` decimal(11,2) NOT NULL COMMENT '余额使用金额',
  `pay_amount` decimal(11,2) NOT NULL COMMENT '实付金额',
  `status` char(1) NOT NULL COMMENT '待支付、待发货、待收货、交易成功、交易取消',
  `create_time` datetime NOT NULL COMMENT '订单创建时间',
  `pay_time` datetime DEFAULT NULL COMMENT '更新时间',
  `finish_time` datetime DEFAULT NULL COMMENT '订单结束时间',
  `buyer_id` varchar(15) NOT NULL COMMENT '买家ID',
  `seller_id` varchar(15) NOT NULL COMMENT '卖家ID',
  `chat_id` varchar(32) DEFAULT NULL COMMENT '在线聊天会话ID',
  `deliver_id` varchar(32) DEFAULT NULL COMMENT '发货流水ID',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单记录表';

-- ----------------------------
-- Table structure for tran_static
-- ----------------------------
drop table IF EXISTS `tran_static`;
create TABLE `tran_static` (
  `user_id` varchar(15) NOT NULL,
  `buy_num` int(11) NOT NULL DEFAULT '0' COMMENT '买方成交数量',
  `sell_num` int(11) NOT NULL DEFAULT '0' COMMENT '卖方成交数量',
  `buy_cancel_numi` int(11) NOT NULL DEFAULT '0' COMMENT '买方取消数量',
  `sell_cancel_num` int(11) NOT NULL DEFAULT '0' COMMENT '卖方取消数量',
  `buy_rank` char(3) NOT NULL DEFAULT '000' COMMENT '买家等级',
  `sell_rank` char(3) NOT NULL DEFAULT '000' COMMENT '卖家等级',
  `tran_rate` decimal(5,2) DEFAULT NULL COMMENT '成交率',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
drop table IF EXISTS `user_info`;
create TABLE `user_info` (
  `user_id` varchar(15) NOT NULL COMMENT '用户ID 手机号',
  `login_pwd` varchar(32) NOT NULL COMMENT '登录密码',
  `pay_pwd` varchar(32) NOT NULL COMMENT '支付密码',
  `user_name` varchar(40) NOT NULL COMMENT '用户姓名',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `idno` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `balance` decimal(11,2) NOT NULL COMMENT '账户余额',
  `status` char(1) NOT NULL COMMENT '状态 正常、冻结',
  `buy_credit` decimal(5,2) NOT NULL COMMENT '买家信用分',
  `buy_sucess` int(9) NOT NULL COMMENT '买家成功数量',
  `buy_cancel` int(9) NOT NULL COMMENT '买家取消数量',
  `sell_credit` decimal(5,2) NOT NULL COMMENT '卖家信用分',
  `sell_success` int(9) NOT NULL COMMENT '卖家成功数量',
  `sell_cancel` int(9) NOT NULL COMMENT '卖家取消数量',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';

SET FOREIGN_KEY_CHECKS = 1;
