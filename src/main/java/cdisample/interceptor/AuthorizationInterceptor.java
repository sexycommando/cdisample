package cdisample.interceptor;

import cdisample.entity.User;
import cdisample.exception.NotAuthorizedException;
import cdisample.qualifier.LoggedIn;
import cdisample.qualifier.Secure;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Secure
@Interceptor
public class AuthorizationInterceptor implements Serializable {

    @Inject
    @LoggedIn
    User user;

    @Inject
    Logger log;

    @AroundInvoke
    public Object authorize(InvocationContext ic) throws Exception {
        if (!user.isBanned()) {
            log.info("Authorized: " + user.getUsername());
            return ic.proceed();
        } else {
            log.info("Not authorized: " + user.getUsername());
            throw new NotAuthorizedException();
        }
    }
}
