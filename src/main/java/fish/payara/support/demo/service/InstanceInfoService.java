package fish.payara.support.demo.service;

import fish.payara.micro.PayaraMicro;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;

/**
 * Simple service that retrieves run-time server information
 * 
 * @author Fabio Turizo
 */
@ApplicationScoped
public class InstanceInfoService {

    private static final Logger LOG = Logger.getLogger(InstanceInfoService.class.getName());
    private static final String DEFAULT_NAME = "payara-micro";

    public String getName() {
        String instanceName = null;
        try {
            instanceName = PayaraMicro.getInstance().getInstanceName();
        } catch (Exception exception) {
            LOG.log(Level.SEVERE, "Error retrieving instance name", exception);
        }
        return Optional.ofNullable(instanceName).orElse(DEFAULT_NAME);
    }
}
