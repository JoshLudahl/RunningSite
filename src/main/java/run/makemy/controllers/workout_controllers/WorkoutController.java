package run.makemy.controllers.workout_controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import run.makemy.domains.user.CurrentUser;
import run.makemy.domains.validator.UserCreateFormValidator;
import run.makemy.domains.workouts.Workout;
import run.makemy.services.currentuser.CurrentUserServiceImpl;
import run.makemy.services.user.UserService;
import run.makemy.services.workouts.Calculations;
import run.makemy.services.workouts.WorkoutService;
import sun.reflect.generics.visitor.Reifier;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@ComponentScan
public class WorkoutController {

    private final WorkoutService workoutService;
    private final UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkoutController.class);


    @Autowired
    public WorkoutController(UserService userService, WorkoutService workoutService) {
        this.userService = userService;
        this.workoutService = workoutService;

    }

    //Display users workouts
    @PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
    @RequestMapping(value = "/workouts/{id}", method = RequestMethod.GET)
    public String displayWorkouts(@PathVariable Long id, Model model) throws ParseException {
        Collection<Workout> workoutList = (ArrayList<Workout>) workoutService.getUserWorkoutsByOrderByDate(id);
        int listSize = workoutList.size();
        Calculations calc = new Calculations((ArrayList<Workout>)workoutList);


        model.addAttribute("qty", listSize);
        model.addAttribute("workouts", (ArrayList<Workout>) workoutService.getUserWorkoutsByOrderByDate(id));

        model.addAttribute("user", userService.getUserById(id).orElseThrow(() -> new NoSuchElementException(String.format("User=%s not found", id))));

        return "workouts";
    }

    //Add a workout (GET)
    //Bring in the user ID and set the author to the ID
    @PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
    @RequestMapping(value = "/workouts/add/{id}", method = RequestMethod.GET)
    public ModelAndView addWorkout(@PathVariable Long id) {
        Workout wkout = new Workout();
        wkout.setAuthor(id);
        ModelAndView model = new ModelAndView("add_workout", "workout", wkout);
        return model;
    }


    //Add a workout (POST)
    @PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
    @RequestMapping(value = "/workouts/add/{id}", method = RequestMethod.POST)
    public String create(@PathVariable Long id, @ModelAttribute("workout") Workout workout, final RedirectAttributes redirectAttributes) {

        if (workoutService.create(workout) != null) {
            redirectAttributes.addFlashAttribute("create", "success");
            String redirectString = "redirect:/workouts/" + workout.getAuthor().toString();
            return redirectString;
        } else {
            redirectAttributes.addFlashAttribute("addWorkout", "fail");
            LOGGER.debug("Failed, " + workout.getAuthor());
        }
        return "redirect:/addWorkout";
    }

    //Remove and edit workout
    @PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
    @RequestMapping(value = "/workouts/{operation}/{workoutid}/{id}", method = RequestMethod.GET)
    public String removeOrEditWorkout(@PathVariable("operation") String operation,
                                      @PathVariable("workoutid") Long workoutid, @PathVariable("id") Long id, final RedirectAttributes redirectAttributes,
                                      Model model) {
        if (operation.equals("delete")) {
            if (workoutService.deleteWorkout(workoutid)) {
                redirectAttributes.addFlashAttribute("deletion", "success");
            } else {
                redirectAttributes.addFlashAttribute("deletion", "unsuccess");
            }
        } else if (operation.equals("edit")) {
            Workout editWorkout = workoutService.findWorkout(workoutid);
            if (editWorkout != null) {
                model.addAttribute("editWorkout", editWorkout);
                return "editWorkout";
            } else {
                redirectAttributes.addFlashAttribute("status", "notfound");
            }
        }

        return "redirect:/workouts/" + id;
    }


    //Update workout
    @PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
    @RequestMapping(value = "/workouts/update/{id}", method = RequestMethod.POST)
    public String updateWorkout(@PathVariable Long id, @ModelAttribute("editWorkout") Workout editWorkout, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        editWorkout.setAuthor(id);
        if (workoutService.editWorkout(editWorkout) != null) {
            redirectAttributes.addFlashAttribute("edit", "success");
        } else {
            redirectAttributes.addFlashAttribute("edit", "failed");
        }
        return "redirect:/workouts/" + id;
    }


}
