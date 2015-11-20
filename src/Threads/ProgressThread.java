/* 
  Julio Sevilla
  CLASS 
  ASSIGNMENT NAME
  DATE
  Developed on NetBeans IDE 8.0 (Build 201403101706)
  Java: 1.8.0_05
*/
package Threads;
import java.util.concurrent.TimeUnit;
import javax.swing.JProgressBar;


public class ProgressThread extends Thread {
    private boolean alive;
        final private JProgressBar threadBar;
        private boolean isTimerAvailable;
        private boolean isIndeterminate;
        private int percentage;
        private String timer;
        private String currentTask;
        private MovingAverage average;
        
        public ProgressThread (JProgressBar bar) {
            this.alive = true;
            this.threadBar = bar;
            this.threadBar.setValue(0);
            this.threadBar.setStringPainted(true);
            this.isIndeterminate = false;
            timer = "unknown";
            this.isTimerAvailable = false;
            currentTask = "";
            percentage = 0;
            average = new MovingAverage(200);
        }
        public ProgressThread (JProgressBar bar, boolean isIndeterminate) {
            this.alive = true;
            this.threadBar = bar;
            if (!isIndeterminate)
                this.threadBar.setValue(0);
            else 
                this.threadBar.setIndeterminate(true);
            this.threadBar.setStringPainted(true);
            this.isTimerAvailable = false;
            timer = "unknown";
            currentTask = "";
            percentage = 0;
            average = new MovingAverage(200);
        }
        
        public ProgressThread (JProgressBar bar, boolean isIndeterminate, boolean isTimerAvailable) {
            this.alive = true;
            this.threadBar = bar;
            if (!isIndeterminate)
                this.threadBar.setValue(0);
            else 
                this.threadBar.setIndeterminate(true);
            this.threadBar.setStringPainted(true);
            if (!isIndeterminate)
                this.isTimerAvailable = isTimerAvailable;
            else
                this.isTimerAvailable = false;
            timer = "unknown";
            currentTask = "";
            percentage = 0;
            average = new MovingAverage(200);
        }
        
        @Override
        public void run() {
            while(alive) {
                if (!isIndeterminate) {
                    threadBar.setValue(percentage);
                    if (!isTimerAvailable)
                        threadBar.setString(currentTask + " " + percentage + "%");
                    else
                        threadBar.setString(currentTask + " " + percentage + "%" + " (Estimated Time: " + timer + ")");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    
                    }
                }
                if (percentage == 100) {
                    if (!isIndeterminate) {
                        threadBar.setValue(100);
                        threadBar.setString(currentTask + " " + percentage + "%");
                    }
                    else 
                        threadBar.setIndeterminate(false);
                    alive = false;
                }
                threadBar.repaint();
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
        
        public boolean isTimerAvailable() {
            return isTimerAvailable;
        }
        
        public void setTimer (boolean turnOnTimer) {
            isTimerAvailable = turnOnTimer;
        }
        
        public void setCurrentTask(String task) {
            currentTask = task;
            threadBar.update(threadBar.getGraphics());
        }
        
        public void setTimerString(String timerString) {
            timer = timerString;
        }
        
        public void setPercentage(int number) {
            percentage = number;
        }
        
        public void setIndefinate (boolean value) {
            if (value) {
                isIndeterminate = value;
                threadBar.setIndeterminate(value);
            }
            else {
                isIndeterminate = value;
                threadBar.setValue(threadBar.getMinimum());
                threadBar.setIndeterminate(value);
            }
        }
        
        public boolean isIndefinate() {
            return threadBar.isIndeterminate();
        }
        
        public void generateTime (long startTimer, int totalCount, int currentCount, int offSet) {
            String value = "";
            long timerSeconds = average.compute(System.nanoTime() - startTimer);
            timerSeconds *= ((totalCount - currentCount) / offSet);
            int day = (int) TimeUnit.NANOSECONDS.toDays(timerSeconds);
            long hours = TimeUnit.NANOSECONDS.toHours(timerSeconds) - (day * 24);
            long minute = TimeUnit.NANOSECONDS.toMinutes(timerSeconds) - (TimeUnit.NANOSECONDS.toHours(timerSeconds) * 60);
            long second = TimeUnit.NANOSECONDS.toSeconds(timerSeconds) - (TimeUnit.NANOSECONDS.toMinutes(timerSeconds) *60);
            if(day > 0) value += day + "d ";
            if(hours > 0) value += hours + "h ";
            if(minute > 0) value += minute + "m ";
            if(second > 0) value += second + "s ";
            if(second == 0 && timerSeconds > 0) value = "< 1s";
            timer = value;
        }

}
