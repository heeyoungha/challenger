package com.challenger.modules.event.service.event;

import com.challenger.infra.config.AppProperties;
import com.challenger.infra.mail.EmailMessage;
import com.challenger.infra.mail.EmailService;
import com.challenger.modules.account.Account;
import com.challenger.modules.event.domain.Enrollment;
import com.challenger.modules.event.domain.Event;
import com.challenger.modules.event.domain.event.EnrollmentEvent;
import com.challenger.modules.notification.domain.Notification;
import com.challenger.modules.notification.repository.NotificationRepository;
import com.challenger.modules.notification.domain.NotificationType;
import com.challenger.modules.study.domain.Study;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;

@Slf4j @Async
@Component
@Transactional
@RequiredArgsConstructor
public class EnrollmentEventListener {

    private final NotificationRepository notificationRepository;
    private final AppProperties appProperties;
    private final TemplateEngine templateEngine;
    private final EmailService emailService;

    @EventListener
    public void handleEnrollmentEvent(EnrollmentEvent enrollmentEvent) {
        Enrollment enrollment = enrollmentEvent.getEnrollment();
        Account account = enrollment.getAccount();
        Event event = enrollment.getEvent();
        Study study = event.getStudy();

        if (account.isStudyEnrollmentResultByEmail()) {
            sendEmail(enrollmentEvent, account, event, study);
        }

        if (account.isStudyEnrollmentResultByWeb()) {
            createNotification(enrollmentEvent, account, event, study);
        }
    }

    private void sendEmail(EnrollmentEvent enrollmentEvent, Account account,
                                                      Event event, Study study) {
        Context context = new Context();
        context.setVariable("nickname", account.getNickname());
        context.setVariable("link", "/study/" + study.getEncodedPath()
                                                        + "/events/" + event.getId());
        context.setVariable("linkName", study.getTitle());
        context.setVariable("message", enrollmentEvent.getMessage());
        context.setVariable("host", appProperties.getHost());
        String message = templateEngine.process("mail/simple-link", context);

        EmailMessage emailMessage = EmailMessage.builder()
                .subject("모임올래, " + event.getTitle() + " 모임 참가 신청 결과입니다.")
                .to(account.getEmail())
                .message(message)
                .build();

        emailService.sendEmail(emailMessage);
    }

    private void createNotification(EnrollmentEvent enrollmentEvent, Account account,
                                                             Event event, Study study) {
        Notification notification = new Notification();
        notification.setTitle(study.getTitle() + " / " + event.getTitle());
        notification.setLink("/study/" + study.getEncodedPath()
                                                     + "/events/" + event.getId());
        notification.setChecked(false);
        notification.setCreatedDateTime(LocalDateTime.now());
        notification.setMessage(enrollmentEvent.getMessage());
        notification.setAccount(account);
        notification.setNotificationType(NotificationType.EVENT_ENROLLMENT);
        notificationRepository.save(notification);
    }

}

