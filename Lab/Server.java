package Lab;
import Collection.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main (String [] args) throws Exception{
        CollectionsofPerson collection = new CollectionsofPerson();
        collection.doInitialization();
        //int port = 5555;
        int port = Integer.parseInt(args[0]);
        DatagramSocket socket = new DatagramSocket(port);
        LinkedHashSet<Integer> ports = new LinkedHashSet<>();
        ExecutorService pool = Executors.newFixedThreadPool(3);


        while(true){
            ServerReadAndDealThread thread = new ServerReadAndDealThread(collection,socket,ports);
            thread.start();
            thread.join();
            collection.setPeople(thread.getCollection().getPeople());
            ports = thread.getPorts();
            Response response = thread.getResponse();
            DatagramPacket packet = thread.getPacket();
            pool.execute(new ServerSendingThread(response,socket,packet));
            if(thread.isExit()){
                System.exit(2);
            }
            //send.start();
        }
    }

    protected static LinkedHashSet<Person> sort(LinkedHashSet<Person> linkedHashSet) throws NullException{
        LinkedHashSet<Person> newone = new LinkedHashSet<>();
        Comparator<Person> comparator;
        comparator = Comparator.comparingInt(a -> a.getId());
        linkedHashSet.stream().sorted(comparator).forEach(newone::add);
        return newone;
    }
}
