package com.testcode.kiosk.client.mail

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class MailSendClient {
    private val logger = LoggerFactory.getLogger(javaClass)
    fun sendEmail(fromEmail: String, toEmail: String, subject: String, content: String): Boolean {
        // 메일 전송 로직 구현
        logger.info("메일 전송")
        throw IllegalArgumentException("메일 전송")
        return true
    }
}
