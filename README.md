# Bank Information Management System

Backend application that simulates the core transactional system of a bank, managing clients, accounts, loans and transfers with role-based access control and approval flows.

## Tech Stack

- Java 17
- Spring Boot
- Spring Web
- Spring Security
- Spring Data JPA
- MySQL
- Lombok

## Description

The system handles two types of clients (natural persons and companies), bank accounts, loans and transfers. Each operation is governed by strict business rules — for example, loans require approval from an internal analyst before disbursement, and high-value company transfers must be authorized by a company supervisor within 60 minutes or they expire automatically.

All significant operations are recorded in an audit log for traceability and compliance purposes.


## Students
Daniel Molina Torres
Linda Estefania Moreno
Evelyn Montoya
