package example.dao;

import javax.persistence.EntityManager;

import org.milyn.scribe.annotation.Dao;
import org.milyn.scribe.annotation.Lookup;
import org.milyn.scribe.annotation.Param;

import example.jpa.entity.Product;

@Dao
public class ProductDao {

    private final EntityManager em;

    public ProductDao(EntityManager em) {
        this.em = em;
    }

    @Lookup(name = "id")
    public Product findProductById(@Param("id") int id) {
        return em.find(Product.class, id);
    }
}
