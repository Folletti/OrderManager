import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;


public class Visual {
    public Visual() {}

    static final String JDBC_Driver = "com.mysql.cj.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/testbase?useSSL=false&allowPublicKeyRetrieval=true";
    Connection connection = null;
    PreparedStatement statement = null;
    String sql;
    ResultSet resultSet;
    HashSet <Object> orCol, mechCol, clCol;
    int l = 25;
    int d = 20;
    JFrame fr;
    Rectangle bt;
    int countMechs, countClients, countOrders;
    Object[] colounmHeader1;
    Object[] coloumnHeader2;
    Object[] coloumnHeader3;
    JTable tbl1, tbl2, tbl3;
    JScrollPane jsp1, jsp2, jsp3;
    Box mechBox, clBox, orBox;
    final static Status slated = new Status(Status.SLATED);
    final static Status ready = new Status(Status.READY);
    final static Status accepted = new Status(Status.ACCEPTED);
    final static Status undefined = new Status(Status.UNDEFINED);
    Connector connector;

    public void create() {

        /*try {*/

            //SQL
            /*Object driver = Class.forName(JDBC_Driver);
            connection = DriverManager.getConnection(DATABASE_URL, "root", "1234");*/
            connector = new Connector();
            connection = connector.connect();

            //Swing
            fr = new JFrame("Менеджер заказов");
            JPanel pn = new JPanel();
            pn.setLayout(null);
            fr.setLayout(new BorderLayout());

            fr.setBounds(200, 50, 950, 700);
            JButton addOrBt = new JButton("Добавить заказ");
            JButton chOrBt = new JButton("Изменить заказ");
            JButton delOrBt = new JButton("Удалить заказ");

            JButton orFltrBt = new JButton("Фильтровать");

            JButton addMechBt = new JButton("Добавить механика");
            JButton chMechBt = new JButton("Изменить данные механика");
            JButton delMechBt = new JButton("Удалить механика");
            JButton mechStatBt = new JButton();

            Font bigFont = new Font("Verdana", Font.BOLD, 16);
            JLabel statBtLb1 = new JLabel("Статистика");
            JLabel statBtLb2 = new JLabel("механика");
            mechStatBt.setLayout(null);
            mechStatBt.add(statBtLb1);
            mechStatBt.add(statBtLb2);
            statBtLb1.setFont(bigFont);
            statBtLb2.setFont(bigFont);

            JButton addClBt = new JButton("Добавить клиента");
            JButton chClBt = new JButton("Изменить данные клиента");
            JButton delClBt = new JButton("Удалить клиента");

            JButton refrBt = new JButton();
            JLabel refrBtLb1 = new JLabel("Обновить");
            JLabel refrBtLb2 = new JLabel("таблицы");
            refrBt.setLayout(null);
            refrBt.add(refrBtLb1);
            refrBt.add(refrBtLb2);
            refrBtLb1.setFont(bigFont);
            refrBtLb2.setFont(bigFont);

            colounmHeader1 = new Object[] {"ID", "Фамилия", "Имя", "Отчество", "Зарплата, руб/час"};
            coloumnHeader2 = new Object[] {"ID", "Фамилия", "Имя", "Отчество", "Телефон"};
            coloumnHeader3 = new Object[] {"ID", "Описание", "Клиент", "Механик", "Дата создания", "Дата выполнения", "Стоимость, руб", "Статус"};

            tbl1 = new JTable(new Object[20][colounmHeader1.length], colounmHeader1);
            tbl2 = new JTable(new Object[20][coloumnHeader2.length], coloumnHeader2);
            tbl3 = new JTable(new Object[20][coloumnHeader3.length], coloumnHeader3);

            tbl1.getColumnModel().getColumn(0).setMaxWidth(40);
            tbl2.getColumnModel().getColumn(0).setMaxWidth(40);
            tbl3.getColumnModel().getColumn(0).setMaxWidth(40);
            tbl3.getColumnModel().getColumn(5).setMinWidth(110);
            tbl3.getColumnModel().getColumn(5).setMaxWidth(300);

            mechBox = new Box(BoxLayout.Y_AXIS);
            jsp1 = new JScrollPane(tbl1);
            mechBox.add(jsp1);
            JLabel mechBxLb = new JLabel("Список механиков: ");
            mechBxLb.setHorizontalAlignment(JLabel.CENTER);
            Font verdana = new Font("Verdana", Font.BOLD, 14);
            mechBxLb.setFont(verdana);

            clBox = new Box(BoxLayout.Y_AXIS);
            jsp2 = new JScrollPane(tbl2);
            clBox.add(jsp2);
            JLabel clBxLb = new JLabel("Список клиентов: ");
            clBxLb.setHorizontalAlignment(JLabel.CENTER);
            clBxLb.setFont(verdana);

            orBox = new Box(BoxLayout.Y_AXIS);
            jsp3 = new JScrollPane(tbl3);
            orBox.add(jsp3);

            JLabel orBxLb = new JLabel("Список заказов: ");
            orBxLb.setHorizontalAlignment(JLabel.CENTER);
            orBxLb.setFont(verdana);

            //Создание текстовых полей и надписей фильтра
            JTextField clFltr = new JTextField();
            //JTextField statFltr = new JTextField();
            String[] variants = new String[] {"", "Запланирован", "Выполнен", "Принят клиентом"};
            JComboBox statFltr = new JComboBox(variants);
            JTextField descFltr = new JTextField();
            JLabel clFltrLb = new JLabel("Фамилия клиента");
            JLabel statFltrLb = new JLabel("Статус");
            JLabel descFltrLb = new JLabel("Описание заказа");

            fr.add(pn, BorderLayout.CENTER);
            pn.add(addOrBt);
            pn.add(delOrBt);
            pn.add(addMechBt);
            pn.add(delMechBt);
            pn.add(refrBt);
            pn.add(chClBt);
            pn.add(chMechBt);
            pn.add(chOrBt);
            pn.add(mechStatBt);
            pn.add(orFltrBt);
            pn.add(addClBt);
            pn.add(delClBt);

            pn.add(clFltr);
            pn.add(descFltr);
            pn.add(statFltr);
            pn.add(clFltrLb);
            pn.add(descFltrLb);
            pn.add(statFltrLb);

            pn.add(mechBox);
            pn.add(clBox);
            pn.add(orBox);
            pn.add(mechBxLb);
            pn.add(clBxLb);
            pn.add(orBxLb);

            mechBox.setBounds(l, d, fr.getWidth() - 250, (fr.getHeight() - 230 - 2 * d) / 3);
            mechBxLb.setBounds(mechBox.getX(), mechBox.getY() - 15, mechBox.getWidth(), 15);
            clBox.setBounds(l, mechBox.getY() + mechBox.getHeight() + d, mechBox.getWidth(), mechBox.getHeight());
            clBxLb.setBounds(clBox.getX(), clBox.getY() - 15, clBox.getWidth(), 15);
            orBox.setBounds(l, mechBox.getY() + 2 * (mechBox.getHeight() + d), mechBox.getWidth(), mechBox.getHeight());
            orBxLb.setBounds(orBox.getX(), orBox.getY() - 15, orBox.getWidth(), 15);

            //Кнопки редактирования таблиц
            bt = new Rectangle(orBox.getX(), orBox.getY() + orBox.getHeight() + d, (int)((mechBox.getWidth() - 4 * l) / 3.), 30);

            addMechBt.setBounds(bt);
            addClBt.setBounds(bt.x + bt.width + l, addMechBt.getY(), bt.width, bt.height);
            addOrBt.setBounds(bt.x + 2 * (bt.width + l), addMechBt.getY(), bt.width, bt.height);

            chMechBt.setBounds(bt.x, addMechBt.getY() + addMechBt.getHeight() + d, bt.width, bt.height);
            chClBt.setBounds(bt.x + bt.width + l, chMechBt.getY(), bt.width, bt.height);
            chOrBt.setBounds(bt.x + 2 * (bt.width + l), chMechBt.getY(), bt.width, bt.height);

            delMechBt.setBounds(bt.x, chMechBt.getY() + chMechBt.getHeight() + d, bt.width, bt.height);
            delClBt.setBounds(bt.x + bt.width + l, delMechBt.getY(), bt.width, bt.height);
            delOrBt.setBounds(bt.x + 2 * (bt.width + l), delMechBt.getY(), bt.width, bt.height);

            //Кнопка вывода статистики
            mechStatBt.setBounds(fr.getWidth() - 200, mechBox.getY(), bt.width - 40, bt.height + 40);
            statBtLb1.setBounds(mechStatBt.getWidth() / 2 - 50, 12, 100, 15);
            statBtLb2.setBounds(mechStatBt.getWidth() / 2 - 50, 37, 100, 15);
            statBtLb1.setHorizontalAlignment(JLabel.CENTER);
            statBtLb2.setHorizontalAlignment(JLabel.CENTER);

            //Кнопка обновления всех таблиц
            refrBt.setBounds(mechStatBt.getX(), fr.getHeight() - 150, mechStatBt.getWidth(), bt.height + 50);
            refrBtLb1.setBounds(refrBt.getWidth() / 2 - 50, 20, 100, 15);
            refrBtLb2.setBounds(refrBt.getWidth() / 2 - 50, 45, 100, 15);
            refrBtLb1.setHorizontalAlignment(JLabel.CENTER);
            refrBtLb2.setHorizontalAlignment(JLabel.CENTER);

            //Текстовые поля фильтра
            clFltr.setBounds(orBox.getX() + orBox.getWidth() + l, orBox.getY(), 150, 25);
            descFltr.setBounds(clFltr.getX(), clFltr.getY() + clFltr.getHeight() + d, clFltr.getWidth(), clFltr.getHeight());
            statFltr.setBounds(clFltr.getX(), descFltr.getY() + descFltr.getHeight() + d, clFltr.getWidth(), clFltr.getHeight());

            //Надписи фильтра
            clFltrLb.setBounds(clFltr.getX(), clFltr.getY() - 15, clFltr.getWidth(), 15);
            descFltrLb.setBounds(clFltr.getX(), descFltr.getY() - 15, descFltr.getWidth(), 15);
            statFltrLb.setBounds(clFltr.getX(), statFltr.getY() - 15, statFltr.getWidth(), 15);

            //Кнопка фильтра
            orFltrBt.setBounds(statFltr.getX(), statFltr.getY() + statFltr.getHeight() + d, mechStatBt.getWidth(), bt.height);


            fr.addWindowListener(new WindowAdapter() {

                @Override
                public void windowClosing(WindowEvent e) {
                    e.getWindow().dispose();
                    /*try {
                        connection.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }*/
                    connector.disconnect();
                }
            });

            fr.setVisible(true);

            //Действие по нажатию кнопки обновления таблиц
            refrBt.addActionListener((ActionEvent e) -> {

                readClients();
                readMech();
                readOrders();

            });

            //Действие по нажатию кнопки добавления заказа
            addOrBt.addActionListener((ActionEvent e) -> {

                //Создание диалогового окна добавления заказа

                OrderDialog dg = new OrderDialog("Добавление заказа");

                dg.okBt.addActionListener((ActionEvent ev) -> {

                    try {

                        sql = "INSERT into orders values (null, '" + dg.descTf.getText() + "', '" +
                                dg.clCBox.getSelectedItem() + "', '" + dg.mechCBox.getSelectedItem() + "', '" + dg.inDateTf.getText() + "', '" +
                                dg.finDateTf.getText() + "', " + dg.costTf.getText() + ", '" + dg.stCBox.getSelectedItem() + "')";

                        statement = connection.prepareStatement(sql);
                        statement.executeUpdate();

                        readOrders();

                        dg.dispose();


                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                });

                //Код для выполнения по нажатию "Enter"
                /*Component[] cmps = dg.getContentPane().getComponents();
                for (int i = 0; i < cmps.length; i++) {
                    cmps[i].addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyPressed(KeyEvent e) {
                            if (e.getKeyCode() == 10) dg.okBt

                        }
                    });
                }*/



            });

            //Действие кнопки изменения выбранного заказа
            chOrBt.addActionListener( (ActionEvent e) -> {

                //Создание диалогового окна изменения заказа

                if (tbl3.getSelectedRow() != -1) {

                    OrderDialog dg = new OrderDialog("Изменение заказа");

                    Component[] ncomp = dg.getContentPane().getComponents();

                    for (int i = 0; i < ncomp.length; i++) {
                        if (ncomp[i] instanceof JTextField) {
                            ((JTextField) ncomp[i]).setText(String.valueOf(tbl3.getValueAt(tbl3.getSelectedRow(), i + 1)));
                        } else if (ncomp[i] instanceof JComboBox) {
                            ((JComboBox) ncomp[i]).setSelectedItem(String.valueOf(tbl3.getValueAt(tbl3.getSelectedRow(), i + 1)));
                        }
                    }

                    dg.okBt.addActionListener((ActionEvent ev) -> {

                        try {
                            Object rowId = tbl3.getValueAt(tbl3.getSelectedRow(), 0);

                            sql = "UPDATE orders SET description = '" + dg.descTf.getText() + "', client = '" +
                                    dg.clCBox.getSelectedItem() + "', mech = '" + dg.mechCBox.getSelectedItem() + "', init_date = '" + dg.inDateTf.getText() + "', fin_date = '" +
                                    dg.finDateTf.getText() + "', cost = " + dg.costTf.getText() + ", status = '" + dg.stCBox.getSelectedItem() + "' where id = " + (int) rowId;

                            statement = connection.prepareStatement(sql);
                            statement.executeUpdate();

                            readOrders();

                            dg.dispose();


                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }

                    });
                } else {
                    FailDialog dg = new FailDialog("Ошибка!", "Не выбран заказ, который требуется изменить");
                }
            });

            //Действие кнопки удаления заказа
            delOrBt.addActionListener( (ActionEvent e) -> {
                if (tbl3.getSelectedRow() != -1) {
                    try {

                        Object rowId = tbl3.getValueAt(tbl3.getSelectedRow(), 0);

                        sql = "DELETE from orders where id = " + (int) rowId;

                        statement = connection.prepareStatement(sql);
                        statement.executeUpdate();

                        readOrders();

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    new FailDialog("Ошибка!", "Не выбран заказ, который требуется удалить");
                }
            });

            //Действие кнопки добавления механика
            addMechBt.addActionListener( (ActionEvent e) -> {

                //Создание диалогового окна добавления механика

                MechDialog dg = new MechDialog("Добавление механика");

                dg.okBt.addActionListener((ActionEvent ev) -> {

                    try {

                        sql = "INSERT into staff values (null, '" + dg.nameTf.getText() + "', '" +
                                dg.secNmTf.getText() + "', '" + dg.snameTf.getText() + "', '" + dg.salaryTf.getText() + "')";

                        statement = connection.prepareStatement(sql);
                        statement.executeUpdate();

                        readMech();

                        dg.dispose();

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                });
            });

            //Действие кнопки изменения механика
            chMechBt.addActionListener( (ActionEvent e) -> {

                //Создание диалогового окна изменения механика

                if (tbl1.getSelectedRow() != -1) {


                    MechDialog dg = new MechDialog("Изменение данных механика");

                    Component[] ncomp = dg.getContentPane().getComponents();

                    for (int i = 0; i < ncomp.length; i++) {
                        if (ncomp[i] instanceof JTextField) {
                            ((JTextField) ncomp[i]).setText(String.valueOf(tbl1.getValueAt(tbl1.getSelectedRow(), i + 1)));
                        }
                    }

                    dg.okBt.addActionListener((ActionEvent ev) -> {

                        try {
                            Object rowId = tbl1.getValueAt(tbl1.getSelectedRow(), 0);

                            sql = "UPDATE staff SET name = '" + dg.nameTf.getText() + "', second_name = '" +
                                    dg.secNmTf.getText() + "', surname = '" + dg.snameTf.getText() + "', salary = '" + dg.salaryTf.getText() +
                                    "' where id = " + (int) rowId;

                            statement = connection.prepareStatement(sql);
                            statement.executeUpdate();

                            readMech();

                            dg.dispose();


                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }

                    });
                } else {
                    new FailDialog("Ошибка!", "Не выбран механик, данные которого требуется изменить");
                }
            });

            //Действие кнопки удаления механика
            delMechBt.addActionListener( (ActionEvent e) -> {
                if (tbl1.getSelectedRow() != -1) {
                    try {

                        Object rowId = tbl1.getValueAt(tbl1.getSelectedRow(), 0);

                        sql = "DELETE from staff where id = " + (int) rowId;

                        statement = connection.prepareStatement(sql);
                        statement.executeUpdate();

                        readMech();

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    new FailDialog("Ошибка!", "Не выбран механик, которого требуется удалить");
                }
            });

            addClBt.addActionListener( (ActionEvent e) -> {

                //Создание диалогового окна добавления клиента

                ClientDialog dg = new ClientDialog("Добавление клиента");

                dg.okBt.addActionListener((ActionEvent ev) -> {

                    try {

                        sql = "INSERT into clients values (null, '" + dg.nameTf.getText() + "', '" +
                                dg.secNmTf.getText() + "', '" + dg.snameTf.getText() + "', '" + dg.telTf.getText() + "')";

                        statement = connection.prepareStatement(sql);
                        statement.executeUpdate();

                        readClients();

                        dg.dispose();

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                });
            });

            //Действие кнопки изменения клиента
            chClBt.addActionListener( (ActionEvent e) -> {

                //Создание диалогового окна изменения клиента

                if (tbl2.getSelectedRow() != -1) {
                    ClientDialog dg = new ClientDialog("Изменение данных клиента");

                    Component[] ncomp = dg.getContentPane().getComponents();

                    for (int i = 0; i < ncomp.length; i++) {
                        if (ncomp[i] instanceof JTextField) {
                            ((JTextField) ncomp[i]).setText(String.valueOf(tbl2.getValueAt(tbl2.getSelectedRow(), i + 1)));
                        }
                    }

                    dg.okBt.addActionListener((ActionEvent ev) -> {

                        try {
                            Object rowId = tbl2.getValueAt(tbl2.getSelectedRow(), 0);

                            sql = "UPDATE clients SET name = '" + dg.nameTf.getText() + "', second_name = '" +
                                    dg.secNmTf.getText() + "', surname = '" + dg.snameTf.getText() + "', tel_number = '" + dg.telTf.getText() +
                                    "' where id = " + (int) rowId;

                            statement = connection.prepareStatement(sql);
                            statement.executeUpdate();

                            readClients();

                            dg.dispose();


                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }

                    });
                } else {
                    new FailDialog("Ошибка!", "Не выбран клиент, данные которого требуется изменить");
                }
            });

            //Действие кнопки удаления клиента
            delClBt.addActionListener( (ActionEvent e) -> {
                if (tbl2.getSelectedRow() != -1) {
                    try {

                        Object rowId = tbl2.getValueAt(tbl2.getSelectedRow(), 0);

                        sql = "DELETE from clients where id = " + (int) rowId;

                        statement = connection.prepareStatement(sql);
                        statement.executeUpdate();

                        readClients();

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    new FailDialog("Ошибка!", "Не выбран клиент, которого требуется удалить");
                }
            });

            orFltrBt.addActionListener( (ActionEvent e) -> {

                Status st = new Status(statFltr.getSelectedItem().toString());
                Filter filter = new Filter(descFltr.getText(), clFltr.getText(), st);

                readOrders(filter);

                //Здесь использовать коллекцию

                //for (int i = 0; i < tbl3.)


            });

            mechStatBt.addActionListener( (ActionEvent e) -> {
                if (tbl1.getSelectedRow() != -1) {
                    Object[] mech = new Object[colounmHeader1.length];
                    for (int i = 0; i < mech.length; i++) {
                        mech[i] = tbl1.getValueAt(tbl1.getSelectedRow(), i);
                    }

                    int stats = 0;
                    for (int i = 0; i < countOrders; i++) {
                        if (tbl3.getValueAt(i, 3) != null && tbl3.getValueAt(i, 3).equals((String) mech[1])) stats++;
                    }
                    new StatsDialog("Статистика механика", (String) mech[1], (String) mech[2], (String) mech[3], (int) mech[4], stats);
                } else new FailDialog("Ошибка!", "Не выбран механик");
            });


        /*} catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e2) {
            e2.printStackTrace();
        }*/


    }

    //Объявление метода обновления таблицы персонала
    public void readMech() {
        try {

            sql = "select count(*) as count from staff";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            resultSet.next();
            countMechs = resultSet.getInt("count");

            mechBox.setVisible(false);
            mechBox.remove(jsp1);
            tbl1 = new JTable(new Object[countMechs][colounmHeader1.length], colounmHeader1);
            jsp1 = new JScrollPane(tbl1);
            mechBox.add(jsp1);
            mechBox.setVisible(true);

            sql = "SELECT * FROM staff";
            resultSet = statement.executeQuery(sql);
            int count = 0;
            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                tbl1.setValueAt(id, count, 0);
                String name = resultSet.getString("name");
                tbl1.setValueAt(name, count, 2);
                String secondname = resultSet.getString("second_name");
                tbl1.setValueAt(secondname, count, 3);
                String surname = resultSet.getString("surname");
                tbl1.setValueAt(surname, count, 1);
                int salary = resultSet.getInt("salary");
                tbl1.setValueAt(salary, count, 4);

                count++;

            }

            resultSet.close();
            statement.close();

            for (int i = count; i < tbl1.getRowCount(); i++) {
                for (int j = 0; j < tbl1.getColumnCount(); j++)
                    tbl1.setValueAt(null, i, j);
            }

            tbl1.getColumnModel().getColumn(0).setMaxWidth(40);


        } catch (SQLException e3) {
            e3.printStackTrace();
        }
    }

    //Объявление метода обновление таблицы клиентов
    public void readClients() {

        try {

            sql = "select count(*) as count from clients";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            resultSet.next();
            countClients = resultSet.getInt("count");

            clBox.setVisible(false);
            clBox.remove(jsp2);
            tbl2 = new JTable(new Object[countClients][coloumnHeader2.length], coloumnHeader2);
            jsp2 = new JScrollPane(tbl2);
            clBox.add(jsp2);
            clBox.setVisible(true);

            sql = "SELECT * FROM clients";
            resultSet = statement.executeQuery(sql);

            int count = 0;

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                tbl2.setValueAt(id, count, 0);
                String name = resultSet.getString("name");
                tbl2.setValueAt(name, count, 2);
                String secondname = resultSet.getString("second_name");
                tbl2.setValueAt(secondname, count, 3);
                String surname = resultSet.getString("surname");
                tbl2.setValueAt(surname, count, 1);
                long tel = resultSet.getLong("tel_number");
                tbl2.setValueAt(tel, count, 4);

                count++;

            }

            resultSet.close();
            statement.close();



            for (int i = count; i < tbl2.getRowCount(); i++) {
                for (int j = 0; j < tbl2.getColumnCount(); j++)
                    tbl2.setValueAt(null, i, j);
            }


            tbl2.getColumnModel().getColumn(0).setMaxWidth(40);


        } catch (SQLException e3) {
            e3.printStackTrace();
        }
    }

    //Объявление метода обновления таблицы заказов
    public void readOrders() {
        try {

            //Получение максимальных ID из БД для создания таблиц

            sql = "select count(*) as count from orders";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            resultSet.next();
            countOrders = resultSet.getInt("count");

            //Генерация новой таблицы
            orBox.setVisible(false);
            orBox.remove(jsp3);
            tbl3 = new JTable(new Object[countOrders][coloumnHeader3.length], coloumnHeader3);
            jsp3 = new JScrollPane(tbl3);
            orBox.add(jsp3);
            orBox.setVisible(true);

            sql = "SELECT * FROM orders";
            resultSet = statement.executeQuery(sql);

            int count = 0;

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                tbl3.setValueAt(id, count, 0);
                String description = resultSet.getString("description");
                tbl3.setValueAt(description, count, 1);
                String client = resultSet.getString("client");
                tbl3.setValueAt(client, count, 2);
                String mech = resultSet.getString("mech");
                tbl3.setValueAt(mech, count, 3);
                String init_time = resultSet.getString("init_date");
                tbl3.setValueAt(init_time, count, 4);
                String fin_time = resultSet.getString("fin_date");
                tbl3.setValueAt(fin_time, count, 5);
                int cost = resultSet.getInt("cost");
                tbl3.setValueAt(cost, count, 6);
                String status = resultSet.getString("status");
                tbl3.setValueAt(status, count, 7);

                count++;

            }

            resultSet.close();
            statement.close();

            for (int i = count; i < tbl3.getRowCount(); i++) {
                for (int j = 0; j < tbl3.getColumnCount(); j++)
                    tbl3.setValueAt(null, i, j);
            }

            tbl3.getColumnModel().getColumn(0).setMaxWidth(40);
            tbl3.getColumnModel().getColumn(5).setMinWidth(110);
            tbl3.getColumnModel().getColumn(5).setMaxWidth(300);

        } catch (SQLException e3) {
            e3.printStackTrace();
        }
    }

    public void readOrders(Filter filter) {
        try {

            //Получение максимальных ID из БД для создания таблиц

            sql = "select count(*) as count from orders";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            resultSet.next();
            countOrders = resultSet.getInt("count");

            //Генерация новой таблицы
            orBox.setVisible(false);
            orBox.remove(jsp3);
            tbl3 = new JTable(new Object[countOrders][coloumnHeader3.length], coloumnHeader3);
            jsp3 = new JScrollPane(tbl3);
            orBox.add(jsp3);
            orBox.setVisible(true);

            sql = "SELECT * FROM orders";
            resultSet = statement.executeQuery(sql);

            int count = 0;

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String description = resultSet.getString("description");
                String client = resultSet.getString("client");
                String mech = resultSet.getString("mech");
                String init_time = resultSet.getString("init_date");
                String fin_time = resultSet.getString("fin_date");
                int cost = resultSet.getInt("cost");
                String status = resultSet.getString("status");



                if (!filter.filter(new Filter(description, client, new Status(status)))) continue;
                /*if (filter.filter(client, filter.getClient()) ||
                        filter.filter(description, filter.getDesc()) ||
                        filter.filter(status, filter.getStatus().toString())) continue;*/

                tbl3.setValueAt(id, count, 0);
                tbl3.setValueAt(description, count, 1);
                tbl3.setValueAt(client, count, 2);
                tbl3.setValueAt(mech, count, 3);
                tbl3.setValueAt(init_time, count, 4);
                tbl3.setValueAt(fin_time, count, 5);
                tbl3.setValueAt(cost, count, 6);
                tbl3.setValueAt(status, count, 7);

                count++;

            }

            resultSet.close();
            statement.close();

            for (int i = count; i < tbl3.getRowCount(); i++) {
                for (int j = 0; j < tbl3.getColumnCount(); j++)
                    tbl3.setValueAt(null, i, j);
            }

            tbl3.getColumnModel().getColumn(0).setMaxWidth(40);
            tbl3.getColumnModel().getColumn(5).setMinWidth(110);
            tbl3.getColumnModel().getColumn(5).setMaxWidth(300);

        } catch (SQLException e3) {
            e3.printStackTrace();
        }
    }

    //Объявление класса диалогового окна заказов
    class OrderDialog extends JDialog {
        public OrderDialog(String title) {

            setTitle(title);
            setBounds(fr.getX() + 200, fr.getY() + 200, fr.getWidth() - 500, fr.getHeight() - 350);
            setLayout(null);
            descTf = new JTextField();
            //clTf = new JTextField();
            //mechTf = new JTextField();
            clCBox = new JComboBox();
            mechCBox = new JComboBox();
            inDateTf = new JTextField();
            finDateTf = new JTextField();
            costTf = new JTextField();
            stVars = new String[] {"Запланирован", "Выполнен", "Принят клиентом"};
            stCBox = new JComboBox(stVars);
            descTfLb = new JLabel("Описание заказа: ");
            clTfLb = new JLabel("Фамилия клиента: ");
            mechTfLb = new JLabel("Фамилия механика: ");
            inDateTfLb = new JLabel("Дата начала работ: ");
            finDateTfLb = new JLabel("Дата окончания работ: ");
            costTfLb = new JLabel("Стоимость, руб: ");
            stTfLb = new JLabel("Статус заказа: ");
            okBt = new JButton("OK");
            add(descTf);
            //add(clTf);
            //add(mechTf);
            add(clCBox);
            add(mechCBox);
            add(inDateTf);
            add(finDateTf);
            add(costTf);
            add(stCBox);
            add(descTfLb);
            add(clTfLb);
            add(mechTfLb);
            add(inDateTfLb);
            add(finDateTfLb);
            add(costTfLb);
            add(stTfLb);
            add(okBt);

            //Размещение компонентов

            descTf.setBounds(50, 50, 150, 25);
            //clTf.setBounds(descTf.getX(), descTf.getY() + descTf.getHeight() + d, descTf.getWidth(), descTf.getHeight());
            //mechTf.setBounds(descTf.getX(), descTf.getY() + 2 * (descTf.getHeight() + d), descTf.getWidth(), descTf.getHeight());
            clCBox.setBounds(descTf.getX(), descTf.getY() + descTf.getHeight() + d, descTf.getWidth(), descTf.getHeight());
            mechCBox.setBounds(descTf.getX(), descTf.getY() + 2 * (descTf.getHeight() + d), descTf.getWidth(), descTf.getHeight());
            costTf.setBounds(descTf.getX(), descTf.getY() + 3 * (descTf.getHeight() + d), descTf.getWidth(), descTf.getHeight());
            inDateTf.setBounds(descTf.getX() + descTf.getWidth() + l, descTf.getY(), descTf.getWidth(), descTf.getHeight());
            finDateTf.setBounds(inDateTf.getX(), clCBox.getY(), descTf.getWidth(), descTf.getHeight());

            stCBox.setBounds(inDateTf.getX(), mechCBox.getY(), descTf.getWidth(), descTf.getHeight());
            descTfLb.setBounds(descTf.getX(), descTf.getY() - 15, descTf.getWidth(), 15);
            clTfLb.setBounds(clCBox.getX(), clCBox.getY() - 15, clCBox.getWidth(), 15);
            mechTfLb.setBounds(mechCBox.getX(), mechCBox.getY() - 15, mechCBox.getWidth(), 15);
            inDateTfLb.setBounds(inDateTf.getX(), inDateTf.getY() - 15, inDateTf.getWidth(), 15);
            finDateTfLb.setBounds(finDateTf.getX(), finDateTf.getY() - 15, finDateTf.getWidth(), 15);
            costTfLb.setBounds(costTf.getX(), costTf.getY() - 15, costTf.getWidth(), 15);

            stTfLb.setBounds(stCBox.getX(), stCBox.getY() - 15, stCBox.getWidth(), 15);

            for (int i = 0; i < tbl1.getRowCount(); i++) {
                mechCBox.addItem(tbl1.getValueAt(i, 1));
            }

            for (int i = 0; i < tbl2.getRowCount(); i++) {
                clCBox.addItem(tbl2.getValueAt(i, 1));
            }

            //Размещение кнопки "OK"
            okBt.setBounds(getWidth() / 2 - bt.width / 2, getHeight() - 100, bt.width, bt.height);
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    e.getWindow().dispose();
                }
            });

            setVisible(true);
        }
        JTextField descTf, inDateTf, finDateTf, costTf;
        //JTextField clTf;
        //JTextField mechTf;
        JComboBox clCBox, mechCBox, stCBox;
        String[] clVars, mechVars, stVars;
        JLabel descTfLb, clTfLb, mechTfLb, inDateTfLb, finDateTfLb, costTfLb, stTfLb;
        JButton okBt;
    }

    class MechDialog extends JDialog {
        public MechDialog(String title) {
            setTitle(title);
            setBounds(fr.getX() + 200, fr.getY() + 200, fr.getWidth() - 500, fr.getHeight() - 400);
            setLayout(null);
            snameTf = new JTextField();
            nameTf = new JTextField();
            secNmTf = new JTextField();
            salaryTf = new JTextField();

            snameTfLb = new JLabel("Фамилия: ");
            nameTfLb = new JLabel("Имя: ");
            secNmTfLb = new JLabel("Отчество: ");
            salTfLb = new JLabel("Зарплата в час: ");

            okBt = new JButton("OK");
            add(snameTf);
            add(nameTf);
            add(secNmTf);
            add(salaryTf);

            add(snameTfLb);
            add(nameTfLb);
            add(secNmTfLb);
            add(salTfLb);

            add(okBt);
            //Размещение компонентов
            snameTf.setBounds(50, 50, 150, 25);
            nameTf.setBounds(snameTf.getX(), snameTf.getY() + snameTf.getHeight() + d, snameTf.getWidth(), snameTf.getHeight());
            secNmTf.setBounds(snameTf.getX(), snameTf.getY() + 2 * (snameTf.getHeight() + d), snameTf.getWidth(), snameTf.getHeight());
            salaryTf.setBounds(snameTf.getX() + snameTf.getWidth() + l, snameTf.getY(), snameTf.getWidth(), snameTf.getHeight());
            snameTfLb.setBounds(snameTf.getX(), snameTf.getY() - 15, snameTf.getWidth(), 15);
            nameTfLb.setBounds(nameTf.getX(), nameTf.getY() - 15, nameTf.getWidth(), 15);
            secNmTfLb.setBounds(secNmTf.getX(), secNmTf.getY() - 15, secNmTf.getWidth(), 15);
            salTfLb.setBounds(salaryTf.getX(), salaryTf.getY() - 15, salaryTf.getWidth(), 15);

            //Размещение кнопки "OK"
            okBt.setBounds(getWidth() / 2 - bt.width / 2, getHeight() - 100, bt.width, bt.height);
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    e.getWindow().dispose();
                }
            });
            setVisible(true);
        }
        JTextField snameTf;
        JTextField nameTf;
        JTextField secNmTf;
        JTextField salaryTf;
        JLabel snameTfLb;
        JLabel nameTfLb;
        JLabel secNmTfLb;
        JLabel salTfLb;

        JButton okBt;
        
    }

    class ClientDialog extends JDialog {
        public ClientDialog(String title) {
            setTitle(title);
            setBounds(fr.getX() + 200, fr.getY() + 200, fr.getWidth() - 500, fr.getHeight() - 400);
            setLayout(null);
            snameTf = new JTextField();
            nameTf = new JTextField();
            secNmTf = new JTextField();
            telTf = new JTextField();

            snameTfLb = new JLabel("Фамилия: ");
            nameTfLb = new JLabel("Имя: ");
            secNmTfLb = new JLabel("Отчество: ");
            telTfLb = new JLabel("Телефон: ");

            okBt = new JButton("OK");
            add(snameTf);
            add(nameTf);
            add(secNmTf);
            add(telTf);

            add(snameTfLb);
            add(nameTfLb);
            add(secNmTfLb);
            add(telTfLb);

            add(okBt);
            //Размещение компонентов
            snameTf.setBounds(50, 50, 150, 25);
            nameTf.setBounds(snameTf.getX(), snameTf.getY() + snameTf.getHeight() + d, snameTf.getWidth(), snameTf.getHeight());
            secNmTf.setBounds(snameTf.getX(), snameTf.getY() + 2 * (snameTf.getHeight() + d), snameTf.getWidth(), snameTf.getHeight());
            telTf.setBounds(snameTf.getX() + snameTf.getWidth() + l, snameTf.getY(), snameTf.getWidth(), snameTf.getHeight());
            snameTfLb.setBounds(snameTf.getX(), snameTf.getY() - 15, snameTf.getWidth(), 15);
            nameTfLb.setBounds(nameTf.getX(), nameTf.getY() - 15, nameTf.getWidth(), 15);
            secNmTfLb.setBounds(secNmTf.getX(), secNmTf.getY() - 15, secNmTf.getWidth(), 15);
            telTfLb.setBounds(telTf.getX(), telTf.getY() - 15, telTf.getWidth(), 15);

            //Размещение кнопки "OK"
            okBt.setBounds(getWidth() / 2 - bt.width / 2, getHeight() - 100, bt.width, bt.height);
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    e.getWindow().dispose();
                }
            });
            setVisible(true);
        }
        JTextField snameTf;
        JTextField nameTf;
        JTextField secNmTf;
        JTextField telTf;
        JLabel snameTfLb;
        JLabel nameTfLb;
        JLabel secNmTfLb;
        JLabel telTfLb;

        JButton okBt;

    }

    //Класс окна сообщения об ошибке из-за не выбранной строки таблицы
    class FailDialog extends JDialog {
        public FailDialog(String title, String message) {

            setTitle(title);
            setBounds(fr.getX() + 250, fr.getY() + 300, 400, 180);
            setLayout(null);;

            messageLb = new JLabel(message);

            okBt = new JButton("OK");

            add(messageLb);

            add(okBt);

            //Размещение компонентов

            messageLb.setBounds(getWidth() / 2 - ((int) (message.length() * 6.5)) / 2, 10, (int) (message.length() * 6.5), 50);

            //Размещение кнопки "OK"
            okBt.setBounds(getWidth() / 2 - 40, messageLb.getY() + messageLb.getHeight() + 20, 80, 40);
            okBt.addActionListener((ActionEvent actEv) -> {
                dispose();
            });
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    e.getWindow().dispose();
                }
            });
            setVisible(true);
        }

        JLabel messageLb;

        JButton okBt;
    }
    
    public static void main (String[] args) {
        new Visual().create();
    }

    class StatsDialog extends JDialog {
        public StatsDialog(String title, String sName, String name, String secNm, int sal, int stats) {

            setTitle(title);
            setBounds(fr.getX() + 200, fr.getY() + 200, fr.getWidth() - 500, fr.getHeight() - 400);
            setLayout(null);

            sNameLbLb = new JLabel("Фамилия: ");
            nameLbLb = new JLabel("Имя: ");
            secNmLbLb = new JLabel("Отчество: ");
            salLbLb = new JLabel("Зарплата: ");
            statsLbLb = new JLabel("Суммарное кличество заказов: ");

            sNameLb = new JLabel(sName);
            nameLb = new JLabel(name);
            secNmLb = new JLabel(secNm);
            salLb = new JLabel(String.valueOf(sal) + " р./час");
            statsLb = new JLabel(String.valueOf(stats));

            Font font = new Font("Verdana", Font.BOLD, 15);
            sNameLb.setFont(font);
            nameLb.setFont(font);;
            secNmLb.setFont(font);
            salLb.setFont(font);
            statsLb.setFont(font);

            okBt = new JButton("OK");
            add(sNameLb);
            add(nameLb);
            add(secNmLb);
            add(salLb);
            add(statsLb);

            add(sNameLbLb);
            add(nameLbLb);
            add(secNmLbLb);
            add(salLbLb);
            add(statsLbLb);

            add(okBt);
            //Размещение компонентов

            sNameLbLb.setBounds(50, 10, 230, 20);
            nameLbLb.setBounds(sNameLbLb.getX(), sNameLbLb.getY() + sNameLbLb.getHeight() + d, sNameLbLb.getWidth(), sNameLbLb.getHeight());
            secNmLbLb.setBounds(sNameLbLb.getX(), nameLbLb.getY() + nameLbLb.getHeight() + d, sNameLbLb.getWidth(), sNameLbLb.getHeight());
            salLbLb.setBounds(sNameLbLb.getX(), secNmLbLb.getY() + secNmLbLb.getHeight() + d, sNameLbLb.getWidth(), sNameLbLb.getHeight());
            statsLbLb.setBounds(sNameLbLb.getX(), salLbLb.getY() + salLbLb.getHeight() + d, sNameLbLb.getWidth(), sNameLbLb.getHeight());

            sNameLb.setBounds(sNameLbLb.getX() + 230, sNameLbLb.getY(), sNameLbLb.getWidth(), sNameLbLb.getHeight());
            nameLb.setBounds(sNameLb.getX(), nameLbLb.getY(), sNameLb.getWidth(), sNameLbLb.getHeight());
            secNmLb.setBounds(sNameLb.getX(), secNmLbLb.getY(), sNameLb.getWidth(), sNameLbLb.getHeight());
            salLb.setBounds(sNameLb.getX(), salLbLb.getY(), sNameLb.getWidth(), sNameLbLb.getHeight());
            statsLb.setBounds(sNameLb.getX(), statsLbLb.getY(), sNameLb.getWidth(), sNameLbLb.getHeight());

            //Размещение кнопки "OK"
            okBt.setBounds(getWidth() / 2 - bt.width / 2, getHeight() - 80, bt.width, bt.height);
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    e.getWindow().dispose();
                }
            });
            okBt.addActionListener( (ActionEvent e) -> {
                dispose();
            });
            setVisible(true);
        }

        JLabel sNameLb, nameLb, secNmLb, salLb, statsLb, sNameLbLb, nameLbLb, secNmLbLb, salLbLb, statsLbLb;

        JButton okBt;

    }
}

