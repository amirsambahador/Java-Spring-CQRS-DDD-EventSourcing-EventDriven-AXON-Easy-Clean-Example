package org.j2os.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/*
    Bahador, Amirsam
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonRemoveCommand {
    @TargetAggregateIdentifier
    private String personId;
    private String name;
    private String family;

}


