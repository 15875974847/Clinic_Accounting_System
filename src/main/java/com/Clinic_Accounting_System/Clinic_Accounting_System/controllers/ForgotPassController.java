package com.Clinic_Accounting_System.Clinic_Accounting_System.controllers;

import com.Clinic_Accounting_System.Clinic_Accounting_System.models.Users;
import com.Clinic_Accounting_System.Clinic_Accounting_System.repositories.UsersRepository;
import com.Clinic_Accounting_System.Clinic_Accounting_System.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/forgot")
public class ForgotPassController {

    @Autowired
    UsersRepository userService;

    @GetMapping(value = "/")
    public String showForgotPage(HttpServletRequest request){
        HttpSession session = request.getSession();
        ControllerUtils.goThru_MessageByTicket_System(session);
        return "forgot/get_username";
    }

    @PostMapping(value="/checkUsername")
    public String checkUsername(HttpServletRequest request){
        HttpSession session = request.getSession();
        // scrapping username from request
        String username = request.getParameter("username");
        // checking in database on such username existence
        Users user = userService.getByUsername(username);
        if(user != null){
            // moving username from request into session
            session.setAttribute("forgot_page_username", username);
            // and redirecting forward to receive code on email to restore password
            return "redirect:/forgot/enter_email_code";
        } else {
            ControllerUtils.giveTicketToMyMessage(session, "User with such username do not exist!");
            return "redirect:/forgot/";
        }
    }


    @GetMapping(value="/enter_email_code")
    public String enterEmailCode(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        // firstly, checking if we have "forgot_page_username" attrib
        if(checkForgotPageUsernameAttribInSession(session)){
            // then
            return "/forgot/enter_email_code";
        } else {
            return "redirect:/forgot/";
        }
    }

    @PostMapping(value="/checkCode")
    public String checkCode(HttpServletRequest request){
        HttpSession session = request.getSession();
        // firstly, checking if we have "forgot_page_username" attrib
        if(checkForgotPageUsernameAttribInSession(session)){
            return "/forgot/enter_email_code";
        } else {
            return "redirect:/forgot/";
        }
    }

    private boolean checkForgotPageUsernameAttribInSession(HttpSession session){
        if(session != null){
            String username = (String)session.getAttribute("forgot_page_username");
            return username != null;
        } else {
            return false;
        }
    }

}
