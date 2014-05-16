DROP TABLE IF EXISTS "Address";
DROP TABLE IF EXISTS "Car";
DROP TABLE IF EXISTS "Corners";
DROP TABLE IF EXISTS "Entries";
DROP TABLE IF EXISTS "ForbiddenFuel";
DROP TABLE IF EXISTS "Fuel";
DROP TABLE IF EXISTS "History";
DROP TABLE IF EXISTS "HourlyRate";
DROP TABLE IF EXISTS "OpeningHours";
DROP TABLE IF EXISTS "Parking";
DROP TABLE IF EXISTS "User";


CREATE TABLE Address(addressId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, 
	parking INTEGER NOT NULL, 
	street TEXT NOT NULL, 
	number INTEGER NOT NULL, 
	city TEXT NOT NULL, 
	zip INTEGER NOT NULL, 
	country TEXT NOT NULL, 
	latitude DOUBLE NOT NULL, 
	longitude DOUBLE NOT NULL, 
	FOREIGN KEY(parking) REFERENCES Parking(parkingId));
INSERT INTO "Address" VALUES(0, 1, 'Place Sainte Barbe', 1, 'Louvain-la-Neuve', 1348, 'BE', 50.667408, 4.62202);
INSERT INTO "Address" VALUES(1, 3, 'Place de Hotel de Ville', 0, 'Saint-Quentin', 2100, 'FR', 49.846122, 3.287457);
INSERT INTO "Address" VALUES(2, 4, 'Cours Saleya', 0, '6300', 'Nice', 'FR', 43.695607, 7.49227);
INSERT INTO "Address" VALUES(3, 5, 'Place du XVe Corps', 0, 'Nice', 6000, 'FR', 43.7072969, 7.2801912);
INSERT INTO "Address" VALUES(4, 6, 'Place du Palais de Justice', 0, 'Nice', 6000, 'FR', 43.696672, 7.273752);
INSERT INTO "Address" VALUES(5, 7, 'Rue de Ponthieu', 59, 'Paris', 75008, 'FR', 48.8721412, 2.3049417);
INSERT INTO "Address" VALUES(6, 8, 'Rue des Fossés', 19, 'Braine-l Alleud', 1420, 'BE', 50.6836196, 4.3712134);
INSERT INTO "Address" VALUES(7, 9, 'Place de la Justice', 16, 'Bruxelles', 1000, 'BE', 50.8439031, 4.3546111);
INSERT INTO "Address" VALUES(8, 10, 'Boulevard Emile Jacqmain', 14, 'Bruxelles', 1000, 'BE', 50.852511, 4.3529971);
INSERT INTO "Address" VALUES(9, 10, 'Boulevard du Jardin Botanique', 29, 'Bruxelles', 1000, 'BE', 50.854461, 4.3601597);
INSERT INTO "Address" VALUES(11, 12, 'Rue du Damier', 26, 'Bruxelles', 1000, 'BE', 50.8532061, 4.3580816);
INSERT INTO "Address" VALUES(12, 13, 'Rue des Cendres', 8, 'Bruxelles', 1000, 'BE', 50.8546555, 4.3598398);
INSERT INTO "Address" VALUES(13, 14, 'Place du Nouveau Marché aux Grains', 1, 'Bruxelles', 1000, 'BE', 50.8507354, 4.3449141);
INSERT INTO "Address" VALUES(14, 15, 'Rue de Flandre', 60, 'Bruxelles', 1000, 'BE', 50.851665, 4.3462069);
INSERT INTO "Address" VALUES(15, 16, 'Place De Brouckère', 1, 'Bruxelles', 1000, 'BE', 50.8510611, 4.3524389);
INSERT INTO "Address" VALUES(16, 17, 'Boulevard de Waterloo', 1, 'Bruxelles', 1000, 'BE', 50.8388739, 4.3613843);
INSERT INTO "Address" VALUES(17, 18, 'Rue de l\'Ecuyer', 15, 'Bruxelles', 1000, 'BE', 50.8492771, 4.3534652);


