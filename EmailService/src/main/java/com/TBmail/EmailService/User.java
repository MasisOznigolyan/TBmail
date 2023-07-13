package com.TBmail.EmailService;


import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "users")
public class User {
	
	@NonNull
    @Id
    private String id;
		
	@NonNull
    @Field("uid")
    private String uid;

    @NonNull
    @Field("eMail")
    private String eMail;
    
    @NonNull
    @Field("categoryUrl")
    private List<String> categoryUrl;
    

	@Field("lastSentUrl")
	private String lastSentUrl;
    
	public void SetId(String id) {
		this.id=id;
	}

	public User() {
		this.uid=generateUniqueId();
	}

	@Override
	public String toString() {
		return "User [uid=" + uid + ", eMail=" + eMail + ", categoryUrl=" + categoryUrl + ", lastSentUrl=" + lastSentUrl
				+ "]";
	}
	private static String generateUniqueId() {
	    // Create a random UUID object
	    UUID uuid = UUID.randomUUID();
	    // Convert it to a string
	    String uniqueId = uuid.toString();
	    // Return the unique id
	    return uniqueId;
	  }
}

