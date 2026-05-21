package com.mirandnyan.mired;

import net.minecraft.resources.ResourceLocation;

/** AKA Lang */
public class MiredTranslations {
    public static final LangEntry CREATIVE_MODE_TAB = new LangEntry("itemGroup", Mired.MOD_ID, Mired.MOD_NAME);

    public static final LangEntry ADDITION_MODE = new LangEntry("analog_computator.computation_mode.addition", "Addition Mode");
    public static final LangEntry SUBTRACTION_MODE = new LangEntry("analog_computator.computation_mode.subtraction", "Subtraction Mode");
    public static final LangEntry MULTIPLICATION_MODE = new LangEntry("analog_computator.computation_mode.multiplication", "Multiplication Mode");
    public static final LangEntry DIVISION_MODE = new LangEntry("analog_computator.computation_mode.division", "Division Mode");
    public static final LangEntry COMPUTATION_MODE = new LangEntry("analog_computator.computation_mode", "Computation Mode");


    public static final LangEntry TOOLTIP_RISING_EDGE_ONLY_ON = new LangEntry("tooltip.analog_sr_latch.risingEdgeOnlyOn", "Updates on Rising Edge only");
    public static final LangEntry TOOLTIP_RISING_EDGE_ONLY_OFF = new LangEntry("tooltip.analog_sr_latch.risingEdgeOnlyOff", "Updates continuously");


    public static class LangEntry {
        public String textEnglish;
        public String translationKey;
        public LangEntry(String translationKey, String textEnglish) {
            this(Mired.MOD_ID, translationKey, textEnglish);
        }
        public LangEntry(String prefix, String translationKey, String textEnglish) {
            this.textEnglish = textEnglish;
            this.translationKey = (prefix.isEmpty() ? "" : prefix + ".") + translationKey;
            Mired.getRegistrate().addRawLang(this.translationKey, textEnglish);
        }
    }

    public static void register() {
        // load class for datagen
    }
}
