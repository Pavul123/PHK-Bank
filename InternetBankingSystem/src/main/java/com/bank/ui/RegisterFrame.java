package com.bank.ui;

import com.bank.dao.CustomerDAO;
import com.bank.model.Customer;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Random;

public class RegisterFrame extends JFrame {

    // ----- Brand palette (matches the Secure Bank login screen) -----
    private static final Color NAVY_DARK   = new Color(13, 27, 62);
    private static final Color NAVY_MID    = new Color(20, 38, 80);
    private static final Color ACCENT_BLUE = new Color(0, 123, 255);
    private static final Color TEXT_MUTED  = new Color(110, 120, 140);
    private static final Color BORDER_GRAY = new Color(210, 216, 228);
    private static final Color PAGE_BG     = new Color(244, 246, 251);

    private JTextField nameField, phoneField, emailField, addressField;
    private JPasswordField passwordField, confirmPwdField;
    private JTextField secQField, answerField;
    private JComboBox<String> accountTypeBox;

    public RegisterFrame() {
        setTitle("Register — Secure Bank");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setResizable(false);
        initUI();
    }

    private void initUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(PAGE_BG);

        root.add(buildLeftPanel(), BorderLayout.WEST);
        root.add(buildRightPanel(), BorderLayout.CENTER);

