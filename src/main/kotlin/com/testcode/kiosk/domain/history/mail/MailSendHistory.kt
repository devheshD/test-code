package com.testcode.kiosk.domain.history.mail

import com.testcode.kiosk.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class MailSendHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val fromEmail: String,
    val toEmail: String,
    val subject: String,
    val content: String,
): BaseEntity()
