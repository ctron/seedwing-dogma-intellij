package io.seedwing.policy.dogma.syntax.lexer;

import java.util.StringJoiner;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.textmate.language.syntax.lexer.TextMateScope;

import com.intellij.psi.tree.IElementType;

import io.seedwing.policy.dogma.DogmaLanguage;

public class DogmaElementType extends IElementType {
    private final TextMateScope scope;

    public DogmaElementType(@NotNull TextMateScope scope) {
        super("DOGMA", DogmaLanguage.INSTANCE, false);
        this.scope = scope;
    }

    @NotNull
    public TextMateScope getScope() {
        return this.scope;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", DogmaElementType.class.getSimpleName() + "[", "]")
                .add("scope=" + scope)
                .toString();
    }

}
