/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package subsystem3;

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
public class Subsystem3 {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("Subsystem1PU");
    static EntityManager em = emf.createEntityManager();
    
    @Resource(lookup = "myConnFactory")
    static ConnectionFactory connFactory;
    
    @Resource(lookup="serverTopic")
    static Topic topic;
    
    @Resource(lookup="myQueue")
    static Queue serverQueue;
    
    //kreirati po jednog proizvodjaca i jednog potrosaca za svaku vezu sa drugim podsistemom
    
    
    @Resource(lookup="subsystem3_subsystem1_queue")
    static Queue subsystem3_subsystem1_queue; // producer
    JMSProducer subsystem3_subsystem1_producer;
    
    @Resource(lookup="subsystem1_subsystem3_queue")
    static Queue subsystem1_subsystem3_queue; // consumer
    JMSConsumer subsystem1_subsystem3_consumer;
    
    @Resource(lookup="subsystem3_subsystem2_queue")
    static Queue subsystem3_subsystem2_queue; // producer
    JMSProducer subsystem3_subsystem2_producer;
    
    @Resource(lookup="subsystem2_subsystem3_queue")
    static Queue subsystem2_subsystem3_queue; // consumer
    JMSConsumer subsystem2_subsystem3_consumer;
    
    JMSContext context;
    JMSConsumer consumer;
    JMSProducer producer;
    //==========================================================================
    
    private void serverListener(Message msg) {}
    
    private void run () 
    {
        String msgSelector = "podsistem=3";
        context = connFactory.createContext();
        context.setClientID("3");
        
        
        consumer = context.createDurableConsumer(topic, "sub1", msgSelector, false);
        consumer.setMessageListener((Message msg) -> { serverListener(msg); });
        producer = context.createProducer();
        
        subsystem3_subsystem1_producer = context.createProducer();
        subsystem1_subsystem3_consumer = context.createConsumer(subsystem1_subsystem3_queue);
        
        subsystem3_subsystem2_producer = context.createProducer();
        subsystem2_subsystem3_consumer = context.createConsumer(subsystem2_subsystem3_queue);
        

        
    }
    
    public static void main(String[] args) {
        Subsystem3 sub3 = new Subsystem3();
        sub3.run();
    }
    
}
