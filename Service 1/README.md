# Service 1

### Description
1.	This will be consumer-facing service which will accept request from the user.
2.	The service should accept the data in JSON format within the request body.
3.	The service should also accept another input fileType as a parameter or header within the request. The value to this parameter could be either CSV or XML.
4.	The service should expose 3 endpoints as below
      a.	Store – to save new data
      b.	Update – to update existing data
      c.	Read – to read existing data, Read need not contain the fileType header or parameter
      Note the request itself could contain any data. Sample given below can be used.
      Sample body:
      { name: “Hello”, dob: “20-08-2020”, salary: “122111241.150”, age: 20 }
      Request Header or Parameter
      fileType = CSV
      fileType = XML
5.	The service should process the request received with the below constraints.
      a.	Validate the request completely. Including the data and data types
      b.	Pass on the validated information to Service 2. Below are the conditions to keep in mind.
      i.	The communication between Service 1 & Service 2 should NOT be http or https. Use some other means of communication.
      ii.	The data transferred to the second service should be encrypted and transformed to Google’s protocol buffer format.
      iii.	Only READ operation can be done over http
      
### How to Run
```shell
mvn spring-boot:run
```

### Swagger UI
You can access swagger UI in your browser to see the API documentation on below url.
```shell
http://localhost:8081/swagger-ui.html
```

*Note:* This will need Java 11 installed in your machine.