package com.github.payne.generator.annotations;

/**
 * Decorator annotation to help navigate from a class to the resources file which it uses to
 * generate content dynamically.
 * <p>
 * Using your IDE, you should be able to {@code ctrl + click} on the file in the annotation and
 * it'll automatically open it for you.
 */
public @interface DynamicFile {

    String value();
}
