/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package subsystem3;

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
public class Subsystem3 {

    private static final byte CREATE_CITY = 1;
    private static final byte CREATE_USER = 2;
    private static final byte WIRE_MONEY_TO_USER = 3;
    private static final byte CHANGE_USER_ADDRESS = 4;
    
    private static final byte CREATE_ARTICLE = 6;
    private static final byte MODIFY_ARTICLE_PRICE = 7;
    private static final byte ADD_ARTICLE_DISCOUNT = 8;
    private static final byte ADD_TO_CART = 9;
    private static final byte REMOVE_FROM_CART = 10;
    
    private static final byte PAYMENT = 11;
    private static final byte USER_ORDERS = 17;
    private static final byte ALL_ORDERS = 18;
    private static final byte ALL_TRANSACTIONS = 19;
    
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("Subsystem3PU");
    static EntityManager em = emf.createEntityManager();
    
    @Resource(lookup = "myConnFactory")
    static ConnectionFactory connFactory;
    
    @Resource(lookup="ZOCOVTOPIC")
    static Topic topic;
    
    @Resource(lookup="KKP")
    static Queue serverQueue;
    
    
    @Resource(lookup="subsystem1_subsystem3_queue")
    static Queue subsystem1_subsystem3_queue; // consumer
    JMSConsumer subsystem1_consumer;
    
    @Resource(lookup="subsystem3_subsystem1_queue")
    static Queue subsystem3_subsystem1_queue; // producer
    JMSProducer subsystem1_producer;
    
    @Resource(lookup="subsystem2_subsystem3_queue")
    static Queue subsystem2_subsystem3_queue; // consumer
    JMSConsumer subsystem2_consumer;
    
    @Resource(lookup="subsystem3_subsystem2_queue")
    static Queue subsystem3_subsystem2_queue; // producer
    JMSProducer subsystem2_producer;
    
    
    JMSContext context;
    JMSConsumer consumer;
    JMSProducer producer;
    //==========================================================================
    
