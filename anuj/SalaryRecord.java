
package anuj;

import java.sql.ResultSet;
import javax.swing.table.JTableHeader;
import java.sql.SQLException;
import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.print.PrinterException;
import java.text.MessageFormat;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.Window;
import java.awt.EventQueue;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class SalaryRecord extends JFrame
{
    private JPanel contentPane;
    private JTable table;
    
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final SalaryRecord frame = new SalaryRecord();
                    frame.setVisible(true);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public SalaryRecord() {
        this.setType(Type.UTILITY);
        this.setDefaultCloseOperation(2);
        this.setBounds(100, 100, 1171, 358);
        (this.contentPane = new JPanel()).setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(this.contentPane);
        this.contentPane.setLayout(new BorderLayout(0, 0));
        final JPanel panel = new JPanel();
        this.contentPane.add(panel, "North");
        panel.setLayout(new FlowLayout(1, 5, 5));
        final JLabel lblSalaryRecord = new JLabel("Salary Record");
        lblSalaryRecord.setFont(new Font("Tahoma", 3, 16));
        panel.add(lblSalaryRecord);
        final JButton btnPrint = new JButton("Print");
        btnPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final MessageFormat headerFormat = new MessageFormat("Salary Report");
                final MessageFormat footerFormat = new MessageFormat("Page{0,number,integer}");
                try {
                    SalaryRecord.this.table.print(JTable.PrintMode.FIT_WIDTH, headerFormat, footerFormat);
                }
                catch (PrinterException e2) {
                    e2.printStackTrace();
                }
            }
        });
        btnPrint.setBackground(Color.BLUE);
        btnPrint.setForeground(Color.WHITE);
        btnPrint.setFont(new Font("Tahoma", 1, 15));
        panel.add(btnPrint);
        final JScrollPane scrollPane = new JScrollPane();
        this.contentPane.add(scrollPane, "Center");
        (this.table = new JTable()).setBackground(Color.PINK);
        final String[] columns = { "Emp id", "Name", "Designation", "Salary", "TA", "DA", "HRA", "Medical", "Bonus", "Bonesty", "Advance Paid", "Loan", "Leave Cut" };
        final DefaultTableModel tm = new DefaultTableModel(columns, 0);
        final JTableHeader tHeader = this.table.getTableHeader();
        tHeader.setBackground(Color.black);
        tHeader.setForeground(Color.white);
        tHeader.setFont(new Font("Tahoma", 1, 15));
        this.table.setModel(tm);
        this.table.add(tHeader);
        scrollPane.setViewportView(this.table);
        final ResultSet rs = DAO.salaryRecord();
        try {
            while (rs.next()) {
                final Object[] data = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13) };
                tm.addRow(data);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
