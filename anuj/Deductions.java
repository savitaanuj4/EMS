
package anuj;

import javax.swing.border.SoftBevelBorder;
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
import java.awt.EventQueue;
import java.sql.ResultSet;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class Deductions extends JFrame
{
    private JPanel contentPane;
    private JTextField idTxt;
    private JTextField nameTxt;
    private JTextField designationTxt;
    private JTextField salaryTxt;
    private JTextField advanceTxt;
    private JTextField loanTxt;
    private JTextField leavesTxt;
    ResultSet rs;
    
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final Deductions frame = new Deductions();
                    frame.setVisible(true);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public Deductions() {
        this.setDefaultCloseOperation(2);
        this.setBounds(100, 100, 376, 344);
        (this.contentPane = new JPanel()).setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(this.contentPane);
        this.contentPane.setLayout(null);
        final JPanel panel = new JPanel();
        panel.setBackground(Color.PINK);
        panel.setBorder(new EtchedBorder(1, Color.MAGENTA, Color.CYAN));
        panel.setBounds(0, 0, 360, 306);
        this.contentPane.add(panel);
        panel.setLayout(null);
        final JLabel lblDeductions = new JLabel("Deductions");
        lblDeductions.setHorizontalAlignment(0);
        lblDeductions.setFont(new Font("Tahoma", 1, 15));
        lblDeductions.setBounds(0, 0, 351, 24);
        panel.add(lblDeductions);
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
                final int id = Integer.valueOf(Deductions.this.idTxt.getText());
                Deductions.this.rs = DAO.searchFromSalary(id);
                try {
                    if (Deductions.this.rs.next()) {
                        Deductions.this.nameTxt.setText(Deductions.this.rs.getString("name"));
                        Deductions.this.designationTxt.setText(Deductions.this.rs.getString("designation"));
                        Deductions.this.salaryTxt.setText(Deductions.this.rs.getString("salary"));
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
        final JLabel label_2 = new JLabel("Name");
        label_2.setFont(new Font("Traditional Arabic", 0, 14));
        label_2.setBounds(10, 65, 68, 14);
        panel.add(label_2);
        final JLabel label_3 = new JLabel("Designation");
        label_3.setFont(new Font("Traditional Arabic", 0, 14));
        label_3.setBounds(10, 90, 68, 14);
        panel.add(label_3);
        final JLabel label_4 = new JLabel("Salary");
        label_4.setFont(new Font("Traditional Arabic", 0, 14));
        label_4.setBounds(10, 114, 68, 14);
        panel.add(label_4);
        (this.nameTxt = new JTextField()).setColumns(10);
        this.nameTxt.setBackground(Color.CYAN);
        this.nameTxt.setBounds(120, 62, 128, 20);
        panel.add(this.nameTxt);
        final JButton resetBtn = new JButton("Reset");
        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                Deductions.this.idTxt.setText("");
                Deductions.this.nameTxt.setText("");
                Deductions.this.designationTxt.setText("");
                Deductions.this.salaryTxt.setText("");
                Deductions.this.advanceTxt.setText("");
                Deductions.this.loanTxt.setText("");
                Deductions.this.leavesTxt.setText("");
            }
        });
        resetBtn.setForeground(Color.WHITE);
        resetBtn.setFont(new Font("Tahoma", 3, 10));
        resetBtn.setBackground(Color.BLUE);
        resetBtn.setBounds(258, 61, 68, 23);
        panel.add(resetBtn);
        (this.designationTxt = new JTextField()).setColumns(10);
        this.designationTxt.setBackground(Color.CYAN);
        this.designationTxt.setBounds(120, 87, 128, 20);
        panel.add(this.designationTxt);
        (this.salaryTxt = new JTextField()).setColumns(10);
        this.salaryTxt.setBackground(Color.CYAN);
        this.salaryTxt.setBounds(120, 111, 128, 20);
        panel.add(this.salaryTxt);
        final JPanel panel_1 = new JPanel();
        panel_1.setBorder(new SoftBevelBorder(1, Color.CYAN, Color.MAGENTA, Color.MAGENTA, Color.CYAN));
        panel_1.setBounds(120, 144, 207, 94);
        panel.add(panel_1);
        panel_1.setLayout(null);
        final JLabel lblAdvancePaid = new JLabel("Advance Paid");
        lblAdvancePaid.setFont(new Font("Traditional Arabic", 0, 14));
        lblAdvancePaid.setBounds(10, 17, 89, 14);
        panel_1.add(lblAdvancePaid);
        final JLabel lblLoan = new JLabel("Loan");
        lblLoan.setFont(new Font("Traditional Arabic", 0, 14));
        lblLoan.setBounds(10, 43, 89, 14);
        panel_1.add(lblLoan);
        final JLabel lblLeaves = new JLabel("Leaves");
        lblLeaves.setFont(new Font("Traditional Arabic", 0, 14));
        lblLeaves.setBounds(10, 68, 89, 14);
        panel_1.add(lblLeaves);
        (this.advanceTxt = new JTextField()).setColumns(10);
        this.advanceTxt.setBackground(Color.CYAN);
        this.advanceTxt.setBounds(98, 11, 99, 20);
        panel_1.add(this.advanceTxt);
        (this.loanTxt = new JTextField()).setColumns(10);
        this.loanTxt.setBackground(Color.CYAN);
        this.loanTxt.setBounds(98, 37, 99, 20);
        panel_1.add(this.loanTxt);
        (this.leavesTxt = new JTextField()).setColumns(10);
        this.leavesTxt.setBackground(Color.CYAN);
        this.leavesTxt.setBounds(98, 62, 99, 20);
        panel_1.add(this.leavesTxt);
        final JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(10, 249, 341, 7);
        panel.add(separator_1);
        final JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final double advance = Double.parseDouble(Deductions.this.advanceTxt.getText());
                final double loan = Double.parseDouble(Deductions.this.loanTxt.getText());
                final double leaveCut = Double.parseDouble(Deductions.this.leavesTxt.getText());
                final int id = Integer.valueOf(Deductions.this.idTxt.getText());
                if (DAO.deductions(advance, loan, leaveCut, id)) {
                    JOptionPane.showMessageDialog(null, "data updated!");
                    Deductions.this.idTxt.setText("");
                    Deductions.this.nameTxt.setText("");
                    Deductions.this.designationTxt.setText("");
                    Deductions.this.salaryTxt.setText("");
                    Deductions.this.advanceTxt.setText("");
                    Deductions.this.loanTxt.setText("");
                    Deductions.this.leavesTxt.setText("");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Error!");
                }
            }
        });
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(new Font("Tahoma", 3, 10));
        saveBtn.setBackground(Color.BLUE);
        saveBtn.setBounds(144, 258, 68, 23);
        panel.add(saveBtn);
    }
}
