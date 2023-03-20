/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.List;

/**
 *
 * @author alolo
 */
public interface InterfaceService<T> {

    public void ajouter(T t);

    public List<T> getAll();

    public void supprimer(T p);
}
