# Receipt Processor

A Spring Boot application that processes receipts and calculates reward points based on various rules.

## Features

- Process receipts and calculate reward points
- REST API endpoints for submitting receipts and retrieving points
- Validation for receipt data
- Error handling with appropriate HTTP status codes
- In-memory storage for receipts and points

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Spring Boot 3.2.0

## Installation

1. Clone the repository:
```bash
git clone https://github.com/JaveedShaik42/receipt-processor.git
cd receipt-processor
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8081`

## API Endpoints

### 1. Process Receipt
```http
POST /receipts/process
```

**Request Body:**
```json
{
    "retailer": "Target",
    "purchaseDate": "2022-01-02",
    "purchaseTime": "13:13",
    "total": "1.25",
    "items": [
        {
            "shortDescription": "Pepsi - 12-oz",
            "price": "1.25"
        }
    ]
}
```

**Response:**
```json
{
    "id": "adb6b560-0eef-42bc-9d16-df48f30e89b2"
}
```

### 2. Get Points
```http
GET /receipts/{id}/points
```

**Response:**
```json
{
    "points": 32
}
```

## Points Calculation Rules

1. One point for every alphanumeric character in the retailer name
2. 50 points if the total is a round dollar amount with no cents
3. 25 points if the total is a multiple of 0.25
4. 5 points for every two items on the receipt
5. If the trimmed length of the item description is a multiple of 3, multiply the price by 0.2 and round up to the nearest integer
6. 6 points if the day in the purchase date is odd
7. 10 points if the time of purchase is between 2:00pm and 4:00pm

## Error Handling

### 400 Bad Request
```json
{
    "message": "The receipt is invalid. Please verify input."
}
```

### 404 Not Found
```json
{
    "message": "No receipt found for that ID."
}
```

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           └── receiptprocessor/
│   │               ├── controller/
│   │               ├── model/
│   │               ├── service/
│   │               └── exception/
│   └── resources/
│       └── application.properties
└── test/
    └── java/
```

## Configuration

Application properties can be modified in `src/main/resources/application.properties`:

```properties
server.port=8081
spring.jackson.default-property-inclusion=non_null
```

## Testing

Run the tests using:
```bash
mvn test
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

- Spring Boot team for the amazing framework
- OpenAPI for API specification

## Contact

Javeed Shaik - [javeeds2542@gmail.com](mailto:javeeds2542@gmail.com)
Project Link: [https://github.com/JaveedShaik42/receipt-processor](https://github.com/JaveedShaik42/receipt-processor)
