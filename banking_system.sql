CREATE DATABASE OOP_priti; 
USE OOP_priti; 
 
 
    CREATE TABLE accounts 
    ( 
    acc_id INT PRIMARY KEY AUTO_INCREMENT, 
    name VARCHAR(100), 
    balance DOUBLE, 
    daily_limit DOUBLE 
    ); 
 
 
SHOW TABLES; 
DESCRIBE accounts;