CREATE TABLE Car (carId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	name TEXT NOT NULL,
	height INTEGER DEFAULT 0,
	fuel INTEGER DEFAULT 0,
	user INTEGER NOT NULL,
	latitude DOUBLE,
	longitude DOUBLE,
	FOREIGN KEY(user) REFERENCES User(userId),
	FOREIGN KEY(fuel) REFERENCES Fuel(fuelId));
INSERT INTO "Car" VALUES(1, 'My car', 162, 1, 1, 50.668791, 4.62165291);
INSERT INTO "Car" VALUES(2, 'Pimp my car', 165, 3, 1, 50.667408, 4.62202);
INSERT INTO "Car" VALUES(3, 'My tailor is rich in my pimped car', 0, 0, 2, 50.666408, 4.62202);


CREATE TABLE Corners (cornerId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	parking INTEGER NOT NULL,
	latitude DOUBLE NOT NULL,
	longitude DOUBLE NOT NULL,
	FOREIGN KEY(parking) REFERENCES Parking(parkingId));
INSERT INTO "Corners" VALUES(1, 1, 50.667905, 4.621993);
INSERT INTO "Corners" VALUES(2, 1, 50.667876, 4.621063);
INSERT INTO "Corners" VALUES(3, 1, 50.667556, 4.621084);
INSERT INTO "Corners" VALUES(4, 1, 50.667299, 4.621462);
INSERT INTO "Corners" VALUES(5, 1, 50.667408, 4.62202);


CREATE TABLE Entries(entryId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	parking INTEGER NOT NULL,
	latitude DOUBLE NOT NULL,
	longitude DOUBLE NOT NULL,
	FOREIGN KEY(parking) REFERENCES Parking(parkingId));
INSERT INTO "Entries" VALUES(1, 1, 50.667905, 4.621993);


CREATE TABLE ForbiddenFuel (forbiddenFuelId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	parking INTEGER NOT NULL ,
	forbidden INTEGER NOT NULL);
INSERT INTO "ForbiddenFuel" VALUES(1, 1, 3);
INSERT INTO "ForbiddenFuel" VALUES(2, 2, 2);
INSERT INTO "ForbiddenFuel" VALUES(3, 3, 1);
INSERT INTO "ForbiddenFuel" VALUES(4, 4, 4);
INSERT INTO "ForbiddenFuel" VALUES(5, 3, 5);


CREATE TABLE Fuel (fuelId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	name TEXT NOT NULL UNIQUE);
INSERT INTO "Fuel" VALUES(1, 'Diesel');
INSERT INTO "Fuel" VALUES(2, 'Gasoline');
INSERT INTO "Fuel" VALUES(3, 'LPG');
INSERT INTO "Fuel" VALUES(4, 'Ethanol');


CREATE TABLE History (historyId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	car INTEGER NOT NULL,
	start DATETIME NOT NULL,
	end DATETIME DEFAULT (null),
	parking INTEGER NOT NULL,
	user INTEGER NOT NULL,
	feedback INTEGER NOT NULL CONSTRAINT chk_val CHECK (feedback IN (0, 1, 2, 3, 4)));
INSERT INTO "History" VALUES(1, 1, '2007-01-01 10:00:00', '2007-01-01 11:00:00', 1, 1, 2);
INSERT INTO "History" VALUES(2, 1, '2014-05-15 18:00:00', NULL, 1, 1, 3);


CREATE TABLE HourlyRate (hourlyrateId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	start TIME NOT NULL,
	end TIME,
	cost FLOAT NOT NULL DEFAULT (null),
	parking INTEGER NOT NULL);
