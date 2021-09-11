
package anuj;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import java.awt.Toolkit;
import java.awt.EventQueue;
import javax.swing.JFrame;

public class EmsMainWindow extends JFrame
{
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final EmsMainWindow frame = new EmsMainWindow();
                    frame.setVisible(true);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public EmsMainWindow() {
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("images\\ems.png"));
        this.setTitle("Employee Management System");
        this.setDefaultCloseOperation(3);
        this.setExtendedState(6);
        this.setBounds(100, 100, 697, 320);
        final JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        final JMenu mnEmployees = new JMenu("Employees");
        menuBar.add(mnEmployees);
        final JMenuItem mntmAdd = new JMenuItem("Add");
        mntmAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final AddEmployee addEmp = new AddEmployee();
                addEmp.setVisible(true);
            }
        });
        mnEmployees.add(mntmAdd);
        final JMenuItem mntmSearch = new JMenuItem("Search");
        mntmSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final SearchEmployee searchEmp = new SearchEmployee();
                searchEmp.setVisible(true);
            }
        });
        mnEmployees.add(mntmSearch);
        final JMenuItem mntmEdit = new JMenuItem("Edit");
        mntmEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final UpdateEmployee updateEmp = new UpdateEmployee();
                updateEmp.setVisible(true);
            }
        });
        mnEmployees.add(mntmEdit);
        final JMenuItem mntmRemove = new JMenuItem("Remove");
        mntmRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final RemoveEmployee removeEmp = new RemoveEmployee();
                removeEmp.setVisible(true);
            }
        });
        mnEmployees.add(mntmRemove);
        final JMenu mnSalary = new JMenu("Salary");
        menuBar.add(mnSalary);
        final JMenuItem mntmBasicSalary = new JMenuItem("Basic Salary");
        mntmBasicSalary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final BasicSalary bs = new BasicSalary();
                bs.setVisible(true);
            }
        });
        mnSalary.add(mntmBasicSalary);
        final JMenuItem mntmIncentives = new JMenuItem("Incentives");
        mntmIncentives.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final Incentives inc = new Incentives();
                inc.setVisible(true);
            }
        });
        mnSalary.add(mntmIncentives);
        final JMenuItem mntmBonus = new JMenuItem("Bonus");
        mntmBonus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final Bonus bs = new Bonus();
                bs.setVisible(true);
            }
        });
        mnSalary.add(mntmBonus);
        final JMenuItem mntmDeductions = new JMenuItem("Deductions");
        mntmDeductions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final Deductions ds = new Deductions();
                ds.setVisible(true);
            }
        });
        mnSalary.add(mntmDeductions);
        final JMenu mnLeaves = new JMenu("Leaves");
        menuBar.add(mnLeaves);
        final JMenuItem mntmLeaveEntry = new JMenuItem("Leave Entry");
        mntmLeaveEntry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final LeaveEntry leaveEntry = new LeaveEntry();
                leaveEntry.setVisible(true);
            }
        });
        mnLeaves.add(mntmLeaveEntry);
        final JMenu mnRecord = new JMenu("Record");
        menuBar.add(mnRecord);
        final JMenuItem mntmEmployeesRecord = new JMenuItem("Employees Record");
        mntmEmployeesRecord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final EmployeesRecord empRcd = new EmployeesRecord();
                empRcd.setVisible(true);
            }
        });
        mnRecord.add(mntmEmployeesRecord);
        final JMenuItem mntmSalaryRecord = new JMenuItem("Salary Record");
        mntmSalaryRecord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final SalaryRecord slrcd = new SalaryRecord();
                slrcd.setVisible(true);
            }
        });
        mnRecord.add(mntmSalaryRecord);
        final JMenuItem mntmLeaveRecord = new JMenuItem("Leave Record");
        mntmLeaveRecord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final LeavesRecord leavesRcd = new LeavesRecord();
                leavesRcd.setVisible(true);
            }
        });
        mnRecord.add(mntmLeaveRecord);
        final JMenu mnAbout = new JMenu("About");
        menuBar.add(mnAbout);
        final JMenuItem mntmAboutUs = new JMenuItem("About Us");
        mntmAboutUs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final About abt = new About();
                abt.setVisible(true);
            }
        });
        mnAbout.add(mntmAboutUs);
        final JMenuBar menuBar_1 = new JMenuBar();
        menuBar.add(menuBar_1);
        this.getContentPane().setLayout(null);
        this.getContentPane().setLayout(new BorderLayout(0, 0));
        this.getContentPane().setLayout(new BorderLayout(0, 0));
        final JPanel panel = new JPanel();
        panel.setBounds(0, 0, 681, 250);
        this.getContentPane().add(panel);
        panel.setLayout(new BorderLayout(0, 0));
        final JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon("Images\\emsMainW.jpg"));
        panel.add(lblNewLabel);
    }
}
