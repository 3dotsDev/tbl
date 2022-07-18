module tb.bsc.translatorcheck {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;



    opens tb.bsc.translatorcheck.logic.dto to com.fasterxml.jackson.databind, javafx.base;
    opens tb.bsc.translatorcheck to javafx.base, javafx.fxml;
    exports tb.bsc.translatorcheck;
    exports tb.bsc.translatorcheck.logic;
    opens tb.bsc.translatorcheck.logic to javafx.base, javafx.fxml;
    exports tb.bsc.translatorcheck.fxcontroller;
    opens tb.bsc.translatorcheck.fxcontroller to javafx.base, javafx.fxml;
    exports tb.bsc.translatorcheck.logic.helper;
    opens tb.bsc.translatorcheck.logic.helper to javafx.base, javafx.fxml;
}