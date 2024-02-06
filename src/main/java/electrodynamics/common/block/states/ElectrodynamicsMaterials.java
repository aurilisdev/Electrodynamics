package electrodynamics.common.block.states;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

/**
 * Fine I'll do it myself
 * 
 * This is meant as a reference class; copy blocks directly with
 * 
 * @author skip999
 *
 */
public class ElectrodynamicsMaterials {

    public static Properties air() {
        return Properties.of().mapColor(MapColor.NONE).noCollission().forceSolidOff().replaceable().isRedstoneConductor((state, getter, pos) -> false);
    }

    public static Properties structuralAir() {
        return Properties.of().mapColor(MapColor.NONE).noCollission().forceSolidOff().replaceable().isRedstoneConductor((state, getter, pos) -> false);
    }

    public static Properties portal() {
        return Properties.of().mapColor(MapColor.NONE).noCollission().forceSolidOff().pushReaction(PushReaction.BLOCK).isRedstoneConductor((state, getter, pos) -> false);
    }

    public static Properties clothDecoration() {
        return Properties.of().mapColor(MapColor.WOOL).noCollission().forceSolidOff().ignitedByLava().isRedstoneConductor((state, getter, pos) -> false);
    }

    public static Properties plant() {
        return Properties.of().mapColor(MapColor.PLANT).noCollission().forceSolidOff().pushReaction(PushReaction.DESTROY).isRedstoneConductor((state, getter, pos) -> false);
    }

    public static Properties waterPlant() {
        return Properties.of().mapColor(MapColor.WATER).noCollission().forceSolidOff().pushReaction(PushReaction.DESTROY).isRedstoneConductor((state, getter, pos) -> false).instrument(NoteBlockInstrument.BASEDRUM);
    }

    public static Properties replaceablePlant() {
        return Properties.of().mapColor(MapColor.PLANT).noCollission().forceSolidOff().pushReaction(PushReaction.DESTROY).replaceable().ignitedByLava().isRedstoneConductor((state, getter, pos) -> false);
    }

    public static Properties replaceableFireproofPlant() {
        return Properties.of().mapColor(MapColor.PLANT).noCollission().forceSolidOff().pushReaction(PushReaction.DESTROY).replaceable().isRedstoneConductor((state, getter, pos) -> false);
    }

    public static Properties replaceableWaterPlant() {
        return Properties.of().mapColor(MapColor.WATER).noCollission().forceSolidOff().pushReaction(PushReaction.DESTROY).replaceable().isRedstoneConductor((state, getter, pos) -> false);
    }

    public static Properties water() {
        return Properties.of().mapColor(MapColor.WATER).noCollission().forceSolidOff().pushReaction(PushReaction.DESTROY).replaceable().liquid().isRedstoneConductor((state, getter, pos) -> false);
    }

    public static Properties bubbleColumn() {
        return Properties.of().mapColor(MapColor.WATER).noCollission().forceSolidOff().pushReaction(PushReaction.DESTROY).replaceable().liquid().isRedstoneConductor((state, getter, pos) -> false);
    }

    public static Properties lava() {
        return Properties.of().mapColor(MapColor.FIRE).noCollission().forceSolidOff().pushReaction(PushReaction.DESTROY).replaceable().liquid().isRedstoneConductor((state, getter, pos) -> false);
    }

    public static Properties topSnow() {
        return Properties.of().mapColor(MapColor.SNOW).noCollission().forceSolidOff().pushReaction(PushReaction.DESTROY).replaceable().isRedstoneConductor((state, getter, pos) -> false);
    }

    public static Properties fire() {
        return Properties.of().mapColor(MapColor.NONE).noCollission().forceSolidOff().pushReaction(PushReaction.DESTROY).replaceable().isRedstoneConductor((state, getter, pos) -> false);
    }

    public static Properties decoration() {
        return Properties.of().mapColor(MapColor.NONE).noCollission().forceSolidOff().pushReaction(PushReaction.DESTROY).isRedstoneConductor((state, getter, pos) -> false);
    }

    public static Properties web() {
        return Properties.of().mapColor(MapColor.WOOL).noCollission().forceSolidOff().pushReaction(PushReaction.DESTROY).isRedstoneConductor((state, getter, pos) -> false);
    }

    public static Properties sculk() {
        return Properties.of().mapColor(MapColor.COLOR_BLACK);
    }

