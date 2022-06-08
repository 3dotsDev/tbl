package tb.bsc.translatorcheck.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import tb.bsc.translatorcheck.TranslatorApplication;
import tb.bsc.translatorcheck.logic.dto.Vocab;
import tb.bsc.translatorcheck.logic.dto.Vocabulary;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

public class ValueWriter {
    public void writeData(List<Vocab> data, Path dataFilePath) {
        try {
            File currentDataFile = new File(String.valueOf(dataFilePath));
            if (!currentDataFile.exists()) {
                if (!currentDataFile.createNewFile()) {
                    throw new Exception("File kann nicht erstellt werden");
                }
            }
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            objectMapper.writeValue(currentDataFile, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
