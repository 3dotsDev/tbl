package tb.bsc.translatorcheck.logic.helper;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Random;

public class ValueHelper {

    public static int getLottery(int size, int currentIndex) {
        int lottery = 0;

        if (size > 1) {
            Random rand = new Random();
            int found = rand.nextInt(0, size - 1); // -1 weil index bei 0 anfaengt
            while (found == currentIndex) {
                found = rand.nextInt(0, size - 1); // -1 weil index bei 0 anfaengt
            }

            lottery = found;
        }
        return lottery;
    }

    public static <T> T fromJSON(final TypeReference<T> type, final byte[] jsonPacket) throws IOException {
        T data = null;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        data = objectMapper.readValue(jsonPacket, type);
        return data;
    }
}
