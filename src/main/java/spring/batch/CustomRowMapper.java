package spring.batch;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class CustomRowMapper implements RowMapper<Usuario> {

	@Override
	public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {

		Usuario usuario = new Usuario();

	      usuario.setId(rs.getInt("id"));
	      usuario.setNombre(rs.getString("nombre"));
	      usuario.setApellidos(rs.getString("apellidos"));
	      usuario.setEdad(rs.getInt("edad"));
	      usuario.setPrecio(rs.getDouble("precio"));
	      usuario.setFecha(rs.getDate("fecha"));

		return usuario;
	}

}

