package src;

import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.Element;
import org.bukkit.ChatColor;
import org.bukkit.util.Vector;
import com.projectkorra.projectkorra.util.ParticleEffect;
import com.projectkorra.projectkorra.util.DamageHandler;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Entity;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.Ability;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.ability.CoreAbility;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import org.bukkit.Location;
import java.util.UUID;
import java.util.List;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.FireAbility;

public class FireDrive extends FireAbility implements AddonAbility
{
    public static List<UUID> playerBlue;
    private long cooldown;
    private long duration;
    private long time;
    private double damage;
    private double speed;
    private double blueDriveSpeed;
    private static String bindMsgBlue;
    private static String unbindMsgBlue;
    private static boolean blueFireDriveEnabled;
    private Location location;
    
    static {
        FireDrive.playerBlue = new ArrayList<UUID>();
    }
    
    public FireDrive(final Player player) {
        super(player);
        if (!this.bPlayer.canBend((CoreAbility)this) || !this.bPlayer.canBendIgnoreBinds((CoreAbility)this)) {
            return;
        }
        this.setFields();
        this.start();
    }
    
    private void setFields() {
        this.cooldown = ConfigManager.getConfig().getLong("ExtraAbilities.Prride.FireDrive.Cooldown");
        this.duration = ConfigManager.getConfig().getLong("ExtraAbilities.Prride.FireDrive.Duration");
        this.damage = ConfigManager.getConfig().getDouble("ExtraAbilities.Prride.FireDrive.Damage");
        this.speed = ConfigManager.getConfig().getDouble("ExtraAbilities.Prride.FireDrive.Speed");
        FireDrive.blueFireDriveEnabled = ConfigManager.getConfig().getBoolean("ExtraAbilities.Prride.FireDrive.BlueFireDrive.Enabled");
        this.blueDriveSpeed = ConfigManager.getConfig().getDouble("ExtraAbilities.Prride.FireDrive.BlueFireDrive.Speed");
        FireDrive.bindMsgBlue = "Your FireDrive is now blue.";
        FireDrive.unbindMsgBlue = "Your FireDrive is now back to normal.";
        this.location = this.player.getLocation().clone().subtract(0.0, 1.0, 0.0);
        this.time = System.currentTimeMillis();
    }
    
    public long getCooldown() {
        return this.cooldown;
    }
    
    public Location getLocation() {
        return this.location;
    }
    
    public String getName() {
        return "FireDrive";
    }
    
    public boolean isHarmlessAbility() {
        return false;
    }
    
    public boolean isSneakAbility() {
        return true;
    }
    
