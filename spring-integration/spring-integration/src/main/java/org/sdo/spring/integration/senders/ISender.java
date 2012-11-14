package org.sdo.spring.integration.senders;

public interface ISender {
	<MSG_TYPE> void sendMessage(MSG_TYPE msg);
}
