package tb.bsc.translatorcheck.dto;

import java.util.List;

/**
 *
 */
public class Vocable {

    private List<Vocable> vocables;

    public Vocable(List<Vocable> vocables) {
        this.vocables = vocables;
    }

    public List<Vocable> getVocables() {
        return vocables;
    }

    public void setVocables(List<Vocable> vocables) {
        this.vocables = vocables;
    }
}
