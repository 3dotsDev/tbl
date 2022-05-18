package tb.bsc.translatorcheck.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import tb.bsc.translatorcheck.TranslatorApplication;
import tb.bsc.translatorcheck.logic.dto.Vocabulary;

import java.io.File;
import java.net.URL;

public class ValueWriter {

    public void writeData(Vocabulary data) {
        try {

            URL url = TranslatorApplication.class.getResource("data.json");
            File file = new File(url.toURI());
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT,true);
            objectMapper.writeValue(file,data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
