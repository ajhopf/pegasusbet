package serializers

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import dtos.BetResponseDTO
import org.apache.kafka.common.serialization.Serializer
import users.Bet

class BetSerializer implements Serializer<BetResponseDTO> {
    @Override
    byte[] serialize(String topic, BetResponseDTO data) {
        try {
            ObjectMapper mapper = new ObjectMapper()
            mapper.registerModule(new JavaTimeModule())
            return mapper.writeValueAsBytes(data)
        }  catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize Bet", e)
        }
    }
}
