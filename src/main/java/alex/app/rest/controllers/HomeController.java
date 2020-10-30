package alex.app.rest.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController { //TODO: remove!

	@RequestMapping("/")
	public String home() {
		return "index";
	}

}
