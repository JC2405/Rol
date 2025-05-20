-- Script de creación de base de datos para la aplicación de gestión de roles

-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS roles_db;
USE roles_db;

-- Tabla de usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    rol ENUM('Instructor', 'Aprendiz') NOT NULL,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de aprendices
CREATE TABLE IF NOT EXISTS aprendices (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cedula VARCHAR(20) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(200) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insertar datos de prueba para usuarios
INSERT INTO usuarios (username, password, email, rol) VALUES
('instructor1', 'password123', 'instructor1@ejemplo.com', 'Instructor'),
('aprendiz1', 'password123', 'aprendiz1@ejemplo.com', 'Aprendiz');

-- Insertar datos de prueba para aprendices
INSERT INTO aprendices (cedula, nombre, direccion, email) VALUES
('1234567890', 'Juan Pérez', 'Calle 123 #45-67, Bogotá', 'juan.perez@ejemplo.com'),
('0987654321', 'María López', 'Carrera 78 #90-12, Medellín', 'maria.lopez@ejemplo.com'),
('5678901234', 'Carlos Rodríguez', 'Avenida 34 #56-78, Cali', 'carlos.rodriguez@ejemplo.com');