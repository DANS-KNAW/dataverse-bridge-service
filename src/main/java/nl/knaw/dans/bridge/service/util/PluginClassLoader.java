package nl.knaw.dans.bridge.service.util;

import java.net.URL;
import java.net.URLClassLoader;

public class PluginClassLoader extends URLClassLoader {
    public PluginClassLoader(URL jarFileUrl) {
        super(new URL[] {jarFileUrl});
    }
}

