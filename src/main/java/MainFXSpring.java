import ctrl.ComputerRepairShopController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import services.ComputerRepairServices;
import services.ServicesException;

public class MainFXSpring extends Application {
    private static ApplicationContext context;

    @Override
    public void init() {
        try {
            context = new AnnotationConfigApplicationContext(RepairShopConfig.class);
        } catch (Exception e) {
            System.err.println("Failed to initialize Spring context: " + e.getMessage());
            Platform.exit();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getClassLoader().getResource("RepairShopWindow.fxml")
            );

            Parent root = loader.load();
            ComputerRepairShopController ctrl = loader.getController();
            ctrl.setService(getService());

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.sizeToScene();
            primaryStage.setTitle("Computer Repairs Shop");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error starting application", e);
            Platform.exit();
        }
    }

    private static ComputerRepairServices getService() throws ServicesException {
        if (context == null) {
            throw new ServicesException("Application context not initialized");
        }
        return context.getBean(ComputerRepairServices.class);
    }

    private void showError(String message, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    @Override
    public void stop() {
        if (context != null) {
            ((AnnotationConfigApplicationContext) context).close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}