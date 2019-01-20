/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba.calculadorasoap;

import com.prueba.calculadorasoap.config.NewHibernateUtil;
import com.prueba.calculadorasoap.entity.Operacion;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import net.minidev.json.parser.ParseException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.tempuri.CalculatorSoap;

/**
 *
 * @author mac
 */
public class CalculadoraSOAP {

    public static void main(String[] args) {
        /* Se inicia la conexion con la base de datos */
        Session session = null;
        Transaction transaction = null;

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
        } catch (HibernateException exception) {
            if (session != null) {
                session.close();
            }
            if (transaction != null) {
                transaction.rollback();
            }
            JOptionPane.showMessageDialog(null, "No se pudo realizar la conexion: " + exception.getMessage());
        }

        /* Se crea el objeto que permite consumir el servicio y guardar el json */
        CalculadoraSOAP calculadoraSOAP = new CalculadoraSOAP();
        //calculadoraSOAP.consumirServicioCalculador(session);
        calculadoraSOAP.consumirServicioREST(session);
        transaction.commit();
        session.close();
        JOptionPane.showMessageDialog(null, "El programa ha terminado");
        System.out.println("Programa ha terminado");
        System.exit(0);
    }

    public void consumirServicioCalculador(Session session) {
        /* Se solicitan los valores a operar */
        int intA = 0;
        int intB = 0;
        try {
            intA = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el valor de intA"));
            intB = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el valor de intB"));
        } catch (NumberFormatException numberFormatException) {
            JOptionPane.showMessageDialog(null, "Debe ingresar valores enteros: " + numberFormatException.getMessage());
        }
        /* Se crea el objeto que consume el servicio */
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
        /* Se ejecutan las operaciones remotas */
        int intCAdd = calculatorSoap.add(intA, intB);
        int intCSub = calculatorSoap.subtract(intA, intB);
        int intCMul = calculatorSoap.multiply(intA, intB);
        /* Se almacenan los resultados en la base de datos */
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
    }

    public void consumirServicioREST(Session session) {
        JSONParser parser = new JSONParser();
        try {
            URL url = new URL("https://jsonplaceholder.typicode.com/users");
            URLConnection uRLConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(uRLConnection.getInputStream()));

            String inputLine = "";
            String jsonstring = "";
            while ((inputLine = bufferedReader.readLine()) != null) {
                jsonstring += inputLine;

            }
            System.out.println("es " + jsonstring);
            JSONArray listaUsers = (JSONArray) parser.parse(jsonstring);

            // Loop through each item
            for (Object object : listaUsers) {
                /* users */
                JSONObject user = (JSONObject) object;
                Long id = (Long) user.get("id");
                System.out.println("id : " + id);
                String title = (String) user.get("name");
                System.out.println("name : " + title);
                String username = (String) user.get("username");
                System.out.println("username : " + username);   
                String email = (String) user.get("email");
                System.out.println("email : " + email);    
                /* address */
                JSONObject address = (JSONObject) user.get("address");
                System.out.println("street : " + address.get("street"));
                System.out.println("suite : " + address.get("suite"));  
                System.out.println("city : " + address.get("city"));  
                System.out.println("zipcode : " + address.get("zipcode"));
                /* geo */
                JSONObject geo = (JSONObject) address.get("geo");
                System.out.println("lat : " + geo.get("lat"));  
                System.out.println("lng : " + geo.get("lng")); 
                /* phone */
                System.out.println("phone : " + user.get("phone")); 
                /* website */
                System.out.println("website : " + user.get("website")); 
                /* company */
                JSONObject company = (JSONObject) user.get("company");
                System.out.println("name : " + company.get("name")); 
                System.out.println("catchPhrase : " + company.get("catchPhrase")); 
                System.out.println("bs : " + company.get("bs")); 
                System.out.println("\n");
            }
            bufferedReader.close();
        } catch (FileNotFoundException | org.json.simple.parser.ParseException exception) {
            JOptionPane.showMessageDialog(null, "Error: " + exception.getMessage());
            Logger.getLogger(CalculadoraSOAP.class.getName()).log(Level.SEVERE, null, exception);
        } catch (IOException ex) {
            Logger.getLogger(CalculadoraSOAP.class.getName()).log(Level.SEVERE, null, ex);
        }

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
    }
}
