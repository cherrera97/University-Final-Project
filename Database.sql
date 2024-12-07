-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`user_table`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`user_table` ;

CREATE TABLE IF NOT EXISTS `mydb`.`user_table` (
  `email` VARCHAR(45) NOT NULL,
  `pass` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`email`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`user_accounts`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`user_accounts` ;

CREATE TABLE IF NOT EXISTS `mydb`.`user_accounts` (
  `email` VARCHAR(45) NOT NULL,
  `accountID` VARCHAR(50) NOT NULL,
  `accountType` VARCHAR(20) NOT NULL,
  `issuer` VARCHAR(30) NOT NULL,
  `balance` DOUBLE NOT NULL,
  `dueDate` DATE NULL,
  INDEX `fk_user_accounts_user_table_idx` (`email` ASC) VISIBLE,
  UNIQUE INDEX `accountID_UNIQUE` (`accountID` ASC) VISIBLE,
  PRIMARY KEY (`accountID`),
  CONSTRAINT `fk_user_accounts_user_table`
    FOREIGN KEY (`email`)
    REFERENCES `mydb`.`user_table` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`account_transactions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`account_transactions` ;

CREATE TABLE IF NOT EXISTS `mydb`.`account_transactions` (
  `accountID` VARCHAR(50) NOT NULL,
  `transactionID` VARCHAR(50) NOT NULL,
  `name` VARCHAR(50) NOT NULL,
  `amount` DOUBLE NOT NULL,
  `date` DATE NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `category` VARCHAR(45) NULL,
  PRIMARY KEY (`transactionID`),
  INDEX `fk_account_transactions_user_accounts1_idx` (`accountID` ASC) VISIBLE,
  UNIQUE INDEX `transactionID_UNIQUE` (`transactionID` ASC) VISIBLE,
  INDEX `fk_account_transactions_user_table1_idx` (`email` ASC) VISIBLE,
  CONSTRAINT `fk_account_transactions_user_accounts1`
    FOREIGN KEY (`accountID`)
    REFERENCES `mydb`.`user_accounts` (`accountID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_account_transactions_user_table1`
    FOREIGN KEY (`email`)
    REFERENCES `mydb`.`user_table` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
