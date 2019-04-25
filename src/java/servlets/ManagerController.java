package servlets;

import entity.Book;
import entity.History;
import entity.User;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
import session.HistoryFacade;
import session.RoleFacade;
import session.UserFacade;
import util.PageReturner;

/**
 *
 * @author Melnikov
 */
@WebServlet(name = "ManagerController", urlPatterns = {
    "/newBook",
    "/addBook",
    "/showUsers",
    "/showTakeBookToReader",
    "/takeBookToReader",
    "/showTakeBooks",
    "/returnBook",
    "/deleteBook",
    
    
})
public class ManagerController extends HttpServlet {
    
@EJB BookFacade bookFacade;
@EJB UserFacade userFacade;
@EJB HistoryFacade historyFacade;
@EJB RoleFacade roleFacade;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF8");
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
        if(!sl.isRole(regUser, "MANAGER")){
            request.setAttribute("info", "У вас нет прав доступа к ресурсу");
            request.getRequestDispatcher(PageReturner.getPage("showLogin"))
                    .forward(request, response);
            return;
        } 
        String path = request.getServletPath();
        switch (path) {
            case "/newBook":
                request.getRequestDispatcher(PageReturner.getPage("newBook")).forward(request, response);
                break;
            case "/addBook":{
                String nameBook = request.getParameter("nameBook");
                String author = request.getParameter("author");
                String yearPublished = request.getParameter("yearPublished");
                String isbn = request.getParameter("isbn");
                String countStr = request.getParameter("count");
                Book book = new Book(nameBook, author, new Integer(yearPublished), isbn, new Integer(countStr));
                bookFacade.create(book);
                request.setAttribute("book", book);
                request.getRequestDispatcher(PageReturner.getPage("index")).forward(request, response);
                    break;
                }
            case "/showUsers":
                List<User> listUsers = userFacade.findAll();
                request.setAttribute("listUsers", listUsers);
                request.getRequestDispatcher(PageReturner.getPage("listUsers")).forward(request, response);
                break;
            case "/showTakeBookToReader":
                List<Book>listBooks = bookFacade.findActived(true);
                if(listBooks != null) request.setAttribute("listBooks", listBooks);
                request.setAttribute("listUsers", userFacade.findAll());
                request.getRequestDispatcher(PageReturner.getPage("showTakeBookToReader")).forward(request, response);
                break;
            case "/showTakeBooks":{
                List<History> takeBooks = historyFacade.findTakeBooks();
                request.setAttribute("takeBooks", takeBooks);
                request.getRequestDispatcher(PageReturner.getPage("listTakeBook")).forward(request, response);
                    break;
                }
            case "/takeBookToReader":{
                String selectedBook = request.getParameter("selectedBook");
                String selectedUser = request.getParameter("selectedUser");
                Book book = bookFacade.find(new Long(selectedBook));

                User user = userFacade.find(new Long(selectedUser));
                Calendar c = new GregorianCalendar();
                if(book.getCount()>0){
                    book.setCount(book.getCount()-1);
                    bookFacade.edit(book);
                    History history = new History(book, user, c.getTime(), null);
                    historyFacade.create(history);
                }else{
                    request.setAttribute("info", "Все книги выданы");
                }
                List<History> takeBooks = historyFacade.findTakeBooks();
                request.setAttribute("takeBooks", takeBooks);
                request.getRequestDispatcher(PageReturner.getPage("listTakeBook")).forward(request, response);
                    break;
                }
            case "/returnBook":{
                String historyId = request.getParameter("historyId");
                History history = historyFacade.find(new Long(historyId));
                Calendar c = new GregorianCalendar();
                history.setDateReturn(c.getTime());
                history.getBook().setCount(history.getBook().getCount()+1);
                historyFacade.edit(history);
                List<History> takeBooks = historyFacade.findTakeBooks();
                request.setAttribute("takeBooks", takeBooks);
                request.getRequestDispatcher(PageReturner.getPage("listTakeBook")).forward(request, response);
                    break;
                }
            case "/deleteBook":{
                String deleteBookId = request.getParameter("deleteBookId");
                Book book = bookFacade.find(new Long(deleteBookId));
                book.setActive(Boolean.FALSE);
                bookFacade.edit(book);
                //historyFacade.remove(deleteBookId);
                listBooks = bookFacade.findActived(true);
                request.setAttribute("listBooks", listBooks);
                request.getRequestDispatcher(PageReturner.getPage("listBook")).forward(request, response);
                    break;
                }
            default:
                request.setAttribute("info", "Нет такой станицы!");
                request.getRequestDispatcher(PageReturner.getPage("index")).forward(request, response);
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