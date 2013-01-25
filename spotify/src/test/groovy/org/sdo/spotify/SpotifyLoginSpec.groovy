package org.sdo.spotify

import static org.hamcrest.Matchers.*
import static org.junit.Assert.*

import spock.lang.Ignore;
import spock.lang.Specification

@RunSpotify
class SpotifyLoginSpec extends Specification{

	def "verify that user cannot login with incorrect credentials"(){
		setup: "start spotify"
		def loginPage = new LoginScrean()
		
		expect: "login failed should be shown"
		loginPage.login([login:"sdo.semenov", password:"wrong password"]) is false
	}
	
	def "verify that user can login with correct credentials"(){
		setup: "start spotify"
		def loginPage = new LoginScrean()
		
		expect: "login failed should be shown"
		loginPage.login([login:"sdo.semenov", password:"correct pwd"]) is true
	}
	
	@Ignore
	def "verify user credentials"(){
		setup: "start spotify"
		def loginPage = new LoginScrean()
		expect:
		loginPage.login([login:login, password:password]) is success
		where:
		login			| password			| success
		"sdo.semenov"	| 'wrong password'	| false
		"sdo.semenov"   | 'correct pwd'				| true
	}
}
