package beans;

import utils.DataBaseHandler;
import utils.Hit;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Class to handle operations
 */
@ManagedBean(name = "handlerBean")
@SessionScoped
public class HandlerBean implements Serializable {
     private long startTime;

    public Hit hit = new Hit();
    public List<Hit> hits = new ArrayList<>();

    /**
     * Get hits from database
     */
    public HandlerBean() {
        setHits(DataBaseHandler.getHits());
    }

    /**
     * Set hit
     * @param hit hit
     */
    public void setHit(Hit hit) {
        this.hit = hit;
    }

    /**
     * Get hit
     * @return hit
     */
    public Hit getHit() {
        return hit;
    }

    /**
     * Set hits
     * @param hits hits
     */
    public void setHits(List<Hit> hits) {
        this.hits = hits;
    }

    /**
     * Get hits
     * @return hits
     */
    public List<Hit> getHits() {
        List<Hit> outputHits = new ArrayList<>(hits);
        Collections.reverse(outputHits);
        return outputHits;
    }

    /**
     * Get information about hit and add to database
     */
    public void addHit() {
        startTime = System.currentTimeMillis();
        hit.setHit(hit.checkHit());
        hit.setCurrentTime(LocalDateTime.now());
        hit.setWorkTime((System.currentTimeMillis() - startTime));
        hits.add(hit);
        DataBaseHandler.addHit(hit);
        hit = new Hit(hit.getX(), hit.getY(), hit.getR());
    }
}
