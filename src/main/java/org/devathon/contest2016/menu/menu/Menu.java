package org.devathon.contest2016.menu.menu;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.devathon.contest2016.menu.MenuHolder;
import org.devathon.contest2016.menu.MenuItemClickEvent;
import org.devathon.contest2016.menu.Rows;
import org.devathon.contest2016.menu.items.MenuItem;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public abstract class Menu {

    protected final Player player;
    protected final Rows rows;
    protected final String title;
    protected final Map<Integer, MenuItem> menuItems;
    protected final Set<Integer> moveableSlots;

    protected Inventory inventory;

    public Menu(Player player, Rows rows, String title) {
        this.player = player;
        this.rows = rows;
        this.title = title;

        this.menuItems = new HashMap<>();
        this.moveableSlots = new HashSet<>();
    }

    public void onItemClick(MenuItemClickEvent event) {
        MenuItem menuItem = this.menuItems.get(event.getIndex());

        if (menuItem != null) {
            menuItem.onClick(event);
        }
    }

    public final void updateItems() {
        if (this.inventory != null) {
            for (Map.Entry<Integer, MenuItem> entry : this.menuItems.entrySet()) {
                this.inventory.setItem(entry.getKey(), entry.getValue().getIcon().get());
            }
        }
    }

    public void addMoveableSlot(int index) {
        Preconditions.checkState(!this.menuItems.containsKey(index), "You can not add a moveable slot where a menu item already is.");
        this.moveableSlots.add(index);
    }

    public void addMenuItem(int index, MenuItem menuItem) {
        Preconditions.checkState(!this.moveableSlots.contains(index), "You can not set a menu item to a moveable slot.");
        this.menuItems.put(index, menuItem);
    }

    public boolean isMoveableSlot(int index) {
        return this.moveableSlots.contains(index);
    }

    public void removeMenuItem(int index) {
        this.menuItems.remove(index);
    }

    public void open() {
        int size = this.rows.getSize();
        this.inventory = Bukkit.createInventory(new MenuHolder(Bukkit.createInventory(this.player, size, this.title), this), size, this.title);

        this.updateItems();

        this.player.openInventory(this.inventory);
    }

    public Optional<Inventory> getInventory() {
        return Optional.ofNullable(this.inventory);
    }

    public void onClose() {
    }

}