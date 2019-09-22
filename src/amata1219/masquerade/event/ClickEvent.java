package amata1219.masquerade.event;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class ClickEvent {

	public final Player player;
	public final Inventory inventory;
	public final InventoryAction action;
	public final ClickType clickType;
	public final SlotType slotType;
	public final int clickedSlot;
	public final int rawSlot;
	public final ItemStack current;
	public final ItemStack cursor;
	public final int hotbarButton;
	public final InventoryView view;

	public ClickEvent(InventoryClickEvent event){
		player = (Player) event.getWhoClicked();
		inventory = event.getClickedInventory();
		clickType = event.getClick();
		slotType = event.getSlotType();
		action = event.getAction();
		clickedSlot = event.getSlot();
		rawSlot = event.getRawSlot();
		current = event.getCurrentItem();
		cursor = event.getCursor();
		hotbarButton = event.getHotbarButton();
		view = event.getView();
	}

	public boolean isOutOfInventory(){
		return inventory == null;
	}

	public boolean isCreativeAction(){
		return clickType.isCreativeAction();
	}

	public boolean isKeyboardClick(){
		return clickType.isKeyboardClick();
	}

	public boolean isRightClick(){
		return clickType.isRightClick();
	}

	public boolean isLeftClick(){
		return clickType.isLeftClick();
	}

	public boolean isShiftClick(){
		return clickType.isShiftClick();
	}

}
