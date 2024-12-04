package serializers

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.serialization.Serializer
import models.Jockey

class JockeySerializer implements Serializer<Jockey> {
    @Override
    byte[] serialize(String topic, Jockey data) {
        try {
            return new ObjectMapper().writeValueAsBytes(data)
        }  catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize Jockey", e)
        }
    }
}