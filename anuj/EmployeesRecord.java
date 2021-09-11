
package anuj;

import javax.swing.JScrollPane;
import java.awt.print.PrinterException;
import java.text.MessageFormat;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.sql.SQLException;
import java.awt.Font;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.table.TableModel;
import java.awt.Container;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.Window;
import java.awt.EventQueue;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.sql.ResultSet;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class EmployeesRecord extends JFrame
{
    JPanel contentPane;
    JTable table;
    ResultSet rs;
    JTableHeader tHeader;
    DefaultTableModel tm;
    
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final EmployeesRecord frame = new EmployeesRecord();
                    frame.setVisible(true);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public EmployeesRecord() {
        this.setType(Type.UTILITY);
        this.setDefaultCloseOperation(2);
        this.setBounds(100, 100, 650, 307);
        (this.contentPane = new JPanel()).setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(this.contentPane);
        final String[] columns = { "Emp_id", "Name", "Designation", "DOJ", "City", "Mobile", "Email", "Shift" };
        this.tm = new DefaultTableModel(columns, 0);
        (this.table = new JTable(this.tm)).setBackground(Color.PINK);
        this.table.setBorder(new EtchedBorder(0, Color.MAGENTA, Color.CYAN));
        this.table.setRowHeight(0, 20);
        (this.tHeader = this.table.getTableHeader()).setBackground(Color.black);
        this.tHeader.setForeground(Color.white);
        this.tHeader.setFont(new Font("Tahoma", 1, 15));
        this.rs = DAO.employeesRecord();
        try {
            while (this.rs.next()) {
                final Object[] o = { this.rs.getString(1), this.rs.getString(2), this.rs.getString(3), this.rs.getString(4), this.rs.getString(5), this.rs.getString(6), this.rs.getString(7), this.rs.getString(8) };
                this.tm.addRow(o);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        this.contentPane.setLayout(new BorderLayout(0, 0));
        final JPanel panel = new JPanel();
        this.contentPane.add(panel, "North");
        panel.setLayout(new FlowLayout(1, 5, 5));
        final JLabel lblNewLabel = new JLabel("Employees Record");
        lblNewLabel.setFont(new Font("Tahoma", 1, 18));
        panel.add(lblNewLabel);
        final JButton btnPrint = new JButton("Print");
        btnPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final MessageFormat headerFormat = new MessageFormat("Employees Report");
                final MessageFormat footerFormat = new MessageFormat("Page{0,number,integer}");
                try {
                    EmployeesRecord.this.table.print(JTable.PrintMode.FIT_WIDTH, headerFormat, footerFormat);
                }
                catch (PrinterException e2) {
                    e2.printStackTrace();
                }
            }
        });
        btnPrint.setFont(new Font("Tahoma", 3, 15));
        btnPrint.setForeground(Color.WHITE);
        btnPrint.setBackground(Color.BLUE);
        panel.add(btnPrint);
        final JScrollPane scrollPane = new JScrollPane(this.table);
        this.contentPane.add(scrollPane);
    }
    
    public JPanel toCenter() {
        final JPanel panel = new JPanel();
        panel.add(panel);
        return panel;
    }
}
