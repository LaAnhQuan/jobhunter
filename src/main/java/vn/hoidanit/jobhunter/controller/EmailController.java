package vn.hoidanit.jobhunter.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.hoidanit.jobhunter.service.MailService;
import vn.hoidanit.jobhunter.util.annotation.ApiMessage;

@RestController
@RequestMapping("/api/v1")
public class EmailController {
    public final MailService mailService;

    public EmailController(MailService mailService) {
        this.mailService = mailService;
    }


    @GetMapping("/email")
    @ApiMessage("Send simple email")
    public String sendSimpleEmail(){
        this.mailService.sendEmailFromTemplateSync("nitnit072004@gmail.com","test send email", "job");
        return "ok";
    }
}
