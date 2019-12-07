package com.Clinic_Accounting_System.Clinic_Accounting_System.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public final class ControllerUtils {

    public static void giveTicketToMyMessage(HttpSession session, String message){
        session.setAttribute("message_ticket", true);
        session.setAttribute("message", message);
    }

    public static void goThru_MessageByTicket_System(HttpSession session){
        // implementing meine invention: display-message-with-ticket paradigm
        if(session.getAttribute("message_ticket") != null){
            session.removeAttribute("message_ticket");
        } else {
            if(session.getAttribute("message")!=null) session.removeAttribute("message");
        }
    }

    public static String processNonexistentUserWithValidSessionParams(HttpSession session, HttpServletRequest request){
        // invalidate this session and notify that user with such 'user_id' does not exist
        session.invalidate();
        session = request.getSession(true);
        giveTicketToMyMessage(session, "User with such user_id not found. Sorry!");
        return "redirect:/sign_in";
    }

}
