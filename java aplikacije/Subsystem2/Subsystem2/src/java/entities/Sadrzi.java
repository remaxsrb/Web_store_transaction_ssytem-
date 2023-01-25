/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "Sadrzi")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sadrzi.findAll", query = "SELECT s FROM Sadrzi s"),
    @NamedQuery(name = "Sadrzi.findByIdKorpa", query = "SELECT s FROM Sadrzi s WHERE s.sadrziPK.idKorpa = :idKorpa"),
    @NamedQuery(name = "Sadrzi.findByIdArtikal", query = "SELECT s FROM Sadrzi s WHERE s.sadrziPK.idArtikal = :idArtikal"),
    @NamedQuery(name = "Sadrzi.findByKolicinaArtikla", query = "SELECT s FROM Sadrzi s WHERE s.kolicinaArtikla = :kolicinaArtikla")})
public class Sadrzi implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SadrziPK sadrziPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "KolicinaArtikla")
    private int kolicinaArtikla;
    @JoinColumn(name = "idArtikal", referencedColumnName = "idArtikal", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Artikal artikal;
    @JoinColumn(name = "idKorpa", referencedColumnName = "idKorpa", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Korpa korpa;

    public Sadrzi() {
    }

    public Sadrzi(SadrziPK sadrziPK) {
        this.sadrziPK = sadrziPK;
    }

    public Sadrzi(SadrziPK sadrziPK, int kolicinaArtikla) {
        this.sadrziPK = sadrziPK;
        this.kolicinaArtikla = kolicinaArtikla;
    }

    public Sadrzi(int idKorpa, int idArtikal) {
        this.sadrziPK = new SadrziPK(idKorpa, idArtikal);
    }

    public SadrziPK getSadrziPK() {
        return sadrziPK;
    }

    public void setSadrziPK(SadrziPK sadrziPK) {
        this.sadrziPK = sadrziPK;
    }

    public int getKolicinaArtikla() {
        return kolicinaArtikla;
    }

    public void setKolicinaArtikla(int kolicinaArtikla) {
        this.kolicinaArtikla = kolicinaArtikla;
    }

    public Artikal getArtikal() {
        return artikal;
    }

    public void setArtikal(Artikal artikal) {
        this.artikal = artikal;
    }

    public Korpa getKorpa() {
        return korpa;
    }

    public void setKorpa(Korpa korpa) {
        this.korpa = korpa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sadrziPK != null ? sadrziPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sadrzi)) {
            return false;
        }
        Sadrzi other = (Sadrzi) object;
        if ((this.sadrziPK == null && other.sadrziPK != null) || (this.sadrziPK != null && !this.sadrziPK.equals(other.sadrziPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Sadrzi[ sadrziPK=" + sadrziPK + " ]";
    }
    
}
