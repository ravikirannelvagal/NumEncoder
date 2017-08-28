# NumEncoder
The project can be built from the command prompt/terminal by executing Maven command "mvn clean package". The jar file "NumEncoder-0.0.1-SNAPSHOT.jar" built inside the target folder can be run as "java -jar NumEncoder-0.0.1-SNAPSHOT.jar" to launch the program and run. While running through an IDE, the main method is present inside the class "com.T360.NumEncoder.Encoder"

The program doesn't expect any input from the user. The dictionary file is read when the program is run and stored in memory on a HashMap.
Input file is automatically picked up by the program and calculates the equivalent encoding based on words present in the dictionary and prints the output on the console
