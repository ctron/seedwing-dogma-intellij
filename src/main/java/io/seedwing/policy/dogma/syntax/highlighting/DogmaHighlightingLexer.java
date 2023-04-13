package io.seedwing.policy.dogma.syntax.highlighting;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.jetbrains.plugins.textmate.bundles.Bundle;
import org.jetbrains.plugins.textmate.bundles.VSCBundle;
import org.jetbrains.plugins.textmate.language.TextMateLanguageDescriptor;
import org.jetbrains.plugins.textmate.language.syntax.TextMateSyntaxTable;
import org.jetbrains.plugins.textmate.language.syntax.lexer.TextMateHighlightingLexer;
import org.jetbrains.plugins.textmate.plist.CompositePlistReader;
import org.jetbrains.plugins.textmate.plist.Plist;
import org.jetbrains.plugins.textmate.plist.PlistReader;

import com.google.common.io.ByteStreams;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManagerCore;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.util.containers.Interner;

import io.seedwing.policy.dogma.DogmaFileType;

public class DogmaHighlightingLexer extends TextMateHighlightingLexer {
    public DogmaHighlightingLexer() {
        super(getTextMateLanguageDescriptor(), 20000);
    }

    private static TextMateLanguageDescriptor getTextMateLanguageDescriptor() {
        try {
            Bundle bundle = new VSCBundle("dogma", getBundlePath().getAbsolutePath());
            TextMateSyntaxTable syntaxTable = new TextMateSyntaxTable();
            Interner<CharSequence> interner = Interner.createWeakInterner();
            PlistReader reader = new CompositePlistReader();
            Plist plist = reader.read(bundle.getGrammarFiles().stream().findFirst().get());
            CharSequence scopeName = syntaxTable.loadSyntax(plist, interner);
            return new TextMateLanguageDescriptor(scopeName, syntaxTable.getSyntax(scopeName));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static File getBundlePath() {
        try {
            IdeaPluginDescriptor plugin = PluginManagerCore.getPlugin(PluginId.getId("io.seedwing.policy.seedwing-dogma"));
            String version = plugin.getVersion();
            File bundleDirectory = new File(plugin.getPluginPath() + "/bundles/" + version);
            if (!bundleDirectory.exists()) {
                InputStream bundle = DogmaFileType.class.getClassLoader().getResourceAsStream("bundle.zip");
                if (bundle != null ) {
                    try (ZipInputStream zip = new ZipInputStream(bundle)) {
                        extract(zip, bundleDirectory);
                    }
                } else {
                    throw new RuntimeException("Failed to locate bundle");
                }
            }
            return bundleDirectory;
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    private static void extract(ZipInputStream zip, File target) throws IOException {
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                File file = new File(target, entry.getName());
                if (!file.toPath().normalize().startsWith(target.toPath())) {
                    throw new IOException("Bad zip entry");
                }
                if (entry.isDirectory()) {
                    file.mkdirs();
                    continue;
                }

                file.getParentFile().mkdirs();
                try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
                    ByteStreams.copy(zip, out);
                }

            }

    }
}
