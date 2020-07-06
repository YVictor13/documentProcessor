CREATE TABLE `operatelog` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `userid` INT NOT NULL,
  `type` VARCHAR(32) DEFAULT NULL,
  `inputpath` VARCHAR(512) DEFAULT NULL,
  `outputpath` VARCHAR(512) DEFAULT NULL,
  `description` VARCHAR(1024) DEFAULT NULL,
  `operatetime` TIME DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userid` (`userid`),
  CONSTRAINT `operatelog_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `userinfo` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8