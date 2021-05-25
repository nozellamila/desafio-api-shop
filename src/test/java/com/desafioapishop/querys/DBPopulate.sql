INSERT INTO ROLE VALUES(1, 'ADMIN');
INSERT INTO ROLE VALUES(2, 'USER');

INSERT INTO USER VALUES(1, 'admin@email.com', 'Admin', '$2a$10$.7EbLhT7pQNG/G6u.RAscuPI4q9yBu1fL.A1NhTsbOOfTm6eYOlMi');
INSERT INTO USER VALUES(2, 'user@email.com', 'User', '$2a$10$lESLnwSZwAK9StY7cotyze584M75aylXEA/.HWTnv.6lamaZJzJKy');
INSERT INTO USER VALUES(3, 'exclude.me@email.com', 'ExcludeMe', '$2a$10$lESLnwSZwAK9StY7cotyze584M75aylXEA/.HWTnv.6lamaZJzJKy');
INSERT INTO USER VALUES(4, 'user.cart@email.com', 'UserForRegisterCart', '$2a$10$lESLnwSZwAK9StY7cotyze584M75aylXEA/.HWTnv.6lamaZJzJKy');

INSERT INTO USER_ROLE VALUES(1, 1);
INSERT INTO USER_ROLE VALUES(2, 2);
INSERT INTO USER_ROLE VALUES(3, 2);
INSERT INTO USER_ROLE VALUES(4, 2);

INSERT INTO PRODUCT VALUES(1, 'Ração para cães adultos', 'Magnus 15kg', 65.0, 10);
INSERT INTO PRODUCT VALUES(2, 'Focinheira para cães grandes', 'Focinheira de couro', 47.50, 10);