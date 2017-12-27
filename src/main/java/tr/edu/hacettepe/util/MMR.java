package tr.edu.hacettepe.util;

import org.apache.mahout.math.Matrix;
import org.apache.mahout.math.Vector;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Memn
 * @date 27.12.2017
 */

public class MMR {
    private final Vector ranks;
    private final Matrix similarities;

    public MMR(Vector ranks, Matrix similarities) {

        this.ranks = ranks;
        this.similarities = similarities;
    }

    public LinkedHashSet<Integer> marginalOrder() {
        LinkedHashSet<Integer> set = new LinkedHashSet<>();
        LinkedHashMap<Integer, Double> ranksSortedByScore = sortedRanks();
        while (true) {
            if (ranksSortedByScore.isEmpty()) {
                break;
            } else {
                int i = calculateMMRs(ranksSortedByScore, set);
                assert i != -1 : "Something gone really bad with the MMR calculation.";
                set.add(i);
                ranksSortedByScore.remove(i);
            }
        }

        return set;
    }

    private int calculateMMRs(LinkedHashMap<Integer, Double> ranksSortedByScore,
                              LinkedHashSet<Integer> set) {
        double maxIrrelevance = Double.NEGATIVE_INFINITY;
        int maxRelevanceIndex = -1;

        for (Map.Entry<Integer, Double> entry : ranksSortedByScore.entrySet()) {
            int i = entry.getKey();
            double maxSimilarityIn = maxSimilarityIn(i, set);
            double irrelevance = entry.getValue() - maxSimilarityIn;
            if (maxIrrelevance < irrelevance) {
                maxIrrelevance = irrelevance;
                maxRelevanceIndex = i;
            }
        }
        return maxRelevanceIndex;
    }

    private double maxSimilarityIn(Integer index, LinkedHashSet<Integer> set) {
        double max = Double.NEGATIVE_INFINITY;
        for (Integer i : set) {
            double sim = similarities.get(i, index);
            if (max < sim) {
                max = sim;
            }
        }

        return max;
    }

    private LinkedHashMap<Integer, Double> sortedRanks() {
        Map<Integer, Double> indexScore = new HashMap<>(ranks.size());
        ranks.all().forEach(element -> indexScore.put(element.index(), element.get()));
        return indexScore.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }
}
