import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;

public class Visual {
    public Visual() {}

    static final String JDBC_Driver = "com.mysql.cj.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/men?useSSL=false";
    Connection connection;
    Statement statement;
    String sql;
    ResultSet resultSet;

    public void create() {

        try {

            //SQL
            Object driver = Class.forName(JDBC_Driver);


            //IO
            PipedInputStream piw = new PipedInputStream();
            PipedOutputStream pow = new PipedOutputStream(piw);
            OutputStreamWriter osw = new OutputStreamWriter(pow);
            PrintWriter pwriter = new PrintWriter(osw);
            InputStreamReader isr = new InputStreamReader(piw);

            //Swing
            JFrame fr = new JFrame("JDBC");
            JPanel pn = new JPanel();
            pn.setLayout(null);
            fr.setLayout(new BorderLayout());

            fr.setBounds(100, 50, 800, 600);
            JButton bt1 = new JButton("Добавить заказ");
            JButton bt2 = new JButton("Удалить заказ");
            JButton bt3 = new JButton("Добавить механика");
            JButton bt4 = new JButton("Удалить механика");
            JButton bt5 = new JButton("Обновить таблицу");
            JTextField tf1 = new JTextField();
            JTextArea ta1 = new JTextArea();
            JDialog dg1 = new JDialog();
            JDialog dg2 = new JDialog();
            JDialog dg3 = new JDialog();
            JDialog dg4 = new JDialog();

            fr.add(pn, BorderLayout.CENTER);
            pn.add(bt1);
            pn.add(bt2);
            pn.add(bt3);
            pn.add(bt4);
            pn.add(bt5);
            // pn.add(tf1, BorderLayout.CENTER);
            pn.add(ta1);

            int l = 25;
            int d = 20;

            ta1.setBounds(l, d, fr.getWidth() - 2 * l, fr.getHeight() - 200);

            Rectangle bt = new Rectangle(ta1.getX(), ta1.getY() + ta1.getHeight() + d, (int)((ta1.getWidth() - 3 * l) / 4.), 30);
            bt1.setBounds(bt);
            bt2.setBounds(bt.x + bt.width + l, bt.y, bt.width, bt.height);
            bt3.setBounds(bt.x + 2 * (bt.width + l), bt.y, bt.width, bt.height);
            bt4.setBounds(bt.x + 3 * (bt.width + l), bt.y, bt.width, bt.height);
            bt5.setBounds(bt.x, bt.y + bt.height + d, ta1.getWidth(), bt.height);

            fr.addWindowListener(new WindowAdapter() {

                @Override
                public void windowClosing(WindowEvent e) {
                    e.getWindow().dispose();
                }
            });

            fr.setVisible(true);

            class Monitor implements Runnable {
                Monitor() {}

                public void run() {

                    StringBuffer sb = new StringBuffer();
                    char[] c = new char[10];
                    int count;

                    try {
                        while (true) {

                            if (isr.ready()) {
                                sb.delete(0, sb.capacity());

                                while (isr.ready()) {

                                    count = isr.read(c, 0, c.length);
                                    sb.append(c, 0, count);
                                }

                                ta1.setText(sb.toString());
                            }
                            Thread.sleep(200);
                        }

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                }
            }

            Thread monitorThread = new Thread(new Monitor());
            monitorThread.setDaemon(true);
            monitorThread.start();

            bt5.addActionListener((ActionEvent e) -> {

                try {
                    connection = null;
                    statement = null;
                    sql = "SELECT * FROM STAFF";

                    connection = DriverManager.getConnection(DATABASE_URL, "root", "1234");
                    statement = connection.createStatement();
                    resultSet = statement.executeQuery(sql);
                    ta1.setText("");

                    pwriter.println("\nSTAFF: ");

                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        String secondname = resultSet.getString("secondname");
                        String surname = resultSet.getString("surname");
                        int salary = resultSet.getInt("salary");

                        pwriter.println("---------------------------------------");
                        pwriter.println("ID:  " + id);
                        pwriter.println(surname + " " + name + " " + secondname);

                        System.out.println(surname + " " + name + " " + secondname);

                        pwriter.println("Salary: $" + salary);

                        pwriter.flush();

                    }

                    resultSet.close();
                    statement.close();
                    connection.close();

                } catch (SQLException e3) {
                    e3.printStackTrace();
                }

            });



        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e3) {
            e3.printStackTrace();/*catch (SQLException e2) {
            e2.printStackTrace();
        } */
        }
    }

    public static void main (String[] args) {
        new Visual().create();
    }
}




