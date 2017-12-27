package tr.edu.hacettepe.summary;

import org.apache.commons.lang3.StringUtils;
import tr.edu.hacettepe.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * @author Memn
 * @date 21.12.2017
 */

public class Summary {

    private String docset;
    private String type;
    private int size;
    private String docref;
    private char selector;
    private char summarizer;
    private Set<String> summary;

    private int actualSummary;


    public Summary(Summary summary, File file, char summarizer) throws IOException {
        this(summary.docset, summary.type, summary.size, summary.docref, summary.selector, summarizer, FileUtil.sentencesOf(file));
    }

    public Summary(String docset,
                   String type,
                   int size,
                   String docref,
                   char selector,
                   char summarizer,
                   Set<String> summary) {
        this.docset = docset;
        this.type = type;
        this.size = size;
        this.docref = docref;
        this.selector = selector;
        this.summarizer = summarizer;
        this.summary = summary;
        this.actualSummary = countWords(summary);
    }

    private int countWords(Set<String> summary) {
        int count = 0;
        for (String sentence : summary) {
            count += StringUtils.split(sentence).length;
        }
        return count;
    }

    public int getActualSummary() {
        return actualSummary;
    }

    @Override
    public String toString() {
        return String.format("<SUM \n" +
                        " DOCSET='%s'\n" +
                        " TYPE='%s'\n" +
                        " SIZE='%d'\n" +
                        " DOCREF='%s'\n" +
                        " SELECTOR='%s'\n" +
                        " SUMMARIZER='%s'>\n" +
                        " summary='%s'</SUM>", docset, type, size, docref, selector, summarizer,
                StringUtils.join(summary.toArray(), '\n'));
    }

    public String getDocset() {
        return docset;
    }

    public int getSize() {
        return size;
    }

    public Set<String> getSummary() {
        return summary;
    }

    public char getSelector() {
        return selector;
    }
}
