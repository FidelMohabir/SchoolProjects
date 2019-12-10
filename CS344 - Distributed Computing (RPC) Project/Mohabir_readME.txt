I ran this on secure shell client

1) How to run the files:
I included two .jar files which I used to run these programs

the server file MUST be on hawk.cs.qc.edu (I used that servers IP addresss)
the client file can be on any other bird but I personally used robin.cs.qc.edu

commands to run in birds server
java -jar Mohabir_CS344_P2_Server.jar

java -jar Mohabir_CS344_P2_Client.jar


2) Output files:

The server output tracks (commuter, attendant, train) disconnects and who enters the train/random number of commuters. 
I didn't post client side because of my implementation from project 1 

The client output tracks (commuter, attendant, train) disconnects and progression through the program.

3) Issues:
For some reason, sometimes (but very rarely), the attendants will leave early. To fix it restart the server itself. It might
have something to do with some value initializing weirdly. I believe these issues were also present in project 1 but I never
ran into them. 