        add(root);
    }

    // ===================== LEFT BRAND PANEL =====================

    private JPanel buildLeftPanel() {
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBackground(NAVY_DARK);
        left.setPreferredSize(new Dimension(420, 0));
        left.setBorder(BorderFactory.createEmptyBorder(0, 60, 0, 60));

        left.add(Box.createVerticalGlue());

        // Logo circle
        JPanel logoCircle = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(NAVY_MID);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        logoCircle.setOpaque(false);
        logoCircle.setPreferredSize(new Dimension(110, 110));
        logoCircle.setMaximumSize(new Dimension(110, 110));
        logoCircle.setLayout(new GridBagLayout());
        JLabel dollar = new JLabel("$");
        dollar.setFont(new Font("Arial", Font.BOLD, 42));
        dollar.setForeground(new Color(120, 170, 255));
        logoCircle.add(dollar);

        JPanel logoWrap = new JPanel();
        logoWrap.setOpaque(false);
        logoWrap.setLayout(new BoxLayout(logoWrap, BoxLayout.X_AXIS));
        logoWrap.add(Box.createHorizontalGlue());
        logoWrap.add(logoCircle);
        logoWrap.add(Box.createHorizontalGlue());
        logoWrap.setAlignmentX(Component.CENTER_ALIGNMENT);
        left.add(logoWrap);

        left.add(Box.createRigidArea(new Dimension(0, 28)));

        JLabel bank = new JLabel("PHK BANK");
        bank.setFont(new Font("Arial", Font.BOLD, 30));
        bank.setForeground(Color.WHITE);
        bank.setAlignmentX(Component.CENTER_ALIGNMENT);
        left.add(bank);

        left.add(Box.createRigidArea(new Dimension(0, 6)));

        JLabel subtitle = new JLabel("Create Your Free Account");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setForeground(new Color(140, 175, 255));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        left.add(subtitle);

        left.add(Box.createRigidArea(new Dimension(0, 22)));

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(50, 65, 105));
        sep.setMaximumSize(new Dimension(300, 1));
        left.add(sep);

        left.add(Box.createRigidArea(new Dimension(0, 22)));

        left.add(bullet("Instant Account Creation"));
        left.add(Box.createRigidArea(new Dimension(0, 10)));
        left.add(bullet("Savings & Current Accounts"));
        left.add(Box.createRigidArea(new Dimension(0, 10)));
        left.add(bullet("Bank-Grade Data Security"));
        left.add(Box.createRigidArea(new Dimension(0, 10)));
        left.add(bullet("24/7 Online Banking Access"));

        left.add(Box.createVerticalGlue());

        return left;
    }

    private JLabel bullet(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(new Color(210, 220, 240));
        lbl.setFont(new Font("Arial", Font.PLAIN, 13));
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        return lbl;
    }

    // ===================== RIGHT FORM PANEL =====================

    private JPanel buildRightPanel() {
        JPanel right = new JPanel(new GridBagLayout());
        right.setBackground(PAGE_BG);
        right.setBackground(new Color(25, 44, 90));

        JPanel card = new RoundedCard(18);
        card.setLayout(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(40, 48, 32, 48));

        GridBagConstraints cgbc = new GridBagConstraints();
        cgbc.gridx = 0;
        cgbc.fill = GridBagConstraints.HORIZONTAL;
        cgbc.insets = new Insets(0, 0, 0, 0);
        cgbc.gridy = 0;

        JLabel heading = new JLabel("Create Account");
        heading.setFont(new Font("Arial", Font.BOLD, 26));
        heading.setForeground(NAVY_DARK);
        card.add(heading, cgbc);

        cgbc.gridy++;
        cgbc.insets = new Insets(4, 0, 22, 0);
        JLabel sub = new JLabel("Register for a new Secure Bank account");
        sub.setFont(new Font("Arial", Font.PLAIN, 13));
        sub.setForeground(TEXT_MUTED);
        card.add(sub, cgbc);

        nameField       = new JTextField();
        phoneField      = new JTextField();
        emailField      = new JTextField();
        addressField    = new JTextField();
        passwordField   = new JPasswordField();
        confirmPwdField = new JPasswordField();
        secQField       = new JTextField();
        answerField     = new JTextField();
        accountTypeBox  = new JComboBox<>(new String[]{"SAVINGS", "CURRENT"});

        JTextField[] allFields = {nameField, phoneField, emailField, addressField,
                passwordField, confirmPwdField, secQField, answerField};
        for (JTextField f : allFields) styleField(f);
        styleCombo(accountTypeBox);

        cgbc.insets = new Insets(0, 0, 0, 0);
        cgbc.gridy++;
        addField(card, cgbc, "Full Name", nameField);
        addField(card, cgbc, "Phone Number", phoneField);
        addField(card, cgbc, "Email Address", emailField);
        addField(card, cgbc, "Address", addressField);
        addField(card, cgbc, "Password", passwordField);
        addField(card, cgbc, "Confirm Password", confirmPwdField);
        addField(card, cgbc, "Security Question", secQField);
        addField(card, cgbc, "Answer", answerField);
        addField(card, cgbc, "Account Type", accountTypeBox);

        JButton registerBtn = new SolidButton("CREATE ACCOUNT", NAVY_DARK, Color.WHITE);
        registerBtn.setPreferredSize(new Dimension(0, 44));
        registerBtn.setFont(new Font("Arial", Font.BOLD, 14));
        registerBtn.setFocusPainted(false);
        registerBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        cgbc.gridy++;
        cgbc.insets = new Insets(18, 0, 10, 0);
        card.add(registerBtn, cgbc);

        JButton backBtn = new SolidButton("Back to Customer Login", Color.WHITE, ACCENT_BLUE);
        backBtn.setFont(new Font("Arial", Font.BOLD, 16));
        backBtn.setFocusPainted(false);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.setHorizontalAlignment(SwingConstants.CENTER);

        cgbc.gridy++;
        cgbc.insets = new Insets(0, 0, 0, 0);
        card.add(backBtn, cgbc);

        // Center the card, give it a sensible fixed width
        card.setPreferredSize(new Dimension(460, card.getPreferredSize().height));

        JScrollPane scroll = new JScrollPane(card);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        GridBagConstraints outer = new GridBagConstraints();
        outer.gridx = 0;
        outer.gridy = 0;
        outer.weightx = 1;
        outer.weighty = 1;
        right.add(scroll, outer);

        registerBtn.addActionListener(e -> handleRegister());
        backBtn.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        return right;
    }

    private void addField(JPanel p, GridBagConstraints g, String label, JComponent field) {
        g.insets = new Insets(10, 0, 2, 0);
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        lbl.setForeground(new Color(70, 80, 100));
        p.add(lbl, g);

        g.gridy++;
        g.insets = new Insets(0, 0, 0, 0);
        p.add(field, g);
        g.gridy++;
    }

    private void styleField(JTextField f) {
        f.setPreferredSize(new Dimension(0, 38));
        f.setFont(new Font("Arial", Font.PLAIN, 13));
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_GRAY, 1, true),
                BorderFactory.createEmptyBorder(6, 12, 6, 12)));
    }

    private void styleCombo(JComboBox<String> box) {
        box.setPreferredSize(new Dimension(0, 38));
        box.setFont(new Font("Arial", Font.PLAIN, 13));
        box.setBackground(Color.WHITE);
        box.setBorder(BorderFactory.createLineBorder(BORDER_GRAY, 1, true));
    }

    // ===================== BACKEND (UNCHANGED LOGIC) =====================

    private void handleRegister() {
        String name     = nameField.getText().trim();
        String phone    = phoneField.getText().trim();
        String email    = emailField.getText().trim();
        String address  = addressField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirm  = new String(confirmPwdField.getPassword());
        String secQ     = secQField.getText().trim();
        String answer   = answerField.getText().trim();
        String accType  = (String) accountTypeBox.getSelectedItem();

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!password.equals(confirm)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Generate account number
        String accountNo = "ACC" + String.format("%06d", new Random().nextInt(999999));

        Customer c = new Customer();
        c.setName(name); c.setPhone(phone); c.setEmail(email);
        c.setAddress(address); c.setPassword(password);
        c.setSecurityQuestion(secQ); c.setAnswer(answer);

        CustomerDAO dao = new CustomerDAO();
        boolean success = dao.register(c, accountNo, accType);

        if (success) {
            JOptionPane.showMessageDialog(this,
                    "Account created successfully!\nYour Account Number: " + accountNo,
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            new LoginFrame().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Registration failed! Email may already be registered.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ===================== ROUNDED CARD HELPER =====================

    /**
     * A JButton that always paints its own fill color and text color,
     * regardless of the OS Look-and-Feel. Plain JButton.setBackground/
     * setForeground is ignored by some native LAFs (notably on Windows/macOS),
     * which is why text can appear invisible (e.g. white text on a white button,
     * or blue text that silently resets to black/gray).
     */
    private static class SolidButton extends JButton {
        private final Color fill;
        private final Color textColor;

        SolidButton(String text, Color fill, Color textColor) {
            super(text);
            this.fill = fill;
            this.textColor = textColor;
            setContentAreaFilled(false);
            setBorderPainted(false);
            setOpaque(false);
            setForeground(textColor);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getModel().isPressed() ? darken(fill) : fill);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
            g2.dispose();

            // Force the text color every paint, in case the LAF resets it
            setForeground(textColor);
            super.paintComponent(g);
        }

        private Color darken(Color c) {
            // Avoid pure white darkening into invisible gray-on-gray; keep a subtle press effect
            int r = Math.max(0, c.getRed() - 18);
            int g = Math.max(0, c.getGreen() - 18);
            int b = Math.max(0, c.getBlue() - 18);
            return new Color(r, g, b);
        }
    }

    /** A JPanel with rounded corners and a soft drop shadow, used for the white form card. */
    private static class RoundedCard extends JPanel {
        private final int radius;

        RoundedCard(int radius) {
            this.radius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));
            g2.dispose();
            super.paintComponent(g);
        }
    }
}