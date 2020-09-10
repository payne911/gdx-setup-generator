package com.github.payne.generator;

import java.util.Map;

public interface IGenerator {

    Map<String, byte[]> generateFileStructure(GeneratorModel generatorModel);
}
