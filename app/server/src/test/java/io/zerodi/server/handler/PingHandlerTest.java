package io.zerodi.server.handler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;

import io.zerodi.environment.service.CurrentClock;
import io.zerodi.ping.PingMessage;

public class PingHandlerTest {

    @InjectMocks
    private PingHandler handler;

    @Spy
    private CurrentClock currentClock;

    private PingMessage  pingMessage;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        pingMessage = new PingMessage("test", new Date());
    }

    @Test
    public void itShouldProduceResponseToPingMessage() throws Exception {
        // given
        Date responseDate = new Date();

        // when
        doReturn(responseDate).when(currentClock).getCurrentDate();
        PingMessage response = handler.handleMessage(pingMessage);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getPayload()).isEqualTo("response to: test");
        assertThat(response.getDate()).isEqualTo(responseDate);
    }
}
