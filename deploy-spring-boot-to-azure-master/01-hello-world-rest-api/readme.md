# Hello World Rest API

### Running the Application

Run com.in28minutes.rest.webservices.restfulwebservices.RestfulWebServicesApplication as a Java Application.

- http://localhost:8080/hello-world

```txt
Hello World
```

- http://localhost:8080/hello-world-bean

```json
{"message":"Hello World"}
```

- http://localhost:8080/hello-world/path-variable/in28minutes

```json
{"message":"Hello World, in28minutes"}
```

### Plugin configuration

```
<plugin>
	<groupId>com.microsoft.azure</groupId>
	<artifactId>azure-webapp-maven-plugin</artifactId>
	<version>1.7.0</version>
</plugin>
```
				
### Azure CLI

- https://docs.microsoft.com/en-us/cli/azure/install-azure-cli?view=azure-cli-latest

### Final Plugin Configuration
```
<plugin>
	<groupId>com.microsoft.azure</groupId>
	<artifactId>azure-webapp-maven-plugin</artifactId>
	<version>1.7.0</version>
	<configuration>
		<schemaVersion>V2</schemaVersion>
		<resourceGroup>hello-world-rest-api-rg</resourceGroup>
		<appName>hello-world-rest-api-in28minutes</appName>
		<pricingTier>B1</pricingTier>
		<region>westeurope</region>
		<appSettings>
			<property>
				<name>JAVA_OPTS</name>
				<value>-Dserver.port=80</value>
			</property>
		</appSettings>
		<runtime>
			<os>linux</os>
			<javaVersion>java11</javaVersion>
			<webContainer>java11</webContainer>
		</runtime>
		<deployment>
			<resources>
				<resource>
					<directory>${project.basedir}/target</directory>
					<includes>
						<include>*.jar</include>
					</includes>
				</resource>
			</resources>
		</deployment>
	</configuration>
</plugin>

```
### Logging Configuration

```
-Dlogging.level.org.springframework=DEBUG
```

### using maven and cli linux to  configure application in azure
mvn azure-webapp:config 
   1. linuc 
   2. windows
   3. docker
   select -> 1
define value for javaVersion(Default: Java8 ) :
	1. Java 11
	2. Java 8
	select -> 1
confirm (Y/N) : Y


## deploy application in azure 
mvn azure-webapp:deploy
 after the command it throw an exception error -> failed to authenticate with azure 

install azure CLI first 
- https://docs.microsoft.com/en-us/cli/azure/install-azure-cli?view=azure-cli-latest

- in command line 
az login                            for login with account 
cd 01-hello-world-rest-api          change directory to folder 
mvn clean install                   clean build install jar application 
mvn azure-webapp:deploy             deploy jar in vm linux provide by azure like resources  

- pick up the url from console 
url it s like this    
https://hello-world-rest-api-in28minutes.azurewebsites.net
https://hello-world-rest-api-in28minutes.azurewebsites.net/hello-world
https://hello-world-rest-api-in28minutes.azurewebsites.net/hello-world-bean            


- deploy new version of this application 
after change in the application  
mvn clean install -DskipTests
mvn azure-webapp:deploy


## Log application 
 - from  advance tools   kudu 
	- Log stream  in menu 
	- Current Docker log
 - CLI  
az webapp log tail --name hello-world-rest-api-in28minutes --resource-group hello-world-rest-api-rg

ctr+c to terminet log stream


## region why
	- Availability , Performance and Legal Regulations 
		Availability , deploy application across the world , you have a higher availabilty of your application 
		Regulation is obviously regulrations. U.S gouvernment might not want data related to U.S citizens stored outside the U.S geography 