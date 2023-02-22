package net.tmmc.util;

import arc.Events;
import arc.struct.ObjectSet;

import mindustry.type.Planet;
import mindustry.game.EventType;

import org.jetbrains.annotations.NotNull;

import static mindustry.Vars.content;
import static mindustry.Vars.ui;

public class ManySolarSystems {
    public static void init() {
        var solarSystemsSet = new ObjectSet<Planet>();
        for(var planet : content.planets()) {
            var solarSystem = planet.solarSystem;

            if(solarSystem != null) {
                solarSystemsSet.add(solarSystem);
            }
        }

        var solarSystems = solarSystemsSet.toSeq();
        Events.run(EventType.Trigger.update, () -> {
            solarSystems.forEach(ManySolarSystems::updatePlanet);

            var state = ui.planet.state;
            if(state.solarSystem != state.planet.solarSystem) {
                state.solarSystem = state.planet.solarSystem;
            }
        });
    }

    private static void updatePlanet(@NotNull Planet planet){
        planet.position.setZero();
        planet.addParentOffset(planet.position);

        if(planet.parent != null) {
            planet.position.add(planet.parent.position);
        }

       planet.children.forEach(ManySolarSystems::updatePlanet);
    }
}