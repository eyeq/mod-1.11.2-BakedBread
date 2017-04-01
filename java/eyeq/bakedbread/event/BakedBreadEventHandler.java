package eyeq.bakedbread.event;

import net.minecraft.init.Items;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class BakedBreadEventHandler {
    @SubscribeEvent
    public void onItemSmelted(PlayerEvent.ItemSmeltedEvent event) {
        if(event.smelting.getItem() == Items.BREAD) {
            event.player.addStat(AchievementList.MAKE_BREAD);
        }
    }
}
