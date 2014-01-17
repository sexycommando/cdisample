package cdisample.model;

import cdisample.entity.User;
import cdisample.exception.NotLoggedInException;
import cdisample.qualifier.LoggedIn;
import cdisample.qualifier.Users;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Parameter;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@SessionScoped
@Model
public class Login implements Serializable {

    @Inject
    Credentials credentials;

    @Inject
    @Users
    EntityManager userDatabase;

    private CriteriaQuery<User> query;
    private Parameter<String> usernameParam;
    private Parameter<String> passwordParam;

    @Inject
    @LoggedIn
    Event<User> userLoggedInEvent;

    private User user;

    @Inject
    void initQuery(@Users EntityManagerFactory emf) {
        CriteriaBuilder cb = emf.getCriteriaBuilder();
        usernameParam = cb.parameter(String.class);
        passwordParam = cb.parameter(String.class);
        query = cb.createQuery(User.class);
        Root<User> u = query.from(User.class);
        query.select(u);
        query.where(cb.equal(u.get("username"), usernameParam),
                cb.equal(u.get("password"), passwordParam));
    }

    public String login() {
        List<User> results = userDatabase.createQuery(query)
                .setParameter(usernameParam, credentials.getUsername())
                .setParameter(passwordParam, credentials.getPassword())
                .getResultList();
        if (!results.isEmpty()) {
            user = results.get(0);
            userLoggedInEvent.fire(user);
        } else {
            FacesMessage msg = new FacesMessage("Invalid Username or Password");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }

        return "documents";
    }

    public String logout() {
        user = null;
        ExternalContext context = FacesContext.getCurrentInstance()
                .getExternalContext();
        context.invalidateSession();

        return "login";
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    @Produces
    @SessionScoped
    @LoggedIn
    public User getCurrentUser() {
        if (user == null) {
            throw new NotLoggedInException();
        } else {
            return user;
        }
    }
}
