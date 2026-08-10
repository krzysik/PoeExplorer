PoeExplorer

PoeExplorer is a REST API built with Java and Spring Boot that retrieves and processes Path of Exile currency and league data from external APIs.

Features
Retrieve available Path of Exile leagues
Retrieve currency prices
Filter currencies by name or ID
Sort currencies by price or 7-day price change
Pagination of currency results
Validation of pagination parameters
Exception handling for invalid pagination and sorting parameters
Unit tests for the currency service
Technologies
Java 21
Spring Boot 4
Maven
JUnit 5
Mockito
REST API
Git / GitHub
External APIs

The application uses data from:

Path of Exile API
poe.ninja API
Testing

The project contains tests covering:

currency retrieval
filtering
sorting by price
sorting by 7-day price change
pagination
invalid pagination parameters
invalid sorting parameters
empty results