// License: LGPL-3.0 License (c) find-sec-bugs
package unsafe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.lang.System.out;

public class InformationExposure {
    public void vulnerableErrorMessage1(String uri) {
        try {
            Connection conn = DriverManager.getConnection(uri);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace(out); // Normal Priority
        }
    }

    public void vulnerableErrorMessage2(HttpServletRequest req, HttpServletResponse resp) {
        try {
            OutputStream out = resp.getOutputStream();
        } catch (Exception e) {
            e.printStackTrace(out); // Normal Priority
        }
    }

    public void vulnerableErrorMessage3() {
        FileInputStream fis = null;
        try {
            String fileName = "fileName";
            fis = new FileInputStream(fileName);
        } catch (Exception e) {
            e.printStackTrace(); // Low Priority
        }
    }
}
