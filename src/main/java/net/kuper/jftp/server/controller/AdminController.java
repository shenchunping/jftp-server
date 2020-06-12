package net.kuper.jftp.server.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/admin")
public class AdminController {

    @PostMapping("/login")
    @ResponseBody
    public AdminEntity login(){
        return new AdminEntity();
    }
}
