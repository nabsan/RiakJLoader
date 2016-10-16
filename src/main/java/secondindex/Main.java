package main.java.secondindex;

import java.io.File;

import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.api.commands.indexes.BinIndexQuery;
import com.basho.riak.client.api.commands.kv.FetchValue;
import com.basho.riak.client.api.commands.kv.StoreValue;
import com.basho.riak.client.api.convert.ConverterFactory;
import com.google.gson.Gson;

import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

import main.java.riakj.csv.ReadCsv;

public class Main 
{
    public static void main(String[] args) throws UnknownHostException, ExecutionException, InterruptedException
    {
        ConverterFactory factory = ConverterFactory.getInstance();
        KryoPersonConverter converter = new KryoPersonConverter();
        factory.registerConverterForClass(Person.class, converter);

        RiakClient client = RiakClient.newClient("127.0.0.1");

        Person p = new Person("Brian", "Roach", "1111 Basho Drive", "555-1212", "engineer");
        Person p2 = new Person("shinko", "tobiko", "222 Tobhi Drive", "000-1211", "engineer");


        //client.execute(new StoreValue.Builder(p).build());
        //client.execute(new StoreValue.Builder(p2).build());

        // Get the list of keys using the index name we declared in our Person Object
        BinIndexQuery biq = new BinIndexQuery.Builder(p.getPersonNamespace(), "job_title", "engineer").build();
        final BinIndexQuery.Response indexResponse = client.execute(biq);

        for (BinIndexQuery.Response.Entry idxE : indexResponse.getEntries())
        {
            final FetchValue.Response execute =
                    client.execute(new FetchValue.Builder(idxE.getRiakObjectLocation()).build());

            final Person p3 = execute.getValue(Person.class);
            System.out.println(p3.getFullName());
            System.out.println(p3.getAddress());
            System.out.println(p3.getPhone());
            System.out.println(p3.getJobTitle());
            System.out.println();
        }
        
        //ReadCsv csv = new ReadCsv();
        //Object ob =csv.readCSV(new File("/Users/manabu/Documents/workspace/RiakJLoader/data/sampledata.csv"));
        
        //Gson gjson =new Gson();
        //System.out.println(gjson.toJson(ob));
        //curl -i  http://localhost:8098/riak/people/tobiko でも確認可能 

        client.shutdown();
    }
}