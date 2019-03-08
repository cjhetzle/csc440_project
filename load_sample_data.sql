INSERT INTO `PartSource` VALUES
	('S0001'),
	('S0002'),
	('D0001'),
	('D0002');

INSERT INTO `ServiceCenter` VALUES
	('S0001','Downtown Auto Care','437 Fayetteville St','Raleigh','NC','27601','1.800.899.9999'),
	('S0002','Express Auto Shop','201 N Tryon St','Charlotte','NC','28202','1.704.333.1555');

INSERT INTO `Customer` VALUES
	(1001,'S0001','Tom Cruise','203 Park St','Raleigh','NC','27603','ethanhunt@gmail.com','123.456.7890','scientology'),
	(1002,'S0001','Robert Downey Jr.','12-A High St','Raleigh','NC','27605','jarvis@gmail.com','998.987.7791','iamironman'),
	(1003,'S0002','Taylor Swift','2A 3rd Ave','Charlotte','NC','28134','lovestory@gmail.com','817.982.7199','nevereverever'),
	(1004,'S0002','Serena Williams','90 Gorman St','Charlotte','NC','28201','venus@gmail.com','871.479.1879','wimbledon');

INSERT INTO `Vehicle` VALUES
	('AHS-3132','Toyota','Prius',2007,'2011-01-02',1001),
	('DEL-8888','Nissan','Rogue',2014,'2016-05-11',1003),
	('IRM-1212','Nissan','Altima',2001,'2002-09-07',1002),
	('P11-212A','Honda','Accord',2009,'2010-04-14',1004),
	('TSW-3462','Honda','Accord',2015,'2015-12-09',1003),
	('WIM-BLE5','Toyota','Prius',2013,'2014-03-01',1004),
	('XYZ-5643','Honda','Civic',2009,'2009-12-24',1001);

INSERT INTO `Employee` VALUES
	('S0001',183683346,'Anthony Freeman','1188 Summit Street','Raleigh','NC','27627','anfreeman@acme.com','563.496.7912','mechanic','2016-08-15',40.00,'password'),
	('S0002',187658163,'Rickie Henderson','1963 Chenoweth Drive','Charlotte','NC','28228','rihenderson@acme.com','931.539.1294','mechanic','2017-05-29',30.00,'password'),
	('S0002',291056276,'Roderick Phillips','1133 Burton Avenue','Charlotte','NC','28201','rophillips@acme.com','901.777.0492','manager','2017-04-15',12000.00,'password'),
	('S0002',310773348,'James Rivera','908 Alpha Avenue','Charlotte','NC','28130','jarivera@acme.com','903.967.1809','mechanic','2013-10-29',40.00,'password'),
	('S0002',401671897,'Charles Pudilo','196 Park Boulevard','Charlotte','NC','28222','chpudilo@acme.com','641.752.9403','mechanic','2016-07-06',40.00,'password'),
	('S0001',557279280,'Jacob Gloss','2014 Leverton Cove Road','Raleigh','NC','27560','jagloss@acme.com','413.335.9523','mechanic','2018-07-29',30.00,'password'),
	('S0001',557279281,'Eric Fowler','1114 Fincham Road','Raleigh','NC','27545','erfowler@acme.com','760.457.9846','mechanic','2016-05-01',35.00,'password'),
	('S0001',557279282,'Roland Richmond','1951 Little Acres Lane','Raleigh','NC','27513','rorichmond@acme.com','082.984.3373','mechanic','2017-12-28',30.00,'password'),
	('S0001',557279283,'Peter Fitzpatrick','4738 Buckhannan Avenue','Raleigh','NC','27625','pefitzpatrick@acme.com','315.485.1152','mechanic','2017-04-12',34.00,'password'),
	('S0002',590424694,'Dustin Esparza','3510 Hemlock Lane','Charlotte','NC','28202','duesparza@acme.com','956.444.0948','mechanic','2017-01-26',35.00,'password'),
	('S0001',634622236,'Willis Martin','465 Aviation Way','Raleigh','NC','27601','wimartin@acme.com','213.988.2011','receptionist','2017-12-04',8000.00,'password'),
	('S0002',911639633,'Dena Holmes','1382 Whispering Pines Circle','Charlotte','NC','28205','deholmes@acme.com','972.353.3691','receptionist','2010-01-04',9000.00,'password'),
	('S0001',950932130,'Larry Cohen','1979 Henry Street','Raleigh','NC','27606','lacohen@acme.com','316.978.5494','manager','2016-08-15',11000.00,'password'),
	('S0002',983204784,'Willis Morton','404 Tenmile','Charlotte','NC','28134','wimorton@acme.com','757.886.6330','mechanic','2013-10-13',30.00,'password');

