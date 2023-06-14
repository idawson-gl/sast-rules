// License: LGPL-3.0 License (c) find-sec-bugs
package inject;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathTraversal {
    static final String safefinalString = "SAFE";

    // DETECTS: PATH_TRAVERSAL_IN
    public static void unsafe(String[] args) throws IOException, URISyntaxException {
        String input = args.length > 0 ? args[0] : "../../../../etc/password\u0000";
        new File(input);
        new File("test/" + input, "misc.jpg");
        new RandomAccessFile(input, "r");
        new File(new URI(args[0]));

        new FileReader(input);
        new FileInputStream(input);

        // false positive test
        new RandomAccessFile("safe", args[0]);
        new FileWriter("safe".toUpperCase());
        new File(new URI("safe"));

        File.createTempFile(input, "safe");
        File.createTempFile("safe", input);
        File.createTempFile("safe", input, new File("safeDir"));
        new File(safefinalString);
    }

    // nio path traversal, DETECTS: PATH_TRAVERSAL_IN
    public void loadFile(String path) {
        Paths.get(path);
        Paths.get(path,"foo");
        Paths.get(path,"foo", "bar");
        Paths.get("foo", path);
        Paths.get("foo", "bar", path);

        Paths.get("foo");
        Paths.get("foo","bar");
        Paths.get("foo","bar", "allsafe");

    }

    // DETECTS: PATH_TRAVERSAL_IN
    public void tempDir(String input) throws IOException {
        Path p = Paths.get("/");

        Files.createTempFile(p,input,"");
        Files.createTempFile(p,"",input);
        Files.createTempFile(input,"");
        Files.createTempFile("", input);

        Files.createTempDirectory(p,input);
        Files.createTempDirectory(input);

        ClassLoader loader = getClass().getClassLoader();
        InputStream stream1 = loader.getResourceAsStream(input);
        InputStream stream2 = getClass().getClassLoader().getResourceAsStream(input);

        String taintStr = "test" + input;
        InputStream stream3 = loader.getResourceAsStream(taintStr);
        InputStream stream4 = getClass().getClassLoader().getResourceAsStream(taintStr);
    }

    // DETECTS: PATH_TRAVERSAL_OUT
    public void pathTraversalWrite(String input) throws IOException {
        new FileWriter(input);
        new FileWriter(input, true);
        new FileOutputStream(input);
        new FileOutputStream(input, true);
    }
}
