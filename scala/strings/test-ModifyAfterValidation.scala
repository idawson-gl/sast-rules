// License: LGPL-3.0 License (c) find-sec-bugs
import java.text.Normalizer
import java.util.regex.Matcher
import java.util.regex.Pattern


class ModifyAfterValidation {
  def modifyDanger(str: String) = {
    var s = Normalizer.normalize(str, Normalizer.Form.NFKC)
    val pattern = Pattern.compile("<script>")
    val matcher = pattern.matcher(s)
    if (matcher.find) throw new Nothing("Invalid input")
    s = s.replaceAll("[\\p{Cn}]", "") // modified after validation

    s
  }

  def modifyDanger2(str: String) = {
    var s = Normalizer.normalize(str, Normalizer.Form.NFKC)
    val pattern = Pattern.compile("<script>")
    val matcher = pattern.matcher(s)
    if (matcher.find) throw new Nothing("Invalid input")
    s = s.replace("[\\p{Cn}]", "")
    s
  }
}
