package com.ticket.utils;

import java.util.ArrayList;

public class TabCompleteHelper {

    /**
     * Recreates the behavior of Bukkit's copyPartialMatches and adds all Strings in iterable which are contained in
     * the given key
     * @param key String which is checked against
     * @param iterable The ArrayList which is iterated over for matches
     * @param current The ArrayList which the matches should be added to
     * @return The ArrayList of matches added to the given current ArrayList
     */
    public static ArrayList<String> copyPartialMatches(String key, Iterable<String> iterable, ArrayList<String> current){

        for(String item: iterable){
            if(item.toLowerCase().startsWith(key.toLowerCase())){
                current.add(item);
            }
        }
        return current;
    }
}
