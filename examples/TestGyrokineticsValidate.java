package testing;
import java.lang.*;

import imasjava.*;
import imasjava.wrapper.LowLevel;

public class TestGyrokineticsValidate {

	public static void main(String[] args) {
        imas.gyrokinetics_local ids = new imas.gyrokinetics_local();
        
        System.out.println("### Testing Gyrokinetics validate simplest case...");
        try {
            ids.validate();           
        } catch (Exception e) {
        	System.out.println("Error, expected ValidationException: " + e.getMessage());
        }
        
        ids.ids_properties.homogeneous_time = LowLevel.IDS_TIME_MODE_HOMOGENEOUS;

        System.out.println("### Testing HOMOGENEOUS time array...");
        try {
            ids.validate(); 
        } catch (Exception e) {
        	System.out.println("Error, HOMOGENEOUS time array exception " + e.getMessage());
        }

        System.out.println("### Testing species...");
        ids.species = new imas.gyrokinetics_local.speciesClass[2];
        for (int i = 0; i < 2; i++) {
            ids.species[i] = new imas.gyrokinetics_local.speciesClass();
            ids.species[i].potential_energy_norm = new Vect1DDouble(0);
         }
        try {
            ids.validate();
        } catch (Exception e) {
        	System.out.println("Error, Species exception " + e.getMessage());
        }
        
        System.out.println("### Testing non_liner..."); ids.non_linear = new imas.gyrokinetics_local.non_linearClass();
		  ids.non_linear.moments_norm_particle = new imas.gyrokinetics_local.non_linearClass.moments_norm_particleClass();
		  ids.non_linear.moments_norm_particle.density = new Vect5DComplex(2,0,0,0,0);
		  ids.non_linear.fluxes_3d.particles_phi_potential = new Vect3DDouble(0,0,0);
		  
		  try { ids.validate(); } catch (Exception e) {
		  System.out.println("Error, non_liner exception " + e.getMessage()); 
		  }
        		  
        System.out.println("### Testing liner...");
        ids.linear = new imas.gyrokinetics_local.linearClass();
        
        ids.linear.wavevector = new imas.gyrokinetics_local.linearClass.wavevectorClass[5];
        for(int i = 0; i < 5; i++) {
        	ids.linear.wavevector[i] = new imas.gyrokinetics_local.linearClass.wavevectorClass();
        	ids.linear.wavevector[i].eigenmode = new imas.gyrokinetics_local.linearClass.wavevectorClass.eigenmodeClass[2];
        	
			for (int j = 0; j < 2; j++) {
				ids.linear.wavevector[i].eigenmode[j] = new imas.gyrokinetics_local.linearClass.wavevectorClass.eigenmodeClass();
				ids.linear.wavevector[i].eigenmode[j].poloidal_turns = 5;
				ids.linear.wavevector[i].eigenmode[j].time_norm = new Vect1DDouble(5);
				ids.linear.wavevector[i].eigenmode[j].angle_pol = new Vect1DDouble(5);
				
				ids.linear.wavevector[i].eigenmode[j].moments_norm_gyrocenter_bessel_1 = new imas.gyrokinetics_local.linearClass.wavevectorClass.eigenmodeClass.moments_norm_gyrocenter_bessel_1Class();
				ids.linear.wavevector[i].eigenmode[j].moments_norm_gyrocenter_bessel_1.pressure_perpendicular = new Vect3DComplex(2,1,1);
			}
		}
		
		try {
		    ids.validate();
		} catch (Exception e) {
			System.out.println("Error, expected liner exception " + e.getMessage());
		}
		
		System.out.println("Gyrokinetics validate test completed");
    }
}
