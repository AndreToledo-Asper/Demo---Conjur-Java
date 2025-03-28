import java.sql.*;

public class bancoDados {
    private static final String URL = "jdbc:mysql://maglev.proxy.rlwy.net:12233/railway";
    private static final String USUARIO = "root";
    private static final String SENHA = "EdeIpjCqgYqgJLzKWaoJjxaicgWUgYum";

    public static boolean validarLogin(String email, String senha) {
        try (Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA)) {
            String query = "SELECT * FROM MainTableConjurDemo WHERE email = ? AND senha = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int obterSaldo(String email) {
        try (Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA)) {
            String query = "SELECT saldo FROM MainTableConjurDemo WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("saldo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Retorno padrão para erro
    }
}
