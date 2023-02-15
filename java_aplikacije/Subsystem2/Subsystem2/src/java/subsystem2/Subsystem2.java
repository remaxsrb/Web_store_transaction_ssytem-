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
    
    @Resource(lookup="ZOCOVTOPIC")
    static Topic topic;
    
    @Resource(lookup="KKP")
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
        
        ArrayList<String> categoriesString = new ArrayList<>();
        
        for (Kategorija category : categories) 
            categoriesString.add(category.getNaziv());
        
        return context.createObjectMessage(categoriesString);
        
    }
    
    private TextMessage createArticle(String articleName, float articlePrice, String articleDescription, String articleCategory, String articleOwner) 
    {
        TextMessage textMessage = null;
        try {
            
            Artikal article = new Artikal();
            article.setNaziv(articleName);
            article.setCena(articlePrice);
           
        
        List<Artikal> articles = em.createNamedQuery("Artikal.findByNaziv", Artikal.class).
                setParameter("naziv", articleName).
                getResultList();
        
        Artikal articleControlVar = (articles.isEmpty()? null : articles.get(0));
        
        List<Kategorija> categories = em.createNamedQuery("Kategorija.findByNaziv", Kategorija.class).
                setParameter("naziv", articleCategory).
                getResultList();
        
        Kategorija categoryControlVar = (categories.isEmpty()? null : categories.get(0));
        
        List<Korisnik> users = em.createNamedQuery("Korisnik.findByKorisnickoIme", Korisnik.class).
                setParameter("korisnickoIme", articleOwner).
                getResultList();
        
        Korisnik userControlVar = (users.isEmpty()? null : users.get(0));
        
        String responseText = "";
        int returnStatus=0;
        
        if(articleControlVar!=null) 
        {
            responseText = "Article is already in databse";
            returnStatus = -1;
        }
        else if (categoryControlVar==null) 
        {
            responseText = "Category is not in databse";
            returnStatus = -1;
        }
        else if (userControlVar == null) 
        {
            responseText = "Owner is not in databse";
            returnStatus = -1;
        }
        else 
        {
            
            if(!articleDescription.equals("x")) 
            {
                article.setOpis(articleDescription);
            }
            article.setKategorija(categoryControlVar);
            article.setProdavac(userControlVar);
            try {
                    em.getTransaction().begin();
                    em.persist(article);
                    em.getTransaction().commit();
            } catch (EntityExistsException e) {
            responseText = "Article is already in database";
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
    
    private TextMessage setArticleDiscount(String articleName, int discount) 
    {
        TextMessage textMessage = null;
        try {
            
        List<Artikal> articles = em.createNamedQuery("Artikal.findByNaziv", Artikal.class).
                setParameter("naziv", articleName).
                getResultList();
        
        Artikal article = (articles.isEmpty()? null : articles.get(0));
        
        String responseText = "";
        int returnStatus=0;
        
        if(article==null) 
        {
            responseText = "Article is not in database";
            returnStatus = -1;
        }
       
        else 
        {
            
            article.setPopust(discount);
            try {
                    em.getTransaction().begin();
                    em.persist(article);
                    em.getTransaction().commit();
            } catch (ConstraintViolationException e) { e.printStackTrace();
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
    
    private TextMessage setArticlePrice(String articleName, float newPrice) 
    {
        TextMessage textMessage = null;
        try {
            
        List<Artikal> articles = em.createNamedQuery("Artikal.findByNaziv", Artikal.class).
                setParameter("naziv", articleName).
                getResultList();
        
        Artikal article = (articles.isEmpty()? null : articles.get(0));
        
        String responseText = "";
        int returnStatus=0;
        
        if(article==null) 
        {
            responseText = "Article is not in database";
            returnStatus = -1;
        }
       
        else 
        {
            
            article.setCena(newPrice);
            try {
                    em.getTransaction().begin();
                    em.persist(article);
                    em.getTransaction().commit();
            } catch (ConstraintViolationException e) { e.printStackTrace();
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
    
    private ObjectMessage getArticlesForOwner(String username) {
        
        List<Artikal> articles = em.createNamedQuery("Artikal.findByProdavac", Artikal.class).
                setParameter("korisnickoIme", username).
                getResultList();
        
        ArrayList<String> articlesString = new ArrayList<>();
        
        for (Artikal article : articles) 
            articlesString.add(article.getNaziv());
        
        return context.createObjectMessage(articlesString);
        
    }
    
    private void run() 
    {
        String msgSelector = "podsistem=2";
        
        context = connFactory.createContext();
        context.setClientID("2");
        consumer = context.createDurableConsumer(topic, "sub2", msgSelector, false);
        producer = context.createProducer();
        
        String categoryName, superCategoryName, articleName, articlePrice, 
                articleDescription, articleCategory, articleDiscount, articleOwner; 
        
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
                        articleName = textMessage.getStringProperty("articleName");
                        articlePrice = textMessage.getStringProperty("articlePrice");
                        articleDescription = textMessage.getStringProperty("articleDescription");
                        articleCategory = textMessage.getStringProperty("articleCategory");
                        articleOwner = textMessage.getStringProperty("owner");
                        response = createArticle(articleName, Float.parseFloat(articlePrice), articleDescription, articleCategory, articleOwner);
                        
                        break;
                    case MODIFY_ARTICLE_PRICE:
                        
                        articleName = textMessage.getStringProperty("articleName");
                        articlePrice = textMessage.getStringProperty("newPrice");
                        
                        response = setArticlePrice(articleName, Float.parseFloat(articlePrice));
                        
                        break;
                    case ADD_ARTICLE_DISCOUNT:
                        articleName = textMessage.getStringProperty("articleName");
                        articleDiscount = textMessage.getStringProperty("discount");
                        
                        response = setArticleDiscount(articleName, Integer.parseInt(articleDiscount));
                        
                        break;
                    case ADD_TO_CART:
                       
                        break;
                    case REMOVE_FROM_CART:
                       
                        break;
                    case ALL_CATEGORIES:
                       response = getCategories();
                        break;
                    case ALL_ARTICLES_USER_IS_SELLING:
                        articleOwner = textMessage.getStringProperty("username");
                        response = getArticlesForOwner(articleOwner);
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
