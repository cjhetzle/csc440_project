CREATE TABLE IF NOT EXISTS PartSource (
	psID CHAR(5) NOT NULL,
	PRIMARY KEY (psID)
);

CREATE TABLE IF NOT EXISTS ServiceCenter (
	cid CHAR(5) NOT NULL,
	name CHAR(50) NOT NULL,
	street CHAR(50) NOT NULL,
	city CHAR(25) NOT NULL,
	state CHAR(2) NOT NULL,
	zip CHAR(5) NOT NULL,
	phone CHAR(20) NOT NULL,
	PRIMARY KEY (cid),
	FOREIGN KEY (cid) REFERENCES PartSource(psID)
);

CREATE TABLE IF NOT EXISTS Employee (
	cid CHAR(5) NOT NULL,
	eid INT NOT NULL,
	name CHAR(50) NOT NULL,
	street CHAR(30) NOT NULL,
	city CHAR(15) NOT NULL,
	state CHAR(2) NOT NULL,
	zip CHAR(5) NOT NULL,
	emailAddress CHAR(50) NOT NULL,
	phone CHAR(20) NOT NULL,
	role CHAR(12) CHECK (role IN ('mechanic', 'receptionist', 'manager')),
	startDate DATE NOT NULL,
	pay DECIMAL(7,2) NOT NULL,
	password CHAR(100) NOT NULL,
	PRIMARY KEY (eid),
	FOREIGN KEY (cid) REFERENCES ServiceCenter(cid)
);

CREATE TABLE IF NOT EXISTS Customer (
	customerID INT NOT NULL AUTO_INCREMENT,
	cid CHAR(5) NOT NULL,
	name CHAR(50) NOT NULL,
	street CHAR(50) NOT NULL,
	city CHAR(25) NOT NULL,
	state CHAR(2) NOT NULL,
	zip CHAR(5) NOT NULL,
	emailAddress CHAR(50) NOT NULL UNIQUE,
	phone CHAR(20) NOT NULL,
	password CHAR(100) NOT NULL,
	PRIMARY KEY (customerID),
	FOREIGN KEY (cid) REFERENCES ServiceCenter(cid)
);

ALTER TABLE Customer AUTO_INCREMENT=1001;

CREATE TABLE IF NOT EXISTS Vehicle (
	license CHAR(8) NOT NULL,
	make CHAR(20) NOT NULL,
	model CHAR(20) NOT NULL,
	year SMALLINT NOT NULL,
	datePurchased DATE NOT NULL,
	customerID INT NOT NULL,
	PRIMARY KEY (license),
	FOREIGN KEY (customerID) REFERENCES Customer(customerID)
);

CREATE TABLE IF NOT EXISTS Service (
	serviceID INT NOT NULL AUTO_INCREMENT,
	name CHAR(50) NOT NULL,
	PRIMARY KEY (serviceID)
);

CREATE TABLE IF NOT EXISTS ByMileage (
	serviceID INT NOT NULL,
	mileage INT NOT NULL,
	make CHAR(50) NOT NULL,
	model CHAR(50) NOT NULL,
	PRIMARY KEY (serviceID),
	UNIQUE (mileage, make, model),
	FOREIGN KEY (serviceID) REFERENCES Service(serviceID)
);

