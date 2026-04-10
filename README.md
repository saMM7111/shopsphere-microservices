# ShopSphere Microservices Backend

A Spring Boot microservices ecosystem inspired by the Micromart architecture, built with the same core stack:
- Java 17
- Spring Boot 3.x
- Spring Cloud (Config, Eureka, Gateway)
- RabbitMQ for async events
- MySQL + Flyway (service-owned databases)

## Services
- ConfigServer (7012)
- EurekaServer (7010)
- Gateway (7082)
- JwtAuthorities (shared library)
- Users (7001)
- Products (7016)
- Inventory (7061)
- Cart (7041)
- Order (7063)
- Payment (7007)
- Notification (7050)

## Quick Start
1. Build shared JWT library first.
2. Start infrastructure services (ConfigServer, EurekaServer, Gateway).
3. Start business services.