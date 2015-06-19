package helabs.myandroidchallenge.model;

/**
 * Created by Carlos Alexandre on 12/06/2015.
 */
public class Comment {

    private String id;
    private String author;
    private String authorname;
    private String iconserver;
    private String iconfarm;
    private String datecreate;
    private String permalink;
    private String path_alias;
    private String realname;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    public String getIconserver() {
        return iconserver;
    }

    public void setIconserver(String iconserver) {
        this.iconserver = iconserver;
    }

    public String getIconfarm() {
        return iconfarm;
    }

    public void setIconfarm(String iconfarm) {
        this.iconfarm = iconfarm;
    }

    public String getDatecreate() {
        return datecreate;
    }

    public void setDatecreate(String datecreate) {
        this.datecreate = datecreate;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getPath_alias() {
        return path_alias;
    }

    public void setPath_alias(String path_alias) {
        this.path_alias = path_alias;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
