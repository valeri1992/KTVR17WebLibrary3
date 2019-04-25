
package entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Melnikov
 */
@Entity
public class History implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.MERGE)
    private Book book;
    @OneToOne
    private User user;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateBegin;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateReturn;

    public History() {
    }

    public History(Book book, User user, Date dateBegin, Date dateReturn) {
        this.book = book;
        this.user = user;
        this.dateBegin = dateBegin;
        this.dateReturn = dateReturn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(Date dateBegin) {
        this.dateBegin = dateBegin;
    }

    public Date getDateReturn() {
        return dateReturn;
    }

    public void setDateReturn(Date dateReturn) {
        this.dateReturn = dateReturn;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.book);
        hash = 97 * hash + Objects.hashCode(this.user);
        hash = 97 * hash + Objects.hashCode(this.dateBegin);
        hash = 97 * hash + Objects.hashCode(this.dateReturn);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final History other = (History) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.book, other.book)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.dateBegin, other.dateBegin)) {
            return false;
        }
        if (!Objects.equals(this.dateReturn, other.dateReturn)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdm = new SimpleDateFormat("dd.mm.yyyy");
        if(dateReturn != null){
            return "History{" + "id=" + id + ", book=" + book.getNameBook() + ", user=" + user.getName()+" "+user.getSurname() + ", dateBegin=" + sdm.format(dateBegin) + ", dateReturn=" + sdm.format(dateReturn) + '}';
        }else{
            return "History{" + "id=" + id + ", book=" + book.getNameBook() + ", user=" + user.getName()+" "+user.getSurname() + ", dateBegin=" + sdm.format(dateBegin) + '}';
        }
    }
    
    

    
    
}
