/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Book;
import entity.BookCover;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class BookCoverFacade extends AbstractFacade<BookCover> {

    @PersistenceContext(unitName = "KTVR17WebLibrary2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BookCoverFacade() {
        super(BookCover.class);
    }

    public BookCover findByBook(Book book) {
        try {
            return (BookCover) em.createQuery("SELECT bc FROM BookCover bc WHERE bc.book = :book")
                    .setParameter("book", book)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
}