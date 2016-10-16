package main.java.secondindex;

import com.basho.riak.client.api.annotations.RiakIndex;
import com.basho.riak.client.api.annotations.RiakBucketName;
import com.basho.riak.client.api.annotations.RiakKey;
import com.basho.riak.client.api.annotations.RiakUsermeta;
import com.basho.riak.client.core.query.Namespace;

import java.util.Map;

public class Person
{
    // The @RiakKey annotation marks the field you want to use as the Key in Riak
    @RiakKey
    private String lastName;

    @RiakBucketName
    private String riakBucketName = "people";

    // Marked transient so kryo doesn't serialize them
    // The KryoPersonConverter will inject these from Riak
    @RiakIndex(name = "full_name")
    transient private String fullName;

    @RiakIndex(name = "job_title")
    transient private String jobTitle;

    @RiakUsermeta
    transient private Map<String, String> usermetaData;

    private String firstName;
    private String address;
    private String phone;

    public Person() {}

    public Person(String firstName, String lastName, String address, String phone, String title)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.fullName = firstName + " " + lastName;
        this.jobTitle = title;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
        this.setFullName(firstName + " " + lastName);
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
        this.setFullName(firstName + " " + lastName);
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

    public String getJobTitle()
    {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle)
    {
        this.jobTitle = jobTitle;
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    Namespace getPersonNamespace()
    {
        return new Namespace(riakBucketName);
    }
}