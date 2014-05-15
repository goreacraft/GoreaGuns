package com.goreacraft.plugins.goreaguns;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;


public class GoreaGuns extends JavaPlugin{

	public static GoreaGuns gg;
	public static Plugin worldedit;
	public static List<String> aliases;
	
	public final Logger logger = Logger.getLogger("minecraft");
	
	public static ItemStack shootgun;
	public static double shootguncooldown;
	public static double shootgunvelocity;
	public static String shootgunarenas;
	public static int shootgundamage;
	public static int shootegunshells;
	public static float shootegunspread;
	
	public static int maxshield;
	
	public static ItemStack railgun;
	public static double railgunvelocity;
	public static String railgunarenas;
	public static int railgundamage;
	public static double railguncooldown;
	
	public static ItemStack bazooka;
	public static double bazookavelocity;
	public static String bazookaarenas;
	public static int bazookadamage;
	public static  boolean bazookasplash;
	public static int bazookasplashdamage;
	public static int bazookasplashradious;
	public static double bazookacooldown;
	
	public static ItemStack pulsegun;
	public static double pulsegunvelocity;
	public static String pulsegunarenas;
	public static int pulsegundamage;
	public static double pulseguncooldown;
	public static boolean pulsegunteleport;
	//private Methods methods;
	
	//Set<String> gunsnames;
	public static int damageprag;
	
	//private Object taskid;
	//static File spawnPointsFile;
	//static YamlConfiguration spawnPoints = new YamlConfiguration();
	
	static File powerUpFile;
	static YamlConfiguration powerUps = new YamlConfiguration();
	
	static File gunsFile;
	static YamlConfiguration guns = new YamlConfiguration();
	
	//static HashMap<String, SpawnPoint> tasks = new HashMap<String, SpawnPoint>();
	//static HashMap<String, Integer> tasksids = new HashMap<String, Integer>();
	
	static HashMap<String, Integer> armours = new HashMap<String, Integer>();
	static boolean emax;
	static HashMap<Integer,String> reflects = new HashMap<Integer,String>();
	static HashMap<Integer,String> invulns = new HashMap<Integer,String>();
	static HashMap<Integer,String> shieldmessage = new HashMap<Integer,String>();
	
	static HashMap<Integer,String> damageboostlisttasks = new HashMap<Integer,String>();
	static HashMap<String, Double> damageboostlist = new HashMap<String, Double>();
	