INSERT INTO `Service` VALUES
	(1	 ,'Air filter change, Toyota Corolla'),
	(2	 ,'Air filter change, Toyota Prius'),
	(3	 ,'Air filter change, Honda Civic'),
	(4	 ,'Air filter change, Honda Accord'),
	(5	 ,'Air filter change, Nissan Altima'),
	(6	 ,'Air filter change, Nissan Rogue'),
	(7	 ,'Battery replacement, Toyota Corolla'),
	(8	 ,'Battery replacement, Toyota Prius'),
	(9	 ,'Battery replacement, Honda Civic'),
	(10 ,'Battery replacement, Honda Accord'),
	(11 ,'Battery replacement, Nissan Altima'),
	(12 ,'Battery replacement, Nissan Rogue'),
	(13 ,'Brake check, Toyota Corolla'),
	(14 ,'Brake check, Toyota Prius'),
	(15 ,'Brake check, Honda Civic'),
	(16 ,'Brake check, Honda Accord'),
	(17 ,'Brake check, Nissan Altima'),
	(18 ,'Brake check, Nissan Rogue'),
	(19 ,'Brake repair, Toyota Corolla'),
	(20 ,'Brake repair, Toyota Prius'),
	(21 ,'Brake repair, Honda Civic'),
	(22 ,'Brake repair, Honda Accord'),
	(23 ,'Brake repair, Nissan Altima'),
	(24 ,'Brake repair, Nissan Rogue'),
	(25 ,'Camshaft replacement, Toyota Corolla'),
	(26 ,'Camshaft replacement, Toyota Prius'),
	(27 ,'Camshaft replacement, Honda Civic'),
	(28 ,'Camshaft replacement, Honda Accord'),
	(29 ,'Camshaft replacement, Nissan Altima'),
	(30 ,'Camshaft replacement, Nissan Rogue'),
	(31 ,'Catalytic converter replacement, Toyota Corolla'),
	(32 ,'Catalytic converter replacement, Toyota Prius'),
	(33 ,'Catalytic converter replacement, Honda Civic'),
	(34 ,'Catalytic converter replacement, Honda Accord'),
	(35 ,'Catalytic converter replacement, Nissan Altima'),
	(36 ,'Catalytic converter replacement, Nissan Rogue'),
	(37 ,'Coolant recycle, Toyota Corolla'),
	(38 ,'Coolant recycle, Toyota Prius'),
	(39 ,'Coolant recycle, Honda Civic'),
	(40 ,'Coolant recycle, Honda Accord'),
	(41 ,'Coolant recycle, Nissan Altima'),
	(42 ,'Coolant recycle, Nissan Rogue'),
	(43 ,'Drive belt replacement, Toyota Corolla'),
	(44 ,'Drive belt replacement, Toyota Prius'),
	(45 ,'Drive belt replacement, Honda Civic'),
	(46 ,'Drive belt replacement, Honda Accord'),
	(47 ,'Drive belt replacement, Nissan Altima'),
	(48 ,'Drive belt replacement, Nissan Rogue'),
	(49 ,'Engine oil change, Toyota Corolla'),
	(50 ,'Engine oil change, Toyota Prius'),
	(51 ,'Engine oil change, Honda Civic'),
	(52 ,'Engine oil change, Honda Accord'),
	(53 ,'Engine oil change, Nissan Altima'),
	(54 ,'Engine oil change, Nissan Rogue'),
	(55 ,'Gearbox repair, Toyota Corolla'),
	(56 ,'Gearbox repair, Toyota Prius'),
	(57 ,'Gearbox repair, Honda Civic'),
	(58 ,'Gearbox repair, Honda Accord'),
	(59 ,'Gearbox repair, Nissan Altima'),
	(60 ,'Gearbox repair, Nissan Rogue'),
	(61 ,'Headlights replacement, Toyota Corolla'),
	(62 ,'Headlights replacement, Toyota Prius'),
	(63 ,'Headlights replacement, Honda Civic'),
	(64 ,'Headlights replacement, Honda Accord'),
	(65 ,'Headlights replacement, Nissan Altima'),
	(66 ,'Headlights replacement, Nissan Rogue'),
	(67 ,'Oil filter change, Toyota Corolla'),
	(68 ,'Oil filter change, Toyota Prius'),
	(69 ,'Oil filter change, Honda Civic'),
	(70 ,'Oil filter change, Honda Accord'),
	(71 ,'Oil filter change, Nissan Altima'),
	(72 ,'Oil filter change, Nissan Rogue'),
	(73 ,'Piston replacement, Toyota Corolla'),
	(74 ,'Piston replacement, Toyota Prius'),
	(75 ,'Piston replacement, Honda Civic'),
	(76 ,'Piston replacement, Honda Accord'),
	(77 ,'Piston replacement, Nissan Altima'),
	(78 ,'Piston replacement, Nissan Rogue'),
	(79 ,'Power steering check, Toyota Corolla'),
	(80 ,'Power steering check, Toyota Prius'),
	(81 ,'Power steering check, Honda Civic'),
	(82 ,'Power steering check, Honda Accord'),
	(83 ,'Power steering check, Nissan Altima'),
	(84 ,'Power steering check, Nissan Rogue'),
	(85 ,'Spark plugs replacement, Toyota Corolla'),
	(86 ,'Spark plugs replacement, Toyota Prius'),
	(87 ,'Spark plugs replacement, Honda Civic'),
	(88 ,'Spark plugs replacement, Honda Accord'),
	(89 ,'Spark plugs replacement, Nissan Altima'),
	(90 ,'Spark plugs replacement, Nissan Rogue'),
	(91 ,'Suspension check, Toyota Corolla'),
	(92 ,'Suspension check, Toyota Prius'),
	(93 ,'Suspension check, Honda Civic'),
	(94 ,'Suspension check, Honda Accord'),
	(95 ,'Suspension check, Nissan Altima'),
	(96 ,'Suspension check, Nissan Rogue'),
	(97 ,'Tail lights replacement, Toyota Corolla'),
	(98 ,'Tail lights replacement, Toyota Prius'),
	(99 ,'Tail lights replacement, Honda Civic'),
	(100,'Tail lights replacement, Honda Accord'),
	(101,'Tail lights replacement, Nissan Altima'),
	(102,'Tail lights replacement, Nissan Rogue'),
	(103,'Turn lights replacement, Toyota Corolla'),
	(104,'Turn lights replacement, Toyota Prius'),
	(105,'Turn lights replacement, Honda Civic'),
	(106,'Turn lights replacement, Honda Accord'),
	(107,'Turn lights replacement, Nissan Altima'),
	(108,'Turn lights replacement, Nissan Rogue'),
	(109,'Valve replacement, Toyota Corolla'),
	(110,'Valve replacement, Toyota Prius'),
	(111,'Valve replacement, Honda Civic'),
	(112,'Valve replacement, Honda Accord'),
	(113,'Valve replacement, Nissan Altima'),
	(114,'Valve replacement, Nissan Rogue'),
	(115,'Wheel alignment, Toyota Corolla'),
	(116,'Wheel alignment, Toyota Prius'),
	(117,'Wheel alignment, Honda Civic'),
	(118,'Wheel alignment, Honda Accord'),
	(119,'Wheel alignment, Nissan Altima'),
	(120,'Wheel alignment, Nissan Rogue'),
	(121,'Wiper check, Toyota Corolla'),
	(122,'Wiper check, Toyota Prius'),
	(123,'Wiper check, Honda Civic'),
	(124,'Wiper check, Honda Accord'),
	(125,'Wiper check, Nissan Altima'),
	(126,'Wiper check, Nissan Rogue'),
	(127,'Service A, Toyota Corolla'),
	(128,'Service A, Toyota Prius'),
	(129,'Service A, Honda Civic'),
	(130,'Service A, Honda Accord'),
	(131,'Service A, Nissan Altima'),
	(132,'Service A, Nissan Rogue'),
	(133,'Service B, Toyota Corolla'),
	(134,'Service B, Toyota Prius'),
	(135,'Service B, Honda Civic'),
	(136,'Service B, Honda Accord'),
	(137,'Service B, Nissan Altima'),
	(138,'Service B, Nissan Rogue'),
	(139,'Service C, Toyota Corolla'),
	(140,'Service C, Toyota Prius'),
	(141,'Service C, Honda Civic'),
	(142,'Service C, Honda Accord'),
	(143,'Service C, Nissan Altima'),
	(144,'Service C, Nissan Rogue');


