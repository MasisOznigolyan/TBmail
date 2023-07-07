package com.TBmail.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.mongodb.client.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.bson.Document;

@SpringBootApplication
public class EmailServiceApplication {
	
	@Autowired
	private EmailSenderService senderService;
	
	public static void main(String[] args) {
		
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
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        System.out.println("-------");
		System.out.println(database);
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
		//senderService.sendEmail("masis.oznigolyan@tekfen.com.tr","Deneme 2 ","db deneme");
	}
}
