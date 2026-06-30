package com.bank.ui;

import com.bank.dao.CustomerDAO;
import com.bank.model.Customer;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.List;

// ─────────────────────────────────────────────────────────────
// Shared Colors  (UNCHANGED)
// ─────────────────────────────────────────────────────────────
class Theme {
    static final Color NAVY        = new Color(10, 25, 70);
    static final Color NAVY_DEEP   = new Color(6, 15, 45);
    static final Color NAVY_MID    = new Color(18, 40, 95);
    static final Color BG          = new Color(241, 245, 253);
    static final Color CARD        = Color.WHITE;
    static final Color BLUE        = new Color(37, 99, 235);
    static final Color GREEN       = new Color(22, 163, 74);
    static final Color RED         = new Color(220, 38, 38);
    static final Color AMBER       = new Color(217, 119, 6);
    static final Color PURPLE      = new Color(124, 58, 237);
    static final Color TEAL        = new Color(13, 148, 136);
    static final Color TEXT_DARK   = new Color(15, 23, 42);
    static final Color TEXT_MID    = new Color(71, 85, 105);
    static final Color TEXT_LIGHT  = new Color(148, 163, 184);
    static final Color BORDER      = new Color(226, 232, 240);
    static final Color SIDEBAR_TXT = new Color(203, 213, 225);
    static final Color SIDEBAR_SUB = new Color(100, 116, 139);
    static final Color ACTIVE_BG   = new Color(255, 255, 255, 22);
    static final Color ACTIVE_BAR  = new Color(99, 179, 237);
}

// ─────────────────────────────────────────────────────────────
// Rounded Border utility  (UNCHANGED)
// ─────────────────────────────────────────────────────────────
class RoundedBorder implements Border {
    private final int r; private final Color c;
    RoundedBorder(int r, Color c) { this.r = r; this.c = c; }
    public void paintBorder(Component comp, Graphics g, int x, int y, int w, int h) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(c); g2.drawRoundRect(x, y, w-1, h-1, r, r); g2.dispose();
    }
    public Insets getBorderInsets(Component c) { return new Insets(r/2,r/2,r/2,r/2); }
    public boolean isBorderOpaque() { return false; }
}

// ─────────────────────────────────────────────────────────────
// Admin Login Frame  (UNCHANGED — backend untouched)
// ─────────────────────────────────────────────────────────────
class AdminLoginFrame extends JFrame {

    public AdminLoginFrame() {
        setTitle("Admin Login — Secure Bank");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1100, 650));
        setResizable(true);
        initUI();
    }

    private void initUI() {
        setLayout(new GridLayout(1, 2));

        JPanel left = new JPanel(new GridBagLayout()) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, Theme.NAVY_DEEP, getWidth(), getHeight(), Theme.NAVY_MID));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(new Color(255,255,255,8));
                g2.fillOval(-100, -100, 400, 400);
                g2.fillOval(getWidth()-180, getHeight()-180, 320, 320);
                g2.setColor(new Color(255,255,255,5));
                g2.fillOval(getWidth()/2-150, getHeight()/2-150, 300, 300);
            }
        };

        GridBagConstraints lc = new GridBagConstraints();
        lc.gridwidth = GridBagConstraints.REMAINDER;
        lc.anchor = GridBagConstraints.CENTER;
        lc.insets = new Insets(8, 40, 8, 40);

        JLabel bankIconBg = new JLabel("$", SwingConstants.CENTER) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255,255,255,20));
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.setColor(new Color(255,255,255,40));
                g2.setStroke(new BasicStroke(2));
                g2.drawOval(2, 2, getWidth()-4, getHeight()-4);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        bankIconBg.setFont(new Font("Arial", Font.BOLD, 38));
        bankIconBg.setForeground(new Color(147, 197, 253));
        bankIconBg.setPreferredSize(new Dimension(90, 90));
        bankIconBg.setOpaque(false);
        left.add(bankIconBg, lc);

        JLabel bankName = new JLabel("PHK BANK");
        bankName.setFont(new Font("Arial", Font.BOLD, 30));
        bankName.setForeground(Color.WHITE);
        lc.insets = new Insets(4, 40, 4, 40);
        left.add(bankName, lc);

        JLabel tagline = new JLabel("Admin Management System");
        tagline.setFont(new Font("Arial", Font.PLAIN, 13));
        tagline.setForeground(new Color(147, 197, 253));
        lc.insets = new Insets(0, 40, 22, 40);
        left.add(tagline, lc);

        JPanel sepLine = new JPanel() {
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(255,255,255,30));
                g.fillRect(0,0,getWidth(),1);
            }
        };
        sepLine.setOpaque(false);
        sepLine.setPreferredSize(new Dimension(220, 1));
        lc.fill = GridBagConstraints.HORIZONTAL;
        lc.insets = new Insets(0,40,20,40);
        left.add(sepLine, lc);
        lc.fill = GridBagConstraints.NONE;

        String[] features = {
                "Full Customer Management",
                "Account Freeze & Activate",
                "Real-time Transaction Monitoring",
                "Secure Admin Authentication"
        };
        for (String f : features) {
            JLabel fl = new JLabel(f);
            fl.setFont(new Font("Arial", Font.PLAIN, 13));
            fl.setForeground(new Color(186, 213, 248));
            lc.insets = new Insets(3, 30, 3, 30);
            lc.anchor = GridBagConstraints.WEST;
            left.add(fl, lc);
        }

        JPanel right = new JPanel(new GridBagLayout());
        right.setBackground(new Color(25, 44, 90));

        GridBagConstraints rc = new GridBagConstraints();
        rc.gridwidth = GridBagConstraints.REMAINDER;
        rc.fill = GridBagConstraints.HORIZONTAL;

        JPanel formCard = new JPanel(new GridBagLayout());
        formCard.setBackground(Color.WHITE);
        formCard.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(16, new Color(220,228,245)),
                BorderFactory.createEmptyBorder(36, 40, 36, 40)
        ));

        GridBagConstraints fc = new GridBagConstraints();
        fc.gridwidth = GridBagConstraints.REMAINDER;
        fc.fill = GridBagConstraints.HORIZONTAL;

        JLabel loginTitle = new JLabel("Welcome Back");
        loginTitle.setFont(new Font("Arial", Font.BOLD, 26));
        loginTitle.setForeground(Theme.NAVY);
        fc.insets = new Insets(0,0,4,0);
        formCard.add(loginTitle, fc);

        JLabel loginSub = new JLabel("Sign in to your administrator account");
        loginSub.setFont(new Font("Arial", Font.PLAIN, 12));
        loginSub.setForeground(Theme.TEXT_MID);
        fc.insets = new Insets(0,0,28,0);
        formCard.add(loginSub, fc);

        JTextField userField  = styledField();
        JPasswordField pwdField = new JPasswordField();
        stylePass(pwdField);

        fc.insets = new Insets(0,0,5,0);
        formCard.add(fieldLabel("Username"), fc);
        fc.insets = new Insets(0,0,14,0);
        formCard.add(userField, fc);
        fc.insets = new Insets(0,0,5,0);
        formCard.add(fieldLabel("Password"), fc);
        fc.insets = new Insets(0,0,22,0);
        formCard.add(pwdField, fc);

        JButton loginBtn = new JButton("SIGN IN AS ADMIN");
        loginBtn.setPreferredSize(new Dimension(0, 46));
        loginBtn.setBackground(Theme.NAVY);
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 13));
        loginBtn.setBorderPainted(false); loginBtn.setFocusPainted(false);
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { loginBtn.setBackground(Theme.NAVY_MID); }
            public void mouseExited(MouseEvent e)  { loginBtn.setBackground(Theme.NAVY); }
        });
        fc.insets = new Insets(0,0,10,0);
        formCard.add(loginBtn, fc);

        JButton backBtn = new JButton("Back to Customer Login");
        backBtn.setBackground(Color.WHITE); backBtn.setForeground(Theme.BLUE);
        backBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        backBtn.setBorderPainted(false); backBtn.setFocusPainted(false);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        fc.insets = new Insets(0,0,0,0);
        formCard.add(backBtn, fc);

        rc.insets = new Insets(0,50,0,50);
        right.add(formCard, rc);

        add(left); add(right);

        loginBtn.addActionListener(e -> {
            if (checkAdmin(userField.getText().trim(), new String(pwdField.getPassword()))) {
                new AdminDashboard().setVisible(true); dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid admin credentials!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        backBtn.addActionListener(e -> { new LoginFrame().setVisible(true); dispose(); });
    }

    // UNCHANGED — backend untouched
    private boolean checkAdmin(String user, String pwd) {
        try {
            Connection con = com.bank.util.DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM admin WHERE username=? AND password=?");
            ps.setString(1, user); ps.setString(2, pwd);
            return ps.executeQuery().next();
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    private JTextField styledField() {
        JTextField f = new JTextField();
        f.setPreferredSize(new Dimension(0, 42));
        f.setFont(new Font("Arial", Font.PLAIN, 13));
        f.setBackground(new Color(248,250,255)); f.setForeground(Theme.TEXT_DARK);
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER),
                BorderFactory.createEmptyBorder(8,12,8,12)));
        return f;
    }
    private void stylePass(JPasswordField f) {
        f.setPreferredSize(new Dimension(0, 42));
        f.setFont(new Font("Arial", Font.PLAIN, 13));
        f.setBackground(new Color(248,250,255)); f.setForeground(Theme.TEXT_DARK);
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER),
                BorderFactory.createEmptyBorder(8,12,8,12)));
    }
    private JLabel fieldLabel(String t) {
        JLabel l = new JLabel(t);
        l.setFont(new Font("Arial", Font.BOLD, 11));
        l.setForeground(new Color(60,75,110));
        return l;
    }
}