CREATE TABLE IF NOT EXISTS BillableUnit (
	serviceID INT NOT NULL,
	PRIMARY KEY (serviceID),
	FOREIGN KEY (serviceID) REFERENCES Service(serviceID),
	chargeRate SMALLINT  NOT NULL,
	timeReq DECIMAL(3,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS Includes (
	includer INT NOT NULL,
	included INT NOT NULL,
	PRIMARY KEY (includer, included),
	FOREIGN KEY (includer) REFERENCES ByMileage(serviceID),
	FOREIGN KEY (included) REFERENCES Service(serviceID)
);

CREATE TABLE IF NOT EXISTS PartDatabase (
	partID INT NOT NULL,
	distID CHAR(5) NOT NULL,
	name CHAR(30) NOT NULL,
    unitPrice INT NOT NULL,
	distDelay SMALLINT,
	PRIMARY KEY (partID),
	FOREIGN KEY (distID) REFERENCES PartSource(psID)
);

CREATE TABLE IF NOT EXISTS Warranty (
	service INT NOT NULL,
	duration INT NOT NULL,
	PRIMARY KEY (service),
	FOREIGN KEY (service) REFERENCES BillableUnit(serviceID)
);

CREATE TABLE IF NOT EXISTS Consumes (
	serviceID INT NOT NULL,
	partID INT NOT NULL,
	qty SMALLINT NOT NULL,
	PRIMARY KEY (serviceID),
	FOREIGN KEY (serviceID) REFERENCES BillableUnit(serviceID),
	FOREIGN KEY (partID) REFERENCES PartDatabase(partID)
);

CREATE TABLE IF NOT EXISTS Fault (
	name CHAR(50) NOT NULL,
    diagnosticFee INT NOT NULL,
	PRIMARY KEY (name)
);

CREATE TABLE IF NOT EXISTS Recommends (
	fault CHAR(50) NOT NULL,
	make CHAR(20) NOT NULL,
	model CHAR(20) NOT NULL,
	serviceID INT NOT NULL,
	PRIMARY KEY (fault, serviceID),
	FOREIGN KEY (serviceID) REFERENCES BillableUnit(serviceID),
	FOREIGN KEY (fault) REFERENCES Fault(name)
);

CREATE TABLE IF NOT EXISTS ServiceAppointment (
	license CHAR(20) NOT NULL,
	apptDate DATE NOT NULL,
	fault CHAR(50),
	preferredMechanic INT,
	mileage INT NOT NULL,
	PRIMARY KEY (license, apptDate),
	FOREIGN KEY (license) REFERENCES Vehicle(license),
	FOREIGN KEY (fault) REFERENCES Fault(name),
	FOREIGN KEY (preferredMechanic) REFERENCES Employee(eid)
);

CREATE TABLE IF NOT EXISTS Schedules (
	license CHAR(20) NOT NULL,
	dateScheduled DATE NOT NULL,
	timeSlot CHAR(5) CHECK(timeSlot IN ('08:00','08:15','08:30','08:45','09:00','09:15','09:30','09:45','10:00','10:15','10:30','10:45','11:00','11:15','11:30','11:45','12:00','12:15','12:30','12:45','13:00','13:15','13:30','13:45','14:00','14:15','14:30','14:45','15:00','15:15','15:30','15:45','16:00','16:15','16:30','16:45','17:00','17:15','17:30','17:45','18:00','18:15','18:30','18:45')),
	mechanic INT NOT NULL,
	service INT NOT NULL,
	PRIMARY KEY (license, dateScheduled, timeSlot, mechanic),
    UNIQUE (license, dateScheduled, timeSlot),
    UNIQUE (mechanic, dateScheduled, timeSlot),
	FOREIGN KEY (mechanic) REFERENCES Employee(eid),
	FOREIGN KEY (service) REFERENCES BillableUnit(serviceID),
	FOREIGN KEY (license, dateScheduled) REFERENCES ServiceAppointment(license, apptDate)
);

CREATE TABLE IF NOT EXISTS PartInventory (
	partID INT NOT NULL,
	cid CHAR(5) NOT NULL,
	quantity SMALLINT NOT NULL,
	minQnty SMALLINT NOT NULL,
	minOrderQnty SMALLINT NOT NULL,
	PRIMARY KEY (partID, cid),
	FOREIGN KEY (partID) REFERENCES PartDatabase(partID),
	FOREIGN KEY (cid) REFERENCES ServiceCenter(cid)
);

CREATE TABLE IF NOT EXISTS PartOrders (
	orderID INT NOT NULL AUTO_INCREMENT,
	dateOrdered DATE NOT NULL,
	dateExpected DATE NOT NULL,
	dateArrived DATE,
	partID INT NOT NULL,
	quantity SMALLINT NOT NULL,
	sourceFrom CHAR(5) NOT NULL,
	centerTo char(5) NOT NULL,
	status CHAR(8) CHECK (status IN ('pending', 'complete')),
	PRIMARY KEY (orderID),
	FOREIGN KEY (centerTo) REFERENCES ServiceCenter(cid),
	FOREIGN KEY (sourceFrom) REFERENCES PartSource(psID),
	FOREIGN KEY (partID) REFERENCES PartDatabase(partID)
);

CREATE TABLE IF NOT EXISTS Paycheck (
	datePaid DATE NOT NULL,
	periodStart DATE NOT NULL,
	periodEnd DATE NOT NULL,
	eid INT NOT NULL,
	rate INT NOT NULL,
	payableTime INT NOT NULL,
	earning INT NOT NULL,
	ytdEarning INT NOT NULL,
	PRIMARY KEY (eid, datePaid),
	FOREIGN KEY (eid) REFERENCES Employee(eid)
);

CREATE TABLE IF NOT EXISTS Notifications (
	notificationID INT NOT NULL AUTO_INCREMENT,
	dateGenerated DATE NOT NULL,
	orderID INT NOT NULL,
	PRIMARY KEY (notificationID),
    UNIQUE (orderID),
	FOREIGN KEY (orderID) REFERENCES PartOrders(orderID)
);

CREATE TRIGGER order_status_change AFTER UPDATE ON PartOrders
FOR EACH ROW
BEGIN
	IF NEW.status = 'complete' THEN
		UPDATE PartInventory PI SET PI.quantity = PI.quantity + NEW.quantity
		WHERE PI.partID = NEW.partID AND PI.cid = NEW.centerTo;
	END IF;
	IF NEW.status = 'delayed' THEN
		INSERT INTO Notifications VALUES (
			DEFAULT, CURDATE(), NEW.orderID
		);
	END IF;
END;

CREATE TRIGGER order_from_service_center AFTER INSERT ON PartOrders
FOR EACH ROW
BEGIN
	IF NEW.sourceFrom IN (SELECT cid FROM ServiceCenter) THEN
		UPDATE PartInventory PI SET PI.quantity = PI.quantity - NEW.quantity
        WHERE PI.partID = NEW.partID AND PI.cid = NEW.sourceFrom;
	END IF;
END;