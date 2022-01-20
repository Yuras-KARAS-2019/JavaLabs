INSERT INTO employee (name, role, login, password)
VALUES ('Vovka', 'cashier', 'admin', 'd033e22ae348aeb5660fc2140aec35850c4da997'),
       ('Raul', 'merchandise expert', 'raul', 'd033e22ae348aeb5660fc2140aec35850c4da997'),
       ('Pablo', 'merchandise expert', 'pablo', 'd033e22ae348aeb5660fc2140aec35850c4da997');
INSERT INTO product (name, price, amount)
VALUES ('Apples', '10.50', '100'),
       ('Bananas', '27', '130'),
       ('Meat', '165', '10');
INSERT INTO receipt(cashier_id, status)
VALUES (1, 'closed'),
       (2, 'closed');
INSERT INTO receipt_product(receipt_id, product_id, quantity)
VALUES (1, 1, 2.5),
       (1, 2, 5),
       (2, 2, 5),
       (2, 3, 5);