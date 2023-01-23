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
@Table(name = "Transakcija")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transakcija.findAll", query = "SELECT t FROM Transakcija t"),
    @NamedQuery(name = "Transakcija.findByIdTransakcija", query = "SELECT t FROM Transakcija t WHERE t.idTransakcija = :idTransakcija"),
    @NamedQuery(name = "Transakcija.findBySumaNovca", query = "SELECT t FROM Transakcija t WHERE t.sumaNovca = :sumaNovca"),
    @NamedQuery(name = "Transakcija.findByVremePlacanja", query = "SELECT t FROM Transakcija t WHERE t.vremePlacanja = :vremePlacanja"),
    @NamedQuery(name = "Transakcija.findByIdNarudzbina", query = "SELECT t FROM Transakcija t WHERE t.idNarudzbina = :idNarudzbina")})
public class Transakcija implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTransakcija")
    private Integer idTransakcija;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SumaNovca")
    private float sumaNovca;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VremePlacanja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vremePlacanja;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idNarudzbina")
    private int idNarudzbina;

    public Transakcija() {
    }

    public Transakcija(Integer idTransakcija) {
        this.idTransakcija = idTransakcija;
    }

    public Transakcija(Integer idTransakcija, float sumaNovca, Date vremePlacanja, int idNarudzbina) {
        this.idTransakcija = idTransakcija;
        this.sumaNovca = sumaNovca;
        this.vremePlacanja = vremePlacanja;
        this.idNarudzbina = idNarudzbina;
    }

    public Integer getIdTransakcija() {
        return idTransakcija;
    }

    public void setIdTransakcija(Integer idTransakcija) {
        this.idTransakcija = idTransakcija;
    }

    public float getSumaNovca() {
        return sumaNovca;
    }

    public void setSumaNovca(float sumaNovca) {
        this.sumaNovca = sumaNovca;
    }

    public Date getVremePlacanja() {
        return vremePlacanja;
    }

    public void setVremePlacanja(Date vremePlacanja) {
        this.vremePlacanja = vremePlacanja;
    }

    public int getIdNarudzbina() {
        return idNarudzbina;
    }

    public void setIdNarudzbina(int idNarudzbina) {
        this.idNarudzbina = idNarudzbina;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTransakcija != null ? idTransakcija.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transakcija)) {
            return false;
        }
        Transakcija other = (Transakcija) object;
        if ((this.idTransakcija == null && other.idTransakcija != null) || (this.idTransakcija != null && !this.idTransakcija.equals(other.idTransakcija))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Transakcija[ idTransakcija=" + idTransakcija + " ]";
    }
    
}
