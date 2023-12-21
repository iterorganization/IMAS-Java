import java.lang.*;

import imasjava.*;
import imasjava.wrapper.LowLevel;

class TestMagneticsValidate {
    public static void main(String args[]) {
        imas.magnetics ids = new imas.magnetics();
        String expected;
        double[] time = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0};

        System.out.println("### Testing simplest case...");
        expected = "ids_properties.homogeneous_time wrong value (-999999999)";
        CommonValidateUtilities.check_validation((Object) ids, expected);
        System.out.println("");


        ids.ids_properties.homogeneous_time = LowLevel.IDS_TIME_MODE_HOMOGENEOUS;
        
        System.out.println("### Testing HOMOGENEOUS time array...");
        expected = "With the time mode 'HOMOGENEOUS', the time array must be allocated and not be empty.";
        CommonValidateUtilities.check_validation((Object) ids, expected);
        System.out.println("");

        System.out.println("### Testing HOMOGENEOUS flux_loop.indices_differential (fixed size)...");
        expected =  "Error with flux_loop[0]."+"\n\r"+
                    "array_size of flux_loop/indices_differential (3) (dimension 1) wrong. Must be 2.";
        ids.time= new Vect1DDouble(20);
        ids.time.setArray(time, 20);
        ids.flux_loop= new imas.magnetics.flux_loopClass[20];
        for (int i = 0; i < time.length; i++) {
            ids.flux_loop[i] = new imas.magnetics.flux_loopClass();
            ids.flux_loop[i].indices_differential = new Vect1DInt(3);
        }
        CommonValidateUtilities.check_validation((Object) ids, expected);
        System.out.println("");

        System.out.println("### Testing HOMOGENEOUS wrong data size...");
        expected =  "Error with flux_loop[0]."+"\n\r"+
                    "Error with flux_loop/flux."+"\n\r"+
                    "arraySize of flux_loop/flux/data (15) (dimension 1) wrong. Must be the size of time (10)";
        for (int i = 0; i < time.length; i++) {
            ids.flux_loop[i].indices_differential = null;
            ids.flux_loop[i].indices_differential = new Vect1DInt(2);
            ids.flux_loop[i].flux.data = new Vect1DDouble(15);
        }
        CommonValidateUtilities.check_validation((Object) ids, expected);
        System.out.println("");

        System.out.println("### Testing HETEROGENEOUS with missing time coordinate...");
        expected =  "Error with flux_loop[0]."+"\n\r"+
                    "Error with flux_loop/flux."+"\n\r"+
                    "Wrong dimension 1 for flux_loop/flux/data (15). (flux_loop(i1)/flux/time)";
        ids.ids_properties.homogeneous_time = LowLevel.IDS_TIME_MODE_HETEROGENEOUS;
        CommonValidateUtilities.check_validation((Object) ids, expected);
        System.out.println("");

        System.out.println("### Testing HETEROGENEOUS by fixing the issue...");
        expected = "";
        for (int i = 0; i < time.length; i++) {
            ids.flux_loop[i].flux.time = new Vect1DDouble(15);
        }
        CommonValidateUtilities.check_validation((Object) ids, expected);
        System.out.println("");

        System.out.println("### Testing HETEROGENEOUS advanced usage...");
        expected = "";
        ids.shunt = new imas.magnetics.shuntClass[5];
        for (int i = 0; i < 5; i++) {
            ids.shunt[i] = new imas.magnetics.shuntClass();
        }
        ids.diamagnetic_flux = new imas.magnetics.diamagnetic_fluxClass[7];
        for (int i = 0; i < 7; i++) {
            ids.diamagnetic_flux[i] = new imas.magnetics.diamagnetic_fluxClass();
        }
        ids.b_field_tor_probe = new imas.magnetics.b_field_tor_probeClass[13];
        ids.b_field_pol_probe = new imas.magnetics.b_field_pol_probeClass[13];
        for (int i = 0; i < 13; i++) {
            ids.b_field_tor_probe[i] = new imas.magnetics.b_field_tor_probeClass();
            ids.b_field_tor_probe[i].field.data = new Vect1DDouble(9);
            ids.b_field_tor_probe[i].field.time = new Vect1DDouble(9);
            ids.b_field_pol_probe[i] = new imas.magnetics.b_field_pol_probeClass();
            ids.b_field_pol_probe[i].field.data = new Vect1DDouble(9);
            ids.b_field_pol_probe[i].field.time = new Vect1DDouble(9);
        }
        CommonValidateUtilities.check_validation((Object) ids, expected);
        System.out.println("");

        System.out.println("### Testing HETEROGENEOUS logic coordinate consistency...");
        expected =  "Error with b_field_pol_probe[0]."+"\n\r"+
                    "Error with b_field_pol_probe/non_linear_response."+"\n\r"+
                    "Wrong dimension 1 for b_field_pol_probe/non_linear_response/b_field_non_linear (4). (b_field_pol_probe(i1)/non_linear_response/b_field_linear)";
        for (int i = 0; i < 13; i++) {
            ids.b_field_pol_probe[i].non_linear_response.b_field_linear = new Vect1DDouble(3);
            ids.b_field_pol_probe[i].non_linear_response.b_field_non_linear = new Vect1DDouble(4);
        }
        CommonValidateUtilities.check_validation((Object) ids, expected);
        System.out.println("");
    }
}
