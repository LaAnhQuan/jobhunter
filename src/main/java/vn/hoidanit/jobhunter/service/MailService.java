package vn.hoidanit.jobhunter.service;

public interface MailService {

    void sendSimpleEmail();

    void sendEmailSync(String to, String subject, String content, boolean isMultipart, boolean isHtml);

    void sendEmailFromTemplateSync(String to, String subject, String templateName);
}
