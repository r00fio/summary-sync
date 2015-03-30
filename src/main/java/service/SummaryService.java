package service;

import dao.InsertSummaryTask;
import dao.SummaryDAO;
import entity.Summary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import provider.SummaryProvider;
import provider.SummaryProviderException;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

/**
 * Created by pixel on 3/28/15.
 */
public class SummaryService {
    private static final Logger log = LoggerFactory.getLogger(SummaryService.class);
    private final SummaryDAO summaryDAO;
    private final SummaryProvider summaryProvider;
    private static final ExecutorService executorGroup = Executors.newCachedThreadPool();

    public SummaryService(SummaryDAO summaryDAO, SummaryProvider summaryProvider) {
        this.summaryProvider = summaryProvider;
        this.summaryDAO = summaryDAO;
    }

    public Summary getAndPersistSummary(String summaryId) throws SummaryServiceException {
        Summary summary = summaryDAO.findById(summaryId);
        if (summary == null) {
            summary = summaryProvider.getSummary(summaryId);
            putSummary(summary);
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

    /**
     * @param summary
     * @throws SummaryServiceException
     */
    public void putSummary(Summary summary) throws SummaryServiceException {
        // insert in new thread because long running task
        // will block jetty selector
        try {
            executorGroup.execute(new InsertSummaryTask(summary, summaryDAO));
        } catch (RejectedExecutionException e) {
            log.error("Couldn't insert summary ", e);
            throw new SummaryServiceException("Couldn't insert summary", e);
        }
    }

}
