package net.pevori.tameablearachnereborn.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class TameableArachneRebornConfig extends MidnightConfig {
    // region Arachne
    @Comment()
    public static Comment arachneComment;

    @Entry(name = "tameablearachnereborn.midnightconfig.arachne_spawn_flag")
    public static boolean arachneSpawnFlag  = true;

    @Entry(name = "tameablearachnereborn.midnightconfig.arachne_base_hp", min = 0)
    public static int arachneBaseHp = 20;

    @Entry(name = "tameablearachnereborn.midnightconfig.arachne_base_attack", min = 0)
    public static int arachneBaseAttack = 4;

    @Entry(name = "tameablearachnereborn.midnightconfig.arachne_base_defense", min = 0)
    public static int arachneBaseDefense = 2;
    // endregion

    //region Arachne Medium
    @Comment() public static Comment spacer1;
    @Comment() public static Comment arachneMediumComment;

    @Entry(name = "tameablearachnereborn.midnightconfig.arachne_medium_spawn_flag")
    public static boolean arachneMediumSpawnFlag  = true;

    @Entry(name = "tameablearachnereborn.midnightconfig.arachne_medium_base_hp", min = 0)
    public static int arachneMediumBaseHp = 30;

    @Entry(name = "tameablearachnereborn.midnightconfig.arachne_medium_base_attack", min = 0)
    public static int arachneMediumBaseAttack = 6;

    @Entry(name = "tameablearachnereborn.midnightconfig.arachne_medium_base_defense", min = 0)
    public static int arachneMediumBaseDefense = 3;
    //endregion

    //region Harpy

    @Comment() public static Comment spacer2;
    @Comment()
    public static Comment harpyComment;

    @Entry(name = "tameablearachnereborn.midnightconfig.harpy_spawn_flag")
    public static boolean harpySpawnFlag  = true;

    @Entry(name = "tameablearachnereborn.midnightconfig.harpy_base_hp", min = 0)
    public static int harpyBaseHp = 20;

    @Entry(name = "tameablearachnereborn.midnightconfig.harpy_base_attack", min = 0)
    public static int harpyBaseAttack = 2;

    @Entry(name = "tameablearachnereborn.midnightconfig.harpy_base_defense", min = 0)
    public static int harpyBaseDefense = 6;

    @Entry(name = "tameablearachnereborn.midnightconfig.harpy_add_drop_rate", min = 0)
    public static int harpyAddDropRate = 30;
    //endregion

    @Comment() public static Comment spacer3;
    @Comment() public static Comment genericComment;

    @Entry(name = "tameablearachnereborn.midnightconfig.hp_up", min = 0)
    public static int hpUp = 2;

    @Entry(name = "tameablearachnereborn.midnightconfig.attack_up", min = 0)
    public static int attackUp = 1;

    @Entry(name = "tameablearachnereborn.midnightconfig.defense_up", min = 0)
    public static int defenseUp = 1;

    @Entry(name = "tameablearachnereborn.midnightconfig.power_up_limit", min = 0)
    public static int powerUpLimit = 16;

    @Entry(name = "tameablearachnereborn.midnightconfig.name_bonus")
    public static boolean nameBonus = true;

    @Entry(name = "tameablearachnereborn.midnightconfig.name_bonus_value", min = 0)
    public static int nameBonusValue = 1;

    @Entry(name = "tameablearachnereborn.midnightconfig.auto_heal")
    public static boolean autoHeal  = true;

    @Entry(name = "tameablearachnereborn.midnightconfig.auto_heal_value", min = 0)
    public static int autoHealValue = 1;

    @Entry(name = "tameablearachnereborn.midnightconfig.auto_heal_interval", min = 0)
    public static int autoHealInterval = 50;

    @Entry(name = "tameablearachnereborn.midnightconfig.craftable_spawn_eggs")
    public static boolean craftableSpawnEggs  = true;

    @Entry(name = "tameablearachnereborn.midnightconfig.protection_limit", min = 0)
    public static int protectionLimit = 80;

    @Entry(name = "tameablearachnereborn.midnightconfig.fire_protection_limit", min = 0)
    public static int fireProtectionLimit = 80;

    @Entry(name = "tameablearachnereborn.midnightconfig.fall_protection_limit", min = 0)
    public static int fallProtectionLimit = 80;

    @Entry(name = "tameablearachnereborn.midnightconfig.blast_protection_limit", min = 0)
    public static int blastProtectionLimit = 80;

    @Entry(name = "tameablearachnereborn.midnightconfig.projectile_protection_limit", min = 0)
    public static int projectileProtectionLimit = 80;
}
