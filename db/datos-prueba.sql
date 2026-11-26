-- P07 | Datos completamente ficticios para desarrollo local.
-- Revisado contra P03 v1.0 y P04 v1.1; pendiente de ejecucion en SQL Server.
-- Las filas historicas son una carga inicial, no una simulacion de las operaciones Java.
-- Ejecutar despues de esquema.sql, en una BD vacia de pruebas.
-- No ejecutar en produccion. No se borran registros existentes.
-- Hashes PBKDF2-HMAC-SHA256: formato algoritmo$iteraciones$sal_base64$hash_base64.
-- Claves ficticias: DemoAdmin2026! y DemoEncargado2026!; no reutilizar.
-- M1 debe implementar/acordar este formato antes de probar el login.
USE PrestamosEquiposRed;
GO
SET XACT_ABORT ON;
SET ANSI_NULLS ON;
SET QUOTED_IDENTIFIER ON;
SET ANSI_PADDING ON;
SET ANSI_WARNINGS ON;
SET CONCAT_NULL_YIELDS_NULL ON;
SET ARITHABORT ON;
SET NUMERIC_ROUNDABORT OFF;
BEGIN TRY
BEGIN TRANSACTION;
IF EXISTS (SELECT 1 FROM dbo.Usuario) OR EXISTS (SELECT 1 FROM dbo.Estudiante) OR EXISTS (SELECT 1 FROM dbo.Equipo) OR EXISTS (SELECT 1 FROM dbo.Prestamo)
    THROW 50001, N'Los datos de prueba requieren tablas vacias.', 1;
INSERT dbo.Usuario(nombre_usuario,password_hash,rol,activo) VALUES
(N'admin_demo',N'pbkdf2_sha256$600000$eA4C8twIP3goiyHW1tKCGA==$XkfU267l2DTUj9lHeObH5hx1vor+E58ipGRDn6sTack=',N'ADMINISTRADOR',1),
(N'encargado_demo',N'pbkdf2_sha256$600000$lqrRC9BhLvWY2rGnlo68Mg==$c+xePaYZr2lp2zGvK5MbeSiMKS5KwNR0oCzbFP4WfkI=',N'ENCARGADO',1),
(N'inactivo_demo',N'pbkdf2_sha256$600000$lqrRC9BhLvWY2rGnlo68Mg==$c+xePaYZr2lp2zGvK5MbeSiMKS5KwNR0oCzbFP4WfkI=',N'ENCARGADO',0);
INSERT dbo.Estudiante(codigo_universitario,nombres,apellidos) VALUES
(N'DEMO001',N'Ana',N'Ejemplo Uno'),(N'DEMO002',N'Luis',N'Ejemplo Dos'),(N'DEMO003',N'Maria',N'Ejemplo Tres');
INSERT dbo.Equipo(codigo_inventario,tipo,marca,modelo,numero_serie,ubicacion,estado_fisico,observaciones,activo) VALUES
(N'RT-001',N'ROUTER',N'Cisco',N'ISR 1100',N'DEMO-RT1',N'Lab A',N'OPERATIVO',NULL,1),
(N'SW-002',N'SWITCH',N'TP-Link',N'TL-SG108',N'DEMO-SW2',N'Lab A',N'OPERATIVO',NULL,1),
(N'AP-003',N'PUNTO_ACCESO',N'Ubiquiti',N'UniFi AP',NULL,N'Lab A',N'OPERATIVO',NULL,1),
(N'SW-004',N'SWITCH',N'Cisco',N'Catalyst 2960',NULL,N'Lab A',N'MANTENIMIENTO',NULL,1),
(N'RT-005',N'ROUTER',N'MikroTik',N'hEX',NULL,N'Lab A',N'OPERATIVO',NULL,1),
(N'RT-006',N'ROUTER',N'Cisco',N'ISR 1100',NULL,N'Lab A',N'DANADO',N'Dano ficticio al retorno',1),
(N'SW-007',N'SWITCH',N'TP-Link',N'TL-SG108',NULL,N'Lab A',N'OPERATIVO',N'Baja de ejemplo',0);
DECLARE @ahora DATETIME2(0)=SYSDATETIME();
DECLARE @u INT=(SELECT id_usuario FROM dbo.Usuario WHERE nombre_usuario=N'encargado_demo');
DECLARE @a INT=(SELECT id_estudiante FROM dbo.Estudiante WHERE codigo_universitario=N'DEMO001');
DECLARE @b INT=(SELECT id_estudiante FROM dbo.Estudiante WHERE codigo_universitario=N'DEMO002');
INSERT dbo.Prestamo(id_estudiante,id_equipo,id_usuario_prestamo,fecha_prestamo,fecha_prevista,fecha_devolucion,condicion_retorno,observaciones_retorno,id_usuario_devolucion) VALUES
(@a,(SELECT id_equipo FROM dbo.Equipo WHERE codigo_inventario=N'SW-002'),@u,DATEADD(day,-1,@ahora),DATEADD(day,7,@ahora),NULL,NULL,NULL,NULL),
(@b,(SELECT id_equipo FROM dbo.Equipo WHERE codigo_inventario=N'RT-005'),@u,DATEADD(day,-10,@ahora),DATEADD(day,-2,@ahora),NULL,NULL,NULL,NULL),
(@a,(SELECT id_equipo FROM dbo.Equipo WHERE codigo_inventario=N'AP-003'),@u,DATEADD(day,-8,@ahora),DATEADD(day,-4,@ahora),DATEADD(day,-5,@ahora),N'OPERATIVO',N'Retorno correcto',@u),
(@b,(SELECT id_equipo FROM dbo.Equipo WHERE codigo_inventario=N'RT-006'),@u,DATEADD(day,-7,@ahora),DATEADD(day,-3,@ahora),DATEADD(day,-3,@ahora),N'DANADO',N'Dano ficticio',@u),
(@a,(SELECT id_equipo FROM dbo.Equipo WHERE codigo_inventario=N'SW-007'),@u,DATEADD(day,-20,@ahora),DATEADD(day,-15,@ahora),DATEADD(day,-16,@ahora),N'OPERATIVO',NULL,@u);
COMMIT TRANSACTION;
END TRY
BEGIN CATCH
IF @@TRANCOUNT > 0 ROLLBACK TRANSACTION;
THROW;
END CATCH;
GO
-- Consultas de comprobacion: 7 equipos, 2 disponibles; 5 prestamos.
SELECT codigo_inventario,estado_fisico,activo,disponible FROM dbo.vw_DisponibilidadEquipos ORDER BY codigo_inventario;
SELECT p.id_prestamo,e.codigo_inventario,s.codigo_universitario,p.situacion FROM dbo.vw_SituacionPrestamos p JOIN dbo.Equipo e ON e.id_equipo=p.id_equipo JOIN dbo.Estudiante s ON s.id_estudiante=p.id_estudiante ORDER BY p.id_prestamo;
SELECT situacion,COUNT(*) AS cantidad FROM dbo.vw_SituacionPrestamos GROUP BY situacion;
