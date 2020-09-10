package com.github.payne.generator;

import com.github.payne.model.BasicData;
import com.github.payne.model.LanguagesData;
import lombok.Data;
import lombok.NonNull;

@Data
public class GeneratorModel {

    @NonNull
    private final BasicData basicData;

    {
        LanguagesData f = new LanguagesData();
    }
}
