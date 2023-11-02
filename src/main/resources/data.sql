INSERT INTO cars(license_plate, brand, model, vin_number, color, engine, winter_tyres)
VALUES
    ('NL-01-NL', 'Opel', 'Calibra', 'VNV22212345678910',  'blue', '2.5', false),
    ('NL-02-NL', 'Opel', 'Manta', 'VNV00012345678910',  'red', '2.0', false);




INSERT INTO customers(sur_name, last_name, address, phone_number, bank_account, corporate)
VALUES
    ('Jan', 'Janssen', 'straat 1', 0100000000, 'NL01BANK00000', false),
    ('Kees', 'knol', 'laan 2', 0200000000, 'NL02BANK00000', true);


INSERT INTO employees(sur_name, last_name, address, phone_number, function)
VALUES
    ('Enzo', 'Ferrari', 'straat 3', 1231231234, 'Founder'),
    ('Ferruccio', 'Lamborghini', 'straat 8', 7897897894, 'Founder');


INSERT INTO parts(part_number, name, description, location, stock, purchase_price, selling_price)
VALUES
    ('28250', 'klem', 'mooie klem', 'A1,02', 3, 4.50, 7.50),
    ('28350', 'klem', 'mooie klem', 'A1,02', 1, 3.50, 6.50),
    ('28150', 'bout', 'mooie bout', 'A1,05', 6, 0.75, 2.25);



INSERT INTO invoices(invoice_number, total_price, approved, paid, labor_hours)
VALUES
    ('12345', '150', true, false, 1.5),
    ('22222', '200', true, true, 2.0);