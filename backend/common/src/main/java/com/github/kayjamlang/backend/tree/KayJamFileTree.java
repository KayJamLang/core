package com.github.kayjamlang.backend.tree;

import com.github.kayjamlang.backend.utils.FileUtils;
import com.github.kayjamlang.core.KayJamFile;
import com.github.kayjamlang.core.KayJamLexer;
import com.github.kayjamlang.core.KayJamParser;
import com.github.kayjamlang.core.exceptions.KayJamLexerException;
import com.github.kayjamlang.core.exceptions.KayJamParserException;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class KayJamFileTree {
    private static final MatchExtensionPredicate kayJamFilePredicate = new MatchExtensionPredicate(".kj");

    private static List<Path> findAllKayJamFiles(Path startPath) throws IOException {
        List<Path> matches = new ArrayList<>();
        Files.walkFileTree(startPath, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
                if (kayJamFilePredicate.test(file)) {
                    matches.add(file);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {
                return FileVisitResult.CONTINUE;
            }
        });

        return matches;
    }


    public static List<KayJamFile> loadFilesFromPath(Path path) throws IOException, KayJamLexerException, KayJamParserException {
        ArrayList<KayJamFile> kayJamFiles = new ArrayList<>();
        List<Path> matches = findAllKayJamFiles(path);
        for (Path kayJamFile : matches) {
            KayJamParser parser = new KayJamParser(new KayJamFile(kayJamFile.toString()), new KayJamLexer(FileUtils.read(kayJamFile.toFile())));
            parser.fillFile();

            kayJamFiles.add(parser.file);
        }

        return kayJamFiles;
    }
}
