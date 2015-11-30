package tdepinoy.henripotier.cart.serialization;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import tdepinoy.henripotier.model.Book;

public class CartDeserializer extends KeyDeserializer {

    @Override
    public Object deserializeKey(String key, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(key, Book.class);
    }
}
