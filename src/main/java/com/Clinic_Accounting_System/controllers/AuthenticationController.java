package com.Clinic_Accounting_System.Clinic_Accounting_System.controllers;

import com.Clinic_Accounting_System.Clinic_Accounting_System.models.Roles;
import com.Clinic_Accounting_System.Clinic_Accounting_System.models.Users;
import com.Clinic_Accounting_System.Clinic_Accounting_System.repositories.UsersRepository;
import com.Clinic_Accounting_System.Clinic_Accounting_System.services.UserService;
import com.Clinic_Accounting_System.Clinic_Accounting_System.utils.AppLogger;
import com.Clinic_Accounting_System.Clinic_Accounting_System.utils.ControllerUtils;
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
        // checking session obj existence
        HttpSession session = request.getSession(false);
        if(session != null){
            // if session objects exists
            Long id = (Long)session.getAttribute("user_id");
            Roles role = (Roles)session.getAttribute("role");
            if(id != null && role != null){
                // that means that we are authenticated
                // update max inactive interval in "no remember-me" type of session
                if(session.getMaxInactiveInterval() > 0) session.setMaxInactiveInterval(30*60);
                // if such user found in database -> redirect on page he 'deserves'
                return redirectSignedUserToHisHomePage(role);
            } else {
                // we have session, but don't have Auth objects
                ControllerUtils.goThru_MessageByTicket_System(session);
            }
        }
        // and if session not exists -> just go Sign in
        return "sign_in";
    }

    @PostMapping({"/", "sign_in"})
    public String doSignIn(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        // checking existence of username and password form attributes
        if(username == null || password == null){
            AppLogger.logError("SignIn method from Authentication controller: username or password equals null in doSignIn POST request method");
            return "redirect:/sign_in";
        }

        // looking in database thru our DAO object
        Users user = usersRepository.findByUsernameAndPassword(username, password);

        // create new session
        HttpSession session = request.getSession(true);
        // if there is no such user in db -> stay on 'sign in' page and set error message to display
        if(user == null){
            ControllerUtils.giveTicketToMyMessage(session, "Invalid username or password!");
            return "redirect:/sign_in";
        }

        // set Auth session objects
        session.setAttribute("user_id", user.getId());
        session.setAttribute("role", user.getRole());

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

    @PostMapping("/sign_out")
    public String doSign_out(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null) session.invalidate();
        return "redirect:/";
    }

//    // go by default on sign_in page and it will show u the way
//    @RequestMapping (value = "/**", method = {RequestMethod.GET, RequestMethod.POST})
//    public String defaultPath() {
//        AppLogger.logWarn("In catcher!");
//        return "redirect:/sign_in";
//    }

    private String redirectSignedUserToHisHomePage(Roles role){
        switch(role){
            case admin: return "redirect:/admin/";
            case doctor: return "redirect:/doctor/";
            case user: return "redirect:/patient/";
            default: return "redirect:sign_in";
        }
    }


}
