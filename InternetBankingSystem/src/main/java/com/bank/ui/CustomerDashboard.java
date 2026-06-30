package com.bank.ui;

import com.bank.dao.AccountDAO;
import com.bank.dao.CustomerDAO;
import com.bank.model.Account;
import com.bank.model.Customer;
import com.bank.model.Transaction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CustomerDashboard extends JFrame {

    private Customer customer;
    private Account  account;
    private AccountDAO  accountDAO  = new AccountDAO();
    private CustomerDAO customerDAO = new CustomerDAO();

    private JPanel sidePanel, contentPanel;
    private JLabel accountNoLabel;
    private JButton activeBtn = null;

    public CustomerDashboard(Customer customer) {
        this.customer = customer;
        this.account  = accountDAO.getAccountByCustomerId(customer.getCustomerId());
        setTitle("Internet Banking - " + customer.getName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setResizable(false);
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // ── Sidebar ───────────────────────────────────────────────────
        sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(new Color(13, 27, 62));
        sidePanel.setPreferredSize(new Dimension(260, 0));
        sidePanel.setMaximumSize(new Dimension(260, Integer.MAX_VALUE));

        // ── Logo block ────────────────────────────────────────────────
        JPanel logoBlock = new JPanel(new BorderLayout());
        logoBlock.setBackground(new Color(10, 20, 50));
        logoBlock.setBorder(BorderFactory.createEmptyBorder(18, 16, 18, 16));
        logoBlock.setMaximumSize(new Dimension(260, 72));
        logoBlock.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel logoLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        logoLeft.setOpaque(false);

        JLabel badge = new JLabel("PHK");
        badge.setFont(new Font("Arial", Font.BOLD, 13));
        badge.setForeground(Color.WHITE);
        badge.setOpaque(true);
        badge.setBackground(new Color(0, 100, 220));
        badge.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));

        JPanel badgeWrapper = new JPanel(new GridBagLayout());
        badgeWrapper.setOpaque(false);
        badgeWrapper.add(badge);

        JPanel logoText = new JPanel();
        logoText.setLayout(new BoxLayout(logoText, BoxLayout.Y_AXIS));
        logoText.setOpaque(false);

        JLabel logoName = new JLabel("PHK BANK");
        logoName.setFont(new Font("Arial", Font.BOLD, 15));
        logoName.setForeground(Color.WHITE);

        JLabel logoSub = new JLabel("");
        logoSub.setFont(new Font("Arial", Font.PLAIN, 11));
        logoSub.setForeground(new Color(120, 140, 180));

        logoText.add(logoName);
        logoText.add(logoSub);

        logoLeft.add(badgeWrapper);
        logoLeft.add(logoText);
        logoBlock.add(logoLeft, BorderLayout.WEST);

        JSeparator logoDivider = new JSeparator();
        logoDivider.setForeground(new Color(30, 50, 90));
        logoDivider.setMaximumSize(new Dimension(260, 1));

        sidePanel.add(logoBlock);
        sidePanel.add(logoDivider);
        sidePanel.add(Box.createVerticalStrut(10));

        // ── User info card ────────────────────────────────────────────
        JPanel userCard = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        userCard.setBackground(new Color(20, 38, 80));
        userCard.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        userCard.setMaximumSize(new Dimension(260, 68));
        userCard.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel avatarLabel = new JLabel(customer.getName().substring(0, 2).toUpperCase());
        avatarLabel.setFont(new Font("Arial", Font.BOLD, 14));
        avatarLabel.setForeground(Color.WHITE);
        avatarLabel.setOpaque(true);
        avatarLabel.setBackground(new Color(80, 60, 160));
        avatarLabel.setPreferredSize(new Dimension(38, 38));
        avatarLabel.setHorizontalAlignment(SwingConstants.CENTER);
        avatarLabel.setVerticalAlignment(SwingConstants.CENTER);

        JPanel userText = new JPanel();
        userText.setLayout(new BoxLayout(userText, BoxLayout.Y_AXIS));
        userText.setOpaque(false);

        JLabel userName = new JLabel(customer.getName());
        userName.setFont(new Font("Arial", Font.BOLD, 13));
        userName.setForeground(Color.WHITE);

        JLabel userStatus = new JLabel("  Online  \u00b7  Customer");
        userStatus.setFont(new Font("Arial", Font.PLAIN, 11));
        userStatus.setForeground(new Color(80, 200, 120));

        userText.add(userName);
        userText.add(userStatus);

        accountNoLabel = new JLabel(account != null ? account.getAccountNo() : "N/A");
        accountNoLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        accountNoLabel.setForeground(new Color(120, 140, 180));

        userCard.add(avatarLabel);
        userCard.add(userText);
        sidePanel.add(userCard);

        JSeparator userDivider = new JSeparator();
        userDivider.setForeground(new Color(30, 50, 90));
        userDivider.setMaximumSize(new Dimension(260, 1));
        sidePanel.add(userDivider);
        sidePanel.add(Box.createVerticalStrut(8));

        // ── Menu sections ─────────────────────────────────────────────
        addSectionLabel("OVERVIEW");
        JButton dashBtn = addMenuButton("Dashboard", "Stats & overview", "\u2261");
        addSectionLabel("ACCOUNTS");
        JButton balBtn  = addMenuButton("Balance Inquiry",    "View account balance",  "#");
        JButton trfBtn  = addMenuButton("Transfer Money",     "Send money instantly",  "\u2192");
        JButton txBtn   = addMenuButton("Transaction History","Full statement log",     "\u2630");
        JButton dwBtn   = addMenuButton("Deposit / Withdraw", "Add or withdraw funds", "$");
        addSectionLabel("PROFILE");
        JButton profBtn = addMenuButton("Profile",            "Update your details",   "\u25CF");
        JButton pwBtn   = addMenuButton("Change Password",    "Reset credentials",     "*");

        sidePanel.add(Box.createVerticalGlue());

        // ── Logout at bottom ─────────────────────────────────────────
        JSeparator btmDivider = new JSeparator();
        btmDivider.setForeground(new Color(30, 50, 90));
        btmDivider.setMaximumSize(new Dimension(260, 1));
        sidePanel.add(btmDivider);
        JButton logoutBtn = addMenuButton("Logout", "End session", "!");

        // ── Action listeners ──────────────────────────────────────────
        dashBtn.addActionListener(e -> { setActive(dashBtn); showDashboard(); });
        balBtn .addActionListener(e -> { setActive(balBtn);  showBalanceInquiry(); });
        trfBtn .addActionListener(e -> { setActive(trfBtn);  showTransfer(); });
        txBtn  .addActionListener(e -> { setActive(txBtn);   showTransactionHistory(); });
        dwBtn  .addActionListener(e -> { setActive(dwBtn);   showDepositWithdraw(); });
        profBtn.addActionListener(e -> { setActive(profBtn); showProfile(); });
        pwBtn  .addActionListener(e -> { setActive(pwBtn);   showChangePassword(); });
        logoutBtn.addActionListener(e -> {
            int c = JOptionPane.showConfirmDialog(this, "Logout?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (c == JOptionPane.YES_OPTION) { new LoginFrame().setVisible(true); dispose(); }
        });

        setActive(dashBtn);

        // ── Content panel ─────────────────────────────────────────────
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(240, 243, 250));
        showDashboard();

        add(sidePanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    // ── Section label helper ──────────────────────────────────────────
    private void addSectionLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Arial", Font.BOLD, 10));
        lbl.setForeground(new Color(80, 100, 140));
        lbl.setBorder(BorderFactory.createEmptyBorder(12, 16, 4, 16));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        lbl.setMaximumSize(new Dimension(260, 28));
        sidePanel.add(lbl);
    }

    // ── Menu button helper ────────────────────────────────────────────
    private JButton addMenuButton(String title, String subtitle, String icon) {
        JButton btn = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getBackground().equals(new Color(0, 70, 160))) {
                    g2.setColor(new Color(255, 152, 0));
                    g2.fillRect(0, 0, 4, getHeight());
                    g2.setColor(new Color(20, 40, 90));
                    g2.fillRect(4, 0, getWidth() - 4, getHeight());
                } else {
                    g2.setColor(getBackground());
                    g2.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };

        btn.setLayout(new BorderLayout());
        btn.setMaximumSize(new Dimension(260, 52));
        btn.setPreferredSize(new Dimension(260, 52));
        btn.setBackground(new Color(13, 27, 62));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Left icon badge
        JLabel iconBadge = new JLabel(icon, SwingConstants.CENTER);
        iconBadge.setFont(new Font("Arial", Font.BOLD, 13));
        iconBadge.setForeground(Color.WHITE);
        iconBadge.setOpaque(true);
        iconBadge.setBackground(new Color(40, 60, 110));
        iconBadge.setPreferredSize(new Dimension(52, 52));
        iconBadge.setHorizontalAlignment(SwingConstants.CENTER);
        iconBadge.setVerticalAlignment(SwingConstants.CENTER);

        // Right text area
        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);
        textPanel.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 8));

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(new Font("Arial", Font.BOLD, 13));
        titleLbl.setForeground(Color.WHITE);

        JLabel subLbl = new JLabel(subtitle);
        subLbl.setFont(new Font("Arial", Font.PLAIN, 11));
        subLbl.setForeground(new Color(100, 130, 180));

        textPanel.add(titleLbl);
        textPanel.add(subLbl);

        btn.add(iconBadge, BorderLayout.WEST);
        btn.add(textPanel, BorderLayout.CENTER);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (!btn.getBackground().equals(new Color(0, 70, 160)))
                    btn.setBackground(new Color(22, 44, 88));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (!btn.getBackground().equals(new Color(0, 70, 160)))
                    btn.setBackground(new Color(13, 27, 62));
            }
        });

        sidePanel.add(btn);
        return btn;
    }

    // ── Set active button ─────────────────────────────────────────────
    private void setActive(JButton btn) {
        if (activeBtn != null) activeBtn.setBackground(new Color(13, 27, 62));
        activeBtn = btn;
        btn.setBackground(new Color(0, 70, 160));
    }

    // ── DASHBOARD ────────────────────────────────────────────────────
    private void showDashboard() {
        contentPanel.removeAll();
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 243, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(28, 28, 28, 28));

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(240, 243, 250));
        topBar.setBorder(BorderFactory.createEmptyBorder(0, 0, 22, 0));

        JLabel heading = new JLabel("Welcome back, " + customer.getName() + " ");
        heading.setFont(new Font("Arial", Font.BOLD, 26));
        heading.setForeground(new Color(13, 27, 62));

        java.time.LocalDate today = java.time.LocalDate.now();
        JLabel dateLabel = new JLabel(today.getDayOfWeek().toString().substring(0,1)
                + today.getDayOfWeek().toString().substring(1).toLowerCase()
                + ", " + today.getDayOfMonth() + " "
                + today.getMonth().toString().substring(0,1)
                + today.getMonth().toString().substring(1).toLowerCase()
                + " " + today.getYear());
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        dateLabel.setForeground(new Color(120, 130, 150));

        topBar.add(heading, BorderLayout.WEST);
        topBar.add(dateLabel, BorderLayout.EAST);
        panel.add(topBar, BorderLayout.NORTH);

        double balance = account != null ? account.getBalance() : 0;
        JPanel cards = new JPanel(new GridLayout(1, 3, 20, 0));
        cards.setBackground(new Color(240, 243, 250));
        cards.setBorder(BorderFactory.createEmptyBorder(0, 0, 28, 0));

        cards.add(createStatCard("Current Balance", String.format("₹ %,.2f", balance), new Color(0, 123, 255), ""));
        cards.add(createStatCard("Account Type", account != null ? account.getAccountType() : "N/A", new Color(40, 167, 69), ""));
        cards.add(createStatCard("Account Status", customer.getStatus(), new Color(255, 152, 0), ""));

        JPanel centerSection = new JPanel(new BorderLayout());
        centerSection.setBackground(new Color(240, 243, 250));
        centerSection.add(cards, BorderLayout.NORTH);

        if (account != null) {
            List<Transaction> txList = accountDAO.getTransactionHistory(account.getAccountNo());
            JPanel txHeader = new JPanel(new BorderLayout());
            txHeader.setBackground(new Color(240, 243, 250));
            txHeader.setBorder(BorderFactory.createEmptyBorder(4, 0, 12, 0));

            JLabel txTitle = new JLabel("Recent Transactions");
            txTitle.setFont(new Font("Arial", Font.BOLD, 17));
            txTitle.setForeground(new Color(13, 27, 62));

            JLabel txCount = new JLabel("Last " + Math.min(txList.size(), 5) + " entries");
            txCount.setFont(new Font("Arial", Font.PLAIN, 12));
            txCount.setForeground(new Color(120, 130, 150));

            txHeader.add(txTitle, BorderLayout.WEST);
            txHeader.add(txCount, BorderLayout.EAST);

            JPanel txPanel = createTransactionTable(txList, 5);
            JPanel bottom = new JPanel(new BorderLayout());
            bottom.setBackground(new Color(240, 243, 250));
            bottom.add(txHeader, BorderLayout.NORTH);
            bottom.add(txPanel, BorderLayout.CENTER);
            centerSection.add(bottom, BorderLayout.CENTER);
        }

        panel.add(centerSection, BorderLayout.CENTER);
        contentPanel.add(panel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // ── BALANCE INQUIRY ───────────────────────────────────────────────
    private void showBalanceInquiry() {
        contentPanel.removeAll();
        account = accountDAO.getAccountByCustomerId(customer.getCustomerId());

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 243, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(28, 28, 28, 28));

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(240, 243, 250));
        topBar.setBorder(BorderFactory.createEmptyBorder(0, 0, 22, 0));
        JLabel heading = new JLabel("Balance Inquiry");
        heading.setFont(new Font("Arial", Font.BOLD, 26));
        heading.setForeground(new Color(13, 27, 62));
        JLabel sub = new JLabel("Your account summary at a glance");
        sub.setFont(new Font("Arial", Font.PLAIN, 13));
        sub.setForeground(new Color(120, 130, 150));
        topBar.add(heading, BorderLayout.WEST);
        topBar.add(sub, BorderLayout.EAST);
        panel.add(topBar, BorderLayout.NORTH);

        JPanel balanceCard = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, new Color(0, 90, 200), getWidth(), getHeight(), new Color(0, 40, 120)));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
            }
        };
        balanceCard.setOpaque(false);
        balanceCard.setBorder(BorderFactory.createEmptyBorder(32, 36, 32, 36));

        JPanel balLeft = new JPanel();
        balLeft.setLayout(new BoxLayout(balLeft, BoxLayout.Y_AXIS));
        balLeft.setOpaque(false);
        JLabel balTitle = new JLabel("CURRENT BALANCE");
        balTitle.setFont(new Font("Arial", Font.BOLD, 12));
        balTitle.setForeground(new Color(180, 210, 255));
        JLabel balValue = new JLabel(String.format("₹ %,.2f", account.getBalance()));
        balValue.setFont(new Font("Arial", Font.BOLD, 40));
        balValue.setForeground(Color.WHITE);
        balValue.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        JLabel accNoTag = new JLabel("Account No:  " + account.getAccountNo() + "     |     Type:  " + account.getAccountType());
        accNoTag.setFont(new Font("Arial", Font.PLAIN, 13));
        accNoTag.setForeground(new Color(180, 210, 255));
        balLeft.add(balTitle); balLeft.add(balValue); balLeft.add(accNoTag);

        JPanel balRight = new JPanel(new GridBagLayout());
        balRight.setOpaque(false);
        JLabel statusBadge = new JLabel("● " + customer.getStatus());
        statusBadge.setFont(new Font("Arial", Font.BOLD, 15));
        statusBadge.setForeground(new Color(80, 220, 130));
        balRight.add(statusBadge);
        balanceCard.add(balLeft, BorderLayout.CENTER);
        balanceCard.add(balRight, BorderLayout.EAST);

        JPanel detailRow = new JPanel(new GridLayout(1, 3, 20, 0));
        detailRow.setBackground(new Color(240, 243, 250));
        detailRow.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        detailRow.add(createInfoCard("Account Holder", customer.getName(), new Color(0, 123, 255)));
        detailRow.add(createInfoCard("Account Type", account.getAccountType(), new Color(40, 167, 69)));
        detailRow.add(createInfoCard("Account Number", account.getAccountNo(), new Color(255, 152, 0)));

        JPanel detailWrapper = new JPanel(new BorderLayout());
        detailWrapper.setBackground(new Color(240, 243, 250));
        detailWrapper.setPreferredSize(new Dimension(0, 160));
        detailWrapper.add(detailRow, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(0, 0));
        centerPanel.setBackground(new Color(240, 243, 250));
        centerPanel.add(balanceCard, BorderLayout.NORTH);
        centerPanel.add(detailWrapper, BorderLayout.CENTER);
        panel.add(centerPanel, BorderLayout.CENTER);

        contentPanel.add(panel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JPanel createInfoCard(String label, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(color);
                g.fillRect(0, 0, getWidth(), 5);
            }
        };
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(0, 130));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 235)),
                BorderFactory.createEmptyBorder(18, 22, 18, 22)));
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.BOLD, 13));
        lbl.setForeground(new Color(100, 110, 130));
        JLabel val = new JLabel(value);
        val.setFont(new Font("Arial", Font.BOLD, 22));
        val.setForeground(color);
        val.setHorizontalAlignment(SwingConstants.LEFT);
        JPanel bottomBar = new JPanel();
        bottomBar.setBackground(color);
        bottomBar.setPreferredSize(new Dimension(0, 4));
        card.add(lbl, BorderLayout.NORTH);
        card.add(val, BorderLayout.CENTER);
        card.add(bottomBar, BorderLayout.SOUTH);
        return card;
    }

    // ── TRANSFER MONEY ────────────────────────────────────────────────
    private void showTransfer() {
        contentPanel.removeAll();
        JPanel panel = createContentPanel("Transfer Money");

        JPanel form = new JPanel(new GridBagLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(0, 123, 255));
                g.fillRect(0, 0, getWidth(), 4);
            }
        };
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 235)),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)));
        form.setPreferredSize(new Dimension(460, 480));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(8, 0, 8, 0);

        JLabel cardTitle = new JLabel("Transfer Details");
        cardTitle.setFont(new Font("Arial", Font.BOLD, 17));
        cardTitle.setForeground(new Color(13, 27, 62));
        JLabel cardSub = new JLabel("Fill in the recipient's details to send money instantly");
        cardSub.setFont(new Font("Arial", Font.PLAIN, 12));
        cardSub.setForeground(new Color(120, 130, 150));
        JLabel fromLabel = new JLabel("From Account: " + account.getAccountNo() + " (₹" + String.format("%,.2f", account.getBalance()) + ")");
        fromLabel.setFont(new Font("Arial", Font.BOLD, 13));
        fromLabel.setForeground(new Color(40, 60, 100));

        JTextField toField   = createFormField();
        JTextField amtField  = createFormField();
        JTextField descField = createFormField();
        JButton transferBtn  = createActionButton("Transfer Now", new Color(0, 123, 255));

        gbc.insets = new Insets(0, 0, 4, 0);  form.add(cardTitle, gbc);
        gbc.insets = new Insets(0, 0, 20, 0); form.add(cardSub, gbc);
        gbc.insets = new Insets(0, 0, 16, 0); form.add(fromLabel, gbc);
        gbc.insets = new Insets(8, 0, 8, 0);
        addFormRow(form, gbc, new JLabel("Beneficiary Account No:"));
        addFormRow(form, gbc, toField);
        addFormRow(form, gbc, new JLabel("Amount (₹):"));
        addFormRow(form, gbc, amtField);
        addFormRow(form, gbc, new JLabel("Description:"));
        addFormRow(form, gbc, descField);
        gbc.insets = new Insets(20, 0, 0, 0);
        addFormRow(form, gbc, transferBtn);

        transferBtn.addActionListener(e -> {
            try {
                String toAcc = toField.getText().trim();
                double amount = Double.parseDouble(amtField.getText().trim());
                if (toAcc.isEmpty()) { JOptionPane.showMessageDialog(this, "Enter beneficiary account!", "Error", JOptionPane.ERROR_MESSAGE); return; }
                if (amount <= 0)     { JOptionPane.showMessageDialog(this, "Enter valid amount!", "Error", JOptionPane.ERROR_MESSAGE); return; }
                int confirm = JOptionPane.showConfirmDialog(this, "Transfer ₹" + amount + " to " + toAcc + "?", "Confirm Transfer", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean success = accountDAO.transferFunds(account.getAccountNo(), toAcc, amount);
                    if (success) {
                        account = accountDAO.getAccountByCustomerId(customer.getCustomerId());
                        JOptionPane.showMessageDialog(this, "Transfer Successful! New Balance: ₹" + String.format("%,.2f", account.getBalance()), "Success", JOptionPane.INFORMATION_MESSAGE);
                        showTransfer();
                    } else {
                        JOptionPane.showMessageDialog(this, "Transfer Failed! Check balance or beneficiary account.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Enter valid amount!", "Error", JOptionPane.ERROR_MESSAGE); }
        });

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(new Color(240, 243, 250));
        GridBagConstraints wgbc = new GridBagConstraints();
        wgbc.anchor = GridBagConstraints.NORTHWEST;
        wrapper.add(form, wgbc);
        panel.add(wrapper, BorderLayout.CENTER);
        contentPanel.add(panel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // ── TRANSACTION HISTORY ───────────────────────────────────────────
    private void showTransactionHistory() {
        contentPanel.removeAll();
        JPanel panel = createContentPanel("Transaction History");
        List<Transaction> txList = accountDAO.getTransactionHistory(account.getAccountNo());
        panel.add(createTransactionTable(txList, txList.size()), BorderLayout.CENTER);
        contentPanel.add(panel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // ── DEPOSIT / WITHDRAW ────────────────────────────────────────────
    private void showDepositWithdraw() {
        contentPanel.removeAll();

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 243, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(28, 28, 28, 28));

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(240, 243, 250));
        topBar.setBorder(BorderFactory.createEmptyBorder(0, 0, 22, 0));
        JLabel heading = new JLabel("Deposit / Withdraw");
        heading.setFont(new Font("Arial", Font.BOLD, 26));
        heading.setForeground(new Color(13, 27, 62));
        JLabel sub = new JLabel("Add funds to or take funds from your account");
        sub.setFont(new Font("Arial", Font.PLAIN, 13));
        sub.setForeground(new Color(120, 130, 150));
        topBar.add(heading, BorderLayout.WEST);
        topBar.add(sub, BorderLayout.EAST);
        panel.add(topBar, BorderLayout.NORTH);

        account = accountDAO.getAccountByCustomerId(customer.getCustomerId());

        // ── Balance banner card ───────────────────────────────────────
        JPanel balanceCard = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, new Color(0, 90, 200), getWidth(), getHeight(), new Color(0, 40, 120)));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
            }
        };
        balanceCard.setOpaque(false);
        balanceCard.setBorder(BorderFactory.createEmptyBorder(26, 36, 26, 36));
        balanceCard.setPreferredSize(new Dimension(620, 120));

        JPanel balLeft = new JPanel();
        balLeft.setLayout(new BoxLayout(balLeft, BoxLayout.Y_AXIS));
        balLeft.setOpaque(false);
        JLabel balCaption = new JLabel("AVAILABLE BALANCE");
        balCaption.setFont(new Font("Arial", Font.BOLD, 12));
        balCaption.setForeground(new Color(180, 210, 255));
        JLabel balLbl = new JLabel("₹ " + String.format("%,.2f", account.getBalance()));
        balLbl.setFont(new Font("Arial", Font.BOLD, 30));
        balLbl.setForeground(Color.WHITE);
        balLbl.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        balLeft.add(balCaption);
        balLeft.add(balLbl);
        balanceCard.add(balLeft, BorderLayout.WEST);

        JPanel balRight = new JPanel(new GridBagLayout());
        balRight.setOpaque(false);
        JLabel acctTag = new JLabel("Account: " + account.getAccountNo());
        acctTag.setFont(new Font("Arial", Font.BOLD, 13));
        acctTag.setForeground(new Color(180, 210, 255));
        balRight.add(acctTag);
        balanceCard.add(balRight, BorderLayout.EAST);

        // ── Action form card ───────────────────────────────────────────
        JPanel form = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(0, 123, 255));
                g.fillRect(0, 0, getWidth(), 4);
            }
        };
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 235)),
                BorderFactory.createEmptyBorder(28, 36, 32, 36)));

        JLabel formTitle = new JLabel("Move Money");
        formTitle.setFont(new Font("Arial", Font.BOLD, 17));
        formTitle.setForeground(new Color(13, 27, 62));
        formTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel formSub = new JLabel("Enter an amount, then choose Deposit or Withdraw");
        formSub.setFont(new Font("Arial", Font.PLAIN, 12));
        formSub.setForeground(new Color(120, 130, 150));
        formSub.setAlignmentX(Component.LEFT_ALIGNMENT);
        formSub.setBorder(BorderFactory.createEmptyBorder(0, 0, 22, 0));

        JTextField amtField = createFormField();
        JPanel amtWrap = buildLabeledField("Amount (₹)", amtField);
        amtWrap.setAlignmentX(Component.LEFT_ALIGNMENT);
        amtWrap.setMaximumSize(new Dimension(548, 64));

        JButton depositBtn  = createActionButton("\u2193  Deposit",  new Color(40, 167, 69));
        JButton withdrawBtn = createActionButton("\u2191  Withdraw", new Color(220, 53, 69));
        depositBtn.setPreferredSize(new Dimension(0, 46));
        withdrawBtn.setPreferredSize(new Dimension(0, 46));

        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 16, 0));
        btnPanel.setOpaque(false);
        btnPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnPanel.setMaximumSize(new Dimension(548, 80));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(26, 0, 0, 0));
        btnPanel.add(depositBtn); btnPanel.add(withdrawBtn);

        form.add(formTitle);
        form.add(formSub);
        form.add(amtWrap);
        form.add(btnPanel);

        depositBtn.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amtField.getText().trim());
                if (accountDAO.deposit(account.getAccountNo(), amount)) {
                    account = accountDAO.getAccountByCustomerId(customer.getCustomerId());
                    balLbl.setText("₹ " + String.format("%,.2f", account.getBalance()));
                    JOptionPane.showMessageDialog(this, "Deposit successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    amtField.setText("");
                }
            } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Enter valid amount!", "Error", JOptionPane.ERROR_MESSAGE); }
        });

        withdrawBtn.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amtField.getText().trim());
                boolean ok = accountDAO.withdraw(account.getAccountNo(), amount);
                if (ok) {
                    account = accountDAO.getAccountByCustomerId(customer.getCustomerId());
                    balLbl.setText("₹ " + String.format("%,.2f", account.getBalance()));
                    JOptionPane.showMessageDialog(this, "Withdrawal successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    amtField.setText("");
                } else { JOptionPane.showMessageDialog(this, "Insufficient balance!", "Error", JOptionPane.ERROR_MESSAGE); }
            } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Enter valid amount!", "Error", JOptionPane.ERROR_MESSAGE); }
        });

        JPanel centerSection = new JPanel(new BorderLayout(0, 22));
        centerSection.setBackground(new Color(240, 243, 250));
        centerSection.add(balanceCard, BorderLayout.NORTH);
        centerSection.add(form, BorderLayout.CENTER);

        JPanel centeredWrapper = new JPanel(new GridBagLayout());
        centeredWrapper.setBackground(new Color(240, 243, 250));
        GridBagConstraints cgbc = new GridBagConstraints();
        cgbc.anchor = GridBagConstraints.NORTH;
        cgbc.weighty = 1;
        cgbc.fill = GridBagConstraints.NONE;
        centeredWrapper.add(centerSection, cgbc);

        panel.add(centeredWrapper, BorderLayout.CENTER);
        contentPanel.add(panel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // ── PROFILE ───────────────────────────────────────────────────────
    private void showProfile() {
        contentPanel.removeAll();

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 243, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(28, 28, 28, 28));

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(240, 243, 250));
        topBar.setBorder(BorderFactory.createEmptyBorder(0, 0, 22, 0));
        JLabel heading = new JLabel("My Profile");
        heading.setFont(new Font("Arial", Font.BOLD, 26));
        heading.setForeground(new Color(13, 27, 62));
        JLabel sub = new JLabel("Manage your personal information");
        sub.setFont(new Font("Arial", Font.PLAIN, 13));
        sub.setForeground(new Color(120, 130, 150));
        topBar.add(heading, BorderLayout.WEST);
        topBar.add(sub, BorderLayout.EAST);
        panel.add(topBar, BorderLayout.NORTH);

        // ── Profile header card (avatar + name + status) ─────────────
        JPanel profileCard = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, new Color(0, 90, 200), getWidth(), getHeight(), new Color(0, 40, 120)));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
            }
        };
        profileCard.setOpaque(false);
        profileCard.setBorder(BorderFactory.createEmptyBorder(28, 36, 28, 36));
        profileCard.setPreferredSize(new Dimension(0, 120));

        JPanel headerLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 18, 0));
        headerLeft.setOpaque(false);

        JLabel bigAvatar = new JLabel(customer.getName().substring(0, 2).toUpperCase(), SwingConstants.CENTER);
        bigAvatar.setFont(new Font("Arial", Font.BOLD, 22));
        bigAvatar.setForeground(Color.WHITE);
        bigAvatar.setOpaque(true);
        bigAvatar.setBackground(new Color(80, 60, 160));
        bigAvatar.setPreferredSize(new Dimension(64, 64));
        bigAvatar.setHorizontalAlignment(SwingConstants.CENTER);
        bigAvatar.setVerticalAlignment(SwingConstants.CENTER);

        JPanel headerText = new JPanel();
        headerText.setLayout(new BoxLayout(headerText, BoxLayout.Y_AXIS));
        headerText.setOpaque(false);

        JLabel nameLbl = new JLabel(customer.getName());
        nameLbl.setFont(new Font("Arial", Font.BOLD, 20));
        nameLbl.setForeground(Color.WHITE);

        JLabel statusLbl = new JLabel("● " + customer.getStatus() + "   ·   Customer");
        statusLbl.setFont(new Font("Arial", Font.PLAIN, 12));
        statusLbl.setForeground(new Color(180, 210, 255));
        statusLbl.setBorder(BorderFactory.createEmptyBorder(6, 0, 0, 0));

        headerText.add(nameLbl);
        headerText.add(statusLbl);

        headerLeft.add(bigAvatar);
        headerLeft.add(headerText);
        profileCard.add(headerLeft, BorderLayout.WEST);

        JPanel headerRight = new JPanel(new GridBagLayout());
        headerRight.setOpaque(false);
        JLabel idTag = new JLabel("Account: " + (account != null ? account.getAccountNo() : "N/A"));
        idTag.setFont(new Font("Arial", Font.BOLD, 13));
        idTag.setForeground(new Color(180, 210, 255));
        headerRight.add(idTag);
        profileCard.add(headerRight, BorderLayout.EAST);

        // ── Personal information card ─────────────────────────────────
        JPanel infoCard = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(0, 123, 255));
                g.fillRect(0, 0, getWidth(), 4);
            }
        };
        infoCard.setLayout(new BoxLayout(infoCard, BoxLayout.Y_AXIS));
        infoCard.setBackground(Color.WHITE);
        infoCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 235)),
                BorderFactory.createEmptyBorder(28, 36, 32, 36)));

        JLabel sectionTitle = new JLabel("Personal Information");
        sectionTitle.setFont(new Font("Arial", Font.BOLD, 17));
        sectionTitle.setForeground(new Color(13, 27, 62));
        sectionTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sectionSub = new JLabel("Keep your contact details up to date");
        sectionSub.setFont(new Font("Arial", Font.PLAIN, 12));
        sectionSub.setForeground(new Color(120, 130, 150));
        sectionSub.setAlignmentX(Component.LEFT_ALIGNMENT);
        sectionSub.setBorder(BorderFactory.createEmptyBorder(0, 0, 22, 0));

        JTextField nameField    = createFormFieldWithValue(customer.getName());
        JTextField phoneField   = createFormFieldWithValue(customer.getPhone());
        JTextField emailField   = createFormFieldWithValue(customer.getEmail());
        emailField.setEditable(false); emailField.setBackground(new Color(245, 247, 252));
        JTextField addressField = createFormFieldWithValue(customer.getAddress());

        JPanel row1 = new JPanel(new GridLayout(1, 2, 24, 0));
        row1.setOpaque(false);
        row1.setAlignmentX(Component.LEFT_ALIGNMENT);
        row1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 64));
        row1.add(buildLabeledField("Full Name", nameField));
        row1.add(buildLabeledField("Phone Number", phoneField));

        JPanel row2 = new JPanel(new GridLayout(1, 2, 24, 0));
        row2.setOpaque(false);
        row2.setAlignmentX(Component.LEFT_ALIGNMENT);
        row2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 64));
        row2.setBorder(BorderFactory.createEmptyBorder(18, 0, 0, 0));
        row2.add(buildLabeledField("Email (read-only)", emailField));
        row2.add(buildLabeledField("Address", addressField));

        JButton saveBtn = createActionButton("Save Changes", new Color(0, 123, 255));
        saveBtn.setPreferredSize(new Dimension(160, 42));
        JPanel saveRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        saveRow.setOpaque(false);
        saveRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        saveRow.setBorder(BorderFactory.createEmptyBorder(28, 0, 0, 0));
        saveRow.add(saveBtn);

        infoCard.add(sectionTitle);
        infoCard.add(sectionSub);
        infoCard.add(row1);
        infoCard.add(row2);
        infoCard.add(saveRow);

        saveBtn.addActionListener(e -> {
            customer.setName(nameField.getText().trim());
            customer.setPhone(phoneField.getText().trim());
            customer.setAddress(addressField.getText().trim());
            if (customerDAO.updateProfile(customer))
                JOptionPane.showMessageDialog(this, "Profile updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        JPanel centerSection = new JPanel(new BorderLayout(0, 20));
        centerSection.setBackground(new Color(240, 243, 250));
        centerSection.add(profileCard, BorderLayout.NORTH);
        centerSection.add(infoCard, BorderLayout.CENTER);

        panel.add(centerSection, BorderLayout.CENTER);
        contentPanel.add(panel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JPanel buildLabeledField(String label, JTextField field) {
        JPanel wrap = new JPanel();
        wrap.setLayout(new BoxLayout(wrap, BoxLayout.Y_AXIS));
        wrap.setOpaque(false);
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        lbl.setForeground(new Color(100, 110, 130));
        lbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        wrap.add(lbl);
        wrap.add(field);
        return wrap;
    }

    // ── CHANGE PASSWORD ───────────────────────────────────────────────
    private void showChangePassword() {
        contentPanel.removeAll();

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 243, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(28, 28, 28, 28));

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(240, 243, 250));
        topBar.setBorder(BorderFactory.createEmptyBorder(0, 0, 22, 0));
        JLabel heading = new JLabel("Change Password");
        heading.setFont(new Font("Arial", Font.BOLD, 26));
        heading.setForeground(new Color(13, 27, 62));
        JLabel sub = new JLabel("Keep your account secure");
        sub.setFont(new Font("Arial", Font.PLAIN, 13));
        sub.setForeground(new Color(120, 130, 150));
        topBar.add(heading, BorderLayout.WEST);
        topBar.add(sub, BorderLayout.EAST);
        panel.add(topBar, BorderLayout.NORTH);

        // ── Security banner card ─────────────────────────────────────
        JPanel securityCard = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, new Color(0, 90, 200), getWidth(), getHeight(), new Color(0, 40, 120)));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
            }
        };
        securityCard.setOpaque(false);
        securityCard.setBorder(BorderFactory.createEmptyBorder(24, 36, 24, 36));
        securityCard.setPreferredSize(new Dimension(0, 90));

        JPanel secLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 18, 0));
        secLeft.setOpaque(false);

        JLabel lockBadge = new JLabel("\u2022", SwingConstants.CENTER);
        lockBadge.setFont(new Font("Arial", Font.BOLD, 22));
        lockBadge.setForeground(Color.WHITE);
        lockBadge.setOpaque(true);
        lockBadge.setBackground(new Color(255, 152, 0));
        lockBadge.setPreferredSize(new Dimension(48, 48));
        lockBadge.setHorizontalAlignment(SwingConstants.CENTER);
        lockBadge.setVerticalAlignment(SwingConstants.CENTER);

        JPanel secText = new JPanel();
        secText.setLayout(new BoxLayout(secText, BoxLayout.Y_AXIS));
        secText.setOpaque(false);

        JLabel secTitle = new JLabel("Account Security");
        secTitle.setFont(new Font("Arial", Font.BOLD, 16));
        secTitle.setForeground(Color.WHITE);

        JLabel secSub = new JLabel("Use a strong password you don't use anywhere else");
        secSub.setFont(new Font("Arial", Font.PLAIN, 12));
        secSub.setForeground(new Color(180, 210, 255));
        secSub.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));

        secText.add(secTitle);
        secText.add(secSub);

        secLeft.add(lockBadge);
        secLeft.add(secText);
        securityCard.add(secLeft, BorderLayout.WEST);

        // ── Password form card ────────────────────────────────────────
        JPanel form = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(255, 152, 0));
                g.fillRect(0, 0, getWidth(), 4);
            }
        };
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 235)),
                BorderFactory.createEmptyBorder(28, 36, 32, 36)));

        JLabel formTitle = new JLabel("Update Your Password");
        formTitle.setFont(new Font("Arial", Font.BOLD, 17));
        formTitle.setForeground(new Color(13, 27, 62));
        formTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel formSub = new JLabel("Enter your current password followed by a new one");
        formSub.setFont(new Font("Arial", Font.PLAIN, 12));
        formSub.setForeground(new Color(120, 130, 150));
        formSub.setAlignmentX(Component.LEFT_ALIGNMENT);
        formSub.setBorder(BorderFactory.createEmptyBorder(0, 0, 24, 0));

        JPasswordField oldPwdField = new JPasswordField();
        JPasswordField newPwdField = new JPasswordField();
        JPasswordField cnfPwdField = new JPasswordField();
        styleFormField(oldPwdField); styleFormField(newPwdField); styleFormField(cnfPwdField);

        JPanel fieldsWrap = new JPanel(new GridLayout(1, 1));
        fieldsWrap.setOpaque(false);
        fieldsWrap.setAlignmentX(Component.LEFT_ALIGNMENT);
        fieldsWrap.setMaximumSize(new Dimension(420, Integer.MAX_VALUE));
        fieldsWrap.setLayout(new BoxLayout(fieldsWrap, BoxLayout.Y_AXIS));
        fieldsWrap.add(buildLabeledField("Current Password", oldPwdField));
        fieldsWrap.add(Box.createVerticalStrut(16));
        fieldsWrap.add(buildLabeledField("New Password", newPwdField));
        fieldsWrap.add(Box.createVerticalStrut(16));
        fieldsWrap.add(buildLabeledField("Confirm New Password", cnfPwdField));

        JButton changeBtn = createActionButton("Change Password", new Color(0, 123, 255));
        changeBtn.setPreferredSize(new Dimension(190, 42));
        JPanel changeRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        changeRow.setOpaque(false);
        changeRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        changeRow.setBorder(BorderFactory.createEmptyBorder(26, 0, 0, 0));
        changeRow.add(changeBtn);

        form.add(formTitle);
        form.add(formSub);
        form.add(fieldsWrap);
        form.add(changeRow);

        changeBtn.addActionListener(e -> {
            String oldP = new String(oldPwdField.getPassword());
            String newP = new String(newPwdField.getPassword());
            String cnf  = new String(cnfPwdField.getPassword());
            if (!newP.equals(cnf)) { JOptionPane.showMessageDialog(this, "New passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE); return; }
            boolean ok = customerDAO.changePassword(customer.getCustomerId(), oldP, newP);
            if (ok) JOptionPane.showMessageDialog(this, "Password changed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            else    JOptionPane.showMessageDialog(this, "Incorrect current password!", "Error", JOptionPane.ERROR_MESSAGE);
        });

        JPanel centerSection = new JPanel(new BorderLayout(0, 20));
        centerSection.setBackground(new Color(240, 243, 250));
        centerSection.add(securityCard, BorderLayout.NORTH);
        centerSection.add(form, BorderLayout.CENTER);

        panel.add(centerSection, BorderLayout.CENTER);
        contentPanel.add(panel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // ── HELPERS ───────────────────────────────────────────────────────
    private JPanel createContentPanel(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 243, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        JLabel heading = new JLabel(title);
        heading.setFont(new Font("Arial", Font.BOLD, 22));
        heading.setForeground(new Color(13, 27, 62));
        heading.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));
        panel.add(heading, BorderLayout.NORTH);
        return panel;
    }

    private JPanel createStatCard(String label, String value, Color color, String icon) {
        JPanel card = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(color); g.fillRect(0, 0, getWidth(), 5);
            }
        };
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 235)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        JPanel topRow = new JPanel(new BorderLayout());
        topRow.setBackground(Color.WHITE);
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        lbl.setForeground(new Color(100, 110, 130));
        topRow.add(lbl, BorderLayout.CENTER);

        JLabel val = new JLabel(value);
        val.setFont(new Font("Arial", Font.BOLD, 24));
        val.setForeground(color);
        val.setBorder(BorderFactory.createEmptyBorder(14, 0, 0, 0));

        JPanel bar = new JPanel();
        bar.setBackground(color);
        bar.setPreferredSize(new Dimension(0, 4));

        card.add(topRow, BorderLayout.NORTH);
        card.add(val, BorderLayout.CENTER);
        card.add(bar, BorderLayout.SOUTH);
        return card;
    }

    private JPanel createTransactionTable(List<Transaction> txList, int maxRows) {
        String[] cols = {"ID", "ACCOUNT", "TYPE", "AMOUNT", "DATE", "DESCRIPTION"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        int count = 0;
        for (Transaction t : txList) {
            if (count++ >= maxRows) break;
            model.addRow(new Object[]{ t.getTransactionId(), t.getAccountNo(), t.getType(),
                    String.format("%.2f", t.getAmount()), t.getDate(), t.getDescription() });
        }

        JTable table = new JTable(model) {
            @Override public Component prepareRenderer(javax.swing.table.TableCellRenderer r, int row, int col) {
                Component c = super.prepareRenderer(r, row, col);
                if (!isRowSelected(row)) c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 248, 255));
                if (col == 2) {
                    String val = getValueAt(row, col).toString();
                    c.setForeground(val.equals("CREDIT") ? new Color(40, 167, 69) : new Color(220, 53, 69));
                    ((JComponent) c).setFont(new Font("Arial", Font.BOLD, 12));
                } else {
                    c.setForeground(new Color(30, 40, 70));
                    ((JComponent) c).setFont(new Font("Arial", Font.PLAIN, 12));
                }
                return c;
            }
        };

        table.setRowHeight(36);
        table.setBackground(Color.WHITE);
        table.setForeground(new Color(30, 40, 70));
        table.setGridColor(new Color(230, 235, 245));
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 1));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.getTableHeader().setBackground(Color.WHITE);
        table.getTableHeader().setForeground(new Color(13, 27, 62));
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(13, 27, 62)));
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 225, 235)));
        scroll.getViewport().setBackground(Color.WHITE);

        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(240, 243, 250));
        p.add(scroll);
        return p;
    }

    private JTextField createFormField() {
        JTextField f = new JTextField(); styleFormField(f); return f;
    }

    private JTextField createFormFieldWithValue(String val) {
        JTextField f = createFormField(); f.setText(val); return f;
    }

    private void styleFormField(JTextField f) {
        f.setPreferredSize(new Dimension(0, 36));
        f.setFont(new Font("Arial", Font.PLAIN, 13));
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 230)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
    }

    private JButton createActionButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(0, 40));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setBorder(BorderFactory.createEmptyBorder());
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void addFormRow(JPanel p, GridBagConstraints g, JComponent c) {
        if (c instanceof JLabel) {
            ((JLabel) c).setFont(new Font("Arial", Font.BOLD, 13));
            ((JLabel) c).setForeground(new Color(40, 60, 100));
        }
        p.add(c, g);
    }
}
