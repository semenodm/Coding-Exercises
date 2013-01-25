package org.sdo.spotify;

import static org.hamcrest.Matchers.*
import static org.junit.Assert.*

import org.sikuli.script.Screen

import spock.lang.Specification

@RunSpotify
class SpotifyUISpec extends Specification{
	def "verify how simple search is working"(){
		given: "start spotify and login"
		new LoginScrean().
				login([login:"sdo.semenov", password:"sdo"])

		when:"search for Guns and Roses songs"
		def spotifyUI = new SpotifyUIScrean()
		spotifyUI.search('Guns and Roses')

		then: "list of songs should not be empty"
		//here i assume that search engine is on server side
		spotifyUI.searchResult().size() > 0
	}

	def "verify how advanced search is working"(){
		given: "start spotify and login"
		new LoginScrean().
				login([login:"sdo.semenov", password:"sdo"])

		when:"find Rainbow's album of year 1979"
		def spotifyUI = new SpotifyUIScrean()
		spotifyUI.advansedSearch([artist:'Rainbow', year:'1979'])
		then:
		spotifyUI.albumFound 'DownToTheEarth' is true
	}

	def "verify that Marilyn Manson Cyclops song is playing"(){
		given: "start spotify and login"
		new LoginScrean().
				login([login:"sdo.semenov", password:"sdo"])

		when: "find Marilyn Manson's songs of year 2005"
		def spotifyUI = new SpotifyUIScrean()
		spotifyUI.advansedSearch([year:'2005', artist:'Marilyn Manson'])
		and: "increase volume so that everyone could here"
		spotifyUI.upVolume()
		and: "play Cyclops song"
		spotifyUI.play 'Cyclops'
		then: "slider should run"
		spotifyUI.songPlayed() is true
	}

	def "team demo"(){
		given:
		new LoginScrean().
				login([login:"sdo.semenov", password:"sdo"])
		expect:
		new Screen().with{
			click("/tmp/sikuli-tmp1472962660822790456.png")
			dragDrop("/tmp/sikuli-tmp932048792653867697.png", "/tmp/sikuli-tmp4727971753639314381.png")
		}
	}
}
