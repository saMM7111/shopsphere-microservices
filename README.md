# 🛍️ ShopSphere: Distributed E-Commerce Microservices Platform

![CI](https://github.com/saMM7111/shopsphere-microservices/actions/workflows/ci.yml/badge.svg)
![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.0.1-blue)
![Docker](https://img.shields.io/badge/Docker-Enabled-blue)
![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)
![Architecture](https://img.shields.io/badge/Architecture-Microservices-0d6efd)
![Pattern](https://img.shields.io/badge/Pattern-Event%20Driven-yellow)

ShopSphere is an enterprise-grade, distributed e-commerce platform built on a reactive, event-driven architecture. Designed for high availability and elastic scalability, it demonstrates sophisticated patterns including distributed transactions (Saga), reactive data streams, and automated resilience engineering.

The platform is structured as independently deployable Spring Boot services with strict domain boundaries, a shared JWT utility library, and a fully automated CI pipeline.

---

## 🏗️ System Architecture

ShopSphere is built around three foundational architectural patterns: the **API Gateway Pattern** for a unified entry point, **Service Discovery** via Eureka for dynamic routing, and the **Database-per-Service Pattern** to enforce domain isolation and independent scalability.

```mermaid
graph LR
    classDef business fill:#0d6efd,color:#fff,stroke:#0a58ca,stroke-width:2px;
    classDef infra fill:#198754,color:#fff,stroke:#146c43,stroke-width:2px;
    classDef data fill:#ffc107,color:#000,stroke:#cc9a06,stroke-width:2px;
    classDef shared fill:#6f42c1,color:#fff,stroke:#59339d,stroke-width:2px;
    classDef external fill:#ffffff,color:#000,stroke:#cccccc,stroke-width:2px;

    Client((Web / Mobile Client)) --> Gateway[API Gateway : 7082]
    class Client external
    class Gateway business

    subgraph "🟩 Infrastructure"
        Config[ConfigServer : 7012]
        Eureka[EurekaServer : 7010]
        Rabbit[RabbitMQ Broker]
    end
    class Config,Eureka,Rabbit infra

    subgraph "🟦 Domain Services"
        Users[Users : 7001]
        Products[Products : 7016]
        Inventory[Inventory : 7061]
        Cart[Cart : 7041]
        Order[Order : 7063]
        Payment[Payment : 7007]
        Notification[Notification : 7050]
    end
    class Users,Products,Inventory,Cart,Order,Payment,Notification business

    subgraph "🟨 Data Stores"
        UsersDB[(Users DB)]
        ProductsDB[(Products DB)]
        InventoryDB[(Inventory DB)]
        CartDB[(Cart DB)]
        OrderDB[(Order DB)]
        PaymentDB[(Payment DB)]
    end
    class UsersDB,ProductsDB,InventoryDB,CartDB,OrderDB,PaymentDB data

    subgraph "🟪 Shared Security"
        JWT[[" 📦 JwtAuthorities.jar (Embedded in Services)"]]
    end
    class JWT shared

    %% Sync calls
    Gateway -.-> Eureka
    Cart -->|Sync: Check Stock| Inventory
    Order -->|Sync: Initiate Payment| Payment

    %% Async event flows
    Order -.->|Publishes Event| Rabbit
    Payment -.->|Publishes Event| Rabbit
    Rabbit -.->|Consumes Event| Order & Inventory & Cart & Notification

    %% DB connections
    Users --> UsersDB
    Products --> ProductsDB
    Inventory --> InventoryDB
    Cart --> CartDB
    Order --> OrderDB
    Payment --> PaymentDB
```

### 📊 Diagram Legend

| Shape & Color | Node Type | Description |
| :--- | :--- | :--- |
| ⚪ **White Circle** | **External Actor** | End-user client (Web / Mobile App) |
| 🟦 **Blue Rectangle** | **Domain Service** | Independent microservice handling core business logic |
| 🟩 **Green Rectangle** | **Infrastructure** | Backbone services for routing, config, and messaging |
| 🟨 **Yellow Cylinder** | **Data Store** | Isolated persistence layers (Database-per-Service) |
| 🟪 **Purple Box** | **Shared Library** | Reusable `.jar` dependency embedded at compile time |

**Communication Lines:**
- `───▶` **Solid Arrow:** Synchronous HTTP/REST call (blocking)
- `- - -▶` **Dashed Arrow:** Asynchronous event-driven message (non-blocking)

---

## 🔄 Checkout Transaction Lifecycle

A single checkout triggers a coordinated distributed transaction across multiple services. The sequence below illustrates both the synchronous and asynchronous phases.

```mermaid
sequenceDiagram
    participant Client
    participant Gateway
    participant Users as Users (JWT)
    participant Order
    participant Payment
    participant Rabbit as RabbitMQ (Broker)
    participant Inventory
    participant Notify as Notification

    Client->>Gateway: POST /api/v1/orders/checkout
    Gateway->>Users: Validate JWT Token
    Users-->>Gateway: Token Valid (Role: USER)
    Gateway->>Order: Create Order (Status: PENDING)
    Order->>Payment: Initiate Payment Session
    Payment-->>Client: Payment Reference / Redirect

    Note over Payment: User completes payment

    Payment->>Rabbit: Publish: PaymentApprovedEvent

    par Async Consumers
        Rabbit-->>Order: Update Status → CONFIRMED
        Rabbit-->>Inventory: Deduct Stock
        Rabbit-->>Notify: Send Confirmation Notification
    end
```

---

## 🛡️ Resilience & Observability

ShopSphere is built with a **"Design for Failure"** mindset. The API Gateway acts as the resilient edge using:

- **Circuit Breakers (Resilience4J):** Protects high-risk downstream routes.
  - **Trip Logic:** Circuit opens if failure rate exceeds **50%** over a rolling window of **10 calls**.
  - **Self-Healing (Half-Open):** After a **10-second cooldown**, **3 probe requests** are allowed through. Sustained success closes the circuit; further failures trip it open again.
- **Time Limiting:** **5-second hard timeouts** prevent a slow downstream service from exhausting the Gateway's thread pool.
- **Global CORS:** Pre-configured for modern frontend clients (e.g., React on port 3000).
- **Observability:** Spring Boot Actuator exposes real-time health checks and metrics on every service.

---

## 📦 Service Registry

| Service | Responsibility | Port |
| :--- | :--- | :---: |
| **Gateway** | API entry point, routing, edge resilience | `7082` |
| **ConfigServer** | Centralized configuration management | `7012` |
| **EurekaServer** | Service registration and dynamic discovery | `7010` |
| **Users** | Registration, login, and session management | `7001` |
| **Products** | Product catalog and category metadata | `7016` |
| **Inventory** | Stock tracking and safety-threshold management | `7061` |
| **Cart** | Add, view, and clear cart operations | `7041` |
| **Order** | Checkout orchestration and order retrieval | `7063` |
| **Payment** | Payment initiation, approval, and history | `7007` |
| **Notification** | Multi-channel dispatch (Email / SMS) via RabbitMQ | `7050` |
| **JwtAuthorities** | **[Library]** Shared JWT parsing and security filters | `N/A` |

---

## 📁 Repository Structure

```text
shopsphere-microservices/
├── ConfigServer/
├── EurekaServer/
├── Gateway/
├── JwtAuthorities/         ← Shared security library
├── Users/
├── Products/
├── Inventory/
├── Cart/
├── Order/
├── Payment/
├── Notification/
├── docker-compose.yml
└── pom.xml                 ← Multi-module Maven root
```

---

## 🚀 Local Development Setup

### Prerequisites

- Java 17
- Maven 3.9+
- Docker Desktop

### Step 1 — Start infrastructure dependencies
```bash
docker-compose up -d
```

### Step 2 — Install the shared JWT library
`JwtAuthorities` is a custom internal library that must be installed to your local `.m2` repository before building anything else:
```bash
mvn -pl JwtAuthorities -am clean install
```

### Step 3 — Build all modules
```bash
mvn clean package -DskipTests
```

### Step 4 — Start infrastructure services
```bash
mvn -pl ConfigServer spring-boot:run
mvn -pl EurekaServer spring-boot:run
mvn -pl Gateway spring-boot:run
```

### Step 5 — Start domain services
```bash
mvn -pl Users spring-boot:run
mvn -pl Products spring-boot:run
mvn -pl Inventory spring-boot:run
mvn -pl Cart spring-boot:run
mvn -pl Order spring-boot:run
mvn -pl Payment spring-boot:run
mvn -pl Notification spring-boot:run
```

> **Tip:** Services register themselves with Eureka on startup. The Gateway resolves routes dynamically — no hardcoded IPs needed.

---

## ⚙️ CI Pipeline

GitHub Actions runs on every push and pull request targeting `main`.

**Workflow location:** `.github/workflows/ci.yml`

| Step | Action |
| :--- | :--- |
| 1 | Checkout repository |
| 2 | Set up JDK 17 (Temurin distribution) |
| 3 | Build shared `JwtAuthorities` library |
| 4 | Run full Maven build with `clean verify` |
| 5 | Execute all unit and integration tests |
