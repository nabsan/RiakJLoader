package main.java.riakj.csv;

import com.basho.riak.client.api.annotations.RiakIndex;
import com.basho.riak.client.api.annotations.RiakBucketName;
import com.basho.riak.client.api.annotations.RiakKey;
import com.basho.riak.client.api.annotations.RiakUsermeta;
import com.basho.riak.client.core.query.Namespace;

import java.util.Map;

public class JsonData
{
    // The @RiakKey annotation marks the field you want to use as the Key in Riak
    @RiakKey
    private String keystr="";

    @RiakBucketName
    private String riakBucketName = "jsondata";

    // Marked transient so kryo doesn't serialize them
    // The KryoPersonConverter will inject these from Riak
    @RiakIndex(name = "measdate")
    transient private String measdate;

    @RiakIndex(name = "koutei")
    transient private String koutei;

    @RiakUsermeta
    transient private Map<String, String> usermetaData;



    public JsonData() {}

    public JsonData(String keystr,String measdate, String koutei)
    {
        this.keystr =keystr;
        this.measdate=measdate;
        this.koutei = koutei;
    }
    

    public String getKeystr() {
		return keystr;
	}

	public void setKeystr(String keystr) {
		this.keystr = keystr;
	}
	
	

	public String getMeasdate() {
		return measdate;
	}

	public void setMeasdate(String measdate) {
		this.measdate = measdate;
	}

	public String getKoutei() {
		return koutei;
	}

	public void setKoutei(String koutei) {
		this.koutei = koutei;
	}

	Namespace getPersonNamespace()
    {
        return new Namespace(riakBucketName);
    }
}