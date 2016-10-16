package main.java.secondindex;

import com.basho.riak.client.api.convert.ConversionException;
import com.basho.riak.client.api.convert.Converter;
import com.basho.riak.client.core.util.BinaryValue;
import com.basho.riak.client.core.util.Constants;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayOutputStream;


public class KryoPersonConverter extends Converter<Person>
{
    public KryoPersonConverter()
    {
        super(Person.class);
    }

    public Person toDomain(BinaryValue value, String contentType) throws ConversionException
    {
        if (value == null)
        {
            return null;
        }

        final Kryo kryo = new Kryo();
        kryo.register(Person.class);
        final Input input = new Input(value.getValue());

        final Person person = kryo.readObject(input, Person.class);
        input.close();
        return person;
    }

    public ContentAndType fromDomain(Person domainObject) throws ConversionException
    {
        Kryo kryo = new Kryo();
        kryo.register(Person.class);

        Output out = new Output(new ByteArrayOutputStream());

        kryo.writeObject(out, domainObject);

        return new ContentAndType(BinaryValue.create(out.toBytes()), Constants.CTYPE_OCTET_STREAM);
    }
}