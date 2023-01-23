/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author remax
 */
@Embeddable
public class AdresaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idAdresa")
    private int idAdresa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Ulica")
    private String ulica;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Broj")
    private int broj;

    public AdresaPK() {
    }

    public AdresaPK(int idAdresa, String ulica, int broj) {
        this.idAdresa = idAdresa;
        this.ulica = ulica;
        this.broj = broj;
    }

    public int getIdAdresa() {
        return idAdresa;
    }

    public void setIdAdresa(int idAdresa) {
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
        hash += (int) idAdresa;
        hash += (ulica != null ? ulica.hashCode() : 0);
        hash += (int) broj;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdresaPK)) {
            return false;
        }
        AdresaPK other = (AdresaPK) object;
        if (this.idAdresa != other.idAdresa) {
            return false;
        }
        if ((this.ulica == null && other.ulica != null) || (this.ulica != null && !this.ulica.equals(other.ulica))) {
            return false;
        }
        if (this.broj != other.broj) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.AdresaPK[ idAdresa=" + idAdresa + ", ulica=" + ulica + ", broj=" + broj + " ]";
    }
    
}
