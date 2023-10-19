// License: LGPL-3.0 License (c) find-sec-bugs
// scaffold: dependencies=com.amazonaws.aws-java-sdk-simpledb@1.12.187
package inject;
import java.io.IOException;
import java.util.Arrays;

public class CommandInjection {

    public void danger(String cmd) throws IOException {
        Runtime r = Runtime.getRuntime();
        r.exec(cmd);
        r.exec(new String[]{"test"});
        r.exec(new String[]{"bash", cmd},new String[]{});
        r.exec(new String[]{"/bin/sh", "-c", cmd},new String[]{});
    }

    public void danger2(String cmd) {
        ProcessBuilder b = new ProcessBuilder();
        b.command(cmd);
        b.command("test");
        b.command(Arrays.asList("/bin/sh", "-c", cmd));
    }
}
