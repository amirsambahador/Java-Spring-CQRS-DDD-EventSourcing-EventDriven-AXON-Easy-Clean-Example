package org.j2os.projection;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.j2os.event.PersonRemoveEvent;
import org.j2os.event.PersonSaveEvent;
import org.j2os.event.PersonUpdateEvent;
import org.j2os.query.PersonFindAllQuery;
import org.j2os.query.PersonFindOneQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
    Bahador, Amirsam
 */
@Service
public class PersonProjection {
    private Map<String, PersonFindOneQuery> map = new ConcurrentHashMap<>();

    @QueryHandler
    public List<PersonFindOneQuery> handle(PersonFindAllQuery query)   {
        List<PersonFindOneQuery> list = new ArrayList<>();
        for (String personId : map.keySet()) {
            list.add(map.get(personId));
        }
        return list;
    }

    @QueryHandler
    public PersonFindOneQuery handle(PersonFindOneQuery query) throws Exception {
        for (String personId : map.keySet()) {
            if (map.get(personId).getId().equals(query.getId()) || map.get(personId).getName().equals(query.getName())) {
                return map.get(personId);
            }
        }
        throw new Exception("Record doest not exist");
    }

    @EventHandler
    public void save(PersonSaveEvent event) {
        map.put(event.getPersonId(), PersonFindOneQuery.builder().id(event.getPersonId()).family(event.getFamily()).name(event.getName()).build());

    }

    @EventHandler
    public void update(PersonUpdateEvent event) {
        map.put(event.getPersonId(), PersonFindOneQuery.builder().id(event.getPersonId()).family(event.getFamily()).name(event.getName()).build());
    }

    @EventHandler
    public void remove(PersonRemoveEvent event) {
        map.remove(event.getPersonId());
    }
}


