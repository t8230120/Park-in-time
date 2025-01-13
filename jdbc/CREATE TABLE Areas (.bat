CREATE TABLE Areas (
    area_id INT NOT NULL PRIMARY KEY,
    area_name NVARCHAR(255) NOT NULL,
    parent_area_id INT,
    FOREIGN KEY (parent_area_id) REFERENCES Areas(area_id)
);

CREATE TABLE Users (
    user_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    username NVARCHAR(255) NOT NULL,
    user_password NVARCHAR(255) NOT NULL,
    email NVARCHAR(255) UNIQUE,
    phone_number NVARCHAR(10) UNIQUE,
    userType NVARCHAR(255),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE Parking (
    parking_id NVARCHAR(255) NOT NULL PRIMARY KEY,
    parking_address NVARCHAR(255),
    parking_spots INT,
    area_id INT,
    opening_hour TIME,
    closing_hour TIME,
    google_maps_link NVARCHAR(2083),
    FOREIGN KEY (area_id) REFERENCES Areas(area_id)
);

CREATE TABLE Customer (
    user_id INT NOT NULL PRIMARY KEY,
    customer_id INT UNIQUE NOT NULL AUTO_INCREMENT,
    licencse_number UNIQUE INT,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

CREATE TABLE ParkingManager (
    user_id INT NOT NULL PRIMARY KEY,
    park_man_id INT UNIQUE NOT NULL AUTO_INCREMENT,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

CREATE TABLE ParkingManagerParkings (
    park_man_id INT,
    parking_id NVARCHAR(255),
    PRIMARY KEY (park_man_id, parking_id),
    FOREIGN KEY (park_man_id) REFERENCES ParkingManager(park_man_id),
    FOREIGN KEY (parking_id) REFERENCES Parking(parking_id)
);

CREATE TABLE CarTypes (
    car_type_id INT NOT NULL PRIMARY KEY,
    car_type_name NVARCHAR(255) NOT NULL
);

CREATE TABLE Cars (
    license_plate NVARCHAR(20) NOT NULL PRIMARY KEY,
    car_model NVARCHAR(255),
    car_type_id INT,
    FOREIGN KEY (car_type_id) REFERENCES CarTypes(car_type_id)
);

CREATE TABLE CustomerCar (
    customer_id INT,
    license_plate NVARCHAR(20),
    PRIMARY KEY (customer_id, license_plate),
    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id),
    FOREIGN KEY (license_plate) REFERENCES Cars(license_plate)
);

CREATE TABLE Reservation (
    reservation_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    license_plate NVARCHAR(20),
    customer_id INT,
    parking_id INT,
    reservation_date DATETIME NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id),
    FOREIGN KEY (license_plate) REFERENCES Cars(license_plate),
    FOREIGN KEY (parking_id) REFERENCES Parking(parking_id)
) AUTO_INCREMENT = 6000000;
