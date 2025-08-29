package com.github.kayjamlang.backend.jvm.utils;

public class NamesUtils {
    public static String normalizeNamespace(String namespace) {
        if (namespace.charAt(0) == '\\') {
            namespace = namespace.substring(1);
        }

        return namespace.replace('\\', '/');
    }

    public static String getFQNFromNamespaceAndSimpleName(String namespace, String simpleName) {
        String classFQN;
        if (namespace == null || namespace.isEmpty() || namespace.equals("\\")) {
            classFQN = "\\" + simpleName;
        } else {
            classFQN = namespace + "\\" + simpleName;
        }

        return classFQN;
    }

    /**
     * Extracts simple name from KayJam FQKN.
     * As example, "\App\Test\Class" -> "Class"
     *
     * @param fqkn Full qualified name.
     * @return Simple name.
     */
    public static String getSimpleNameFromFQN(String fqkn) {
        if (fqkn == null || fqkn.isEmpty()) return "";
        int lastBackslash = fqkn.lastIndexOf('\\');
        if (lastBackslash != -1) {
            return fqkn.substring(lastBackslash + 1);
        }

        return fqkn;
    }

    public static String convertSomeFQNToJavaFQN(String someFQN) {
        if (someFQN == null || someFQN.isEmpty()) return someFQN;
        if (someFQN.startsWith("\\")) {
            return someFQN.substring(1).replace("\\", ".");
        }
        return someFQN;
    }
}
