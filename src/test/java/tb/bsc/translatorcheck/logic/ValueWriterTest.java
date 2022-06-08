package tb.bsc.translatorcheck.logic;

import org.junit.jupiter.api.Test;
import tb.bsc.translatorcheck.TranslatorApplication;
import tb.bsc.translatorcheck.logic.dto.Suggestions;
import tb.bsc.translatorcheck.logic.dto.Vocab;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

class ValueWriterTest {

    @Test
    void loadData() throws URISyntaxException {
        ValueWriter vloader = new ValueWriter();

        List<Vocab> vList = new ArrayList<>();
//        var v1 = new Vocabulary();
        Vocab v2_1 = new Vocab();
        List<Suggestions> sugesstionsList1 = new ArrayList<>();
        Suggestions e1_1 = new Suggestions();
        e1_1.setId(1);
        e1_1.setLang("de");
        e1_1.setText("Hallo");
        sugesstionsList1.add(e1_1);
        Suggestions e1_2 = new Suggestions();
        e1_2.setId(2);
        e1_2.setLang("en");
        e1_2.setText("Hello");
        sugesstionsList1.add(e1_2);
        v2_1.setSuggestions(sugesstionsList1);
        v2_1.setCheckcounter(10);
        v2_1.setCorrectnesCounter(2);
        v2_1.setId(1);
        vList.add(v2_1);

        Vocab v2_2 = new Vocab();
        List<Suggestions> sugesstionsList2 = new ArrayList<>();
        Suggestions e2_2 = new Suggestions();
        e2_2.setId(1);
        e2_2.setLang("de");
        e2_2.setText("Blau");
        sugesstionsList2.add(e2_2);
        Suggestions e2_3 = new Suggestions();
        e2_3.setId(2);
        e2_3.setLang("en");
        e2_3.setText("Blue");
        sugesstionsList2.add(e2_3);
        v2_2.setSuggestions(sugesstionsList2);
        v2_2.setCheckcounter(10);
        v2_2.setCorrectnesCounter(2);
        v2_2.setId(2);
        vList.add(v2_2);

        URL url = TranslatorApplication.class.getResource("data.json");
        File file = new File(url.toURI());

        vloader.writeData(vList, file.toPath());
    }
}
