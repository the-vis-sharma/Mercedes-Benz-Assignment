# Service 2

### Description
1.	Service should receive and decrypt the information passed by the previous service.
2.	Once decrypted the service should store the information either in CSV/XML file based on the input received in the fileType parameter in the previous service.
3.	If the data should be read and returned then the returned data has to be encrypted. Which Service 1 should decrypt and respond to the consumer.


### How to Run
```shell
mvn spring-boot:run
```

### Swagger UI
You can access swagger UI in your browser to see the API documentation on below url.
```shell
http://localhost:8082/swagger-ui.html
```

*Note:* This will need Java 11 installed in your machine.