INSERT INTO `ByMileage` VALUES
	(127, 5000,'Toyota','Corolla'),
	(133,25000,'Toyota','Corolla'),
	(139,45000,'Toyota','Corolla'),
	(128,10000,'Toyota',  'Prius'),
	(134,28000,'Toyota',  'Prius'),
	(140,58000,'Toyota',  'Prius'),
	(129,14000, 'Honda',  'Civic'),
	(135,29000, 'Honda',  'Civic'),
	(141,44000, 'Honda',  'Civic'),
	(130,15000, 'Honda', 'Accord'),
	(136,37000, 'Honda', 'Accord'),
	(142,67000, 'Honda', 'Accord'),
	(131,10000,'Nissan', 'Altima'),
	(137,25000,'Nissan', 'Altima'),
	(143,50000,'Nissan', 'Altima'),
	(132,10000,'Nissan',  'Rogue'),
	(138,37000,'Nissan',  'Rogue'),
	(144,70000,'Nissan',  'Rogue');

INSERT INTO `BillableUnit` VALUES
	(1	 ,50,'0.25'),
	(2	 ,50,'0.25'),
	(3	 ,50,'0.25'),
	(4	 ,50,'0.25'),
	(5	 ,50,'0.25'),
	(6	 ,50,'0.25'),
	(7	 ,50,'0.25'),
	(8	 ,50,'0.25'),
	(9	 ,50,'0.25'),
	(10 ,50,'0.25'),
	(11 ,50,'0.25'),
	(12 ,50,'0.25'),
	(13 ,50,'0.25'),
	(14 ,50,'0.25'),
	(15 ,50,'0.25'),
	(16 ,50,'0.25'),
	(17 ,50,'0.25'),
	(18 ,50,'0.25'),
	(19 ,50,'0.50'),
	(20 ,50,'0.50'),
	(21 ,50,'0.50'),
	(22 ,50,'0.50'),
	(23 ,50,'0.50'),
	(24 ,50,'0.50'),
	(25 ,65,'1.00'),
	(26 ,65,'1.00'),
	(27 ,65,'1.00'),
	(28 ,65,'1.00'),
	(29 ,65,'1.00'),
	(30 ,65,'1.00'),
	(31 ,65,'1.00'),
	(32 ,65,'1.00'),
	(33 ,65,'1.00'),
	(34 ,65,'1.00'),
	(35 ,65,'1.00'),
	(36 ,65,'1.00'),
	(37 ,50,'0.25'),
	(38 ,50,'0.25'),
	(39 ,50,'0.25'),
	(40 ,50,'0.25'),
	(41 ,50,'0.25'),
	(42 ,50,'0.25'),
	(43 ,65,'1.00'),
	(44 ,65,'1.00'),
	(45 ,65,'1.00'),
	(46 ,65,'1.00'),
	(47 ,65,'1.00'),
	(48 ,65,'1.00'),
	(49 ,50,'0.25'),
	(50 ,50,'0.25'),
	(51 ,50,'0.25'),
	(52 ,50,'0.25'),
	(53 ,50,'0.25'),
	(54 ,50,'0.25'),
	(55 ,65,'1.00'),
	(56 ,65,'1.00'),
	(57 ,65,'1.00'),
	(58 ,65,'1.00'),
	(59 ,65,'1.00'),
	(60 ,65,'1.00'),
	(61 ,50,'0.50'),
	(62 ,50,'0.50'),
	(63 ,50,'0.50'),
	(64 ,50,'0.50'),
	(65 ,50,'0.50'),
	(66 ,50,'0.50'),
	(67 ,50,'0.25'),
	(68 ,50,'0.25'),
	(69 ,50,'0.25'),
	(70 ,50,'0.25'),
	(71 ,50,'0.25'),
	(72 ,50,'0.25'),
	(73 ,65,'1.00'),
	(74 ,65,'1.00'),
	(75 ,65,'1.00'),
	(76 ,65,'1.00'),
	(77 ,65,'1.00'),
	(78 ,65,'1.00'),
	(79 ,50,'0.25'),
	(80 ,50,'0.25'),
	(81 ,50,'0.25'),
	(82 ,50,'0.25'),
	(83 ,50,'0.25'),
	(84 ,50,'0.25'),
	(85 ,50,'0.25'),
	(86 ,50,'0.25'),
	(87 ,50,'0.25'),
	(88 ,50,'0.25'),
	(89 ,50,'0.25'),
	(90 ,50,'0.25'),
	(91 ,50,'0.25'),
	(92 ,50,'0.25'),
	(93 ,50,'0.25'),
	(94 ,50,'0.25'),
	(95 ,50,'0.25'),
	(96 ,50,'0.25'),
	(97 ,50,'0.50'),
	(98 ,50,'0.50'),
	(99 ,50,'0.50'),
	(100,50,'0.50'),
	(101,50,'0.50'),
	(102,50,'0.50'),
	(103,50,'0.50'),
	(104,50,'0.50'),
	(105,50,'0.50'),
	(106,50,'0.50'),
	(107,50,'0.50'),
	(108,50,'0.50'),
	(109,65,'1.00'),
	(110,65,'1.00'),
	(111,65,'1.00'),
	(112,65,'1.00'),
	(113,65,'1.00'),
	(114,65,'1.00'),
	(115,65,'1.00'),
	(116,65,'1.00'),
	(117,65,'1.00'),
	(118,65,'1.00'),
	(119,65,'1.00'),
	(120,65,'1.00'),
	(121,50,'0.25'),
	(122,50,'0.25'),
	(123,50,'0.25'),
	(124,50,'0.25'),
	(125,50,'0.25'),
	(126,50,'0.25');

INSERT INTO `Includes` VALUES
	(127, 49),
	(127, 37),
	(133, 127),
	(133,  1),
	(133, 67),
	(133, 13),
	(133,121),
	(133, 85),
	(139, 133),
	(139, 91),
	(139, 19),
	(139, 79),
	(128, 50),
	(128, 38),
	(128, 68),
	(134, 128),
	(134,  2),
	(134, 14),
	(134,122),
	(134, 86),
	(134,  8),
	(140, 134),
	(140, 92),
	(140, 20),
	(140, 80),
	(129, 51),
	(129, 15),
	(129, 39),
	(135, 129),
	(135,  3),
	(135, 69),
	(135, 21),
	(135,123),
	(135, 87),
	(141, 135),
	(141, 93),
	(141, 81),
	(130, 52),
	(130,  4),
	(130, 70),
	(130, 40),
	(136, 130),
	(136, 52),
	(136, 16),
	(136,124),
	(136, 88),
	(142, 136),
	(142, 94),
	(142, 22),
	(142, 82),
	(131, 53),
	(131,  5),
	(131, 71),
	(131, 41),
	(137, 131),
	(137, 17),
	(137,125),
	(143, 137),
	(143, 95),
	(143, 23),
	(143, 83),
	(143, 89),
	(132, 54),
	(132,  6),
	(132, 72),
	(132, 42),
	(132, 84),
	(132, 18),
	(138, 132),
	(138, 96),
	(138,126),
	(138, 90),
	(144, 138),
	(144, 24),
	(144, 12);

