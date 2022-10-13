// License: LGPL-3.0 License (c) find-sec-bugs
package injection;

import org.hibernate.Session;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import org.springframework.jdbc.core.JdbcTemplate;

public class SqlInjection {
    private static final String CLIENT_FIELDS = "client_id, client_secret, resource_ids, scope, "
            + "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
            + "refresh_token_validity, additional_information, autoapprove";

    private static final String DEFAULT_INSERT_STATEMENT = "insert into oauth_client_details (" + CLIENT_FIELDS + ")"
            + "values (?,?,?,?,?,?,?,?,?,?,?)";

    private JdbcTemplate jdbcTemplate;

    public class UserEntity {
        private Long id;
        private String test;

        public String getTest() {
            return test;
        }

        public void setTest(String test) {
            this.test = test;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }

    private static final PersistenceManagerFactory pmfInstance = JDOHelper
            .getPersistenceManagerFactory("transactions-optional");

    public static PersistenceManager getPM() {
        return pmfInstance.getPersistenceManager();
    }

    public void testJdoQueries(String input) {
        PersistenceManager pm = getPM();

        pm.newQuery("select * from Users where name = " + input);

        pm.newQuery("sql", "select * from Products where name = " + input);

        // Test for false positive

        pm.newQuery("select * from Config");

        final String query = "select * from Config";
        pm.newQuery(query);

        pm.newQuery("sql", query);
    }

    public void testJdoQueriesAdditionalMethodSig(String input) {
        PersistenceManager pm = getPM();

        pm.newQuery(UserEntity.class, new ArrayList(), "id == " + input); // Injection?

        pm.newQuery(UserEntity.class, new ArrayList(), "id == 1");

        pm.newQuery(UserEntity.class, "id == " + input); // Injection?

        pm.newQuery(UserEntity.class, "id == 1");

        pm.newQuery((Extent) null, "id == " + input); // Injection?

        pm.newQuery((Extent) null, "id == 1");

    }

    public void testHibernate(String input) {
        Session session = null;
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Object> query = null;
        // should not be reported
        session.createQuery(query);
        // should be reported
        session.createQuery(input);
        CriteriaQuery<Object> cq = cb.createQuery(Object.class);
    }

    public void good(String clientDetails) {
        final String statementUsingConstants = "insert into oauth_client_details (" + CLIENT_FIELDS + ")"
                + "values (?,?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(statementUsingConstants, clientDetails);
    }

    public void good2(String clientDetails) {
        jdbcTemplate.update(DEFAULT_INSERT_STATEMENT, clientDetails);
    }

    public void bad(String clientDetails) {
        String stmtUsingFuncParam = "test" + clientDetails + "test";
        jdbcTemplate.update(stmtUsingFuncParam, clientDetails);
    }

    public void badInline(String clientDetails) {
        jdbcTemplate.update("test" + clientDetails + "test", clientDetails);
    }

}
