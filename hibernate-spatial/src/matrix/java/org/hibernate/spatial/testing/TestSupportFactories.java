package org.hibernate.spatial.testing;

import org.hibernate.dialect.Dialect;
import org.hibernate.spatial.testing.dialects.mysql.MySQLTestSupport;
import org.hibernate.spatial.testing.dialects.oracle.OracleSDOTestSupport;
import org.hibernate.spatial.testing.dialects.postgis.PostgisTestSupport;
import org.hibernate.spatial.testing.dialects.sqlserver.SQLServerTestSupport;


/**
 * @author Karel Maesen, Geovise BVBA
 *         creation-date: Sep 30, 2010
 */
public class TestSupportFactories {

    private static TestSupportFactories instance = new TestSupportFactories();

    public static TestSupportFactories instance() {
        return instance;
    }

    private TestSupportFactories() {
    }


    public TestSupport getTestSupportFactory(Dialect dialect) throws InstantiationException, IllegalAccessException {
        if (dialect == null) {
            throw new IllegalArgumentException("Dialect argument is required.");
        }
        Class testSupportFactoryClass = getSupportFactoryClass(dialect);
        return instantiate(testSupportFactoryClass);

    }

    private TestSupport instantiate(Class<? extends TestSupport> testSupportFactoryClass) throws IllegalAccessException, InstantiationException {
        return testSupportFactoryClass.newInstance();
    }

    private ClassLoader getClassLoader() {
        return this.getClass().getClassLoader();
    }

    //TODO -- find a better way to initialize and inject the TestSupport class.
    //This whole class can probably be made obsolete.

    private static Class<? extends TestSupport> getSupportFactoryClass(Dialect dialect) {
        String canonicalName = dialect.getClass().getCanonicalName();
        if ("org.hibernate.spatial.dialect.postgis.PostgisDialect".equals(canonicalName)) {
            return PostgisTestSupport.class;
        }
//        if ("org.hibernate.spatial.geodb.GeoDBDialect".equals(canonicalName)) {
//            return "org.hibernate.spatial.geodb.GeoDBSupport";
//        }
        if ("org.hibernate.spatial.dialect.sqlserver.SqlServer2008SpatialDialect".equals(canonicalName)) {
            return SQLServerTestSupport.class;
        }
        if ("org.hibernate.spatial.dialect.mysql.MySQLSpatialDialect".equals(canonicalName) ||
                "org.hibernate.spatial.dialect.mysql.MySQLSpatialInnoDBDialect".equals(canonicalName)) {
            return MySQLTestSupport.class;
        }
        if ("org.hibernate.spatial.dialect.oracle.OracleSpatial10gDialect".equals(canonicalName)) {
            return OracleSDOTestSupport.class;
        }
        throw new IllegalArgumentException("Dialect not known in test suite");
    }

}
