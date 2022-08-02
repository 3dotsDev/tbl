package tb.bsc.translatorcheck.logic.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import tb.bsc.translatorcheck.Exception.TranslatorException;
import tb.bsc.translatorcheck.logic.dto.Vocab;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class ValueWriter {
    public void writeData(List<Vocab> data, Path dataFilePath) throws TranslatorException, IOException {
        File currentDataFile = null;
        currentDataFile = new File(String.valueOf(dataFilePath));
        if (!currentDataFile.exists()) {
            if (!currentDataFile.createNewFile()) {
                throw new TranslatorException("File kann nicht erstellt werden");
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        objectMapper.writeValue(currentDataFile, data);
    }
}
