CREATE TABLE Address(parking INTEGER NOT NULL, street TEXT NOT NULL, number INTEGER NOT NULL, city TEXT NOT NULL, zipcode INTEGER NOT NULL, country TEXT NOT NULL, FOREIGN KEY(parking) REFERENCES Parking(parkingId), PRIMARY KEY (street, number, zipcode, city, country));
CREATE TABLE Car(height INTEGER , fuel TEXT, user INTEGER NOT NULL, latitude DOUBLE, longitude DOUBLE,  FOREIGN KEY(user) REFERENCES User(userId));
CREATE TABLE FuelParking(parking INTEGER NOT NULL, forbidden_fuel TEXT NOT NULL, FOREIGN KEY(parking) REFERENCES Parking(parkingId));
CREATE TABLE "History" ("user" INTEGER NOT NULL ,"start" DATETIME NOT NULL  DEFAULT (null) ,"end" DATETIME NOT NULL  DEFAULT (null) ,"parking" INTEGER NOT NULL, FOREIGN KEY(parking) REFERENCES Parking(parkingId), PRIMARY KEY (user, start, end) );
CREATE TABLE "HourlyRate" ("parking" INTEGER NOT NULL, "start" TIME  NOT NULL  DEFAULT (null) ,"end" TIME NOT NULL  DEFAULT (null) ,"cost" INTEGER NOT NULL , FOREIGN KEY(parking) REFERENCES Parking(parkingId), PRIMARY KEY (parking, cost, start, end) );
CREATE TABLE Localisation(parking INTEGER NOT NULL, latitude DOUBLE NOT NULL, longitude DOUBLE NOT NULL, FOREIGN KEY(parking) REFERENCES Parking(parkingId), PRIMARY KEY (latitude, longitude));
CREATE TABLE OpeningHours(dayStart INTEGER NOT NULL CHECK (dayStart <= 6 AND dayStart >= 0), 
dayEnd INTEGER NOT NULL CHECK (dayEnd <= 6 AND dayEnd >= 0),
hourStart TIME NOT NULL, hourEnd TIME NOT NULL, parking INTEGER NOT NULL, FOREIGN KEY(parking) REFERENCES Parking(parkingId));
CREATE TABLE Parking(parkingId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, name varchar(20) NOT NULL, defibrillator BOOL DEFAULT 0, totalPlaces INTEGER CHECK (totalPlaces > 0),
freePlaces INTEGER CHECK(freePlaces < totalPlaces AND freePlaces >= 0) DEFAULT totalPlaces, maxHeight INTEGER CHECK (maxHeight > 0));
CREATE TABLE Pass(passId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, dateStart DATETIME NOT NULL, dateEnd DATETIME NOT NULL CHECK (dateEnd > dateStart), user INTEGER NOT NULL, FOREIGN KEY(user) REFERENCES User(userId));
CREATE TABLE PassParking(parking INTEGER NOT NULL, pass INTEGER NOT NULL, FOREIGN KEY(parking) REFERENCES Parking(parkingId), FOREIGN KEY(pass) REFERENCES Pass(passId));
CREATE TABLE User(userId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, type INTEGER NOT NULL CONSTRAINT chk_type CHECK (type IN (0, 1, 2)), username varchar(20) NOT NULL UNIQUE, password varchar(20) NOT NULL);
