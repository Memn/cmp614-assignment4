package tr.edu.hacettepe.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Memn
 * @date 26.12.2017
 */

public class FileUtil {
    public static int numberOfLines4Name(Path folder, String fileName) throws IOException {
        return FileUtil.numLines(findPath4Name(folder, fileName));

    }

    public static Path findPath4Name(Path folder, String fileName) throws IOException {
        Optional<Path> first = Files.list(folder).filter(p -> p.toFile().getName().equals(fileName)).findFirst();
        assert first.isPresent() : String.format("%s does not exist in path: %s", fileName, folder.toString());
        return first.get();
    }

    private static int numLines(Path sentencesPath) throws IOException {
        int size = 0;
        try (LineIterator lineIterator = FileUtils.lineIterator(sentencesPath.toFile())) {
            while (lineIterator.hasNext()) {
                lineIterator.next();
                size++;
            }
        }
        return size;
    }

    public static File[] foldersStartWith(Path basePath, String startingWith) {
        return basePath.toFile().listFiles((dir, name) -> name.startsWith(startingWith));
    }

    public static Set<String> sentencesOf(File file) throws IOException {
        Set<String> set = new HashSet<>();
        Jsoup.parse(file, "UTF-8").getElementsByTag("s").forEach(element -> set.add(element.text()));
        return set;

    }

    public static List<String> readAllLinesInFolder(Path folder, String fileName) throws IOException {
        Path sentencesPath = FileUtil.findPath4Name(folder, fileName);
        return Files.readAllLines(sentencesPath);


    }
}
