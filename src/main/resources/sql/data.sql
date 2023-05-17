INSERT INTO passenger (passenger_id, age, sex)
VALUES (1, 10, 'M'),
        (2, 20, 'F'),
        (3, 21, 'F'),
        (4, 24, 'F'),
        (5, 26, 'F'),
        (6, 30, 'M'),
        (7, 29, 'M'),
        (8, 30, 'M'),
        (9, 50, 'F');

SELECT SETVAL('passenger_passenger_id_seq', (SELECT MAX(passenger_id) FROM passenger));

INSERT INTO aircraft (aircraft_id, company, model)
VALUES (1, 'Airbus', 'A320'),
        (2, 'Boeing', '767'),
        (3, 'UAC', 'Tupolev');

SELECT SETVAL('aircraft_aircraft_id_seq', (SELECT MAX(aircraft_id) FROM aircraft));

INSERT INTO flight (flight_id, city_from, city_to, date_out, aircraft_id, cost)
VALUES  (1,  'Moscow',    'Ryasan',    '2020-01-23', 1, 1000),
        (2,  'Paris',     'London',    '2020-02-13', 2, 1200),
        (3,  'London',    'Minsk',     '2020-04-06', 3, 600),
        (4,  'Minsk',     'Moscow',    '2020-06-03', 3, 700),
        (5,  'Moscow',    'NewYork',   '2020-11-15', 2, 3000),
        (6,  'Moscow',    'Minsk',     '2020-03-01', 2, 1300),
        (7,  'Paris',     'Amsterdam', '2020-08-23', 1, 800),
        (8,  'Rostov',    'Minsk',     '2020-07-05', 1, 1600),
        (9,  'Amsterdam', 'Minsk',     '2020-02-09', 3, 2000),
        (10, 'Moscow',    'Berlin',    '2020-01-23', 1, 1200),
        (11, 'Berlin',    'London',    '2020-12-26', 2, 900),
        (12, 'Paris',     'London',    '2020-03-05', 3, 1000),
        (13, 'Moscow',    'Ryasan',    '2020-02-06', 1, 1300);

SELECT SETVAL('flight_flight_id_seq', (SELECT MAX(flight_id) FROM flight));

INSERT INTO ticket(order_id, passenger_id, flight_id)
VALUES  (1,  2, 1),
        (2,  4, 2),
        (3,  7, 4),
        (4,  8, 6),
        (5,  9, 9),
        (6,  3, 3),
        (7,  5, 5),
        (8,  6, 6),
        (9,  7, 7),
        (10, 1, 3),
        (11, 1, 7),
        (12, 2, 9),
        (13, 4, 1),
        (14, 6, 4),
        (15, 8, 2),
        (16, 3, 6),
        (17, 9, 7),
        (18, 2, 6),
        (19, 2, 11),
        (20, 3, 13),
        (21, 6, 12),
        (22, 1, 11),
        (23, 1, 13);

SELECT SETVAL('ticket_order_id_seq', (SELECT MAX(order_id) FROM ticket));