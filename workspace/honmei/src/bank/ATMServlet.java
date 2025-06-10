package bank;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class ATMServlet extends HttpServlet {
    private int balance = 50000;
    private final String CORRECT_PASS = "0906";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        String operation = request.getParameter("operation");
        int amount = Integer.parseInt(request.getParameter("amount"));
        String password = request.getParameter("password");

        out.println("<html><body>");
        out.println("<h2>ATM 処理結果</h2>");

        if (!CORRECT_PASS.equals(password)) {
            out.println("<p>暗証番号が間違っています。</p>");
        } else {
            if ("deposit".equals(operation)) {
                balance += amount;
                out.println("<p>" + amount + "円を預け入れました。</p>");
            } else if ("withdraw".equals(operation)) {
                if (amount > balance) {
                    out.println("<p>残高不足です。引き出しできません。</p>");
                } else {
                    balance -= amount;
                    out.println("<p>" + amount + "円を引き出しました。</p>");
                }
            }

            out.println("<p>現在の残高：" + balance + "円</p>");
        }

        out.println("</body></html>");
    }
}
