
package anuj;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.border.EtchedBorder;
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

public class Bonus extends JFrame
{
    private JPanel contentPane;
    private JTextField idTxt;
    private JTextField nameTxt;
    private JTextField designationTxt;
    private JTextField salaryTxt;
    private JTextField bonusTxt;
    ResultSet rs;
    
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final Bonus frame = new Bonus();
                    frame.setVisible(true);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public Bonus() {
        this.setType(Type.UTILITY);
        this.setDefaultCloseOperation(2);
        this.setBounds(100, 100, 379, 333);
        (this.contentPane = new JPanel()).setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(this.contentPane);
        this.contentPane.setLayout(null);
        final JPanel panel = new JPanel();
        panel.setBackground(Color.PINK);
        panel.setBorder(new SoftBevelBorder(1, Color.CYAN, Color.MAGENTA, Color.MAGENTA, Color.CYAN));
        panel.setBounds(0, 0, 361, 298);
        this.contentPane.add(panel);
        panel.setLayout(null);
        final JLabel lblBonus = new JLabel("Bonus");
        lblBonus.setHorizontalAlignment(0);
        lblBonus.setFont(new Font("Tahoma", 1, 15));
        lblBonus.setBounds(0, 0, 351, 24);
        panel.add(lblBonus);
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
        (this.nameTxt = new JTextField()).setColumns(10);
        this.nameTxt.setBackground(Color.CYAN);
        this.nameTxt.setBounds(120, 62, 128, 20);
        panel.add(this.nameTxt);
        final JButton findBtn = new JButton("Find");
        findBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final int id = Integer.valueOf(Bonus.this.idTxt.getText());
                Bonus.this.rs = DAO.searchFromSalary(id);
                try {
                    if (Bonus.this.rs.next()) {
                        Bonus.this.nameTxt.setText(Bonus.this.rs.getString("name"));
                        Bonus.this.designationTxt.setText(Bonus.this.rs.getString("designation"));
                        Bonus.this.salaryTxt.setText(Bonus.this.rs.getString("salary"));
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
                Bonus.this.idTxt.setText("");
                Bonus.this.nameTxt.setText("");
                Bonus.this.designationTxt.setText("");
                Bonus.this.salaryTxt.setText("");
                Bonus.this.bonusTxt.setText("");
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
        final JLabel label_3 = new JLabel("Designation");
        label_3.setFont(new Font("Traditional Arabic", 0, 14));
        label_3.setBounds(10, 90, 68, 14);
        panel.add(label_3);
        (this.designationTxt = new JTextField()).setColumns(10);
        this.designationTxt.setBackground(Color.CYAN);
        this.designationTxt.setBounds(120, 87, 128, 20);
        panel.add(this.designationTxt);
        final JLabel lblSalary = new JLabel("Salary");
        lblSalary.setFont(new Font("Traditional Arabic", 0, 14));
        lblSalary.setBounds(10, 114, 68, 14);
        panel.add(lblSalary);
        (this.salaryTxt = new JTextField()).setColumns(10);
        this.salaryTxt.setBackground(Color.CYAN);
        this.salaryTxt.setBounds(120, 111, 128, 20);
        panel.add(this.salaryTxt);
        final JPanel panel_1 = new JPanel();
        panel_1.setBorder(new EtchedBorder(1, Color.CYAN, Color.MAGENTA));
        panel_1.setBounds(119, 154, 207, 90);
        panel.add(panel_1);
        panel_1.setLayout(null);
        final JLabel lblAmount = new JLabel("Amount");
        lblAmount.setFont(new Font("Traditional Arabic", 0, 14));
        lblAmount.setBounds(10, 21, 68, 14);
        panel_1.add(lblAmount);
        final JLabel lblBonusType = new JLabel("Bonus Type");
        lblBonusType.setFont(new Font("Traditional Arabic", 0, 14));
        lblBonusType.setBounds(10, 47, 68, 14);
        panel_1.add(lblBonusType);
        (this.bonusTxt = new JTextField()).setColumns(10);
        this.bonusTxt.setBackground(Color.CYAN);
        this.bonusTxt.setBounds(102, 18, 95, 20);
        panel_1.add(this.bonusTxt);
        final JComboBox comboBox = new JComboBox();
        comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "New Year", "Holi", "Diwali", "Eid", "Christmas", "Other" }));
        comboBox.setBounds(102, 44, 95, 20);
        panel_1.add(comboBox);
        final JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(10, 255, 341, 7);
        panel.add(separator_1);
        final JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final double bonusAmt = Double.parseDouble(Bonus.this.bonusTxt.getText());
                final String bounesty = comboBox.getSelectedItem().toString();
                final int id = Integer.valueOf(Bonus.this.idTxt.getText());
                if (DAO.bonus(bonusAmt, bounesty, id)) {
                    JOptionPane.showMessageDialog(null, "data updated!");
                    Bonus.this.idTxt.setText("");
                    Bonus.this.nameTxt.setText("");
                    Bonus.this.designationTxt.setText("");
                    Bonus.this.salaryTxt.setText("");
                    Bonus.this.bonusTxt.setText("");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Error!");
                }
            }
        });
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(new Font("Tahoma", 3, 10));
        saveBtn.setBackground(Color.BLUE);
        saveBtn.setBounds(144, 264, 68, 23);
        panel.add(saveBtn);
    }
}
