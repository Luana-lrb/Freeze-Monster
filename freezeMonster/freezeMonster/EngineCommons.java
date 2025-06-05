package freezeMonster;

import spriteframework.Commons;

import java.awt.*;

public class EngineCommons implements Commons {
    private static EngineCommons instance = null;

    private EngineCommons(){
        instance = this;
    }

    public static Commons getNewInstance(){
        if( instance == null){
            return new EngineCommons();
        } else {
            return instance;
        }
    }

    @Override
    public int getBoardHeight() {return 600;}

    @Override
    public int getBoardWidth() {return 600;}

    @Override
    public int getBorderBotton() {return 30;}

    @Override
    public int getBorderRight() {return 30;}

    @Override
    public int getBorderLeft() {return 5;}

    @Override
    public int getGround() {return 525;}

    @Override
    public int getTop() {return 0;}

    public int getNumber_bad_sprites_to_destoy() {return 10;}

    @Override
    public int getBad_sprite_width() {return 40;}
    @Override
    public int getBad_sprite_height() {return 40;}

    public int get_Bad_sprite_init_x(){return 150;}

    public int get_Bad_sprite_init_y(){return 5;}

    public int get_Go_down(){return 15;}

    public int getChance(){return 5;}

    public int getPlayerHeight() {return 70;}

    public int getPlayerWidth(){return 50;}

    @Override
    public int getAttackHeight() {return 40;}

    public String getBackgroungPath(){return "/images/darkcloudsbg.png";}

    public int getInitPlayerX(){return 270;}
    public int getInitPlayerY(){return 280;}

    public int getAttackVelocity(){return 3;}
    public int getMonsterVelocity(){ return 1;}

    public String getBackgroungMusic(){return "/sound/amigoEstouAqui.wav";}
    public Color getFinalLineColor() {return new Color(0,0,0,0);} //invisivel
}
