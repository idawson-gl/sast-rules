// License: LGPL-3.0 License (c) find-sec-bugs
package strings;

import java.net.IDN;
import java.net.URI;
import java.text.Normalizer;

public class ImproperUnicode {

    public boolean dangerToUpperEquals(String s) {
        return s.toUpperCase().equals("TEST");
    }

    public boolean dangerToUpperEqualIgnoreCase(String s) {
        return s.toUpperCase().equalsIgnoreCase("TEST");
    }

    public int dangerToUpperIndexOf(String s) {
        return s.toUpperCase().indexOf("T");
    }

    public boolean dangerToLowerEquals(String s) {
        return s.toLowerCase().equals("test");
    }

    public boolean dangerToLowerEqualIgnoreCase(String s) {
        return s.toLowerCase().equalsIgnoreCase("test");
    }

    public int dangerToLowerIndexOf(String s) {
        return s.toLowerCase().indexOf("t");
    }

    public String dangerURI(URI uri) {
        return uri.toASCIIString();
    }

    public String dangerIDN(String input) {
        return IDN.toASCII(input);
    }

    public boolean dangerNormalize(String s) {
        return Normalizer.normalize(s.toUpperCase(), Normalizer.Form.NFKC).equals("ADMIN");
    }

}
