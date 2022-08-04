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

    public Vocab setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getCorrectnesCounter() {
        return correctnesCounter;
    }

    public Vocab setCorrectnesCounter(Integer correctnesCounter) {
        this.correctnesCounter = correctnesCounter;
        return this;
    }

    public Integer getCheckcounter() {
        return checkcounter;
    }

    public Vocab setCheckcounter(Integer checkcounter) {
        this.checkcounter = checkcounter;
        return this;
    }

    @JsonIgnore
    public Integer getCalculatedFailCount() {
        return this.checkcounter - this.correctnesCounter;
    }

    public String getValueEn() {
        return valueEn;
    }

    public Vocab setValueEn(String valueEn) {
        this.valueEn = valueEn;
        return this;
    }

    public String getValueDe() {
        return valueDe;
    }

    public Vocab setValueDe(String valueDe) {
        this.valueDe = valueDe;
        return this;
    }
}
