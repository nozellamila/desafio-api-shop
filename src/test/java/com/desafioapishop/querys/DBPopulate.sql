INSERT INTO ROLE VALUES(1, 'ADMIN');
INSERT INTO ROLE VALUES(2, 'USER');

INSERT INTO USER VALUES(1, 'admin@email.com', 'Admin', '$2a$10$gS0IGVFRnR9FD0wqgeLi.usdTherz.DXgKiWWMgT7tbjVLWZdWlEW');
INSERT INTO USER VALUES(2, 'user@email.com', 'User', '$2a$10$gS0IGVFRnR9FD0wqgeLi.usdTherz.DXgKiWWMgT7tbjVLWZdWlEW');
INSERT INTO USER VALUES(3, 'user.cart@email.com', 'UserForRegisterCart', '$2a$10$gS0IGVFRnR9FD0wqgeLi.usdTherz.DXgKiWWMgT7tbjVLWZdWlEW');
INSERT INTO USER VALUES(4, 'user.cart.2@email.com', 'UserForRegisterCart', '$2a$10$gS0IGVFRnR9FD0wqgeLi.usdTherz.DXgKiWWMgT7tbjVLWZdWlEW');
INSERT INTO USER VALUES(5, 'exclude.me@email.com', 'ExcludeMe', '$2a$10$gS0IGVFRnR9FD0wqgeLi.usdTherz.DXgKiWWMgT7tbjVLWZdWlEW');
INSERT INTO USER VALUES(6, 'user.nocart@email.com', 'UserNoCart', '$2a$10$gS0IGVFRnR9FD0wqgeLi.usdTherz.DXgKiWWMgT7tbjVLWZdWlEW');

INSERT INTO USER_ROLE VALUES(1, 1);
INSERT INTO USER_ROLE VALUES(2, 2);
INSERT INTO USER_ROLE VALUES(3, 2);
INSERT INTO USER_ROLE VALUES(4, 2);
INSERT INTO USER_ROLE VALUES(5, 2);
INSERT INTO USER_ROLE VALUES(6, 2);

INSERT INTO PRODUCT VALUES(1, 'Product to be found and repeat name', 'Product to find', 65.0, 10);
INSERT INTO PRODUCT VALUES(2, 'Product to not be excluded', 'Product from cart', 47.50, 5);
INSERT INTO PRODUCT VALUES(3, 'Product to finish buy', 'Product to finish buy', 50, 10);
INSERT INTO PRODUCT VALUES(4, 'Product to cancel buy', 'Product to cancel buy', 37.50, 20);
INSERT INTO PRODUCT VALUES(5, 'Product to be excluded', 'Product to be excluded', 37.50, 20);

INSERT INTO CART VALUES (1, '2021-06-03 16:07:58', 47.50, 1, 1);
INSERT INTO CART VALUES (2, '2021-05-13 16:07:58', 100, 2, 3);
INSERT INTO CART VALUES (3, '2021-05-13 16:07:58', 37.50, 1, 4);

INSERT INTO PRODUCT_CART VALUES (2, 1);
INSERT INTO PRODUCT_CART VALUES (3, 2);
INSERT INTO PRODUCT_CART VALUES (4, 3);

INSERT INTO AUX_PRODUCT_CART VALUES (1, 47.50, 2, 1, 1);
INSERT INTO AUX_PRODUCT_CART VALUES (2, 50, 3, 2, 2);
INSERT INTO AUX_PRODUCT_CART VALUES (3, 37.50, 4, 1, 3);