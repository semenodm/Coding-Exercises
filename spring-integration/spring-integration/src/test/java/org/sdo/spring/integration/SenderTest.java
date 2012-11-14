package org.sdo.spring.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sdo.spring.integration.senders.ISender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:sender-ctx.xml"})
public class SenderTest {

	@Autowired
	private ISender sender;
	@Test
	public void i_want_my_sender_send_message_to_file() {
		String msg = "test message";
		//given a senders configuration
		//when i send message
		sender.<String>sendMessage(msg);
		//then
		//i can see the file created in destination folder 
		
	}
}
