/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemagestaoservicos;

/**
 *
 * @author Angelo
 */

import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JFrame {
    public TelaLogin() {
        setTitle("Login");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Componentes
        JLabel lblTitulo = new JLabel("Login");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField txtUsuario = new JTextField(15);
        txtUsuario.setFont(new Font("SansSerif", Font.PLAIN, 18));
        txtUsuario.setMaximumSize(new Dimension(250, 40));
        txtUsuario.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));

        JPasswordField txtSenha = new JPasswordField(15);
        txtSenha.setFont(new Font("SansSerif", Font.PLAIN, 18));
        txtSenha.setMaximumSize(new Dimension(250, 40));
        txtSenha.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));

        JButton btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnLogin.setBackground(new Color(66, 133, 244));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setMaximumSize(new Dimension(250, 40));
        btnLogin.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        btnLogin.addActionListener(e -> {
            String usuario = txtUsuario.getText();
            String senha = new String(txtSenha.getPassword());

            if (usuario.equals("admin") && senha.equals("1234")) {
                new TelaPainel();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Credenciais inválidas");
            }
        });

        // Layout moderno centralizado
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        panel.add(Box.createVerticalGlue());
        panel.add(lblTitulo);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(txtUsuario);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(txtSenha);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(btnLogin);
        panel.add(Box.createVerticalGlue());

        add(panel);
        setVisible(true);
    }
}