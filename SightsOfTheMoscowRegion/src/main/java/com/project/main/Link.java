package com.project.main;

import lombok.Getter;
import lombok.Setter;

public class Link {

    @Setter
    @Getter
    String nameOfTown;

    @Setter
    @Getter
    String link;

    public Link(String nameOfTown, String link) {
        this.nameOfTown = nameOfTown;
        this.link = link;
    }

}
