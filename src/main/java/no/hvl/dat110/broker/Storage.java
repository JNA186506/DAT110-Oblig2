package no.hvl.dat110.broker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import no.hvl.dat110.common.TODO;
import no.hvl.dat110.common.Logger;
import no.hvl.dat110.messages.Message;
import no.hvl.dat110.messagetransport.Connection;

public class Storage {

	// data structure for managing subscriptions
	// maps from a topic to set of subscribed users
	protected ConcurrentHashMap<String, Set<String>> subscriptions;
	
	// data structure for managing currently connected clients
	// maps from user to corresponding client session object
	
	protected ConcurrentHashMap<String, ClientSession> clients;

    protected ConcurrentHashMap<String, List<Message>> offlineMsgs;
    
	public Storage() {
		subscriptions = new ConcurrentHashMap<String, Set<String>>();
		clients = new ConcurrentHashMap<String, ClientSession>();
        offlineMsgs = new ConcurrentHashMap<String, List<Message>>();
	}

	public Collection<ClientSession> getSessions() {
		return clients.values();
	}

	public Set<String> getTopics() {

		return subscriptions.keySet();

	}

	// get the session object for a given user
	// session object can be used to send a message to the user
	
	public ClientSession getSession(String user) {
		return clients.get(user);
	}

	public Set<String> getSubscribers(String topic) {

		return (subscriptions.get(topic));

	}

	public void addClientSession(String user, Connection connection) {
        ClientSession clientSession = new ClientSession(user, connection);
        clients.putIfAbsent(user, clientSession);
	}

	public void removeClientSession(String user) {
        ClientSession clientSession = clients.remove(user);
        if (clientSession != null) {
            clientSession.disconnect();
        }
	}

	public void createTopic(String topic) {
        subscriptions.putIfAbsent(topic, ConcurrentHashMap.newKeySet());
	}

	public void deleteTopic(String topic) {
        subscriptions.remove(topic);
	}

	public void addSubscriber(String user, String topic) {
        subscriptions.putIfAbsent(topic, ConcurrentHashMap.newKeySet());
        subscriptions.get(topic).add(user);
	}

	public void removeSubscriber(String user, String topic) {
       subscriptions.get(topic).remove(user);
	}
    
    public void storeOfflineMessage(String user, Message msg) {
        offlineMsgs.putIfAbsent(user, new ArrayList<>());
        offlineMsgs.get(user).add(msg);
    }
    
    public List<Message> getOfflineMsgs(String user) {
        List<Message> msgs = offlineMsgs.get(user);
        if (msgs == null)  return new ArrayList<Message>();
        return new ArrayList<>(msgs);
    }
    
    public void clearOfflineMsgs(String user) {
        offlineMsgs.remove(user);
    }
}
