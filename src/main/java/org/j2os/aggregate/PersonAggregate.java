package org.j2os.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.j2os.command.PersonRemoveCommand;
import org.j2os.command.PersonSaveCommand;
import org.j2os.command.PersonUpdateCommand;
import org.j2os.event.PersonRemoveEvent;
import org.j2os.event.PersonSaveEvent;
import org.j2os.event.PersonUpdateEvent;

/*
    Bahador, Amirsam
 */
@Aggregate
public class PersonAggregate {
    @AggregateIdentifier
    private String personId;
    private String name;
    private String family;

    public PersonAggregate() {
    }

    @CommandHandler
    public PersonAggregate(PersonSaveCommand cmd) {
        AggregateLifecycle.apply(new PersonSaveEvent(cmd.getPersonId(), "Mr." + cmd.getName(), cmd.getFamily()));//invoke on method (on(SavePersonEvent event))
    }

    @EventSourcingHandler
    public void on(PersonSaveEvent event) {
        this.name = event.getName();
        this.family = event.getFamily();
        this.personId = event.getPersonId();
    }

    @CommandHandler
    public void handle(PersonUpdateCommand cmd) {
        AggregateLifecycle.apply(new PersonUpdateEvent(this.personId, this.name, cmd.getFamily()));//invoke on method (on(UpdatePersonEvent event))
    }

    @EventSourcingHandler
    public void on(PersonUpdateEvent event) {
        this.name = event.getName();
        this.family = event.getFamily();
        this.personId = event.getPersonId();
    }

    @CommandHandler
    public void handle(PersonRemoveCommand cmd) {
        AggregateLifecycle.apply(new PersonRemoveEvent(this.personId, this.name, cmd.getFamily()));//invoke on method (on(UpdatePersonEvent event))
    }

    @EventSourcingHandler
    public void on(PersonRemoveEvent event) {
        this.name = event.getName();
        this.family = event.getFamily();
        this.personId = event.getPersonId();
    }
}