INSERT INTO `PartDatabase` VALUES
	(1 ,'D0002','Toyota Air Filter',11,3),
	(2 ,'D0001','Toyota Axel Rod',123,5),
	(3 ,'D0001','Toyota Battery',63,4),
	(4 ,'D0002','Toyota Brake Fluid',24,4),
	(5 ,'D0002','Toyota Brake Shoes',62,5),
	(6 ,'D0001','Toyota Camshaft',1428,4),
	(7 ,'D0002','Toyota Catalytic converter',801,3),
	(8 ,'D0002','Toyota Coolant',63,4),
	(9 ,'D0002','Toyota Drive belt',528,2),
	(10,'D0001','Toyota Engine oil',63,2),
	(11,'D0001','Toyota Gears',523,5),
	(12,'D0001','Toyota Light assembly',617,3),
	(13,'D0001','Toyota Oil Filter',36,4),
	(14,'D0001','Toyota Piston',1256,2),
	(15,'D0001','Toyota Power Steering Fluid',17,5),
	(16,'D0002','Toyota Spark plug',64,5),
	(17,'D0002','Toyota Suspension fluid',70,3),
	(18,'D0001','Toyota Valve',1338,3),
	(19,'D0002','Toyota Wiper Fluid',28,3),
	(20,'D0002','Honda Air Filter',59,3),
	(21,'D0001','Honda Axel Rod',141,5),
	(22,'D0001','Honda Battery',79,4),
	(23,'D0002','Honda Brake Fluid',75,4),
	(24,'D0002','Honda Brake Shoes',41,5),
	(25,'D0001','Honda Camshaft',511,4),
	(26,'D0002','Honda Catalytic converter',716,3),
	(27,'D0002','Honda Coolant',10,4),
	(28,'D0002','Honda Drive belt',1443,2),
	(29,'D0001','Honda Engine oil',27,2),
	(30,'D0001','Honda Gears',1344,5),
	(31,'D0001','Honda Light assembly',1342,3),
	(32,'D0001','Honda Oil Filter',42,4),
	(33,'D0001','Honda Piston',1145,2),
	(34,'D0001','Honda Power Steering Fluid',24,5),
	(35,'D0002','Honda Spark plug',50,5),
	(36,'D0002','Honda Suspension fluid',77,3),
	(37,'D0001','Honda Valve',1338,3),
	(38,'D0002','Honda Wiper Fluid',56,3),
	(39,'D0002','Nissan Air Filter',61,3),
	(40,'D0001','Nissan Axel Rod',241,5),
	(41,'D0001','Nissan Battery',14,4),
	(42,'D0002','Nissan Brake Fluid',16,4),
	(43,'D0002','Nissan Brake Shoes',47,5),
	(44,'D0001','Nissan Camshaft',1295,4),
	(45,'D0002','Nissan Catalytic converter',589,3),
	(46,'D0002','Nissan Coolant',39,4),
	(47,'D0002','Nissan Drive belt',1084,2),
	(48,'D0001','Nissan Engine oil',14,2),
	(49,'D0001','Nissan Gears',1176,5),
	(50,'D0001','Nissan Light assembly',1367,3),
	(51,'D0001','Nissan Oil Filter',61,4),
	(52,'D0001','Nissan Piston',1341,2),
	(53,'D0001','Nissan Power Steering Fluid',20,5),
	(54,'D0002','Nissan Spark plug',11,5),
	(55,'D0002','Nissan Suspension fluid',32,3),
	(56,'D0001','Nissan Valve',1013,3),
	(57,'D0002','Nissan Wiper Fluid',31,3);

INSERT INTO `Warranty` VALUES
	(7	 ,6),
	(8	 ,6),
	(9	 ,3),
	(10 ,3),
	(11 ,3),
	(12 ,3),
	(19 ,2),
	(20 ,2),
	(21 ,3),
	(22 ,3),
	(23 ,1),
	(24 ,1),
	(25 ,3),
	(26 ,3),
	(27 ,2),
	(28 ,2),
	(29 ,1),
	(30 ,1),
	(31 ,1),
	(32 ,1),
	(33 ,2),
	(34 ,2),
	(35 ,1),
	(36 ,1),
	(43 ,1),
	(44 ,1),
	(45 ,1),
	(46 ,1),
	(47 ,3),
	(48 ,3),
	(55 ,1),
	(56 ,1),
	(57 ,3),
	(58 ,3),
	(59 ,3),
	(60 ,3),
	(61 ,2),
	(62 ,2),
	(63 ,2),
	(64 ,2),
	(65 ,2),
	(66 ,2),
	(73 ,1),
	(74 ,1),
	(75 ,1),
	(76 ,1),
	(77 ,3),
	(78 ,3),
	(85 ,2),
	(86 ,2),
	(87 ,2),
	(88 ,2),
	(89 ,1),
	(90 ,1),
	(97 ,2),
	(98 ,2),
	(99 ,2),
	(100,2),
	(101,2),
	(102,2),
	(103,2),
	(104,2),
	(105,2),
	(106,2),
	(107,2),
	(108,2),
	(109,1),
	(110,1),
	(111,2),
	(112,2),
	(113,1),
	(114,1),
	(115,2),
	(116,2),
	(117,2),
	(118,2),
	(119,3),
	(120,3);

