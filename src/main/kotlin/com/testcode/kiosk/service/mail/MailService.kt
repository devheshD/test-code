package com.testcode.kiosk.service.mail

import com.testcode.kiosk.client.mail.MailSendClient
import com.testcode.kiosk.domain.history.mail.MailSendHistory
import com.testcode.kiosk.domain.history.mail.MailSendHistoryRepository
import org.springframework.stereotype.Service

@Service
class MailService(
    private val mailSendClient: MailSendClient,
    private val mailSendHistoryRepository: MailSendHistoryRepository,
) {
    fun sendMail(fromEmail: String, toEmail: String, subject: String, content: String): Boolean {
        // 메일 전송 로직 구현
        val result = mailSendClient.sendEmail(fromEmail, toEmail, subject, content)
        if (result) {
            mailSendHistoryRepository.save(
                MailSendHistory(
                    fromEmail = fromEmail,
                    toEmail = toEmail,
                    subject = subject,
                    content = content
                )
            )
            return true
        }

        return false
    }
}
