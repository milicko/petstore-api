package models.dogModel;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonPropertyOrder({"id", "name"})
@Getter
@AllArgsConstructor
public class DogCreationCategory {
    public DogCreationCategory() {

    }

    int id;
    String name;
}
