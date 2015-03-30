import dao.SummaryDAO;
import mapper.LocationMapper;
import entity.Summary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.SummaryParser;
import provider.SummaryProvider;
import service.SummaryService;
import service.SummaryServiceException;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.velocity.VelocityTemplateEngine;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

/**
 * Created by pixel on 3/19/15.

 * 2)
 */
public class Server {
    private static final Logger log = LoggerFactory.getLogger(Server.class);
    private LocationMapper locationMapper;
    private SummaryDAO summaryDAO;
    private SummaryParser summaryParser;
    private SummaryService summaryService;

    public void bootstrap() throws IOException {
        locationMapper = new LocationMapper();
        locationMapper.init();
        summaryDAO = new SummaryDAO();
        summaryDAO.connect();
        summaryParser = new SummaryParser();
        summaryService = new SummaryService(summaryDAO
                , new SummaryProvider(locationMapper, summaryParser));

    }

    public void start() throws Exception {
        /**
         * i use HTTP GET because my system doesn't has a state because
         * it is a sync task in which rabota.ua site keeps the state of data.
         */
        get("/cv", this::getSummary, new VelocityTemplateEngine());
        get("/find/cv_list", this::getSummariesLinks, new VelocityTemplateEngine());

        get("/", (req, res) -> {
            ModelAndView modelAndView = new ModelAndView(new HashMap<>(), "index.vm");
            return modelAndView;
        }, new VelocityTemplateEngine());
    }


    private ModelAndView getSummary(Request req, Response res) {
        Summary summary = null;
        String summaryId = req.queryParams("cv_id");
        String msg = null;
        if (summaryId != null && !summaryId.isEmpty()) {
            try {
                summary = summaryService.getAndPersistSummary(summaryId);
            } catch (SummaryServiceException e) {
                log.error("Couldn't get and persist summary due to service exception", e);
                msg = "Couldn't get summary";
            }
        } else {
                msg = "One of the required parameter is not set";
        }
        Map<Object, Object> model = new HashMap<>();
        model.put("summary", summary);
        model.put("msg", msg);
        ModelAndView modelAndView = new ModelAndView(model, "summary.vm");

        return modelAndView;
    }

    private ModelAndView getSummariesLinks(Request req, Response res) {
        String msg = null;
        List<Summary> summaries = null;
        String description = req.queryParams("desc");
        String location = req.queryParams("loc");
        if (description != null && !description.isEmpty()
                && location != null && !location.isEmpty()) {
            // reject rare cases
            // when client searches everywhere and everything
            try {
                summaries = summaryService.getSummaryLinks(description, location);
            } catch (SummaryServiceException e) {
                log.error("Couldn't get summary links due to service exception", e);
                msg = "Couldn't get summary links";
            }

        } else {
                msg = "One of the required parameter is not set";
        }
        HashMap model = new HashMap();
        model.put("summaries", summaries);
        model.put("msg", msg);
        ModelAndView modelAndView = new ModelAndView(model, "index.vm");

        return modelAndView;
    }
}