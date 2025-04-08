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
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class TelaCadastroCliente extends JFrame {
    public TelaCadastroCliente() {
        setTitle("Cadastro de Cliente");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Container principal
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(new Color(192, 192, 192)); // Cor cinza do fundo

        // Título e botão de voltar
        JPanel topo = new JPanel(new BorderLayout());
        topo.setOpaque(false);

        JLabel titulo = new JLabel("Cadastro de Cliente");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        titulo.setBorder(new EmptyBorder(10, 20, 10, 0));

        JLabel voltar = new JLabel("\u21B6"); // símbolo de seta para trás
        voltar.setFont(new Font("Dialog", Font.PLAIN, 22));
        voltar.setHorizontalAlignment(SwingConstants.RIGHT);
        voltar.setBorder(new EmptyBorder(10, 10, 10, 20));
        voltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        voltar.setToolTipText("Voltar");
        voltar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });

        topo.add(titulo, BorderLayout.WEST);
        topo.add(voltar, BorderLayout.EAST);

        // Formulário
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(10, 40, 10, 40));
        panel.setBackground(Color.WHITE);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        Font fonteCampo = new Font("SansSerif", Font.PLAIN, 16);

        JTextField txtNome = new JTextField(20);
        JTextField txtTelefone = new JTextField(20);
        JTextField txtEmail = new JTextField(20);
        JTextField txtEndereco = new JTextField(20);

        JTextField[] campos = {txtNome, txtTelefone, txtEmail, txtEndereco};
        for (JTextField campo : campos) {
            campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
            campo.setFont(fonteCampo);
            campo.setBorder(new CompoundBorder(
                    new LineBorder(Color.LIGHT_GRAY, 1, true),
                    new EmptyBorder(5, 10, 5, 10)
            ));
            campo.setAlignmentX(Component.LEFT_ALIGNMENT);
        }

        panel.add(new JLabel("Nome:"));
        panel.add(txtNome);
        panel.add(Box.createVerticalStrut(10));

        panel.add(new JLabel("Telefone:"));
        panel.add(txtTelefone);
        panel.add(Box.createVerticalStrut(10));

        panel.add(new JLabel("E-mail:"));
        panel.add(txtEmail);
        panel.add(Box.createVerticalStrut(10));

        panel.add(new JLabel("Endereço:"));
        panel.add(txtEndereco);
        panel.add(Box.createVerticalStrut(20));

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setBackground(new Color(66, 133, 244));
        btnSalvar.setFocusPainted(false);
        btnSalvar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnSalvar.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnSalvar.addActionListener(e -> {
            try (Connection con = Conexao.conectar()) {
                String sql = "INSERT INTO clientes (nome, telefone, email, endereco) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, txtNome.getText());
                stmt.setString(2, txtTelefone.getText());
                stmt.setString(3, txtEmail.getText());
                stmt.setString(4, txtEndereco.getText());
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Cliente salvo com sucesso!");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
            }
        });

        panel.add(btnSalvar);

        container.add(topo, BorderLayout.NORTH);
        container.add(panel, BorderLayout.CENTER);

        add(container);
        setVisible(true);
    }
}