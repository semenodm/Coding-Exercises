package org.sdo.spotify;

import java.util.Map;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

class LoginPage {
	Screen s;

	public LoginPage() throws FindFailed {
		s = new Screen();
		s.wait(new Pattern("src/test/resources/sikuli-logo.png").similar(0.5f));
	}

	public void enterLogin(String login) throws FindFailed {
		s.type(login);
	}

	public void enterPassword(String pwd) throws FindFailed {
		s.type("./src/test/resources/sikuli-password.png", pwd);
	}

	public boolean login(Map<String, String> credentials) throws FindFailed {
		enterLogin(credentials.get("login"));
		enterPassword(credentials.get("password"));

		s.click("./src/test/resources/sikuli-login-btn.png");
		try {
			s.wait(new Pattern("src/test/resources/sikuli-login-failed.png").similar(0.75f));
		}
		catch (FindFailed e) {
			return true;
		}
		return false;
	}
}
