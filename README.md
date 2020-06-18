# Key features of the system

* When the system is running it begins to write time each second to the database.
* If the system were run with a parameter '-p' the system will display all records from the database.
* If the connection with the database was lost, the system will write all records to the database after connection will be established back. 
* The system is written on the Core Java 8. 

Before running the system needs to change the connection link to MongoDB instance in the 'MongoDbConfig' interface.

<h5>To run via command line with parameters</h5>

     mvn exec:java -Dexec.mainClass=Main -Dexec.args="-p"
     
<h5>To run via command line</h5>

     mvn exec:java -Dexec.mainClass=Main
     
<h5>To run the tests via command line</h5>

     mvn test
