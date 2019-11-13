package io.riguron.exchange.model.liquid;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.List;

public class LiquidPriceDeserializer extends JsonDeserializer<List<LiquidPriceInfo>> {

    @Override
    public List<LiquidPriceInfo> deserialize(JsonParser p, DeserializationContext context) throws IOException {
        return p.getCodec().readValue(p.getCodec().getFactory().createParser(p.getText()), new TypeReference<List<LiquidPriceInfo>>(){});
    }
}
