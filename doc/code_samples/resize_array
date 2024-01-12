import java.lang.*;

import imasjava.*;
import imasjava.ids.magnetics_IDSBase.b_field_tor_probeClass;
import imasjava.ids.magnetics_IDSBase.flux_loopClass;
import imasjava.wrapper.LowLevel;

class TestResizeArray {
    public static void main(String args[]) {
    	
    	imas.magnetics ids = new imas.magnetics();
        double[] time = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0};
        System.out.println("");
        System.out.println("### Testing HOMOGENEOUS flux_loop resize...");
        ids.time= new Vect1DDouble(10);
        ids.time.setArray(time, 10);
        ids.flux_loop= new imas.magnetics.flux_loopClass[10];
        System.out.println("### Testing HOMOGENEOUS flux_loop size..." + ids.flux_loop.length);
        for (int i = 0; i < ids.flux_loop.length; i++) {
            ids.flux_loop[i] = new imas.magnetics.flux_loopClass();
            ids.flux_loop[i].indices_differential = new Vect1DInt(3);
        }        
        System.out.println("### Before resize ids.flux_loop[0]: " + ids.flux_loop[0]);  
        ids.time= new Vect1DDouble(15);
        ids.time.setArray(time, 15);
        ids.flux_loop = (flux_loopClass[]) imasjava.utilities.ImasReflection.getResizedClassObject(ids.flux_loop, 15);
        System.out.println("### Testing resize flux_loop array resize: " + ids.flux_loop.length);    
        for (int i = 10; i < ids.flux_loop.length; i++) {
            ids.flux_loop[i] = new imas.magnetics.flux_loopClass();
            ids.flux_loop[i].indices_differential = new Vect1DInt(3);
        }
        System.out.println("### After resize ids.flux_loop[0]: " + ids.flux_loop[0]);  
        System.out.println("### After resize ids.flux_loop[14]: " + ids.flux_loop[14]);
        
        System.out.println("");

        System.out.println("### Testing b_field_tor_probe resize...");
        ids.b_field_tor_probe = new imas.magnetics.b_field_tor_probeClass[13];  
        ids.b_field_pol_probe = new imas.magnetics.b_field_pol_probeClass[13];
        System.out.println("### Testing b_field_tor_probe size: " + ids.b_field_tor_probe.length);        
        for (int i = 0; i < 13; i++) {
            ids.b_field_tor_probe[i] = new imas.magnetics.b_field_tor_probeClass();
            ids.b_field_tor_probe[i].field.data = new Vect1DDouble(9);
            ids.b_field_tor_probe[i].field.time = new Vect1DDouble(9);
            ids.b_field_pol_probe[i] = new imas.magnetics.b_field_pol_probeClass();
            ids.b_field_pol_probe[i].field.data = new Vect1DDouble(9);
            ids.b_field_pol_probe[i].field.time = new Vect1DDouble(9);
        }
        System.out.println("### Before resize ids.b_field_tor_probe[0]: " + ids.b_field_tor_probe[0]);  
        
        ids.b_field_tor_probe = (b_field_tor_probeClass[]) imasjava.utilities.ImasReflection.getResizedClassObject(ids.b_field_tor_probe, 20);
        ids.b_field_pol_probe = new imas.magnetics.b_field_pol_probeClass[ids.b_field_tor_probe.length];
        for (int i = 13; i < ids.b_field_tor_probe.length; i++) {
            ids.b_field_tor_probe[i] = new imas.magnetics.b_field_tor_probeClass();
            ids.b_field_tor_probe[i].field.data = new Vect1DDouble(9);
            ids.b_field_tor_probe[i].field.time = new Vect1DDouble(9);
            ids.b_field_pol_probe[i] = new imas.magnetics.b_field_pol_probeClass();
            ids.b_field_pol_probe[i].field.data = new Vect1DDouble(9);
            ids.b_field_pol_probe[i].field.time = new Vect1DDouble(9);
        }
        System.out.println("### Testing b_field_tor_probe resize: " + ids.b_field_tor_probe.length);
        System.out.println("### After resize ids.b_field_tor_probe[0]: " + ids.b_field_tor_probe[0]);  
        System.out.println("### After resize ids.b_field_tor_probe[19]: " + ids.b_field_tor_probe[19]);
        
        System.out.println("");
    }
}
