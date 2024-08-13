<h1 align="center"> Spring Picpay </h1>
<p align="center">
  <a href="#-tecnologies">Technologies</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#-project">Project</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#memo-license">License</a>
  <p align="center">
  <img alt="License" src="https://img.shields.io/static/v1?label=license&message=MIT&color=49AA26&labelColor=000000">
</p>
</p>

<br>

## 🚀 Tecnologies

This project was developed with the following technologies:

- Java & Spring Boot
- H2 Database
- Postman 
- Git & Github 

 <br>

## 💻 Project

This project simulates a basic transaction system.

<br>

## :memo: License


This project is under license from MIT

<br>

## Endpoints 
<p>To test the application endpoints you can use the Postman, HttpPie, Insomnia...</p>

<br>

### Register user
 - <p> To register a user, you'll use: </p>
 
```sh
curl -X POST http://localhost:8080/users \
     -H "Content-Type: application/json" \
     -d '{
           "firstName": "Ms.",
           "lastName": "Smith",
           "document": "8cd39d0f19a1",
           "email": "Zane95@yahoo.com",
           "password": "2wKrztL1v03cBw7",
           "balance": 100.00,
           "userType": "COMMON"
         }'
```

<p> this should be the received response: </p>

```json
{
     "id": "1"
     "firstName": "Ms.",
     "lastName": "Smith",
     "document": "8cd39d0f19a1",
     "email": "Zane95@yahoo.com",
     "password": "2wKrztL1v03cBw7",
     "balance": 100.00,
     "userType": "COMMON"
}
```

<br>

### Transaction
 - <p> To perform a transaction between two users, follow this step: </p>
```sh
curl -X POST http://localhost:8080/transactions \
     -H "Content-Type: application/json" \
     -d '{
            "senderId": 1,
            "receiverId": 2,
            "value": 10
         }'
```
<p>The senderId and receiverId are the IDs generated during user registration.</p>



<br>

<p>Thanks for your attention, see you next time 💜</p>

