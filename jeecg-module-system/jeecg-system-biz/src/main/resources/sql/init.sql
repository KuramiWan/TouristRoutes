use `jeecg-boot`;
DROP TABLE IF EXISTS `traveler`;
CREATE TABLE `traveler` (
                            `id` bigint(36) NOT NULL COMMENT 'ID',
                            `real_name` text DEFAULT NULL COMMENT '真名',
                            `id_card` varchar(255) DEFAULT NULL COMMENT '身份证',
                            `phone` varchar(255) NOT NULL COMMENT '电话',
                            `birthday` text DEFAULT NULL COMMENT '生日',
                            `gender` varchar(255) DEFAULT NULL COMMENT '性别',
                            `type` varchar(255) DEFAULT NULL COMMENT '类型',
                            `user_id` varchar(255) DEFAULT NULL COMMENT '用户id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='出行人';

DROP TABLE IF EXISTS `contact_person`;
CREATE TABLE `contact_person` (
                                  `id` bigint(36) NOT NULL COMMENT 'ID',
                                  `real_name` text DEFAULT NULL COMMENT '真名',
                                  `phone` varchar(255) DEFAULT NULL COMMENT '电话',
                                  `order_id` varchar(255) DEFAULT NULL COMMENT '订单id'

) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='联系人';

DROP TABLE IF EXISTS `traveler_order`;
CREATE TABLE `traveler_order` (
                                  `id` bigint(36) NOT NULL COMMENT 'ID',
                                  `order_id` text DEFAULT NULL COMMENT '真名',
                                  `traveler_id` varchar(255) DEFAULT NULL COMMENT '出行人id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='出行人订单联系id';