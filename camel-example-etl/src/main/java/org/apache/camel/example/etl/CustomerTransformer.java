package org.apache.camel.example.etl;

import java.util.List;
import javax.persistence.EntityManager;
import org.apache.camel.Converter;
import org.apache.camel.Exchange;
import org.apache.camel.component.jpa.JpaConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@Converter
public class CustomerTransformer {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerTransformer.class);

    public CustomerTransformer() {
    }
    
    @Converter
    public static CustomerEntity toCustomer(PersonDocument doc, Exchange exchange) {
        EntityManager entityManager = exchange.getProperty(JpaConstants.ENTITY_MANAGER, EntityManager.class);
        TransactionTemplate transactionTemplate = exchange.getContext().getRegistry().lookupByNameAndType("transactionTemplate", TransactionTemplate.class);
        
        String user = doc.getUser();
        CustomerEntity customer = findCustomerByName(transactionTemplate, entityManager, user);
        
        customer.setUserName(user);
        customer.setFirstName(doc.getFirstName());
        customer.setSurname(doc.getLastName());
        customer.setCity(doc.getCity());
        
        LOG.info("Created object customer: {}", customer);
        
        return customer;
    }

    private static CustomerEntity findCustomerByName(TransactionTemplate transactionTemplate, EntityManager entityManager, String userName) {
        return transactionTemplate.execute(new TransactionCallback<CustomerEntity>() {
            @Override
            public CustomerEntity doInTransaction(TransactionStatus status) {
                entityManager.joinTransaction();
                List<CustomerEntity> list = entityManager.createNamedQuery("findCustomerByUsername", CustomerEntity.class).setParameter("userName", userName).getResultList();
                CustomerEntity answer;
                if(list.isEmpty()) {
                    answer = new CustomerEntity();
                    answer.setUserName(userName);
                    LOG.info("Created a new CustomerEntity {} as no matching persisted entity found.", answer);
                } else {
                    answer = list.get(0);
                    LOG.info("Found a matching CustomerEntity {} having the userName {}.", answer, userName);
                }
                
                return answer;
            }
        });
    }
}
