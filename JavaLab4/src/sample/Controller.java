package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import sample.bank.Account;
import sample.bank.Holder;
import sample.bank.Record;
import sample.bank.Serializer;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    Serializer serializer = Serializer.getInstance();

    ArrayList<Holder> clients = new ArrayList<Holder>();
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

    public void depositAmount(ActionEvent event) throws IOException {
        listView.getItems().clear();

        double amount = Double.parseDouble(depositAmount.getText());
        depositAmount.clear();

        int index = Integer.parseInt(depositToId.getText());
        depositToId.clear();
        Account account = clients.get(index).getAccount();
        account.depositAmount(amount);

        double money = Double.parseDouble(moneyCount.getText());
        money += amount;
        moneyCount.setText(money + "");

        ArrayList<Record> records = account.getHistory().getRecords();

        historyLabel1.setText(clients.get(index).getFullName());

        for (Record record : records) {
            listView.getItems().add(record.toString());
        }

        serializer.saveSerialized("clients.out", clients);
    }

    public void withdrawAmount(ActionEvent event) throws IOException {
        listView.getItems().clear();

        double amount = Double.parseDouble(depositAmount.getText());
        depositAmount.clear();

        int index = Integer.parseInt(depositToId.getText());
        depositToId.clear();
        Account account = clients.get(index).getAccount();
        boolean withdrawn = account.withdrawAmount(amount);

        if (withdrawn){
            double money = Double.parseDouble(moneyCount.getText());
            money -= amount;
            moneyCount.setText(money + "");
        }

        historyLabel1.setText(clients.get(index).getFullName());

        ArrayList<Record> records = account.getHistory().getRecords();

        for (Record record : records) {
            listView.getItems().add(record.toString());
        }

        serializer.saveSerialized("clients.out", clients);
    }

    public void transferAmount(ActionEvent event) throws IOException {
        listView.getItems().clear();
        listView2.getItems().clear();

        double amount = Double.parseDouble(transferAmount.getText());
        transferAmount.clear();

        int index = Integer.parseInt(transferFromId.getText());
        transferFromId.clear();
        Account accountFrom = clients.get(index).getAccount();

        historyLabel1.setText(clients.get(index).getFullName());

        index = Integer.parseInt(transferToId.getText());
        transferToId.clear();
        Account accountTo = clients.get(index).getAccount();

        historyLabel2.setText(clients.get(index).getFullName());

        accountFrom.transferAmount(accountTo, amount);

        ArrayList<Record> records = accountFrom.getHistory().getRecords();
        for (Record record : records) {
            listView.getItems().add(record.toString());
        }

        records = accountTo.getHistory().getRecords();
        for (Record record : records) {
            listView2.getItems().add(record.toString());
        }

        serializer.saveSerialized("clients.out", clients);
    }

    public void registerClient(ActionEvent event) throws IOException {
        clientView.getItems().clear();

        String fullName = clientFullName.getText();
        Holder holder = new Holder(fullName);

        clients.add(holder);
        holder.setHolderId(clients.size());

        //Сериализация
        serializer.saveSerialized("clients.out", clients);

        for (Holder client : clients) {
            clientView.getItems().add((client.getHolderId() - 1) + " | " + client.getFullName());
        }

        int count = Integer.parseInt(accountsCount.getText());
        count++;
        accountsCount.setText(count + "");

        clientFullName.clear();
    }

    public void checkBalance(ActionEvent event){
        int id = Integer.parseInt(checkBalanceId.getText());
        checkBalanceId.clear();

        double amount = clients.get(id).getAccount().getAmount();

        checkBalanceLabel.setText(amount + "");
    }

    public void openAccount(ActionEvent event) throws IOException {
        int index = Integer.parseInt(accountCloseId.getText());
        accountCloseId.clear();

        Holder holder = clients.get(index);

        if (holder.getAccount() == null){
            holder.openAccount();
            serializer.saveSerialized("clients.out", clients);
        }
    }

    public void closeAccount(ActionEvent event) throws IOException {
        int index = Integer.parseInt(accountCloseId.getText());
        accountCloseId.clear();

        Holder holder = clients.get(index);

        double amount = holder.getAccount().getAmount();
        double money = Double.parseDouble(moneyCount.getText());
        money -= amount;
        moneyCount.setText(money + "");

        holder.closeAccount();
        serializer.saveSerialized("clients.out", clients);
    }

    public void showHistory(ActionEvent event){
        listView.getItems().clear();

        int index = Integer.parseInt(showHistoryId.getText());
        showHistoryId.clear();

        ArrayList<Record> records = clients.get(index).getAccount().getHistory().getRecords();

        for (Record record : records){
            listView.getItems().add(record.toString());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clients = (ArrayList<Holder>) serializer.loadSerialized("clients.out");
        if (clients == null){
            clients = new ArrayList<Holder>();
        }
        int count = clients.size();
        accountsCount.setText(count + "");

        double money = 0;
        for (Holder client : clients) {
            clientView.getItems().add((client.getHolderId() - 1) + " | " + client.getFullName());
            money += client.getAccount().getAmount();
        }

        moneyCount.setText(money + "");
    }
}
