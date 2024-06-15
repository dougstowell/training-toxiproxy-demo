# toxiproxy-demo

Rudimentary Spring Boot app that does nothing of interest, but allows demonstation of the following tech.

- Localstack
- Toxiproxy
- Cucumber

## Getting Started

1. Start the containers `docker compose up --build -d`
1. Create the S3 bucket `docker exec toxiproxy-demo-localstack awslocal s3api create-bucket --bucket toxiproxy-demo --create-bucket-configuration LocationConstraint=eu-west-2`
1. Set ENV VAR `AWS_ACCESS_KEY_ID`
1. Set ENV VAR `AWS_SECRET_ACCESS_KEY`
1. Create a new document on S3 `curl POST http://localhost:8080/api/v1/toxiproxy-demo/create`
1. View the document added to cache  `curl GET http://localhost:8080/api/v1/toxiproxy-demo/cache`
1. Resrt the contents of the cache  `curl POST http://localhost:8080/api/v1/toxiproxy-demo/reset`

