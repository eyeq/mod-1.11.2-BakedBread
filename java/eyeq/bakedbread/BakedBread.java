package eyeq.bakedbread;

import eyeq.bakedbread.event.BakedBreadEventHandler;
import eyeq.util.client.model.UModelCreator;
import eyeq.util.client.model.UModelLoader;
import eyeq.util.client.model.gson.ItemmodelJsonFactory;
import eyeq.util.client.renderer.ResourceLocationFactory;
import eyeq.util.client.resource.ULanguageCreator;
import eyeq.util.client.resource.lang.LanguageResourceManager;
import eyeq.util.item.crafting.UCraftingManager;
import eyeq.util.oredict.UOreDictionary;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.io.File;

import static eyeq.bakedbread.BakedBread.MOD_ID;

@Mod(modid = MOD_ID, version = "1.0", dependencies = "after:eyeq_util")
@Mod.EventBusSubscriber
public class BakedBread {
    public static final String MOD_ID = "eyeq_bakedbread";

    @Mod.Instance(MOD_ID)
    public static BakedBread instance;

    private static final ResourceLocationFactory resource = new ResourceLocationFactory(MOD_ID);

    public static Item dough;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new BakedBreadEventHandler());
        addRecipes();
        if(event.getSide().isServer()) {
            return;
        }
        renderItemModels();
        createFiles();
    }

    @SubscribeEvent
    protected static void registerItems(RegistryEvent.Register<Item> event) {
        dough = new ItemFood(2, 0.2F, false).setUnlocalizedName("dough");

        GameRegistry.register(dough, resource.createResourceLocation("dough"));
    }

    public static void addRecipes() {
        UCraftingManager.removeRecipe(new ItemStack(Items.BREAD), "XXX",
                'X', Items.WHEAT);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(dough), "XXX",
                'X', UOreDictionary.OREDICT_WHEAT));
        GameRegistry.addSmelting(dough, new ItemStack(Items.BREAD), 0.35F);
    }

    @SideOnly(Side.CLIENT)
    public static void renderItemModels() {
        UModelLoader.setCustomModelResourceLocation(dough);
    }

    public static void createFiles() {
        File project = new File("../1.11.2-BakedBread");

        LanguageResourceManager language = new LanguageResourceManager();

        language.register(LanguageResourceManager.EN_US, dough, "Dough");
        language.register(LanguageResourceManager.JA_JP, dough, "パン生地");

        ULanguageCreator.createLanguage(project, MOD_ID, language);

        UModelCreator.createItemJson(project, dough, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
    }
}
