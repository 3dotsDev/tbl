package tb.bsc.translatorcheck.dto;


public class Eval {
    private String lang;
    private String text;

    public Eval(){}

    public Eval(String lang, String text) {
        this.lang = lang;
        this.text = text;
    }

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
}
