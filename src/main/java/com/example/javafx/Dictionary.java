package com.example.javafx;

import java.util.HashMap;
import java.util.Map;

public class Dictionary {
    public Map<String, Word> words;

    public Dictionary() {}

    public Dictionary(Map<String,Word> words) {
        words = new HashMap<>();
    }

    public Map<String, Word> getWords() {
        return words;
    }

    public void setWords(Map<String, Word> words) {
        this.words = words;
    }

    public void addWord(Word newWord) {
        words.put(newWord.word_target, newWord);
    }
    public int getSize(Map<String, Word> words) {
        return words.size();
    }
}
