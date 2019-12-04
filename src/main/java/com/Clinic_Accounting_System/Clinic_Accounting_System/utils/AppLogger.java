package com.Clinic_Accounting_System.Clinic_Accounting_System.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AppLogger {
    private static final Logger logger = LoggerFactory.getLogger(AppLogger.class);

    public static void logError(String s){logger.error(s);}
    public static void logWarn(String s){logger.warn(s);}
    public static void logInfo(String s){logger.info(s);}
}
