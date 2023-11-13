INSERT INTO cars(license_plate, brand, model, vin_number, color, engine, winter_tyres)
VALUES
    ('NL-01-NL', 'Opel', 'Calibra', 'VNV22212345678910',  'blue', '2.5', false),
    ('NL-02-NL', 'Opel', 'Manta', 'VNV00012345678910',  'red', '2.0', false);


INSERT INTO customers(id, sur_name, last_name, address, phone_number, bank_account, corporate)
VALUES
    (10001, 'Jan', 'Janssen', 'straat 1', 0100000000, 'NL01BANK00000', false),
    (10002, 'Kees', 'knol', 'laan 2', 0200000000, 'NL02BANK00000', true);


INSERT INTO employees(id, sur_name, last_name, address, phone_number, function)
VALUES
    (10001, 'Enzo', 'Ferrari', 'straat 3', 1231231234, 'Founder'),
    (10002, 'Ferruccio', 'Lamborghini', 'straat 8', 7897897894, 'Founder');


INSERT INTO parts(part_number, name, description, location, stock, purchase_price, selling_price)
VALUES
    ('10001', 'klem', 'mooie klem', 'A1,02', 3, 4.50, 7.50),
    ('10002', 'klem', 'mooie klem', 'A1,02', 1, 3.50, 6.50),
    ('10003', 'bout', 'mooie bout', 'A1,05', 6, 0.75, 2.25);


INSERT INTO invoices(invoice_number, total_price, approved, paid, labor_hours)
VALUES
    ('10001', '150', true, false, 1.5),
    ('10002', '200', true, true, 2.0);


-- ///// Security ///// --

INSERT INTO users(username, password, enabled, apikey, email)
VALUES
    ('test admin', '$2a$12$quFO0Y69QFyUprNxzu5aY.IrwKelctwLSWvUTmpg.eO08u.6PIBJy', true, '7847493', 'test@testy.tst'),
    ('test user', '$2a$12$quFO0Y69QFyUprNxzu5aY.IrwKelctwLSWvUTmpg.eO08u.6PIBJy', false, '741582654', 'hoi@hallo.nl');


INSERT INTO authorities(username, authority)
VALUES
    ('test admin', 'ADMIN'),
    ('test user', 'USER');