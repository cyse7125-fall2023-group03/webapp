# Cloud Web Application
This Project is built using Springboot, Java, Postgresql. 
Docker and Jenkins are installed in the AMI.
The application creates the Custom Resource using client.


## Github Actions
Added branch protection by preventing merge if any workflow fails.
Added unit test cases to the workflow to make sure.
AMI is built when the code is pushed to the main branch.


### API's
```
curl --location --request GET 'http://localhost:8080/healthz' \
--header 'Cookie: JSESSIONID=60495B2988F7210B7555D0445DC94525'
```

```
curl --location 'localhost:8082/v1/actuator/health/liveness'
```

```
curl --location 'localhost:8082/v1/actuator/health/readiness'
```

```
curl --location '34.138.164.207:80/v1/http-check' \
--header 'Content-Type: application/json' \
--data '{
  "name": "string",
  "uri": "string",
  "is_paused": true,
  "num_retries": 5,
  "uptime_sla": 100,
  "response_time_sla": 100,
  "use_ssl": true,
  "response_status_code": 0,
  "check_interval_in_seconds": 86400
}'
```

```
curl --location '34.138.164.207:80/v1/http-check'
```

```
curl --location '34.138.164.207:80/v1/http-check/01f1ac64-67ff-4b53-a67f-840386bf91ec'
```

```
curl --location --request DELETE 'localhost:8082/v1/http-check/748455e7-b891-4d30-a6b1-1dbb3f65ea6e'
```


```
curl --location --request PUT 'localhost:8082/v1/http-check/748455e7-b891-4d30-a6b1-1dbb3f65ea6e' \
--header 'Content-Type: application/json' \
--data '{
  "name": "sdvsf",
  "uri": "string",
  "is_paused": true,
  "num_retries": 5,
  "uptime_sla": 100,
  "response_time_sla": 100,
  "use_ssl": true,
  "response_status_code": 0,
  "check_interval_in_seconds": 86400
}'
```

