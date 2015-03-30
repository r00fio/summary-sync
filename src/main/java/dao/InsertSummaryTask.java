package dao;

import com.mongodb.MongoException;
import entity.Summary;

/**
 * Created by pixel on 3/27/15.
 */
public class InsertSummaryTask implements Runnable {

    private SummaryDAO summaryDAO;
    private final Summary summary;

    public InsertSummaryTask(Summary summary, SummaryDAO summaryDAO) {
        this.summaryDAO = summaryDAO;
        this.summary = summary;
    }

    @Override
    public void run() {
        try {
            summaryDAO.insert(summary);
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }
}
