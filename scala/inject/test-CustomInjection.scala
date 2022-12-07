// License: LGPL-3.0 License (c) find-sec-bugs
// scaffold: dependencies=com.amazonaws.aws-java-sdk-simpledb@1.12.187
package inject

import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import javax.sql.DataSource


class CustomInjection {
  @throws[SQLException]
  def danger(dataSource: Nothing, input: Nothing): Unit = {
    val sql = "select * from Users where name = " + input
    val connection = dataSource.getConnection
    try {
      val statement = connection.createStatement
      try {
        val resultSet = statement.executeQuery(sql)
        System.out.println(resultSet)
      } finally if (statement != null) statement.close()
    }
  }

  @throws[SQLException]
  def danger2(dataSource: Nothing, input: Nothing): Unit = {
    val value = String.format("%s", input)
    val sql = "select * from Users where name = " + input
    val connection = dataSource.getConnection
    try {
      val statement = connection.createStatement
      try {
        val resultSet = statement.executeQuery(sql)
        System.out.println(resultSet)
      } finally if (statement != null) statement.close()
    }
  }

  @throws[SQLException]
  def danger3(dataSource: Nothing, input: Nothing): Unit = {
    val connection = dataSource.getConnection
    try {
      val statement = connection.createStatement
      try {
        val resultSet = statement.executeQuery("select * from Users where name = " + input)
        System.out.println(resultSet)
      } finally if (statement != null) statement.close()
    }
  }

  @throws[SQLException]
  def danger4(dataSource: Nothing, input: Nothing): Unit = {
    val connection = dataSource.getConnection
    try {
      val statement = connection.createStatement
      try {
        val resultSet = statement.executeQuery("select * from Users where name = " + String.format("%s", input))
        System.out.println(resultSet)
      } finally if (statement != null) statement.close()
    }
  }

  @throws[SQLException]
  def danger5(dataSource: Nothing, input: Nothing): Unit = {
    val connection = dataSource.getConnection
    try {
      val statement = connection.createStatement
      try {
        val resultSet = statement.executeQuery(String.format("select * from Users where name = %s", input))
        System.out.println(resultSet)
      } finally if (statement != null) statement.close()
    }
  }
}
