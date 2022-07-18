package tb.bsc.translatorcheck.logic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Vocab {

    private Integer checkcounter;
    private Integer correctnesCounter;
    private Integer id;
    private Integer calculatedFailCount;
    private String valueEn;
    private String valueDe;

    public Vocab() {
    }
//
//    public Vocable(ArrayList<Eval> evals, Integer checkcounter, Integer correctnesCounter, Integer id) {
//        this.evals = evals;
//        this.checkcounter = checkcounter;
//        this.correctnesCounter = correctnesCounter;
//        this.id = id;
//    }

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

    public Integer getCalculatedFailCount() {
        return this.checkcounter - this.correctnesCounter;
    }

    public String getValueEn() {
        return valueEn;
    }

    public void setValueEn(String valueEn) {
        this.valueEn = valueEn;
    }

    public String getValueDe() {
        return valueDe;
    }

    public void setValueDe(String valueDe) {
        this.valueDe = valueDe;
    }
}
