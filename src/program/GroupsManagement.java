package program;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import entities.Group;
import utils.Connector;

public class GroupsManagement {

	public static void showGroups() {
		System.out.println("Lista zadan: \n");

		try {
			Connection conn = Connector.getConnection();
			Group[] allGroups = Group.loadAllGroups(conn);

			System.out.println("id | name");

			for (Group group : allGroups) {
				System.out.println(group.getId() + " | " + group.getName());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void options() {
		System.out.println("Wybierz jedna z opcji: ");
		System.out.println("add - dodawanie grupy");
		System.out.println("edit - edycja grupy");
		System.out.println("delete - usuwanie grupy");
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
			showGroups();
			options();
			String option = sc.nextLine();

			if (option.equals("quit")) {
				sc.close();
				return;
			} else if (option.equals("add")) {

				System.out.println("Podaj nazwe grupy: ");
				String name = sc.nextLine();
				Group group = new Group(name);
				try {
					group.saveToDB(conn);
				} catch (SQLException e) {
					e.printStackTrace();
				}

			} else if (option.equals("edit")) {

				System.out.println("Podaj id grupy do edycji: ");
				int id = checkNumber();
				Group group = null;
				try {
					group = Group.loadGroupById(conn, id);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("Podaj nazwe grupy: ");
				String name = sc.nextLine();
				group.setName(name);
				try {
					group.saveToDB(conn);
				} catch (SQLException e) {
					e.printStackTrace();
				}

			} else if (option.equals("delete")) {

				System.out.println("Podaj id grupy do usuniecia: ");
				int id = checkNumber();
				try {
					Group group = Group.loadGroupById(conn, id);
					group.delete(conn);
				} catch (SQLException e) {
					e.printStackTrace();
				}

			} else {
				System.out.println("Bledna operacja. Sprobuj jeszcze raz.");
			}
		}

	}

}
