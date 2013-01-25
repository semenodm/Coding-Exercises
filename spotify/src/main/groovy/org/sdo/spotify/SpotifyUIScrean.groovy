package org.sdo.spotify

import org.sikuli.script.ChangeEvent
import org.sikuli.script.FindFailed
import org.sikuli.script.Key
import org.sikuli.script.Match
import org.sikuli.script.Pattern
import org.sikuli.script.Screen
import org.sikuli.script.SikuliEventAdapter

class SpotifyUIScrean {
	Screen s
	public SpotifyUIScrean() {
		s = new Screen()
		s.wait(new Pattern("src/test/resources/sikuli-search.png").similar(0.75))
	}

	def advansedSearch = { params
		->
		def searchString = ""
		params.each {searchString += "$it.key:$it.value "}
		s.type(new Pattern("src/test/resources/sikuli-search.png").similar(0.75), searchString + Key.ENTER)
	}
	
	def search = { params
		->
		s.type(new Pattern("src/test/resources/sikuli-search.png").similar(0.75), params + Key.ENTER)
	}

	def play = {song ->
		s.doubleClick(new Pattern("src/test/resources/${song}.png").similar(0.75))
	}

	def progress = {
		->
		Match match = s.find(new Pattern("src/test/resources/slider.png").similar(0.75))
		def inside = match.inside()
		def sliderMoved = false
		inside.onChange(new SikuliEventAdapter(){
					@Override
					public void targetChanged(ChangeEvent event){
						sliderMoved = true
					}
				}
				)
		inside.observe(5)
		sliderMoved
	}
	def upVolume = {->
		Match match = s.find(new Pattern("src/test/resources/sikuli-control.png").similar(0.75))
			.right()
			.find(new Pattern("src/test/resources/small_slider.png").similar(0.75))
		s.dragDrop(match, new Pattern("src/test/resources/sikuli-mic.png").similar(0.75))
	}
	
	def searchResult = {->
		def itemIterator = s.findAll(new Pattern("src/test/resources/sikuli-track.png").similar(0.75))
		itemIterator.collect{it}
	}
	
	def album = {album ->
		try{
			s.wait(new Pattern("src/test/resources/${album}.png").similar(0.75))
		}catch(FindFailed e){
			return true
		}
		false
	}
}
