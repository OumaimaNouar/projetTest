package com.projetTest.controller;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
@Controller
public class HomeController {
 
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String home() {
		System.out.println("coucou");
		
		return "home";
	}
	@RequestMapping(value="/welcome",method=RequestMethod.GET)
	public String welcome() {
		System.out.println("welcome");
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	System.out.println("***************************************");
		            	System.out.println(new Date() + " Execution de ma tache");
		            	System.out.println("***************************************");
		               rdv();
		               
		            }
		        } 
		        , 1000, 60000);
		return "welcome";
	}
	public void rdv(){
		 try {
				/*URL url = new URL("http://www.val-de-marne.gouv.fr/booking/create/5440/0");
				URLConnection con;
				con = url.openConnection();
				InputStream is =con.getInputStream();
			    BufferedReader br = new BufferedReader(new InputStreamReader(is));
			    String line = null;
			    // read each line and write to System.out
			    while ((line = br.readLine()) != null) {
			    	if(line.contains("<input type=\"checkbox\" name=\"condition\""))
			            System.out.println(line);
			        }*/
			 WebClient webClient = new WebClient();
			 HtmlPage page = webClient.getPage("http://www.val-de-marne.gouv.fr/booking/create/5440/0");
			 HtmlInput checkBox = page.getElementByName("condition");
			 //System.out.println("attribut "+checkBox.getTypeAttribute());
			 checkBox.setChecked(true);
			 HtmlSubmitInput button = page.getElementByName("nextButton");
			 //System.out.println(page.asText());
			 HtmlPage page2 =button.click();
			 //System.out.println(page2.asText());
			 if(page2.asText().contains("Il n'existe plus de plage horaire libre pour votre demande de rendez-vous. Veuillez recommencer ultérieurement.\r\n" + 
			 		"Terminer")) {
				 System.out.println("PAS DE PLAGE HORAIRE DISPO");
				 //sendMail("PAS DE PLAGE HORAIRE DISPO");
			 }
			 else {
				 System.out.println("PLAGE HORAIRE DISPO");
				 sendMail("PLAGE HORAIRE DISPO");
			 }
		        /*searchBox.setValueAttribute("htmlunit");

		        HtmlSubmitInput googleSearchSubmitButton = 
		                          page.getElementByName("btnG"); // sometimes it's "btnK"
		        page=googleSearchSubmitButton.click();

		        HtmlDivision resultStatsDiv =
		                                page.getFirstByXPath("//div[@id='resultStats']");

		        System.out.println(resultStatsDiv.asText()); // About 309,000 results*/
		        webClient.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public void sendMail(String text) {

	      final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	      // Get a Properties object
	         Properties props = System.getProperties();
	         props.setProperty("mail.smtp.host", "smtp.gmail.com");
	         props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
	         props.setProperty("mail.smtp.socketFactory.fallback", "false");
	         props.setProperty("mail.smtp.port", "465");
	         props.setProperty("mail.smtp.socketFactory.port", "465");
	         props.put("mail.smtp.auth", "true");
	         props.put("mail.debug", "true");
	         props.put("mail.store.protocol", "pop3");
	         props.put("mail.transport.protocol", "smtp");
	         final String username = "test19165@gmail.com";
	         final String password = "oumaima1995";
	         try{
	         Session session = Session.getDefaultInstance(props, 
	                              new Authenticator(){
	                                 protected PasswordAuthentication getPasswordAuthentication() {
	                                    return new PasswordAuthentication(username, password);
	                                 }});

	       // -- Create a new message --
	         Message msg = new MimeMessage(session);

	      // -- Set the FROM and TO fields --
	         msg.setFrom(new InternetAddress("test19165@gmail.com"));
	         msg.setRecipients(Message.RecipientType.TO, 
	                          InternetAddress.parse("oumaimanouar5@gmail.com",false));
	         msg.setSubject("Rendez-vous");
	         msg.setText(text);
	         msg.setSentDate(new Date());
	         Transport.send(msg);
	         System.out.println("Message sent.");
	      }catch (MessagingException e){ System.out.println("Erreur d'envoi, cause: " + e);}
	}
}
