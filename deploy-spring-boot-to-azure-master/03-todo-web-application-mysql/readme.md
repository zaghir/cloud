# Todo Web Application using Spring Boot and MySQL as Database

Run com.in28minutes.springboot.web.SpringBootFirstWebApplication as a Java Application.

Runs on default port of Spring Boot - 8080

Application uses h2 database to run the tests.

## Can be run as a Jar or a WAR

`mvn clean install` generate a war which can deployed to your favorite web server.

We will deploy to Cloud as a WAR

## Web Application

- http://localhost:8080/login with in28minutes/dummy as credentials
- You can add, delete and update your todos
- Spring Security is used to secure the application
- `com.in28minutes.springboot.web.security.SecurityConfiguration` contains the in memory security credential configuration.


## Changes from H2 Application

#### pom.xml

```
<dependency>
	<groupId>com.h2database</groupId>
	<artifactId>h2</artifactId>
	<scope>test</scope>
</dependency>
<dependency>
	<groupId>mysql</groupId>
	<artifactId>mysql-connector-java</artifactId>
</dependency>
```

#### src/main/resources/application.properties

```
#spring.h2.console.enabled=true
#spring.h2.console.settings.web-allow-others=true

spring.jpa.hibernate.ddl-auto=update
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL55Dialect
spring.datasource.url=jdbc:mysql://localhost:3306/todos
spring.datasource.username=todos-user
spring.datasource.password=dummytodos
```

#### src/test/resources/application.properties

```
spring.jpa.hibernate.ddl-auto=create-drop
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1
spring.datasource.username=sa
spring.datasource.password=sa
```

#### public class Todo

```
@Size(min=10, message="Enter at least 10 Characters...")
@Column(name="description")
private String desc;
```
## My SQL

### Launching MySQL using Docker

```
docker run --detach --env MYSQL_ROOT_PASSWORD=dummypassword --env MYSQL_USER=todos-user --env MYSQL_PASSWORD=dummytodos --env MYSQL_DATABASE=todos --name mysql --publish 3306:3306 mysql:5.7
```

### My SQL Shell Client

- https://dev.mysql.com/downloads/shell/

- Install on mac using `brew install caskroom/cask/mysql-shell`.


```
Rangas-MacBook-Air:projects rangakaranam$ mysqlsh
MySQL Shell 8.0.15
Copyright (c) 2016, 2019, Oracle and/or its affiliates. All rights reserved.
Oracle is a registered trademark of Oracle Corporation and/or its affiliates.
Other names may be trademarks of their respective owners.

Type '\help' or '\?' for help; '\quit' to exit.

MySQL  JS > \connect todos-user@localhost:3306
Creating a session to 'todos-user@localhost:3306'
Please provide the password for 'todos-user@localhost:3306': 
Save password for 'todos-user@localhost:3306'? [Y]es/[N]o/Ne[v]er (default No): v
Fetching schema names for autocompletion... Press ^C to stop.
Your MySQL connection id is 37
Server version: 5.7.26 MySQL Community Server (GPL)
No default schema selected; type \use <schema> to set one.

 MySQL  localhost:3306 ssl  JS > \sql
Switching to SQL mode... Commands end with ;

 MySQL  localhost:3306 ssl  SQL > use todos
Default schema set to `todos`.
Fetching table and column names from `todos` for auto-completion... Press ^C to stop.

 MySQL  localhost:3306 ssl  todos  SQL > select * from todo ;
+----+--------------+---------+----------------------------+-------------+
| id | description  | is_done | target_date                | user        |
+----+--------------+---------+----------------------------+-------------+
|  1 | Default Desc | 0       | 2019-06-26 18:30:00.000000 | in28minutes |
+----+--------------+---------+----------------------------+-------------+
1 row in set (0.0032 sec)

```

### Create Todo Table for Production

```
create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table todo (id integer not null, description varchar(255), is_done bit not null, target_date datetime(6), user varchar(255), primary key (id)) engine=InnoDB
```

### Plugin Configuration Backup

```
<plugin>
	<groupId>com.microsoft.azure</groupId>
	<artifactId>azure-webapp-maven-plugin</artifactId>
	<version>1.7.0</version>
	<configuration>
		<schemaVersion>V2</schemaVersion>
		<resourceGroup>todo-web-application-mysql-rg</resourceGroup>
		<appName>todo-web-application-mysql-in28minutes</appName>
		<pricingTier>B1</pricingTier>
		<region>westeurope</region>
		<runtime>
			<os>linux</os>
			<javaVersion>java11</javaVersion>
			<webContainer>TOMCAT 9.0</webContainer>
		</runtime>
		<deployment>
			<resources>
				<resource>
					<directory>${project.basedir}/target</directory>
					<includes>
						<include>*.war</include>
					</includes>
				</resource>
			</resources>
		</deployment>
	</configuration>
</plugin>

```

### Get log 
```
---> get log for web application 
az webapp log tail --name todo-web-application-mysql-in28minute --resource-group todo-web-application-mysql-rg

---> log application to know all what hapens , for exemple connection to database ...
```

### restart application 
```
--->  2 way , 1-> in UIoverview --- restart application  
              2-> in CLI 
az webapp restart tail --name todo-web-application-mysql-in28minute --resource-group todo-web-application-mysql-rg

when we add varibles into configuration menu , we should to restart application 

```
## connection and configure connection to mysql 
```
 ### Using azure UI  
	- configure varibale 
	- configure ipClient and allow connection 
 ### Using azure CLI

-- install extension for azure CLI 
az extension add --name db-up            extension allow us to create a database 
az mysq up -g in28minutes-database -s todo-web-application-in28minutes-command-line -d todos -u todosuser -p
-g => resource group 
-s => serverName 
-d => database  it will be created directly on executing command 
-u => username  
-p => password

-- this command create a new server in the same resource group 
new we add a veribles for mysql connection 
configuration -> application settings -> new application settings  [name : value] [RDS_HOSTNAME : todo-web-application-in28minutes-command-line.mysql.database.azure.com  , RDS_PASSWORD:todospassword  , RDS_USERNAME: todosuser@todo-web-application-in28minutes-command-line, ]

-- restart application to mack change 
az webapp restart tail --name todo-web-application-mysql-in28minute --resource-group todo-web-application-mysql-rg

mysql --version             get version 
mysql  --host todo-web-application-in28minutes.mysql.database.azure.com --user todosuser@todo-web-application-in28minutes -p

create database todos;
use todos;
select * from todo ;



```
