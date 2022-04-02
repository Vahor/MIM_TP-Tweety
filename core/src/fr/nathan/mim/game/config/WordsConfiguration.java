package fr.nathan.mim.game.config;

import java.util.List;

public class WordsConfiguration {

    private List<String > words;

    // parser
    public WordsConfiguration() {
    }

    public WordsConfiguration(List<String> words) {
        this.words = words;
    }

    public List<String> getWords() {
        return words;
    }
}
