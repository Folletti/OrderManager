import java.sql.*;
import com.jcraft.jsch.*;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;


public class Connector {

    public Connector() {

    }

    private static final String JDBC_Driver = "com.mysql.cj.jdbc.Driver";

    //local
    private static final int localPort = 3309;

    //SQL
    private static final String sqlRemoteHost = "a247213.mysql.mchost.ru";
    private static final int sqlRemotePort = 3306;
    private static final String sqlOpts = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String sqlUser = "a247213_1";
    private static final String sqlPswd = "vTCST9tmfI";
    private static final String sqlDbName = "a247213_1";

    //SSH
    private static final String sshHost = "ftp.darkthread.mcdir.ru";
    private static final int sshPort = 22;
    private static final String sshUser = sqlUser;
    private static final String sshPswd = "hj7Fv835cz6m";
    private static int assignedPort;
    private static com.jcraft.jsch.Session session;

    private StringBuilder dbUrl = new StringBuilder("jdbc:mysql://localhost:");
    private Connection connection;

    public Connection connect () {

        try {
            JSch jsch = new JSch();

            session = jsch.getSession(sshUser, sshHost, sshPort);
            session.setPassword(sshPswd);

            java.util.Properties conf = new java.util.Properties();
            conf.put("StrictHostKeyChecking", "no");
            conf.put("Compression","yes");
            conf.put("ConnectionAttempts","2");
            session.setConfig(conf);

            session.connect();
            assignedPort = session.setPortForwardingL(localPort, sqlRemoteHost, sqlRemotePort);

            Object driver = Class.forName(JDBC_Driver);

            dbUrl.append(assignedPort).append("/").append(sqlDbName).append(sqlOpts);

            connection = DriverManager.getConnection(dbUrl.toString(), sqlUser, sqlPswd);

        } catch (JSchException je) {
            je.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e2) {
            e2.printStackTrace();
        } finally {
            return connection;
        }
    }

    public void disconnect() {
        if (session != null) session.disconnect();
        try {
            if (connection != null) connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public com.jcraft.jsch.Session getSession() {
        return session;
    }
}
