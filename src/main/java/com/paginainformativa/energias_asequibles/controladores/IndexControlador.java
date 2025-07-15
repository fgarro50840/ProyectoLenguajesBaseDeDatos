package com.paginainformativa.energias_asequibles.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexControlador {

    @GetMapping("/index")
    public String volverIndex() {
        return "index";
    }

}
