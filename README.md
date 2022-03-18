<h1>Web Scraper</h1>

<h2> Application running URL </h2>
<h3> https://subash-web-scraper.herokuapp.com/ </h3>

<h2>What it does</h2>

<p>Web Scraper is a web application that helps to crawl the given (URL) web page recursively and provides the HTTP(s) links and phone numbers in it. Based on the API call application can return the HTML content as a file for the URL and the links or phone numbers or both in the given Webpage</p>

----
<h2>Solution Description</h2>

 - The Application exposes the various APIs, users can utilize based on their needs. Particularly POST API's getHtmlLinks and getPhoneNumberCrawls are takes input as URL and a requiredDepth, which indicates how deep the system should crawl recursively and fetch data.
 - Choose wisely the requiredDepth value. Increased number leads request wait time. Ideally the requiredDepth can be 1 to 5. 
 - The API getPhoneNumberCrawls also store URL and the corresponding phone numbers in mongo DB.
 - Refer the [swagger yml](https://github.com/subishsubash/web-scraper/blob/main/src/main/resources/api.yml) for the API request and response details.
 - The request and response of the application are properly logged and most of the error cases are handled with proper logger and response.
 - Utilize the [Postman collection](https://github.com/subishsubash/web-scraper/blob/main/Web%20Scraper.postman_collection.json) for the Heroku deployment.
----

<h2>API's Exposed in Web Scraper</h2>

| Method | Operation Id | URI | Description |  
|:-----------|:-----------|:------:|:-----------|  
| GET | getHtmlFile | https://subash-web-scraper.herokuapp.com/v1/webscraper/htmlfile?url=https://www.google.com/ | Fetch the HTML content for given URL (webpage) and return as a HTML file |  
| POST | getHtmlLinks | https://subash-web-scraper.herokuapp.com/v1/webscraper/htmlcrawls | Fetch and return all HTTP or HTTPS links from the given URL (webpage) recursively |
| GET | getPhoneNumber | https://subash-web-scraper.herokuapp.com/v1/webscraper/phoneNumber?url=http://www.wellclean.com/ | Fetch and return the phone number from the given URL (webpage) |  
| POST | getPhoneNumberCrawls | https://subash-web-scraper.herokuapp.com/v1/webscraper/phonenumbercrawls | Fetch and return all HTTP or HTTPS links and the corresponding phone number in it recursively <br> Store the same details in Database |

----

<h2>Languages & Frameworks</h2> 

1. Backend
- Java
- Spring Boot
- MongoDB
2. Backend Libaries
- org.jsoup
- org.openapitools (Swagger)
----

<h2>Deployment</h2>

```
docker pull mongo
docker pull subash12396/web-scraper:1.1
docker run --name mongodb -d mongo
docker run -p 8080:8080 --link mongodb:mongo --name web-scraper-container -d subash12396/web-scraper:1.1
```
<h2>Testing</h2>

>Microservice APIs
 ```
 http://192.168.99.100:8080
 ```
 >Note: Replace the IP with docker enviornment
 
 
