package BE;

public class Movie {

    private int id;

    private String name;

    private double rating;

    private String fileLink;

    private int lastView;

    private double personalRating;


    public Movie(int id, String name, double rating, String fileLink, int lastView, double personalRating) {
        this.id = id;
        this.name = name;
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

    public int getLastView() {

        return lastView;
    }

    public double getPersonalRating() {

        return personalRating;
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

    public void setLastView() {

        this.lastView = lastView;
    }

    public void setPersonalRating(Double personalRating) {

        this.personalRating = personalRating;
    }

}
