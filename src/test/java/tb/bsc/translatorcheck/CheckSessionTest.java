package tb.bsc.translatorcheck;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tb.bsc.translatorcheck.Exception.TranslatorException;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

public class CheckSessionTest {

    private Path testFilePath;

    @BeforeEach
    public void doStartUp() throws URISyntaxException {
        URL url = TranslatorApplication.class.getResource("data.json");
        File file = new File(url.toURI());
        testFilePath = file.toPath();
    }

    @Test
    public void checkSessionStart() throws InterruptedException, TranslatorException {
        CheckSession session = new CheckSession(testFilePath);
        Thread.sleep(10); // take time to get sure the timelaps expand

        Assertions.assertTrue(session.getTimeElapsed() > 0);
    }

    @Test
    public void checkSessionEnd() throws InterruptedException, TranslatorException {
        CheckSession session = new CheckSession(testFilePath);
        Thread.sleep(10); // take time to get sure the timelaps expand
        session.stopSession();
        Assertions.assertTrue(session.getTimeElapsed() > 0);
    }

    @Test
    public void checkSessionData() throws TranslatorException {
        CheckSession session = new CheckSession(testFilePath);
        Assertions.assertEquals(session.getVocabulary().size(), 2);
    }
}
