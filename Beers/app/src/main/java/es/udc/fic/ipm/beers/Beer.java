package es.udc.fic.ipm.beers;

/**
 * Created by wifopc on 31/10/17.
 */

public class Beer {

    private String name;
    //tiene que ser string pq Estrella Galicia pone muchas veces
    private String date;
    private String madeIn;
    private String type;
    private String comment;
    private String moreInfo;
    private String photoURL;

    public Beer(String name, String date, String madeIn, String type,
                String comment, String moreInfo, String photoURL) {
        this.name = name;
        this.date = date;
        this.madeIn = madeIn;
        this.type = type;
        this.comment = comment;
        this.moreInfo = moreInfo;
        this.photoURL = photoURL;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMadeIn(String madeIn) {
        this.madeIn = madeIn;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    public void setPhotoURL (String photoURL) {
        this.photoURL = photoURL;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getMadeIn() {
        return madeIn;
    }

    public String getType() {
        return type;
    }

    public String getComment() {
        return comment;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public String getPhotoURL() {
        return this.photoURL;
    }
}
