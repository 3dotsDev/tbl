package tb.bsc.translatorcheck.logic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import tb.bsc.translatorcheck.logic.dto.Vocab;

import java.util.List;

class ValueLoaderTest {

    @Test
    void loadData() {
        ValueLoader vloader = new ValueLoader();
        List<Vocab> data = vloader.loadData();

        Assertions.assertEquals(2, data.stream().count());

    }
}
