package dev.ithundxr.returnofonlyin.mixin.plugin;

import cpw.mods.modlauncher.LaunchPluginHandler;
import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
import dev.ithundxr.returnofonlyin.internal.Variables;
import dev.ithundxr.returnofonlyin.services.ClientOnlyLaunchPluginService;
import org.objectweb.asm.tree.ClassNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MixinPlugin implements IMixinConfigPlugin {
    private static final Logger LOGGER = LoggerFactory.getLogger(MixinPlugin.class);

    @Override
    public void onLoad(String mixinPackage) {
        final LaunchPluginHandler launchPluginHandler;
        final Map<String, ILaunchPluginService> plugins;

        try {
            launchPluginHandler = (LaunchPluginHandler) MethodHandles.privateLookupIn(Launcher.class, MethodHandles.lookup())
                    .findVarHandle(Launcher.class, "launchPlugins", LaunchPluginHandler.class)
                    .get(Launcher.INSTANCE);
            //noinspection unchecked
            plugins = (Map<String, ILaunchPluginService>) MethodHandles.privateLookupIn(LaunchPluginHandler.class, MethodHandles.lookup())
                    .findVarHandle(LaunchPluginHandler.class, "plugins", Map.class)
                    .get(launchPluginHandler);

            ILaunchPluginService service = new ClientOnlyLaunchPluginService(Variables.PACKAGES_TO_TRANSFORM, Variables.CLASSES_TO_TRANSFORM);
            plugins.put(service.name(), service);
        } catch(Throwable e) {
            LOGGER.error(e.toString());
        }
    }

    @Override
    public String getRefMapperConfig() { return null; } // DEFAULT

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) { return true; } // DEFAULT

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) { } // NO-OP

    @Override
    public List<String> getMixins() { return null; } // DEFAULT

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) { } // NO-OP

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) { } // NO-OP
}
