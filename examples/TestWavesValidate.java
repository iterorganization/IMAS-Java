import java.lang.*;

import imasjava.*;
import imasjava.wrapper.LowLevel;

class TestWavesValidate {
    public static void main(String args[]) {
        imas.waves ids = new imas.waves();
        int max1 = 5;
        int max2 = 8;
        int max3 = 7;

        System.out.println("### Testing simplest case...");
        try {
            ids.validate();
            System.out.println("Error, expected ValidationException.");
            System.exit(1);
        } catch (Exception e) {
        }
        System.out.println("");


        ids.ids_properties.homogeneous_time = LowLevel.IDS_TIME_MODE_HOMOGENEOUS;

        System.out.println("### Testing HOMOGENEOUS time array...");
        try {
            ids.validate();
            System.out.println("Error, expected ValidationException.");
            System.exit(1);
        } catch (Exception e) {
        }
        System.out.println("");

        System.out.println("### Testing HOMOGENEOUS alternative/fixed coordinates");
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
                    ids.coherent_wave[i].beam_tracing[j].beam[k].wave_vector.n_parallel = new Vect1DDouble(5);
                }
            }
        }
        try {
            ids.validate();
            System.out.println("Error, expected ValidationException.");
            System.exit(1);
        } catch (Exception e) {
        }
        System.out.println("");

        System.out.println("### Testing HOMOGENEOUS alternative/fixed coordinates issue fixed v1");
        for (int i = 0; i < max1; i++) {
            for (int j = 0; j < max2; j++) {
                for (int k = 0; k < max3; k++) {
                    ids.coherent_wave[i].beam_tracing[j].beam[k].wave_vector.n_parallel = null;
                    ids.coherent_wave[i].beam_tracing[j].beam[k].wave_vector.n_parallel = new Vect1DDouble(1);
                    ids.coherent_wave[i].beam_tracing[j].beam[k].length = new Vect1DDouble(1);
                }
            }
        }
        try {
            ids.validate();
        } catch (Exception e) {
            System.out.println("Error: unexpected ValidationException: " + e.getMessage());
            System.exit(1);
        }
        System.out.println("");

        System.out.println("### Testing HOMOGENEOUS alternative/fixed coordinates issue fixed v2");
        for (int i = 0; i < max1; i++) {
            for (int j = 0; j < max2; j++) {
                for (int k = 0; k < max3; k++) {
                    ids.coherent_wave[i].beam_tracing[j].beam[k].wave_vector.n_parallel = null;
                    ids.coherent_wave[i].beam_tracing[j].beam[k].wave_vector.n_parallel = new Vect1DDouble(5);
                    ids.coherent_wave[i].beam_tracing[j].beam[k].length = new Vect1DDouble(5);
                }
            }
        }
        try {
            ids.validate();
        } catch (Exception e) {
            System.out.println("Error: unexpected ValidationException: " + e.getMessage());
            System.exit(1);
        }
        System.out.println("");
    }
}
