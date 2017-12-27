package tr.edu.hacettepe.summary;

import tr.edu.hacettepe.util.FileUtil;
import tr.edu.hacettepe.util.MMR;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Memn
 * @date 22.12.2017
 */

public class SummaryEvaluator {

    private final Path extractsFolder;

    public SummaryEvaluator(Path extractsPath) {

        this.extractsFolder = extractsPath;
    }

    public Set<Evaluation> evaluate(Summarizer summarizer) throws IOException {
        // calculate evaluations for each summarizer in extract path

        File[] folders = FileUtil.foldersStartWith(extractsFolder, summarizer.getDocset());
        assert folders != null : String.format("No such folders in Path:%s starting with: %s", extractsFolder.toString(), summarizer.getDocset());

        Set<Evaluation> evaluations = new HashSet<>();
        for (File folder : folders) {

            File[] files = folder.listFiles((dir, name) -> isEvaluationFile(name));
            assert files != null : "No such files named '200e' or '400e' in Path: " + folder.getAbsolutePath();


            for (File file : files) {
                Summary summary = summarizer.maxWordCount(toCount(file)).summarize();
                evaluations.add(new Evaluation(file, summary));
            }
        }

        return evaluations;
    }

    private int toCount(File file) {
        return Integer.parseInt(file.getName().substring(0, file.getName().length() - 1));
    }

    private boolean isEvaluationFile(String name) {
        return name.equals("200e") || name.equals("400e");
    }
}
