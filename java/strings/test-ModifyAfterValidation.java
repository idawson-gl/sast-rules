// License: LGPL-3.0 License (c) find-sec-bugs
package strings;

import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModifyAfterValidation {

    public String modifyDanger(String str) {
        String s = Normalizer.normalize(str, Normalizer.Form.NFKC);
        Pattern pattern = Pattern.compile("<script>");
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
            throw new IllegalArgumentException("Invalid input");
        }
        s = s.replaceAll("[\\p{Cn}]", ""); // modified after validation
        return s;
    }

    public String modifyDanger2(String str) {
        String s = Normalizer.normalize(str, Normalizer.Form.NFKC);
        Pattern pattern = Pattern.compile("<script>");
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
            throw new IllegalArgumentException("Invalid input");
        }
        s = s.replace("[\\p{Cn}]", ""); // modified after validation
        return s;
    }
}
