package dao;

import com.google.gson.Gson;
import com.mongodb.*;
import com.mongodb.util.JSON;
import entity.Summary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;

/**
 * Created by pixel on 3/27/15.
 */
public class SummaryDAO {
    private static final Logger log = LoggerFactory.getLogger(SummaryDAO.class);
    private static final String DB_NAME = "rabota_ua";
    private static final String COLLECTION = "summary";

    private MongoClient connectionsPool;
    private DBCollection cvCollection;
    private Gson gson = new Gson();

    public void connect() throws DaoException{
        try {
            connectionsPool = new MongoClient();
        } catch (UnknownHostException e) {
            log.error("Couldn't get connection to db");
            throw new DaoException("Couldn't get connection to db", e);
        }
        DB db = connectionsPool.getDB(DB_NAME);
        cvCollection = db.collectionExists(COLLECTION) ? db.getCollection(COLLECTION) :
                db.createCollection(COLLECTION, new BasicDBObject());

    }

    public void insert(Summary summary) throws DaoException {
        try {

            cvCollection.insert((DBObject) JSON.parse(gson.toJson(summary)));
        } catch (MongoException e) {
            log.error("Couldn't insert summary");
            throw new DaoException("Couldn't insert summary", e);
        }
    }

    public Summary findById(String criteria) {
        Summary result = null;
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("id", criteria);
        DBCursor cursor = cvCollection.find(whereQuery);
        while (cursor.hasNext()) {
            DBObject next = cursor.next();
            result = gson.fromJson(next.toString(), Summary.class);
        }
        return result;
    }
}
