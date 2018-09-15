package com.pikerobodevils.lib.util;

import java.io.InputStream;
import java.net.URL;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public class RobotMetadata {
    public final String BUILD_USER;
    public final String BUILD_TIMESTAMP;
    public final String BUILD_COMMIT;
    public final String BUILD_VERSION;
    public final String BUILD_JDK;
    public final String BUILD_OS;
    public final String BUILD_TOOL;

    private RobotMetadata() {
        Manifest mf = getManifest(getClass());
        Attributes attributes = mf.getMainAttributes();
        BUILD_USER = attributes.getValue("Built-By");
        BUILD_TIMESTAMP = attributes.getValue("Build-Timestamp");
        BUILD_COMMIT = attributes.getValue("Build-Revision");
        BUILD_VERSION = attributes.getValue("Build-Version");
        BUILD_JDK = attributes.getValue("Build-Jdk");
        BUILD_OS = attributes.getValue("Build-OS");
        BUILD_TOOL = attributes.getValue("Created-By");

    }

    private static RobotMetadata mInstance;

    public static RobotMetadata getInstance() {
        if (mInstance == null) {
            mInstance = new RobotMetadata();
        }
        return mInstance;
    }

    private static Manifest getManifest(Class<?> clz) {
        String resource = "/" + clz.getName().replace(".", "/") + ".class";
        String fullPath = clz.getResource(resource).toString();
        String archivePath = fullPath.substring(0, fullPath.length() - resource.length());
        if (archivePath.endsWith("\\WEB-INF\\classes") || archivePath.endsWith("/WEB-INF/classes")) {
            archivePath = archivePath.substring(0, archivePath.length() - "/WEB-INF/classes".length()); // Required for wars
        }

        try (InputStream input = new URL(archivePath + "/META-INF/MANIFEST.MF").openStream()) {
            return new Manifest(input);
        } catch (Exception e) {
            throw new RuntimeException("Loading MANIFEST for class " + clz + " failed!", e);
        }
    }

    @Override
    public String toString() {
        return "RobotMetadata{" +
                "BUILD_USER='" + BUILD_USER + '\'' +
                ", BUILD_TIMESTAMP='" + BUILD_TIMESTAMP + '\'' +
                ", BUILD_COMMIT='" + BUILD_COMMIT + '\'' +
                ", BUILD_VERSION='" + BUILD_VERSION + '\'' +
                ", BUILD_JDK='" + BUILD_JDK + '\'' +
                ", BUILD_OS='" + BUILD_OS + '\'' +
                ", BUILD_TOOL='" + BUILD_TOOL + '\'' +
                '}';
    }
}
