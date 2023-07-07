package com.TBmail.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;


import com.mongodb.client.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Properties;

import org.bson.Document;

@SpringBootApplication
public class EmailServiceApplication {
	
	Deque<String> lastNewsUrls = new ArrayDeque<>();
	String lastSent=new String();
	@Autowired
	private EmailSenderService senderService;
	
	
	public static void main(String[] args) {
        
		/*MongoClient mongoClient =MongoClients.create("mongodb://localhost:27017");
		MongoDatabase database= mongoClient.getDatabase("TB");
		MongoCollection<Document> collection= database.getCollection("data");
		Document doc= new Document("eMail","oznigolyan3@stu.khas.edu.tr").append("name", "MasisOz");
		collection.insertOne(doc);
		mongoClient.close();*/
	
		
		SpringApplication.run(EmailServiceApplication.class, args);
	}

	
	
	@EventListener(ApplicationReadyEvent.class)
	public void sendMail() {
		MongoDatabase db = getDb();
		MongoCollection<Document> collection = db.getCollection("data");
		Document searchQuery = new Document();
		searchQuery.put("tags","https://trendbasket.net/tag/fenerbahce-beko/");
		FindIterable<Document> cursor = collection.find();
    	/*
    	 //Taglerin hepsini array'e çevirmek mantıklı olabilir
		try (final MongoCursor<Document> cursorIterator = cursor.cursor()) {
		    while (cursorIterator.hasNext()) {
		        var x= cursorIterator.next().get("tags"); 
		        if(x.getClass()== ArrayList.class) {
		        	@SuppressWarnings("unchecked")
					ArrayList<String> y=(ArrayList<String>) x;
		        	for(int i=0; i< y.size(); i++) {
		        		System.out.println(y.get(i));
		        	}
		        }
		        else
		        	System.out.println(x);
		        
		        System.out.println("---------");
		    }
		}
		*/
		String tag="https://trendbasket.net/tag/boston-celtics/";
		while(true) { 
			System.out.println("-------------------");
			//System.out.println("dequeue: "+lastNewsUrls);
			ArrayList<String> urls=new ArrayList<String>(); 
			for(int i=0; i<20; i++) {
				//System.out.println(LastNews.getNewsUrl(MailContent.getHtml(tag)));
				String t1=LastNews.getNewsUrl(MailContent.getHtml(tag));
				urls.add(t1);
				
			}
			
			
			if(lastNewsUrls.size()==0) {
				lastSent=urls.get(urls.size()-1);//urls.get(2);
				System.out.println("last sent is:	 "+lastSent);	
				
			}
			
			
			if(!(urls.get(0).equals(lastSent))) { /////////
				System.out.println("mail is being sent");
				ArrayList<String> mailUrls=new ArrayList<String>();
				int index=urls.indexOf(lastSent);
				
				if(index!=-1) {
					for(int i=index-1; i>=0; i--) {
						mailUrls.add(urls.get(i));
					}
				}
				else{
					for(int i=0; i<urls.size(); i++) {
						mailUrls.add(urls.get(i));
					}
				}
				
				
				/*for(int i=0; i<mailUrls.size(); i++) {
					System.out.println(mailUrls.get(i));
				}*/
				//send mail
				for(int i=0; i<mailUrls.size(); i++) {
					System.out.println("Following news will be sent: "+mailUrls.get(i));
					//senderService.sendEmail("masis.oznigolyan@tekfen.com.tr","TB özet "+Integer.toString(i+1), MailContent.getContent(mailUrls.get(i)));
					lastSent=mailUrls.get(i);
					if(lastNewsUrls.size()<20) {
						lastNewsUrls.addFirst(mailUrls.get(i));
					}
					else {
						lastNewsUrls.removeLast();
						lastNewsUrls.addFirst(mailUrls.get(i));
					}
				}
				
				
			}
			
			else {
				System.out.println("New news is not found");
			}
			
			LastNews.resetIndex();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
			LocalDateTime now = LocalDateTime.now();  
			System.out.println("Waiting");
			System.out.println("current date and time is: "+dtf.format(now));
			wait(10);//wait(1000*60*60);
			
		}		
	}
	
	
	
	public MongoDatabase getDb() {
		Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream("C:\\Users\\masis.oznigolyan\\Desktop\\EmailService\\src\\main\\resources\\application.properties");
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String mongoURI = properties.getProperty("mongodb.uri");
        String databaseName = properties.getProperty("mongodb.database");

        MongoClient mongoClient = MongoClients.create(mongoURI);
        return mongoClient.getDatabase(databaseName);
	}
	
	public void wait(int ms)
	{
	    try
	    {
	        Thread.sleep(ms);
	    }
	    catch(InterruptedException ex)
	    {
	        Thread.currentThread().interrupt();
	    }
	}
}
