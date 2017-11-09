package es.udc.fic.ipm.beers;

/**
 * Created by wifopc on 31/10/17.
 */

public class Beer {

    //guardamos el numero de fila para despues editar su comentario
    private int rowNum;
    private String name;
    //tiene que ser string pq Estrella Galicia pone muchas veces
    private String date;
    private String madeIn;
    private String type;
    private String comment;
    private String moreInfo;
    private String photoURL;


    public Beer(int rowNum, String name, String date, String madeIn, String type,
                String comment, String moreInfo, String photoURL) {
        this.rowNum = rowNum;
        this.name = name;
        this.date = date;
        this.madeIn = madeIn;
        this.type = type;
        this.comment = comment;
        this.moreInfo = moreInfo;
        this.photoURL = photoURL;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRowNum() {
        return this.rowNum;
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
