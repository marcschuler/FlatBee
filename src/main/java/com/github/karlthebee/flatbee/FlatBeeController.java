package com.github.karlthebee.flatbee;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * The controller class for the demo
 * 
 * @author karlthebee@gmail.com
 * @version 1.0
 */
public class FlatBeeController {

    private static final Color[] colors = new Color[] { Color.rgb(26, 188, 156), Color.rgb(155, 89, 182),
             Color.rgb(241, 196, 15), Color.rgb(230, 126, 34), Color.rgb(231, 76, 60),
            Color.rgb(236, 240, 241), Color.rgb(149, 165, 166) };

    private String style = "#2ecc71";

    private String resource;

    @FXML
    private Pane root;
    @FXML
    private Pane colorBox;

    @FXML
    private void initialize() {
        Path p;
        try {
            p = Paths.get(getClass().getResource("FlatBee.css").toURI());
            resource = new String(Files.readAllBytes(p), StandardCharsets.UTF_8);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        /*
         * Until i know an better way to set an new -color, i have to create an
         * temporary file and override the color value :/ That seems wrong, but
         * i have to look for a better way anytime...
         */
        for (Color color : colors) {
            Button button = new Button(toRGBCode(color));
            button.setOnAction((event) -> {
                // replace the gui color
                String rgb = toRGBCode(color);
                System.out.println("Selected main color " + rgb);
                // write a temp file with this css file but change the color
                try {
                    Path p2 = Files.createTempFile("flatbee-style-" + rgb, ".css");
                    String s = resource.replaceAll(style, rgb);
                    Files.write(p2, s.getBytes());
                    root.getStylesheets().clear();
                    root.getStylesheets().add(p2.toUri().toURL().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            button.setStyle("-fx-background-color : " + toRGBCode(color) + ";");
            colorBox.getChildren().add(button);

        }
    }

    /**
     * @param color
     *            the color to process
     * @return the color as hex version, for example : #123456, #ABCDEF,
     *         #CAFEBABE, ...
     */
    public static String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X", (int) (color.getRed() * 255), (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    @FXML
    private void openGithub() {
        openLink("https://www.github.com/karlthebee/flatbee");
    }

    @FXML
    private void openTwitter() {
        openLink("https://www.twitter.com/karlthebee");
    }

    @FXML
    private void sendMail() {
        openLink("sendmail://karlthebee@gmail.com");
    }

    private void openLink(String url) {
        if (!Desktop.isDesktopSupported()) {
            System.err.println("Desktop is not supported, please enter link manually : " + url);
            return;
        }

        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.browse(URI.create(url));
        } catch (IOException e) {
            System.err.println("Could not open link, please enter link manually : " + url);
        }
    }
}
