package com.JAVA.Servlet;

import com.JAVA.Beans.User;
import com.JAVA.DAO.UserDAO;
import com.JAVA.DAO.UserDAOImpl;
import com.JAVA.utils.DAOFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        try {
            this.userDAO = new UserDAOImpl(daoFactory);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = userDAO.findUserByLoginAndPassword(email, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            String redirectURL = "";
            switch (user.getType().intValue()) {
                case 1:
                    redirectURL = "admin/views/index.jsp?user_id=" + user.getId();
                    break;
                case 2:
                    redirectURL = "Fermier/views/index.jsp?idFermier=" + user.getId();
                    break;

                case 3:
                    redirectURL = request.getContextPath() + "/ListerCommandesServlet?user_id=" + user.getId();
                    break;
                case 4:
                	redirectURL = request.getContextPath() + "/ListerProduits?page=home&user_id=" + user.getId();
                    break;
                default:
                    redirectURL = "login.jsp?error=invalid";
                    break;
            }
            response.sendRedirect(redirectURL);
        } else {
            response.sendRedirect("login.jsp?error=invalid");
        }
    }
}
