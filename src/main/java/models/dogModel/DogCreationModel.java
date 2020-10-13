package models.dogModel;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@JsonPropertyOrder({"id", "category", "name", "photoUrls", "tags", "status"})
public class DogCreationModel {

    public DogCreationModel() {

    }

    int id;
    DogCreationCategory category;
    String name;
    List<String> photoUrls;
    List<DogCreationTags> tags;
    String status;


}



