import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.HashMap;

public class SimplePasswordServer {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(5500), 0);
        server.createContext("/", new FormHandler());
        server.setExecutor(null);
        System.out.println("サーバー起動中: http://localhost:5500/");
        server.start();
    }

    static class FormHandler implements HttpHandler {
        @SuppressWarnings("override")
        public void handle(HttpExchange exchange) throws IOException {
            String response = "0";

            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                InputStream inputStream = exchange.getRequestBody();
                String formData = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                Map<String, String> params = parseFormData(formData);
                String password = params.getOrDefault("password", "");
                String hashed = hashPassword(password);

                response = "<html><body>"
                        + "<h2>ハッシュ結果</h2>"
                        + "<p>元のパスワード: " + password + "</p>"
                        + "<p>SHA-256: " + hashed + "</p>"
                        + "<a href='/'>戻る</a>"
                        + "</body></html>";

            } else {
                response = "<html><body>"
                        + "<h2>パスワードを入力してください</h2>"
                        + "<form method='post'>"
                        + "パスワード: <input type='password' name='password' /><br><br>"
                        + "<input type='submit' value='ハッシュ化' />"
                        + "</form>"
                        + "</body></html>";
            }

            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes(StandardCharsets.UTF_8));
            }
        }

        private static String hashPassword(String password) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] digest = md.digest(password.getBytes(StandardCharsets.UTF_8));
                StringBuilder sb = new StringBuilder();
                for (byte b : digest) {
                    sb.append(String.format("%02x", b));
                }
                return sb.toString();
            } catch (NoSuchAlgorithmException e) {
                return "エラー: " + e.getMessage();
            }
        }

        private static Map<String, String> parseFormData(String formData) {
            Map<String, String> map = new HashMap<>();
            for (String pair : formData.split("&")) {
                String[] parts = pair.split("=");
                if (parts.length == 2) {
                    map.put(parts[0], java.net.URLDecoder.decode(parts[1], StandardCharsets.UTF_8));
                }
            }
            return map;
        }
    }
}
