package com.Clinic_Accounting_System.Clinic_Accounting_System.controllers;

import com.Clinic_Accounting_System.Clinic_Accounting_System.models.UserInfo;
import com.Clinic_Accounting_System.Clinic_Accounting_System.models.Users;
import com.Clinic_Accounting_System.Clinic_Accounting_System.repositories.UserInfoRepository;
import com.Clinic_Accounting_System.Clinic_Accounting_System.repositories.UsersRepository;
import com.Clinic_Accounting_System.Clinic_Accounting_System.services.EmailServiceImpl;
import com.Clinic_Accounting_System.Clinic_Accounting_System.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.SendFailedException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/pass_recovery")
public class PasswordRecoveryController {

    @Autowired
    UsersRepository userService;

    @Autowired
    UserInfoRepository userInfoService;

    @Autowired
    EmailServiceImpl emailService;

    @GetMapping(value = "/")
    public String showGetUsernamePage(HttpServletRequest request){
        HttpSession session = request.getSession();
        ControllerUtils.goThru_MessageByTicket_System(session);
        return "pass_recovery/get_username";
    }

    @PostMapping(value="/checkUsername")
    public String checkUsernameAndSendCodeOnEmail(HttpServletRequest request){
        HttpSession session = request.getSession();
        // scrapping username from request
        String username = request.getParameter("username");
        // checking in database if user with such username exists
        Users user = userService.getByUsername(username);
        if(user != null){
            // checking if user info profile with such use_id exists
            UserInfo userInfo = userInfoService.getOne(user.getId());
            if(userInfo != null){
                try {
                    // moving "forgot_page_user_id" and "forgot_page_username" tokens to have access to next pages
                    session.setAttribute("pass_recovery_user_id", user.getId());
                    session.setAttribute("pass_recovery_username", username);
                    // then try to send code to email from profile
                    emailService.sendCodeToRestorePassword(userInfo.getEmail(), "Hey buddy, it's Arti's CAS password recovery system", "Your code: " + user.getPassword().hashCode());
                    return "redirect:/pass_recovery/enter_email_code";
                } catch (MailException e){
                    ControllerUtils.giveTicketToMyMessage(session, "Sorry, but we cannot send your code to restore password on provided email!");
                    return "redirect:/sign_in";
                }
            } else{
                ControllerUtils.giveTicketToMyMessage(session, "Sorry, but your profile info does not exist!");
                return "redirect:/sign_in";
            }
        } else {
            ControllerUtils.giveTicketToMyMessage(session, "User with such username does not exist!");
            return "redirect:/pass_recovery/";
        }
    }


    @GetMapping(value="/enter_email_code")
    public String enterEmailCode(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(checkForgotPageUsernameAttribInSession(session)){
            ControllerUtils.goThru_MessageByTicket_System(session);
            return "forgot/enter_email_code";
        } else {
            return "redirect:/pass_recovery/";
        }
    }

    @PostMapping(value="/checkCode")
    public String checkCode(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        // firstly, checking if we have "forgot_page_user_id" attrib
        if(checkForgotPageUsernameAttribInSession(session)){
            // taking "email_code" from request
            String code = request.getParameter("email_code");
            Users user = userService.getOne((Long)session.getAttribute("pass_recovery_user_id"));
            if(code != null && user != null){
                // if hashcode of password equals entered code
                if(code.equals(Integer.toString(user.getPassword().hashCode()))){
                    // taking new password from request scope
                    String newPassword = request.getParameter("new_password");
                    // check if password is following requirements, you can add yours
                    if(newPassword.length() >= 8){
                        // and update it in database
                        user.setPassword(newPassword);
                        userService.saveAndFlush(user);
                        // then notify user that password successfully changed and go sign in
                        ControllerUtils.giveTicketToMyMessage(session, "Password successfully changed!");
                        return "redirect:/sign_in";
                    } else{
                        ControllerUtils.giveTicketToMyMessage(session, "Password is less then 8 characters!");
                        return "redirect:/pass_recovery/enter_email_code";
                    }
                } else {
                    ControllerUtils.giveTicketToMyMessage(session, "Sorry, but u entered wrong code! Try once again!");
                    return "redirect:/pass_recovery/enter_email_code";
                }
            } else {
                ControllerUtils.giveTicketToMyMessage(session, "Sorry, but somebody already deleted your user account");
                return "redirect:/sign_in";
            }
        } else {
            return "redirect:/pass_recovery/";
        }
    }

    private boolean checkForgotPageUsernameAttribInSession(HttpSession session){
        if(session != null){
            Long id = (Long)session.getAttribute("pass_recovery_user_id");
            String username = (String)session.getAttribute("pass_recovery_username");
            return username != null && id != null;
        } else {
            return false;
        }
    }

}
