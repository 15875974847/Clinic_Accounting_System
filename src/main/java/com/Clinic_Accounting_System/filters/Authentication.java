package com.Clinic_Accounting_System.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(
        urlPatterns = "/*",
        filterName = "AuthenticationFilter",
        description = "Handles application's authentication."
)
public class Authentication implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain)
            throws IOException, ServletException {

    }

    public void destroy() {
    }
}
