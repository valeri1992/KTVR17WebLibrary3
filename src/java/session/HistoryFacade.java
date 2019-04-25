package session;

import entity.Book;
import entity.History;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Melnikov
 */
@Stateless
public class HistoryFacade extends AbstractFacade<History> {

    @EJB BookFacade bookFacade;
    

    @PersistenceContext(unitName = "KTVR17WebLibrary2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HistoryFacade() {
        super(History.class);
    }
    
    public List<History> findTakeBooks(){
        return em.createQuery("SELECT h FROM History h WHERE h.dateReturn=NULL")
                .getResultList();
    }
    
    public List<History> find(Book book){
        return em.createQuery("SELECT h FROM History h WHERE h.book = :book")
                .setParameter("book", book)
                .getResultList();
    }
    
    
    
}
