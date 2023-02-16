/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author remax
 */
@Entity
@Table(name = "Artikal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Artikal.findAll", query = "SELECT a FROM Artikal a"),
    @NamedQuery(name = "Artikal.findByIdArtikal", query = "SELECT a FROM Artikal a WHERE a.idArtikal = :idArtikal"),
    @NamedQuery(name = "Artikal.findByNaziv", query = "SELECT a FROM Artikal a WHERE a.naziv = :naziv"),
    @NamedQuery(name = "Artikal.findByOpis", query = "SELECT a FROM Artikal a WHERE a.opis = :opis"),
    @NamedQuery(name = "Artikal.findByCena", query = "SELECT a FROM Artikal a WHERE a.cena = :cena"),
    @NamedQuery(name = "Artikal.findByPopust", query = "SELECT a FROM Artikal a WHERE a.popust = :popust")})
public class Artikal implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Naziv")
    private String naziv;
    @Size(max = 100)
    @Column(name = "Opis")
    private String opis;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Cena")
    private float cena;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Popust")
    private int popust;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "artikal")
    private List<Sadrzi> sadrziList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idArtikal")
    private Integer idArtikal;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idArtikal")
    private Stavka stavka;

    public Artikal() {
    }

    public Artikal(Integer idArtikal) {
        this.idArtikal = idArtikal;
    }

    public Artikal(Integer idArtikal, String naziv, float cena, int popust) {
        this.idArtikal = idArtikal;
        this.naziv = naziv;
        this.cena = cena;
        this.popust = popust;
    }

    public Integer getIdArtikal() {
        return idArtikal;
    }

    public void setIdArtikal(Integer idArtikal) {
        this.idArtikal = idArtikal;
    }


    public Stavka getStavka() {
        return stavka;
    }

    public void setStavka(Stavka stavka) {
        this.stavka = stavka;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idArtikal != null ? idArtikal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Artikal)) {
            return false;
        }
        Artikal other = (Artikal) object;
        if ((this.idArtikal == null && other.idArtikal != null) || (this.idArtikal != null && !this.idArtikal.equals(other.idArtikal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Artikal[ idArtikal=" + idArtikal + " ]";
    }
    @XmlTransient
    public List<Sadrzi> getSadrziList() {
        return sadrziList;
    }
    public void setSadrziList(List<Sadrzi> sadrziList) {
        this.sadrziList = sadrziList;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public float getCena() {
        return cena;
    }

    public void setCena(float cena) {
        this.cena = cena;
    }

    public int getPopust() {
        return popust;
    }

    public void setPopust(int popust) {
        this.popust = popust;
    }
    
}
