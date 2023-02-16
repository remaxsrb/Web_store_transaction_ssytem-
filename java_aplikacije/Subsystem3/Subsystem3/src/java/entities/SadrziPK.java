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

/**
 *
 * @author remax
 */
@Embeddable
public class SadrziPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idKorpa")
    private int idKorpa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idArtikal")
    private int idArtikal;

    public SadrziPK() {
    }

    public SadrziPK(int idKorpa, int idArtikal) {
        this.idKorpa = idKorpa;
        this.idArtikal = idArtikal;
    }

    public int getIdKorpa() {
        return idKorpa;
    }

    public void setIdKorpa(int idKorpa) {
        this.idKorpa = idKorpa;
    }

    public int getIdArtikal() {
        return idArtikal;
    }

    public void setIdArtikal(int idArtikal) {
        this.idArtikal = idArtikal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idKorpa;
        hash += (int) idArtikal;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SadrziPK)) {
            return false;
        }
        SadrziPK other = (SadrziPK) object;
        if (this.idKorpa != other.idKorpa) {
            return false;
        }
        if (this.idArtikal != other.idArtikal) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.SadrziPK[ idKorpa=" + idKorpa + ", idArtikal=" + idArtikal + " ]";
    }
    
}
