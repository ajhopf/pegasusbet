package serializers

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.serialization.Serializer
import models.RaceCourse

class RaceCourseSerializer implements Serializer<RaceCourse> {
    @Override
    byte[] serialize(String topic, RaceCourse data) {
        try {
            return new ObjectMapper().writeValueAsBytes(data)
        }  catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize RaceCourse", e)
        }
    }
}
