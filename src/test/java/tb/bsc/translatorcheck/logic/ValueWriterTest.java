package tb.bsc.translatorcheck.logic;

import org.junit.jupiter.api.Test;
import tb.bsc.translatorcheck.TranslatorApplication;
import tb.bsc.translatorcheck.logic.dto.Vocab;
import tb.bsc.translatorcheck.logic.helper.ValueWriter;

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
        Vocab v2_1 = new Vocab();
       v2_1.setValueDe("Gaga");
       v2_1.setValueEn("Test");
        v2_1.setCheckcounter(10);
        v2_1.setCorrectnesCounter(2);
        v2_1.setId(1);
        vList.add(v2_1);

        Vocab v2_2 = new Vocab();

        v2_2.setValueDe("Gaga2");
        v2_2.setValueEn("Test2");
        v2_2.setCheckcounter(10);
        v2_2.setCorrectnesCounter(2);
        v2_2.setId(2);
        vList.add(v2_2);

        URL url = TranslatorApplication.class.getResource("data.json");
        File file = new File(url.toURI());

        vloader.writeData(vList, file.toPath());
    }
}
