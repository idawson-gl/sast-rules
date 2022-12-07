// License: LGPL-3.0 License (c) find-sec-bugs
import org.springframework.context.expression.MapAccessor
import org.springframework.expression.Expression
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.expression.spel.support.StandardEvaluationContext
import org.springframework.util.PropertyPlaceholderHelper
import org.springframework.web.servlet.View
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.util._


class SpelView(val template: String) extends View {
  this.context.addPropertyAccessor(new Nothing)
  this.resolver = (name: String) => {
    try {
      val expression = parser.parseExpression(name) //BOOM!
      val value = expression.getValue(context)
      if (value == null) null
      else value.toString
    } catch {
      case e: Nothing =>
        null
    }
  }
  final private val parser = new SpelExpressionParser()
  final private val context = new StandardEvaluationContext()

  def getContentType = "text/html"

  @throws[Exception]
  def render(model: Map[String, _], request: HttpServletRequest, response: HttpServletResponse): Unit = {
    val map = new HashMap[String, Object](model)
    val path = ServletUriComponentsBuilder.fromContextPath(request).build.getPath
    map.put("path", if (path == null) ""
    else path)
    context.setRootObject(map)
    val helper = new PropertyPlaceholderHelper("${", "}")
    val result = helper.replacePlaceholders(template, resolver)
    response.setContentType(getContentType)
    response.getWriter.append(result)
  }
}
