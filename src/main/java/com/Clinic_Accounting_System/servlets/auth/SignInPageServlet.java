package com.Clinic_Accounting_System.servlets.auth;

import com.Clinic_Accounting_System.dao.UserDAO;
import com.Clinic_Accounting_System.entities.User;
import com.Clinic_Accounting_System.utils.ControllerUtils;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@Log4j2
@WebServlet(name = "SignInPageServlet", urlPatterns = {"/sign_in"})
public class SignInPageServlet extends HttpServlet {

    private final UserDAO userDAO = UserDAO.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("sign_in.jsp").forward(request, response);
    }

    public void doPost (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            // check existence of username and password form attributes
            if(username == null || password == null){
                log.warn("Hacker tried to go without user/password params at sign_in.");
                response.sendRedirect("/sign_in");
            }

            // look for user in database thru DAO object
            User user = userDAO.getByUsernameAndPassword(username, password);

            // create new session
            HttpSession session = request.getSession(true);
            // if there is no such user in db -> stay on 'sign in' page and set error message to display
            if(user == null) {
                ControllerUtils.giveTicketToMyMessage(session, "Invalid credentials!");
                response.sendRedirect("/sign_in");
            }

            // set Auth session objects
            session.setAttribute("user_id", user.getId());
            session.setAttribute("role", user.getRole());

            // also don't forget remember-me checkbox(am i poked vain on him???)
            if(request.getParameterValues("remember-me") != null){
                // if checkbox selected --> set endless session timeout
                session.setMaxInactiveInterval(-1);
            } else{
                // set session inactive interval = 30 mins by default
                session.setMaxInactiveInterval(30*60);
            }

            // redirect user to corresponded home page
            response.sendRedirect(user.getRole() + "/home");
        } catch (SQLException e) {
            log.error("500: SQLException at auth/SignInServlet");
            request.getRequestDispatcher("errors/500.html").forward(request, response);
        }
    }

}