INSERT INTO `Consumes` VALUES
	(1	 , 1, 1),
	(2	 , 1, 1),
	(3	 ,20, 1),
	(4	 ,20, 2),
	(5	 ,39, 1),
	(6	 ,39, 3),
	(7	 , 3, 1),
	(8	 , 3, 1),
	(9	 ,22, 1),
	(10 ,22, 1),
	(11 ,41, 1),
	(12 ,41, 2),
	(13 , 4, 1),
	(14 , 4, 1),
	(15 ,23, 1),
	(16 ,23, 1),
	(17 ,42, 1),
	(18 ,42, 2),
	(19 , 5, 4),
	(20 , 5, 4),
	(21 ,24, 4),
	(22 ,24, 4),
	(23 ,43, 4),
	(24 ,43, 4),
	(25 , 6, 1),
	(26 , 6, 1),
	(27 ,25, 1),
	(28 ,25, 1),
	(29 ,44, 1),
	(30 ,44, 2),
	(31 , 7, 1),
	(32 , 7, 1),
	(33 ,26, 1),
	(34 ,26, 1),
	(35 ,45, 1),
	(36 ,45, 1),
	(37 , 8, 1),
	(38 , 8, 1),
	(39 ,27, 1),
	(40 ,27, 1),
	(41 ,46, 2),
	(42 ,46, 2),
	(43 , 9, 1),
	(44 , 9, 1),
	(45 ,28, 1),
	(46 ,28, 1),
	(47 ,47, 1),
	(48 ,47, 1),
	(49 ,10, 1),
	(50 ,10, 1),
	(51 ,29, 1),
	(52 ,29, 1),
	(53 ,48, 2),
	(54 ,48, 3),
	(55 ,11, 6),
	(56 ,11, 6),
	(57 ,30,12),
	(58 ,30, 7),
	(59 ,49, 7),
	(60 ,49, 9),
	(61 ,12, 2),
	(62 ,12, 2),
	(63 ,31, 2),
	(64 ,31, 2),
	(65 ,50, 2),
	(66 ,50, 2),
	(67 ,13, 1),
	(68 ,13, 1),
	(69 ,32, 1),
	(70 ,32, 1),
	(71 ,51, 1),
	(72 ,51, 2),
	(73 ,14, 4),
	(74 ,14, 4),
	(75 ,33, 4),
	(76 ,33, 4),
	(77 ,52, 6),
	(78 ,52, 8),
	(79 ,15, 1),
	(80 ,15, 1),
	(81 ,34, 1),
	(82 ,34, 1),
	(83 ,53, 1),
	(84 ,53, 1),
	(85 ,16, 4),
	(86 ,16, 4),
	(87 ,35, 4),
	(88 ,35, 4),
	(89 ,54, 6),
	(90 ,54, 8),
	(91 ,17, 1),
	(92 ,17, 1),
	(93 ,36, 1),
	(94 ,36, 1),
	(95 ,55, 1),
	(96 ,55, 1),
	(97 ,12, 2),
	(98 ,12, 2),
	(99 ,31, 2),
	(100,31, 2),
	(101,50, 2),
	(102,50, 2),
	(103,12, 4),
	(104,12, 4),
	(105,31, 4),
	(106,31, 4),
	(107,50, 4),
	(108,50, 4),
	(109,18, 4),
	(110,18, 4),
	(111,37, 4),
	(112,37, 4),
	(113,56, 6),
	(114,56, 8),
	(115, 2, 2),
	(116, 2, 2),
	(117,21, 2),
	(118,21, 2),
	(119,40, 2),
	(120,40, 2),
	(121,19, 1),
	(122,19, 1),
	(123,38, 1),
	(124,38, 1),
	(125,57, 1),
	(126,57, 1);

INSERT INTO `Fault` VALUES
	('Engine knock'                        ,75),
	('Car drifts in a particular direction',50),
	('Battery does not hold charge'        ,25),
	('Black/unclean exhaust'               ,75),
	('A/C-Heater not working'              ,50),
	('Headlamps/Tail lamps not working'    ,30),
	('Check engine light'                  ,100);

