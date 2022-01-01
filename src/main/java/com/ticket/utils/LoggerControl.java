package com.ticket.utils;

import java.util.logging.Logger;

public class LoggerControl {

    private static Logger logger;

    /** Sets the active logger (Called by either Spigot Entrypoint or BungeeCord)
     * @param LoggerInstance Logger
     */
    public static void setLogger(Logger LoggerInstance){
        logger = LoggerInstance;
    }

    /**
     * All logs calls are logged as INFO
     * @param msg String
     */
    public static void log(String msg){
        logger.info(msg);
    }

    /** Logs a string as a warning to the active logger
     * @param msg String
     */
    public static void warning(String msg){
        logger.warning(msg);
    }


}
