package lk.ijse.gdse71.orm_course_work;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.ijse.gdse71.orm_course_work.config.FactoryConfiguration;
import org.hibernate.Session;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/view/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        Session session = FactoryConfiguration.getInstance().getSession();
        System.out.println("...!");
        session.close();
    }

    public static void main(String[] args) {
        launch();
    }
}