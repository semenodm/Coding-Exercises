package org.sdo.spotify;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sikuli.script.FindFailed;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

public class SpotifyUITest{
	private Process p;
	
	@Before
	public void setup() throws Exception{
		p = Runtime.getRuntime().exec("spotify");
		LoginPage loginPage = new LoginPage();
		Map<String, String> credentials = new HashMap<String, String>();
		credentials.put("login", "sdo.semenov");
		credentials.put("password", "sdo");
		loginPage.login(credentials);
	}
	
	@After
	public void tearDown(){
		p.destroy();
	}
	
	@Test
	public void verify_how_simple_search_is_working() throws FindFailed{

		SpotifyUI spotifyUI = new SpotifyUI();
		spotifyUI.search("Guns and Roses");

		//here i assume that search engine is on server side
		assertThat(spotifyUI.searchResult(), hasSize(greaterThan(0)));
	}

	@Test
	public void verify_how_advanced_search_is_working() throws FindFailed{

		SpotifyUI spotifyUI = new SpotifyUI();
		Map<String, String> searchParams = new HashMap<String, String>();
		searchParams.put("artist","Rainbow");
		searchParams.put("year","1979");
		spotifyUI.advansedSearch(searchParams);
		assertThat(spotifyUI.albumFound("DownToTheEarth"), is(true));
	}

	@Test
	public void verify_that_Marilyn_Manson_Cyclops_song_is_playing() throws FindFailed{
		SpotifyUI spotifyUI = new SpotifyUI();
		Map<String, String> searchParams = new HashMap<String, String>();
		searchParams.put("artist","Marilyn Manson");
		searchParams.put("year","2005");
		spotifyUI.advansedSearch(searchParams);
		spotifyUI.upVolume();
		spotifyUI.play("Cyclops");
		
		assertThat(spotifyUI.songPlayed(), is(true));
	}
}
