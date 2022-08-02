package tb.bsc.translatorcheck.logic.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 */
public class Vocab {

    private Integer checkcounter;
    private Integer correctnesCounter;
    private Integer id;
    private String valueEn;
    private String valueDe;

    public Vocab() {
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

    @JsonIgnore
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
