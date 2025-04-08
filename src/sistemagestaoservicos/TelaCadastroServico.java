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
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class TelaCadastroServico extends JFrame {
    public TelaCadastroServico() {
        setTitle("Cadastro de Serviço");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(new Color(192, 192, 192)); // Fundo cinza

        // Topo com título e botão voltar
        JPanel topo = new JPanel(new BorderLayout());
        topo.setOpaque(false);

        JLabel titulo = new JLabel("Cadastro de Serviço");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        titulo.setBorder(new EmptyBorder(10, 20, 10, 0));

        JLabel voltar = new JLabel("\u21B6"); // símbolo de voltar
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

        // Painel de formulário
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(10, 40, 10, 40));
        panel.setBackground(Color.WHITE);

        Font fonteCampo = new Font("SansSerif", Font.PLAIN, 16);

        JTextField txtClienteID = new JTextField(20);
        JTextField txtDescricao = new JTextField(20);
        JTextField txtData = new JTextField(20);
        JTextField txtValor = new JTextField(20);

        JTextField[] campos = {txtClienteID, txtDescricao, txtData, txtValor};
        for (JTextField campo : campos) {
            campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
            campo.setFont(fonteCampo);
            campo.setBorder(new CompoundBorder(
                    new LineBorder(Color.LIGHT_GRAY, 1, true),
                    new EmptyBorder(5, 10, 5, 10)
            ));
            campo.setAlignmentX(Component.LEFT_ALIGNMENT);
        }

        panel.add(new JLabel("Cliente:"));
        panel.add(txtClienteID);
        panel.add(Box.createVerticalStrut(10));

        panel.add(new JLabel("Descrição:"));
        panel.add(txtDescricao);
        panel.add(Box.createVerticalStrut(10));

        panel.add(new JLabel("Data:"));
        panel.add(txtData);
        panel.add(Box.createVerticalStrut(10));

        panel.add(new JLabel("Valor:"));
        panel.add(txtValor);
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
                String sql = "INSERT INTO servicos (descricao, valor, data, cliente_id) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, txtDescricao.getText());
                stmt.setDouble(2, Double.parseDouble(txtValor.getText()));
                stmt.setDate(3, Date.valueOf(txtData.getText()));
                stmt.setInt(4, Integer.parseInt(txtClienteID.getText()));
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Serviço salvo com sucesso!");
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