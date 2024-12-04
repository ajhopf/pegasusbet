package serializers

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.serialization.Serializer
import webcrawler.Horse

class HorseSerializer implements Serializer<Horse> {
    @Override
    byte[] serialize(String topic, Horse data) {
        try {
            return new ObjectMapper().writeValueAsBytes(data)
        }  catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize Horse", e)
        }
    }
}
