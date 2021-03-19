
import jakarta.enterprise.util.AnnotationLiteral;
import jakarta.inject.Singleton;
import java.lang.annotation.Annotation;
import org.atinject.tck.Tck;
import org.atinject.tck.auto.*;
import org.atinject.tck.auto.accessories.*;
import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.BuilderHelper;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author jcoustick
 */
public class Hk2Test {

    public static junit.framework.Test suite() {

        ServiceLocator locator = ServiceLocatorUtilities.createAndPopulateServiceLocator("test-locator");
        DynamicConfiguration dynamicConfig = ServiceLocatorUtilities.createDynamicConfiguration(locator);
        
        dynamicConfig.bind(BuilderHelper.link(FuelTank.class).build());
        dynamicConfig.bind(BuilderHelper.link(Seat.class).in(Singleton.class).build());
        dynamicConfig.bind(BuilderHelper.link(DriversSeat.class).to(Seat.class).qualifiedBy(Drivers.class.getName()).build());
        dynamicConfig.bind(BuilderHelper.link(Seatbelt.class).build());
        dynamicConfig.bind(BuilderHelper.link(V8Engine.class).to(GasEngine.class).to(Engine.class).build());
        dynamicConfig.bind(BuilderHelper.link(Cupholder.class).in(Singleton.class).build());
        dynamicConfig.bind(BuilderHelper.link(Tire.class).build());
        dynamicConfig.bind(BuilderHelper.link(SpareTire.class).to(Tire.class).named("spare").build());
        dynamicConfig.bind(BuilderHelper.link(Convertible.class).to(Car.class).build());
        
        dynamicConfig.commit();
        System.out.println(locator);
        Car tckCar = locator.getService(Car.class);
        return Tck.testsFor(tckCar, false, true);
    }
    
    static class DriversLiteral extends AnnotationLiteral<Drivers> {
        
    } 

}
