
package anuj;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
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
import javax.swing.border.SoftBevelBorder;
import java.awt.Window;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class RemoveEmployee extends JFrame
{
    private JPanel contentPane;
    private JTextField idTxt;
    private JTextField nameTxt;
    private JTextField designationTxt;
    private JTextField dojTxt;
    private JTextField cityTxt;
    private JTextField mobileTxt;
    private JTextField emailTxt;
    
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final RemoveEmployee frame = new RemoveEmployee();
                    frame.setVisible(true);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public RemoveEmployee() {
        this.setBackground(Color.PINK);
        this.setResizable(false);
        this.setType(Type.UTILITY);
        this.setDefaultCloseOperation(2);
        this.setBounds(100, 100, 349, 357);
        (this.contentPane = new JPanel()).setBackground(Color.PINK);
        this.contentPane.setBorder(new SoftBevelBorder(0, Color.CYAN, Color.MAGENTA, Color.MAGENTA, Color.CYAN));
        this.setContentPane(this.contentPane);
        this.contentPane.setLayout(null);
        final JLabel lblRemoveEmployee = new JLabel("Remove Employee");
        lblRemoveEmployee.setHorizontalAlignment(0);
        lblRemoveEmployee.setForeground(Color.GRAY);
        lblRemoveEmployee.setFont(new Font("Tahoma", 1, 18));
        lblRemoveEmployee.setBackground(Color.ORANGE);
        lblRemoveEmployee.setBounds(10, 0, 323, 29);
        this.contentPane.add(lblRemoveEmployee);
        final JSeparator separator = new JSeparator();
        separator.setBounds(10, 27, 323, 2);
        this.contentPane.add(separator);
        final JLabel label_1 = new JLabel("Emp Id");
        label_1.setFont(new Font("Traditional Arabic", 0, 14));
        label_1.setBounds(20, 47, 68, 14);
        this.contentPane.add(label_1);
        final JLabel label_2 = new JLabel("Name");
        label_2.setFont(new Font("Traditional Arabic", 0, 14));
        label_2.setBounds(20, 73, 68, 14);
        this.contentPane.add(label_2);
        final JLabel label_3 = new JLabel("Designation");
        label_3.setFont(new Font("Traditional Arabic", 0, 14));
        label_3.setBounds(20, 98, 68, 14);
        this.contentPane.add(label_3);
        final JLabel label_4 = new JLabel("D-O-J");
        label_4.setFont(new Font("Traditional Arabic", 0, 14));
        label_4.setBounds(20, 123, 68, 14);
        this.contentPane.add(label_4);
        final JLabel label_5 = new JLabel("City");
        label_5.setFont(new Font("Traditional Arabic", 0, 14));
        label_5.setBounds(20, 150, 68, 14);
        this.contentPane.add(label_5);
        final JLabel label_6 = new JLabel("Mobile");
        label_6.setFont(new Font("Traditional Arabic", 0, 14));
        label_6.setBounds(20, 176, 68, 14);
        this.contentPane.add(label_6);
        final JLabel label_7 = new JLabel("Email");
        label_7.setFont(new Font("Traditional Arabic", 0, 14));
        label_7.setBounds(20, 201, 68, 14);
        this.contentPane.add(label_7);
        (this.idTxt = new JTextField()).setColumns(10);
        this.idTxt.setBackground(Color.CYAN);
        this.idTxt.setBounds(130, 44, 128, 20);
        this.contentPane.add(this.idTxt);
        (this.nameTxt = new JTextField()).setColumns(10);
        this.nameTxt.setBackground(Color.CYAN);
        this.nameTxt.setBounds(130, 70, 128, 20);
        this.contentPane.add(this.nameTxt);
        (this.designationTxt = new JTextField()).setColumns(10);
        this.designationTxt.setBackground(Color.CYAN);
        this.designationTxt.setBounds(130, 95, 128, 20);
        this.contentPane.add(this.designationTxt);
        (this.dojTxt = new JTextField()).setColumns(10);
        this.dojTxt.setBackground(Color.CYAN);
        this.dojTxt.setBounds(130, 120, 128, 20);
        this.contentPane.add(this.dojTxt);
        (this.cityTxt = new JTextField()).setColumns(10);
        this.cityTxt.setBackground(Color.CYAN);
        this.cityTxt.setBounds(130, 147, 128, 20);
        this.contentPane.add(this.cityTxt);
        (this.mobileTxt = new JTextField()).setColumns(10);
        this.mobileTxt.setBackground(Color.CYAN);
        this.mobileTxt.setBounds(130, 173, 128, 20);
        this.contentPane.add(this.mobileTxt);
        (this.emailTxt = new JTextField()).setColumns(10);
        this.emailTxt.setBackground(Color.CYAN);
        this.emailTxt.setBounds(130, 198, 128, 20);
        this.contentPane.add(this.emailTxt);
        final JButton btnRemove = new JButton("Remove");
        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final int emp_id = Integer.valueOf(RemoveEmployee.this.idTxt.getText());
                final int cnf = JOptionPane.showConfirmDialog(null, "Do yo really want to delete this data!");
                if (cnf == 0) {
                    if (DAO.removeEmployee(emp_id)) {
                        JOptionPane.showMessageDialog(null, "data removed");
                        RemoveEmployee.this.idTxt.setText("");
                        RemoveEmployee.this.nameTxt.setText("");
                        RemoveEmployee.this.designationTxt.setText("");
                        RemoveEmployee.this.dojTxt.setText("");
                        RemoveEmployee.this.cityTxt.setText("");
                        RemoveEmployee.this.mobileTxt.setText("");
                        RemoveEmployee.this.emailTxt.setText("");
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Error");
                    }
                }
            }
        });
        btnRemove.setForeground(Color.WHITE);
        btnRemove.setFont(new Font("Tahoma", 1, 15));
        btnRemove.setBackground(Color.BLUE);
        btnRemove.setBounds(109, 278, 116, 23);
        this.contentPane.add(btnRemove);
        final JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(10, 265, 323, 2);
        this.contentPane.add(separator_1);
        final JLabel label_8 = new JLabel("Shift");
        label_8.setFont(new Font("Traditional Arabic", 0, 14));
        label_8.setBounds(19, 227, 68, 14);
        this.contentPane.add(label_8);
        final JComboBox comboBox = new JComboBox();
        comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Day", "Night" }));
        comboBox.setBackground(Color.CYAN);
        comboBox.setBounds(130, 229, 128, 20);
        this.contentPane.add(comboBox);
        final JButton btnFind = new JButton("Find");
        btnFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final int emp_id = Integer.valueOf(RemoveEmployee.this.idTxt.getText());
                final ResultSet rs = DAO.searchEmployee(emp_id);
                try {
                    if (rs.next()) {
                        RemoveEmployee.this.nameTxt.setText(rs.getString("name"));
                        RemoveEmployee.this.designationTxt.setText(rs.getString("designation"));
                        RemoveEmployee.this.dojTxt.setText(rs.getString("doj"));
                        RemoveEmployee.this.cityTxt.setText(rs.getString("city"));
                        RemoveEmployee.this.mobileTxt.setText(rs.getString("mobile"));
                        RemoveEmployee.this.emailTxt.setText(rs.getString("email"));
                        comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { rs.getString("shift") }));
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
        btnFind.setBounds(268, 43, 68, 23);
        this.contentPane.add(btnFind);
        final JButton resetBtn = new JButton("Reset");
        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                RemoveEmployee.this.idTxt.setText("");
                RemoveEmployee.this.nameTxt.setText("");
                RemoveEmployee.this.designationTxt.setText("");
                RemoveEmployee.this.dojTxt.setText("");
                RemoveEmployee.this.cityTxt.setText("");
                RemoveEmployee.this.mobileTxt.setText("");
                RemoveEmployee.this.emailTxt.setText("");
            }
        });
        resetBtn.setForeground(Color.WHITE);
        resetBtn.setFont(new Font("Tahoma", 1, 10));
        resetBtn.setBackground(Color.BLUE);
        resetBtn.setBounds(268, 69, 68, 23);
        this.contentPane.add(resetBtn);
    }
}
