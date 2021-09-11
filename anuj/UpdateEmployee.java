
package anuj;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JSeparator;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.Container;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.Window;
import java.awt.EventQueue;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class UpdateEmployee extends JFrame
{
    private JPanel contentPane;
    private JTextField idTxt;
    private JTextField nameTxt;
    private JTextField designationTxt;
    private JTextField dojTxt;
    private JTextField cityTxt;
    private JTextField mobileTxt;
    private JTextField emailTxt;
    private JComboBox comboBox;
    
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final UpdateEmployee frame = new UpdateEmployee();
                    frame.setVisible(true);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public UpdateEmployee() {
        this.setResizable(false);
        this.setType(Type.UTILITY);
        this.setDefaultCloseOperation(2);
        this.setBounds(100, 100, 353, 380);
        (this.contentPane = new JPanel()).setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(this.contentPane);
        this.contentPane.setLayout(null);
        final JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBorder(new EtchedBorder(1, Color.PINK, Color.GREEN));
        panel.setBackground(Color.PINK);
        panel.setBounds(0, 0, 343, 345);
        this.contentPane.add(panel);
        final JLabel lblUpdateEmployees = new JLabel("Update Employees");
        lblUpdateEmployees.setHorizontalAlignment(0);
        lblUpdateEmployees.setForeground(Color.GRAY);
        lblUpdateEmployees.setFont(new Font("Tahoma", 1, 18));
        lblUpdateEmployees.setBackground(Color.ORANGE);
        lblUpdateEmployees.setBounds(10, 11, 323, 29);
        panel.add(lblUpdateEmployees);
        final JSeparator separator = new JSeparator();
        separator.setBounds(10, 38, 323, 2);
        panel.add(separator);
        final JLabel label_1 = new JLabel("Emp Id");
        label_1.setFont(new Font("Traditional Arabic", 0, 14));
        label_1.setBounds(20, 58, 68, 14);
        panel.add(label_1);
        final JLabel label_2 = new JLabel("Name");
        label_2.setFont(new Font("Traditional Arabic", 0, 14));
        label_2.setBounds(20, 84, 68, 14);
        panel.add(label_2);
        final JLabel label_3 = new JLabel("Designation");
        label_3.setFont(new Font("Traditional Arabic", 0, 14));
        label_3.setBounds(20, 109, 68, 14);
        panel.add(label_3);
        final JLabel label_4 = new JLabel("D-O-J");
        label_4.setFont(new Font("Traditional Arabic", 0, 14));
        label_4.setBounds(20, 134, 68, 14);
        panel.add(label_4);
        final JLabel label_5 = new JLabel("City");
        label_5.setFont(new Font("Traditional Arabic", 0, 14));
        label_5.setBounds(20, 161, 68, 14);
        panel.add(label_5);
        final JLabel label_6 = new JLabel("Mobile");
        label_6.setFont(new Font("Traditional Arabic", 0, 14));
        label_6.setBounds(20, 187, 68, 14);
        panel.add(label_6);
        final JLabel label_7 = new JLabel("Email");
        label_7.setFont(new Font("Traditional Arabic", 0, 14));
        label_7.setBounds(20, 212, 68, 14);
        panel.add(label_7);
        (this.idTxt = new JTextField()).setColumns(10);
        this.idTxt.setBackground(Color.CYAN);
        this.idTxt.setBounds(130, 55, 128, 20);
        panel.add(this.idTxt);
        (this.nameTxt = new JTextField()).setColumns(10);
        this.nameTxt.setBackground(Color.CYAN);
        this.nameTxt.setBounds(130, 81, 128, 20);
        panel.add(this.nameTxt);
        (this.designationTxt = new JTextField()).setColumns(10);
        this.designationTxt.setBackground(Color.CYAN);
        this.designationTxt.setBounds(130, 106, 128, 20);
        panel.add(this.designationTxt);
        (this.dojTxt = new JTextField()).setColumns(10);
        this.dojTxt.setBackground(Color.CYAN);
        this.dojTxt.setBounds(130, 131, 128, 20);
        panel.add(this.dojTxt);
        (this.cityTxt = new JTextField()).setColumns(10);
        this.cityTxt.setBackground(Color.CYAN);
        this.cityTxt.setBounds(130, 158, 128, 20);
        panel.add(this.cityTxt);
        (this.mobileTxt = new JTextField()).setColumns(10);
        this.mobileTxt.setBackground(Color.CYAN);
        this.mobileTxt.setBounds(130, 184, 128, 20);
        panel.add(this.mobileTxt);
        (this.emailTxt = new JTextField()).setColumns(10);
        this.emailTxt.setBackground(Color.CYAN);
        this.emailTxt.setBounds(130, 209, 128, 20);
        panel.add(this.emailTxt);
        final JButton btnUpdate = new JButton("UPDATE");
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final int id = Integer.valueOf(UpdateEmployee.this.idTxt.getText());
                final String name = UpdateEmployee.this.nameTxt.getText();
                final String designation = UpdateEmployee.this.designationTxt.getText();
                final String doj = UpdateEmployee.this.dojTxt.getText();
                final String city = UpdateEmployee.this.cityTxt.getText();
                final long mobile = Long.parseLong(UpdateEmployee.this.mobileTxt.getText());
                final String email = UpdateEmployee.this.emailTxt.getText();
                final String shift = UpdateEmployee.this.comboBox.getSelectedItem().toString();
                if (DAO.updateEmployee(name, doj, designation, city, mobile, email, shift, id)) {
                    JOptionPane.showMessageDialog(null, "Record Updated");
                    UpdateEmployee.this.idTxt.setText("");
                    UpdateEmployee.this.nameTxt.setText("");
                    UpdateEmployee.this.designationTxt.setText("");
                    UpdateEmployee.this.dojTxt.setText("");
                    UpdateEmployee.this.cityTxt.setText("");
                    UpdateEmployee.this.mobileTxt.setText("");
                    UpdateEmployee.this.emailTxt.setText("");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Error");
                }
            }
        });
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setFont(new Font("Tahoma", 1, 15));
        btnUpdate.setBackground(Color.BLUE);
        btnUpdate.setBounds(84, 294, 116, 23);
        panel.add(btnUpdate);
        final JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(10, 276, 323, 2);
        panel.add(separator_1);
        final JLabel label_8 = new JLabel("Shift");
        label_8.setFont(new Font("Traditional Arabic", 0, 14));
        label_8.setBounds(19, 238, 68, 14);
        panel.add(label_8);
        (this.comboBox = new JComboBox()).setModel(new DefaultComboBoxModel<String>(new String[] { "Day", "Night" }));
        this.comboBox.setBackground(Color.CYAN);
        this.comboBox.setBounds(130, 240, 128, 20);
        panel.add(this.comboBox);
        final JButton btnFind = new JButton("Find");
        btnFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final int emp_id = Integer.valueOf(UpdateEmployee.this.idTxt.getText());
                final ResultSet rs = DAO.searchEmployee(emp_id);
                try {
                    if (rs.next()) {
                        UpdateEmployee.this.nameTxt.setText(rs.getString("name"));
                        UpdateEmployee.this.designationTxt.setText(rs.getString("designation"));
                        UpdateEmployee.this.dojTxt.setText(rs.getString("doj"));
                        UpdateEmployee.this.cityTxt.setText(rs.getString("city"));
                        UpdateEmployee.this.mobileTxt.setText(rs.getString("mobile"));
                        UpdateEmployee.this.emailTxt.setText(rs.getString("email"));
                        UpdateEmployee.this.comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { rs.getString("shift"), "Day", "Night" }));
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "record not found");
                    }
                }
                catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
        });
        btnFind.setForeground(Color.WHITE);
        btnFind.setFont(new Font("Tahoma", 1, 10));
        btnFind.setBackground(Color.BLUE);
        btnFind.setBounds(268, 54, 68, 23);
        panel.add(btnFind);
        final JButton resetBtn = new JButton("Reset");
        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                UpdateEmployee.this.idTxt.setText("");
                UpdateEmployee.this.nameTxt.setText("");
                UpdateEmployee.this.designationTxt.setText("");
                UpdateEmployee.this.dojTxt.setText("");
                UpdateEmployee.this.cityTxt.setText("");
                UpdateEmployee.this.mobileTxt.setText("");
                UpdateEmployee.this.emailTxt.setText("");
            }
        });
        resetBtn.setForeground(Color.WHITE);
        resetBtn.setFont(new Font("Tahoma", 1, 10));
        resetBtn.setBackground(Color.BLUE);
        resetBtn.setBounds(268, 80, 68, 23);
        panel.add(resetBtn);
    }
}
