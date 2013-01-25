package org.sdo.spotify;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.sikuli.script.ChangeEvent;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;
import org.sikuli.script.SikuliEventAdapter;

class SpotifyUI {
	private static final Pattern SEARCH_PATTERN = new Pattern("src/test/resources/sikuli-search.png");

	private static final float IMAGE_SIMILARITY = 0.75f;

	private static final char SEARCH_SEPARATOR = ' ';

	Screen s;

	public SpotifyUI() throws FindFailed {
		s = new Screen();
		s.wait(SEARCH_PATTERN.similar(IMAGE_SIMILARITY));
	}

	public void advansedSearch(Map<String, String> params) throws FindFailed {
		StringBuilder searchString = new StringBuilder();
		for (Entry<String, String> param : params.entrySet()) {
			searchString.append(param.getKey()).append(':').append(param.getValue()).append(SEARCH_SEPARATOR);
		}
		s.type(SEARCH_PATTERN.similar(IMAGE_SIMILARITY), searchString + Key.ENTER);
	}

	public void search(String param) throws FindFailed {
		s.type(SEARCH_PATTERN.similar(IMAGE_SIMILARITY), param + Key.ENTER);
	}

	public void play(String song) throws FindFailed {
		s.doubleClick(new Pattern("src/test/resources/" + song + ".png").similar(IMAGE_SIMILARITY));
	}

	public boolean songPlayed() throws FindFailed {
		Match match = s.find(new Pattern("src/test/resources/slider.png").similar(IMAGE_SIMILARITY));
		Region inside = match.inside();
		//observe can have asynchronous nature
		final List<ChangeEvent> changes = Collections.synchronizedList(new ArrayList<ChangeEvent>());
		inside.onChange(new SikuliEventAdapter() {
			@Override
			public void targetChanged(ChangeEvent event) {
				changes.add(event);
			}
		});
		inside.observe(5);
		return !changes.isEmpty();
	}

	public void upVolume() throws FindFailed {
		Match match = s.find(new Pattern("src/test/resources/sikuli-control.png").similar(IMAGE_SIMILARITY)).right()
				.find(new Pattern("src/test/resources/small_slider.png").similar(IMAGE_SIMILARITY));
		s.dragDrop(match, new Pattern("src/test/resources/sikuli-mic.png").similar(IMAGE_SIMILARITY));
	}

	public List<Match> searchResult() throws FindFailed {
		Iterator<Match> itemIterator = s.findAll(new Pattern("src/test/resources/sikuli-track.png").similar(IMAGE_SIMILARITY));
		List<Match> result = new ArrayList<Match>();
		for (Iterator<Match> iterator = itemIterator; iterator.hasNext();) {
			Match item = (Match) iterator.next();
			result.add(item);
		}
		return result;
	}

	public boolean albumFound(String album) {
		try {
			s.wait(new Pattern("src/test/resources/" + album + ".png").similar(IMAGE_SIMILARITY));
		}
		catch (FindFailed e) {
			return true;
		}
		return false;
	}
}
