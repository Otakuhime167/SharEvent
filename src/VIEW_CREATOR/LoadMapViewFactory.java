package VIEW_CREATOR;

import INTERFACE_ADAPTER.LoadMapController;
import javafx.embed.swing.SwingNode;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.util.LinkedList;

public class LoadMapViewFactory {


    public StackPane createView(StackPane pane, LoadMapViewModel viewModel, LoadMapController controller) {
        LinkedList<Button> buttons = createButtons(viewModel);

        SwingNode swingNode = new SwingNode();
        swingNode.setContent(viewModel.getMapKit());

        pane.getChildren().add(swingNode);
        for (Button button : buttons) pane.getChildren().add(button);

        adjustButtonLocation(pane);
        setButtonListeners(pane, controller);

        return pane;
    }

    private LinkedList<Button> createButtons(LoadMapViewModel viewModel) {
        //TODO: addjust buttons so that they meet the UI
        Button viewProfileButton = LoadMapButtonFactory.createViewProfileButton(LoadMapButtonFactory.class, viewModel.getViewProfileButtonImgPath(), viewModel.getViewProfileButtonStyle(), viewModel.getViewProfileButtonSize());
        Button filterEventsButton = LoadMapButtonFactory.createFilterEventsButton(LoadMapButtonFactory.class, viewModel.getFilterEventsButtonImgPath(), viewModel.getRightMenuButtonsStyle(), viewModel.getRightMenuButtonSize());
        Button viewFriendsButton = LoadMapButtonFactory.createViewFriendsButton(LoadMapButtonFactory.class, viewModel.getViewFriendsButtonImgPath(), viewModel.getRightMenuButtonsStyle(), viewModel.getRightMenuButtonSize());
        Button viewEventsButton = LoadMapButtonFactory.createViewEventsButton(LoadMapButtonFactory.class, viewModel.getViewEventsButtonImgPath(), viewModel.getRightMenuButtonsStyle(), viewModel.getRightMenuButtonSize());
        Button addEventButton = LoadMapButtonFactory.createAddEventButton(LoadMapButtonFactory.class, viewModel.getAddEventButtonImgPath(), viewModel.getRightMenuButtonsStyle(), viewModel.getRightMenuButtonSize());
        Button updateEventsButton = LoadMapButtonFactory.createUpdateEventsButton(viewModel.getUpdateEventsButtonName(), viewModel.getUpdateEventsButtonStyle());

        LinkedList<Button> buttons = new LinkedList<>();

        buttons.add(viewProfileButton);
        buttons.add(filterEventsButton);
        buttons.add(viewFriendsButton);
        buttons.add(viewEventsButton);
        buttons.add(addEventButton);
        buttons.add(updateEventsButton);

        return buttons;
    }

    private void adjustButtonLocation(StackPane pane){

        Button viewProfileButton = (Button) pane.getChildren().get(1);
        Button filterEventsButton = (Button) pane.getChildren().get(2);
        Button viewFriendsButton = (Button) pane.getChildren().get(3);
        Button viewEventsButton = (Button) pane.getChildren().get(4);
        Button addEventButton = (Button) pane.getChildren().get(5);
        Button updateEventsButton = (Button) pane.getChildren().get(6);

        StackPane.setAlignment(viewProfileButton, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(viewProfileButton, new Insets(0, 20, 820, 0));

        StackPane.setAlignment(filterEventsButton, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(filterEventsButton, new Insets(0, 20, 620, 0));

        StackPane.setAlignment(viewFriendsButton, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(viewFriendsButton, new Insets(0, 20, 420, 0));

        StackPane.setAlignment(viewEventsButton, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(viewEventsButton, new Insets(0, 20, 220, 0));

        StackPane.setAlignment(addEventButton, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(addEventButton, new Insets(0, 20, 20, 0));

        StackPane.setAlignment(updateEventsButton, Pos.BOTTOM_CENTER);
        StackPane.setMargin(updateEventsButton, new Insets(0, 0, 12, 0));
        updateEventsButton.setVisible(true);
    }

    private void setButtonListeners(StackPane pane, LoadMapController controller) {

        Button viewProfileButton = (Button) pane.getChildren().get(1);
        Button filterEventsButton = (Button) pane.getChildren().get(2);
        Button viewFriendsButton = (Button) pane.getChildren().get(3);
        Button viewEventsButton = (Button) pane.getChildren().get(4);
        Button addEventButton = (Button) pane.getChildren().get(5);
        Button updateEventsButton = (Button) pane.getChildren().get(6);

        viewProfileButton.setOnAction(e -> {
            controller.viewProfile();
        });

        filterEventsButton.setOnAction(e -> {
            controller.filterEvents();
        });

        viewFriendsButton.setOnAction(e -> {
            controller.viewFriends();
        });

        viewEventsButton.setOnAction(e -> {
            controller.viewEvents();
        });

        addEventButton.setOnAction(e -> {
            controller.addEvent();
        });

        updateEventsButton.setOnAction(e -> {
            controller.updateEvents();
        });
    }
}
