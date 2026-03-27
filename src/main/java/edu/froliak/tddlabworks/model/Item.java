package edu.froliak.tddlabworks.model;

/*
  @author eugen
  @project tdd-labworks
  @class Item
  @version 1.0.0
  @since 3/27/2026 - 10.51
*/


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Document
public class Item extends AuditMetadata {
    @Id
    private String id;
    private String name;
    private String code;
    private String description;

    public Item(String name, String code, String description) {
        this.name = name;
        this.code = code;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;
        return getId().equals(item.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}