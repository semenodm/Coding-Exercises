import geb.Page


public class HotlineHomePage extends Page {

	static String  url = "http://hotline.ua"

	static content = {
		category{val -> $('a.m', href:contains(val))}
	}
	def clickOnCategory(){
		category.click()
	}

	//	def methodMissing(String name, args) {
	//		println name + 'HotlineHomePage'
	//	}
}
