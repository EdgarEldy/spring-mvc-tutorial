# spring_mvc_tutorial

A tutorial for building a server-rendered web application with **Spring MVC** (on Spring Boot), **Thymeleaf**, **Spring Data JPA / Hibernate** and **MySQL**. It covers an e-commerce domain (categories, products, customers, orders) with a classic CRUD UI: list, add, edit and delete pages, Bean Validation feedback rendered inline, and flash messages after each action.

This document is the **specification** of the project: it is meant to be followed step by step to implement each branch.

## Table of contents

- [Tech stack](#tech-stack)
- [Data model](#data-model)
- [Branching strategy](#branching-strategy)
- [Project structure](#project-structure)
- [Routes](#routes)
- [Testing strategy](#testing-strategy)
  - [Test naming convention](#test-naming-convention)
- [feature/config](#featureconfig)
- [feature/products](#featureproducts)
- [feature/customers](#featurecustomers)
- [feature/orders](#featureorders)
- [Order of work](#order-of-work)
- [Code conventions](#code-conventions)
- [Concepts covered](#concepts-covered)
- [How to follow this tutorial](#how-to-follow-this-tutorial)

## Tech stack

| Component | Choice |
|---|---|
| Framework | Spring Boot 2.7.x (Spring MVC, Spring Data JPA) |
| Language | Java 8 |
| Build | Maven (with the Maven wrapper) |
| Database | MySQL (Hibernate `ddl-auto=update`) |
| Template engine | Thymeleaf, with `thymeleaf-layout-dialect` for the shared layout |
| Front-end | Bootstrap, jQuery, Chart.js (static assets under `src/main/resources/static`) |
| Mapping | MapStruct (entity ↔ DTO) |
| Boilerplate reduction | Lombok |
| Validation | Jakarta/Bean Validation on entities and DTOs |
| Seed data | JavaFaker, through factories and seeders run under the `dev` profile |
| Tests | JUnit 5, Mockito, Spring Test (`MockMvc`), H2 |

## Data model

```
categories (id, category_name)
    │ 1
    │
    │ N
products (id, category_id, product_name, unit_price)
    │ 1
    │
    │ N
orders (id, customer_id, product_id, qty, total)
    │ N
    │
    │ 1
customers (id, first_name, last_name, tel, email, address)
```

## Branching strategy

| Branch | Role |
|---|---|
| `master` | Stable, production-ready code. No direct commits, only merges from `develop`. |
| `develop` | Integration branch. |
| `feature/config` | Project set-up: dependencies (MapStruct, Lombok, H2, test starters), datasource configuration, temporary deactivation of Spring Security. |
| `feature/products` | `Category` and `Product`: entity, DTO, mapper, repository, service, controller, views, tests. |
| `feature/customers` | `Customer`: same layers as above. |
| `feature/orders` | `Order`, which depends on `Product` and `Customer`. |

## Project structure

```
spring_mvc_tutorial/
├── pom.xml
├── mvnw, mvnw.cmd
└── src/
    ├── main/
    │   ├── java/com/edgareldy/spring_mvc_tutorial/
    │   │   ├── SpringMvcTutorialApplication.java
    │   │   ├── controller/      (Category, Product, Customer, Order, Dashboard controllers)
    │   │   ├── dto/             (CategoryDto, ProductDto, CustomerDto, OrderDto)
    │   │   ├── entity/          (Category, Product, Customer, Order)
    │   │   ├── mapper/          (MapStruct interfaces, entity ↔ dto)
    │   │   ├── repository/      (Spring Data JPA repositories)
    │   │   ├── service/         (service contracts)
    │   │   │   └── impl/        (service implementations, logging and transactions)
    │   │   ├── exception/       (ResourceNotFoundException, GlobalExceptionHandler)
    │   │   └── database/
    │   │       ├── factory/     (JavaFaker-based factories)
    │   │       └── seeder/      (DatabaseSeeder and one seeder per entity, `dev` profile only)
    │   └── resources/
    │       ├── application.properties
    │       ├── static/          (css, js, fonts)
    │       └── templates/
    │           ├── layouts/default.html
    │           ├── partials/    (nav, sidebar)
    │           ├── dashboard/, categories/, products/, customers/, orders/   (index, add, edit)
    │           └── error/       (404, 500)
    └── test/java/com/edgareldy/spring_mvc_tutorial/
        ├── SpringMvcTutorialApplicationTests.java
        ├── controller/          (one MockMvc test class per controller)
        └── service/             (one Mockito test class per service)
```

## Routes

Every resource (`/categories`, `/products`, `/customers`, `/orders`) exposes the same six routes:

| Method | Path | Purpose |
|---|---|---|
| GET | `/<resource>` | List page |
| GET | `/<resource>/add` | Empty creation form |
| POST | `/<resource>` | Create, then redirect to the list with a flash message |
| GET | `/<resource>/edit/{id}` | Pre-filled edit form |
| POST | `/<resource>/edit/{id}` | Update, then redirect to the list with a flash message |
| POST | `/<resource>/delete/{id}` | Delete, then redirect to the list with a flash message |

`GET /` renders the dashboard. A validation error re-renders the form with inline messages; an unknown id is mapped by `GlobalExceptionHandler` to the `error/404` view and any other exception to `error/500`.

## Testing strategy

Every branch from `feature/products` onward ships tests at both layers before its Pull Request is opened.

| Layer | Tool | What it verifies | Data |
|---|---|---|---|
| Service | JUnit 5 + Mockito | Business rules and orchestration, with every repository (and any other service) mocked, asserting on the returned DTOs | No database, constructed test data only |
| Controller | JUnit 5 + Spring Test (`MockMvc`) | HTTP-level behavior: returned view names, redirects, flash messages, model content, validation errors, with the service layer mocked | No database |

### Test naming convention

Every test method, at every layer, is named `_NN_Should<Outcome>_When<Condition>`: a two-digit, zero-padded sequence number (the order of the methods within the class, restarting at `_01_` in each class; JUnit does not enforce it, it is kept consistent by convention), followed by what is expected, followed by the condition that produces it.

```java
@Test
void _01_ShouldReturnCategory_WhenCategoryExists() { ... }

@Test
void _02_ShouldThrowResourceNotFound_WhenCategoryDoesNotExist() { ... }
```

No other naming style (`shouldX()`, `testX()`, `givenX_whenY_thenZ()`, `getCategories_returnsIndexView()`) is used anywhere in this project's test suite. This applies to test methods only, not to `@BeforeEach`/`@AfterEach` helpers.

## feature/config

Technical foundation, to be merged first into `develop`.

### Tasks

- [x] Initialize the project (Maven, Java 8, Spring Boot 2.7.x) with the Spring MVC, Thymeleaf, Data JPA, validation and MySQL dependencies
- [x] Replace ModelMapper with MapStruct, and configure the Lombok + MapStruct annotation processors
- [x] Add H2 and `spring-security-test` as test dependencies
- [x] `application.properties`: MySQL datasource, Hibernate settings, `dev` profile active
- [x] Temporarily exclude `SecurityAutoConfiguration` (security is out of scope of this tutorial for now)
- [x] Pin minimum versions of Tomcat, Logback and Jackson to mitigate CVEs not patched in Spring Boot 2.7.x

## feature/products

### Tasks

- [x] `Category` and `Product` entities with Bean Validation (`@NotBlank`, `@Size`, `@Positive`, `@ManyToOne`)
- [x] `CategoryDto`, `ProductDto` and their MapStruct mappers
- [x] Repositories, service contracts and implementations (logging, transactions)
- [x] `ResourceNotFoundException` and `GlobalExceptionHandler` (`error/404`, `error/500`)
- [x] `CategoryController` and `ProductController` with the six routes above
- [x] Thymeleaf views (`index`, `add`, `edit`) for both resources, the shared layout, the dashboard
- [x] Factories and seeders (`dev` profile)
- [x] Service tests (Mockito) and controller tests (`MockMvc`)

## feature/customers

### Tasks

- [x] `Customer` entity (`@NotBlank`, `@Email`, `@Size`), `CustomerDto`, mapper
- [x] Repository, service contract and implementation
- [x] `CustomerController` and its views, with inline validation errors and flash messages
- [x] `CustomerFactory` and `CustomerSeeder`
- [x] Service tests (Mockito) and controller tests (`MockMvc`)

## feature/orders

### Tasks

- [x] `Order` entity (`@ManyToOne` on `Customer` and `Product`), `OrderDto` carrying `customerId`/`productId` plus the display fields `customerName`, `productName`, `unitPrice`
- [x] `OrderMapper`, repository, service contract and implementation
- [x] `OrderController` and its views, with product and customer dropdowns pre-filled on the edit page
- [x] `OrderFactory` and `OrderSeeder` (random customers and products)
- [x] Service tests (Mockito) and controller tests (`MockMvc`), including the not-found case for an unknown order

## Order of work

1. `feature/config` → Pull Request to `develop`
2. `feature/products` (depends on `config`) → Pull Request to `develop`
3. `feature/customers` (depends on `products`) → Pull Request to `develop`
4. `feature/orders` (depends on `products` and `customers`) → Pull Request to `develop`
5. `develop` → `master`

## Code conventions

- Root package: `com.edgareldy.spring_mvc_tutorial`
- Lombok on entities and DTOs; MapStruct for every entity ↔ DTO conversion
- A controller never touches a repository or an entity directly: only a service, and only DTOs
- A service is declared as an interface in `service/` and implemented in `service/impl/`
- Validation rules live on the entity and on the DTO, and are surfaced inline in the views
- Every mutating action redirects (Post/Redirect/Get) with a flash message
- Seeders only run under the `dev` profile
- Test methods follow the `_NN_Should<Outcome>_When<Condition>` naming convention (see [Test naming convention](#test-naming-convention))

## Concepts covered

- Spring MVC controllers, view resolution and the Post/Redirect/Get pattern
- Server-side rendering with Thymeleaf and a shared layout (`thymeleaf-layout-dialect`)
- Spring Data JPA and Hibernate with MySQL
- The Entity / DTO / Mapper / Repository / Service layering, with MapStruct
- Bean Validation with inline error rendering
- Centralized error handling with `@ControllerAdvice`
- Seeding a development database with JavaFaker
- Service tests with Mockito and controller tests with `MockMvc`

## How to follow this tutorial

1. Clone the repository and check out `develop`
2. Follow the branches in order: `feature/config` → `feature/products` → `feature/customers` → `feature/orders`
3. Create a MySQL database named `spring_db` and adjust the credentials in `src/main/resources/application.properties`
4. Run the tests with `./mvnw test`
5. Run the application with `./mvnw spring-boot:run`, then open `http://localhost:8080/`
