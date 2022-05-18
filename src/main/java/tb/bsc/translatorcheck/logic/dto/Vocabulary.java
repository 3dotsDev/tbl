package tb.bsc.translatorcheck.logic.dto;

import java.util.ArrayList;
import java.util.List;

public class Vocabulary {
    private List<Vocab> data = new ArrayList<>();

    public Vocabulary(){}

    public Vocabulary(List<Vocab> data) {
        this.data = data;
    }

    public List<Vocab> getData() {
        return data;
    }

    public void setData(List<Vocab> data) {
        this.data = data;
    }
}
