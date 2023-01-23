/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author remax
 */
@Entity
@Table(name = "Adresa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Adresa.findAll", query = "SELECT a FROM Adresa a"),
    @NamedQuery(name = "Adresa.findByIdAdresa", query = "SELECT a FROM Adresa a WHERE a.adresaPK.idAdresa = :idAdresa"),
    @NamedQuery(name = "Adresa.findByUlica", query = "SELECT a FROM Adresa a WHERE a.adresaPK.ulica = :ulica"),
    @NamedQuery(name = "Adresa.findByBroj", query = "SELECT a FROM Adresa a WHERE a.adresaPK.broj = :broj")})
public class Adresa implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AdresaPK adresaPK;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "adresa")
    private List<Narudzbina> narudzbinaList;

    public Adresa() {
    }

    public Adresa(AdresaPK adresaPK) {
        this.adresaPK = adresaPK;
    }

    public Adresa(int idAdresa, String ulica, int broj) {
        this.adresaPK = new AdresaPK(idAdresa, ulica, broj);
    }

    public AdresaPK getAdresaPK() {
        return adresaPK;
    }

    public void setAdresaPK(AdresaPK adresaPK) {
        this.adresaPK = adresaPK;
    }

    @XmlTransient
    public List<Narudzbina> getNarudzbinaList() {
        return narudzbinaList;
    }

    public void setNarudzbinaList(List<Narudzbina> narudzbinaList) {
        this.narudzbinaList = narudzbinaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (adresaPK != null ? adresaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Adresa)) {
            return false;
        }
        Adresa other = (Adresa) object;
        if ((this.adresaPK == null && other.adresaPK != null) || (this.adresaPK != null && !this.adresaPK.equals(other.adresaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Adresa[ adresaPK=" + adresaPK + " ]";
    }
    
}
