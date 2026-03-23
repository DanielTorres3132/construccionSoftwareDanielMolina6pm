# EVALUACIÓN - construccionSoftwareDanielMolina6pm

## Información General
- **Estudiante(s):** Daniel Molina (dmolina / DanielTorres3132)
- **Rama evaluada:** develop
- **Fecha de evaluación:** 2026-03-23

---

## Tabla de Calificación

| # | Criterio | Peso | Puntaje (1–5) | Nota ponderada |
|---|---|---|---|---|
| 1 | Modelado de dominio | 25% | 3 | 0.75 |
| 2 | Relaciones entre entidades | 15% | 3 | 0.45 |
| 3 | Uso de Enums | 15% | 4 | 0.60 |
| 4 | Manejo de estados | 5% | 4 | 0.20 |
| 5 | Tipos de datos | 5% | 3 | 0.15 |
| 6 | Separación Usuario vs Cliente | 10% | 2 | 0.20 |
| 7 | Bitácora | 5% | 2 | 0.10 |
| 8 | Reglas básicas de negocio | 5% | 2 | 0.10 |
| 9 | Estructura del proyecto | 10% | 4 | 0.40 |
| 10 | Repositorio | 10% | 1 | 0.10 |
| **TOTAL** | | **100%** | | **3.05** |

## Penalizaciones
- Ninguna aplicada (nombres de clases y variables en inglés; comentarios internos en español son tolerables).

## Bonus
- Ninguno.

## Nota Final: 3.1 / 5.0

---

## Análisis por Criterio

### 1. Modelado de dominio — 3/5
Entidades presentes: `BankAccount`, `Loan`, `CompanyClient`, `NaturalPerson`, `User` (abstracto), `BankingProduct`, `RegisterLog`, `Transfer`. La mayoría de entidades existen. Sin embargo, `CompanyClient` y `NaturalPerson` extienden `User`, mezclando los conceptos de cliente y usuario del sistema.

### 2. Relaciones entre entidades — 3/5
`User` tiene `relatedId` (String) para relacionar usuario con cliente. `CompanyClient` tiene `User legalRepresentative` (auto-referencia a User). Las relaciones existen pero son débiles (IDs en lugar de referencias de objeto) o confusas (User como base de clients).

### 3. Uso de Enums — 4/5
Enums presentes: `AccountStatus`, `AccountType`, `LoanStatus`, `LoanType`, `SystemRole`, `TransferStatus`, `UserStatus`. Falta `Currency`. `BankAccount` usa `String currency` en lugar del enum.

### 4. Manejo de estados — 4/5
La mayoría de estados usan enums correctamente en las entidades. Falta `Currency` y el campo moneda es `String`.

### 5. Tipos de datos — 3/5
`BankAccount` usa `LocalDate openingDate` ✓. `RegisterLog` usa `LocalDateTime operationDateTime` ✓. Sin embargo, el balance es `double currentBalance` en lugar de `BigDecimal`, y la moneda es `String`. Las fechas son correctas.

### 6. Separación Usuario vs Cliente — 2/5
`CompanyClient` y `NaturalPerson` heredan de la clase abstracta `User`. El campo `relatedId` en `User` intenta relacionar usuario con cliente, pero la herencia hace que cada cliente sea también un usuario del sistema. No hay una jerarquía de clientes separada.

### 7. Bitácora — 2/5
`RegisterLog` existe con `LocalDateTime`, `SystemRole userRole` (enum ✓), pero `operationType` es `String` y `detailData` es `String` en lugar de `Map<String, Object>`.

### 8. Reglas básicas de negocio — 2/5
No se observan métodos de negocio ni validaciones en las entidades de dominio.

### 9. Estructura del proyecto — 4/5
Organización por agregados en sub-paquetes: `Account/`, `Loan/`, `Log/`, `Product/`, `Transfer/`, `User/`. Los enums se ubican en sub-paquetes de su entidad respectiva. Buena organización, aunque no es la convención DDD típica.

### 10. Repositorio — 1/5
- **Nombre:** `construccionSoftwareDanielMolina6pm` — correcto.
- **README:** Solo el nombre del repositorio.
- **Commits:** En español ("ajustes", "Creación de user y sus clases hijas", "Traduccion del proyecto a ingles"). Sin formato ADD/CHG.
- **Ramas:** Tiene `develop` ✓ y rama `1-Models-inicio` (buen uso de feature branch ✓).
- **Tag:** No hay tag.

---

## Fortalezas
- Buenos enums, casi completos.
- Correcto uso de fechas (`LocalDate`, `LocalDateTime`).
- Estructura organizada por sub-dominios.
- Uso de rama feature (`1-Models-inicio`).
- Código con nombres en inglés.

## Oportunidades de mejora
- Crear jerarquía `Client` separada de `User`.
- Agregar `Currency` enum y usarlo en `BankAccount`.
- Cambiar `double currentBalance` a `BigDecimal`.
- Agregar `Map<String, Object>` en `RegisterLog`.
- Agregar lógica de negocio en las entidades.
- Mejorar README con info de la materia.
- Escribir commits en inglés con formato ADD/CHG y agregar tag.
