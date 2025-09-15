package vn.hoidanit.jobhunter.controller;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.hoidanit.jobhunter.service.EmailService;
import vn.hoidanit.jobhunter.service.SubscriberService;
import vn.hoidanit.jobhunter.util.annotation.ApiMessage;

@RestController
@RequestMapping("/api/v1")
public class EmailController {
    public final EmailService mailService;
    public final SubscriberService subscriberService;

    public EmailController(EmailService mailService, SubscriberService subscriberService) {
        this.mailService = mailService;
        this.subscriberService = subscriberService;
    }


    @GetMapping("/email")
    @ApiMessage("Send simple email")
    @Scheduled(cron = "*/60 * * * * *")
    @Transactional
    public String sendSimpleEmail(){
        System.out.println("hẹ hẹ");
        this.subscriberService.sendSubscribersEmailJobs();
        return "ok";
    }
}
