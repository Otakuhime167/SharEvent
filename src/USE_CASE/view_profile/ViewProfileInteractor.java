package USE_CASE.view_profile;



import DATA_ACCESS.loadevents_dataaccess.LoadEventsDataAccessInterface;
import ENTITY.Event;
import ENTITY.User;

public class ViewProfileInteractor implements ViewProfileInputBoundary {
    private final LoadEventsDataAccessInterface eventDataAccessInterface;
    private final ViewProfileOutputBoundary viewEventPresenter;

    public ViewProfileInteractor(LoadEventsDataAccessInterface eventDataAccessInterface, ViewProfileOutputBoundary viewEventPresenter) {
        this.eventDataAccessInterface = eventDataAccessInterface;
        this.viewEventPresenter = viewEventPresenter;
    }

    @Override
    public void execute(ViewProfileInputData username) {

       User user = eventDataAccessInterface(username.getUsername());

        if (user != null) {
            ViewProfileOutputData outputData = new ViewProfileOutputData(
                    user.getUsername(),
                    user.getName(),
                    user.getEmail(),
                    user.getRegisteredEvents(),
                    user.getHostedEvents()
            );;
            viewEventPresenter.successesView(outputData);
        } // додати фейл сітуейшн
    }



}
