# Casos de prueba iniciales

## Objetivo
Definir los escenarios iniciales de prueba del sistema de préstamos y devoluciones de equipos de laboratorio.

## Casos de prueba

| ID | Escenario | Precondición | Acción | Resultado esperado |
|---|---|---|---|---|
| CP01 | Equipo libre | Equipo disponible y estudiante registrado y habilitado. | Registrar un préstamo válido. | Se registra el préstamo y el equipo queda ocupado. |
| CP02 | Equipo ocupado | El equipo tiene un préstamo activo. | Intentar registrar otro préstamo del mismo equipo. | Se rechaza la operación sin modificar el préstamo activo. |
| CP03 | Equipo dañado | El equipo está registrado como dañado. | Intentar prestar el equipo. | Se rechaza el préstamo y se informa que el equipo no está disponible. |
| CP04 | Estudiante inexistente | El identificador del estudiante no está registrado. | Intentar registrar un préstamo con dicho identificador. | Se rechaza la operación y se informa que el estudiante no existe. |
| CP05 | Préstamo vencido | Existe un préstamo activo cuya fecha límite ya pasó. | Consultar el préstamo y registrar su devolución. | Se identifica el retraso y se permite cerrar el préstamo registrando la devolución. |
| CP06 | Devolución repetida | El préstamo ya fue devuelto. | Intentar registrar otra devolución del mismo préstamo. | Se rechaza la operación sin duplicar registros ni modificar el estado existente. |

## Estado de ejecución
Los seis casos están pendientes de ejecución. Los resultados descritos son los esperados, no resultados de pruebas realizadas.

## Observaciones
Las reglas específicas de estados, restricciones y tratamiento de préstamos vencidos deberán validarse con el equipo de desarrollo.
