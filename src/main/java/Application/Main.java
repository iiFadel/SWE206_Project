package Application;

import GUI.WelcomePage;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			WelcomePage welcomePage = new WelcomePage();
			primaryStage = welcomePage.getMainStage();
			primaryStage.setTitle("");
			primaryStage.setResizable(false);
			primaryStage.setOpacity(0);
			primaryStage.show();
			primaryStage.setOpacity(1);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
