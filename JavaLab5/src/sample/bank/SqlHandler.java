package sample.bank;

import java.sql.*;
import java.util.ArrayList;



public class SqlHandler {

    private static final String URL = "jdbc:mysql://localhost:3306/bank";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Holder addClientToDatabase(String fullName){

        Holder holder = new Holder(fullName);
        Account account = new Account(0);

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement(
                     ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY))
        {

            String query = "INSERT INTO holders (full_name) VALUES (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)){

                preparedStatement.setString(1, fullName);
                preparedStatement.executeUpdate();

            }

            long holderId;
            try (ResultSet result = statement.executeQuery("SELECT holder_id FROM holders")) {

                result.last();
                holderId = result.getLong("holder_id");

            }
            holder.setHolderId(holderId);

            query = "INSERT INTO accounts (amount, holder_id) VALUES (0, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)){

                preparedStatement.setLong(1, holderId);
                preparedStatement.executeUpdate();

            }

            long accountId;
            try (ResultSet result = statement.executeQuery("SELECT account_id FROM accounts")){

                result.last();
                accountId = result.getLong("account_id");

            }
            account.setAccountNumber(accountId);

            holder.setAccount(account);
        }
        catch (SQLException exception) {

            exception.printStackTrace();

        }

        return holder;

    }

    public static void updateAccountInDatabase(Account account) {

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);){

            updateAmount(connection, account);
            updateRecords(connection, account);

        }
        catch (SQLException exception) {

            exception.printStackTrace();

        }

    }

    public static void updateAccountsAfterTransfer(Account accountFrom, Account accountTo){

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            updateAmount(connection, accountFrom);
            updateRecords(connection, accountFrom);

            updateAmount(connection, accountTo);
            updateRecords(connection, accountTo);

        }
        catch (SQLException exception) {

            exception.printStackTrace();

        }
    }

    public static Account openAccountInDatabase(Holder holder) {

        long accountNumber = 0;
        long holderId = holder.getHolderId();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)){

            String statement = "INSERT INTO accounts (amount, holder_id) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement)){

                preparedStatement.setDouble(1, 0);
                preparedStatement.setLong(2, holderId);
                preparedStatement.executeUpdate();

            }

            statement = "SELECT account_id FROM accounts WHERE holder_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement)){

                preparedStatement.setLong(1, holderId);

                try (ResultSet result = preparedStatement.executeQuery()){

                    result.next();
                    accountNumber = result.getLong("account_id");

                }

            }
        }
        catch (SQLException exception) {

            exception.printStackTrace();

        }

        return new Account(accountNumber, 0, new AccountHistory());

    }

    public static void closeAccountInDatabase(Holder holder){

        long holderId = holder.getHolderId();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)){

            String statement = "DELETE FROM accounts WHERE holder_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(statement)){

                preparedStatement.setLong(1, holderId);
                preparedStatement.executeUpdate();

            }

        }
        catch (SQLException exception) {

            exception.printStackTrace();

        }

    }

    public static ArrayList<Holder> loadHolders(){

        ArrayList<Holder> holders = new ArrayList<Holder>();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            int holderId;
            String fullName;

            try (ResultSet result = statement.executeQuery("select holder_id, full_name from holders")){

                while (result.next()) {

                    holderId = result.getInt("holder_id");
                    fullName = result.getString("full_name");

                    double amount = 0;
                    long accountId = 0;

                    String query = "select amount, account_id from accounts where holder_id = ?";

                    try (PreparedStatement preparedStatement = connection.prepareStatement(query);) {

                        preparedStatement.setLong(1, holderId);

                        try (ResultSet resultAmount = preparedStatement.executeQuery()){

                            while (resultAmount.next()){

                                amount = resultAmount.getDouble("amount");
                                accountId = resultAmount.getLong("account_id");

                            }

                        }

                    }

                    Holder holder = new Holder(holderId, fullName);

                    if (accountId != 0) {

                        ArrayList<Record> records;
                        records = loadRecords(connection, accountId);

                        AccountHistory accountHistory = new AccountHistory(records);
                        Account account = new Account(accountId, amount, accountHistory);
                        holder.setAccount(account);

                    }

                    holders.add(holder);

                }
            }
        }
        catch (SQLException throwables) {

            throwables.printStackTrace();

        }

        return holders;
    }

    private static void updateAmount(Connection connection, Account account){

        String statement = "UPDATE accounts SET amount = ? WHERE account_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {

            preparedStatement.setDouble(1, account.getAmount());
            preparedStatement.setLong(2, account.getAccountNumber());
            preparedStatement.executeUpdate();

        }
        catch (SQLException exception) {

            exception.printStackTrace();

        }
    }

    private static void updateRecords(Connection connection, Account account){

        String statement = "INSERT INTO records (action_type, before_action, after_action, action_date, account_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {

            ArrayList<Record> records = account.getHistory().getRecords();
            Record newRecord = records.get(records.size() - 1);

            preparedStatement.setString(1, newRecord.actionType.name());
            preparedStatement.setDouble(2, newRecord.beforeAction);
            preparedStatement.setDouble(3, newRecord.afterAction);

            preparedStatement.setDate(4, new Date(newRecord.actionDate.getTime()));
            preparedStatement.setLong(5, account.getAccountNumber());
            preparedStatement.executeUpdate();

        }
        catch (SQLException exception) {

            exception.printStackTrace();

        }
    }

    private static ArrayList<Record> loadRecords(Connection connection, long accountId){

        ActionType actionType;
        double beforeAction;
        double afterAction;
        Date actionDate;
        ArrayList<Record> records = new ArrayList<Record>();

        String statement = "select account_id, action_type, before_action, after_action, action_date from records where account_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {

            preparedStatement.setLong(1, accountId);

            try (ResultSet result = preparedStatement.executeQuery()){

                while (result.next()){

                    actionType = ActionType.valueOf(result.getString("action_type"));
                    beforeAction = result.getDouble("before_action");
                    afterAction = result.getDouble("after_action");
                    actionDate = result.getDate("action_date");

                    Record record = new Record(actionType, beforeAction, afterAction, actionDate);
                    records.add(record);

                }
            }
        }
        catch (SQLException throwables) {

            throwables.printStackTrace();

        }

        return records;

    }
}
