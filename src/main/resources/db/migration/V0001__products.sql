-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema textile_bpm
-- -----------------------------------------------------

CREATE SCHEMA IF NOT EXISTS `textile_bpm` DEFAULT CHARACTER SET utf8 ;
USE `textile_bpm` ;

-- -----------------------------------------------------
-- Table `textile_bpm`.`order_entry`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `textile_bpm`.`order_entry` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `voucher_number` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

USE `textile_bpm` ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
