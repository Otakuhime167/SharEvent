package APP;


import DATA_ACCESS.DatabaseDAO;
import DATA_ACCESS.LoadEventsDataAccessInterface;
import INTERFACE_ADAPTER.ViewManagerModel;
import INTERFACE_ADAPTER.add_event.AddEventViewModel;
import INTERFACE_ADAPTER.delete_event.DeleteEventController;
import INTERFACE_ADAPTER.delete_event.DeleteEventViewModel;
import INTERFACE_ADAPTER.login_adapter.LoginViewModel;
import INTERFACE_ADAPTER.map_adapter.LoggedInViewModel;
import INTERFACE_ADAPTER.signup_adapter.SignUpViewModel;
import INTERFACE_ADAPTER.view_event.ViewEventViewModel;
import VIEW.*;
import VIEW_CREATOR.LoadMapViewModel;
import javafx.application.Application;
import javafx.stage.Stage;

import VIEW.LoadMapView;

import javax.swing.*;
import java.awt.*;


public class main extends Application {
    int primaryStageWidth;
    int primaryStageHeight;
public static LoadMapView loadMapView;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Map Embed Example");
        JFrame application = new JFrame("SharEvent");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);
        application.add(views);
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        LoginViewModel loginViewModel = new LoginViewModel();
        SignUpViewModel signupViewModel = new SignUpViewModel();
        LoadMapViewModel mapViewModel = new LoadMapViewModel();
        AddEventViewModel addEventViewModel = new AddEventViewModel();
        ViewEventViewModel viewEventViewModel = new ViewEventViewModel();
        DatabaseDAO databaseDAO = new DatabaseDAO();
        DeleteEventViewModel deleteEventViewModel = new DeleteEventViewModel();

        SignUpView signupView = SignUpUseCaseFactory.SignupUseCaseFactory.create(viewManagerModel, loginViewModel, signupViewModel);
        views.add(signupView, signupView.viewName);

        LoginView loginView = LoginUseCaseFactory.create(viewManagerModel, loginViewModel, mapViewModel);
        views.add(loginView, loginView.viewName);

       loadMapView = MapUseCasesFactory.create(mapViewModel, loginViewModel, addEventViewModel, viewEventViewModel, databaseDAO, viewManagerModel, deleteEventViewModel);
        views.add(loadMapView, loadMapView.viewName);

        viewManagerModel.setActiveView(signupView.viewName);
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setVisible(true);

    }
}