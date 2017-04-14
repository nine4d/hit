/**
 * 
 */
package com.hanuritien.processor.detection.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hanuritien.processor.detection.address.AddressChecker;
import com.hanuritien.processor.detection.address.AddressService;

@Controller
public class GreetingController {

	@Autowired
	AddressService addresssvc;
	

	
    @RequestMapping("greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }
    
/*    @RequestMapping("ttt")
    public String helloWorld() throws Exception {

		return this.addresssvc.findByCode("3808011").getAddress();
	}
    */

}
