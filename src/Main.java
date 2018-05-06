import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Main {

	Session session;

	public static void main(String[] args) {
		Main main = new Main();
		//main.printSchools();
		//main.addNewData();
        //main.executeQueries();
		
		main.executeQuery4();
		main.close();
	}

	public Main() {
		session = HibernateUtil.getSessionFactory().openSession();
	}

	public void close() {
		session.close();
		HibernateUtil.shutdown();
	}

	private void printSchools() {
		Criteria crit = session.createCriteria(School.class);
		List<School> schools = crit.list();

		System.out.println("### Schools and classes");
		for (School s : schools) {
			System.out.println(s);

			for (SchoolClass sc : s.getClasses()) {
				System.out.println(sc);
				System.out.println("  > Students:");

				for (Student st : sc.getStudents()) {
					System.out.println(st);
				}
			}
		}
	}

	private void addNewData() {
		Student st1 = new Student("Anna", "Nowak", "90100207654");
		Student st2 = new Student("Tomasz", "Kwiatek", "89010189456");
		Student st3 = new Student("Maria", "Ignaciak", "88121290345");
		
		SchoolClass sc = new SchoolClass(2017, 2018, "biol-chem");
		sc.addStudent(st1);
		sc.addStudent(st2);
		sc.addStudent(st3);
		
		School s = new School("UJ", "Grodzka 56");
		s.addClasses(sc);
		
		Transaction transaction = session.beginTransaction();
		session.save(s);
		transaction.commit();

	}

/*	private void executeQueries() {
        String hql = "FROM School";
        Query query = session.createQuery(hql);
        List results = query.list();
        System.out.println(results);
}*/

//Chcemy znaleŸæ tylko szko³y, których nazwa to UE
	
	private void executeQuery1() {
		String hql = "FROM School as school WHERE school.name='UE'";
		Query query = session.createQuery(hql);
		List<School> results = query.list();
		for (School s : results) {
			System.out.println(s);
		}
	}

//Wykorzystuj¹c funkcjê session.delete() i analogiê do tworzenia obiektów, usuñ wszystkie odnalezione w powy¿szym punkcie szko³y.	
	
	private void executeQuery2() {
		String hql = "FROM School as school WHERE school.name='UE'";
		Query query = session.createQuery(hql);
		List<School> results = query.list();
		for (School s : results) {
			System.out.println("DRY RUN: session.delete(s)");
		}
	}

//Napisz zapytanie, które zwraca iloœæ szkó³ w bazie (PodpowiedŸ: u¿yj funkcji COUNT())
	
	private void executeQuery3() {
		String hql = "SELECT COUNT (school.name) from schools as school GROUP BY school.name";
		List<Object> result = session.createSQLQuery(hql).list();
		System.out.println(result.size());				
		
	}

//Napisz zapytanie, które zwraca iloœæ studentów w bazie.
	private void executeQuery4() {
		String hql = "FROM Student as students";
		Query query = session.createQuery(hql);
		List<School> results = query.list();
		System.out.println(results.size());
	
	}

//Napisz zapytanie, które zwraca wszystkie szko³y o liczbie klas wiêkszej lub równej 2.
	private void executeQuery5() {
	}

//wyszukuje szko³ê z klas¹ o profilu mat-fiz oraz obecnym roku wiêkszym b¹dŸ równym 2
	private void executeQuery6() {
	}
	
	private void jdbcTest() {
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("org.sqlite.JDBC");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection("jdbc:sqlite:school.db", "", "");

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT * FROM schools";
			ResultSet rs = stmt.executeQuery(sql);

			// STEP 5: Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				String name = rs.getString("name");
				String address = rs.getString("address");

				// Display values
				System.out.println("Name: " + name);
				System.out.println(" address: " + address);
			}
			// STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");
	}// end jdbcTest

}
