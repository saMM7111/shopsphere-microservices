# ShopSphere: Distributed E-Commerce Microservices Platform

![CI](https://github.com/saMM7111/shopsphere-microservices/actions/workflows/ci.yml/badge.svg)
![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.0.1-blue)
![Architecture](https://img.shields.io/badge/Architecture-Microservices-0d6efd)
![Pattern](https://img.shields.io/badge/Pattern-Event%20Driven-yellow)

ShopSphere is a Java microservices backend inspired by the same architecture style as the reference ecosystem: gateway-first routing, centralized configuration, service discovery, and asynchronous integration through messaging.

The project is structured as independent Spring Boot services with clear domain boundaries and a reusable JWT utility library.

---

## System Architecture

ShopSphere uses the API Gateway pattern, service discovery, and service-per-domain boundaries for scalability and loose coupling.

```mermaid
graph LR
	classDef business fill:#0d6efd,color:#fff,stroke:#0a58ca,stroke-width:2px;
	classDef infra fill:#198754,color:#fff,stroke:#146c43,stroke-width:2px;
	classDef data fill:#ffc107,color:#000,stroke:#cc9a06,stroke-width:2px;
	classDef shared fill:#6f42c1,color:#fff,stroke:#59339d,stroke-width:2px;
	classDef external fill:#ffffff,color:#000,stroke:#cccccc,stroke-width:2px;

	Client((Web or Mobile Client)) --> Gateway[Gateway : 7082]
	class Client external
	class Gateway business

	subgraph Infrastructure
		Config[ConfigServer : 7012]
		Eureka[EurekaServer : 7010]
		Rabbit[RabbitMQ]
	end
	class Config,Eureka,Rabbit infra

	subgraph Domain Services
		Users[Users : 7001]
		Products[Products : 7016]
		Inventory[Inventory : 7061]
		Cart[Cart : 7041]
		Order[Order : 7063]
		Payment[Payment : 7007]
		Notification[Notification : 7050]
	end
	class Users,Products,Inventory,Cart,Order,Payment,Notification business

	subgraph Data Stores
		UsersDB[(Users DB)]
		ProductsDB[(Products DB)]
		InventoryDB[(Inventory DB)]
		OrderDB[(Order DB)]
		PaymentDB[(Payment DB)]
		CartDB[(Cart DB)]
	end
	class UsersDB,ProductsDB,InventoryDB,OrderDB,PaymentDB,CartDB data

	subgraph Shared Security
		JWT[[JwtAuthorities Library]]
	end
	class JWT shared

	Gateway -.-> Eureka
	Order --> Payment
	Cart --> Inventory

	Order -. publishes .-> Rabbit
	Payment -. publishes .-> Rabbit
	Rabbit -. consumes .-> Notification
	Rabbit -. consumes .-> Order
	Rabbit -. consumes .-> Inventory
	Rabbit -. consumes .-> Cart

	Users --> UsersDB
	Products --> ProductsDB
	Inventory --> InventoryDB
	Cart --> CartDB
	Order --> OrderDB
	Payment --> PaymentDB
```

---

## Checkout Lifecycle

The sequence below shows a typical order flow with both synchronous and asynchronous steps.

```mermaid
sequenceDiagram
	participant Client
	participant Gateway
	participant Users
	participant Order
	participant Payment
	participant Rabbit as RabbitMQ
	participant Inventory
	participant Notify as Notification

	Client->>Gateway: POST /api/orders/checkout
	Gateway->>Users: Validate user or session
	Gateway->>Order: Create order (PENDING)
	Order->>Payment: Initiate payment
	Payment-->>Client: Payment reference

	Payment->>Rabbit: Payment approved event

	par Async consumers
		Rabbit-->>Order: Mark order as CONFIRMED
		Rabbit-->>Inventory: Deduct stock
		Rabbit-->>Notify: Send user notification
	end
```

---

## Reliability and Operations

- Gateway includes circuit-breaker support for critical routes.
- Services expose Spring Boot Actuator endpoints for health and diagnostics.
- RabbitMQ dependencies are in place for event-driven communication between services.
- Each service is independently runnable and deployable.

---

## Service Registry

| Service | Responsibility | Port |
| :--- | :--- | :--- |
| Gateway | API entrypoint, routing, edge resilience | 7082 |
| ConfigServer | Centralized configuration source | 7012 |
| EurekaServer | Service discovery registry | 7010 |
| Users | Registration and login endpoints | 7001 |
| Products | Product and category catalog endpoints | 7016 |
| Inventory | Stock and safety-threshold management | 7061 |
| Cart | Add or view or clear cart operations | 7041 |
| Order | Checkout and order retrieval endpoints | 7063 |
| Payment | Payment initiation, approval, and status | 7007 |
| Notification | Multi-channel notification dispatch | 7050 |
| JwtAuthorities | Shared JWT parsing utility library | N/A |

---

## Repository Structure

```text
Microservices/
  ConfigServer/
  EurekaServer/
  Gateway/
  JwtAuthorities/
  Users/
  Products/
  Inventory/
  Cart/
  Order/
  Payment/
  Notification/
  docker-compose.yml
  pom.xml
```

---

## Local Development Setup

### 1) Prerequisites

- Java 17
- Maven 3.9+
- Docker Desktop

### 2) Start dependencies

```bash
docker-compose up -d
```

### 3) Build shared JWT library first

```bash
mvn -pl JwtAuthorities -am clean install
```

### 4) Build all modules

```bash
mvn clean package -DskipTests
```

### 5) Start infrastructure services

```bash
mvn -pl ConfigServer spring-boot:run
mvn -pl EurekaServer spring-boot:run
mvn -pl Gateway spring-boot:run
```

### 6) Start business services

```bash
mvn -pl Users spring-boot:run
mvn -pl Products spring-boot:run
mvn -pl Inventory spring-boot:run
mvn -pl Cart spring-boot:run
mvn -pl Order spring-boot:run
mvn -pl Payment spring-boot:run
mvn -pl Notification spring-boot:run
```

---

## CI Pipeline

GitHub Actions is configured to run on push and pull requests to main.

Workflow location:

- .github/workflows/ci.yml

Pipeline steps:

1. Checkout repository
2. Set up JDK 17 (Temurin)
3. Run Maven build and tests with clean verify
