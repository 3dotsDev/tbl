package tb.bsc.translatorcheck.logic;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import tb.bsc.translatorcheck.TranslatorApplication;
import tb.bsc.translatorcheck.dto.Vocable;

import java.io.File;
import java.net.URL;
import java.util.List;

public class ValueLoader {

    public List<Vocable> loadData() {
        try {

            URL url = TranslatorApplication.class.getResource("data.json");
            File file = new File(url.toURI());

            ObjectMapper objectMapper = new ObjectMapper();
            ObjectReader objectReader = objectMapper.readerForArrayOf(Vocable.class);
            List<Vocable> o = objectReader.readValue(file);
            List<Vocable> vocable = objectMapper.readValue(file, new TypeReference<List<Vocable>>() {});
            return vocable;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
