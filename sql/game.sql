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

 Date: 15/07/2020 22:19:06
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
  `msg_type` char(1) COLLATE utf8_bin NOT NULL COMMENT '消息类型 T-text I-image N-Notice',
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
  `status` char(1) NOT NULL COMMENT '1创建 2上架 3下架 0删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `user_id` varchar(15) NOT NULL,
  PRIMARY KEY (`goods_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品表';

-- ----------------------------
-- Table structure for interface_record
-- ----------------------------
drop table IF EXISTS `interface_record`;
create TABLE `interface_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `api_name` varchar(20) NOT NULL COMMENT '第三方接口名称',
  `api_channel` varchar(20) NOT NULL COMMENT '第三方渠道名称',
  `source_id` int(11) DEFAULT NULL COMMENT '关联记录ID',
  `req_url` varchar(50) NOT NULL COMMENT '请求URL',
  `req_body` varchar(2000) NOT NULL COMMENT '请求报文',
  `req_time` datetime NOT NULL COMMENT '请求时间',
  `res_body` varchar(2000) DEFAULT NULL COMMENT '响应报文',
  `res_time` datetime DEFAULT NULL COMMENT '响应时间',
  `cost_time` int(11) DEFAULT NULL COMMENT '接口耗时',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='接口调用记录 转账 发短信';

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
  `status` char(1) NOT NULL COMMENT '1、待支付：用户提交订单，但未支付订单\r\n2、待发货：支付成功，但卖家尚未发货订单\r\n3、待收货：卖家已发货、买家未确认\r\n4、交易成功：卖家发货成功订单\r\n5、交易取消：支付超时取消订单、用户取消订单或卖家超时未发货订单',
  `create_time` datetime NOT NULL COMMENT '订单创建时间',
  `pay_time` datetime NOT NULL COMMENT '更新时间',
  `finish_time` datetime NOT NULL COMMENT '订单结束时间',
  `buyer_id` varchar(15) NOT NULL COMMENT '买家ID',
  `seller_id` varchar(15) NOT NULL COMMENT '卖家ID',
  `chat_id` varchar(32) NOT NULL DEFAULT '' COMMENT '在线聊天会话ID',
  `deliver_id` varchar(32) NOT NULL DEFAULT '' COMMENT '发货流水ID',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单记录表';

-- ----------------------------
-- Table structure for pay_record
-- ----------------------------
drop table IF EXISTS `pay_record`;
create TABLE `pay_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `order_id` varchar(32) NOT NULL COMMENT '订单ID',
  `pay_account` varchar(32) NOT NULL COMMENT '支付账户号',
  `pay_account_type` char(1) NOT NULL COMMENT '支付账户类型 b-余额 c-银行卡 a-支付宝 w-微信',
  `pay_amount` decimal(11,2) NOT NULL COMMENT '支付金额',
  `pay_userid` varchar(15) NOT NULL COMMENT '付款方ID',
  `pay_name` varchar(64) NOT NULL DEFAULT '' COMMENT '付款方开户名',
  `pay_phone` varchar(15) NOT NULL DEFAULT '' COMMENT '付款方开户名',
  `rec_account` varchar(32) NOT NULL COMMENT '收款账户号',
  `rec_account_type` char(1) NOT NULL COMMENT '收款账户类型 b-余额 c-银行卡 a-支付宝 w-微信',
  `rec_userid` varchar(15) NOT NULL COMMENT '收款方ID',
  `create_time` datetime NOT NULL COMMENT '支付时间',
  `status` char(2) NOT NULL COMMENT '支付状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='订单支付记录';

-- ----------------------------
-- Table structure for tran_record
-- ----------------------------
drop table IF EXISTS `tran_record`;
create TABLE `tran_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `tran_type` char(1) NOT NULL COMMENT '转账类型 1-付款 2-提现 3-退款 4-充值',
  `tran_amount` decimal(11,2) NOT NULL COMMENT '转账金额',
  `o_type` char(1) NOT NULL COMMENT '支付账户类型 b-余额 c-银行卡 a-支付宝 w-微信',
  `o_account` varchar(32) NOT NULL COMMENT '支付账户号',
  `o_name` varchar(64) DEFAULT NULL COMMENT '付款方开户名',
  `o_phone` varchar(15) DEFAULT NULL COMMENT '付款方银行卡注册手机号',
  `in_type` char(1) NOT NULL COMMENT '收款账户类型 b-余额 c-银行卡 a-支付宝 w-微信',
  `in_account` varchar(32) NOT NULL COMMENT '收款账户号',
  `in_name` varchar(64) DEFAULT NULL COMMENT '收款方开户名',
  `status` char(2) NOT NULL COMMENT '交易状态',
  `pay_id` varchar(32) NOT NULL COMMENT '支付记录ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='动账记录 真实调用接口转账';

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
