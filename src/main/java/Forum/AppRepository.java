package aula20200821.repositoryCompleto;

import java.sql.SQLException;
import java.util.List;

public class AppRepository {

	public static void main(String[] args) throws SQLException {
		ConnectionManager connManager = new ConnectionManager();
		try {
			EditoraRepository repo = new EditoraRepository(connManager);
			
			Editora NovaEditora = new Editora("Nova Era Editora de Livros", "Rua São Paulo, numero 266 - São Paulo SP";
			NovaEditora = repo.save(NovaEditora);
			connManager.commit();
			
			Long id = NovaEditora.getId();
			System.out.println("Procurando pelo id: " + id);
			
			List<Editora> editorasCadastrados = repo.findAll();
			System.out.println("Todos as editoras: ");
			for (Editora editora : editorasCadastrados) {
				System.out.println(editora.toString());
			}
			System.out.println("Final de consulta.");

			
		} catch (Exception e) {
			e.printStackTrace();			
		} finally {
			connManager.close();
		}
	}

}
