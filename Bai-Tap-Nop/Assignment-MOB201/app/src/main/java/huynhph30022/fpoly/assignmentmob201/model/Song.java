package huynhph30022.fpoly.assignmentmob201.model;

import java.io.Serializable;

public class Song implements Serializable {
    private int id;
    private String tenBaiHat;
    private String link;

    public Song() {
    }

    public Song(int id, String tenBaiHat, String link) {
        this.id = id;
        this.tenBaiHat = tenBaiHat;
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenBaiHat() {
        return tenBaiHat;
    }

    public void setTenBaiHat(String tenBaiHat) {
        this.tenBaiHat = tenBaiHat;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
