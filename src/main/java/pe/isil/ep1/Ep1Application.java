package pe.isil.ep1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.sql.*;

@SpringBootApplication
public class Ep1Application {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Ep1Application.class, args);
		//profe igual iniciaba si lo ponía sin el 'cj"
		//y sin usar el class.forname sale que es innecesario
		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/ep1_artist", "root","root");

		System.out.println("data");
		getStatement(connection);
		System.out.println("\n \n \n");
		UpdatePreparedStatement(connection);
		System.out.println("\n \n \n");
		createCallableStatement(connection,"chiasdasdstos","test","24","rock");

		connection.close();





	}

	public static void getStatement(Connection connection) throws  Exception {
		Statement st = connection.createStatement();


		ResultSet resultSet = st.executeQuery("SELECT * FROM ARTIST");
		while(resultSet.next()){
			System.out.println(resultSet.getString("username"));
			System.out.println(resultSet.getString("name"));
			System.out.println(resultSet.getString("age"));
			System.out.println(resultSet.getString("genre"));



		}

	}

	public static void UpdatePreparedStatement (Connection connection) throws  Exception {

		PreparedStatement preparedStatement = connection.prepareStatement("update artist set name =? where idArtist=?");
		preparedStatement.setString(1, "DEADMAU");
		preparedStatement.setInt(2,3);
		preparedStatement.executeUpdate();
		//puse el get en el update para reutilizar el metodo
		System.out.println("data actualizada ");
		getStatement(connection);
		int rows = preparedStatement.executeUpdate();
		System.out.println("Número de filas actualizadas:" + rows);



	}

	public static void createCallableStatement (Connection connection, String username, String name, String age, String gender ) throws  Exception {

		CallableStatement cs = connection.prepareCall("{call insertArtist(?,?,?,?)}");
		cs.setString(1, username);
		cs.setString(2, name);
		cs.setString(3, age);
		cs.setString(4, gender);

		ResultSet rs = cs.executeQuery();
		//profe al hacer el print hay algo que estoy haciendo mal, igualmente si lo crea en la tabla, y muestra el mensaje de ya creado si es que existe.
		while(rs.next()){
			System.out.println(cs.getString(1));





		}

	}



}
