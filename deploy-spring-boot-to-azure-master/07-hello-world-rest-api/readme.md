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
mvn azure-webapp:configure

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

### Installing curl and watch on windows
```
https://curl.haxx.se/windows/
    ---> curl in watch mode 
watch curl https://hello-world-rest-api-v2-in28minutes.azurewebsites.net/hello-world-bean-list

---> curl in watch mode  , execute a request every 100ms
watch -n 0.1 curl https://hello-world-rest-api-v2-in28minutes.azurewebsites.net/hello-world-bean-list

```

### Installing watch on MAC
```
http://osxdaily.com/2010/08/22/install-watch-command-on-os-x/
```

### Deploy
mvn azure-webapp:deploy


ARR infinity 
Application Request Routing Infinity 
  -- by default in on 
  all resquest seended to user , first one it s satified , the give the same response (same session of uner)
  for the rest api turn this option to --> Off

