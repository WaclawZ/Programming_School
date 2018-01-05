package program;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import entities.Exercise;
import entities.Solution;
import entities.User;
import utils.Connector;

public class UserInterface {

	public static void options() {
		System.out.println();
		System.out.println("Wybierz jedną opcje: ");
		System.out.println("add - dodawanie rozwiazania");
		System.out.println("view - przegladanie swoich rozwiazan");
		System.out.println("quit - zakonczenie programu");
	}

	public static void main(String[] args) {

		Connection conn = null;
		try {
			conn = Connector.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Integer userId = Integer.parseInt(args[0]);
		User user = null;
		if (userId != null) {
			try {
				user = User.loadUserById(conn, userId);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (user != null) {
				Scanner scan = new Scanner(System.in);
				while (true) {
					options();
					String opt = scan.nextLine();
					if (opt.equalsIgnoreCase("quit")) {
						scan.close();
						return;
					} else if (opt.equalsIgnoreCase("add")) {
						List<Exercise> userExercises = null;
						List<Exercise> allExercises = null;
						try{
						userExercises = Arrays.asList(Exercise.loadAllByUserId(conn, userId));
						allExercises = Arrays.asList(Exercise.loadAllExercises(conn));
						}catch(SQLException e){
							e.printStackTrace();
						}
						List<Exercise> toDoExercises = new ArrayList<>();
						toDoExercises.addAll(allExercises);
						Iterator<Exercise> it1 = toDoExercises.iterator();
						while (it1.hasNext()) {
							Iterator<Exercise> it2 = userExercises.iterator();
							Exercise e1 = it1.next();
							while (it2.hasNext()) {
								if (e1.getId() == it2.next().getId()) {
									it1.remove();
									break;
								}
							}
						}
						Exercise[] toDoEx = new Exercise[toDoExercises.size()];
						toDoEx = toDoExercises.toArray(toDoEx);
						ExerciseManagement.showEx(toDoEx);
						System.out.println("Podaj id rozwiazania do dodania: ");
						int exerciseId = ExerciseManagement.checkNumber();
						boolean canAdd = false;
						for (Exercise e : toDoEx) {
							if (e.getId() == exerciseId) {
								canAdd = true;
								break;
							}
						}
						if (canAdd) {
							System.out.println("Podaj rozwiazanie zadania");
							String description = scan.nextLine();
							Solution solution = new Solution(null, null, description);
							solution.setExercise_id(exerciseId);
							solution.setUser_id(userId);
							try {
								solution.saveToDB(conn);
							} catch (SQLException e1) {
								e1.printStackTrace();
							}

						} else {
							System.out.println("Nie mozna dodac rozwiazania");
						}

					} else if (opt.equalsIgnoreCase("view")) {
						try{
						Solution[] solutions = Solution.loadAllByUserId(userId, conn);
						SolutionManagement.showSolutions(solutions, conn);
						}catch(SQLException e){
							e.printStackTrace();
						}

					} else {
						System.out.println("Bledna operacja. Sprobuj jeszcze raz.");
					}
				}
			}
		}

	}

}
