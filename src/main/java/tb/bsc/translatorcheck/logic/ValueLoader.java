package tb.bsc.translatorcheck.logic;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import tb.bsc.translatorcheck.logic.dto.Vocab;
import tb.bsc.translatorcheck.logic.dto.Vocabulary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class ValueLoader {

    public List<Vocab> loadData(Path dataFilePath) throws IOException {
        byte[] jsonData = Files.readAllBytes(dataFilePath);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        Vocab[] vocable = objectMapper.readValue(jsonData, Vocab[].class);
        return Arrays.stream(vocable).toList();
    }
}