INSERT INTO `Recommends` VALUES
	('Engine knock','Toyota','Corolla'                        , 43),
	('Engine knock','Honda','Civic'                        , 45),
	('Engine knock','Nissan','Altima'                        , 47),
	('Engine knock','Toyota','Prius'                        , 44),
	('Engine knock','Honda','Accord'                        , 46),
	('Engine knock','Nissan','Rogue'                        , 48),
	('Engine knock','Toyota','Corolla'                        ,85),
	('Engine knock','Honda','Civic'                        ,87),
	('Engine knock','Nissan','Altima'                        ,89),
	('Engine knock','Toyota','Prius'                        ,86),
	('Engine knock','Honda','Accord'                        ,88),
	('Engine knock','Nissan','Rogue'                        ,90),
	('Engine knock','Toyota','Corolla'                        , 25),
	('Engine knock','Honda','Civic'                        , 27),
	('Engine knock','Nissan','Altima'                        , 29),
	('Engine knock','Toyota','Prius'                        , 26),
	('Engine knock','Honda','Accord'                        , 28),
	('Engine knock','Nissan','Rogue'                        , 30),
	('Engine knock','Toyota','Corolla'                        ,109),
	('Engine knock','Honda','Civic'                        ,111),
	('Engine knock','Nissan','Altima'                        ,113),
	('Engine knock','Toyota','Prius'                        ,110),
	('Engine knock','Honda','Accord'                        ,112),
	('Engine knock','Nissan','Rogue'                        ,114),
	('Car drifts in a particular direction','Toyota','Corolla',115),
	('Car drifts in a particular direction','Honda','Civic',117),
	('Car drifts in a particular direction','Nissan','Altima',119),
	('Car drifts in a particular direction','Toyota','Prius',116),
	('Car drifts in a particular direction','Honda','Accord',118),
	('Car drifts in a particular direction','Nissan','Rogue',120),
	('Battery does not hold charge','Toyota','Corolla'        , 7),
	('Battery does not hold charge','Honda','Civic'        , 9),
	('Battery does not hold charge','Nissan','Altima'        , 11),
	('Battery does not hold charge','Toyota','Prius'        , 8),
	('Battery does not hold charge','Honda','Accord'        , 10),
	('Battery does not hold charge','Nissan','Rogue'        , 12),
	('Black/unclean exhaust','Toyota','Corolla'               , 1),
	('Black/unclean exhaust','Honda','Civic'               , 3),
	('Black/unclean exhaust','Nissan','Altima'               , 5),
	('Black/unclean exhaust','Toyota','Prius'               , 2),
	('Black/unclean exhaust','Honda','Accord'               , 4),
	('Black/unclean exhaust','Nissan','Rogue'               , 6),
	('Black/unclean exhaust','Toyota','Corolla'               ,67),
	('Black/unclean exhaust','Honda','Civic'               ,69),
	('Black/unclean exhaust','Nissan','Altima'               ,71),
	('Black/unclean exhaust','Toyota','Prius'               ,68),
	('Black/unclean exhaust','Honda','Accord'               ,70),
	('Black/unclean exhaust','Nissan','Rogue'               ,72),
	('Black/unclean exhaust','Toyota','Corolla'               , 31),
	('Black/unclean exhaust','Honda','Civic'               , 33),
	('Black/unclean exhaust','Nissan','Altima'               , 35),
	('Black/unclean exhaust','Toyota','Prius'               , 32),
	('Black/unclean exhaust','Honda','Accord'               , 34),
	('Black/unclean exhaust','Nissan','Rogue'               , 36),
	('A/C-Heater not working','Toyota','Corolla'              , 43),
	('A/C-Heater not working','Honda','Civic'              , 45),
	('A/C-Heater not working','Nissan','Altima'              , 47),
	('A/C-Heater not working','Toyota','Prius'              , 44),
	('A/C-Heater not working','Honda','Accord'              , 46),
	('A/C-Heater not working','Nissan','Rogue'              , 48),
	('A/C-Heater not working','Toyota','Corolla'              , 37),
	('A/C-Heater not working','Honda','Civic'              , 39),
	('A/C-Heater not working','Nissan','Altima'              , 41),
	('A/C-Heater not working','Toyota','Prius'              , 38),
	('A/C-Heater not working','Honda','Accord'              , 40),
	('A/C-Heater not working','Nissan','Rogue'              , 42),
	('A/C-Heater not working','Toyota','Corolla'              , 7),
	('A/C-Heater not working','Honda','Civic'              , 9),
	('A/C-Heater not working','Nissan','Altima'              , 11),
	('A/C-Heater not working','Toyota','Prius'              , 8),
	('A/C-Heater not working','Honda','Accord'              , 10),
	('A/C-Heater not working','Nissan','Rogue'              , 12),
	('Headlamps/Tail lamps not working','Toyota','Corolla'    ,61),
	('Headlamps/Tail lamps not working','Honda','Civic'    ,63),
	('Headlamps/Tail lamps not working','Nissan','Altima'    ,65),
	('Headlamps/Tail lamps not working','Toyota','Prius'    ,62),
	('Headlamps/Tail lamps not working','Honda','Accord'    ,64),
	('Headlamps/Tail lamps not working','Nissan','Rogue'    ,66),
	('Headlamps/Tail lamps not working','Toyota','Corolla'    ,97),
	('Headlamps/Tail lamps not working','Honda','Civic'    ,99),
	('Headlamps/Tail lamps not working','Nissan','Altima'    ,101),
	('Headlamps/Tail lamps not working','Toyota','Prius'    ,98),
	('Headlamps/Tail lamps not working','Honda','Accord'    ,100),
	('Headlamps/Tail lamps not working','Nissan','Rogue'    ,102),
	('Headlamps/Tail lamps not working','Toyota','Corolla'    ,103),
	('Headlamps/Tail lamps not working','Honda','Civic'    ,105),
	('Headlamps/Tail lamps not working','Nissan','Altima'    ,107),
	('Headlamps/Tail lamps not working','Toyota','Prius'    ,104),
	('Headlamps/Tail lamps not working','Honda','Accord'    ,106),
	('Headlamps/Tail lamps not working','Nissan','Rogue'    ,108),
	('Check engine light','Toyota','Corolla'                  ,73),
	('Check engine light','Honda','Civic'                  ,75),
	('Check engine light','Nissan','Altima'                  ,77),
	('Check engine light','Toyota','Prius'                  ,74),
	('Check engine light','Honda','Accord'                  ,76),
	('Check engine light','Nissan','Rogue'                  ,78),
	('Check engine light','Toyota','Corolla'                  ,55),
	('Check engine light','Honda','Civic'                  ,57),
	('Check engine light','Nissan','Altima'                  ,59),
	('Check engine light','Toyota','Prius'                  ,56),
	('Check engine light','Honda','Accord'                  ,58),
	('Check engine light','Nissan','Rogue'                  ,60),
	('Check engine light','Toyota','Corolla'                  , 25),
	('Check engine light','Honda','Civic'                  , 27),
	('Check engine light','Nissan','Altima'                  , 29),
	('Check engine light','Toyota','Prius'                  , 26),
	('Check engine light','Honda','Accord'                  , 28),
	('Check engine light','Nissan','Rogue'                  , 30),
	('Check engine light','Toyota','Corolla'                  ,109),
	('Check engine light','Honda','Civic'                  ,111),
	('Check engine light','Nissan','Altima'                  ,113),
	('Check engine light','Toyota','Prius'                  ,110),
	('Check engine light','Honda','Accord'                  ,112),
	('Check engine light','Nissan','Rogue'                  ,114);

INSERT INTO `ServiceAppointment` VALUES
	('XYZ-5643', '2018-09-10', NULL, 557279280, 90452),
	('XYZ-5643', '2018-02-25', NULL, 557279281, 46452),
	('XYZ-5643', '2017-10-15', NULL, 183683346, 17452),
	('AHS-3132', '2018-08-06', 'Battery does not hold charge', 557279282, 65452),
	('AHS-3132', '2018-05-05', NULL, 557279283, 65452),
	('AHS-3132', '2018-01-28', NULL, 557279283, 37452),
	('IRM-1212', '2018-02-11', NULL, 557279281, 210452),
	('IRM-1212', '2017-12-10', NULL, 557279281, 200452),
	('IRM-1212', '2017-01-20', NULL, 557279281, 150452),
	('DEL-8888', '2018-02-11', NULL, 187658163, 31209),
	('DEL-8888', '2016-11-05', 'Headlamps/Tail lamps not working', 401671897, 21209),
	('P11-212A', '2017-09-01', NULL, 590424694, 60452),
	('P11-212A', '2014-06-05', NULL, 310773348, 23452),
	('WIM-BLE5', '2016-11-11', NULL, 310773348, 19876),
	('WIM-BLE5', '2016-01-02', 'A/C-Heater not working', 401671897, 9876),
	('WIM-BLE5', '2015-07-30' 	, 'Engine knock', 401671897, 9876);

