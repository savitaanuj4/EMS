
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
import javax.swing.border.EtchedBorder;
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

public class LeaveEntry extends JFrame
{
    private JPanel contentPane;
    private JTextField idTxt;
    private JTextField nameTxt;
    private JTextField designationTxt;
    private JTextField salaryTxt;
    private JTextField leavesTxt;
    ResultSet rs;
    
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final LeaveEntry frame = new LeaveEntry();
                    frame.setVisible(true);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public LeaveEntry() {
        this.setType(Type.UTILITY);
        this.setDefaultCloseOperation(2);
        this.setBounds(100, 100, 380, 285);
        (this.contentPane = new JPanel()).setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(this.contentPane);
        this.contentPane.setLayout(null);
        final JPanel panel = new JPanel();
        panel.setBackground(Color.PINK);
        panel.setBorder(new EtchedBorder(1, Color.MAGENTA, Color.CYAN));
        panel.setBounds(0, 0, 366, 244);
        this.contentPane.add(panel);
        panel.setLayout(null);
        final JLabel lblLeavesEntry = new JLabel("Leaves Entry");
        lblLeavesEntry.setHorizontalAlignment(0);
        lblLeavesEntry.setFont(new Font("Tahoma", 1, 15));
        lblLeavesEntry.setBounds(0, 0, 351, 24);
        panel.add(lblLeavesEntry);
        final JSeparator separator = new JSeparator();
        separator.setBounds(10, 25, 341, 7);
        panel.add(separator);
        final JLabel label_1 = new JLabel("Emp Id");
        label_1.setFont(new Font("Traditional Arabic", 0, 14));
        label_1.setBounds(10, 39, 68, 14);
        panel.add(label_1);
        (this.idTxt = new JTextField()).setColumns(10);
        this.idTxt.setBackground(Color.CYAN);
        this.idTxt.setBounds(120, 36, 128, 20);
        panel.add(this.idTxt);
        final JButton findBtn = new JButton("Find");
        findBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final int id = Integer.valueOf(LeaveEntry.this.idTxt.getText());
                LeaveEntry.this.rs = DAO.searchFromSalary(id);
                try {
                    if (LeaveEntry.this.rs.next()) {
                        LeaveEntry.this.nameTxt.setText(LeaveEntry.this.rs.getString("name"));
                        LeaveEntry.this.designationTxt.setText(LeaveEntry.this.rs.getString("designation"));
                        LeaveEntry.this.salaryTxt.setText(LeaveEntry.this.rs.getString("salary"));
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
        findBtn.setBounds(258, 35, 68, 23);
        panel.add(findBtn);
        final JButton resetBtn = new JButton("Reset");
        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                LeaveEntry.this.idTxt.setText("");
                LeaveEntry.this.nameTxt.setText("");
                LeaveEntry.this.designationTxt.setText("");
                LeaveEntry.this.salaryTxt.setText("");
                LeaveEntry.this.leavesTxt.setText("");
            }
        });
        resetBtn.setForeground(Color.WHITE);
        resetBtn.setFont(new Font("Tahoma", 3, 10));
        resetBtn.setBackground(Color.BLUE);
        resetBtn.setBounds(258, 61, 68, 23);
        panel.add(resetBtn);
        final JLabel label_2 = new JLabel("Name");
        label_2.setFont(new Font("Traditional Arabic", 0, 14));
        label_2.setBounds(10, 65, 68, 14);
        panel.add(label_2);
        (this.nameTxt = new JTextField()).setColumns(10);
        this.nameTxt.setBackground(Color.CYAN);
        this.nameTxt.setBounds(120, 62, 128, 20);
        panel.add(this.nameTxt);
        final JLabel label_3 = new JLabel("Designation");
        label_3.setFont(new Font("Traditional Arabic", 0, 14));
        label_3.setBounds(10, 90, 68, 14);
        panel.add(label_3);
        (this.designationTxt = new JTextField()).setColumns(10);
        this.designationTxt.setBackground(Color.CYAN);
        this.designationTxt.setBounds(120, 87, 128, 20);
        panel.add(this.designationTxt);
        final JLabel label_4 = new JLabel("Salary");
        label_4.setFont(new Font("Traditional Arabic", 0, 14));
        label_4.setBounds(10, 114, 68, 14);
        panel.add(label_4);
        (this.salaryTxt = new JTextField()).setColumns(10);
        this.salaryTxt.setBackground(Color.CYAN);
        this.salaryTxt.setBounds(120, 111, 128, 20);
        panel.add(this.salaryTxt);
        final JLabel lblTotalLeaves = new JLabel("Total Leaves");
        lblTotalLeaves.setFont(new Font("Traditional Arabic", 0, 14));
        lblTotalLeaves.setBounds(10, 142, 84, 14);
        panel.add(lblTotalLeaves);
        (this.leavesTxt = new JTextField()).setColumns(10);
        this.leavesTxt.setBackground(Color.CYAN);
        this.leavesTxt.setBounds(120, 139, 128, 20);
        panel.add(this.leavesTxt);
        final JLabel lblLeaveType = new JLabel("Leave Type");
        lblLeaveType.setFont(new Font("Traditional Arabic", 0, 14));
        lblLeaveType.setBounds(10, 166, 68, 14);
        panel.add(lblLeaveType);
        final JComboBox comboBox = new JComboBox();
        comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Holiday", "Medical", "Other" }));
        comboBox.setBounds(120, 163, 128, 20);
        panel.add(comboBox);
        final JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(10, 191, 341, 7);
        panel.add(separator_1);
        final JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final int id = Integer.valueOf(LeaveEntry.this.idTxt.getText());
                final String name = LeaveEntry.this.nameTxt.getText();
                final String designation = LeaveEntry.this.designationTxt.getText();
                final Double salary = Double.parseDouble(LeaveEntry.this.salaryTxt.getText());
                final int leaves = Integer.parseInt(LeaveEntry.this.leavesTxt.getText());
                final String leaveType = comboBox.getSelectedItem().toString();
                if (DAO.leavesEntry(id, name, designation, salary, leaves, leaveType)) {
                    JOptionPane.showMessageDialog(null, "data added!");
                    LeaveEntry.this.idTxt.setText("");
                    LeaveEntry.this.nameTxt.setText("");
                    LeaveEntry.this.designationTxt.setText("");
                    LeaveEntry.this.salaryTxt.setText("");
                    LeaveEntry.this.leavesTxt.setText("");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Error!");
                }
            }
        });
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(new Font("Tahoma", 3, 10));
        saveBtn.setBackground(Color.BLUE);
        saveBtn.setBounds(144, 200, 68, 23);
        panel.add(saveBtn);
    }
}
