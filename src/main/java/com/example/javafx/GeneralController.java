package com.example.javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.control.ListView;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneralController {
    public static final String ENG_VIE_PATH = "src/main/resources/File/anhviet109K.txt";
    public static final String VIE_ENG_PATH = "src/main/resources/File/vietanh.txt";

    @FXML
    private Pane resultSearchPane;
    @FXML
    protected Button searchButton;

    @FXML
    protected TextField searchTextField;
    @FXML
    private Pane wordResult;

    private List<String> searchVieWord(String keyword) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(VIE_ENG_PATH));
        String line;

        // Tạo regex pattern với từ khóa
        Pattern pattern = Pattern.compile("\\b" + keyword + "\\b", Pattern.CASE_INSENSITIVE);

        List<String> resultList = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                resultList.add(matcher.group());
            }
        }
        reader.close();

        return resultList;
    }

    private List<String> searchEngWord(String keyword) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(ENG_VIE_PATH));
        String line;

        // Tạo regex pattern với từ khóa
        Pattern pattern = Pattern.compile("\\b" + keyword + "\\b", Pattern.CASE_INSENSITIVE);

        List<String> resultList = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                resultList.add(matcher.group());
            }
        }
        reader.close();
        return resultList;
    }

    public List<String> savedList = new ArrayList<>();
    @FXML
    private Button saveButton;
    @FXML
    private TextField searchField;

    @FXML
    private ListView<String> resultListView;
    @FXML
    public void handleSaveButton() {
        String selectedWord = resultListView.getSelectionModel().getSelectedItem();
        if (selectedWord != null && !savedList.contains(selectedWord)) {
            savedList.add(selectedWord);
            updateSavedListView();
        }
    }
    private void updateSavedListView() {
        ObservableList<String> observableSavedList = FXCollections.observableArrayList(savedList);
        // Hiển thị danh sách từ đã lưu trong savedList
        // Ví dụ:
        // savedListView.setItems(observableSavedList);
        // savedListView.refresh(); // Cập nhật hiển thị
    }

    @FXML
    private Button removeButton;

    public static void removeWordFromEngFile(String wordToRemove) {
        List<String> lines = new ArrayList<>();

        // Đọc nội dung tệp vào danh sách lines
        try (BufferedReader reader = new BufferedReader(new FileReader(ENG_VIE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Xóa từ cần xóa khỏi danh sách lines
        lines.removeIf(line -> line.equalsIgnoreCase(wordToRemove));

        // Ghi lại nội dung mới vào tệp
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ENG_VIE_PATH))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleRemoveButton(){
        String wordToDelete = resultSearchPane.getText().trim(); // Lấy từ cần xóa từ TextField

        if (!wordToDelete.isEmpty()) {
            removeWordFromEngFile(wordToDelete);
        }
    }




}
