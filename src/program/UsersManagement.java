package program;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import entities.User;
import utils.Connector;

public class UsersManagement {

	public static void showUsers() {
		System.out.println("Lista uzytkownikow: \n");

		try {
			Connection conn = Connector.getConnection();
			User[] allUsers = User.loadAll(conn);

			System.out.println("groupId | id | username | email");

			for (User user : allUsers) {
				System.out.println(user.getUser_group_id() + " | " + user.getId() + " | " + user.getUsername() + " | "
						+ user.getEmail());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void options() {
		System.out.println("Wybierz jedna z opcji: ");
		System.out.println("add - dodawanie uzytkownika");
		System.out.println("edit - edycja uzytkownika");
		System.out.println("delete - usuwanie uzytkownika");
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
			showUsers();
			options();
			String option = sc.nextLine();

			if (option.equals("quit")) {
				sc.close();
				return;
			} else if (option.equals("add")) {

				System.out.println("Podaj nazwe uzytkownika: ");
				String username = sc.nextLine();
				System.out.println("Podaj emaila uzytkownika: ");
				String email = sc.nextLine();
				System.out.println("Podaj haslo uzytkownika: ");
				String password = sc.nextLine();
				System.out.println("Podaj id grupy uzytkownika: ");
				int groupId = checkNumber();
				User user = new User(username, email, password, groupId);
				try {
					user.saveToDB(conn);
				} catch (SQLException e) {
					e.printStackTrace();
				}

			} else if (option.equals("edit")) {

				System.out.println("Podaj id uzytkownika do edycji: ");
				int id = checkNumber();
				User user = null;
				try {
					user = User.loadUserById(conn, id);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("Podaj nazwe uzytkownika: ");
				String username = sc.nextLine();
				System.out.println("Podaj emaila uzytkownika: ");
				String email = sc.nextLine();
				System.out.println("Podaj haslo uzytkownika: ");
				String password = sc.nextLine();
				System.out.println("Podaj id grupy uzytkownika: ");
				int groupId = checkNumber();
				user.setUsername(username);
				user.setEmail(email);
				user.setPassword(password);
				user.setUser_group_id(groupId);
				try {
					user.saveToDB(conn);
				} catch (SQLException e) {
					e.printStackTrace();
				}

			} else if (option.equals("delete")) {

				System.out.println("Podaj id uzytkownika do usuniecia: ");
				int id = checkNumber();
				try {
					User user = User.loadUserById(conn, id);
					user.delete(conn);
				} catch (SQLException e) {
					e.printStackTrace();
				}

			} else {
				System.out.println("Bledna operacja. Sprobuj jeszcze raz.");
			}
		}

	}

}
