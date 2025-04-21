package electrodynamics.compatibility.mekanism;

import java.util.Objects;
import java.util.function.Predicate;

import electrodynamics.common.settings.ElectroConstants;
import electrodynamics.common.tile.compatibility.TileRotaryUnifier;
import electrodynamics.registers.ElectrodynamicsTiles;
import mekanism.api.Action;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalHandler;
import mekanism.common.capabilities.Capabilities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import voltaic.api.gas.Gas;
import voltaic.api.gas.GasAction;
import voltaic.api.gas.GasStack;
import voltaic.api.gas.PropertyGasTank;
import voltaic.common.network.utils.GasUtilities;
import voltaic.prefab.properties.types.SinglePropertyType;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentProcessor;
import voltaic.prefab.utilities.BlockEntityUtils;

public class MekanismHandler {

    public static final SinglePropertyType<ChemicalStack, RegistryFriendlyByteBuf> CHEMICAL_STACK = new SinglePropertyType<>(
            //
            Objects::equals,
            //
            ChemicalStack.OPTIONAL_STREAM_CODEC,
            //
            writer -> {
                Tag fluidTag = new CompoundTag();
                fluidTag = ChemicalStack.OPTIONAL_CODEC.encode(writer.prop().getValue(), NbtOps.INSTANCE, fluidTag).getOrThrow();
                writer.tag().put(writer.prop().getName(), fluidTag);
            },
            //
            reader -> ChemicalStack.OPTIONAL_CODEC.decode(NbtOps.INSTANCE, reader.tag().getCompound(reader.prop().getName())).getOrThrow().getFirst()
            //
    );

