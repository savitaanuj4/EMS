
package anuj;

import java.sql.ResultSet;
import javax.swing.table.JTableHeader;
import java.awt.print.PrinterException;
import java.text.MessageFormat;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.sql.SQLException;
import java.awt.Font;
import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JScrollPane;
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

public class LeavesRecord extends JFrame
{
    private JPanel contentPane;
    private JTable table;
    
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final LeavesRecord frame = new LeavesRecord();
                    frame.setVisible(true);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public LeavesRecord() {
        this.setType(Type.UTILITY);
        this.setDefaultCloseOperation(2);
        this.setBounds(100, 100, 619, 300);
        (this.contentPane = new JPanel()).setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(this.contentPane);
        this.contentPane.setLayout(new BorderLayout(0, 0));
        final JScrollPane scrollPane = new JScrollPane();
        this.contentPane.add(scrollPane, "Center");
        (this.table = new JTable()).setBackground(Color.PINK);
        final Object[] columns = { "Emp Id", "Name", "Designation", "Leaves", "Leaves", "Leave Type" };
        final DefaultTableModel tm = new DefaultTableModel(columns, 0);
        this.table.setModel(tm);
        final JTableHeader tHeader = this.table.getTableHeader();
        tHeader.setBackground(Color.black);
        tHeader.setForeground(Color.white);
        tHeader.setFont(new Font("Tahoma", 1, 15));
        final ResultSet rs = DAO.leavesRecord();
        try {
            while (rs.next()) {
                final Object[] data = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6) };
                tm.addRow(data);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        scrollPane.setViewportView(this.table);
        final JPanel panel = new JPanel();
        this.contentPane.add(panel, "North");
        final JLabel lblNewLabel = new JLabel("Leaves Record");
        lblNewLabel.setFont(new Font("Tahoma", 1, 16));
        panel.add(lblNewLabel);
        final JButton btnPrint = new JButton("Print");
        btnPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final MessageFormat headerFormat = new MessageFormat("Leaves Report");
                final MessageFormat footerFormat = new MessageFormat("Page{0,number,integer}");
                try {
                    LeavesRecord.this.table.print(JTable.PrintMode.FIT_WIDTH, headerFormat, footerFormat);
                }
                catch (PrinterException e2) {
                    e2.printStackTrace();
                }
            }
        });
        btnPrint.setForeground(Color.WHITE);
        btnPrint.setBackground(Color.BLUE);
        btnPrint.setFont(new Font("Tahoma", 1, 15));
        panel.add(btnPrint);
    }
}
