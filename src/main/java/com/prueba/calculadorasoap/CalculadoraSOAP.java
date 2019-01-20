/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba.calculadorasoap;

import com.prueba.calculadorasoap.config.NewHibernateUtil;
import com.prueba.calculadorasoap.entity.Operacion;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.tempuri.CalculatorSoap;

/**
 *
 * @author mac
 */
public class CalculadoraSOAP {
    
    public static void main(String[] args) {
        int intA = 0;
        int intB = 0;
        try {
            intA = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el valor de intA"));
            intB = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el valor de intB"));
        } catch (NumberFormatException numberFormatException) {
            JOptionPane.showMessageDialog(null, "Debe ingresar valores enteros: " + numberFormatException.getMessage());
        }
        URL url = null;
        try {
            url = new URL("http://www.dneonline.com/calculator.asmx?WSDL");
        } catch (MalformedURLException ex) {
            JOptionPane.showMessageDialog(null, "No fue posible realizar la conexion con el servicio: " + ex.getMessage());
            Logger.getLogger(CalculadoraSOAP.class.getName()).log(Level.SEVERE, null, ex);
        }
        QName qname = new QName("http://tempuri.org/", "Calculator");
        Service service = Service.create(url, qname);
        System.out.println(service.getServiceName());
        CalculatorSoap calculatorSoap = service.getPort(CalculatorSoap.class);
        
        int intCAdd = calculatorSoap.add(intA, intB);
        int intCSub = calculatorSoap.subtract(intA, intB);
        int intCMul = calculatorSoap.multiply(intA, intB);
        
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        
        Operacion operacion = new Operacion();
        operacion.setIntA(intA);
        operacion.setIntB(intB);
        operacion.setFecha(new Date());
        operacion.setIntCadd(intCAdd);
        operacion.setIntCsub(intCSub);
        operacion.setIntCmul(intCMul);
        if (intB == 0) {
            JOptionPane.showMessageDialog(null, "No es posible realizar la division por cero el resultado sera null");
            operacion.setIntCdiv(null);
        } else {
            operacion.setIntCdiv(calculatorSoap.divide(intA, intB));
        }
        session.save(operacion);

        /*

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

         */
        transaction.commit();
        
        session.close();
        //System.out.println("registros insertados");
        JOptionPane.showMessageDialog(null, "Se han registrado las operaciones correctamente");
        
    }
}
