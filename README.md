# TopdownShooter

## Usage
### Connecting to a server
1. Download or clone this repository to your computer
2. Run the `client/TopdownShooter.java` file
3. You should now connect to the default server
### Running your own server
This is not properly supported at the moment, but it is still possible at the moment with the Java SDK
1. Open `client/Client.java` in an editor 
2. Set your port to 1024 on line `19` 
3. Enter your ip-address on line `23`
4. Compile, run the server and connect via the client
## Known bugs
* Server cannot run in Python 3 (Issue #1)
* Server is unprotected from invalid data (Issue #2)
