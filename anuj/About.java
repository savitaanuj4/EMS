
package anuj;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JEditorPane;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.Window;
import java.awt.EventQueue;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class About extends JFrame
{
    private JPanel contentPane;
    
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final About frame = new About();
                    frame.setVisible(true);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public About() {
        this.setType(Type.UTILITY);
        this.setDefaultCloseOperation(2);
        this.setBounds(100, 100, 576, 204);
        (this.contentPane = new JPanel()).setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(this.contentPane);
        this.contentPane.setLayout(new BorderLayout(0, 0));
        final JPanel panel = new JPanel();
        this.contentPane.add(panel);
        panel.setLayout(new BorderLayout(0, 0));
        final JEditorPane dtrpnAnEmployeeManagement = new JEditorPane();
        dtrpnAnEmployeeManagement.setBackground(Color.PINK);
        dtrpnAnEmployeeManagement.setFont(new Font("Tahoma", 1, 14));
        dtrpnAnEmployeeManagement.setText("Employee management system is designed to simplify the   process of record maintenance of employees in an organisation. It helps in managing the information of employees for HR functions. In general, employee management system is a part of a comprehensive Human Resource Management System.\r\n\r\n\r\n");
        panel.add(dtrpnAnEmployeeManagement);
        final JPanel panel_1 = new JPanel();
        panel_1.setBackground(Color.GRAY);
        panel.add(panel_1, "South");
        final JLabel label = new JLabel("Designed&Devloped BY: Anuj Savita, Vishnu Gupta");
        label.setHorizontalAlignment(0);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Tahoma", 1, 15));
        label.setBackground(Color.BLACK);
        panel_1.add(label);
    }
}
