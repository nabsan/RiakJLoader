package main.java.sample;

import com.basho.riak.client.api.annotations.RiakBucketName;
import com.basho.riak.client.api.annotations.RiakKey;
import com.basho.riak.client.core.query.Namespace;

public class Person
{
    // The @RiakKey annotation marks the field you want to use as the Key in Riak
    @RiakKey
    private String name;

    @RiakBucketName
    private String riakBucketName = "people";

    private String address;
    private String phone;

    public Person() {}

    public Person(String name, String address, String phone)
    {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    Namespace getPersonNamespace()
    {
        return new Namespace(riakBucketName);
    }
}
