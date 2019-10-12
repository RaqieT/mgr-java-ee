package pl.dmcs.rkotas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pl.dmcs.rkotas.domain.AppUser;

@Controller
public class AppUserController {

    @RequestMapping(value = "/appUsers")
    public ModelAndView showAppUsers() {

	 /*  AppUser appUser = new AppUser();
	   appUser.setFirstName("rafal");
	   appUser.setLastName("kotas");
	   appUser.setEmail("rkotas@dmcs.pl");
	   appUser.setTelephone("123456789");*/

	 return new ModelAndView("appUser", "appUser", new AppUser());
    }

    @RequestMapping(value = "/addAppUser", method = RequestMethod.POST)
    public String addAppUser(@ModelAttribute("appUser") AppUser appUser) {

        System.out.println("First Name: " + appUser.getFirstName() +
                " Last Name: " + appUser.getLastName() + " Tel.: " +
                appUser.getTelephone() + " Email: " + appUser.getEmail());

        return "redirect:appUsers.html";
    }

}

