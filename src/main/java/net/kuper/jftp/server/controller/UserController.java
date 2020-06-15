package net.kuper.jftp.server.controller;

import net.kuper.jftp.server.dto.UserPagination;
import net.kuper.jftp.server.entity.FtpUser;
import net.kuper.jftp.server.message.PageRes;
import net.kuper.jftp.server.message.Res;
import net.kuper.jftp.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public Res<PageRes<FtpUser>> query(UserPagination pagination) {
        PageRes<FtpUser> pageRes = userService.userPagination(pagination);
        return new Res().success().data(pageRes);
    }

    @GetMapping("/{id}")
    public Res<FtpUser> query(@PathVariable("id") String id) {
        FtpUser admin = userService.getObject(id);
        return new Res().success().data(admin);
    }

    @PutMapping
    public Res updatePwd(@RequestBody FtpUser ftpUser) {
        userService.update(ftpUser);
        return new Res().success();
    }

    @PutMapping("/reset/pwd/{id}")
    public Res<String> resetPwd(@PathVariable("id") String id) {
        String pwd = userService.resetPwd(id);
        return new Res().success().data(pwd);
    }

    @DeleteMapping("/{id}")
    public Res<String> delete(@PathVariable("id") String id, Boolean delFiles) {
        userService.delete(id, delFiles);
        return new Res().success();
    }
}
