# TopdownShooter

## Usage
### Connecting to a server
1. Download or clone this repository to your computer
2. Run the `client/TopdownShooter.java` file
3. You should now connect to the default server
### Running your own server
This is not properly supported at the moment, but it is still possible at the moment with the Java SDK
1. Open `client/Client.java` in an editor 
2. Enter your ip-address on line `23`
3. Compile the java code by running `javac *.java` when in the `client` folder
4. Run the `server/server.py` file with Python 2.7
5. Start the java client via `java Topdownshooter` from the `client` folder
## Requirements
In order to run the game, you will need to have [Java](https://java.com/en/download/) installed on your computer. If you're not sure wether or not you have Java installed, try typing `java` in a console window and see if it gives you an error. On the other hand, running the server requires Python 2.7. Try typing `python2` in your console and see if you get an error. If you do get an error, Python can be download [here](https://www.python.org/downloads/).
## Known bugs
* Server cannot run in Python 3 (Issue [#1](https://github.com/LogFlames/TopdownShooter/issues/1))
* Server is unprotected from invalid data (Issue [#2](https://github.com/LogFlames/TopdownShooter/issues/2))
## Contact
* [Website](https://troff.xyz)
* [troffaholic@gmail.com](mailto:troffaholic@gmail.com)
* Discord: troffaholic#1066
