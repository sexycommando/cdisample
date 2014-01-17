package cdisample.decorator;

import cdisample.dao.DataAccess;
import cdisample.entity.Permission;
import cdisample.exception.NotAuthorizedException;
import java.io.Serializable;
import java.util.Set;
import java.util.logging.Logger;
import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

@Decorator
public abstract class DataAccessAuthorizationDecorator<T, V> implements DataAccess<T, V>, Serializable {

    @Inject
    @Delegate
    DataAccess<T, V> delegate;

    @Inject
    Logger log;

    @Inject
    Set<Permission> permissions;

    @Override
    public void save(T object) {
        authorize("save");
        delegate.save(object);
    }

    @Override
    public void delete(V object) {
        authorize("delete");
        delegate.delete(object);
    }

    private void authorize(String action) {
        Class<T> type = delegate.getDataType();
        if (permissions.contains(new Permission(action, type.getName()))) {
            log.info("Authorized for " + action);
        } else {
            log.info("Not authorized for " + action);
            throw new NotAuthorizedException(action);
        }
    }
}
