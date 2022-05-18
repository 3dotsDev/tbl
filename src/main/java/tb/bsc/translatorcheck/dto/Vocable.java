package tb.bsc.translatorcheck.dto;

import java.util.ArrayList;

/**
 *
 */
public class Vocable {

    private ArrayList<Eval> evals;
    private Integer checkcounter;
    private Integer correctnesCounter;
    private Integer id;

    public Vocable(){}

    public Vocable(ArrayList<Eval> evals, Integer checkcounter, Integer correctnesCounter, Integer id) {
        this.evals = evals;
        this.checkcounter = checkcounter;
        this.correctnesCounter = correctnesCounter;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCorrectnesCounter() {
        return correctnesCounter;
    }

    public void setCorrectnesCounter(Integer correctnesCounter) {
        this.correctnesCounter = correctnesCounter;
    }

    public Integer getCheckcounter() {
        return checkcounter;
    }

    public void setCheckcounter(Integer checkcounter) {
        this.checkcounter = checkcounter;
    }

    public ArrayList<Eval> getEvals() {
        return evals;
    }

    public void setEvals(ArrayList<Eval> evals) {
        this.evals = evals;
    }
}
