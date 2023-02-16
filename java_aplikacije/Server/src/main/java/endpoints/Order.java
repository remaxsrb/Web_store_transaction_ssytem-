/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package endpoints;

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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.OK;

/**
 *
 * @author remax
 */
@Path("order")
public class Order {
    
    private static final byte USER_ORDERS = 17;
    private static final byte ALL_ORDERS = 18;
    
    @Resource(lookup = "myConnFactory")
    ConnectionFactory connectionFactory;
    
    @Resource(lookup = "ZOCOVTOPIC")
    Topic topic;
    
    @Resource(lookup = "KKP")
    Queue queue;
    
    
    @GET
    @Path("viewOrders/{username}")
    public Response viewCart(@PathParam("username") String username) {
    
    ArrayList<String> cart = null;
        
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            
            // message
            TextMessage textMessage = context.createTextMessage("request");
            textMessage.setByteProperty("request", USER_ORDERS);
            textMessage.setIntProperty("podsistem", 3);
            
            textMessage.setStringProperty("username", username);
            
            producer.send(topic, textMessage);
            
            // response
            Message mess = consumer.receive();
            if (!(mess instanceof ObjectMessage)){
                return Response.status(Response.Status.BAD_REQUEST).entity("Greska: Neodgovarajuci tip poruke!").build();
            }
            ObjectMessage objMsg = (ObjectMessage) mess;
            cart = (ArrayList<String>) objMsg.getObject();
            
        } catch (JMSException ex) {
            Logger.getLogger(String.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassCastException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Greska: Neodgovarajuci tip objekta!").build();
        }
        
        return Response.status(OK).entity(new GenericEntity<List<String>>(cart){}).build();
    }
    
    
    @GET
    @Path("viewOrders/")
    public Response viewCart() {
    
    ArrayList<String> cart = null;
        
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            
            // message
            TextMessage textMessage = context.createTextMessage("request");
            textMessage.setByteProperty("request", ALL_ORDERS);
            textMessage.setIntProperty("podsistem", 3);
            
            
            
            producer.send(topic, textMessage);
            
            // response
            Message mess = consumer.receive();
            if (!(mess instanceof ObjectMessage)){
                return Response.status(Response.Status.BAD_REQUEST).entity("Greska: Neodgovarajuci tip poruke!").build();
            }
            ObjectMessage objMsg = (ObjectMessage) mess;
            cart = (ArrayList<String>) objMsg.getObject();
            
        } catch (JMSException ex) {
            Logger.getLogger(String.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassCastException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Greska: Neodgovarajuci tip objekta!").build();
        }
        
        return Response.status(OK).entity(new GenericEntity<List<String>>(cart){}).build();
    }
    
}
