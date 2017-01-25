package run.makemy.controllers.user_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import run.makemy.domains.user.UserCreateForm;
import run.makemy.domains.validator.UserCreateFormValidator;
import run.makemy.domains.workouts.Workout;
import run.makemy.services.user.UserService;
import run.makemy.services.workouts.Calculations;
import run.makemy.services.workouts.WorkoutService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

/**
 * Created by Ohlaph on 5/7/2016.
 */

@Controller
public class UserController {

    private final UserService userService;
    private final UserCreateFormValidator userCreateFormValidator;

    private final WorkoutService workoutService;

    @Autowired
    public UserController(UserService userService, UserCreateFormValidator userCreateFormValidator, WorkoutService workoutService) {
        this.userService = userService;
        this.userCreateFormValidator = userCreateFormValidator;
        this.workoutService = workoutService;
    }

    @InitBinder("form")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userCreateFormValidator);
    }


    //Display a single user information
    @PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
    @RequestMapping("/user/{id}")
    public ModelAndView getUserPage(@PathVariable Long id, Model model) throws NoSuchElementException {
        Collection<Workout> workoutList = (ArrayList<Workout>) workoutService.getUserWorkoutsByOrderByDate(id);
        int listSize = workoutList.size();
        model.addAttribute("qty", listSize);
        Calculations calc = new Calculations((ArrayList<Workout>)workoutList);
        model.addAttribute("distance", calc.calculateDistance());
        model.addAttribute("totalDuration", calc.buildTime());
        model.addAttribute("averageRun", calc.calculateAverageRun(listSize));

        return new ModelAndView("user", "user", userService.getUserById(id).orElseThrow(new Supplier<NoSuchElementException>() {
            @Override
            public NoSuchElementException get() {
                return new NoSuchElementException(String.format("User=%s not found", id));
            }
        }));
    }

    //Display the user form before posting the data

    @RequestMapping(value = "/sign_up", method = RequestMethod.GET)
    public ModelAndView getUserCreatePage() {
        return new ModelAndView("sign_up", "form", new UserCreateForm());
    }

    //Process the form after a user submits it

    @RequestMapping(value = "/sign_up", method = RequestMethod.POST)
    public String HandleUserCreateForm(@Valid @ModelAttribute("form") UserCreateForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "sign_up";
        }
        try {
            userService.create(form);

        } catch (DataIntegrityViolationException e) {
            bindingResult.reject("email.exists", "Email already exists");
            return "sign_up";
        }
        return "redirect:/";
    }

}
