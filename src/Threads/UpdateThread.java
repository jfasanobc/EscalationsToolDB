/* 
  Julio Sevilla
  CLASS 
  ASSIGNMENT NAME
  DATE
  Developed on NetBeans IDE 8.0 (Build 201403101706)
  Java: 1.8.0_05
*/
package Threads;

import BCLibrary.StoreBasic;
import javax.swing.JEditorPane;


public class UpdateThread extends Thread{
    final private JEditorPane pane;
    private boolean alive;
    final private StoreBasic store;
    private int percentage;
     
        public UpdateThread(JEditorPane pane, StoreBasic store) {
            alive = true;
            this.pane = pane;
            this.store = store;
            percentage = 0;
        }
        public void run() {
            while(alive) {
                pane.setText("Connected: Yes\n" + "API Limit: " + store.getAPILimit());
                try {
                    Thread.sleep(2);
                } catch (InterruptedException ex) {
                    
                }
                if (percentage == 100)
                    alive = false;
            }
        }
        public void toggle(String choice) {
            if (choice.equalsIgnoreCase("on"))
                alive = true;
            else
                alive = false;
        }
        
        public boolean isOn() {
            return alive;
        }
        
        public void setPercentage(int number) {
            percentage = number;
        }

}
