package com.TBmail.EmailService;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersRepository extends MongoRepository<User, String> {
	Optional<User> findByUid(String uid);
	//Optional<User> findByEMail(String EMail);
}
