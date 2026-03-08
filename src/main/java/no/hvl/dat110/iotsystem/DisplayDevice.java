package no.hvl.dat110.iotsystem;

import no.hvl.dat110.client.Client;
import no.hvl.dat110.messages.CreateTopicMsg;
import no.hvl.dat110.messages.Message;
import no.hvl.dat110.messages.PublishMsg;
import no.hvl.dat110.common.TODO;

public class DisplayDevice {
	
	private static final int COUNT = 10;
		
	public static void main (String[] args) {
		
		System.out.println("Display starting ...");
		
		// TODO - START
        String user = "display";
        String topic = "temperature";
				
		// create a client object and use it to
        Client client = new Client(user, Common.BROKERHOST, Common.BROKERPORT);
        client.connect();
		
		// - connect to the broker - use "display" as the username
        
		// - create the temperature topic on the broker
        client.createTopic(topic);
        client.subscribe(topic);
		// - subscribe to the topic
		// - receive messages on the topic
		// - unsubscribe from the topic
        for (int i = 0; i < COUNT; i++) {
            Message message = client.receive();
            System.out.println("Recieved: " + message.toString());
        }
        
        client.unsubscribe(topic);
        client.disconnect();
		
		System.out.println("Display stopping ... ");
		
	}
}
