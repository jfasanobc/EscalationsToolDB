package Orphans;

public class Category {
    private String catName;
    private int catID;
    
    public Category(String name, int id) {
        catName = name;
        catID = id;
    }

    public String getCatName() {
        return catName;
    }

    public int getCatID() {
        return catID;
    }

}
