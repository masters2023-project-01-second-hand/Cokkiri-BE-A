SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `catrgory`, `chat_message`, `chat_room`, `favorite`, `image`, `item`, `item_content`, `item_metadata`, `location`, `status`, `user`;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE `catrgory`
(
    `id`   int         NOT NULL AUTO_INCREMENT,
    `name` varchar(45) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `location`
(
    `id`   int NOT NULL,
    `name` varchar(100) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `status`
(
    `id`   int          NOT NULL AUTO_INCREMENT,
    `name` varchar(100) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `user`
(
    `id`                int          NOT NULL AUTO_INCREMENT,
    `username`          varchar(100) NOT NULL,
    `password`          varchar(100) NOT NULL,
    `profile_image_url` varchar(2000) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `item`
(
    `id`          int          NOT NULL AUTO_INCREMENT,
    `title`       varchar(100) NOT NULL,
    `price`       int DEFAULT NULL,
    `seller_id`   int          NOT NULL,
    `location_id` int          NOT NULL,
    `status_id`   int          NOT NULL,
    `catrgory_id` int          NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_item_user1_idx` (`seller_id`),
    KEY `fk_item_location1_idx` (`location_id`),
    KEY `fk_item_status1_idx` (`status_id`),
    KEY `fk_item_catrgory1_idx` (`catrgory_id`),
    CONSTRAINT `fk_item_catrgory1` FOREIGN KEY (`catrgory_id`) REFERENCES `catrgory` (`id`),
    CONSTRAINT `fk_item_location1` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`),
    CONSTRAINT `fk_item_status1` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`),
    CONSTRAINT `fk_item_user1` FOREIGN KEY (`seller_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `chat_room`
(
    `id`          int NOT NULL AUTO_INCREMENT,
    `item_id`     int NOT NULL,
    `consumer_id` int NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_chat_room_item1_idx` (`item_id`),
    KEY `fk_chat_room_user1_idx` (`consumer_id`),
    CONSTRAINT `fk_chat_room_item1` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`),
    CONSTRAINT `fk_chat_room_user1` FOREIGN KEY (`consumer_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `chat_message`
(
    `id`           int NOT NULL AUTO_INCREMENT,
    `content`      varchar(2000) DEFAULT NULL,
    `seller_id`    int NOT NULL,
    `chat_room_id` int NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_chat_message_user1_idx` (`seller_id`),
    KEY `fk_chat_message_chat_room1_idx` (`chat_room_id`),
    CONSTRAINT `fk_chat_message_chat_room1` FOREIGN KEY (`chat_room_id`) REFERENCES `chat_room` (`id`),
    CONSTRAINT `fk_chat_message_user1` FOREIGN KEY (`seller_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `favorite`
(
    `id`      int NOT NULL AUTO_INCREMENT,
    `user_id` int NOT NULL,
    `item_id` int NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_favorite_user1_idx` (`user_id`),
    KEY `fk_favorite_item1_idx` (`item_id`),
    CONSTRAINT `fk_favorite_item1` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`),
    CONSTRAINT `fk_favorite_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `image`
(
    `id`      int NOT NULL AUTO_INCREMENT,
    `url`     varchar(2000) DEFAULT NULL,
    `item_id` int NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_image_item1_idx` (`item_id`),
    CONSTRAINT `fk_image_item1` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `item_content`
(
    `id`      int NOT NULL AUTO_INCREMENT,
    `content` varchar(4000) DEFAULT NULL,
    `item_id` int NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_item_content_item_idx` (`item_id`),
    CONSTRAINT `fk_item_content_item` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `item_metadata`
(
    `id`       int NOT NULL AUTO_INCREMENT,
    `hit`      int DEFAULT NULL,
    `chat`     int DEFAULT NULL,
    `favorite` int DEFAULT NULL,
    `item_id`  int NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_item_metadata_item1_idx` (`item_id`),
    CONSTRAINT `fk_item_metadata_item1` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