INSERT INTO "HourlyRate" VALUES(1, '00:00:00', '24:00:00', 2.35, 1);
INSERT INTO "HourlyRate" VALUES(2, '00:00:00', '24:00:00', 4.7, 2);
INSERT INTO "HourlyRate" VALUES(3, '00:00:00', '24:00:00', 7.05, 3);
INSERT INTO "HourlyRate" VALUES(4, '00:00:00', '24:00:00', 9.4, 4);
INSERT INTO "HourlyRate" VALUES(5, '00:00:00', '24:00:00', 11.75, 5);
INSERT INTO "HourlyRate" VALUES(6, '00:00:00', '24:00:00', 14.1, 6);
INSERT INTO "HourlyRate" VALUES(7, '00:00:00', '01:00:00', 1.5, 2);
INSERT INTO "HourlyRate" VALUES(8, '01:00:01', '03:00:00', 1.25, 2);
INSERT INTO "HourlyRate" VALUES(9, '03:00:01', '24:00:00', 1, 2);
INSERT INTO "HourlyRate" VALUES(10, '00:00:00', '01:00:00', 3, 3);
INSERT INTO "HourlyRate" VALUES(11, '01:00:01', '03:00:00', 1.1, 3);
INSERT INTO "HourlyRate" VALUES(12, '03:00:01', '24:00:00', 1, 3);
INSERT INTO "HourlyRate" VALUES(13, '00:00:00', '01:00:00', 1.8, 4);
INSERT INTO "HourlyRate" VALUES(14, '01:00:01', '03:00:00', 1.5, 4);
INSERT INTO "HourlyRate" VALUES(15, '03:00:01', '24:00:00', 1, 4);
INSERT INTO "HourlyRate" VALUES(16, '00:00:00', '01:00:00', 2.1, 5);
INSERT INTO "HourlyRate" VALUES(17, '01:00:01', '03:00:00', 1.5, 5);
INSERT INTO "HourlyRate" VALUES(18, '03:00:01', '24:00:00', 1, 5);
INSERT INTO "HourlyRate" VALUES(19, '00:00:00', '01:00:00', 2.1, 6);
INSERT INTO "HourlyRate" VALUES(20, '01:00:01', '03:00:00', 1.5, 6);
INSERT INTO "HourlyRate" VALUES(21, '03:00:01', '24:00:00', 1, 6);
INSERT INTO "HourlyRate" VALUES(22, '00:00:00', '01:00:00', 2.1, 7);
INSERT INTO "HourlyRate" VALUES(23, '01:00:01', '03:00:00', 1.5, 7);
INSERT INTO "HourlyRate" VALUES(24, '03:00:01', '24:00:00', 1, 7);
INSERT INTO "HourlyRate" VALUES(25, '00:00:00', '01:00:00', 2.1, 8);
INSERT INTO "HourlyRate" VALUES(26, '01:00:01', '03:00:00', 1.5, 8);
INSERT INTO "HourlyRate" VALUES(27, '03:00:01', '24:00:00', 1, 8);
INSERT INTO "HourlyRate" VALUES(28, '00:00:00', '01:00:00', 2.1, 9);
INSERT INTO "HourlyRate" VALUES(29, '01:00:01', '03:00:00', 1.5, 9);
INSERT INTO "HourlyRate" VALUES(30, '03:00:01', '24:00:00', 1, 9);
INSERT INTO "HourlyRate" VALUES(31, '00:00:00', '01:00:00', 2.1, 10);
INSERT INTO "HourlyRate" VALUES(32, '01:00:01', '03:00:00', 1.5, 10);
INSERT INTO "HourlyRate" VALUES(33, '03:00:01', '24:00:00', 1, 10);
INSERT INTO "HourlyRate" VALUES(34, '00:00:00', '01:00:00', 2.1, 11);
INSERT INTO "HourlyRate" VALUES(35, '01:00:01', '03:00:00', 1.5, 11);
INSERT INTO "HourlyRate" VALUES(36, '03:00:01', '24:00:00', 1, 11);
INSERT INTO "HourlyRate" VALUES(37, '00:00:00', '01:00:00', 2.1, 12);
INSERT INTO "HourlyRate" VALUES(38, '01:00:01', '03:00:00', 1.5, 12);
INSERT INTO "HourlyRate" VALUES(39, '03:00:01', '24:00:00', 1, 12);
INSERT INTO "HourlyRate" VALUES(40, '00:00:00', '01:00:00', 2.1, 13);
INSERT INTO "HourlyRate" VALUES(41, '01:00:01', '03:00:00', 1.5, 13);
INSERT INTO "HourlyRate" VALUES(42, '03:00:01', '24:00:00', 1, 13);
INSERT INTO "HourlyRate" VALUES(43, '00:00:00', '01:00:00', 2.1, 14);
INSERT INTO "HourlyRate" VALUES(44, '01:00:01', '03:00:00', 1.5, 14);
INSERT INTO "HourlyRate" VALUES(45, '03:00:01', '24:00:00', 1, 14);
INSERT INTO "HourlyRate" VALUES(46, '00:00:00', '01:00:00', 2.1, 15);
INSERT INTO "HourlyRate" VALUES(47, '01:00:01', '03:00:00', 1.5, 15);
INSERT INTO "HourlyRate" VALUES(48, '03:00:01', '24:00:00', 1, 15);
INSERT INTO "HourlyRate" VALUES(49, '00:00:00', '01:00:00', 2.1, 16);
INSERT INTO "HourlyRate" VALUES(50, '01:00:01', '03:00:00', 1.5, 16);
INSERT INTO "HourlyRate" VALUES(51, '03:00:01', '24:00:00', 1, 16);
INSERT INTO "HourlyRate" VALUES(52, '00:00:00', '01:00:00', 2.1, 17);
INSERT INTO "HourlyRate" VALUES(53, '01:00:01', '03:00:00', 1.5, 17);
INSERT INTO "HourlyRate" VALUES(54, '03:00:01', '24:00:00', 1, 17);
INSERT INTO "HourlyRate" VALUES(55, '00:00:00', '01:00:00', 2.1, 18);
INSERT INTO "HourlyRate" VALUES(56, '01:00:01', '03:00:00', 1.5, 18);
INSERT INTO "HourlyRate" VALUES(57, '03:00:01', '24:00:00', 1, 18);


