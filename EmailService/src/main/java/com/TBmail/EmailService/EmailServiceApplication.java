package com.TBmail.EmailService;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.util.Pair;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

/*
 * To Dos:
 * 
 * Okurken Id gelmiyor
 * tekrar çalıştırmada dbyi temizle
 * tekrar çalıştırmada Uid ayarlama
 * Cron ve while(true) aynı çalışmıyor
 * initializeDb()'nin yeri nerede olmalı
 *												read data userCrud'tan okunabilir
 * birden fazla categoryUrl ve lastSentUrl için düzenlenmeli
 * */

@SpringBootApplication
@EnableScheduling
public class EmailServiceApplication {
	
	@Autowired
	private UserCrud userCrud;
	
	String lastSent=new String();
	@Autowired
	private EmailSenderService senderService;
	
	
	public static void main(String[] args) {
		
		SpringApplication.run(EmailServiceApplication.class, args);
		
	}

	
	
	
	public Pair<MongoClient,MongoDatabase> getDb() {
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
        Pair<MongoClient,MongoDatabase> ans= Pair.of(mongoClient, mongoClient.getDatabase(databaseName));
        return ans;
	}
	
	public void disconnectDb( Pair<MongoClient,MongoDatabase> db) {
		MongoClient mongoClient=db.getFirst();
		mongoClient.close();
	}	
	
	/*public void updateData(int index, String field, String value) { //use userCrud instead
		Pair<MongoClient, MongoDatabase> db = getDb();

	    MongoCollection<Document> collection = db.getSecond().getCollection("data");
        FindIterable<Document> documents = collection.find();
	    
	    List<Document> documentList = new ArrayList<>();
        documents.into(documentList);
	    
	    if (index >= 0 && index < documentList.size()) {
	        Document document = documentList.get(index);
	        
	        document.put(field, value);

	        Document filter = new Document("_id", document.get("_id"));
	        Document update = new Document("$set", new Document(field, value));

	        collection.updateOne(filter, update);
	        
	    } else {
	        System.out.println("Invalid index");
	        
	    }
	    disconnectDb(db);
	}*/
	
	
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
	
	 private void initializeDb() {     
        
        
        User user1 = new User();
        user1.setCategoryUrl(List.of("https://trendbasket.net/tag/fenerbahce-beko/"));
        user1.setEMail("oznigolyan3@gmail.com");
        user1.setLastSentUrl("https://trendbasket.net/fenerbahce-beko-yam-madari-istiyor/");
        userCrud.createUser(user1);
        
        
        User user2 = new User();
        user2.setCategoryUrl(List.of("https://trendbasket.net/tag/darussafaka-lassa/"));
        user2.setEMail("oznigolyan3@stu.khas.edu.tr");
        user2.setLastSentUrl("https://trendbasket.net/basketbol-sampiyonlar-liginde-temsilcilerimizin-gruplari-belli-oldu/");
        userCrud.createUser(user2);
        
        
        List<User> allUsers = userCrud.getAllUsers();
        for(int i=0; i<allUsers.size(); i++)
        	System.out.println(allUsers.get(i));

	    }

}
