import javax.swing.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class a {

    public static void main(String[] args) {
        JPasswordField passwordField = new JPasswordField();
        int option = JOptionPane.showConfirmDialog(null, passwordField, "パスワードを入力してください", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            char[] passwordChars = passwordField.getPassword();
            byte[] passwordBytes = new String(passwordChars).getBytes(StandardCharsets.UTF_8);
            Arrays.fill(passwordChars, '\0'); // セキュリティ対策：配列をクリア

            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hashBytes = md.digest(passwordBytes);

                StringBuilder sb = new StringBuilder();
                for (byte b : hashBytes) {
                    sb.append(String.format("%02x", b));
                }
                String hashedPassword = sb.toString();

                // 結果を表示
                System.out.println("ハッシュ化されたパスワード: " + hashedPassword);

                String htmlOutput = "<html><body>"
                        + "<h1>ハッシュ化されたパスワード</h1>"
                        + "<p>ハッシュ値: " + hashedPassword + "</p>"
                        + "</body></html>";

                System.out.println("以下のHTMLが生成されました:");
                System.out.println(htmlOutput);

            } catch (NoSuchAlgorithmException e) {
                System.err.println("ハッシュアルゴリズムが見つかりません: " + e.getMessage());
            }
        } else {
            System.out.println("キャンセルされました。");
        }
    }
}
