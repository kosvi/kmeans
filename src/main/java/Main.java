import java.util.Scanner;

import model.Clusters;
import ui.UI;

public class Main {

	public static void main(String[] args) {
		System.out.println("Welcome to KMeans");
		Scanner s = new Scanner(System.in);
		Clusters c = new Clusters();
		UI ui = new UI(s, c, 100, 3, 200);
		ui.start();
	}
}
