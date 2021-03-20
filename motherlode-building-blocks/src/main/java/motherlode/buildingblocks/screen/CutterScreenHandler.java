package motherlode.buildingblocks.screen;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CuttingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public abstract class CutterScreenHandler extends ScreenHandler {
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
    private List<? extends CuttingRecipe> availableRecipes = new ArrayList<>();
    private Runnable screenOnInventoryChanged = () -> { };

    public CutterScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerType<? extends CutterScreenHandler> handlerType) {
        this(syncId, ScreenHandlerContext.EMPTY, playerInventory, handlerType);
    }

    public CutterScreenHandler(int syncId, ScreenHandlerContext context, PlayerInventory playerInventory, ScreenHandlerType<? extends CutterScreenHandler> handlerType) {
        super(handlerType, syncId);
        this.context = context;
        this.world = playerInventory.player.world;
        this.playerInventory = playerInventory;

        final int playerInventoryY = 84;
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, playerInventoryY + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, playerInventoryY + 3 * 18 + 4));
        }
    }

    public abstract RecipeType<? extends CuttingRecipe> getRecipeType();

    public abstract Block getBlock();

    @Override
    public void onContentChanged(Inventory inventory) {
        ItemStack itemStack = inputSlot.getStack();
        if (!itemStack.isOf(inputStack.getItem())) {
            availableRecipes.clear();
            if (!itemStack.isEmpty()) {
                this.availableRecipes = world.getRecipeManager().getAllMatches(getRecipeType(), input, world);
            }
        }
        inputStack = itemStack.copy();

        super.onContentChanged(inventory);
    }

    public <T extends CuttingRecipe> void craft(T recipe) {
        if (availableRecipes.contains(recipe) && recipe.matches(input, world)) {
            ItemStack cursorStack = this.getCursorStack();
            if (cursorStack.isOf(recipe.getOutput().getItem()) && cursorStack.getMaxCount() >= cursorStack.getCount()+recipe.getOutput().getCount()) {
                cursorStack.increment(recipe.craft(input).getCount());
                inputSlot.getStack().decrement(1);
            } else if (cursorStack.isEmpty()) {
                this.setCursorStack(recipe.craft(input));
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
        Block block = getBlock();
        return block != null && canUse(context, player, block);
    }

    public List<? extends CuttingRecipe> getAvailableRecipes() {
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
            } else if (world.getRecipeManager().getFirstMatch(getRecipeType(), new SimpleInventory(itemStack2), this.world).isPresent()) {
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
