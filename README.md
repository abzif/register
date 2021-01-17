# Description
This is a simple **spring-boot** application which demonstrates usage of 3 operations on registers - **recharge**, **transfer**, **getCurrentBalances**. A simple command line UI allows to perform these operations on predefined registers.

# Libraries used
* **spring boot** - application skeleton was created using [Spring Initializr](https://start.spring.io)
* **spring boot jpa support** (hibernate library is used as JPA implementation)
* **liquibase** - database schema and data migration library
* **h2** - embedded, sql compatible database
* **junit5** - test support library
* **mockito** - mocking library

# Architecture
The application is very simple and contains of a few files only
* **RegisterService** - this class implements the required operations (recharge, transfer, getCurrentBalances)
* **Register**, **RegisterRepository** - database entity mapping and database repository which allows create/update/delete/find operations on registers
* **RegisterApplication** - the main class to launch
* **RegisterCmdLineRunner** - console UI which allows to execute operations on registers. It is for demo purposes only. In real application **RegisterService** would be used by UI web layer or exposed via REST api
* **db.changelog-master.xml** - database migration file, creates and populates registry table in the database
* **application.properties** - configuration file
* **RegisterServiceTest** - unit test of **RegisterService** class


# How to start
Launch class **com.example.register.RegisterApplication**. A database will be created in **user.home** directory (if does not exist yet) and populated with initial data. Command line ui will be available on console. The UI allows to execute 3 required commands on registers.