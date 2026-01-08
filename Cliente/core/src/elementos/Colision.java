package elementos;

public class Colision {
    public static final short COLISION_LINEAS = 0x0001; 
    public static final short COLISION_PELOTA = 0x0002; 
    public static final short COLISION_PALOS = 0x0004; 
    public static final short COLISION_JUGADOR = 0x0008;
    public static final short MASCARA_PALOS = COLISION_PELOTA; 
    public static final short MASCARA_PELOTA = COLISION_LINEAS | COLISION_PALOS | COLISION_JUGADOR;
    public static final short MASCARA_PELOTA_JUGADOR = COLISION_PALOS | COLISION_JUGADOR;
    public static final short MASCARA_JUGADOR = MASCARA_PELOTA| COLISION_PALOS|COLISION_JUGADOR;
}
