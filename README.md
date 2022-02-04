
# Mailer: A Java Email Application

Mailer is a Java application that allows you to send out emails. Has a driver for sending simple emails to a

single recipient and supports command line usage with file input for emailing html to multiple recipients. 


Note: Generating and using an application specific password for the email service you use  
is highly recommended. 


Note: This application was developed and tested with Java 11. It may or may not work with other versions of Java. 
 

## How to Compile

	javac -cp "activation.jar;javax.mail.jar" Mailer.java


## How to Run in Driver Mode

	java -cp "activation.jar;javax.mail.jar;." Mailer 


## How to Run with Command Line Arguments

	java -cp "activation.jar;javax.mail.jar;." Mailer [service] [recipients] [from] [password] [subject] [message]


[service]: The respective number of one of the following email service providers:
1) iCloud
2) Gmail
3) Outlook

[recipients]: A csv file containing all recipients. Important: It ignores the first line on the assumption that it is the column header. 
Since it only contains a single column, it is basically a list with an email on each line.

[from]: The sender's email address.

[password]: The (hopefully application specific) password of the sender.

[subject]: The subject line of the email.

[message]: An html file containing the message.


Example usage:
	
	java -cp "activation.jar;javax.mail.jar;." Mailer 1 emails.csv myemail@icloud.com mypassword Hello! message.html


Known bug: When using Mailer form the command line, multi-word subjects cannot be used.


Author: Benjamin Sheth
All rights reserved, unless otherwise stated. Anyone may run this software. This software may not be sold. 
No data or passwords are saved. You assume all and any risks by running the software. 