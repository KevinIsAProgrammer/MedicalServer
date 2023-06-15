To run the server: `./mvnw spring-boot:run` 

By default, the server runs on port 8080 on localhost.

**Description**

This REST server is assumed to be part of a larger
family of medical services, which may need to reference
the identifier field for a patient.

The server assigns a unique identifier field (a randomly generated UUID) to each
patient when the record is created. All the other data (patient first name, last name, and date of birth) may or
may not be known when the patient is created  (for example, an unconscious patient admitted to 
an ER). 

The following four endpoints are available:

1. **POST** /create

**body**: A patient object without an identifier.

**returns**: Status 200 and a string consisting of the patient's newly assigned identifier.

**Examples**: `curl http://localhost:8080/create -H "Content-Type: application/json" -d '{"firstName": "John", "lastName": "Doe",dateOfBirth:"2000-12-25"}'`
prints the randomly generated uuid for the patient, such as `50c9f6e6-3769-4a15-a46e-cf221a6bc869`

2. **GET** /read?id=*some uuid goes in here...*

**returns**: Status 200 and the patient object if found, or
status 404 and an object containing debugging information if not.

**Examples**: Continuing on from our previous example, the command:

`curl http://localhost:8080/read?id=5dfc4fc0-8466-483b-9682-a5d9f90ad3e3 -H "Content-Type: application/json"
` 
would then return

`{"identifier":"5dfc4fc0-8466-483b-9682-a5d9f90ad3e3","firstName":"John","lastName":"Doe","dateOfBirth":"2000-12-25"}`

but if we search for an patient with an identifier that doesn't exist on our server 
( such as *ea6f42e3-4e83-4267-8a76-ff4c1c2b465e* ) using the command below:

`curl http://localhost:8080/read?id=ea6f42e3-4e83-4267-8a76-ff4c1c2b465e -H "Content-Type: application/json"`

we get the following result:
`{"timestamp":"2023-06-15T01:49:19.118+00:00","status":404,"error":"Not Found","path":"/read"}`


3. **GET** /readAll

returns: Status 200 and a list of all the patients

**Examples**: `curl http://localhost:8080/readAll -H "Content-Type: application/json"` resulted in `
[{"identifier":"5dfc4fc0-8466-483b-9682-a5d9f90ad3e3","firstName":"John","lastName":"Doe","dateOfBirth":"2000-12-25"}]`

4. **GET** /find?firstName=*some first name goes here...*

returns: Status 200 and a list of all the patients with the requested first Name

**Examples**
`curl http://localhost:8080/find?firstName=John -H "Content-Type: application/json"
` would result in
`[{"identifier":"5dfc4fc0-8466-483b-9682-a5d9f90ad3e3","firstName":"John","lastName":"Doe","dateOfBirth":"2000-12-25"}]`

but

`curl http://localhost:8080/find?firstName=Jon -H "Content-Type: application/json" `
would result in just an empty list
`[]`