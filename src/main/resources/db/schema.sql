CREATE DATABASE IF NOT EXISTS clush;

USE clush;

CREATE TABLE IF NOT EXISTS `user` ( `user_id` VARCHAR(31) PRIMARY KEY , `password` VARCHAR(255) NOT NULL);

CREATE TABLE IF NOT EXISTS `calendar` (
                            `cid` BINARY(16) DEFAULT (UUID_TO_BIN(UUID(), 1)) PRIMARY KEY ,
                            `user_id` VARCHAR(31) NOT NULL,
                            `name` VARCHAR(255) NOT NULL DEFAULT '이벤트',
                            `start` TIMESTAMP NOT NULL,
                            `end` TIMESTAMP NOT NULL,
                            `need_noti` TINYINT(1) NULL DEFAULT 0,
                            `depth` TINYINT NULL DEFAULT 1,
                            `tag` ENUM('RED', 'ORANGE', 'YELLOW', 'GREEN', 'BLUE', 'PURPLE', 'PINK') DEFAULT 'RED',
                            CONSTRAINT `FK_User_TO_Calendar_1` FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`)
);

CREATE TABLE IF NOT EXISTS `todo` (
                        `tid` BINARY(16) DEFAULT (UUID_TO_BIN(UUID(), 1)) PRIMARY KEY ,
                        `user_id` VARCHAR(31) NOT NULL,
                        `priority` TINYINT NULL,
                        `complate` TIMESTAMP NULL,
                        `delay` TINYINT(1) NULL DEFAULT 0,
                        `name` VARCHAR(255) NULL,
                        `category` VARCHAR(31) NOT NULL,
                        CONSTRAINT `FK_User_TO_Todo_1` FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`)
);
