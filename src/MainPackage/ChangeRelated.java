package MainPackage;


import BCLibrary.StoreBasic;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONObject;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author julio.sevilla
 */
public class ChangeRelated {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, Exception {
        StoreBasic store = new StoreBasic("apipath","apiusername","apitoken");
        JSONObject newJSONObject = new JSONObject();
        newJSONObject.put("is_required", true);


        File file = new File("forgedparts_optionsets_options.csv");
        Scanner reader = new Scanner(file);
        long startTimer = 0;
        String id;
        String url = "option_sets/options/";
        int max_lines = 531;
        int counter = 0;
        long timerSeconds = 0;
        String timer;
        while (reader.hasNext()) {
            startTimer = System.nanoTime();
            
            counter++;
            id = reader.next();
            System.out.print("Updating " + url + id);
            store.put(url + id, newJSONObject.toString());
            timer = "";
            timerSeconds = (System.nanoTime() - startTimer) * (max_lines - counter);
                        int day = (int) TimeUnit.NANOSECONDS.toDays(timerSeconds);
                        long hours = TimeUnit.NANOSECONDS.toHours(timerSeconds) - (day * 24);
                        long minute = TimeUnit.NANOSECONDS.toMinutes(timerSeconds) - (TimeUnit.NANOSECONDS.toHours(timerSeconds) * 60);
                        long second = TimeUnit.NANOSECONDS.toSeconds(timerSeconds) - (TimeUnit.NANOSECONDS.toMinutes(timerSeconds) *60);
                        if(day > 0) timer += day + "d ";
                        if(hours > 0) timer+= hours + "h ";
                        if(minute > 0) timer+= minute + "m ";
                        if(second > 0) timer+= second + "s ";
            System.out.println(" : Time it took to update " + TimeUnit.NANOSECONDS.toMillis(timerSeconds) + "ms" + " (" + TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - startTimer) + "sec" + ")" + " Time remaining: " + timer + "API Limit at: " + store.getAPILimit());
        }

       
        
    }
}
