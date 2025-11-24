import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class ProdutosDAO {
    private Connection getConnection() throws Exception {
        String url  = "jdbc:mysql://127.0.0.1:3306/leiloes_db"
                    + "?useSSL=false"
                    + "&allowPublicKeyRetrieval=true"
                    + "&serverTimezone=America/Sao_Paulo";
        String user = "root";
        String pass = "Panya121505@";
        Class.forName("com.mysql.cj.jdbc.Driver");
        return java.sql.DriverManager.getConnection(url, user, pass);
    }

    public boolean cadastrarProduto(ProdutosDTO p) {
        String sql = "INSERT INTO produto (nome, valor, status) VALUES (?, ?, ?)";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getNome());
            ps.setInt(2, p.getValor());
            ps.setString(3, p.getStatus());
            int linhas = ps.executeUpdate();
            return (linhas == 1);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public java.util.List<ProdutosDTO> listarProdutos() {
        java.util.List<ProdutosDTO> lista = new java.util.ArrayList<>();
        String sql = "SELECT id, nome, valor, status FROM produto ORDER BY id DESC";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ProdutosDTO p = new ProdutosDTO();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setValor(rs.getInt("valor"));
                p.setStatus(rs.getString("status"));
                lista.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
