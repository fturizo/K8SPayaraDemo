package fish.payara.support.demo.service;

import fish.payara.support.demo.service.util.OSEnvironmentUtils;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;

/**
 * Singleton service used to make the CPU being busy for an specific percentage of time.
 * Algorithms derived from sampling simulator project at https://github.com/pradykaushik/cpu-load-generator
 *
 * @author Fabio Turizo
 */
@ApplicationScoped
public class LoadGeneratorService {

    private final static double LOAD_FACTOR = 0.75;
    private static final Logger LOG = Logger.getLogger(LoadGeneratorService.class.getName());

    private ThreadGroup currentThreadGroup;
    private int threadCounter;
    private AtomicBoolean inProgress = new AtomicBoolean(false);
    
    public boolean generateCPULoad() {

        if (inProgress.get()) {
            return false;
        } else {
            inProgress.set(true);
            this.currentThreadGroup = new ThreadGroup("cpu-load-generator-tg");
            
            int cores = Runtime.getRuntime().availableProcessors();
            int numThreadsPerCore = OSEnvironmentUtils.getThreadsPerCore();
            
            LOG.log(Level.INFO, "Detected {0} available cores", cores);
            LOG.log(Level.INFO, "Number of threads per core: {0}", numThreadsPerCore);
            for (int thread = 0; thread < cores * numThreadsPerCore; thread++) {
                String name = "cpu-load-generator-" + (threadCounter++);
                LOG.log(Level.INFO, "Spawning new CPU Load generator thread: {0}", name);
                new Thread(currentThreadGroup, this::executeCPULoadTask, name).start();
            }            
            return true;
        }
    }
    
    public boolean stopCurrentCPULoad(){
        if(!inProgress.get()){
            return false;
        }else{
            inProgress.set(false);
            currentThreadGroup = null;
            return true;
        }
    }

    private void executeCPULoadTask() {
        try {
            while (inProgress.get()) {
                // Every 100ms, sleep for the percentage of "idle" time
                if (System.currentTimeMillis() % 100 == 0) {
                    Thread.sleep((long) Math.floor((1 - LOAD_FACTOR) * 100));
                }
            }
            LOG.log(Level.INFO, "Finishing execution of thread {0}", Thread.currentThread().getName());
        } catch (InterruptedException exception) {
            LOG.log(Level.INFO, "{0} interrupted", Thread.currentThread().getName());
        }
    }
}
