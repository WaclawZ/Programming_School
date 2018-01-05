package program;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import entities.Exercise;
import entities.Solution;
import utils.Connector;

public class SolutionManagement {

	public static void showSolutions(Solution[] solutions, Connection conn) throws SQLException {
		System.out.println("id | created | updated | title | decription");
		for (Solution solution : solutions) {
			System.out.print(solution.getId() + " | " + solution.getCreated() + " | " + solution.getUpdated() + " | ");
			Exercise exercise = Exercise.loadExerciseById(conn, solution.getExercise_id());
			System.out.println(exercise.getTitle() + " | " + solution.getDescription() + "\n");
		}
	}

	public static void options() {
		System.out.println("Wybierz jedna z opcji: ");
		System.out.println("add - przypisanie zadan do uzytkownikow");
		System.out.println("view - przegladanie rozwiazan danego uzytkownika");
		System.out.println("quit - zakonczenie programu");
	}

	public static int checkNumber() {
		Scanner sc = new Scanner(System.in);
		while (!sc.hasNextInt()) {
			sc.nextLine();
			System.out.println("Zly format. Sprobuj jeszcze raz.");
		}
		sc.close();
		return sc.nextInt();
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Connection conn = null;
		try {
			conn = Connector.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		while (true) {
			options();
			String option = sc.nextLine();

			if (option.equals("quit")) {
				sc.close();
				return;
			} else if (option.equals("add")) {

				UsersManagement.showUsers();
				System.out.println("Podaj id użytkownika: ");
				int userId = checkNumber();
				ExerciseManagement.showExercises();
				System.out.println("Podaj id zadania ");
				int exerciseId = checkNumber();
				Solution solution = new Solution(null, null, null);
				solution.setExercise_id(exerciseId);
				solution.setUser_id(userId);
				try {
					solution.saveToDB(conn);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			} else if (option.equals("view")) {

				UsersManagement.showUsers();
				System.out.println("Podaj id użytkownika aby wyswietlic jego rozwiazania");
				int id = checkNumber();
				try {
					Solution[] solutions = Solution.loadAllByUserId(id, conn);
					showSolutions(solutions, conn);
				} catch (SQLException e) {
					e.printStackTrace();
				}

			} else {
				System.out.println("Bledna operacja. Sprobuj jeszcze raz.");
			}
		}

	}

}
