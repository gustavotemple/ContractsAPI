INSERT INTO clients (client_id, cnpj)
VALUES (1, '01234');
INSERT INTO clients (client_id, cnpj)
VALUES (2, '56789');

INSERT INTO services (service_id, name)
VALUES (1, 'BACKEND');
INSERT INTO services (service_id, name)
VALUES (2, 'FRONTEND');

INSERT INTO contracts (contract_id, number, begin_date, end_date, service_id, client_id)
VALUES (1, '123', parsedatetime('18-08-2020 01:02:03.000', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('20-08-2020 01:02:03.000', 'dd-MM-yyyy hh:mm:ss.SS'), 1, 1);
INSERT INTO contracts (contract_id, number, begin_date, end_date, service_id, client_id)
VALUES (2, '456', parsedatetime('18-08-2020 01:02:03.000', 'dd-MM-yyyy hh:mm:ss.SS'), parsedatetime('20-08-2020 01:02:03.000', 'dd-MM-yyyy hh:mm:ss.SS'), 2, 2);
