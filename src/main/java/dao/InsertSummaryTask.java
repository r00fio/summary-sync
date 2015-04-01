package dao;

import com.mongodb.MongoException;
import entity.Summary;
import org.eclipse.jetty.util.ConcurrentHashSet;

import java.util.Iterator;

/**
 * Created by pixel on 3/27/15.
 */
public class InsertSummaryTask implements Runnable {

    private SummaryDAO summaryDAO;
    public static final ConcurrentHashSet<Summary> SUMMARIES = new ConcurrentHashSet<>();

    public InsertSummaryTask(SummaryDAO summaryDAO) {
        this.summaryDAO = summaryDAO;
    }

    public static boolean addSummary(Summary summary) {
        return SUMMARIES.add(summary);
    }

    @Override
    public void run() {
        try {
            Iterator<Summary> iterator = SUMMARIES.iterator();
            while (iterator.hasNext()) {
                Summary next = iterator.next();
                iterator.remove();
                summaryDAO.insert(next);
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }
}
