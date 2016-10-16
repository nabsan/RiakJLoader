package main.java.sample;

import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.api.commands.kv.FetchValue;
import com.basho.riak.client.api.commands.kv.StoreValue;
import com.basho.riak.client.api.convert.ConverterFactory;
import com.basho.riak.client.core.query.Location;

import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

public class Main 
{
    public static void main(String[] args) throws UnknownHostException, ExecutionException, InterruptedException
    {
        ConverterFactory factory = ConverterFactory.getInstance();
        KryoPersonConverter converter = new KryoPersonConverter();
        factory.registerConverterForClass(Person.class, converter);

        RiakClient client = RiakClient.newClient( "127.0.0.1");

        Person p = new Person("Brian Roach", "1111 Basho Drive", "555-1212");
        Person p2 = new Person("Manabu", "moriyama", "111-1212");

        final StoreValue store = new StoreValue.Builder(p).build();
        client.execute(store);
        
        final StoreValue store2 = new StoreValue.Builder(p2).build();
        client.execute(store2);

        final FetchValue fetch = new FetchValue.Builder(new Location(p.getPersonNamespace(), p.getName())).build();
        final FetchValue.Response fetchResp = client.execute(fetch);
        final Person fetchedPerson = fetchResp.getValue(Person.class);

        System.out.println(fetchedPerson.getName());
        System.out.println(fetchedPerson.getAddress());
        System.out.println(fetchedPerson.getPhone());
        
      //curl -i  http://localhost:8098/riak/people/Brian%20Roach でも確認可能 

        client.shutdown();
    }
}
