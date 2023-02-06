/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package subsystem1;

import entities.*;
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
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 *
 * @author remax
 */
public class Subsystem1 {
    
    private static final byte CREATE_CITY = 1;
    
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("Subsystem1PU");
    static EntityManager em = emf.createEntityManager();
    
    @Resource(lookup = "myConnFactory")
    static ConnectionFactory connFactory;
    
    @Resource(lookup="serverTopic")
    static Topic topic;
    
    @Resource(lookup="myQueue")
    static Queue serverQueue;
    
    //kreirati po jednog proizvodjaca i jednog potrosaca za svaku vezu sa drugim podsistemom
    
    @Resource(lookup="subsystem1_subsystem2_queue")
    static Queue subsystem1_subsystem2_queue; // producer
    JMSProducer subsystem2producer;
    
    @Resource(lookup="subsystem2_subsystem1_queue")
    static Queue subsystem2_subsystem1_queue; // consumer
    JMSConsumer subsystem2consumer;
    
    @Resource(lookup="subsystem1_subsystem3_queue")
    static Queue subsystem1_subsystem3_queue; // producer
    JMSProducer subsystem3sroducer;
    
    @Resource(lookup="subsystem3_subsystem1_queue")
    static Queue subsystem3_subsystem1_queue; // consumer
    JMSConsumer subsystem3consumer;
    
    JMSContext context;
    JMSConsumer consumer;
    JMSProducer producer;
    
    private TextMessage createCity(String cityName, String cityCountry) 
    {
        TextMessage textMessage = null;
        
        try {
            
            Grad city = new Grad();
            city.setNaziv(cityName);
            city.setDrzava(cityCountry);
        
        List<Grad> cities = em.createNamedQuery("Grad.findByGradDrzava", Grad.class).
                setParameter("grad", cityName).
                setParameter("drzava", cityCountry).getResultList();
        
        Grad controlVar = (cities.isEmpty()? null : cities.get(0));
        
        String responseText = "";
        int returnStatus=0;
        
        if(controlVar!=null) 
        {
            responseText = "City in specified country is already in databse";
            returnStatus = -1;
        }
        else 
        {
            try {
                    em.getTransaction().begin();
                    em.persist(city);
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
            Logger.getLogger(Subsystem1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return textMessage;
    }
    
    private void run() 
    {
        String msgSelector = "podsistem=1";
        
        context = connFactory.createContext();
        context.setClientID("1");
        consumer = context.createDurableConsumer(topic, "sub1", msgSelector, false);
        producer = context.createProducer();
        
        String cityName, cityCountry;
        
        while (true) 
        {
            try {
                System.out.println("Podsistem1: Cekanje zahteva...");
                
                TextMessage textMessage = (TextMessage) consumer.receive();
                byte request = textMessage.getByteProperty("request");
                
                Message response = null;
                
                switch(request) {
                
                    case CREATE_CITY:
                        cityName = textMessage.getStringProperty("cityName");
                        cityCountry = textMessage.getStringProperty("cityCountry");
                        response = createCity(cityName, cityCountry);
                        break;
                }
                
                producer.send(serverQueue, response);
                
            } catch (JMSException ex) {
                Logger.getLogger(Subsystem1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void main(String[] args) {
        
        Subsystem1 p1 = new Subsystem1();
        p1.run();
    }
    
}
