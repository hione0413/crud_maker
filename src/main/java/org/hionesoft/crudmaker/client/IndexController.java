package org.hionesoft.crudmaker.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/welcome")
    public String index() {
        return "vue/index";
    }

}
