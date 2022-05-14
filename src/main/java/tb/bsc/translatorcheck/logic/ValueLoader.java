package tb.bsc.translatorcheck.logic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import tb.bsc.translatorcheck.dto.Eval;
import tb.bsc.translatorcheck.dto.Vocable;
import tb.bsc.translatorcheck.dto.Vocabulary;

import java.io.File;
import java.io.FileReader;
import java.util.List;

public class ValueLoader {

    public List<Vocable> loadData() {
        try {
            File file = new File("C:\\BPS\\TranslatorCheck\\data.json");

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            JsonReader reader = new JsonReader(new FileReader(file));

            Vocabulary vocabulary = gson.fromJson(reader,Vocabulary.class);
            return vocabulary.getVocables();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