    private ObjectMessage getUserOrders(String username) 
    {
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
            List<Narudzbina> orders = em.createNamedQuery("Narudzbina.findByKorisnickoIme", Narudzbina.class).
                setParameter("korisnickoIme", username).
                getResultList();
        
        ArrayList<String> articlesString = new ArrayList<>();
        
        for (Narudzbina order : orders) 
        {
//            List<Stavka> items = em.createNamedQuery("Stavka.findByNarudzbina", Stavka.class).
//                setParameter("narudzbina", order).
//                getResultList();
//            
//            String itemsString = "[";
//            
//            for (Stavka item: items) 
//            {
//                String str = item.getIdArtikal().getNaziv() + "| " + item.getKolicina() + ",";
//                itemsString
//            }
            
            articlesString.add(order.getKorisnik().getKorisnickoIme() + "|" + 
                    order.getUkupnaCena() + "|" + order.getVremeKreiranja().toString());
        }
            
        
        returnStrings = articlesString;
        }
        
        
        return context.createObjectMessage(returnStrings);
    }
    
    private ObjectMessage getAllOrders() 
    {
        ArrayList<String> returnStrings = new ArrayList<>();
        
        
            List<Narudzbina> orders = em.createNamedQuery("Narudzbina.findAll", Narudzbina.class).
                getResultList();
        
        ArrayList<String> articlesString = new ArrayList<>();
        
        for (Narudzbina order : orders) 
        {
//            List<Stavka> items = em.createNamedQuery("Stavka.findByNarudzbina", Stavka.class).
//                setParameter("narudzbina", order).
//                getResultList();
//            
//            String itemsString = "[";
//            
//            for (Stavka item: items) 
//            {
//                String str = item.getIdArtikal().getNaziv() + "| " + item.getKolicina() + ",";
//                itemsString
//            }
            
            articlesString.add(order.getKorisnik().getKorisnickoIme() + "|" + 
                    order.getUkupnaCena() + "|" + order.getVremeKreiranja().toString());
        }
            
        
        returnStrings = articlesString;
        
        return context.createObjectMessage(returnStrings);
    }
    
    
    private void createUser(String username, String cityName, String cityCountry, String streetName, int streetNumber) 
    {
        Korisnik user = new Korisnik();
        user.setKorisnickoIme(username);
        
        Adresa userAddress = em.createNamedQuery("Adresa.findByUlicaBroj", Adresa.class).
                setParameter("ulica", streetName).
                setParameter("broj", streetNumber).
                getResultList().get(0);
        
        Grad userCity = em.createNamedQuery("Grad.findByNazivDrzava", Grad.class).
                setParameter("naziv", cityName).
                setParameter("drzava", cityCountry).
                getResultList().get(0);
        
        user.setIdAdresa(userAddress);
        user.setIdGrad(userCity);
        
        try {
                em.getTransaction().begin();
                em.persist(user);
                em.getTransaction().commit();
            } catch (ConstraintViolationException e) {e.printStackTrace();}
            finally 
            {
                if (em.getTransaction().isActive())
                   em.getTransaction().rollback();
            }
        
        Korpa cart = new Korpa();
        
        Korisnik mostRecentlyAddedUser = em.createNamedQuery("Korisnik.findByKorisnickoIme", Korisnik.class).
                setParameter("korisnickoIme", username).
                getResultList().get(0);
        
        cart.setKorisnik(mostRecentlyAddedUser);
        cart.setUkupnaCena(0);
        
        try {
                    em.getTransaction().begin();
                    em.persist(cart);
                    em.getTransaction().commit();
            } catch (ConstraintViolationException e) {e.printStackTrace();}
            finally 
            {
                 if (em.getTransaction().isActive())
                        em.getTransaction().rollback();
            }
        
            
    }
    
    private void createCity(String name, String country) 
    {
        Grad city = new Grad();
        city.setNaziv(name);
        city.setDrzava(country);
        
        try {
                    em.getTransaction().begin();
                    em.persist(city);
                    em.getTransaction().commit();
            } catch (EntityExistsException e) { e.printStackTrace();
            }
            finally 
            {
                 if (em.getTransaction().isActive())
                        em.getTransaction().rollback();
            }
        
    }
    
    private void changeAddress(String username, String street, int streetNumber) 
    {
        Korisnik user = em.createNamedQuery("Korisnik.findByKorisnickoIme", Korisnik.class).
                setParameter("korisnickoIme", username).
                getResultList().get(0);
        
        Adresa userAddress = em.createNamedQuery("Adresa.findByUlicaBroj", Adresa.class).
                setParameter("ulica", street).
                setParameter("broj", streetNumber).
                getResultList().get(0);
        
        user.setIdAdresa(userAddress);
        
        try {
                    em.getTransaction().begin();
                    em.persist(user);
                    em.getTransaction().commit();
            } catch (EntityExistsException e) { e.printStackTrace();
            }
            finally 
            {
                 if (em.getTransaction().isActive())
                        em.getTransaction().rollback();
            }
        
        
        
    }
    
    private void subsystem1Listener(Message msg) 
    {
        String username, cityName, cityCountry, streetName;
        
        int streetNumber;
        
        try {
            switch(msg.getIntProperty("request"))
            {
                case CREATE_USER: 
                    
                    username = msg.getStringProperty("username");
                    cityName = msg.getStringProperty("cityName");
                    cityCountry = msg.getStringProperty("cityCountry");
                    streetName = msg.getStringProperty("streetName");
                    streetNumber = Integer.parseInt(msg.getStringProperty("streetNumber"));
                    
                    createUser(username, cityName, cityCountry, streetName, streetNumber);
                   
                    break;
                case CREATE_CITY:
                    cityName = msg.getStringProperty("cityName");
                    cityCountry = msg.getStringProperty("cityCountry");
                    createCity(cityName, cityCountry);
                    break;
                case CHANGE_USER_ADDRESS:
                    username = msg.getStringProperty("username");
                    streetName = msg.getStringProperty("streetName");
                    streetNumber = Integer.parseInt(msg.getStringProperty("streetNumber"));
                    changeAddress(username, streetName, streetNumber);
                    break;
                
            }
        } catch (JMSException ex) {
            Logger.getLogger(Subsystem3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void createArticle(String articleName, String description, float price) 
    {
        Artikal article = new Artikal();
        
        article.setCena(price);
        article.setNaziv(articleName);
        article.setOpis(description);
        article.setPopust(0);
        
        try {
                    em.getTransaction().begin();
                    em.persist(article);
                    em.getTransaction().commit();
            } catch (EntityExistsException e) { e.printStackTrace();
            }
            finally 
            {
                 if (em.getTransaction().isActive())
                        em.getTransaction().rollback();
            }
        
    }
    
    private void updatePrice(String articleName, float price) 
    {
        Artikal article = em.createNamedQuery("Artikal.findByNaziv", Artikal.class).
                setParameter("naziv", articleName).
                getResultList().get(0);
        
        article.setCena(price);
        
        try {
                em.getTransaction().begin();
                em.persist(article);
                em.getTransaction().commit();
            } catch (EntityExistsException e) { e.printStackTrace();
            }
            finally 
            {
                if (em.getTransaction().isActive())
                        em.getTransaction().rollback();
            }
        
    }
    
    private void setDiscount(String articleName, int discount) 
    {
        Artikal article = em.createNamedQuery("Artikal.findByNaziv", Artikal.class).
                setParameter("naziv", articleName).
                getResultList().get(0);
        
        article.setPopust(discount);
        
        try {
                em.getTransaction().begin();
                em.persist(article);
                em.getTransaction().commit();
            } catch (EntityExistsException e) { e.printStackTrace();
            }
            finally 
            {
                if (em.getTransaction().isActive())
                        em.getTransaction().rollback();
            }
    }
    
    private void addToCart(String articleName, float totalPrice ,int ammount, String username) 
    {
        Korisnik user = em.createNamedQuery("Korisnik.findByKorisnickoIme", Korisnik.class).
                setParameter("korisnickoIme", username).
                getResultList().get(0);
        
        Korpa cart = em.createNamedQuery("Korpa.findByKorisnickoIme", Korpa.class).
                setParameter("korisnickoIme", user).
                getResultList().get(0);
        
        Artikal article = em.createNamedQuery("Artikal.findByNaziv", Artikal.class).
                setParameter("naziv", articleName).
                getResultList().get(0);
        
        Sadrzi articleInCart = em.createNamedQuery("Sadrzi.findByArtikalKorpa", Sadrzi.class).
                setParameter("idKorpa", cart.getIdKorpa()).
                setParameter("idArtikal", article.getIdArtikal()).
                getResultList().get(0);
        
        cart.setUkupnaCena(totalPrice);
        
        try {
                em.getTransaction().begin();
                em.persist(cart);
                em.getTransaction().commit();
                } catch (ConstraintViolationException e) { e.printStackTrace();}
                finally 
                {
                    if (em.getTransaction().isActive())
                            em.getTransaction().rollback();
                }
        
        articleInCart.setKolicinaArtikla(ammount);
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
    
    private void removeFromCart(String articleName, float totalPrice ,int ammount, String username) 
    {
        
        Korisnik user = em.createNamedQuery("Korisnik.findByKorisnickoIme", Korisnik.class).
                setParameter("korisnickoIme", username).
                getResultList().get(0);
        
        Korpa cart = em.createNamedQuery("Korpa.findByKorisnickoIme", Korpa.class).
                setParameter("korisnickoIme", user).
                getResultList().get(0);
        
        Artikal article = em.createNamedQuery("Artikal.findByNaziv", Artikal.class).
                setParameter("naziv", articleName).
                getResultList().get(0);
        
        Sadrzi articleInCart = em.createNamedQuery("Sadrzi.findByArtikalKorpa", Sadrzi.class).
                setParameter("idKorpa", cart.getIdKorpa()).
                setParameter("idArtikal", article.getIdArtikal()).
                getResultList().get(0);
        
        
        cart.setUkupnaCena(totalPrice);
        
        if(ammount == 0) 
        {
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
            articleInCart.setKolicinaArtikla(ammount);
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
    
    private void subsystem2Listener(Message msg) 
    {
       String articleName, articleDescription, username;
       
       int discount, ammount;
       
       float price;
        
        try {
            switch(msg.getIntProperty("request"))
            {
                case CREATE_ARTICLE: 
                    
                    articleName = msg.getStringProperty("articleName");
                    articleDescription = msg.getStringProperty("articleDescription");
                    price= msg.getFloatProperty("articlePrice");
                    
                    createArticle(articleName, articleDescription, price);
                    
                    break;
                case MODIFY_ARTICLE_PRICE: 
                    articleName = msg.getStringProperty("articleName");
                    price= msg.getFloatProperty("articlePrice");
                    updatePrice(articleName, price);
                    break;
                case ADD_ARTICLE_DISCOUNT: 
                    
                    articleName = msg.getStringProperty("articleName");
                    discount= msg.getIntProperty("discount");
                    
                    setDiscount(articleName, discount);
                    break;
                case ADD_TO_CART:
                    articleName = msg.getStringProperty("articleName");
                    username = msg.getStringProperty("username");
                    ammount = msg.getIntProperty("totalPrice");
                    price = msg.getFloatProperty("price");
                    addToCart(articleName, price, ammount, username);
                    break;
                case REMOVE_FROM_CART:
                    articleName = msg.getStringProperty("articleName");
                    username = msg.getStringProperty("username");
                    ammount = msg.getIntProperty("totalPrice");
                    price = msg.getFloatProperty("price");
                    removeFromCart(articleName, price, ammount, username);
                    break;
            }
        } catch (JMSException ex) {
            Logger.getLogger(Subsystem3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void run () 
    {
        String msgSelector = "podsistem=3";
        
        context = connFactory.createContext();
        context.setClientID("3");
        consumer = context.createDurableConsumer(topic, "sub3", msgSelector, false);
        producer = context.createProducer();
        
        subsystem1_producer = context.createProducer();
        subsystem1_consumer = context.createConsumer(subsystem1_subsystem3_queue);
        subsystem1_consumer.setMessageListener((Message msg) -> { subsystem1Listener(msg); });
        
        subsystem2_producer = context.createProducer();
        subsystem2_consumer = context.createConsumer(subsystem2_subsystem3_queue);
        subsystem2_consumer.setMessageListener((Message msg) -> { subsystem2Listener(msg); });
        
        String username;

        while (true) 
        {
            try {
                System.out.println("Podsistem3: Cekanje zahteva...");
                
                
                TextMessage textMessage = (TextMessage) consumer.receive();
                byte request = textMessage.getByteProperty("request");

                Message response = null;
                
                switch(request) {
                
                    case PAYMENT:
                        
                        break;
                        
                    case USER_ORDERS:
                        
                       username = textMessage.getStringProperty("username");
                       response = getUserOrders(username);
                        
                        break;
                    case ALL_ORDERS:
                        
                       response = getAllOrders();
                        
                        break;
                    case ALL_TRANSACTIONS:
                        
                        break;
                }
                
                producer.send(serverQueue, response);
                
            } catch (JMSException ex) {
                Logger.getLogger(Subsystem3.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public static void main(String[] args) {
        Subsystem3 sub3 = new Subsystem3();
        sub3.run();
    }
    
}
