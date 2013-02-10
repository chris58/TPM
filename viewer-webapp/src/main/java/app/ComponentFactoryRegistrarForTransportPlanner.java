package app;

import com.google.inject.Singleton;
import org.apache.isis.viewer.wicket.viewer.registries.components.ComponentFactoryRegistrarDefault;

@Singleton
public class ComponentFactoryRegistrarForTransportPlanner extends ComponentFactoryRegistrarDefault {

    @Override
    public void addComponentFactories(ComponentFactoryList componentFactories) {
        super.addComponentFactories(componentFactories);
//        componentFactories.add(new CollectionContentsAsFullCalendarFactory());
//        componentFactories.add(new CollectionOfEntitiesAsLocatablesFactory());
        // currently no replacements
    }
}
