FROM adoptopenjdk/openjdk16:alpine-jre
ADD target/web-scraper-0.0.1-SNAPSHOT.jar web-scraper.jar
ENTRYPOINT ["java","-jar","web-scraper.jar"]
