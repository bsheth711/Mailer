/**
 * File Header
 * 
 * @author Benjamin Sheth All rights reserved, unless otherwise stated.
 */
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.Scanner;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mailer {

    /**
     * A method that sends emails
     * 
     * @param service The email service provider of the user
     * @param to      The recipient of the email
     * @param from    The sender of the email
     * @param pw      The user's password
     * @throws MessagingException If the user cannot sign in because of 2FA or for some other reason
     *                            the email is unable to be sent
     */
    private static void sendMail(int service, String to, String from, char[] pw, String sub, String body)
            throws MessagingException {
        String host = "";

        // Sending email with icloud
        if (service == 1) {
            host = "smtp.mail.me.com";
        } // Sending email with gmail
        else if (service == 2) {
            host = "smtp.gmail.com";
        } // Sending email with outlook
        else if (service == 3) {
            host = "smtp.live.com";
        }

        // Getting system properties
        Properties props = System.getProperties();

        // Setting up mail server
        props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.port", "587");



        // Getting default Session object
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, new String(pw));
            }
        });

        // Creating default MimeMessage
        MimeMessage message = new MimeMessage(session);

        // Set From
        message.setFrom(new InternetAddress(from));

        // Set To
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

        // Set subject
        message.setSubject(sub);

        // TODO: Allow file input for message
        // Set actual message
        message.setText(body);

        Transport.send(message);
    }
    
    /**
     * A method that sends emails
     * 
     * @param service The email service provider of the user
     * @param to      The recipient of the email
     * @param from    The sender of the email
     * @param pw      The user's password
     * @throws MessagingException If the user cannot sign in because of 2FA or for some other reason
     *                            the email is unable to be sent
     * @throws FileNotFoundException if recipients or html file are not found
     */
    private static void sendMail(int service, File recipients, String from, String pw, String sub, File html)
            throws MessagingException, FileNotFoundException {
        Scanner scnr = new Scanner(html); // Warning: html file cannot be too large
        scnr.useDelimiter("\\Z");
        String body = scnr.next();
        scnr.close();
        
        String host = "";

        // Sending email with icloud
        if (service == 1) {
            host = "smtp.mail.me.com";
        } // Sending email with gmail
        else if (service == 2) {
            host = "smtp.gmail.com";
        } // Sending email with outlook
        else if (service == 3) {
            host = "smtp.live.com";
        }

        // Getting system properties
        Properties props = System.getProperties();

        // Setting up mail server
        props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.port", "587");



        // Getting default Session object
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, pw);
            }
        });

        // Creating default MimeMessage
        MimeMessage message = new MimeMessage(session);

        // Set From
        message.setFrom(new InternetAddress(from));

        // Set To
        Scanner scnr2 = new Scanner(recipients);
        scnr2.nextLine();
        while (scnr2.hasNext()) {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(scnr2.next()));
        }
        scnr2.close();

        // Set subject
        message.setSubject(sub);

        // TODO: Allow file input for message
        // Set actual message
        message.setContent(body, "text/html; charset=utf-8");

        Transport.send(message);
    }

    /**
     * The driver for the Mailer application. Allows you to send mail.
     * 
     * @param Args to be implemented later
     */
    public static void main(String[] Args) {
        
        // command line argument parsing
        if (Args.length > 0) {
            if (Args.length != 6) {
                System.out.println("Proper Usage:\n"
                    + "java Mailer [service] [recipients] [from] [password] [subject] [message]\n"
                    + "service: The respective number of the following email service providers:\n"
                    + "\t1)iCloud\n"
                    + "\t2)Gmail\n"
                    + "\t3)Outlook\n"
                    + "recipients: A csv file containing all recipients. Ignores the first line.\n"
                    + "from: The email of the sender.\n"
                    + "password: The application specific password of the sender.\n"
                    + "subject: The subject line for the email.\n"
                    + "message: An html file containing the message.\n");
                return;
            }
            
            try {
                sendMail(Integer.parseInt(Args[0]), new File(Args[1]), Args[2], Args[3], Args[4], 
                        new File(Args[5]));
            }
            catch (MessagingException mex) {
                System.out.println("Note: If you have 2 factor authentication you must generate"
                        + "an application specific password. Otherwise, double check your "
                        + "spelling.");
                mex.printStackTrace();
            }
            catch (FileNotFoundException fex) {
                System.out.println("Make sure file names are spelled correctly and in the same "
                        + "folder as Mailer.java.");
                fex.printStackTrace();
            }
            
            return;          
        }
        
        // Driver
        Scanner scnr = new Scanner(System.in);

        System.out.println("Please enter an option: ");
        System.out.println("s: send email");
        System.out.println("q: quit\n");
        String input = scnr.next();
        
        while (!input.equals("q")) {
            if (input.equals("s")) {
                do {
                    System.out.println("Please enter the number of the sender's service:");
                    System.out.println("1) iCloud");
                    System.out.println("2) Gmail");
                    System.out.println("3) Outlook\n");
                    input = scnr.next();
                } while (!(input.equals("1") || input.equals("2") || input.equals("3")));

                int service = Integer.parseInt(input);

                System.out.println("Enter the recipient: ");
                String to = scnr.next();

                System.out.println("Enter your email: ");
                String from = scnr.next();

                Console cnsl = System.console();
                char[] pw;

                if (cnsl != null) {
                    System.out.println("Enter your application specific password: ");
                    pw = cnsl.readPassword();
                } else {
                    System.out.println("No console available. Continue and echo password? Y/N");

                    if (!scnr.next().equalsIgnoreCase("y")) {
                        System.out.println("Process aborted.");
                        return;
                    }
                    
                    System.out.println("Enter password: ");
                    pw = scnr.next().toCharArray();
                }
                
                System.out.println("Enter a subject: ");
                String sub = scnr.next();
                
                System.out.println("Enter a message: ");
                String body = scnr.next();
                

                try {
                    sendMail(service, to, from, pw, sub, body);

                    // Zeroing the password
                    pw = null;

                    System.out.println("Email(s) successfully sent.\n");
                } catch (MessagingException mex) {
                    System.out.println("Note: If you have 2 factor authentication you must generate"
                            + "an application specific password. Otherwise, double check your "
                            + "spelling.");
                    mex.printStackTrace();
                }
            }

            System.out.println("Please enter a valid option: ");
            System.out.println("s: send email");
            System.out.println("q: quit\n");
            input = scnr.next();
        }

        scnr.close();
        System.out.println("Thank you for using Mailer.java.");
    }
}
