package com.Clinic_Accounting_System.Clinic_Accounting_System.controllers;

import com.Clinic_Accounting_System.Clinic_Accounting_System.models.Roles;
import com.Clinic_Accounting_System.Clinic_Accounting_System.models.UserInfo;
import com.Clinic_Accounting_System.Clinic_Accounting_System.models.Users;
import com.Clinic_Accounting_System.Clinic_Accounting_System.repositories.UserInfoRepository;
import com.Clinic_Accounting_System.Clinic_Accounting_System.repositories.UsersRepository;
import com.Clinic_Accounting_System.Clinic_Accounting_System.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private UsersRepository userService;

    @Autowired
    private UserInfoRepository userInfoService;

    @GetMapping(value = "/")
    public String showAccountInfoForm(HttpServletRequest request){
        HttpSession session = request.getSession(true);
        // going thru message by ticket system
        ControllerUtils.goThru_MessageByTicket_System(session);
        return "registration/new_account_application";
    }

    @PostMapping(value = "/submitNewAccountApplicationForm")
    public String submitAccountInfoForm(HttpServletRequest request){
        // creating new session to store params or error messages there
        HttpSession session = request.getSession(true);
        // scraping params from request
        String reg_username = request.getParameter("reg_username");
        String reg_password = request.getParameter("reg_password");
        // checking on param existence
        if(reg_username.length() >= 8 || reg_password.length() >= 8) {
            // checking if user with such username already exists in database
            if(!userService.existsByUsername(reg_username)){
                // moving username and pwd params from request scope to session scope
                session.setAttribute("reg_username", reg_username);
                session.setAttribute("reg_password", reg_password);
                return "redirect:/registration/pers_info_application";
            } else {
                ControllerUtils.giveTicketToMyMessage(session, "Sorry, but user with such username already exists!");
                return "redirect:/registration/";
            }
        } else {
            ControllerUtils.giveTicketToMyMessage(session, "Username and password length should be no less then 8 characters!");
            return "redirect:/registration/";
        }
    }


    @GetMapping(value = "/pers_info_application")
    public String showPersonalInfoApplication(HttpServletRequest request){
        if(checkSessionAttribsForRegOnExistence(request)){
            return "registration/pers_info_application";
        } else {
            return "redirect:/registration/";
        }
    }

    @PostMapping(value = "/submitPersonalInformationForm")
    public String submitPersonalInformationForm(HttpServletRequest request){
        if(checkSessionAttribsForRegOnExistence(request)){
            HttpSession session = request.getSession();
            // here could be server's parameters validation, but let's omit them for brevity
            // to be sure checking once again if nobody took our username
            if(!userService.existsByUsername((String)session.getAttribute("reg_username"))){
                // persisting new user in database
                Users user = new Users((String)session.getAttribute("reg_username"), (String)session.getAttribute("reg_password"), Roles.user);
                userService.saveAndFlush(user);
                // scrapping user info from form params
                String firstname = request.getParameter("firstname");
                String midname = request.getParameter("midname");
                String lastname = request.getParameter("lastname");
                Date dob = java.sql.Date.valueOf(request.getParameter("dob"));
                String phone = request.getParameter("phone");
                String email = request.getParameter("email");
                String homeAddress = request.getParameter("homeAddress");
                // persist user info in database
                UserInfo userInfo = new UserInfo(firstname, midname, lastname, dob, phone, email, homeAddress, user);
                userInfoService.saveAndFlush(userInfo);
                // take registration username and password
                session.removeAttribute("reg_username");
                session.removeAttribute("reg_password");
                // and redirect new user to sign in page
                return "redirect:/sign_in";
            } else {
                ControllerUtils.giveTicketToMyMessage(session, "Sorry, but your username somebody already took!");
                return "redirect:/registration/";
            }
        } else {
            return "redirect:/registration/";
        }
    }


    private boolean checkSessionAttribsForRegOnExistence(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return session.getAttribute("reg_username") != null && session.getAttribute("reg_password") != null;
        }
        return false;
    }
}
