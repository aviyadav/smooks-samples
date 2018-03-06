package example.dao;

import example.jpa.entity.Order;
import javax.persistence.EntityManager;
import org.milyn.scribe.annotation.Dao;
import org.milyn.scribe.annotation.Insert;

@Dao
public class OrderDao {
    
    private final EntityManager em;

    public OrderDao(EntityManager em) {
        this.em = em;
    }
    
    @Insert
    public void insertOrder(Order order) {
        em.persist(order);
    }
}
