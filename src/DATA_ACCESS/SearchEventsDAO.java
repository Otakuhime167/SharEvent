package DATA_ACCESS;

import ENTITY.Event;

import java.util.Set;

public interface SearchEventsDAO {
    public Set<Event> SearchEvent(String searchInput);
}
