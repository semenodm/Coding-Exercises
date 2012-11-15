package org.sdo.spring.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sdo.spring.integration.senders.ISender;
import org.sdo.spring.integration.senders.TestMessageHandaler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:sender-ctx.xml" })
public class SenderTest {

	private static final String MESSAGE_TO_SUCCESSFUL_SEND = "success message";

	private static String MESSAGE_TO_FAIL = "fail msg";

	@Autowired
	private ISender sender;

	@Autowired
	@Qualifier(value = "queueHandler")
	private TestMessageHandaler<String> mainQueueHandler;

	@Autowired
	@Qualifier(value = "failHandler")
	private TestMessageHandaler<String> failQueueHandler;
	@Autowired
	@Qualifier(value = "backupHandler")
	private TestMessageHandaler<String> backupQueueHandler;

	@Before
	public void setUp() {
		mainQueueHandler.reset();
		failQueueHandler.reset();
	}

	@Test
	public void i_want_my_sender_backup_messages_in_case_of_success() {
		sender.<String> sendMessage(MESSAGE_TO_SUCCESSFUL_SEND);
		// then
		assertThat("checking main queue", mainQueueHandler.sentMessage(), is(MESSAGE_TO_SUCCESSFUL_SEND));
		// and
		assertThat("checking backup queue", backupQueueHandler.sentMessage(), is(MESSAGE_TO_SUCCESSFUL_SEND));
		// and
		assertThat("checking fail queue", failQueueHandler.nothingHasSent(), is(true));
	}

	@Test
	public void i_want_my_sender_put_message_into_fail_queue_when_sending_failed() {

		mainQueueHandler.emulateFail();

		// when i send message
		sender.<String> sendMessage(MESSAGE_TO_FAIL);

		// then
		assertThat("checking main queue", mainQueueHandler.nothingHasSent(), is(true));
		// and
		assertThat("checking backup queue", backupQueueHandler.nothingHasSent(), is(true));
		// and
		assertThat("checking fail queue", failQueueHandler.sentMessage(), is(MESSAGE_TO_FAIL));
	}
}
