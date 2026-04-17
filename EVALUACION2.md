# EVALUACION 2 - construccionSoftwareDanielMolina6pm

## Informacion general
- Estudiante(s): Daniel Molina (usuario GitHub: DanielTorres3132)
- Rama evaluada: develop
- Commit evaluado: 2083f8325812e2c75e6e3fcddf0b06f32dd9b102
- Fecha: 2026-04-11
- Nota: README.md solo contiene el titulo del repositorio, sin nombres de integrantes.

---

## Tabla de calificacion

| # | Criterio | Peso | Puntaje (1-5) | Parcial |
|---|---|---|---|---|
| 1 | Modelado de dominio | 20% | 4 | 0.80 |
| 2 | Modelado de puertos | 20% | 1 | 0.20 |
| 3 | Modelado de servicios de dominio | 20% | 1 | 0.20 |
| 4 | Enums y estados | 10% | 4 | 0.40 |
| 5 | Reglas de negocio criticas | 10% | 1 | 0.10 |
| 6 | Bitacora y trazabilidad | 5% | 2 | 0.10 |
| 7 | Estructura interna de dominio | 10% | 3 | 0.30 |
| 8 | Calidad tecnica base en domain | 5% | 4 | 0.20 |
| | **Total base** | | | **2.30** |

### Calculo
Nota base = (4*20 + 1*20 + 1*20 + 4*10 + 1*10 + 2*5 + 3*10 + 4*5) / 100 = 230 / 100 = **2.30**

---

## Penalizaciones aplicadas

Ninguna penalizacion mayor aplicable.

---

## Nota final
**2.3 / 5.0**

---

## Hallazgos

### Criterio 1 - Modelado de dominio (4/5)
- Entidades organizadas por subdominio: `Account/BankAccount`, `Loan/Loan`, `Log/RegisterLog`, `Product/BankingProduct`, `Transfer/Transfer`, `User/CompanyClient`, `User/NaturalPerson`, `User/User`.
- Buena decision de organizar por subdominio en lugar de tipo de clase.
- `CompanyClient` y `NaturalPerson` presentes.
- Falta: clase base `Client` que unifique la jerarquia.
- Falta: relacion `BankAccount → Client`.

### Criterio 2 - Modelado de puertos (1/5)
- **No existe ninguna interfaz de puerto en el dominio.**
- No hay carpeta `domain/ports/` ni interfaces `*Port`.

### Criterio 3 - Servicios de dominio (1/5)
- **No existe ninguna clase de servicio de dominio.**

### Criterio 4 - Enums y estados (4/5)
- Enums por subdominio: `Account/enums/AccountStatus`, `Account/enums/AccountType`, `Loan/enums/LoanStatus`, `Loan/enums/LoanType`, `Transfer/enums/TransferStatus`, `User/enums/SystemRole`, `User/enums/UserStatus`.
- Buena cobertura de los estados criticos.
- Falta: `Currency/Moneda`.

### Criterio 5 - Reglas de negocio criticas (1/5)
- Sin servicios no hay reglas implementadas.

### Criterio 6 - Bitacora y trazabilidad (2/5)
- `Log/RegisterLog` existe como entidad de dominio.
- Sin puerto ni servicio de registro de eventos.

### Criterio 7 - Estructura interna de dominio (3/5)
- Organizacion creativa y coherente por subdominio (`Account/`, `Loan/`, `Transfer/`, `User/`).
- La organizacion por subdominio facilita la escalabilidad.
- Falta: paquetes `ports/` y `services/`.

### Criterio 8 - Calidad tecnica (4/5)
- Nomenclatura en ingles consistente.
- Organizacion por subdominio es una buena practica.
- Sin typos detectados.

---

## Recomendaciones
1. Crear clase base `Client` que unifique `CompanyClient` y `NaturalPerson`.
2. Agregar `domain/ports/` con interfaces de contrato por agregado.
3. Implementar `domain/services/` con casos de uso del enunciado.
4. Agregar el enum `Currency`.
5. Exponer relacion `BankAccount → Client` en el modelo.
6. Incluir nombres de integrantes en `README.md`.
