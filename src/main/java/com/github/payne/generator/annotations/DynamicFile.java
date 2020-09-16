package com.github.payne.generator.annotations;

/**
 * Decorator annotation to help navigate from a class to the resources file which it uses to
 * generate content dynamically.
 */
public @interface DynamicFile {

    String value();
}
