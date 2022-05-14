package tb.bsc.translatorcheck.logic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import tb.bsc.translatorcheck.dto.Vocable;

import java.util.List;

class ValueLoaderTest {

    @Test
    void loadData() {
        ValueLoader vloader = new ValueLoader();
        List<Vocable> vocables = vloader.loadData();

        Assertions.assertEquals(4, vocables.stream().count());

    }
}
