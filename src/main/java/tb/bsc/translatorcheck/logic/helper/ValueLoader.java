package tb.bsc.translatorcheck.logic.helper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import tb.bsc.translatorcheck.logic.dto.Vocab;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class ValueLoader {

    public ArrayList<Vocab> loadData(Path dataFilePath) throws IOException {
        byte[] jsonData = Files.readAllBytes(dataFilePath);
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<Vocab> vocabs = ValueHelper.fromJSON(new TypeReference<ArrayList<Vocab>>() {
        }, jsonData);
        return vocabs;
    }
}
