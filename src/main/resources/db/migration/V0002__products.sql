USE `textile_bpm` ;

-- -----------------------------------------------------
-- Table `textile_bpm`.`order_inward_entry`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `textile_bpm`.`order_inward_entry` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `party_name` VARCHAR(100) NOT NULL,
  `bill_number` INT NOT NULL,
  `bill_date` DATETIME NOT NULL,
  `bill_amount` DOUBLE NOT NULL DEFAULT 0.00,
  `lot_number` INT NOT NULL,
  `quality` VARCHAR(100) NOT NULL,
  `than_quantity` INT NOT NULL,
  `meters` DOUBLE NOT NULL DEFAULT 0.00,
  `bale_quantity` INT NOT NULL,
  `rate` DOUBLE NOT NULL DEFAULT 0.00,
  `lr_number` INT NOT NULL,
  `order_number` INT NOT NULL,
   PRIMARY KEY (`id`))
ENGINE = InnoDB;