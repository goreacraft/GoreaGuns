package com.goreacraft.plugins.goreaguns;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.bukkit.WGBukkit;

public class PickupEvent implements Listener {
	
	
	
	@EventHandler
	   public void onPlayerRightClick(PlayerPickupItemEvent e)
	{

		String itemname = e.getItem().getItemStack().getType().toString();
		if(!GoreaGuns.powerUps.isConfigurationSection("PowerUp"))
				{
			System.out.println("[WARNING] POWERUPS FILE DOES NOT CONTAIN ENTRY 'PowerUp'");
			return;
				}
		
		
	if(GoreaGuns.powerUps.isConfigurationSection("PowerUp."+ itemname))
	{
		short meta = e.getItem().getItemStack().getDurability();
		if(GoreaGuns.powerUps.getConfigurationSection("PowerUp."+ itemname).getKeys(false).contains("Damagevalue"))
		if(GoreaGuns.powerUps.getInt("PowerUp."+ itemname + ".Damagevalue")==meta)
			{	
			Player player = e.getPlayer();
			//String name = player.getName();
		if(	WGBukkit.getRegionManager(player.getWorld()).getRegionExact(GoreaGuns.powerUps.getString("PowerUp."+ itemname + ".Region")) == null)
		{
			//Methods.findPlayerByString("goreacraft").sendMessage("not here");
			return;
		}
		//Methods.findPlayerByString("goreacraft").sendMessage("is here");
	Vector pt = new Vector(player.getLocation().getX(),player.getLocation().getY(),player.getLocation().getZ());
	List<String> regions = WGBukkit.getRegionManager(player.getWorld()).getApplicableRegionsIDs(pt);//.getApplicableRegions(player.getLocation());			
	//for(ProtectedRegion region:regions)
	if(!regions.isEmpty() && regions.contains(GoreaGuns.powerUps.getString("PowerUp."+ itemname + ".Region")))
		{		
			
			if(GoreaGuns.powerUps.getConfigurationSection("PowerUp."+ itemname).getKeys(false).contains("Effect"))
			if(GoreaGuns.efects.contains(GoreaGuns.powerUps.getString("PowerUp."+ itemname + ".Effect")))	
				{ //normal effects
				
				//ApplicableRegionSet regions = WGBukkit.getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation());			
				//for(ProtectedRegion region:regions)
					
					//if(GoreaGuns.powerUps.getConfigurationSection("PowerUp."+ itemname).getKeys(false).contains("Regions"))
					//if(GoreaGuns.powerUps.getString("PowerUp."+ itemname + ".Region").contains(regions.getId()) )
						{
						e.setCancelled(true);
						Location loc = e.getItem().getLocation();
						
							for ( Entity ent :Bukkit.getServer().getWorld(loc.getWorld().getName()).getEntities())
								{
								if(ent.getUniqueId().equals(e.getItem().getUniqueId()))
									{
										ent.remove();
									}
								}
								PotionEffectType effect = PotionEffectType.getByName(GoreaGuns.powerUps.getString("PowerUp."+ itemname + ".Effect", "SPEED"));
								player.addPotionEffect( effect.createEffect(GoreaGuns.powerUps.getInt("PowerUp."+ itemname + ".Duration", 20), 
																			GoreaGuns.powerUps.getInt("PowerUp."+ itemname + ".Amplifier", 0)), true);
							
								return;
								
						}
					
				} else {
					//if my effects
					if(GoreaGuns.powerUps.getString("PowerUp."+ itemname + ".Effect").equalsIgnoreCase("DamageBoost"))
					{
						
						player.sendMessage(ChatColor.AQUA + "DamageBoost");
						double amplifier = GoreaGuns.powerUps.getDouble("PowerUp."+ itemname + ".Amplifier");
						//adddamageboost(name, amplifier);
						GoreaGuns.damageboostlist.put(player.getName(), amplifier);
						int taskid = new BukkitRunnable() {
	            			@Override
							public void run() {
	            				
	            				
	            				if(GoreaGuns.damageboostlist.keySet().contains(GoreaGuns.damageboostlisttasks.get(getTaskId())))
	            				GoreaGuns.damageboostlist.remove(GoreaGuns.damageboostlisttasks.get(getTaskId()));
	            				Methods.findPlayerByString(GoreaGuns.damageboostlisttasks.get(getTaskId())).sendMessage(ChatColor.AQUA + "DamageBoost worn off");
	            				GoreaGuns.damageboostlisttasks.remove(getTaskId());
	            			}
						}.runTaskLater(GoreaGuns.gg, GoreaGuns.powerUps.getInt("PowerUp."+ itemname + ".Duration")).getTaskId();
						GoreaGuns.damageboostlisttasks.put(taskid, player.getName());
						e.setCancelled(true);
						Location loc = e.getItem().getLocation();
							for ( Entity ent :Bukkit.getServer().getWorld(loc.getWorld().getName()).getEntities())
								{
								if(ent.getUniqueId().equals(e.getItem().getUniqueId()))
									{
										ent.remove();
									}
								}
							return;
					} 
					else 
					{
						if(GoreaGuns.powerUps.getString("PowerUp."+ itemname + ".Effect").equalsIgnoreCase("Shield"))
						{
							
							//player.sendMessage(ChatColor.AQUA + "Shield");
							//addeffect(name, "Shield");
							e.setCancelled(true);
							Location loc = e.getItem().getLocation();
							if(GoreaGuns.powerUps.getConfigurationSection("PowerUp."+ itemname).getKeys(false).contains("Shield"))
							{
								if(GoreaGuns.emax)
								{
									int existent = 0;
									if(GoreaGuns.armours.keySet().contains(player.getName()))
									{
										existent = GoreaGuns.armours.get(player.getName());
										if (existent>=GoreaGuns.maxshield)
										{

											if(!GoreaGuns.shieldmessage.values().contains(player.getName()))
											{
												player.sendMessage(ChatColor.AQUA + "Shield is already at max: " + existent);
											int taskid = new BukkitRunnable() {
						            			@Override
												public void run() {
						            				GoreaGuns.shieldmessage.remove(getTaskId());
						            			}
											}.runTaskLater(GoreaGuns.gg, 100).getTaskId();
											GoreaGuns.shieldmessage.put(taskid, player.getName());
											}
											e.setCancelled(true);
											
											return;
										}
									}								
									 
									int itemsarmor = GoreaGuns.powerUps.getInt("PowerUp."+ itemname + ".Shield");
									int total = existent + itemsarmor;
									if( total >= GoreaGuns.maxshield)
									{
										GoreaGuns.armours.put(player.getName(), GoreaGuns.maxshield);
										player.sendMessage(ChatColor.AQUA + "Shield at max: " + GoreaGuns.maxshield);
									} else 
										{
										GoreaGuns.armours.put(player.getName(), total);
										player.sendMessage(ChatColor.AQUA + "Shield: " + total);
										}
									
								} 
								else 
									{
										if(GoreaGuns.armours.keySet().contains(player.getName()))
										{
											
											
											if(GoreaGuns.armours.get(player.getName()) < GoreaGuns.powerUps.getInt("PowerUp."+ itemname + ".Shield"))
											{
												GoreaGuns.armours.put(player.getName(), GoreaGuns.powerUps.getInt("PowerUp."+ itemname + ".Shield"));
												player.sendMessage(ChatColor.AQUA + "Shield: " + GoreaGuns.powerUps.getInt("PowerUp."+ itemname + ".Shield"));
												
												for ( Entity ent :Bukkit.getServer().getWorld(loc.getWorld().getName()).getEntities())
												{
												if(ent.getUniqueId().equals(e.getItem().getUniqueId()))
													{
														ent.remove();
													}
												}
											} else {
												
												if(!GoreaGuns.shieldmessage.values().contains(player.getName()))
												{
												player.sendMessage(ChatColor.AQUA + "You have better shield active: " + GoreaGuns.armours.get(player.getName()));
												int taskid = new BukkitRunnable() {
							            			@Override
													public void run() {
							            				GoreaGuns.shieldmessage.remove(getTaskId());
							            			}
												}.runTaskLater(GoreaGuns.gg, 100).getTaskId();
												GoreaGuns.shieldmessage.put(taskid, player.getName());
												}
												
												e.setCancelled(true);
												return;
											}
										} else {
											GoreaGuns.armours.put(player.getName(), GoreaGuns.powerUps.getInt("PowerUp."+ itemname + ".Shield"));
											player.sendMessage(ChatColor.AQUA + "Shield: " + GoreaGuns.powerUps.getInt("PowerUp."+ itemname + ".Shield"));
											
													for ( Entity ent :Bukkit.getServer().getWorld(loc.getWorld().getName()).getEntities())
													{
													if(ent.getUniqueId().equals(e.getItem().getUniqueId()))
														{
															ent.remove();
														}
													}
												}
									}
							
								} else player.sendMessage("[WARNING] PowerUp."+ itemname + " Does not have the 'Shield' value, contact an admin");
								//dddddd
									for ( Entity ent :Bukkit.getServer().getWorld(loc.getWorld().getName()).getEntities())
										{
										if(ent.getUniqueId().equals(e.getItem().getUniqueId()))
											{
												ent.remove();
											}
										}
									return;
							}
												
						else {
							if(GoreaGuns.powerUps.getString("PowerUp."+ itemname + ".Effect").equalsIgnoreCase("Reflect"))
							{ 
								
								player.sendMessage(ChatColor.AQUA + "Reflect");
								//addeffect(name, "Reflect");
								int taskid = new BukkitRunnable() {
			            			@Override
									public void run() {
			            				Methods.findPlayerByString(GoreaGuns.reflects.get(getTaskId())).sendMessage(ChatColor.AQUA + "Reflect worn off");
			            				GoreaGuns.reflects.remove(getTaskId());
			            			}
								}.runTaskLater(GoreaGuns.gg, GoreaGuns.powerUps.getInt("PowerUp."+ itemname + ".Duration")).getTaskId();
								GoreaGuns.reflects.put(taskid, player.getName());
								e.setCancelled(true);
								Location loc = e.getItem().getLocation();
									for ( Entity ent :Bukkit.getServer().getWorld(loc.getWorld().getName()).getEntities())
										{
										if(ent.getUniqueId().equals(e.getItem().getUniqueId()))
											{
												ent.remove();
											}
										}
									return;
							} else {
								if(GoreaGuns.powerUps.getString("PowerUp."+ itemname + ".Effect").equalsIgnoreCase("Invulnerability"))
								{
								
									player.sendMessage(ChatColor.AQUA + "Invulnerability");
									//addeffect(name, "Invulnerability");
									int taskid = new BukkitRunnable() {
				            			@Override
										public void run() {
				            				
				            				Methods.findPlayerByString(GoreaGuns.invulns.get(getTaskId())).sendMessage(ChatColor.AQUA + "Invulnerability worn off");
				            				GoreaGuns.invulns.remove(getTaskId());
				            			}
									}.runTaskLater(GoreaGuns.gg, GoreaGuns.powerUps.getInt("PowerUp."+ itemname + ".Duration")).getTaskId();
									
									GoreaGuns.invulns.put(taskid, player.getName());
									
									e.setCancelled(true);
									Location loc = e.getItem().getLocation();
										for ( Entity ent :Bukkit.getServer().getWorld(loc.getWorld().getName()).getEntities())
											{
											if(ent.getUniqueId().equals(e.getItem().getUniqueId()))
												{
													ent.remove();
												}
											}
								}
								return;
						
						
					}
					
					
						}
				}
			}
		}
		
	}
	}
	}
public static Vector getvector(double x,double y,double z ){
	return null;
	
}
	 
}
