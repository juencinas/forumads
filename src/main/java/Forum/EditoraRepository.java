package aula20200821.repositoryCompleto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EditoraRepository {

	private ConnectionManager connectionManager;

	public EditoraRepository(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
		this.createTable();		
	}
		

	private void createTable() {
		PreparedStatement psCreateTable = null;
		try {
			psCreateTable =  connectionManager.prepareStatement("create table if not exists editora ("
					+ "id long not null primary key,"
					+ "nomeEditora varchar(255) not null,"
					+ "endereco varchar(255) not null"
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



	public Editora findById(Long id) throws SQLException {
		Editora found = null;
		PreparedStatement psSelect = connectionManager.prepareStatement("select id, nomeEditora, endereco from editora where id = ?");
		psSelect.setLong(1, id);
		ResultSet rsSelect = psSelect.executeQuery();
		try {
			if (rsSelect.next()) {
				found = new Editora(
						rsSelect.getLong("id"), 
						rsSelect.getString("nomeEditora"), 
						rsSelect.getString("endereco")); 
			}
		} finally {
			rsSelect.close();
			psSelect.close();
		}
		return found;
	}

	public Editora save(Editora editora) {
		boolean exists = editora.getId() != null;
		PreparedStatement psSave = null;
		try {
			if (exists) {
				psSave = connectionManager.prepareStatement("update editora set nomeEditora = ?, endereco = ? where id = ?");
				psSave.setString(1, editora.getNomeEditora());
				psSave.setString(2, editora.getendereco());
				psSave.setLong(3, editora.getId());
			} else {
				Long id = selectNewId();
				System.out.println("Novo id: " + id);
				psSave = connectionManager.prepareStatement("insert into editora (id, nomeEditora, endereco) values (?,?,?)");
				psSave.setLong(1, id);
				psSave.setString(2, editora.getNomeEditora());
				psSave.setString(3, editora.getendereco());
				editora = new Editora(id,  editora.getNomeEditora(), editora.getendereco());
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
		return editora;
	}

	private Long selectNewId() throws SQLException {
		PreparedStatement psSelect = connectionManager.prepareStatement("select coalesce(max(id),0)+1 as newId from editora");
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
		PreparedStatement psDelete = connectionManager.prepareStatement("delete from editora where id = ?");
		psDelete.setLong(1, id);
		try {
			psDelete.executeUpdate();
		} finally {
			psDelete.close();
		}		
	}


	public List<Editora> findAll() throws SQLException {
		List<Editora> all = new ArrayList<>();
		PreparedStatement psSelect = connectionManager.prepareStatement("select id, nomeEditora, endereco from editora");
		ResultSet rsSelect = psSelect.executeQuery();
		try {
			while (rsSelect.next()) {
				Editora editora = new Editora(
						rsSelect.getLong("id"), 
						rsSelect.getString("nomeEditora"), 
						rsSelect.getString("endereco"));
				all.add(editora);
			}
		} finally {
			rsSelect.close();
			psSelect.close();
		}
		return all;
	}



}	
