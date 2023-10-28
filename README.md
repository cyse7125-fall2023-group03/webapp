# Cloud Web Application
This Project is built using Springboot, Java, Postgresql. 
Docker and Jenkins are installed in the AMI.


## Github Actions
Added branch protection by preventing merge if any workflow fails.
Added unit test cases to the workflow to make sure.
AMI is built when the code is pushed to the main branch.


### Healthz API
```
curl --location --request GET 'http://localhost:8080/healthz' \
--header 'Cookie: JSESSIONID=60495B2988F7210B7555D0445DC94525'
```
