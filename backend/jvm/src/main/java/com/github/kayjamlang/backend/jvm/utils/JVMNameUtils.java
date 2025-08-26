package com.github.kayjamlang.backend.jvm.utils;

public class JVMNameUtils {
    private static String snakeToCamel(String str) {
        str = str.substring(0, 1).toUpperCase() + str.substring(1);
        StringBuilder builder = new StringBuilder(str);
        for (int i = 0; i < builder.length(); i++) {
            if (builder.charAt(i) == '_') {
                builder.deleteCharAt(i);
                builder.replace(
                        i, i + 1,
                        String.valueOf(
                                Character.toUpperCase(
                                        builder.charAt(i))));
            }
        }

        return builder.toString();
    }

    public static String normalizeNamespace(String namespace) {
        String[] parts = namespace.split("\\\\");
        StringBuilder result = new StringBuilder();
        for (String part : parts){
            if(part.isEmpty()){
                continue;
            }

            String newPart = snakeToCamel(part);
            result.append(newPart.substring(0, 1).toLowerCase())
                    .append(newPart.substring(1))
                    .append("/");
        }

        return result.substring(0, result.length() - 1);
    }

    public static String normalizeClassName(String classname) {
        return classname.substring(0, 1).toUpperCase() + classname.substring(1);
    }
}
