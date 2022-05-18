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



    opens tb.bsc.translatorcheck.logic.dto to com.fasterxml.jackson.databind;
    opens tb.bsc.translatorcheck to javafx.fxml;
    exports tb.bsc.translatorcheck;
}