    public static void addDataListener(AddReloadListenerEvent event) {
        event.addListener(GasMapReloadListener.INSTANCE);
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.CHEMICAL.block(), ElectrodynamicsTiles.TILE_ROTARYUNIFIER.get(), (tile, context) -> {
            if (context == null || context != tile.chemicalIO) {
                return null;
            }
            SingleProperty<ChemicalStack> prop = getProp(tile);
            return new IChemicalHandler() {
                @Override
                public int getChemicalTanks() {
                    return 1;
                }

                @Override
                public ChemicalStack getChemicalInTank(int tank) {
                    return prop.getValue();
                }

                @Override
                public void setChemicalInTank(int tank, ChemicalStack stack) {
                    prop.setValue(stack);
                }

                @Override
                public long getChemicalTankCapacity(int tank) {
                    return TileRotaryUnifier.MAX_CHEM_AMOUNT;
                }

                @Override
                public boolean isValid(int tank, ChemicalStack stack) {
                    return GasMapReloadListener.INSTANCE.chemicalToGasMap.containsKey(stack.getChemical()) && (prop.getValue().is(stack.getChemical()) || prop.getValue().isEmpty());
                }

                @Override
                public ChemicalStack insertChemical(int tank, ChemicalStack stack, Action action) {

                    ChemicalStack returner = stack.copy();


                    if (!tile.conversionIsFlipped.getValue()) {
                        return returner;
                    }
                    if (stack.isEmpty() || !isValid(tank, stack)) {
                        return returner;
                    }
                    if (action.simulate()) {
                        if (prop.getValue().isEmpty()) {
                            returner.shrink(Math.min(TileRotaryUnifier.MAX_CHEM_AMOUNT, stack.getAmount()));
                            return returner;
                        }
                        if (!ChemicalStack.isSameChemical(prop.getValue(), stack)) {
                            return stack;
                        }
                        returner.shrink(Math.min(TileRotaryUnifier.MAX_CHEM_AMOUNT - prop.getValue().getAmount(), stack.getAmount()));
                        return returner;
                    }
                    if (prop.getValue().isEmpty()) {
                        long accepted = Math.min(TileRotaryUnifier.MAX_CHEM_AMOUNT, stack.getAmount());
                        returner.shrink(accepted);
                        prop.setValue(stack.copyWithAmount(accepted));
                        return returner;
                    }
                    if (!ChemicalStack.isSameChemical(prop.getValue(), stack)) {
                        return stack;
                    }
                    long filled = TileRotaryUnifier.MAX_CHEM_AMOUNT - prop.getValue().getAmount();

                    ChemicalStack chem = prop.getValue().copy();

                    if (stack.getAmount() < filled) {
                        chem.grow(stack.getAmount());
                        prop.setValue(chem);
                        filled = stack.getAmount();
                    } else {
                        chem.setAmount(TileRotaryUnifier.MAX_CHEM_AMOUNT);
                        prop.setValue(chem);
                    }
                    returner.shrink(filled);
                    return returner;
                }

                @Override
                public ChemicalStack extractChemical(int tank, long amount, Action action) {
                    if (tile.conversionIsFlipped.getValue()) {
                        return ChemicalStack.EMPTY;
                    }
                    long drained = TileRotaryUnifier.MAX_CHEM_AMOUNT;
                    if (prop.getValue().getAmount() < drained) {
                        drained = prop.getValue().getAmount();
                    }
                    ChemicalStack stack = prop.getValue().copyWithAmount(drained);
                    if (action.execute() && drained > 0) {
                        ChemicalStack chem = prop.getValue().copy();
                        chem.shrink(drained);
                        prop.setValue(chem);
                    }
                    return stack;
                }
            };
        });

    }

    public static int addProperty(TileRotaryUnifier tile) {
        return tile.property(new SingleProperty<>(CHEMICAL_STACK, "chemicalstackprop", ChemicalStack.EMPTY)).index();
    }

    public static Predicate<GasStack> getTankPredicate() {
        return stack -> GasMapReloadListener.INSTANCE.gasToChemicalMap.containsKey(stack.getGas());
    }

    public static SingleProperty<ChemicalStack> getProp(TileRotaryUnifier tile) {
        return (SingleProperty<ChemicalStack>) tile.getPropertyManager().getProperties().get(tile.chemStackIndex);
    }

    public static boolean canProcess(TileRotaryUnifier tile, ComponentProcessor proc) {

        SingleProperty<ChemicalStack> prop = getProp(tile);
        ;
        PropertyGasTank tank = tile.gasTank;
        int rate = (int) (ElectroConstants.ROTARY_UNIFIER_CONVERSION_RATE * proc.operatingSpeed.getValue());
        ComponentElectrodynamic electro = tile.getComponent(IComponentType.Electrodynamic);

        if (tile.conversionIsFlipped.getValue()) {

            GasUtilities.outputToPipe(tile, tank.asArray(), BlockEntityUtils.MachineDirection.RIGHT.mappedDir);

            if (electro.getJoulesStored() < proc.getUsage(0) || prop.getValue().isEmpty()) {
                return false;
            }

            Gas gas = GasMapReloadListener.INSTANCE.chemicalToGasMap.get(prop.getValue().getChemical());

            if (gas == null) {
                return false;
            }

            GasStack proposed = new GasStack(gas, rate, gas.getCondensationTemp() + 1, Gas.PRESSURE_AT_SEA_LEVEL);

            if (!tank.isGasValid(proposed)) {
                return false;
            }

            int accepted = tank.fill(proposed, GasAction.SIMULATE);

            return accepted > 0;

        } else {

            BlockEntity faceTile = tile.getLevel().getBlockEntity(tile.getBlockPos().relative(tile.chemicalIO));

            if (faceTile != null && !prop.getValue().isEmpty()) {
                IChemicalHandler handler = faceTile.getLevel().getCapability(Capabilities.CHEMICAL.block(), faceTile.getBlockPos(), faceTile.getBlockState(), faceTile, tile.chemicalIO.getOpposite());

                if (handler != null) {
                    for (int i = 0; i < handler.getChemicalTanks(); i++) {

                        ChemicalStack storedChem = prop.getValue().copy();

                        ChemicalStack accepted = handler.insertChemical(storedChem, Action.EXECUTE);

                        storedChem.shrink(accepted.getAmount());

                        prop.setValue(storedChem);
                    }
                }


            }

            if (electro.getJoulesStored() < proc.getUsage(0)) {
                return false;
            }

            GasStack gas = tank.getGas();

            if (gas.isEmpty()) {
                return false;
            }

            Chemical chemical = GasMapReloadListener.INSTANCE.gasToChemicalMap.get(gas.getGas());

            if (chemical == null || (!prop.getValue().isEmpty() && !prop.getValue().is(chemical)) || gas.getTemperature() > gas.getGas().getCondensationTemp() + 1) {
                return false;
            }

            return (Math.max(0, TileRotaryUnifier.MAX_CHEM_AMOUNT - prop.getValue().getAmount())) > 0;

        }

    }

    public static void process(TileRotaryUnifier tile, ComponentProcessor proc) {

        SingleProperty<ChemicalStack> prop = getProp(tile);
        ;
        PropertyGasTank tank = tile.gasTank;
        int rate = (int) (ElectroConstants.ROTARY_UNIFIER_CONVERSION_RATE * proc.operatingSpeed.getValue());

        if (tile.conversionIsFlipped.getValue()) {

            Gas gas = GasMapReloadListener.INSTANCE.chemicalToGasMap.get(prop.getValue().getChemical());
            GasStack proposed = new GasStack(gas, rate, gas.getCondensationTemp() + 1, Gas.PRESSURE_AT_SEA_LEVEL);
            int accepted = tank.fill(proposed, GasAction.EXECUTE);

            ChemicalStack chemicalStack = prop.getValue().copy();
            chemicalStack.shrink(accepted);
            prop.setValue(chemicalStack);


        } else {

            GasStack gas = tank.getGas();
            Chemical chemical = GasMapReloadListener.INSTANCE.gasToChemicalMap.get(gas.getGas());
            int accepted = (int) Math.min(rate, Math.max(gas.getAmount(), TileRotaryUnifier.MAX_CHEM_AMOUNT - prop.getValue().getAmount()));

            if (prop.getValue().isEmpty()) {
                prop.setValue(new ChemicalStack(chemical, accepted));
            } else {
                ChemicalStack stack = prop.getValue().copy();
                stack.grow(accepted);
                prop.setValue(stack);
            }
            tank.drain(accepted, GasAction.EXECUTE);

        }

    }

}