INSERT INTO `Schedules` VALUES
	('XYZ-5643', '2018-09-10', '10:00', 557279280, 81),
	('XYZ-5643', '2018-09-10', '10:15', 557279280, 93),
	('XYZ-5643', '2018-09-10', '10:30', 557279280, 3),
	('XYZ-5643', '2018-09-10', '10:45', 557279280, 21),
	('XYZ-5643', '2018-09-10', '11:00', 557279280, 21),
	('XYZ-5643', '2018-09-10', '11:15', 557279280, 69),
	('XYZ-5643', '2018-09-10', '11:30', 557279280, 87),
	('XYZ-5643', '2018-09-10', '11:45', 557279280, 123),
	('XYZ-5643', '2018-09-10', '12:00', 557279280, 15),
	('XYZ-5643', '2018-09-10', '12:15', 557279280, 39),
	('XYZ-5643', '2018-09-10', '12:30', 557279280, 51),
	('XYZ-5643', '2018-02-25', '09:00', 557279281, 3),
	('XYZ-5643', '2018-02-25', '09:15', 557279281, 21),
	('XYZ-5643', '2018-02-25', '09:30', 557279281, 21),
	('XYZ-5643', '2018-02-25', '09:45', 557279281, 69),
	('XYZ-5643', '2018-02-25', '10:00', 557279281, 87),
	('XYZ-5643', '2018-02-25', '10:15', 557279281, 123),
	('XYZ-5643', '2018-02-25', '10:30', 557279281, 15),
	('XYZ-5643', '2018-02-25', '10:45', 557279281, 39),
	('XYZ-5643', '2018-02-25', '11:00', 557279281, 51),
	('XYZ-5643', '2017-10-15', '08:00', 183683346, 15),
	('XYZ-5643', '2017-10-15', '08:15', 183683346, 39),
	('XYZ-5643', '2017-10-15', '08:30', 183683346, 51),
	('WIM-BLE5', '2016-11-11', '08:30', 310773348, 38),
	('WIM-BLE5', '2016-11-11', '08:45', 310773348, 50),
	('WIM-BLE5', '2016-11-11', '09:00', 310773348, 68),
	('WIM-BLE5', '2016-01-02', '14:00', 401671897, 8),
	('WIM-BLE5', '2016-01-02', '14:15', 401671897, 38),
	('WIM-BLE5', '2016-01-02', '14:30', 401671897, 44),
	('WIM-BLE5', '2016-01-02', '14:45', 401671897, 44),
	('WIM-BLE5', '2016-01-02', '15:00', 401671897, 44),
	('WIM-BLE5', '2016-01-02', '15:15', 401671897, 44),
	('WIM-BLE5', '2015-07-30', '11:00', 401671897, 26),
	('WIM-BLE5', '2015-07-30', '11:15', 401671897, 26),
	('WIM-BLE5', '2015-07-30', '11:30', 401671897, 26),
	('WIM-BLE5', '2015-07-30', '11:45', 401671897, 26),
	('WIM-BLE5', '2015-07-30', '12:00', 401671897, 44),
	('WIM-BLE5', '2015-07-30', '12:15', 401671897, 44),
	('WIM-BLE5', '2015-07-30', '12:30', 401671897, 44),
	('WIM-BLE5', '2015-07-30', '12:45', 401671897, 44),
	('WIM-BLE5', '2015-07-30', '13:00', 401671897, 86),
	('WIM-BLE5', '2015-07-30', '13:15', 401671897, 110),
	('WIM-BLE5', '2015-07-30', '13:30', 401671897, 110),
	('WIM-BLE5', '2015-07-30', '13:45', 401671897, 110),
	('WIM-BLE5', '2015-07-30', '14:00', 401671897, 110),
	('P11-212A', '2017-09-01', '09:00', 590424694, 16),
	('P11-212A', '2017-09-01', '09:15', 590424694, 52),
	('P11-212A', '2017-09-01', '09:30', 590424694, 88),
	('P11-212A', '2017-09-01', '09:45', 590424694, 124),
	('P11-212A', '2017-09-01', '10:00', 590424694, 4),
	('P11-212A', '2017-09-01', '10:15', 590424694, 40),
	('P11-212A', '2017-09-01', '10:30', 590424694, 52),
	('P11-212A', '2017-09-01', '10:45', 590424694, 70),
	('P11-212A', '2014-06-05', '08:30', 310773348, 4),
	('P11-212A', '2014-06-05', '08:45', 310773348, 40),
	('P11-212A', '2014-06-05', '09:00', 310773348, 52),
	('P11-212A', '2014-06-05', '09:15', 310773348, 70),
	('IRM-1212', '2018-02-11', '08:30', 557279281, 5),
	('IRM-1212', '2018-02-11', '08:45', 557279281, 41),
	('IRM-1212', '2018-02-11', '09:00', 557279281, 53),
	('IRM-1212', '2018-02-11', '09:15', 557279281, 71),
	('IRM-1212', '2017-12-10', '09:30', 557279281, 23),
	('IRM-1212', '2017-12-10', '09:45', 557279281, 23),
	('IRM-1212', '2017-12-10', '10:00', 557279281, 83),
	('IRM-1212', '2017-12-10', '10:15', 557279281, 89),
	('IRM-1212', '2017-12-10', '10:30', 557279281, 95),
	('IRM-1212', '2017-12-10', '10:45', 557279281, 17),
	('IRM-1212', '2017-12-10', '11:00', 557279281, 125),
	('IRM-1212', '2017-12-10', '11:15', 557279281, 5),
	('IRM-1212', '2017-12-10', '11:30', 557279281, 41),
	('IRM-1212', '2017-12-10', '11:45', 557279281, 53),
	('IRM-1212', '2017-12-10', '12:00', 557279281, 71),
	('IRM-1212', '2017-01-20', '10:00', 557279281, 17),
	('IRM-1212', '2017-01-20', '10:15', 557279281, 125),
	('IRM-1212', '2017-01-20', '10:30', 557279281, 5),
	('IRM-1212', '2017-01-20', '10:45', 557279281, 41),
	('IRM-1212', '2017-01-20', '11:00', 557279281, 53),
	('IRM-1212', '2017-01-20', '11:15', 557279281, 71),
	('DEL-8888', '2018-02-11', '08:30', 187658163, 6),
	('DEL-8888', '2018-02-11', '08:45', 187658163, 18),
	('DEL-8888', '2018-02-11', '09:00', 187658163, 42),
	('DEL-8888', '2018-02-11', '09:15', 187658163, 54),
	('DEL-8888', '2018-02-11', '09:30', 187658163, 72),
	('DEL-8888', '2018-02-11', '09:45', 187658163, 84),
	('DEL-8888', '2016-11-05', '09:00', 401671897, 66),
	('DEL-8888', '2016-11-05', '09:15', 401671897, 66),
	('DEL-8888', '2016-11-05', '09:30', 401671897, 102),
	('DEL-8888', '2016-11-05', '09:45', 401671897, 102),
	('DEL-8888', '2016-11-05', '10:00', 401671897, 108),
	('DEL-8888', '2016-11-05', '10:15', 401671897, 108),
	('AHS-3132', '2018-08-06', '08:00', 557279282, 8),
	('AHS-3132', '2018-05-05', '10:30', 557279283, 2),
	('AHS-3132', '2018-05-05', '10:45', 557279283, 8),
	('AHS-3132', '2018-05-05', '11:00', 557279283, 14),
	('AHS-3132', '2018-05-05', '11:15', 557279283, 86),
	('AHS-3132', '2018-05-05', '11:30', 557279283, 122),
	('AHS-3132', '2018-05-05', '11:45', 557279283, 38),
	('AHS-3132', '2018-05-05', '12:00', 557279283, 50),
	('AHS-3132', '2018-05-05', '12:15', 557279283, 68),
	('AHS-3132', '2018-01-28', '12:00', 557279283, 38),
	('AHS-3132', '2018-01-28', '12:15', 557279283, 50),
	('AHS-3132', '2018-01-28', '12:30', 557279283, 68);

