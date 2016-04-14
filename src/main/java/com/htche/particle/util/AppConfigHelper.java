package com.htche.particle.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppConfigHelper {

    private final String _APPPATH = "config/app.conf";
    public static Map<String, String> nodeMap = new HashMap<>();

    public AppConfigHelper() {
        try {
            String path = this.getClass().getClassLoader().getResource(_APPPATH).getPath();
            Document doc = XmlHelper.loadDocument(path);
            Element root = doc.getDocumentElement();
            List<Element> elements = XmlHelper.getChildElements(root, "add");
            if (elements != null && elements.size() > 0) {
                for (Element element : elements) {
                    String key = element.getAttribute("key");
                    String value = element.getAttribute("value");
                    nodeMap.put(key, value);
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
