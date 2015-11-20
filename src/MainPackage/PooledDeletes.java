/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage;

import BCLibrary.StoreBasic;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PooledDeletes {
    
    public static void main(String argv[]) throws FileNotFoundException {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        StoreBasic store = new StoreBasic("apipath","apiusername","apitoken", true);
        File file = new File("sacc_delets.csv");
        Scanner reader = new Scanner(file);
        String id;
        while (reader.hasNext()) {
            id = reader.next();
            Runnable thread = store.deleteThread("categories/" + id);
            executor.execute(thread);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {}
        System.out.println("DONE!");
    }
}