// ─────────────────────────────────────────────────────────────
// Admin Dashboard
// ─────────────────────────────────────────────────────────────
public class AdminDashboard extends JFrame {

    private final CustomerDAO customerDAO = new CustomerDAO(); // UNCHANGED
    private JPanel contentPanel;
    private JPanel activeNavPanel = null;   // tracks active nav item (JPanel, not JButton)

    // Sidebar width as a single constant used everywhere
    private static final int SW = 240;

    public AdminDashboard() {
        setTitle("Secure Bank — Admin Portal");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1200, 700));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        add(buildSidebar(), BorderLayout.WEST);
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Theme.BG);
        add(contentPanel, BorderLayout.CENTER);
        showAdminDashboard();
    }

    // ══════════════════════════════════════════════════════════
    //  SIDEBAR — uses GridBagLayout so every row is EXACTLY SW wide
    // ══════════════════════════════════════════════════════════
    private JPanel buildSidebar() {

        JPanel sidebar = new JPanel(new BorderLayout()) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(new GradientPaint(0, 0, Theme.NAVY_DEEP, 0, getHeight(), new Color(12, 28, 72)));
                g2.fillRect(0, 0, getWidth(), getHeight());
                // Right border line
                g2.setColor(new Color(255,255,255,12));
                g2.fillRect(getWidth()-1, 0, 1, getHeight());
            }
        };
        sidebar.setOpaque(false);
        sidebar.setPreferredSize(new Dimension(SW, 0));

        // ── Inner scroll container uses GridBagLayout so rows fill exactly SW ──
        JPanel nav = new JPanel(new GridBagLayout());
        nav.setOpaque(false);

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0; gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1.0; gc.insets = new Insets(0,0,0,0);
        int row = 0;

        // ── Logo row ──────────────────────────────────────────
        gc.gridy = row++;
        gc.insets = new Insets(20, 14, 14, 14);
        nav.add(buildLogoRow(), gc);

        // ── Profile chip ──────────────────────────────────────
        gc.gridy = row++;
        gc.insets = new Insets(0, 10, 10, 10);
        nav.add(buildProfileChip(), gc);

        // ── Divider ───────────────────────────────────────────
        gc.gridy = row++;
        gc.insets = new Insets(0, 0, 10, 0);
        nav.add(makeDivider(), gc);

        // ── OVERVIEW ──────────────────────────────────────────
        gc.gridy = row++;
        gc.insets = new Insets(0, 14, 4, 14);
        nav.add(sectionLabel("OVERVIEW"), gc);

        gc.gridy = row++;
        gc.insets = new Insets(0, 8, 2, 8);
        nav.add(makeNavItem("Dashboard",        "Stats & overview",       Theme.BLUE,   "="), gc);

        // ── ACCOUNTS ──────────────────────────────────────────
        gc.gridy = row++;
        gc.insets = new Insets(10, 14, 4, 14);
        nav.add(sectionLabel("ACCOUNTS"), gc);

        gc.gridy = row++;
        gc.insets = new Insets(0, 8, 2, 8);
        nav.add(makeNavItem("All Customers",    "Manage all accounts",    Theme.PURPLE, "#"), gc);

        gc.gridy = row++;
        nav.add(makeNavItem("Freeze Account",   "Suspend account access", Theme.RED,    "X"), gc);

        gc.gridy = row++;
        nav.add(makeNavItem("Activate Account", "Restore account access", Theme.GREEN,  "O"), gc);

        // ── FINANCE ───────────────────────────────────────────
        gc.gridy = row++;
        gc.insets = new Insets(10, 14, 4, 14);
        nav.add(sectionLabel("FINANCE"), gc);

        gc.gridy = row++;
        gc.insets = new Insets(0, 8, 2, 8);
        nav.add(makeNavItem("All Transactions", "Full transaction log",   Theme.AMBER,  "~"), gc);

        // Vertical filler so items stay top-aligned
        gc.gridy = row++;
        gc.weighty = 1.0;
        gc.insets = new Insets(0,0,0,0);
        nav.add(Box.createVerticalGlue(), gc);
        gc.weighty = 0;

        JScrollPane scroll = new JScrollPane(nav,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(3,0));
        scroll.getVerticalScrollBar().setUnitIncrement(12);
        sidebar.add(scroll, BorderLayout.CENTER);

        // ── Logout always visible at bottom ───────────────────
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);

        JPanel divWrap = new JPanel(new BorderLayout());
        divWrap.setOpaque(false);
        divWrap.add(makeDivider());

        JPanel logoutWrap = new JPanel(new BorderLayout());
        logoutWrap.setOpaque(false);
        logoutWrap.setBorder(BorderFactory.createEmptyBorder(6,8,10,8));
        logoutWrap.add(makeNavItem("Logout", "End session", Theme.RED, "!"));

        bottom.add(divWrap,    BorderLayout.NORTH);
        bottom.add(logoutWrap, BorderLayout.CENTER);
        sidebar.add(bottom, BorderLayout.SOUTH);

        return sidebar;
    }

    // ── Logo row ──────────────────────────────────────────────
    private JPanel buildLogoRow() {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setOpaque(false);

        JPanel logoCircle = new JPanel(new GridBagLayout()) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0,0,new Color(59,130,246),getWidth(),getHeight(),new Color(37,99,235)));
                g2.fillRoundRect(0,0,getWidth(),getHeight(),10,10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        logoCircle.setOpaque(false);
        Dimension ld = new Dimension(40,40);
        logoCircle.setPreferredSize(ld); logoCircle.setMinimumSize(ld); logoCircle.setMaximumSize(ld);
        JLabel ll = new JLabel("PHK");
        ll.setFont(new Font("Arial", Font.BOLD, 11));
        ll.setForeground(Color.WHITE);
        logoCircle.add(ll);

        JPanel logoText = new JPanel();
        logoText.setLayout(new BoxLayout(logoText, BoxLayout.Y_AXIS));
        logoText.setOpaque(false);
        JLabel brandLbl = new JLabel("PHK BANK");
        brandLbl.setFont(new Font("Arial", Font.BOLD, 14));
        brandLbl.setForeground(Color.WHITE);
        JLabel versionLbl = new JLabel("Admin Portal v2.0");
        versionLbl.setFont(new Font("Arial", Font.PLAIN, 10));
        versionLbl.setForeground(Theme.SIDEBAR_SUB);
        logoText.add(brandLbl);
        logoText.add(versionLbl);

        row.add(logoCircle, BorderLayout.WEST);
        row.add(logoText,   BorderLayout.CENTER);
        return row;
    }

    // ── Profile chip ──────────────────────────────────────────
    private JPanel buildProfileChip() {
        JPanel chip = new JPanel(new BorderLayout(10, 0)) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255,255,255,10));
                g2.fillRoundRect(0,0,getWidth(),getHeight(),10,10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        chip.setOpaque(false);
        chip.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));

        JPanel avatar = new JPanel(new GridBagLayout()) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(79,70,229));
                g2.fillOval(0,0,getWidth(),getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        avatar.setOpaque(false);
        Dimension ad = new Dimension(36,36);
        avatar.setPreferredSize(ad); avatar.setMinimumSize(ad); avatar.setMaximumSize(ad);
        JLabel avLbl = new JLabel("AD");
        avLbl.setFont(new Font("Arial", Font.BOLD, 11));
        avLbl.setForeground(Color.WHITE);
        avatar.add(avLbl);

        JPanel textCol = new JPanel();
        textCol.setLayout(new BoxLayout(textCol, BoxLayout.Y_AXIS));
        textCol.setOpaque(false);
        textCol.setBorder(BorderFactory.createEmptyBorder(1, 2, 1, 0));
        JLabel nameLbl = new JLabel("Administrator");
        nameLbl.setFont(new Font("Arial", Font.BOLD, 12));
        nameLbl.setForeground(Color.WHITE);
        nameLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel onlineRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        onlineRow.setOpaque(false);
        onlineRow.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
        onlineRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel dot = new JLabel("●");
        dot.setFont(new Font("Arial", Font.PLAIN, 8));
        dot.setForeground(new Color(34,197,94));
        JLabel onlineLbl = new JLabel("Online · Super Admin");
        onlineLbl.setFont(new Font("Arial", Font.PLAIN, 10));
        onlineLbl.setForeground(Theme.SIDEBAR_SUB);
        onlineRow.add(dot); onlineRow.add(onlineLbl);
        textCol.add(nameLbl); textCol.add(onlineRow);

        chip.add(avatar,  BorderLayout.WEST);
        chip.add(textCol, BorderLayout.CENTER);
        return chip;
    }

    // ══════════════════════════════════════════════════════════
    //  NAV ITEM — JPanel instead of JButton so BoxLayout/GBL
    //  honours width correctly and text never overflows
    // ══════════════════════════════════════════════════════════
    private JPanel makeNavItem(String label, String sub, Color accent, String sym) {
        // Container that draws the active/hover highlight
        JPanel item = new JPanel(new BorderLayout(10, 0)) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                boolean isActive = (this == activeNavPanel);
                if (isActive) {
                    g2.setColor(new Color(255,255,255,18));
                    g2.fillRoundRect(0,0,getWidth(),getHeight(),10,10);
                    // left accent bar
                    g2.setColor(accent);
                    g2.fillRoundRect(0, 8, 4, getHeight()-16, 4, 4);
                } else if (getClientProperty("hover") != null) {
                    g2.setColor(new Color(255,255,255,10));
                    g2.fillRoundRect(0,0,getWidth(),getHeight(),10,10);
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        item.setOpaque(false);
        item.setCursor(new Cursor(Cursor.HAND_CURSOR));
        item.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        // Height fixed; width comes from GridBagLayout (fills parent)
        item.setPreferredSize(new Dimension(SW - 16, 52));

        // ── Icon box ──────────────────────────────────────────
        JPanel iconBox = new JPanel(new GridBagLayout()) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                boolean isActive = (item == activeNavPanel);
                Color bg = isActive
                        ? accent
                        : new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 45);
                g2.setColor(bg);
                g2.fillRoundRect(0,0,getWidth(),getHeight(),8,8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        iconBox.setOpaque(false);
        Dimension id = new Dimension(34,34);
        iconBox.setPreferredSize(id); iconBox.setMinimumSize(id); iconBox.setMaximumSize(id);
        JLabel symLbl = new JLabel(sym, SwingConstants.CENTER);
        symLbl.setFont(new Font("Arial", Font.BOLD, 13));
        symLbl.setForeground(Color.WHITE);
        iconBox.add(symLbl);

        // ── Text column ───────────────────────────────────────
        JPanel txtCol = new JPanel();
        txtCol.setLayout(new BoxLayout(txtCol, BoxLayout.Y_AXIS));
        txtCol.setOpaque(false);

        JLabel mainLbl = new JLabel(label);
        mainLbl.setFont(new Font("Arial", Font.BOLD, 13));
        mainLbl.setForeground(Color.WHITE);

        JLabel subLbl = new JLabel(sub);
        subLbl.setFont(new Font("Arial", Font.PLAIN, 10));
        subLbl.setForeground(Theme.SIDEBAR_SUB);

        txtCol.add(mainLbl);
        txtCol.add(subLbl);

        item.add(iconBox, BorderLayout.WEST);
        item.add(txtCol,  BorderLayout.CENTER);

        // ── Hover & click handling ────────────────────────────
        item.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                item.putClientProperty("hover", Boolean.TRUE);
                item.repaint(); iconBox.repaint();
            }
            public void mouseExited(MouseEvent e) {
                item.putClientProperty("hover", null);
                item.repaint(); iconBox.repaint();
            }
            public void mousePressed(MouseEvent e) {
                activeNavPanel = item;
                item.getRootPane().repaint();
                handleNavAction(label);
            }
        });
        // Make children pass clicks up to the panel
        propagateMouseEvents(item, item);

        return item;
    }

    /** Forwards mouse events from child components to the nav item panel */
    private void propagateMouseEvents(JPanel navItem, JComponent target) {
        MouseListener fwd = new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                navItem.putClientProperty("hover", Boolean.TRUE);
                navItem.repaint();
            }
            public void mouseExited(MouseEvent e) {
                navItem.putClientProperty("hover", null);
                navItem.repaint();
            }
            public void mousePressed(MouseEvent e) {
                activeNavPanel = navItem;
                navItem.getRootPane().repaint();
                handleNavAction(((JLabel)((JPanel)navItem.getComponent(1)).getComponent(0)).getText());
            }
        };
        for (Component c : target.getComponents()) {
            c.addMouseListener(fwd);
            if (c instanceof JComponent) propagateMouseEvents(navItem, (JComponent)c);
        }
    }

    private void handleNavAction(String label) {
        switch (label) {
            case "Dashboard":        showAdminDashboard();  break;
            case "All Customers":    showAllCustomers();    break;
            case "Freeze Account":   showFreezeAccount();   break;
            case "Activate Account": showActivateAccount(); break;
            case "All Transactions": showAllTransactions(); break;
            case "Logout":
                new LoginFrame().setVisible(true); dispose(); break;
        }
    }

    // ── Sidebar divider ────────────────────────────────────────
    private JPanel makeDivider() {
        JPanel d = new JPanel() {
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(255,255,255,18));
                g.fillRect(0,0,getWidth(),1);
            }
        };
        d.setOpaque(false);
        d.setPreferredSize(new Dimension(SW, 1));
        return d;
    }

    // ── Section label ──────────────────────────────────────────
    private JLabel sectionLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Arial", Font.BOLD, 9));
        l.setForeground(new Color(100, 116, 139));
        return l;
    }

    // ══════════════════════════════════════════════════════════
    //  DASHBOARD VIEW  (UNCHANGED logic)
    // ══════════════════════════════════════════════════════════
    private void showAdminDashboard() {
        contentPanel.removeAll();

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Theme.BG);
        wrapper.setBorder(BorderFactory.createEmptyBorder(28,28,28,28));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(0,0,22,0));

        JPanel titleCol = new JPanel();
        titleCol.setLayout(new BoxLayout(titleCol, BoxLayout.Y_AXIS));
        titleCol.setOpaque(false);
        JLabel pageTitle = new JLabel("Dashboard Overview");
        pageTitle.setFont(new Font("Arial", Font.BOLD, 24));
        pageTitle.setForeground(Theme.TEXT_DARK);
        JLabel pageSub = new JLabel("Welcome back, Administrator — here's what's happening today.");
        pageSub.setFont(new Font("Arial", Font.PLAIN, 13));
        pageSub.setForeground(Theme.TEXT_MID);
        titleCol.add(pageTitle);
        titleCol.add(Box.createVerticalStrut(4));
        titleCol.add(pageSub);
        header.add(titleCol, BorderLayout.WEST);

        String dateStr = new java.text.SimpleDateFormat("EEE, dd MMM yyyy").format(new java.util.Date());
        JLabel dateBadge = new JLabel("  " + dateStr + "  ");
        dateBadge.setFont(new Font("Arial", Font.PLAIN, 11));
        dateBadge.setForeground(Theme.TEXT_MID);
        dateBadge.setBackground(Color.WHITE);
        dateBadge.setOpaque(true);
        dateBadge.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER),
                BorderFactory.createEmptyBorder(6,8,6,8)
        ));
        header.add(dateBadge, BorderLayout.EAST);
        wrapper.add(header, BorderLayout.NORTH);

        List<Customer> all = customerDAO.getAllCustomers(); // UNCHANGED
        int  total  = all.size();
        long active = all.stream().filter(c -> "ACTIVE".equals(c.getStatus())).count();
        long frozen = total - active;

        JPanel cardsRow = new JPanel(new GridLayout(1,3,16,0));
        cardsRow.setOpaque(false);
        cardsRow.add(statCard("Total Customers", String.valueOf(total),  "All registered accounts", Theme.BLUE,  "#"));
        cardsRow.add(statCard("Active Accounts",  String.valueOf(active), "Fully operational",       Theme.GREEN, "O"));
        cardsRow.add(statCard("Frozen Accounts",  String.valueOf(frozen), "Suspended access",        Theme.RED,   "X"));

        JPanel bottomRow = new JPanel(new GridLayout(1,2,16,0));
        bottomRow.setOpaque(false);
        bottomRow.setBorder(BorderFactory.createEmptyBorder(16,0,0,0));
        bottomRow.add(buildAccountHealthPanel(all, total, active, frozen));
        bottomRow.add(buildSystemInfoPanel(total, active, frozen));

        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);
        center.add(cardsRow,  BorderLayout.NORTH);
        center.add(bottomRow, BorderLayout.CENTER);
        wrapper.add(center, BorderLayout.CENTER);

        contentPanel.add(wrapper);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // ── Stat card ──────────────────────────────────────────────
    private JPanel statCard(String label, String value, String sub, Color accent, String sym) {
        JPanel card = new JPanel(new BorderLayout()) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0,0,getWidth(),getHeight(),14,14);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(new RoundedBorder(14, Theme.BORDER));

        JPanel topRow = new JPanel(new BorderLayout());
        topRow.setOpaque(false);
        topRow.setBorder(BorderFactory.createEmptyBorder(20,20,12,20));

        JPanel iconCircle = new JPanel(new GridBagLayout()) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 18));
                g2.fillOval(0,0,getWidth(),getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        iconCircle.setOpaque(false);
        iconCircle.setPreferredSize(new Dimension(48,48));
        iconCircle.setMinimumSize(new Dimension(48,48));
        JLabel symLbl = new JLabel(sym, SwingConstants.CENTER);
        symLbl.setFont(new Font("Arial", Font.BOLD, 18));
        symLbl.setForeground(accent);
        iconCircle.add(symLbl);

        JPanel textCol = new JPanel();
        textCol.setLayout(new BoxLayout(textCol, BoxLayout.Y_AXIS));
        textCol.setOpaque(false);
        textCol.setBorder(BorderFactory.createEmptyBorder(0,14,0,0));
        JLabel valLbl  = new JLabel(value);
        valLbl.setFont(new Font("Arial", Font.BOLD, 36));
        valLbl.setForeground(accent);
        JLabel nameLbl = new JLabel(label);
        nameLbl.setFont(new Font("Arial", Font.BOLD, 13));
        nameLbl.setForeground(Theme.TEXT_DARK);
        JLabel subLbl  = new JLabel(sub);
        subLbl.setFont(new Font("Arial", Font.PLAIN, 11));
        subLbl.setForeground(Theme.TEXT_LIGHT);
        textCol.add(valLbl); textCol.add(nameLbl); textCol.add(subLbl);

        topRow.add(iconCircle, BorderLayout.WEST);
        topRow.add(textCol,    BorderLayout.CENTER);
        card.add(topRow, BorderLayout.CENTER);

        JPanel bar = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0,0,accent,getWidth(),0,
                        new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 100)));
                g2.fillRect(0,0,getWidth(),getHeight());
                g2.dispose();
            }
        };
        bar.setOpaque(false);
        bar.setPreferredSize(new Dimension(0,4));
        card.add(bar, BorderLayout.SOUTH);
        return card;
    }

    // ══════════════════════════════════════════════════════════
    //  ACCOUNT HEALTH PANEL  (UNCHANGED logic)
    // ══════════════════════════════════════════════════════════
    private JPanel buildAccountHealthPanel(List<Customer> all, int total, long active, long frozen) {
        JPanel outer = new JPanel(new BorderLayout()) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0,0,getWidth(),getHeight(),14,14);
                g2.dispose();
            }
        };
        outer.setOpaque(false);
        outer.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(14, Theme.BORDER),
                BorderFactory.createEmptyBorder(18,20,18,20)
        ));

        JLabel panelTitle = new JLabel("Account Health");
        panelTitle.setFont(new Font("Arial", Font.BOLD, 15));
        panelTitle.setForeground(Theme.TEXT_DARK);
        JLabel panelSub = new JLabel("Live breakdown from the customer database");
        panelSub.setFont(new Font("Arial", Font.PLAIN, 11));
        panelSub.setForeground(Theme.TEXT_LIGHT);
        JPanel hdr = new JPanel();
        hdr.setLayout(new BoxLayout(hdr, BoxLayout.Y_AXIS));
        hdr.setOpaque(false);
        hdr.setBorder(BorderFactory.createEmptyBorder(0,0,16,0));
        hdr.add(panelTitle); hdr.add(Box.createVerticalStrut(3)); hdr.add(panelSub);
        outer.add(hdr, BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);

        int activePct = total > 0 ? (int)(active * 100 / total) : 0;
        int frozenPct = 100 - activePct;

        content.add(healthBar("Active Accounts", active, total, Theme.GREEN));
        content.add(Box.createVerticalStrut(14));
        content.add(healthBar("Frozen Accounts", frozen, total, Theme.RED));
        content.add(Box.createVerticalStrut(20));

        JLabel proportionLbl = new JLabel("Overall account status split");
        proportionLbl.setFont(new Font("Arial", Font.BOLD, 11));
        proportionLbl.setForeground(Theme.TEXT_MID);
        proportionLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(proportionLbl);
        content.add(Box.createVerticalStrut(8));

        final int ap = activePct;
        JPanel segBar = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth(), h = getHeight();
                g2.setColor(new Color(241,245,249));
                g2.fillRoundRect(0,0,w,h,h,h);
                int fillW = (int)(w * ap / 100.0);
                if (fillW > 0) {
                    g2.setPaint(new GradientPaint(0,0,new Color(34,197,94),fillW,0,new Color(22,163,74)));
                    g2.fillRoundRect(0,0,Math.min(fillW,w),h,h,h);
                }
                int frozenW = w - fillW;
                if (frozenW > 4) {
                    g2.setColor(new Color(220,38,38,60));
                    g2.fillRoundRect(fillW,0,frozenW,h,h,h);
                }
                g2.dispose();
            }
        };
        segBar.setOpaque(false);
        segBar.setPreferredSize(new Dimension(Integer.MAX_VALUE, 14));
        segBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 14));
        segBar.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(segBar);
        content.add(Box.createVerticalStrut(8));

        JPanel legend = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 0));
        legend.setOpaque(false);
        legend.setAlignmentX(Component.LEFT_ALIGNMENT);
        legend.add(legendDot(Theme.GREEN, activePct + "% Active"));
        legend.add(legendDot(Theme.RED,   frozenPct + "% Frozen"));
        content.add(legend);
        content.add(Box.createVerticalStrut(20));

        JLabel previewLbl = new JLabel("Recent Customers");
        previewLbl.setFont(new Font("Arial", Font.BOLD, 11));
        previewLbl.setForeground(Theme.TEXT_MID);
        previewLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(previewLbl);
        content.add(Box.createVerticalStrut(8));

        int show = Math.min(all.size(), 4);
        for (int i = 0; i < show; i++) {
            Customer c = all.get(i);
            boolean isActive = "ACTIVE".equals(c.getStatus());
            content.add(customerRow(c.getName(), c.getEmail(), isActive));
            if (i < show - 1) content.add(Box.createVerticalStrut(6));
        }
        if (all.isEmpty()) {
            JLabel empty = new JLabel("No customers found.");
            empty.setFont(new Font("Arial", Font.PLAIN, 12));
            empty.setForeground(Theme.TEXT_LIGHT);
            empty.setAlignmentX(Component.LEFT_ALIGNMENT);
            content.add(empty);
        }

        outer.add(content, BorderLayout.CENTER);
        return outer;
    }

    private JPanel healthBar(String label, long count, int total, Color accent) {
        JPanel row = new JPanel(new BorderLayout(0,5));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        JLabel nameLbl = new JLabel(label);
        nameLbl.setFont(new Font("Arial", Font.PLAIN, 12));
        nameLbl.setForeground(Theme.TEXT_DARK);
        JLabel cntLbl = new JLabel(count + " / " + total);
        cntLbl.setFont(new Font("Arial", Font.BOLD, 12));
        cntLbl.setForeground(accent);
        top.add(nameLbl, BorderLayout.WEST);
        top.add(cntLbl,  BorderLayout.EAST);
        row.add(top, BorderLayout.NORTH);

        int pct = total > 0 ? (int)(count * 100 / total) : 0;
        JPanel track = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(241,245,249));
                g2.fillRoundRect(0,0,getWidth(),getHeight(),getHeight(),getHeight());
                int fw = (int)(getWidth() * pct / 100.0);
                if (fw > 0) {
                    g2.setPaint(new GradientPaint(0,0,accent,fw,0,
                            new Color(accent.getRed(),accent.getGreen(),accent.getBlue(),150)));
                    g2.fillRoundRect(0,0,fw,getHeight(),getHeight(),getHeight());
                }
                g2.dispose();
            }
        };
        track.setOpaque(false);
        track.setPreferredSize(new Dimension(Integer.MAX_VALUE, 8));
        row.add(track, BorderLayout.CENTER);
        return row;
    }

    private JPanel legendDot(Color color, String text) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        p.setOpaque(false);
        JLabel dot = new JLabel("●");
        dot.setFont(new Font("Arial", Font.PLAIN, 10));
        dot.setForeground(color);
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Arial", Font.PLAIN, 11));
        lbl.setForeground(Theme.TEXT_MID);
        p.add(dot); p.add(lbl);
        return p;
    }

    private JPanel customerRow(String name, String email, boolean isActive) {
        JPanel row = new JPanel(new BorderLayout(10,0)) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(248,250,255));
                g2.fillRoundRect(0,0,getWidth(),getHeight(),8,8);
                g2.dispose();
            }
        };
        row.setOpaque(false);
        row.setBorder(BorderFactory.createEmptyBorder(7,10,7,10));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        String initials = name.trim().isEmpty() ? "?" :
                (name.trim().split("\\s+").length > 1
                        ? "" + name.trim().split("\\s+")[0].charAt(0) + name.trim().split("\\s+")[1].charAt(0)
                        : "" + name.trim().charAt(0));
        JPanel ava = new JPanel(new GridBagLayout()) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(99,102,241,80));
                g2.fillOval(0,0,getWidth(),getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        ava.setOpaque(false);
        ava.setPreferredSize(new Dimension(28,28));
        ava.setMinimumSize(new Dimension(28,28));
        JLabel avaLbl = new JLabel(initials.toUpperCase());
        avaLbl.setFont(new Font("Arial", Font.BOLD, 9));
        avaLbl.setForeground(new Color(79,70,229));
        ava.add(avaLbl);

        JPanel txt = new JPanel();
        txt.setLayout(new BoxLayout(txt, BoxLayout.Y_AXIS));
        txt.setOpaque(false);
        JLabel nameLbl = new JLabel(name);
        nameLbl.setFont(new Font("Arial", Font.BOLD, 12));
        nameLbl.setForeground(Theme.TEXT_DARK);
        JLabel emailLbl = new JLabel(email != null ? email : "");
        emailLbl.setFont(new Font("Arial", Font.PLAIN, 10));
        emailLbl.setForeground(Theme.TEXT_LIGHT);
        txt.add(nameLbl); txt.add(emailLbl);

        JLabel statusBadge = new JLabel(isActive ? "● Active" : "● Frozen");
        statusBadge.setFont(new Font("Arial", Font.BOLD, 10));
        statusBadge.setForeground(isActive ? Theme.GREEN : Theme.RED);

        row.add(ava,         BorderLayout.WEST);
        row.add(txt,         BorderLayout.CENTER);
        row.add(statusBadge, BorderLayout.EAST);
        return row;
    }

    // ══════════════════════════════════════════════════════════
    //  SYSTEM INFO PANEL  (UNCHANGED logic)
    // ══════════════════════════════════════════════════════════
    private JPanel buildSystemInfoPanel(int total, long active, long frozen) {
        JPanel outer = new JPanel(new BorderLayout()) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0,0,getWidth(),getHeight(),14,14);
                g2.dispose();
            }
        };
        outer.setOpaque(false);
        outer.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(14, Theme.BORDER),
                BorderFactory.createEmptyBorder(18,20,18,20)
        ));

        JLabel hTitle = new JLabel("System Info");
        hTitle.setFont(new Font("Arial", Font.BOLD, 15));
        hTitle.setForeground(Theme.TEXT_DARK);
        hTitle.setBorder(BorderFactory.createEmptyBorder(0,0,16,0));
        outer.add(hTitle, BorderLayout.NORTH);

        JPanel rows = new JPanel();
        rows.setLayout(new BoxLayout(rows, BoxLayout.Y_AXIS));
        rows.setOpaque(false);

        rows.add(infoRow("System",      "Internet Banking Portal", null));
        rows.add(infoRow("Version",     "2.0",                     null));
        rows.add(infoRow("Admin Role",  "Super Administrator",     null));
        rows.add(infoRow("Status",      "● Online",                Theme.GREEN));
        rows.add(infoRow("Total Users", String.valueOf(total),      Theme.BLUE));
        rows.add(infoRow("Active",      String.valueOf(active),     Theme.GREEN));
        rows.add(infoRow("Frozen",      String.valueOf(frozen),     frozen > 0 ? Theme.RED : Theme.TEXT_MID));

        if (total > 0) {
            rows.add(Box.createVerticalStrut(14));
            JLabel barLbl = new JLabel("Account Health");
            barLbl.setFont(new Font("Arial", Font.BOLD, 11));
            barLbl.setForeground(Theme.TEXT_MID);
            barLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
            rows.add(barLbl);
            rows.add(Box.createVerticalStrut(6));

            int pct = (int)(active * 100 / total);
            JPanel track = new JPanel() {
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(226,232,240));
                    g2.fillRoundRect(0,0,getWidth(),getHeight(),8,8);
                    int fw = (int)(getWidth() * pct / 100.0);
                    g2.setColor(Theme.GREEN);
                    g2.fillRoundRect(0,0,fw,getHeight(),8,8);
                    g2.dispose();
                }
            };
            track.setOpaque(false);
            track.setPreferredSize(new Dimension(Integer.MAX_VALUE,10));
            track.setMaximumSize(new Dimension(Integer.MAX_VALUE,10));
            track.setAlignmentX(Component.LEFT_ALIGNMENT);
            rows.add(track);
            rows.add(Box.createVerticalStrut(4));

            JLabel pctLbl = new JLabel(pct + "% accounts fully active");
            pctLbl.setFont(new Font("Arial", Font.PLAIN, 10));
            pctLbl.setForeground(Theme.TEXT_LIGHT);
            pctLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
            rows.add(pctLbl);
        }

        outer.add(rows, BorderLayout.CENTER);
        return outer;
    }

    private JPanel infoRow(String key, String val, Color valColor) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        row.setBorder(BorderFactory.createMatteBorder(0,0,1,0, new Color(241,245,249)));
        JLabel k = new JLabel(key);
        k.setFont(new Font("Arial", Font.PLAIN, 12));
        k.setForeground(Theme.TEXT_MID);
        JLabel v = new JLabel(val);
        v.setFont(new Font("Arial", Font.BOLD, 12));
        v.setForeground(valColor != null ? valColor : Theme.TEXT_DARK);
        v.setHorizontalAlignment(SwingConstants.RIGHT);
        row.add(k, BorderLayout.WEST);
        row.add(v, BorderLayout.EAST);
        return row;
    }

    // ══════════════════════════════════════════════════════════
    //  ALL CUSTOMERS  (UNCHANGED backend)
    // ══════════════════════════════════════════════════════════
    private void showAllCustomers() {
        contentPanel.removeAll();
        JPanel wrapper = pageWrapper("All Customers", "Manage and monitor all registered customer accounts");
        List<Customer> customers = customerDAO.getAllCustomers(); // UNCHANGED

        String[] cols = {"ID", "Full Name", "Phone", "Email", "Status"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        for (Customer c : customers)
            model.addRow(new Object[]{c.getCustomerId(), c.getName(), c.getPhone(), c.getEmail(), c.getStatus()});

        JTable table = styledTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);
        table.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object v, boolean sel, boolean foc, int r, int c) {
                JLabel l = (JLabel) super.getTableCellRendererComponent(t,v,sel,foc,r,c);
                String s = v != null ? v.toString() : "";
                l.setText("ACTIVE".equals(s) ? "● ACTIVE" : "● " + s);
                l.setForeground("ACTIVE".equals(s) ? Theme.GREEN : Theme.RED);
                l.setFont(new Font("Arial", Font.BOLD, 11));
                setBorder(BorderFactory.createEmptyBorder(0,14,0,14));
                return l;
            }
        });

        wrapper.add(tableCard(table), BorderLayout.CENTER);
        contentPanel.add(wrapper);
        contentPanel.revalidate(); contentPanel.repaint();
    }

    // ══════════════════════════════════════════════════════════
    //  FREEZE / ACTIVATE  (UNCHANGED backend calls)
    // ══════════════════════════════════════════════════════════
    private void showFreezeAccount() {
        String id = inputDialog("Freeze Account", "Enter the Customer ID to freeze:");
        if (id != null && !id.trim().isEmpty()) {
            try {
                int cid = Integer.parseInt(id.trim());
                if (customerDAO.updateStatus(cid, "FROZEN"))  // UNCHANGED
                    successMsg("Account #" + cid + " has been frozen successfully.");
                else errorMsg("No customer found with ID: " + cid);
            } catch (NumberFormatException e) { errorMsg("Please enter a valid numeric Customer ID."); }
        }
    }

    private void showActivateAccount() {
        String id = inputDialog("Activate Account", "Enter the Customer ID to activate:");
        if (id != null && !id.trim().isEmpty()) {
            try {
                int cid = Integer.parseInt(id.trim());
                if (customerDAO.updateStatus(cid, "ACTIVE"))  // UNCHANGED
                    successMsg("Account #" + cid + " has been activated successfully.");
                else errorMsg("No customer found with ID: " + cid);
            } catch (NumberFormatException e) { errorMsg("Please enter a valid numeric Customer ID."); }
        }
    }

    // ══════════════════════════════════════════════════════════
    //  ALL TRANSACTIONS  (UNCHANGED SQL + DB call)
    // ══════════════════════════════════════════════════════════
    private void showAllTransactions() {
        contentPanel.removeAll();
        JPanel wrapper = pageWrapper("All Transactions", "Complete transaction log across all customer accounts");
        String sql = "SELECT t.*, a.customer_id FROM transaction t " +
                "JOIN account a ON t.account_no = a.account_no ORDER BY t.date DESC";
        String[] cols = {"Tx ID", "Account No", "Type", "Amount", "Date", "Description"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        try {
            Connection con = com.bank.util.DBConnection.getConnection(); // UNCHANGED
            ResultSet rs   = con.createStatement().executeQuery(sql);
            while (rs.next())
                model.addRow(new Object[]{
                        rs.getInt("transaction_id"), rs.getString("account_no"),
                        rs.getString("type"), String.format("Rs %.2f", rs.getDouble("amount")),
                        rs.getTimestamp("date"),     rs.getString("description")
                });
        } catch (Exception e) { e.printStackTrace(); }

        JTable table = styledTable(model);
        table.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object v, boolean sel, boolean foc, int r, int c) {
                JLabel l = (JLabel) super.getTableCellRendererComponent(t,v,sel,foc,r,c);
                String type = v != null ? v.toString().toUpperCase() : "";
                l.setForeground("CREDIT".equals(type) ? Theme.GREEN : Theme.RED);
                l.setFont(new Font("Arial", Font.BOLD, 11));
                setBorder(BorderFactory.createEmptyBorder(0,14,0,14));
                return l;
            }
        });

        wrapper.add(tableCard(table), BorderLayout.CENTER);
        contentPanel.add(wrapper);
        contentPanel.revalidate(); contentPanel.repaint();
    }

    // ══════════════════════════════════════════════════════════
    //  SHARED UI HELPERS  (UNCHANGED logic)
    // ══════════════════════════════════════════════════════════
    private JPanel pageWrapper(String title, String subtitle) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Theme.BG);
        wrapper.setBorder(BorderFactory.createEmptyBorder(28,28,28,28));
        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setOpaque(false);
        top.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
        JLabel t = new JLabel(title);
        t.setFont(new Font("Arial", Font.BOLD, 24));
        t.setForeground(Theme.TEXT_DARK);
        JLabel s = new JLabel(subtitle);
        s.setFont(new Font("Arial", Font.PLAIN, 13));
        s.setForeground(Theme.TEXT_MID);
        top.add(t); top.add(Box.createVerticalStrut(4)); top.add(s);
        wrapper.add(top, BorderLayout.NORTH);
        return wrapper;
    }

    private JPanel tableCard(JTable table) {
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);
        JPanel card = new JPanel(new BorderLayout()) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0,0,getWidth(),getHeight(),14,14);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(new RoundedBorder(14, Theme.BORDER));
        card.add(scroll);
        return card;
    }

    private JTable styledTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setRowHeight(42);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setForeground(Theme.TEXT_DARK);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(new Color(239,246,255));
        table.setSelectionForeground(Theme.TEXT_DARK);
        table.setGridColor(new Color(241,245,249));
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0,0));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 11));
        header.setBackground(new Color(248,250,255));
        header.setForeground(Theme.TEXT_MID);
        header.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Theme.BORDER));
        header.setPreferredSize(new Dimension(0,40));
        header.setReorderingAllowed(false);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object v, boolean sel, boolean foc, int r, int c) {
                super.getTableCellRendererComponent(t,v,sel,foc,r,c);
                if (!sel) setBackground(r%2==0 ? Color.WHITE : new Color(249,251,255));
                setBorder(BorderFactory.createEmptyBorder(0,14,0,14));
                setFont(new Font("Arial", Font.PLAIN, 12));
                return this;
            }
        });
        return table;
    }

    private String inputDialog(String title, String msg) {
        JPanel p = new JPanel(new BorderLayout(0,10));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        JLabel m = new JLabel(msg);
        m.setFont(new Font("Arial", Font.PLAIN, 13));
        m.setForeground(Theme.TEXT_DARK);
        JTextField f = new JTextField();
        f.setPreferredSize(new Dimension(280,38));
        f.setFont(new Font("Arial", Font.PLAIN, 13));
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER),
                BorderFactory.createEmptyBorder(6,10,6,10)));
        p.add(m, BorderLayout.NORTH);
        p.add(f, BorderLayout.CENTER);
        int res = JOptionPane.showConfirmDialog(this, p, title,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        return res == JOptionPane.OK_OPTION ? f.getText() : null;
    }

    private void successMsg(String msg) {
        JOptionPane.showMessageDialog(this,
                "<html><font color='#16a34a'><b>Success</b></font><br><br>" + msg + "</html>",
                "Done", JOptionPane.PLAIN_MESSAGE);
    }
    private void errorMsg(String msg) {
        JOptionPane.showMessageDialog(this,
                "<html><font color='#dc2626'><b>Error</b></font><br><br>" + msg + "</html>",
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}