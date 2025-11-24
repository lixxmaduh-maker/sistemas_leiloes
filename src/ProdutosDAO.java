import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ProdutosDAO {

    private final Connection conn;

    public ProdutosDAO() {
        this.conn = new conectaDAO().connectDB();
    }

    public boolean cadastrarProduto(ProdutosDTO p) {
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNome());
            ps.setInt(2, p.getValor());
            ps.setString(3, p.getStatus());
            ps.execute();
            return true;

        } catch (Exception e) {
            System.out.println("Erro ao cadastrar: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<ProdutosDTO> listarProdutos() {
        ArrayList<ProdutosDTO> lista = new ArrayList<>();

        String sql = "SELECT id, nome, valor, status FROM produtos ORDER BY id DESC";

        try (PreparedStatement ps = conn.prepareStatement(sql);
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
            System.out.println("Erro ao listar: " + e.getMessage());
        }

        return lista;
    }
    
    public boolean venderProduto(int id) {
    String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ? AND status <> 'Vendido'";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, id);
        int linhas = ps.executeUpdate();
        return linhas == 1;
    } catch (Exception e) {
        return false;
        }
    }
}
