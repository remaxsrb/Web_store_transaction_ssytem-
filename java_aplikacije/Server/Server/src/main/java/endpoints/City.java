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
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.OK;

/**
 *
 * @author remax
 */
@Path("cities")
public class City {
    
    private static final byte CREATE_CITY = 1;
    private static final byte ALL_CITIES = 12;
    
    @Resource(lookup = "myConnFactory")
    ConnectionFactory connectionFactory;
    
    @Resource(lookup = "serverTopic")
    Topic topic;
    
    @Resource(lookup = "myQueue")
    Queue queue;
    
    @POST
    public Response createCity(@FormParam("cityName") String cityName,@FormParam("cityCountry") String cityCountry ) {
        
        try {
            
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(queue);
            
            //create message
            
            TextMessage textMessage = context.createTextMessage("request");
            
            textMessage.setByteProperty("request", CREATE_CITY);
            textMessage.setIntProperty("subsystem", 1);
            
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
    
    return Response.status(OK).entity("City successfuly created!").build();
    }
    
}
