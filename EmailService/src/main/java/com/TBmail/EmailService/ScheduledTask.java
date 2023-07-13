package com.TBmail.EmailService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
public class ScheduledTask {
	

	@Autowired
	private UserCrud userCrud;
	
	String lastSent=new String();
	@Autowired
	private EmailSenderService senderService;

	@Scheduled(cron = "0 * * * * *")   //   0 10/2 * * * 
	public void sendMail() {
			
			//initializeDb();
			//while(true) {
				List<User> data=userCrud.getAllUsers();  //readData();
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
				//wait(5000);
				System.out.println("++++++++++++++++++++++++++++++++++++");
				//}
			
		}
}
