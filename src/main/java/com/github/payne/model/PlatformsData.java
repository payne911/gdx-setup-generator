package com.github.payne.model;

import com.github.payne.generator.JAVA.model.enums.Platform;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PlatformsData extends UniqueItemsContainer<Platform> {

    public PlatformsData(Platform... platforms) {
        super(platforms);
        set.add(Platform.CORE); // enforcing CORE to always be present
    }
}
