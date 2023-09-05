/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flowershopmanagement;

import java.io.File;
import javafx.event.ActionEvent;
import java.net.URL;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author minul
 */
public class dashboardController implements Initializable {

    @FXML
    private AnchorPane main_from;

    @FXML
    private Button close;

    @FXML
    private Button minimize;

    @FXML
    private AnchorPane availableFlowers_from;

    @FXML
    private Label username;

    @FXML
    private Button home_btn;

    @FXML
    private Button available_btn;

    @FXML
    private Button purchase_btn;

    @FXML
    private Label logout;

    @FXML
    private AnchorPane home_from;

    @FXML
    private Label home_availableFlowers;

    @FXML
    private Label home_totalincome;

    @FXML
    private Label home_totalcustomers;

    @FXML
    private BarChart<?, ?> home_chart;

    @FXML
    private AnchorPane availableFlowers_mainfrom;

    @FXML
    private ImageView availableFlowers_imageView;

    @FXML
    private Button availableFlowers_imagebtn;

    @FXML
    private TextField availableFlowers_FlowerID;

    @FXML
    private TextField availableFlowers_FlowerName;

    @FXML
    private ComboBox<?> availableFlowers_Flower_status;

    @FXML
    private Button availableFlowers_addBtn;

    @FXML
    private Button availableFlowers_updateBtn;

    @FXML
    private Button availableFlowers_clearBtn;

    @FXML
    private Button availableFlowers_deleteBtn;

    @FXML
    private TextField availableFlowers_price;

    @FXML
    private TextField search;

    @FXML
    private TableView<flowerData> availableFlowers_tableView;

    @FXML
    private TableColumn<flowerData, Integer> availableFlowers_Col_FlowerID;

    @FXML
    private TableColumn<flowerData, String> availableFlowers_col_FlowerName;

    @FXML
    private TableColumn<flowerData, String> availableFlowers_Col_status;

    @FXML
    private TableColumn<flowerData, Double> availableFlowers_Col_Price;

    @FXML
    private AnchorPane purchase_Form;

    @FXML
    private ComboBox<?> purchase_flowerID;

    @FXML
    private ComboBox<?> purchase_flowerName;

    @FXML
    private Spinner<Integer> purchase_quantity;

    @FXML
    private Label purchase_total;

    @FXML
    private Button purchase_payBTN;

    @FXML
    private Button purchase_addCart;

    @FXML
    private TableView<customerData> purchase_tableView;

    @FXML
    private TableColumn<customerData, Integer> purchase_Col_flowerID;

    @FXML
    private TableColumn<customerData, String> purchase_Col_flowerName;

    @FXML
    private TableColumn<customerData, Integer> purchase_Col_Quantity;

    @FXML
    private TableColumn<customerData, Double> purchase_Col_Price;

    @FXML
    private Button logoutBtn;

    private double x = 0;
    private double y = 0;

    private Connection connect;
    private PreparedStatement pre;
    private Statement statement;
    private ResultSet resultSet;

    private Image image;