    public static Properties buildableGlass() {
        return Properties.of().mapColor(MapColor.NONE);
    }

    public static Properties clay() {
        return Properties.of().mapColor(MapColor.CLAY);
    }

    public static Properties dirt() {
        return Properties.of().mapColor(MapColor.DIRT);
    }

    public static Properties grass() {
        return Properties.of().mapColor(MapColor.GRASS);
    }

    public static Properties iceSolid() {
        return Properties.of().mapColor(MapColor.ICE);
    }

    public static Properties sand() {
        return Properties.of().mapColor(MapColor.SAND).instrument(NoteBlockInstrument.SNARE);
    }

    public static Properties sponge() {
        return Properties.of().mapColor(MapColor.COLOR_YELLOW);
    }

    public static Properties shulkerShell() {
        return Properties.of().mapColor(MapColor.COLOR_PURPLE);
    }

    public static Properties wood() {
        return Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS);
    }

    public static Properties netherWood() {
        return Properties.of().mapColor(MapColor.WOOD);
    }

    public static Properties bambooSapling() {
        return Properties.of().mapColor(MapColor.WOOD).ignitedByLava().pushReaction(PushReaction.DESTROY).noCollission();
    }

    public static Properties bamboo() {
        return Properties.of().mapColor(MapColor.WOOD).ignitedByLava().pushReaction(PushReaction.DESTROY);
    }

    public static Properties wool() {
        return Properties.of().mapColor(MapColor.WOOL).ignitedByLava();
    }

    public static Properties explosive() {
        return Properties.of().mapColor(MapColor.FIRE).ignitedByLava().forceSolidOff().isRedstoneConductor((state, getter, pos) -> false);
    }

    public static Properties leaves() {
        return Properties.of().mapColor(MapColor.PLANT).ignitedByLava().forceSolidOff().pushReaction(PushReaction.DESTROY).isRedstoneConductor((state, getter, pos) -> false);
    }

    public static Properties glass() {
        return Properties.of().mapColor(MapColor.NONE).forceSolidOff().isRedstoneConductor((state, getter, pos) -> false).instrument(NoteBlockInstrument.HAT);
    }

    public static Properties ice() {
        return Properties.of().mapColor(MapColor.ICE).forceSolidOff().isRedstoneConductor((state, getter, pos) -> false);
    }

    public static Properties cactus() {
        return Properties.of().mapColor(MapColor.PLANT).forceSolidOff().pushReaction(PushReaction.DESTROY).isRedstoneConductor((state, getter, pos) -> false);
    }

    public static Properties metal() {
        return Properties.of().mapColor(MapColor.METAL);
    }

    public static Properties stone() {
        return Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM);
    }

    /** The material for crafted snow. */
    public static Properties snow() {
        return Properties.of().mapColor(MapColor.SNOW);
    }

    // Dave Mustaine
    public static Properties heavyMetal() {
        return Properties.of().mapColor(MapColor.METAL).pushReaction(PushReaction.BLOCK);
    }

    public static Properties barrier() {
        return Properties.of().mapColor(MapColor.NONE).pushReaction(PushReaction.BLOCK);
    }

    public static Properties piston() {
        return Properties.of().mapColor(MapColor.STONE).pushReaction(PushReaction.BLOCK);
    }

    public static Properties moss() {
        return Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY);
    }

    public static Properties vegetable() {
        return Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY);
    }

    public static Properties egg() {
        return Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY);
    }

    public static Properties cake() {
        return Properties.of().mapColor(MapColor.NONE).pushReaction(PushReaction.DESTROY);
    }

    public static Properties amethyst() {
        return Properties.of().mapColor(MapColor.COLOR_PURPLE);
    }

    public static Properties powderSnow() {
        return Properties.of().mapColor(MapColor.SNOW).noCollission();
    }

    public static Properties frogSpawn() {
        return Properties.of().mapColor(MapColor.WATER).noCollission().forceSolidOff().pushReaction(PushReaction.DESTROY).isRedstoneConductor((state, getter, pos) -> false);
    }

    public static Properties frogLight() {
        return Properties.of().mapColor(MapColor.NONE);
    }

    public static Properties decoratedPot() {
        return Properties.of().mapColor(MapColor.TERRACOTTA_RED).pushReaction(PushReaction.DESTROY);
    }

}
