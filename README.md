
# Car Park API

This API is used to get nearest car parks which has available slots.

The project is based on Spring Boot.

### How to build

Using Maven wrapper to build the project.

```
./mvnw clean install
```

### How to run

Using docker-compose to bring MySQL as database, build the Docker image for project and running.

```
docker-compose up
```

**Note** this step takes a while to complete at first time.

### How to test

The API is expose at port 8080

Using Postman to play around API

Example http://localhost:8080/carparks/nearest?latitude=1.37326&longitude=103.897&page=1&per_page=3

Or import the [Postman collection file](https://github.com/tonynguyen1799/carpark/blob/master/docs/Car%20Park.postman_collection.json "download") here.

![Postman](https://raw.githubusercontent.com/tonynguyen1799/carpark/master/docs/Postman.png)