INSERT INTO `PartInventory` VALUES
	(1 ,'S0001',13, 2, 5),
	(1 ,'S0002',26, 5, 6),
	(2 ,'S0001',18, 5, 7),
	(2 ,'S0002',27, 6,10),
	(3 ,'S0001',10, 2, 5),
	(3 ,'S0002',24, 2, 3),
	(4 ,'S0001', 4, 4, 6),
	(4 ,'S0002',25, 3, 3),
	(5 ,'S0001',18, 4, 3),
	(5 ,'S0002',20, 3, 5),
	(6 ,'S0001',12, 2, 5),
	(6 ,'S0002',22, 2, 6),
	(7 ,'S0001',11, 1, 5),
	(7 ,'S0002',29, 4, 5),
	(8 ,'S0001',13, 2, 4),
	(8 ,'S0002',21, 2, 4),
	(9 ,'S0001',15, 3, 5),
	(9 ,'S0002',20, 3, 4),
	(10,'S0001', 5, 5, 2),
	(10,'S0002',26, 5, 2),
	(11,'S0001', 8, 3, 5),
	(11,'S0002',20, 7, 5),
	(12,'S0001', 2, 1, 8),
	(12,'S0002',28, 6, 3),
	(13,'S0001', 1, 1, 4),
	(13,'S0002',21, 4, 5),
	(14,'S0001',15, 4, 2),
	(14,'S0002',21, 3, 5),
	(15,'S0001', 2, 1, 5),
	(15,'S0002',27, 6, 4),
	(16,'S0001',15, 4, 5),
	(16,'S0002',20, 2, 4),
	(17,'S0001', 3, 1, 5),
	(17,'S0002',25, 6, 6),
	(18,'S0001',12, 7, 5),
	(18,'S0002',11, 3, 6),
	(19,'S0001',12, 5, 5),
	(19,'S0002',16,13, 5),
	(20,'S0001',43,20, 5),
	(20,'S0002',46,35, 6),
	(21,'S0001', 8, 5, 7),
	(21,'S0002', 7, 6,10),
	(22,'S0001',20,20, 5),
	(22,'S0002',64,52, 3),
	(23,'S0001',14, 4, 6),
	(23,'S0002',15, 3, 3),
	(24,'S0001', 8, 4, 3),
	(24,'S0002',20, 3, 5),
	(25,'S0001',22,20, 5),
	(25,'S0002',42,42, 6),
	(26,'S0001',31,10, 5),
	(26,'S0002',29,24, 5),
	(27,'S0001',23,23, 4),
	(27,'S0002',21,12, 4),
	(28,'S0001',35,30, 5),
	(28,'S0002',30,23, 4),
	(29,'S0001',15, 5, 2),
	(29,'S0002',26, 5, 2),
	(30,'S0001',18, 3, 5),
	(30,'S0002',70, 7, 5),
	(31,'S0001',12,10, 8),
	(31,'S0002', 8, 6, 3),
	(32,'S0001',11, 4, 4),
	(32,'S0002',31,24, 5),
	(33,'S0001',55,48, 2),
	(33,'S0002',81,73, 5),
	(34,'S0001',12, 7, 5),
	(34,'S0002',17, 6, 4),
	(35,'S0001',45,42, 5),
	(35,'S0002',50,32, 4),
	(36,'S0001',13,11, 5),
	(36,'S0002',15, 6, 6),
	(37,'S0001',12, 7, 5),
	(37,'S0002',11, 3, 6),
	(38,'S0001',12, 5, 5),
	(38,'S0002',16,13, 5),
	(39,'S0001',33,20, 5),
	(39,'S0002',16, 5, 6),
	(40,'S0001',38, 5, 7),
	(40,'S0002',17, 6,10),
	(41,'S0001',30,20, 5),
	(41,'S0002',14, 5, 3),
	(42,'S0001',34, 4, 6),
	(42,'S0002',15, 3, 3),
	(43,'S0001',38, 4, 3),
	(43,'S0002',10, 3, 5),
	(44,'S0001',32,20, 5),
	(44,'S0002',12, 4, 6),
	(45,'S0001',31,10, 5),
	(45,'S0002',19, 4, 5),
	(46,'S0001',33,23, 4),
	(46,'S0002',11, 2, 4),
	(47,'S0001',35,30, 5),
	(47,'S0002',10, 3, 4),
	(48,'S0001',35, 5, 2),
	(48,'S0002',16, 5, 2),
	(49,'S0001',38, 3, 5),
	(49,'S0002',10, 7, 5),
	(50,'S0001',32,10, 8),
	(50,'S0002',18, 6, 3),
	(51,'S0001',31, 4, 4),
	(51,'S0002',11, 4, 5),
	(52,'S0001',35,18, 2),
	(52,'S0002',11, 7, 5),
	(53,'S0001',32, 7, 5),
	(53,'S0002',17, 6, 4),
	(54,'S0001',35,12, 5),
	(54,'S0002',10, 2, 4),
	(55,'S0001',33,11, 5),
	(55,'S0002',15, 6, 6),
	(56,'S0001',32, 7, 5),
	(56,'S0002',11, 3, 6),
	(57,'S0001',12, 5, 5),
	(57,'S0002',16,13, 5);

INSERT INTO `PartOrders` VALUES
	(1,'2014-06-09','2014-06-13','2014-06-18',3,5,'D0001','S0001','complete'),
	(2,'2015-09-16','2015-09-21','2015-09-21',38,5,'D0002','S0001','complete'),
	(3,'2016-02-10','2016-02-11','2016-02-11',30,5,'S0001','S0002','complete'),
	(4,'2017-08-09','2017-08-10','2017-08-11',46,4,'S0002','S0001','complete'),
	(5,'2018-10-04','2018-10-05','2018-08-05',1,6,'S0001','S0002','complete'),
	(6,'2018-10-26','2018-11-01','2018-11-05',44,5,'D0001','S0001','complete'),
	(7,'2018-11-09','2018-11-14',NULL,38,7,'D0002','S0002','pending'),
	(8,'2018-11-07','2018-11-14',NULL,21,12,'D0001','S0002','pending'),
	(9,'2018-11-08','2018-11-14',NULL,25,6,'D0001','S0002','pending'),
	(10,'2018-11-08','2018-11-14',NULL,32,5,'D0001','S0002','pending'),
	(11,'2018-11-08','2018-11-14',NULL,23,5,'D0002','S0002','pending');
