package com.markmolenmaker.dioninsanity.backend.models.cluebingo;

import java.util.Random;

public enum ELoot {
    MITHRIL_FULLHELMET, MITHRIL_PLATEBODY, MITHRIL_PLATELEGS, MITHRIL_PLATESKIRT, MITHRIL_KITESHIELD,
    MITHRIL_FULLHELMET_GT, MITHRIL_PLATEBODY_GT, MITHRIL_PLATELEGS_GT, MITHRIL_PLATESKIRT_GT, MITHRIL_KITESHIELD_GT,
    ADAMANT_FULLHELMET, ADAMANT_PLATEBODY, ADAMANT_PLATELEGS, ADAMANT_PLATESKIRT, ADAMANT_KITESHIELD,
    ADAMANT_GT_FULLHELMET, ADAMANT_GT_PLATEBODY, ADAMANT_GT_PLATELEGS, ADAMANT_GT_PLATESKIRT, ADAMANT_GT_KITESHIELD,
    CLIMBING_BOOTS, SPIKED_MANACLES, RANGER_BOOTS, HOLY_SANDALS, WIZARD_BOOTS,
    HEADBAND_BLACK, HEADBAND_RED, HEADBAND_BROWN, HEADBAND_PINK, HEADBAND_GREEN, HEADBAND_BLUE, HEADBAND_WHITE, HEADBAND_GOLD,
    BOATER_RED, BOATER_ORANGE, BOATER_GREEN, BOATER_BLUE, BOATER_BLACK, BOATER_PURPLE, BOATER_PINK, BOATER_WHITE,
    GREEN_DHIDE_BODY, GREEN_DHIDE_CHAPS, GREEN_DHIDE_BODY_GT, GREEN_DHIDE_CHAPS_GT,
    ADAMANT_HELMET_H1, ADAMANT_HELMET_H2, ADAMANT_HELMET_H3, ADAMANT_HELMET_H4, ADAMANT_HELMET_H5,
    ADAMANT_PLATEBODY_H1, ADAMANT_PLATEBODY_H2, ADAMANT_PLATEBODY_H3, ADAMANT_PLATEBODY_H4, ADAMANT_PLATEBODY_H5,
    ADAMANT_SHIELD_H1, ADAMANT_SHIELD_H2, ADAMANT_SHIELD_H3, ADAMANT_SHIELD_H4, ADAMANT_SHIELD_H5,
    ELEGANT_SHIRT_BLACK, ELEGANT_SHIRT_PURPLE, ELEGANT_SHIRT_PINK, ELEGANT_SHIRT_GOLD,
    ELEGANT_LEGS_BLACK, ELEGANT_LEGS_PURPLE, ELEGANT_LEGS_PINK, ELEGANT_LEGS_GOLD,
    ELEGANT_BLOUSE_BLACK, ELEGANT_BLOUSE_PURPLE, ELEGANT_BLOUSE_PINK, ELEGANT_BLOUSE_GOLD,
    ELEGANT_SKIRT_BLACK, ELEGANT_SKIRT_PURPLE, ELEGANT_SKIRT_PINK, ELEGANT_SKIRT_GOLD,
    WOLF_MASK, WOLF_CLOAK, AMULET_OF_STRENGTH, ADAMANT_CANE,
    MITRE_GUTHIX, MITRE_SARADOMIN, MITRE_ZAMORAK, MITRE_BANDOS, MITRE_ARMADYL, MITRE_ANCIENT,
    CLOAK_GUTHIX, CLOAK_SARADOMIN, CLOAK_ZAMORAK, CLOAK_BANDOS, CLOAK_ARMADYL, CLOAK_ANCIENT,
    STOLE_ANCIENT, STOLE_ARMADYL, STOLE_BANDOS,
    CROZIER_ANCIENT, CROZIER_ARMADYL, CROZIER_BANDOS,
    CAT_MASK, PENGUIN_MASK, GNOMISH_FIRELIGHTER,
    CRIER_HAT, CRIER_BELL, CRIER_COAT,
    LEPRECHAUN_HAT, LEPRECHAUN_HAT_BLACK, UNICORN_MASK_BLACK, UNICORN_MASK_WHITE,
    BANNER_ARCEUUS, BANNER_HOSIDIUS, BANNER_LOVAKENGJ, BANNER_PISCARILIUS, BANNER_SHAYZIEN,
    CABBAGE_ROUND_SHIELD, CLUELESS_SCROLL;

    private static final Random PRNG = new Random();
    public static ELoot getRandomELoot() {
        ELoot[] loot = values();
        return loot[PRNG.nextInt(loot.length)];
    }
}