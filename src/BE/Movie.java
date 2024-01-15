package BE;

import java.util.Date;

public class Movie {

    private int id;

    private String name;

    private double rating;

    private String fileLink;

    private Date lastView;

    private double personalRating;

    private String catName;

    public Movie(int id, String name, String catName, double rating, String fileLink, Date lastView, double personalRating) {
        this.id = id;
        this.name = name;
        this.catName = catName;
        this.rating = rating;
        this.fileLink = fileLink;
        this.lastView = lastView;
        this.personalRating = personalRating;
    }

    public Movie(String name, double rating, String fileLink) {
        //this.id = id;
        this.name = name;
        this.rating = rating;
        this.fileLink = fileLink;
        //this.lastView = lastView;
        //this.personalRating = personalRating;
    }

    public Movie(String name, String catName, double rating, String fileLink) {
        this.name = name;
        this.catName = catName;
        this.rating = rating;
        this.fileLink = fileLink;
    }

    public int getId() {

        return id;
    }

    public String getName() {

        return name;
    }

    public double getRating() {

        return rating;
    }

    public String getFileLink() {

        return fileLink;
    }

    public Date getLastView() {

        return lastView;
    }

    public double getPersonalRating() {

        return personalRating;
    }

    public String getCatName() {
        return catName;
    }

    public void setName() {

        this.name = name;
    }

    public void setRating() {

        this.rating = rating;
    }

    public void setFileLink() {

        this.fileLink = fileLink;
    }

    public void setLastView(Date lastView) {

        this.lastView = lastView;
    }

    public void setPersonalRating(Double personalRating) {

        this.personalRating = personalRating;
    }

}
