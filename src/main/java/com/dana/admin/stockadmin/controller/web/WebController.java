package com.dana.admin.stockadmin.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {



    @GetMapping("/multichart")
    public String multichart (@RequestParam String code , ModelMap model ){
        model.put("code" , code);
        return "chart14";
    }

}
