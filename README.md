# CryptoCurrency Watcher REST API

This is a REST API for monitoring cryptocurrency prices and registering user notifications for price changes. It retrieves cryptocurrency data from the CoinLore API and provides endpoints for accessing and managing cryptocurrency information.

## Features

* Fetch and display a list of supported cryptocurrencies.
* Get the current price of a specific cryptocurrency by symbol.
* Register users for price change notifications.
* Periodically update cryptocurrency prices and notify registered users of price changes.

## Installation Requirements

To install and run this project, you will need:

* Java
* Spring Boot
* Spring Data JPA
* PostgreSQL
* RestTemplate
* MapStruct
* Maven

## Setting Up the Application 

To get started with the project, follow these steps:

1. Clone the repository:

    ```
    git clone https://github.com/KirylKandratski/cryptocurrency-watcher-rest-api.git
    ```

2. Create a PostgreSQL database and update the database configuration in application.yml with the appropriate credentials:

    ```
    spring:
      datasource:
         driver-class-name: org.postgresql.Driver
         url: jdbc:postgresql://localhost:5432/your_database_name
         username: your_username
         password: your_password
    ```

3. Build and run the application using Maven:
   ```
   cd cryptocurrency-watcher-rest-api
   mvn spring-boot:run
    ```

4. The API will be accessible at http://localhost:8080/api/cryptocurrencies.

## API Endpoints

1. Get a list of all supported cryptocurrencies:

    ```
    GET /api/cryptocurrencies
    ```
   Example request payload:
   ```
   Status: 200 OK
   Body: [
      {
         "id": "1",
         "symbol": "BTC",
         "currentPrice": 45000.00
      },
      {
         "id": "2",
         "symbol": "ETH",
         "currentPrice": 3500.00
      },
      ...
   ]
    ```

2. Get the current price of a cryptocurrency by symbol:

    ```
    GET /api/cryptocurrencies/price/{symbol}
    ```
   Example request payload:
   ```
   Status: 200 OK
   Body: {
         "id": "1",
         "symbol": "BTC",
         "currentPrice": 45000.00
   }
    ```

3. Register a user for price change notifications. Requires a JSON request body with username and symbol fields:
    ```
    POST /api/cryptocurrencies/notify
    ```
   Request Body:
   ```
   {
   "username": "john_doe",
    "symbol": "BTC"
   }
   ```
   Request Body:
   ```
   Status: 201 Created
   Body: {
      "id": 1,
      "username": "john_doe",
      "symbol": "BTC"
   }
   ```

## License

This project is licensed under the MIT License.