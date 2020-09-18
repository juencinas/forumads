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
			psCreateTable =  connectionManager.prepareStatement("create table if not exists escritor ("
					+ "id long not null primary key,"
					+ "nomeEscritor varchar(255) not null,"
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



	public Escritor findById(Long id) throws SQLException {
		Escritor found = null;
		PreparedStatement psSelect = connectionManager.prepareStatement("select id, nomeEscritor, endereco from escritor where id = ?");
		psSelect.setLong(1, id);
		ResultSet rsSelect = psSelect.executeQuery();
		try {
			if (rsSelect.next()) {
				found = new Escritor(
						rsSelect.getLong("id"), 
						rsSelect.getString("nomeEscritor"), 
						rsSelect.getString("endereco")); 
			}
		} finally {
			rsSelect.close();
			psSelect.close();
		}
		return found;
	}

	public Escritor save(Escritor escritor) {
		boolean exists = escritor.getId() != null;
		PreparedStatement psSave = null;
		try {
			if (exists) {
				psSave = connectionManager.prepareStatement("update escritor set nomeEscritor = ?, endereco = ? where id = ?");
				psSave.setString(1, escritor.getNomeEscritor());
				psSave.setString(2, escritor.getendereco());
				psSave.setLong(3, escritor.getId());
			} else {
				Long id = selectNewId();
				System.out.println("Novo id: " + id);
				psSave = connectionManager.prepareStatement("insert into escritor (id, nomeEscritor, endereco) values (?,?,?)");
				psSave.setLong(1, id);
				psSave.setString(2, escritor.getNomeEscritor());
				psSave.setString(3, escritor.getendereco());
				escritor = new Escritor(id,  escritor.getNomeEscritor(), escritor.getendereco());
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
		return escritor;
	}

	private Long selectNewId() throws SQLException {
		PreparedStatement psSelect = connectionManager.prepareStatement("select coalesce(max(id),0)+1 as newId from escritor");
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
		PreparedStatement psDelete = connectionManager.prepareStatement("delete from escritor where id = ?");
		psDelete.setLong(1, id);
		try {
			psDelete.executeUpdate();
		} finally {
			psDelete.close();
		}		
	}


	public List<Escritor> findAll() throws SQLException {
		List<Escritor> all = new ArrayList<>();
		PreparedStatement psSelect = connectionManager.prepareStatement("select id, nomeEscritor, endereco from escritor");
		ResultSet rsSelect = psSelect.executeQuery();
		try {
			while (rsSelect.next()) {
				Escritor escritor = new Escritor(
						rsSelect.getLong("id"), 
						rsSelect.getString("nomeEscritor"), 
						rsSelect.getString("endereco"));
				all.add(escritor);
			}
		} finally {
			rsSelect.close();
			psSelect.close();
		}
		return all;
	}



}	
