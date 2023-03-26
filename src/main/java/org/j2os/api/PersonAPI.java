package org.j2os.api;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.j2os.command.PersonRemoveCommand;
import org.j2os.command.PersonSaveCommand;
import org.j2os.command.PersonUpdateCommand;
import org.j2os.query.PersonFindAllQuery;
import org.j2os.query.PersonFindOneQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

/*
    Bahador, Amirsam
 */
@RestController
public class PersonAPI {
    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private QueryGateway queryGateway;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleMyException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    @GetMapping("/save/{name}/{family}")
    public Object save(@PathVariable("name") String name, @PathVariable("family") String family) throws ExecutionException, InterruptedException {

        PersonSaveCommand person = PersonSaveCommand.builder().personId(UUID.randomUUID().toString()).name(name).family(family).build();
        return commandGateway.sendAndWait(person);
    }

    @GetMapping("/update/{id}/{family}")
    public String update(@PathVariable("id") String id, @PathVariable("family") String family) {
        PersonUpdateCommand person = PersonUpdateCommand.builder().personId(id).family(family).build();
        return commandGateway.sendAndWait(person);
    }

    //Not use
    @GetMapping("/remove/{id}/{family}")
    public String remove(@PathVariable("id") String id, @PathVariable("family") String family) {
        PersonRemoveCommand person = PersonRemoveCommand.builder().personId(id).family(family).build();
        return commandGateway.sendAndWait(person);

    }

    @GetMapping("/findAll")
    public Object findAll() {
        PersonFindAllQuery personFindAllQuery = new PersonFindAllQuery();
        return queryGateway.query(personFindAllQuery, ResponseTypes.multipleInstancesOf(PersonFindOneQuery.class));
    }

    @GetMapping("/findOne")
    public Object findOne() {
        PersonFindOneQuery personFindOneQuery = new PersonFindOneQuery();
        personFindOneQuery.setId("51dd1929-5a91-4a1b-89fd-2158b12682a1");
        personFindOneQuery.setName("Mr.amirsam");

        return queryGateway.query(personFindOneQuery, ResponseTypes.instanceOf(PersonFindOneQuery.class));
    }
}
