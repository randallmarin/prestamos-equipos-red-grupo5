-- P07 | Grupo 5 | CIB02 | SQL Server
-- Compatible por revision documental con P03 v1.0 y P04 v1.1; pendiente de aprobacion grupal y ejecucion.
-- Ejecutar en SQL Server Management Studio (SSMS).
USE master;
GO
IF DB_ID(N'PrestamosEquiposRed') IS NULL
    CREATE DATABASE PrestamosEquiposRed;
GO
USE PrestamosEquiposRed;
GO
SET ANSI_NULLS ON;
SET QUOTED_IDENTIFIER ON;
SET ANSI_PADDING ON;
SET ANSI_WARNINGS ON;
SET CONCAT_NULL_YIELDS_NULL ON;
SET ARITHABORT ON;
SET NUMERIC_ROUNDABORT OFF;
GO
-- No se eliminan tablas ni datos existentes. Ejecutar sobre una BD nueva.
SET XACT_ABORT ON;
BEGIN TRY
BEGIN TRANSACTION;
CREATE TABLE dbo.Usuario (
    id_usuario INT IDENTITY(1,1) PRIMARY KEY,
    nombre_usuario NVARCHAR(50) NOT NULL UNIQUE,
    password_hash NVARCHAR(255) NOT NULL,
    rol NVARCHAR(20) NOT NULL CHECK (rol IN (N'ADMINISTRADOR', N'ENCARGADO')),
    activo BIT NOT NULL DEFAULT 1
);
CREATE TABLE dbo.Estudiante (
    id_estudiante INT IDENTITY(1,1) PRIMARY KEY,
    codigo_universitario NVARCHAR(20) NOT NULL UNIQUE,
    nombres NVARCHAR(100) NOT NULL,
    apellidos NVARCHAR(100) NOT NULL
);
CREATE TABLE dbo.Equipo (
    id_equipo INT IDENTITY(1,1) PRIMARY KEY,
    codigo_inventario NVARCHAR(30) NOT NULL UNIQUE,
    tipo NVARCHAR(30) NOT NULL CHECK (tipo IN (N'ROUTER', N'SWITCH', N'PUNTO_ACCESO')),
    marca NVARCHAR(50) NOT NULL,
    modelo NVARCHAR(80) NOT NULL,
    numero_serie NVARCHAR(80) NULL,
    ubicacion NVARCHAR(100) NULL,
    estado_fisico NVARCHAR(20) NOT NULL CHECK (estado_fisico IN (N'OPERATIVO', N'DANADO', N'MANTENIMIENTO')),
    observaciones NVARCHAR(500) NULL,
    activo BIT NOT NULL DEFAULT 1
);
CREATE TABLE dbo.Prestamo (
    id_prestamo INT IDENTITY(1,1) PRIMARY KEY,
    id_estudiante INT NOT NULL REFERENCES dbo.Estudiante(id_estudiante),
    id_equipo INT NOT NULL REFERENCES dbo.Equipo(id_equipo),
    id_usuario_prestamo INT NOT NULL REFERENCES dbo.Usuario(id_usuario),
    fecha_prestamo DATETIME2(0) NOT NULL,
    fecha_prevista DATETIME2(0) NOT NULL,
    fecha_devolucion DATETIME2(0) NULL,
    condicion_retorno NVARCHAR(20) NULL,
    observaciones_retorno NVARCHAR(500) NULL,
    id_usuario_devolucion INT NULL REFERENCES dbo.Usuario(id_usuario),
    CONSTRAINT CK_Prestamo_Fechas CHECK (fecha_prevista >= fecha_prestamo AND (fecha_devolucion IS NULL OR fecha_devolucion >= fecha_prestamo)),
    CONSTRAINT CK_Prestamo_Retorno CHECK (
        (fecha_devolucion IS NULL AND condicion_retorno IS NULL AND id_usuario_devolucion IS NULL AND observaciones_retorno IS NULL)
        OR (fecha_devolucion IS NOT NULL AND condicion_retorno IS NOT NULL AND condicion_retorno IN (N'OPERATIVO', N'DANADO', N'MANTENIMIENTO') AND id_usuario_devolucion IS NOT NULL)
    )
);
-- RN-03: un solo prestamo abierto por equipo, incluso vencido.
CREATE UNIQUE INDEX UX_Prestamo_EquipoAbierto ON dbo.Prestamo(id_equipo) WHERE fecha_devolucion IS NULL;
CREATE INDEX IX_Prestamo_Estudiante ON dbo.Prestamo(id_estudiante);
-- Vistas calculadas; no se almacenan disponibilidad ni situacion.
EXEC(N'CREATE VIEW dbo.vw_DisponibilidadEquipos AS
SELECT e.*, CAST(CASE WHEN e.activo = 1 AND e.estado_fisico = N''OPERATIVO'' AND NOT EXISTS
(SELECT 1 FROM dbo.Prestamo p WHERE p.id_equipo = e.id_equipo AND p.fecha_devolucion IS NULL)
THEN 1 ELSE 0 END AS BIT) AS disponible FROM dbo.Equipo e;');
EXEC(N'CREATE VIEW dbo.vw_SituacionPrestamos AS
SELECT p.*, CASE WHEN p.fecha_devolucion IS NOT NULL THEN N''DEVUELTO''
WHEN p.fecha_prevista < SYSDATETIME() THEN N''VENCIDO'' ELSE N''ACTIVO'' END AS situacion
FROM dbo.Prestamo p;');
COMMIT TRANSACTION;
END TRY
BEGIN CATCH
IF @@TRANCOUNT > 0 ROLLBACK TRANSACTION;
THROW;
END CATCH;
GO
-- Contrato para servicios JDBC (P04 v1.1):
-- RN-05: UPDATE Prestamo ... WHERE id_prestamo=? AND fecha_devolucion IS NULL;
-- verificar una fila afectada, no sobrescribir una devolucion existente.
-- RN-06/RN-08: prestamo, devolucion, baja y cambio de condicion deben
-- usar una transaccion y bloquear primero la misma fila Equipo
-- (SELECT ... WITH (UPDLOCK,HOLDLOCK) WHERE id_equipo=?).
-- Validar usuario existente, activo y autorizado; equipo activo y operativo, sin prestamo abierto.
-- Al devolver, cerrar el prestamo y actualizar Equipo.estado_fisico juntos.
-- No dar de baja equipos con prestamos abiertos; no borrar historial.
-- Estas reglas de servicio no quedan totalmente impuestas por el DDL.
-- Convencion temporal propuesta: hora local del servidor.
