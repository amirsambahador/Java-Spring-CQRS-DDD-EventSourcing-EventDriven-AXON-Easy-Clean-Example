package org.j2os.query;

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
public class PersonFindOneQuery {

    private String id;
    private String name;
    private String family;
}
