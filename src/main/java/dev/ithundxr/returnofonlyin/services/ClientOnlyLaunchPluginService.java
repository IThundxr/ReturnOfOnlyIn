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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
        Map<Integer, FieldNode> fieldNodeMap = new LinkedHashMap<>();
        Map<Integer, MethodNode> methodNodeMap = new LinkedHashMap<>();

        int fieldNodeNumber = 0;
        for (FieldNode fieldNode : classNode.fields) {
            fieldNodeMap.put(fieldNodeNumber, fieldNode);
            fieldNodeNumber++;
        }

        int methodNodeNumber = 0;
        for (MethodNode methodNode : classNode.methods) {
            methodNodeMap.put(methodNodeNumber, methodNode);
            methodNodeNumber++;
        }

        fieldNodeMap.forEach(((i, fieldNode) -> {
            List<AnnotationNode> annotationNodes = fieldNode.visibleAnnotations;

            if (annotationNodes != null) {
                for (AnnotationNode annotationNode : annotationNodes) {
                    if (annotationNode.desc.equals("Ldev/ithundxr/returnofonlyin/annotation/ClientOnly;") && FMLLoader.getDist().isDedicatedServer()) {
                        classNode.fields.remove(i.intValue());
                    }
                }
            }
        }));

        methodNodeMap.forEach(((i, methodNode) -> {
            List<AnnotationNode> annotationNodes = methodNode.visibleAnnotations;

            if (annotationNodes != null) {
                for (AnnotationNode annotationNode : annotationNodes) {
                    if (annotationNode.desc.equals("Ldev/ithundxr/returnofonlyin/annotation/ClientOnly;") && FMLLoader.getDist().isDedicatedServer()) {
                        classNode.methods.remove(i.intValue());
                    }
                }
            }
        }));
    }
}
