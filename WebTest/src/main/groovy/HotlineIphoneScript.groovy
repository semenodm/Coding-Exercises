@Grapes([
	@Grab("org.codehaus.geb:geb-core:0.6.1"),
	@Grab("org.seleniumhq.selenium:selenium-firefox-driver:2.20.0"),
	@Grab("org.seleniumhq.selenium:selenium-support:2.20.0"),
	@Grab("org.apache.ivy:ivy:latest.release")
])
import geb.Browser

import org.openqa.selenium.firefox.FirefoxDriver



Browser.drive([driver : new FirefoxDriver()]){
	to (HotlineHomePage)
	category 'mobile' click()
	at (HotlineCategoryPage)
	select category "11" 
	vendorSelect click()
	cardSelect click()
	goButton click()
	assert $('h1', text:"Apple iPhone 4S 16GB Black Neverlock").size() == 1
}.quit()

