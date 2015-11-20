package Orphans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class HashCategoryMap {
    private int totalCategory;
    private HashMap <Integer, ArrayList<Category>> categories;
    
    public HashCategoryMap() {
        totalCategory = 0;
        categories = new HashMap<>();
    }
    
    public void add(Category category, int parent) {
        if (categories.containsKey(parent)) {
            ArrayList <Category> tempList = categories.get(parent);
            tempList.add(category);
            categories.put(parent, tempList);
            totalCategory++;
        }
        else {
            ArrayList <Category> tempList = new ArrayList<>();
            tempList.add(category);
            categories.put(parent, tempList);
            totalCategory++;
        }
    }
    
    public void add(ArrayList <Category> list, int parent) {
        if (categories.containsKey(parent)) {
            ArrayList <Category> tempList = categories.get(parent);
            totalCategory -= tempList.size();
            categories.put(parent, list);
            totalCategory += list.size();
        }
        else {
            categories.put(parent, list);
            totalCategory += list.size();
        }
            
    }
    
    public ArrayList <Category> getFromParentID(int parent) {
        if (categories.containsKey(parent))
            return categories.get(parent);
        else
            return null;
    }
    public int getCount() {
        return totalCategory;
    }
    // get parents
    public Set <Integer> getKeys() {
        return categories.keySet();
    }
}
