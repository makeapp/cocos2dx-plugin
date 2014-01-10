package com.makeapp.android.jpa;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;
import javax.xml.parsers.DocumentBuilderFactory;

import com.makeapp.javase.log.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author <a href="mailto:yuanyou@shqianzhi.com">yuanyou</a>
 * @version $Date:10-11-22 ����5:55 $
 *          $Id$
 */
public class AndroidPersistenceProvider
        implements PersistenceProvider
{
    Map<String, PersistenceUnitEntity> persistenceUnitMap = new HashMap();

    Logger logger = Logger.getLogger(this);

    public AndroidPersistenceProvider()
    {
        InputStream in = getClass().getResourceAsStream("/persistence.xml");
        if (in == null) {
            logger.error("Invalid persistence.xml ");
            return;
        }
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            Document document = factory.newDocumentBuilder().parse(in);
            NodeList children = document.getChildNodes().item(0).getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                Node n = children.item(i);
                if ("persistence-unit".equals(n.getNodeName())) {
                    NodeList pus = n.getChildNodes();

                    PersistenceUnitEntity pu = new PersistenceUnitEntity();
                    pu.setName(getNodeAttribute(n, "name"));
                    persistenceUnitMap.put(pu.getName(), pu);

                    for (int j = 0; j < pus.getLength(); j++) {
                        Node pn = pus.item(j);
                        String nodeName = pus.item(j).getNodeName();
                        if ("properties".equals(nodeName)) {
                            for (int k = 0; k < pn.getChildNodes().getLength(); k++) {
                                Node ppn = pn.getChildNodes().item(k);
                                String name = getNodeAttribute(ppn, "name");
                                String value = getNodeAttribute(ppn, "value");
                                if (name != null && value != null) {
                                    pu.setProperty(name, value);
                                }
                            }
                        } else if ("class".equals(nodeName)) {
                            String className = getNodeAttribute(pn);
                            pu.addClass(className);
                        }
                    }
                }
            }
//            logger.info("" + persistenceUnitMap);
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    public static String getNodeAttribute(Node testCase, String name)
    {
        if (testCase == null) {
            return null;
        }
        NamedNodeMap namedNodeMap = testCase.getAttributes();

        if (namedNodeMap != null) {
            return namedNodeMap.getNamedItem(name).getNodeValue();
        }
        return null;
    }

    public static String getNodeAttribute(Node testCase)
    {
        if (testCase == null) {
            return null;
        }

        return testCase.getTextContent();
    }

    public EntityManagerFactory createEntityManagerFactory(String emName)
    {
        return createEntityManagerFactory(emName, null);
    }

    public EntityManagerFactory createEntityManagerFactory(String emName, Map map)
    {
        if (persistenceUnitMap.containsKey(emName)) {
            return new AndroidEntityManagerFactory(persistenceUnitMap.get(emName));
        }
        return null;
    }

//    public EntityManagerFactory createContainerEntityManagerFactory(PersistenceUnitInfo info, Map map)
//    {
//        return createEntityManagerFactory(info.getPersistenceUnitName(), null);
//    }

//    public ProviderUtil getProviderUtil()
//    {
//        return null;
//    }
}