	//static HashMap<String, List<String>> specialeffectslist = new HashMap<String, List<String>>();
	public static List<String> efects; 
	//public static FileConfiguration data;
	
	
	public void onEnable()
    {
		PluginDescriptionFile pdfFile = this.getDescription();
    	this.logger.info(pdfFile.getName() + " Version " + pdfFile.getVersion() + " has been enabled! " + pdfFile.getWebsite());
		getConfig().options().copyDefaults(true);
      	getConfig().options().header("If you need help with this plugin you can contact goreacraft on teamspeak ip: goreacraft.com\n Website http://www.goreacraft.com");
      	saveConfig();
      	
      	worldedit= getServer().getPluginManager().getPlugin("WorldEdit");
		gg = this;
		
		
		//spawnPointsFile = new File(getDataFolder(), "SpawnPoints.yml");
		powerUpFile = new File(getDataFolder(), "PowerUp.yml");
		gunsFile = new File(getDataFolder(), "Guns.yml");
		aliases = getCommand("goreaguns").getAliases();
			loadconfigs();
			efects = new ArrayList<String>();
			efects.add("SPEED"); 
			efects.add("SLOW"); 
			efects.add("INCREASE_DAMAGE"); 
			efects.add("HEAL"); 
			efects.add("HARM"); 
			efects.add("JUMP"); 
			efects.add("CONFUSION"); 
			efects.add("REGENERATION"); 
			efects.add("DAMAGE_RESISTANCE"); 
			efects.add("FIRE_RESISTANCE"); 
			efects.add("WATER_BREATHING"); 
			efects.add("INVISIBILITY");
			efects.add("BLINDNESS");
			efects.add("NIGHT_VISION");
			efects.add("HUNGER");
			efects.add("WEAKNESS");
			efects.add("POISON");
			efects.add("WITHER");
			efects.add("FAST_DIGGING");
			efects.add("SLOW_DIGGING");

		//data = getConfig();
			//gunsnames = gg.getConfig().getKeys(false);
		
		Bukkit.getServer().getPluginManager().registerEvents(new Railgun(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PickupEvent(), this);
		
		//====================================== METRICS STUFF =====================================================
	   	
		try {
	   		    Metrics metrics = new Metrics(this);
	   		    metrics.start();
	   		} catch (IOException e) {
	   		    // Failed to submit the stats :-(
	   		}
		/*if(getConfig().getBoolean("CheckUpdates")) 
		{
			new Updater(77997);
		}*/
    }
	
	
	void loadconfigs(){
		
		
		
	    
	   /* if(!spawnPointsFile.exists())
	    {
	    	try {spawnPointsFile.createNewFile();
            } catch (IOException e) {e.printStackTrace();}	    	
	    }*/
	    if(!powerUpFile.exists())
	    {
	    	try {powerUpFile.createNewFile();
            } catch (IOException e) {e.printStackTrace();}	    	
	    }
	    if(!gunsFile.exists())
	    {
	    	try {gunsFile.createNewFile();
            } catch (IOException e) {e.printStackTrace();}	    	
	    }
	   // powerUpsFile.
	    powerUps = YamlConfiguration.loadConfiguration(powerUpFile);
	   // spawnPoints = YamlConfiguration.loadConfiguration(spawnPointsFile);
	    guns = YamlConfiguration.loadConfiguration(gunsFile);
	    
	    maxshield = getConfig().getInt("Max shield");
	    emax = getConfig().getBoolean("Enable max shield");
	    
	    bazooka =  new ItemStack(Material.getMaterial(guns.getString("Guns.Bazooka.Material")));
		bazookavelocity =guns.getDouble("Guns.Bazooka.Velocity", 1);
		bazookaarenas =  guns.getString("Guns.Bazooka.Regions");
		bazookadamage = guns.getInt("Guns.Bazooka.Damage", 1);
		bazookasplash = guns.getBoolean("Guns.Bazooka.Splash.Enable", true);
		bazookasplashdamage = guns.getInt("Guns.Bazooka.Splash.Damage", 2);
		bazookasplashradious = guns.getInt("Guns.Bazooka.Splash.Radious", 3);
		bazookacooldown = guns.getDouble("Guns.Bazooka.Cooldown");
		
		railgun =  new ItemStack(Material.getMaterial(guns.getString("Guns.Railgun.Material")));
		railgunvelocity = guns.getDouble("Guns.Railgun.Velocity", 1);
		railgunarenas =  guns.getString("Guns.Railgun.Regions");						
		railgundamage = guns.getInt("Guns.Railgun.Damage", 1);
		railguncooldown = guns.getDouble("Guns.Railgun.Cooldown");
		
		pulsegun =  new ItemStack(Material.getMaterial(guns.getString("Guns.Pulsegun.Material")));
		pulsegunvelocity = guns.getDouble("Guns.Pulsegun.Velocity", 1);
		pulsegunarenas =  guns.getString("Guns.Pulsegun.Regions");						
		pulsegundamage = guns.getInt("Guns.Pulsegun.Damage", 1);
		pulseguncooldown = guns.getDouble("Guns.Pulsegun.Cooldown");
		pulsegunteleport = guns.getBoolean("Guns.Pulsegun.Teleport", true);
		
		shootgun= new ItemStack(Material.getMaterial(guns.getString("Guns.Shootgun.Material")));
		shootguncooldown= guns.getDouble("Guns.Shootgun.Cooldown");
		shootgunvelocity = guns.getDouble("Guns.Shootgun.Velocity", 1);
		shootgunarenas =  guns.getString("Guns.Shootgun.Regions");						
		shootgundamage = guns.getInt("Guns.Shootgun.Damage", 1);
		shootguncooldown = guns.getDouble("Guns.Shootgun.Cooldown");
		shootegunshells = guns.getInt("Guns.Shootgun.Shells", 3);
		shootegunspread = (float) guns.getDouble("Guns.Shootgun.Spread", 0.3);
		
		damageprag = getConfig().getInt("Min damage to show message");
	   // Methods.setpoints();
	    
	}
private void clean(){
	if(!armours.isEmpty())
		armours.clear();
	//tasks.clear();
	//tasksids.clear();
	//Methods.ents.clear();
	
}



	public void onDisable()
    {
		PluginDescriptionFile pdfFile = this.getDescription();
    	this.logger.info(pdfFile.getName() + " Version " + pdfFile.getVersion() + " has been disabled!" + pdfFile.getWebsite());
    	Bukkit.getScheduler().cancelTasks(gg);

    	
    	
    	
    }
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
		if(label.equalsIgnoreCase("ggdamage"))
		{
			if(args.length>0)
			{
				//check if player online
				if(Methods.findPlayerByString(args[0]) != null)
					{
					//check if world exists
					if(args.length>1 && Methods.isInteger(args[1]))
						{
						int chance = 100;
						if(args.length>2 && Methods.isInteger(args[2]))
						{					
							chance= Integer.parseInt(args[2]);							
						}
						if(sender instanceof Player)
						{
							if(!sender.isOp()) {
								sender.sendMessage(ChatColor.RED + "You dont have sufficient permissions");
								return true;
							}
						}
						damageplayer(Methods.findPlayerByString(args[0]),Integer.parseInt(args[1]), chance );
						//do here the damage
						
						} else {
							//not integer
							return false;
						}
					} else { 
						//not player
						if(sender instanceof Player)
						{
							sender.sendMessage(ChatColor.RED + "No online player with this name");
						} else {
							System.out.println("No online player with this name");
						}					
						
					}
				//return false;
			} else {
				return false;
			}
		return true;
		}
		
		if(label.equalsIgnoreCase("ggtp"))
		{
			if(args.length>0)
			{
				//check if player online
				if(Methods.findPlayerByString(args[0]) != null)
					{
					//check if world exists
					if(args.length>1 && Bukkit.getServer().getWorld(args[1])!=null)
						{
							if(args.length>=5 && Methods.isDouble(args[2]) && Methods.isDouble(args[3]) && Methods.isDouble(args[4]))
							{
								if(sender instanceof Player)
										{
										if (sender.isOp())
											{
											//teleports player
											teleportplayer(args);
											} else {
												//player not op
												sender.sendMessage(ChatColor.RED + "You dont have permissions to do this");
											}
										} else{
											//sender boot
											teleportplayer(args);
										}										
							} else {
								
								//coords incorect
								return false;
							}
						} else {
							// world incorrect or not exists
							return false;
						}
							
					} else{
							//player not online or null
						return false;
						}
			}else{
				//need more args
			return false;
			}
		return true;	
		}

        if (sender instanceof Player)
        {        
        	Player player = ((Player) sender).getPlayer();
        	if(!player.isOp())
        	{
        		player.sendMessage(ChatColor.RED +"You dont have permissions to use this commands");
        		return true;
        	}
        	if (aliases.contains(label))
            {
        		if (args.length == 0)
            	{
        			showhelpplayer(player);
            	}
            	

        		if (args.length == 1)
            	{
        			if (args[0].equals("debug"))
					{
        				player.sendMessage("Listing reflects:");
        				player.sendMessage("Reflects tasks: " +GoreaGuns.reflects.values());
        				player.sendMessage("DamageBoosts tasks: " +GoreaGuns.damageboostlisttasks.values());
        				player.sendMessage("Invulnerabilitys tasks: " +GoreaGuns.invulns.values());
					}
        			if (args[0].equals("reload"))
        					{
        					Bukkit.getScheduler().cancelTasks(gg);
        					clean();
        					armours.clear();
        					reloadConfig();
        					loadconfigs();
        					sender.sendMessage(ChatColor.GREEN + "Reloaded the configs");
        					return true;
        					}
        			if (args[0].equals("help") || args[0].equals("?"))
					{
				
        				showhelpplayer(player);
					return true;
					}
            	}
            }  
        }
        
        if (!(sender instanceof Player))
        {
	        if (aliases.contains(label))
	        {
				if (args.length == 1)
		    	{
					if (args[0].equals("reload"))
						{
						Bukkit.getScheduler().cancelTasks(gg);
    					//tasks.clear();
    				//	tasksids.clear();
    					//Methods.ents.clear();
    					gg.reloadConfig();
    					loadconfigs();    					   					
						System.out.println("[GoreaGuns] Reloaded the configs");
						return true;
						}
					if (args[0].equals("help") || args[0].equals("?"))
					{
						System.out.println("[GoreaGuns] Will be a nice help page here soon.");
						//showhelpplayer(player);
					return true;
					}
					
					/*if (args[0].equals("start"))
					{
						//Bukkit.getScheduler().cancelTasks(gg);
						//Methods.startSpawnPoints();
						return true;
					}*/
					if (args[0].equals("stop"))
					{
						
						Bukkit.getScheduler().cancelTasks(gg);
						System.out.println("Forcefully cancel all plugin tasks");
						return true;						
					}		
	    	}
		
        }
        }
		return false;
    
        
        
	
}


