package parser;

import com.google.common.collect.Lists;
import entity.PreviousJob;
import entity.Summary;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pixel on 3/28/15.
 */
public class SummaryParser {
    public List<Summary> parseSummaryList(String htmlDocument) throws IOException {
        Document doc = Jsoup.parse(htmlDocument);
        Element summaryList = doc.getElementById("list_holder");
        Elements summaries = summaryList.getElementsByClass("resitem");
        List<Summary> result = new ArrayList<>();
        summaries.forEach((summary) -> {
            Summary s = new Summary();

            Elements resumeName = summary.getElementsByClass("resumeName");
            if (resumeName != null && !resumeName.isEmpty()) {
                Element element = resumeName.get(0);
                s.setJobPosition(element.text());
                String id = element.attr("href");
                if (id != null && !id.isEmpty())
                s.setId(id.split("/")[2]);
            }

            Elements name = summary.getElementsByAttributeValueEnding("id"
                    , "ResumeList_lblName_" + result.size());
            if (name != null && !name.isEmpty()) {
                s.setOwnerName(name.get(0).text());
            }
            result.add(s);
        });
        return result;
    }

    public Summary parseSummary(String htmlDocument) throws IOException {
        Document doc = Jsoup.parse(htmlDocument);
        Summary summary = new Summary();

        Element name = doc.getElementById("centerZone_BriefResume1_CvView1_cvHeader_lblName");
        if (name != null) {
            summary.setOwnerName(name.text());
        }

        Element jobPosition = doc.getElementById("centerZone_BriefResume1_CvView1_cvHeader_txtJobName");
        if (jobPosition != null) {
            summary.setJobPosition(jobPosition.text());
        }

        Element aimHolder = doc.getElementById("AimHolder");
        if (aimHolder != null) {
            Element summaryAim = aimHolder.getElementById("centerZone_BriefResume1_CvView1_Aim_txtAim");
            if (summaryAim != null) {
                summary.setGoal(summaryAim.text());
            }
        }

        Element skillsHolder = doc.getElementById("SkillsHolder");
        if (skillsHolder != null) {
            Element skills = skillsHolder.getElementById("centerZone_BriefResume1_CvView1_Skills_txtSkills");
            if (skills != null) {
                summary.setSkills(skills.text());
            }
        }


        Element experienceHolder = doc.getElementById("ExperienceHolder");
        if (experienceHolder != null) {
            Elements experience = experienceHolder.getElementsByTag("p");
            if (experience != null && !experience.isEmpty()) {
                List<PreviousJob> experienceList = Lists.newArrayList();
                experience.forEach((e) -> {
                    if (!e.className().equals("muted")) {
                        PreviousJob previousJob = new PreviousJob();
                        Elements prevJob = e.getElementsByTag("b");
                        if (prevJob != null && !prevJob.isEmpty()) {
                            previousJob.setTitle(prevJob.text());
                        }
                        Elements period = e.getElementsByClass("muted");
                        if (period != null && !period.isEmpty()) {
                            previousJob.setWorkPeriod(period.text());
                        }
                        if (previousJob.getTitle() != null) {
                            experienceList.add(previousJob);
                        }
                    }
                });
                summary.setExperience(experienceList);
            }
        }

        Element educationHolder = doc.getElementById("EducationHolder");
        if (educationHolder != null) {
            Elements education = educationHolder.getElementsByTag("p");
            if (education != null && !education.isEmpty()) {
                List<String> educationList = Lists.newArrayList();
                education.forEach((e) -> educationList.add(e.text()));
                summary.setEducation(educationList);
            }
        }
        return summary;
    }
}
