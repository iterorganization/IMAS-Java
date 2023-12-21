import java.lang.*;

import imasjava.*;
import imasjava.wrapper.LowLevel;

class TestDistributionsValidate {

    public static void main(String args[]) {
        imas.distributions ids = new imas.distributions();
        String expected;
        int max1 = 5;
        int max2 = 10;
        int max3 = 7;
        int max4 = 4;
        double[] time = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0};

        System.out.println("### Testing simplest case...");
        expected = "ids_properties.homogeneous_time wrong value (-999999999)";
        CommonValidateUtilities.check_validation((Object) ids, expected);
        System.out.println("");

        System.out.println("### Testing HOMOGENEOUS multiple alternative coordinates...");
        expected = "Error with distribution[0]."+"\n\r"+
        "Error with distribution/profiles_2d[0]."+"\n\r"+
        "Coordinate consistency error for distribution/profiles_2d/density (dimension 2) (2). Exactly one of the possible coordinate must be allocated. (distribution(i1)/profiles_2d(itime)/grid/z OR distribution(i1)/profiles_2d(itime)/grid/theta_geometric OR distribution(i1)/profiles_2d(itime)/grid/theta_straight)";
        ids.ids_properties.homogeneous_time = LowLevel.IDS_TIME_MODE_HOMOGENEOUS;
        ids.time= new Vect1DDouble(max2);
        ids.distribution = new imas.distributions.distributionClass[max1];
        for (int i = 0; i < max1; i++) {
            ids.distribution[i] = new imas.distributions.distributionClass();
            ids.distribution[i].profiles_2d = new imas.distributions.distributionClass.profiles_2dClass[max2];
            for (int j = 0; j < max2; j++) {
                ids.distribution[i].profiles_2d[j] = new imas.distributions.distributionClass.profiles_2dClass();
                ids.distribution[i].profiles_2d[j].grid.r = new Vect1DDouble(max3);
                ids.distribution[i].profiles_2d[j].grid.z = new Vect1DDouble(max3);
                ids.distribution[i].profiles_2d[j].grid.theta_straight = new Vect1DDouble(max3);
                ids.distribution[i].profiles_2d[j].density = new Vect2DDouble(max3,2);
            }
        }
        CommonValidateUtilities.check_validation((Object) ids, expected);
        System.out.println("");

        System.out.println("### Testing HOMOGENEOUS multiple alternative coordinates wrong dim2");
        expected = "Error with distribution[0]."+"\n\r"+
        "Error with distribution/profiles_2d[0]."+"\n\r"+
        "Wrong dimension 2 for distribution/profiles_2d/density (2). (distribution(i1)/profiles_2d(itime)/grid/z OR distribution(i1)/profiles_2d(itime)/grid/theta_geometric OR distribution(i1)/profiles_2d(itime)/grid/theta_straight)";
        for (int i = 0; i < max1; i++) {
            for (int j = 0; j < max2; j++) {
                ids.distribution[i].profiles_2d[j].grid.theta_straight = null;
            }
        }
        CommonValidateUtilities.check_validation((Object) ids, expected);
        System.out.println("");

        System.out.println("### Testing HOMOGENEOUS multiple alternative coordinates fixing dim2");
        expected = "";
        for (int i = 0; i < max1; i++) {
            for (int j = 0; j < max2; j++) {
                ids.distribution[i].profiles_2d[j].density = null;
                ids.distribution[i].profiles_2d[j].density = new Vect2DDouble(max3,max3);
            }
        }
        CommonValidateUtilities.check_validation((Object) ids, expected);
        System.out.println("");

