package io.seedwing.policy.dogma;

import javax.swing.*;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.intellij.openapi.fileTypes.LanguageFileType;

public class DogmaFileType extends LanguageFileType {

    public static final DogmaFileType INSTANCE = new DogmaFileType();

    private DogmaFileType() {
        super(DogmaLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "Dogma";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Dogma";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return ".dog";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return null;
    }
}
