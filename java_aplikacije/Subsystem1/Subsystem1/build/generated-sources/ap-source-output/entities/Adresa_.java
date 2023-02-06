package entities;

import entities.Korisnik;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2023-02-06T15:32:18")
@StaticMetamodel(Adresa.class)
public class Adresa_ { 

    public static volatile SingularAttribute<Adresa, Integer> idAdresa;
    public static volatile SingularAttribute<Adresa, String> ulica;
    public static volatile SingularAttribute<Adresa, Integer> broj;
    public static volatile ListAttribute<Adresa, Korisnik> korisnikList;

}