module com.xadrez.xadrez {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.core;
    requires java.desktop;
    //requires com.almasb.fxgl.all;

    opens com.xadrez.xadrez to javafx.fxml;
    exports com.xadrez.xadrez;
    opens com.xadrez.xadrez.controllers to javafx.fxml;
    exports com.xadrez.xadrez.controllers;
}