package itt.lnc.news_aggregation.service.implementations;

import itt.lnc.news_aggregation.model.Article;
import itt.lnc.news_aggregation.model.User;
import itt.lnc.news_aggregation.service.NotificationSender;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailNotificationSender implements NotificationSender {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public void sendNotification(User user, List<Article> articles) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(user.getEmail());
            helper.setSubject("News Digest");
            helper.setFrom(fromEmail);

            String htmlContent = buildHtmlEmailBody(user, articles);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("Failed to send email to {}", user.getEmail(), e);
        }
    }

    private String buildHtmlEmailBody(User user, List<Article> articles) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<html><body>");
        stringBuilder.append("<h2>Hello ").append(user.getName()).append(",</h2>");
        stringBuilder.append("<p>Here are the latest articles just for you:</p><ul>");

        for (Article article : articles) {
            stringBuilder.append("<li>")
                    .append("<h3>").append(article.getTitle()).append("</h3>")
                    .append("<p>").append(article.getDescription()).append("</p>")
                    .append("<p><a href=\"").append(article.getArticleUrl()).append("\">Read More</a></p>")
                    .append("<hr>")
                    .append("</li>");
        }

        stringBuilder.append("</ul>");
        stringBuilder.append("</body></html>");

        return stringBuilder.toString();
    }
}
