package tb.bsc.translatorcheck.logic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import tb.bsc.translatorcheck.TranslatorApplication;
import tb.bsc.translatorcheck.logic.dto.Vocab;
import tb.bsc.translatorcheck.logic.helper.ValueLoader;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

class ValueLoaderTest {

    @Test
    void loadData() throws URISyntaxException, IOException {
        ValueLoader vloader = new ValueLoader();

        URL url = TranslatorApplication.class.getResource("data.json");
        File file = new File(url.toURI());
        List<Vocab> data = vloader.loadData(file.toPath());

        Assertions.assertEquals(2, data.stream().count());

    }
}
