package com.mapfinal.resource.bundle;

import com.mapfinal.converter.ConverterManager;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ReadCdiUtils {

//    public static String cdiPath = "D:\\data\\conf.cdi";

    public static Envelope getCdiEnvelope(String cdiPath) {
        //1.创建DocumentBuilderFactory对象
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //2.创建DocumentBuilder对象
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document d = builder.parse(new File(cdiPath));
            NodeList sList = d.getElementsByTagName("EnvelopeN");
            //获取切片最大最小坐标
            Double xMin = null;
            Double yMin = null;
            Double xMax = null;
            Double yMax = null;
            for (int i = 0; i < sList.getLength(); i++) {
                Node node = sList.item(i);
                NodeList childNodes = node.getChildNodes();
                for (int j = 0; j < childNodes.getLength(); j++) {
                    if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                        String nodeName = childNodes.item(j).getNodeName();
                        String nodeValue = childNodes.item(j).getFirstChild().getNodeValue();
                        if ("XMin".equals(nodeName)) {
                            xMin = Double.valueOf(nodeValue);
                        }
                        if ("YMin".equals(nodeName)) {
                            yMin = Double.valueOf(nodeValue);
                        }
                        if ("XMax".equals(nodeName)) {
                            xMax = Double.valueOf(nodeValue);
                        }
                        if ("YMax".equals(nodeName)) {
                            yMax = Double.valueOf(nodeValue);
                        }
                        System.out.print(childNodes.item(j).getNodeName() + ":");
                        System.out.println(childNodes.item(j).getFirstChild().getNodeValue());
                    }
                }
            }
            Coordinate minMercatorCoordinate = new Coordinate(xMin, yMin);
            Coordinate maxMercatorCoordinate = new Coordinate(xMax, yMax);
            Coordinate minWGS84Coordinate = ConverterManager.me().mercatorToWgs84(minMercatorCoordinate);
            Coordinate maxWGS84Coordinate = ConverterManager.me().mercatorToWgs84(maxMercatorCoordinate);
            System.out.println("wgs84:" + minWGS84Coordinate);
            System.out.println("wgs84:" + maxWGS84Coordinate);
            return new Envelope(minWGS84Coordinate.getX(), maxWGS84Coordinate.getX(), minWGS84Coordinate.getY(), maxWGS84Coordinate.getY());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
