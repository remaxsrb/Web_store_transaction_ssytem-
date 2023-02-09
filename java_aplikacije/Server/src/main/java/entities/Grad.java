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
@Table(name = "Grad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grad.findAll", query = "SELECT g FROM Grad g"),
    @NamedQuery(name = "Grad.findByIdGrad", query = "SELECT g FROM Grad g WHERE g.idGrad = :idGrad"),
    @NamedQuery(name = "Grad.findByNaziv", query = "SELECT g FROM Grad g WHERE g.naziv = :naziv"),
    @NamedQuery(name = "Grad.findByDrzava", query = "SELECT g FROM Grad g WHERE g.drzava = :drzava")})
public class Grad implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Naziv")
    private String naziv;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Drzava")
    private String drzava;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGrad")
    private List<Korisnik> korisnikList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idGrad")
    private Integer idGrad;

    public Grad() {
    }

    public Grad(Integer idGrad) {
        this.idGrad = idGrad;
    }

    public Grad(Integer idGrad, String naziv, String drzava) {
        this.idGrad = idGrad;
        this.naziv = naziv;
        this.drzava = drzava;
    }

    public Integer getIdGrad() {
        return idGrad;
    }

    public void setIdGrad(Integer idGrad) {
        this.idGrad = idGrad;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGrad != null ? idGrad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grad)) {
            return false;
        }
        Grad other = (Grad) object;
        if ((this.idGrad == null && other.idGrad != null) || (this.idGrad != null && !this.idGrad.equals(other.idGrad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Grad[ idGrad=" + idGrad + " ]";
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getDrzava() {
        return drzava;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    @XmlTransient
    public List<Korisnik> getKorisnikList() {
        return korisnikList;
    }

    public void setKorisnikList(List<Korisnik> korisnikList) {
        this.korisnikList = korisnikList;
    }
    
}
