
package anuj;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JSeparator;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.LayoutManager;
import java.awt.Container;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.Window;
import java.awt.EventQueue;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class AddEmployee extends JFrame
{
    private JPanel contentPane;
    private JTextField idTxt;
    private JTextField nameTxt;
    private JTextField designationTxt;
    private JTextField dojTxt;
    private JTextField cityTxt;
    private JTextField mobileTxt;
    private JTextField emailTxt;
    private JComboBox shiftCombo;
    
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final AddEmployee frame = new AddEmployee();
                    frame.setVisible(true);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public AddEmployee() {
        this.setResizable(false);
        this.setType(Type.UTILITY);
        this.setDefaultCloseOperation(2);
        this.setBounds(100, 100, 295, 365);
        (this.contentPane = new JPanel()).setBackground(Color.PINK);
        this.contentPane.setBorder(new EtchedBorder(1, Color.PINK, Color.GREEN));
        this.setContentPane(this.contentPane);
        this.contentPane.setLayout(null);
        final JLabel lblAddEmployees = new JLabel("Add Employees");
        lblAddEmployees.setBackground(Color.ORANGE);
        lblAddEmployees.setForeground(Color.GRAY);
        lblAddEmployees.setFont(new Font("Tahoma", 1, 18));
        lblAddEmployees.setHorizontalAlignment(0);
        lblAddEmployees.setBounds(10, 11, 259, 29);
        this.contentPane.add(lblAddEmployees);
        final JSeparator separator = new JSeparator();
        separator.setBounds(10, 38, 259, 2);
        this.contentPane.add(separator);
        final JLabel lblEmpId = new JLabel("Emp Id");
        lblEmpId.setFont(new Font("Traditional Arabic", 0, 14));
        lblEmpId.setBounds(20, 58, 68, 14);
        this.contentPane.add(lblEmpId);
        final JLabel lblName = new JLabel("Name");
        lblName.setFont(new Font("Traditional Arabic", 0, 14));
        lblName.setBounds(20, 84, 68, 14);
        this.contentPane.add(lblName);
        final JLabel lblDesignation = new JLabel("Designation");
        lblDesignation.setFont(new Font("Traditional Arabic", 0, 14));
        lblDesignation.setBounds(20, 109, 68, 14);
        this.contentPane.add(lblDesignation);
        final JLabel lblSalary = new JLabel("D-O-J");
        lblSalary.setFont(new Font("Traditional Arabic", 0, 14));
        lblSalary.setBounds(20, 134, 68, 14);
        this.contentPane.add(lblSalary);
        final JLabel lblCity = new JLabel("City");
        lblCity.setFont(new Font("Traditional Arabic", 0, 14));
        lblCity.setBounds(20, 161, 68, 14);
        this.contentPane.add(lblCity);
        final JLabel lblMobile = new JLabel("Mobile");
        lblMobile.setFont(new Font("Traditional Arabic", 0, 14));
        lblMobile.setBounds(20, 187, 68, 14);
        this.contentPane.add(lblMobile);
        final JLabel lblEmail = new JLabel("Email");
        lblEmail.setFont(new Font("Traditional Arabic", 0, 14));
        lblEmail.setBounds(20, 212, 68, 14);
        this.contentPane.add(lblEmail);
        (this.idTxt = new JTextField()).setBackground(new Color(0, 255, 255));
        this.idTxt.setBounds(130, 55, 128, 20);
        this.contentPane.add(this.idTxt);
        this.idTxt.setColumns(10);
        (this.nameTxt = new JTextField()).setBackground(new Color(0, 255, 255));
        this.nameTxt.setColumns(10);
        this.nameTxt.setBounds(130, 81, 128, 20);
        this.contentPane.add(this.nameTxt);
        (this.designationTxt = new JTextField()).setBackground(new Color(0, 255, 255));
        this.designationTxt.setColumns(10);
        this.designationTxt.setBounds(130, 106, 128, 20);
        this.contentPane.add(this.designationTxt);
        (this.dojTxt = new JTextField()).setBackground(new Color(0, 255, 255));
        this.dojTxt.setColumns(10);
        this.dojTxt.setBounds(130, 131, 128, 20);
        this.contentPane.add(this.dojTxt);
        (this.cityTxt = new JTextField()).setBackground(new Color(0, 255, 255));
        this.cityTxt.setColumns(10);
        this.cityTxt.setBounds(130, 158, 128, 20);
        this.contentPane.add(this.cityTxt);
        (this.mobileTxt = new JTextField()).setBackground(new Color(0, 255, 255));
        this.mobileTxt.setColumns(10);
        this.mobileTxt.setBounds(130, 184, 128, 20);
        this.contentPane.add(this.mobileTxt);
        (this.emailTxt = new JTextField()).setBackground(new Color(0, 255, 255));
        this.emailTxt.setColumns(10);
        this.emailTxt.setBounds(130, 209, 128, 20);
        this.contentPane.add(this.emailTxt);
        final JButton btnAdd = new JButton("ADD");
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setBackground(Color.BLUE);
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final int id = Integer.valueOf(AddEmployee.this.idTxt.getText());
                final String name = AddEmployee.this.nameTxt.getText();
                final String designation = AddEmployee.this.designationTxt.getText();
                final String doj = AddEmployee.this.dojTxt.getText();
                final String city = AddEmployee.this.cityTxt.getText();
                final long mobile = Long.parseLong(AddEmployee.this.mobileTxt.getText());
                final String email = AddEmployee.this.emailTxt.getText();
                final String shift = AddEmployee.this.shiftCombo.getSelectedItem().toString();
                if (DAO.addEmployee(id, name, designation, doj, city, mobile, email, shift)) {
                    JOptionPane.showMessageDialog(null, "Record Added Succesfully");
                    AddEmployee.this.idTxt.setText("");
                    AddEmployee.this.nameTxt.setText("");
                    AddEmployee.this.designationTxt.setText("");
                    AddEmployee.this.dojTxt.setText("");
                    AddEmployee.this.cityTxt.setText("");
                    AddEmployee.this.mobileTxt.setText("");
                    AddEmployee.this.emailTxt.setText("");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Error");
                }
            }
        });
        btnAdd.setFont(new Font("Tahoma", 1, 15));
        btnAdd.setBounds(84, 294, 116, 23);
        this.contentPane.add(btnAdd);
        final JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(10, 276, 259, 2);
        this.contentPane.add(separator_1);
        final JLabel shiftLab = new JLabel("Shift");
        shiftLab.setFont(new Font("Traditional Arabic", 0, 14));
        shiftLab.setBounds(19, 238, 68, 14);
        this.contentPane.add(shiftLab);
        (this.shiftCombo = new JComboBox()).setBackground(new Color(0, 255, 255));
        this.shiftCombo.setModel(new DefaultComboBoxModel<String>(new String[] { "Day", "Night" }));
        this.shiftCombo.setBounds(130, 240, 128, 20);
        this.contentPane.add(this.shiftCombo);
    }
}
