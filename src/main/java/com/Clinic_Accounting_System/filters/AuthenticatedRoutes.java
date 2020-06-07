package com.Clinic_Accounting_System.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(
        urlPatterns = {"/admin/*",
                        "/doctor/*",
                        "/patient/*",
                        "/sign_out"}, // all protected routes
        filterName = "AuthenticatedRoutesFilter",
        description = "Prevents from accidental access to authenticated routes when not authenticated."
)
public class AuthenticatedRoutes implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {}

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain)
            throws IOException, ServletException {
        // checking session obj existence
        HttpSession session = ((HttpServletRequest)request).getSession(false);
        if(session != null) {
            // if session objects exists
            Long id = (Long) session.getAttribute("user_id");
            String role = (String) session.getAttribute("role");

            if (id != null && role != null) {
                // means that we are authenticated
                // update max inactive interval in "no remember-me" type of session
                if(session.getMaxInactiveInterval() > 0) session.setMaxInactiveInterval(30*60);
                // check if role param in session matches requested URI path
                // approach works if URI matches pattern /webapp/doctor/home
                String[] uriChunks = ((HttpServletRequest)request).getRequestURI().split("/", 4);
                if(uriChunks[2].equals(role) || uriChunks[2].equals("sign_out")) {
                    filterChain.doFilter(request, response);
                } else {
                    // if user get lost -> redirect him to his home page
                    ((HttpServletResponse)response).sendRedirect(((HttpServletRequest) request).getContextPath() +
                                                                        "/" + role + "/home");
                }
                return;
            }
        }
        // we are not authenticated -> go sign in
        ((HttpServletResponse)response).sendRedirect(((HttpServletRequest) request).getContextPath() +"/sign_in");
    }

    public void destroy() {}
}
