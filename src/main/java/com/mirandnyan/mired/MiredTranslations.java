package com.mirandnyan.mired;

import com.mirandnyan.mired.util.LangMap;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;

/** AKA Lang */
public class MiredTranslations {
    public static final LangEntry CREATIVE_MODE_TAB = new LangEntry("itemGroup", Mired.MOD_ID, Mired.MOD_NAME);

    public static final LangEntry ANALOG_SCROLL_VALUE = new LangEntry("scroll_value_behaviour.analog_scoll_value", "Analog Value");

    // Analog Computator
    public static final LangEntry ADDITION_MODE = new LangEntry("analog_computator.computation_mode.addition", "Addition Mode");
    public static final LangEntry SUBTRACTION_MODE = new LangEntry("analog_computator.computation_mode.subtraction", "Subtraction Mode");
    public static final LangEntry MULTIPLICATION_MODE = new LangEntry("analog_computator.computation_mode.multiplication", "Multiplication Mode");
    public static final LangEntry DIVISION_MODE = new LangEntry("analog_computator.computation_mode.division", "Division Mode");
    public static final LangEntry COMPUTATION_MODE = new LangEntry("analog_computator.computation_mode", "Computation Mode");

    public static final LangEntry TOOLTIP_RISING_EDGE_ONLY_ON = new LangEntry("tooltip.analog_sr_latch.risingEdgeOnlyOn", "Updates on Rising Edge only");
    public static final LangEntry TOOLTIP_RISING_EDGE_ONLY_OFF = new LangEntry("tooltip.analog_sr_latch.risingEdgeOnlyOff", "Updates continuously");

    // Analog Gate
    public static final LangEntry GATE_VALUE = new LangEntry("analog_gate.gate_value", "Gate Value");

    public static final LangEntry TOOLTIP_MODE_GREATER_EQUAL = new LangEntry("tooltip.analog_gate.mode.greater_equal", "Greater than or Equal");
    public static final LangEntry TOOLTIP_MODE_LESS_EQUAL = new LangEntry("tooltip.analog_gate.mode.less_equal", "Less than or Equal");

    public static class LangEntry extends LangMap {
        public LangEntry(String translationKey, String textEnglish) {
            this(Mired.MOD_ID, translationKey, textEnglish);
        }
        public LangEntry(String prefix, String translationKey, String textEnglish) {
            super((prefix.isEmpty() ? "" : prefix + ".") + translationKey, textEnglish);
            Mired.getRegistrate().addRawLang(this.translationKey, this.textEnglish);
        }

        public Component resolveComponent() {
            return Component.translatable(translationKey);
        }
        public String resolveString() {
            return Language.getInstance().getOrDefault(translationKey);
        }
    }

    public static void register() {
        // load class for datagen
    }
}
