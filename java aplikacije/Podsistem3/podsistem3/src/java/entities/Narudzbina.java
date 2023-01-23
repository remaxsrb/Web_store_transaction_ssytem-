/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author remax
 */
@Entity
@Table(name = "Narudzbina")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Narudzbina.findAll", query = "SELECT n FROM Narudzbina n"),
    @NamedQuery(name = "Narudzbina.findByIdNarudzbina", query = "SELECT n FROM Narudzbina n WHERE n.idNarudzbina = :idNarudzbina"),
    @NamedQuery(name = "Narudzbina.findByUkupnaCena", query = "SELECT n FROM Narudzbina n WHERE n.ukupnaCena = :ukupnaCena"),
    @NamedQuery(name = "Narudzbina.findByVremeKreiranja", query = "SELECT n FROM Narudzbina n WHERE n.vremeKreiranja = :vremeKreiranja")})
public class Narudzbina implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idNarudzbina")
    private Integer idNarudzbina;
    @Basic(optional = false)
    @NotNull
    @Column(name = "UkupnaCena")
    private float ukupnaCena;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VremeKreiranja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vremeKreiranja;
    @JoinColumn(name = "Adresa", referencedColumnName = "idAdresa")
    @ManyToOne(optional = false)
    private Adresa adresa;
    @JoinColumn(name = "Grad", referencedColumnName = "idGrad")
    @ManyToOne(optional = false)
    private Grad grad;

    public Narudzbina() {
    }

    public Narudzbina(Integer idNarudzbina) {
        this.idNarudzbina = idNarudzbina;
    }

    public Narudzbina(Integer idNarudzbina, float ukupnaCena, Date vremeKreiranja) {
        this.idNarudzbina = idNarudzbina;
        this.ukupnaCena = ukupnaCena;
        this.vremeKreiranja = vremeKreiranja;
    }

    public Integer getIdNarudzbina() {
        return idNarudzbina;
    }

    public void setIdNarudzbina(Integer idNarudzbina) {
        this.idNarudzbina = idNarudzbina;
    }

    public float getUkupnaCena() {
        return ukupnaCena;
    }

    public void setUkupnaCena(float ukupnaCena) {
        this.ukupnaCena = ukupnaCena;
    }

    public Date getVremeKreiranja() {
        return vremeKreiranja;
    }

    public void setVremeKreiranja(Date vremeKreiranja) {
        this.vremeKreiranja = vremeKreiranja;
    }

    public Adresa getAdresa() {
        return adresa;
    }

    public void setAdresa(Adresa adresa) {
        this.adresa = adresa;
    }

    public Grad getGrad() {
        return grad;
    }

    public void setGrad(Grad grad) {
        this.grad = grad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNarudzbina != null ? idNarudzbina.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Narudzbina)) {
            return false;
        }
        Narudzbina other = (Narudzbina) object;
        if ((this.idNarudzbina == null && other.idNarudzbina != null) || (this.idNarudzbina != null && !this.idNarudzbina.equals(other.idNarudzbina))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Narudzbina[ idNarudzbina=" + idNarudzbina + " ]";
    }
    
}
