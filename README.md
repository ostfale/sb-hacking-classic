# Links

+ Github Repo: https://github.com/hacking-with-spring-boot/hacking-with-spring-boot-classic-code

# Curl Command

+  curl -v localhost:8080/server # random dish

# Docker

## Using Paketo 

````bash
mvn spring-boot:build-image
#[INFO] Successfully built image 'docker.io/library/sb-hacking-classic:0.0.1-SNAPSHOT'

docker run -it -p 8080:8080 docker.io/library/sb-hacking-classic:0.0.1-SNAPSHOT
````