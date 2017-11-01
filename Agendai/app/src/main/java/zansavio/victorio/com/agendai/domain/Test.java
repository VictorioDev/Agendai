package zansavio.victorio.com.agendai.domain;

/**
 * Created by Victorio Zansavio on 24/09/2017.
 */

public class Test {
    private String title;
    private String date;

    public Test() {

    }

    public Test(String title, String date) {
        this.title = title;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
