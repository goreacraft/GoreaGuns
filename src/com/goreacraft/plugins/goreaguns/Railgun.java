package com.goreacraft.plugins.goreaguns;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.util.Vector;

import com.sk89q.worldguard.bukkit.WGBukkit;



public class Railgun implements Listener{
	

	static HashMap<String, Long> railguncooldown = new HashMap<String, Long>();
	static HashMap<String, Long> bazookacooldown = new HashMap<String, Long>();
	static HashMap<String, Long> pulseguncooldown = new HashMap<String, Long>();
	private double boost = 1;
	private HashMap<String, Long> shootguncooldown= new HashMap<String, Long>();;
	
	
	
	@EventHandler
	   public void onPlayerRightClick(PlayerInteractEvent e) 
	   {
		 if(e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK))
		 {
			Player player = e.getPlayer();	
			if (player.getItemInHand().isSimilar(GoreaGuns.railgun) || player.getItemInHand().isSimilar(GoreaGuns.bazooka ) || player.getItemInHand().isSimilar(GoreaGuns.pulsegun)|| player.getItemInHand().isSimilar(GoreaGuns.shootgun))
			{
			String name = player.getName();
			World world = e.getPlayer().getWorld();
			Location loc = player.getLocation();
			//player.sendMessage("1");	
				if (player.getItemInHand().isSimilar(GoreaGuns.railgun))// && player.hasPermission("gorea.railgun")) 
					{
					List<String> regions = new ArrayList<String>();
					com.sk89q.worldedit.Vector vec = new com.sk89q.worldedit.Vector(loc.getX(),loc.getY(),loc.getZ());
					regions = WGBukkit.getRegionManager(world).getApplicableRegionsIDs(vec);	
						if(!regions.isEmpty() && regions.contains(GoreaGuns.railgunarenas))
						{	
							if(GoreaGuns.guns.isConfigurationSection("Guns.Railgun.Sound") && GoreaGuns.guns.getConfigurationSection("Guns.Railgun.Sound").getKeys(false).contains("Enable"))
								if(GoreaGuns.guns.getBoolean("Guns.Railgun.Sound.Enable"))
									{
									
									String sound = GoreaGuns.guns.getString("Guns.Railgun.Sound.Sound", "BURP");
									float volume = GoreaGuns.guns.getLong("Guns.Railgun.Sound.Volume", 1);	
									float pitch = GoreaGuns.guns.getLong("Guns.Railgun.Sound.Pitch", 1);	
									player.getWorld().playSound(player.getLocation(),Sound.valueOf(sound), volume, pitch);
									}
							long timpacum = new Long(Calendar.getInstance().getTimeInMillis() / 100);
							if(!(railguncooldown.containsKey(name)) || (timpacum - railguncooldown.get(name)) > GoreaGuns.railguncooldown)// || player.hasPermission("goreaguns.railgun.cooldown"))
		            			{
									Arrow f = player.launchProjectile(Arrow.class);
									Vector b = f.getVelocity().multiply(GoreaGuns.railgunvelocity);
									f.setVelocity(b);							
									f.setBounce(false);									
									railguncooldown.put(name, timpacum);
									e.setCancelled(true);
		            			}							
							return;
						}						
					}	
					if (player.getItemInHand().isSimilar(GoreaGuns.bazooka ))// && player.hasPermission("gorea.bazooka"))
						{							
						//if( WGBukkit.getRegionManager(world)..hasRegion(GoreaGuns.bazookaarenas))
						List<String> regions = new ArrayList<String>();
						com.sk89q.worldedit.Vector vec = new com.sk89q.worldedit.Vector(loc.getX(),loc.getY(),loc.getZ());
						regions = WGBukkit.getRegionManager(world).getApplicableRegionsIDs(vec);							 
							if(!regions.isEmpty() && regions.contains(GoreaGuns.bazookaarenas))//WGBukkit.getRegionManager(world).getRegion(GoreaGuns.bazookaarenas).contains(loc.getBlockX(),loc.getBlockY(),loc.getBlockZ()))
								{
									long timpacum = new Long(Calendar.getInstance().getTimeInMillis() / 100);
									if(!(bazookacooldown.containsKey(name)) || (timpacum - bazookacooldown.get(name)) > GoreaGuns.bazookacooldown)// || player.hasPermission("goreaguns.railgun.cooldown"))
			            			{
										Fireball f = player.launchProjectile(Fireball.class);
										Vector b = f.getVelocity().multiply(GoreaGuns.bazookavelocity);
										f.setVelocity(b);							
										f.setBounce(false);
										f.setYield(0);
										e.setCancelled(true);																		
										bazookacooldown.put(name, timpacum);
										return;	
			            			}
								}							
						}							
		 if (player.getItemInHand().isSimilar(GoreaGuns.pulsegun)) //&& player.hasPermission("gorea.pulsegun")) 
			{
			 List<String> regions = new ArrayList<String>();
			com.sk89q.worldedit.Vector vec = new com.sk89q.worldedit.Vector(loc.getX(),loc.getY(),loc.getZ());
			regions = WGBukkit.getRegionManager(world).getApplicableRegionsIDs(vec);					 
			if(!regions.isEmpty() && regions.contains(GoreaGuns.bazookaarenas))
				{
					long timpacum = new Long(Calendar.getInstance().getTimeInMillis() / 100);
					if(!(pulseguncooldown.containsKey(name)) || (timpacum - pulseguncooldown.get(name)) > GoreaGuns.pulseguncooldown)
						{
						if(GoreaGuns.guns.isConfigurationSection("Guns.Pulsegun.Sound") && GoreaGuns.guns.getConfigurationSection("Guns.Pulsegun.Sound").getKeys(false).contains("Enable"))
							if(GoreaGuns.guns.getBoolean("Guns.Pulsegun.Sound.Enable"))
								{
								
								String sound = GoreaGuns.guns.getString("Guns.Pulsegun.Sound.Sound", "BURP");
								float volume = GoreaGuns.guns.getLong("Guns.Pulsegun.Sound.Volume", 1);	
								float pitch = GoreaGuns.guns.getLong("Guns.Pulsegun.Sound.Pitch", 1);	
								player.getWorld().playSound(player.getLocation(),Sound.valueOf(sound), volume, pitch);
								}
							EnderPearl f = player.launchProjectile(EnderPearl.class);
							Vector b = f.getVelocity().multiply(GoreaGuns.pulsegunvelocity);
							f.setVelocity(b);	
							
							f.setBounce(false);
							e.setCancelled(true);							
							pulseguncooldown.put(name, timpacum);	
							return;
						}
				}
			}
		 if (player.getItemInHand().isSimilar(GoreaGuns.shootgun)) //&& player.hasPermission("gorea.pulsegun")) 
			{
			// player.sendMessage("found shootgun");
			 List<String> regions = new ArrayList<String>();
			com.sk89q.worldedit.Vector vec = new com.sk89q.worldedit.Vector(loc.getX(),loc.getY(),loc.getZ());
			regions = WGBukkit.getRegionManager(world).getApplicableRegionsIDs(vec);					 
			if(!regions.isEmpty() && regions.contains(GoreaGuns.bazookaarenas))
				{
					long timpacum = new Long(Calendar.getInstance().getTimeInMillis() / 100);
					if(!(shootguncooldown.containsKey(name)) || (timpacum - shootguncooldown.get(name)) > GoreaGuns.shootguncooldown)
						{
						if(GoreaGuns.guns.isConfigurationSection("Guns.Shootgun.Sound") && GoreaGuns.guns.getConfigurationSection("Guns.Shootgun.Sound").getKeys(false).contains("Enable"))
							if(GoreaGuns.guns.getBoolean("Guns.Shootgun.Sound.Enable"))
								{
								
								String sound = GoreaGuns.guns.getString("Guns.Shootgun.Sound.Sound", "BURP");
								float volume = GoreaGuns.guns.getLong("Guns.Shootgun.Sound.Volume", 1);	
								float pitch = GoreaGuns.guns.getLong("Guns.Shootgun.Sound.Pitch", 1);	
								player.getWorld().playSound(player.getLocation(),Sound.valueOf(sound), volume, pitch);
								}
						for(int i=0;i<GoreaGuns.shootegunshells;i++){
							SmallFireball f = player.launchProjectile(SmallFireball.class);
							f.setIsIncendiary(false);
							f.setYield(0);
							f.setFireTicks(0);
							
							
							float accuracy = GoreaGuns.shootegunspread;
							Vector v = e.getPlayer().getLocation().getDirection();
							v.add(new Vector(Math.random() * accuracy - accuracy/2,Math.random() * accuracy - accuracy/2,Math.random() * accuracy - accuracy/2)).multiply(GoreaGuns.pulsegunvelocity);;
							f.setVelocity(v);
							//f.setBounce(false);
							//f.setTicksLived(2);
							
							
							//f.setFireTicks(2);
						//	e.setCancelled(true);
							
							shootguncooldown.put(name, timpacum);	
						}
							return;
						}
				}
			}
			}	
		 
		 }	
	}	
						
	public static void createBeacon(Location location) {
	    int x = location.getBlockX();
	    int y = location.getBlockY() - 3;
	    int z = location.getBlockZ();
	 
	    World world = location.getWorld();
	 
	    world.getBlockAt(x, y, z).setType(Material.BEACON);
	    for (int i = 0; i <= 29; ++i) world.getBlockAt(x, (y + 1) + i, z).setType(Material.GLASS);
	    for (int xPoint = x-1; xPoint <= x+1 ; xPoint++) { 
	        for (int zPoint = z-1 ; zPoint <= z+1; zPoint++) {            
	            world.getBlockAt(xPoint, y-1, zPoint).setType(Material.IRON_BLOCK);
	        }
	    } 
	}			
		 
		
	   
	
	
	@EventHandler
	private void onPulseGunTeleport(PlayerTeleportEvent e) 
	{
		if(e.getCause().equals(TeleportCause.ENDER_PEARL)) 
		{
			World world = e.getPlayer().getWorld();			
			if( WGBukkit.getRegionManager(world).hasRegion(GoreaGuns.pulsegunarenas))
				{
				
				if ( e.getPlayer().getItemInHand().isSimilar(GoreaGuns.pulsegun))
					{	
						e.setCancelled(true);							
					}					
				}
		}
	}
		
	@EventHandler
	public void onDamageBy(EntityDamageByEntityEvent e) 
	 {		
		
		
		if ( e.getDamager() instanceof Arrow)
		{
			Arrow s = (Arrow) e.getDamager();
			if(s.getShooter()!=null && s.getShooter() instanceof Player)
			{
				Player shooter = (Player) s.getShooter();
				if( shooter.getItemInHand().isSimilar(GoreaGuns.railgun)) //WEIRD CHECK
				{					
					List<String> regions = new ArrayList<String>();
					com.sk89q.worldedit.Vector vec = new com.sk89q.worldedit.Vector(s.getLocation().getX(),s.getLocation().getY(),s.getLocation().getZ());
					regions = WGBukkit.getRegionManager(e.getEntity().getWorld()).getApplicableRegionsIDs(vec);
					//if( WGBukkit.getRegionManager(world).hasRegion(GoreaGuns.railgunarenas))
					if(!regions.isEmpty() && regions.contains(GoreaGuns.railgunarenas))//WGBukkit.getRegionManager(world).getRegion(GoreaGuns.railgunarenas).contains(s.getLocation().getBlockX(),s.getLocation().getBlockY(),s.getLocation().getBlockZ()))
					{
						
						
						//s.setBounce(false);
						if(e.getEntityType().equals("PLAYER"))
						{
							shooter.sendMessage("PLAYER");
							double boost = 1;
						    if(GoreaGuns.damageboostlisttasks.values().contains(shooter.getName()))
								boost = GoreaGuns.damageboostlist.get(shooter.getName());
							
							Player target = (Player) e.getEntity();
							if(GoreaGuns.reflects.values().contains(target.getName()))
								{
									if(!GoreaGuns.invulns.values().contains(shooter.getName()))
									{
									shooter.sendMessage(ChatColor.RED + "Damage got reflected back.");
									//new event to damage shooter?
									shooter.damage(checkShield(shooter,(int)(GoreaGuns.railgundamage * boost) ),target);
									} else shooter.sendMessage(ChatColor.YELLOW + "Damage got reflected back but stopped: " + GoreaGuns.pulsegundamage* boost);
									
									
								} else {
									if(!GoreaGuns.invulns.values().contains(target.getName()))
									{
										e.setDamage(checkShield(target,(int)(GoreaGuns.railgundamage * boost)));
									//target.damage(checkShield(target,(int)(GoreaGuns.railgundamage * boost)) );
									
									}
								}
									
						} else {
							e.setDamage((int)(GoreaGuns.railgundamage * boost) );
						}
					//s.getWorld().playEffect(s.getLocation(), Effect.MOBSPAWNER_FLAMES, 5, 16);
					//e.setCancelled(true);
					s.remove();
					
					}					
				}
			}
			
		}

		if ( e.getDamager() instanceof Snowball)
		{
			Snowball s = (Snowball) e.getDamager();
			if( s.getShooter() instanceof Player)
			{
				Player shooter = (Player) s.getShooter();
				if( shooter.getItemInHand().isSimilar(GoreaGuns.bazooka))
				{
					List<String> regions = new ArrayList<String>();
					com.sk89q.worldedit.Vector vec = new com.sk89q.worldedit.Vector(e.getEntity().getLocation().getX(),e.getEntity().getLocation().getY(),e.getEntity().getLocation().getZ());
					regions = WGBukkit.getRegionManager(e.getEntity().getWorld()).getApplicableRegionsIDs(vec);
					if(!regions.isEmpty() && regions.contains(GoreaGuns.bazookaarenas))
					{		
						if(e.getEntity() instanceof Player)
						{
							double boost = 1;
						    if(GoreaGuns.damageboostlisttasks.values().contains(shooter.getName()))
								boost = GoreaGuns.damageboostlist.get(shooter.getName());
							
							Player target = (Player) e.getEntity();
							
							if(GoreaGuns.reflects.values().contains(target.getName()))
								{
								if(!GoreaGuns.invulns.values().contains(shooter.getName()))
									{
									
									shooter.sendMessage(ChatColor.RED + "Damage got reflected back: "  + GoreaGuns.pulsegundamage * boost);
									shooter.damage(checkShield(shooter,(int)(GoreaGuns.bazookadamage * boost)),target);
									}else shooter.sendMessage(ChatColor.YELLOW + "Damage got reflected back but stopped: " + GoreaGuns.pulsegundamage* boost);
								} else {
									if(!GoreaGuns.invulns.values().contains(target.getName()))
									{
										//target.sendMessage("you been hit by " + shooter.getName());
										//shooter.sendMessage("you hit " + target.getName());
									e.setDamage(checkShield(target,(int)(GoreaGuns.bazookadamage * boost)));
									}
								}
						} else {
							e.setDamage((int)(GoreaGuns.bazookadamage * boost));
								}
					}
				}
			}
		}
		//Methods.findPlayerByString("gorea01").sendMessage("getDamage " + e.getDamage()); //ammount of damage
		//Methods.findPlayerByString("gorea01").sendMessage("getDamager " + e.getDamager()); //projectile type type CraftEnderPearl
		//Methods.findPlayerByString("gorea01").sendMessage("getEventName " + e.getEventName());
	//	Methods.findPlayerByString("gorea01").sendMessage("getEntityType " + e.getEntityType()); //target entity type
		//Methods.findPlayerByString("gorea01").sendMessage("getEntity " + e.getEntity()); //target CraftPig CraftPlayer
	//	Methods.findPlayerByString("gorea01").sendMessage("getCause.name " + e.getCause().);
		if(e.getDamager() instanceof EnderPearl )
		{
			
			EnderPearl pearl = (EnderPearl) e.getDamager();
			if(pearl.getShooter() instanceof Player)
			{
					if(e.getEntity() instanceof Player)
					{
						
						Player target = (Player) e.getEntity(); 						
						Player shooter = (Player) pearl.getShooter();
						
						/*if(GoreaGuns.guns.isConfigurationSection("Guns.Pulsegun.Sound") && GoreaGuns.guns.getConfigurationSection("Guns.Pulsegun.Sound").getKeys(false).contains("Enable"))
							if(GoreaGuns.guns.getBoolean("Guns.Pulsegun.Sound.Enable"))
								{
								
								String sound = GoreaGuns.guns.getString("Guns.Pulsegun.Sound.Sound", "BURP");
								float volume = GoreaGuns.guns.getLong("Guns.Pulsegun.Sound.Volume", 1);	
								float pitch = GoreaGuns.guns.getLong("Guns.Pulsegun.Sound.Pitch", 1);	
								pearl.getWorld().playSound(e.getEntity().getLocation(),Sound.valueOf(sound), volume, pitch);
								}*/
						if(GoreaGuns.pulsegunteleport)	target.teleport(shooter.getLocation());
						if(GoreaGuns.reflects.values().contains(target.getName()))
							{
							double boost = 1;
						    if(GoreaGuns.damageboostlisttasks.values().contains(shooter.getName()))						    	
								boost = GoreaGuns.damageboostlist.get(shooter.getName());
						    
								if(!GoreaGuns.invulns.values().contains(shooter.getName()))
								{
								shooter.sendMessage(ChatColor.RED + "Damage got reflected back: " + GoreaGuns.pulsegundamage * boost);
								shooter.damage(checkShield(shooter,(int)(GoreaGuns.pulsegundamage * boost)),target);
								} else shooter.sendMessage(ChatColor.YELLOW + "Damage got reflected back but stopped: " + GoreaGuns.pulsegundamage * boost);
							return;
							} else 
								{
									if(!GoreaGuns.invulns.values().contains(shooter.getName()))
									{			
										
										e.setDamage(checkShield(target,(int)(GoreaGuns.pulsegundamage * boost)));
										return;
									}
								}
					} else {
						e.setDamage((int)(GoreaGuns.pulsegundamage * boost));
					}
			}
		}
	}
	
	@EventHandler
    public void arrowhit(ProjectileHitEvent event){
		//=========================Railgun
		if((event.getEntity() instanceof Arrow))
		{
			Projectile projectile = event.getEntity();
			Arrow arrow = (Arrow) projectile;
		    
		    if((arrow.getShooter() instanceof Player)) //Making sure the shooter is a player
		    {
		    	Player shooter = (Player) arrow.getShooter();

					if ( shooter.getItemInHand().isSimilar(GoreaGuns.railgun))			
							{
						projectile.getWorld().playEffect(projectile.getLocation(), Effect.SMOKE, 5, 32);
						
						event.getEntity().remove();
		
							}
					}
		    }
		
		
		if((event.getEntity() instanceof SmallFireball))
		{
			Projectile projectile = event.getEntity();
			SmallFireball smallFireball = (SmallFireball) projectile;
		    
		    if((smallFireball.getShooter() instanceof Player)) //Making sure the shooter is a player
		    {
		    	Player shooter = (Player) smallFireball.getShooter();

					///if ( shooter.getItemInHand().isSimilar(GoreaGuns.shootgun))			
							{
						projectile.getWorld().playEffect(projectile.getLocation(), Effect.SMOKE, 5, 32);
						
						
						event.getEntity().getLocation().getBlock().
						Methods.findPlayerByString("goreacraft").sendMessage("fireball "+ event.getEntity().getFireTicks());
							}
					}
		    }
		
		//=========================Bazooka
	if((event.getEntity() instanceof Snowball))
		{
	if (GoreaGuns.bazookasplash)
		{
			Projectile projectile = event.getEntity();
			Snowball snowbal = (Snowball) projectile;
		    
		    if((snowbal.getShooter() instanceof Player)) //Making sure the shooter is a player
		    {
		    	Player shooter = (Player) snowbal.getShooter();

				if ( shooter.getItemInHand().isSimilar(GoreaGuns.bazooka))
					{
						double boost = 1;
						if(GoreaGuns.damageboostlisttasks.values().contains(shooter.getName()))
							boost = GoreaGuns.damageboostlist.get(shooter.getName());

						List<String> regions = new ArrayList<String>();
						com.sk89q.worldedit.Vector vec = new com.sk89q.worldedit.Vector(event.getEntity().getLocation().getX(),event.getEntity().getLocation().getY(),event.getEntity().getLocation().getZ());
						regions = WGBukkit.getRegionManager(event.getEntity().getWorld()).getApplicableRegionsIDs(vec);		
					if(!regions.isEmpty() && regions.contains(GoreaGuns.bazookaarenas))
						{
								Location targetloc = snowbal.getLocation().add(snowbal.getVelocity());
								projectile.getWorld().playEffect(targetloc, Effect.MOBSPAWNER_FLAMES, 5, 32);
								projectile.getWorld().playEffect(targetloc, Effect.MOBSPAWNER_FLAMES, 5, 32);
								projectile.getWorld().playEffect(targetloc, Effect.MOBSPAWNER_FLAMES, 5, 32);
								if(GoreaGuns.guns.isConfigurationSection("Guns.Bazooka.Sound") && GoreaGuns.guns.getConfigurationSection("Guns.Bazooka.Sound").getKeys(false).contains("Enable"))
								if(GoreaGuns.guns.getBoolean("Guns.Bazooka.Sound.Enable"))
									{
									
									String sound = GoreaGuns.guns.getString("Guns.Bazooka.Sound.Sound", "BURP");
									float volume = GoreaGuns.guns.getLong("Guns.Bazooka.Sound.Volume", 1);	
									float pitch = GoreaGuns.guns.getLong("Guns.Bazooka.Sound.Pitch", 1);	
									projectile.getWorld().playSound(event.getEntity().getLocation(),Sound.valueOf(sound), volume, pitch);
									} 
										
								
								
					        	for ( Entity eee :projectile.getNearbyEntities(GoreaGuns.bazookasplashradious, GoreaGuns.bazookasplashradious, GoreaGuns.bazookasplashradious))
					        	{
					        		if ( eee instanceof LivingEntity)
					        		{
					        			LivingEntity enty = (LivingEntity) eee;
					        			projectile.getWorld().playEffect(targetloc, Effect.POTION_BREAK,3);
					        			
					        			if(enty instanceof Player)
					        			{
					        				Player targetsplash = (Player) enty; 
					        				//targetsplash()
					        				
					        				targetsplash.damage(checkShield(targetsplash,(int)(GoreaGuns.bazookasplashdamage * boost)),shooter);
					        				
					        			}else {
					        			
					        			enty.damage((int) (GoreaGuns.bazookasplashdamage *boost), enty);
					        			}
					        
					        		}
					        	}
					        }
					    }
					}
				}
		    }
		
		/*			      //=========================Pulsegun  
		 if((event.getEntity() instanceof EnderPearl))
			{
			
				Projectile projectile = event.getEntity();
				EnderPearl arrow = (EnderPearl) projectile;
				List<Entity> targets = arrow.getNearbyEntities(2, 2, 2);			    
			    Player shooter = (Player) arrow.getShooter();
			    
			   
			    
						if ( shooter.getItemInHand().isSimilar(GoreaGuns.pulsegun))			
						{
							
							projectile.getWorld().playEffect(projectile.getLocation(), Effect.SMOKE, 5, 32);
							
							for ( Entity ent : targets)
							{
								if ( ent instanceof LivingEntity)
								{
									LivingEntity targetent = (LivingEntity) ent;
									if (!targetent.equals(shooter))
									{
										if(GoreaGuns.pulsegunteleport)	targetent.teleport(arrow.getShooter().getLocation());
										if(targetent instanceof Player)
										{
											Player target = (Player) targetent; 
											
											if(GoreaGuns.reflects.values().contains(target.getName()))
												{
												double boost = 1;
											    if(GoreaGuns.damageboostlisttasks.values().contains(shooter.getName()))
											    	
													boost = GoreaGuns.damageboostlist.get(shooter.getName());
													if(!GoreaGuns.invulns.values().contains(shooter.getName()))
													{
													shooter.sendMessage(ChatColor.RED + "Damage got reflected back: " + GoreaGuns.pulsegundamage * boost);
													shooter.damage(checkShield(shooter,(int)(GoreaGuns.pulsegundamage * boost)));
													} else shooter.sendMessage(ChatColor.YELLOW + "Damage got reflected back but stopped: " + GoreaGuns.pulsegundamage * boost);
												return;
												} else 
													{
														if(!GoreaGuns.invulns.values().contains(shooter.getName()))
														{														
															target.damage(checkShield(target,(int)(GoreaGuns.pulsegundamage * boost)));
															return;
														}
													}
										} else {
											targetent.damage((int)(GoreaGuns.pulsegundamage * boost));
										}

									}

								}
							}
					}
		}*/

		
    }
	 
	
	static int checkShield(Player player, int damage)
	{
			String name =  player.getName();
	if(!GoreaGuns.armours.isEmpty())
		if(GoreaGuns.armours.containsKey(name))
			{
				int dam = GoreaGuns.armours.get(name) - damage;
				if(dam <= 0)
				{
					player.sendMessage(ChatColor.RED + "Shield depleted.");
					GoreaGuns.armours.remove(name);
					return Math.abs(dam);
				} 
				if (dam>0){
					if(GoreaGuns.damageprag < damage)
					{
						player.sendMessage(ChatColor.AQUA + "Shield left: " + dam);
					}
					GoreaGuns.armours.put(name, dam);		
					return 0;
				}
					
					
				}  
		
		return damage;
	}

}
