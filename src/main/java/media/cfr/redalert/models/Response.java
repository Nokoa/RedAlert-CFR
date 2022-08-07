package media.cfr.redalert.models;

import java.util.List;

/**
 * The JSON response model for the API request to OREF
 */
public class Response {

    private long id;
    private String cat;
    private String title;
    private List<String> data;
    private String desc;

    public Response() {
    }

    public Response(long id, String cat, String title, List<String> data, String desc) {
        this.id = id;
        this.cat = cat;
        this.title = title;
        this.data = data;
        this.desc = desc;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
