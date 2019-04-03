package javaProject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainAppWindow extends SortingAlgorithm implements Initializable {

    @FXML
    private TableView<DatabaseTable> dataTable;
    @FXML
    private TableColumn<DatabaseTable, String> colItemCategory, colFirstName;
    @FXML
    private TableColumn<DatabaseTable, Integer> colItemName, colItemNumber, colItemPrice, colQuantity;
    @FXML
    private ComboBox<String> comboBx;

    private MyDatabase db = new MyDatabase();
    private Connection conn = db.connectDatabase();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBx.getItems().addAll("Bucket Sort (Price)", "Heap Sort (Quantity)", "Unsorted");
    }

    @FXML
    private void loadTableBucketSort() throws SQLException {
        Statement state = conn.createStatement();
        state.executeUpdate("DELETE FROM sortedItems");

        loadBucketSortData();
        ObservableList<DatabaseTable> obList = FXCollections.observableArrayList();
        obList.clear();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM sortedItems");
        while (rs.next()) {
            obList.add(new DatabaseTable(rs.getString("itemCategory"), rs.getString("itemName"),
                    rs.getInt("itemNumber"), rs.getInt("itemPrice"), rs.getInt("quantity"), rs.getString("firstName")));

            colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
            colItemCategory.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
            colItemNumber.setCellValueFactory(new PropertyValueFactory<>("itemNumber"));
            colItemPrice.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
            colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            dataTable.setItems(obList);
        }
    }

    @FXML
    private void loadTableHeapSort() throws SQLException {
        Statement state = conn.createStatement();
        state.executeUpdate("DELETE FROM sortedItems");

        loadHeapSortData();
        ObservableList<DatabaseTable> obList = FXCollections.observableArrayList();
        obList.clear();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM sortedItems");
        while (rs.next()) {
            obList.add(new DatabaseTable(rs.getString("itemCategory"), rs.getString("itemName"),
                    rs.getInt("itemNumber"), rs.getInt("itemPrice"), rs.getInt("quantity"), rs.getString("firstName")));

            colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
            colItemCategory.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
            colItemNumber.setCellValueFactory(new PropertyValueFactory<>("itemNumber"));
            colItemPrice.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
            colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            dataTable.setItems(obList);
        }
    }

    @FXML
    public void loadTableUnsorted() throws SQLException {
        MyDatabase db = new MyDatabase();
        ObservableList<DatabaseTable> obList = FXCollections.observableArrayList();
        obList.clear();
        Connection conn = db.connectDatabase();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM itemsToSell");
        while (rs.next()) {
            obList.add(new DatabaseTable(rs.getString("itemCategory"), rs.getString("itemName"),
                    rs.getInt("itemNumber"), rs.getInt("itemPrice"), rs.getInt("quantity"), rs.getString("firstName")));

            colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
            colItemCategory.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
            colItemNumber.setCellValueFactory(new PropertyValueFactory<>("itemNumber"));
            colItemPrice.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
            colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            dataTable.setItems(obList);
        }
    }

    @FXML
    public void sortingList() throws SQLException {
        String choice = comboBx.getValue();
        if(choice.contains("Unsorted")) {
            loadTableUnsorted();
        } else if(choice.contains("Bucket")) {
            loadTableBucketSort();
        } else if(choice.contains("Heap")){
            loadTableHeapSort();
        }
    }

    @FXML
    public void sellItemButton() throws IOException {
        Stage sellingItemFormWindow = new Stage();
        sellingItemFormWindow.initModality(Modality.APPLICATION_MODAL);
        sellingItemFormWindow.setTitle("Add New Product");
        sellingItemFormWindow.setScene(new Scene(FXMLLoader.load(getClass().getResource("SellingItemFormFXML.fxml"))));
        sellingItemFormWindow.showAndWait();
    }
}
