package org.example.demo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrDao {
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public List<String> getLangCode(String langCode) {
        Connection conn = MariaDBConnection.getConnection();
        if (conn == null) {
            errorMessage = "There was an error connecting to the database";
            return null;
        }
        ;
        List emplist = new ArrayList();

        try {
            PreparedStatement s = conn.prepareStatement("SELECT Key_name, translation_text FROM translations WHERE Language_code = ?");
            s.setString(1, langCode);
            ResultSet rs = s.executeQuery();

            while (rs.next()) {
                String key_name = rs.getString("Key_name");
                String translation_text = rs.getString("translation_text");
                emplist.add(key_name + " : " + translation_text);
            }
        } catch (SQLException e) {
            errorMessage = "Error fetching all currencies from the database: ";
            e.printStackTrace();
        }
        return emplist;
    }

    public void add(String keyName, String languageCode, String translationText) {
        Connection conn = MariaDBConnection.getConnection();
        if (conn == null) {
            errorMessage = "There was an error connecting to the database";
        }
        try {
            PreparedStatement s = conn.prepareStatement("INSERT INTO translations (Key_name, Language_code, translation_text) VALUES (?, ?, ?)");
            s.setString(1, keyName);
            s.setString(2, languageCode);
            s.setString(3, translationText);
            s.executeUpdate();
        } catch (SQLException e) {
            errorMessage = "Error adding new currency to the database: ";
            e.printStackTrace();
        }
    }
}