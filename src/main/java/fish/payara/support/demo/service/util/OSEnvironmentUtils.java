package fish.payara.support.demo.service.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class to interact with the OS environment.
 * 
 * @author Fabio Turizo
 */
public final class OSEnvironmentUtils {
    
    private static final Logger LOG = Logger.getLogger(OSEnvironmentUtils.class.getName());
    private static final int DEFAULT_THREADS_PER_CORE = 2;
    
    public static int getThreadsPerCore(){
        try {
            Process lscpu = Runtime.getRuntime().exec("lscpu");
            BufferedReader reader = new BufferedReader(new InputStreamReader(lscpu.getInputStream()));
            return reader.lines()
                    .filter(line -> line.contains("Thread(s) per core:"))
                    .map(line -> line.split("\\s+"))
                    .mapToInt(parts -> Integer.parseInt(parts[parts.length - 1]))
                    .findFirst()
                    .orElse(DEFAULT_THREADS_PER_CORE);
        } catch (IOException exception) {
            LOG.log(Level.SEVERE, "Error retrieving Threads per core value", exception);
            return DEFAULT_THREADS_PER_CORE;
        }
    }
}
