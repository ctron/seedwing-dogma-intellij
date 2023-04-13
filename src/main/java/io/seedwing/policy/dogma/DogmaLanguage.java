package io.seedwing.policy.dogma;

import com.intellij.lang.Language;

public class DogmaLanguage extends Language {

    public static final DogmaLanguage INSTANCE = new DogmaLanguage();

    private DogmaLanguage () {
        super("Dogma");
    }

}
