package com.github.payne.generator.exceptions;

import java.util.List;

/**
 * See {@link com.github.payne.utils.FileUtils#readResourceFile(List)} for the original source.
 */
public class ResourceFileReadException extends RuntimeException {

    public ResourceFileReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
