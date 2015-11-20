/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import java.util.LinkedList;

/**
 *
 * @author Julio
 */
public class MovingAverage {
    private final LinkedList<Long> values;
    private final int length;
    private long sum;
    private long average;
    
    public MovingAverage (int length) {
        this.length = length;
        this.values = new LinkedList<>();
        this.sum = 0;
        this.average = 0;
    }
    
    public long compute (long value) {
        if (values.size() == length && length > 0) {
            sum -= (values.getFirst());
            values.removeFirst();
        }
        sum += value;
        values.addLast(value);
        average = sum / values.size();
        return average;
    }
}
