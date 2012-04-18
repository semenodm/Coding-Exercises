import javax.swing.JTabbedPane.Page

import javax.swing.JTabbedPane.Page

import geb.Page


class HotlineCategoryPage extends Page {
	//static String  url = "http://hotline.ua/mobile/"
	static content = {


		categoryElement {val -> $('select', name : 'category').find('option', value : val)}

		vendorSelect(wait : true){$('select', name : 'vendor').find('option', text : 'Apple')}

		cardSelect(wait : true) {$('select.last', name : 'card').find('option', value : "apple-iphone-4s-16gb-black-neverlock")}

		goButton {$('input.but', value : 'выбрать')}
	}

	//	def select(def val) {
	//		println 'in HotlineCategoryPage.select'
	//		["categ" : {
	//				println "val is $val"
	//				categoryElem(val).click()
	//			}
	//		]
	//	}

	def select = {ctg->
		println 'in HotlineCategoryPage.select'
		[category : {val -> 
				println "in category"
				//println "val is $val"
				categoryElement(val).click()}

		]
	}

	//	def methodMissing(String name, args) {
	//		println name + 'HotlineCategoryPage'
	//	}
}