	private void damageplayer(Player player, int damage, int chance) 
	{
		// TODO Auto-generated method stub
		
		
			if(!GoreaGuns.invulns.values().contains(player.getName()))
			{
			int randomNum = (int)(Math.random()*100); 
			if(randomNum<=chance)
			{
				player.sendMessage(ChatColor.RED + "You got damaged: " + damage);			
				player.damage(Railgun.checkShield(player,(int)(damage) ));
			} else player.sendMessage(ChatColor.GREEN + "You got lucky and deflected " + ChatColor.RED + damage + ChatColor.GREEN + " damage.");
			
			} else player.sendMessage(ChatColor.YELLOW + "Damage got stopped: " + damage);
			
			
		 
		
	}


	private void showhelpplayer(Player player){
		
		player.sendMessage( ChatColor.YELLOW + "......................................................." + ChatColor.GOLD + " Plugin made by: "+ ChatColor.YELLOW + ".......................................................");
    	player.sendMessage( ChatColor.YELLOW + "     o   \\ o /  _ o              \\ /               o_   \\ o /   o");
    	player.sendMessage( ChatColor.YELLOW + "    /|\\     |      /\\   __o        |        o__    /\\      |     /|\\");
    	player.sendMessage( ChatColor.YELLOW + "    / \\   / \\    | \\  /) |       /o\\       |  (\\   / |    / \\   / \\");
    	player.sendMessage( ChatColor.YELLOW + "......................................................." + ChatColor.GOLD + ChatColor.BOLD + " GoreaCraft  "+ ChatColor.YELLOW + ".......................................................");
    	
    	player.sendMessage("");
    	player.sendMessage( ChatColor.YELLOW + "Aliases: " + ChatColor.LIGHT_PURPLE +  aliases );
    	player.sendMessage( ChatColor.YELLOW + "[] " + ChatColor.RESET + " Required field, " + ChatColor.YELLOW + "<> " + ChatColor.RESET + " Optional field"); 
    	player.sendMessage( ChatColor.YELLOW + "/gg :" + ChatColor.RESET + " Nothing for now");    	
    	player.sendMessage( ChatColor.YELLOW + "/gg help/? :" + ChatColor.RESET + " Shows this! Do it again, i know you want to... ");
    	player.sendMessage( ChatColor.YELLOW + "/gg reload :" + ChatColor.RESET + " Reloads the configs.");
    	player.sendMessage( ChatColor.YELLOW + "/gg stop :" + ChatColor.RESET + " Stops all plugin tasks");
    	player.sendMessage( ChatColor.YELLOW + "/ggtp [player_name] [world_name] [x] [y] [z] :" + ChatColor.RESET + " Teleports player to coords");
    	player.sendMessage( ChatColor.YELLOW + "/ggdamage [player_name] [ammount] <chance>:" + ChatColor.RESET + " Chance to damage the player, chance is value from 0 to 100"+ChatColor.ITALIC+ " (if no value defined, will default to 100)");
    	player.sendMessage( ChatColor.GREEN +"" + ChatColor.ITALIC + "Use the GoreaItemspawn plugin to spawn powerups, command should be '/gi'");
    	player.sendMessage( ChatColor.GREEN +"" + ChatColor.ITALIC + "http://dev.bukkit.org/bukkit-plugins/gorea-item-spawn/ ");
	}
	
	private void teleportplayer(String[] args)
	{
		Player player = Methods.findPlayerByString(args[0]);
		World world = Bukkit.getServer().getWorld(args[1]);
		Location location = new Location(world, Double.parseDouble(args[2]),Double.parseDouble(args[3]),Double.parseDouble(args[4]));
		player.teleport(location);
	}
	
	
	
	/*private String[] split(String string){
		String[] aaa = string.split(" ");
		
		return aaa;
	}
*/
}
