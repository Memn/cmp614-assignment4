package tr.edu.hacettepe.util;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.File;
import java.util.Set;

/**
 * @author Memn
 * @date 26.12.2017
 */

public class FileUtilTest {
    @Test
    public void sentencesOf() throws Exception {
        File file = new File("C:\\Users\\MehmetEminMumcu\\Desktop\\memn\\hacettepe\\cmp614\\assignments\\assignment4\\cmp614-assignment4\\src\\main\\resources\\extracts\\d061jb\\200e");
        Set<String> sentences = FileUtil.sentencesOf(file);
        System.out.println("size: " + sentences.size());
        System.out.println(StringUtils.join(sentences.toArray(), '\n'));
    }

}