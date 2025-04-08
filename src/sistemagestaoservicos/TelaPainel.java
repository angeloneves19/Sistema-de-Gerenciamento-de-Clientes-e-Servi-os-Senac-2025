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
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class TelaPainel extends JFrame {
    JLabel lblClientes, lblServicos, lblPendentes;

    public TelaPainel() {
        setTitle("Painel");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Font fonteTitulo = new Font("Arial", Font.BOLD, 14);
        Font fonteValor = new Font("Arial", Font.PLAIN, 20);
        Color corCaixa = Color.WHITE;
        Color corBorda = new Color(200, 200, 200);
        Color corFundo = new Color(240, 240, 240);
        Color corBotao = new Color(66, 133, 244);
        Color corTextoBotao = Color.WHITE;

        getContentPane().setBackground(corFundo);

        lblClientes = new JLabel("0", SwingConstants.CENTER);
        lblServicos = new JLabel("0", SwingConstants.CENTER);
        lblPendentes = new JLabel("0", SwingConstants.CENTER);

        atualizarContadores();

        JPanel boxClientes = criarBox("Clientes", lblClientes, fonteTitulo, fonteValor, corCaixa, corBorda);
        JPanel boxServicos = criarBox("Serviços", lblServicos, fonteTitulo, fonteValor, corCaixa, corBorda);
        JPanel boxPendentes = criarBox("Pagamento pendente", lblPendentes, fonteTitulo, fonteValor, corCaixa, corBorda);

        JButton btnNovoCliente = new JButton("+ Novo Cliente");
        JButton btnNovoServico = new JButton("+ Novo Serviço");

        btnNovoCliente.setBackground(corBotao);
        btnNovoCliente.setForeground(corTextoBotao);
        btnNovoServico.setBackground(corBotao);
        btnNovoServico.setForeground(corTextoBotao);

        btnNovoCliente.setFocusPainted(false);
        btnNovoServico.setFocusPainted(false);

        btnNovoCliente.setPreferredSize(new Dimension(160, 35));
        btnNovoServico.setPreferredSize(new Dimension(160, 35));

        btnNovoCliente.addActionListener(e -> new TelaCadastroCliente());
        btnNovoServico.addActionListener(e -> new TelaCadastroServico());

        JPanel boxPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        boxPanel.setBackground(corFundo);
        boxPanel.add(boxClientes);
        boxPanel.add(boxServicos);
        boxPanel.add(boxPendentes);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        btnPanel.setBackground(corFundo);
        btnPanel.add(btnNovoCliente);
        btnPanel.add(btnNovoServico);

        JPanel topoPanel = new JPanel();
        topoPanel.setLayout(new BoxLayout(topoPanel, BoxLayout.Y_AXIS));
        topoPanel.setBackground(corFundo);
        topoPanel.add(boxPanel);
        topoPanel.add(btnPanel);

        JTable tabela = new JTable(new DefaultTableModel(new Object[][] {}, new Object[]{"Descrição", "Data", "Status", "Valor"}));
        preencherTabela(tabela);

        tabela.setRowHeight(28);
        tabela.setFont(new Font("Arial", Font.PLAIN, 14));
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabela.getTableHeader().setBackground(new Color(220, 220, 220));
        tabela.setGridColor(Color.LIGHT_GRAY);

        tabela.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String status = value != null ? value.toString().toLowerCase() : "";
                c.setForeground(status.contains("pendente") ? Color.RED : Color.BLACK);
                return c;
            }
        });

        JPanel painelCentral = new JPanel(new BorderLayout());
        painelCentral.add(topoPanel, BorderLayout.NORTH);
        painelCentral.add(new JScrollPane(tabela), BorderLayout.CENTER);

        add(painelCentral);
        setVisible(true);
    }

    private JPanel criarBox(String titulo, JLabel valorLabel, Font fonteTitulo, Font fonteValor, Color corFundo, Color corBorda) {
        JLabel tituloLabel = new JLabel(titulo, SwingConstants.CENTER);
        tituloLabel.setFont(fonteTitulo);

        valorLabel.setFont(fonteValor);

        JPanel box = new JPanel(new GridLayout(2, 1));
        box.add(tituloLabel);
        box.add(valorLabel);
        box.setPreferredSize(new Dimension(150, 70));
        box.setBackground(corFundo);
        box.setBorder(new LineBorder(corBorda, 1, true));
        return box;
    }

    private void atualizarContadores() {
        try (Connection con = Conexao.conectar()) {
            Statement stmt = con.createStatement();

            ResultSet rsClientes = stmt.executeQuery("SELECT COUNT(*) FROM clientes");
            rsClientes.next();
            lblClientes.setText(String.valueOf(rsClientes.getInt(1)));

            ResultSet rsServicos = stmt.executeQuery("SELECT COUNT(*) FROM servicos");
            rsServicos.next();
            lblServicos.setText(String.valueOf(rsServicos.getInt(1)));

            ResultSet rsPendentes = stmt.executeQuery("SELECT COUNT(*) FROM pagamentos WHERE status='pendente'");
            rsPendentes.next();
            lblPendentes.setText(String.valueOf(rsPendentes.getInt(1)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void preencherTabela(JTable tabela) {
        try (Connection con = Conexao.conectar()) {
            String sql = """
                    SELECT c.nome, s.descricao, s.data, p.status, s.valor 
                    FROM servicos s 
                    JOIN clientes c ON s.cliente_id = c.id
                    LEFT JOIN pagamentos p ON p.cliente_id = c.id
                    """;

            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = (DefaultTableModel) tabela.getModel();
            while (rs.next()) {
                String desc = rs.getString("nome") + " " + rs.getString("descricao");
                model.addRow(new Object[]{
                        desc,
                        rs.getDate("data"),
                        rs.getString("status") != null ? rs.getString("status") : "Sem Info",
                        String.format("R$%.2f", rs.getDouble("valor"))
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}