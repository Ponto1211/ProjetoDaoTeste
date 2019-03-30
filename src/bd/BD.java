package bd;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class BD {

	private static Connection con = null;

	public static Connection conectar() {
		if (con == null) {
			try {
				Properties prop = carregarPropriedades();
				String url = prop.getProperty("dburl");
				con = DriverManager.getConnection(url, prop);
			} catch (SQLException e) {
				throw new BdException(e.getMessage());
			}
		}
		return con;
	}

	public static void desconectar() {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				throw new BdException(e.getMessage());
			}
		}
	}

	private static Properties carregarPropriedades() {
		try (FileInputStream fs = new FileInputStream("BD.propriedades")) {
			Properties prop = new Properties();
			prop.load(fs);
			return prop;
		} catch (IOException e) {
			throw new BdException(e.getMessage());
		}
	}

	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new BdException(e.getMessage());
			}
		}
	}

	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new BdException(e.getMessage());
			}
		}
	}
}
