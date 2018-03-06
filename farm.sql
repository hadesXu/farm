CREATE TABLE `t_account_ticket` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '唯一，关联t_user表',
  `balance` decimal(18,2) DEFAULT '0.00' COMMENT '当前账户余额默认0.00',
  `frozen` decimal(18,2) DEFAULT '0.00' COMMENT '冻结金额',
  `acc_recharge` decimal(18,2) DEFAULT '0.00' COMMENT '累计充值 默认0.00',
  `acc_withdraw` decimal(18,2) DEFAULT '0.00' COMMENT '累计体现 默认0.00',
  `acc_profit` decimal(18,2) DEFAULT '0.00' COMMENT '累计收益 默认0.00',
  `acc_commission` decimal(18,2) DEFAULT '0.00' COMMENT '累计佣金 默认0.00',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_account_ticket_userid` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL COMMENT '唯一，系统生成',
  `telephone` varchar(11) NOT NULL COMMENT '注册手机号,唯一',
  `password` varchar(100) NOT NULL COMMENT '登录密码',
  `number` int(8) NOT NULL COMMENT '唯一，系统生成，填写扫码师傅ID时用到',
  `opend_id` varchar(40) DEFAULT NULL COMMENT '微信ID',
  `father_number` varchar(40) DEFAULT NULL COMMENT '推荐人ID',
  `grade` int(1) NOT NULL DEFAULT '1' COMMENT '1:徒弟（默认）2:师傅 3:师爷 4:地主 5:合伙人  6:代理',
  `is_trainee` int(1) NOT NULL DEFAULT '1' COMMENT '值为1（否）、2（是）。默认为否。',
  `s_egg` int(1) NOT NULL DEFAULT '1' COMMENT '值为1（否）、2（是）。默认为否。',
  `s_duck` int(1) NOT NULL DEFAULT '1' COMMENT '值为1（否）、2（是）。默认为否。',
  `active` varchar(2) DEFAULT '2' COMMENT '值为1（无效）、2（有效）。默认为有效。',
  `img_url` varchar(200) DEFAULT NULL COMMENT '头像地址',
  `qr_code` varchar(200) DEFAULT NULL COMMENT '二维码地址',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_telephone` (`telephone`),
  UNIQUE KEY `idx_user_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `t_account_ticket_flow` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(20) NOT NULL,
  `type` INT(2) NOT NULL COMMENT '1.购买种鸭 2.购买种蛋 3.充值 4.提现 5.出售商品鸭 6.出售商品蛋 7.购买饲料 8.佣金 9.购买看门狗 10.购买机器人 11.兑换积分',
  `amount` DECIMAL(18,2) DEFAULT '0.00' COMMENT '金额',
  `amount_before` DECIMAL(18,2) DEFAULT '0.00' COMMENT '交易前金额',
  `amount_after` DECIMAL(18,2) DEFAULT '0.00' COMMENT '交易后金额',
  `remarks` VARCHAR(200) DEFAULT NULL COMMENT '备注',
  `add_time` DATETIME DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX idx_account_ticket_flow_userid(user_id)
) ENGINE=INNODB DEFAULT CHARSET=utf8;