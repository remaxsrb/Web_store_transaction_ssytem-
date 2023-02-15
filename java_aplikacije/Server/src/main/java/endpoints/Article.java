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
@Path("articles")
public class Article {
    
    private static final byte CREATE_ARTICLE = 6;
    private static final byte MODIFY_ARTICLE_PRICE = 7;
    private static final byte ADD_ARTICLE_DISCOUNT = 8;
    private static final byte ALL_ARTICLES_USER_IS_SELLING = 15;
    
    @Resource(lookup = "myConnFactory")
    ConnectionFactory connectionFactory;
    
    @Resource(lookup = "ZOCOVTOPIC")
    Topic topic;
    
    @Resource(lookup = "KKP")
    Queue queue;
    
    @POST
    @Path("createArticle/{articleName}/{articlePrice}/{articleDescription}/{articleCategory}/{owner}")
    public Response createArticle(@PathParam("articleName") String articleName, 
            @PathParam("articlePrice") String articlePrice, 
            @PathParam("articleDescription") String articleDescription, 
            @PathParam("articleCategory") String articleCategory,
            @PathParam("owner") String owner) {
        
        try {
            
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            
            //create message
            
            TextMessage textMessage = context.createTextMessage("request");
            
            textMessage.setByteProperty("request", CREATE_ARTICLE);
            textMessage.setIntProperty("podsistem", 2);
            
            textMessage.setStringProperty("articleName", articleName);
            textMessage.setStringProperty("articlePrice", articlePrice);
            textMessage.setStringProperty("articleDescription", articleDescription);
            textMessage.setStringProperty("articleCategory", articleCategory);
            textMessage.setStringProperty("owner", owner);
            
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
    
    return Response.status(OK).entity("Article successfuly created!").build();
    }

    
    @POST
    @Path("changeArticlePrice/{articleName}/{newPrice}")
    public Response changeArticlePrice(@PathParam("articleName") String articleName, 
            @PathParam("newPrice") String newPrice) {
        
        try {
            
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            
            //create message
            
            TextMessage textMessage = context.createTextMessage("request");
            
            textMessage.setByteProperty("request", MODIFY_ARTICLE_PRICE);
            textMessage.setIntProperty("podsistem", 2);
            
            textMessage.setStringProperty("articleName", articleName);
            textMessage.setStringProperty("newPrice", newPrice);
            
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
    
    return Response.status(OK).entity("Article price successfuly changed!").build();
    }
    
    @POST
    @Path("setArticleDiscount/{articleName}/{discount}")
    public Response setArticleDiscount(@PathParam("articleName") String articleName, 
            @PathParam("discount") String discount) {
        
        try {
            
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            
            //create message
            
            TextMessage textMessage = context.createTextMessage("request");
            
            textMessage.setByteProperty("request", ADD_ARTICLE_DISCOUNT);
            textMessage.setIntProperty("podsistem", 2);
            
            textMessage.setStringProperty("articleName", articleName);
            textMessage.setStringProperty("discount", discount);
            
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
    
    return Response.status(OK).entity("Article discount successfuly set!").build();
    }
    
    @GET
    @Path("getUserArticles/{username}")
    public Response getUserArticles(@PathParam("username") String username) {
    
    ArrayList<String> articles = null;
        
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            
            // message
            TextMessage textMessage = context.createTextMessage("request");
            textMessage.setByteProperty("request", ALL_ARTICLES_USER_IS_SELLING);
            textMessage.setIntProperty("podsistem", 2);
            
            textMessage.setStringProperty("username", username);
            
            producer.send(topic, textMessage);
            
            // response
            Message mess = consumer.receive();
            if (!(mess instanceof ObjectMessage)){
                return Response.status(Response.Status.BAD_REQUEST).entity("Greska: Neodgovarajuci tip poruke!").build();
            }
            ObjectMessage objMsg = (ObjectMessage) mess;
            articles = (ArrayList<String>) objMsg.getObject();
            
        } catch (JMSException ex) {
            Logger.getLogger(String.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassCastException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Greska: Neodgovarajuci tip objekta!").build();
        }
        
        return Response.status(OK).entity(new GenericEntity<List<String>>(articles){}).build();
    }
}
