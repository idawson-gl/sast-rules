// License: LGPL-3.0 License (c) find-sec-bugs
package strings;

import java.net.IDN;
import java.net.URI;
import java.text.Normalizer;
import java.net.URISyntaxException;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.regex.Pattern;

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

    public static void stringNormalizationSuite() throws URISyntaxException {
        String host1 = "https://www.evil.c\u2100.microsoft.com";
        String host2 = "https://www.evil.c\u2101.microsoft.com";
        String host3 = "https://www.evil.c\u2105.microsoft.com";
        String host4 = "https://www.evil.c\u2106.microsoft.com";
        String host5 = "https://www.evil.c\u210E.microsoft.com";
        String host6 = "https://www.evil.c\u2121.microsoft.com";
        String host7 = "https://\u2116dejs.org";
        String host8 = "https://montrehac\u212A.ca";
        String host9 = "https://evil.ca\uff0f.microsoft.com";

        for(String h : Arrays.asList(host1,/*host2,host3,host4,host5,host6,*/ host7,host8,host9)) {
            System.out.println("~~~~~");
            System.out.println("Original URL : "+h);
            System.out.println("");
            System.out.println("Normalizer NFKC: "+Normalizer.normalize(h, Normalizer.Form.NFKC)); //RISKY!
            System.out.println("Normalizer NFKD: "+Normalizer.normalize(h, Normalizer.Form.NFKD)); //RISKY!
            System.out.println("Normalizer NFC: "+Normalizer.normalize(h, Normalizer.Form.NFC)); //RISKY!
            System.out.println("Normalizer NFD: "+Normalizer.normalize(h, Normalizer.Form.NFD)); //RISKY!
            System.out.println("URL.toASCIIString(): "+new URI(h).toASCIIString()); //RISKY!
            System.out.println("URL.toString(): "+new URI(h).toString());
            System.out.println("IDN.toASCII(): "+ IDN.toASCII(h)); //RISKY!
        }
    }
}
