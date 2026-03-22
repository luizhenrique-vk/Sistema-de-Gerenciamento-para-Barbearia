# Sistema de Gerenciamento para Barbearia

API REST completa em Java com Spring Boot para gerenciamento de barbearias, com cadastro de usuarios, autenticacao JWT, controle de clientes, servicos, agendamentos e resumo operacional.

## Tecnologias

- Java 21
- Spring Boot 3
- Spring Security + JWT
- Spring Data JPA
- MySQL
- H2 para testes

## Funcionalidades

- Cadastro e autenticacao de usuarios
- CRUD de clientes
- CRUD de servicos
- CRUD de agendamentos
- Validacao de conflito de horarios por barbeiro
- Resumo de dashboard com indicadores e faturamento
- Integracao com banco de dados MySQL

## Credenciais iniciais

Ao subir a aplicacao, um usuario administrador e criado automaticamente:

- E-mail: `admin@barbearia.com`
- Senha: `admin123`

O cadastro publico nao permite criar novos administradores.

## Configuracao do banco

As propriedades podem ser alteradas por variaveis de ambiente:

- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `JWT_SECRET`
- `JWT_EXPIRATION_MINUTES`

Configuracao padrao:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/barbershop_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Sao_Paulo
spring.datasource.username=root
spring.datasource.password=root
```

## Executando

```powershell
$env:JAVA_HOME="CAMINHO_DO_JDK"
.\mvnw.cmd spring-boot:run
```

## Endpoints principais

### Autenticacao

- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET /api/auth/me`

### Usuarios

- `GET /api/users`
- `GET /api/users?onlyBarbers=true`

### Clientes

- `GET /api/clients`
- `GET /api/clients/{id}`
- `POST /api/clients`
- `PUT /api/clients/{id}`
- `DELETE /api/clients/{id}`

### Servicos

- `GET /api/services`
- `GET /api/services/{id}`
- `POST /api/services`
- `PUT /api/services/{id}`
- `DELETE /api/services/{id}`

### Agendamentos

- `GET /api/appointments`
- `GET /api/appointments?date=2026-03-20`
- `GET /api/appointments?status=SCHEDULED`
- `GET /api/appointments/{id}`
- `POST /api/appointments`
- `PUT /api/appointments/{id}`
- `DELETE /api/appointments/{id}`

### Dashboard

- `GET /api/dashboard/summary`

## Exemplo de login

```json
{
  "email": "admin@barbearia.com",
  "password": "admin123"
}
```

## Exemplo de agendamento

```json
{
  "clientId": 1,
  "serviceId": 1,
  "barberId": 1,
  "appointmentDateTime": "2026-03-25T14:00:00",
  "status": "SCHEDULED",
  "notes": "Cliente prefere acabamento na navalha"
}
```
