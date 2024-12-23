CREATE DATABASE IF NOT EXISTS clush;

USE clush;

CREATE TABLE IF NOT EXISTS `user`
(
    `user_id`  VARCHAR(31) PRIMARY KEY,
    `password` VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS `calendar`
(
    `cid`       BIGINT AUTO_INCREMENT  PRIMARY KEY,
    `user_id`   VARCHAR(31)  NOT NULL,
    `name`      VARCHAR(255) NOT NULL DEFAULT '이벤트',
    `start`     TIMESTAMP    NOT NULL,
    `end`       TIMESTAMP    NOT NULL,
    `need_noti` BOOL         NULL     DEFAULT FALSE,
    `depth`     SMALLINT     NULL     DEFAULT 1,
    `tag`       TINYINT               DEFAULT 0,
    `sent`      BOOL              DEFAULT FALSE,
    CONSTRAINT `FK_User_TO_Calendar_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
);

CREATE TABLE IF NOT EXISTS `todo`
(
    `tid`      BIGINT AUTO_INCREMENT  PRIMARY KEY,
    `user_id`  VARCHAR(31)  NOT NULL,
    `priority` SMALLINT     NULL,
    `complete` TIMESTAMP    NULL,
    `delay`    BOOL         NOT NULL DEFAULT FALSE,
    `name`     VARCHAR(255) NULL,
    `category` VARCHAR(31)  NOT NULL,
    CONSTRAINT `FK_User_TO_Todo_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
);
