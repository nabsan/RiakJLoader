package main.java.riakj.csv;

import java.io.File;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.api.commands.indexes.BinIndexQuery;
import com.basho.riak.client.api.commands.kv.FetchValue;
import com.basho.riak.client.api.commands.kv.StoreValue;
import com.basho.riak.client.api.convert.ConverterFactory;
import com.google.gson.Gson;

public class MainJson 
{
	
    public static void main(String[] args) throws UnknownHostException, ExecutionException, InterruptedException
    {
        ConverterFactory factory = ConverterFactory.getInstance();
        KryoJsonDataConverter converter = new KryoJsonDataConverter();
        factory.registerConverterForClass(JsonData.class, converter);
        //factory.registerConverterForClass(JsonData.class, converter);

        RiakClient client = RiakClient.newClient("127.0.0.1");
        
        ReadCsv csv = new ReadCsv();
        ArrayList<SampleCsvBean> ob =csv.readCSV(new File("/Users/manabu/Documents/workspace/RiakJLoader/data/sampledata.csv"));
        
        Gson gjson =new Gson();
        JsonData jd = new JsonData("key1","ggg",gjson.toJson(ob)) ;
        System.out.println(gjson.toJson(ob));

        //Person p = new Person("Brian", "Roach", "1111 Basho Drive", "555-1212", "engineer");
        //Person p2 = new Person("shinko", "tobiko", "222 Tobhi Drive", "000-1211", "engineer");


        client.execute(new StoreValue.Builder(jd).build());
        //client.execute(new StoreValue.Builder(p2).build());

//        // Get the list of keys using the index name we declared in our Person Object
       BinIndexQuery biq = new BinIndexQuery.Builder(jd.getPersonNamespace(), "measdate", "ggg").build();
        final BinIndexQuery.Response indexResponse = client.execute(biq);
//
        for (BinIndexQuery.Response.Entry idxE : indexResponse.getEntries())
        {
            final FetchValue.Response execute =
                    client.execute(new FetchValue.Builder(idxE.getRiakObjectLocation()).build());
//
            final JsonData p3 = execute.getValue(JsonData.class);
            System.out.println(p3.getKoutei());
            System.out.println(p3.getKeystr());
            System.out.println(p3.getMeasdate());
//
            //System.out.println(p3.get);
            System.out.println("------");
        }
        System.out.println("end");
        

        //curl -i  http://localhost:8098/riak/people/tobiko でも確認可能 

        client.shutdown();
    }
}