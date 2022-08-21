
# Car Park API

This API is used to get nearest car parks which has available slots.

The project is based on Java 11, Spring Boot 2.7.2 and MySQL 8.

### How to build

Using Maven wrapper to build the project.

```
./mvnw clean install
```

### How to run

Using Docker to containerize the API and docker-compose to running.

```
docker-compose up
```

**Note** this step takes a while to complete at first time.

To stop services

```
docker-compose down
```

### How to test

The API is expose at port 8080, you can use Postman to all the cases.

Example http://localhost:8080/carparks/nearest?latitude=1.37326&longitude=103.897&page=1&per_page=3

Or import the [Postman collection file](https://github.com/tonynguyen1799/carpark/blob/master/docs/Car%20Park.postman_collection.json "download") here.

![Postman](https://raw.githubusercontent.com/tonynguyen1799/carpark/master/docs/Postman.png)