CREATE SCHEMA `internet_shop` DEFAULT CHARACTER SET utf8 ;

 CREATE TABLE `internet_shop`.`products` (
                                             `product_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                             `name` VARCHAR(255) NOT NULL,
                                             `price` DECIMAL(10,2) NOT NULL,
                                             `deleted` TINYINT UNSIGNED NOT NULL DEFAULT 0,
                                             PRIMARY KEY (`product_id`));
CREATE TABLE `internet_shop`.`users` (
                                         `user_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                         `name` VARCHAR(225) NOT NULL,
                                         `login` VARCHAR(225) NOT NULL,
                                         `password` VARCHAR(225) NOT NULL,
                                         `salt` VARBINARY(16) NOT NULL,
                                         `deleted` TINYINT UNSIGNED NOT NULL DEFAULT 0,
                                         PRIMARY KEY (`user_id`),
                                         UNIQUE INDEX `name_UNIQUE` (`login` ASC) VISIBLE);
CREATE TABLE `internet_shop`.`roles` (
                                         `role_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                         `role_name` VARCHAR(225) NOT NULL,
                                         PRIMARY KEY (`role_id`),
                                         UNIQUE INDEX `role_name_UNIQUE` (`role_name` ASC) VISIBLE);
CREATE TABLE `internet_shop`.`orders` (
                                          `order_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                          `user_id` BIGINT(11) NOT NULL,
                                          `deleted` TINYINT UNSIGNED NOT NULL DEFAULT 0,
                                          PRIMARY KEY (`order_id`),
                                          INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
                                          CONSTRAINT `user_id`
                                              FOREIGN KEY (`user_id`)
                                                  REFERENCES `internet_shop`.`users` (`user_id`)
                                                  ON DELETE NO ACTION
                                                  ON UPDATE NO ACTION);
CREATE TABLE `internet_shop`.`shopping_carts` (
                                                  `cart_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                                  `user_id` BIGINT(11) NOT NULL,
                                                  `deleted` TINYINT UNSIGNED NOT NULL DEFAULT 0,
                                                  PRIMARY KEY (`cart_id`),
                                                  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
                                                  CONSTRAINT `user_id_cart`
                                                      FOREIGN KEY (`user_id`)
                                                          REFERENCES `internet_shop`.`users` (`user_id`)
                                                          ON DELETE NO ACTION
                                                          ON UPDATE NO ACTION);
CREATE TABLE `internet_shop`.`orders_products` (
                                                   `order_id` BIGINT(11) NOT NULL,
                                                   `product_id` BIGINT(11) NOT NULL,
                                                   INDEX `id_from_orders_idx` (`order_id` ASC) VISIBLE,
                                                   INDEX `id_from_products_idx` (`product_id` ASC) VISIBLE,
                                                   CONSTRAINT `id_from_orders`
                                                       FOREIGN KEY (`order_id`)
                                                           REFERENCES `internet_shop`.`orders` (`order_id`)
                                                           ON DELETE NO ACTION
                                                           ON UPDATE NO ACTION,
                                                   CONSTRAINT `id_from_products`
                                                       FOREIGN KEY (`product_id`)
                                                           REFERENCES `internet_shop`.`products` (`product_id`)
                                                           ON DELETE NO ACTION
                                                           ON UPDATE NO ACTION);
CREATE TABLE `internet_shop`.`shopping_carts_products` (
                                                           `cart_id` BIGINT(11) NOT NULL,
                                                           `product_id` BIGINT(11) NOT NULL,
                                                           INDEX `cartid_from_cart_idx` (`cart_id` ASC) VISIBLE,
                                                           INDEX `productid_from_cart_idx` (`product_id` ASC) VISIBLE,
                                                           CONSTRAINT `cartid_from_cart`
                                                               FOREIGN KEY (`cart_id`)
                                                                   REFERENCES `internet_shop`.`shopping_carts` (`cart_id`)
                                                                   ON DELETE NO ACTION
                                                                   ON UPDATE NO ACTION,
                                                           CONSTRAINT `productid_from_cart`
                                                               FOREIGN KEY (`product_id`)
                                                                   REFERENCES `internet_shop`.`products` (`product_id`)
                                                                   ON DELETE NO ACTION
                                                                   ON UPDATE NO ACTION);
CREATE TABLE `internet_shop`.`users_roles` (
                                               `user_id` BIGINT(11) NOT NULL,
                                               `role_id` BIGINT(11) NOT NULL,
                                               INDEX `userid_from_roles_idx` (`user_id` ASC) VISIBLE,
                                               INDEX `roleid_from_roles_idx` (`role_id` ASC) VISIBLE,
                                               CONSTRAINT `userid_from_roles`
                                                   FOREIGN KEY (`user_id`)
                                                       REFERENCES `internet_shop`.`users` (`user_id`)
                                                       ON DELETE NO ACTION
                                                       ON UPDATE NO ACTION,
                                               CONSTRAINT `roleid_from_roles`
                                                   FOREIGN KEY (`role_id`)
                                                       REFERENCES `internet_shop`.`roles` (`role_id`)
                                                       ON DELETE NO ACTION
                                                       ON UPDATE NO ACTION);
INSERT INTO `internet_shop`.`roles` (`role_name`) VALUES ('USER');
INSERT INTO `internet_shop`.`roles` (`role_name`) VALUES ('ADMIN');
