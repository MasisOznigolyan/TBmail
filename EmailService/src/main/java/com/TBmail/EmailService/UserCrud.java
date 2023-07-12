package com.TBmail.EmailService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCrud{
	private UsersRepository userRepository;
	
	@Autowired
	public UserCrud(UsersRepository userRepository) {
		this.userRepository=userRepository;
	}
	
	public User createUser(User user) {
		
		return userRepository.save(user);
	}
	
	public User getUserById(String id) {
		return userRepository.findById(id).orElse(null);
	}
	
	public User getUserByUid(String uid) {
	    return userRepository.findByUid(uid).orElse(null);
	}
	
	/*
	 public User getUserByEmail(String eMail) {
	    return userRepository.findByEMail(eMail).orElse(null);
	 }
	 
	 */
	
	public List<User> getAllUsers() {
        return userRepository.findAll();
    }
	
	public User updateUser(User existingUser, User updatedUser) {
	    existingUser.setEMail(updatedUser.getEMail());
	    existingUser.setCategoryUrl(updatedUser.getCategoryUrl());
	    existingUser.setLastSentUrl(updatedUser.getLastSentUrl());
	    return userRepository.save(existingUser);
	}
	
	public User UpdateByUid(String uid, String field, List<String> value) {
		User user=getUserByUid(uid);
		if(user!=null) {
			switch (field) {
            case "eMail":
                user.setEMail(value.get(0));
                break;
            case "categoryUrl":
                user.setCategoryUrl(value);
                break;
            case "lastSentUrl":
            	user.setLastSentUrl(value.get(0));
            	break;
            default:
            	System.out.println("No such field as "+field);
                break;
			}
			return userRepository.save(user);
		}
		else {
			System.out.println("No such uid as "+uid);
			return null;
		}
	}
	
	public void deleteUser(String id) {
		userRepository.deleteById(id);
    }
}
