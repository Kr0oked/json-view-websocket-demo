package com.example.item;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Item {

    @JsonView(Views.Public.class)
    private Integer id;

    @JsonView(Views.Public.class)
    private String itemName;

    @JsonView(Views.Internal.class)
    private String ownerName;
}
