
package anuj;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
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

public class Incentives extends JFrame
{
    private JPanel contentPane;
    private JTextField idTxt;
    private JTextField designationTxt;
    private JTextField salaryTxt;
    private JTextField nameTxt;
    private JTextField taPer;
    private JTextField daPer;
    private JTextField hraPer;
    private JTextField medicalPer;
    private JTextField taAmt;
    private JTextField daAmt;
    private JTextField hraAmt;
    private JTextField medicalAmt;
    ResultSet rs;
    
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final Incentives frame = new Incentives();
                    frame.setVisible(true);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public Incentives() {
        this.setType(Type.UTILITY);
        this.setDefaultCloseOperation(2);
        this.setBounds(100, 100, 395, 387);
        (this.contentPane = new JPanel()).setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(this.contentPane);
        this.contentPane.setLayout(null);
        final JPanel panel = new JPanel();
        panel.setBackground(Color.PINK);
        panel.setBorder(new SoftBevelBorder(1, new Color(0, 255, 255), new Color(255, 0, 255), new Color(255, 0, 255), new Color(0, 255, 255)));
        panel.setBounds(0, 0, 371, 342);
        this.contentPane.add(panel);
        panel.setLayout(null);
        final JLabel lblIncentives_1 = new JLabel("Incentives");
        lblIncentives_1.setHorizontalAlignment(0);
        lblIncentives_1.setFont(new Font("Tahoma", 1, 15));
        lblIncentives_1.setBounds(10, 11, 351, 24);
        panel.add(lblIncentives_1);
        final JSeparator separator = new JSeparator();
        separator.setBounds(10, 36, 351, 7);
        panel.add(separator);
        final JLabel label_1 = new JLabel("Emp Id");
        label_1.setFont(new Font("Traditional Arabic", 0, 14));
        label_1.setBounds(20, 50, 68, 14);
        panel.add(label_1);
        final JLabel label_2 = new JLabel("Name");
        label_2.setFont(new Font("Traditional Arabic", 0, 14));
        label_2.setBounds(20, 76, 68, 14);
        panel.add(label_2);
        final JLabel label_3 = new JLabel("Designation");
        label_3.setFont(new Font("Traditional Arabic", 0, 14));
        label_3.setBounds(20, 101, 68, 14);
        panel.add(label_3);
        (this.idTxt = new JTextField()).setColumns(10);
        this.idTxt.setBackground(Color.CYAN);
        this.idTxt.setBounds(130, 47, 128, 20);
        panel.add(this.idTxt);
        (this.designationTxt = new JTextField()).setColumns(10);
        this.designationTxt.setBackground(Color.CYAN);
        this.designationTxt.setBounds(130, 98, 128, 20);
        panel.add(this.designationTxt);
        final JButton findBtn = new JButton("Find");
        findBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final int id = Integer.valueOf(Incentives.this.idTxt.getText());
                Incentives.this.rs = DAO.searchFromSalary(id);
                try {
                    if (Incentives.this.rs.next()) {
                        Incentives.this.nameTxt.setText(Incentives.this.rs.getString("name"));
                        Incentives.this.designationTxt.setText(Incentives.this.rs.getString("designation"));
                        Incentives.this.salaryTxt.setText(Incentives.this.rs.getString("salary"));
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
                Incentives.this.idTxt.setText("");
                Incentives.this.nameTxt.setText("");
                Incentives.this.designationTxt.setText("");
                Incentives.this.salaryTxt.setText("");
                Incentives.this.taAmt.setText("");
                Incentives.this.taPer.setText("");
                Incentives.this.daPer.setText("");
                Incentives.this.daAmt.setText("");
                Incentives.this.hraPer.setText("");
                Incentives.this.hraAmt.setText("");
                Incentives.this.medicalPer.setText("");
                Incentives.this.medicalAmt.setText("");
            }
        });
        resetBtn.setForeground(Color.WHITE);
        resetBtn.setFont(new Font("Tahoma", 3, 10));
        resetBtn.setBackground(Color.BLUE);
        resetBtn.setBounds(268, 72, 68, 23);
        panel.add(resetBtn);
        final JLabel lblSalary = new JLabel("Salary");
        lblSalary.setFont(new Font("Traditional Arabic", 0, 14));
        lblSalary.setBounds(20, 125, 68, 14);
        panel.add(lblSalary);
        (this.salaryTxt = new JTextField()).setColumns(10);
        this.salaryTxt.setBackground(Color.CYAN);
        this.salaryTxt.setBounds(130, 122, 128, 20);
        panel.add(this.salaryTxt);
        (this.nameTxt = new JTextField()).setColumns(10);
        this.nameTxt.setBackground(Color.CYAN);
        this.nameTxt.setBounds(130, 73, 128, 20);
        panel.add(this.nameTxt);
        final JLabel lblIncentives = new JLabel("Incentives");
        lblIncentives.setFont(new Font("Traditional Arabic", 0, 14));
        lblIncentives.setBounds(20, 150, 68, 14);
        panel.add(lblIncentives);
        final JPanel panel_1 = new JPanel();
        panel_1.setBorder(new EtchedBorder(1, Color.MAGENTA, Color.CYAN));
        panel_1.setBounds(98, 150, 240, 135);
        panel.add(panel_1);
        panel_1.setLayout(null);
        final JLabel lblTa = new JLabel("TA");
        lblTa.setFont(new Font("Traditional Arabic", 0, 14));
        lblTa.setBounds(10, 28, 55, 14);
        panel_1.add(lblTa);
        final JLabel lblDa = new JLabel("DA");
        lblDa.setFont(new Font("Traditional Arabic", 0, 14));
        lblDa.setBounds(10, 54, 68, 14);
        panel_1.add(lblDa);
        final JLabel lblFca = new JLabel("HRA");
        lblFca.setFont(new Font("Traditional Arabic", 0, 14));
        lblFca.setBounds(10, 79, 68, 14);
        panel_1.add(lblFca);
        final JLabel lblMedical = new JLabel("Medical");
        lblMedical.setFont(new Font("Traditional Arabic", 0, 14));
        lblMedical.setBounds(10, 103, 68, 14);
        panel_1.add(lblMedical);
        (this.taPer = new JTextField()).addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(final KeyEvent e) {
                final double salary = Double.parseDouble(Incentives.this.salaryTxt.getText());
                double ta = Double.parseDouble(Incentives.this.taPer.getText());
                ta = salary * ta / 100.0;
                Incentives.this.taAmt.setText(Double.toString(ta));
            }
        });
        this.taPer.setColumns(10);
        this.taPer.setBackground(Color.CYAN);
        this.taPer.setBounds(64, 22, 55, 20);
        panel_1.add(this.taPer);
        (this.daPer = new JTextField()).addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(final KeyEvent e) {
                final double salary = Double.parseDouble(Incentives.this.salaryTxt.getText());
                double da = Double.parseDouble(Incentives.this.daPer.getText());
                da = salary * da / 100.0;
                Incentives.this.daAmt.setText(Double.toString(da));
            }
        });
        this.daPer.setColumns(10);
        this.daPer.setBackground(Color.CYAN);
        this.daPer.setBounds(64, 48, 55, 20);
        panel_1.add(this.daPer);
        (this.hraPer = new JTextField()).addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(final KeyEvent e) {
                final double salary = Double.parseDouble(Incentives.this.salaryTxt.getText());
                double hra = Double.parseDouble(Incentives.this.hraPer.getText());
                hra = salary * hra / 100.0;
                Incentives.this.hraAmt.setText(Double.toString(hra));
            }
        });
        this.hraPer.setColumns(10);
        this.hraPer.setBackground(Color.CYAN);
        this.hraPer.setBounds(64, 73, 55, 20);
        panel_1.add(this.hraPer);
        (this.medicalPer = new JTextField()).addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(final KeyEvent e) {
                final double salary = Double.parseDouble(Incentives.this.salaryTxt.getText());
                double medical = Double.parseDouble(Incentives.this.medicalPer.getText());
                medical = salary * medical / 100.0;
                Incentives.this.medicalAmt.setText(Double.toString(medical));
            }
        });
        this.medicalPer.setColumns(10);
        this.medicalPer.setBackground(Color.CYAN);
        this.medicalPer.setBounds(64, 97, 55, 20);
        panel_1.add(this.medicalPer);
        (this.taAmt = new JTextField()).setColumns(10);
        this.taAmt.setBackground(Color.CYAN);
        this.taAmt.setBounds(129, 22, 101, 20);
        panel_1.add(this.taAmt);
        (this.daAmt = new JTextField()).setColumns(10);
        this.daAmt.setBackground(Color.CYAN);
        this.daAmt.setBounds(129, 48, 101, 20);
        panel_1.add(this.daAmt);
        (this.hraAmt = new JTextField()).setColumns(10);
        this.hraAmt.setBackground(Color.CYAN);
        this.hraAmt.setBounds(129, 73, 101, 20);
        panel_1.add(this.hraAmt);
        (this.medicalAmt = new JTextField()).setColumns(10);
        this.medicalAmt.setBackground(Color.CYAN);
        this.medicalAmt.setBounds(129, 97, 101, 20);
        panel_1.add(this.medicalAmt);
        final JLabel lblNewLabel = new JLabel("%");
        lblNewLabel.setHorizontalAlignment(0);
        lblNewLabel.setFont(new Font("Tahoma", 1, 15));
        lblNewLabel.setBounds(64, 3, 46, 14);
        panel_1.add(lblNewLabel);
        final JLabel lblAmount = new JLabel("Amount");
        lblAmount.setHorizontalAlignment(0);
        lblAmount.setFont(new Font("Tahoma", 0, 13));
        lblAmount.setBounds(129, 5, 101, 14);
        panel_1.add(lblAmount);
        final JButton btnSave = new JButton("Save");
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final double taAmount = Double.parseDouble(Incentives.this.taAmt.getText());
                final double daAmount = Double.parseDouble(Incentives.this.daAmt.getText());
                final double hraAmount = Double.parseDouble(Incentives.this.hraAmt.getText());
                final double medicalAmount = Double.parseDouble(Incentives.this.medicalAmt.getText());
                final int id = Integer.valueOf(Incentives.this.idTxt.getText());
                if (DAO.incentives(taAmount, daAmount, hraAmount, medicalAmount, id)) {
                    JOptionPane.showMessageDialog(null, "data saved!");
                    Incentives.this.idTxt.setText("");
                    Incentives.this.nameTxt.setText("");
                    Incentives.this.designationTxt.setText("");
                    Incentives.this.salaryTxt.setText("");
                    Incentives.this.taAmt.setText("");
                    Incentives.this.taPer.setText("");
                    Incentives.this.daPer.setText("");
                    Incentives.this.daAmt.setText("");
                    Incentives.this.hraPer.setText("");
                    Incentives.this.hraAmt.setText("");
                    Incentives.this.medicalPer.setText("");
                    Incentives.this.medicalAmt.setText("");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Error!");
                }
            }
        });
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font("Tahoma", 3, 10));
        btnSave.setBackground(Color.BLUE);
        btnSave.setBounds(157, 308, 68, 23);
        panel.add(btnSave);
        final JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(10, 296, 351, 7);
        panel.add(separator_1);
    }
}
