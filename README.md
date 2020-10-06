[![](https://jitpack.io/v/payne911/gdx-setup-generator.svg)](https://jitpack.io/#payne911/gdx-setup-generator)
![build](https://github.com/payne911/gdx-setup-generator/workflows/build/badge.svg)

# API
A "consuming" application should only mostly interact with ``Generator#generateFileStructure``:
* Input is ``GeneratorConfigs``.
* Ouput is ``GeneratedProject``.

In this way, the logic's internals are completely hidden from the user.

## Using the output
The ``GeneratedProject`` contains a possible error message, and a virtual file structure.

The ``VirtualFileSystem`` class contained in the generated project is a custom tree (data structure) implementation. The root is obtained via ``#getRoot()``. All nodes of the tree are ``FileNode``, and navigation happens through those (via ``#getChildren()``).

```java
    private void generate(GeneratorConfigs input) {
        Generator generator = new Generator();
        GeneratedProject output = generator.generateFileStructure(input);
        output.getVirtualFileSystem().depthFirstTraversal((node, depth) -> {
            // you do whatever you want in here... you'll probably want to actually save those files, for example
            System.out.println("depth " + depth + " | folder? " + node.isFolder() + " | node path: " + node.getFullPath);
        });
    }
```

# Documentation
This project is meant to be used as a black-box dependency that generates libGDX projects according to different inputs. A consuming client would only concern itself with the ``generator`` package.

A maintainer would have to play around in the ``logic`` package. Some key things to keep in mind, for maintainers:
* Files are organized in the ``resources`` in mostly two categories: the ones within the ``static`` directory are meant to simply be copy-pasted, whereas the ``dynamic`` ones are meant to have content injected into them.
* The content gets injected where there are "keys": `${thisIsAnExample}`.
* The ``DynamicFile`` class takes care of automatically replacing certain specific keys. The most common ones are: ``${corePackage}``, ``${mainClass}``, and ``${dependencies}``.
* The content for the keys automatically replaced comes from the ``GeneratorConfigs`` class that a user would have given to the generator.
* The paths leading to a file in the ``resources`` folder will always be a full path since that allows the IDE to index it. This mean you can ``Ctrl+Click`` on those path strings and the concerned file will automatically be opened for you (and you can also use ``Find Usage`` on a ``resources`` file to know where in the ``logic`` package it might be touched).
* `FileUtils` does pretty much all of the work related to actual ``resources`` files (be it read them, or replace content in them).
* It was a design decision to ensure that the classes in the ``generator`` package do not know about any class in the ``logic`` package.
* In its current state, the Third-Party support is unstable because the community hasn't yet decided if that should be supported by this current project.

# Tests
IntelliJ Run Configurations are provided out-of-the-box with the repository.

You can use those to test the implementation of the generator in different aspects:

## ``Run tests``
Automated tests are part of the development process and the CI pipeline.

After any major changes, you can quickly test if some fundamental parts of the generator were affected.

## ``Visualizer``
To help with quickly reviewing the content of the generated output according to different input, a GUI application has been set up in the ``test`` folder.

Below is a sketch of the basic idea, followed by a gif of the beta version of an implementation.

![VisualizerSketch](media/visualizer_sketch.png)

![VisualizerGif](media/poc_demo.gif)

# liftoff
This is an effort to extract the logic from the [``liftoff``](https://github.com/tommyettinger/gdx-liftoff) project which couples the Model and the View together.

For the most part, the underlying parts of the logic remained the same.

Differences:
* One of the key differences is that instead of generating the project by going from one input-option to the next (which was the natural choice for an approach using ``LML-MVC``), this rather focuses the generation on each separated file.
As an example, ``liftoff`` had a class which took the "Kotlin Language Option" and acted on the properties of the different other components affected by that choice.
This current project instead focuses on specific generated files instead: thus, the "Kotlin Language Option" configuration is observed within the class which takes care of generating the ``android`` module's ``build.gradle`` file (and all other files related, separately).
While this file-oriented approach breaks a few software engineering principles, I feel like it's a more natural way of interacting with the code: it's easier to think of our desires in terms of the convention-based generated file structure rather than in terms of the arbitrarily-structured input.
* As a corollary, it's easier to maintain the code because a file's content is always completely determined at its instantiation, rather than spanning through different classes' processes.
* Possibility to recursively copy the content of a folder in the ``resources``.
* Another interesting difference is the existence of "dynamic files": instead of having text-blocks in the code, certain files are part of the resources and include "keys" at specific places which are to be dynamically replaced in the code.
* Moreover, the project was ported from Kotlin to Java to increase maintainability on the long-term (as not everyone knows Kotlin). The secondary goal is to facilitate an eventually GWT-compatible version.

## Commits
A few of the more recent commits from TEttinger weren't yet ported in:

* https://github.com/tommyettinger/gdx-liftoff/commit/8f4c20e2fb952a31be762091d0788af07f18afe4
* https://github.com/tommyettinger/gdx-liftoff/commit/9d06e3d524061c6090215a0b0a0c839428c7232b

## Credits
Thanks to the creators and maintainers of the ``liftoff`` project, from which I've taken most of the logic to build this service:
* [TEttinger](https://github.com/tommyettinger)
* [czyzby](https://github.com/czyzby)

# Concerns
A few concerns remain.

## GWT-compatibility
For GWT-compatibility, this needs to be changed:

* ``FileUtils#readResourceFile`` uses a ClassLoader
* ``FileUtils#replaceResourceFileContent`` uses a Regex
* ``StringUtils#keepAlphaNumerics`` uses a Regex
* ``LibGdxVersion#transform`` uses a Regex
* ``BuildGradleFile#getDependencyString`` uses a Regex

Once the project is GWT-compatible, it should be possible to generate a file structure directly from the frontend (no need for a server).

## Vulnerability
The replacement algorithm used for generating content dynamically from static files has a vulnerability.

It needs to check if the input text doesn't contain a key to avoid a "recursive" call which would explode the memory.

## Unanswered questions
* How many versions should this service support? It currently goes past ``1.9.6``, but maybe only ``1.9.10`` and onward are sufficient.
* Do we want to support libGDX Extensions and Third-Party libraries?
The fix is easy to integrate, but for now this project is a MVP (Minimal Viable Product).
