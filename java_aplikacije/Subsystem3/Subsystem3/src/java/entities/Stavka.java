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
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
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
    @NamedQuery(name = "Stavka.findByKolicina", query = "SELECT s FROM Stavka s WHERE s.kolicina = :kolicina"),
    @NamedQuery(name = "Stavka.findByJedinicnaCena", query = "SELECT s FROM Stavka s WHERE s.jedinicnaCena = :jedinicnaCena"),
    @NamedQuery(name = "Stavka.findByNarudzbina", query = "SELECT s FROM Stavka s WHERE s.idNarudzbina = :narudzbina")})
public class Stavka implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "Kolicina")
    private int kolicina;
    @Basic(optional = false)
    @NotNull
    @Column(name = "JedinicnaCena")
    private float jedinicnaCena;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idStavka")
    private Integer idStavka;
    @JoinColumn(name = "idArtikal", referencedColumnName = "idArtikal")
    @OneToOne(optional = false)
    private Artikal idArtikal;
    @JoinColumn(name = "idNarudzbina", referencedColumnName = "idNarudzbina")
    @OneToOne(optional = false)
    private Narudzbina idNarudzbina;

    public Stavka() {
    }

    public Stavka(Integer idStavka) {
        this.idStavka = idStavka;
    }

    public Stavka(Integer idStavka, int kolicina, float jedinicnaCena) {
        this.idStavka = idStavka;
        this.kolicina = kolicina;
        this.jedinicnaCena = jedinicnaCena;
    }

    public Integer getIdStavka() {
        return idStavka;
    }

    public void setIdStavka(Integer idStavka) {
        this.idStavka = idStavka;
    }


    public Artikal getIdArtikal() {
        return idArtikal;
    }

    public void setIdArtikal(Artikal idArtikal) {
        this.idArtikal = idArtikal;
    }

    public Narudzbina getIdNarudzbina() {
        return idNarudzbina;
    }

    public void setIdNarudzbina(Narudzbina idNarudzbina) {
        this.idNarudzbina = idNarudzbina;
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
    
}
