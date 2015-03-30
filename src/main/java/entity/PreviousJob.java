package entity;

import java.io.Serializable;

/**
 * Created by pixel on 3/29/15.
 */
public class PreviousJob implements Serializable{
    private String title;
    private String workPeriod;

    public PreviousJob() {
    }

    public PreviousJob(String title, String workPeriod) {
        this.title = title;
        this.workPeriod = workPeriod;
    }

    public String getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(String workPeriod) {
        this.workPeriod = workPeriod;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
