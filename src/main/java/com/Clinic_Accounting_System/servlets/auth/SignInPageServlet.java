package com.Clinic_Accounting_System.servlets.auth;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "SignInPageServlet", urlPatterns = {"/", "/sign_in"})
public class SignInPageServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
                return response.sendRedirect(getUserHomePage(user.getRole()));
            } else {
                // we have session, but don't have Auth objects
                ControllerUtils.goThru_MessageByTicket_System(session);
            }
        }
        // and if session not exists -> just go Sign in
        request.getRequestDispatcher("sign_in.jsp").forward(request, response);
    }

    public void doPost (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        // checking existence of username and password form attributes
        if(username == null || password == null){
            AppLogger.logError("SignIn method from Authentication controller: username or password equals null in doSignIn POST request method");
            response.sendRedirect("/sign_in");
        }

        // looking in database thru our DAO object
        Users user = usersRepository.findByUsernameAndPassword(username, password);

        // create new session
        HttpSession session = request.getSession(true);
        // if there is no such user in db -> stay on 'sign in' page and set error message to display
        if(user == null) {
            ControllerUtils.giveTicketToMyMessage(session, "Invalid username or password!");
            response.sendRedirect("/sign_in");
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
        return response.sendRedirect(getUserHomePage(user.getRole()));
    }

    private String getUserHomePage(Roles role){
        switch(role){
            case admin: return "/admin";
            case doctor: return "/doctor";
            case user: return "/patient";
            default: return "/sign_in";
        }
    }

}
