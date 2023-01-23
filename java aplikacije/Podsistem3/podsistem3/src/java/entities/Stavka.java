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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author remax
 */
@Entity
@Table(name = "Stavka")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stavka.findAll", query = "SELECT s FROM Stavka s"),
    @NamedQuery(name = "Stavka.findByIdStavka", query = "SELECT s FROM Stavka s WHERE s.idStavka = :idStavka"),
    @NamedQuery(name = "Stavka.findByIdNarudzbina", query = "SELECT s FROM Stavka s WHERE s.idNarudzbina = :idNarudzbina"),
    @NamedQuery(name = "Stavka.findByIdArtikal", query = "SELECT s FROM Stavka s WHERE s.idArtikal = :idArtikal"),
    @NamedQuery(name = "Stavka.findByKolicina", query = "SELECT s FROM Stavka s WHERE s.kolicina = :kolicina"),
    @NamedQuery(name = "Stavka.findByJedinicnaCena", query = "SELECT s FROM Stavka s WHERE s.jedinicnaCena = :jedinicnaCena")})
public class Stavka implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idStavka")
    private Integer idStavka;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idNarudzbina")
    private int idNarudzbina;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idArtikal")
    private int idArtikal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Kolicina")
    private int kolicina;
    @Basic(optional = false)
    @NotNull
    @Column(name = "JedinicnaCena")
    private float jedinicnaCena;

    public Stavka() {
    }

    public Stavka(Integer idStavka) {
        this.idStavka = idStavka;
    }

    public Stavka(Integer idStavka, int idNarudzbina, int idArtikal, int kolicina, float jedinicnaCena) {
        this.idStavka = idStavka;
        this.idNarudzbina = idNarudzbina;
        this.idArtikal = idArtikal;
        this.kolicina = kolicina;
        this.jedinicnaCena = jedinicnaCena;
    }

    public Integer getIdStavka() {
        return idStavka;
    }

    public void setIdStavka(Integer idStavka) {
        this.idStavka = idStavka;
    }

    public int getIdNarudzbina() {
        return idNarudzbina;
    }

    public void setIdNarudzbina(int idNarudzbina) {
        this.idNarudzbina = idNarudzbina;
    }

    public int getIdArtikal() {
        return idArtikal;
    }

    public void setIdArtikal(int idArtikal) {
        this.idArtikal = idArtikal;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public float getJedinicnaCena() {
        return jedinicnaCena;
    }

    public void setJedinicnaCena(float jedinicnaCena) {
        this.jedinicnaCena = jedinicnaCena;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idStavka != null ? idStavka.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Stavka)) {
            return false;
        }
        Stavka other = (Stavka) object;
        if ((this.idStavka == null && other.idStavka != null) || (this.idStavka != null && !this.idStavka.equals(other.idStavka))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Stavka[ idStavka=" + idStavka + " ]";
    }
    
}
