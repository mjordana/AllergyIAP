package com.allergyiap.entities;

import android.graphics.Bitmap;

public class AllergyEntity extends Entity {

    public int id;
    public String type;
    public int risk;
    public String preview;

    public AllergyEntity(){

    }

    public AllergyEntity(int id, String type, int risk, String preview){

        this.id = id;
        this.type = type;
        this.risk = risk;
        this.preview = preview;
    }

    /**
     <current>
         <value en="Null">0</value>
         <value en="Low">1</value>
         <value en="Medium">2</value>
         <value en="High">3</value>
         <value en="Max">4</value>
     </current>
     <forecast>
         <value en="Increase">A</value>
         <value en="Stable">=</value>
         <value en="Decrease">D</value>
         <value en="Attention">!</value>
     </forecast>
     *
     */
}
