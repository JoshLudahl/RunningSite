package run.makemy.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Ohlaph on 5/7/2016.
 */

@Controller
public class IndexController {

    @RequestMapping("/")
    public String getIndex() {
        return "index";
    }

    @RequestMapping("/temp")
    public String temp() {
        return "temp";
    }

}
