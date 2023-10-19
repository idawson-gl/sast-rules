// License: LGPL-3.0 License (c) find-sec-bugs
package form;

import org.apache.struts.validator.ValidatorForm;

public class FormValidate extends ValidatorForm {
    private String name;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
