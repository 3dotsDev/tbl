package tb.bsc.translatorcheck.logic.dto;

public class Suggestions {
    private Integer id;
    private String lang;
    private String text;

    public Suggestions(){}
//
//    public Eval(String lang, String text) {
//        this.lang = lang;
//        this.text = text;
//    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
