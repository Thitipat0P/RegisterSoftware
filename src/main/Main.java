
import ui.LoginGUI;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {
        
        // 1. (Optional) ปรับแต่งหน้าตา GUI ให้สวยงามตามระบบปฏิบัติการ (Windows/Mac)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // ถ้าโหลดธีม OS ไม่สำเร็จ ให้ใช้ธีมมาตรฐานของ Java Swing แทน
            System.out.println("ไม่สามารถดึงธีมของระบบได้ ใช้ธีม Default");
        }

        // 2. เรียกเปิดหน้า LoginGUI บน Thread สำหรับแสดงผล GUI โดยเฉพาะ (Swing Thread Safety)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginGUI(); // สร้างและเปิดหน้าจอ Login
            }
        });
    }
}