http://redfin.com/
==========================

Crawling Redfin website and getting redfin price estimate.

Goal: To get home listing information from redfin.com for a set of zipcodes.

redfin.com has a option to download home listing data as a csv file for each zipcode.
So we can simply write a script to download csv files and then import csv files to the database.

The desired zipcodes are in zip.txt file.

downloadCSVfiles.java extracts download link for each zipcode and writes the urls in a file.(out.txt).

downloadRedfin_Script adds "wget" and naming format for each file to create a bash script for downloading csv files.
The download bash script name is RedfinLinks.sh

So by running RedfinLinks.sh file all of the csv files will be downloaded.

Now we have to import these csv files to the data base. SO first we have to create the table schema.

Here is the table schema:

+-----------------+--------------+------+-----+-------------------+----------------+
| Field           | Type         | Null | Key | Default           | Extra          |
+-----------------+--------------+------+-----+-------------------+----------------+
| id              | int(11)      | NO   | UNI | NULL              | auto_increment |
| sale_type       | varchar(50)  | YES  |     | NULL              |                |
| property_type   | varchar(50)  | YES  |     | NULL              |                |
| address         | varchar(250) | YES  |     | NULL              |                |
| city            | varchar(50)  | YES  |     | NULL              |                |
| state           | varchar(50)  | YES  |     | NULL              |                |
| zip             | int(10)      | YES  |     | NULL              |                |
| price           | int(10)      | NO   |     | -1                |                |
| Redfin_Estimate | int(10)      | YES  |     | NULL              |                |
| beds            | int(10)      | NO   |     | -1                |                |
| baths           | int(10)      | NO   |     | -1                |                |
| location        | varchar(100) | YES  |     | NULL              |                |
| sqft            | int(10)      | YES  |     | NULL              |                |
| lot_size        | int(10)      | YES  |     | NULL              |                |
| year_built      | int(10)      | YES  |     | NULL              |                |
| days_on_market  | int(10)      | YES  |     | NULL              |                |
| status          | varchar(50)  | YES  |     | NULL              |                |
| url             | varchar(200) | NO   | PRI | n/a               |                |
| source          | varchar(100) | NO   |     | n/a               |                |
| listing_id      | int(10)      | YES  |     | NULL              |                |
| latitude        | decimal(9,6) | YES  |     | NULL              |                |
| longitude       | decimal(9,6) | YES  |     | NULL              |                |
| crawl_time      | timestamp    | NO   | PRI | CURRENT_TIMESTAMP |                |
+-----------------+--------------+------+-----+-------------------+----------------+
 
The sql.java create a sql script to import these csv files into the above table. The result sql script name is 1600.sql.

The command to run 1600. sql file is as follows:

mysql  -u "root" "-phome123" -f -D "homeDB" < "1600.sql"

Now all the data is in the table but these csv files don't contain redfin estimate.

So I have a seperate program for getting redfin estimate.

Red.java goes through the redfin table and gets home url link and extracts redfin estimate.

SIn order to avoid being blocked by redfin I use switch between proxies.
