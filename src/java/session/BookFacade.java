package session;

import entity.Book;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Melnikov
 */
@Stateless
public class BookFacade extends AbstractFacade<Book> {

    @PersistenceContext(unitName = "KTVR17WebLibrary2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BookFacade() {
        super(Book.class);
    }

    public List<Book> findActived(boolean active) {
        try {
            return em.createQuery("SELECT b FROM Book b WHERE b.active = :active AND b.count > 0")
                .setParameter("active", active)
                .getResultList();
        } catch (Exception e) {
            return null;
        }
        
    }
    
}
