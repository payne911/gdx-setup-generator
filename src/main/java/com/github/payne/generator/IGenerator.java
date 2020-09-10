package com.github.payne.generator;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.output.GeneratedProject;

public interface IGenerator {

    GeneratedProject generateFileStructure(GeneratorConfigs generatorConfigs);
}
