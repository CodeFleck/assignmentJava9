
This app runs with Java 9.
To be able to run this springboot app you will need to first build it. To build and package into a single executable Jar file with Maven, use the below command.
You will need to run it from the project folder which contains the pom.xml file.

`mvn spring-boot:run`

This assignment was created with Java and Springboot. It simulates the backend of an online shop where an item can be bought.
The system counts how many times an item was viewed by keeping the number of requests and individual item receives within an hour.
If the item is seen more than 10 times within an hour the item price receives an increase of 10%. The system does not use any database
but holds the items, users, prices and views in memory.
   
**The following endpoints are exposed:**

(GET) http://127.0.0.1:8080/api/items (Return a Json with all items)

(GET) http://127.0.0.1:8080/api/items/{itemName} (Return a Json with one item)

(POST) http://127.0.0.1:8080/api/purchase/{itemName} (Return a Json that simulate the purchase of an item)

(POST) http://127.0.0.1:8080/users/sign-up (Sign an user up)

The authentication mechanism chosen was spring security with JWT, for the reliability of the libraries and convenience of implementation.

**POST to register a new user:**

`curl -H "Content-Type: application/json" -X POST -d '{
     "username": "admin",
     "password": "password"
 }' http://127.0.0.1:8080/users/sign-up`                   
           
**logs into the application (JWT is generated):**

`curl -i -H "Content-Type: application/json" -X POST -d '{
     "username": "admin",
     "password": "password"
 }' http://127.0.0.1:8080/login`   
 
**GET request to retrieve items where xxx.yyy.zzz is substituted by your generated JWT token:**

`curl -H "Authorization: Bearer xxx.yyy.zzz" http://localhost:8080/api/items` 

**Response**:

`[
     {
         "name": "Shirt",
         "description": "medium",
         "price": 22.0
     },
     {
         "name": "Pants",
         "description": "large",
         "price": 90.0
     },
     {
         "name": "Shoes",
         "description": "leather",
         "price": 120.0
     }
 ]`

**Example of GET request to retrieve the Shirt item:**

`curl -H "Authorization: Bearer xxx.yyy.zzz" http://127.0.0.1:8080/api/items/Shirt` 

**Response**: `{
               "name": "Shirt",
               "description": "medium",
               "price": 20.0
           }`
           
   **POST simulating the purchase of a Shirt**
   
   `curl -H "Accept: application/json" -H "Authorization: Bearer xxx.yyy.zzz" http://127.0.0.1:8080/api/purchase/Shirt`
    
   
**Response**: 

`{ "purchased": true }` or `{ "Out of stock": false }`   