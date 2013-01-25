package org.sdo.spotify;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sikuli.script.FindFailed;

public class SpotifyLoginTest {
	
	
	private Process p;

	@Before
	public void setup() throws IOException {
		p = Runtime.getRuntime().exec("spotify");

	}

	@After
	public void tearDown() throws Exception {
		p.destroy();
	}

	@Test
	public void verify_that_user_cannot_login_with_incorrect_credentials() throws FindFailed {
		LoginPage loginPage = new LoginPage();

		Map<String, String> credentials = new HashMap<String, String>();
		credentials.put("login", "sdo.semenov");
		credentials.put("password", "wrong password");
		assertThat(loginPage.login(credentials), is(false));
	}

	@Test
	public void verify_that_user_can_login_with_correct_credentials() throws FindFailed {
		LoginPage loginPage = new LoginPage();
		Map<String, String> credentials = new HashMap<String, String>();
		credentials.put("login", "sdo.semenov");
		credentials.put("password", "sdo");
		assertThat(loginPage.login(credentials), is(true));
	}
}
