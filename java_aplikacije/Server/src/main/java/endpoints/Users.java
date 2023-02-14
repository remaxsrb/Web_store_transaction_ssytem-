/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package endpoints;

import entities.Grad;
import entities.Korisnik;
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
import javax.ws.rs.FormParam;
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
@Path("users")
public class Users {
    
    private static final byte CREATE_USER = 2;
    private static final byte WIRE_MONEY_TO_USER = 3;
    private static final byte CHANGE_USER_ADDRESS = 4;
    private static final byte ALL_USERS = 13;
    
    @Resource(lookup = "myConnFactory")
    ConnectionFactory connectionFactory;
    
    @Resource(lookup = "serverTestTopic")
    Topic topic;
    
    @Resource(lookup = "myTestQueue")
    Queue queue;
    
    @POST
    @Path("createUser/{userName}/{firstName}/{lastName}/{password}/{street}/{streetNumber}/{cityName}/{cityCountry}")
    public Response createUser(@PathParam("userName") String userName,@PathParam("firstName") 
            String firstName, @PathParam("lastName") String lastName, @PathParam("password") String password, 
            @PathParam("street") String street, @PathParam("streetNumber") String streetNumber,
            @PathParam("cityName") String cityName, @PathParam("cityCountry") String cityCountry) {
        
        try {
            
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            
            //create message
            
            TextMessage textMessage = context.createTextMessage("request");
            
            textMessage.setByteProperty("request", CREATE_USER);
            textMessage.setIntProperty("podsistem", 1);
            
            textMessage.setStringProperty("userName", userName);
            textMessage.setStringProperty("firstName", firstName);
            textMessage.setStringProperty("lastName", lastName);
            textMessage.setStringProperty("password", password);
            textMessage.setStringProperty("street", street);
            textMessage.setStringProperty("streetNumber", streetNumber);
            textMessage.setStringProperty("cityName", cityName);
            textMessage.setStringProperty("cityCountry", cityCountry);
            
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
    
    return Response.status(OK).entity("User successfuly created!").build();
    }
    
    @GET
    @Path("getusers")
    public Response getUsers() {
    
    ArrayList<Korisnik> users = null;
        
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(topic);
            
            // message
            TextMessage msg = context.createTextMessage("request");
            msg.setByteProperty("request", ALL_USERS);
            msg.setIntProperty("podsistem", 1);
            
            producer.send(queue, msg);
            
            // response
            Message mess = consumer.receive();
            if (!(mess instanceof ObjectMessage)){
                return Response.status(Response.Status.BAD_REQUEST).entity("Greska: Neodgovarajuci tip poruke!").build();
            }
            ObjectMessage objMsg = (ObjectMessage) mess;
            users = (ArrayList<Korisnik>) objMsg.getObject();
            
        } catch (JMSException ex) {
            Logger.getLogger(Grad.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassCastException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Greska: Neodgovarajuci tip objekta!").build();
        }
        
        return Response.status(OK).entity(new GenericEntity<List<Korisnik>>(users){}).build();
    }
    
    @POST
    @Path("wireMoneyToUser/{userName}/{money}")
    public Response wireMoneyToUser(@PathParam("userName") String userName, @PathParam("money") String money) 
    {
        try {
            
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            
            //create message
            
            TextMessage textMessage = context.createTextMessage("request");
            
            textMessage.setByteProperty("request", WIRE_MONEY_TO_USER);
            textMessage.setIntProperty("podsistem", 1);
            
            textMessage.setStringProperty("userName", userName);
            textMessage.setStringProperty("money", money);
            
            
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
    
    return Response.status(OK).entity("Money successfuly wired!").build();
    }
}
