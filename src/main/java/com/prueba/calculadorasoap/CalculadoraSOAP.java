/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba.calculadorasoap;

import com.prueba.calculadorasoap.config.NewHibernateUtil;
import com.prueba.calculadorasoap.entity.Operacion;
import com.prueba.calculadorasoap.entity.User;
import java.awt.HeadlessException;
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
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
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
        String opcion = "";
        do {
            /* muestra el menu para seleccionar una opcion */
            opcion = JOptionPane.showInputDialog("MENU \n"
                    + "1. CalculadoraSOAP \n"
                    + "2. Guardar Users REST \n"
                    + "3. Borrar datos Users REST \n"
                    + "4. Salir del programa \n"
                    + "Seleccione una opcion:");

            /* Se crea el objeto que permite consumir el servicio y guardar el json */
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
                JOptionPane.showMessageDialog(null, "No se pudo realizar la conexion a la base de datos: " + exception.getMessage());
                return;
            }
            CalculadoraSOAP calculadoraSOAP = new CalculadoraSOAP();
            try {
                switch (opcion) {
                    case "1":
                        if (calculadoraSOAP.consumirServicioCalculador(session) == 0) {
                            transaction.commit();
                            JOptionPane.showMessageDialog(null, "Servicio calculadora consumido correctamente");
                        }
                        break;
                    case "2":
                        if (calculadoraSOAP.consumirServicioREST(session) == 0) {
                            transaction.commit();
                            JOptionPane.showMessageDialog(null, "Servicio REST consumido correctamente");
                        }
                        break;
                    case "3":
                        if (calculadoraSOAP.borrarDatosUserREST(session) == 0) {
                            transaction.commit();
                        }
                        break;
                    case "4":
                        System.exit(0);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Ha seleccionado una opcion incorrecta");
                        break;
                }
            } catch (HeadlessException | HibernateException exception) {
                JOptionPane.showMessageDialog(null, "No es posible insertar los registros borre la tabla antes con la opcion 3");
            }
            session.close();
        } while (!"4".equals(opcion));
        System.exit(0);
    }

    public int borrarDatosUserREST(Session session) {
        SQLQuery query = session.createSQLQuery("TRUNCATE TABLE USER;");
        if (query.executeUpdate() == 0) {
            JOptionPane.showMessageDialog(null, "Datos de user eliminados correctamente");
            return 0;
        } else {
            JOptionPane.showMessageDialog(null, "No fue posible eliminar los registros users");
            return -1;
        }
    }

    public int consumirServicioCalculador(Session session) {
        /* Se solicitan los valores a operar */
        int intA = 0;
        int intB = 0;
        try {
            intA = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el valor de intA"));
            intB = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el valor de intB"));
        } catch (NumberFormatException numberFormatException) {
            JOptionPane.showMessageDialog(null, "Debe ingresar valores enteros: " + numberFormatException.getMessage());
            return -1;
        }
        /* Se crea el objeto que consume el servicio */
        URL url = null;
        try {
            url = new URL("http://www.dneonline.com/calculator.asmx?WSDL");
        } catch (MalformedURLException ex) {
            JOptionPane.showMessageDialog(null, "No fue posible realizar la conexion con el servicio: " + ex.getMessage());
            Logger.getLogger(CalculadoraSOAP.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
        QName qname = new QName("http://tempuri.org/", "Calculator");
        Service service = Service.create(url, qname);
        CalculatorSoap calculatorSoap = service.getPort(CalculatorSoap.class);
        /* Se ejecutan las operaciones remotas */
        int intCAdd = calculatorSoap.add(intA, intB);
        int intCSub = calculatorSoap.subtract(intA, intB);
        int intCMul = calculatorSoap.multiply(intA, intB);
        Integer intCDiv = null;
        /* Se almacenan los resultados en la base de datos */
        Operacion operacion = new Operacion();
        operacion.setIntA(intA);
        operacion.setIntB(intB);
        operacion.setFecha(new Date());
        operacion.setIntCadd(intCAdd);
        operacion.setIntCsub(intCSub);
        operacion.setIntCmul(intCMul);
        if (intB == 0) {
            operacion.setIntCdiv(intCDiv);
        } else {
            intCDiv = calculatorSoap.divide(intA, intB);
            operacion.setIntCdiv(intCDiv);
        }
        String resultados
                = "Parametros: \n"
                + "intA = " + intA + ", intB = " + intB + "\n"
                + "Resultados: \n"
                + "suma = " + intCAdd + "\n"
                + "resta = " + intCSub + "\n"
                + "multiplicacion = " + intCMul + "\n"
                + "division = " + (intCDiv == null ? "Indefinido" : "" + intCDiv);
        session.save(operacion);
        JOptionPane.showMessageDialog(null, resultados);
        return 0;
    }

    public int consumirServicioREST(Session session) {
        /* conexion para obtener el JSON */
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
            JSONArray listaUsers = (JSONArray) parser.parse(jsonstring);
            for (Object object : listaUsers) {
                User user = new User();
                /* users */
                JSONObject jsonUser = (JSONObject) object;
                int id = Integer.parseInt(String.valueOf(jsonUser.get("id")));
                user.setId(id);
                user.setName((String) jsonUser.get("name"));
                user.setUsername((String) jsonUser.get("username"));
                user.setEmail((String) jsonUser.get("email"));
                /* address */
                JSONObject address = (JSONObject) jsonUser.get("address");
                user.setStreet((String) address.get("street"));
                user.setSuite((String) address.get("suite"));
                user.setCity((String) address.get("city"));
                user.setZipcode((String) address.get("zipcode"));
                /* geo */
                JSONObject geo = (JSONObject) address.get("geo");
                user.setLat(Double.parseDouble((String) geo.get("lat")));
                user.setLng(Double.parseDouble((String) geo.get("lng")));
                /* phone */
                user.setPhone((String) jsonUser.get("phone"));
                /* website */
                user.setWebsite((String) jsonUser.get("website"));
                /* company */
                JSONObject company = (JSONObject) jsonUser.get("company");
                user.setCompanyName((String) company.get("name"));
                user.setCatchPhrase((String) company.get("catchPhrase"));
                user.setBs((String) company.get("bs"));
                session.save(user);
            }
            bufferedReader.close();
        } catch (FileNotFoundException | org.json.simple.parser.ParseException exception) {
            JOptionPane.showMessageDialog(null, "Error: " + exception.getMessage());
            Logger.getLogger(CalculadoraSOAP.class.getName()).log(Level.SEVERE, null, exception);
            return -1;
        } catch (IOException ex) {
            Logger.getLogger(CalculadoraSOAP.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            return -1;
        }
        return 0;
    }
}
