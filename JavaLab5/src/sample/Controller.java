package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import sample.bank.*;
import sample.bank.Record;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;



public class Controller implements Initializable {

    ArrayList<Holder> clients = new ArrayList<Holder>();
    HashMap<Long, Holder> holderMap = new HashMap<Long, Holder>();

    public ListView<String> clientView;

    @FXML private ListView<String> listView;
    @FXML public ListView<String> listView2;

    @FXML private TextField depositAmount;
    @FXML private TextField depositToId;

    @FXML private TextField transferFromId;
    @FXML private TextField transferToId;
    @FXML private TextField transferAmount;

    @FXML private TextField clientFullName;

    @FXML private Label historyLabel1;
    @FXML private Label historyLabel2;

    @FXML private TextField checkBalanceId;
    @FXML private Label checkBalanceLabel;

    @FXML private Label accountsCount;
    @FXML private Label moneyCount;

    @FXML private TextField accountCloseId;
    @FXML private TextField showHistoryId;

    public void depositAmount(ActionEvent event) {

        listView.getItems().clear();
        listView2.getItems().clear();

        double amount = Double.parseDouble(depositAmount.getText());
        depositAmount.clear();

        long i = Integer.parseInt(depositToId.getText());
        depositToId.clear();

        Account account = holderMap.get(i).getAccount();
        account.depositAmount(amount);

        SqlHandler.updateAccountInDatabase(account);

        double totalMoneyAmount = Double.parseDouble(moneyCount.getText());
        totalMoneyAmount += amount;
        moneyCount.setText(totalMoneyAmount + "");

        historyLabel1.setText(holderMap.get(i).getFullName());

        ArrayList<Record> records = account.getHistory().getRecords();
        for (Record record : records) {

            listView.getItems().add(record.toString());

        }

    }

    public void withdrawAmount(ActionEvent event) {

        listView.getItems().clear();
        listView2.getItems().clear();

        double amount = Double.parseDouble(depositAmount.getText());
        depositAmount.clear();

        long i = Integer.parseInt(depositToId.getText());
        depositToId.clear();

        Account account = holderMap.get(i).getAccount();
        boolean withdrawn = account.withdrawAmount(amount);

        if (withdrawn){

            SqlHandler.updateAccountInDatabase(account);

            double totalMoneyAmount = Double.parseDouble(moneyCount.getText());
            totalMoneyAmount -= amount;
            moneyCount.setText(totalMoneyAmount + "");

        }

        historyLabel1.setText(holderMap.get(i).getFullName());

        ArrayList<Record> records = account.getHistory().getRecords();
        for (Record record : records) {

            listView.getItems().add(record.toString());

        }

    }

    public void transferAmount(ActionEvent event) {

        listView.getItems().clear();
        listView2.getItems().clear();

        double amount = Double.parseDouble(transferAmount.getText());
        transferAmount.clear();

        long i = Integer.parseInt(transferFromId.getText());
        transferFromId.clear();
        Account accountFrom = holderMap.get(i).getAccount();
        historyLabel1.setText(holderMap.get(i).getFullName());

        i = Integer.parseInt(transferToId.getText());
        transferToId.clear();
        Account accountTo = holderMap.get(i).getAccount();
        historyLabel2.setText(holderMap.get(i).getFullName());

        accountFrom.transferAmount(accountTo, amount);

        SqlHandler.updateAccountsAfterTransfer(accountFrom, accountTo);

        ArrayList<Record> records = accountFrom.getHistory().getRecords();
        for (Record record : records) {

            listView.getItems().add(record.toString());

        }

        records = accountTo.getHistory().getRecords();
        for (Record record : records) {

            listView2.getItems().add(record.toString());

        }

    }

    public void registerClient(ActionEvent event) {

        clientView.getItems().clear();

        String fullName = clientFullName.getText();

        Holder holder = SqlHandler.addClientToDatabase(fullName);

        holderMap.put(holder.getHolderId(), holder);
        ArrayList<Holder> clients = new ArrayList<Holder>(holderMap.values());

        for (Holder client : clients) {

            clientView.getItems().add((client.getHolderId()) + " | " + client.getFullName());

        }

        int count = Integer.parseInt(accountsCount.getText());
        count++;
        accountsCount.setText(count + "");

        clientFullName.clear();
    }

    public void checkBalance(ActionEvent event) {

        long i = Long.parseLong(checkBalanceId.getText());
        checkBalanceId.clear();

        double amount = holderMap.get(i).getAccount().getAmount();
        checkBalanceLabel.setText(amount + "");

    }

    public void openAccount(ActionEvent event) {

        long i = Integer.parseInt(accountCloseId.getText());
        accountCloseId.clear();

        Holder holder = holderMap.get(i);

        if (holder.getAccount() == null){

            Account account = SqlHandler.openAccountInDatabase(holder);
            holder.setAccount(account);

        }

    }

    public void closeAccount(ActionEvent event) {

        long i = Integer.parseInt(accountCloseId.getText());
        accountCloseId.clear();

        Holder holder = holderMap.get(i);

        double amount = holder.getAccount().getAmount();
        double totalMoneyAmount = Double.parseDouble(moneyCount.getText());
        totalMoneyAmount -= amount;
        moneyCount.setText(totalMoneyAmount + "");

        SqlHandler.closeAccountInDatabase(holder);
        holder.closeAccount();

    }

    public void showHistory(ActionEvent event) {

        listView.getItems().clear();
        listView2.getItems().clear();

        long i = Integer.parseInt(showHistoryId.getText());
        showHistoryId.clear();

        ArrayList<Record> records = holderMap.get(i).getAccount().getHistory().getRecords();

        for (Record record : records){

            listView.getItems().add(record.toString());

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        clients = SqlHandler.loadHolders();

        for (Holder client : clients){

            holderMap.put(client.getHolderId(), client);

        }

        int count = clients.size();
        accountsCount.setText(count + "");

        double totalMoneyAmount = 0;
        for (Holder client : clients) {

            clientView.getItems().add((client.getHolderId()) + " | " + client.getFullName());
            if (client.getAccount() != null){

                totalMoneyAmount += client.getAccount().getAmount();

            }

        }

        moneyCount.setText(totalMoneyAmount + "");

    }
}
