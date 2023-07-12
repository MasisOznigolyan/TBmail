package com.TBmail.EmailService;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.util.Pair;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/*
 * To Dos:
 * 
 * Okurken Id gelmiyor
 * tekrara çalıştırmada Uid ayarlama
 * Cron ve while(true) aynı çalışmıyor
 * initializeDb()'nin yeri nerede olmalı
 * read data userCrud'tan okunabilir
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

	//@Scheduled(cron = "* * * * * *")   //   0 10/2 * * * 
	@EventListener(ApplicationReadyEvent.class)
	public void sendMail() {
		
		//initializeDb();
		while(true) {
			ArrayList<User> data= readData();
			System.out.println(data);
			System.out.println("+-+-+-+-+-+-+-+");
			for(int i=0; i<data.size(); i++) {
				String tag=data.get(i).getCategoryUrl().get(0);
				ArrayList<String> urls=new ArrayList<String>(); 
				for(int j=0; j<20; j++) {
					//System.out.println(LastNews.getNewsUrl(MailContent.getHtml(tag)));
					String t1=LastNews.getNewsUrl(MailContent.getHtml(tag));
					urls.add(t1);
					
				}
				lastSent=data.get(i).getLastSentUrl();
				if(!(urls.get(0).equals(lastSent))) {
					System.out.println("mail is being sent");
					ArrayList<String> mailUrls=new ArrayList<String>();
					int index=urls.indexOf(lastSent);
					System.out.println(index);
					if(index!=-1) {
						LastNews.resetIndex();
						for(int j=index-1; j>=0; j--) {
							mailUrls.add(urls.get(j));
						}
					}
					else{
						for(int j=0; j<urls.size(); j++) {
							mailUrls.add(urls.get(j));
						}
					}
					
					
					for(int j=0; j<mailUrls.size(); j++) {
						
						System.out.println("Following news will be sent: "+mailUrls.get(j));
						//senderService.sendEmail(data.get(i).geteMail(),"TB özet",mailUrls.get(j) ); //MailContent.getContent(mailUrls.get(j))
						
						if(j==mailUrls.size()-1) {
							userCrud.UpdateByUid(Integer.toString(i), "lastSentUrl" , List.of(mailUrls.get(j)));
						}
						
					}
					
					
					
				}
				else
					System.out.println("New news is not found");
				
				
				
				LastNews.resetIndex();
				
				
			}
			
			
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
			LocalDateTime now = LocalDateTime.now();  
			System.out.println("Waiting...");
			System.out.println("current date and time is: "+dtf.format(now));
			wait(5000);
			System.out.println("++++++++++++++++++++++++++++++++++++");
			}
		
	}
	
	
	public ArrayList<User> readData() {
        Pair<MongoClient, MongoDatabase> db = getDb();

        // Access the collection and perform read operations
        MongoCollection<Document> collection = db.getSecond().getCollection("users");
        FindIterable<Document> documents = collection.find();

        List<Document> documentList = new ArrayList<>();
        documents.into(documentList);
        ArrayList<User> data= new ArrayList<User>();
        for (int i = 0; i < documentList.size(); i++) {
            Document document = documentList.get(i);
            
            
            //@SuppressWarnings("unchecked")
			//ArrayList<String> tags=(ArrayList<String>)document.get("tags");
            //String eMail=(String) document.get("eMail");
            //String lastSent=(String) document.get("lastSent");
            //
            //String id=(String) document.get("_id");
            String uid= (String) document.get("uid");
            String eMail=(String) document.get("eMail");
            @SuppressWarnings("unchecked")
			List<String> categoryUrl= (List<String>) document.get("categoryUrl");
            String lastSentUrl= (String) document.getString("lastSentUrl");
            
            
            User d= new User();
            //d.SetId(id);
            d.setUid(uid);
            d.setEMail(eMail);
            d.setCategoryUrl(categoryUrl);
            d.setLastSentUrl(lastSentUrl);
            //System.out.println(d);
            data.add(d);
        }

        disconnectDb(db);
        
        
        return data;
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
