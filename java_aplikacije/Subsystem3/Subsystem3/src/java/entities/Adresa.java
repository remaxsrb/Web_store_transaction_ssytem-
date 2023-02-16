/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author remax
 */
@Entity
@Table(name = "Adresa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Adresa.findAll", query = "SELECT a FROM Adresa a"),
    @NamedQuery(name = "Adresa.findByIdAdresa", query = "SELECT a FROM Adresa a WHERE a.idAdresa = :idAdresa"),
    @NamedQuery(name = "Adresa.findByUlica", query = "SELECT a FROM Adresa a WHERE a.ulica = :ulica"),
    @NamedQuery(name = "Adresa.findByBroj", query = "SELECT a FROM Adresa a WHERE a.broj = :broj"),
    @NamedQuery(name = "Adresa.findByUlicaBroj", query = "SELECT a FROM Adresa a WHERE a.broj = :broj AND a.ulica = :ulica")})
public class Adresa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAdresa")
    private Integer idAdresa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Ulica")
    private String ulica;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Broj")
    private int broj;

    public Adresa() {
    }

    public Adresa(Integer idAdresa) {
        this.idAdresa = idAdresa;
    }

    public Adresa(Integer idAdresa, String ulica, int broj) {
        this.idAdresa = idAdresa;
        this.ulica = ulica;
        this.broj = broj;
    }

    public Integer getIdAdresa() {
        return idAdresa;
    }

    public void setIdAdresa(Integer idAdresa) {
        this.idAdresa = idAdresa;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public int getBroj() {
        return broj;
    }

    public void setBroj(int broj) {
        this.broj = broj;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAdresa != null ? idAdresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Adresa)) {
            return false;
        }
        Adresa other = (Adresa) object;
        if ((this.idAdresa == null && other.idAdresa != null) || (this.idAdresa != null && !this.idAdresa.equals(other.idAdresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Adresa[ idAdresa=" + idAdresa + " ]";
    }
    
}
