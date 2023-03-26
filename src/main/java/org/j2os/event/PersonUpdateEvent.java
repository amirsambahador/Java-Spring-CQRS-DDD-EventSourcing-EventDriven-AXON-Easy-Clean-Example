package org.j2os.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
    Bahador, Amirsam
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonUpdateEvent {
    private String personId;
    private String name;
    private String family;
}
