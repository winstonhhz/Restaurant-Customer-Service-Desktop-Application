    package com.example.restaurantcustomerservice.controller;


import com.example.restaurantcustomerservice.model.SessionHolder;
import com.example.restaurantcustomerservice.model.database;
    import java.net.URL;
    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.util.*;

    import javafx.animation.TranslateTransition;
    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.fxml.Initializable;
    import javafx.scene.Node;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.scene.control.*;
    import javafx.scene.layout.AnchorPane;
    import javafx.stage.Stage;
    import javafx.util.Duration;

    public class HelloController implements Initializable {

        @FXML
        private Button btnCustomer;

        @FXML
        private Button btnAdmin;

        @FXML
        private Button btnStaff;

        @FXML
        private AnchorPane si_loginForm;

        @FXML
        private TextField si_username;

        @FXML
        private PasswordField si_password;

        @FXML
        private Button si_loginBtn;

        @FXML
        private Hyperlink si_forgotPass;

        @FXML
        private AnchorPane su_signupForm;

        @FXML
        private TextField su_username;

        @FXML
        private PasswordField su_password;

        @FXML
        private ComboBox<String> su_question;

        @FXML
        private TextField su_answer;

        @FXML
        private Button su_signupBtn;

        @FXML
        private TextField fp_username;

        @FXML
        private AnchorPane fp_questionForm;

        @FXML
        private Button fp_proceedBtn;

        @FXML
        private ComboBox<String> fp_question;

        @FXML
        private TextField fp_answer;

        @FXML
        private Button fp_back;

        @FXML
        private AnchorPane np_newPassForm;

        @FXML
        private PasswordField np_newPassword;

        @FXML
        private PasswordField np_confirmPassword;

        @FXML
        private Button np_changePassBtn;

        @FXML
        private Button np_back;

        @FXML
        private AnchorPane side_form;

        @FXML
        private Button side_CreateBtn;

        @FXML
        private Button side_alreadyHave;

        @FXML
        private AnchorPane roleSelectPane;


        private final String ADMIN_USERNAME = "admin";
        private final String ADMIN_PASSWORD = "admin123";
        private final String STAFF_USERNAME = "staff";
        private final String STAFF_PASSWORD = "staff123";

        private Alert alert;

        // Security questions list
        private final String[] questionList = {
                "What is your favorite Color?",
                "What is your favorite food?",
                "What is your birth date?"
        };
        private EventObject event;


        @FXML
        public void selectCustomer(ActionEvent event) {
            roleSelectPane.setVisible(false);
            su_signupForm.setVisible(true);
            si_loginForm.setVisible(true);
            side_form.setVisible(true);

            side_alreadyHave.setVisible(true);
            side_CreateBtn.setVisible(false);

            loadSecurityQuestions();

            // Animate side form sliding to right
            TranslateTransition slider = new TranslateTransition(Duration.seconds(0.5), side_form);
            slider.setToX(300);
            slider.play();
        }

        @FXML
        public void selectAdmin(ActionEvent event) {
            roleSelectPane.setVisible(false);
            su_signupForm.setVisible(false);
            si_loginForm.setVisible(true);
            side_form.setVisible(true);

            side_alreadyHave.setVisible(false);
            side_CreateBtn.setVisible(true);

            // Reset side_form position in case
            side_form.setTranslateX(0);
        }

        @FXML
        public void selectStaff(ActionEvent event) {
            roleSelectPane.setVisible(false);
            su_signupForm.setVisible(false);
            si_loginForm.setVisible(true);
            side_form.setVisible(true);

            side_alreadyHave.setVisible(false);
            side_CreateBtn.setVisible(true);

            side_form.setTranslateX(0);
        }



        @FXML
        public void loginBtn() {
            String username = si_username.getText().trim();
            String password = si_password.getText();

            if (username.isEmpty() || password.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Login Error", "Please enter username and password.");
                return;
            }


            if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
                showAlert(Alert.AlertType.INFORMATION, "Admin Login", "Welcome, Admin!");

                SessionHolder.set("admin", -1, ADMIN_USERNAME, "");
                
                clearLoginFields();
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/restaurantcustomerservice/view/adminMain.fxml"));
//                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/restaurantcustomerservice/view/adminDashboard.fxml"));
                    Parent root = loader.load();

                    Stage stage = (Stage) si_loginBtn.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Admin Main");
                    stage.show();

                } catch (Exception e) {
                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to load Admin Dashboard.");
                }

                return;
            }


            if (username.equals(STAFF_USERNAME) && password.equals(STAFF_PASSWORD)) {
                showAlert(Alert.AlertType.INFORMATION, "Staff Login", "Welcome, Staff!");

                SessionHolder.set("staff", -1, STAFF_USERNAME, "");
   
                clearLoginFields();
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/restaurantcustomerservice/view/staffMain.fxml"));
//                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/restaurantcustomerservice/view/staffDashboard.fxml"));
                    AnchorPane root = loader.load();

//                    StaffDashboardController controller = loader.getController();
//                    controller.setLoginTime(); // optional method to show login time if you've implemented it

                    Stage stage = (Stage) si_loginBtn.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Staff Main Page");
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "Navigation Error", "Unable to load staff dashboard.");
                }

                return;
            }


            String query = "SELECT * FROM login WHERE username = ? AND password = ?";

            try (Connection connect = database.connectDB();
                 PreparedStatement prepare = connect.prepareStatement(query)) {

                prepare.setString(1, username);
                prepare.setString(2, password);

                try (ResultSet result = prepare.executeQuery()) {
                    if (result.next()) {
                        int idlogin = result.getInt("idlogin");
                        String question = result.getString("question");
                        
                        
                        SessionHolder.set("customer", idlogin, username, question);

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/restaurantcustomerservice/view/customerMain.fxml"));
//                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/restaurantcustomerservice/view/customerDashboard.fxml"));
                        Parent root = loader.load();
//                        CustomerDashboardController controller = loader.getController();

//                        controller.setCustomerData(idlogin, username, question);  // ✅ Now this is inside the scope
                        Stage stage = (Stage) si_loginBtn.getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.setTitle("Customer Main"); 
                        stage.show();


                    } else {
                        showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while connecting to the database.");
            }
        }

        private void clearLoginFields() {
            si_username.clear();
            si_password.clear();
        }



        @FXML
        public void regBtn() {
            String username = su_username.getText().trim();
            String password = su_password.getText();
            String question = su_question.getSelectionModel().getSelectedItem();
            String answer = su_answer.getText().trim();

            if (username.isEmpty() || password.isEmpty() || question == null || answer.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Registration Error", "Please fill all blank fields.");
                return;
            }

            if (password.length() < 8) {
                showAlert(Alert.AlertType.ERROR, "Registration Error", "Password must be at least 8 characters.");
                return;
            }


            int generatedId = Math.abs(java.util.UUID.randomUUID().hashCode());

            String checkUsernameSql = "SELECT username FROM login WHERE username = ?";
            String insertSql = "INSERT INTO login (idlogin, username, password, question, answer, date) VALUES (?, ?, ?, ?, ?, ?)";

            try (Connection connect = database.connectDB();
                 PreparedStatement checkStmt = connect.prepareStatement(checkUsernameSql)) {

                checkStmt.setString(1, username);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        showAlert(Alert.AlertType.ERROR, "Registration Error", username + " is already taken.");
                        return;
                    }
                }

                try (PreparedStatement insertStmt = connect.prepareStatement(insertSql)) {
                    insertStmt.setInt(1, generatedId);
                    insertStmt.setString(2, username);
                    insertStmt.setString(3, password);
                    insertStmt.setString(4, question);
                    insertStmt.setString(5, answer);
                    insertStmt.setDate(6, new java.sql.Date(new Date().getTime()));

                    int affectedRows = insertStmt.executeUpdate();
                    if (affectedRows > 0) {
                        showAlert(Alert.AlertType.INFORMATION, "Registration Success", "Account successfully registered!");
                        clearRegistrationFields();


                        TranslateTransition slider = new TranslateTransition(Duration.seconds(0.5), side_form);
                        slider.setToX(0);
                        slider.setOnFinished((e) -> {
                            side_alreadyHave.setVisible(false);
                            side_CreateBtn.setVisible(true);
                        });
                        slider.play();
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Registration Error", "Failed to register account.");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while connecting to the database.");
            }
        }

        private void clearRegistrationFields() {
            su_username.clear();
            su_password.clear();
            su_question.getSelectionModel().clearSelection();
            su_answer.clear();
        }


        private void loadSecurityQuestions() {
            List<String> questions = new ArrayList<>();
            for (String q : questionList) {
                questions.add(q);
            }

            ObservableList<String> listData = FXCollections.observableArrayList(questions);
            su_question.setItems(listData);
            fp_question.setItems(listData);
        }



        @FXML
        public void switchForgotPass() {
            fp_questionForm.setVisible(true);
            si_loginForm.setVisible(false);
            loadSecurityQuestions();
        }

        @FXML
        public void proceedBtn() {
            String username = fp_username.getText().trim();
            String question = fp_question.getSelectionModel().getSelectedItem();
            String answer = fp_answer.getText().trim();

            if (username.isEmpty() || question == null || answer.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please fill all blank fields.");
                return;
            }

            String sql = "SELECT username FROM login WHERE username = ? AND question = ? AND answer = ?";

            try (Connection connect = database.connectDB();
                 PreparedStatement prepare = connect.prepareStatement(sql)) {

                prepare.setString(1, username);
                prepare.setString(2, question);
                prepare.setString(3, answer);

                try (ResultSet rs = prepare.executeQuery()) {
                    if (rs.next()) {
                        // Correct info, proceed to new password form
                        np_newPassForm.setVisible(true);
                        fp_questionForm.setVisible(false);
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Error", "Incorrect information provided.");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while connecting to the database.");
            }
        }

        @FXML
        public void changePassBtn() {
            String newPass = np_newPassword.getText();
            String confirmPass = np_confirmPassword.getText();

            if (newPass.isEmpty() || confirmPass.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please fill all blank fields.");
                return;
            }

            if (!newPass.equals(confirmPass)) {
                showAlert(Alert.AlertType.ERROR, "Error", "Passwords do not match.");
                return;
            }

            String username = fp_username.getText().trim();
            String question = fp_question.getSelectionModel().getSelectedItem();
            String answer = fp_answer.getText().trim();

            // Retrieve original date to keep it unchanged
            String getDateSql = "SELECT date FROM login WHERE username = ?";
            String updateSql = "UPDATE login SET password = ?, question = ?, answer = ? WHERE username = ?";

            try (Connection connect = database.connectDB();
                 PreparedStatement getDateStmt = connect.prepareStatement(getDateSql);
                 PreparedStatement updateStmt = connect.prepareStatement(updateSql)) {

                getDateStmt.setString(1, username);
                String originalDate = null;
                try (ResultSet rs = getDateStmt.executeQuery()) {
                    if (rs.next()) {
                        originalDate = rs.getString("date");
                    }
                }


                updateStmt.setString(1, newPass);
                updateStmt.setString(2, question);
                updateStmt.setString(3, answer);
                updateStmt.setString(4, username);

                int updated = updateStmt.executeUpdate();

                if (updated > 0) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Password successfully changed!");
                    // Reset forms
                    np_newPassForm.setVisible(false);
                    si_loginForm.setVisible(true);

                    np_newPassword.clear();
                    np_confirmPassword.clear();
                    fp_question.getSelectionModel().clearSelection();
                    fp_answer.clear();
                    fp_username.clear();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to update password.");
                }

            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while connecting to the database.");
            }
        }


        @FXML
        public void backToRoleSelection() {
            // Hide all forms
            su_signupForm.setVisible(false);
            si_loginForm.setVisible(false);
            side_form.setVisible(false);
            fp_questionForm.setVisible(false);
            np_newPassForm.setVisible(false);

            // Show role selection pane
            roleSelectPane.setVisible(true);
        }

        @FXML
        public void backToLoginForm() {
            si_loginForm.setVisible(true);
            fp_questionForm.setVisible(false);
            
        }

        @FXML
        public void backToQuestionForm() {
            fp_questionForm.setVisible(true);
            np_newPassForm.setVisible(false);
        }

        @FXML
        public void switchForm(ActionEvent event) {
            TranslateTransition slider = new TranslateTransition(Duration.seconds(0.5), side_form);

            if (event.getSource() == side_CreateBtn) {
                slider.setToX(300);
                slider.setOnFinished(e -> {
                    side_alreadyHave.setVisible(true);
                    side_CreateBtn.setVisible(false);

                    fp_questionForm.setVisible(false);
                    si_loginForm.setVisible(true);
                    np_newPassForm.setVisible(false);

                    loadSecurityQuestions();
                });
                slider.play();
            } else if (event.getSource() == side_alreadyHave) {
                slider.setToX(0);
                slider.setOnFinished(e -> {
                    side_alreadyHave.setVisible(false);
                    side_CreateBtn.setVisible(true);

                    fp_questionForm.setVisible(false);
                    si_loginForm.setVisible(true);
                    np_newPassForm.setVisible(false);
                });
                slider.play();
            }
        }



        private void showAlert(Alert.AlertType type, String title, String message) {
            alert = new Alert(type);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }

        @Override
        public void initialize(URL url, ResourceBundle rb) {

            loadSecurityQuestions();


            roleSelectPane.setVisible(true);
            si_loginForm.setVisible(false);
            su_signupForm.setVisible(false);
            side_form.setVisible(false);
            fp_questionForm.setVisible(false);
            np_newPassForm.setVisible(false);

            side_alreadyHave.setVisible(false);
            side_CreateBtn.setVisible(true);
        }
    }