package aula20200821.repositoryCompleto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EscritorRepository {

	private ConnectionManager connectionManager;

	public EscritorRepository(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
		this.createTable();		
	}
		

	private void createTable() {
		PreparedStatement psCreateTable = null;
		try {
			psCreateTable =  connectionManager.prepareStatement("create table if not exists livro ("
					+ "id long not null primary key,"
					+ "titulo varchar(255) not null,"
					+ "qtdepaginas long not null",
					+ "id_escritor long not null",
					+ "id_editora long not null",
					+ ")");
			psCreateTable.executeLargeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (psCreateTable != null) {
				try {
					psCreateTable.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}		
	}



	public Escritor findById(Long id) throws SQLException {
		Escritor found = null;
		PreparedStatement psSelect = connectionManager.prepareStatement("select id, titulo, qtdepaginas, id_escritor, id_editora from livro where id = ?");
		psSelect.setLong(1, id);
		ResultSet rsSelect = psSelect.executeQuery();
		try {
			if (rsSelect.next()) {
				found = new Escritor(
						rsSelect.getLong("id"), 
						rsSelect.getString("titulo"), 
						rsSelect.getLong("qtdepaginas"),
						rsSelect.getLong("id_escritor"),
						rsSelect.getLong("id_editora")); 
			}
		} finally {
			rsSelect.close();
			psSelect.close();
		}
		return found;
	}

	public Escritor save(Escritor livro) {
		boolean exists = livro.getId() != null;
		PreparedStatement psSave = null;
		try {
			if (exists) {
				psSave = connectionManager.prepareStatement("update livro set titulo = ?, qtdepaginas = ?, id_escritor = ?, id_editora = ? where id = ?");
				psSave.setString(1, livro.getTitulo());
				psSave.setLong(2, livro.getqtdePaginas());
				psSave.setLong(3, livro.getIdEscritor());
				psSave.setLong(4, livro.getIdEditora());
				psSave.setLong(5, livro.getId());
			} else {
				Long id = selectNewId();
				System.out.println("Novo id: " + id);
				psSave = connectionManager.prepareStatement("insert into livro (id, titulo, qtdepaginas, id_escritor, id_editora) values (?,?,?,?,?)");
				psSave.setLong(1, id);
				psSave.setString(2, livro.getTitulo());
				psSave.setLong(3, livro.getqtdePaginas());
				psSave.setLong(4, livro.getIdEscritor());
				psSave.setLong(5, livro.getIdEditora());				
				livro = new Escritor(id,  livro.getTitulo(), livro.getendereco());
			}
			psSave.executeUpdate();			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				psSave.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return livro;
	}

	private Long selectNewId() throws SQLException {
		PreparedStatement psSelect = connectionManager.prepareStatement("select coalesce(max(id),0)+1 as newId from livro");
		ResultSet rsSelect = psSelect.executeQuery();
		try {
			if (rsSelect.next()) {
				return rsSelect.getLong("newId");
			}
		} finally {
			rsSelect.close();
			psSelect.close();
		}
		return null;
	}


	public void deleteById(Long id) throws SQLException {
		PreparedStatement psDelete = connectionManager.prepareStatement("delete from livro where id = ?");
		psDelete.setLong(1, id);
		try {
			psDelete.executeUpdate();
		} finally {
			psDelete.close();
		}		
	}


	public List<Escritor> findAll() throws SQLException {
		List<Escritor> all = new ArrayList<>();
		PreparedStatement psSelect = connectionManager.prepareStatement("select id, titulo, qtdepaginas, id_escritor, id_editora from livro");
		ResultSet rsSelect = psSelect.executeQuery();
		try {
			while (rsSelect.next()) {
				Escritor livro = new Escritor(
						rsSelect.getLong("id"), 
						rsSelect.getString("titulo"), 
						rsSelect.getLong("qtdepaginas"),
						rsSelect.getLong("id_escritor"),
						rsSelect.getLong("id_editora"));
				all.add(livro);
			}
		} finally {
			rsSelect.close();
			psSelect.close();
		}
		return all;
	}



}	
