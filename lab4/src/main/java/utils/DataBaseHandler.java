package utils;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


public class DataBaseHandler {
    private static EntityManagerFactory factory =
            Persistence.createEntityManagerFactory("hit");
    private static EntityManager entityManager = factory.createEntityManager();
    private static List<Hit> hits = new ArrayList<>();
    private static EntityTransaction transaction = entityManager.getTransaction();


    public static void addHit(Hit hit) {
        transaction.begin();
        entityManager.persist(hit);
        transaction.commit();
    }

    public static List<Hit> getHits() {
        transaction.begin();
        Query query = entityManager.createQuery("SELECT e FROM hit e");
        hits = query.getResultList();
        transaction.commit();
        return hits;
    }

}
