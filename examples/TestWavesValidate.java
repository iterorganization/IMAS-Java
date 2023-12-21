import java.lang.*;

import imasjava.*;
import imasjava.wrapper.LowLevel;

class TestWavesValidate {
    public static void main(String args[]) {
        imas.waves ids = new imas.waves();
        String expected;
        int max1 = 5;
        int max2 = 8;
        int max3 = 7;

        System.out.println("### Testing simplest case...");
        expected = "ids_properties.homogeneous_time wrong value (-999999999)";
        CommonValidateUtilities.check_validation((Object) ids, expected);
        System.out.println("");


        ids.ids_properties.homogeneous_time = LowLevel.IDS_TIME_MODE_HOMOGENEOUS;
        
        System.out.println("### Testing HOMOGENEOUS time array...");
        expected = "With the time mode 'HOMOGENEOUS', the time array must be allocated and not be empty.";
        CommonValidateUtilities.check_validation((Object) ids, expected);
        System.out.println("");

        System.out.println("### Testing HOMOGENEOUS alternative/fixed coordinates");
        expected = "Error with coherent_wave[0]."+"\n\r"+
                "Error with coherent_wave/beam_tracing[0]."+"\n\r"+
                "Error with coherent_wave/beam_tracing/beam[0]."+"\n\r"+
                "Wrong dimension 1 for coherent_wave/beam_tracing/beam/wave_vector/n_tor (5). (coherent_wave(i1)/beam_tracing(itime)/beam(i2)/length OR 1...1)";
        ids.time = new Vect1DDouble(max2);
        ids.coherent_wave = new imas.waves.coherent_waveClass[max1];
        for (int i = 0; i < max1; i++) {
            ids.coherent_wave[i] = new imas.waves.coherent_waveClass();
            ids.coherent_wave[i].beam_tracing = new imas.waves.coherent_waveClass.beam_tracingClass[max2];
            for (int j = 0; j < max2; j++) {
                ids.coherent_wave[i].beam_tracing[j] = new imas.waves.coherent_waveClass.beam_tracingClass();
                ids.coherent_wave[i].beam_tracing[j].beam = new imas.waves.coherent_waveClass.beam_tracingClass.beamClass[max3];
                for (int k = 0; k < max3; k++) {
                    ids.coherent_wave[i].beam_tracing[j].beam[k] = new imas.waves.coherent_waveClass.beam_tracingClass.beamClass();
                    ids.coherent_wave[i].beam_tracing[j].beam[k].wave_vector.n_tor = new Vect1DInt(5);
                }
            }
        }
        CommonValidateUtilities.check_validation((Object) ids, expected);
        System.out.println("");

        System.out.println("### Testing HOMOGENEOUS alternative/fixed coordinates issue fixed v1");
        expected = "";
        for (int i = 0; i < max1; i++) {
            for (int j = 0; j < max2; j++) {
                for (int k = 0; k < max3; k++) {
                    ids.coherent_wave[i].beam_tracing[j].beam[k].wave_vector.n_tor = null;
                    ids.coherent_wave[i].beam_tracing[j].beam[k].wave_vector.n_tor = new Vect1DInt(1);
                }
            }
        }
        CommonValidateUtilities.check_validation((Object) ids, expected);
        System.out.println("");

        System.out.println("### Testing HOMOGENEOUS alternative/fixed coordinates issue fixed v2");
        expected = "";
        for (int i = 0; i < max1; i++) {
            for (int j = 0; j < max2; j++) {
                for (int k = 0; k < max3; k++) {
                    ids.coherent_wave[i].beam_tracing[j].beam[k].wave_vector.n_tor = null;
                    ids.coherent_wave[i].beam_tracing[j].beam[k].wave_vector.n_tor = new Vect1DInt(5);
                    ids.coherent_wave[i].beam_tracing[j].beam[k].length = new Vect1DDouble(5);
                }
            }
        }
        CommonValidateUtilities.check_validation((Object) ids, expected);
        System.out.println("");
    }
}
