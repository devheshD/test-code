package com.testcode.kiosk.service.mail

import com.testcode.kiosk.client.mail.MailSendClient
import com.testcode.kiosk.domain.history.mail.MailSendHistory
import com.testcode.kiosk.domain.history.mail.MailSendHistoryRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.junit.jupiter.MockitoExtension

// 테스트가 시작될때 MockitoExtension을 사용하여 Mock 객체를 초기화합니다.
@ExtendWith(MockitoExtension::class)
class MailServiceTest {

    @Mock
    private lateinit var mailSendClient: MailSendClient

    @Mock
    private lateinit var mailSendHistoryRepository: MailSendHistoryRepository

    @Test
    fun `메일 전송 테스트`() {
        // given
        val mailService = MailService(mailSendClient, mailSendHistoryRepository)
        // stubbing
        Mockito.`when`(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
            .thenReturn(true)

        // when
        val result = mailService.sendMail("", "", "", "")

        // then
        assertThat(result).isTrue
        Mockito.verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory::class.java))
    }
}
