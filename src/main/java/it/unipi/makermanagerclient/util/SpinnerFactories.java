package it.unipi.makermanagerclient.util;

import javafx.scene.control.SpinnerValueFactory;
import javafx.util.StringConverter;

/**
 * Factory per SpinnerValueFactory.IntegerSpinnerValueFactory con un
 * converter "sicuro" verso i valori nulli.
 *
 * Quando l'editor di uno Spinner editabile perde il focus, JavaFX chiama
 * Spinner.commitValue(), che a sua volta chiama converter.fromString(testo)
 * e passa il risultato a valueFactory.setValue(...). Se il campo e' stato
 * svuotato, il converter di default (IntegerStringConverter) restituisce
 * null; il listener interno di IntegerSpinnerValueFactory pero' confronta
 * quel valore con getMin() senza controllare che non sia null, causando
 * una NullPointerException (visibile anche solo cliccando altrove, senza
 * premere alcun tasto). Per evitarlo il converter qui sotto ripiega
 * sull'ultimo valore valido invece di restituire null.
 */
public final class SpinnerFactories {

    private SpinnerFactories() {
        throw new UnsupportedOperationException(
            "SpinnerFactories è una classe di utilità statica e non può essere istanziata."
        );
    }

    public static SpinnerValueFactory.IntegerSpinnerValueFactory interoSicuro(
        int min, int max, int valoreIniziale
    ) {

        var fabbrica = new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, valoreIniziale);
        StringConverter<Integer> converterDefault = fabbrica.getConverter();

        fabbrica.setConverter(new StringConverter<>() {

            @Override
            public String toString(Integer valore) {
                return converterDefault.toString(valore);
            }

            @Override
            public Integer fromString(String testo) {
                Integer valore = converterDefault.fromString(testo);
                return valore != null ? valore : fabbrica.getValue();
            }

        });

        return fabbrica;

    }

}
