# Life Simulator

Life Simulator is a text-based life simulation where characters age from 18 to 100+ years, managing interconnected
needs (hunger, sleep, energy, mood) while working productivity-based jobs and experiencing realistic health
consequences. Poor decisions cascade through the system - chronic sleep deprivation leads to health problems, which
reduce work performance, potentially resulting in job loss.

### Key Features

* Real-time Simulation: 1 second of real time = 1 hour of game time
* Needs Management: Hunger, Energy, Hygiene, and Mood degrade over time
* Dynamic Work System: Performance-based salary with KPI tracking and consequences
* Health Simulation: Acute and chronic diseases with cascading effects
* Event System: 500+ random and contextual events with meaningful choices
* Difficulty Modes: Easy, Normal, and Hardcore with different starting conditions
* Persistent Consequences: No save scumming - live with your decisions


### Services

* Auth Service: JWT-based authentication and user management
* Character Service: Character data, balance, and statistics
* Time Service: Game time management (1s = 1h) with temporal events
* Needs Service: Hunger, energy, hygiene, mood tracking and actions
* Work Service: Job assignment, shift work, salary calculation, KPI tracking
* Health Service: Disease system with acute and chronic conditions
* Event Service: Random and contextual event generation with choices
* Game Session Service: Orchestrates game creation and state aggregation

## Technology Stack

### Backend

* Kotlin 2.3.10
* Spring Boot 4.0.1
* PostgreSQL (main data storage)
* Redis (caching, game time storage)
* Apache Kafka (event-driven communication)
* Neo4j (planned: family tree/genealogy)

### Architecture Patterns

* Microservices
* Event-Driven Architecture
* Saga Pattern (for distributed transactions)
* CQRS (read/write separation)
* Domain-Driven Design

### Build & DevOps

* Gradle Multi-Module
* Docker & Docker Compose
* GitHub Actions (CI/CD)
* TestContainers (integration tests)

## Running Locally

* Clone the repository

```shell
git clone https://github.com/woowy-team/life-simulator.git
cd life-simulator
```

* Start infrastructure services

```shell
docker-compose up -d postgres redis rabbitmq
```

* Build all services

```shell
./gradlew build
```

* Run services (in separate terminals or use Docker Compose)

```shell
./gradlew :services:auth-service:bootRun
./gradlew :services:character-service:bootRun
```

Or use Docker Compose to run everything:

```shell
docker-compose up
```

* Run CLI Client

```shell
./gradlew :cli-client:run
```