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
        
        
        ArrayList<String> returnStrings = new ArrayList<>();
        
        List<Korisnik> users = em.createNamedQuery("Korisnik.findByKorisnickoIme", Korisnik.class).
                setParameter("korisnickoIme", username).
                getResultList();
        
        Korisnik userControlVar = (users.isEmpty()? null : users.get(0));
        
        if(userControlVar == null) 
        {
            returnStrings.add("Korisnik ne postoji u bazi!");
        }
        else 
        {
            List<Artikal> articles = em.createNamedQuery("Artikal.findByProdavac", Artikal.class).
                setParameter("korisnickoIme", username).
                getResultList();
        
        ArrayList<String> articlesString = new ArrayList<>();
        
        for (Artikal article : articles) 
            articlesString.add(article.getNaziv());
        
        returnStrings = articlesString;
        }
        
        
        return context.createObjectMessage(returnStrings);
        
    }
    
    private TextMessage addToCart(String articleName, int articleAmmount, String user) 
    {
        TextMessage textMessage = null;
        try {
            
        List<Artikal> articles = em.createNamedQuery("Artikal.findByNaziv", Artikal.class).
                setParameter("naziv", articleName).
                getResultList();
        
        Artikal article= (articles.isEmpty()? null : articles.get(0));
        
        
        List<Korisnik> users = em.createNamedQuery("Korisnik.findByKorisnickoIme", Korisnik.class).
                setParameter("korisnickoIme", user).
                getResultList();
        
        Korisnik userControlVar = (users.isEmpty()? null : users.get(0));
        
        
        String responseText = "";
        int returnStatus=0;
        
        if(article==null) 
        {
            responseText = "Article is not in database";
            returnStatus = -1;
        }
        else if (userControlVar==null) 
        {
            responseText = "User is not in database";
            returnStatus = -1;
        }
        else if (article.getProdavac().getKorisnickoIme().equals(user)) 
        {
            responseText = "User can not purchase an item from himself";
            returnStatus = -1;
        }
        else 
        {
            
            Korpa cart = em.createNamedQuery("Korpa.findByKorisnickoIme", Korpa.class).
                setParameter("korisnickoIme", userControlVar).
                getResultList().get(0);
            
            List<Sadrzi> articleInCartArr = em.createNamedQuery("Sadrzi.findByArtikalKorpa", Sadrzi.class).
                setParameter("idKorpa", cart.getIdKorpa()).
                setParameter("idArtikal", article.getIdArtikal()).
                getResultList();
            
            Sadrzi articleInCart = (articleInCartArr.isEmpty()? null : articleInCartArr.get(0));
            
            if(articleInCart==null) 
            {
                cart.setUkupnaCena(article.getCena() * articleAmmount);
            
                Sadrzi addArticleToCart = new Sadrzi(cart.getIdKorpa(),article.getIdArtikal());
                addArticleToCart.setKolicinaArtikla(articleAmmount);
            
                try {
                    em.getTransaction().begin();
                    em.persist(addArticleToCart);
                    em.getTransaction().commit();
                } catch (ConstraintViolationException e) { e.printStackTrace();
                }
                finally 
                {
                    if (em.getTransaction().isActive())
                            em.getTransaction().rollback();
                }
            }
            else 
            {
                int newAmmount = articleInCart.getKolicinaArtikla() + articleAmmount;
                articleInCart.setKolicinaArtikla(newAmmount);
                
                float newTotalPrice = cart.getUkupnaCena() + articleAmmount*article.getCena();
                
                cart.setUkupnaCena(newTotalPrice);
                
                try {
                    em.getTransaction().begin();
                    em.persist(articleInCart);
                    em.getTransaction().commit();
                } catch (ConstraintViolationException e) { e.printStackTrace();
                }
                finally 
                {
                    if (em.getTransaction().isActive())
                            em.getTransaction().rollback();
                }
                
            }
            
            try {
                    em.getTransaction().begin();
                    em.persist(cart);
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
    
    
    private TextMessage removeFromCart(String articleName, int articleAmmount, String user) 
    {
        TextMessage textMessage = null;
        try {
            
        List<Artikal> articles = em.createNamedQuery("Artikal.findByNaziv", Artikal.class).
                setParameter("naziv", articleName).
                getResultList();
        
        Artikal article= (articles.isEmpty()? null : articles.get(0));
        
        
        List<Korisnik> users = em.createNamedQuery("Korisnik.findByKorisnickoIme", Korisnik.class).
                setParameter("korisnickoIme", user).
                getResultList();
        
        Korisnik userControlVar = (users.isEmpty()? null : users.get(0));
        
        
        String responseText = "";
        int returnStatus=0;
        
        if(article==null) 
        {
            responseText = "Article is not in database";
            returnStatus = -1;
        }
        else if (userControlVar==null) 
        {
            responseText = "User is not in database";
            returnStatus = -1;
        }
        else 
        {
            
            Korpa cart = em.createNamedQuery("Korpa.findByKorisnickoIme", Korpa.class).
                setParameter("korisnickoIme", userControlVar).
                getResultList().get(0);
            
            List<Sadrzi> articleInCartArr = em.createNamedQuery("Sadrzi.findByArtikalKorpa", Sadrzi.class).
                setParameter("idKorpa", cart.getIdKorpa()).
                setParameter("idArtikal", article.getIdArtikal()).
                getResultList();
            
            Sadrzi articleInCart = (articleInCartArr.isEmpty()? null : articleInCartArr.get(0));
            
            if(articleInCart==null) 
            {
                responseText = "Article is not in cart!";
            }
            else 
            {
                
                if(articleInCart.getKolicinaArtikla() <= articleAmmount) 
                {
                    
                    float newTotalPrice = cart.getUkupnaCena() - articleInCart.getKolicinaArtikla()*article.getCena();
                    
                    cart.setUkupnaCena(newTotalPrice);
                    
                    try {
                    em.getTransaction().begin();
                    em.remove(articleInCart);
                    em.getTransaction().commit();
                    } catch (ConstraintViolationException e) { e.printStackTrace();
                    }
                    finally 
                    {
                        if (em.getTransaction().isActive())
                                em.getTransaction().rollback();
                    }
                }
                else 
                {
                    float newTotalPrice = cart.getUkupnaCena() - articleAmmount*article.getCena();
                    
                    cart.setUkupnaCena(newTotalPrice);
                    
                    int newArticleAmmount = articleInCart.getKolicinaArtikla() - articleAmmount;
                    
                    articleInCart.setKolicinaArtikla(newArticleAmmount);
                    
                    
                    try {
                    em.getTransaction().begin();
                    em.persist(articleInCart);
                    em.getTransaction().commit();
                    } catch (ConstraintViolationException e) { e.printStackTrace();
                    }
                    finally 
                    {
                        if (em.getTransaction().isActive())
                                em.getTransaction().rollback();
                    }
                    
                }
                
                try {
                    em.getTransaction().begin();
                    em.persist(cart);
                    em.getTransaction().commit();
                    } catch (ConstraintViolationException e) { e.printStackTrace();
                    }
                    finally 
                    {
                        if (em.getTransaction().isActive())
                                em.getTransaction().rollback();
                    }
                
            }
            
            
        }
            
        textMessage = context.createTextMessage(responseText);
        textMessage.setIntProperty("status", returnStatus);
            
        } catch (JMSException ex) {
            Logger.getLogger(Subsystem2.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return textMessage;
    }
    
    
    private ObjectMessage viewCart(String username) 
    {
        ArrayList<String> returnStrings = new ArrayList<>();
        
        List<Korisnik> users = em.createNamedQuery("Korisnik.findByKorisnickoIme", Korisnik.class).
                setParameter("korisnickoIme", username).
                getResultList();
        
        Korisnik userControlVar = (users.isEmpty()? null : users.get(0));
        
        if(userControlVar == null) 
        {
            returnStrings.add("User is not in database!");
        }
        else 
        {
            Korpa cart = em.createNamedQuery("Korpa.findByKorisnickoIme", Korpa.class).
                setParameter("korisnickoIme", userControlVar).
                getResultList().get(0);
        
            List<Sadrzi> articlesInCart = em.createNamedQuery("Sadrzi.findByIdKorpa", Sadrzi.class).
                setParameter("idKorpa", cart.getIdKorpa()).
                getResultList();
            
            if(articlesInCart.isEmpty()) 
            {
                returnStrings.add("User has nothing in cart!");
            }
            else 
            {
                for (Sadrzi s:articlesInCart) 
                    returnStrings.add(s.getArtikal().getNaziv() + "|" + s.getKolicinaArtikla());
            }
            
        }
        
        
        return context.createObjectMessage(returnStrings);
    }
    
    private void run() 
    {
        String msgSelector = "podsistem=2";
        
        context = connFactory.createContext();
        context.setClientID("2");
        consumer = context.createDurableConsumer(topic, "sub2", msgSelector, false);
        producer = context.createProducer();
        
        String categoryName, superCategoryName, articleName, articlePrice, 
                articleDescription, articleCategory, articleDiscount, 
                articleOwner, user, articleAmmount; 
        
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
                        articleName = textMessage.getStringProperty("articleName");
                        articleAmmount = textMessage.getStringProperty("articleAmmount");
                        user = textMessage.getStringProperty("username");
                        
                        response = addToCart(articleName,Integer.parseInt(articleAmmount),user);
                        
                        break;
                    case REMOVE_FROM_CART:
                        
                       articleName = textMessage.getStringProperty("articleName");
                        articleAmmount = textMessage.getStringProperty("articleAmmount");
                        user = textMessage.getStringProperty("username");
                        
                        response = removeFromCart(articleName,Integer.parseInt(articleAmmount),user);
                        break;
                    case ALL_CATEGORIES:
                       response = getCategories();
                        break;
                    case ALL_ARTICLES_USER_IS_SELLING:
                        articleOwner = textMessage.getStringProperty("username");
                        response = getArticlesForOwner(articleOwner);
                        break;
                    case VIEW_CART:
                        user = textMessage.getStringProperty("username");
                        response = viewCart(user);
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
