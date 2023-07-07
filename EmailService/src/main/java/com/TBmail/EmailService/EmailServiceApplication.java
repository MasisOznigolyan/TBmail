package com.TBmail.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.mongodb.client.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.bson.Document;

@SpringBootApplication
public class EmailServiceApplication {
	
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
		//senderService.sendEmail("masis.oznigolyan@tekfen.com.tr","Deneme 2 ","db deneme");
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
}
