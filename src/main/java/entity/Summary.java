package entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by pixel on 3/28/15.
 */
public class Summary implements Serializable{

    private String ownerName;
    private String goal;
    private String skills;
    private String jobPosition;
    private List<PreviousJob> experience;
    private List<String> education;
    private String trainings;
    private String id;

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public List<PreviousJob> getExperience() {
        return experience;
    }

    public void setExperience(List<PreviousJob> experience) {
        this.experience = experience;
    }

    public List<String> getEducation() {
        return education;
    }

    public void setEducation(List<String> education) {
        this.education = education;
    }

    public String getTrainings() {
        return trainings;
    }

    public void setTrainings(String trainings) {
        this.trainings = trainings;
    }

    public String getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }
}
