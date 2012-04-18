@Grapes([
	@Grab("org.codehaus.geb:geb-core:0.6.1"),	
	@Grab("org.seleniumhq.selenium:selenium-firefox-driver:2.20.0"),
	@Grab("org.seleniumhq.selenium:selenium-support:2.20.0"),
	@Grab("org.apache.ivy:ivy:latest.release")
])
import geb.Browser

import org.openqa.selenium.firefox.FirefoxDriver

Browser.drive(driver : new FirefoxDriver()) {
	go('http://hotline.ua')
	$('li.tm', id:"lv-1-14").children()[0].click()
	$('select', name:'category').find('option', value:'11').click()

	def element
	waitFor{
		element = $('select', name:'vendor').find('option', value:'242')
	}
	element.click()
	waitFor{
		element = $('select', name:'card').find('option', value:"apple-iphone-3g-8gb")
	}
	element.click()
	$('input.but', value:'выбрать').click()
	assert $('h1', text:'Apple iPhone 3G 8Gb').size() == 1
}.quit()
