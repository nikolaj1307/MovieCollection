package BE;

public class Category {

    private int catId;

    private String catName;


    public Category(int catId, String catName) {
        this.catId = catId;
        this.catName = catName;
    }

    public Category(String catName) {
        this.catName = catName;
    }

    public String getCatName() {
        return catName; }
    

}
