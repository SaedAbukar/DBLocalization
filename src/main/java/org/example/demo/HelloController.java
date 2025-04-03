package org.example.demo;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class HelloController {
    @FXML
    private Label greeting;

    @FXML
    private ComboBox<String> langSelector;

    @FXML
    private ListView<String> jobList;

    @FXML
    private TextField keyField;

    @FXML
    private TextField translationField;

    @FXML
    private Button addTranslationButton;

    TrDao dao = new TrDao();
    String lang = "en_US";

    @FXML
    public void initialize() {
        Locale locale = new Locale(lang);
        ResourceBundle rb = ResourceBundle.getBundle("messages", locale);

        langSelector.getItems().addAll("English", "Spanish", "French", "中文");
        langSelector.setValue("English");
        langSelector.setOnAction(event -> changeLang());

        greeting.setText(rb.getString("greeting"));
        keyField.setPromptText(rb.getString("key"));
        translationField.setPromptText(rb.getString("translation"));
        addTranslationButton.setText(rb.getString("addTranslation"));

        addTranslationButton.setOnAction(event -> {
            String keyName = keyField.getText().trim();
            String translationText = translationField.getText().trim();
            String langCode = getLang(langSelector.getValue());

            if (!keyName.isEmpty() && !translationText.isEmpty()) {
                dao.add(keyName, langCode, translationText);
                keyField.clear();
                translationField.clear();
                jobList.getItems().clear();
                jobList.getItems().addAll(dao.getLangCode(langCode));
            }
        });
    }

    @FXML
    private void changeLang() {
        String selectedLang = langSelector.getValue();
        String langCode = getLang(selectedLang);

        switch (langCode) {
            case "en":
                lang = "en_US";
                break;
            case "es":
                lang = "es_ES";
                break;
            case "fr":
                lang = "fr_FR";
                break;
            case "ja":
                lang = "ja_JP";
        }
    }


    private String getLang(String lang) {
        switch (lang) {
            case "English":
                return "en";
            case "Spanish":
                return "es";
            case "French":
                return "fr";
            case "中文":
                return "zh";
            default:
                return "en";
        }
    }
}