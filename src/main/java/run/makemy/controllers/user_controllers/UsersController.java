package run.makemy.controllers.user_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import run.makemy.services.user.UserService;

/**
 * Created by Ohlaph on 5/7/2016.
 */

@Controller
public class UsersController {

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("/users")
    public ModelAndView getUsersPage() {

        //return modelavdview "page", "variable_to_use", and utilize userService
        return new ModelAndView("users", "users", userService.getAllUsers());
    }
}
