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
@Path("cart")
public class Cart {
    
    private static final byte ADD_TO_CART = 9;
    private static final byte REMOVE_FROM_CART = 10;
    private static final byte VIEW_CART = 16;
    
    
    @Resource(lookup = "myConnFactory")
    ConnectionFactory connectionFactory;
    
    @Resource(lookup = "ZOCOVTOPIC")
    Topic topic;
    
    @Resource(lookup = "KKP")
    Queue queue;
    
    @POST
    @Path("addToCart/{articleName}/{articleAmmount}/{username}")
    public Response addToCart(@PathParam("articleName") String articleName, 
            @PathParam("articleAmmount") String articleAmmount,
            @PathParam("username") String username) {
        
        try {
            
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            
            //create message
            
            TextMessage textMessage = context.createTextMessage("request");
            
            textMessage.setByteProperty("request", ADD_TO_CART);
            textMessage.setIntProperty("podsistem", 2);
            
            textMessage.setStringProperty("articleName", articleName);
            textMessage.setStringProperty("articleAmmount", articleAmmount);
            textMessage.setStringProperty("username", username);
            
            producer.send(topic, textMessage);
            
            //response
            
            Message message = consumer.receive();
            
            if (!(message instanceof TextMessage)){
                return Response.status(Response.Status.BAD_REQUEST).entity("Greska: Neodgovarajuci tip poruke!").build();
            }
            TextMessage recievedTextMessage = (TextMessage) message;
            String res = recievedTextMessage.getText();
            int ret = recievedTextMessage.getIntProperty("status");
            if (ret != 0) 
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            
        } catch (JMSException ex) {
            Logger.getLogger(City.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    return Response.status(OK).entity("Articlea added to cart!").build();
    }

    
    @POST
    @Path("removeFromCart/{articleName}/{articleAmmount}/{username}")
    public Response removeFromCart(@PathParam("articleName") String articleName, 
            @PathParam("articleAmmount") String articleAmmount,
            @PathParam("username") String username) {
        
        try {
            
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            
            //create message
            
            TextMessage textMessage = context.createTextMessage("request");
            
            textMessage.setByteProperty("request", REMOVE_FROM_CART);
            textMessage.setIntProperty("podsistem", 2);
            
            textMessage.setStringProperty("articleName", articleName);
            textMessage.setStringProperty("articleAmmount", articleAmmount);
            textMessage.setStringProperty("username", username);
            
            producer.send(topic, textMessage);
            
            //response
            
            Message message = consumer.receive();
            
            if (!(message instanceof TextMessage)){
                return Response.status(Response.Status.BAD_REQUEST).entity("Greska: Neodgovarajuci tip poruke!").build();
            }
            TextMessage recievedTextMessage = (TextMessage) message;
            String res = recievedTextMessage.getText();
            int ret = recievedTextMessage.getIntProperty("status");
            if (ret != 0) 
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            
        } catch (JMSException ex) {
            Logger.getLogger(City.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    return Response.status(OK).entity("Article removed from cart!").build();
    }
    
    @GET
    @Path("viewCart/{username}")
    public Response viewCart(@PathParam("username") String username) {
    
    ArrayList<String> cart = null;
        
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            
            // message
            TextMessage textMessage = context.createTextMessage("request");
            textMessage.setByteProperty("request", VIEW_CART);
            textMessage.setIntProperty("podsistem", 2);
            
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
    
}
