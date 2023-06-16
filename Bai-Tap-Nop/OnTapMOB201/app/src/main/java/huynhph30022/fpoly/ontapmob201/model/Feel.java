package huynhph30022.fpoly.ontapmob201.model;

import java.io.Serializable;

public class Feel implements Serializable {
    private String title, description, pubDate, link;
    private int idRss;

    public Feel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getIdRss() {
        return idRss;
    }

    public void setIdRss(int idRss) {
        this.idRss = idRss;
    }
}
