package controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import model.Product;
import service.ProductService;
import validator.ProductValidator;

@Controller
@RequestMapping("/products/management")
public class ProductManagementController {

	@Autowired
	private ProductService service;
	
	@Autowired
	private ProductValidator productValidator;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	// java.lang.IllegalStateException: Neither BindingResult nor plain target
	// object for bean name 'newProduct' available as request attribute
	// eger @ModelAttribute verilmezse hata aliriz.

	// http://localhost:8080/SpringMVCWeek03//products/management/add
	public String newProductPage(@ModelAttribute("newProduct") Product newProduct) {
		//initialize model
		newProduct.setDescription("default description....");
		newProduct.setCategory("Laptop");
		return "webstore.view/productAdd";
	}

//	@RequestMapping(value = "/add", method = RequestMethod.POST)
//	public String addNewProduct(@ModelAttribute("newProduct") Product product) {
//		
//		
//		System.out.println(product);
//		service.addProduct(product);
//		return "redirect:/products";
//	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addNewProduct(@ModelAttribute("newProduct") Product product, BindingResult bindingResult) {
		
		productValidator.validate(product, bindingResult);
		
		if(bindingResult.hasErrors()){
			return "webstore.view/productAdd";
		}
		
		System.out.println(product);
		service.addProduct(product);
		return "redirect:/products";
	}

	//
	@ModelAttribute("manufacturerList")
	public List<String> prepareManufacturerList() {
		System.out.println("prepareManufacturerList is invoked!");
		///// prepare data
		// gerekirse veritabanindan git, ya da property dosyasindan oku vs
		return Arrays.asList("Apple", "Samsung", "Google");
	}
	
	@ModelAttribute("categoryList")
	public List<String> prepareCategoryList() {
		System.out.println("prepareCategoryList is invoked!");
		///// prepare data
		// gerekirse veritabanindan git, ya da property dosyasindan oku vs
		return Arrays.asList("Tablet", "SmartPhone", "Laptop");
	}
	
	@ModelAttribute("conditionsMap")
	public Map<String, String> prepareConditionMap(){
		Map<String,String> conditions = new HashMap<>();
		conditions.put("new", "New Product");
		conditions.put("old", "Old");
		conditions.put("refurbished", "Refurbished");
		return conditions;
	}

}
