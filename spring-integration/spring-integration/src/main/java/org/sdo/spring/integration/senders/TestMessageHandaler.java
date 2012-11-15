package org.sdo.spring.integration.senders;

public class TestMessageHandaler<T> {

	private boolean toFail = false;
	private T lastSentMessage;

	public TestMessageHandaler() {
	}

	public void handleMessage(T msg) {
		if (toFail) {
			throw new RuntimeException(msg + " throw exception");
		} else {
			lastSentMessage = msg;
		}
	}

	public void emulateFail() {
		toFail = true;
	}

	public boolean nothingHasSent() {
		return lastSentMessage == null;
	}

	public T sentMessage() {
		return lastSentMessage;
	}

	public void reset() {
		lastSentMessage = null;
		toFail = false;
	}

}
