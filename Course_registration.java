
package course_registration;

public class Course_registration {


    public static void main(String[] args) {
        // Run the UI program safely
        java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            
            // 1. Create the main window, title it "Login System", and close program when clicking X
            javax.swing.JFrame frame = new javax.swing.JFrame("Login System");
            frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
            
            // 2. Put our Login screen design inside this main window
            frame.setContentPane(new LoginGUI());
            
            // 3. Auto-fit the window size to the components and place it at the center of screen
            frame.pack();
            frame.setLocationRelativeTo(null);
            
            // 4. Display the window on screen
            frame.setVisible(true);
        }
    });
    }
    
}
