There are two main projects in this directory:
one is indeed/indeed.java and the other is resume/r.java

There are also wikinew.java and indeedplace.java which are for gainin information about companies i.e (type, industry, revenue , number of employee and locations) and obtaining latitude,longitude and address based on company name, city and state by using Google places API.


The program takes one argument i.e. (one , two, three, four, five or six).

Objective:
  indeed.java: Collecting information about number of Jobs, Type of Jobs, pay of the jobs and the companies that provide the jobs in that city.
  
r.java:  Collecting information about number of job seeker, their level of education and experience and number of jobs and companies in each city.

Dependencies:
The files utilize functionalities from following external libraries:
		a. jsoup
		b. mysql-connector-java
		c. selenium-server



 
In indeed.java and r.java , the argument is one of the following:
one, two, three, four, five, six

when you enter one, 10 states will be crawlled sequentially. You can open a "screen" and run the program with another argument to have programs running in paralle to spped up the crawling speed.

In order to check whether the crawler is executed correctly for each state, refer to indeed_cities.txt. This file has the number of cities per state so by using a sql query on each state and crawl_time on the month that crawler is executed, the number of cities for each state should match indeed_cities.txt.

selenium problem resolution:
==================================================================================


If there is any problem related to selenium on server, refer to selenium problem resolution file, and run the commands on v75.


Jobs Table:
+------------+-------------+------+-----+---------------------+-------+
| Field      | Type        | Null | Key | Default             | Extra |
+------------+-------------+------+-----+---------------------+-------+
| city       | varchar(50) | NO   | PRI | NA                  |       |
| state      | varchar(2)  | NO   | PRI | NA                  |       |
| crawl_time | timestamp   | NO   | PRI | 0000-00-00 00:00:00 |       |
| fullTime   | int(10)     | YES  |     | NULL                |       |
| partTime   | int(10)     | YES  |     | NULL                |       |
| contract   | int(10)     | YES  |     | NULL                |       |
| temp       | int(10)     | YES  |     | NULL                |       |
| commission | int(10)     | YES  |     | NULL                |       |
| intern     | int(10)     | YES  |     | NULL                |       |
| k20        | int(10)     | YES  |     | NULL                |       |
| k25        | int(10)     | YES  |     | NULL                |       |
| k30        | int(10)     | YES  |     | NULL                |       |
| k35        | int(10)     | YES  |     | NULL                |       |
| k40        | int(10)     | YES  |     | NULL                |       |
| k45        | int(10)     | YES  |     | NULL                |       |
| k50        | int(10)     | YES  |     | NULL                |       |
| k55        | int(10)     | YES  |     | NULL                |       |
| k60        | int(10)     | YES  |     | NULL                |       |
| k65        | int(10)     | YES  |     | NULL                |       |
| k70        | int(10)     | YES  |     | NULL                |       |
| k75        | int(10)     | YES  |     | NULL                |       |
| k80        | int(10)     | YES  |     | NULL                |       |
| k85        | int(10)     | YES  |     | NULL                |       |
| k90        | int(10)     | YES  |     | NULL                |       |
| k95        | int(10)     | YES  |     | NULL                |       |
+------------+-------------+------+-----+---------------------+-------+


Companies Table:
+---------------------+---------------+------+-----+---------------------+----------------+
| Field               | Type          | Null | Key | Default             | Extra          |
+---------------------+---------------+------+-----+---------------------+----------------+
| id                  | int(11)       | NO   | PRI | NULL                | auto_increment |
| city                | varchar(50)   | NO   | MUL | NA                  |                |
| state               | varchar(2)    | NO   |     | NA                  |                |
| crawl_time          | timestamp     | NO   |     | 0000-00-00 00:00:00 |                |
| company             | varchar(500)  | YES  |     | NULL                |                |
| jobsNumber          | bigint(20)    | YES  |     | NULL                |                |
| latitude            | decimal(9,6)  | NO   |     | 0.000000            |                |
| longitude           | decimal(9,6)  | NO   |     | 0.000000            |                |
| address             | varchar(250)  | YES  |     | NULL                |                |
| addr_status         | varchar(30)   | YES  |     | NULL                |                |
| type                | varchar(300)  | YES  |     | NULL                |                |
| industry            | varchar(200)  | YES  |     | NULL                |                |
| revenue             | decimal(19,2) | YES  |     | NULL                |                |
| number_of_employees | int(11)       | YES  |     | NULL                |                |
| number_of_locations | int(11)       | YES  |     | NULL                |                |
| comment             | varchar(30)   | YES  |     | NULL                |                |
+---------------------+---------------+------+-----+---------------------+----------------+

resume table:
+------------+-------------+------+-----+-------------------+-------+
| Field      | Type        | Null | Key | Default           | Extra |
+------------+-------------+------+-----+-------------------+-------+
| city       | varchar(50) | NO   | PRI | NULL              |       |
| state      | varchar(2)  | NO   | PRI | NULL              |       |
| crawl_time | timestamp   | NO   | PRI | CURRENT_TIMESTAMP |       |
| y0_1       | int(11)     | YES  |     | NULL              |       |
| y1_2       | int(11)     | YES  |     | NULL              |       |
| y3_5       | int(11)     | YES  |     | NULL              |       |
| y6_10      | int(11)     | YES  |     | NULL              |       |
| y10_       | int(11)     | YES  |     | NULL              |       |
| doctor     | int(11)     | YES  |     | NULL              |       |
| master     | int(11)     | YES  |     | NULL              |       |
| bachelor   | int(11)     | YES  |     | NULL              |       |
| associate  | int(11)     | YES  |     | NULL              |       |
| diploma    | int(11)     | YES  |     | NULL              |       |
+------------+-------------+------+-----+-------------------+-------+

resumeJobs table:
+------------+------------------+------+-----+-------------------+-------+
| Field      | Type             | Null | Key | Default           | Extra |
+------------+------------------+------+-----+-------------------+-------+
| city       | varchar(50)      | NO   | PRI | NULL              |       |
| state      | varchar(2)       | NO   | PRI | NULL              |       |
| crawl_time | timestamp        | NO   | PRI | CURRENT_TIMESTAMP |       |
| jobTitle   | varchar(500)     | NO   |     | NULL              |       |
| jobCount   | int(10) unsigned | YES  |     | NULL              |       |
+------------+------------------+------+-----+-------------------+-------+
resumeCompanies:
+--------------+------------------+------+-----+-------------------+-------+
| Field        | Type             | Null | Key | Default           | Extra |
+--------------+------------------+------+-----+-------------------+-------+
| city         | varchar(50)      | NO   | PRI | NULL              |       |
| state        | varchar(2)       | NO   | PRI | NULL              |       |
| crawl_time   | timestamp        | NO   | PRI | CURRENT_TIMESTAMP |       |
| companyName  | varchar(500)     | NO   |     | NULL              |       |
| companyCount | int(10) unsigned | YES  |     | NULL              |       |
+--------------+------------------+------+-----+-------------------+-------+

