package com.Clinic_Accounting_System.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(
        urlPatterns = {"/sign_in",
                "/pass_recovery/*",
                "/registration/*"},     // all admin's, doctor's and patient's paths are protected routes
        filterName = "NonAuthenticatedRoutesFilter",
        description = "Prevents from accidental access to non-authenticated routes."
)
public class NonAuthenticatedRoutes implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {}

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain)
            throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest)request).getSession(false);
        if(session != null) {
            // check session objects on existence
            Long id = (Long) session.getAttribute("user_id");
            String role = (String) session.getAttribute("role");
            if (id != null && role != null) {
                // means that we are authenticated
                request.getRequestDispatcher(role + "/home").forward(request, response);
            }
        }
        filterChain.doFilter(request, response);
    }

    public void destroy() {}
}
