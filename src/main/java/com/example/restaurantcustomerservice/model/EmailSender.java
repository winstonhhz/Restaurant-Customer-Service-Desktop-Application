/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.restaurantcustomerservice.model;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.io.File;
import java.util.Properties;


/**
 *
 * @author Hello
 */
public class EmailSender {

    // Replace username and password with your real sender email and 16-character App Password from Google
    private final String username = "yourname@gmail.com";
    private final String password = "xxxx xxxx xxxx xxxx";
    
    public void sendEmail(String status, String staffMessage){
        
            Properties prop = new Properties();
            prop.put("mail.smtp.auth", true);
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", "587");
            prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");


        Session session = Session.getInstance(prop, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("from@gmail.com"));
                message.setRecipients(
                        Message.RecipientType.TO, InternetAddress.parse("to@gmail.com"));
                message.setSubject("Mail Subject");
                
                String staffMsg = "<p><b>Message From the staff:  </b>" + staffMessage + "</p>";
                String msg = """
                             """;
                // Email text
                switch (status){
                    case "Unsolved":
                        msg = """
                                     <html>
                                        <body>
                                            <img src = 'cid:ticket-img' style = 'max-width: 100%; max-height: 500px;'>                                      
                                            <h1>New Ticket</h1>
                                            <p>You've successfully created a new Ticket.</p>
                                            <p>We'll respond to this ticket in <b>1 - 3</b> business day. </p>
                                        </body>
                                     </html>
                                     """;
                        break;
                    case "In progress":
                        msg = """
                                     <html>
                                        <body>
                                            <img src = 'cid:ticket-img' style = 'max-width: 100%; max-height: 500px;'>                                      
                                            <h1>Ticket Update</h1>
                                            <p>A Staff is now working with your <b>Ticket</b>.</p>
                                            <p>Click the chat icon in the app for communicate with the staff.</p>
                                            """ +                                            
                                            (!(staffMessage.isEmpty() || staffMessage.isBlank()) ? staffMsg : "") +                                            
                                            """
                                        </body>
                                     </html>
                                     """;
                        break;
                    case "Solved": 
                        msg = """
                                     <html>
                                        <body>
                                            <img src = 'cid:ticket-img' style = 'max-width: 100%; max-height: 500px;'>                                      
                                            <h1>Ticket Solved</h1>
                                            <p>Your ticket have been solved by one of our staff.</p>
                                            <p>Hope you have a good day! </p>
                                            """ +                                            
                                            (!(staffMessage.isEmpty() || staffMessage.isBlank()) ? staffMsg : "") +                                            
                                            """                        
                                            <p>Restaurant Customer Support Team</p>
                                        </body>
                                     </html>
                               """;                        
                        break;
                        
                }
                // cid = content id
                MimeBodyPart textPart = new MimeBodyPart();
                textPart.setContent(msg, "text/html; charset=utf-8");
                
                
                     
                // Add attachment...
                MimeBodyPart attachmentBodyPart = new MimeBodyPart();
                File file = new File(getClass().getResource("/ticketImage/ticketImg.jpg").toURI());
                attachmentBodyPart.attachFile(file);
                attachmentBodyPart.setContentID("<ticket-img>");
                
                // Place both text and attachment to the body
                
                Multipart multipart = new MimeMultipart();

                multipart.addBodyPart(textPart);
                multipart.addBodyPart(attachmentBodyPart);      

                
                message.setContent(multipart);

                Transport.send(message);
                
            } catch (MessagingException me){
                System.out.println("Error: " + me);
            } catch (Exception e){
                e.printStackTrace();
            }
            
       
    }
}
    
 
