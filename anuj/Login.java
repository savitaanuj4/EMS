
package anuj;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Component;
import javax.swing.border.Border;
import javax.swing.border.BevelBorder;
import javax.swing.JPanel;
import java.awt.LayoutManager;
import java.awt.Window;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.EventQueue;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JFrame;

public class Login
{
    private JFrame frmEmployeeManagementSystem;
    private JTextField usernameTxt;
    private JPasswordField passwordTxt;
    private JLabel invalidLabel;
    
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final Login window = new Login();
                    window.frmEmployeeManagementSystem.setVisible(true);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public Login() {
        this.initialize();
    }
    
    private void initialize() {
        (this.frmEmployeeManagementSystem = new JFrame()).setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Anuj\\Desktop\\ems.png"));
        this.frmEmployeeManagementSystem.setResizable(false);
        this.frmEmployeeManagementSystem.getContentPane().setBackground(Color.LIGHT_GRAY);
        this.frmEmployeeManagementSystem.setBackground(Color.GRAY);
        this.frmEmployeeManagementSystem.setAlwaysOnTop(true);
        this.frmEmployeeManagementSystem.setTitle("Employee Management System");
        this.frmEmployeeManagementSystem.setType(Window.Type.UTILITY);
        this.frmEmployeeManagementSystem.setBounds(100, 100, 424, 174);
        this.frmEmployeeManagementSystem.setDefaultCloseOperation(3);
        this.frmEmployeeManagementSystem.getContentPane().setLayout(null);
        final JPanel panel = new JPanel();
        panel.setBackground(Color.PINK);
        panel.setForeground(Color.BLACK);
        panel.setBorder(new BevelBorder(0, Color.DARK_GRAY, Color.BLACK, Color.DARK_GRAY, Color.BLUE));
        panel.setBounds(10, 34, 286, 105);
        this.frmEmployeeManagementSystem.getContentPane().add(panel);
        panel.setLayout(null);
        final JLabel lblUserName = new JLabel("User Name");
        lblUserName.setFont(new Font("Tahoma", 1, 15));
        lblUserName.setBounds(10, 26, 104, 14);
        panel.add(lblUserName);
        final JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Tahoma", 1, 15));
        lblPassword.setBounds(10, 64, 104, 14);
        panel.add(lblPassword);
        (this.usernameTxt = new JTextField()).setBackground(Color.CYAN);
        this.usernameTxt.setBounds(136, 25, 125, 20);
        panel.add(this.usernameTxt);
        this.usernameTxt.setColumns(10);
        (this.passwordTxt = new JPasswordField()).setBackground(Color.CYAN);
        this.passwordTxt.setBounds(135, 63, 126, 20);
        panel.add(this.passwordTxt);
        (this.invalidLabel = new JLabel("invalid username or password!")).setForeground(Color.RED);
        this.invalidLabel.setHorizontalAlignment(0);
        this.invalidLabel.setBounds(10, 89, 251, 14);
        this.invalidLabel.setVisible(false);
        panel.add(this.invalidLabel);
        final JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final String username = Login.this.usernameTxt.getText();
                final String password = Login.this.passwordTxt.getText();
                if (username.equals("savitaanuj4") && password.equals("1234")) {
                    final EmsMainWindow ems = new EmsMainWindow();
                    ems.setVisible(true);
                    Login.this.frmEmployeeManagementSystem.setVisible(false);
                }
                else {
                    Login.this.invalidLabel.setVisible(true);
                }
            }
        });
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBackground(Color.BLUE);
        btnLogin.setFont(new Font("Tahoma", 3, 15));
        btnLogin.setBounds(306, 96, 89, 23);
        this.frmEmployeeManagementSystem.getContentPane().add(btnLogin);
        final JButton btnReset = new JButton("Reset");
        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                Login.this.usernameTxt.setText("");
                Login.this.passwordTxt.setText("");
            }
        });
        btnReset.setForeground(Color.WHITE);
        btnReset.setBackground(Color.BLUE);
        btnReset.setFont(new Font("Tahoma", 3, 15));
        btnReset.setBounds(306, 51, 89, 23);
        this.frmEmployeeManagementSystem.getContentPane().add(btnReset);
        final JLabel lblAdminLogin = new JLabel("Admin Login");
        lblAdminLogin.setFont(new Font("Trebuchet MS", 1, 15));
        lblAdminLogin.setBounds(94, 9, 135, 23);
        this.frmEmployeeManagementSystem.getContentPane().add(lblAdminLogin);
    }
}
