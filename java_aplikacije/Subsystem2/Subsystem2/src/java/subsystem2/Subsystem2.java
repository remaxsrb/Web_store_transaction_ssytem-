/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package subsystem2;

import entities.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.validation.ConstraintViolationException;


/**
 *
 * @author remax
 */
public class Subsystem2 {
    
    private static final byte CREATE_CATEGORY = 5;
    private static final byte CREATE_ARTICLE = 6;
    private static final byte MODIFY_ARTICLE_PRICE = 7;
    private static final byte ADD_ARTICLE_DISCOUNT = 8;
    private static final byte ADD_TO_CART = 9;
    private static final byte REMOVE_FROM_CART = 10;
    private static final byte ALL_CATEGORIES = 14;
    private static final byte ALL_ARTICLES_USER_IS_SELLING = 15;
    private static final byte VIEW_CART = 16;
    
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("Subsystem2PU");
    static EntityManager em = emf.createEntityManager();
    
    @Resource(lookup = "myConnFactory")
    static ConnectionFactory connFactory;
    
    @Resource(lookup="serverTestTopic")
    static Topic topic;
    
    @Resource(lookup="serverTestQueue")
    static Queue serverQueue;
    
    
    JMSContext context;
    JMSConsumer consumer;
    JMSProducer producer;
    //==========================================================================
    
    private TextMessage createCategory(String categoryName, String superCategoryName) 
    {

        TextMessage textMessage = null;
        try {
            
            Kategorija category = new Kategorija();
            category.setNaziv(categoryName);
        
        List<Kategorija> categories = em.createNamedQuery("Kategorija.findByNaziv", Kategorija.class).
                setParameter("naziv", categoryName).
                getResultList();
        
        Kategorija categoryControlVar = (categories.isEmpty()? null : categories.get(0));
        
        List<Kategorija> superCategories = em.createNamedQuery("Kategorija.findByNaziv", Kategorija.class).
                setParameter("naziv", superCategoryName).
                getResultList();
        
        Kategorija superCategoryControlVar = (superCategories.isEmpty()? null : superCategories.get(0));
        
        
        String responseText = "";
        int returnStatus=0;
        
        if(categoryControlVar!=null) 
        {
            responseText = "Category is already in databse";
            returnStatus = -1;
        }
        else if (superCategoryControlVar==null && !superCategoryName.equals("x")) 
        {
            responseText = "Super category is not in databse";
            returnStatus = -1;
        }
        else 
        {
            
            if(!superCategoryName.equals("x")) 
            {
                category.setIdNadKategorija(superCategoryControlVar.getIdKategorija());
            }
            
            try {
                    em.getTransaction().begin();
                    em.persist(category);
                    em.getTransaction().commit();
            } catch (EntityExistsException e) {
            responseText = "City in specified country is already in databse";
            returnStatus = -1;
            }
            finally 
            {
                 if (em.getTransaction().isActive())
                        em.getTransaction().rollback();
            }
        }
            
        textMessage = context.createTextMessage(responseText);
        textMessage.setIntProperty("status", returnStatus);
            
        } catch (JMSException ex) {
            Logger.getLogger(Subsystem2.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return textMessage;
    }
    
    private ObjectMessage getCategories() {
        
        List<Kategorija> categories = em.createNamedQuery("Kategorija.findAll", Kategorija.class).getResultList();
        
        ArrayList<Kategorija> k = new ArrayList<>();
        
        for (Kategorija category : categories) 
            k.add(category);
        
        return context.createObjectMessage(k);
        
    }
    
    private void run() 
    {
        String msgSelector = "podsistem=2";
        
        context = connFactory.createContext();
        context.setClientID("2");
        consumer = context.createDurableConsumer(topic, "sub2", msgSelector, false);
        producer = context.createProducer();
        
        String categoryName, superCategoryName;
        
        while (true) 
        {
            try {
                System.out.println("Podsistem2: Cekanje zahteva...");
                
                
                TextMessage textMessage = (TextMessage) consumer.receive();
                byte request = textMessage.getByteProperty("request");

                Message response = null;
                
                switch(request) {
                
                    case CREATE_CATEGORY:
                        categoryName = textMessage.getStringProperty("categoryName");
                        superCategoryName = textMessage.getStringProperty("superCategoryName");
                        response = createCategory(categoryName, superCategoryName);
                        break;
                        
                    case CREATE_ARTICLE:
                      
                        break;
                    case MODIFY_ARTICLE_PRICE:
                       
                        break;
                    case ADD_ARTICLE_DISCOUNT:
                       
                        break;
                    case ADD_TO_CART:
                       
                        break;
                    case REMOVE_FROM_CART:
                       
                        break;
                    case ALL_CATEGORIES:
                       response = getCategories();
                        break;
                    case ALL_ARTICLES_USER_IS_SELLING:
                       
                        break;
                    case VIEW_CART:
                       
                        break;
                }
                
                producer.send(serverQueue, response);
                
            } catch (JMSException ex) {
                Logger.getLogger(Subsystem2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void main(String[] args) {
        
        Subsystem2 sub2 = new Subsystem2();
        sub2.run();
    }
    
}
