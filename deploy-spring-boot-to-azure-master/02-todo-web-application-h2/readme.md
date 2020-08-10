# Todo Web Application using Spring Boot and H2 In memory database

Run com.in28minutes.springboot.web.SpringBootFirstWebApplication as a Java Application.

Runs on default port of Spring Boot - 8080 

## Can be run as a Jar or a WAR

`mvn clean install` generate a war which can deployed to your favorite web server.

## Web Application

- http://localhost:8080/login with in28minutes/dummy as credentials
- You can add, delete and update your todos
- Spring Security is used to secure the application
- `com.in28minutes.springboot.web.security.SecurityConfiguration` contains the in memory security credential configuration.

## H2 Console

- http://localhost:8080/h2-console
- Use `jdbc:h2:mem:testdb` as JDBC URL 


## Plugin Initial Configuration
```
<plugin>
	<groupId>com.microsoft.azure</groupId>
	<artifactId>azure-webapp-maven-plugin</artifactId>
	<version>1.7.0</version>
</plugin>

```

## Plugin Final Configuration
```
			<plugin>
				<groupId>com.microsoft.azure</groupId>
				<artifactId>azure-webapp-maven-plugin</artifactId>
				<version>1.7.0</version>
				<configuration>
					<schemaVersion>V2</schemaVersion>
					<resourceGroup>todo-web-application-h2-rg</resourceGroup>
					<appName>todo-web-application-h2-in28minutes</appName>
					<pricingTier>B1</pricingTier>
					<region>westeurope</region>
					<runtime>
						<os>windows</os>
						<javaVersion>11</javaVersion>
						<webContainer>tomcat 9.0</webContainer>
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
## Deploy application in azure 
```
before , execute mvn clean install  to create a war file 

az login 
	- using maven plugin 
mvn azure-webapp:config               configuring azure-webapp-maven-plugin 
	1. linux
	2. windows <-
	3 docker 
	javaVersion   21. 11.0.2_2020 <-
	webContainer  16. tomcat 9.0  <-
	Confirm (Y/N) ? :  Y

After modification by the plugin 
change appName , resourceGroup ,pricingTier...  in pom.xml 

mvn azure-webapp:deploy               to deploy into cloud 
```

## test application
```
	- url 
http://todo-web-application-h2-in28minutes.azurewebsites.net
user: in28mintes
pass: dummy

http://todo-web-application-h2-in28minutes.azurewebsites.net/h2-console
Diver Class  : org.h2.Driver
JDBC URL : jdbc:h2:mem:testdb
username : sa 

```