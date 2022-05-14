package tb.bsc.translatorcheck.dto;

import java.util.List;

public class Vocable {
    private int id;

    private int checkCounter;
    private List<Eval> evals;
    private int correctnesCounter;


    public int getCheckCounter() {
        return checkCounter;
    }

    public void setCheckCounter(int checkCounter) {
        this.checkCounter = checkCounter;
    }

    public int getCorrectnesCounter() {
        return correctnesCounter;
    }

    public void setCorrectnesCounter(int correctnesCounter) {
        this.correctnesCounter = correctnesCounter;
    }

    public List<Eval> getEvals() {
        return evals;
    }

    public void setEvals(List<Eval> evals) {
        this.evals = evals;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