    @SuppressWarnings("deprecation")
	public void progress() {
        if (this.player.isDead() || !this.player.isOnline()) {
            this.remove();
            return;
        }
        if (System.currentTimeMillis() > this.time + this.duration) {
            this.bPlayer.addCooldown((Ability)this);
            this.remove();
            return;
        }
        if (!this.bPlayer.canBendIgnoreBindsCooldowns((CoreAbility)this)) {
            this.remove();
            return;
        }
        for (final Entity entity : GeneralMethods.getEntitiesAroundPoint(this.location, 2.0)) {
            if (!GeneralMethods.isRegionProtectedFromBuild(this.player, "FireDrive", entity.getLocation()) && entity instanceof LivingEntity && entity.getEntityId() != this.player.getEntityId() && !(entity instanceof ArmorStand)) {
                entity.setVelocity(entity.getLocation().toVector().subtract(this.player.getLocation().toVector()).multiply(1));
                DamageHandler.damageEntity(entity, this.damage, (Ability)this);
                this.remove();
            }
        }
        if (FireDrive.playerBlue.contains(this.player.getUniqueId())) {
            final Vector velocity = this.player.getEyeLocation().getDirection().clone().normalize().multiply(-this.blueDriveSpeed);
            this.player.setVelocity(velocity);
            this.player.setFallDistance(0.0f);
        }
        else {
            final Vector velocity = this.player.getEyeLocation().getDirection().clone().normalize().multiply(-this.speed);
            this.player.setVelocity(velocity);
            this.player.setFallDistance(0.0f);
        }
        if (FireDrive.playerBlue.contains(this.player.getUniqueId())) {
            GeneralMethods.displayColoredParticle(GeneralMethods.getLeftSide(this.player.getLocation(), 0.55).add(0.0, 1.2, 0.0), "079afc", 0.1f, 0.1f, 0.1f);
            GeneralMethods.displayColoredParticle(GeneralMethods.getRightSide(this.player.getLocation(), 0.55).add(0.0, 1.2, 0.0), "079afc", 0.1f, 0.1f, 0.1f);
            GeneralMethods.displayColoredParticle(GeneralMethods.getLeftSide(this.player.getLocation(), 0.55).add(0.0, 1.2, 0.0), "5f42f4", 0.1f, 0.1f, 0.1f);
            GeneralMethods.displayColoredParticle(GeneralMethods.getRightSide(this.player.getLocation(), 0.55).add(0.0, 1.2, 0.0), "5f42f4", 0.1f, 0.1f, 0.1f);
            GeneralMethods.displayColoredParticle(GeneralMethods.getLeftSide(this.player.getLocation(), 0.55).add(0.0, 1.2, 0.0), "ffff7a", 0.1f, 0.1f, 0.1f);
            GeneralMethods.displayColoredParticle(GeneralMethods.getRightSide(this.player.getLocation(), 0.55).add(0.0, 1.2, 0.0), "ffff7a", 0.1f, 0.1f, 0.1f);
            GeneralMethods.displayColoredParticle(GeneralMethods.getLeftSide(this.player.getLocation(), 0.55).add(0.0, 1.2, 0.0), "fbffb7", 0.1f, 0.1f, 0.1f);
            GeneralMethods.displayColoredParticle(GeneralMethods.getRightSide(this.player.getLocation(), 0.55).add(0.0, 1.2, 0.0), "fbffb7", 0.1f, 0.1f, 0.1f);
            ParticleEffect.CRIT_MAGIC.display(GeneralMethods.getLeftSide(this.player.getLocation(), 0.55).add(0.0, 1.2, 0.0), 10, 0.1f, 0.1f, 0.1f, 0.0f);
            ParticleEffect.CRIT_MAGIC.display(GeneralMethods.getRightSide(this.player.getLocation(), 0.55).add(0.0, 1.2, 0.0), 10, 0.1f, 0.1f, 0.1f, 0.0f);
        }
        else {
            ParticleEffect.FLAME.display(GeneralMethods.getLeftSide(this.player.getLocation(), 0.55).add(0.0, 1.2, 0.0), 10, 0.1f, 0.1f, 0.1f, 0.0f);
            ParticleEffect.FLAME.display(GeneralMethods.getRightSide(this.player.getLocation(), 0.55).add(0.0, 1.2, 0.0), 10, 0.1f, 0.1f, 0.1f, 0.0f);
            ParticleEffect.SMOKE_LARGE.display(GeneralMethods.getLeftSide(this.player.getLocation(), 0.55).add(0.0, 1.2, 0.0), 1, 0.1f, 0.1f, 0.1f, 0.0f);
            ParticleEffect.SMOKE_LARGE.display(GeneralMethods.getRightSide(this.player.getLocation(), 0.55).add(0.0, 1.2, 0.0), 1, 0.1f, 0.1f, 0.1f, 0.0f);
        }
    }
    
    public static void toggleBlueFire(final Player player, final boolean activate) {
        if (FireDrive.blueFireDriveEnabled && player.hasPermission("bending.ability.FireDrive")) {
            if (activate) {
                if (!FireDrive.playerBlue.contains(player.getUniqueId())) {
                    FireDrive.playerBlue.add(player.getUniqueId());
                    player.sendMessage(ChatColor.BLUE + FireDrive.bindMsgBlue);
                }
            }
            else if (FireDrive.playerBlue.contains(player.getUniqueId())) {
                FireDrive.playerBlue.remove(player.getUniqueId());
                player.sendMessage(ChatColor.BLUE + FireDrive.unbindMsgBlue);
            }
        }
        else {
            player.sendMessage(Element.FIRE.getColor() + "No");
        }
    }
    
    public String getAuthor() {
        return "Prride";
    }
    
    public String getVersion() {
        return "Build v1.0";
    }
    
    public String getDescription() {
        return "FireDrive Version " + this.getVersion() + " is created by " + this.getAuthor() + ". " + "\nFirebenders like Azula are able to use their FireJet to propel themselves backwards instead of forwards! They will deal damage as they bash towards their enemy.";
    }
    
    public String getInstructions() {
        return "Tap sneak to propel yourself backwards.";
    }
    
    public void load() {
        ProjectKorra.plugin.getServer().getPluginManager().registerEvents((Listener)new FireDriveListener(), (Plugin)ProjectKorra.plugin);
        ProjectKorra.log.info(String.valueOf(this.getName()) + " " + this.getVersion() + " by " + this.getAuthor() + " loaded! ");
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.FireDrive.Cooldown", (Object)6000);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.FireDrive.Duration", (Object)2100);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.FireDrive.Damage", (Object)3);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.FireDrive.Speed", (Object)0.9);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.FireDrive.BlueFireDrive.Enabled", (Object)true);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.FireDrive.BlueFireDrive.Speed", (Object)1.1);
        ConfigManager.defaultConfig.save();
    }
    
    public void stop() {
    }
}

