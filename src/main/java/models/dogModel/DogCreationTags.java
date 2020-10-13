package models.dogModel;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonPropertyOrder({"id", "name"})
@Getter
@AllArgsConstructor
public class DogCreationTags {
    public DogCreationTags(){

    }
    int id;
    String name;
}
