package controller;

import service.AuthenticationService;
import ui.AdminDashboard;
import ui.LoginGUI;
import ui.StudentDashboard;

import javax.swing.*;

public class LoginController {

    private AuthenticationService authService;

    public LoginController(AuthenticationService authService) {
        this.authService = authService;
    }

    public void processLogin(String studentId, String password) {

        String role = authService.login(studentId, password);

        if (role == null) {
            JOptionPane.showMessageDialog(
                null,
                "ID or Password are INCORECT! or NULL!"
            );
            new LoginGUI().setVisible(true);
        }

        if (role.equals("ADMIN")) {
            new AdminDashboard().setVisible(true);

        } else if (role.equals("STUDENT")) {
            new StudentDashboard().setVisible(true);
        }
    }
}