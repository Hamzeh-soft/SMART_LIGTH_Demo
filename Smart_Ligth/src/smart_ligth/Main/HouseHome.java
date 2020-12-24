/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smart_ligth.Main;

import java.awt.Color;
import smart_ligth.Shouse;
import static smart_ligth.Shouse.getInstant;

/**
 *
 * @author User
 */
public class HouseHome {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Shouse house = getInstant();
        house.getContentPane().setBackground(Color.LIGHT_GRAY);
        house.show();

    }

}
