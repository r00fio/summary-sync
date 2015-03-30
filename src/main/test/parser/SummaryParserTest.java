package parser;

import com.google.common.collect.Lists;
import entity.PreviousJob;
import entity.Summary;
import org.junit.Test;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.unitils.reflectionassert.ReflectionAssert.*;

public class SummaryParserTest {

    @Test
    public void testResourceExists() throws Exception {
        assertNotNull("Test file missing",
                getClass().getResource("../resources/SummaryList.html"));
    }

    @Test
    public void testParseSummaryList() throws Exception {
        Summary s = new Summary();
        s.setJobPosition("Junior Java developer");
        s.setId("7217333");
        s.setOwnerName("Александр");
        ArrayList<Summary> expected = Lists.newArrayList(s);
        SummaryParser summaryParser = new SummaryParser();
        URL url = Thread.currentThread().getContextClassLoader().getResource("resources/SummaryList.html");
        String htmlDocument = new String(Files.readAllBytes(Paths.get(url.toURI())));
        List<Summary> summary = summaryParser.parseSummaryList(htmlDocument);
        assertReflectionEquals(expected, summary);
    }

    @Test
    public void testParseSummary() throws Exception {
        Summary expected = new Summary();
        expected.setJobPosition("Junior .Net, Java, C++");
        expected.setOwnerName("Анатолий Юрьевич");
        expected.setSkills("o Офисные приложения MS Office :Word‚ Excel‚ Access‚ Visio; o Языки .Net (WinForms, ASP.NET,MVC, JQuery, AJAX, JavaScript), C++, Java; o ООП как свои 5 пальцев o Среды разработки Visual studio 2012, Dreamweaver, SQL Server o Базовые знания PHP,HTML; o 1С:Предприятие‚ BPWin‚ ERWin; o СУБД: MS SQL Server; o Подключение‚ настройка и обслуживание ПК и периферийных устройств.");
        expected.setGoal("Получение работы в престижной компании");
        ArrayList<PreviousJob> experience = Lists.newArrayList(
                new PreviousJob("фриланс", "янв 2013 - янв 2015 (2 года 1 мес)"),
                new PreviousJob("дипломы, курсовые, лабораторные", "янв 2011 - июн 2014 (3 года 6 мес)"),
                new PreviousJob("практика", "янв 2013 - апр 2013 (4 мес)"),
                new PreviousJob("практика", "янв 2011 - мар 2011 (3 мес)")
        );
        expected.setExperience(experience);
        expected.setEducation(Lists.newArrayList("ХНЕУ(Харьков) Год окончания 2014"));
        URL url = Thread.currentThread().getContextClassLoader().getResource("resources/Summary.html");
        String htmlDocument = new String(Files.readAllBytes(Paths.get(url.toURI())));
        SummaryParser summaryParser = new SummaryParser();
        Summary summary = summaryParser.parseSummary(htmlDocument);
        assertReflectionEquals(expected, summary);
    }

}