CREATE TABLE OpeningHours(openingId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	dayStart INTEGER NOT NULL CHECK (dayStart <= 6 AND dayStart >= 0),
	dayEnd INTEGER NOT NULL CHECK (dayEnd <= 6 AND dayEnd >= 0),
	hourStart TIME NOT NULL,
	hourEnd TIME NOT NULL,
	parking INTEGER NOT NULL,
	FOREIGN KEY(parking) REFERENCES Parking(parkingId));
INSERT INTO "OpeningHours" VALUES(1, 0, 6, '00:00:00', '23:59:59', 1);
INSERT INTO "OpeningHours" VALUES(2, 0, 6, '00:00:00', '23:59:59', 2);
INSERT INTO "OpeningHours" VALUES(3, 0, 6, '00:00:00', '23:59:59', 3);
INSERT INTO "OpeningHours" VALUES(4, 0, 6, '00:00:00', '23:59:59', 4);
INSERT INTO "OpeningHours" VALUES(5, 0, 6, '00:00:00', '23:59:59', 5);
INSERT INTO "OpeningHours" VALUES(6, 0, 6, '00:00:00', '23:59:59', 6);
INSERT INTO "OpeningHours" VALUES(7, 0, 6, '00:00:00', '23:59:59', 7);
INSERT INTO "OpeningHours" VALUES(8, 0, 6, '00:00:00', '23:59:59', 8);
INSERT INTO "OpeningHours" VALUES(9, 0, 6, '00:00:00', '23:59:59', 9);
INSERT INTO "OpeningHours" VALUES(10, 0, 6, '00:00:00', '23:59:59', 10);
INSERT INTO "OpeningHours" VALUES(11, 0, 6, '00:00:00', '23:59:59', 11);
INSERT INTO "OpeningHours" VALUES(12, 0, 6, '00:00:00', '23:59:59', 12);
INSERT INTO "OpeningHours" VALUES(13, 0, 6, '00:00:00', '23:59:59', 13);
INSERT INTO "OpeningHours" VALUES(14, 0, 6, '00:00:00', '23:59:59', 14);
INSERT INTO "OpeningHours" VALUES(15, 0, 3, '00:00:00', '22:59:59', 15);
INSERT INTO "OpeningHours" VALUES(16, 4, 6, '00:00:00', '23:59:59', 15);
INSERT INTO "OpeningHours" VALUES(17, 0, 6, '07:00:00', '20:59:59', 16);
INSERT INTO "OpeningHours" VALUES(18, 0, 6, '06:00:00', '20:59:59', 17);
INSERT INTO "OpeningHours" VALUES(19, 0, 6, '00:00:00', '20:59:59', 18);


