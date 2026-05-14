# Sistema de Alquiler de Vehículos

Sistema de microservicios para gestión de alquiler de vehículos construido con Spring Boot y Spring Cloud.

## Arquitectura

```
├── eureka-server/     # Servidor de registro y descubrimiento
├── api-gateway/       # Gateway de enrutamiento
├── ms-vehiculo/       # Microservicio de vehículos
├── ms-cliente/        # Microservicio de clientes
├── ms-alquiler/       # Microservicio de alquileres
├── ms-auth/           # Microservicio de autenticación
└── docker-compose.yml
```

## Tecnologías

- Java 25
- Spring Boot 4.0.6
- Spring Cloud 2025.1.1
- Spring Cloud Gateway
- Netflix Eureka
- Spring Data JPA
- H2 Database
- OpenFeign
- Docker

## Servicios y Puertos

| Servicio | Puerto | Descripción |
|----------|--------|-------------|
| eureka-server | 8761 | Registro de servicios |
| api-gateway | 8080 | Gateway principal |
| ms-vehiculo | 8081 | Gestión de vehículos |
| ms-cliente | 8082 | Gestión de clientes |
| ms-alquiler | 8083 | Gestión de alquileres |
| ms-auth | 8084 | Autenticación |

## Instalación

### Prerrequisitos

- Java 25+
- Maven 3.9+
- Docker y Docker Compose

### Compilar el proyecto

```bash
mvn clean package -DskipTests
```

## Levantar con Docker

```bash
docker-compose up --build
```

Para detener los servicios:

```bash
docker-compose down
```

## Acceso

- **API Gateway**: http://localhost:8080
- **Eureka Dashboard**: http://localhost:8761
- **H2 Console**: http://localhost:{puerto}/h2-console (en cada microservicio)

## Colección de Postman

Importa el archivo `postman_collection.json` en Postman para probar todos los endpoints. La colección usa `{{baseUrl}}` apuntando a `http://localhost:8080` (API Gateway).
