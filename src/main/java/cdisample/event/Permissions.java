package cdisample.event;

import cdisample.entity.Permission;
import cdisample.entity.User;
import cdisample.qualifier.LoggedIn;
import cdisample.qualifier.Users;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Parameter;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@SessionScoped
public class Permissions implements Serializable {

    @Produces
    private Set permissions = new HashSet();

    @Inject
    @Users
    EntityManager userDatabase;
    Parameter usernameParam;
    CriteriaQuery query;

    @Inject
    void initQuery(@Users EntityManagerFactory emf) {
        CriteriaBuilder cb = emf.getCriteriaBuilder();
        usernameParam = cb.parameter(String.class);
        query = cb.createQuery(Permission.class);
        Root<Permission> p = query.from(Permission.class);
        query.select(p);
        query.where(cb.equal(p.get("username"), usernameParam));
    }

    void onLogin(@Observes @LoggedIn User user) {
        permissions = new HashSet(userDatabase.createQuery(query)
                .setParameter(usernameParam, user.getUsername())
                .getResultList());
    }

}
