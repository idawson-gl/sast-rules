// License: LGPL-3.0 License (c) find-sec-bugs
import org.apache.struts.validator.ValidatorForm

class FormValidate extends ValidatorForm {
  private var name = null
  private var email = null

  def getName = name

  def setName(name: String): Unit = {
    this.name = name
  }

  def getEmail = email

  def setEmail(email: String): Unit = {
    this.email = email
  }
}
