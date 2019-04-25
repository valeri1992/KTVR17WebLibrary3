package servlets;

import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import secure.Role;
import secure.SecureLogic;
import secure.UserRoles;
import session.RoleFacade;
import session.UserFacade;
import util.PageReturner;

/**
 *
 * @author pupil
 */
@WebServlet(name = "AdminController", urlPatterns = {
    "/showUserRoles",
    "/changeUserRole",
    
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
            case "/showUserRoles":
                Map<User,String> mapUsers = new HashMap<>();
                List<User> listUsers = userFacade.findAll();
                int n = listUsers.size();
                for(int i=0;i<n;i++){
                    mapUsers.put(listUsers.get(i), sl.getRole(listUsers.get(i)));
                }
                List<Role> listRoles = roleFacade.findAll();
                request.setAttribute("mapUsers", mapUsers);
                request.setAttribute("listRoles", listRoles);
                request.getRequestDispatcher(PageReturner.getPage("showUserRoles"))
                        .forward(request, response);
                break;
            case "/changeUserRole":
                String setButton = request.getParameter("setButton");
                String deleteButton = request.getParameter("deleteButton");
                String userId = request.getParameter("user");
                String roleId = request.getParameter("role");
                User user = userFacade.find(new Long(userId));
                Role roleToUser = roleFacade.find(new Long(roleId));
                UserRoles ur = new UserRoles(user, roleToUser);
                if(setButton != null){
                    sl.addRoleToUser(ur);
                }
                if(deleteButton != null){
                    sl.deleteRoleToUser(ur.getUser());
                }
                mapUsers = new HashMap<>();
                listUsers = userFacade.findAll();   
                n = listUsers.size();
                for(int i=0;i<n;i++){
                    mapUsers.put(listUsers.get(i), sl.getRole(listUsers.get(i)));
                }
                request.setAttribute("mapUsers", mapUsers);
                List<Role> newListRoles = roleFacade.findAll();
                request.setAttribute("listRoles", newListRoles);
                request.getRequestDispatcher(PageReturner.getPage("showUserRoles"))
                        .forward(request, response);
                break;
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
  
