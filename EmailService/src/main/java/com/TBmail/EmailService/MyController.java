package com.TBmail.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tb")
public class MyController {

    @Autowired
    private ScheduledTask myMicroservice;

    @GetMapping("/mail")
    public void performMicroserviceAction() {
        myMicroservice.sendMail();
    }
}
