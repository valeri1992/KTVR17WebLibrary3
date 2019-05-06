package servlets;

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
import session.RoleFacade;
import session.UserFacade;
import util.EncriptPass;
import util.PageReturner;

/**
 *
 * @author pupil
 */
@WebServlet(name = "AdminController", urlPatterns = {
   "/changePassword",
    
    "/showChangePassword",
})
public class AdminController extends HttpServlet {
    @EJB UserFacade userFacade;
    @EJB RoleFacade roleFacade;
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
        if(!sl.isRole(regUser, "ADMIN")){
            request.setAttribute("info", "У вас нет прав доступа к ресурсу");
            request.getRequestDispatcher(PageReturner.getPage("showLogin"))
                    .forward(request, response);
            return;
        } 
        String path = request.getServletPath();
        switch (path) {
            
            case "/showChangePassword":
               List<User> listUsers= userFacade.findAll();
               request.setAttribute("listUser", listUsers);
               request.getRequestDispatcher(PageReturner.getPage("showChangePassword"))
                        .forward(request, response);
                break;
//                 case "/changePassword":
//                String userId = request.getParameter("userId");
//                String password1 = request.getParameter("password1");
//                String password2  = request.getParameter("password2");
//                if(!password1.equals(password2)){
//                  request.setAttribute("info", "Неправильно введен пароль");
//                request.getRequestDispatcher(PageReturner.getPage("showChangePassword"))
//                        .forward(request, response);}
//                break;
//                EncriptPass ep =new EncriptPass();
//                String salts =ep.createSalts();
//                String encriptPass =ep.setEncriptPass(password1,salts);
//                User user =userFacade.find(new Long(userId));
//                user.set
//                 case "/changePassword":
//                String userId = request.getParameter("userId");
//                String password1 = request.getParameter("password1");
//                String password2  = request.getParameter("password2");
//                if(!password1.equals(password2)){
//                  request.setAttribute("info", "Неправильно введен пароль");
//                request.getRequestDispatcher(PageReturner.getPage("showChangePassword"))
//                        .forward(request, response);}
//                break;
//                EncriptPass ep =new EncriptPass();
//                String salts =ep.createSalts();
//                String encriptPass =ep.setEncriptPass(password1,salts);
//                User user =userFacade.find(new Long(userId));
//                user.set
                
            case "/changePassword":
                 String userId = request.getParameter("user");
               String newpassword = request.getParameter("newpassword");
               User user = userFacade.find(new Long(userId));
             
      
               EncriptPass ep= new EncriptPass();
               String  salts = ep.createSalts();
               String encriptPass = ep.setEncriptPass(newpassword, salts);
               user.setPassword(newpassword);
               userFacade.edit(user);
               request.setAttribute("info", "Пароль изменен!");
               listUsers =userFacade.findAll();
               request.setAttribute("listUsers", listUsers);
               request.getRequestDispatcher(PageReturner.getPage("changePassword"))
                        .forward(request, response);
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
  
