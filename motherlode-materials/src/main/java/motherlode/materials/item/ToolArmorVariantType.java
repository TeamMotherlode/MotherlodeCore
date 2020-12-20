package motherlode.materials.item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;
import motherlode.base.api.assets.CommonAssets;
import motherlode.base.api.assets.CommonData;
import motherlode.base.Motherlode;
import motherlode.base.api.assets.AssetProcessor;
import motherlode.base.api.assets.DataProcessor;
import motherlode.base.api.varianttype.RegisterableVariantType;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;

public class ToolArmorVariantType implements RegisterableVariantType<Item>, AssetProcessor, DataProcessor {
    public final Item PICKAXE;
    public final Item SWORD;
    public final Item AXE;
    public final Item SHOVEL;
    public final Item HOE;

    public final Item HELMET;
    public final Item CHESTPLATE;
    public final Item LEGGINGS;
    public final Item BOOTS;

    private final String material;

    private final List<Pair<Identifier, Item>> ALL = new ArrayList<>();

    public ToolArmorVariantType(Identifier id, String material, DefaultToolMaterial toolMaterial, ArmorMaterial armorMaterial) {
        this.PICKAXE = new MaterialPickaxe(toolMaterial);
        this.SWORD = new SwordItem(toolMaterial, 3, -2.4F, new Item.Settings().maxCount(1).group(ItemGroup.COMBAT));
        this.AXE = new MaterialAxe(toolMaterial);
        this.SHOVEL = new ShovelItem(toolMaterial, 1.5F, 3.0F, new Item.Settings().maxCount(1).group(ItemGroup.TOOLS));
        this.HOE = new MaterialHoe(toolMaterial);

        this.HELMET = new ArmorItem(armorMaterial, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT));
        this.CHESTPLATE = new ArmorItem(armorMaterial, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));
        this.LEGGINGS = new ArmorItem(armorMaterial, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT));
        this.BOOTS = new ArmorItem(armorMaterial, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT));

        this.material = material;

        ALL.add(new Pair<>(Motherlode.id(id.getNamespace(), id.getPath() + "_pickaxe"), this.PICKAXE));
        ALL.add(new Pair<>(Motherlode.id(id.getNamespace(), id.getPath() + "_sword"), this.SWORD));
        ALL.add(new Pair<>(Motherlode.id(id.getNamespace(), id.getPath() + "_axe"), this.AXE));
        ALL.add(new Pair<>(Motherlode.id(id.getNamespace(), id.getPath() + "_shovel"), this.SHOVEL));
        ALL.add(new Pair<>(Motherlode.id(id.getNamespace(), id.getPath() + "_hoe"), this.HOE));

        ALL.add(new Pair<>(Motherlode.id(id.getNamespace(), id.getPath() + "_helmet"), this.HELMET));
        ALL.add(new Pair<>(Motherlode.id(id.getNamespace(), id.getPath() + "_chestplate"), this.CHESTPLATE));
        ALL.add(new Pair<>(Motherlode.id(id.getNamespace(), id.getPath() + "_leggings"), this.LEGGINGS));
        ALL.add(new Pair<>(Motherlode.id(id.getNamespace(), id.getPath() + "_boots"), this.BOOTS));
    }

    @Override
    public void register(Identifier identifier) {
        for (Pair<Identifier, Item> entry : ALL) {
            Registry.register(Registry.ITEM, entry.getLeft(), entry.getRight());
        }
    }

    @Override
    public Item[] variants() {
        return ALL.stream().map(Pair::getRight).collect(Collectors.toList()).toArray(new Item[ALL.size()]);
    }

    @Override
    public void accept(ArtificeResourcePack.ClientResourcePackBuilder pack, Identifier id) {
        for (Pair<Identifier, Item> entry : ALL) {
            CommonAssets.DEFAULT_ITEM_MODEL.accept(pack, entry.getLeft());
        }
    }

    @Override
    public void accept(ArtificeResourcePack.ServerResourcePackBuilder pack, Identifier id) {
        for (Pair<Identifier, Item> entry : ALL) {
            CommonData.ITEM_TAG.apply(Motherlode.id(CommonData.COMMON_NAMESPACE, entry.getLeft().getPath()))
                .accept(pack, entry.getLeft());
        }

        Identifier material = Motherlode.id(CommonData.COMMON_NAMESPACE, this.material);
        Identifier stick = Motherlode.id("minecraft", "stick");

        // Pickaxe
        pack.addShapedRecipe(Motherlode.id(id.getNamespace(), id.getPath() + "_pickaxe"), recipe -> recipe
            .pattern("***", " | ", " | ")
            .ingredientTag('*', material)
            .ingredientItem('|', stick)
            .result(Motherlode.id(id.getNamespace(), id.getPath() + "_pickaxe"), 1));

        // Sword
        pack.addShapedRecipe(Motherlode.id(id.getNamespace(), id.getPath() + "_sword"), recipe -> recipe
            .pattern("*", "*", "|")
            .ingredientTag('*', material)
            .ingredientItem('|', stick)
            .result(Motherlode.id(id.getNamespace(), id.getPath() + "_sword"), 1));

        // Axe
        pack.addShapedRecipe(Motherlode.id(id.getNamespace(), id.getPath() + "_axe"), recipe -> recipe
            .pattern("** ", "*| ", " | ")
            .ingredientTag('*', material)
            .ingredientItem('|', stick)
            .result(Motherlode.id(id.getNamespace(), id.getPath() + "_axe"), 1)
            .group(Motherlode.id(id.getNamespace(), id.getPath() + "_axe")));

        // Axe reversed
        pack.addShapedRecipe(Motherlode.id(id.getNamespace(), id.getPath() + "_axe_reversed"), recipe -> recipe
            .pattern(" **", " |*", " | ")
            .ingredientTag('*', material)
            .ingredientItem('|', stick)
            .result(Motherlode.id(id.getNamespace(), id.getPath() + "_axe"), 1)
            .group(Motherlode.id(id.getNamespace(), id.getPath() + "_axe")));

        // Shovel
        pack.addShapedRecipe(Motherlode.id(id.getNamespace(), id.getPath() + "_shovel"), recipe -> recipe
            .pattern("*", "|", "|")
            .ingredientTag('*', material)
            .ingredientItem('|', stick)
            .result(Motherlode.id(id.getNamespace(), id.getPath() + "_shovel"), 1));

        // Hoe
        pack.addShapedRecipe(Motherlode.id(id.getNamespace(), id.getPath() + "_hoe"), recipe -> recipe
            .pattern("** ", " | ", " | ")
            .ingredientTag('*', material)
            .ingredientItem('|', stick)
            .result(Motherlode.id(id.getNamespace(), id.getPath() + "_hoe"), 1)
            .group(Motherlode.id(id.getNamespace(), id.getPath() + "_hoe")));

        // Hoe reversed
        pack.addShapedRecipe(Motherlode.id(id.getNamespace(), id.getPath() + "_hoe_reversed"), recipe -> recipe
            .pattern(" **", " | ", " | ")
            .ingredientTag('*', material)
            .ingredientItem('|', stick)
            .result(Motherlode.id(id.getNamespace(), id.getPath() + "_hoe"), 1)
            .group(Motherlode.id(id.getNamespace(), id.getPath() + "_hoe")));

        // Helmet
        pack.addShapedRecipe(Motherlode.id(id.getNamespace(), id.getPath() + "_helmet"), recipe -> recipe
            .pattern("***", "* *", "   ")
            .ingredientTag('*', material)
            .result(Motherlode.id(id.getNamespace(), id.getPath() + "_helmet"), 1));

        // Chestplate
        pack.addShapedRecipe(Motherlode.id(id.getNamespace(), id.getPath() + "_chestplate"), recipe -> recipe
            .pattern("* *", "***", "***")
            .ingredientTag('*', material)
            .result(Motherlode.id(id.getNamespace(), id.getPath() + "_chestplate"), 1));

        // Leggings
        pack.addShapedRecipe(Motherlode.id(id.getNamespace(), id.getPath() + "_leggings"), recipe -> recipe
            .pattern("* *", "* *", "***")
            .ingredientTag('*', material)
            .result(Motherlode.id(id.getNamespace(), id.getPath() + "_leggings"), 1));

        // Boots
        pack.addShapedRecipe(Motherlode.id(id.getNamespace(), id.getPath() + "_boots"), recipe -> recipe
            .pattern("   ", "* *", "* *")
            .ingredientTag('*', material)
            .result(Motherlode.id(id.getNamespace(), id.getPath() + "_boots"), 1));
    }
}

class MaterialPickaxe extends PickaxeItem {
    MaterialPickaxe(DefaultToolMaterial material) {
        super(material, 1, -2.8F, new Settings().maxCount(1).group(ItemGroup.TOOLS));
    }
}

class MaterialAxe extends AxeItem {
    MaterialAxe(DefaultToolMaterial material) {
        super(material, 6, -3.1F, new Settings().maxCount(1).group(ItemGroup.TOOLS));
    }
}

class MaterialHoe extends HoeItem {
    MaterialHoe(DefaultToolMaterial material) {
        super(material, -2, -1F, new Settings().maxCount(1).group(ItemGroup.TOOLS));
    }
}
