--CREATE DATABASE airport;

CREATE TABLE IF NOT EXISTS passenger (
	passenger_id SERIAL PRIMARY KEY,
	age INT NOT NULL CHECK (age >= 6 AND age <= 100),
	sex VARCHAR(1)
);

CREATE TABLE IF NOT EXISTS aircraft (
	aircraft_id SERIAL PRIMARY KEY,
	company VARCHAR(128) NOT NULL,
	model VARCHAR(128) NOT NULL
);

CREATE TABLE IF NOT EXISTS flight (
	flight_id SERIAL PRIMARY KEY,
	city_from VARCHAR(128) NOT NULL,
	city_to VARCHAR(128) NOT NULL,
	date_out DATE NOT NULL CHECK(date_out > '1900-01-01'),
	aircraft_id INT REFERENCES aircraft(aircraft_id) ON DELETE CASCADE,
	cost INT NOT NULL CHECK(cost > 0)
);

CREATE TABLE IF NOT EXISTS ticket (
	order_id SERIAL PRIMARY KEY,
	passenger_id INT REFERENCES passenger(passenger_id) ON DELETE CASCADE,
	flight_id INT REFERENCES flight(flight_id) ON DELETE CASCADE,
	UNIQUE(passenger_id, flight_id)
);