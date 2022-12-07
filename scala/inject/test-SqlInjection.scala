// License: LGPL-3.0 License (c) find-sec-bugs
package injection

import org.hibernate.Session
import javax.jdo.Extent
import javax.jdo.JDOHelper
import javax.jdo.PersistenceManager
import javax.jdo.PersistenceManagerFactory
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import java.util
import org.springframework.jdbc.core.JdbcTemplate


object SqlInjection {
  private val CLIENT_FIELDS = "client_id, client_secret, resource_ids, scope, " + "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, " + "refresh_token_validity, additional_information, autoapprove"
  private val DEFAULT_INSERT_STATEMENT = "insert into oauth_client_details (" + CLIENT_FIELDS + ")" + "values (?,?,?,?,?,?,?,?,?,?,?)"
  private val pmfInstance = JDOHelper.getPersistenceManagerFactory("transactions-optional")

  def getPM = pmfInstance.getPersistenceManager
}

class SqlInjection {
  private val jdbcTemplate = null

  class UserEntity {
    private var id = null
    private var test = null

    def getTest = test

    def setTest(test: Nothing): Unit = {
      this.test = test
    }

    def getId = id

    def setId(id: Nothing): Unit = {
      this.id = id
    }
  }

  def testJdoQueries(input: Nothing): Unit = {
    val pm = SqlInjection.getPM
    pm.newQuery("select * from Users where name = " + input)
    pm.newQuery("sql", "select * from Products where name = " + input)
    // Test for false positive
    pm.newQuery("select * from Config")
    val query = "select * from Config"
    pm.newQuery(query)
    pm.newQuery("sql", query)
  }

  def testJdoQueriesAdditionalMethodSig(input: Nothing): Unit = {
    val pm = SqlInjection.getPM
    pm.newQuery(classOf[SqlInjection#UserEntity], new Nothing, "id == " + input) // Injection?

    pm.newQuery(classOf[SqlInjection#UserEntity], new Nothing, "id == 1")
    pm.newQuery(classOf[SqlInjection#UserEntity], "id == " + input)
    pm.newQuery(classOf[SqlInjection#UserEntity], "id == 1")
    pm.newQuery(null.asInstanceOf[Nothing], "id == " + input)
    pm.newQuery(null.asInstanceOf[Nothing], "id == 1")
  }

  def testHibernate(input: Nothing): Unit = {
    val session = null
    val cb = session.getCriteriaBuilder
    val query = null
    // should not be reported
    session.createQuery(query)
    // should be reported
    session.createQuery(input)
    val cq = cb.createQuery(classOf[Nothing])
  }

  def good(clientDetails: Nothing): Unit = {
    val statementUsingConstants = "insert into oauth_client_details (" + SqlInjection.CLIENT_FIELDS + ")" + "values (?,?,?,?,?,?,?,?,?,?,?)"
    jdbcTemplate.update(statementUsingConstants, clientDetails)
  }

  def good2(clientDetails: Nothing): Unit = {
    jdbcTemplate.update(SqlInjection.DEFAULT_INSERT_STATEMENT, clientDetails)
  }

  def bad(clientDetails: Nothing): Unit = {
    val stmtUsingFuncParam = "test" + clientDetails + "test"
    jdbcTemplate.update(stmtUsingFuncParam, clientDetails)
  }

  def badInline(clientDetails: Nothing): Unit = {
    jdbcTemplate.update("test" + clientDetails + "test", clientDetails)
  }
}
