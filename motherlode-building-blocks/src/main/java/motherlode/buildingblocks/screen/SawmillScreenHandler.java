package motherlode.buildingblocks.screen;

import java.util.ArrayList;
import java.util.List;

import motherlode.buildingblocks.MotherlodeModule;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import motherlode.buildingblocks.MotherlodeBuildingBlocks;
import motherlode.buildingblocks.recipe.SawmillingRecipe;

public class SawmillScreenHandler extends ScreenHandler {
    private final ScreenHandlerContext context;
    public final Inventory input = new SimpleInventory(1) {
        public void markDirty() {
            super.markDirty();
            onContentChanged(this);
            screenOnInventoryChanged.run();
        }
    };
    public final Slot inputSlot = addSlot(new Slot(input, 0, 16, 33));
    private final World world;
    private final PlayerInventory playerInventory;
    private ItemStack inputStack = ItemStack.EMPTY;
    private List<SawmillingRecipe> availableRecipes = new ArrayList<>();
    private Runnable screenOnInventoryChanged = ()->{};

    public SawmillScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, ScreenHandlerContext.EMPTY, playerInventory);
    }

    public SawmillScreenHandler(int syncId, ScreenHandlerContext context, PlayerInventory playerInventory) {
        super(MotherlodeModule.SAWMILL_SCREEN_HANDLER, syncId);
        this.context = context;
        this.world = playerInventory.player.world;
        this.playerInventory = playerInventory;

        final int playerInventoryY = 84;
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, playerInventoryY + i * 18));
            }
        }
        for(int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, playerInventoryY + 3*18 + 4));
        }
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        ItemStack itemStack = inputSlot.getStack();
        if (!itemStack.isOf(inputStack.getItem())) {
            availableRecipes.clear();
            if (!itemStack.isEmpty()) {
                this.availableRecipes = world.getRecipeManager().getAllMatches(MotherlodeModule.SAWMILLING_RECIPE_TYPE, input, world);
            }
        }
        inputStack = itemStack.copy();
    }

    public void craft(SawmillingRecipe recipe) {
        if (availableRecipes.contains(recipe) && recipe.matches(input, world)) {
            ItemStack cursorStack = playerInventory.getCursorStack();
            if (cursorStack.isOf(recipe.getOutput().getItem()) && cursorStack.getMaxCount() >= cursorStack.getCount()+recipe.getOutput().getCount()) {
                cursorStack.increment(recipe.craft(input).getCount());
                inputSlot.getStack().decrement(1);
            } else if (cursorStack.isEmpty()) {
                playerInventory.setCursorStack(recipe.craft(input));
                inputSlot.getStack().decrement(1);
            }
            onContentChanged(input);
        }
    }

    public boolean onButtonClick(PlayerEntity player, int id) {
        craft(availableRecipes.get(id));
        return true;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return canUse(context, player, MotherlodeBuildingBlocks.SAWMILL);
    }

    public List<SawmillingRecipe> getAvailableRecipes() {
        return availableRecipes;
    }

    @Environment(EnvType.CLIENT)
    void setScreenOnInventoryChanged(Runnable screenOnInventoryChanged) {
        this.screenOnInventoryChanged = screenOnInventoryChanged;
    }

    @Environment(EnvType.CLIENT)
    public boolean canCraft() {
        return this.inputSlot.hasStack() && !this.availableRecipes.isEmpty();
    }

    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (index == 0) {
                if (!insertItem(itemStack2, 1, 37, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (world.getRecipeManager().getFirstMatch(MotherlodeModule.SAWMILLING_RECIPE_TYPE, new SimpleInventory(new ItemStack[]{itemStack2}), this.world).isPresent()) {
                if (!insertItem(itemStack2, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 1 && index < 28) {
                if (!insertItem(itemStack2, 28, 37, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 28 && index < 37 && !this.insertItem(itemStack2, 1, 28, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            }

            slot.markDirty();
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTakeItem(player, itemStack2);
            sendContentUpdates();
        }

        return itemStack;
    }

    public void close(PlayerEntity player) {
        super.close(player);
        context.run((world, blockPos) -> {
            dropInventory(player, input);
        });
    }
}
