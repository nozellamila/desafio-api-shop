INSERT INTO ROLE VALUES(1, 'ADMIN');
INSERT INTO ROLE VALUES(2, 'USER');

INSERT INTO USER VALUES(1, 'admin@email.com', 'Admin', '$2a$10$gS0IGVFRnR9FD0wqgeLi.usdTherz.DXgKiWWMgT7tbjVLWZdWlEW');
INSERT INTO USER VALUES(2, 'user@email.com', 'User', '$2a$10$gS0IGVFRnR9FD0wqgeLi.usdTherz.DXgKiWWMgT7tbjVLWZdWlEW');
INSERT INTO USER VALUES(3, 'user.cart@email.com', 'UserForRegisterCart', '$2a$10$gS0IGVFRnR9FD0wqgeLi.usdTherz.DXgKiWWMgT7tbjVLWZdWlEW');
INSERT INTO USER VALUES(4, 'exclude.me@email.com', 'ExcludeMe', '$2a$10$gS0IGVFRnR9FD0wqgeLi.usdTherz.DXgKiWWMgT7tbjVLWZdWlEW');

INSERT INTO PRODUCT VALUES(1, 'Pomada para gatos adultos', 'Pomada antifungica', 65.0, 10);
INSERT INTO PRODUCT VALUES(2, 'Focinheira de couro com fivelas laterais', 'Focinheira de couro', 47.50, 5);
INSERT INTO PRODUCT VALUES(3, 'Produto para ser excluido', 'Produto a ser excluido', 47.50, 10);