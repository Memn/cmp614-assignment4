package tr.edu.hacettepe.summary;

import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * @author Memn
 * @date 23.12.2017
 */

public class Evaluation {


    private final String docset;
    private final int size;
    private final char summarizer;
    private final char selector;

    private double truePositives = 0;
    private double manualSummaryCounts = 0;
    private double summaryCounts = 0;


    Evaluation(File file, Summary summary) throws IOException {
        this.size = summary.getSize();
        this.docset = summary.getDocset();
        this.selector = summary.getSelector();
        this.summarizer = file.getParentFile().getName().charAt(file.getParentFile().getName().length() - 1);
        Summary manual = new Summary(summary, file, summarizer);
        summaryCounts = summary.getSummary().size();
        manualSummaryCounts = manual.getSummary().size();
        measure(manual, summary);
    }

    private void measure(Summary manual, Summary summary) {
        Set<String> manualSummary = manual.getSummary();
        for (String sentence : summary.getSummary()) {
            if (manualSummary.contains(sentence.trim())) {
                truePositives++;
            }
        }
    }

    @Override
    public int hashCode() {
        int result = docset.hashCode();
        result = 31 * result + size;
        result = 31 * result + (int) summarizer;
        result = 31 * result + (int) selector;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Evaluation that = (Evaluation) o;

        if (size != that.size) {
            return false;
        }
        if (summarizer != that.summarizer) {
            return false;
        }
        if (selector != that.selector) {
            return false;
        }
        return docset.equals(that.docset);
    }

    @Override
    public String toString() {
        return String.format("Evaluation{docset='%s%s%s', size=%d, recall=%.4f, precision=%.4f}", docset, selector, summarizer, size, recall(), precision());
    }

    public double recall() {
        return truePositives / manualSummaryCounts;
    }

    public double precision() {
        return truePositives / summaryCounts;
    }

    public double truePositives() {
        return truePositives;
    }

    public double summaryCounts() {
        return summaryCounts;
    }

    public double manualSummaryCounts() {
        return manualSummaryCounts;
    }
}
