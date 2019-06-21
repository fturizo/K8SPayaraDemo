package fish.payara.support.demo.service;

import fish.payara.cluster.Clustered;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

/**
 * Service used to track the current ID counter of user data.
 * 
 * @author Fabio Turizo
 */
// @ApplicationScoped
@Singleton
@Clustered(callPostConstructOnAttach = false)
public class CounterService implements Serializable {

    private static final long serialVersionUID = -3389532791656375615L;
    private static final Logger LOG = Logger.getLogger(CounterService.class.getName());

    private final AtomicInteger userCounter = new AtomicInteger(0);

    @PostConstruct
    public void initialize() {
        LOG.info("Initializing counter service");
    }

    public Integer getNextValue() {
        return userCounter.incrementAndGet();
    }
    
    public Integer getCurrentValue(){
        return userCounter.get();
    }
}
