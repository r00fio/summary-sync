package service;

import dao.InsertSummaryTask;
import dao.SummaryDAO;
import entity.Summary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import provider.SummaryProvider;
import provider.SummaryProviderException;

import java.util.List;
import java.util.concurrent.*;

/**
 * Created by pixel on 3/28/15.
 */
public class SummaryService {
    private static final Logger log = LoggerFactory.getLogger(SummaryService.class);
    private final SummaryDAO summaryDAO;
    private final SummaryProvider summaryProvider;
    private final InsertSummaryTask summaryPersistScheduler;
    public static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    public static final long TASKS_START_UP_DELAY = 10000;
    public static final long TASK_TIME_SLICE = 10000;

    public SummaryService(SummaryDAO summaryDAO, SummaryProvider summaryProvider) {
        this.summaryProvider = summaryProvider;
        this.summaryDAO = summaryDAO;
        summaryPersistScheduler = new InsertSummaryTask(summaryDAO);
        executor.scheduleAtFixedRate(summaryPersistScheduler,TASKS_START_UP_DELAY, TASK_TIME_SLICE, TimeUnit.MILLISECONDS);
    }

    public Summary getAndPersistSummary(String summaryId) throws SummaryServiceException {
        Summary summary = summaryDAO.findById(summaryId);
        if (summary == null) {
            summary = summaryProvider.getSummary(summaryId);
            summaryPersistScheduler.addSummary(summary);
        }
        return summary;
    }

    public List<Summary> getSummaryLinks(String description, String location) throws SummaryServiceException {
        try {
            List<Summary> summary =
                    summaryProvider.getSummariesLinks(description, location);
            return summary;
        } catch (SummaryProviderException e) {
            log.error("Can't retrieve summaries due to provider exception", e);
            throw new SummaryServiceException("Can't retrieve summaries due to provider exception", e);
        }
    }
}
