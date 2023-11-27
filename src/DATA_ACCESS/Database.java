package DATA_ACCESS;

import ENTITY.Event;
import ENTITY.User;
import lombok.SneakyThrows;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public class Database {
    Connection connection;

    public Database() {
        connection = connect();
    }

    public Connection connect() {
        String url = "jdbc:postgresql://db.bqeyxdersfsiysrpyzqb.supabase.co:5432/postgres";
        String user = "basic_user";
        String password = "pass1111";


        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean closeConnection() {
        try {
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public Object executeQuery(String query, boolean isUpdate, Object... parameters) {
        connection = connect();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // Set parameters if any
            for (int i = 0; i < parameters.length; i++) {
                statement.setObject(i + 1, parameters[i]);
            }

            ResultSet resultSet;
            // Execute the query
            if (isUpdate) {
                // For INSERT, UPDATE, DELETE
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected == 0) {
                    System.out.println("Failed to save");
                    return false;
                } else {
                    return true;
                }
            } else {
                // For SELECT
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    //TODO What are we supposed to do gere instead of printing resultSet?
                    System.out.println(resultSet.getString(1));
                    return resultSet;
                } else {
                    System.out.println("No results found");
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error executing SQL query");
            e.printStackTrace();
            return null;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    // find one user
    public Object executeQueryUser(String query, String username) {
        connection = connect();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            return extractUser(resultSet);

        } catch (SQLException e) {
            System.out.println("Error executing SQL query");
            e.printStackTrace();
            return null;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public Object executeQueryUserListForEvent(String query, int event_id) {
        connection = connect();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, event_id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            List<User> userList = new LinkedList<>();

            while (resultSet.next()) {
                userList.add(extractUser(resultSet));
            }

            return extractUser(resultSet);

        } catch (SQLException e) {
            System.out.println("Error executing SQL query");
            e.printStackTrace();
            return null;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public User getUserByUsername(String username) {
        connection = connect();
        String query = "select * from public.user where username=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            return extractUser(resultSet);

        } catch (SQLException e) {
            System.out.println("Error executing SQL query");
            e.printStackTrace();
            return null;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<User> getUsersRegisteredForEvent(int event_id) {
        connection = connect();
        String query = "SELECT * " +
                "FROM public.user JOIN public.attendedEvents " +
                "WHERE public.attendedEvents.event = " + event_id +
                ";";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            List<User> userList = new LinkedList<>();

            while (resultSet.next()) {
                userList.add(extractUser(resultSet));
            }

            return userList;

        } catch (SQLException e) {
            System.out.println("Error executing SQL query");
            e.printStackTrace();
            return null;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    // USER
    public Object executeQueryUserList(String query, String username) {
        connection = connect();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            List<User> userList = new LinkedList<>();

            while (resultSet.next()) {
                userList.add(extractUser(resultSet));
            }

            return extractUser(resultSet);

        } catch (SQLException e) {
            System.out.println("Error executing SQL query");
            e.printStackTrace();
            return null;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }


    @SneakyThrows
    private User extractUser(ResultSet resultSet) {
        String username = resultSet.getString("username");
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");

        // треба якось переробити з цими налами
        return new User(username, name, email, password);
    }


    public Object executeQueryEventList(String query, String username) {

        Connection connection = connect();
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();
            List<Event> eventList = new LinkedList<>();

            while (resultSet.next()) {
                eventList.add(extractEvent(resultSet));
            }

            return eventList;

        } catch (SQLException e) {
            System.out.println("Error executing SQL query");
            e.printStackTrace();
            return null;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    // EVENT

    public Set<Event> executeQueryEventList() {
        // шось не так з табличкою event, бо до юзера все норм доступається
        String query = "SELECT * FROM public.event";
        connection = connect();
        Set<Event> eventSet = new HashSet<>();  // Use HashSet to represent a Set

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Event ev = extractEvent(resultSet);
                eventSet.add(ev);

            }

            System.out.println(eventSet.isEmpty());

            return eventSet;
        } catch (SQLException e) {
            System.out.println("Error executing SQL query: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                    System.out.println("Connection closed.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    public Object executeQueryEvent(String query, int event_id) {
        connection = connect();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, event_id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            return extractEvent(resultSet);

        }  catch (SQLException e) {
            System.out.println("Error executing SQL query");
            e.printStackTrace();
            return null;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public Object executeQueryEvent(String query) {

        Set<Event> events = new HashSet<>();

        connection = connect();
        try (PreparedStatement statement = connection.prepareStatement(query)) {


            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                events.add(extractEvent(resultSet));

            } else {
                System.out.println("No results found");
                return null;
            }

        } catch (SQLException e) {
            System.out.println("Error executing SQL query");
            e.printStackTrace();
            return null;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        System.out.println("our event set: " + events.isEmpty());

        return events;
    }

    @SneakyThrows
    private Event extractEvent(ResultSet resultSet) {
        int id_event = resultSet.getInt("id_event");
        String event_name = resultSet.getString("event_name");
        String description = resultSet.getString("description");
        String type = resultSet.getString("type");

        // Extracting java.sql.Date and converting it to LocalDate
        String dateSql = resultSet.getString("date");
        LocalDate date = LocalDate.parse(dateSql);

        // Extracting java.sql.Time and converting it to LocalTime
        String timeSql = resultSet.getString("time");
        LocalTime time = LocalTime.parse(timeSql);

        double longitude = resultSet.getDouble("longitude");
        double latitude = resultSet.getDouble("latitude");
        String creator = resultSet.getString("creator");
        User creatorUser = getUserByUsername(creator);
        List<User> attendants = getUsersRegisteredForEvent(id_event);

        // Create and return the Event object
        return new Event(id_event, event_name, type, description, date, time, creatorUser, attendants, latitude, longitude);
    }

    public int executeInsertAndGetGeneratedId(String query, Object... parameters) {
        connection = connect();
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < parameters.length; i++) {
                statement.setObject(i + 1, parameters[i]);
            }

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                System.out.println("Failed to insert event");
                return -1; // Or throw an exception
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    System.out.println("Failed to retrieve generated ID");
                    return -1; // Or throw an exception
                }
            }
        } catch (SQLException e) {
            System.out.println("Error executing SQL query");
            e.printStackTrace();
            return -1; // Or throw an exception
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}