package jtechlog.springsecurityaclboot;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AclCache;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class H2JdbcMutableAclService extends JdbcMutableAclService {

    public H2JdbcMutableAclService(DataSource dataSource, LookupStrategy lookupStrategy, AclCache aclCache) {
        super(dataSource, lookupStrategy, aclCache);
    }

    private String insertSid = "insert into acl_sid (principal, sid) values (?, ?)";
    private String selectClassPrimaryKey = "select id from acl_class where class=?";

    private String selectSidPrimaryKey = "select id from acl_sid where principal=? and sid=?";

    private static final String DEFAULT_INSERT_INTO_ACL_CLASS = "insert into acl_class (class) values (?)";

    private static final String DEFAULT_INSERT_INTO_ACL_CLASS_WITH_ID = "insert into acl_class (class, class_id_type) values (?, ?)";

    @Override
    protected Long createOrRetrieveClassPrimaryKey(String type, boolean allowCreate, Class idType) {
        // selectClassPrimaryKey private
        List<Long> classIds = this.jdbcOperations.queryForList(selectClassPrimaryKey, new Object[] { type },
                Long.class);

        if (!classIds.isEmpty()) {
            return classIds.get(0);
        }

        if (allowCreate) {
            String sql;
            String[] params;
            if (!isAclClassIdSupported()) {
                // insertClass private
                sql = DEFAULT_INSERT_INTO_ACL_CLASS;
                params = new String[]{type};
            }
            else {
                sql = DEFAULT_INSERT_INTO_ACL_CLASS_WITH_ID;
                params = new String[]{type, idType.getCanonicalName()};
            }

            KeyHolder keyHolder = new GeneratedKeyHolder();
            this.jdbcOperations.update(new PreparedStatementCreator() {
                                    @Override
                                    public PreparedStatement createPreparedStatement(Connection connection)
                                            throws SQLException {
                                        PreparedStatement ps =
                                                connection.prepareStatement(sql,
                                                        Statement.RETURN_GENERATED_KEYS);
                                        ps.setString(1, params[0]);
                                        if (params.length > 1) {
                                            ps.setString(2, params[1]);
                                        }
                                        return ps;
                                    }
                                }, keyHolder
            );
            Assert.isTrue(TransactionSynchronizationManager.isSynchronizationActive(), "Transaction must be running");
            //            return keyHolder.getKey().longValue();
            return (Long) keyHolder.getKeys().get("id");
        }

        return null;
    }

    protected Long createOrRetrieveSidPrimaryKey(String sidName, boolean sidIsPrincipal, boolean allowCreate) {
        List<Long> sidIds = this.jdbcOperations.queryForList(this.selectSidPrimaryKey,
                new Object[] { sidIsPrincipal, sidName }, Long.class);
        if (!sidIds.isEmpty()) {
            return sidIds.get(0);
        }
        if (allowCreate) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            this.jdbcOperations.update(new PreparedStatementCreator() {
                                           @Override
                                           public PreparedStatement createPreparedStatement(Connection connection)
                                                   throws SQLException {
                                               PreparedStatement ps =
                                                       connection.prepareStatement(insertSid,
                                                               Statement.RETURN_GENERATED_KEYS);
                                               ps.setBoolean(1, sidIsPrincipal);
                                               ps.setString(2, sidName);
                                               return ps;
                                           }
                                       }, keyHolder
            );

            Assert.isTrue(TransactionSynchronizationManager.isSynchronizationActive(), "Transaction must be running");
//            return keyHolder.getKey().longValue();
            return (Long) keyHolder.getKeys().get("id");
        }
        return null;
    }
}
