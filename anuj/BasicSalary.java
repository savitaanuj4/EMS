
package anuj;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JSeparator;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.border.SoftBevelBorder;
import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.Container;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.Window;
import java.awt.EventQueue;
import java.sql.ResultSet;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class BasicSalary extends JFrame
{
    private JPanel contentPane;
    private JTextField idTxt;
    private JTextField nameTxt;
    private JTextField designationTxt;
    private JTextField salaryTxt;
    ResultSet rs;
    
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final BasicSalary frame = new BasicSalary();
                    frame.setVisible(true);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public BasicSalary() {
        this.setType(Type.UTILITY);
        this.setDefaultCloseOperation(2);
        this.setBounds(100, 100, 393, 241);
        (this.contentPane = new JPanel()).setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(this.contentPane);
        this.contentPane.setLayout(null);
        final JPanel panel = new JPanel();
        panel.setBackground(Color.PINK);
        panel.setBorder(new SoftBevelBorder(0, Color.CYAN, Color.MAGENTA, Color.MAGENTA, Color.CYAN));
        panel.setBounds(0, 0, 371, 196);
        this.contentPane.add(panel);
        panel.setLayout(null);
        final JLabel lblBasicSalary = new JLabel("Basic Salary");
        lblBasicSalary.setHorizontalAlignment(0);
        lblBasicSalary.setFont(new Font("Tahoma", 1, 15));
        lblBasicSalary.setBounds(10, 11, 351, 24);
        panel.add(lblBasicSalary);
        final JSeparator separator = new JSeparator();
        separator.setBounds(10, 36, 351, 7);
        panel.add(separator);
        final JLabel label = new JLabel("Emp Id");
        label.setFont(new Font("Traditional Arabic", 0, 14));
        label.setBounds(20, 50, 68, 14);
        panel.add(label);
        final JLabel label_1 = new JLabel("Name");
        label_1.setFont(new Font("Traditional Arabic", 0, 14));
        label_1.setBounds(20, 76, 68, 14);
        panel.add(label_1);
        final JLabel label_2 = new JLabel("Designation");
        label_2.setFont(new Font("Traditional Arabic", 0, 14));
        label_2.setBounds(20, 101, 68, 14);
        panel.add(label_2);
        (this.idTxt = new JTextField()).setColumns(10);
        this.idTxt.setBackground(Color.CYAN);
        this.idTxt.setBounds(130, 47, 128, 20);
        panel.add(this.idTxt);
        (this.nameTxt = new JTextField()).setColumns(10);
        this.nameTxt.setBackground(Color.CYAN);
        this.nameTxt.setBounds(130, 73, 128, 20);
        panel.add(this.nameTxt);
        (this.designationTxt = new JTextField()).setColumns(10);
        this.designationTxt.setBackground(Color.CYAN);
        this.designationTxt.setBounds(130, 98, 128, 20);
        panel.add(this.designationTxt);
        final JButton findBtn = new JButton("Find");
        findBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final int id = Integer.valueOf(BasicSalary.this.idTxt.getText());
                BasicSalary.this.rs = DAO.searchEmployee(id);
                try {
                    if (BasicSalary.this.rs.next()) {
                        BasicSalary.this.nameTxt.setText(BasicSalary.this.rs.getString("name"));
                        BasicSalary.this.designationTxt.setText(BasicSalary.this.rs.getString("designation"));
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "record not found!");
                    }
                }
                catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
        });
        findBtn.setForeground(Color.WHITE);
        findBtn.setFont(new Font("Tahoma", 3, 10));
        findBtn.setBackground(Color.BLUE);
        findBtn.setBounds(268, 46, 68, 23);
        panel.add(findBtn);
        final JButton resetBtn = new JButton("Reset");
        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                BasicSalary.this.idTxt.setText("");
                BasicSalary.this.nameTxt.setText("");
                BasicSalary.this.designationTxt.setText("");
                BasicSalary.this.salaryTxt.setText("");
            }
        });
        resetBtn.setForeground(Color.WHITE);
        resetBtn.setFont(new Font("Tahoma", 3, 10));
        resetBtn.setBackground(Color.BLUE);
        resetBtn.setBounds(268, 72, 68, 23);
        panel.add(resetBtn);
        final JLabel lblSalaryType = new JLabel("Salary Type");
        lblSalaryType.setFont(new Font("Traditional Arabic", 0, 14));
        lblSalaryType.setBounds(20, 150, 68, 14);
        panel.add(lblSalaryType);
        final JLabel lblBasicSalary_1 = new JLabel("Basic Salary");
        lblBasicSalary_1.setFont(new Font("Traditional Arabic", 0, 14));
        lblBasicSalary_1.setBounds(20, 125, 68, 14);
        panel.add(lblBasicSalary_1);
        (this.salaryTxt = new JTextField()).setColumns(10);
        this.salaryTxt.setBackground(Color.CYAN);
        this.salaryTxt.setBounds(130, 122, 128, 20);
        panel.add(this.salaryTxt);
        final JComboBox comboBox = new JComboBox();
        comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Monthly", "Annually" }));
        comboBox.setBounds(130, 147, 128, 20);
        panel.add(comboBox);
        final JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final int id = Integer.valueOf(BasicSalary.this.idTxt.getText());
                final String name = BasicSalary.this.nameTxt.getText();
                final String designation = BasicSalary.this.designationTxt.getText();
                final double salary = Double.parseDouble(BasicSalary.this.salaryTxt.getText());
                final String salaryType = comboBox.getSelectedItem().toString();
                if (DAO.basicSalary(id, name, designation, salary, salaryType)) {
                    JOptionPane.showMessageDialog(null, "record added!");
                    BasicSalary.this.idTxt.setText("");
                    BasicSalary.this.nameTxt.setText("");
                    BasicSalary.this.designationTxt.setText("");
                    BasicSalary.this.salaryTxt.setText("");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Error!");
                }
            }
        });
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(new Font("Tahoma", 3, 10));
        saveBtn.setBackground(Color.BLUE);
        saveBtn.setBounds(268, 146, 68, 23);
        panel.add(saveBtn);
        final JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(10, 178, 351, 7);
        panel.add(separator_1);
    }
}
