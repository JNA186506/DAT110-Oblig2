package no.hvl.dat110.iotsystem;

import no.hvl.dat110.broker.ClientSession;
import no.hvl.dat110.client.Client;
import no.hvl.dat110.common.TODO;

public class TemperatureDevice {

	private static final int COUNT = 10;

	public static void main(String[] args) {

		// simulated / virtual temperature sensor
		TemperatureSensor sn = new TemperatureSensor();

		// TODO - start

        String user = "sensor";
		// create a client object and use it to
        Client client = new Client(user, Common.BROKERHOST, Common.BROKERPORT);

		// - connect to the broker - user "sensor" as the user name
        client.connect();
		// - publish the temperature(s)
        String topic = "temperature";
        for (int i = 0; i < COUNT; i++) {
            int temp = sn.read();
            String message = Integer.toString(temp);
            client.publish(topic, message);
        }
		// - disconnect from the broker
        client.disconnect();

		// TODO - end

	}
}
