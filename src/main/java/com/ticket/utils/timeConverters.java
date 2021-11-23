package com.ticket.utils;

public class timeConverters {

    /**
     * Converts the given string duration to a second-based integer
     * Supports m, h,hr,d,day time formatting
     * @param dur String
     * @return dur int
     */
    public static int getDuration(String dur){
        if(dur.contains("m")){
            int index = dur.indexOf("m");
            return 60*Integer.parseInt(dur.substring(0,index));
        }
        else if(dur.contains("h") || dur.contains("hr")){
            int index = dur.indexOf("h");
            return 3600*Integer.parseInt(dur.substring(0,index));
        }
        else if(dur.contains("d") || dur.contains("day")){
            int index = dur.indexOf("d");
            return (24*3600)*Integer.parseInt(dur.substring(0,index));
        }
        else{
            return (int)Integer.parseInt(dur);
        }
    }

    /**
     * Converts the given integer of time (in seconds) into a string
     * @param duration int
     * @return dur String
     */
    public static String getStringDuration(int duration){
        String dur;
        if(duration <=60){
            dur = String.valueOf(duration) + "s";
        }
        else if (duration <3600 ){
            dur = String.valueOf(duration/60) + "m";
        }
        else if(duration < 86400){
            dur = String.valueOf(duration/3600) + "h";
        }
        else{
            dur = String.valueOf(duration/86400) +"d";
        }
        return dur;
    }

}
