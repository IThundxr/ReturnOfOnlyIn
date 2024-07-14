package dev.ithundxr.returnofonlyin.services;

import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
import net.neoforged.fml.loading.FMLLoader;
import org.jetbrains.annotations.ApiStatus;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

@ApiStatus.Internal
public class ClientOnlyLaunchPluginService implements ILaunchPluginService {
    private static final EnumSet<Phase> YES = EnumSet.of(Phase.AFTER);
    private static final EnumSet<Phase> NO = EnumSet.noneOf(Phase.class);
    
    private final List<String> packagesToTransform;
    private final List<String> classesToTransform;

    public ClientOnlyLaunchPluginService(List<String> packagesToTransform, List<String> classesToTransform) {
        this.packagesToTransform = packagesToTransform;
        this.classesToTransform = classesToTransform;
    }

    @Override
    public String name() {
        return "returnofonlyin_clientonly_launchpluginservice";
    }

    @Override
    public EnumSet<Phase> handlesClass(Type classType, boolean isEmpty) {
        String className = classType.getClassName();
        
        for (String package_ : packagesToTransform) {
            if (className.contains(package_))
                return YES;
        }
        
        if (classesToTransform.contains(className))
            return YES;
        
        return NO;
    }

    @Override
    public boolean processClass(Phase phase, ClassNode classNode, Type classType) {
        processClassNode(classNode);
        return true;
    }
    
    public static void processClassNode(ClassNode classNode) {
        for (Iterator<FieldNode> it = classNode.fields.iterator(); it.hasNext();) {
            FieldNode fieldNode = it.next();

            List<AnnotationNode> annotationNodes = fieldNode.visibleAnnotations;

            if (annotationNodes != null) {
                for (AnnotationNode annotationNode : annotationNodes) {
                    if (annotationNode.desc.equals("Ldev/ithundxr/returnofonlyin/annotation/ClientOnly;") && FMLLoader.getDist().isDedicatedServer()) {
                        it.remove();
                    }
                }
            }
        }

        for (Iterator<MethodNode> it = classNode.methods.iterator(); it.hasNext();) {
            MethodNode methodNode = it.next();

            List<AnnotationNode> annotationNodes = methodNode.visibleAnnotations;

            if (annotationNodes != null) {
                for (AnnotationNode annotationNode : annotationNodes) {
                    if (annotationNode.desc.equals("Ldev/ithundxr/returnofonlyin/annotation/ClientOnly;") && FMLLoader.getDist().isDedicatedServer()) {
                        it.remove();
                    }
                }
            }
        }
    }
}
