package tb.bsc.translatorcheck.logic.dto;

import java.util.ArrayList;
import java.util.List;

public class Vocabulary2 {
    private List<Vocab> data = new ArrayList<>();

    public Vocabulary2(){}

    public Vocabulary2(List<Vocab> data) {
        this.data = data;
    }

    public List<Vocab> getData() {
        return data;
    }

    public void setData(List<Vocab> data) {
        this.data = data;
    }
}