CREATE TABLE Parking(parkingId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	name varchar(20) NOT NULL, defibrillator BOOL DEFAULT 0,
	totalPlaces INTEGER CHECK (totalPlaces > 0),
	freePlaces INTEGER CHECK(freePlaces <= totalPlaces AND freePlaces >= 0) DEFAULT 0,
	maxHeight INTEGER CHECK (maxHeight > 0),
	disable BOOL DEFAULT 0,
	user INTEGER);
INSERT INTO "Parking" VALUES(1, 'Sainte Barbe', 1, 150, 10, 250, 0, 1);
INSERT INTO "Parking" VALUES(3, 'Hotel de Ville', 0, 150, 10, 260, 1, 1);
INSERT INTO "Parking" VALUES(4, 'Saleya', 1, 170, 17, 310, 1, 1);
INSERT INTO "Parking" VALUES(5, 'Acropolis - Jean Bouin', 0, 70, 15, 10, 1, 1);
INSERT INTO "Parking" VALUES(6, 'Palais de Justice', 0, 770, 150, 10, 0, 1);
INSERT INTO "Parking" VALUES(7, 'Pink Paradise', 0, 1, 0, 180, 0, 1);
INSERT INTO "Parking" VALUES(8, 'City Parking', 0, 1, 0, 200, 0, 1);
INSERT INTO "Parking" VALUES(9, 'Albertine Square', 0, 200, 150, 260, 1, 1);
INSERT INTO "Parking" VALUES(10, 'Alhambra', 0, 200, 150, 300, 1, 1);
INSERT INTO "Parking" VALUES(11, 'Botanique', 0, 200, 150, 320, 1, 1);
INSERT INTO "Parking" VALUES(12, 'Centre', 0, 400, 190, 260, 1, 1);
INSERT INTO "Parking" VALUES(13, 'City 2', 0, 500, 239, 270, 1, 1);
INSERT INTO "Parking" VALUES(14, 'Dansaert', 0, 457, 96, 290, 1, 1);
INSERT INTO "Parking" VALUES(15, 'Dansaert 2', 0, 190, 150, 280, 1, 1);
INSERT INTO "Parking" VALUES(16, 'De Brouckère', 0, 500, 150, 300, 1, 1);
INSERT INTO "Parking" VALUES(17, 'Deux Portes', 0, 190, 150, 300, 1, 1);
INSERT INTO "Parking" VALUES(18, 'Ecuyer', 0, 387, 54, 300, 1, 1);


CREATE TABLE User(userId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	type INTEGER NOT NULL CONSTRAINT chk_type CHECK (type IN (0, 1, 2)),
	username varchar(20) NOT NULL UNIQUE,
	password varchar(20) NOT NULL,
	favoriteCar INT);
INSERT INTO "User" VALUES(1, 1, 'user', 'abcd', 0);
INSERT INTO "User" VALUES(2, 2, 'user2', 'abcd', 0);
INSERT INTO "User" VALUES(3, 0, 'user3', 'abcd', 0);

