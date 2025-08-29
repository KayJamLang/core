package com.github.kayjamlang.backend.jvm.impl;

import com.github.kayjamlang.backend.jvm.ClasspathClassProvider;
import com.github.kayjamlang.backend.jvm.SymbolRegistry;
import com.github.kayjamlang.backend.jvm.types.*;
import com.github.kayjamlang.backend.jvm.types.data.PropertyInfo;
import com.github.kayjamlang.core.opcodes.AccessType;
import org.objectweb.asm.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ASMClasspathClassProviderImpl implements ClasspathClassProvider {
    private final List<File> classpathEntries;

    public ASMClasspathClassProviderImpl(List<File> classpathEntries) {
        this.classpathEntries = classpathEntries;
    }

    @Override
    public ClassJVMType loadClass(SymbolRegistry symbolRegistry, String javaFQN) {
        String resourcePath = javaFQN.replace('.', '/') + ".class";

        try {
            InputStream is = null;
            for (File entry : classpathEntries) {
                is = findResource(entry, resourcePath);
                if (is != null) {
                    break;
                }
            }

            if (is == null) {
                is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath);
            }

            if (is != null) {
                ClassJVMType clazz = parseClass(symbolRegistry, is, javaFQN);
                is.close();

                return clazz;
            }
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Error loading class " + javaFQN + ": " + e.getMessage()
            );
        }

        return null;
    }

    private InputStream findResource(File entry, String resourcePath) throws IOException {
        if (entry.isDirectory()) {
            File classFile = new File(entry, resourcePath);
            return classFile.exists() ? new FileInputStream(classFile) : null;
        } else if (entry.isFile() && entry.getName().endsWith(".jar")) {
            JarFile jarFile = new JarFile(entry);
            JarEntry jarEntry = jarFile.getJarEntry(resourcePath);
            return jarEntry != null ? jarFile.getInputStream(jarEntry) : null;
        }
        return null;
    }

    private ClassJVMType parseClass(SymbolRegistry symbolRegistry, InputStream is, String javaFQN) throws IOException {
        ClassReader classReader = new ClassReader(is);

        ClassJVMType classInfo = new ClassJVMType(
                javaFQN,
                (classReader.getAccess() & Opcodes.ACC_INTERFACE) != 0 ?
                        ClassJVMType.ClassType.INTERFACE
                        : ClassJVMType.ClassType.CLASS
        );

        symbolRegistry.externalClassesCache.put(classInfo.name, classInfo);

        final ClassJVMType finalClassInfo = classInfo;
        classReader.accept(new ClassVisitor(Opcodes.ASM9) {
            @Override
            public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                if (superName != null) {
                    String superFQN = superName.replace('/', '.');
                    finalClassInfo.superType = symbolRegistry.findClass(superFQN);
                }

                if (interfaces != null) {
                    for (String ifaceInternalName : interfaces) {
                        String ifaceFQN = ifaceInternalName.replace('/', '.');
                        finalClassInfo.interfaces.add(symbolRegistry.findClass(ifaceFQN));
                    }
                }
            }

            @Override
            public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
                JVMType fieldJVMType = convertJvmDescriptorToJVMType(symbolRegistry, descriptor);
                AccessType fieldAccessType = convertAsmAccessToKayJamAccess(access);

                PropertyInfo fieldInfo = new PropertyInfo(name, fieldJVMType, fieldAccessType, null);
                finalClassInfo.properties.put(name, fieldInfo);

                return super.visitField(access, name, descriptor, signature, value);
            }

            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                Type methodType = Type.getMethodType(descriptor);
                List<JVMType> argTypes = new ArrayList<>();
                for (Type argAsmType : methodType.getArgumentTypes()) {
                    argTypes.add(convertAsmTypeToJVMType(symbolRegistry, argAsmType));
                }
                JVMType returnType = convertAsmTypeToJVMType(symbolRegistry, methodType.getReturnType());
                AccessType methodAccessType = convertAsmAccessToKayJamAccess(access);

//                FunctionInfo methodInfo = new FunctionInfo(name, argTypes, returnType, methodAccessType);
//                finalClassInfo.functions.put(name, methodInfo);

                return super.visitMethod(access, name, descriptor, signature, exceptions);
            }
        }, 0);

        return finalClassInfo;
    }

    private JVMType convertJvmDescriptorToJVMType(SymbolRegistry symbolRegistry, String descriptor) {
        Type asmType = Type.getType(descriptor);
        return convertAsmTypeToJVMType(symbolRegistry, asmType);
    }

    private JVMType convertAsmTypeToJVMType(SymbolRegistry symbolRegistry, Type asmType) {
        switch (asmType.getSort()) {
            case Type.BOOLEAN:
                return BooleanJVMType.INSTANCE;
            case Type.CHAR, Type.SHORT, Type.INT, Type.BYTE:
                return IntJVMType.INSTANCE;
            case Type.FLOAT, Type.DOUBLE:
                return DoubleJVMType.INSTANCE;
            case Type.LONG:
                return LongJVMType.INSTANCE;
            case Type.VOID:
                return VoidJVMType.INSTANCE;
            case Type.OBJECT:
                String className = asmType.getClassName();
                ClassJVMType resolvedClass = symbolRegistry.findClass(className);
                if (resolvedClass == null) {
                    throw new IllegalStateException("Could not resolve external class JVM type for " + className + ".");
                }

                return resolvedClass;
            case Type.ARRAY:
                // TODO: Обработка массивов (например, `[I` для `int[]`, `[Ljava/lang/String;` для `String[]`)
                return AnyJVMType.INSTANCE;
            default:
                return AnyJVMType.INSTANCE;
        }
    }

    private AccessType convertAsmAccessToKayJamAccess(int access) {
        if ((access & Opcodes.ACC_PUBLIC) != 0) {
            return AccessType.PUBLIC;
        } else if ((access & Opcodes.ACC_PROTECTED) != 0) {
            return AccessType.PROTECTED;
        } else if ((access & Opcodes.ACC_PRIVATE) != 0) {
            return AccessType.PRIVATE;
        } else {
            return AccessType.INTERNAL;
        }
    }
}