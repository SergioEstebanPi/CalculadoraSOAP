package com.prueba.calculadorasoap.entity;
// Generated 20/01/2019 11:49:57 AM by Hibernate Tools 4.3.1


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Operacion generated by hbm2java
 */
@Entity
@Table(name="operacion"
    ,catalog="calculadorasoap"
)
public class Operacion  implements java.io.Serializable {


     private Integer id;
     private Integer intA;
     private Integer intB;
     private Integer intCadd;
     private Integer intCsub;
     private Integer intCmul;
     private Integer intCdiv;
     private Date fecha;

    public Operacion() {
    }

	
    public Operacion(Date fecha) {
        this.fecha = fecha;
    }
    public Operacion(Integer intA, Integer intB, Integer intCadd, Integer intCsub, Integer intCmul, Integer intCdiv, Date fecha) {
       this.intA = intA;
       this.intB = intB;
       this.intCadd = intCadd;
       this.intCsub = intCsub;
       this.intCmul = intCmul;
       this.intCdiv = intCdiv;
       this.fecha = fecha;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    
    @Column(name="intA")
    public Integer getIntA() {
        return this.intA;
    }
    
    public void setIntA(Integer intA) {
        this.intA = intA;
    }

    
    @Column(name="intB")
    public Integer getIntB() {
        return this.intB;
    }
    
    public void setIntB(Integer intB) {
        this.intB = intB;
    }

    
    @Column(name="intCAdd")
    public Integer getIntCadd() {
        return this.intCadd;
    }
    
    public void setIntCadd(Integer intCadd) {
        this.intCadd = intCadd;
    }

    
    @Column(name="intCSub")
    public Integer getIntCsub() {
        return this.intCsub;
    }
    
    public void setIntCsub(Integer intCsub) {
        this.intCsub = intCsub;
    }

    
    @Column(name="intCMul")
    public Integer getIntCmul() {
        return this.intCmul;
    }
    
    public void setIntCmul(Integer intCmul) {
        this.intCmul = intCmul;
    }

    
    @Column(name="intCDiv")
    public Integer getIntCdiv() {
        return this.intCdiv;
    }
    
    public void setIntCdiv(Integer intCdiv) {
        this.intCdiv = intCdiv;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="fecha", nullable=false, length=10)
    public Date getFecha() {
        return this.fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }




}


