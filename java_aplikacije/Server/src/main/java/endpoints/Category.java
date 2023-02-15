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
@Path("categories")
public class Category {
    
    private static final byte CREATE_CATEGORY = 5;
    private static final byte ALL_CATEGORIES = 14;

    
    @Resource(lookup = "myConnFactory")
    ConnectionFactory connectionFactory;
    
    @Resource(lookup = "ZOCOVTOPIC")
    Topic topic;
    
    @Resource(lookup = "KKP")
    Queue queue;
    
    @POST
    @Path("createCategory/{categoryName}/{superCategoryName}")
    public Response createCategory(@PathParam("categoryName") String categoryName, @PathParam("superCategoryName") String superCategoryName ) {
        
        try {
            
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            
            //create message
            
            TextMessage textMessage = context.createTextMessage("request");
            
            textMessage.setByteProperty("request", CREATE_CATEGORY);
            textMessage.setIntProperty("podsistem", 2);
            
            textMessage.setStringProperty("categoryName", categoryName);
            textMessage.setStringProperty("superCategoryName", superCategoryName);
            
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
    
    return Response.status(OK).entity("Category successfuly created!").build();
    }

    
    @GET
    @Path("getCategories")
    public Response getCategories() {
    
    ArrayList<String> categories = null;
        
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            
            // message
            TextMessage msg = context.createTextMessage("request");
            msg.setByteProperty("request", ALL_CATEGORIES);
            msg.setIntProperty("podsistem", 2);
            
            producer.send(topic, msg);
            
            // response
            Message mess = consumer.receive();
            if (!(mess instanceof ObjectMessage)){
                return Response.status(Response.Status.BAD_REQUEST).entity("Greska: Neodgovarajuci tip poruke!").build();
            }
            ObjectMessage objMsg = (ObjectMessage) mess;
            categories = (ArrayList<String>) objMsg.getObject();
            
        } catch (JMSException ex) {
            Logger.getLogger(String.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassCastException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Greska: Neodgovarajuci tip objekta!").build();
        }
        
        return Response.status(OK).entity(new GenericEntity<List<String>>(categories){}).build();
    }
    
}
