# Todo and Hello World Rest APIs Connecting to H2 In memory database running on port 5000

Run com.in28minutes.rest.webservices.restfulwebservices.RestfulWebServicesApplication as a Java Application.


## Containerization

### Troubleshooting

- Problem - Caused by: com.spotify.docker.client.shaded.javax.ws.rs.ProcessingException: java.io.IOException: No such file or directory
- Solution - Check if docker is up and running!
- Problem - Error creating the Docker image on MacOS - java.io.IOException: Cannot run program “docker-credential-osxkeychain”: error=2, No such file or directory
- Solution - https://medium.com/@dakshika/error-creating-the-docker-image-on-macos-wso2-enterprise-integrator-tooling-dfb5b537b44e


### Creating Image

- create image 
mvn clean package  


### Creating Containers


- mvn clean package
- docker run in28min/todo-rest-api-h2:0.0.1-SNAPSHOT
- docker run -p 5000:5000 in28min/todo-rest-api-h2:0.0.1-SNAPSHOT
- docker run -p 5000:5000 in28min/todo-rest-api-h2:1.0.0.RELEASE

### Pushing image in docker hub 

   -- login in docker hub 
docker login 

docker push in28min/todo-rest-api-h2:1.0.0:RELEASE 

use your own docker hub id  in this case is in28min 
change again in dockerfile-maven-plugin 
        <configuration>
					<repository>in28min/${project.artifactId}</repository>
					<tag>${project.version}</tag>
					<skipDockerInfo>true</skipDockerInfo>
				</configuration>

To test execute API at http://localhost:5000/users/in28minutes/todos.

```
docker login
```


## Hello World URLS

- http://localhost:5000/hello-world

```txt
Hello World
```

- http://localhost:5000/hello-world-bean

```json
{"message":"Hello World - Changed"}
```

- http://localhost:5000/hello-world/path-variable/in28minutes

```json
{"message":"Hello World, in28minutes"}
```


## Todo JPA Resource URLs

- GET - http://localhost:5000/jpa/users/in28minutes/todos

```
[
  {
    "id": 10001,
    "username": "in28minutes",
    "description": "Learn JPA",
    "targetDate": "2019-06-27T06:30:30.696+0000",
    "done": false
  },
  {
    "id": 10002,
    "username": "in28minutes",
    "description": "Learn Data JPA",
    "targetDate": "2019-06-27T06:30:30.700+0000",
    "done": false
  },
  {
    "id": 10003,
    "username": "in28minutes",
    "description": "Learn Microservices",
    "targetDate": "2019-06-27T06:30:30.701+0000",
    "done": false
  }
]
```

#### Retrieve a specific todo

- GET - http://localhost:5000/jpa/users/in28minutes/todos/10001

```
{
  "id": 10001,
  "username": "in28minutes",
  "description": "Learn JPA",
  "targetDate": "2019-06-27T06:30:30.696+0000",
  "done": false
}
```

#### Creating a new todo

- POST to http://localhost:5000/jpa/users/in28minutes/todos with BODY of Request given below

```
{
  "username": "in28minutes",
  "description": "Learn to Drive a Car",
  "targetDate": "2030-11-09T10:49:23.566+0000",
  "done": false
}
```

#### Updating a new todo

- http://localhost:5000/jpa/users/in28minutes/todos/10001 with BODY of Request given below

```
{
  "id": 10001,
  "username": "in28minutes",
  "description": "Learn to Drive a Car",
  "targetDate": "2045-11-09T10:49:23.566+0000",
  "done": false
}
```

#### Delete todo

- DELETE to http://localhost:5000/jpa/users/in28minutes/todos/10001


## H2 Console

- http://localhost:5000/h2-console
- Use `jdbc:h2:mem:testdb` as JDBC URL 

## Azure Resource Commands

```
    Deploying Java Spring boot Rest Api Docker Image to Azure 
    In CLI 
    1. ---> create a resource group 
az group create --name container-resource-group --location westeurope

    2. ---> create a app service plan  
az appservice plan create --name container-service-plan --resource-group container-resource-group --sku P1v2 --is-linux

    3. ---> create a web app  
az webapp create --resource-group container-resource-group --plan container-service-plan --name todo-rest-api-h2-container --deployment-container-image-name in28min/todo-rest-api-h2:1.0.0.RELEASE

    4. ---> wait for 10min or more to finish deployment 

    5. ---> test url 
    http://todo-rest-api-h2-container.azurewebsites.net/jpa/users/in28minutes/todos
    Get
    Post
    Put
   
```


## Continuous Deployment

- https://docs.microsoft.com/en-us/azure/app-service/containers/app-service-linux-ci-cd
- https://docs.docker.com/docker-hub/webhooks/