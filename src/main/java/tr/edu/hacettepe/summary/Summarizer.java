package tr.edu.hacettepe.summary;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Memn
 * @date 21.12.2017
 */

public class Summarizer {

    private static final String SENTENCES_FILENAME = "sents.txt";


    private final List<String> sentences;
    private final LinkedHashSet<Integer> order;

    private String docset;
    private String type;
    private String docref;
    private char selector;
    private char summarizer;
    private int maxWordCount = 200;

    public Summarizer(Path folder, LinkedHashSet<Integer> order, List<String> sentences) throws IOException {

        this.sentences = sentences;
        String filename = folder.toFile().getName();
        this.docset = filename.substring(0, filename.length() - 1);
        this.type = "";
        this.docref = StringUtils.join(folder.toFile().list((dir, name) -> name.contains(".S")), ' ');
        this.selector = filename.charAt(filename.length() - 1);
        this.summarizer = '-';
        this.order = order;
    }

    Summarizer maxWordCount(int maxWordCount) {
        if (maxWordCount > 0) {
            this.maxWordCount = maxWordCount;
        }
        return this;
    }


    Summary summarize() throws IOException {
        Set<String> summary = new HashSet<>();
        int summaryWordCount = 0;
        for (Integer choice : order) {
            String s = sentences.get(choice);
            if (!summary.contains(s)) {
                summary.add(s);
                summaryWordCount += StringUtils.split(s).length;
            }

            if (summaryWordCount >= maxWordCount) {
                break; // reached out to limit
            }
        }

        return new Summary(docset, type, maxWordCount, docref, selector, summarizer, summary);

    }

    public String getDocset() {
        return docset;
    }
}
