How to run the game:

1. Copy the log4j2.xml from svn available inside the following location : tiapd\config and save it to your desired location.
2. Copy the PresidentialRescue.jar executable from svn available inside the following location: tiapd\Games\Executable and save it to your desired location.
3. Ensure that jdk1.8 is installed on your OS
4. Open a command prompt, navigate to the location where you have saved PresidentialRescue.jar execute the following command with the 4 arguments:

java -jar PresidentialRescue.jar  << "full qualified path of jstack">> << "full qualified path of the folder location where you want to store stacktraces">> << "interval at which you want to take stack-traces in milliseconds">> << "full qualified path where you saved the log4j2.xml">>

Example:

Windows:
java -jar PresidentialRescue.jar "C:\Program Files\Java\jdk1.8.0_25\bin\jstack"   "C:\Users\Dipanjan\Desktop\StackTrace" "2000"  "C:\Users\Dipanjan\Desktop\log4j2.xml"

Linux:

java -jar PresidentialRescue.jar "/usr/lib/jvm/java-8-oracle/bin/jstack" "/home/dipanjan/Desktop/StackTrace/" "1000" "/home/dipanjan/Desktop/log4j2.xml" 


Note: Please include all the arguments inside double quotes " "
