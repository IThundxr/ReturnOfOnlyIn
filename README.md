# The Return Of @OnlyIn

To begin using this library you'll need to import and JiJ the library which can be by doing the following:

```groovy
jarJar.enable()

repositories {
    ...
    maven { url "https://mvn.devos.one/snapshots" }
}

dependencies {
    ...
    jarJar(group: "dev.ithundxr.returnofonlyin", name: "ReturnOfOnlyIn", version: "[1.0.0-build.5,]")
}
```

You'll need to register your classes/packages to be transformed, this is quite simple and can be done like so:
1. Create a class that implements `IMixinConfigPlugin`
2. In your `mixins.json` add a field that points to the class you created, it should look like this `"plugin": "dev.ithundxr.examplemod.mixin.plugin.MixinPlugin"`
3. In the `onLoad()` method call the following methods to register packages/classes to be transformed (The documentation for these methods can be found in their java docs, your IDE should show them however if it does not you can view them [here](https://github.com/IThundxr/ReturnOfOnlyIn/blob/main/src/main/java/dev/ithundxr/returnofonlyin/ReturnOfOnlyIn.java))
   - `ReturnOfOnlyIn.addPackages(List<String>)`
   - `ReturnOfOnlyIn.addPackage(String)`
   - `ReturnOfOnlyIn.addClasses(List<String>)`
   - `ReturnOfOnlyIn.addClass(String)`
4. Any methods/fields annotated with @ClientOnly (which is provided by @ReturnOfOnlyIn) will be removed on the dedicated server if the class or package the class resides in is registered.
