package example.dao;

import example.jpa.entity.Customer;
import javax.persistence.EntityManager;
import org.milyn.scribe.annotation.Dao;
import org.milyn.scribe.annotation.Param;

@Dao
public class CustomerDao {
    
    private final EntityManager em;

    public CustomerDao(EntityManager em) {
        this.em = em;
    }
    
    public Customer findCustomerById(@Param("id") int id) {
        return em.find(Customer.class, id);
    }
}
