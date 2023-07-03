Project : 02_REPORTS_API

It is a Springboot Microservice.
Reports Api is one of the module in the Health Insurance System (HIS) Project.
It provides reports of HIS project on time basic based on the search criteria.
Ex : If we want to see a particular time range reports or based on plan name or plan status.
Reports Module provide the information about how many applications got approved or denied based on time period.
And also it will generate PDF and EXCEL format reports.

To develop this REST-API i have used below dependencies,
1) Web-starter
2) Devtools
3) Data-jpa-starter
4) MySQL-driver
5) H2 database
6) Lombok
7) Openpdf
8) Excel dependency
9) Swagger dependency.

Web-starter dependency is used to provide MVC based application design, it provides in-built server(tomcat) to run our application.

Devtools dependency is used to provide re-start(live re-loading) the server when make changes in the code automatically.

Data-jpa provide persistence logic to communicate with database.

MySQL driver is used to connect the Mysql database and it provides repository methods.

H2 database is a in-built database it stores the data while application is running.

Lombok is used to generate setter and getter for our application.
By using @Data we can generate setters and getters.

Openpdf and Excel dependencies are used to generate the PDF and Excel document of reports for our application.

Swagger dependency is used to generate the documentation of our application it provide necessary data like URL Mappings, input format, output format, method parameters, method return type etc.
Also swagger provides UI to test the functionality of Rest-API.

Note : Sample requirement pictures are provided below.
