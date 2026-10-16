-- Script de inicialización de MySQL para entorno de producción (Docker)
-- Se ejecuta automáticamente al iniciar el contenedor MySQL

CREATE DATABASE IF NOT EXISTS cursosdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS alumnosdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS administraciondb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS securitydb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Usuario que usa el config server en los archivos *-docker.yml
CREATE USER IF NOT EXISTS 'Escuela'@'%' IDENTIFIED BY 'escuela123';
GRANT ALL PRIVILEGES ON cursosdb.* TO 'Escuela'@'%';
GRANT ALL PRIVILEGES ON alumnosdb.* TO 'Escuela'@'%';
GRANT ALL PRIVILEGES ON administraciondb.* TO 'Escuela'@'%';
GRANT ALL PRIVILEGES ON securitydb.* TO 'Escuela'@'%';
FLUSH PRIVILEGES;
