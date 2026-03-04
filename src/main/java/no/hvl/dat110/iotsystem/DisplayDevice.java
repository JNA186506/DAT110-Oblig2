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
        String topic = "temperature";
				
		// create a client object and use it to
        Client client = new Client(topic, Common.BROKERHOST, Common.BROKERPORT);
		
        String username = "display";
		// - connect to the broker - use "display" as the username
        
		// - create the temperature topic on the broker
        Message temperatureMsg = new CreateTopicMsg(username, topic);
		// - subscribe to the topic
        client.subscribe(topic);
        
		// - receive messages on the topic
        client.receive();
		// - unsubscribe from the topic
        
        client.unsubscribe(topic);
		// - disconnect from the broker
        client.disconnect();
		
		// TODO - END
		
		System.out.println("Display stopping ... ");
		
		throw new UnsupportedOperationException(TODO.method());
		
	}
}
