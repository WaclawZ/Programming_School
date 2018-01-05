package program;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import entities.Exercise;
import utils.Connector;

public class ExerciseManagement {

	public static void showExercises() {
		System.out.println("Lista zadan: \n");

		try {
			Connection conn = Connector.getConnection();
			Exercise[] allExercises = Exercise.loadAllExercises(conn);

			System.out.println("id | title | description");

			for (Exercise ex : allExercises) {
				System.out.println(ex.getId() + " | " + ex.getTitle() + " | " + ex.getDescription());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void showEx(Exercise[] allExercises) {
		System.out.println("id | title | description|");
		for (Exercise ex : allExercises) {
			System.out.println(ex.getId() + " | " + ex.getTitle() + " | " + ex.getDescription() + "\n");
		}
	}

	public static void options() {
		System.out.println("Wybierz jedna z opcji: ");
		System.out.println("add - dodawanie zadania");
		System.out.println("edit - edycja zadania");
		System.out.println("delete - usuwanie zadania");
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
			showExercises();
			options();
			String option = sc.nextLine();

			if (option.equals("quit")) {
				sc.close();
				return;
			} else if (option.equals("add")) {

				System.out.println("Podaj tytul zadania: ");
				String title = sc.nextLine();
				System.out.println("Podaj opis zadania: ");
				String description = sc.nextLine();
				Exercise exercise = new Exercise(title, description);
				try {
					exercise.saveToDB(conn);
				} catch (SQLException e) {
					e.printStackTrace();
				}

			} else if (option.equals("edit")) {

				System.out.println("Podaj id zadania do edycji");
				int id = checkNumber();
				Exercise ex = null;
				try {
					ex = Exercise.loadExerciseById(conn, id);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("Podaj tytul zadania: ");
				String title = sc.nextLine();
				System.out.println("Podaj opis zadania: ");
				String description = sc.nextLine();
				ex.setTitle(title);
				ex.setDescription(description);
				try {
					ex.saveToDB(conn);
				} catch (SQLException e) {
					e.printStackTrace();
				}

			} else if (option.equals("delete")) {
				
				System.out.println("Podaj id zadania do usuniecia: ");
				int id = checkNumber();
				try {
					Exercise ex = Exercise.loadExerciseById(conn, id);
					ex.delete(conn);
				} catch (SQLException e) {
					e.printStackTrace();
}

			} else {
				System.out.println("Bledna operacja. Sprobuj jeszcze raz.");
			}
		}
	}

}
