/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Store;

/**
 *
 * @author julio
 */
public class CategoryNode {
    private int categoryID;
    private String categoryName;
    private int parentID;
    
    public CategoryNode (String name, int id) {
        categoryID = id;
        categoryName = name;
        parentID = -1;
    }
    
    public CategoryNode (String name, int id, int pID) {
        categoryID = id;
        categoryName = name;
        parentID = pID;
    }
    public String toString() {
        return categoryName;
    }
    public String getName() {
        return categoryName;
    }
    public int getID() {
        return categoryID;
    }
    
    public int getParentID() {
        return parentID;
    }
}
