package net.kuper.jftp.server.controller;

import net.kuper.jftp.server.auth.annotation.Admin;
import net.kuper.jftp.server.auth.annotation.IgnoreAuth;
import net.kuper.jftp.server.dto.AdminPagination;
import net.kuper.jftp.server.dto.UpdatePwd;
import net.kuper.jftp.server.entity.FtpAdmin;
import net.kuper.jftp.server.message.PageRes;
import net.kuper.jftp.server.message.Res;
import net.kuper.jftp.server.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(path = "/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @IgnoreAuth
    @PostMapping("/login")
    public void login(String account, String password, HttpServletRequest request) {
        FtpAdmin ftpAdmin = adminService.login(account, password);
        request.getSession().setAttribute("ftp-admin", ftpAdmin);
        request.getRequestDispatcher("/users.html");
    }

    @GetMapping
    public Res<PageRes<FtpAdmin>> query(AdminPagination pagination) {
        PageRes<FtpAdmin> pageRes = adminService.adminPagination(pagination);
        return new Res().success().data(pageRes);
    }

    @GetMapping("/{id}")
    public Res<FtpAdmin> query(@PathVariable("id") String id) {
        FtpAdmin admin = adminService.getObject(id);
        return new Res().success().data(admin);
    }

    @PutMapping("/update/pwd")
    public Res updatePwd(@RequestBody UpdatePwd updatePwd) {
        adminService.updatePwd(updatePwd);
        return new Res().success();
    }

    @PutMapping("/reset/pwd/{id}")
    public Res<String> resetPwd(@PathVariable("id") String id) {
        String pwd = adminService.resetPwd(id);
        return new Res().success().data(pwd);
    }

    @DeleteMapping("/{id}")
    public Res<String> delete(@PathVariable("id") String id, @Admin FtpAdmin ftpAdmin) {
        adminService.delete(id, ftpAdmin.getId());
        return new Res().success();
    }
}
