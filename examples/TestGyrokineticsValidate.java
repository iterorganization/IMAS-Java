import java.lang.*;

import imasjava.*;
import imasjava.wrapper.LowLevel;

public class TestGyrokineticsValidate {
	
	public static void main(String[] args) {
        imas.gyrokinetics_local ids = new imas.gyrokinetics_local();
        
        System.out.println("### Validating Gyrokinetics validate simplest case...\n");
        try {
            ids.validate();            
        } catch (Exception e) {
        }
        
        ids.ids_properties.homogeneous_time = LowLevel.IDS_TIME_MODE_HOMOGENEOUS;

        System.out.println("### Validating HOMOGENEOUS time array...\n");
        try {
            ids.validate(); 
        } catch (Exception e) {
        	System.out.println("Error, HOMOGENEOUS time array exception " + e.getMessage());
        }

        System.out.println("### Validating species_all...\n");
        ids.species_all = new imas.gyrokinetics_local.species_allClass();
        ids.species_all.angle_pol_equilibrium = new Vect1DDouble(1);
        ids.species_all.angle_pol_equilibrium.setElementAt(0, 10.0); 
        try {
            ids.validate(); 
        } catch (Exception e) {
        	System.out.println("Error, species_all validate exception " + e.getMessage());
        }

        System.out.println("### Validating species...\n");
        ids.species = new imas.gyrokinetics_local.speciesClass[2];
        for (int i = 0; i < 2; i++) {
            ids.species[i] = new imas.gyrokinetics_local.speciesClass();
            ids.species[i].potential_energy_norm = new Vect1DDouble(1);
            ids.species[i].potential_energy_norm.setElementAt(0, 10.0);
        }
        try {
            ids.validate(); 
        } catch (Exception e) {
        	System.out.println("Error, species validate exception " + e.getMessage());
        }
        
        System.out.println("### Validating non_linear...\n"); 
        ids.non_linear = new imas.gyrokinetics_local.non_linearClass();
		ids.non_linear.moments_norm_particle = new imas.gyrokinetics_local.non_linearClass.moments_norm_particleClass();
		ids.non_linear.moments_norm_particle.density = new Vect5DComplex(2,0,0,0,0);
		ids.non_linear.fluxes_3d.particles_phi_potential = new Vect3DDouble(0,0,0);

		try {
			ids.validate(); 
		} catch (Exception e) {
			System.out.println("Error, non_linear validate exception " + e.getMessage());
		}
        		  
        System.out.println("### Validating linear...\n");
        ids.linear = new imas.gyrokinetics_local.linearClass();
        
        ids.linear.wavevector = new imas.gyrokinetics_local.linearClass.wavevectorClass[1];
        for(int i = 0; i < 1; i++) {
        	ids.linear.wavevector[i] = new imas.gyrokinetics_local.linearClass.wavevectorClass();
        	ids.linear.wavevector[i].eigenmode = new imas.gyrokinetics_local.linearClass.wavevectorClass.eigenmodeClass[1];
        	for (int j = 0; j < 1; j++) {
				ids.linear.wavevector[i].eigenmode[j] = new imas.gyrokinetics_local.linearClass.wavevectorClass.eigenmodeClass();
				ids.linear.wavevector[i].eigenmode[j].poloidal_turns = 5;
				ids.linear.wavevector[i].eigenmode[j].angle_pol = new Vect1DDouble(2);
				ids.linear.wavevector[i].eigenmode[j].time_norm = new Vect1DDouble(2);				
				
				ids.linear.wavevector[i].eigenmode[j].moments_norm_gyrocenter_bessel_1 = new imas.gyrokinetics_local.linearClass.wavevectorClass.eigenmodeClass.moments_norm_gyrocenter_bessel_1Class();
				ids.linear.wavevector[i].eigenmode[j].moments_norm_gyrocenter_bessel_1.pressure_perpendicular = new Vect3DComplex(2,2,0);
				ids.linear.wavevector[i].eigenmode[j].moments_norm_gyrocenter_bessel_1.density = new Vect3DComplex(2,2,0);
			}
		}		
        try {
		    ids.validate();
		} catch (Exception e) {
			System.out.println("Error, linear validate exception " + e.getMessage());
		}
		
		System.out.println("### Gyrokinetics validate test completed\n");
    }
}
