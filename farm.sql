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
  `wechat` varchar(40) DEFAULT NULL COMMENT '微信ID',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


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
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_user_token` (
  `user_id` BIGINT(20) NOT NULL COMMENT '用户id',
  `token` VARCHAR(130) DEFAULT NULL COMMENT '登录token',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`user_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;



CREATE TABLE `t_duck_breeding` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `num` int(11) NOT NULL DEFAULT '0' COMMENT '孵化蛋数量',
  `num_harvest` int(11) NOT NULL DEFAULT '0' COMMENT '已孵化的鸭数量',
  `day` int(11) NOT NULL DEFAULT '0' COMMENT '已孵化的天数',
  `acc_no_feed` int(11) NOT NULL DEFAULT '0' COMMENT '累计未喂养天数',
  `status` int(5) NOT NULL DEFAULT '1' COMMENT '1:孵化中，2：孵化成功，3：孵化失败',
  `if_feed` int(5) DEFAULT '1' COMMENT '今日是否喂养 1:是，2：否',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_duck_breeding_userid` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `t_duck_warehouse` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) NOT NULL,
  `duck` int(11) NOT NULL DEFAULT '0' COMMENT '种鸭(待生产)',
  `duck_doing` int(11) NOT NULL DEFAULT '0' COMMENT '生产中的鸭',
  `egg` int(11) NOT NULL DEFAULT '0' COMMENT '商品蛋(可出售)',
  `egg_freeze` int(11) NOT NULL DEFAULT '0' COMMENT '冻结中的蛋',
  `egg_harvest` int(11) NOT NULL DEFAULT '0' COMMENT '可收获的蛋',
  `if_harvest` int(5) NOT NULL DEFAULT '1' COMMENT '今日是否丰收 1:是，2：否',
  `if_steal` int(5) NOT NULL DEFAULT '1' COMMENT '今日是否可偷 1:是，2：否',
  `all_sell` int(11) NOT NULL DEFAULT '0' COMMENT '累计出售商品蛋数量',
  `all_profit` decimal(18,2) DEFAULT '0.00' COMMENT '累计出售商品蛋利润',
  `all_integral` decimal(18,2) DEFAULT '0.00' COMMENT '累计出售商品蛋积分',
  `food` decimal(18,2) DEFAULT '0.00' COMMENT '剩余饲料',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_duck_warehouse_userid` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `t_egg_breeding` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `num` int(11) NOT NULL DEFAULT '0' COMMENT '孵化蛋数量',
  `num_harvest` int(11) NOT NULL DEFAULT '0' COMMENT '已孵化的鸭数量',
  `day` int(11) NOT NULL DEFAULT '0' COMMENT '已孵化的天数',
  `acc_no_hot` int(11) NOT NULL DEFAULT '0' COMMENT '累计未加温天数',
  `status` int(5) NOT NULL DEFAULT '1' COMMENT '1:孵化中，2：孵化成功，3：孵化失败',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_egg_breeding_userid` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `t_egg_warehouse` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `egg` int(11) NOT NULL DEFAULT '0' COMMENT '种蛋（待孵化）',
  `egg_doing` int(11) NOT NULL DEFAULT '0' COMMENT '孵化中的蛋',
  `duck` int(11) NOT NULL DEFAULT '0' COMMENT '商品鸭(可出售)',
  `duck_freeze` int(11) NOT NULL DEFAULT '0' COMMENT '冻结中的鸭',
  `duck_harvest` int(11) NOT NULL DEFAULT '0' COMMENT '可收获的鸭',
  `if_hot` int(5) NOT NULL DEFAULT '1' COMMENT '今日是否加温1:是，2否',
  `if_harvest` int(5) NOT NULL DEFAULT '1' COMMENT '今日是否丰收1:是，2否',
  `if_steal` int(5) NOT NULL DEFAULT '1' COMMENT '今日是否可偷1:是，2否',
  `all_sell` int(11) NOT NULL DEFAULT '0' COMMENT '累计出售商品鸭数量',
  `all_profit` decimal(18,2) DEFAULT '0.00' COMMENT '累计出售商品鸭利润',
  `all_integral` decimal(18,2) DEFAULT '0.00' COMMENT '累计出售商品鸭积分',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_egg_warehouse_userid` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `t_notice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `type` int(5) NOT NULL COMMENT '1:喂鸡，2：给蛋加温，3：孵蛋',
  `remarks` varchar(200) DEFAULT NULL,
  `add_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_notice_userid` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `t_user_token` (
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `token` varchar(130) DEFAULT NULL COMMENT '登录token',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4


