/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba.calculadorasoap;

import config.NewHibernateUtil;
import entity.Company;
import entity.User;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.tempuri.CalculatorSoap;

/**
 *
 * @author mac
 */
public class CalculadoraSOAP {

    public static void main(String[] args) {
        URL url = null;
        try {
            url = new URL("http://www.dneonline.com/calculator.asmx?WSDL");
            //URL url = new URL("http://localhost:7779/calculator");
        } catch (MalformedURLException ex) {
            Logger.getLogger(CalculadoraSOAP.class.getName()).log(Level.SEVERE, null, ex);
        }
        // 1st argument service URI, refer to wsdl document above NAMESPACE
        // 2nd argument is service name, refer to wsdl document above
        QName qname = new QName("http://tempuri.org/", "Calculator");
        //QName qname = new QName("http://calculator/", "CalculatorImplService");
        Service service = Service.create(url, qname);
        System.out.println(service.getServiceName());
        CalculatorSoap calculatorSoap = service.getPort(CalculatorSoap.class);

        System.out.println(calculatorSoap.add(8, 6));
        System.out.println(calculatorSoap.subtract(8, 6));
        System.out.println(calculatorSoap.multiply(8, 6));
        System.out.println(calculatorSoap.divide(8, 6));

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Company company = new Company();
        company.setId(2);
        company.setCatchPhrase("something");
        company.setBs("other");
        session.save(company);

        Company company2 = new Company();
        company2.setId(3);
        company2.setCatchPhrase("something1");
        company2.setBs("other");
        session.save(company2);

        Company company3 = new Company();
        company3.setId(4);
        company3.setCatchPhrase("something2");
        company3.setBs("other");
        session.save(company3);

        User user = new User();
        user.setId(1);
        user.setUsername("programador123");
        user.setName("pepe");
        user.setEmail("pepe@hotmail.com");
        user.setCompany(company);
        user.setSuite("suite");
        user.setStreet("street");
        user.setCity("city");
        user.setLat(1.333);
        user.setLng(2.3333);
        user.setWebsite("");
        user.setZipcode("23512 15251235");
        user.setPhone("4133355153");
        session.save(user);

        transaction.commit();

        session.close();
        System.out.println("registros insertados");

    }
}
