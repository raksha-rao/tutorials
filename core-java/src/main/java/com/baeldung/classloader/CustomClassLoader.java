package com.baeldung.classloader;

import java.io.*;

public class CustomClassLoader extends ClassLoader {

    public CustomClassLoader(ClassLoader parent){
        super(parent);
    }

    public Class getClass(String name) throws ClassNotFoundException {
        byte[] b = loadClassFromFile(name);
        return defineClass(name, b, 0, b.length);
    }

    @Override
    public Class loadClass(String name) throws ClassNotFoundException {

        if (name.startsWith("com.baeldung")) {
            System.out.println("Loading Class from Custom Class Loader");
            return getClass(name);
        }
        return super.loadClass(name);
    }

    private byte[] loadClassFromFile(String fileName)  {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(
                fileName.replace('.', File.separatorChar) + ".class");
        byte[] buffer;
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        int nextValue = 0;
        try {
            while ( (nextValue = inputStream.read()) != -1 ) {
                byteStream.write(nextValue);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        buffer = byteStream.toByteArray();
        return buffer;
    }
}
