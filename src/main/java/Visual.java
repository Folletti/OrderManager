import javax.swing.*;
import java.awt.*;

public class Visual {
    public Visual() {}


    public void create() {

        JFrame fr = new JFrame("JDBC");
        JPanel pn = new JPanel();
        pn.setLayout(new BorderLayout());
        fr.setLayout(new BorderLayout());

        JButton bt1 = new JButton("Добавить заказ");
        JButton bt2 = new JButton("Удалить заказ");
        JButton bt3 = new JButton("Добавить механика");
        JButton bt4 = new JButton("Удалить механика");
        JTextField tf1 = new JTextField();
        JTextArea ta1 = new JTextArea();

        fr.add(pn, "CENTER");
        pn.add(bt1, "SOUTH");
        pn.add(bt2, "SOUTH");
        pn.add(bt3, "SOUTH");
        pn.add(bt4, "SOUTH");
        pn.add(tf1, "CENTER");
        pn.add(ta1, "NORTH");

       // fr.addWindowListener();


        fr.setVisible(true);

    }



}
