package com.Clinic_Accounting_System.Clinic_Accounting_System.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;

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

    public static void makeCorrectionForTimeZone(Date date){
        // making 'wind correction' cuz actually JS picker returning one day back date(because it's returning GMT-0, and i'm in GMT+3), i.e if we pick 23 Feb 00:00:00, it'll return 22 Feb 21:00:00
        date.setTime(date.getTime()+1000*60*60*24);
    }

}
