package main.java.riakj.csv;

import com.basho.riak.client.api.convert.ConversionException;
import com.basho.riak.client.api.convert.Converter;
import com.basho.riak.client.core.util.BinaryValue;
import com.basho.riak.client.core.util.Constants;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayOutputStream;


public class KryoJsonDataConverter extends Converter<JsonData>
{
    public KryoJsonDataConverter()
    {
        super(JsonData.class);
    }

    public JsonData toDomain(BinaryValue value, String contentType) throws ConversionException
    {
        if (value == null)
        {
            return null;
        }

        final Kryo kryo = new Kryo();
        kryo.register(JsonData.class);
        final Input input = new Input(value.getValue());

        final JsonData person = kryo.readObject(input, JsonData.class);
        input.close();
        return person;
    }

    public ContentAndType fromDomain(JsonData domainObject) throws ConversionException
    {
        Kryo kryo = new Kryo();
        kryo.register(JsonData.class);

        Output out = new Output(new ByteArrayOutputStream());

        kryo.writeObject(out, domainObject);

        return new ContentAndType(BinaryValue.create(out.toBytes()), Constants.CTYPE_OCTET_STREAM);
    }
}