package com.example.garaiza.mapas;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by garaiza on 3/7/16.
 */
public class Fontenizer extends TextView {
    public Fontenizer(Context context) {
        super(context);
        setFont();
    }

    /**
     * Constructor.
     * @param context
     * @param attrs
     */
    public Fontenizer(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }


    public Fontenizer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont();
    }
    /**
     * Set the text font.
     */
    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "2/Roboto-Bold.ttf");
        setTypeface(font, Typeface.NORMAL);
    }

}
//com.example.garaiza.mapas.Fontenizer