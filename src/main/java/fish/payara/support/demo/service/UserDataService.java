package fish.payara.support.demo.service;

import fish.payara.support.demo.entities.UserData;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.cache.Cache;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 * Service that manages user data
 * 
 * @author Fabio Turizo
 */
@RequestScoped
public class UserDataService {

    @Inject
    Cache<Integer, UserData> dataSet;

    @Inject
    CounterService counterService;

    @Inject
    InstanceInfoService infoService;

    public Optional<UserData> retrieve(Integer id) {
        return Optional.ofNullable(dataSet.get(id));
    }

    public UserData store(UserData userData) {
        int nextId = counterService.getNextValue();
        dataSet.putIfAbsent(nextId, new UserData(nextId, userData, infoService.getName()));
        return dataSet.get(nextId);
    }
    
    public List<UserData> listAll(){
        return IntStream.rangeClosed(0, counterService.getCurrentValue())
                        .filter(dataSet::containsKey)
                        .mapToObj(dataSet::get)
                        .collect(Collectors.toList());
    }
}