        System.out.println("### Testing HOMOGENEOUS multiple alternative coordinates same_as");
        expected = "Error with distribution[0]."+"\n\r"+
        "Error with distribution/ggd[0]."+"\n\r"+
        "Error with distribution/ggd/grid."+"\n\r"+
        "Error with distribution/ggd/grid/grid_subset[0]."+"\n\r"+
        "Error with distribution/ggd/grid/grid_subset/base[0]."+"\n\r"+
        "Wrong dimension 3 for distribution/ggd/grid/grid_subset/base/tensor_contravariant (8). (distribution(i1)/ggd(itime)/grid/grid_subset(i2)/base(i3)/tensor_covariant)";
        for (int i = 0; i < max1; i++) {
            ids.distribution[i].ggd = new imas.distributions.distributionClass.ggdClass[max2]; 
            for (int j = 0; j < max2; j++) {
                ids.distribution[i].ggd[j] = new imas.distributions.distributionClass.ggdClass();
                ids.distribution[i].ggd[j].grid.grid_subset = new imas.distributions.distributionClass.ggdClass.gridClass.grid_subsetClass[max3];
                for (int k = 0; k < max3; k++) {
                    ids.distribution[i].ggd[j].grid.grid_subset[k] = new imas.distributions.distributionClass.ggdClass.gridClass.grid_subsetClass();
                    ids.distribution[i].ggd[j].grid.grid_subset[k].base = new imas.distributions.distributionClass.ggdClass.gridClass.grid_subsetClass.baseClass[max4];
                    ids.distribution[i].ggd[j].grid.grid_subset[k].element = new imas.distributions.distributionClass.ggdClass.gridClass.grid_subsetClass.elementClass[max3];
                    for (int l = 0; l < max4; l++) {
                        ids.distribution[i].ggd[j].grid.grid_subset[k].base[l] = new imas.distributions.distributionClass.ggdClass.gridClass.grid_subsetClass.baseClass();
                        ids.distribution[i].ggd[j].grid.grid_subset[k].base[l].tensor_covariant = new Vect3DDouble(max3,max3,max3);
                        ids.distribution[i].ggd[j].grid.grid_subset[k].base[l].tensor_contravariant = new Vect3DDouble(max3,max3,max3+1);
                    }
                }
            }
        }
        CommonValidateUtilities.check_validation((Object) ids, expected);
        System.out.println("");

        System.out.println("### Testing HOMOGENEOUS multiple alternative coordinates fixing same_as");
        expected = "";
        for (int i = 0; i < max1; i++) {
            for (int j = 0; j < max2; j++) {
                for (int k = 0; k < max3; k++) {
                    for (int l = 0; l < max4; l++) {
                        ids.distribution[i].ggd[j].grid.grid_subset[k].base[l].tensor_contravariant = null;
                        ids.distribution[i].ggd[j].grid.grid_subset[k].base[l].tensor_contravariant = new Vect3DDouble(max3,max3,max3);
                    }
                }
            }
        }
        CommonValidateUtilities.check_validation((Object) ids, expected);
        System.out.println("");

        System.out.println("### Testing HETEROGENEOUS time coordinate");
        expected = "Error with distribution[0]."+"\n\r"+
                    "Time coordinate of global_quantities wrong. global_quantities[itime].time is invalid Must be the size of time (-9.0E40).";
        ids.ids_properties.homogeneous_time = LowLevel.IDS_TIME_MODE_HETEROGENEOUS; 
        for (int i = 0; i < max1; i++) {
            ids.distribution[i].profiles_2d = null;
            ids.distribution[i].ggd = null;
            ids.distribution[i].global_quantities = new imas.distributions.distributionClass.global_quantitiesClass[max2+1];
            for (int j = 0; j < max2+1; j++) {
                ids.distribution[i].global_quantities[j] = new imas.distributions.distributionClass.global_quantitiesClass();
            }
            ids.distribution[i].profiles_1d = new imas.distributions.distributionClass.profiles_1dClass[max2+2];
            for (int j = 0; j < max2+2; j++) {
                ids.distribution[i].profiles_1d[j] = new imas.distributions.distributionClass.profiles_1dClass();
            }
            ids.distribution[i].profiles_2d = new imas.distributions.distributionClass.profiles_2dClass[max2+3];
            for (int j = 0; j < max2+3; j++) {
                ids.distribution[i].profiles_2d[j] = new imas.distributions.distributionClass.profiles_2dClass();
            }
        }
        CommonValidateUtilities.check_validation((Object) ids, expected);
        System.out.println("");

        System.out.println("### Testing HETEROGENEOUS time coordinate fixed");
        expected = "";
        for (int i = 0; i < max1; i++) {
            for (int j = 0; j < max2+1; j++) {
                ids.distribution[i].global_quantities[j].time = 0.1;
            }
            for (int j = 0; j < max2+2; j++) {
                ids.distribution[i].profiles_1d[j].time = 0.1;
            }
            for (int j = 0; j < max2+3; j++) {
                ids.distribution[i].profiles_2d[j].time = 0.1;
            }
        }
        CommonValidateUtilities.check_validation((Object) ids, expected);
        System.out.println("");

    }
}
