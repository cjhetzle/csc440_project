# CSC440-Team-Croesus
Database system for the Acme Car Service and Repair Centers.

Created by:
- Mac Chaffee (machaffe)
- Cam Hetzler (cjheztle)
- Daniel Kenline (dskenlin)
- Danny Mickens (dsmicken)


## Step 1: Installation

> NOTE: OUR PROJECT USES MYSQL INSTEAD OF ORACLE. 
We found that developing in MySQL was easier than in Oracle.

1. Ensure you have [Java 8](https://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html) installed
2. Install [mysql-server 8](https://dev.mysql.com/downloads/windows/installer/8.0.html)
3. Run the installer *without changing any of the default choices*
4. Set the MySQL root password and **remember it** for later

## Step 2: Creating the database (Windows)
1. After installation, open a MySQL shell by running these commands in `cmd`:
    ```
    cd C:\Program Files\MySQL\MySQL Server 8.0\bin
    mysql.exe -u root -p
    ```
2. When prompted, enter your root password, then run these commands:
    ```
    CREATE DATABASE cars;
    CREATE USER acme IDENTIFIED BY 'croesus';
    GRANT ALL PRIVILEGES ON cars.* TO acme;
    SET GLOBAL log_bin_trust_function_creators=1;
    ```
    
## Step 3: Creating the schema and sample data
1. After creating the database, Execute `java -jar Reset.jar` in your command prompt window

> Reset.jar will drop and recreate all tables, change some
MySQL config, and run load_sample_data.sql

## Step 4: Run the application
1. Execute `java -jar Application.jar` in your command prompt window

_____________________________

## Development
> Note: Make sure you have already completed "Step 1: Installation" and "Step 2: Creating the database"
before starting these instructions

1. Import the project into Eclipse
2. Go to Project > Properties > Java Build Path (or File > Project Structure > Libraries in IntelliJ)
3. In the Libraries tab, click "Add external JARs"
4. Select `lib/mysql-connector-java-8.0.13.jar` and `lib/gson-2.8.5.jar`
5. Run `src/main/java/backend/Reset.java` to create the schema and populate the database
6. If it worked, you should be able to run `src/main/java/ui/UI.java`

_____________________________

## Deviations from recommended application flow

### Sign Up Page
- Since customers are required to be registered with a service center, added a prompt for service center ID

### Login Page
- Error message says "User ID or password was invalid. Please try again" instead of "Login Incorrect"

### NewCarModel
- The service name, price, hours of service, and quantity of required part have all been added to the display for prompting.

### ManagerServiceDetails
- The basic services under Service A, B, and C are displayed by name. Months, part list, and year are not displayed. 
