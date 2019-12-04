package com.Clinic_Accounting_System.Clinic_Accounting_System.controllers;

import com.Clinic_Accounting_System.Clinic_Accounting_System.models.Roles;
import com.Clinic_Accounting_System.Clinic_Accounting_System.models.Users;
import com.Clinic_Accounting_System.Clinic_Accounting_System.repositories.UsersRepository;
import com.Clinic_Accounting_System.Clinic_Accounting_System.services.UserService;
import com.Clinic_Accounting_System.Clinic_Accounting_System.utils.AppLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class AuthenticationController {

    // Getting bean from Spring container to handle user repository data
    @Autowired
    private UsersRepository usersRepository;

    @GetMapping(value = {"/", "/sign_in"})
    public String doStart(HttpServletRequest request) {
        AppLogger.logInfo("LOGINFO TEST from Get mapping method!");
        // checking session obj existence
        HttpSession session = request.getSession(false);
        if(session != null){
            // if session exists
            Long id = (Long)session.getAttribute("user_id");
            if(id != null){
                // checking if id exists in session
                Users user = usersRepository.getOne(id);
                if(user != null) {
                    // update max inactive interval in "no remember-me" type of session
                    if(session.getMaxInactiveInterval() > 0) session.setMaxInactiveInterval(30*60);
                    // if such user found in database -> redirect on page he 'deserves'
                    return redirectSignedUserToHisHomePage(user.getRole());
                } else {
                    AppLogger.logError("Start method from Authentication controller: Strange behavior caught! user_id from session object not found in database");
                    session.removeAttribute("user_id");
                    session.invalidate();
                }
            } else {
                // we have session object, but don't have 'user_id', so probably we have 'message' attribute and we need delete it if we don't want to show same error message twice
                if(session.getAttribute("message") != null) {
                    session.removeAttribute("message");
                } else {
                    AppLogger.logError("Start method from Authentication controller: Strange behavior caught! We didn't get from session object neither user_id nor message! Invalidating this session...");
                    session.invalidate();
                }
            }
        }
        // and if session not exists -> just go Sign in
        return "sign_in";
    }

    @PostMapping({"/", "sign_in"})
    public String doSignIn(HttpServletRequest request){
        AppLogger.logInfo("LOGINFO TEST from post mapping method!");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        // checking existence of username and password form attributes
        if(username == null || password == null){
            AppLogger.logError("SignIn method from Authentication controller: username or password equals null in doSignIn POST request method");
            return "sign_in";
        }

        // looking in database thru our DAO object
        Users user = usersRepository.findByUsernameAndPassword(username, password);

        // create new session
        HttpSession session = request.getSession(true);
        // if there is no such user in db -> stay on 'sign in' page and set error message to display
        if(user == null){
            session.setAttribute("message", "Invalid username or password!");
            return "sign_in";
        }

        session.setAttribute("user_id", user.getId());
        // also don't forget remember-me checkbox(am i poked vain on him???)
        if(request.getParameterValues("remember-me") != null){
            // if it's selected --> set endless session timeout
            session.setMaxInactiveInterval(-1);
        } else{
            // setting session inactive interval = 30 mins
            session.setMaxInactiveInterval(30*60);
        }

        // and in the end of all -> redirect user on the page he 'deserves'
        return redirectSignedUserToHisHomePage(user.getRole());
    }



    private String redirectSignedUserToHisHomePage(Roles role){
        switch(role){
            case admin: return "redirect:admin/";
            case doctor: return "redirect:doctor/";
            case user: return "patient/home";
            default: return "redirect:sign_in";
        }
    }


}
