package com.mirandnyan.mired;

import net.minecraft.resources.ResourceLocation;

public class MiredLang {
    public static final LangEntry ADDITION_MODE = new LangEntry("analog_computator.computation_mode.addition", "Addition Mode");
    public static final LangEntry SUBTRACTION_MODE = new LangEntry("analog_computator.computation_mode.subtraction", "Addition Mode");
    public static final LangEntry MULTIPLICATION_MODE = new LangEntry("analog_computator.computation_mode.multiplication", "Addition Mode");
    public static final LangEntry DIVISION_MODE = new LangEntry("analog_computator.computation_mode.division", "Addition Mode");
    public static final LangEntry COMPUTATION_MODE = new LangEntry("analog_computator.computation_mode", "Computation Mode");



    public static class LangEntry {
        public String textEnglish;
        public String type;
        public String translationKey;
        public LangEntry(String translationKey, String textEnglish) {
            this("", translationKey, textEnglish);
        }
        public LangEntry(String prefix, String translationKey, String textEnglish) {
            this.textEnglish = textEnglish;
            this.translationKey = translationKey;
            var loc = ResourceLocation.tryBySeparator(translationKey, '.');

            String type = (prefix.isEmpty() ? "" : prefix + ".") + Mired.MOD_ID;
            if (loc != null)
                Mired.getRegistrate().addLang(type, loc, textEnglish);
        }
    }

    public static void register() {
        // load class for datagen
    }
}
