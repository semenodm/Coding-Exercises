package org.sdo.spotify

import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern
import org.sikuli.script.Screen

class LoginScrean {
	def s

	public LoginScrean() {
		s = new Screen()
		s.wait(new Pattern("src/test/resources/sikuli-logo.png").similar(0.5))
	}

	def enterLogin = { login ->
		s.type("sdo.semenov")
	}

	def enterPassword = { pwd ->
		s.type("./src/test/resources/sikuli-password.png", pwd)
	}

	def login = {credentials ->
		enterLogin credentials.login
		enterPassword credentials.password

		s.with{
			click( "./src/test/resources/sikuli-login-btn.png")
			try{
				wait(new Pattern("src/test/resources/sikuli-login-failed.png").similar(0.75))
			}catch(FindFailed e){
				return true
			}
			false
		}
	}
}
