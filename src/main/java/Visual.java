import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;


public class Visual {
    public Visual() {}

    static final String JDBC_Driver = "com.mysql.cj.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/testbase?useSSL=false";
    Connection connection;
    PreparedStatement statement;
    String sql;
    ResultSet resultSet;
    int l = 25;
    int d = 20;
    JFrame fr;
    Rectangle bt;

    public void create() {

        try {

            //SQL
            Object driver = Class.forName(JDBC_Driver);
            connection = null;
            statement = null;
            connection = DriverManager.getConnection(DATABASE_URL, "root", "1234");

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
            JButton mechStatBt = new JButton("Статистика");

            JButton addClBt = new JButton("Добавить клиента");
            JButton chClBt = new JButton("Изменить данные клиента");
            JButton delClBt = new JButton("Удалить клиента");

            JButton refrBt = new JButton("Обновить таблицы");

            Object[] colounmHeader1 = new Object[] {"ID", "Фамилия", "Имя", "Отчество", "Зарплата, руб/час"};
            Object[] coloumnHeader2 = new Object[] {"ID", "Фамилия", "Имя", "Отчество", "Телефон"};
            Object[] coloumnHeader3 = new Object[] {"ID", "Описание", "Клиент", "Механик", "Дата создания", "Дата выполнения", "Стоимость", "Статус"};

            JTable tbl1 = new JTable(new Object[80][colounmHeader1.length], colounmHeader1);
            JTable tbl2 = new JTable(new Object[80][coloumnHeader2.length], coloumnHeader2);
            JTable tbl3 = new JTable(new Object[80][coloumnHeader3.length], coloumnHeader3);

            Box mechBox = new Box(BoxLayout.Y_AXIS);
            mechBox.add(new JScrollPane(tbl1));
            JLabel mechBxLb = new JLabel("Список механиков: ");
            mechBxLb.setHorizontalAlignment(JLabel.CENTER);
            Font verdana = new Font("Verdana", Font.BOLD, 14);
            mechBxLb.setFont(verdana);

            Box clBox = new Box(BoxLayout.Y_AXIS);
            clBox.add(new JScrollPane(tbl2));
            JLabel clBxLb = new JLabel("Список клиентов: ");
            clBxLb.setHorizontalAlignment(JLabel.CENTER);
            clBxLb.setFont(verdana);

            Box orBox = new Box(BoxLayout.Y_AXIS);
            orBox.add(new JScrollPane(tbl3));
            JLabel orBxLb = new JLabel("Список заказов: ");
            orBxLb.setHorizontalAlignment(JLabel.CENTER);
            orBxLb.setFont(verdana);

            //Создание текстовых полей и надписей фильтра
            TextField clFltr = new TextField();
            TextField statFltr = new TextField();
            TextField descFltr = new TextField();
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

            addOrBt.setBounds(bt);
            addClBt.setBounds(bt.x + bt.width + l, addOrBt.getY(), bt.width, bt.height);
            addMechBt.setBounds(bt.x + 2 * (bt.width + l), addOrBt.getY(), bt.width, bt.height);

            chOrBt.setBounds(bt.x, addOrBt.getY() + addOrBt.getHeight() + d, bt.width, bt.height);
            chClBt.setBounds(bt.x + bt.width + l, chOrBt.getY(), bt.width, bt.height);
            chMechBt.setBounds(bt.x + 2 * (bt.width + l), chOrBt.getY(), bt.width, bt.height);

            delOrBt.setBounds(bt.x, chOrBt.getY() + chOrBt.getHeight() + d, bt.width, bt.height);
            delClBt.setBounds(bt.x + bt.width + l, delOrBt.getY(), bt.width, bt.height);
            delMechBt.setBounds(bt.x + 2 * (bt.width + l), delOrBt.getY(), bt.width, bt.height);

            //Кнопка вывода статистики
            mechStatBt.setBounds(fr.getWidth() - 200, mechBox.getY(), bt.width - 40, bt.height + 40);
            //Кнопка обновления всех таблиц
            refrBt.setBounds(mechStatBt.getX(), fr.getHeight() - 150, mechStatBt.getWidth(), bt.height + 50);

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
                    try {
                        connection.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            fr.setVisible(true);

            //Действие по нажатию кнопки обновления таблиц
            refrBt.addActionListener((ActionEvent e) -> {

                readClients(tbl2);
                readMech(tbl1);
                readOrders(tbl3);

            });

            //Действие по нажатию кнопки добавления заказа
            addOrBt.addActionListener((ActionEvent e) -> {

                //Создание диалогового окна добавления заказа

                OrderDialog dg = new OrderDialog("Добавление заказа");

                dg.okBt.addActionListener((ActionEvent ev) -> {

                    try {

                        sql = "INSERT into orders values (null, '" + dg.descTf.getText() + "', '" +
                                dg.clTf.getText() + "', '" + dg.mechTf.getText() + "', '" + dg.inDateTf.getText() + "', '" +
                                dg.finDateTf.getText() + "', " + dg.costTf.getText() + ", '" + dg.cmbBx.getSelectedItem() + "')";

                        statement = connection.prepareStatement(sql);
                        statement.executeUpdate();

                        readOrders(tbl3);

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
                            if (e.getKeyCode() == 10) dg.okBt.eve

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
                            ((JComboBox) ncomp[i]).setSelectedItem(String.valueOf(tbl3.getValueAt(tbl3.getSelectedRow(), 7)));
                        }
                    }

                    dg.okBt.addActionListener((ActionEvent ev) -> {

                        try {
                            Object rowId = tbl3.getValueAt(tbl3.getSelectedRow(), 0);

                            sql = "UPDATE orders SET description = '" + dg.descTf.getText() + "', client = '" +
                                    dg.clTf.getText() + "', mech = '" + dg.mechTf.getText() + "', init_date = '" + dg.inDateTf.getText() + "', fin_date = '" +
                                    dg.finDateTf.getText() + "', cost = " + dg.costTf.getText() + ", status = '" + dg.cmbBx.getSelectedItem() + "' where id = " + (int) rowId;

                            statement = connection.prepareStatement(sql);
                            statement.executeUpdate();

                            readOrders(tbl3);

                            dg.dispose();


                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }

                    });
                } else {
                    FailDialog dg = new FailDialog("Ошибка!");
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

                        readOrders(tbl3);

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    new FailDialog("Ошибка!");
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

                        readMech(tbl1);

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

                            readMech(tbl1);

                            dg.dispose();


                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }

                    });
                } else {
                    new FailDialog("Ошибка!");
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

                        readMech(tbl1);

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    new FailDialog("Ошибка!");
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

                        readClients(tbl2);

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

                            readClients(tbl2);

                            dg.dispose();


                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }

                    });
                } else {
                    new FailDialog("Ошибка!");
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

                        readClients(tbl2);

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    new FailDialog("Ошибка!");
                }
            });


        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e2) {
            e2.printStackTrace();
        }


    }

    //Объявление метода обновления таблицы персонала
    public void readMech(JTable tbl) {
        try {

            sql = "SELECT * FROM staff";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            int count = 0;
            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                tbl.setValueAt(id, count, 0);
                String name = resultSet.getString("name");
                tbl.setValueAt(name, count, 2);
                String secondname = resultSet.getString("second_name");
                tbl.setValueAt(secondname, count, 3);
                String surname = resultSet.getString("surname");
                tbl.setValueAt(surname, count, 1);
                int salary = resultSet.getInt("salary");
                tbl.setValueAt(salary, count, 4);

                count++;

            }

            resultSet.close();
            statement.close();

            for (int i = count; i < tbl.getRowCount(); i++) {
                for (int j = 0; j < tbl.getColumnCount(); j++)
                    tbl.setValueAt(null, i, j);
            }

        } catch (SQLException e3) {
            e3.printStackTrace();
        }
    }

    //Объявление метода обновление таблицы клиентов
    public void readClients(JTable tbl) {

        try {

            sql = "SELECT * FROM clients";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            int count = 0;

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                tbl.setValueAt(id, count, 0);
                String name = resultSet.getString("name");
                tbl.setValueAt(name, count, 2);
                String secondname = resultSet.getString("second_name");
                tbl.setValueAt(secondname, count, 3);
                String surname = resultSet.getString("surname");
                tbl.setValueAt(surname, count, 1);
                long tel = resultSet.getLong("tel_number");
                tbl.setValueAt(tel, count, 4);

                count++;

            }

            resultSet.close();
            statement.close();

            for (int i = count; i < tbl.getRowCount(); i++) {
                for (int j = 0; j < tbl.getColumnCount(); j++)
                    tbl.setValueAt(null, i, j);
            }


        } catch (SQLException e3) {
            e3.printStackTrace();
        }
    }

    //Объявление метода обновления таблицы заказов
    public  void readOrders(JTable tbl) {
        try {

            sql = "SELECT * FROM orders";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            int count = 0;

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                tbl.setValueAt(id, count, 0);
                String description = resultSet.getString("description");
                tbl.setValueAt(description, count, 1);
                String client = resultSet.getString("client");
                tbl.setValueAt(client, count, 2);
                String mech = resultSet.getString("mech");
                tbl.setValueAt(mech, count, 3);
                String init_time = resultSet.getString("init_date");
                tbl.setValueAt(init_time, count, 4);
                String fin_time = resultSet.getString("fin_date");
                tbl.setValueAt(fin_time, count, 5);
                int cost = resultSet.getInt("cost");
                tbl.setValueAt(cost, count, 6);
                String status = resultSet.getString("status");
                tbl.setValueAt(status, count, 7);

                count++;

            }

            resultSet.close();
            statement.close();

            for (int i = count; i < tbl.getRowCount(); i++) {
                for (int j = 0; j < tbl.getColumnCount(); j++)
                    tbl.setValueAt(null, i, j);
            }

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
            clTf = new JTextField();
            mechTf = new JTextField();
            inDateTf = new JTextField();
            finDateTf = new JTextField();
            costTf = new JTextField();
            //stTf = new JTextField();
            variants = new String[] {"Запланирован", "Выполнен", "Принят клиентом"};
            cmbBx = new JComboBox(variants);
            descTfLb = new JLabel("Описание заказа: ");
            clTfLb = new JLabel("Фамилия клиента: ");
            mechTfLb = new JLabel("Фамилия механика: ");
            inDateTfLb = new JLabel("Дата начала работ: ");
            finDateTfLb = new JLabel("Дата окончания работ: ");
            costTfLb = new JLabel("Стоимость: ");
            stTfLb = new JLabel("Статус заказа: ");
            okBt = new JButton("OK");
            add(descTf);
            add(clTf);
            add(mechTf);
            add(inDateTf);
            add(finDateTf);
            add(costTf);
            //add(stTf);
            add(cmbBx);
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
            clTf.setBounds(descTf.getX(), descTf.getY() + descTf.getHeight() + d, descTf.getWidth(), descTf.getHeight());
            mechTf.setBounds(descTf.getX(), descTf.getY() + 2 * (descTf.getHeight() + d), descTf.getWidth(), descTf.getHeight());
            costTf.setBounds(descTf.getX(), descTf.getY() + 3 * (descTf.getHeight() + d), descTf.getWidth(), descTf.getHeight());
            inDateTf.setBounds(descTf.getX() + descTf.getWidth() + l, descTf.getY(), descTf.getWidth(), descTf.getHeight());
            finDateTf.setBounds(inDateTf.getX(), clTf.getY(), descTf.getWidth(), descTf.getHeight());
            //stTf.setBounds(inDateTf.getX(), mechTf.getY(), descTf.getWidth(), descTf.getHeight());
            cmbBx.setBounds(inDateTf.getX(), mechTf.getY(), descTf.getWidth(), descTf.getHeight());
            descTfLb.setBounds(descTf.getX(), descTf.getY() - 15, descTf.getWidth(), 15);
            clTfLb.setBounds(clTf.getX(), clTf.getY() - 15, clTf.getWidth(), 15);
            mechTfLb.setBounds(mechTf.getX(), mechTf.getY() - 15, mechTf.getWidth(), 15);
            inDateTfLb.setBounds(inDateTf.getX(), inDateTf.getY() - 15, inDateTf.getWidth(), 15);
            finDateTfLb.setBounds(finDateTf.getX(), finDateTf.getY() - 15, finDateTf.getWidth(), 15);
            costTfLb.setBounds(costTf.getX(), costTf.getY() - 15, costTf.getWidth(), 15);
            //stTfLb.setBounds(stTf.getX(), stTf.getY() - 15, stTf.getWidth(), 15);
            stTfLb.setBounds(cmbBx.getX(), cmbBx.getY() - 15, cmbBx.getWidth(), 15);
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
        JTextField descTf;
        JTextField clTf;
        JTextField mechTf;
        JTextField inDateTf;
        JTextField finDateTf;
        JTextField costTf;
        //JTextField stTf;
        String[] variants;
        JComboBox cmbBx;
        JLabel descTfLb;
        JLabel clTfLb;
        JLabel mechTfLb;
        JLabel inDateTfLb;
        JLabel finDateTfLb;
        JLabel costTfLb;
        JLabel stTfLb;
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
        public FailDialog(String title) {

            setTitle(title);
            setBounds(fr.getX() + 250, fr.getY() + 250, 300, 180);
            setLayout(null);;

            messageLb = new JLabel("Изменяемая строка не выбрана!");

            okBt = new JButton("OK");

            add(messageLb);

            add(okBt);

            //Размещение компонентов

            messageLb.setBounds(40, 10, getWidth(), 50);

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
}






