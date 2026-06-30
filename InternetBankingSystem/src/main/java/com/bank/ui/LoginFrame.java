package com.bank.ui;

import com.bank.dao.CustomerDAO;
import com.bank.model.Customer;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;

public class LoginFrame extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginBtn, registerBtn;

    private static final Color ACCENT_BLUE  = new Color(0, 123, 255);
    private static final Color MUTED_TEXT   = new Color(150, 160, 185);
    private static final Color CARD_BG      = new Color(25, 44, 90);
    private static final Color FIELD_BORDER = new Color(210, 214, 224);
    private static final Color FIELD_TEXT   = new Color(33, 37, 41);

    public LoginFrame() {
        setTitle("Internet Banking System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setResizable(true);
        initUI();
    }

    private void initUI() {
        JPanel root = new JPanel(new GridLayout(1, 2));
        root.add(buildSidebar());
        root.add(buildFormStage());
        add(root);
    }

    // ---------------------------------------------------------------
    // LEFT PANEL
    // ---------------------------------------------------------------
    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth(), h = getHeight();
                GradientPaint base = new GradientPaint(0, 0, new Color(24, 40, 84), w, h, new Color(10, 20, 48));
                g2.setPaint(base);
                g2.fillRect(0, 0, w, h);
                drawGlow(g2, -180, -180, 520, new Color(70, 100, 175));
                drawGlow(g2, w - 420, h - 420, 600, new Color(50, 80, 150));
                drawGlow(g2, w / 2 - 350, h / 2 - 480, 700, new Color(55, 85, 150));
                g2.dispose();
            }
        };
        sidebar.setOpaque(true);

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        // Bank building logo
        JComponent circleIcon = new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth(), h = getHeight();
                g2.setColor(new Color(40, 62, 115, 220));
                g2.fillOval(6, 6, w - 12, h - 12);
                g2.setColor(new Color(110, 140, 200, 160));
                g2.setStroke(new BasicStroke(1.6f));
                g2.drawOval(2, 2, w - 5, h - 5);
                g2.setColor(ACCENT_BLUE);
                int cx = w / 2, cy = h / 2;
                int[] xPts = {cx - 28, cx, cx + 28};
                int[] yPts = {cy - 6, cy - 22, cy - 6};
                g2.fillPolygon(xPts, yPts, 3);
                g2.fillRect(cx - 30, cy - 6, 60, 6);
                g2.fillRect(cx - 22, cy, 9, 20);
                g2.fillRect(cx - 5,  cy, 9, 20);
                g2.fillRect(cx + 13, cy, 9, 20);
                g2.fillRect(cx - 32, cy + 20, 64, 5);
                g2.fillRect(cx - 28, cy + 25, 56, 4);
                g2.dispose();
            }
        };
        circleIcon.setPreferredSize(new Dimension(100, 100));
        circleIcon.setMaximumSize(new Dimension(100, 100));
        circleIcon.setOpaque(false);
        circleIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel bankName = new JLabel("PHK BANK");
        bankName.setForeground(Color.WHITE);
        bankName.setFont(new Font("Arial", Font.BOLD, 26));
        bankName.setAlignmentX(Component.CENTER_ALIGNMENT);
        bankName.setBorder(BorderFactory.createEmptyBorder(18, 0, 4, 0));

        JLabel tagline = new JLabel("Login to Your Account");
        tagline.setForeground(MUTED_TEXT);
        tagline.setFont(new Font("Arial", Font.PLAIN, 14));
        tagline.setAlignmentX(Component.CENTER_ALIGNMENT);
        tagline.setBorder(BorderFactory.createEmptyBorder(0, 0, 18, 0));

        JSeparator divider = new JSeparator();
        divider.setForeground(new Color(45, 60, 100));
        divider.setBackground(new Color(45, 60, 100));
        divider.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        divider.setAlignmentX(Component.CENTER_ALIGNMENT);
        divider.setBorder(BorderFactory.createEmptyBorder(0, 0, 24, 0));

        content.add(circleIcon);
        content.add(bankName);
        content.add(tagline);
        content.add(divider);
        content.add(featureItem("Secure Online Banking"));
        content.add(featureItem("Instant Fund Transfers"));
        content.add(featureItem("Bank Grade Data Security"));
        content.add(featureItem("24/7 Account Access"));

        JLabel loginIntroTitle = new JLabel("Login");
        loginIntroTitle.setForeground(Color.WHITE);
        loginIntroTitle.setFont(new Font("Arial", Font.BOLD, 22));
        loginIntroTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginIntroTitle.setBorder(BorderFactory.createEmptyBorder(28, 0, 4, 0));

        JLabel loginIntroSubtitle = new JLabel("Please login to continue");
        loginIntroSubtitle.setForeground(MUTED_TEXT);
        loginIntroSubtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        loginIntroSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.add(loginIntroTitle);
        content.add(loginIntroSubtitle);

        sidebar.add(content, new GridBagConstraints());
        return sidebar;
    }

    private void drawGlow(Graphics2D g2, int x, int y, int size, Color color) {
        Point2D center = new Point2D.Float(x + size / 2f, y + size / 2f);
        float[] dist = {0f, 1f};
        Color[] colors = {
                new Color(color.getRed(), color.getGreen(), color.getBlue(), 70),
                new Color(color.getRed(), color.getGreen(), color.getBlue(), 0)
        };
        RadialGradientPaint glow = new RadialGradientPaint(center, size / 2f, dist, colors);
        g2.setPaint(glow);
        g2.fillOval(x, y, size, size);
    }

    private JLabel featureItem(String text) {
        JLabel item = new JLabel(text);
        item.setForeground(new Color(200, 208, 225));
        item.setFont(new Font("Arial", Font.PLAIN, 14));
        item.setAlignmentX(Component.CENTER_ALIGNMENT);
        item.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        return item;
    }

    // ---------------------------------------------------------------
    // RIGHT PANEL
    // ---------------------------------------------------------------
    private JPanel buildFormStage() {
        JPanel stage = new JPanel(new GridBagLayout());
        stage.setBackground(CARD_BG);

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(50, 80, 140), 1, true),
                BorderFactory.createEmptyBorder(36, 40, 32, 40)
        ));
        card.setPreferredSize(new Dimension(440, 540));

        GridBagConstraints fc = new GridBagConstraints();
        fc.fill = GridBagConstraints.HORIZONTAL;
        fc.gridwidth = GridBagConstraints.REMAINDER;
        fc.insets = new Insets(6, 0, 6, 0);

        // Heading
        JLabel formHeading = new JLabel("Login");
        formHeading.setFont(new Font("Arial", Font.BOLD, 22));
        formHeading.setForeground(Color.WHITE);

        // Email
        JPanel emailLabelRow = labelWithRequired("Email Address");
        emailField = createTextField();

        // Password
        JPanel passLabelRow = labelWithRequired("Password");
        passwordField = new JPasswordField();
        styleField(passwordField);

        // LOGIN button — custom painted to bypass L&F override
        loginBtn = makeFilledButton("LOGIN", ACCENT_BLUE, Color.WHITE);

        // "Don't have an account?"
        JLabel orLabel = new JLabel("Don't have an account?");
        orLabel.setForeground(new Color(180, 195, 220));
        orLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        orLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // REGISTER button — custom painted, outlined style
        registerBtn = makeOutlineButton("REGISTER", ACCENT_BLUE, Color.WHITE, CARD_BG);

        // Admin Login link
        JLabel adminLabel = new JLabel("<html><font color='#90B8F8'>Admin Login</font></html>");
        adminLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        adminLabel.setHorizontalAlignment(SwingConstants.CENTER);
        adminLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Layout
        card.add(formHeading, fc);

        fc.insets = new Insets(10, 0, 4, 0);
        card.add(emailLabelRow, fc);
        fc.insets = new Insets(0, 0, 14, 0);
        card.add(emailField, fc);

        fc.insets = new Insets(0, 0, 4, 0);
        card.add(passLabelRow, fc);
        fc.insets = new Insets(0, 0, 22, 0);
        card.add(passwordField, fc);

        fc.insets = new Insets(0, 0, 16, 0);
        card.add(loginBtn, fc);

        fc.insets = new Insets(4, 0, 12, 0);
        card.add(orLabel, fc);

        fc.insets = new Insets(0, 0, 16, 0);
        card.add(registerBtn, fc);

        fc.insets = new Insets(0, 0, 0, 0);
        card.add(adminLabel, fc);

        stage.add(card, new GridBagConstraints());

        // Action listeners
        loginBtn.addActionListener(this::handleLogin);
        registerBtn.addActionListener(e -> {
            new RegisterFrame().setVisible(true);
            dispose();
        });
        adminLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                new AdminLoginFrame().setVisible(true);
                dispose();
            }
        });
        passwordField.addActionListener(this::handleLogin);

        return stage;
    }

    /**
     * Solid filled button — overrides L&F by painting background manually.
     */
    private JButton makeFilledButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Slightly darken on press
                Color fill = getModel().isPressed()
                        ? bg.darker()
                        : getModel().isRollover() ? bg.brighter() : bg;
                g2.setColor(fill);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                g2.dispose();
                // Draw text on top
                super.paintComponent(g);
            }
        };
        btn.setForeground(fg);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(0, 44));
        btn.setOpaque(false);           // let our paintComponent handle bg
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    /**
     * Outlined button — transparent fill, colored border, colored text.
     */
    private JButton makeOutlineButton(String text, Color borderColor, Color fg, Color bgColor) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Fill background
                Color fill = getModel().isPressed()
                        ? borderColor.darker()
                        : getModel().isRollover()
                        ? new Color(borderColor.getRed(), borderColor.getGreen(), borderColor.getBlue(), 40)
                        : bgColor;
                g2.setColor(fill);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                // Draw border
                g2.setColor(borderColor);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 6, 6);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setForeground(fg);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setPreferredSize(new Dimension(0, 40));
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    /**
     * White label + red " *" required marker.
     */
    private JPanel labelWithRequired(String labelText) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        row.setOpaque(false);

        JLabel lbl = new JLabel(labelText);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Arial", Font.BOLD, 13));

        JLabel star = new JLabel(" *");
        star.setForeground(new Color(255, 60, 60));
        star.setFont(new Font("Arial", Font.BOLD, 14));

        row.add(lbl);
        row.add(star);
        return row;
    }

    private void handleLogin(ActionEvent e) {
        String email    = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        CustomerDAO dao = new CustomerDAO();
        Customer customer = dao.login(email, password);

        if (customer != null) {
            JOptionPane.showMessageDialog(this,
                    "Welcome, " + customer.getName() + "!", "Login Successful",
                    JOptionPane.INFORMATION_MESSAGE);
            new CustomerDashboard(customer).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Invalid email or password!", "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        styleField(field);
        return field;
    }

    private void styleField(JTextField field) {
        field.setPreferredSize(new Dimension(0, 42));
        field.setFont(new Font("Arial", Font.PLAIN, 13));
        field.setBackground(Color.WHITE);
        field.setForeground(FIELD_TEXT);
        field.setCaretColor(FIELD_TEXT);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(FIELD_BORDER),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

    public static void main(String[] args) {
        // Do NOT use system L&F — it overrides button colors
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}