package huynhph30022.fpoly.music.model;

public class Music {
    private int id;
    private String tenNhac;
    private String linkNhac;

    public Music() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenNhac() {
        return tenNhac;
    }

    public void setTenNhac(String tenNhac) {
        this.tenNhac = tenNhac;
    }

    public String getLinkNhac() {
        return linkNhac;
    }

    public void setLinkNhac(String linkNhac) {
        this.linkNhac = linkNhac;
    }
}
