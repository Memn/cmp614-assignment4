package tr.edu.hacettepe.util;

import org.apache.mahout.math.Vector;

/**
 * @author Memn
 * @date 16.12.2017
 */

public class VectorPrintUtil {
    public static String toString(Vector v) {

        StringBuilder result = new StringBuilder();
        result.append('{');
        for (Vector.Element element : v.nonZeroes()) {
            result.append(element.index());
            result.append(':');
            result.append(String.format("%.7f", element.get()));
            result.append(',');
        }

        if (result.length() > 1) {
            result.setCharAt(result.length() - 1, '}');
        } else {
            result.append('}');
        }
        return result.toString();
    }
}
