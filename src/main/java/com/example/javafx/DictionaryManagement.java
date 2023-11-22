package com.example.javafx;


import java.io.*;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DictionaryManagement extends Dictionary {

    /**
     * insert new word in dictionary.
     * @param newDictionary create new dictionary.
     */
    public void insertFromCommandline(Dictionary newDictionary) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("No | English | Vietnamese");
        int num = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < num; i++) {
            System.out.print("Nhập từ tiếng anh: ");
            String wordTarget = scanner.next();

            System.out.print("Nghĩa của từ: ");
            String wordExplain = scanner.next();

            Word word = new Word(wordTarget, wordExplain);
            newDictionary.addWord(word);
        }
    }

    /**
     * insert word in file.
     * @param newDictionary Dictionary.
     * @param filename file which inserted word in.
     */
    public void insertFromFile(Dictionary newDictionary, String filename) {
        try {
            File file = new File(filename); // thay file name thành dictionary.txt
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                // tách các từ trong file ra thành 2 phần tiếng việt, tiếng anh
                String[] parts = line.split("\t");

                if (parts.length == 2) {
                    String wordTarget = parts[0];
                    String wordExplain = parts[1];
                    Word newWord = new Word(wordTarget, wordExplain);
                    newDictionary.addWord(newWord);
                }
            }
            scanner.close();
        } catch (FileNotFoundException error) {
            System.out.println("Không tìm thấy tệp " + filename);
        }
    }

    public void dictionaryLookUp(Dictionary newDictionary) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập từ cần tìm: ");
        String lookUpWord = scanner.nextLine();

        Word word =  newDictionary.getWords().get(lookUpWord);

        if (word != null) {
            System.out.println("Từ: " + word.word_target);
            System.out.println("Nghĩa của từ: " + word.word_explain);
        } else {
            System.out.print("Không tìm thấy!");
        }
    }


    public void modify(Dictionary newDictionary, String wordTarget, Word modWord){
        Map<String, Word> wordMap = newDictionary.getWords();
        if(wordMap.containsKey(wordTarget)) {
            wordMap.put(wordTarget, modWord);
            System.out.println("Từ đã được sửa thành công.");
        }
        else {
            System.out.println("Không tìm thấy từ.");
        }
    }
    public void removeWord(Dictionary newDictionary, String wordTarget) {
        Map<String, Word> wordMap = newDictionary.getWords();
        if (wordMap.containsKey(wordTarget)) {
            wordMap.remove(wordTarget);
            System.out.println("Từ đã được xóa.");
        }
        else {
            System.out.println("Không tìm thấy từ.");
        }
    }

    public static void searchVocabulary(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        StringBuilder text = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            text.append(line).append("\n");
        }
        reader.close();

        // Regex pattern để tìm các từ vựng có định dạng @a, @a b c
        Pattern pattern = Pattern.compile("@(\\w+)(?:\\s+\\w+)*\\s+/(.+?)/", Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            String word = matcher.group(1);
            String definition = matcher.group(2);
            System.out.println("Word: " + word);
            System.out.println("Definition: " + definition);
            System.out.println(); // In một dòng trống giữa từng cặp từ vựng và định nghĩa
        }
    }

}
