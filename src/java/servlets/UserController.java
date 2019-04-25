package servlets;

import entity.Book;
import entity.User;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import secure.SecureLogic;
import session.BookFacade;
import util.PageReturner;

@WebServlet(name = "UserController", urlPatterns = {
    "/showBooks",

})
public class UserController extends HttpServlet {
    @EJB BookFacade bookFacade;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        HttpSession session = request.getSession(false);
        SecureLogic sl = new SecureLogic();
        User regUser = null;
        if(session != null){
            try {
                regUser = (User) session.getAttribute("regUser");
            } catch (Exception e) {
                regUser = null;
            }
        }
        if(regUser == null){
            request.setAttribute("info", "У вас нет прав доступа к ресурсу");
            request.getRequestDispatcher(PageReturner.getPage("showLogin"))
                    .forward(request, response);
            return;
        }
        if(!sl.isRole(regUser, "USER")){
            request.setAttribute("info", "У вас нет прав доступа к ресурсу");
            request.getRequestDispatcher(PageReturner.getPage("showLogin"))
                    .forward(request, response);
            return;
        } 
        
        String path = request.getServletPath();
        switch (path) {
            case "/showBooks":
                List<Book> listBooks = bookFacade.findActived(true);
                request.setAttribute("role", sl.getRole(regUser));
                request.setAttribute("listBooks", listBooks);
                request.getRequestDispatcher(PageReturner.getPage("listBook")).forward(request, response);
                break;
            default:
                request.setAttribute("info", "Нет такой стpаницы!");
                request.getRequestDispatcher(PageReturner.getPage("welcome")).forward(request, response);
                break;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}