    public void homeAF() {

        String sql = "SELECT COUNT(id) FROM flowers WHERE status = 'Available'";

        connect = database.con();

        try {
            int countAF = 0;
            statement = connect.createStatement();
            resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                countAF = resultSet.getInt("COUNT(id)");

            }

            home_availableFlowers.setText(String.valueOf(countAF));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void homeTI() {

        String sql = "SELECT SUM(total) FROM customerinfo";
        connect = database.con();

        try {
            double countTI = 0;
            statement = connect.createStatement();
            resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                countTI = resultSet.getInt("SUM(total)");

            }

            home_totalincome.setText("Rs. " +String.valueOf(countTI));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void homeTC() {
        String sql = "SELECT COUNT(id) FROM customerinfo";
        connect = database.con();

        try {

            int countTC = 0;
            statement = connect.createStatement();
            resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                countTC = resultSet.getInt("COUNT(id)");

            }
            home_totalcustomers.setText(String.valueOf(countTC));

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    
    
    
    public void homeChart(){
    
    home_chart.getData().clear();
    
    String sql= "SELECT date , SUM(total) FROM customerinfo GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 7";
    
    connect=database.con();
    
    
        try {
            XYChart.Series chart =  new XYChart.Series();
            
            pre = connect.prepareStatement(sql);
            resultSet = pre.executeQuery();
            
            while(resultSet.next()){
            chart.getData().add(new XYChart.Data(resultSet.getString(1),resultSet.getInt(2)));
            
            }
            home_chart.getData().add(chart);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    
    }
    
    public void availableFlowersAdd() {

        String sql = "INSERT INTO flowers (flowerId,name , status,price,image,date)" + "VALUES(?,?,?,?,?,?)";
        connect = database.con();

        try {
            Alert alert;

            if (availableFlowers_FlowerID.getText().isEmpty() || availableFlowers_FlowerName.getText().isEmpty()
                    || availableFlowers_Flower_status.getSelectionModel().isEmpty() || availableFlowers_price.getText().isEmpty()
                    || getData.path == null || getData.path == "") {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();

            } else {

                String checkData = "SELECT flowerId FROM flowers WHERE flowerId = '" + availableFlowers_FlowerID.getText() + "' ";

                statement = connect.createStatement();
                resultSet = statement.executeQuery(checkData);

                if (resultSet.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Flower ID:" + availableFlowers_FlowerID.getText() + "was already exist!");
                    alert.showAndWait();

                } else {

                    pre = connect.prepareStatement(sql);
                    pre.setString(1, availableFlowers_FlowerID.getText());
                    pre.setString(2, availableFlowers_FlowerName.getText());
                    pre.setString(3, (String) availableFlowers_Flower_status.getSelectionModel().getSelectedItem());
                    pre.setString(4, availableFlowers_price.getText());

                    String uri = getData.path;
                    uri = uri.replace("\\", "\\\\");
                    pre.setString(5, uri);

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    pre.setString(6, String.valueOf(sqlDate));

                    pre.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added");
                    alert.showAndWait();

                    availableFlowersShowList();

                    avialableFlowerClear();

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void availableFlowerUpdate() {

        String uri = getData.path;

        if (!(uri == null || uri == "")) {

            uri = uri.replace("\\", "\\\\");
        }

        String sql = "UPDATE flowers SET name= "
                + "'" + availableFlowers_FlowerName.getText() + "' ,"
                + "status='" + availableFlowers_Flower_status.getSelectionModel().getSelectedItem() + "' ,"
                + " price= '" + availableFlowers_price.getText() + "',"
                + "image='" + uri + "' WHERE flowerId ='" + availableFlowers_FlowerID.getText() + "'";

        connect = database.con();

        try {
            Alert alert;

            if (availableFlowers_FlowerID.getText().isEmpty() || availableFlowers_FlowerName.getText().isEmpty()
                    || availableFlowers_Flower_status.getSelectionModel().isEmpty() || availableFlowers_price.getText().isEmpty()
                    || uri == null || uri == "") {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();

            } else {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Conformation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Flower ID:" + availableFlowers_FlowerID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated");
                    alert.showAndWait();

                    availableFlowersShowList();

                    avialableFlowerClear();

                }

                statement = connect.createStatement();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    String listStatus[] = {"Available", "Not Available"};

    public void availableFlowerStatus() {

        List<String> listS = new ArrayList<>();

        for (String data : listStatus) {

            listS.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listS);
        availableFlowers_Flower_status.setItems(listData);

    }

    public void availableFlowerDelete() {

        String sql = "DELETE FROM flowers WHERE flowerId = '" + availableFlowers_FlowerID.getText() + "' ";

        connect = database.con();

        try {
            Alert alert;

            if (availableFlowers_FlowerID.getText().isEmpty() || availableFlowers_FlowerName.getText().isEmpty()
                    || availableFlowers_Flower_status.getSelectionModel().isEmpty() || availableFlowers_price.getText().isEmpty()
                    || getData.path == null || getData.path == "") {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();

            } else {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Conformation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to Delete Flower ID:" + availableFlowers_FlowerID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted");
                    alert.showAndWait();

                    availableFlowersShowList();

                    avialableFlowerClear();

                }

                statement = connect.createStatement();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void avialableFlowerClear() {
        availableFlowers_FlowerID.setText("");
        availableFlowers_FlowerName.setText("");
        availableFlowers_Flower_status.getSelectionModel().clearSelection();
        availableFlowers_price.setText("");
        getData.path = "";

        availableFlowers_imageView.setImage(null);

    }

    public void availableFlowersInsertImage() {

        FileChooser open = new FileChooser();
        open.setTitle("Open File Image");
        open.getExtensionFilters().add(new ExtensionFilter("Image File", "*jpg", "*png"));

        File file = open.showOpenDialog(main_from.getScene().getWindow());

        if (file != null) {

            getData.path = file.getAbsolutePath();

            image = new Image(file.toURI().toString(), 116, 139, false, true);
            availableFlowers_imageView.setImage(image);

        }

    }

    public ObservableList<flowerData> availableFlowersListData() {
        ObservableList<flowerData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM flowers";

        connect = database.con();
        try {
            pre = connect.prepareStatement(sql);

            resultSet = pre.executeQuery();

            flowerData flower;

            while (resultSet.next()) {

                flower = new flowerData(resultSet.getInt("flowerId"),
                        resultSet.getString("name"),
                        resultSet.getString("status"),
                        resultSet.getDouble("price"),
                        resultSet.getDate("date"),
                        resultSet.getString("image")
                );

                listData.add(flower);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<flowerData> availableFlowersList;

    public void availableFlowersShowList() {

        availableFlowersList = availableFlowersListData();

        availableFlowers_Col_FlowerID.setCellValueFactory(new PropertyValueFactory<>("flowerId"));
        availableFlowers_col_FlowerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        availableFlowers_Col_Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        availableFlowers_Col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        availableFlowers_tableView.setItems(availableFlowersList);

    }

    public void availableFlowerSearch() {

        FilteredList<flowerData> filter = new FilteredList<>(availableFlowersList, e -> true);
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filter.setPredicate(predicateFlowerData -> {
                if (newValue.isEmpty() || newValue == null) {

                    return true;
                }
                String searchKey = newValue.toLowerCase();

                if (predicateFlowerData.getFlowerId().toString().contains(searchKey)) {
                    return true;
                } else if (predicateFlowerData.getName().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateFlowerData.getStatus().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateFlowerData.getPrice().toString().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<flowerData> sortlist = new SortedList<>(filter);

        sortlist.comparatorProperty().bind(availableFlowers_tableView.comparatorProperty());

        availableFlowers_tableView.setItems(sortlist);

    }

    public void availableFlowerSelect() {

        flowerData flower = availableFlowers_tableView.getSelectionModel().getSelectedItem();
        int num = availableFlowers_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        availableFlowers_FlowerID.setText(String.valueOf(flower.getFlowerId()));
        availableFlowers_FlowerName.setText(flower.getName());
        availableFlowers_price.setText(String.valueOf(flower.getPrice()));

        getData.path = flower.getImage();

        String uri = "file:" + flower.getImage();
        image = new Image(uri, 116, 139, false, true);
        availableFlowers_imageView.setImage(image);

    }

    public void purchaseAddtoCart() {
        purchaseCustomerId();

        String sql = "INSERT INTO customer(customerId, flowerId, name, quantity, price, date)" + "VALUES (?,?,?,?,?,?)";

        connect = database.con();

        try {

            Alert alert;

            if (purchase_flowerID.getSelectionModel().getSelectedItem() == null || purchase_flowerName.getSelectionModel().getSelectedItem() == null || qty == 0) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please choose the product first");
                alert.showAndWait();

            } else {
                double totalPrice = 0;
                double priceData = 0;
                String checkPrice = "SELECT name,price FROM flowers WHERE name='" + purchase_flowerName.getSelectionModel().getSelectedItem() + "'";

                statement = connect.createStatement();
                resultSet = statement.executeQuery(checkPrice);
                if (resultSet.next()) {

                    priceData = resultSet.getDouble("price");
                }

                pre = connect.prepareStatement(sql);
                pre.setString(1, String.valueOf(customerId));
                pre.setInt(2, (Integer) purchase_flowerID.getSelectionModel().getSelectedItem());
                pre.setString(3, (String) purchase_flowerName.getSelectionModel().getSelectedItem());
                pre.setString(4, String.valueOf(qty));

                totalPrice = (priceData * qty);

                pre.setString(5, String.valueOf(totalPrice));

                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                pre.setString(6, String.valueOf(sqlDate));
                pre.executeUpdate();
                purchaseShowListsData();
                purchaseDisplayTotal();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void purchasePay() {

        String sql = "INSERT INTO customerinfo (customerid,total,date) VALUES (?,?,?)";

        connect = database.con();

        try {

            Alert alert;

            if (totalp == 0) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Something Wrong :3");
                alert.showAndWait();
            } else {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Conformation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {

                    pre = connect.prepareStatement(sql);
                    pre.setString(1, String.valueOf(customerId));
                    pre.setString(2, String.valueOf(totalp));

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    pre.setString(3, String.valueOf(sqlDate));
                    pre.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Thanks for purchasing");
                    alert.showAndWait();

                    totalp = 0;

                }

            }

//            resultSet = pre.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private double totalp = 0;

    public void purchaseDisplayTotal() {

        purchaseCustomerId();
        String sql = "SELECT SUM(price) FROM customer WHERE customerId='" + customerId + "'";
        connect = database.con();

        try {
            pre = connect.prepareStatement(sql);
            resultSet = pre.executeQuery();

            if (resultSet.next()) {
                totalp = resultSet.getDouble("SUM(price)");
            }
            purchase_total.setText("Rs. " + String.valueOf(totalp));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void purchaseFlowerId() {

        String sql = "SELECT status, flowerId FROM flowers WHERE status= 'Available'";

        connect = database.con();
        try {

            ObservableList listData = FXCollections.observableArrayList();

            pre = connect.prepareStatement(sql);
            resultSet = pre.executeQuery();

            while (resultSet.next()) {
                listData.add(resultSet.getInt("flowerId"));
            }
            purchase_flowerID.setItems(listData);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void purchaseFlowerName() {

        String sql = "SELECT flowerId , name FROM flowers WHERE flowerId='" + purchase_flowerID.getSelectionModel().getSelectedItem() + "'";

        connect = database.con();

        try {
            pre = connect.prepareStatement(sql);
            resultSet = pre.executeQuery();
            ObservableList listData = FXCollections.observableArrayList();

            while (resultSet.next()) {
                listData.add(resultSet.getString("name"));
            }
            purchase_flowerName.setItems(listData);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private SpinnerValueFactory<Integer> spinner;

    public void purchaseSpinner() {

        spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0);

        purchase_quantity.setValueFactory(spinner);
    }

    private int qty;

    public void purchaseQuantity() {

        qty = purchase_quantity.getValue();
    }

    public ObservableList<customerData> purchaseListData() {
        purchaseCustomerId();
        ObservableList<customerData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM customer WHERE customerId= '" + customerId + "' ";
        connect = database.con();

        try {
            pre = connect.prepareStatement(sql);
            resultSet = pre.executeQuery();
            customerData customer;

            while (resultSet.next()) {
                customer = new customerData(resultSet.getInt("customerId"),
                        resultSet.getInt("flowerId"),
                        resultSet.getString("name"),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("price"),
                        resultSet.getDate("date")
                );

                listData.add(customer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<customerData> purchaseList;

    public void purchaseShowListsData() {
        purchaseList = purchaseListData();

        purchase_Col_flowerID.setCellValueFactory(new PropertyValueFactory<>("flowerId"));
        purchase_Col_flowerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        purchase_Col_Quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        purchase_Col_Price.setCellValueFactory(new PropertyValueFactory<>("price"));

        purchase_tableView.setItems(purchaseList);
    }

    private int customerId;

    public void purchaseCustomerId() {
        String sql = "SELECT MAX(customerId) FROM customer";

        connect = database.con();
        try {
            pre = connect.prepareStatement(sql);
            resultSet = pre.executeQuery();

            if (resultSet.next()) {
                customerId = resultSet.getInt("MAX(customerId)");

            }

            int countData = 0;

            String checkInfo = "SELECT MAX(customerId) FROM customerinfo ";

            pre = connect.prepareStatement(checkInfo);
            resultSet = pre.executeQuery();

            if (resultSet.next()) {
                customerId = resultSet.getInt("MAX(customerId)");

            }

            if (customerId == 0) {

                customerId += 1;
            } else if (customerId == countData) {
                customerId = countData++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void displayUsername() {

        String user = getData.username;
        username.setText(user.substring(0, 1).toUpperCase() + user.substring(1));

    }

    public void Switchform(ActionEvent event) {

        if (event.getSource() == home_btn) {

            home_from.setVisible(true);
            availableFlowers_mainfrom.setVisible(false);
            purchase_Form.setVisible(false);

            home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #bb1a3a, #722327)");
            available_btn.setStyle("-fx-background-color:transparent");
            purchase_btn.setStyle("-fx-background-color:transparent");

            homeAF();
            homeTI();
            homeTC();
            homeChart();

        } else if (event.getSource() == available_btn) {

            home_from.setVisible(false);
            availableFlowers_mainfrom.setVisible(true);
            purchase_Form.setVisible(false);

            available_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #bb1a3a, #722327)");
            home_btn.setStyle("-fx-background-color:transparent");
            purchase_btn.setStyle("-fx-background-color:transparent");

            availableFlowersShowList();
            availableFlowerStatus();
            availableFlowerSearch();

        } else if (event.getSource() == purchase_btn) {

            home_from.setVisible(false);
            availableFlowers_mainfrom.setVisible(false);
            purchase_Form.setVisible(true);

            purchase_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #bb1a3a, #722327)");
            available_btn.setStyle("-fx-background-color:transparent");
            home_btn.setStyle("-fx-background-color:transparent");
            purchaseShowListsData();
            purchaseFlowerId();
            purchaseFlowerName();
            purchaseSpinner();
            purchaseDisplayTotal();
        }

    }

    public void logout() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("conformation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                logoutBtn.getScene().getWindow().hide();

                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getSceneX() - x);
                    stage.setY(event.getSceneY() - y);
                });

                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) main_from.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayUsername();

        homeAF();
        homeTI();
        homeTC();
        homeChart();

        availableFlowersShowList();
        availableFlowerStatus();
        purchaseShowListsData();
        purchaseFlowerId();
        purchaseFlowerName();
        purchaseSpinner();
        purchaseDisplayTotal();
    }

}
