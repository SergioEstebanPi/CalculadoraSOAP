/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba.calculadorasoap;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import org.tempuri.Calculator;
import org.tempuri.CalculatorSoap;

/**
 *
 * @author mac
 */
public class CalculadoraSOAP {

    public static void main(String[] args) {
        try {
            System.out.println("hello");
            URL url = new URL("http://www.dneonline.com/calculator.asmx?WSDL");
            //URL url = new URL("http://localhost:7779/calculator");
            
            // 1st argument service URI, refer to wsdl document above NAMESPACE
            // 2nd argument is service name, refer to wsdl document above
            QName qname = new QName("http://tempuri.org/", "Calculator");
            //QName qname = new QName("http://calculator/", "CalculatorImplService");
            Service service = Service.create(url, qname);
            System.out.println(service.getServiceName());
            CalculatorSoap calculator = service.getPort(CalculatorSoap.class);
            
            System.out.println(calculator.add(8, 6));
            System.out.println(calculator.subtract(8, 6));
            System.out.println(calculator.multiply(8, 6));
            System.out.println(calculator.divide(8, 6));
        } catch (MalformedURLException ex) {
            Logger.getLogger(CalculadoraSOAP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