class Filter {
    Filter(String desc, String client, Status status) {
        this.desc = desc;
        this.client = client;
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Filter real = (Filter) o;
        return getClient().equals(real.getClient())
                && getDesc().equals(real.getDesc())
                && getStatus().equals(real.getStatus());
    }

    private String desc = "";
    private String client = "";
    private Status status;

    /*public boolean filter(String arg, String filterArg) {
        return !arg.contains(filterArg) && !filterArg.equals("");
    }*/

    public boolean isNotZero() {
        return !equals(new Filter("","",Visual.undefined));
    }

    public boolean filter(Filter values) {
        return (values.getDesc().contains(getDesc()) && !getDesc().equals("")
                || values.getClient().contains(getClient()) && !getClient().equals("")
                || values.getStatus().toString().contains(getStatus().toString()) && !getStatus().toString().equals("")
                || !isNotZero());
    }

}


class Status {
    Status(String status) {
        if (status.equals(SLATED) || status.equals(READY) || status.equals(ACCEPTED)) {
            this.status = status;
        } else this.status = UNDEFINED;
    }
    final static String SLATED = "Запланирован";
    final static String READY = "Выполнен";
    final static String ACCEPTED = "Принят клиентом";
    final static String UNDEFINED = "";

    private String status = UNDEFINED;

    @Override
    public String toString() {
        return status;
    }

    public void setStatus(String status) {
        if (status.equals(SLATED) || status.equals(READY) || status.equals(ACCEPTED)) {
            this.status = status;
        } else this.status = UNDEFINED;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return status.toString().equals(toString());

    }



}


