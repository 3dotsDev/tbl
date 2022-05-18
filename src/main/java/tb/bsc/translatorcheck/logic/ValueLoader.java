package tb.bsc.translatorcheck.logic;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import tb.bsc.translatorcheck.TranslatorApplication;
import tb.bsc.translatorcheck.logic.dto.Vocab;
import tb.bsc.translatorcheck.logic.dto.Vocabulary;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ValueLoader {

    public List<Vocab> loadData(Path dataFilePath) {
        try {
            byte[] jsonData = Files.readAllBytes(dataFilePath);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY,true);
            Vocabulary vocable = objectMapper.readValue(jsonData, Vocabulary.class);
            return vocable.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
