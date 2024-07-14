package dev.ithundxr.returnofonlyin;

import dev.ithundxr.returnofonlyin.internal.Variables;

import java.util.List;

public class ReturnOfOnlyIn {
    /**
     * Add a list of packages in which @ClientOnly annotated methods and fields should be found, and removed
     * <p> 
     * Packages should be formatted like the following: {@code dev.ithundxr.mymod} or {@code dev.ithundxr.mymod.features},
     * They must be seperated with dots and not slashes
     * 
     * @param packages The list of packages to be transformed
     */
    public static void addPackages(List<String> packages) {
        Variables.PACKAGES_TO_TRANSFORM.addAll(packages);
    }

    /**
     * Add a package in which @ClientOnly annotated methods and fields should be found, and removed
     * <p> 
     * The package should be formatted like the following: {@code dev.ithundxr.mymod} or {@code dev.ithundxr.mymod.features},
     * It must be seperated with dots and not slashes
     * 
     * @param package_ The package to be transformed
     */
    public static void addPackage(String package_) {
        Variables.PACKAGES_TO_TRANSFORM.add(package_);
    }

    /**
     * Add a list of classes in which @ClientOnly annotated methods and fields should be found, and removed
     * <p> 
     * Classes should be formatted like the following: {@code dev.ithundxr.mymod.MainClass},
     * They must be seperated with dots and not slashes, It is recommended to use {@link org.objectweb.asm.Type#getType(Class)} to get the names
     * 
     * @param classes The list of classes to be transformed
     */
    public static void addClasses(List<String> classes) {
        Variables.CLASSES_TO_TRANSFORM.addAll(classes);
    }

    /**
     * Add a class in which @ClientOnly annotated methods and fields should be found, and removed
     * <p> 
     * The class should be formatted like the following: {@code dev.ithundxr.mymod.MainClass},
     * It must be seperated with dots and not slashes, It is recommended to use {@link org.objectweb.asm.Type#getType(Class)} to get the name
     * 
     * @param class_ The class to be transformed
     */
    public static void addClass(String class_) {
        Variables.CLASSES_TO_TRANSFORM.add(class_);
    }
}
