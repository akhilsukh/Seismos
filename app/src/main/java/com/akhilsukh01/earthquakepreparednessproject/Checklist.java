package com.akhilsukh01.earthquakepreparednessproject;

import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.Window;
import android.widget.TextView;

public class Checklist extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_checklist);


        TextView linkJimdo = (TextView) findViewById(R.id.linkJimdo);
        Spanned text1 = Html.fromHtml("<a href='https://earthquakeap.jimdo.com/'>earthquakeap.jimdo.com</a>");
        linkJimdo.setMovementMethod(LinkMovementMethod.getInstance());
        linkJimdo.setText(text1);

        TextView linkRed = (TextView) findViewById(R.id.linkRed);
        Spanned text2 = Html.fromHtml("<a href='http://www.redcross.org/get-help/how-to-prepare-for-emergencies/types-of-emergencies/earthquake#Before'>redcross.org</a>");
        linkRed.setMovementMethod(LinkMovementMethod.getInstance());
        linkRed.setText(text2);

        TextView linkFema = (TextView) findViewById(R.id.linkFema);
        Spanned text3 = Html.fromHtml("<a href='https://www.fema.gov/earthquake-safety-home'>fema.gov</a>");
        linkFema.setMovementMethod(LinkMovementMethod.getInstance());
        linkFema.setText(text3);

        TextView linkNatGeo = (TextView) findViewById(R.id.linkNatGeo);
        Spanned text4 = Html.fromHtml("<a href='https://www.nationalgeographic.com/environment/natural-disasters/earthquake-safety-tips/'>nationalgeographic.com</a>");
        linkNatGeo.setMovementMethod(LinkMovementMethod.getInstance());
        linkNatGeo.setText(text4);

        TextView linkCountry = (TextView) findViewById(R.id.linkCountry);
        Spanned text5 = Html.fromHtml("<a href='https://www.earthquakecountry.org/sevensteps/'>earthquakecountry.org</a>");
        linkCountry.setMovementMethod(LinkMovementMethod.getInstance());
        linkCountry.setText(text5);

    }
}
