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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "Korisnik")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Korisnik.findAll", query = "SELECT k FROM Korisnik k"),
    @NamedQuery(name = "Korisnik.findByKorisnickoIme", query = "SELECT k FROM Korisnik k WHERE k.korisnickoIme = :korisnickoIme")})
public class Korisnik implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "Novac")
    private float novac;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "korisnik")
    private Korpa korpa;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "KorisnickoIme")
    private String korisnickoIme;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "korisnik")
    private List<Narudzbina> narudzbinaList;
    @JoinColumn(name = "idAdresa", referencedColumnName = "idAdresa")
    @ManyToOne(optional = false)
    private Adresa idAdresa;
    @JoinColumn(name = "idGrad", referencedColumnName = "idGrad")
    @ManyToOne(optional = false)
    private Grad idGrad;

    public Korisnik() {
    }

    public Korisnik(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    @XmlTransient
    public List<Narudzbina> getNarudzbinaList() {
        return narudzbinaList;
    }

    public void setNarudzbinaList(List<Narudzbina> narudzbinaList) {
        this.narudzbinaList = narudzbinaList;
    }

    public Adresa getIdAdresa() {
        return idAdresa;
    }

    public void setIdAdresa(Adresa idAdresa) {
        this.idAdresa = idAdresa;
    }

    public Grad getIdGrad() {
        return idGrad;
    }

    public void setIdGrad(Grad idGrad) {
        this.idGrad = idGrad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (korisnickoIme != null ? korisnickoIme.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Korisnik)) {
            return false;
        }
        Korisnik other = (Korisnik) object;
        if ((this.korisnickoIme == null && other.korisnickoIme != null) || (this.korisnickoIme != null && !this.korisnickoIme.equals(other.korisnickoIme))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Korisnik[ korisnickoIme=" + korisnickoIme + " ]";
    }


    public Korpa getKorpa() {
        return korpa;
    }

    public void setKorpa(Korpa korpa) {
        this.korpa = korpa;
    }

    public float getNovac() {
        return novac;
    }

    public void setNovac(float novac) {
        this.novac = novac;
    }
    
}
