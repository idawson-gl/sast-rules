// License: LGPL-3.0 License (c) find-sec-bugs
package inject;
import org.springframework.ldap.core.DefaultNameClassPairMapper;
import org.springframework.ldap.core.DirContextProcessor;
import org.springframework.ldap.core.LdapEntryIdentificationContextMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.CountNameClassPairCallbackHandler;
import org.springframework.ldap.core.support.DefaultIncrementalAttributesMapper;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import java.util.Hashtable;
import java.util.Properties;

public class LDAPInjection {
    private final static String ldapURI = "ldaps://ldap.server.com/dc=ldap,dc=server,dc=com";
    private final static String contextFactory = "com.sun.jndi.ldap.LdapCtxFactory";

    /***************** JNDI LDAP **********************/

    static boolean authenticate(String username, String password) {
        try {
            Properties props = new Properties();
            props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            props.put(Context.PROVIDER_URL, "ldap://ldap.example.com");
            props.put(Context.REFERRAL, "ignore");
            props.put(Context.SECURITY_PRINCIPAL, dnFromUser(username));
            props.put(Context.SECURITY_CREDENTIALS, password);

            new InitialDirContext(props);
            return true;
        } catch (NamingException e) {
            return false;
        }

    }

    private static String dnFromUser(String username) throws NamingException {
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        props.put(Context.PROVIDER_URL, "ldap://ldap.example.com");
        props.put(Context.REFERRAL, "ignore");

        InitialDirContext context = new InitialDirContext(props);

        SearchControls ctrls = new SearchControls();
        ctrls.setReturningAttributes(new String[]{"givenName", "sn"});
        ctrls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        NamingEnumeration<SearchResult> answers = context.search("dc=People,dc=example,dc=com", "(uid=" + username + ")", ctrls);
        SearchResult result = answers.next();

        return result.getNameInNamespace();
    }

    private static DirContext ldapContext(Hashtable<String, String> env) throws Exception {
        env.put(Context.INITIAL_CONTEXT_FACTORY, contextFactory);
        env.put(Context.PROVIDER_URL, ldapURI);
        env.put(Context.SECURITY_AUTHENTICATION, "none");
        DirContext ctx = new InitialDirContext(env);
        return ctx;
    }

    public static boolean testBind(String dn, String password) throws Exception {
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.SECURITY_AUTHENTICATION, "simple"); //false positive
        env.put(Context.SECURITY_PRINCIPAL, dn);
        env.put(Context.SECURITY_CREDENTIALS, password);

        try {
            ldapContext(env);
        } catch (javax.naming.AuthenticationException e) {
            return false;
        }
        return true;
    }

    /***************** SPRING LDAP **********************/

    public void queryVulnerableToInjection(LdapTemplate template, String jndiInjectMe, SearchControls searchControls, DirContextProcessor dirContextProcessor) throws NamingException {
        template.list(jndiInjectMe);
        template.list(jndiInjectMe, new DefaultNameClassPairMapper());
        template.list(jndiInjectMe, new CountNameClassPairCallbackHandler());

        template.lookup(jndiInjectMe);
        DefaultIncrementalAttributesMapper mapper = new DefaultIncrementalAttributesMapper("");
        template.lookup(jndiInjectMe, mapper);
        template.lookup(jndiInjectMe, new LdapEntryIdentificationContextMapper());

        template.search(jndiInjectMe, "dn=1", searchControls, new CountNameClassPairCallbackHandler());
        template.search(jndiInjectMe, "dn=1", searchControls, mapper, dirContextProcessor);
        template.search(jndiInjectMe, "dn=1", searchControls, new LdapEntryIdentificationContextMapper(), dirContextProcessor);
        template.search(jndiInjectMe, "dn=1", searchControls, new CountNameClassPairCallbackHandler(), dirContextProcessor);
        template.search(jndiInjectMe, "dn=1", SearchControls.OBJECT_SCOPE, true, new CountNameClassPairCallbackHandler());
        template.search(jndiInjectMe, "dn=1", new CountNameClassPairCallbackHandler());
        template.search(jndiInjectMe, "dn=1", SearchControls.OBJECT_SCOPE, new String[0], mapper);
        template.search(jndiInjectMe, "dn=1", SearchControls.OBJECT_SCOPE, mapper);
        template.search(jndiInjectMe, "dn=1", mapper);
        template.search(jndiInjectMe, "dn=1", SearchControls.OBJECT_SCOPE, new String[0], new LdapEntryIdentificationContextMapper());
        template.search(jndiInjectMe, "dn=1", SearchControls.OBJECT_SCOPE, new LdapEntryIdentificationContextMapper());
        template.search(jndiInjectMe, "dn=1", new LdapEntryIdentificationContextMapper());
        template.search(jndiInjectMe, "dn=1", searchControls, new LdapEntryIdentificationContextMapper());
        template.search(jndiInjectMe, "dn=1", searchControls, mapper);
    }

    public void safeQuery(LdapTemplate template, SearchControls searchControls, DirContextProcessor dirContextProcessor) throws NamingException {
        String safeQuery = "uid=test";
        template.list(safeQuery);
        template.list(safeQuery, new DefaultNameClassPairMapper());
        template.list(safeQuery, new CountNameClassPairCallbackHandler());

        template.lookup(safeQuery);
        DefaultIncrementalAttributesMapper mapper = new DefaultIncrementalAttributesMapper("");
        template.lookup(safeQuery, mapper);
        template.lookup(safeQuery, new LdapEntryIdentificationContextMapper());

        template.search(safeQuery, "dn=1", searchControls, new CountNameClassPairCallbackHandler());
        template.search(safeQuery, "dn=1", searchControls, mapper, dirContextProcessor);
        template.search(safeQuery, "dn=1", searchControls, new LdapEntryIdentificationContextMapper(), dirContextProcessor);
        template.search(safeQuery, "dn=1", searchControls, new CountNameClassPairCallbackHandler(), dirContextProcessor);
        template.search(safeQuery, "dn=1", SearchControls.OBJECT_SCOPE, true, new CountNameClassPairCallbackHandler());
        template.search(safeQuery, "dn=1", new CountNameClassPairCallbackHandler());
        template.search(safeQuery, "dn=1", SearchControls.OBJECT_SCOPE, new String[0], mapper);
        template.search(safeQuery, "dn=1", SearchControls.OBJECT_SCOPE, mapper);
        template.search(safeQuery, "dn=1", mapper);
        template.search(safeQuery, "dn=1", SearchControls.OBJECT_SCOPE, new String[0], new LdapEntryIdentificationContextMapper());
        template.search(safeQuery, "dn=1", SearchControls.OBJECT_SCOPE, new LdapEntryIdentificationContextMapper());
        template.search(safeQuery, "dn=1", new LdapEntryIdentificationContextMapper());
        template.search(safeQuery, "dn=1", searchControls, new LdapEntryIdentificationContextMapper());
        template.search(safeQuery, "dn=1", searchControls, mapper);
    }


    /***************** JNDI LDAP SPECIAL **********************/


    public static void main(String param) throws NamingException {
        DirContext ctx = null;
        String base = "ou=users,ou=system";
        SearchControls sc = new SearchControls();
        sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
        String filter = "(&(objectclass=person))(|(uid="+param+")(street={0}))";
        Object[] filters = new Object[]{"The streetz 4 Ms bar"};
        System.out.println("Filter " + filter);
        NamingEnumeration<SearchResult> results = ctx.search(base, filter,filters, sc);
    }

}
