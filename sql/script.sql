

CREATE DATABASE ecommerce;
USE ecommerce;

CREATE TABLE department (
  Id int(11) NOT NULL AUTO_INCREMENT,
  Name varchar(60) NULL,
  PRIMARY KEY (Id)
);

CREATE TABLE seller (
  Id int(11) NOT NULL AUTO_INCREMENT,
  Name varchar(60) NOT NULL,
  Email varchar(100) NOT NULL,
  BirthDate datetime NOT NULL,
  BaseSalary double NOT NULL,
  DepartmentId int(11) NOT NULL,
  PRIMARY KEY (Id),
  FOREIGN KEY (DepartmentId) REFERENCES department (id)
);

INSERT INTO department (Name) VALUES 
  ('Computers'),
  ('Electronics'),
  ('Fashion'),
  ('Books'),
  ('Sports');

INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) VALUES 
  ('Eva Garcia', 'eva.garcia@example.com', '1993-07-18', 58000.00, 2),
  ('Carlos Rodriguez', 'carlos.rodriguez@example.com', '1987-02-28', 65000.00, 3),
  ('Sophie Wilson', 'sophie.wilson@example.com', '1995-09-12', 53000.00, 4),
  ('Michael White', 'michael.white@example.com', '1990-12-08', 60000.00, 1),
  ('John Doe', 'john.doe@example.com', '1990-05-15', 50000.00, 1),
  ('Jane Smith', 'jane.smith@example.com', '1985-08-22', 60000.00, 2),
  ('Bob Johnson', 'bob.johnson@example.com', '1992-11-10', 55000.00, 5),
  ('Alice Brown', 'alice.brown@example.com', '1988-04-05', 52000.00, 3);
