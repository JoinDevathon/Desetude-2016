package org.devathon.contest2016.machine;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.function.Function;

public class Process {

    private final Function<ItemStack, Integer> inputAmountRequired;
    private final ItemStack output;
    private final long processingPower;

    private Process(Function<ItemStack, Integer> inputAmountRequired, ItemStack output, long processingPower) {
        this.inputAmountRequired = inputAmountRequired;
        this.output = output;
        this.processingPower = processingPower;
    }

    public static Process create(Function<ItemStack, Integer> inputAmountRequired, ItemStack output, long processingPower) {
        return new Process(inputAmountRequired, output, processingPower);
    }

    public static Process create(Material material, int inputData, int inputAmount, ItemStack output, long processingPower) {
        return create(item -> {
            if (item.getType().equals(material) && item.getDurability() == inputData) {
                return inputAmount;
            }

            return -1;
        }, output, processingPower);
    }

    public static Process create(Material material, int inputData, int inputAmount, Material output, long processingPower) {
        return create(material, inputAmount, new ItemStack(output), processingPower);
    }

    public static Process create(Material material, int inputAmount, ItemStack output, long processingPower) {
        return create(item -> {
            if (item.getType().equals(material)) {
                return inputAmount;
            }

            return -1;
        }, output, processingPower);
    }

    public static Process create(Material material, int inputAmount, Material output, long processingPower) {
        return create(material, inputAmount, new ItemStack(output), processingPower);
    }

    public static Process create(Material material, ItemStack output, long processingPower) {
        return create(material, 1, output, processingPower);
    }

    public static Process create(Material material, Material output, long processingPower) {
        return create(material, new ItemStack(output), processingPower);
    }

    public int getAmountRequired(ItemStack itemStack) {
        return this.inputAmountRequired.apply(itemStack);
    }

    public ItemStack getOutput() {
        return this.output;
    }

    public long getProcessingPower() {
        return this.processingPower;
    }

}
