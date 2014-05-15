package com.goreacraft.plugins.goreaguns;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Methods {

	//static HashMap<String,UUID> ents = new HashMap<String,UUID>();
	
	/*public static void startSpawnPoints()
	{
		{
			Set<String> points = GoreaGuns.spawnPoints.getKeys(false);	
			{
						for (String point : points)		
						{		
							
						new Runnables(point);
						}	
			}
		}
	}*/

	/*
	protected static void spawnItem(String nr) {
		SpawnPoint sp = GoreaGuns.tasks.get(nr);
		Location loc = sp.getLocation();
		Chunk c = loc.getChunk();
		if(!ents.isEmpty())
		{
		UUID id = ents.get(nr); 
		
		for (Entity e: c.getEntities()) {
		    if (e.getUniqueId() == id) {
	
		    	//Methods.findPlayerByString("gorea01").sendMessage(" Found entity.. stoping spawning");
				return;
		    }
		}
		}
	
		
		
		Item item = Bukkit.getServer().getWorld(sp.getWorldName()).dropItem(loc.add(0, 0.4, 0), sp.getItemStack());
		item.setVelocity(new Vector(0, 0, 0));
		
		
		ents.put(nr, item.getUniqueId());
		//item.teleport(loc);
	}*/
	protected static boolean check(ItemStack item, Location loc){
		
		if (loc.getBlock().isEmpty())
		return true;
		else return false;
		
	}
	
	/*protected ItemStack getItemStack(String nr){
		
		Set<String> itemA = GoreaGuns.spawnPoints.getConfigurationSection(nr).getKeys(false);
		String itemB = itemA.iterator().next();
		String[] parts = itemB.split(":");
		ItemStack item = new ItemStack(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
		return item;
	}*/

	protected void setSpawnPoint() {
	/*	HashMap<String, Object> values = new HashMap<String,Object>();
		//String loc = Methods.locationToString(((Player) sender).getLocation());
		 
		values.put("Item", player.getItemInHand().getTypeId());
		values.put("Meta", player.getItemInHand().getItemMeta());
		values.put("Amount", player.getItemInHand().getAmount());
		values.put("Location", Methods.locationToString(player.getLocation()));
		values.put("Timer", args[2]);*/
		
	}
	static Location locationfromString(String string)
	 {
		 String[] locs = string.split(" ");
		 
		 //add world
		 Location loc = new Location(
				 Bukkit.getWorld(locs[0]), 
				 Double.parseDouble(locs[1]), 
				 Double.parseDouble(locs[2]), 
				 Double.parseDouble(locs[3]));
		 
		return loc;
		 
	 }
	static String cleanlocationString(String string)
	 {
		 String[] locs = string.split(" ");
		 
		 //add world
		// Location loc = new Location(
		 
		 int x = (int) Double.parseDouble(locs[1]);
		 int y = (int) Double.parseDouble(locs[2]);
		 int z = (int) Double.parseDouble(locs[3]);
		 
			String clean = 	locs[0] + " " + 
				 x + "," +  
				 y + "," +  
				 z;
		 
		return clean;
		 
	 }
	
	static String locationToString(Location loc)
	{
		String location = loc.getWorld().getName() + " " + loc.getX() + " "+ loc.getY() + " " + loc.getZ();
		
				return location;
	}

	/*public static void setpoints() {
		Set<String> points = GoreaGuns.spawnPoints.getKeys(false);	
	
		
		{
		for (String point : points)
		{
			SpawnPoint entry = new SpawnPoint();
			entry.setItemid(GoreaGuns.spawnPoints.getInt(point+".Item"));
			entry.setMeta(Short.parseShort(GoreaGuns.spawnPoints.getString(point+".Meta")));
			entry.setAmount(GoreaGuns.spawnPoints.getInt(point+".Amount"));
			entry.setLocation(GoreaGuns.spawnPoints.getString(point+".Location"));
			entry.setTimer(GoreaGuns.spawnPoints.getLong(point+".Timer"));
			GoreaGuns.tasks.put(point, entry);
		//	GoreaGuns.tasksids.put(point, point));
			//listspawnpoints.add(entry);
		}*/
			
			//Set<String> itemA = GoreaGuns.spawnPoints.getConfigurationSection(point).getKeys(false);
			//String item = itemA.iterator().next();
			
			
			
			
			
			
			
		
		//}
		
	//}
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    return true;
	}
	public static boolean isDouble(String s) {
	    try { 
	        Double.parseDouble(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    return true;
	}
	
	public static Player findPlayerByString(String name) 
	{
		for ( Player player : Bukkit.getServer().getOnlinePlayers())
		{
			if(player.getName().equals(name)) 
			{
				return player;
			}
		}
		
		return null;
	}
}
